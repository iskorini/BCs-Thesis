public class FacplStatus {
	private List<StatusAttribute> attributeList;
	private String statusID;
	public FacplStatus(String statusID) {
		attributeList = new ArrayList<StatusAttribute>();
		this.statusID = statusID;
	}
	public FacplStatus(List<StatusAttribute> attributeList, String statusID) {
		this.attributeList = attributeList;
		this.statusID = statusID;
	}
	public FacplStatus() {
		attributeList = new ArrayList<StatusAttribute>();
		this.statusID = UUID.randomUUID().toString().substring(0, 8);
	}
	public String getStatusID() {
		return this.statusID;
	}
	public void add(StatusAttribute a) {
		this.attributeList.add(a);
	}
	public StatusAttribute retrieveAttribute(StatusAttribute attribute) throws MissingAttributeException {
		int i = this.attributeList.indexOf(attribute);
		if (i != -1) {
			return this.attributeList.get(i);
		} else {
			throw new MissingAttributeException("attribute doesn't exist in the current status");
		}
	}
	public Object getValue(StatusAttribute attribute) throws MissingAttributeException {
		return (Object) (this.retrieveAttribute(attribute).getValue());
	}

}
