public class Obligation extends AbstractObligation {
	public Obligation(String pepAction, Effect evaluatedOn, ObligationType type, Object... args) {
		super(evaluatedOn, type, args);
		this.pepAction = pepAction;
		if (args != null) {
			for (Object ob : args) {
				argsFunction.add(ob);
			}
		}
	}
	@Override
	protected AbstractFulfilledObligation createObligation() {
		return new FullfilledObbligation(this.evaluatedOn, this.typeObl, (String) this.pepAction);
	}
}
