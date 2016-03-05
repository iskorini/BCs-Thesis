public abstract class AbstractFulfilledObligation {
	protected Effect evaluatedOn;
	protected ObligationType type;
	protected LinkedList<Object> arguments;
	protected LinkedList<Object> argsStatus;
	public AbstractFulfilledObligation(Effect effect, ObligationType typeObl) {
		this.type = typeObl;
		this.evaluatedOn = effect;
		this.arguments = new LinkedList<Object>();
	}
	public AbstractFulfilledObligation() {

	}
	public void addArg(Object object) {
		if (this.arguments == null) {
			this.arguments = new LinkedList<Object>();
		}
		this.arguments.add(object);
	}
	public void addArgStatus(LinkedList<Object> ob) {
		if (this.argsStatus == null) {
			this.argsStatus = new LinkedList<Object>();
		}
		this.argsStatus = ob;
	}
	public Effect getEvaluatedOn() {
		return evaluatedOn;
	}
	public ObligationType getType() {
		return type;
	}
	public abstract Object getPepAction();
	public LinkedList<Object> getArguments() {
		return arguments;
	}
	public LinkedList<Object> getArgsStatus() {
		return argsStatus;
	}
	public abstract AbstractFulfilledObligation evaluateObl() throws Throwable;
}