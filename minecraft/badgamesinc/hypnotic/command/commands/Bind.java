package badgamesinc.hypnotic.command.commands;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class Bind extends Command {

	@Override
	public String getAlias() {
		
		return "bind";
	}

	@Override
	public String getDescription() {
		
		return "Binds modules to a specified key";
	}

	@Override
	public String getSyntax() {
		
		return ".bind (key)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args.length == 2) {
				String moduleName = args[0];
				String keyName = args[1];
				
			for(Mod m : Hypnotic.instance.instance.moduleManager.modules) {
				if (args[2] != "0") {
					m.getName().replaceAll(" ", "");
					if(m.getName().equalsIgnoreCase(moduleName)) {
						m.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
						
						Wrapper.tellPlayer(String.format("Bound %s to %s", ColorUtils.white + m.getName() + ColorUtils.gray, ColorUtils.white + Keyboard.getKeyName(m.getKey())));
					} else if (args[2].equalsIgnoreCase("0") || args[2].equalsIgnoreCase("none")) {
						m.setKey(Keyboard.KEY_NONE);
						Wrapper.tellPlayer(String.format("Unbound %s", ColorUtils.white + m.getName()));
					}
				}
			}	
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				for(Mod m : Hypnotic.instance.moduleManager.modules) {
					m.setKey(0);
				}
				Wrapper.tellPlayer("Cleared all binds");
			} else 
				Wrapper.tellPlayer(args[0] + " is not a module");
		}
		
		if(args[0] == null) {
			Wrapper.tellPlayer("Usage: " + getSyntax());
		}
		
	}

}
