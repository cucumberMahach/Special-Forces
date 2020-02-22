package world.effects.particles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import engine.Loader;
import engine.Style;
import stages.World;
import view.Group;
import world.objects.MapObject;

public class Particles extends Group{
	private World world;
	
	private DropParticle dropParticles[];
	private DefaultParticle defaultParticles[];
	
	private int dropIndex;
	private int defaultIndex;
	
	private Loader loader;
	
	private TextureRegion smokeTex;
	private TextureRegion explosion[];
	
	private boolean toRestore;
	
	public Particles(World world, Loader loader){
		this.loader = loader;
		this.world = world;
		smokeTex = loader.getParticle("smoke");
		explosion = loader.getAnimation("explosion");
		
		dropParticles = new DropParticle[Style.PARTICLES_DROP_COUNT];
		defaultParticles = new DefaultParticle[Style.PARTICLES_DEFAULT_COUNT];
		
		int i;
		for (i = 0; i < dropParticles.length; i++)
			dropParticles[i] = new DropParticle(world);
		
		for (i = 0; i < defaultParticles.length; i++)
			defaultParticles[i] = new DefaultParticle();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (!toRestore)
			return;
		free();
	}
	
	private void free(){
		toRestore = false;
		dropIndex = 0;
		defaultIndex = 0;
		int i;
		for (i = 0; i < dropParticles.length; i++)
			dropParticles[i].remove();
		for (i = 0; i < defaultParticles.length; i++)
			defaultParticles[i].remove();
	}
	
	public void restore(){
		toRestore = true;
	}
	
	public DefaultParticle createParticle(String name, float x, float y, float velX, float velY, float smoothCof, float rotationVel, float fadeCof, float width){
		DefaultParticle p = getParticle();
		p.setParams(loader.getParticle(name), x, y, rotationVel, fadeCof, width);
		p.setVelocity(velX, velY, smoothCof);
		p.setRotation(MathUtils.random(360));
		addToStage(p);
		return p;
	}
	
	public DefaultParticle createExplosionParticle(float x, float y, float velX, float velY, float smoothCof, float rotationVel, float fadeCof, float width){
		DefaultParticle p = getParticle();
		p.setParams(new Animation<TextureRegion>(0.02f, explosion), x, y, rotationVel, fadeCof, width);
		p.setVelocity(velX, velY, smoothCof);
		addToStage(p);
		return p;
	}
	
	public DefaultParticle createSmokeParticle(float x, float y, float velX, float velY, float smoothCof, float rotationVel, float fadeCof, float width){
		DefaultParticle p = getParticle();
		p.setParams(smokeTex, x, y, rotationVel, fadeCof, width);
		p.setVelocity(velX, velY, smoothCof);
		p.setRotation(MathUtils.random(360));
		addToStage(p);
		return p;
	}
	
	public void createDropParticle(String key, float x, float y, float velX, float velY, float sizeCof, MapObject exception){
		createDropParticle(key, x, y, velX, velY, Style.PARTICLES_GRAVITY, MathUtils.randomTriangular(10f), sizeCof, exception);
	}
	
	public void createDropParticle(String key, float x, float y, float velX, float velY, float gravity, float rotationVel, float sizeCof, MapObject exception){
		DropParticle p = dropParticles[dropIndex];
		p.set(loader.getParticle(key), x, y, velX, velY, gravity, rotationVel, sizeCof, exception);
		p.setRotation(MathUtils.random(360));
		if (p.getStage() == null)
			world.objects().addGrounded(p);
		dropIndex++;
		if (dropIndex == dropParticles.length)
			dropIndex = 0;
	}
	
	public void createDynamicParticle(String key, float x, float y, float velX, float velY, float gravity, float rotationVel, float sizeCof, MapObject exception){
		DropParticle p = dropParticles[dropIndex];
		TextureRegion[] regs = loader.getDynamicParticles(key);
		p.set(regs[MathUtils.random(regs.length-1)], x, y, velX, velY, gravity, rotationVel, sizeCof, exception);
		p.setRotation(MathUtils.random(360));
		if (p.getStage() == null)
			world.objects().addGrounded(p);
		dropIndex++;
		if (dropIndex == dropParticles.length)
			dropIndex = 0;
	}
	
	private DefaultParticle getParticle(){
		return defaultParticles[defaultIndex];
	}
	
	private void addToStage(DefaultParticle p){
		if (p.getStage() == null)
			addActor(p);
		defaultIndex++;
		if (defaultIndex == defaultParticles.length)
			defaultIndex = 0;
	}
}
