package engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;

public class Cursors {
    private Loader loader;

    public Cursors(Loader loader){
        this.loader = loader;
    }

    public void setCursor(CursorType cursorType){
        Cursor cursor = loader.getCursor(cursorType.getCursorName());
        Gdx.graphics.setCursor(cursor);
    }

    public void setDefaultCursor(){
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
    }
}

