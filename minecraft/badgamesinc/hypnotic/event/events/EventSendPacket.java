package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;
import net.minecraft.network.Packet;

@SuppressWarnings("hiding")
public class EventSendPacket<EventSendPacket> extends Event {

	public EventSendPacket(Packet packet) {
		this.packet = packet;
	}
	
	public Packet packet = null;
}
