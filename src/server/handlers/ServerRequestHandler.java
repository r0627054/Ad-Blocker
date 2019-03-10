package server.handlers;

import java.net.Socket;

import client.commands.HTTPCommand;
import shared.handlers.HttpRequestHandler;

public abstract class ServerRequestHandler extends HttpRequestHandler {

	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
}
