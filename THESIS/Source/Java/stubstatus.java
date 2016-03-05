public class ContextStub_Status_Default extends AbstractContextStub {
	private static ContextStub_Status_Default instance;
	private static FacplStatus status;
	public static ContextStub_Status_Default getInstance() {
		if (instance == null) {
			instance = new ContextStub_Status_Default();
		}
		return instance;
	}
	private ContextStub_Status_Default() {	}
	public static void setStatus(FacplStatus status) {
		ContextStub_Status_Default.status = status;
	}
	public StatusAttribute getStatusAttribute(StatusAttribute attribute) throws MissingAttributeException {
		return status.retrieveAttribute(attribute);
	}
	public Object getContextValues(Object attr) {
		try {
			if (attr instanceof StatusAttribute) {
				return status.getValue((StatusAttribute) attr);
			} else {
				return super.getContextValues(attr);
			}
		} catch (MissingAttributeException e) {
			return null;
		}
	}
}