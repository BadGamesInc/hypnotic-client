package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import badgamesinc.hypnotic.Hypnotic;

public class Save extends Command {
  
  @Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "save";
	}

	@Override
	public String getDescription() {
		return "Saves your config";
	}

	@Override
	public String getSyntax() {
		return ".save (config_name)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			Wrapper.tellPlayer("Usage: " + getSyntax());
		} else {
			Hypnotic.instance.saveLoad.configName = args[0];
			Hypnotic.instance.saveLoad.save();
    			Wrapper.tellPlayer("Succesfully saved " + args[0]);
		}
  	}
}
