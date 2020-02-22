package view.gui;

import com.badlogic.gdx.utils.Align;

import view.Font;

public enum ButtonType {
	MENU_ICON("button_icon_menu"),
	MENU_TEXT("button_text_menu", Font.BIG_BUTTON, Align.center),
	MANAGER("button_manager", Font.SMALL_BUTTON, Align.center),
	SMALL_YELLOW("button_small_yellow"),
	SMALL_GREEN("button_small_green"),
	SMALL_RED("button_small_red"),
	SMALL_TEXT("button_small_green", Font.BIG_BUTTON, Align.center),
	WEAPON("button_weapon"),
	BUY("button_contract", Font.SMALL_BUTTON, Align.center),
	LOCATION("button_location", Font.SMALL_BUTTON, Align.left, true),
	DIALOG("button_dialog", Font.BIG_BUTTON, Align.center);
	
	private final String name;
	private final Font font;
	private final int fontAlign;
	private final boolean nextToIcon;
	
	ButtonType(String name, Font font, int align, boolean nextToIcon){
		this.name = name;
		this.font = font;
		this.fontAlign = align;
		this.nextToIcon = nextToIcon;
	}
	
	ButtonType(String name, Font font, int align){
		this(name, font, align, false);
	}
	
	ButtonType(String name){
		this(name, null, 0);
	}
	
	public String getName(){
		return name;
	}
	
	public Font getFont(){
		return font;
	}
	
	public int getFontAlign(){
		return fontAlign;
	}
	
	public boolean isNextToIcon(){
		return nextToIcon;
	}
	
	public boolean isFonted(){
		return font != null;
	}
	
	public boolean isSmall(ButtonType type){
		return type == SMALL_GREEN || type == SMALL_RED || type == SMALL_YELLOW;
	}
}