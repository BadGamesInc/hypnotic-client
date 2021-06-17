package badgamesinc.hypnotic.util;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import badgamesinc.hypnotic.Hypnotic;
import net.minecraft.client.Minecraft;

public class SoundUtil {

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream (
					Hypnotic.class.getResourceAsStream("/assets/minecraft/hypnotic/sounds/" + url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	public static synchronized void playSong(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream (
							
					Hypnotic.class.getResourceAsStream(new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic/music").toString() + "\\" + url));
					
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
}
