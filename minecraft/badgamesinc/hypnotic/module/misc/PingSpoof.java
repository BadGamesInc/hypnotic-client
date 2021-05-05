package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimeHelper;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class PingSpoof extends Mod {

	TimeHelper timer = new TimeHelper();
    private List<C00PacketKeepAlive> packetList = new ArrayList<>();
    public PingSpoof(){
        super("PingSpoof", Keyboard.KEY_NONE, Category.MISC, "Alters your ping to appear higher to the server");

        Hypnotic.instance.setmgr.rSetting(new Setting("PingSpoof Delay", this, 500, 0, 3000, true));
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event){
        final long delay = (long) Hypnotic.instance.setmgr.getSettingByName("PingSpoof Delay").getValDouble();



        if (this.packetList.size() > 10) {
            this.packetList.clear();
        }



        if (event.getPacket() instanceof C00PacketKeepAlive && mc.thePlayer.isEntityAlive() && mc.thePlayer != null) {
            C00PacketKeepAlive packet = (C00PacketKeepAlive) event.getPacket();
            this.packetList.add(packet);
            event.setCancelled(true);

            if (this.timer.hasReached(delay)) {
                this.timer.reset();
            }
        }

        if (this.timer.hasReached(delay) && !this.packetList.isEmpty()) {
            C00PacketKeepAlive packet = this.packetList.get(0);
            if (packet != null && packetList.contains(packet)) {
                mc.getNetHandler().getNetworkManager().sendPacket(packet);
                this.packetList.remove(packet);
                this.timer.reset();
            }
        }
    }
}
