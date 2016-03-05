public abstract class MathOperationStatus implements IExpressionFunctionStatus {

	public MathOperationStatus() {
		super();
	}

	public void evaluateFunction(List<Object> args) throws Throwable {
		if (args.size() == 2) { 
			StatusAttribute s1;
			if (args.get(0) instanceof StatusAttribute) {
				s1 = (StatusAttribute) args.get(0);
			} else {
				throw new Exception("First argument it's not a Status Attribute");
			}
			Object o2 = args.get(1); 
			ArithmeticEvaluatorStatus evaluator = ArithmeticEvaluatorFactoryStatus.getInstance().getEvaluator(o2);
			op(evaluator, s1, o2); 						
		} else {
			throw new Exception("Illegal number of arguments");
		}
	}

	/*
	 * abstract method for operation
	 */
	abstract protected void op(ArithmeticEvaluatorStatus ev, StatusAttribute s1, Object o2) throws Throwable;

}
