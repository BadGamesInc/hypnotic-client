package badgamesinc.hypnotic.module.render.wings;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventRenderPlayer;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Wings extends Mod {

	public float partialTicks = 0;
	
	public Wings() {
		super("Wings", 0, Category.RENDER, "Renders dragon wings on your player");
	}
	
	@EventTarget
	public void event3d(Event3D e) {
		this.partialTicks = e.getPartialTicks();
	}
	
	@EventTarget
	public void eventRenderPlayer(EventRenderPlayer event) {
		RenderWings.getWings().onRenderPlayer(event.entity, partialTicks);
	}

}
