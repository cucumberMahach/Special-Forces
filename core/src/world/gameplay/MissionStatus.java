package world.gameplay;

public enum MissionStatus{
	LOCKED("markLocked"),
	AVAILABLE("markAvailable"),
	COMPLETED("markCompleted");
	
	private final String name;
	
	MissionStatus(String name){
		this.name = name;
	}
	
	public String getMarkIcon(){
		return name;
	}
}
