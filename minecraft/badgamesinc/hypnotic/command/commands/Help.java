package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
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
		Wrapper.tellPlayer("Command List: (" + Hypnotic.instance.commandManager.commands.size() + ")");
		for(Command cmd : Hypnotic.instance.commandManager.commands) 
		{
			Wrapper.rawTellPlayer("Command: " + cmd.getSyntax());
			Wrapper.rawTellPlayer("Description: " + cmd.getDescription());
			Wrapper.rawTellPlayer("==========================");
		}
	}
}
