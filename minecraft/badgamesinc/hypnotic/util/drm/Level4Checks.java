package badgamesinc.hypnotic.util.drm;

import badgamesinc.hypnotic.Hypnotic;

public class Level4Checks 
{
	public static void checkLevel4() 
	{
		if(Hypnotic.instance.discordRP.publicAppKey != "830588291085762562") 
		{
			while(true) {
			System.out.println("---- Hypnotic Crash Report ----\r\n"
					+ "// LEVEL 4 SKID DETECTED.\r\n"
					+ "\r\n"
					+ "Time: 4/20/69 6:21 PM\r\n"
					+ "Description: Initializing game\r\n"
					+ "\r\n"
					+ "java.lang.NullPointerException: Initializing game\r\n"
					+ "	at net.minecraft.client.Minecraft.displayGuiScreen(Minecraft.java:256)\r\n"
					+ "	at badgamesinc.hypnotic.Hypnotic.startup(Hypnotic.java:57)\r\n"
					+ "	at net.minecraft.client.Minecraft.startGame(Minecraft.java:452)\r\n"
					+ "	at net.minecraft.client.Minecraft.run(Minecraft.java:213)\r\n"
					+ "	at net.minecraft.client.main.Main.main(Main.java:145)\r\n"
					+ "	at Start.main(Start.java:9)\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "A detailed walkthrough of the error, its code path and all known details is as follows:\r\n"
					+ "---------------------------------------------------------------------------------------\r\n"
					+ "\r\n"
					+ "-- Head --\r\n"
					+ "Stacktrace:\r\n"
					+ "	at net.minecraft.client.Minecraft.displayGuiScreen(Minecraft.java:243)\r\n"
					+ "	at badgamesinc.hypnotic.Hypnotic.startup(Hypnotic.java:65)\r\n"
					+ "	at net.minecraft.client.Minecraft.startGame(Minecraft.java:435)\r\n"
					+ "\r\n"
					+ "-- Initialization --\r\n"
					+ "Details:\r\n"
					+ "Stacktrace:\r\n"
					+ "	at net.minecraft.client.Minecraft.run(Minecraft.java:463)\r\n"
					+ "	at net.minecraft.client.main.Main.main(Main.java:174)\r\n"
					+ "	at Start.main(Start.java:7)");}
		}
	}
}
