package stages;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import controller.ControllerType;
import editor.Commands;
import editor.Gui;
import editor.Manager;
import editor.Map;
import editor.MapSender;
import editor.Objects;
import editor.ToolType;
import editor.controller.AddObjectController;
import editor.controller.CameraController;
import editor.controller.EditObjectController;
import editor.controller.MapPaintController;
import editor.controller.TouchController;
import editor.controller.TouchZoomController;
import editor.objects.EditorObjectType;
import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Tiled;
import view.Debugger;
import world.gameplay.ItemType;
import world.gameplay.WeaponType;

public class Editor extends Stage implements Tiled{
	private Map map;
	private Objects objects;
	private Debugger debugger;
	private Gui gui;
	private Manager manager;
	private Commands commands;
	private MapSender mapSender;
	
	private ControllerType controllerType;
	private InputMultiplexer multiplexer;
	private CameraController camController;
	private EditObjectController editObjectController;
	private InputProcessor paintController, addObjectController;
	private TouchController touchController;
	private TouchZoomController touchZoomController;
	private GestureDetector zoomDetector;
	
	private ToolType tool;
	
	public Editor(Viewport viewport, Batch batch, Loader loader, InputMultiplexer multiplexer){
		super(viewport, batch);
		this.multiplexer = multiplexer;
		
		map = new Map(this, loader);
		objects = new Objects(loader);
		debugger = new Debugger();
		gui = new Gui(this, loader);
		manager = new Manager(this, loader);
		commands = new Commands(this);
		mapSender = new MapSender();
		
		addActor(map);
		addActor(objects);
		addActor(debugger);
		addActor(gui);
		
		controllerType = SpecialForces.getInstance().getControllerType();
		paintController = new MapPaintController(this);
		addObjectController = new AddObjectController(this);
		editObjectController = new EditObjectController(this);
		
		if (controllerType == ControllerType.TOUCH){
			touchController = new TouchController(this);
			touchZoomController = new TouchZoomController(this);
			zoomDetector = new GestureDetector(touchZoomController);
		}else{
			camController = new CameraController(this);
		}
			
		initControllers();
		selectTool(ToolType.EDIT_OBJECT);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		updateCamera();
		updateControllers();
	}
	
	public void paint(int x, int y){
		map.setTile(x, y, gui.mapTools().getIndex());
	}
	
	private void updateControllers(){
		if (tool == ToolType.EDIT_OBJECT)
			editObjectController.update();
	}
	
	public void selectTool(ToolType tool){
		this.tool = tool;
		multiplexer.clear();
		commands.setRemoveMode(false);
		initControllers();
		switch (tool) {
		case PAINT:
			multiplexer.addProcessor(paintController);
			break;
		case ADD_OBJECT:
			multiplexer.addProcessor(addObjectController);
			break;
		case EDIT_OBJECT:
			multiplexer.addProcessor(editObjectController);
			break;
		case CAMERA:
			multiplexer.addProcessor(zoomDetector);
			multiplexer.addProcessor(touchController);

			break;
		default:
			break;
		}
	}

	public TouchZoomController getTouchZoomController() {
		return touchZoomController;
	}

	private void initControllers(){
		multiplexer.addProcessor(this);
		if (controllerType == ControllerType.KEYS)
			multiplexer.addProcessor(camController);
	}
	
	public void clearControllers(){
		multiplexer.clear();
		initControllers();
		commands.setRemoveMode(false);
		tool = ToolType.NONE;
	}
	
	public void addObject(float x, float y){
		EditorObjectType type = gui.objectTools().getObjectType();
		int index = gui.objectTools().getIndex();
		switch (type) {
		case DECORATION:
			objects.addDecoration(x, y, "bed", 30);
			break;
		case BOX:
			objects.addBox(x, y);
			break;
		case BARREL:
			objects.addBarrel(x, y, index-1);
			break;
		case BOT:
			objects.addBot(x, y, 0, Style.VISIBLE_DISTANCE, 65, WeaponType.Beretta, 64);
			break;
		case ITEM:
			objects.addItem(x, y, ItemType.values()[index-5], -1);
			break;
		case PLAYER:
			objects.addPlayer(x, y);
			break;
		case ZONE:
			objects.addZone(x, y, Style.TILE_SIZE, Style.TILE_SIZE);
			break;
		}
		commands.selectObject(objects.getLast());
	}
	
	public void moveBy(float dx, float dy){
		getCamera().position.add(dx * 5, dy * 5, 0);
	}
	
	public void moveCameraAndroid(float x, float y){
		final float zoom = getOrtCam().zoom;
		getCamera().position.add(x * zoom, y * zoom, 0);
	}

	private void updateCamera(){
		if (tool == ToolType.CAMERA)
			touchZoomController.update();
		if (controllerType == ControllerType.KEYS)
			camController.update();
		gui.setPosition(getCamera().position.x - (SpecialForces.WIDTH/2)*getOrtCam().zoom, getCamera().position.y - (SpecialForces.HEIGHT/2)*getOrtCam().zoom);
		gui.setScale(getOrtCam().zoom);
	}
	
	public void zoomBy(float amount){
		if (getOrtCam().zoom + amount >= 0.1f)
			getOrtCam().zoom += amount;
		else
			getOrtCam().zoom = 0.1f;
	}

	public MapSender mapSender(){
		return mapSender;
	}

	public Commands commands(){
		return commands;
	}
	
	public Gui gui(){
		return gui;
	}
	
	public Objects objects(){
		return objects;
	}
	
	public Debugger debugger(){
		return debugger;
	}
	
	public Map map(){
		return map;
	}
	
	public Manager manager(){
		return manager;
	}

	@Override
	public int getMapWidth() {
		return map.getMapWidth();
	}

	@Override
	public int getMapHeight() {
		return map.getMapHeight();
	}

	@Override
	public OrthographicCamera getOrtCam() {
		return ((OrthographicCamera) getCamera());
	}

	@Override
	public float getPlayerX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPlayerY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
