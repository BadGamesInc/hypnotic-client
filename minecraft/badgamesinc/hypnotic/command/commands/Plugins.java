package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Plugins extends Command {

	@Override
	public String getAlias() {
		
		return "plugins";
	}

	@Override
	public String getDescription() {
		
		return "Attemps to list server plugins";
	}

	@Override
	public String getSyntax() {
		
		return ".plugins";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		//Wrapper.tellPlayer(mc.getCurrentServerData().);
	}
}
