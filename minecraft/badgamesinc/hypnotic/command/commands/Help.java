package badgamesinc.hypnotic.command.commands;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.command.CommandManager;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Help extends Command {

	@Override
	public String getAlias() {
		
		return "help";
	}

	@Override
	public String getDescription() {
		
		return "List commands and their use";
	}

	@Override
	public String getSyntax() {
		
		return ".help";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		Wrapper.tellPlayer("Command List: (" + Hypnotic.instance.commandManager.commands.size() + ")");
		for(Command cmd : Hypnotic.instance.commandManager.commands) 
		{
			Wrapper.rawTellPlayer("Command: " + cmd.getSyntax());
			Wrapper.rawTellPlayer("Description: " + cmd.getDescription());
			Wrapper.rawTellPlayer("==========================");
		}
	}
}
