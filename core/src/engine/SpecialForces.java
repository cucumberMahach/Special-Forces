package engine;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import controller.ControllerType;
import engine.utils.advertise.AdHandler;
import screens.ScreenType;

public class SpecialForces extends Game {
	
	private static SpecialForces instance = new SpecialForces();
	
	public static final String 						TITLE 					= "Special forces";
	
	public static final int 						WIDTH 					= 1280;
	public static final int 						HEIGHT 					= 720;
	
	public static final boolean 					FULLSCREEN 				= false;
	public static final boolean 					RESIZABLE 				= false;
	
	public static final int 						FPS 					= 60;
	public static final boolean 					VSYNC 					= true;
	
	public static final int 						SAMPLES 				= 16;
	
	private ShapeRenderer shaper;
	private float ppuX, ppuY;
	private ScreenManager scrMng;
	private ControllerType controllerType;
	private Sounds sounds;
	private Loader loader;
	private PlayerData playerData;
	private Adverts adverts;
	private Cursors cursors;
	
	private SpecialForces() {}

	@Override
	public void create() {
		controllerType = isAndroid() ? ControllerType.TOUCH : ControllerType.KEYS;
		SpriteBatch batch = new SpriteBatch();
		
		shaper = new ShapeRenderer();
		loader = new Loader();
		scrMng = new ScreenManager(loader, batch);
		playerData = new PlayerData();
		sounds = new Sounds(loader);
		cursors = new Cursors(loader);
		adverts = new Adverts();
		
		loader.loadPrev();
		screenManager().loadPrev();
		
		screenManager().show(ScreenType.LOAD);

		Gdx.gl.glClearColor(0, 0, 0, 1);
	}
	
	public void loadingDone(){
		loader.load();
		playerData.load(loader);
		sounds.loadSettings();
		screenManager().load();
	}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		ppuX = (float) width / WIDTH;
		ppuY = (float) height / HEIGHT;
	}

	public PlayerData playerData(){
		return playerData;
	}
	
	public Sounds sounds(){
		return sounds;
	}
	
	public ControllerType getControllerType(){
		return controllerType;
	}
	
	public boolean isAndroid(){
		return Gdx.app.getType() == ApplicationType.Android;
	}
	
	public ScreenManager screenManager(){
		return scrMng;
	}
	
	public float getPpuX(){
		return ppuX;
	}
	
	public float getPpuY(){
		return ppuY;
	}
	
	public ShapeRenderer getShaper(){
		return shaper;
	}

	public void setAdHandler(AdHandler adHandler){
		adverts.setAdHandler(adHandler);
	}

	public Adverts adverts(){
		return adverts;
	}

	public Cursors cursors(){
		return cursors;
	}
	
	public static SpecialForces getInstance(){
		return instance;
	}
	
}
