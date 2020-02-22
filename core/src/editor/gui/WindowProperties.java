package editor.gui;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import editor.gui.properties.BotProperties;
import editor.gui.properties.DecorationProperties;
import editor.gui.properties.ItemProperties;
import editor.objects.EditorBot;
import editor.objects.EditorDecoration;
import editor.objects.EditorItem;
import editor.objects.EditorObject;
import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import world.gameplay.ItemType;

public class WindowProperties extends Group{
	private Image background;
	private Label captionLab;
	
	private BotProperties botPr;
	private ItemProperties itemPr;
	private DecorationProperties decorationPr;
	
	public WindowProperties(Editor editor, Loader loader){
		background = new Image(loader.getBackground("window"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		captionLab = new Label(loader, "object properties", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		captionLab.setPosition(10, getHeight() - captionLab.getHeight() - 10);
		
		botPr = new BotProperties(editor, loader);
		botPr.setPosition(10, 10);

		itemPr = new ItemProperties(editor, loader);
		itemPr.setPosition(10, 10);
		
		decorationPr = new DecorationProperties(editor, loader);
		decorationPr.setPosition(10, 10);
		
		addActor(background);
		addActor(captionLab);
		addActor(botPr);
		addActor(itemPr);
		addActor(decorationPr);
		
		addListener(new ClickListener());
		
		setTouchable(Touchable.childrenOnly);
		clearProperties();
		hide();
	}
	
	public void update(EditorObject object){
		if (object == null){
			hide();
			return;
		}
		switch (object.getObjectType()) {
		case DECORATION:
			EditorDecoration dec = (EditorDecoration) object;
			decorationPr.setNameValue(dec.getName());
			decorationPr.setHealthValue(dec.getHealth());
			show(decorationPr);
			break;
		case BOT:
			EditorBot bot = (EditorBot) object;
			botPr.setHealthValue(bot.getHealth());
			botPr.setWeaponValue(bot.getWeaponType().ordinal());
			botPr.setAmmoValue(bot.getAmmo());
			show(botPr);
			break;
		case ITEM:
			
			EditorItem item = (EditorItem) object;
			if (item.getType() == ItemType.Medkit || item.getType() == ItemType.Energy){
				hide();
				break;
			}
			itemPr.setAmmoValue(item.getAmmo());
			show(itemPr);
			break;

		default:
			hide();
			break;
		}
	}
	
	public void show(Group properties){
		clearProperties();
		setVisible(true);
		properties.setVisible(true);
	}
	
	public void hide(){
		setVisible(false);
	}
	
	private void clearProperties(){
		botPr.setVisible(false);
		itemPr.setVisible(false);
		decorationPr.setVisible(false);
	}
}
