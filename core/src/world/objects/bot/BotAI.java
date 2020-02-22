package world.objects.bot;

import java.util.ArrayList;

import world.objects.bot.AI.behavior.BotBehavior;

public class BotAI {
	
	private Bot bot;
	private BotCommands c;
	private ArrayList<BotBehavior> behaviors;
	
	public BotAI(Bot bot){
		this.bot = bot;
		c = bot.commands();
		behaviors = new ArrayList<BotBehavior>();
	}
	
	public void addBehavior(BotBehavior behavior){
		behaviors.add(behavior);
		behavior.set(bot, c);
	}
	
	public void clearBehaviors(){
		behaviors.clear();
	}
	
	public void update(float delta){
		for (int i = 0; i < behaviors.size(); i++)
			behaviors.get(i).update(delta);
	}
}
