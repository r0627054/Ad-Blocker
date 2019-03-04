package client;

import java.net.Socket;
import java.util.Map;

public abstract class RequestHandler {

	public abstract String getRequestHeader();
	
	public abstract void handle(HTTPCommand command, Socket socket);
	
	public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}
}
