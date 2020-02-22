package editor;

import editor.objects.EditorBot;
import editor.objects.EditorObject;
import engine.utils.Maths;
import stages.Editor;

public class Commands {
	private Editor editor;
	private EditorObject selObj;
	private boolean removeMode;
	private boolean roundMode;
	
	public Commands(Editor editor){
		this.editor = editor;
	}
	
	public void firstProperty(float x, float y){
		final float len = Maths.len(selObj.getCenterX(), selObj.getCenterY(), x, y);
		final float vecX = x - selObj.getCenterX();
		final float vecY = y - selObj.getCenterY();
		switch (selObj.getObjectType()) {
		case BOT:
			EditorBot bot = ((EditorBot) selObj);
			bot.setVisibleDistance(len);
			bot.setRotation(Maths.calcDegrees(vecX, vecY));
			break;

		default:
			break;
		}
	}
	
	public void setRemoveMode(boolean value){
		removeMode = value;
	}
	
	public void toggleRoundMode(){
		roundMode = !roundMode;
	}
	
	public void selectObject(EditorObject obj){
		if (selObj != null)
			selObj.unselect();
		selObj = obj;
		if (selObj != null)
			selObj.select();
		editor.gui().updateProperties(selObj);
	}
	
	public void removeSelectObject(){
		editor.objects().removeObject(selObj);
		selObj.remove();
		selectObject(null);
	}
	
	public EditorObject getSelectObject(){
		return selObj;
	}
	
	public boolean isRemoveMode(){
		return removeMode;
	}
	
	public boolean isRoundMode(){
		return roundMode;
	}
}
