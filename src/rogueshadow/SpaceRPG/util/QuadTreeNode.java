package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import rogueshadow.SpaceRPG.interfaces.Collidable;

public class QuadTreeNode {
	public ArrayList<Collidable> elements = new ArrayList<Collidable>();
	public Rectangle rect;
	public QuadTreeNode tl = null;
	public QuadTreeNode tr = null;
	public QuadTreeNode bl = null;
	public QuadTreeNode br = null;
	public QuadTreeNode parent = null;
	public int depth;
	public boolean leaf;
	
	public QuadTreeNode(QuadTreeNode parent, Rectangle rect){
		this.parent = parent;
		this.rect = rect;
		leaf = true;
		if (this.parent == null){
			this.depth = 1;
		}else{
			this.depth = this.parent.depth + 1;
		}
	}
	
	public void split(){
		leaf = false;
		float x = rect.getMinX();
		float y = rect.getMinY();
		float w = rect.getWidth()/2f;
		float h = rect.getHeight()/2f;
		this.tl = new QuadTreeNode(this,new Rectangle(x,y,w,h));
		this.tr = new QuadTreeNode(this,new Rectangle(x+w,y,w,h));
		this.bl = new QuadTreeNode(this,new Rectangle(x,y+h,w,h));
		this.br = new QuadTreeNode(this,new Rectangle(x+w,y+h,w,h));
	}
	
	public QuadTreeNode getParent(){
		return this.parent;
	}
	
	public void remove(Collidable c){
		if (!leaf){
			tl.remove(c);
			tr.remove(c);
			bl.remove(c);
			br.remove(c);
			
			int sum = 0;
			sum += tl.count() + tr.count() + bl.count() + br.count();
			if (sum < QuadTree.maxElementsPerNode){
				elements.addAll(tl.getAll());
				elements.addAll(tr.getAll());
				elements.addAll(bl.getAll());
				elements.addAll(br.getAll());
				tl = null;
				tr = null;
				bl = null;
				br = null;
				leaf = true;
			}
			
			
		}else{
			elements.remove(c);
		}
	}
	
	public ArrayList<Collidable> get(Rectangle rect){
		ArrayList<Collidable> list = new ArrayList<Collidable>();
		if (leaf){
			if (getRect().intersects(rect))list.addAll(elements);
		}else{
			if (tl.intersects(rect))list.addAll(tl.get(rect));
			if (tr.intersects(rect))list.addAll(tr.get(rect));
			if (bl.intersects(rect))list.addAll(bl.get(rect));
			if (br.intersects(rect))list.addAll(br.get(rect));
		}
		return list;
	}
	
	public boolean intersects(Rectangle rect){
		return getRect().intersects(rect);
	}
	
	public ArrayList<Collidable> getAll(){
		ArrayList<Collidable> list = new ArrayList<Collidable>();
		if (leaf){
			list.addAll(elements);
			return list;
		}else{
			list.addAll(tl.getAll());
			list.addAll(tr.getAll());
			list.addAll(bl.getAll());
			list.addAll(br.getAll());
			return list;
		}
	}


	public int count(){
		int count = 0;
		if (!leaf){
			count += tl.count();
			count += tr.count();
			count += bl.count();
			count += br.count();
		}else{
			count += elements.size();
		}
		return count;
	}
	
	public void add(Collidable c){
		if (leaf){
			elements.add(c);
			if (elements.size() > QuadTree.maxElementsPerNode && getRect().getWidth() > QuadTree.minWidth){
				split();
				for (Collidable e: elements)pushDown(e);
			    elements.clear();
			}
		}else{
			pushDown(c);
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.green);
		g.draw(getRect());
		if (!leaf){
			tl.render(g);
			tr.render(g);
			bl.render(g);
			br.render(g);
		}
	}
	
	public void pushDown(Collidable c){
		int i = 0;
		if (tl.getRect().intersects(c.getRect())){
			i++;
			tl.add(c);
		}
		if (tr.getRect().intersects(c.getRect())){
			i++;
			tr.add(c);
		}
		if (bl.getRect().intersects(c.getRect())){
			i++;
			bl.add(c);
		}
		if (br.getRect().intersects(c.getRect())){
			i++;
			br.add(c);
		}
		if (i == 0){
			System.err.println("Element fell through cracks in tree.");
			pushUp(c);
		}
	}
	public void pushUp(Collidable c){
		if (getParent() != null){
			getParent().add(c);
		}
	}
	
	public Rectangle getRect(){
		return this.rect;
	}
	

}
