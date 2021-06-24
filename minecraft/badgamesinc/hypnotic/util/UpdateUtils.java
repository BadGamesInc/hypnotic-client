package badgamesinc.hypnotic.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.music.FileDownloader;

public class UpdateUtils {

	public static String appdata = System.getenv("APPDATA");
	public static String mcVerFolder = appdata + "/.minecraft/versions";
	public static String clientFolder = mcVerFolder + "/Hypnotic";
	public static String jarFile = clientFolder + "/Hypnotic.jar";
	public static String jsonFile = clientFolder + "/Hypnotic.json";
	public static File clientFolderDir = new File(clientFolder);
	public static File clientJar = new File(jarFile);
	public static File clientJson = new File(jsonFile);
	
	public static void updateClient() {
		Hypnotic.instance.setFinishedInstall(false);
		Logger.log("Creating Folder: " + clientFolder);
		if (!clientFolderDir.exists())
			clientFolderDir.mkdirs();
		if (clientJar.exists())
			clientJar.delete();
		Logger.log("Downloading JSON File", "File Downloader");
		Thread jsonDownloadThread = new Thread(new FileDownloader("https://www.dropbox.com/s/jlsup3c0p6bhhha/HypnoticJson.json?dl=1", clientJson));
		jsonDownloadThread.run();
		Logger.log("Downloading Jar File", "File Downloader");
		Thread jarDownloadThread = new Thread(new FileDownloader("https://www.dropbox.com/s/cazlarz1050p14s/Hypnotic.jar?dl=1", clientJar));
		jarDownloadThread.run();
		Logger.log("Succesfully downloaded all neccessary files", "File Downloader");
		Hypnotic.instance.setFinishedInstall(true);
	}
}
