package engine.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;

import engine.Loader;

public class CoordsMap {
	
	public static int[][] readCoordsMap(String filename){
		try {
			return loadCoords(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int[][] loadCoords(String filename) throws IOException{
		int[][] data = new int[getLineCount(filename)][];
		BufferedReader bf = Gdx.files.internal(Loader.DIRECTORY + filename).reader(1024);
		String str[];
		int q = 0;
		for (int i = 0; i < data.length; i++){
			str = bf.readLine().split(",");
			data[i] = new int[str.length];
			for (q = 0; q < str.length; q++)
				data[i][q] = Integer.valueOf(str[q]);
		}
		bf.close();
		return data;
	}
	
	private static int getLineCount(String filename) throws IOException{
		int count = 0;
		BufferedReader bf = Gdx.files.internal(Loader.DIRECTORY + filename).reader(1024);
		while(bf.ready()){
			bf.readLine();
			count++;
		}
		bf.close();
		return count;
	}
}
