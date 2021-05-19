package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
    Packet packet;
    public EventReceivePacket(Packet packet){
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
