package world.map;

import java.util.LinkedList;
import java.util.Stack;

import world.objects.MapObject;

public class PathFinder {
	private Cell[] map;
	private int width, height;
	
	private LinkedList<Cell> openList, closedList, tmpList;
	
	public PathFinder(Cell[] map, int width, int height){
		this.map = map;
		this.width = width;
		this.height = height;
		openList = new LinkedList<Cell>();
		closedList = new LinkedList<Cell>();
		tmpList = new LinkedList<Cell>();
	}
	
	public void find(int x1, int y1, int x2, int y2, Stack<Cell> steps){
		Cell start, finish, min;
		boolean found = false, noRoute = false;
		
		clear();
		openList.clear();
		closedList.clear();
		tmpList.clear();
		
		start = get(x1, y1);
		start.start = true;
		finish = get(x2, y2);
		finish.finish = true;
		
		openList.push(start);
		start.open = true;
		while(!found && !noRoute){
			min = openList.getFirst();
			for (int i = 0; i < openList.size(); i++){
				Cell cell = openList.get(i);
				if (cell.F < min.F)
					min = cell;
			}
			
			min.closed = true;
			openList.remove(min);
			min.open = false;
			
			tmpList.clear();
            tmpList.add(get(min.x - 1, min.y - 1));
            tmpList.add(get(min.x,     min.y - 1));
            tmpList.add(get(min.x + 1, min.y - 1));
            tmpList.add(get(min.x + 1, min.y));
            tmpList.add(get(min.x + 1, min.y + 1));
            tmpList.add(get(min.x,     min.y + 1));
            tmpList.add(get(min.x - 1, min.y + 1));
            tmpList.add(get(min.x - 1, min.y));
			
			for (Cell cell : tmpList){
				if (cell.type == TileType.WALL || cell.closed || (cell.objects.size != 0 && checkIgnorePathfinding(cell)))
					continue;
				
				if (!cell.open){
					openList.add(cell);
					cell.open = true;
					cell.parent = min;
					cell.calc(finish);
					continue;
				}
				
				if (cell.G + cell.price(min) < min.G){
					cell.parent = min;
					cell.calc(finish);
				}
			}
			
			if (finish.open)
				found = true;
			if (openList.isEmpty())
				noRoute = true;
		}
		
		if (found)
			buildPath(start, finish, steps);
	}
	
	private boolean checkIgnorePathfinding(Cell cell){
		for (int i = 0; i < cell.objects.size; i++){
			final MapObject obj = cell.objects.get(i);
			if (obj.ignorePathFinding())
				return false;
		}
		return true;
	}
	
	private void buildPath(Cell start, Cell finish, Stack<Cell> steps){
		Cell cell = finish.parent;
		while(!cell.equals(start)){
			steps.push(cell);
			cell.road = true;
			cell = cell.parent;
			if (cell == null)
				break;
		}
	}
	
	private Cell get(int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height ? map[x + y * width] : new Cell(x, y, TileType.WALL);
	}
	
	private void clear(){
		int i, len = map.length;
		for (i = 0; i < len; i++)
			map[i].clear();
	}
}