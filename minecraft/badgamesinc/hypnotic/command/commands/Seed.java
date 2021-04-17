package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Seed extends Command {

	@Override
	public String getAlias() {
		
		return "seed";
	}

	@Override
	public String getDescription() {
		
		return "The seed of the current world";
	}

	@Override
	public String getSyntax() {
		
		return ".seed";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		Wrapper.tellPlayer(mc.theWorld.getSeed() + "");
	}
}
