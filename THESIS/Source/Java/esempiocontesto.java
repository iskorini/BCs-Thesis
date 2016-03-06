public class ContextRequest_StopReadRequestAlice {
	private static ContextRequest_Status CxtReq;
	public static ContextRequest_Status getContextReq() {
		if (CxtReq != null) {
			return CxtReq;
		}
		HashMap<String, Object> req_category_attribute_name = new HashMap<String, Object>();
		HashMap<String, Object> req_category_attribute_action = new HashMap<String, Object>();
		HashMap<String, Object> req_category_attribute_file = new HashMap<String, Object>();
		req_category_attribute_name.put("id", "Alice");
		req_category_attribute_action.put("id", "stopRead");
		req_category_attribute_file.put("id", "file1");
		Request req = new Request("stop_read_request");
		req.addAttribute("name", req_category_attribute_name);
		req.addAttribute("action", req_category_attribute_action);
		req.addAttribute("file", req_category_attribute_file);
		CxtReq = new ContextRequest_Status(req, ContextStub_Status_Default.getInstance());
		ContextStub_Status_Default.getInstance().setStatus(createStatus());
		return CxtReq;
	}
	private static FacplStatus createStatus() {
		ArrayList<StatusAttribute> attributeList = new ArrayList<StatusAttribute>();
		attributeList.add(new StatusAttribute("isWriting", FacplStatusType.BOOLEAN, "false"));
		attributeList.add(new StatusAttribute("counterReadFile1", FacplStatusType.INT, "0"));
		attributeList.add(new StatusAttribute("counterReadFile2", FacplStatusType.INT, "0"));
		FacplStatus status = new FacplStatus(attributeList, "stato");
		return status;
	}
}