package badgamesinc.hypnotic.gui.music.buttons;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import badgamesinc.hypnotic.gui.music.GuiMusic;
import badgamesinc.hypnotic.gui.music.MusicUtils;
import badgamesinc.hypnotic.util.SoundUtil;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SongButton {

	private int x, y, songWidth, songHeight, playWidth, playHeight, playX, playY, pauseX, pauseY, pauseWidth, pauseHeight, stopX, stopY, stopWidth, stopHeight;
	private String name;
	private File file;
	private boolean selected, playing;
	
	public SongButton(int x, int y, int width, int height, String name, File file) {
		this.x = x;
		this.y = y;
		this.songWidth = width;
		this.songHeight = height;
		this.name = name;
		this.file = file;
	}
	
	public void drawSong(int mouseX, int mouseY) {
		int width = 50;
		int height = 50;
		GlStateManager.enableBlend();
		FontManager.bigJello.drawCenteredString(name, x + width / 2, y + 60, -1);
		Gui.drawRect(x, y, x + width, y + height, new Color(100, 100, 100, 100).getRGB());
		Gui.drawRect(x, y, x, y, new Color(255, 255, 255).getRGB());
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/music/musik.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
		if (isHoveredSong(mouseX, mouseY) || this.selected)
			Gui.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 100).getRGB());
		Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
	}
	
	public void drawSongOptions(int mouseX, int mouseY) {
		Gui.drawRect(GuiMusic.instance.width / 2 - 300, GuiMusic.instance.height / 2 + 100, GuiMusic.instance.width / 2 + 300, GuiMusic.instance.height / 2 + 150, new Color(100, 100, 100, 180).getRGB());
		this.playWidth = 30;
		this.playHeight = 30;
		this.playX = GuiMusic.instance.width / 2 - 40;
		this.playY = GuiMusic.instance.height / 2 + 110;
		
		this.pauseWidth = 30;
		this.pauseHeight = 30;
		this.pauseX = GuiMusic.instance.width / 2 + 5;
		this.pauseY = GuiMusic.instance.height / 2 + 110;
		
		this.stopWidth = 30;
		this.stopHeight = 30;
		this.stopX = GuiMusic.instance.width / 2 + 50;
		this.stopY = GuiMusic.instance.height / 2 + 110;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/music/play.png"));
		Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		Gui.drawModalRectWithCustomSizedTexture(playX, playY, playWidth, playHeight, playWidth, playHeight, playWidth, playHeight);
		if (isHoveredPlay(mouseX, mouseY)) {
			Gui.drawRect(playX, playY, playX + playWidth, playY + playHeight, new Color(100, 100, 100, 100).getRGB());
			Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/music/pause.png"));
		Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		Gui.drawModalRectWithCustomSizedTexture(pauseX, pauseY, pauseWidth, pauseHeight, pauseWidth, pauseHeight, pauseWidth, pauseHeight);
		if (isHoveredPause(mouseX, mouseY)) {
			Gui.drawRect(pauseX, pauseY, pauseX + pauseWidth, pauseY + pauseHeight, new Color(100, 100, 100, 100).getRGB());
			Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/music/stop.png"));
		Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		Gui.drawModalRectWithCustomSizedTexture(stopX, stopY, stopWidth, stopHeight, stopWidth, stopHeight, stopWidth, stopHeight);
		if (isHoveredStop(mouseX, mouseY)) {
			Gui.drawRect(stopX, stopY, stopX + stopWidth, stopY + stopHeight, new Color(100, 100, 100, 100).getRGB());
			Gui.drawRect(0, 0, 0, 0, new Color(255, 255, 255, 255).getRGB());
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		MusicUtils music = new MusicUtils();
		Thread musicThread = new Thread(() -> {
            while (true) {
                music.playSong(file.toString(), false);
            }
        });
		if (isHoveredSong(mouseX, mouseY) && mouseButton == 0) {
			this.setSelected(true);
		}
		if (!isHoveredSong(mouseX, mouseY) && !isHoveredOptions(mouseX, mouseY) && mouseButton == 0) {
			this.setSelected(false);
		}
		if (isHoveredPlay(mouseX, mouseY) && mouseButton == 0) {
			musicThread.setDaemon(true);
			musicThread.start();
			this.setPlaying(true);
		}
		if (isHoveredPause(mouseX, mouseY) && mouseButton == 0 && this.isPlaying()) {
			this.setPlaying(false);
			musicThread.suspend();
		}
		if (isHoveredStop(mouseX, mouseY) && mouseButton == 0 && this.isPlaying()) {
			musicThread.stop();
		}
	}
	
	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isHoveredOptions(int mouseX, int mouseY) {
		return mouseX >= GuiMusic.instance.width / 2 - 300 && mouseX <= GuiMusic.instance.width / 2 + 300 && mouseY >= GuiMusic.instance.height / 2 + 100 && mouseY <= GuiMusic.instance.height / 2 + 150;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isHoveredSong(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + songWidth && mouseY >= y && mouseY <= y + songHeight;
	}
	
	public boolean isHoveredPlay(int mouseX, int mouseY) {
		return mouseX >= playX && mouseX <= playX + playWidth && mouseY >= playY && mouseY <= playY + playHeight;
	}
	
	public boolean isHoveredPause(int mouseX, int mouseY) {
		return mouseX >= pauseX && mouseX <= pauseX + pauseWidth && mouseY >= pauseY && mouseY <= pauseY + pauseHeight;
	}
	
	public boolean isHoveredStop(int mouseX, int mouseY) {
		return mouseX >= stopX && mouseX <= stopX + stopWidth && mouseY >= stopY && mouseY <= stopY + stopHeight;
	}
}
