package world.gameplay;

import engine.Loader;

public class Soldier {
	private String name;
	private int face;
	private int level, cost;
	private float accuracy, reaction, brave;
	private WeaponType weapon;
	
	public Soldier(Loader loader, int level, float f, float g, float h, WeaponType weapon){
		this.name = loader.getRandomName();
		this.level = level;
		this.accuracy = f;
		this.reaction = g;
		this.brave = h;
		this.weapon = weapon;
		this.cost = calcCost();
		this.face = calcTexture(loader);
	}
	
	public Soldier(){}
	
	private int calcTexture(Loader loader){
		final int max = loader.getFacesCount();
		int index = 0;
		if (level > 20)
			index = max-1;
		else
			index = Math.round(max / 20f * level)-1;
		return index;
	}
	
	private int calcCost(){
		return (int) (level*300 + accuracy*300 + reaction*300 + brave*300 + weapon.ordinal()*600);
	}

	public String getName() {
		return name;
	}

	public int getFace() {
		return face;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public float getReaction() {
		return reaction;
	}

	public float getBrave() {
		return brave;
	}

	public int getLevel() {
		return level;
	}

	public int getCost() {
		return cost;
	}

	public WeaponType getWeapon() {
		return weapon;
	}
	
}
