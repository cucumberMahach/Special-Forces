package editor.objects;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import world.gameplay.ItemType;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;
import world.objects.Item;

public class EditorItem extends EditorObject{
	private ItemType type;
	private int ammo;
	
	public EditorItem(Loader loader, float x, float y, ItemType type, int ammo){
		super(Item.getTexture(loader, type), EditorObjectType.ITEM, x, y);
		this.ammo = ammo;
		this.type = type;
		
		if (Item.isKit(type)) {
			setSize(getWidth() * 0.5f, getHeight() * 0.5f);
		}else {
			setSize(getWidth() * 0.4f, getHeight() * 0.4f);
			Weapon config = loader.getWeaponConfig(WeaponType.values()[type.ordinal()]);
			if (ammo == -1)
				setAmmo(config.getMaxAmmo() / 2);
		}
		setOrigin(Align.center);
	}
	
	public ItemType getType(){
		return type;
	}
	
	public int getAmmo(){
		return ammo;
	}

	public void setAmmo(int value){
		ammo = value;
	}
}
