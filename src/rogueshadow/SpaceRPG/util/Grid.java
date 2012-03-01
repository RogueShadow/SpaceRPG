package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.utility.BB;
import rogueshadow.utility.Point;


public class Grid {
	public int cellSize = 250;
	public int width, height;
	public int xSteps, ySteps;
	
	public int size;

	public Cell[][] cells = null;
	
	public Grid(float width, float height){
		
		xSteps = (int) Math.floor(width/cellSize);
		ySteps =  (int) Math.floor(height/cellSize);
		
		this.width = xSteps*cellSize;
		this.height = ySteps*cellSize;
		
		System.out.println("Creating new Grid, xStep: " + xSteps + " yStep: " + ySteps + " cellSize: " + cellSize +" Intended width/height: " + width + "/" + height + " Actual: " + xSteps*cellSize + "/" + ySteps*cellSize);
		
		cells = new Cell[xSteps][ySteps];
		
		for (int x = 0; x < xSteps; x += 1){
			for (int y = 0; y < ySteps ; y += 1){
				cells[x][y] = new Cell(x*cellSize, y*cellSize, cellSize, cellSize);
			}
		}	
	}
	
	public void render(Graphics g){
		for (int x = 0; x < cells.length; x++){
			for (int y = 0; y < cells[0].length; y++){
				cells[x][y].render(g);
			}
		}
	}
	

	public class Cell {
		public BB area;
		private ArrayList<WorldObject> elements = new ArrayList<WorldObject>();
		
		public Cell(double x, double y, double w, double h){
			this.area = new BB(x,y,w,h);
		}
		
		public void render(Graphics g){
			g.drawRect((float)area.min.x, (float)area.min.y,(float) area.getWidth(), (float)area.getHeight());
		}
		
		public String loc(){
			return "(" + getCellx(area.min.x) + "," + getCelly(area.min.y) + ")";
		}

		public ArrayList<WorldObject> getElements() {
			// TODO Auto-generated method stub
			return elements;
		}
		
	}


	public ArrayList<WorldObject> get(BB bb) {
		int sx = getCellx(bb.min.x);
		int sy = getCelly(bb.min.y);
		int endx = getCellx(bb.max.x);
		int endy = getCelly(bb.max.y);
		
		ArrayList<WorldObject> list = new ArrayList<WorldObject>();
		
		for (int x = sx ; x <= endx; x++){
			for (int y = sy; y <= endy; y++){
				if (x == sx || x == endx || y == sy || y == endy){
					for (WorldObject obj: cells[x][y].elements){
						if (bb.contains(obj.getPoint()))list.add(obj);
					}
				}else{
					list.addAll(cells[x][y].elements);
				}
			}
		}
		
		return list;
	}
	public void msg(WorldObject obj, String msg){
		System.err.println(obj.toString() + "::Loc"+obj.getPoint().toString() + ": " + msg);
	}

	public boolean remove(WorldObject obj) {
		Cell c = getCell(obj.getPoint().x, obj.getPoint().y);
		if (c.area.contains(obj.getPoint())){
			if (c.elements.remove(obj)){
				size--;
				return true;
			}
		}else{
	
			c = getCell(obj.oldPos.x, obj.oldPos.y);
			if (!c.elements.remove(obj)){
				System.err.println(obj.toString() + "wasn't in cell" + c.loc() + " checking all cells.");
			}else{
				return true;
			}
					
			boolean removed = false;
			for (int x = 0; x < xSteps; x++){
				for (int y = 0; y < ySteps; y++){
					if (cells[x][y].elements.remove(obj)){
						System.err.println(obj.toString() + "was found in cell" + cells[x][y].loc()	+ " removed.");
						removed = true;
						size--;
					}
				}
			}
			return removed;
		}
		System.err.println(obj.toString() + " wasn't in the grid. Loc: " + obj.getPoint().toString());
		return false;
		
	}

	public boolean add(WorldObject obj) {
		Point p = obj.getPoint();
		int X, Y;
		X = getCellx(p.x);
		Y = getCelly(p.y);
		if (cells[X][Y].area.contains(p)){
			if (cells[X][Y].elements.add(obj)){
				size++;
				return true;
			}
		}
		return false;
	}

	public int realCount() { // add every element in every grid.
		int count = 0;
		for (int x = 0; x < xSteps; x++){
			for (int y = 0; y < ySteps; y++){
				count += cells[x][y].elements.size();
			}
		}
		return count;
	}
	
	public int count(){ // running total counter from add/remove operations. Should be right, we hope.
		return size;
	}

	public ArrayList<WorldObject> get(Point p) {
		ArrayList<WorldObject> list = new ArrayList<WorldObject>();
		list.addAll(cells[getCellx(p.x)][getCelly(p.y)].elements);
		return list;

	}
	
	public Cell getCell(double x, double y){
			return cells[getCellx(x)][getCelly(y)];
	}
	public int getCellx(double x){
		int cellx = (int) Math.floor(x/cellSize);
		if (cellx < 0)cellx = 0;
		if (cellx >= (xSteps)) cellx = xSteps-1;
		return cellx;
	}
	public int getCelly(double y){
		int celly = (int) Math.floor(y/cellSize);
		if (celly < 0)celly = 0;
		if (celly >= (ySteps)) celly = ySteps-1;
		return celly;
	}

	public void move(int oldx, int oldy, int newx, int newy, WorldObject obj) {
		if (!cells[oldx][oldy].elements.remove(obj)){
			System.err.println(obj.toString() + " has not been removed during moving, very sad. Not re-added either, for safety.");
		}else{
			cells[newx][newy].elements.add(obj);
		}
	}

	public ArrayList<WorldObject> get(BB bb, WorldObject obj) {
		int sx = getCellx(bb.min.x);
		int sy = getCelly(bb.min.y);
		int endx = getCellx(bb.max.x);
		int endy = getCelly(bb.max.y);
		
		ArrayList<WorldObject> list = new ArrayList<WorldObject>();
		
		for (int x = sx ; x <= endx; x++){
			for (int y = sy; y <= endy; y++){
				if (x == sx || x == endx || y == sy || y == endy){
					for (WorldObject e: cells[x][y].elements){
						if (obj == e)continue;
						if (bb.contains(obj.getPoint()))list.add(e);
					}
				}else{
					list.addAll(cells[x][y].elements);
				}
			}
		}
		
		return list;
	}
}
