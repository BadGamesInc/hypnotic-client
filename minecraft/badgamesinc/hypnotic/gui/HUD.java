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
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
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
	public FontRenderer fr = mc.fontRendererObj;
	public ScaledResolution sr = new ScaledResolution(mc);
	public static UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Bold", 20, 0, 1, 1);
    public static UnicodeFontRenderer ufr2 = UnicodeFontRenderer.getFontFromAssets("Magneto-Bold", 20, 0, 1, 1);;
	private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Consolas", 18, false, false, false);
	
	public int height;
	public int width;
	
	public static class ModuleComparator implements Comparator<Mod>
    {
        @Override
        public int compare(final Mod arg0, final Mod arg1) {
            if (ufr.getStringWidth(arg0.getDisplayName()) > ufr.getStringWidth(arg1.getDisplayName())) {
                return -1;
            }
            if (ufr.getStringWidth(arg0.getDisplayName()) < ufr.getStringWidth(arg1.getDisplayName())) {
                return 1;
            }
            return 0;
        }
    }
	
	public void draw() {
		
		
		
		ufr2.drawStringWithShadow("H", 2, 4, ColorUtils.rainbow(2, 0.5f, 0.5f));
		ufr2.drawStringWithShadow("ypnotic", 14, 4, -1);
		
		renderTargetHUD();
		renderArrayList(new ScaledResolution(mc));    
	    
		//ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 15, 0, 1, 1);
		
		//if(ufr == null){
        //    ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 15, 0, 1, 1);
       // }
        
        
		
	
		if(Hypnotic.instance.moduleManager.getModuleByName("PC Pinger").isEnabled()) {
				fontRenderer.drawString("PINGING PC", 2.8f, 3, ColorUtils.rainbow(2, 0.5f, 0.5f), true);
			
        }
		
		
		
		
	}
	
	public void renderArrayList(ScaledResolution sr) {
		Collections.sort(Hypnotic.instance.moduleManager.modules, new ModuleComparator());	
		Color temp = ColorUtil.getClickGUIColor().darker();
		String theme = Hypnotic.instance.setmgr.getSettingByName("Style").getValString();
		final int height = sr.getScaledHeight();    
		final int width = sr.getScaledWidth();	
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		int count = 0;
		
		
		
			for(Mod m : Hypnotic.instance.moduleManager.getEnabledModules()) {	
				if(Hypnotic.instance.moduleManager != null) {
					if(!Hypnotic.instance.moduleManager.getModuleByName("Array List").isEnabled()) {
						return;
					}
				}
				String mods = m.getDisplayName();
				float modeOffset = 0;
				Boolean background = Hypnotic.instance.setmgr.getSettingByName("Background").getValBoolean();
				if(Hypnotic.instance.setmgr != null && Hypnotic.instance.setmgr.getSettingByName("Rainbow") != null) {
					if(Hypnotic.instance.setmgr.getSettingByName("Rainbow").getValBoolean()) {
						color = ColorUtils.rainbow(2.0f, 0.5f, 1f, count * 120);
					} else {
						color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
					}
				}
				
				if(mods.contains("§7")) {
					modeOffset = ufr.getStringWidth("§7");
				}
				
				double offset = count * (ufr.FONT_HEIGHT + 4);      
				
				if(!mods.equalsIgnoreCase("Array List")) {
					//Chill
					if(theme.equalsIgnoreCase("Chill") && !background) {
						Gui.drawRect(width + modeOffset, offset, width - 3, 4 + ufr.FONT_HEIGHT + offset, color);               
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 8) + modeOffset, (1 + offset), color);
					}
					if(theme.equalsIgnoreCase("Chill") && background) {
						Gui.drawRect(width + modeOffset, offset, width - 3, 4 + ufr.FONT_HEIGHT + offset, color);        
						Gui.drawRect(width - ufr.getStringWidth(m.getDisplayName()) - 11 + modeOffset, offset, sr.getScaledWidth() - 3, 4 + ufr.FONT_HEIGHT + offset, 0xff212020);	        
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 8) + modeOffset, (1 + offset), color);
					}
					
					//Accent
					if(theme.equalsIgnoreCase("Accent") && !background) {
						Gui.drawRect(width - ufr.getStringWidth(m.getDisplayName()) - 9 + modeOffset, offset, width - ufr.getStringWidth(m.getDisplayName()) - 10 + modeOffset, 4 + ufr.FONT_HEIGHT + offset, color);                
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 5) + modeOffset, (1 + offset), color);
					}
					if(theme.equalsIgnoreCase("Accent") && background) {
						Gui.drawRect(width - ufr.getStringWidth(m.getDisplayName()) - 9 + modeOffset, offset, width - ufr.getStringWidth(m.getDisplayName()) - 10 + modeOffset, 4 + ufr.FONT_HEIGHT + offset, color);        
						Gui.drawRect(width - ufr.getStringWidth(m.getDisplayName()) - 9 + modeOffset, offset, sr.getScaledWidth(), 4 + ufr.FONT_HEIGHT + offset, 0xff212020);	        
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 5) + modeOffset, (1 + offset), color);
					}
					
					//Normal
					if(theme.equalsIgnoreCase("Normal") && !background) {
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 5) + modeOffset, (1 + offset), color);
					}
					if(theme.equalsIgnoreCase("Normal") && background) {
						Gui.drawRect(width - ufr.getStringWidth(m.getDisplayName()) - 9 + modeOffset, offset, sr.getScaledWidth(), 4 + ufr.FONT_HEIGHT + offset, 0xff212020);
						ufr.drawString(m.getDisplayName().toLowerCase(), (float)(width - ufr.getStringWidth(m.displayName) - 5) + modeOffset, (1 + offset), color);
					}
				count++;
			}
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
				
				fr.drawString(target.getName(), sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - ufr.FONT_HEIGHT / 2f, -1);
				
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
