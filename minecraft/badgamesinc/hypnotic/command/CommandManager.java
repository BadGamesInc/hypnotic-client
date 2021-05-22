package badgamesinc.hypnotic.command;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.commands.*;
import badgamesinc.hypnotic.gui.notifications.Notification;
import badgamesinc.hypnotic.gui.notifications.NotificationType;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class CommandManager {

	public CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();
	
	public CommandManager() 
	{
		commands.add(new Toggle());
		commands.add(new VClip());
		commands.add(new Say());
		commands.add(new Bind());
		commands.add(new Server());
		commands.add(new Save());
		commands.add(new Teleport());
		commands.add(new Load());
		commands.add(new Seed());
		commands.add(new Enchant());
		commands.add(new Help());
		commands.add(new About());
		commands.add(new Modules());
		commands.add(new NBT());
		//commands.add(new Plugins());
		commands.add(new ClearBind());
	}
	
	public CopyOnWriteArrayList<Command> getCommands() 
	{
		return commands;
	}
	
	public void callCommand(String input)
	{
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for(Command c: getCommands()){
			if(c.getAlias().equalsIgnoreCase(command))
			{
				try
				{
					c.onCommand(args, args.split(" "));
				}
				catch(Exception e)
				{
					Hypnotic.instance.notificationManager.show(new Notification(ColorUtils.red + "Usage: " + c.getSyntax(), (int) 5, NotificationType.WARNING));
					e.printStackTrace();
				}
				return;
			}
		}

	}
}
