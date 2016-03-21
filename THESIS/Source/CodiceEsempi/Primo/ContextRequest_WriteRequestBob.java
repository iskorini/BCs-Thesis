
public class ContextRequest_WriteRequestBob {
	private static ContextRequest_Status CxtReq;
	public static ContextRequest_Status getContextReq() {
		if (CxtReq != null) {
			return CxtReq;
		}
		HashMap<String, Object> req_category_attribute_name = new HashMap<String, Object>();
		HashMap<String, Object> req_category_attribute_action = new HashMap<String, Object>();
		HashMap<String, Object> req_category_attribute_file = new HashMap<String, Object>();
		req_category_attribute_name.put("id", "Bob");
		req_category_attribute_action.put("id", "write");
		req_category_attribute_file.put("id", "file1");
		Request req = new Request("write_request");
		req.addAttribute("name", req_category_attribute_name);
		req.addAttribute("action", req_category_attribute_action);
		req.addAttribute("file", req_category_attribute_file);
		CxtReq = new ContextRequest_Status(req, ContextStub_Default.getInstance());
		StatusRW st = new StatusRW();
		CxtReq.setStatus(st.getStatus());
		return CxtReq;
	}

}
