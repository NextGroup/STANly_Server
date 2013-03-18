package stanly.server.MetricView.Json;

import java.util.HashMap;

public class TreeElement {
	private String data;
	private HashMap<String,String> attr;
	private String state;
	
	public TreeElement(String data, String state) {
		super();
		this.data = data;
		this.state = state;
		attr = new HashMap<String,String>();
	}
	public TreeElement(String data,int NSleft,int NSRight) {
		super();
		this.data = data;
		this.state = (NSRight-NSleft >1) ? "closed" : "";
		attr = new HashMap<String,String>();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAttrID() {
		return attr.get("id");
	}
	public void setAttrID(String ID) {
		if(attr.containsKey("id"))
			attr.remove("id");
		
		attr.put("id", ID);
	}
	public String getRel() {
		return attr.get("rel");
	}
	public void setRel(String rel) {
		if(attr.containsKey("rel"))
			attr.remove("rel");
		
		attr.put("rel", rel);
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
