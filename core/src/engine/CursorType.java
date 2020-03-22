package engine;

public enum CursorType{
    AIM("aim"),
    ARROW("arrow");

    private String cursorName;

    CursorType(String cursorName){
        this.cursorName = cursorName;
    }

    public String getCursorName() {
        return cursorName;
    }
}
