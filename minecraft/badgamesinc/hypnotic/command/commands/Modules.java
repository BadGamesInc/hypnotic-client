package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.Wrapper;

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
		Wrapper.tellPlayer("Module List: (" + Hypnotic.instance.moduleManager.modules.size() + ")");
		for(Mod mod : Hypnotic.instance.moduleManager.modules) 
		{
			if (mod.getCategory() == Category.HIDDEN)
				continue;
			Wrapper.rawTellPlayer("Module: " + mod.displayName);
			Wrapper.rawTellPlayer("Description: " + mod.getDescription());
			Wrapper.rawTellPlayer("==========================");
		}
		Wrapper.rawTellPlayer(Hypnotic.instance.moduleManager.modules.size() + " total modules");
	}
}
