package engine.utils.timer;

public class Timer {
	
	private TimerEvent event;
	
	private float interval;
	private float time;
	
	private boolean enabled;
	private boolean repeat;
	
	public Timer(){
		enabled = false;
	}
	
	public void start(){
		enabled = true;
		time = 0;
	}
	
	public void stop(){
		enabled = false;
	}
	
	public void update(float delta){
		if (!enabled)
			return;
		time += delta;
		if (time >= interval){
			event.event();
			if (!repeat)
				stop();
			time = 0;
		}
	}
	
	public void set(float interval, boolean repeat){
		setInterval(interval);
		setRepeat(repeat);
		start();
	}
	
	public void setRepeat(boolean value){
		repeat = value;
	}
	
	public boolean isRepeat(){
		return repeat;
	}
	
	public void setEvent(TimerEvent event){
		this.event = event;
	}
	
	public TimerEvent getEvent(){
		return event;
	}
	
	public void setInterval(float value){
		interval = value;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public float getCurrentTime(){
		return time;
	}
	
	public float getInterval(){
		return interval;
	}
}
