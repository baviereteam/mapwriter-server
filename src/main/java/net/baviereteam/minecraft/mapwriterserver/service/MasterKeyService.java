package net.baviereteam.minecraft.mapwriterserver.service;

public class MasterKeyService {
	private static MasterKeyService instance = null;
	public static MasterKeyService getInstance() {
		if(instance == null) {
			instance = new MasterKeyService();
		}
		
		return instance;
	}
	
	private String masterKey;
	
	public String getMasterKey() {
		return masterKey;
	}
	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	
	public boolean isValid(String userKey) {
		return this.masterKey.equalsIgnoreCase(userKey);
	}
}
