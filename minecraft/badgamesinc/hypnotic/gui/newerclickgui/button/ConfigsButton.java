package badgamesinc.hypnotic.gui.newerclickgui.button;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.config.Config;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.client.gui.Gui;

public class ConfigsButton {

	public int width = 50;
	public int height = 20;
	public float x;
	public float y;
	public int cfgWidth = 50;
	public int cfgHeight = 20;
	public float cfgX;
	public float cfgY;
	public static boolean open;
	public float openTicks = 0;
	public float openTicks2 = 0;
	public float openTicks3 = 0;
	
	public ConfigsButton() {
		
	}
	
	public void drawButton(float x, float y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		this.width = (int) (50 + openTicks);
		
		RenderUtils.drawRoundedRect(x, y, x + width, y + height, 4, new Color(80, 80, 80));
		if (isHovered(mouseX, mouseY))
			RenderUtils.drawRoundedRect(x, y, x + width, y + height, 4, new Color(100, 100, 100, 255));
		for (int i = 0; i < 3; i++) {
			RenderUtils.drawFilledCircle((int) (x + (width / 3.5) + (i * (10 + openTicks3))), (int) (int) (y + height / 2), 3, -1);
		}
		if (open == true) {
			float count = 0;
			for (Config c : Hypnotic.instance.cfgManager.getConfigs()) {
				count++;
			}
			if (openTicks < 20) {
				openTicks+=0.5;
			}
			if (openTicks2 < count * 100) {
				openTicks2+=1;
			}
			if (openTicks3 < 5) {
				openTicks3+=0.125;
			}
			
			
			Gui.drawRect(x + 4, y, x + width - 4, y - 8 - openTicks2, new Color(40, 40, 40, 255).getRGB());
			RenderUtils.drawRoundedRect(x + 2, y - 8, x + width - 2, y - openTicks2, 4, new Color(40, 40, 40, 255));
		} else {
			if (openTicks > 0) {
				openTicks-=0.5;
			}
			if (openTicks2 > 0) {
				openTicks2-=1.25;
			}
			if (openTicks3 > 0) {
				openTicks3-=0.125;
			}
			if (openTicks3 != 0) {
				Gui.drawRect(x + 4, y, x + width - 4, y - 8 - openTicks2, new Color(40, 40, 40, 255).getRGB());
				RenderUtils.drawRoundedRect(x + 2, y - 8, x + width - 2, y - openTicks2, 4, new Color(40, 40, 40, 255));
			}
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}
	
	public boolean isHoveredConfig(int mouseX, int mouseY) {
		return false;
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (isHovered(mouseX, mouseY)) {
			this.open = !this.open;
		}
	}
}
