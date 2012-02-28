package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class Grid<T extends GridObject>  {
	public int cellSize = 250;

	public ArrayList<Block<T>> blocks = new ArrayList<Block<T>>();

	public Grid(float width, float height){
		
		int xStep = (int) Math.floor(width/cellSize);
		int yStep =  (int) Math.floor(height/cellSize);
		
		for (int x = 0; x < xStep; x += 1){
			for (int y = 0; y < yStep ; y += 1){
				blocks.add(new Block<T>(x*cellSize,y*cellSize,cellSize,cellSize));
			}
		}	
	}
	
	public boolean add(T obj){
		for (Block<T> b: blocks){
			if (b.area.contains(obj.getPoint())){
				b.elements.add(obj);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<T> get(BB box){
		ArrayList<T> list = new ArrayList<T>();
		for (Block<T> b: blocks){
			if (box.contains(b.area)){
				list.addAll(b.elements);
			}else{
				if (box.intersects(b.area)){
					for (T e: b.elements){
						if (box.contains(e.getPoint()))list.add(e);
					}
				}
			}
			
		}
		return list;
	}
	
	public ArrayList<T> get(Point p){
		for (Block<T> b: blocks){
			if (b.area.contains(p)){
				return b.elements;
			}
		}
		return new ArrayList<T>();
	}
	public ArrayList<T> getNeighbors(Point p){
		for (Block<T> b: blocks){
			if (b.area.contains(p)){
				return b.elements;
			}
		}
		return new ArrayList<T>();
	}
	
	public int count(){
		int c = 0;
		for (Block<T> b: blocks){
			c += b.elements.size();
		}
		return c;
	}
	
	public void render(Graphics g){
		for (Block<T> b: blocks){
			b.render(g);
		}
	}
	
	public class Block<U> {
		public BB area;
		public ArrayList<U> elements = new ArrayList<U>();
		
		public Block(float x, float y, float w, float h){
			this.area = new BB(x,y,w,h);
		}
		
		public void render(Graphics g){
			g.drawRect(area.min.x, area.min.y, area.getWidth(), area.getHeight());
		}
		
		
	}

	public boolean remove(T obj) {
		boolean removed = false;
		for (Block<T> b: blocks){
			if (b.elements.remove(obj))removed = true;
		}
		return removed;
	}
}
