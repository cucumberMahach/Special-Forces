package world.map;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

import engine.Loader;

public class MapReader {
	
	private Cell[] map;
	private String[] objectsMap;
	
	private int width, height;
	
	public void loadMap(String filename) throws IOException{
		String[] data;
		data = readFile(filename);
		readMap(data);
	}
	
	public void loadMapFromString(String map){
		String[] data = map.split("\r\n");
		if (data.length < 2)
			data = map.split("\n");
		readMap(data);
	}
	
	private void readMap(String[] data){
		int[] separators = findSeparators(data);
		map = readMap(data, separators[0]+1, separators[1]-1);
		
		objectsMap = new String[separators[2] - separators[1] - 1];
		System.arraycopy(data, separators[1]+1, objectsMap, 0, separators[2] - separators[1] - 1);
		
	}
	
	private Cell[] readMap(String[] data, int start, int len){
		width = data[start].length();
		Cell[] map = new Cell[width * len];
		height = map.length / width;
		String str;
		int y = 0, i, x;
		for (i = start; i < len+start; i++){
			str = data[i];
			for (x = 0; x < str.length(); x++){
				map[x + y * width] = new Cell(x, y, str.charAt(x));
			}
			y++;
		}
		return map;
	}
	
	private int[] findSeparators(String[] data){
		int i, values[] = new int[3];
		for (i = 0; i < data.length; i++){
			switch (data[i]) {
			case "#map":
				values[0] = i;
				break;
				
			case "#objects":
				values[1] = i;
				break;
				
			case "#end":
				values[2] = i;
				break;

			default:
				break;
			}
				
		}
		return values;
	}
	
	private String[] readFile(String filename) throws IOException{
		String[] data = new String[getLineCount(filename)];
		BufferedReader bf = Gdx.files.internal(Loader.DIRECTORY + filename).reader(1024);
		int i = 0;
		while(bf.ready()){
			data[i] = bf.readLine();
			i++;
		}
		bf.close();
		return data;
	}
	
	private int getLineCount(String filename) throws IOException{
		int count = 0;
		BufferedReader bf = Gdx.files.internal(Loader.DIRECTORY + filename).reader(1024);
		while(bf.ready()){
			bf.readLine();
			count++;
		}
		bf.close();
		return count;
	}
	
	public Cell[] getMap(){
		return map;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public String[] getObjects(){
		return objectsMap;
	}
}
