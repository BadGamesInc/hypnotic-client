package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.gui.music.GuiMusic;
import badgamesinc.hypnotic.util.Wrapper;

public class Music extends Command {

	@Override
	public String getAlias() {
		return "music";
	}

	@Override
	public String getDescription() {
		return "Displays the music gui";
	}

	@Override
	public String getSyntax() {
		return ".music";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		mc.displayGuiScreen(new GuiMusic());
	}

}
