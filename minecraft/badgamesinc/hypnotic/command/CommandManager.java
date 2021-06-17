package badgamesinc.hypnotic.command;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.command.commands.*;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.util.ColorUtils;

public class CommandManager {

	public CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();
	
	public CommandManager() 
	{
		commands.add(new Toggle());
		commands.add(new VClip());
		commands.add(new Say());
		commands.add(new Bind());
		commands.add(new Server());
		commands.add(new Teleport());
		commands.add(new ConfigCmd());
		commands.add(new Seed());
		commands.add(new Enchant());
		commands.add(new Help());
		commands.add(new About());
		commands.add(new Modules());
		commands.add(new NBT());
		commands.add(new Panic());
		commands.add(new ConfigCmd());
		commands.add(new LogoName());
		commands.add(new Nick());
		commands.add(new Friend());
		commands.add(new SpammerCmd());
		//commands.add(new Plugins());
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
					NotificationManager.getNotificationManager().createNotification(ColorUtils.red + "Usage: " + c.getSyntax(), "", true, 1500, Type.WARNING, Color.RED);
					e.printStackTrace();
				}
				return;
			}
		}

	}
}
