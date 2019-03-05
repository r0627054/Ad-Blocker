package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public abstract class RequestHandler {

	//public abstract String getRequestHeader();
	
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
	/*public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}*/
}
