package world.objects.bot;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

import engine.Style;
import engine.utils.Maths;
import world.Map;
import world.gameplay.WeaponType;
import world.map.TileType;
import world.objects.MapObject;
import world.objects.ObjectType;
import world.objects.bot.commands.Navigator;
import world.objects.bot.commands.SimpleNavigator;

public class BotCommands {
	private Bot bot;
	
	private Vector2 vect;
	private Ray ray;
	
	private SimpleNavigator simpleNavigator;
	private Navigator navigator;
	
	public BotCommands(Bot bot){
		this.bot = bot;
		vect = new Vector2();
		ray = new Ray();
		
		simpleNavigator = new SimpleNavigator(this);
		navigator = new Navigator(bot, this);
	}
	
	public void update(float delta){
		simpleNavigator.update(delta);
		navigator.update(delta);
	}
	
	public SimpleNavigator simpleNavigator(){
		return simpleNavigator;
	}
	
	public Navigator navigator(){
		return navigator;
	}
	
	public boolean isPreventShoot(MapObject obj, MapObject target){
		final float dx = target.getCenterX() - bot.getCenterX();
		final float dy = target.getCenterY() - bot.getCenterY();
		final float ang = MathUtils.atan2(dy, dx);
		return bot.getWorld().physics().isObjectPrevent(bot.getCenterX(), bot.getCenterY(), MathUtils.cos(ang), MathUtils.sin(ang), obj);
	}
	
	public MapObject getRaycastObject(ObjectType type, float range, float distance){
		return bot.getWorld().physics().getRaycastObject(type, bot.getCenterX(), bot.getCenterY(), Maths.degreesToRadians(bot.getRotation()), range, distance);
	}
	
	public boolean isDistArrived(int x, int y){
		return getDist(x, y) <= Style.ARRIVE_DISTANCE;
	}
	
	public boolean isDistArrived(MapObject obj){
		return getDist(obj.getCenterX(), obj.getCenterY()) <= Style.ARRIVE_DISTANCE * Style.TILE_SIZE;
	}
	
	public boolean isArrived(int x, int y){
		return getMapX() == x && getMapY() == y;
	}
	
	public boolean teleport(int x, int y){
		final Map map = bot.getWorld().map();
		final boolean can = map.get(x, y).type == TileType.NONE;
		if (can){
			bot.setPosition(Maths.fromMapX(x), Maths.fromMapY(y));
			map.registerObject(bot);
		}
		return can;
	}
	
	public void teleportRandom(int orgX, int orgY){
		for (int radius = 1; radius < 100; radius++){
			for (float ang = 0; ang < MathUtils.PI2; ang += 0.1f){
				final int x = Maths.toMapX(radius * MathUtils.cos(ang));
				final int y = Maths.toMapX(radius * MathUtils.sin(ang));
				if (teleport(orgX + x, orgY + y))
					return;
			}
		}
	}
	
	public void setDirection(int x, int y, boolean rotate){
		setDirection(Maths.fromMapX(x) + 16, Maths.fromMapY(y) + 16, rotate);
	}
	
	public void setDirection(float x, float y, boolean rotate){
		vectorTo(x, y, vect);
		vect.nor();
		bot.setDirection(vect.x, vect.y);
		if (rotate)
			bot.setRotation(getDegrees(vect.x, vect.y));
	}
	
	public void setDirectionFrom(float x, float y, boolean rotate){
		vectorFrom(x, y, vect);
		vect.nor();
		bot.setDirection(vect.x, vect.y);
		if (rotate)
			bot.setRotation(getDegrees(vect.x, vect.y));
	}
	
	public boolean isVisible(MapObject obj, float visibleDistance, float eyeRange){
		return isVisible(obj.getCenterX(), obj.getCenterY(), visibleDistance, eyeRange);
	}
	
	public boolean isVisible(float x, float y, float visibleDistance, float eyeRange){
		ray.origin.x = bot.getCenterX();
		ray.origin.y = bot.getCenterY();
		float ang = Maths.degreesToRadians(bot.getRotation());
		ray.direction.set( MathUtils.cos(ang), MathUtils.sin(ang), 0);
		return bot.getWorld().physics().isVisible(ray, x, y, visibleDistance, eyeRange);
	}
	
	public boolean isVisible(float x, float y, float visibleDistance){
		return isVisible(x, y, visibleDistance, 0);
	}
	
	public void stop(){
		bot.setDirection(0, 0);
	}
	
	public void setWeapon(WeaponType type){
		bot.setWeaponIndex(type.ordinal());
	}
	
	public void shoot(MapObject target){
		bot.actions().shoot(target.getCenterX() - bot.getCenterX(), target.getCenterY() - bot.getCenterY());
	}
	
	public void reload(){
		bot.actions().reload();
	}

	public boolean secondWeapon(){
		return bot.secondWeapon();
	}

	public boolean isFullEmpty(){
		return bot.getWeapon().isEmpty();
	}

	public boolean isEmpty(){
		return bot.getWeapon().getCaseAmmo() == 0;
	}
	
	public void rotateTo(MapObject target){
		rotateTo(target.getCenterX(), target.getCenterY());
	}
	
	public void rotateTo(float x, float y){
		vectorTo(x, y, vect);
		final float len = vect.len();
		vect.nor();
		float degr = getDegrees(vect.x, vect.y);
		bot.setRotation(degr + 0.02f * len);
	}
	
	public float getFastDist(float toX, float toY){
		final float x = toX - bot.getCenterX();
		final float y = toY - bot.getCenterY();
		return x * x + y * y;
	}
	
	public float getDist(int toX, int toY){
		return getDist(getMapX(), getMapY(), toX, toY);
	}
	
	public float getDist(float toX, float toY){
		final float x = toX - bot.getCenterX();
		final float y = toY - bot.getCenterY();
		return Math.abs(x) + Math.abs(y);
	}
	
	public float getDist(int x1, int y1, int x2, int y2){
		final int x = x2 - x1;
		final int y = y2 - y1;
		return Math.abs(x) + Math.abs(y);
	}
	
	private float getDegrees(float toX, float toY){
		final float ang = MathUtils.atan2(toY, toX);
		return Maths.radiansToDegrees(ang);
	}
	
	private void vectorTo(float x, float y, Vector2 vectorTo){
		vectorTo.x = x - bot.getCenterX();
		vectorTo.y = y - bot.getCenterY();
	}
	
	private void vectorFrom(float x, float y, Vector2 vectorFrom){
		vectorFrom.x = bot.getCenterX() - x;
		vectorFrom.y = bot.getCenterY() - y;
	}
	
	public int getMapY(){
		return Maths.toMapY(bot.getCenterY());
	}
	
	public int getMapX(){
		return Maths.toMapX(bot.getCenterX());
	}
}
