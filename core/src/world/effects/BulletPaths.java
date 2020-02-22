package world.effects;

import engine.Style;
import view.Group;

public class BulletPaths extends Group{
	
	private	Path[] paths;
	private int index;
	
	private boolean toRestore;
	
	public BulletPaths(){
		paths = new Path[Style.BULLET_PATH_COUNT];
		for (int i = 0; i < paths.length; i++){
			paths[i] = new Path();
			addActor(paths[i]);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (!toRestore)
			return;
		free();
	}
	
	private void free(){
		toRestore = false;
		index = 0;
		int i;
		for (i = 0; i < paths.length; i++)
			paths[i].restore();
	}
	
	public void restore(){
		toRestore = true;
	}
	
	public void addPath(float origX, float origY, float dirX, float dirY, float distance){
		Path path = paths[index];
		path.start(origX, origY, dirX, dirY, distance);
		index++;
		if (index == paths.length)
			index = 0;
	}
}
