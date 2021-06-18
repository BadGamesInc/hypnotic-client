package badgamesinc.hypnotic.gui.newererclickgui.elements.button.settings;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.elements.Element;
import badgamesinc.hypnotic.gui.newererclickgui.elements.button.Button;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModeButton extends Element {

	private boolean hovered;
	private Button parent;
	private ModeSetting modeSet = (ModeSetting)set;
	private int offset;
	private int x;
	private int y;

	private int modeIndex;
	
	public ModeButton(Setting set, Button button, int offset) {
		super(set, button, offset);
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}
	
	@Override
	public void setOff(int newOff) {
		if (modeSet == null)
			return;
		offset = newOff;
	}
	
	@Override
	public void drawButton() {
		if (modeSet == null)
			return;
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF333333 : 0xFF222222);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 2, parent.parent.getY() + offset + 12, 0xFF111111);
		FontManager.small.drawString(ColorUtils.white + modeSet.name + ": " + ColorUtils.reset + modeSet.getSelected(), (parent.parent.getX() + 5), (parent.parent.getY() + offset + 1), (Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled() ? ColorUtils.rainbow(8, 0.6f, 1, parent.offset * 10) : ClickGui.color), true);
	}
	
	@Override
	public void updateElement(int mouseX, int mouseY) {
		if (modeSet == null)
			return;
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (modeSet == null)
			return;
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			int maxIndex = modeSet.getModes().size();
			modeSet.cycle();
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if (modeSet == null)
			return false;
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
