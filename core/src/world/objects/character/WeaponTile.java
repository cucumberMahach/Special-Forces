package world.objects.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import view.Actor;
import world.gameplay.Weapon;
import world.objects.MapObject;

public class WeaponTile extends Actor{
	private Weapon weapon;
	private MapObject owner;
	private float widthCof, heightCof;
	private boolean alwaysVisible;
	
	public WeaponTile(MapObject owner){
		this.owner = owner;
	}
	
	public WeaponTile(boolean alwaysVisible){
		this.alwaysVisible = true;
	}

	public void update(Weapon weapon, float widthCof, float heightCof){
		this.weapon = weapon;
		this.widthCof = widthCof;
		this.heightCof = heightCof;
		setSize(weapon.getTexture().getRegionWidth() * widthCof, weapon.getTexture().getRegionHeight() * heightCof);
		setOrigin(Align.center);
		moveBy(weapon.getHandleX() * widthCof, weapon.getHandleY() * heightCof);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!weapon.isEmpty() || alwaysVisible)
			batch.draw(weapon.getTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	public void getRelativePos(WeaponPos pos, Vector2 vect){
		vect.set(getX(), getY()).sub(owner.getOriginX(), owner.getOriginY());
		switch (pos) {
		case SHOT:
			vect.add(weapon.getShotX() * widthCof, weapon.getShotY() * heightCof);
			break;
		case CENTER:
			vect.add(getOriginX(), getOriginY());
			break;

		default:
			break;
		}
		vect.rotate(owner.getRotation()).add(owner.getOriginX(), owner.getOriginY());
	}
}
