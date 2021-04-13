package badgamesinc.hypnotic.module.combat;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.EventSigma.impl.EventAttack;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimeHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class Criticals extends Mod {
    public Setting mode;
    float FallStack;
    static TimeHelper timer = new TimeHelper();
    public Criticals(){
        super("Criticals", Keyboard.KEY_NONE, Category.COMBAT, "Do critical hits without jumping");
        ArrayList<String> options = new ArrayList<>();
        options.add("Minis");
        options.add("Hypixel");
        Hypnotic.instance.setmgr.rSetting(mode = new Setting("Criticals Mode", this, "Hypixel", options));
    }
    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }


    public static void criticals(EventMotionUpdate event){
        if(timer.hasReached(350)) {
            final double[] watchdogOffsets = {0.056f, 0.013f, 0.001f};
            for(double i : watchdogOffsets){
                event.setY(event.getY() + i);
            }
            timer.reset();

        }
    }

    public static void postCriticals() {
    }

    @EventTarget
    public void onAttack(EventAttack event){
        if(mode.getValString().equalsIgnoreCase("Minis")){
            if(mc.thePlayer.onGround){
                mc.thePlayer.motionY = 0.11;
            }
        }
    }

    public  double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event){

    }


}
