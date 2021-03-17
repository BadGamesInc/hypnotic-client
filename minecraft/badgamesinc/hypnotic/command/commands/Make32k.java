package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Toggle extends Command {

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "32k";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Makes you a 32k (creative only)";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return ".32k";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		ItemStack itemStack = getItemStack();
		Utils.addEnchantment(itemStack, "sharpness", 32767);
		Utils.addEnchantment(itemStack, "unbreaking", 32767);
	}

}
