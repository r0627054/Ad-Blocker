package client.handlers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.commands.HTTPCommand;
import shared.handlers.HttpRequestHandler;

public abstract class ClientRequestHandler extends HttpRequestHandler{
	
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
	/*public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}*/
}
