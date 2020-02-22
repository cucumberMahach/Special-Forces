package world.gameplay;

public enum WeaponType {
	
	Beretta("case0", "bullet0"),
	Colt("case1", "bullet0"),
	MP5("case2", "bullet1"),
	AK47("case3", "bullet1"),
	Shotgun("case4", "bullet2"),
	Sniper("case5", "bullet3"),
	Bazooka(null, "bullet5"),
	Grenade(null, "bullet4");
	
	private String weaponCase, bullet;
	
	WeaponType(String weaponCase, String bullet){
		this.weaponCase = weaponCase;
		this.bullet = bullet;
	}

	public String getWeaponCase() {
		return weaponCase;
	}

	public String getBullet() {
		return bullet;
	}
}