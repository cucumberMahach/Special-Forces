package world.objects.bot.AI.behavior;
import com.badlogic.gdx.math.MathUtils;

import engine.Style;
import world.gameplay.Soldier;
import world.objects.Enemy;
import world.objects.ObjectType;
import world.objects.bot.Bot;
import world.objects.bot.BotCommands;
import world.objects.player.Player;

public class TeammateBehavior implements BotBehavior{
	
	private Bot b;
	private BotCommands c;
	private Player p;
	private final float visibleDistance, visibleRange, eyeRange;
	private int oldX, oldY;
	
	private float navigationTime, stuckTime, reactionTime, shootingTime, waitTime, raycastTime;
	private Enemy target;
	
	private final float reactionDelay, stuckDelay = Style.BOT_MAX_STUCK_DELAY, shootingDelay, navigateDelay = Style.BOT_MAX_NAVIGATE_DELAY, waitDelay;
	
	public TeammateBehavior(Soldier soldier) {
		this.visibleDistance = Style.VISIBLE_DISTANCE*2;
		this.visibleRange = MathUtils.PI2;
		this.eyeRange = 1 - (2 / MathUtils.PI2 * visibleRange);
		reactionDelay = (1 - soldier.getReaction()) * Style.BOT_MAX_REACTION_DELAY;
		shootingDelay = (1 - soldier.getBrave()) * Style.BOT_MAX_SHOOTING_DELAY;
		waitDelay = (1 - soldier.getReaction()) * Style.BOT_MAX_WAIT_DELAY;
	}

	@Override
	public void set(Bot b, BotCommands c) {
		this.b = b;
		this.c = c;
	}
	
	@Override
	public void update(float delta) {
		navigationTime += delta;
		p = b.getWorld().getPlayer();
		
		raycastTime += delta;
		if (raycastTime > Style.BOT_RAYCAST_FREQUENCY){
			raycastTime = 0;
			target = (Enemy) c.getRaycastObject(ObjectType.ENEMY, visibleRange, visibleDistance);
		}
		
		if (target != null){
			if (c.isVisible(target.getCenterX(), target.getCenterY(), visibleDistance, eyeRange)){
				if (reacted(delta))
					actionToTarget(delta);
			}else{
				reactionTime = 0;
				doTask(BehaviorTask.WALK_TO_TARGET, true);
			}
		}else{
			waitTime += delta;
			if (waitTime > waitDelay)
				doTask(BehaviorTask.WALK_TO_PLAYER, true);
		}
		
		if (c.navigator().isNavigating())
			checkStuck(delta);
		
	}
	
	private boolean reacted(float delta){
		reactionTime += delta;
		return reactionTime > reactionDelay;
	}
	
	private void checkStuck(float delta){
		final int newX = c.getMapX();
		final int newY = c.getMapY();
		if (newX == oldX && newY == oldY){
			stuckTime += delta;
		}else{
			stuckTime = 0;
			oldX = newX;
			oldY = newY;
		}
		
		if (stuckTime > stuckDelay){
			stuckTime = 0;
			c.navigator().teleportToResult();
		}
	}
	
	private void actionToTarget(float delta){
		if (c.isVisible(target.getCenterX(), target.getCenterY(), 500, -1)){
			if (!c.isPreventShoot(p, target))
				shoot();
			else
				shootingTime = 3;
		}
		shootingTime += delta;
		if (shootingTime > shootingDelay){
			shootingTime = 0;
			doTask(BehaviorTask.WALK_TO_TARGET, false);
		}
	}
	
	private void shoot(){
		if (c.isEmpty()) {
			if (c.isFullEmpty())
				c.secondWeapon();
			c.reload();
		}else{
			c.rotateTo(target);
			c.shoot(target);
		}
	}

	public void doTask(BehaviorTask task, boolean rotate){
		if (navigationTime < navigateDelay)
			return;
		navigationTime = 0;
		switch (task) {
		case WALK_TO_TARGET:
			c.navigator().navigateTo(target, rotate);
			break;
		case WALK_TO_PLAYER:
			c.navigator().navigateTo(p, rotate);
			break;
		default:
			break;
		}
	}
}