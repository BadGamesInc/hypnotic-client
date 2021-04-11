package badgamesinc.hypnotic.discordrpc;

import java.util.Random;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.util.ResourceLocation;

public class DiscordRP {
	private boolean running = true;
	private long created = 0;
	public String title = "Default Text";
	public void start() 
	{
		this.created = System.currentTimeMillis();
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			public void apply(DiscordUser user) {
				System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
				update(title, "");
			}
		}).build();
		DiscordRPC.discordInitialize("830588291085762562", handlers, true);
		new Thread("Discord RPC Callback") {
			public void run() {
				while(running) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();
	}
	public void shutdown() 
	{
		running = false;
		DiscordRPC.discordShutdown();
	}
	public void update(String firstLine, String secondLine) 
	{
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("large.png", "");
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		DiscordRPC.discordUpdatePresence(b.build());
	}
}
