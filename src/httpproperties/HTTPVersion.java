package httpproperties;

/**
 * Stores the different HTTP versions which can be used.
 */
public enum HTTPVersion {
HTTP10("HTTP/1.0"), HTTP11("HTTP/1.1");
	
	/**
	 * Variable storing the version code: the version in the correct String format.
	 */
	private final String versionCode;
	
	/**
	 * Initialise the HTTPVersion with the correct versioncode.
	 * @param versionCode
	 *        | the versioncode of the HTTPVersion
	 */
	private HTTPVersion(String versionCode) {
		this.versionCode = versionCode;
	}
	
	/**
	 * Returns the version code of a HTTPVersion.
	 */
	public String getVersionCode() {
		return this.versionCode;
	}
	


}
