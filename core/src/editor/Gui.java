package editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import controller.ControllerType;
import editor.gui.FileSelectAction;
import editor.gui.MapTools;
import editor.gui.ObjectTools;
import editor.gui.WindowFileSelect;
import editor.gui.WindowHelp;
import editor.gui.WindowMessage;
import editor.gui.WindowNewMap;
import editor.gui.WindowProperties;
import editor.objects.EditorObject;
import engine.Loader;
import engine.SpecialForces;
import engine.utils.Maths;
import engine.utils.Point;
import screens.ScreenType;
import stages.Editor;
import view.Font;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;

public class Gui extends Group{
	
	private Label infoLab;
	private Button saveBtn, exitBtn, moveBtn, cameraBtn, roundBtn, loadBtn, newBtn, deleteBtn, testBtn, sendBtn, helpBtn;
	private MapTools mapTools;
	private ObjectTools objectTools;
	private WindowProperties windowProperties;
	private ControllerType controllerType;
	private WindowFileSelect fileSelect;
	private WindowMessage message;
	private WindowNewMap newMap;
	private WindowHelp helpWindow;

	private Editor editor;
	private Loader loader;
	
	private Point point;
	
	public Gui(Editor editor, Loader loader){
		this.editor = editor;
		this.loader = loader;
		controllerType = SpecialForces.getInstance().getControllerType();
		point = new Point();
		infoLab = new Label(loader, "debug label", Font.SMALL, Align.left, 200, 100, 0, 0);
		infoLab.setPosition(10, SpecialForces.HEIGHT - 10 - infoLab.getHeight());
		exitBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		exitBtn.setPosition(1198,641);
		exitBtn.addListener(new MenuEvent());
		mapTools = new MapTools(editor, loader);
		mapTools.setPosition(0, 100);
		objectTools = new ObjectTools(editor, loader);
		objectTools.setPosition(250, 0);
		moveBtn = new Button(loader, ButtonType.BUY, "move", null);
		moveBtn.setPosition(200, SpecialForces.HEIGHT - moveBtn.getHeight() - 10);
		moveBtn.addListener(new EditToolEvent(editor, ToolType.EDIT_OBJECT));
		if (controllerType == ControllerType.TOUCH){
			deleteBtn = new Button(loader, ButtonType.BUY, "remove", null);
			deleteBtn.setPosition(moveBtn.getX(), moveBtn.getY() - deleteBtn.getHeight() - 10);
			deleteBtn.addListener(new RemoveModeEvent(editor));
			addActor(deleteBtn);
			roundBtn = new Button(loader, ButtonType.BUY, "round mode", null);
			roundBtn.setPosition(moveBtn.getRight() + 10, moveBtn.getY());
			roundBtn.addListener(new RoundEvent(editor));
			addActor(roundBtn);
			cameraBtn = new Button(loader, ButtonType.BUY, "camera", null);
			cameraBtn.setPosition(deleteBtn.getRight() + 10, deleteBtn.getY());
			cameraBtn.addListener(new EditToolEvent(editor, ToolType.CAMERA));
			addActor(cameraBtn);
		}
		windowProperties = new WindowProperties(editor, loader);
		windowProperties.setPosition(SpecialForces.WIDTH - windowProperties.getWidth() - 10, 10);
		fileSelect = new WindowFileSelect(editor, loader);
		fileSelect.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		saveBtn = new Button(loader, ButtonType.DIALOG, "save", null);
		saveBtn.setPosition(exitBtn.getX() - saveBtn.getWidth() - 10, SpecialForces.HEIGHT - saveBtn.getHeight() - 10);
		saveBtn.addListener(new FileSelectEvent(fileSelect, FileSelectAction.SAVE));
		loadBtn = new Button(loader, ButtonType.DIALOG, "load", null);
		loadBtn.setPosition(saveBtn.getX(), saveBtn.getY() - loadBtn.getHeight() - 10);
		loadBtn.addListener(new FileSelectEvent(fileSelect, FileSelectAction.OPEN));

		newMap = new WindowNewMap(editor, loader);
		newMap.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		newBtn = new Button(loader, ButtonType.DIALOG, "new", null);
		newBtn.setPosition(loadBtn.getX(), loadBtn.getY() - newBtn.getHeight() - 10);
		newBtn.addListener(new NewMapEvent(newMap));
		testBtn = new Button(loader, ButtonType.DIALOG, "test map", null);
		testBtn.setPosition(newBtn.getX(), newBtn.getY() - testBtn.getHeight() - 10);
		testBtn.addListener(new TestMapEvent(editor));

		//sendBtn = new Button(loader, ButtonType.MANAGER, "send map", null);
		//sendBtn.setPosition(SpecialForces.WIDTH - sendBtn.getWidth() - 10, 10);
		//sendBtn.addListener(new SendMapEvent(editor));

		helpWindow = new WindowHelp(editor, loader);
		helpWindow.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		helpBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.HELP);
		helpBtn.setPosition(exitBtn.getX(), exitBtn.getY() - helpBtn.getHeight() - 10);
		helpBtn.addListener(new HelpEvent(helpWindow));

