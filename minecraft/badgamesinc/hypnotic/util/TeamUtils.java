package badgamesinc.hypnotic.util;

import net.minecraft.entity.player.EntityPlayer;

public class TeamUtils {

	  /*  public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
	        // ChatUtil.printChat(e2.getDisplayName().getFormattedText().contains("§" + isTeam(e)) + " " + isTeam(e));
	        return e.getDisplayName().getFormattedText().contains("§" + isTeam(e)) && e2.getDisplayName().getFormattedText().contains("§" + isTeam(e));
	    }
	    private static String isTeam(EntityPlayer player) {
	        final Matcher m = Pattern.compile("§(.).*§r").matcher(player.getDisplayName().getFormattedText());
	        if (m.find()) {
	            return m.group(1);
	        }
	        return "f";
	    }
	     */
		public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
			if(e2.getTeam() != null && e.getTeam() != null){
				Character target = e2.getDisplayName().getFormattedText().charAt(1);
				Character player = e.getDisplayName().getFormattedText().charAt(1);
				if(target.equals(player)){
					return true;
				}
			}else{
				return true;
			}
			return false;}
		
	}
