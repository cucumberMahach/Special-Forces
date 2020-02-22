package world.gameplay;

import world.effects.particles.HitParticle;
import world.objects.MapObject;

public class Shoot {
	public float origX, origY, hitX, hitY, distance;
	public float dirX, dirY;
	public MapObject object, from;
	public Weapon weapon;
	
	public Shoot(float origX, float origY, float hitX, float hitY, float dirX, float dirY, MapObject object){
		set(origX, origY, hitX, hitY, dirX, dirY, object);
	}
	
	public Shoot(){
		this(0, 0, 0, 0, 0, 0, null);
	}
	
	public void set(float origX, float origY, float hitX, float hitY, float dirX, float dirY, MapObject object){
		setOrigin(origX, origY);
		setHit(hitX, hitY);
		setDirection(dirX, dirY);
		this.object = object;
		dist();
	}
	
	public void setOrigin(float x, float y){
		this.origX = x;
		this.origY = y;
	}
	
	public void setHit(float x, float y){
		this.hitX = x;
		this.hitY = y;
	}
	
	public void setDirection(float x, float y){
		this.dirX = x;
		this.dirY = y;
	}
	
	public HitParticle getHitParticle(){
		return isWall() ? HitParticle.SMOKE : object.getHitParticle();
	}
	
	public boolean isWall(){
		return object == null;
	}
	
	public float dist(){
		distance = (float) Math.sqrt((hitX - origX) * (hitX - origX) + (hitY - origY) * (hitY - origY));
		return distance;
	}
}
