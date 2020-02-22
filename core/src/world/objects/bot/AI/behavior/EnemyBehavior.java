package world.objects.bot.AI.behavior;

import com.badlogic.gdx.math.MathUtils;

import engine.Style;
import engine.utils.Maths;
import world.objects.ObjectType;
import world.objects.bot.Bot;
import world.objects.bot.BotCommands;
import world.objects.player.Player;

public class EnemyBehavior implements BotBehavior{
	
	private Bot b;
	private BotCommands c;
	private Player p;
	
	private float searchTime, visibleTime, navigationTime, raycastTime;
	private float defX, defY;
	private final float visibleDistance;
	
	public EnemyBehavior(float visibleDistance) {
		this.visibleDistance = visibleDistance;
	}

	@Override
	public void set(Bot b, BotCommands c) {
		this.b = b;
		this.c = c;
		defX = b.getCenterX();
		defY = b.getCenterY();
		searchTime = Style.SEARCH_TIME;
	}

	@Override
	public void update(float delta) {
		raycastTime += delta;
		p = b.getWorld().getPlayer();

		if (p.isDied()){
			doTask(Task.RETURN);
		}else {
			if (c.isVisible(p, visibleDistance, 0) || checkRaycast()) {
				searchTime = 0;
				visibleTime += delta;
				c.rotateTo(p);
				if (visibleTime > Style.REACTION_DELAY) {
					doTask(Task.SHOOT);
				}
			} else {
				visibleTime = 0;
				if (b.getWeapon().canReload())
					c.reload();
				if (searchTime >= Style.SEARCH_TIME) {
					if (!c.isDistArrived(Maths.toMapX(defX), Maths.toMapY(defY)))
						doTask(Task.RETURN);
				} else {
					doTask(Task.SEARCH);
					searchTime += delta;
				}
			}
		}
		navigationTime += delta;
	}
	
	private boolean checkRaycast(){
		if (raycastTime < Style.BOT_RAYCAST_FREQUENCY){
			raycastTime = 0;
			if (c.getFastDist(p.getCenterX(), p.getCenterY()) < visibleDistance * visibleDistance)
				return c.getRaycastObject(ObjectType.PLAYER, MathUtils.PI, visibleDistance) != null;
		}
		return false;
	}
	
	private void shoot(){
		float dist;

		if (c.isEmpty()) {
			if (c.isFullEmpty())
				c.secondWeapon();
			c.reload();
		}else {
			c.shoot(p);
		}
		dist = c.getDist(p.getCenterX(), p.getCenterY());
		if (dist < Style.NEAREST_DISTANCE){
			c.setDirectionFrom(p.getCenterX(), p.getCenterY(), false);
		}else{
			if (dist > Style.FARTHEST_DISTANCE){
				c.setDirection(p.getCenterX(), p.getCenterY(), false);
			}else{
				c.stop();
			}
		}
		if (c.navigator().isNavigating())
			c.navigator().stopNavigate();
	}
	
	private void navigate(Task task){
		if (navigationTime < 1)
			return;
		switch(task){
		case SEARCH:
			c.navigator().navigateTo(p.getCenterX(), p.getCenterY(), true);
			break;
		case RETURN:
			c.navigator().navigateTo(defX, defY, true);
			break;
		default:
			break;
		}
		navigationTime = 0;
	}
	
	private void doTask(Task task){
		switch (task) {
		case SHOOT:
			shoot();
			break;
		case SEARCH:
		case RETURN:
			navigate(task);
			break;

		default:
			break;
		}
	}
}
enum Task {
	SHOOT, SEARCH, RETURN
}
