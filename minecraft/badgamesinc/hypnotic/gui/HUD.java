package badgamesinc.hypnotic.gui;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

public class HUD {

	public Minecraft mc = Minecraft.getMinecraft();
	private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Consolas", 18, false, false, false);
	
	public int height;
	public int width;
	
	public static class ModuleComparator implements Comparator<Mod> {

		@Override
		public int compare(Mod arg0, Mod arg1) {
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getDisplayName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getDisplayName())) {
				return -1;
			}
			
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getDisplayName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getDisplayName())) {
				return 1;
			}
			return 0;
		}
		
	}
	
	public void draw() {
		FontRenderer fr = mc.fontRendererObj;
		ScaledResolution sr = new ScaledResolution(mc);
		Collections.sort(Hypnotic.instance.moduleManager.modules, new ModuleComparator());
		
		fr.drawStringWithShadow("H", 3, 4, ColorUtils.rainbow(2, 0.5f, 0.5f));
		fr.drawStringWithShadow("ypnotic", 10, 4, -1);
		
		final int height = sr.getScaledHeight();
	    final int width = sr.getScaledWidth();
	    
	    renderTargetHUD();
	    
	    int count = 0;
	    
	    Collections.sort(Hypnotic.instance.moduleManager.modules, new ModuleComparator());
	    	
		for(Mod m : Hypnotic.instance.moduleManager.getEnabledModules()) {		
			
			double offset = count * (fr.FONT_HEIGHT + 6);
			
			
			
			//Hypnotic.fm.getFont("SFB 8").drawString(m.getDisplayName(), width - fr.getStringWidth(m.getDisplayName()) - 4, count * (fr.FONT_HEIGHT + 6) + 4, ColorUtils.rainbow(2, 0.5f, 0.5f, count * 100));
             
			Gui.drawRect(width, offset, width - 3, 6 + fr.FONT_HEIGHT + offset, ColorUtils.rainbow(2.0f, 0.6f, 1f, count * 100));
            Gui.drawRect(width - fr.getStringWidth(m.getDisplayName()) - 12, offset, sr.getScaledWidth() - 3, 6 + fr.FONT_HEIGHT + offset, -1879048192);
            fr.drawString(m.getDisplayName(), (float)(width - fr.getStringWidth(m.getDisplayName()) - 8), (3.5 + count * (fr.FONT_HEIGHT + 6)), ColorUtils.rainbow(2.0f, 0.6f, 1f, count * 100));
        
			count++;
		}
		if(Hypnotic.instance.moduleManager.getModuleByName("PC Pinger").isEnabled()) {
				fontRenderer.drawString("PINGING PC", 2.8f, 3, ColorUtils.rainbow(2, 0.5f, 0.5f), true);
			
        }
		
		
		
		
	}
	
	public void renderTargetHUD() {
		Color temp = ColorUtil.getClickGUIColor().darker();
		FontRenderer fr = mc.fontRendererObj;
		ScaledResolution sr = new ScaledResolution(mc);		
		EntityLivingBase target = KillAura.target;
		
		int healthBarColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		
		if(Hypnotic.instance.moduleManager.getModuleByName("TargetHUD").isEnabled() && Hypnotic.instance.moduleManager.getModuleByName("KillAura").isEnabled()) {
			if(target != null) {
				GuiInventory.drawEntityOnScreen(sr.getScaledWidth() / 3.2f, sr.getScaledHeight() / 1.69f, 25, target.rotationYaw, target.rotationPitch, target);
			
				Gui.drawRect(sr.getScaledWidth() / 3.4f, sr.getScaledHeight() / 2.05f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 2.3f, sr.getScaledHeight() / 1.6f, 0xff212020);
						
				Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + 20 * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, -1);
				
				Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + 20 * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, 0x55111111);
					
				Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + target.getHealth() * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, healthBarColor);				
				
				fr.drawString(target.getName(), sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f, ColorUtils.rainbow(2f, 0.5f, 0.5f));
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 6, 0);
				GlStateManager.scale(1.6, 1.6, 1);
				GlStateManager.translate(-(sr.getScaledWidth() / 3f), -(sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 6), 0);
				fr.drawString(MathUtils.round(target.getHealth(), 2) + " \u2764", sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 10, ColorUtils.rainbow(2f, 0.5f, 0.5f));
				GlStateManager.popMatrix();
				
			} else {
				
			}
		}
	}
	
	private void renderPlayer2d(final double n, final double n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10, final AbstractClientPlayer abstractClientPlayer) {
        mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawScaledCustomSizeModalRect((int)n, (int)n2, n3, n4, n5, n6, n7, n8, n9, n10);
        GL11.glDisable(3042);
    }

}
