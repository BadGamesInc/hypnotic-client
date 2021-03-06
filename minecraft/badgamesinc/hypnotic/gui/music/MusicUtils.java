package badgamesinc.hypnotic.gui.music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicUtils {
    
	public int playTime;
	public Player player;
	private FileInputStream fileInputStream;
	private String filePath = "";
	
	public MusicUtils() {
		try {
			this.fileInputStream = new FileInputStream(filePath);
			this.player = new Player(fileInputStream);
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void playSong(String filePath) {
		this.filePath = filePath;
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Player player = new Player(fileInputStream);
			this.playTime = player.getPosition();
			player.play();
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stopSong(String filePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Player player = new Player(fileInputStream);
			player.close();
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
	}
}
