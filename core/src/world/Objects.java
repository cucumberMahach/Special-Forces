package world;

import java.util.Iterator;
import java.util.Stack;

import com.badlogic.gdx.utils.Array;

import engine.Style;
import engine.utils.ComparatorToPlayer;
import stages.World;
import view.Actor;
import view.Group;
import world.objects.Item;
import world.objects.MapObject;
import world.objects.ObjectType;

public class Objects extends Group{
	
	private World world;
	private Group grounded;
	
	private Stack<MapObject> toSpawn;
	private Array<MapObject> mapObjects;
	private Array<Item> items;
	private ComparatorToPlayer comparator;
	
	private boolean toFree;
	
	private float sortTime;
	
	public Objects(World world){
		this.world = world;
		toSpawn = new Stack<MapObject>();
		mapObjects = new Array<MapObject>();
		items = new Array<Item>();
		grounded = new Group();
		comparator = new ComparatorToPlayer(world);
		addActor(grounded);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		sortTime += delta;
		if (sortTime == Style.SORT_DELAY)
			sort();
		if (!toFree)
			return;
		toFree();
	}
	
	private void toFree(){
		toFree = false;
		Iterator<MapObject> iter = mapObjects.iterator();
		Iterator<Item> iter2 = items.iterator();
		while(iter.hasNext())
			iter.next().remove();
		mapObjects.clear();
		while(iter2.hasNext())
			iter2.next().remove();
		items.clear();
		toSpawn.clear();
	}
	
	public void free(){
		toFree = true;
	}
	
	public void addItem(Item item){
		grounded.addActor(item);
		items.add(item);
	}
	
	public void addGrounded(MapObject obj){
		grounded.addActor(obj);
	}
	
	public void addGrounded(Actor actor){
		grounded.addActor(actor);
	}
	
	public void add(MapObject obj){
		toSpawn.add(obj);
	}
	
	public void sort(){
		sortTime = 0;
		mapObjects.sort(comparator);
	}
	
	public void spawn(){
		MapObject obj;
		while(!toSpawn.isEmpty()){
			obj = toSpawn.pop();
			mapObjects.add(obj);
			if (!(obj.getType() == ObjectType.PLAYER && world.manager().isCreditsMode()))
				addActor(obj);
		}
		sort();
		world.map().refreshItems();
	}
	
	public void removeItem(Item item){
		items.removeValue(item, false);
		world.map().refreshItems();
	}
	
	public void remove(MapObject obj){
		mapObjects.removeValue(obj, false);
	}
	
	public Array<Item> getItems(){
		return items;
	}
	
	public Array<MapObject> getObjects(){
		return mapObjects;
	}
}
