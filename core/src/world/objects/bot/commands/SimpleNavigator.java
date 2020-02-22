package world.objects.bot.commands;

import engine.utils.Maths;
import world.objects.bot.BotCommands;

public class SimpleNavigator {
	private BotCommands c;
	
	private boolean reached;
	private int reachX, reachY;
	
	public SimpleNavigator(BotCommands c){
		this.c = c;
	}
	
	public void update(float delta){
		if (!reached){
			if (c.isDistArrived(reachX, reachY)){
				c.stop();
				reachX = 0;
				reachY = 0;
				reached = true;
			}
		}
	}
	
	public void walkTo(float x, float y){
		walkTo(Maths.toMapX(x), Maths.toMapY(y));
	}
	
	public void walkTo(int x, int y){
		if (c.isDistArrived(x, y))
			return;
		reachX = x;
		reachY = y;
		c.setDirection(reachX, reachY, true);
		reached = false;
	}
}
