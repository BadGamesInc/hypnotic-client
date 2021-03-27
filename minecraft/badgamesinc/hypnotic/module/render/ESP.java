package badgamesinc.hypnotic.module.render;

import java.awt.Color;
import java.util.Objects;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event2D;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.render.EspUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;

public class ESP extends Mod {
	
	
    
	public ESP() {
		super("ESP", 0, Category.RENDER, "Draws an outline around selected entities");
	}
	
	@EventTarget
	public void renderESP(Event2D event2d) {
		Color temp = ColorUtil.getClickGUIColor().darker();
		int espColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		
		for (Entity entity : mc.theWorld.loadedEntityList) {


            if (entity instanceof EntityHanging) {
                continue;
            }
            if (entity == mc.thePlayer) {
                continue;
            }
            if (entity.isInvisible()) {
                continue;
            }
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb) {
                continue;
            }

            Gui.drawRect(entity.posX, entity.posY, entity.posX + 10, entity.posY + 10, espColor);
        }
		
		
	}

}
