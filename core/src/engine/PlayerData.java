package engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.Date;

import world.gameplay.Location;
import world.gameplay.Mission;
import world.gameplay.MissionStatus;
import world.gameplay.Soldier;
import world.gameplay.Weapon;

public class PlayerData {
	private final Json json;
	private final Preferences pref;
	
	private int goldenBars, dollars, experience, location, mission;
	private boolean soundsEnableStatus, musicEnableStatus;

	private Array<Soldier> team;
	private Weapon[] inventory;
	private Location[] locations;

	private long mapSenderTime;
	
	public PlayerData(){
		json = new Json();
		pref = Gdx.app.getPreferences(SpecialForces.TITLE);
	}
	
	public void load(Loader loader){
		loadPrefs(loader);
		prepare(loader);
		save();
	}
	
	@SuppressWarnings("unchecked")
	private void loadPrefs(Loader loader){
		String str;
		goldenBars = pref.getInteger("g", 0);
		dollars = pref.getInteger("d", 0);
		experience = pref.getInteger("e", 0);
		mapSenderTime = pref.getLong("mst", 0);
		
		str = pref.getString("t", null);
		if (str == null)
			team = new Array<Soldier>();
		else
			team = json.fromJson(Array.class, Soldier.class, str);
		
		str = pref.getString("i", null);
		if (str == null)
			inventory = loader.createWeapons(false);
		else
			inventory = json.fromJson(Weapon[].class, str);
		
		location = pref.getInteger("l", 0);
		mission = pref.getInteger("m", 0);
		locations = json.fromJson(Location[].class, loader.getFileHandle("locations.txt"));

		soundsEnableStatus = pref.getBoolean("enbl_s", true);
		musicEnableStatus = pref.getBoolean("enbl_m", true);

		compareProgress(location, mission, locations);
	}
	
	private void compareProgress(int location, int mission, Location[] locations){
		Mission[] missions;
		missions = locations[location].getMissions();
		if (mission < missions.length)
			missions[mission].setStatus(MissionStatus.AVAILABLE);
		for (int i = 0; i < location+1; i++){
			missions = locations[i].getMissions();
			for (int k = 0; k < missions.length; k++){
				if (i == location && k == mission)
					return;
				missions[k].setStatus(MissionStatus.COMPLETED);
			}
		}
	}
	
	private void prepare(Loader loader){
		for (int i = 0; i < inventory.length; i++){
			Weapon w = inventory[i];
			w.setConfig(loader.getWeaponConfig(w.getType()));
		}
	}
	
	public void save(){
		pref.putInteger("d", dollars);
		pref.putInteger("g", goldenBars);
		pref.putInteger("e", experience);
		pref.putString("t", json.toJson(team));
		pref.putString("i", json.toJson(inventory));
		pref.putInteger("l", location);
		pref.putInteger("m", mission);
		pref.flush();
	}

	public void setMapSenderTime(long value){
		mapSenderTime = value;
		pref.putLong("mst", value);
		pref.flush();
	}

	public void saveSoundsEnableStatus(boolean enabled){
		soundsEnableStatus = enabled;
		pref.putBoolean("enbl_s", enabled);
		pref.flush();
	}

	public void saveMusicEnableStatus(boolean enabled){
		musicEnableStatus = enabled;
		pref.putBoolean("enbl_m", enabled);
		pref.flush();
	}

	public void changeDollars(int amount) {
		this.dollars += amount;
	}
	
	public void changeGoldenBars(int amount) {
		this.goldenBars += amount;
	}
	
	public void changeExperience(int amount) {
		this.experience += amount;
	}
	
	public void changeProgress(int fromLocation, int fromMission){
		if (fromLocation < location || (fromLocation == location && fromMission < mission))
			return;
		locations[fromLocation].getMissions()[fromMission].setStatus(MissionStatus.COMPLETED);
		if (mission+1 == locations[location].getMissions().length){
			if (location+1 == locations.length){
				mission++;
				return;
			}
			location++;
			mission = 0;
		}else{
			mission++;
		}
		locations[location].getMissions()[mission].setStatus(MissionStatus.AVAILABLE);
	}

	public long getMapSenderTime(){
		return mapSenderTime;
	}
	
	public int getGoldenBars() {
		return goldenBars;
	}

	public int getDollars() {
		return dollars;
	}

	public int getExperience() {
		return experience;
	}

	public Array<Soldier> getTeam() {
		return team;
	}

	public Weapon[] getInventory() {
		return inventory;
	}
	
	public Location[] getLocations() {
		return locations;
	}

	public boolean getSoundsEnableStatus() {
		return soundsEnableStatus;
	}

	public boolean getMusicEnableStatus() {
		return musicEnableStatus;
	}
	
	public void copyToInventory(Weapon[] duplicate){
		Weapon.copyInventoryData(duplicate, inventory);
	}
}
