package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventRenderPlayer extends Event {
	
	public EventRenderPlayer(AbstractClientPlayer entity) {
		
		this.entity = entity;
		
	}
	
	public AbstractClientPlayer entity;
	
}
