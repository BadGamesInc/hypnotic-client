package badgamesinc.hypnotic.command.commands;

import badgamesinc.hypnotic.command.Command;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command {

	@Override
	public String getAlias() {
		
		return "say";
	}

	@Override
	public String getDescription() {
		
		return "Says the message you type in chat";
	}

	@Override
	public String getSyntax() {
		
		return ".say (message)";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0] == null) {
			Wrapper.tellPlayer("Usage: " + getSyntax());
		} else {
			mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
		}
		
	}

}
