package badgamesinc.hypnotic.module.render;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventRenderPlayer;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class BrightPlayer extends Mod {

	public BrightPlayer() {
		super("BrightPlayer", 0, Category.RENDER, "Just makes your player brighter, only added because I (BadGamesInc) like it");
	}
	
	@EventTarget
	public void renderPlayer(EventRenderPlayer e) {
		GL11.glColor4f(255, 255, 255, 255);
	}

}
