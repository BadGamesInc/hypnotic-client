package badgamesinc.hypnotic.command.commands;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.ModuleManager;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Modules extends Command {

	@Override
	public String getAlias() {
		
		return "modules";
	}

	@Override
	public String getDescription() {
		
		return "List modules and their use";
	}

	@Override
	public String getSyntax() {
		
		return ".modules";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception 
	{
		Wrapper.tellPlayer("Command List: (" + Hypnotic.instance.moduleManager.modules.size() + ")");
		for(Mod mod : Hypnotic.instance.moduleManager.modules) 
		{
			Wrapper.rawTellPlayer("Command: " + mod.displayName);
			Wrapper.rawTellPlayer("Description: " + mod.getDescription());
			Wrapper.rawTellPlayer("==========================");
		}
	}
}
