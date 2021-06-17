package badgamesinc.hypnotic.module.world;

import com.mojang.authlib.GameProfile;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPlayerDeath;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Mod {

    private EntityOtherPlayerMP other;
    private EntityOtherPlayerMP fakethePlayer = null;

    public FakePlayer() {
        super("FakePlayer", 0, Category.WORLD, "Spawns a fake player to be used for testing");
    }

    @Override
    public void onDisable() {
        mc.theWorld.removeEntityFromWorld(-1);
        mc.theWorld.removeEntityFromWorld(-69);
        this.fakethePlayer = null;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        EntityOtherPlayerMP fakethePlayer = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(null, "Fake Player"));

        fakethePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakethePlayer.posY -= 0.0;

        fakethePlayer.rotationPitch = 0;
        fakethePlayer.rotationYaw = 90;
        mc.theWorld.addEntityToWorld(-69, fakethePlayer);
        super.onEnable();
    }
    
    @Override
    public void onUpdate() {
    	if (mc.thePlayer.isDead)
    		mc.theWorld.removeEntity(fakethePlayer);
    	super.onUpdate();
    }
    
    @EventTarget
    public void onDeath(EventPlayerDeath event) {
    	System.out.println("YOU DIED RETARD");
    }
    
}

