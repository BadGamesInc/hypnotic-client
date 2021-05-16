package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class About extends Command {

	@Override
	public String getAlias() {
		
		return "about";
	}

	@Override
	public String getDescription() {
		
		return "Info about your current version of Hypnotic";
	}

	@Override
	public String getSyntax() {
		
		return ".about";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		Wrapper.tellPlayer("You are running Hypnotic " + Hypnotic.instance.clientVersion);
		Wrapper.tellPlayer("There have been " + "[TEMP]" + " new releases since this version");
	}
}
