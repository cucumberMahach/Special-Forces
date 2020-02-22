package world.objects.bot.AI.behavior;

import world.objects.bot.Bot;
import world.objects.bot.BotCommands;

public interface BotBehavior {
	public void set(Bot b, BotCommands c);
	public void update(float delta);
}
