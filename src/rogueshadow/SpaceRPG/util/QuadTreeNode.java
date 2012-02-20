package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;

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
			System.err.println("Depth: " + depth);
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
		}else{
			elements.remove(c);
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
			if (elements.size() > QuadTree.maxElementsPerNode && getRect().getWidth() > 5){
				split();
				for (Collidable e: elements)pushDown(e);
			    elements.clear();
			}
		}else{
			pushDown(c);
		}
	}
	
	public void render(Graphics g){
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
		}
	}
	
	public Rectangle getRect(){
		return this.rect;
	}
	

}
