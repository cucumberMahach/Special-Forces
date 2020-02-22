package world.objects.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import view.Image;
import world.effects.particles.HitParticle;
import world.gameplay.Shoot;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;
import world.objects.Item;
import world.objects.MapObject;

public abstract class Character extends MapObject{
	private TextureRegion character[];
	private Image characterImg;
	
	private WeaponTile weapon;
	private CharacterFeets feets;
	
	private CharacterBody body;
	private CharacterActions actions;
	private CharacterInventory inventory;
	
	private float widthCof, heightCof;
	private int health, energy;
	private boolean healthChanged, exploded;
	
	private Vector2 vect;
	
	public Character(){}
	
	protected void set(Loader loader, String skin, float x, float y, CharacterBody body, CharacterActions actions, CharacterInventory inventory){
		this.body = body;
		this.actions = actions;
		this.inventory = inventory;
		this.character = loader.getCharacter(skin);
		feets = new CharacterFeets(loader);
		characterImg = new Image(character[0]);
		weapon = new WeaponTile(this);
		
		addActor(feets);
		addActor(characterImg);
		addActor(weapon);
		
		vect = Maths.getTmpVector();
		setPosition(x, y);
		setHitParticle(HitParticle.NONE);
		changeWeaponIndex(0);
		
		feets.resize(widthCof, heightCof);
		feets.setPosition(getOriginX(), getOriginY(), Align.center);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		body.update(delta);
		actions.update(delta);
		updateHealth();
		updateFeets(delta);
	}
	
	protected void updateFeets(float delta){
		feets.update(body.isMoving(), body.getSpeedPercent());
		feets.act(delta);
	}
	
	public void giveAmmo(WeaponType type, int ammo){
		inventory.giveAmmo(type, ammo);
	}
	
	public void setInfinityAmmo(WeaponType type, boolean value){
		inventory.setInfinityAmmo(type, value);
	}
	
	protected void updateHealth(){
		if (!healthChanged)
			return;
		healthChanged = false;
		healthChanged();
	}
	
	private void healthChanged(){
		if (isDied())
			die(exploded);
	}
	
	protected void die(boolean exploded){
		float vel = exploded ? Style.EXPLODE_DROP_SCL : 1;
		for (int i = 0; i < 10; i++){
			vect.set(5,5).scl(vel);
			vect.rotate(MathUtils.random(360));
			getWorld().effects().dropParticle("body".concat(String.valueOf(MathUtils.random(2))), getCenterX(), getCenterY(), vect.x, vect.y, 0.9f, 10, 0.4f, this);
			vect.set(3,3).scl(vel);
			vect.rotate(MathUtils.random(360));
			getWorld().effects().dropParticle("blood".concat(String.valueOf(MathUtils.random(4))), getCenterX(), getCenterY(), vect.x, vect.y, 0.8f, 10, 1f, this);
			vect.set(6,6).scl(vel);
			vect.rotate(MathUtils.random(360));
			getWorld().effects().dropParticle("blood".concat(String.valueOf(MathUtils.random(4))), getCenterX(), getCenterY(), vect.x, vect.y, 0.8f, 10, 0.7f, this);
		}
		removeGlobal();
		if (exploded)
			SpecialForces.getInstance().sounds().exploded();
		else
			SpecialForces.getInstance().sounds().death();
	}
	
	public boolean isDied(){
		return health <= 0;
	}

	public boolean secondWeapon(){
		return inventory.secondWeapon();
	}
	
	@Override
	public void getShot(Shoot shoot) {
		if (getWorld() == null)
			return;
		vect.set(shoot.hitX - getCenterX(), shoot.hitY - getCenterY());
		vect.nor().scl(1f);
		getWorld().effects().createParticle("blood".concat(String.valueOf(MathUtils.random(4))), shoot.hitX, shoot.hitY, vect.x, vect.y, 0.8f, MathUtils.randomTriangular(3), 0.1f, 8);
	}
	
