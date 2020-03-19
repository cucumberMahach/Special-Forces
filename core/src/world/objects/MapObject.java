package world.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;

import stages.World;
import view.Group;
import world.effects.particles.HitParticle;
import world.gameplay.Shoot;

public abstract class MapObject extends Group{
	
	private boolean inView = true;
	private boolean toCollide = true;
	private boolean toShoot = true;
	private boolean toExplode = true;
	private boolean ignorePathFinding = false;
	private HitParticle hitParticle = HitParticle.SMOKE;
	private float colWidth, colHeight;
	private ObjectType type = ObjectType.DEFAULT;
	
	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public boolean ignorePathFinding(){
		return ignorePathFinding;
	}
	
	public void ignorePathFinding(boolean value){
		ignorePathFinding = value;
	}
	
	public float getCollideWidth(){
		return colWidth == 0 ? getWidth() : colWidth;
		
	}
	
	public float getCollideHeight(){
		return colHeight == 0 ? getHeight() : colHeight;
	}
	
	public void setCollideBox(float width, float height){
		colWidth = width;
		colHeight = height;
	}
	
	public void setHitParticle(HitParticle type){
		this.hitParticle = type;
	}
	
	public HitParticle getHitParticle(){
		return hitParticle;
	}
	
	public void setToExplode(boolean value){
		toExplode = value;
	}
	
	public void setToCollide(boolean value){
		toCollide = value;
	}
	
	public void setToShoot(boolean value){
		toShoot = value;
	}
	
	public boolean canToExplode(){
		return toExplode;
	}
	
	public boolean canToCollide(){
		return toCollide;
	}
	
	public boolean canToShoot(){
		return toShoot;
	}
	
	@Override
	public void act(float delta) {
		final OrthographicCamera camera = getWorld().getOrtCam();
		final float x = camera.position.x - (camera.viewportWidth/2) * camera.zoom;
		final float y = camera.position.y - (camera.viewportHeight/2) * camera.zoom;
		final float w = camera.viewportWidth * camera.zoom;
		final float h = camera.viewportHeight * camera.zoom;
		inView = getX() + getWidth() > x && getX() < x + w && getY() + getHeight() > y && getY() < y + h;
	}
	
	public boolean isInView(){
		return inView;
	}
	
	public abstract void getShot(Shoot shoot);
	
	public abstract void getDamage(float damage, boolean isExplosion);
	
	public void removeGlobal(){
		if (getWorld() == null)
			return;
		getWorld().objects().remove(this);
		super.remove();
	}
	
	public World getWorld(){
		return ((World) getStage());
	}
}
