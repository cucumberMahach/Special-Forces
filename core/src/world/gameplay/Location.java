package world.gameplay;

public class Location {
	private String name;
	private Mission[] missions;
	private int mapIndex;
	
	public Location(String name, Mission[] missions, int mapIndex){
		this.name = name;
		this.missions = missions;
		this.mapIndex = mapIndex;
	}
	
	public Location(){}
	
	public Mission[] getMissions(){
		return missions;
	}
	
	public String getName(){
		return name;
	}

	public int getMapIndex() {
		return mapIndex;
	}
}
