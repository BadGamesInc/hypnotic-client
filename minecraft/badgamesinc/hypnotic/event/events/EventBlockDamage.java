package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;
import net.minecraft.util.BlockPos;

public class EventBlockDamage extends Event {

	private final BlockPos blockPos;
	
	public EventBlockDamage(BlockPos pos) {
		this.blockPos = pos;
	}
	
	public BlockPos getBlockPos() {
		return this.blockPos;
	}
}
