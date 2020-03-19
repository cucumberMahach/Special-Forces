package world;

import engine.SpecialForces;
import engine.utils.Maths;
import engine.utils.Zone;
import screens.ScreenType;
import stages.World;
import world.objects.player.Player;

public abstract class Manager {
	private World world;
	private boolean toBuild, toExit, completed, active;
	private Zone completeZone;
	private ScreenType exitScreen;

	private boolean creditsMode;

	private String mapName;
	
	public Manager(World world){
		this.world = world;
		completeZone = world.getCompleteZone();
	}
	
	public void startMap(String mapName){
		this.mapName = mapName;
		world.map().freeMap();
		world.map().load(mapName);
		creditsMode = getMapName().equals("credits");
		start();
	}
	
	public void startMapFromString(String map){
		mapName = "";
		world.map().freeMap();
		world.map().loadFromString(map);
		creditsMode = false;
		start();
	}
	
	private void start(){
		Maths.setWorld(world);
		world.map().build();
		completed = false;
		active = true;
		world.hud().setVisibleTouchController(true);
		Player player = world.getPlayer();
		if (player != null) {
			world.getOrtCam().position.x = player.getX();
			world.getOrtCam().position.y = player.getY();
		}
		mapStarted();
		mapBuilded();
	}
	
	public void update(float delta){
		if (!completed){
			if(completeZone.contains(world.getPlayer())){
				completed = true;
				stop();
				world.gui().showMapComplete();
				world.hud().setVisibleTouchController(false);
				mapCompleted();
			}
		}
		if (toBuild)
			toBuild();
		if (toExit)
			toExit();
	}
	
	protected abstract void mapBuilded();
	
	protected abstract void mapStarted();
	
	protected abstract void mapCompleted();
	
	public abstract void mapContinue();
	
	public void restart(){
		stop();
		toBuild = true;
	}
	
	public void stop(){
		world.objects().free();
		world.effects().free();
		active = false;
	}
	
	public void exit(ScreenType screen){
		stop();
		toExit = true;
		exitScreen = screen;
	}
	
	public void setPause(boolean pause){
		world.setPause(pause);
	}
	
	private void toExit(){
		toExit = false;
		SpecialForces.getInstance().screenManager().show(exitScreen);
	}
	
	private void toBuild(){
		toBuild = false;
		world.map().build();
		mapBuilded();
	}
	
	public boolean isMapCompleted(){
		return completed;
	}
	
	public boolean isMapActive(){
		return active;
	}

	public boolean isCreditsMode(){
		return creditsMode;
	}

	protected String getMapName(){
		return mapName;
	}
}
