public class MainFACPL {
	private PDP pdp;
	private PEP pep;
	public MainFACPL() throws MissingAttributeException {
		LinkedList<FacplPolicy> policies = new LinkedList<FacplPolicy>();
		policies.add(new PolicySet_ReadWrite(ContextRequest_WriteRequestAlice.getContextReq()));
		this.pdp = new PDP(it.unifi.facpl.lib.algorithm.PermitUnlessDenyGreedy.class, policies, false);
		this.pep = new PEP(EnforcementAlgorithm.DENY_BIASED);
		this.pep.addPEPActions(PEPAction.getPepActions());
	}
	public static void main(String[] args) throws MissingAttributeException {
		MainFACPL system = new MainFACPL();
		StringBuffer result = new StringBuffer();
		LinkedList<ContextRequest_Status> requests = new LinkedList<ContextRequest_Status>();
		requests.add(ContextRequest_ReadRequestAlice.getContextReq()); 
		requests.add(ContextRequest_WriteRequestBob.getContextReq()); 
		requests.add(ContextRequest_ReadRequestBob.getContextReq()); 
		requests.add(ContextRequest_StopReadRequestAlice.getContextReq()); 
		requests.add(ContextRequest_StopReadRequestBob.getContextReq()); 
		requests.add(ContextRequest_WriteRequestAlice.getContextReq()); 
		for (ContextRequest rcxt : requests) {
			AuthorisationPDP resPDP = system.pdp.doAuthorisation(rcxt);
			AuthorisationPEP resPEP = system.pep.doEnforcement(resPDP);
		}
	}
}
