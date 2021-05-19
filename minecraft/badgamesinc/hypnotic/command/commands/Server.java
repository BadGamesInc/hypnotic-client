package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.ServerAddress;

public class Server extends Command {

	@Override
	public String getAlias() {
		
		return "server";
	}

	@Override
	public String getDescription() {
		
		return "Info about the server you are currently on";
	}

	@Override
	public String getSyntax() {
		
		return ".server";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		if(!mc.isSingleplayer()) 
		{
			Wrapper.tellPlayer("IP: " + ServerAddress.getIP());
			Wrapper.tellPlayer("Port: " + ServerAddress.getPort());
		}
		else 
		{
			Wrapper.tellPlayer("This is a singleplayer world.");
		}
	}
}
