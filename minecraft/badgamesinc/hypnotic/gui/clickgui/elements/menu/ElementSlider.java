package badgamesinc.hypnotic.gui.clickgui.elements.menu;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.elements.Element;
import badgamesinc.hypnotic.gui.clickgui.elements.ModuleButton;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.gui.clickgui.util.FontUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ElementSlider extends Element {
	public boolean dragging;
	public static UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 18, 0, 1, 1);

	public ElementSlider(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		dragging = false;
		super.setup();
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
		boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || dragging;
		
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
		int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
		
		if(Hypnotic.instance.setmgr.getSettingByName("Rainbow GUI").getValBoolean()) {
			color = ColorUtils.rainbow(2, 0.5f, 1);
			color2 = ColorUtils.rainbow(2, 0.7f, 1.4f);
		}
		
		//selected = iset.getValDouble() / iset.getMax();
		double percentBar = (set.getValDouble() - set.getMin())/(set.getMax() - set.getMin());
		

		Gui.drawRect(x, y, x + width, y + height, 0xff1a1a1a);

		ufr.drawString(setstrg, x + 1, y + 2, 0xffffffff);
		ufr.drawString(displayval, x + width - FontUtil.getStringWidth(displayval), y + 2, 0xffffffff);

		Gui.drawRect(x, y + 12, x + width, y + 13.5, 0xff101010);
		Gui.drawRect(x, y + 12, x + (percentBar * width), y + 13.5, color);
		
		if(percentBar > 0 && percentBar < 1)
		Gui.drawRect(x + (percentBar * width)-1, y + 12, x + Math.min((percentBar * width), width), y + 13.5, color2);

		if (this.dragging) {
			double diff = set.getMax() - set.getMin();
			double val = set.getMin() + (MathHelper.clamp_double((mouseX - x) / width, 0, 1)) * diff;
			set.setValDouble(val); //Die Value im Setting updaten
		}

	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
			this.dragging = true;
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	public boolean isSliderHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 14;
	}
}