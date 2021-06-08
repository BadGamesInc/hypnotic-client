package badgamesinc.hypnotic.gui.newclickgui.settingwindow;

public class SettingsWindow {
	
	/*public ArrayList<Button> buttons;
	public Setting setting;
	public Button parentMod;
	public int xWidth, xHeight;
	public String parentName;
	public static GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 45, false, false, false);
	
	public SettingsWindow(Button parentMod) {
		this.parentMod = parentMod;
		this.xWidth = 20;
		this.xHeight = 20;
		this.parentName = parentMod.name;
	}
	
	public void draw(int mouseX, int mouseY) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		if (Button.settingsOpen == false)
			return;
		
		Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, new Color(0, 0, 0, 150).getRGB());
		//Outline
		Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 - 352, sr.getScaledHeight() / 2 + 175, -1);
		Gui.drawRect(sr.getScaledWidth() / 2 + 352, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, -1);
		Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 - 175, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 - 173, -1);
		Gui.drawRect(sr.getScaledWidth() / 2 - 350, sr.getScaledHeight() / 2 + 173, sr.getScaledWidth() / 2 + 350, sr.getScaledHeight() / 2 + 175, -1);
		
		bigFontRenderer.drawString(this.parentName, sr.getScaledWidth() / 2 - 330, sr.getScaledHeight() / 2 - 165, -1, true);
			
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
	
	public void onClick(int mouseX, int mouseY, int mouseButton) {
		if (isHoveredOverXButton(mouseX, mouseY) && mouseButton == 0) {
			Button.settingsOpen = false;
		}
	}
	
	public boolean isHoveredOverXButton(int mouseX, int mouseY) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int xX = sr.getScaledWidth() / 2 + 320;
		int xY = sr.getScaledHeight() / 2 - 165;
		return mouseX >= xX && mouseX <= xX + xWidth && mouseY >= xY && mouseY <= xY + xHeight;
	}*/

}
