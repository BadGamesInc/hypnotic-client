package badgamesinc.hypnotic.util;

import badgamesinc.hypnotic.Hypnotic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Timer;

public class Wrapper {

	public static void tellPlayer(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Hypnotic.instance.prefix + message));
	}
	
	public static void rawTellPlayer(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
	}
	
	public static Timer getTimer(){
        return Minecraft.getMinecraft().timer;
    }

    public static EntityPlayerSP getPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    public static WorldClient getWorld(){
        return Minecraft.getMinecraft().theWorld;
    }
}
