package editor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import engine.SpecialForces;
import stages.Editor;

public class TouchZoomController implements GestureListener{
    private Editor editor;
    private float zoomAmount, oldZoomAmount;

    public boolean zoomHappened;

    public TouchZoomController(Editor editor) {
        this.editor = editor;
    }

    public void update() {
        editor.zoomBy(getZoom());
    }

    private float getZoom(){
        final float zoom = ((zoomAmount - oldZoomAmount) * 0.0025f);
        oldZoomAmount = zoomAmount;

        return zoom;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if (pointer > 0)
            return false;
        zoomHappened = false;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        zoomHappened = false;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        oldZoomAmount = zoomAmount;
        zoomAmount = initialDistance - distance;
        zoomHappened = true;
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void pinchStop() {
        // TODO Auto-generated method stub

    }
}
