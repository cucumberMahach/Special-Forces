package world.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import engine.Loader;
import engine.SpecialForces;
import engine.utils.Maths;
import engine.utils.Point;
import stages.World;
import view.Button;
import view.Group;
import view.joystick.Joystick;
import view.joystick.JoystickListener;

public class TouchController extends Group{
	private World world;
	private Joystick joystick;
	private Button leftButton, rightButton, reloadBtn, shootBtn;
	private Actor blank;
	
	private TouchListener touchController;
	private ShootListener shootController;
	private MovePlayer moveController;
	
	public TouchController(World world, Loader loader){
		this.world = world;
		setSize(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		joystick = new Joystick(loader, 70, 120);
		
		leftButton = new Button(loader.getHud("btnArrow"), 10, 400);
		rightButton = new Button(loader.getHud("btnArrow"), leftButton.getX() + leftButton.getWidth() + 50, leftButton.getY());
		rightButton.setRotation(180);
		reloadBtn = new Button(loader.getHud("btnReload"), 1140, 120);
		shootBtn = new Button(loader.getHud("btnShoot"), 1126, 400);
		
		shootController = new ShootListener(world);
		shootBtn.addListener(shootController);
		leftButton.setName("left");
		rightButton.setName("right");
		reloadBtn.setName("reload");
		
		moveController = new MovePlayer(world);
		joystick.setListener(moveController);
		
		blank = new Actor();
		blank.setSize(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		touchController = new TouchListener(world, this);
		blank.addListener(touchController);
		
		addListener(new HitListener(this));
		
		addActor(blank);
		addActor(joystick);
		addActor(leftButton);
		addActor(rightButton);
		addActor(reloadBtn);
		addActor(shootBtn);
	}
	
	public void setVisibleButtons(boolean visible){
		leftButton.setVisible(visible);
		rightButton.setVisible(visible);
		reloadBtn.setVisible(visible);
		shootBtn.setVisible(visible);
	}
	
	public void hitted(Actor actor){
		String name = actor.getName();
		if (name == null)
			return;
		if (name.equals("left")){
			world.getPlayer().changeWeaponIndex(-1);
		}else if(name.equals("right")){
			world.getPlayer().changeWeaponIndex(1);
		}else if(name.equals("reload")){
			world.getPlayer().actions().reload();
		}
	}
	
	@Override
	public void act(float delta) {
		if (world.getPlayer().isDied())
			return;
		shootController.update();
		touchController.update();
		moveController.setCanMove(!shootController.isPressed());
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (world.getPlayer() != null)
			super.draw(batch, parentAlpha);
	}
}

class HitListener extends InputListener{
	
	private TouchController group;
	
	public HitListener(TouchController group){
		this.group = group;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		group.hitted(group.hit(x, y, true));
		return false;
	}
}

class MovePlayer implements JoystickListener{
	private World world;
	private boolean canMove, down;
	private float dirX, dirY;
	
	public MovePlayer(World world) {
		this.world = world;
	}
	
	public void setCanMove(boolean canMove){
		this.canMove = canMove;
		if (!canMove)
			setDirection(0, 0);
		else
			if (down)
				setDirection(dirX, dirY);
	}

	@Override
	public void dragDown(float x, float y) {
		down = true;
	}

	@Override
	public void dragged(float x, float y, float cof) {
		setSpeedRate(cof);
		setRotation(x, y);
		dirX = x;
		dirY = y;
		if (canMove)
			setDirection(x, y);
	}

	@Override
	public void dragUp() {
		setDirection(0, 0);
		down = false;
	}
	
	private void setRotation(float x, float y){
		if (world.getPlayer().isMouseRotation())
			return;
		world.getPlayer().setRotation(Maths.radiansToDegrees( (float) Math.atan2(y, x)));
	}
	
	private void setDirection(float x, float y){
		world.getPlayer().setDirection(x * Maths.FPSCompensation(), y * Maths.FPSCompensation());
	}
	
	private void setSpeedRate(float rate){
		world.getPlayer().setSpeedRate(rate);
	}
	
}

class TouchListener extends InputListener implements Updatable{
	
	private World world;
	private TouchController parent;
	private Point tmpPoint, touchPoint;
	private boolean down;
	
	public TouchListener(World world, TouchController parent){
		this.world = world;
		tmpPoint = new Point();
		touchPoint = new Point();
		this.parent = parent;
	}
	
	public void update(){
		world.getPlayer().mouseRotation(down);
		if (down) {

			shoot(touchPoint.x, touchPoint.y);
		}
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		touchPoint.set(x, y);
		down = true;
		parent.setVisibleButtons(false);
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		down = false;
		parent.setVisibleButtons(true);
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		touchPoint.set(x, y);
	}
	
	private void shoot(float x, float y){
		Maths.mouseToPlayer(x, y, tmpPoint);
		world.getPlayer().setRotation(Maths.radiansToDegrees( (float) Math.atan2(tmpPoint.y, tmpPoint.x)));
		world.getPlayer().actions().shoot(tmpPoint.x, tmpPoint.y);
	}
}

class ShootListener extends InputListener implements Updatable{
	
	private World world;
	private float ang;
	private boolean down;
	
	public ShootListener(World world){
		this.world = world;
	}
	
	@Override
	public void update(){
		if (down) {
			world.getPlayer().mouseRotation(false);
			shoot();
		}
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		down = true;
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		down = false;
	}
	
	private void shoot(){
		ang = Maths.degreesToRadians(world.getPlayer().getRotation());
		world.getPlayer().actions().shoot(MathUtils.cos(ang) * 200, MathUtils.sin(ang) * 200);
	}
	
	public boolean isPressed(){
		return down;
	}
}