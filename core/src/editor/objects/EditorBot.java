package editor.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import engine.Loader;
import engine.Style;
import world.gameplay.WeaponType;
import world.objects.character.WeaponTile;

public class EditorBot extends EditorObject{
	private WeaponType weaponType;
	private WeaponTile weaponTile;
	
	private Loader loader;
	private float visibleDistance;
	private int health, ammo;
	
	public EditorBot(Loader loader, float x, float y, float rotation, float visibleDistance, int health, WeaponType weaponType, int ammo) {
		super(loader.getCharacter("skin1")[1], EditorObjectType.BOT, x, y, Style.CHARACTER_SIZECOF);
		
		this.loader = loader;
		this.visibleDistance = visibleDistance;
		this.health = health;
		this.ammo = ammo;
		
		setRotation(rotation);
		
		weaponTile = new WeaponTile(true);
		setWeaponType(weaponType);
		weaponTile.setTouchable(Touchable.disabled);
		addActor(weaponTile);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (isSelected())
			getEditor().debugger().drawCircle(getCenterX(), getCenterY(), visibleDistance, Color.BLUE, false);
	}
	
	public void setWeaponType(WeaponType type) {
		this.weaponType = type;
		weaponTile.setPosition(Style.CHARACTER_ARM_X * Style.CHARACTER_SIZECOF, Style.CHARACTER_ARM_Y * Style.CHARACTER_SIZECOF);
		weaponTile.update(loader.getWeaponConfig(weaponType), Style.CHARACTER_SIZECOF, Style.CHARACTER_SIZECOF);
	}

	public void setVisibleDistance(float visibleDistance) {
		this.visibleDistance = visibleDistance;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public float getVisibleDistance(){
		return visibleDistance;
	}
	
	public int getHealth(){
		return health;
	}
	
	public WeaponType getWeaponType(){
		return weaponType;
	}
	
	public int getAmmo(){
		return ammo;
	}
}
