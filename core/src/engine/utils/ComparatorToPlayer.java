package engine.utils;

import java.util.Comparator;

import stages.World;
import world.objects.MapObject;

public class ComparatorToPlayer implements Comparator<MapObject>{
	
	private final World world;
	
	public ComparatorToPlayer(World world) {
		this.world = world;
	}

	@Override
	public int compare(MapObject obj1, MapObject obj2) {
		final float px = world.getPlayerX();
		final float py = world.getPlayerY();
		final float lenObj1 = Maths.len2(obj1.getCenterX(), obj1.getCenterY(), px, py);
		final float lenObj2 = Maths.len2(obj2.getCenterX(), obj2.getCenterY(), px, py);
		return lenObj1 < lenObj2 ? -1 : 1;
	}

}
