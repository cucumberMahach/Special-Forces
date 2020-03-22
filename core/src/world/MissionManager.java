package world;

import com.badlogic.gdx.utils.Array;

import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import engine.utils.Maths;
import screens.ScreenType;
import stages.World;
import world.gameplay.Location;
import world.gameplay.Mission;
import world.gameplay.MissionConfig;
import world.gameplay.MissionStatus;
import world.gameplay.Soldier;
import world.gameplay.Weapon;
import world.objects.Teammate;

public class MissionManager extends Manager{
	private Loader loader;
	private World world;
	private PlayerData playerData;
	private int mapIndex;
	
	private MissionConfig missionCfg;
	private Mission mission;
	private Location location;
	
	private Weapon[] missionInventory;
	private Weapon[] mapInventory;
	
	private int deaths;
	private float mapTime, missionTime;
	private boolean testMode;

	public MissionManager(World world, Loader loader) {
		super(world);
		this.world = world;
		this.loader = loader;
		missionInventory = loader.createWeapons(false);
		mapInventory = loader.createWeapons(false);
	}
	
	public void startMission(MissionConfig missionConfig, PlayerData playerData){
		this.missionCfg = missionConfig;
		this.playerData = playerData;
		location = playerData.getLocations()[missionConfig.locationIndex];
		mission = location.getMissions()[missionCfg.missionIndex];
		Weapon.copyInventoryData(playerData.getInventory(), missionInventory);
		Weapon.copyInventoryData(missionInventory, mapInventory);
		clean();
		testMode = false;
		nextMap();
		world.creditsMode(isCreditsMode());
		setPause(false);
	}
	
	public void startTestMap(String map, PlayerData playerData){
		this.playerData = playerData;
		String[] maps = {"test"};
		mission = new Mission("test", maps, 0, 0, 0, 0, MissionStatus.AVAILABLE);
		Weapon.copyInventoryData(loader.createWeapons(false), missionInventory);
		Weapon.copyInventoryData(missionInventory, mapInventory);
		clean();
		mapIndex++;
		testMode = true;
		world.creditsMode(false);
		startMapFromString(map);
		setPause(false);
	}
	
	private void spawnTeammates(Array<Soldier> teammates){
		if (isCreditsMode())
			return;
		final float px = world.getPlayerX();
		final float py = world.getPlayerY();
		Teammate[] mates = new Teammate[teammates.size];
		for (int i = 0; i < teammates.size; i++)
			mates[i] = world.spawner().spawnTeammate(px, py, teammates.get(i));
		world.spawner().spawn();
		for (int i = 0; i < mates.length; i++)
			mates[i].commands().teleportRandom(Maths.toMapX(px), Maths.toMapY(py));
	}
	
	private void nextMap(){
		mapIndex++;
		startMap(mission.getMaps()[mapIndex]);
	}
	
	public void update(float delta){
		super.update(delta);
		if (!isMapCompleted())
			mapTime += delta;
	}
	
	@Override
	public void restart() {
		Weapon.copyInventoryData(missionInventory, mapInventory);
		super.restart();
	}
	
	@Override
	protected void mapBuilded() {
		if (!testMode)
			spawnTeammates(playerData.getTeam());
	}

	@Override
	protected void mapStarted() {
		mapTime = 0;
	}

	@Override
	public void mapContinue() {
		Weapon.copyInventoryData(mapInventory, missionInventory);
		if (mapIndex+1 == mission.getMaps().length)
			missionComplete();
		else
			nextMap();
	}
	
	protected void mapCompleted(){
		missionTime += mapTime;
		SpecialForces.getInstance().sounds().end();
	}
	
	public void missionComplete(){
		if (testMode){
			exit();
			return;
		}
		playerData.changeProgress(missionCfg.locationIndex, missionCfg.missionIndex);
		playerData.copyToInventory(missionInventory);
		playerData.changeDollars(mission.getDollarsReward());
		playerData.changeExperience(mission.getExperienceReward());
		playerData.save();
		world.gui().showMissionComplete();
	}
	
	private void clean(){
		mapIndex = -1;
		missionTime = 0;
		deaths = 0;
	}
	
	public void exit() {
		world.setCompleteZone(-100,-100,1,1);
		if (testMode) {
			super.exit(ScreenType.EDITOR);
		}else {
			super.exit(ScreenType.GENERAL);
		}
		SpecialForces.getInstance().adverts().showAd();
	}
	
	public Mission getMission(){
		return mission;
	}
	
	public int getMapIndex(){
		return mapIndex;
	}
	
	public int getMapsCount(){
		return mission.getMaps().length;
	}
	
	public String getMapTime(){
		return formatTime((int) mapTime);
	}
	
	public String getMissionTime(){
		return formatTime((int) missionTime);
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public void incPlayerDie(){
		deaths++;
	}
	
	public Weapon[] getPlayerInventory(){
		return mapInventory;
	}
	
	private String formatTime(int time){
		int min = time / 60;
		String sec = String.valueOf(time - min * 60);
		return String.valueOf(min).concat(" : ").concat(sec.length() == 1 ? "0".concat(sec) : sec);
	}
}
