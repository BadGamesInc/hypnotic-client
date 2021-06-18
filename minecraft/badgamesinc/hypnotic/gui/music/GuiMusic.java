package badgamesinc.hypnotic.gui.music;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.gui.AnimatedButton;
import badgamesinc.hypnotic.gui.CleanTextField;
import badgamesinc.hypnotic.gui.music.buttons.SongButton;
import badgamesinc.hypnotic.util.Wrapper;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiMusic extends GuiScreen {

	private CleanTextField fileName;
	private CleanTextField urlBox;
	private ArrayList<SongButton> songButtons;
	public static GuiMusic instance = new GuiMusic();
	
	public GuiMusic() {
		songButtons = new ArrayList<SongButton>();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		ScaledResolution sr = new ScaledResolution(mc);
		this.drawRect(sr.getScaledWidth() / 2 - 300, sr.getScaledHeight() / 2 - 150, sr.getScaledWidth() / 2 + 300, sr.getScaledHeight() / 2 + 150, new Color(0, 0, 0, 160).getRGB());
		fileName.drawTextBox();
		urlBox.drawTextBox();
		FontManager.jelloFont.drawString("Download Song", this.width / 2 - 290, this.height / 2 - 140, -1);
		if (fileName.getText().isEmpty()) {
			FontManager.jelloFont.drawString("File Name", this.width / 2 - 285, this.height / 2 - 113, new Color(150, 150, 150).getRGB());
		}
		
		if (urlBox.getText().isEmpty()) {
			FontManager.bigJello.drawString("File URL (direct link)", this.width / 2 - 285, this.height / 2 - 83, new Color(151, 150, 150).getRGB());
		}
		
		
		
		int xOff = 0;
		int yOff = 0;
		int xOff2 = 0;
		int size = 0;
		
		for (SongButton song : songButtons) {
			song.drawSong(mouseX, mouseY);
			if (song.isSelected() || song.isPlaying())
				song.drawSongOptions(mouseX, mouseY);
			xOff+=100;
			size++;
			if (size >= 4) {
				xOff=0;
				yOff+=100;
				xOff2+=100;
			}
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		fileName.mouseClicked(mouseX, mouseY, mouseButton);
		urlBox.mouseClicked(mouseX, mouseY, mouseButton);
		for (SongButton song : songButtons) {
			song.mouseClicked(mouseX, mouseY, mouseButton);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void initGui() {
		ScaledResolution sr = new ScaledResolution(mc);
		fileName = new CleanTextField(100, fontRendererObj, this.width / 2 - 290, this.height / 2 - 120, 150, 20);
		urlBox = new CleanTextField(2, fontRendererObj, this.width / 2 - 290, this.height / 2 - 90, 150, 20);
		fileName.setMaxStringLength(15);
		urlBox.setMaxStringLength(Integer.MAX_VALUE);
		this.buttonList.add(new GuiButton(1, this.width / 2 - 290, this.height / 2 - 60, 100, 20, "Download", 2));
		File musicDir = new File(mc.mcDataDir, "Hypnotic/music");
		if (!musicDir.exists()) {
			musicDir.mkdirs();
		}
		String[] songs = musicDir.list();
		int xOff = 0;
		int yOff = 0;
		int xOff2 = 0;
		int size = 0;

		
		
		for (String songName : songs) {
			System.out.println(musicDir.toString() + "\\" + songName);
			this.songButtons.add(new SongButton(this.width / 2 - 100 + xOff, this.height / 2 - 122 + yOff, 50, 50, songName, new File(musicDir, songName)));
			xOff+=100;
			size++;
			if (size >= 4) {
				xOff=0;
				yOff+=100;
				xOff2+=100;
			}
		}
		super.initGui();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (typedChar == '\t') {
            if (!this.fileName.isFocused() && !this.urlBox.isFocused()) {
                this.fileName.setFocused(true);
            } else {
                this.fileName.setFocused(this.urlBox.isFocused());
                this.fileName.setFocused(!this.fileName.isFocused());
            }
        }
		fileName.textboxKeyTyped(typedChar, keyCode);
		urlBox.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 1:
				if (urlBox.getText().isEmpty() || fileName.getText().isEmpty()) {
					Wrapper.rawTellPlayer("Please type a name and/or URL");
				} else {
					File musicDir = new File(mc.mcDataDir, "Hypnotic/music");
					if (!musicDir.exists()) {
						musicDir.mkdirs();
					}	
					File musicFile = new File(musicDir, fileName.getText() + ".wav");		
					new Thread(new FileDownloader(urlBox.getText(), musicFile)).start();	
				}
				break;
		}
		super.actionPerformed(button);
	}	
	
	@Override
	public void onGuiClosed() {
		songButtons.clear();
		super.onGuiClosed();
	}
}
