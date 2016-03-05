public class FulfilledObligationStatus extends AbstractFulfilledObligation {
	private IExpressionFunctionStatus pepFunction;
	public FulfilledObligationStatus(Effect effect, ObligationType typeObl, IExpressionFunctionStatus pepFunction) {
		super(effect, typeObl);
		this.pepFunction = pepFunction;
	}
	public FulfilledObligationStatus(Effect effect, ObligationType typeObl,
			Class<? extends IExpressionFunctionStatus> pepFunction) {
		super(effect, typeObl);
	}
	@Override
	public AbstractFulfilledObligation evaluateObl() throws Throwable {
		this.pepFunction.evaluateFunction(this.getArgsStatus());
		return this;
	}
	@Override
	public String toString() {
		return evaluatedOn + " " + type.toString() + " " + pepFunction.toString() + "(" + arguments.toString() + ")";
	}
	@Override
	public Object getPepAction() {
		return this.pepFunction;
	}
}
