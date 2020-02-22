package view.joystick;

public interface JoystickListener {
	public void dragDown(float x, float y);
	public void dragged(float x, float y, float cof);
	public void dragUp();
}
