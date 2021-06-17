package badgamesinc.hypnotic.gui.newererclickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.sun.security.ntlm.Client;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newerclickgui.button.settings.Keybind;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.component.Component;
import badgamesinc.hypnotic.gui.newererclickgui.component.Frame;
import badgamesinc.hypnotic.gui.newererclickgui.component.components.sub.Checkbox;
import badgamesinc.hypnotic.gui.newererclickgui.component.components.sub.ModeButton;
import badgamesinc.hypnotic.gui.newererclickgui.component.components.sub.Slider;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button extends Component {

	public Mod mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	private ArrayList<Setting> settings;
	public boolean open;
	private int height;
	
	public Button(Mod mod, Frame parent, int offset) {
		super(mod, parent, offset);
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if (mod.getSettings() != null) {
        	settings = mod.getSettings();
        }
		if (settings != null) {
			for(Setting s : settings){
				if(s instanceof ModeSetting){
					this.subcomponents.add(new ModeButton(s, this, opY));
					opY += 12;
				}
				if(s instanceof NumberSetting){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s instanceof BooleanSetting){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		int rainbowOrder = 0;
		
		boolean rainbow = Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled();
		
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ?  (this.mod.isEnabled() ? (rainbow ? new Color(ColorUtils.rainbow(8, 0.6f, 1, offset * 10)).darker().getRGB() : new Color(ClickGui.color).darker().getRGB()) : 0xFF111111) : (this.mod.isEnabled() ? (rainbow ? ColorUtils.rainbow(8, 0.6f, 1, offset * 10) : new Color(ClickGui.color).getRGB()) : 0xFF222222));
		FontManager.robotoSmall.drawStringWithShadow(this.mod.getName(), (parent.getX() + 5), (parent.getY() + offset + 1), this.mod.isEnabled() ? -1 : new Color(180, 180, 180, 255).getRGB());
		if(this.subcomponents.size() > 1)
			FontManager.robotoSmall.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10), (parent.getY() + offset + 1), mod.isEnabled() ? -1 : new Color(180, 180, 181, 255).getRGB());
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
