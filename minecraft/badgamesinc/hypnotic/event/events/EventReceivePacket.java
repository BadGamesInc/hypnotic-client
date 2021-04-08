package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventDirection;
import badgamesinc.hypnotic.event.EventType;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {

	public Packet packet;
	
	public EventReceivePacket(EventType type, EventDirection dir, Packet packet) {
		
		this.setType(type);
		this.setDirection(dir);
		this.packet = packet;
		
	}
}