	@Override
	public void getDamage(float damage, boolean isExplosion) {
		if (energy > 0){
			subEnergy(damage/3);
			health -= damage/2;
		}else{
			health -= damage;
		}
		healthChanged = true;
		exploded = isExplosion;
		blood();
		SpecialForces.getInstance().sounds().damage();
	}

	private void subEnergy(float value){
		energy -= value;
		if (energy < 0)
			energy = 0;
	}

	private void blood(){
		if (getWorld() == null)
			return;
		for (int i = 0; i < 5; i++){
			vect.setToRandomDirection().scl(3);
			getWorld().effects().createParticle("blood".concat(String.valueOf(MathUtils.random(4))), getCenterX(), getCenterY(), vect.x, vect.y, 0.8f, MathUtils.randomTriangular(3), 0.1f, 8);
		}
	}

	public void pickUp(Item item){
		boolean used = false;
		if (item.isKit()){
			switch (item.getItemType()) {
			case Medkit:
				used = addHealth(Style.MEDKIT_INCREASE);
				break;
			case Energy:
				used = addEnergy(Style.ENERGY_INCREASE);
				break;

			default:
				break;
			}
			if (used)
				item.pickUp(0);
			return;
		}
		inventory.pickUp(item);
	}

	public boolean addHealth(int value){
		if (health == 100)
			return false;
		int newHealth = health + value;
		if (newHealth < 100)
			health = newHealth;
		else
			health = 100;
		return true;
	}

	public boolean addEnergy(int value){
		if (energy == 100)
			return false;
		int newArmor = energy + value;
		if (newArmor < 100)
			energy = newArmor;
		else
			energy = 100;
		return true;
	}

	private void updateSize(){
		final float regW = characterImg.getTexture().getRegionWidth();
		final float regH = characterImg.getTexture().getRegionHeight();
		characterImg.setSize(Style.TILE_SIZE, Style.TILE_SIZE * regH / regW);
		characterImg.setOrigin(Align.center);
		setSize(characterImg.getWidth(), characterImg.getHeight());
		setOrigin(Align.center);
		widthCof = getWidth() / regW;
		heightCof = getHeight() / regH;
	}

	private void setPose(Pose pose){
		switch (pose) {
		case HAND:
			characterImg.setTexture(character[0]);
			weapon.setPosition(Style.CHARACTER_HAND_X, Style.CHARACTER_HAND_Y);
			break;

		case ARM:
			characterImg.setTexture(character[1]);
			weapon.setPosition(Style.CHARACTER_ARM_X, Style.CHARACTER_ARM_Y);
			break;

		case STAND:
			characterImg.setTexture(character[2]);
			break;
		}
		updateSize();
		weapon.setPosition(weapon.getX() * widthCof, weapon.getY() * heightCof);
		weapon.update(getWeapon(), widthCof, heightCof);
	}

	public void setWeaponIndex(int index){
		inventory.setWeaponIndex(index);
		updatePose();
	}

	public void changeWeaponIndex(int amount){
		inventory.changeWeaponIndex(amount);
		updatePose();
	}
	
	public void updatePose(){
		setPose(getWeapon().getPose());
	}
	
	public Weapon getWeapon(){
		return inventory.getWeapon();
	}
	
	public Weapon[] getWeapons(){
		return inventory.getWeapons();
	}
	
	public int getWeaponIndex(){
		return inventory.getWeaponIndex();
	}
	
	public WeaponTile getWeaponTile(){
		return weapon;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!isInView())
			return;
		super.draw(batch, parentAlpha);
	}
	
	public void setHealth(int value){
		health = value;
	}
	
	public void setEnergy(int value){
		energy = value;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	public float getWidthCof(){
		return widthCof;
	}
	
	public float getHeightCof(){
		return heightCof;
	}

	public Ray getRay(){
		return actions.getRay();
	}
	
}
