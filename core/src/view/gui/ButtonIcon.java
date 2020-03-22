package view.gui;

public enum ButtonIcon {
	
	SOUNDS("black_sounds"),
	SOUNDS_OFF("black_sounds_off"),
	HELP("black_help"),
	TEAM("black_team"),
	INVENTORY("black_inventory"),
	MISSIONS("black_missions"),
	MUSIC("black_music"),
	CLOSE("black_close"),
	LOCATION("black_location"),
	PLUS("black_plus"),
	FULLSCREEN("fullscreen"),
	CODE("code");
	
	private final String name;
	
	ButtonIcon(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}