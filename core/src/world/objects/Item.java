package world.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import world.gameplay.ItemType;
import world.gameplay.Shoot;
import world.gameplay.WeaponType;

public class Item extends MapObject{
	private TextureRegion texture;
	private ItemType type;
	private int ammo;
	
	public Item(Loader loader, ItemType type, int ammo, float x, float y){
		this.ammo = ammo;
		this.type = type;
		
		texture = getTexture(loader, type);
		if (isKit())
			setSize(texture.getRegionWidth() * 0.5f, texture.getRegionHeight() * 0.5f);
		else
			setSize(texture.getRegionWidth() * 0.4f, texture.getRegionHeight() * 0.4f);
		
		setPosition(x, y);
		setOrigin(Align.center);
		setCollideBox(getWidth(), getHeight());
	}
	
	public boolean isKit(){
		return isKit(type);
	}
	
	public WeaponType getWeaponType(){
		return WeaponType.values()[type.ordinal()];
	}
	
	public ItemType getItemType(){
		return type;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public void pickUp(int ost){
		ammo = ost;
		if (ammo == 0)
			removeGlobal();
		
		if (isKit()){
			switch (type) {
			case Medkit:
				SpecialForces.getInstance().sounds().medkit();
				break;
			case Energy:
				SpecialForces.getInstance().sounds().energy();
				break;

			default:
				break;
			}
			return;
		}
		SpecialForces.getInstance().sounds().ammo();
	}
	
	public void removeGlobal() {
		getWorld().objects().removeItem(this);
		super.remove();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isInView())
			batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	@Override
	public void getShot(Shoot shoot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		// TODO Auto-generated method stub
		
	}
	
	public static boolean isKit(ItemType type){
		return type.ordinal() > 7;
	}
	
	public static TextureRegion getTexture(Loader loader, ItemType type){
		if (isKit(type)){
			return loader.getItemIcon(type.ordinal()-8);
		}else{
			return loader.getWeaponIcon(type.ordinal());
		}
	}
}
