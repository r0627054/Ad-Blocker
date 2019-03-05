package httpproperties;

public enum HTTPVersion {
HTTP10("HTTP/1.0"), HTTP11("HTTP/1.1");
	
	private final String versionCode;
	
	private HTTPVersion(String versionCode) {
		this.versionCode = versionCode;
	}
	
	public String getVersionCode() {
		return this.versionCode;
	}
	


}
