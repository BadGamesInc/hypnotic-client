package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import badgamesinc.hypnotic.Hypnotic;

public class Save extends Command {
  
  @Override
	public String getAlias() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "Saves your config";
	}

	@Override
	public String getSyntax() {
		return ".save";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
			Hypnotic.instance.saveLoad.save();
    			Wrapper.tellPlayer("Succesfully saved");
		}
  	}
}
