package uma.caosd.evoting.impl;

import uma.caosd.evoting.User;

public class SimpleUser implements User {
	private String id;
	
	public SimpleUser() {
		
	}
	
	public SimpleUser(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
