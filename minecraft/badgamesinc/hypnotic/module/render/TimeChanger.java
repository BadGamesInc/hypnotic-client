package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.TimeHelper;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeChanger extends Mod {
	
    public NumberSetting time = new NumberSetting("Time", 0 , 0, 15000, 100);
    public BooleanSetting loop = new BooleanSetting("Loop", false);
    TimeHelper timer = new TimeHelper();
    
    public TimeChanger(){
        super("Time Changer", 0, Category.RENDER, "Visibly change the time in the world");
        addSettings(time, loop);
    }

    @EventTarget
    public void onRecieve(EventReceivePacket event){
        if(event.getPacket() instanceof S03PacketTimeUpdate){
            event.setCancelled(true);
        }

        if(!loop.isEnabled()) {
            mc.theWorld.setWorldTime((long) time.getValue());
        }else {
            mc.theWorld.setWorldTime(timer.getCurrentTime());
            if(timer.getCurrentTime() > 30000){
                timer.reset();
            }
        }
    }

    @Override
    public void onEnable(){
        super.onEnable();
        timer.reset();
    }
}