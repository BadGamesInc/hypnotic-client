package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

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
		Wrapper.tellPlayer("Commands: (" + Hypnotic.instance.commandManager.commands.size() + ")");
		Wrapper.rawTellPlayer(ColorUtils.white + "==========================");
		for(Command cmd : Hypnotic.instance.commandManager.commands) 
		{
			Wrapper.rawTellPlayer(ColorUtils.green + "Command: " + cmd.getSyntax());
			Wrapper.rawTellPlayer(ColorUtils.green + "Description: " + cmd.getDescription());
			Wrapper.rawTellPlayer(ColorUtils.white + "==========================");
		}
		Wrapper.rawTellPlayer("For more information visit the github page, https://github.com/BadGamesInc/hypnotic-client");
	}
}
