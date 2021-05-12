package badgamesinc.hypnotic.gui.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.Panel;
import badgamesinc.hypnotic.gui.clickgui.elements.menu.ElementCheckBox;
import badgamesinc.hypnotic.gui.clickgui.elements.menu.ElementComboBox;
import badgamesinc.hypnotic.gui.clickgui.elements.menu.ElementSlider;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.gui.clickgui.util.FontUtil;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ModuleButton {
	public Mod mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;
	public static UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 20, 0, 1, 1);
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);

	public ModuleButton(Mod imod, Panel pl) {
		mod = imod;
		height = fontRenderer.getFontHeight() + 2;
		parent = pl;
		menuelements = new ArrayList<>();

		if (Hypnotic.instance.setmgr.getSettingsByMod(imod) != null)
			for (Setting s : Hypnotic.instance.setmgr.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		if(Hypnotic.instance.setmgr.getSettingByName("Rainbow GUI").getValBoolean()) {
			int xOffset = 0;
			for (Category c : Category.values()) {
				xOffset++;
			}
			color = ColorUtils.rainbow(6, 0.5f, 0.5f, (long) (xOffset * 150));
		}
		
		int textcolor = 0xffafafaf;
		if (mod.isEnabled()) {
			//Gui.drawRect(x - 2, y, x + width + 2, y + height + 10, color);
			textcolor = (int) (color);
		}

		if (isHovered(mouseX, mouseY)) {	
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution sr = new ScaledResolution(mc);
			Gui.drawRect(0, sr.getScaledHeight(), 8 + fontRenderer.getStringWidth(mod.getDescription()) + 10, sr.getScaledHeight() - 14, 0xff212020);
			Gui.drawRect(x + 12, y, x + width - 12, y + height + 1, 0x55111111);	
			fontRenderer.drawString(mod.getDescription(), 4, sr.getScaledHeight() - 12, -1, false);
		}

		if(mod.isEnabled())
			fontRenderer.drawString(mod.getName(), x + width / 2 - 42, y + 0 + height / 2 - 6, textcolor, true);
		else
			fontRenderer.drawString(mod.getName(), x + width / 2 - 42, y + 0 + height / 2 - 6, textcolor, true);
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		if (mouseButton == 0) {
			mod.toggle();
		} else if (mouseButton == 1) {
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Hypnotic.instance.clickgui.closeAllSettings();
				this.extended = b;
				
				if(Hypnotic.instance.setmgr.getSettingByName("Sound").getValBoolean())
					if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
				
				if(extended) {
					
				}
			}
		} else if (mouseButton == 2) {

			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {

		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				Wrapper.tellPlayer("Bound " + ColorUtils.white + mod.getName() + ColorUtils.gray + "" + " to " + ColorUtils.white + Keyboard.getKeyName(keyCode) + "");
				mod.setKey(keyCode);
			} else {
				Wrapper.tellPlayer("Unbound " + ColorUtils.white + mod.getName() + "");
				mod.setKey(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
