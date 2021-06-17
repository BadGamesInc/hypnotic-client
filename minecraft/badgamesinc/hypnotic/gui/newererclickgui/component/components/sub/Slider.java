package badgamesinc.hypnotic.gui.newererclickgui.component.components.sub;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.component.Component;
import badgamesinc.hypnotic.gui.newererclickgui.component.components.Button;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Slider extends Component {

	private boolean hovered;
	private NumberSetting numSet = (NumberSetting)set;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private boolean dragging = false;

	private double renderWidth;
	
	public Slider(Setting value, Button button, int offset) {
		super(value, button, offset);
		this.set = value;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void renderComponent() {
		if (numSet == null)
			return;
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, this.hovered ? 0xFF333333 : 0xFF222222);
		 final int drag = (int)(this.numSet.getValue() / this.numSet.getMax() * this.parent.parent.getWidth());
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 12, hovered ? (Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled() ? new Color(ColorUtils.rainbow(8, 0.6f, 1, parent.offset * 10)).darker().getRGB() : new Color(ClickGui.color).darker().getRGB()) : (Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled() ? ColorUtils.rainbow(8, 0.6f, 1, parent.offset * 10) : ClickGui.color));
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 2, parent.parent.getY() + offset + 12, 0xFF111111);
		FontManager.small.drawString(this.numSet.name + ": " + this.numSet.getValue() , (parent.parent.getX() + 5), (parent.parent.getY() + offset + 1), -1, true);
	}
	
	@Override
	public void setOff(int newOff) {
		if (numSet == null)
			return;
		offset = newOff;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		if (numSet == null)
			return;
		this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		double diff = Math.min(88, Math.max(0, mouseX - this.x));

		double min = numSet.getMin();
		double max = numSet.getMax();
		
		renderWidth = (88) * (numSet.getValue() - min) / (max - min);
		
		if (dragging) {
			if (diff == 0) {
				numSet.setValue(numSet.getMin());
			}
			else {
				double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
				numSet.setValue(newValue);
			}
		}
	}
	
	private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (numSet == null)
			return;
		if(isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
		if(isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		if (numSet == null)
			return;
		dragging = false;
	}
	
	public boolean isMouseOnButtonD(int x, int y) {
		if (numSet == null)
			return false;
		if(x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
	public boolean isMouseOnButtonI(int x, int y) {
		if (numSet == null)
			return false;
		if(x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
