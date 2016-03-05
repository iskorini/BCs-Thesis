public class AddStatus extends MathOperationStatus {

	@Override
	protected void op(ArithmeticEvaluatorStatus ev, StatusAttribute s1, Object o2) throws Throwable {
		ev.add(s1, o2);
	}
}
