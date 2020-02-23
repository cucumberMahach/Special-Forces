package world.objects.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import engine.utils.timer.Timer;
import engine.utils.timer.TimerEvent;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;

public abstract class CharacterActions {
	private Character ch;
	
	private Ray ray, dirRay;
	private float lastShoot;
	private Vector2 vect;
	
	private Timer reloadTimer;
	private Timer changeTimer;
	
	public CharacterActions(Character ch){
		this.ch = ch;
		
		ray = new Ray();
		dirRay = new Ray();
		vect = new Vector2();
		
		reloadTimer = new Timer();
		reloadTimer.setEvent(new ReloadEvent());

		changeTimer = new Timer();
		changeTimer.setInterval(0.3f);
		changeTimer.setEvent(new ChangeEvent());
	}
	
	public void update(float delta){
		lastShoot += delta;
		reloadTimer.update(delta);
		changeTimer.update(delta);
	}
	
	public void reload(){
		Weapon weapon = ch.getWeapon();
		
		if (reloadTimer.isEnabled() || !weapon.canReload() || lastShoot < weapon.getShootDelay())
			return;
		
		reloadTimer.setInterval(weapon.getReloadTime());
		((ReloadEvent) (reloadTimer.getEvent())).setWeapon(weapon);
		reloadTimer.start();

		try {
			ch.getWorld().effects().dropCase(weapon.getType(), ch.getCenterX(), ch.getCenterY(), ch.getWidthCof(), ch.getRotation(), ch);
		}catch (NullPointerException ex){} //fix null
		SpecialForces.getInstance().sounds().playReload(weapon.getType());
	}

	public Ray getRay(){
		ch.getWeaponTile().getRelativePos(WeaponPos.SHOT, vect);
		vect.add(ch.getX(), ch.getY());
		dirRay.origin.set(vect.x, vect.y, 0);
		float ang = Maths.degreesToRadians(ch.getRotation());
		dirRay.direction.set(MathUtils.cos(ang), MathUtils.sin(ang), 0);
		return dirRay;
	}
	
	public void shoot(float x, float y){
		Weapon weapon = ch.getWeapon();

		//origin
		ch.getWeaponTile().getRelativePos(WeaponPos.SHOT, vect);
		vect.add(ch.getX(), ch.getY());
		ray.origin.set(vect.x, vect.y, 0);
		float ang = Maths.degreesToRadians(ch.getRotation());
		ray.direction.set(MathUtils.cos(ang), MathUtils.sin(ang), 0);
		
		if (lastShoot < weapon.getShootDelay() || reloadTimer.isEnabled())
			return;
		
		if (!weapon.shoot()){
			reload();
			return;
		}
		
		//float ang;
		lastShoot = 0;
		

		
		//direction
		if (weapon.getType() == WeaponType.Grenade){
			ray.direction.set(x, y, 0);
		}else{
			ang = Maths.degreesToRadians(ch.getRotation());
			ray.direction.set(MathUtils.cos(ang), MathUtils.sin(ang), 0);
		}
		
		if (weapon.getType() == WeaponType.Shotgun){
			vect.set(ray.direction.x, ray.direction.y);
			vect.rotate(-(Style.SHOTGUN_RANGE * Style.SHOTGUN_BULLETS / 2));
			for (int i = 0; i < Style.SHOTGUN_BULLETS; i++){
				vect.rotate(Style.SHOTGUN_RANGE);
				ray.direction.set(vect.x, vect.y, 0);
				ch.getWorld().shoot(ray, ch, weapon);
			}
		}else{
			if (ch.getWorld() != null) { //fix null
				ch.getWorld().shoot(ray, ch, weapon);
			}
		}
		
		//dropShell
		if (weapon.getType() != WeaponType.Bazooka){
			ch.getWeaponTile().getRelativePos(WeaponPos.CENTER, vect);
			vect.add(ch.getX(), ch.getY());
			if (ch.getWorld() != null)
				ch.getWorld().effects().dropShell(weapon.getType(), vect.x, vect.y, ch.getWidthCof(), ch.getRotation(), ch);
		}
		
		SpecialForces.getInstance().sounds().playShoot(weapon);
		((ChangeEvent) changeTimer.getEvent()).setWeapon(weapon);
		changeTimer.start();
		if (weapon.isEmpty())
			ch.updatePose();
	}
}

class ReloadEvent implements TimerEvent{
	private Weapon weapon;
	
	@Override
	public void event() {
		weapon.reload();
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
}

class ChangeEvent implements TimerEvent{
	private Weapon weapon;

	@Override
	public void event() {
		SpecialForces.getInstance().sounds().playChange(weapon);
	}

	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
}
