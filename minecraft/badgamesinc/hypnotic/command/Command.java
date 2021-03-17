package badgamesinc.hypnotic.command;

import badgamesinc.hypnotic.module.ModuleManager;
import net.minecraft.client.Minecraft;

public abstract class Command {

	protected Minecraft mc = Minecraft.getMinecraft();
	protected ModuleManager mm = new ModuleManager();
	public abstract String getAlias();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;
}
