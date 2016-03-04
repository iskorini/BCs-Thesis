public class StatusAttribute {
	private String id;
	private FacplStatusType type;
	private String value;
	public StatusAttribute(String id, FacplStatusType type) {
		this.id = id;
		this.type = type;
		if (type == (FacplStatusType.INT) || type == (FacplStatusType.DOUBLE)) {
			value = "0";
		} else if (type == FacplStatusType.BOOLEAN) {
			value = "false";
		} else if (type == FacplStatusType.DATE) {
			value = "0";
		} else {
			value = "";
		}
	}
	public StatusAttribute(String id, FacplStatusType type, String value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FacplStatusType getType() {
		return type;
	}
	public void setType(FacplStatusType type) {
		this.type = type;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StatusAttribute) {
			StatusAttribute o = (StatusAttribute) obj;
			return this.getId() == o.getId() && this.getType() == o.getType();
		}
		return false;
	}
	@Override
	public String toString() {
		return this.type.toString() + "/" + this.id + "/" + this.value.toString();
	}

}
