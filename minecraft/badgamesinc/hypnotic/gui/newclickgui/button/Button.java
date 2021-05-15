package badgamesinc.hypnotic.gui.newclickgui.button;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newclickgui.Frame;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Button {

	public Mod m;
	public Frame parent;
	public static ArrayList<Element> buttonelements;
	public float x, y, width, height;
	public boolean extended;
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);

	public Button(Mod m, float x, float y, Frame parent) {
		buttonelements = new ArrayList<>();
		this.m = m;
		this.x = x;
		this.y = y;
		this.width = (int) parent.width;
		this.height = 20;
		this.parent = parent;
	}
	
	
	public void draw(int MouseX, int MouseY) {
		int rainbowOrder = 0;
		
		String c = m.getCategory().toString();
		if (c.equalsIgnoreCase("Combat")) {
			rainbowOrder = 0;
		} else if (c.equalsIgnoreCase("Movement")) {
			rainbowOrder = 1;
		} else if (c.equalsIgnoreCase("Player")) {
			rainbowOrder = 2;
		} else if (c.equalsIgnoreCase("Render")) {
			rainbowOrder = 3;
		} else if (c.equalsIgnoreCase("World")) {
			rainbowOrder = 4;
		} else if (c.equalsIgnoreCase("Misc")) {
			rainbowOrder = 5;
		} else if (c.equalsIgnoreCase("Gui")) {
			rainbowOrder = 6;
		}
		
		int enabledColor = ColorUtils.rainbow(7, 0.5f, 0.5f, (long) (rainbowOrder * 110));
		
		Gui.drawRect(x, y, x + width, y + height, isHovered(MouseX, MouseY) ? new Color (100, 100, 100, 150).getRGB() : new Color(0, 0, 0, 150).getRGB());
		fontRenderer.drawString(m.getName(), x + 2, y + 4, m.isEnabled() ? enabledColor : 0xffafafaf, true);
	}

	public void onClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
			m.toggle();
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			if (buttonelements != null && buttonelements.size() > 0) {
				boolean b = !this.extended;
				ClickGUI.instance.closeAllSettings();
				this.extended = b;
				
				if(Hypnotic.instance.setmgr.getSettingByName("Sound").getValBoolean())
					if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
				
				if(extended) {
					
				}
			}
		}
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
