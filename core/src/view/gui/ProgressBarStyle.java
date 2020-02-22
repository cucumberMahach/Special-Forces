package view.gui;

public enum ProgressBarStyle {
	
	LOAD("bar_load", 10, 12),
	EXPERIENCE("bar_experience", 10, 9),
	ENERGY("bar_energy", 7, 9),
	CONTRACT_STAT("bar_contractStat", 7, 8),
	WEAPON_LEVEL("bar_weaponLevel", 8, 9);
	
	private final String name;
	private final float offx, offy;
	
	ProgressBarStyle(String name, float offx, float offy){
		this.name = name;
		this.offx = offx;
		this.offy = offy;
	}
	
	public String getName(){
		return name;
	}
	
	public float getOffx(){
		return offx;
	}
	
	public float getOffy(){
		return offy;
	}
}

