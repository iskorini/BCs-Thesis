package secondoEsempioStatus;

import java.util.HashMap;

import it.unifi.facpl.lib.enums.FacplStatusType;
import it.unifi.facpl.lib.util.FacplDate;
import it.unifi.facpl.system.status.FacplStatus;
import it.unifi.facpl.system.status.StatusAttribute;

public class Status1 {
private FacplStatus status;
	
	public Status1() {
		FacplDate date = new FacplDate("2016/04/20-00:00:00");
		HashMap<StatusAttribute, Object> attributes = new HashMap<StatusAttribute, Object>();
		attributes.put(new StatusAttribute("accessTypeAlice", FacplStatusType.STRING), "no");
		attributes.put(new StatusAttribute("accessTypeBob", FacplStatusType.STRING), "no");
		attributes.put(new StatusAttribute("aliceFile1viewNumber", FacplStatusType.INT), 0);
		attributes.put(new StatusAttribute("bobFile1viewNumber", FacplStatusType.INT), 0);
		attributes.put(new StatusAttribute("aliceFile1expiration", FacplStatusType.DATE), date.toString());
		attributes.put(new StatusAttribute("bobFile1expiration", FacplStatusType.DATE),date.toString());
		status = new FacplStatus(attributes, this.getClass().getName());
	}
	public FacplStatus getStatus() {
		return status;
	}
}
