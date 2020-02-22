package view.joystick;

import engine.Loader;
import view.Group;
import view.Image;

public class Joystick extends Group{
	
	private Stick stick;
	private Image socket;
	
	public Joystick(Loader loader, float x, float y){
		socket = new Image(loader.getHud("socket"), 0, 0);
		stick = new Stick(loader, this);
		stick.setPosition(socket.getCenterX() - stick.getWidth()/2, socket.getCenterY() - stick.getHeight()/2);
		addActor(socket);
		addActor(stick);
		setPosition(x, y);
	}
	
	public void setListener(JoystickListener listener){
		stick.setListener(listener);
	}

	public Image getSocketImage(){
		return socket;
	}
}
