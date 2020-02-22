package world.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import world.gameplay.Shoot;

public class Decoration extends MapObject{
	
	private TextureRegion texture;
	private int health;
	private String name;
	private boolean exploded;
	
	private final Vector2 vect;
	
	public Decoration(Loader loader, float x, float y, String name, int health){
		this.name = name;
		texture = loader.getObject(name);
		setPosition(x, y);
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
		setOrigin(Align.center);
		this.health = health;
		vect = Maths.getTmpVector();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		checkHealth();
	}
	
	private void checkHealth(){
		if (health <= 0)
			broke();
	}
	
	private void broke(){
		final float vel = exploded ? Style.EXPLODE_DROP_SCL : 1;
		for (int i = 0; i < 8; i++){
			vect.set(5,5).scl(vel);
			vect.rotate(MathUtils.random(360));
			getWorld().effects().dynamicParticle(name, getCenterX(), getCenterY(), vect.x, vect.y, 0.9f, 10, MathUtils.random(0.7f, 0.8f), this);
		}
		removeGlobal();
		SpecialForces.getInstance().sounds().wood(2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isInView())
			batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		health -= damage;
		SpecialForces.getInstance().sounds().wood(3);
		exploded = isExplosion;
	}

	@Override
	public void getShot(Shoot shoot) {
		if (getWorld() == null)
			return;
		vect.set(shoot.hitX - getCenterX(), shoot.hitY - getCenterY());
		vect.nor().scl(3);
		vect.rotate(MathUtils.randomTriangular(90));
		getWorld().effects().dynamicParticle(name, shoot.hitX, shoot.hitY, vect.x, vect.y, 0.9f, 10, MathUtils.random(0.3f, 0.4f), this);
	}

}
