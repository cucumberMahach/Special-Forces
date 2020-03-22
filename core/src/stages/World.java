package stages;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import engine.utils.Point;
import engine.utils.Tiled;
import engine.utils.Zone;
import view.Debugger;
import world.Effects;
import world.Gui;
import world.Hud;
import world.Map;
import world.MissionManager;
import world.Objects;
import world.Physics;
import world.Spawner;
import world.gameplay.Shoot;
import world.gameplay.Weapon;
import world.objects.MapObject;
import world.objects.player.Player;
import world.credits.CreditsGroup;

public class World extends Stage implements Tiled{
	private Map map;
	private Physics physics;
	private Hud hud;
	private Objects objects;
	private Effects effects;
	private Spawner spawner;
	private MissionManager manager;
	private Debugger debugger;
	private Gui gui;
	private Zone completeZone;
	private CreditsGroup creditsGroup;
	
	private float camRoomZoom, camZoom, camDifX, camDifY, camOffset, camStatX, camStatY;
	private Point point;
	private boolean pause;
	
	public World(Viewport viewport, Batch batch, Loader loader, InputMultiplexer multiplexer){
		super(viewport, batch);
		completeZone = new Zone();
		completeZone.hide();
		
		map = new Map(this, loader, false);
		physics = new Physics(this);
		hud = new Hud(this, loader);
		objects = new Objects(this);
		effects = new Effects(this, loader);
		spawner = new Spawner(this, loader, multiplexer);
		debugger = new Debugger();
		gui = new Gui(this, loader);
		manager = new MissionManager(this, loader);
		multiplexer.addProcessor(this);
		point = new Point();
		creditsGroup = new CreditsGroup(this, loader);

		addActor(map);
		addActor(objects);
		addActor(effects);
		addActor(creditsGroup);
		addActor(hud);
		addActor(debugger);
		addActor(gui);
	}

	public void creditsMode(boolean enable){
		if (enable) {
			getPlayer().removeGlobal();
		}
		hud.setVisible(!enable);
		creditsGroup.enable(enable);
	}
	
	@Override
	public void act(float delta) {
		if (pause){
			gui.act(delta);
			return;
		}
		super.act(delta); 
		updateCamera(delta);
		manager.update(delta);
	}
	
	public void zoomBy(float value){
		camRoomZoom += value;
	}
	
	public void moveBy(float x, float y){
		camStatX += x;
		camStatY += y;
	}

	public void moveTo(float x, float y){

	}
	
	private float roomSizeTime;
	
	private void calcCamera(float delta, float x, float y){
		Maths.calcDirection(getPlayer().getRotation(), point);
		point.scl(Style.CAMERA_PLAYER_OFFSET * camRoomZoom);
		point.add(x, y);
		
		roomSizeTime += delta;
		if (roomSizeTime > Style.ROOM_RAYCASTING_FREQUENCY){
			roomSizeTime = 0;
			float size = physics.getMaxRoomLen(x, y);
			if (size <= 0)
				size = Style.VISIBLE_DISTANCE;
			camRoomZoom = size / (size + Style.CAMERA_ROOM_ZOOM);
		}
		
		camDifX = point.x - getCamera().position.x;
		camDifY = point.y - getCamera().position.y;
		camZoom += (camRoomZoom - camZoom) * Style.CAMERA_SPEED_ZOOM;
		camOffset *= 0.95f; 
	}
	
	private void updateCamera(float delta){
		calcCamera(delta, getPlayer().getCenterX(), getPlayer().getCenterY());
		((OrthographicCamera) getCamera()).zoom = camZoom;
		getCamera().position.add(camStatX, camStatY, 0);
		getCamera().position.add(camDifX * Style.CAMERA_SPEED_MOVE, camDifY * Style.CAMERA_SPEED_MOVE, 0);
		getCamera().position.add(MathUtils.randomTriangular(camOffset), MathUtils.randomTriangular(camOffset), 0);
		hud.setPosition(getCamera().position.x - (SpecialForces.WIDTH/2)*camZoom, getCamera().position.y - (SpecialForces.HEIGHT/2)*camZoom);
		hud.setScale(camZoom);
		gui.setPosition(hud.getX(), hud.getY());
		gui.setScale(camZoom);
	}
	
	public void shoot(Ray ray, MapObject exception, Weapon weapon){
		switch (weapon.getType()) {
		case Bazooka:
			spawner.spawnRocket(ray, exception);
			return;
		case Grenade:
			spawner.spawnGrenade(ray, exception);
			return;
		default:
			break;
		}
		Shoot shoot = physics.checkShoot(ray, exception);
		shoot.weapon = weapon;
		shoot.from = exception;
		effects.addBulletPath(shoot);
		effects.addHitParticle(shoot.hitX, shoot.hitY, shoot.getHitParticle());
		if (shoot.object != null){
			shoot.object.getShot(shoot);
			shoot.object.getDamage(weapon.getDamage(), false);
		}
		camOffset(0.5f);
	}
	
	private void camOffset(float value){
		camOffset += value;
	}
	
	public void explode(float x, float y, float radius, float damage){
		physics.checkExplosion(x, y, radius, damage);
		effects.addExplosion(x, y, radius);
		camOffset(10);
		SpecialForces.getInstance().sounds().explode();
	}
	
	public Zone getCompleteZone(){
		return completeZone;
	}
	
	public void setCompleteZone(float x, float y, float width, float height){
		completeZone.set(x, y, width, height);
	}
	
	public void setPause(boolean pause){
		this.pause = pause;
		if (pause){
			SpecialForces.getInstance().sounds().pauseAllPlayedMusic();
		}else{
			SpecialForces.getInstance().sounds().restoreAllPlayedMusic();
		}
	}
	
	public Player getPlayer(){
		return spawner.getPlayer();
	}
	
	public Gui gui(){
		return gui;
	}
	
	public MissionManager manager(){
		return manager;
	}
	
	public Debugger debugger(){
		return debugger;
	}
	
	public Spawner spawner(){
		return spawner;
	}
	
	public Effects effects(){
		return effects;
	}
	
	public Objects objects(){
		return objects;
	}
	
	public Hud hud(){
		return hud;
	}
	
	public Physics physics(){
		return physics;
	}
	
	public Map map(){
		return map;
	}

	@Override
	public int getMapWidth() {
		return map.getMapWidth();
	}

	@Override
	public int getMapHeight() {
		return map.getMapHeight();
	}

	@Override
	public OrthographicCamera getOrtCam() {
		return ((OrthographicCamera) getCamera());
	}

	@Override
	public float getPlayerX() {
		return getPlayer().getCenterX();
	}

	@Override
	public float getPlayerY() {
		return getPlayer().getCenterY();
	}
}
