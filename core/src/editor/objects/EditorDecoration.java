package editor.objects;

import engine.Loader;

public class EditorDecoration extends EditorObject{
	private Loader loader;
	private int health;
	
	public EditorDecoration(Loader loader, String name, float x, float y, int health){
		super(loader.getObject(name), EditorObjectType.DECORATION, x, y);
		this.loader = loader;
		this.health = health;
		setName(name);
	}
	
	public int getHealth(){
		return health;
	}
	
	@Override
	public void setName(String name) {
		setTexture(loader.getObject(name));
		super.setName(name);
	}

	public void setHealth(int value) {
		health = value;
	}
}
