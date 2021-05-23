package badgamesinc.hypnotic.discordrpc;

import badgamesinc.hypnotic.util.drm.Level3Checks;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
	private boolean running = true;
	private long created = 0;
	public static String title = "Default Text";
	public String publicAppKey = "830588291085762562";
	public void start() 
	{
		Level3Checks.setL3();
		this.created = System.currentTimeMillis();
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			public void apply(DiscordUser user) {
				update(title, "");
			}
		}).build();
		DiscordRPC.discordInitialize(publicAppKey, handlers, true);
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
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		DiscordRPC.discordUpdatePresence(b.build());
	}
}
