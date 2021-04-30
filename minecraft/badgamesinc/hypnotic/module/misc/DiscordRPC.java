package badgamesinc.hypnotic.module.misc;

import java.util.Random;

import badgamesinc.hypnotic.discordrpc.DiscordRP;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class DiscordRPC extends Mod
{
	private DiscordRP discordRP = new DiscordRP();
	private Random r = new Random();
	
	public DiscordRPC() 
	{
		super("Discord RPC", 0, Category.MISC, "Toggles on or off Discord Rich Presence");
		this.setEnabled(true);
	}
	
	@Override
	public void onUpdate()
	{
		discordRP.title = this.rptitle();
	}
	
	public void onDisable() 
	{
		discordRP.shutdown();
	}
	
	public String rptitle() 
	{
		switch(r.nextInt(11)) 
		{
			case 0: return "Gaming PVP 1.8.8";
			case 1: return "I downloaded a PC pinger...";
			case 2: return "FUELPUMPS ON TOP :fuelpump:";
			case 3: return "don't let trent censor the media";
			case 4: return "Gaming.";
			case 5: return "https://github.com/BadGamesInc/hypnotic-client/";
			case 6: return "Down with the Millers!";
			case 7: return "Hypnotic on top";
			case 8: return "L";
			case 9: return "E4PE4J";
			case 10: return "Now featuring bypasses!";
			default: return "Default Text";
		}
	}
	
	public DiscordRP getDiscordRP() 
	{
		return discordRP;
	}
}