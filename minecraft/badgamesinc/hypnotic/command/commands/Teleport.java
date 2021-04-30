package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;

public class Teleport extends Command {

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "tp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Its like /tp but you don't need op";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".tp (x, y, z)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			Wrapper.tellPlayer("Usage: " + getSyntax());
		} else {
			mc.thePlayer.setPosition(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			Wrapper.tellPlayer("TP'd to " + args[0] + ", " + args[1] + ", " + args[2]);
		}
		
	}

}
