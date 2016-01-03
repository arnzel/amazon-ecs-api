package de.malkusch.amazon.ecs.configuration;

public class StringConfiguration  implements Configuration{
	
	private String accessKey;
	
	private String secretKey;
	
	private String associateTag;
	
	public StringConfiguration() {
		
	}

	@Override
	public String getAccessKey() {
		return this.accessKey;
	}

	@Override
	public String getSecretKey() {
		return this.secretKey;
	}

	@Override
	public String getAssociateTag() {
		return this.associateTag;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setAssociateTag(String associateTag) {
		this.associateTag = associateTag;
	}

	
	
}
