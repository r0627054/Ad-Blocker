package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class HTTPClient {

	protected Socket socket;
	
	public abstract void handleCommand(HTTPCommand command) throws UnknownHostException, IOException;

	public Socket getSocket() {
		return this.socket;
	}
	
}
