package badgamesinc.hypnotic.module.misc;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.MusicUtils;
import net.minecraft.util.BlockPos;

public class MusicPlayer extends Mod {

	public MusicUtils musicUtils = new MusicUtils();
	public Setting mode;
	
	public MusicPlayer(){
	  super("MusicPlayer",Keyboard.KEY_NONE,Category.WORLD, "Play minecraft songs");
	}
	
	@Override
	public void onEnable() {
		BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		mc.thePlayer.worldObj.playRecord(pos, "item.record." + "cat" + ".desc");
	}

}
