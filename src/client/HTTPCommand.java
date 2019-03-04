package client;

public enum HTTPCommand {
	HEAD("HEAD"),
	GET("GET"),
	PUT("PUT"),
	POST("POST");
	
	private final String commandCode;
	
	private HTTPCommand(String commandCode) {
		this.commandCode = commandCode;
	}
	
	public String getCommandCode() {
		return this.commandCode;
	}
		
}
