package badgamesinc.hypnotic.gui.newclickgui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.module.Category;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {

	public static ClickGUI instance = new ClickGUI();
	
	ArrayList<Frame> frames;
	
	public float offset;
	
	public ClickGUI() {
		frames = new ArrayList<>();
		
		offset = 0.1f;
		for (Category category : Category.values()) {
			frames.add(new Frame(category, 10 + offset, 15));
			offset += 110;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.drawDefaultBackground();
		for (Frame frame : frames) {
			frame.render(mouseX, mouseY);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (Frame frame : frames) {
			frame.onClick(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Button.settingsOpen = false;	
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
