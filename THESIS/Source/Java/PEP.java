public class PEP {
	private static HashMap<String, Class<? extends IPepAction>> pepAction;
	private EnforcementAlgorithm alg;
	public PEP(EnforcementAlgorithm alg) {
		this.alg = alg;
	}
	public AuthorisationPEP doEnforcement(AuthorisationPDP authPDP) {
		Logger l = LoggerFactory.getLogger(PEP.class);
		l.debug("Start PEP enforcement for request: " + authPDP.getId());
		l.debug("Authorisation to enforce: " + authPDP.toString());

		if (pepAction == null) {
			l.debug("Pep actions not inizialized");
		}
		try {
			AuthorisationPEP decPEP = null;
			StandardDecision dec = authPDP.getStandardDecision();
			switch (this.alg) {
			case BASE:
				l.debug("Chosen Enforcement Algorithm " + alg.toString());
				if (dec.equals(StandardDecision.DENY) || dec.equals(StandardDecision.PERMIT)) {
					for (AbstractFulfilledObligation obl : authPDP.getObligation()) {
						try {
							this.dischargeObligation(obl);
						} catch (Throwable t) {
							l.debug("Obligation Evaluation Failed");
							l.debug("Enforced Decision:" + StandardDecision.INDETERMINATE);
							return new AuthorisationPEP(authPDP.getId(), StandardDecision.INDETERMINATE);
						}
					}
					decPEP = new AuthorisationPEP(authPDP.getId(), dec);
				} else {
					l.debug("No Obligations to discharge. Enforcement completed");
					decPEP = new AuthorisationPEP(authPDP.getId(), dec);
				}
				break;
			case DENY_BIASED:
				l.debug("Chosen Enforcement Algorithm " + alg.toString());
				if (dec.equals(StandardDecision.PERMIT)) {
					for (AbstractFulfilledObligation obl : authPDP.getObligation()) {
						try {
							this.dischargeObligation(obl);
						} catch (Throwable t) {
							l.debug("Obligation Evaluation Failed");
							l.debug("Enforced Decision:" + StandardDecision.DENY);
							return new AuthorisationPEP(authPDP.getId(), StandardDecision.DENY);
						}
					}
					decPEP = new AuthorisationPEP(authPDP.getId(), dec);
				} else {
					l.debug("No Obligations to discharge. Enforcement completed");
					decPEP = new AuthorisationPEP(authPDP.getId(), StandardDecision.DENY);
				}
				break;
			case PERMIT_BIASED:
				if (dec.equals(StandardDecision.DENY)) {
					for (AbstractFulfilledObligation obl : authPDP.getObligation()) {
						try {
							this.dischargeObligation(obl);
						} catch (Throwable t) {
							l.debug("Obligations Evaluation Failed");
							l.debug("Enforced Decision:" + StandardDecision.PERMIT);
							return new AuthorisationPEP(authPDP.getId(), StandardDecision.PERMIT);
						}
					}
					decPEP = new AuthorisationPEP(authPDP.getId(), dec);
				} else {
					l.debug("No Obligations to discharge. Enforcement completed");
					decPEP = new AuthorisationPEP(authPDP.getId(), StandardDecision.PERMIT);
				}
				break;
			}
			l.debug("Enforced Decision:" + decPEP.toString());
			return decPEP;
		} catch (Throwable t) {
			l.debug("Unhandled Exception. Enforced Decision: INDETERMINATE");
			return new AuthorisationPEP(authPDP.getId(), StandardDecision.INDETERMINATE);
		}
	}
	private void dischargeObligation(AbstractFulfilledObligation obl) throws Throwable {
		Logger l = LoggerFactory.getLogger(PEP.class);
		if (obl instanceof FullfilledObbligation) {
			obl = (FullfilledObbligation) obl;
			try {
				Class<? extends IPepAction> classAction = pepAction.get((String) obl.getPepAction());
				if (classAction == null) {
					l.debug("Undefined PEP action \"" + (String) obl.getPepAction() + "\"");
					throw new Exception("Undefined " + (String) obl.getPepAction() + " PEP Action");
				}
				Class<?> params[] = new Class[1];
				params[0] = List.class;
				Method eval = classAction.getDeclaredMethod("eval", params);
				Object pepAction = classAction.newInstance();
				eval.invoke(pepAction, obl.getArguments());
			} catch (Throwable t) {
				if (obl.getType().equals(ObligationType.M)) {
					throw t;
				}
				l.debug("Exception ignored. Obligation is optional");
			}
		}
		else if (obl instanceof FulfilledObligationStatus) {
			obl = (FulfilledObligationStatus) obl;
			obl.evaluateObl();
		}

	}
	public void addPEPActions(HashMap<String, Class<? extends IPepAction>> classPepActions) {
		Logger l = LoggerFactory.getLogger(PEP.class);
		l.debug("Add standard actions"); 

		pepAction = new HashMap<String, Class<? extends IPepAction>>();
		pepAction.put("mail", it.unifi.facpl.lib.pepFunction.MailTo.class);
		pepAction.put("log", it.unifi.facpl.lib.pepFunction.Log.class);
		pepAction.put("compress", it.unifi.facpl.lib.pepFunction.Compress.class);

		if (classPepActions != null) {
			for (String key : classPepActions.keySet()) {
				l.debug("Add action " + key);
			}
			pepAction.putAll(classPepActions);
		}

	}

}
