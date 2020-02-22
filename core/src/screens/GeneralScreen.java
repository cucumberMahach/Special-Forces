package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import stages.BackgroundStage;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import view.gui.general.ExperienceBar;
import view.gui.general.Help;
import view.gui.general.Inventory;
import view.gui.general.Missions;
import view.gui.general.Stats;
import view.gui.general.Team;
import world.gameplay.MissionConfig;

public class GeneralScreen implements Screen{
	
	private PlayerData playerData;
	private Loader loader;
	private Stage stage, bgrStage;
	private Stats stats;
	private ExperienceBar experienceBar;
	private Button musicBtn, soundsBtn, helpBtn, exitBtn, teamBtn, inventoryBtn, missionsBtn;
	private Team team;
	private Inventory inventory;
	private Missions missions;
	private Help help;
	private MissionConfig missionCfg;
	
	public GeneralScreen(Loader loader, SpriteBatch batch, BackgroundStage bgrStage) {
		this.bgrStage = bgrStage;
		this.loader = loader;
		playerData = SpecialForces.getInstance().playerData();
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		stage = new Stage(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch);
		stats = new Stats(loader, 20, 570);
		experienceBar = new ExperienceBar(loader, 340, 650);
		musicBtn = new Button(loader, ButtonType.SMALL_YELLOW, "", ButtonIcon.MUSIC);
		musicBtn.setPosition(966, 641);
		soundsBtn = new Button(loader, ButtonType.SMALL_YELLOW, "", ButtonIcon.SOUNDS);
		soundsBtn.setPosition(musicBtn.getRight() + 5, musicBtn.getY());
		helpBtn = new Button(loader, ButtonType.SMALL_YELLOW, "", ButtonIcon.HELP);
		helpBtn.setPosition(soundsBtn.getRight() + 5, musicBtn.getY());
		exitBtn = new Button(loader, ButtonType.SMALL_YELLOW, "", ButtonIcon.CLOSE);
		exitBtn.setPosition(helpBtn.getRight() + 15, musicBtn.getY());
		exitBtn.addListener(new BackEvent(this));
		teamBtn = new Button(loader, ButtonType.MANAGER, "team", ButtonIcon.TEAM);
		teamBtn.setPosition(12, 8);
		inventoryBtn = new Button(loader, ButtonType.MANAGER, "inventory", ButtonIcon.INVENTORY);
		inventoryBtn.setPosition(teamBtn.getRight() + 15, teamBtn.getY());
		missionsBtn = new Button(loader, ButtonType.MANAGER, "missions", ButtonIcon.MISSIONS);
		missionsBtn.setPosition(SpecialForces.WIDTH - missionsBtn.getWidth() - 12, teamBtn.getY());
		
		team = new Team(loader, playerData.getTeam());
		inventory = new Inventory(loader, playerData.getInventory());
		missions = new Missions(loader, bgrStage, playerData.getLocations(), this);
		help = new Help(loader);
		
		teamBtn.addListener(new ShowLayerEvent(this, Layer.TEAM));
		inventoryBtn.addListener(new ShowLayerEvent(this, Layer.INVENTORY));
		missionsBtn.addListener(new ShowLayerEvent(this, Layer.MISSIONS));
		helpBtn.addListener(new ShowLayerEvent(this, Layer.HELP));
		soundsBtn.addListener(new ToggleSoundEvent(soundsBtn, loader));
		musicBtn.addListener(new ToggleMusicEvent(musicBtn, loader));
		
		missionCfg = new MissionConfig();
		
		stage.addActor(stats);
		stage.addActor(experienceBar);
		stage.addActor(musicBtn);
		stage.addActor(soundsBtn);
		stage.addActor(helpBtn);
		stage.addActor(exitBtn);
		stage.addActor(teamBtn);
		stage.addActor(inventoryBtn);
		stage.addActor(missionsBtn);
		stage.addActor(team);
		stage.addActor(inventory);
		stage.addActor(missions);
		stage.addActor(help);
		stage.addActor(stats);
		hideLayers();
	}
	
	public void startMission(int locationIndex, int missionIndex){
		missionCfg.locationIndex = locationIndex;
		missionCfg.missionIndex = missionIndex;
		hideLayers();
		SpecialForces.getInstance().screenManager().startMission(missionCfg, playerData);
	}
	
	public void updateData(){
		stats.setDollars(playerData.getDollars());
		experienceBar.setExperience(playerData.getExperience());
	}
	
	private void updateGui(){
		musicBtn.setIcon(SpecialForces.getInstance().sounds().isMusicEnabled() ? loader.getIcon("black_music") : loader.getIcon("black_music_off"));
		soundsBtn.setIcon(SpecialForces.getInstance().sounds().isSoundsEnabled() ? loader.getIcon("black_sounds") : loader.getIcon("black_sounds_off"));
	}
	
	public void hideLayers(){
		team.setVisible(false);
		inventory.setVisible(false);
		missions.setVisible(false);
		help.setVisible(false);
	}
	
	public void showLayer(Layer layer){
		hideLayers();
		switch (layer) {
		case TEAM:
			team.setVisible(true);
			break;
		case INVENTORY:
			inventory.setVisible(true);
			break;
		case MISSIONS:
			missions.setVisible(true);
			break;
		case HELP:
			help.setVisible(true);
			break;

		default:
			break;
		}
	}

	@Override
	public void show() {
		updateGui();
		updateData();
		showLayer(Layer.TEAM);
		Gdx.input.setInputProcessor(stage);
		SpecialForces.getInstance().sounds().playMusic("menu", true);
	}

	@Override
	public void render(float delta) {
		updateData();
		bgrStage.act(delta);
		bgrStage.draw();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	enum Layer{
		TEAM, INVENTORY, MISSIONS, HELP
	}
	
	class ToggleMusicEvent extends ClickListener{
		
		private Button btn;
		private Loader loader;
		
		public ToggleMusicEvent(Button btn, Loader loader){
			this.btn = btn;
			this.loader = loader;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().sounds().toggleMusic();
			btn.setIcon(SpecialForces.getInstance().sounds().isMusicEnabled() ? loader.getIcon("black_music") : loader.getIcon("black_music_off"));
		}
	}
	
	class ToggleSoundEvent extends ClickListener{
		
		private Button btn;
		private Loader loader;
		
		public ToggleSoundEvent(Button btn, Loader loader){
			this.btn = btn;
			this.loader = loader;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().sounds().toggleSounds();
			btn.setIcon(SpecialForces.getInstance().sounds().isSoundsEnabled() ? loader.getIcon("black_sounds") : loader.getIcon("black_sounds_off"));
		}
	}
	
	class ShowLayerEvent extends ClickListener{
		private GeneralScreen screen;
		private Layer layer;
		
		public ShowLayerEvent(GeneralScreen screen, Layer layer) {
			this.screen = screen;
			this.layer = layer;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			screen.showLayer(layer);
		}
	}
	
	class BackEvent extends ClickListener{
		private GeneralScreen screen;
		
		public BackEvent(GeneralScreen screen) {
			this.screen = screen;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			screen.hideLayers();
			SpecialForces.getInstance().screenManager().show(ScreenType.MENU);
		}
	}
}

