package world.objects;

import engine.Loader;
import world.gameplay.Soldier;
import world.gameplay.WeaponType;
import world.objects.bot.Bot;
import world.objects.bot.AI.behavior.TeammateBehavior;

public class Teammate extends Bot{

	public Teammate(Loader loader, float x, float y, Soldier soldier) {
		super(loader, "skin7", true, x, y);
		
		setType(ObjectType.TEAMMATE);
		
		WeaponType weaponType = soldier.getWeapon();
		giveAmmo(weaponType, loader.getWeaponConfig(weaponType).getMaxAmmo());
		setInfinityAmmo(weaponType, true);
		
		setToCollide(false);
		ai().addBehavior(new TeammateBehavior(soldier));
	}

}
