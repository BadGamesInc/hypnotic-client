package badgamesinc.hypnotic.gui.newclickgui.button;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newclickgui.Frame;
import badgamesinc.hypnotic.gui.newclickgui.button.settingcomponents.Check;
import badgamesinc.hypnotic.gui.newclickgui.button.settingcomponents.Component;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.gui.Gui;

public class Button {

	public Mod m;
	public Frame parent;
	public CopyOnWriteArrayList<Mod> modules;
	public ArrayList<Component> components = new ArrayList<>();
	public float x, y, width, height, offset;
	public boolean extended;
	public static boolean settingsOpen = false;
	public boolean canToggle = true;
	public String name;
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);

	public Button(Mod m, float x, float y, Frame parent, float offset) {
		this.modules = Hypnotic.instance.moduleManager.modules;
		this.offset = offset;
		this.m = m;
		this.x = x;
		this.y = y;
		this.width = (int) parent.width;
		this.height = 20;
		this.parent = parent;
		this.name = m.getName();
		int count = 0;
		
		for (Mod mod : modules) {
			for (Setting set : mod.getSettings()) {
				if (set instanceof BooleanSetting) {
					components.add(new Check(this.x, this.y + count, set, this));
				}
				count+=this.height;
			}
		}
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
		
		if (m.isExpanded()) {
			for (Component comp : components) {
				comp.draw(MouseX, MouseY);
			}
		}
		
		Gui.drawRect(x, y, x + width, y + height, isHovered(MouseX, MouseY) ? new Color (100, 100, 100, 150).getRGB() : new Color(0, 0, 0, 150).getRGB());
		fontRenderer.drawString(m.getName(), x + 2, y + 4, m.isEnabled() ? (enabledColor) : 0xffafafaf, true);
		
		
	}

	public void onClick(int mouseX, int mouseY, int mouseButton) {
		if (settingsOpen == true) {
			canToggle = false;
		} else {
			canToggle = true;
		}
		
		if (mouseButton == 0 && isHovered(mouseX, mouseY) && canToggle) {
			m.toggle();
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			m.expanded = !m.expanded;
		}
		
		for (Component comp : components) {
			comp.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
	
	
}
