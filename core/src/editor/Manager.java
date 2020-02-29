package editor;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.text.SimpleDateFormat;
import java.util.Date;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import stages.Editor;
import world.map.MapBuilder;

public class Manager {
	
	private Editor editor;
	private Loader loader;

	private boolean firstLaunch = true;
	
	public Manager(Editor editor, Loader loader){
		this.editor = editor;
		this.loader = loader;
		newMap(20, 20, 0);
		editor.getOrtCam().position.set((editor.map().getMapWidth() * Style.TILE_SIZE)/2f, (editor.map().getMapHeight() * Style.TILE_SIZE)/2f, 0);
	}

	public void sendMap(){
		if (procErrors())
			return;
        long time = (new Date()).getTime();
		long msTime = SpecialForces.getInstance().playerData().getMapSenderTime();
		long after = Style.MAPSENDER_RESEND_TIME - (time - msTime);
		if (after <= 0){
            String map = mapToString();
            if (!editor.mapSender().sendMap(map)){
				editor.gui().showMessage("Error occurred");
			}else{
				SpecialForces.getInstance().playerData().setMapSenderTime(time);
				editor.gui().showMessage("Done");
			}
        }else{
            editor.gui().showMessage("You can send next map\nafter " + (after / 1000) + " sec");
        }
	}

	public boolean procErrors(){
		if (!editor.objects().isPlayerCreated()){
			editor.gui().showMessage("Player is not created");
			return true;
		}
		if (!editor.objects().isPlayerSingular()){
			editor.gui().showMessage("Map could have only\none player");
			return true;
		}
		if (!editor.objects().isZoneCreated()){
			editor.gui().showMessage("Exit blue zone is not\ncreated");
			return true;
		}
		if (!editor.objects().isZoneSingular()){
			editor.gui().showMessage("Map could have only\none blue zone");
			return true;
		}
		return false;
	}
	
	public void testMap(){
		if (procErrors())
			return;
		String map = mapToString();
		SpecialForces.getInstance().screenManager().startTest(map, SpecialForces.getInstance().playerData());
	}
	
	public void save(int index){
		loader.saveToFile("bin/maps/editor".concat(String.valueOf(index)).concat(".txt"), mapToString());
	}
	
	public void open(int index){
		try{
			String map = loader.loadFromFile("bin/maps/editor".concat(String.valueOf(index)).concat(".txt"));
			clear();
			editor.map().loadFromString(map);
			editor.map().build();
		}catch(GdxRuntimeException e){
			editor.gui().showMessage("This slot is empty");
		}
	}
	
	public void newMap(int width, int height, int tileIndex) {
		StringBuilder sb = new StringBuilder();
		sb.append("#map\n");
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++)
				sb.append(MapBuilder.convertToFile((char) tileIndex));
			sb.append('\n');
		}
		sb.append("#objects\n#end");
		openFromString(sb.toString());
	}
	
	public void clear(){
		editor.objects().free();
		editor.map().freeMap();
		editor.gui().updateProperties(null);
	}
	
	public void show(){
		Maths.setWorld(editor);
		if (firstLaunch) {
			firstLaunch = false;
			//editor.gui().showMessage("You can also send\nyour map to us");
			//editor.gui().showMessage("Resize blue zone - use\nupper right corner");
		}
	}
	
	public void hide(){
		
	}
	
	private String mapToString(){
		StringBuilder sb = new StringBuilder();
		sb.append("#map\n");
		editor.map().generate(sb);
		sb.append("#objects\n");
		editor.objects().generate(sb);
		sb.append("#end");
		return sb.toString();
	}
	
	private void openFromString(String map) {
		clear();
		editor.map().loadFromString(map);
		editor.map().build();
	}
}
