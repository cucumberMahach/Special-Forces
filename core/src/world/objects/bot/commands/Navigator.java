package world.objects.bot.commands;

import java.util.Stack;

import engine.utils.Maths;
import world.map.Cell;
import world.objects.MapObject;
import world.objects.bot.Bot;
import world.objects.bot.BotCommands;


public class Navigator {
	private Bot b;
	private BotCommands c;
	
	private int reachX, reachY;
	
	private Stack<Cell> steps;
	private boolean rotating;
	
	public Navigator(Bot bot, BotCommands c){
		this.b = bot;
		this.c = c;
		steps = new Stack<Cell>();
	}
	
	public void update(float delta){
		if (isNavigating()){
			if (c.isArrived(reachX, reachY)){
				if (steps.isEmpty()){
					reached();
				}else{
					nextPoint();
				}
			}
		}
	}
	
	public boolean isNavigating(){
		return !(reachX == 0 && reachY == 0);
	}
	
	public void stopNavigate(){
		steps.clear();
		reachX = 0;
		reachY = 0;
		c.stop();
	}
	
	private void reached(){
		stopNavigate();
	}
	
	private boolean nextPoint(){
		if (steps.isEmpty())
			return false;
		Cell cell = steps.pop();
		toPoint(cell);
		return true;
	}
	
	private void toPoint(Cell point){
		reachX = point.x;
		reachY = point.y;
		c.setDirection(reachX, reachY, rotating);
	}
	
	private void startNavigating(){
		nextPoint();
	}
	
	public boolean isTargetIs(MapObject obj){
		final int x = Maths.toMapX(obj.getCenterX());
		final int y = Maths.toMapY(obj.getCenterY());
		return x == reachX && y == reachY;
	}
	
	public void teleportToResult(){
		c.teleportRandom(reachX, reachY);
	}
	
	public void navigateTo(MapObject obj, boolean rotating){
		navigateTo(obj.getCenterX(), obj.getCenterY(), rotating);
	}
	
	public void navigateTo(float x, float y, boolean rotating){
		this.rotating = rotating;
		int toX = Maths.toMapX(x);
		int toY = Maths.toMapY(y);
		if (c.isArrived(toX, toY))
			return;
		steps.clear();
		b.getWorld().map().findPath(c.getMapX(), c.getMapY(), toX, toY, steps);
		startNavigating();
	}
}
