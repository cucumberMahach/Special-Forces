package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.CursorType;
import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import stages.World;
import world.gameplay.MissionConfig;

public class GameScreen implements Screen{
	
	private World world;
	private InputMultiplexer multiplexer;
	private Loader loader;
	
	private MissionConfig missionCfg;
	private PlayerData playerData;
	private GameType gameType;
	private String map;
	
	public GameScreen(Loader loader, SpriteBatch batch) {
		this.loader = loader;
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		multiplexer = new InputMultiplexer();
		
		world = new World(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch, loader, multiplexer);
	}
	
	public void setMission(MissionConfig missionCfg, PlayerData playerData){
		gameType = GameType.MISSION;
		this.missionCfg = missionCfg;
		this.playerData = playerData;
	}
	
	public void setMap(String map, PlayerData playerData){
		gameType = GameType.TEST;
		this.map = map;
		this.playerData = playerData;
	}

	@Override
	public void show() {
		SpecialForces.getInstance().cursors().setCursor(CursorType.AIM);
		Gdx.input.setInputProcessor(multiplexer);
		if (gameType == GameType.MISSION){
			world.manager().startMission(missionCfg, playerData);
		}else{
			world.manager().startTestMap(map, playerData);
		}
	}

	@Override
	public void render(float delta) {
		world.act(delta);
		world.draw();
	}

	@Override
	public void resize(int width, int height) {
		world.getViewport().setScreenSize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		SpecialForces.getInstance().cursors().setCursor(CursorType.ARROW);
		Gdx.input.setInputProcessor(null);
		SpecialForces.getInstance().sounds().stopAllMusic();
	}

	@Override
	public void dispose() {
		world.dispose();
	}

}

enum GameType{
	MISSION, TEST;
}
