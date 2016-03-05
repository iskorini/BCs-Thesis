public abstract class AbstractObligation implements IObligationElement {
	protected Effect evaluatedOn;
	protected ObligationType typeObl;
	protected Object pepAction;
	protected LinkedList<Object> argsFunction, argsStatus; 
	public AbstractObligation(Effect evaluatedOn, ObligationType type, Object... args) {
		this.evaluatedOn = evaluatedOn;
		this.typeObl = type;
		this.argsFunction = new LinkedList<Object>();
		this.argsStatus = new LinkedList<Object>();
	}
	protected abstract AbstractFulfilledObligation createObligation();
	@Override
	public AbstractFulfilledObligation getObligationValue(ContextRequest cxtRequest) throws FulfillmentFailed {
		Logger l = LoggerFactory.getLogger(Obligation.class);
		l.debug("Fulfilling Obligation " + this.pepAction.toString() + "...");
		AbstractFulfilledObligation obl = this.createObligation();
		if (obl instanceof FulfilledObligationCheck) {
			l.debug("...created FulfilledObligationCHECK: " + obl.toString());
			return obl;
		}
		for (Object arg : argsFunction) {
			if (arg instanceof ExpressionFunction) {
				Object res = ((ExpressionFunction) arg).evaluateExpression(cxtRequest);
				if (res.equals(ExpressionValue.BOTTOM) || res.equals(ExpressionValue.ERROR)) {
					throw new FulfillmentFailed();
				}
				obl.addArg(res);
			} else if (arg instanceof ExpressionBooleanTree) {
				ExpressionValue res = ((ExpressionBooleanTree) arg).evaluateExpressionTree(cxtRequest);
				if (res.equals(ExpressionValue.BOTTOM) || res.equals(ExpressionValue.ERROR)) {
					throw new FulfillmentFailed();
				}
				obl.addArg(res);
			} else if (arg instanceof AttributeName) {
				try {
					obl.addArg(cxtRequest.getContextRequestValues((AttributeName) arg));
				} catch (MissingAttributeException e) {
					throw new FulfillmentFailed();
				}
			} else {
				obl.addArg(arg);
			}
		}
		l.debug("...fulfillment completed. Arguments: " + obl.getArguments().toString());
		return obl;
	}

	@Override
	public Effect getEvaluatedOn() {
		return this.evaluatedOn;
	}
	@Override
	public ObligationType getTypeObl() {
		return this.typeObl;
	}
}