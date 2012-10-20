package cz.opt.networking;

import java.awt.event.ActionListener;

public interface SocketListener extends ActionListener
{
	void socketReadyRead(SocketEvent event);
}
