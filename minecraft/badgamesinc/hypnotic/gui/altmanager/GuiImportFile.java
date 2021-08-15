package badgamesinc.hypnotic.gui.altmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Scanner;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.AnimatedButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiImportFile extends GuiScreen {

	private GuiTextField filePath;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		filePath.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		this.buttonList.add(new AnimatedButton(1, width / 2 - 100, height / 2 + 50, 200, 20, "Import"));
		filePath = new GuiTextField(2, fontRendererObj, width / 2 - 100, height / 2, 200, 20);
		filePath.setMaxStringLength(Integer.MAX_VALUE);
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1) {
			Scanner scanner;
			try {
				ArrayList<String> usernames = new ArrayList<>();
				ArrayList<String> passwords = new ArrayList<>();
				scanner = new Scanner(new FileReader(new File(filePath.getText())));
				while(scanner.hasNextLine()) {
					if (scanner.nextLine().contains(":")) {
						String[] credentials = scanner.nextLine().split(":");
						usernames.add(credentials[0]);
						passwords.add(credentials[1]);
						AltManager.registry.add(new Alt(credentials[0], credentials[1], ""));
		                try {
		                    Hypnotic.instance.getFileManager().getFile(Alts.class).saveFile();
		                } catch (Exception ex) {
		               
		                }
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		filePath.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		filePath.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
}
