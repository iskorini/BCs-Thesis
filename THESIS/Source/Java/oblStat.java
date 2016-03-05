public class ObligationStatus extends AbstractObligation {
	public ObligationStatus(IExpressionFunctionStatus pepAction, Effect evaluatedOn, ObligationType type,
			Object... args) {
		super(evaluatedOn, type, args);
		this.pepAction = pepAction;
		if (args != null) {
			for (Object ob : args) {
				if (ob instanceof StatusAttribute || ob instanceof Integer || ob instanceof Double
						|| ob instanceof Boolean || ob instanceof FacplDate) {
					argsStatus.add(ob);
				}
				argsFunction.add(ob);
			}
		}
	}
	@Override
	protected AbstractFulfilledObligation createObligation() {
		AbstractFulfilledObligation obl = new FulfilledObligationStatus(this.evaluatedOn, this.typeObl,
				(IExpressionFunctionStatus) this.pepAction);
		if (!argsStatus.isEmpty()) {
			obl.addArgStatus(argsStatus);
		}
		return obl;
	}
}
