package badgamesinc.hypnotic.util.drm;

import badgamesinc.hypnotic.Hypnotic;

public class DRM 
{
	public static void checkDRM() 
	{
		if(AuthCheck.getAuthCheck() != "https://github.com/BadGamesInc/hypnotic-client") 
		{
			while(true) 
			{
				System.err.println("[HYPNOTIC SKID DETECTION]: CLASS 2 LEVEL SKID SENSORS TRIGGERED");
			}
			
		}
	}
	
}
