package badgamesinc.hypnotic.gui.newclickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newclickgui.button.Button;
import badgamesinc.hypnotic.gui.newclickgui.settingwindow.SettingsWindow;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen {

	public static ClickGUI instance = new ClickGUI();
	public Button but = null;
	public SettingsWindow setWin = null;
	public ArrayList<Frame> frames;
	public ArrayList<Button> buttons;
	public float offset;
	public int xWidth, xHeight;
	public static GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 60, false, false, false);
	
	public ClickGUI() {
		frames = new ArrayList<>();
		buttons = new ArrayList<>();
		this.xWidth = 20;
		this.xHeight = 20;
		
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
		
		for(Button b : buttons){
            but = b;
        }
		
		drawSettings(mouseX, mouseY);
	
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(Button b : buttons){
			but = b;
        }
		
		for (Frame frame : frames) {
			frame.onClick(mouseX, mouseY, mouseButton);
		}
		
		if (isHoveredOverXButton(mouseX, mouseY) && mouseButton == 0) {
			Button.settingsOpen = false;
		}
		
		
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Button.settingsOpen = false;
	}
	
	//Settings window
		public void drawSettings(int mouseX, int mouseY) {
			
				ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
				if (Button.settingsOpen == false)
					return;
				
				Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, new Color(0, 0, 0, 150).getRGB());
				//Outline
				Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 - 352, sr.getScaledHeight() / 2 + 175, -1);
				Gui.drawRect(sr.getScaledWidth() / 2 + 352, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, -1);
				Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 - 173, -1);
				Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 + 173, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, -1);
				
				for (Button button : buttons) {
					bigFontRenderer.drawString(button.name, sr.getScaledWidth() / 2 - 330, sr.getScaledHeight() / 2 - 165, -1, true);
				}
				
				drawCloseSettingsButton(mouseX, mouseY);
		}
		
		public void drawCloseSettingsButton(int mouseX, int mouseY) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			int xX = sr.getScaledWidth() / 2 + 320;
			int xY = sr.getScaledHeight() / 2 - 165;
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/clickgui/XButton.png"));
			
			Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 + 320, sr.getScaledHeight() / 2 - 165, 20, 20, 20, 20, 20, 20);
			if (isHoveredOverXButton(mouseX, mouseY))
				Gui.drawRect(xX, xY, xX + xWidth, xY + xHeight, new Color(100, 100, 100, 170).getRGB());
		}
		
		public boolean isHoveredOverXButton(int mouseX, int mouseY) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			int xX = sr.getScaledWidth() / 2 + 320;
			int xY = sr.getScaledHeight() / 2 - 165;
			return mouseX >= xX && mouseX <= xX + xWidth && mouseY >= xY && mouseY <= xY + xHeight;
		}
}
