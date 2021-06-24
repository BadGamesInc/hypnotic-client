package badgamesinc.hypnotic.gui;

import java.awt.Color;
import java.io.IOException;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.util.UpdateUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiOutdated extends GuiScreen {

	private String status = "Your current build is out of date";
	private float timeTicks = 0;
	
	public GuiOutdated() {
		
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new AnimatedButton(1337, this.width / 2 - 50, this.height / 2, 50, 20, "Update"));
		this.buttonList.add(new AnimatedButton(911, this.width / 2, this.height / 2, 50, 20, "Close"));
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawRect(this.width / 2 - 100, this.height / 2 + 50, this.width / 2 + 100, this.height / 2 - 50, new Color(0, 0, 0, 200).getRGB());
		FontManager.roboto.drawCenteredString(status, this.width / 2, this.height / 2 - 20, -1);
		if (Hypnotic.instance.isFinishedInstall()) {
			if (this.timeTicks < 200) {
				this.timeTicks+=10/20;
			}
			if (this.timeTicks >= 200)
				mc.shutdown();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1337:
				UpdateUtils.updateClient();
				this.status = "Succesfully updated to " + Hypnotic.instance.latestBuild + "!";
				FontManager.roboto.drawCenteredString("The client will close in approximently 10 seconds.", this.width / 2, this.height / 2 - 20 - FontManager.roboto.getHeight() - 2, -1);
				break;
			case 911:
				GuiMainMenu mainMenu = new GuiMainMenu();
				if (mainMenu != null)
					mc.displayGuiScreen(mainMenu);
				break;
		}
		super.actionPerformed(button);
	}
}
