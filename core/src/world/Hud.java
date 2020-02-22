package world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import controller.ControllerType;
import engine.Loader;
import engine.SpecialForces;
import engine.utils.Maths;
import engine.utils.Point;
import stages.World;
import view.Group;
import world.hud.Aim;
import world.hud.AmmoBar;
import world.hud.StatsBar;
import world.hud.TouchController;
import world.hud.WeaponBar;
import world.objects.player.Player;

public class Hud extends Group{
	private World world;
	
	private WeaponBar weaponBar;
	private AmmoBar ammoBar;
	private StatsBar statsBar;
	private TouchController touchController;

	private Aim aim;
	
	private int weaponIndex;
	private Point point;

	public Hud(World world, Loader loader){
		this.world = world;
		point = new Point();
		
		weaponBar = new WeaponBar(loader, 80, 430);
		ammoBar = new AmmoBar(loader, 578, -13);
		statsBar = new StatsBar(loader, -5, 0);
		aim = new Aim(world, loader);
		
		float scale = SpecialForces.getInstance().isAndroid() ? 1.2f : 1f;
		weaponBar.setScale(scale);
		ammoBar.setScale(scale);
		statsBar.setScale(scale);
		
		weaponBar.setPosition(SpecialForces.WIDTH / 2 - weaponBar.getWidth() * weaponBar.getScaleX() / 2, SpecialForces.HEIGHT - (weaponBar.getHeight() - 10) * weaponBar.getScaleY());
		
		if (SpecialForces.getInstance().isAndroid()){
			ammoBar.setX(SpecialForces.WIDTH / 2);
			statsBar.setX(SpecialForces.WIDTH / 2 - statsBar.getWidth() * statsBar.getScaleX());
		}else{
			ammoBar.setX(SpecialForces.WIDTH - 222 * ammoBar.getScaleX());
		}

		if (SpecialForces.getInstance().isAndroid())
			addActor(aim);

		addActor(weaponBar);
		addActor(ammoBar);
		addActor(statsBar);
		
		if (SpecialForces.getInstance().getControllerType() == ControllerType.TOUCH){
			touchController = new TouchController(world, loader);
			addActor(touchController);
		}
	}
	
	public void setVisibleTouchController(boolean visible){
		if (SpecialForces.getInstance().isAndroid())
			touchController.setVisible(visible);
	}
	
	@Override
	public void act(float delta) {
		Maths.mouseToWorldYTop(Gdx.input.getX(), Gdx.input.getY(), point);
		if (world.getPlayer() == null)
			return;
		super.act(delta);
		update(world.getPlayer());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (world.getPlayer() == null || world.getPlayer().isDied())
			return;
		super.draw(batch, parentAlpha);
	}
	
	public void update(Player player){
		statsBar.updateStats(player.getHealth(), player.getEnergy());
		ammoBar.updateAmmo(player.getWeapon());
		updateWeapons();
		if (player.getWeaponIndex() == weaponIndex)
			return;
		weaponIndex = player.getWeaponIndex();
		updateWeaponIndex();
	}
	
	private void updateWeapons(){
		if (weaponBar.isActive())
			weaponBar.updateWeapons(world.getPlayer().getWeapons());
	}
	
	private void updateWeaponIndex(){
		weaponBar.setWeaponIndex(world.getPlayer().getWeaponIndex());
		ammoBar.setWeapon(weaponIndex);
	}
}
