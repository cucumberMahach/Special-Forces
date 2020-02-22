package world.gameplay;

public class Mission {
	
	private String name;
	private String[] maps;
	private int dollarsReward, experienceReward;
	private float markX, markY;
	private MissionStatus status;
	
	public Mission(String name, String[] maps, int dollarsReward, int experienceReward, float markX, float markY, MissionStatus status) {
		this.name = name;
		this.maps = maps;
		this.dollarsReward = dollarsReward;
		this.experienceReward = experienceReward;
		this.markX = markX;
		this.markY = markY;
		this.status = status;
	}

	public Mission(){}

	public float getMarkX() {
		return markX;
	}

	public float getMarkY() {
		return markY;
	}

	public String getName() {
		return name;
	}
	
	public String[] getMaps(){
		return maps;
	}

	public int getDollarsReward() {
		return dollarsReward;
	}

	public int getExperienceReward() {
		return experienceReward;
	}

	public MissionStatus getStatus() {
		return status;
	}
	
	public void setStatus(MissionStatus status){
		this.status = status;
	}
	
}