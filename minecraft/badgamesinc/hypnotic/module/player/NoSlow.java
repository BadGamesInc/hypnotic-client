package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event.State;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Mod {

	public ModeSetting mode = new ModeSetting("Mode", "NCP", "NCP", "Vanilla");
	
	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER, "Move at normal speeds while using items");
		addSettings(mode);
	}
	
	
	@EventTarget
    public void onMotionUpdate(EventMotionUpdate event) {
        this.setDisplayName("NoSlow §f[" + mode.getSelected() + "] ");
        if (event.getState() == State.PRE) {
        	if (KillAura.target != null)
        		mc.gameSettings.keyBindUseItem.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            if (mc.thePlayer.isBlocking() && mode.is("NCP") && Hypnotic.instance.moduleManager.getModule(KillAura.class).target == null) { 
            	mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else {
            if (mc.thePlayer.isBlocking() && mode.is("NCP") && Hypnotic.instance.moduleManager.getModule(KillAura.class).target == null){
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));

            }
        }
    }
	
}
