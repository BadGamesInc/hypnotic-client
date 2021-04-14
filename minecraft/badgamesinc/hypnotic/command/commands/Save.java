package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;

public class Save extends Command {
  
  @Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "save";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Saves your config";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".save";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
    Wrapper.tellPlayer("Succesfully saved);
  }
}