		//helpBtn.setVisible(!SpecialForces.getInstance().isAndroid());
		
		addActor(infoLab);
		addActor(mapTools);
		addActor(objectTools);
		addActor(moveBtn);
		addActor(loadBtn);
		addActor(saveBtn);
		addActor(exitBtn);
		addActor(newBtn);
		addActor(testBtn);
		//addActor(sendBtn);
		addActor(helpBtn);
		addActor(windowProperties);
		addActor(fileSelect);
		addActor(helpWindow);
		addActor(newMap);
	}
	
	public void showMessage(String text){
		message = new WindowMessage(editor, loader);
		message.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		addActor(message);
		message.show(text);
	}

	public void updateProperties(EditorObject object){
		windowProperties.update(object);
	}
	
	public ObjectTools objectTools(){
		return objectTools;
	}
	
	public MapTools mapTools(){
		return mapTools;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		updateInfo();
	}
	
	private void updateInfo(){

		Maths.mouseToWorldYTop(Gdx.input.getX(), Gdx.input.getY(), point);

		int mapX = Maths.toMapCoords(point.x), mapY = Maths.toMapCoords(point.y);
		infoLab.setCaption(String.format("x: %f\ny: %f\nmapX: %d (%d)\nmapY: %d (%d)", point.x, point.y, mapX, mapX*32, mapY, mapY*32));
	}
}

class HelpEvent extends ClickListener{
	private WindowHelp parent;

	public HelpEvent(WindowHelp parent) {
		this.parent = parent;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		parent.show();
	}
}

class SendMapEvent extends ClickListener{
	private Editor editor;

	public SendMapEvent(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		editor.manager().sendMap();
	}
}

class RoundEvent extends ClickListener{
private Editor editor;
	
	public RoundEvent(Editor editor) {
		this.editor = editor;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		editor.commands().toggleRoundMode();
	}
}

class TestMapEvent extends ClickListener{
	private Editor editor;
	
	public TestMapEvent(Editor editor) {
		this.editor = editor;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		editor.manager().testMap();
	}
}

class RemoveModeEvent extends ClickListener{
	private Editor editor;
	
	public RemoveModeEvent(Editor editor) {
		this.editor = editor;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		editor.commands().setRemoveMode(true);
	}
}

class NewMapEvent extends ClickListener{
	private WindowNewMap window;
	
	NewMapEvent(WindowNewMap window){
		this.window = window;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		window.show();
	}
}

class MenuEvent extends ClickListener{
	@Override
	public void clicked(InputEvent event, float x, float y) {
		SpecialForces.getInstance().screenManager().show(ScreenType.MENU);
	}
}

class EditToolEvent extends ClickListener{
	private Editor editor;
	private ToolType tool;
	
	public EditToolEvent(Editor editor, ToolType tool) {
		this.editor = editor;
		this.tool = tool;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		editor.selectTool(tool);
	}
}

class FileSelectEvent extends ClickListener{
	private FileSelectAction action;
	private WindowFileSelect window;
	
	public FileSelectEvent(WindowFileSelect window, FileSelectAction action) {
		this.action = action;
		this.window = window;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		window.show(action);
	}
}