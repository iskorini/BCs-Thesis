public class Equal implements IComparisonFunction {
	public Boolean evaluateFunction(List<Object> args) throws Throwable {
		if (args.size() == 2) {
			Object o1, o2;
			o1 = args.get(0) instanceof StatusAttribute ? this.convertType((StatusAttribute) args.get(0)) : args.get(0);
			o2 = args.get(1) instanceof StatusAttribute ? this.convertType((StatusAttribute) args.get(1)) : args.get(1);
			IComparisonEvaluator evaluator = ComparisonEvaluatorFactory.getInstance().getEvaluator(o1);
			return evaluator.areEquals(o1, o2);
		} else {
			throw new Exception("Illegal number of arguments");
		}
	}
	private Object convertType(StatusAttribute sa) {
		if (sa.getType() == FacplStatusType.BOOLEAN) {
			if (sa.getValue() == "true") {
				return true;
			} else
				return false;
		} else if (sa.getType() == FacplStatusType.DOUBLE) {
			return (Double)Double.parseDouble(sa.getValue());
		} else if (sa.getType() == FacplStatusType.INT) {
			return (Integer)Integer.parseInt(sa.getValue());
		} else if (sa.getType() == FacplStatusType.STRING) {
			return sa.getValue();
		}
		return null;
	}
}
