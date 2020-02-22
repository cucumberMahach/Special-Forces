package editor;

import java.util.Iterator;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import editor.controller.ResizeController;
import editor.objects.EditorBarrel;
import editor.objects.EditorBot;
import editor.objects.EditorDecoration;
import editor.objects.EditorItem;
import editor.objects.EditorObject;
import editor.objects.EditorObjectType;
import editor.objects.EditorPlayer;
import editor.objects.EditorZone;
import engine.Loader;
import world.gameplay.ItemType;
import world.gameplay.WeaponType;

public class Objects extends Group{
	private Array<EditorObject> objects;
	private Loader loader;
	private int playerCounts;
	private int zoneCounts;
	
	public Objects(Loader loader){
		this.loader = loader;
		objects = new Array<EditorObject>();
	}
	
	public EditorObject getLast(){
		return objects.peek();
	}
	
	public void removeObject(EditorObject obj){
		objects.removeValue(obj, true);
		if (obj.getObjectType() == EditorObjectType.PLAYER)
			playerCounts--;
		if (obj.getObjectType() == EditorObjectType.ZONE)
			zoneCounts--;
	}
	
	public void addBox(float x, float y){
		addObject(new EditorObject(loader.getObject("box"), EditorObjectType.BOX, x, y));
	}
	
	public void addBarrel(float x, float y, int type){
		addObject(new EditorBarrel(loader, x, y, type));
	}
	
	public void addBot(float x, float y, float rotation, float visibleDistance, int health, WeaponType type, int ammo){
		addObject(new EditorBot(loader, x, y, rotation, visibleDistance, health, type, ammo));
	}
	
	public void addItem(float x, float y, ItemType type, int ammo){
		addObject(new EditorItem(loader, x, y, type, ammo));
	}
	
	public void addPlayer(float x, float y){
		playerCounts++;
		addObject(new EditorPlayer(loader, x, y));
	}
	
	public void addZone(float x, float y, float width, float height){
		EditorObject obj;
		zoneCounts++;
		obj = new EditorZone(x, y, width, height);
		obj.addListener(new ResizeController(obj));
		addObject(obj);
	}
	
	public void addDecoration(float x, float y, String name, int health){
		addObject(new EditorDecoration(loader, name, x, y, health));
	}
	
	public void generate(StringBuilder sb){
		Iterator<EditorObject> iter = objects.iterator();
		EditorObject obj;
		while(iter.hasNext()){
			obj = iter.next();
			switch (obj.getObjectType()) {
			case DECORATION:
				sb.append("decoration,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				sb.append(",");
				sb.append(obj.getName());
				sb.append(",");
				sb.append(((EditorDecoration) obj).getHealth());
				break;
			case BOX:
				sb.append("box,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				break;
			case BARREL:
				sb.append("barrel,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				sb.append(",");
				sb.append(((EditorBarrel) obj).getType());
				break;
			case BOT:
				sb.append("bot,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				sb.append(",");
				sb.append(((EditorBot) obj).getRotation());
				sb.append(",");
				sb.append(((EditorBot) obj).getVisibleDistance());
				sb.append(",");
				sb.append(((EditorBot) obj).getHealth());
				sb.append(",");
				sb.append(((EditorBot) obj).getWeaponType());
				sb.append(",");
				sb.append(((EditorBot) obj).getAmmo());
				break;
			case ITEM:
				sb.append("item,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				sb.append(",");
				sb.append(((EditorItem) obj).getType());
				sb.append(",");
				sb.append(((EditorItem) obj).getAmmo());
				break;
			case PLAYER:
				sb.append("player,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				break;
			case ZONE:
				sb.append("nextlevel,");
				sb.append(obj.getX());
				sb.append(",");
				sb.append(obj.getY());
				sb.append(",");
				sb.append(obj.getWidth());
				sb.append(",");
				sb.append(obj.getHeight());
				break;
			default:
				break;
			}
			sb.append("\n");
		}
	}
	
	public void addObject(EditorObject obj){
		obj.setIndex(objects.size);
		objects.add(obj);
		addActor(obj);
	}
	
	public void free(){
		Iterator<EditorObject> iter = objects.iterator();
		while(iter.hasNext())
			iter.next().remove();
		objects.clear();
		playerCounts = 0;
		zoneCounts = 0;
	}
	
	public boolean isPlayerCreated(){
		return playerCounts > 0;
	}
	
	public boolean isPlayerSingular(){
		return playerCounts == 1;
	}

	public boolean isZoneCreated(){
		return zoneCounts > 0;
	}

	public boolean isZoneSingular(){
		return zoneCounts == 1;
	}
}
