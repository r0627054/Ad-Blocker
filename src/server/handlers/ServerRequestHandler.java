package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import client.commands.HTTPCommand;
import shared.handlers.HttpRequestHandler;

public abstract class ServerRequestHandler extends HttpRequestHandler {

	public abstract void handle(HTTPCommand command, byte[] body, Socket socket)throws Exception;
	
}
