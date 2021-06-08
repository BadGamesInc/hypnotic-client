package badgamesinc.hypnotic.config.friends;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class FriendManager {

	public ArrayList<String> friends = new ArrayList<>();
	
	public FriendManager() {
		
	}
	
	public ArrayList<String> getFriends() {
		return friends;
	}
	
	public void addFriend(String friend) {
		friends.add(friend);
	}
	
	public boolean isFriend(EntityLivingBase friend) {
		for (String name : friends) {
			if (friend instanceof EntityPlayer)
	            if (friend.getName().equalsIgnoreCase(name))
	                return true;
        }
		return false;
	}
	
	public boolean isFriend(String friend) {
		for (String name : friends) {
			friend = name;
			for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
				if (e instanceof EntityPlayer)
					if (e.getName().equalsIgnoreCase(name))
			            return true;
			}
        }
		return false;
	}
}
