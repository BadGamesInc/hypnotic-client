package badgamesinc.hypnotic.gui;

import java.util.Collections;
import java.util.Comparator;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class HUD {

	public Minecraft mc = Minecraft.getMinecraft();
	private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Consolas", 18, false, false, false);
	
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
		
		Hypnotic.fm.getFont("SFB 8").drawString("H", 3, 4, ColorUtils.rainbow(2, 0.5f, 0.5f));
		Hypnotic.fm.getFont("SFB 8").drawString("ypnotic", 10, 4, -1);
		
		final int height = sr.getScaledHeight();
	    final int width = sr.getScaledWidth();
	    
	    int count = 0;
	    
	    Collections.sort(Hypnotic.instance.moduleManager.modules, new ModuleComparator());
	    	
		for(Mod m : Hypnotic.instance.moduleManager.getEnabledModules()) {		
			Hypnotic.fm.getFont("SFB 8").drawString(m.getDisplayName(), width - fr.getStringWidth(m.getDisplayName()) - 4, count * (fr.FONT_HEIGHT + 6) + 4, ColorUtils.rainbow(2, 0.5f, 0.5f, count * 100));
             count++;
		}
		if(Hypnotic.instance.moduleManager.getModuleByName("PC Pinger").isEnabled()) {
			while(true) {
				fontRenderer.drawString("PINGING PC", 2.8f, 3, ColorUtils.rainbow(2, 0.5f, 0.5f), true);
			}
        }
	}
	
	public void renderArrayList(ScaledResolution sr) {
		
	}
}
