package badgamesinc.hypnotic.gui.newererclickgui.elements.button.settings;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.elements.Element;
import badgamesinc.hypnotic.gui.newererclickgui.elements.button.Button;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Checkbox extends Element {

	private boolean hovered;
	private BooleanSetting boolSet = (BooleanSetting)set;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Checkbox(Setting option, Button button, int offset) {
		super(option, button, offset);
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}

	@Override
	public void drawButton() {
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF333333 : 0xFF222222);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 2, parent.parent.getY() + offset + 12, 0xFF111111);
		FontManager.small.drawString(this.boolSet.name, (parent.parent.getX() + 10 + 4) + 5, (parent.parent.getY() + offset + 1), boolSet.isEnabled() ? (Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled() ? ColorUtils.rainbow(8, 0.6f, 1, parent.offset * 10) : ClickGui.color) : -1, true);
		RenderUtils.drawCircle(parent.parent.getX() + 3 + 7, parent.parent.getY() + offset + 6, 4.5f, 15, 0xFF999999);
		if(this.boolSet.isEnabled())
			RenderUtils.drawFilledCircle(parent.parent.getX() + 3 + 7, parent.parent.getY() + offset + 6, 2.5f, (Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled() ? ColorUtils.rainbow(8, 0.6f, 1, parent.offset * 10) : ClickGui.color));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void updateElement(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			this.boolSet.setEnabled(!boolSet.isEnabled());
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
