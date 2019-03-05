package client;

import java.net.Socket;


public class HTTP11Client extends HTTPClient {

	public HTTP11Client() {
		
	}
	
	@Override
	public void handleCommand(HTTPCommand command) throws Exception {
		if(this.socket == null) {
			this.socket = new Socket(command.getHost(), command.getPort());
		}
		
		RequestHandler requestHandler = RequestHandlerFactory.getHandler(command.getHttpmethod());
		try {
			requestHandler.handle(command, socket);
		} catch (Exception e) {
			throw new Exception("Couldn't handle the request");
		}

		this.socket.close();
		}

	
}
