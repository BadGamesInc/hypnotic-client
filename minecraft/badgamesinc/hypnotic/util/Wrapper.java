package badgamesinc.hypnotic.util;

import badgamesinc.hypnotic.Hypnotic;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Wrapper {

	public static void tellPlayer(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Hypnotic.instance.prefix + message));
	}
}
