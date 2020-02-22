package world;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import engine.Loader;
import engine.Style;
import stages.World;
import view.Group;
import world.effects.BulletPaths;
import world.effects.particles.DefaultParticle;
import world.effects.particles.HitParticle;
import world.effects.particles.Particles;
import world.gameplay.Shoot;
import world.gameplay.WeaponType;
import world.objects.MapObject;

public class Effects extends Group{
	
	private BulletPaths bulletPaths;
	private Particles particles;
	
	private Vector2 tmpVect;
	
	public Effects(World world, Loader loader){
		bulletPaths = new BulletPaths();
		particles = new Particles(world, loader);
		
		addActor(bulletPaths);
		addActor(particles);
		
		tmpVect = new Vector2();
	}
	
	public void free(){
		bulletPaths.restore();
		particles.restore();
	}
	
	public void createParticle(String name, float x, float y, float velX, float velY, float smoothCof, float rotationVel, float fadeCof, float width){
		particles.createParticle(name, x, y, velX, velY, smoothCof, rotationVel, fadeCof, width);
	}
	
	public void dropParticle(String name, float x, float y, float velX, float velY, float gravity, float rotationRange, float sizeCof, MapObject exception){
		particles.createDropParticle(name, x, y, velX, velY, gravity, MathUtils.randomTriangular() * rotationRange, sizeCof, exception);
	}
	
	public void dynamicParticle(String name, float x, float y, float velX, float velY, float gravity, float rotationRange, float sizeCof, MapObject exception){
		particles.createDynamicParticle(name, x, y, velX, velY, gravity, MathUtils.randomTriangular() * rotationRange, sizeCof, exception);
	}
	
	public void addExplosion(float x, float y, float radius){
		DefaultParticle p;
		p = particles.createExplosionParticle(x, y, 0, 0, 0, 0, 0.1f, radius*2);
		p.setScaleAction(1, 0.02f, 1);
		p.setLifeTime(1);
	}
	
	public void addSmoke(float x, float y, float width){
		DefaultParticle p;
		p = particles.createSmokeParticle(x, y, 0, 0, 0, MathUtils.randomTriangular(), Style.SMOKE_FADE_COF, width);
		p.setScaleAction(0.5f, 0.05f, 0.9f);
	}
	
	public void addHitParticle(float x, float y, HitParticle type){
		DefaultParticle p;
		switch(type){
		case SMOKE:
			p = particles.createSmokeParticle(x, y, 0, 0, 0, 0, 0.05f, 5);
			p.setScaleAction(1f, 0.5f, 0.9f);
			break;
		default:
			break;
		}
	}
	
	public void addBulletPath(Shoot shoot){
		bulletPaths.addPath(shoot.origX, shoot.origY, shoot.dirX, shoot.dirY, shoot.dist());
	}
	
	public void dropCase(WeaponType type, float x, float y, float sizeCof, float degrees, MapObject exception){
		final String weaponCase;
		weaponCase = type.getWeaponCase();
		if (weaponCase == null)
			return;
		tmpVect.set(MathUtils.random(-1f, -0.5f), MathUtils.randomTriangular(Style.CHARACTER_SIZECOF));
		tmpVect.nor().scl(3).rotate(degrees);
		particles.createDropParticle(weaponCase, x, y, tmpVect.x, tmpVect.y, sizeCof, exception);
	}
	
	public void dropShell(WeaponType type, float x, float y, float sizeCof, float degrees, MapObject exception){
		final String bullet;
		bullet = type.getBullet();
		if (bullet == null)
			return;
		tmpVect.set(MathUtils.random(Style.CHARACTER_SIZECOF, 1f), MathUtils.randomTriangular(0.1f));
		tmpVect.nor().scl(MathUtils.random(3f, 4f)).rotate(degrees);
		particles.createDropParticle(type.getBullet(), x, y, tmpVect.x, tmpVect.y, sizeCof, exception);
	}

}
