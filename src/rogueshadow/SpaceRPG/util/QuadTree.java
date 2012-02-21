package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import rogueshadow.SpaceRPG.interfaces.ISpatialPartition;
import rogueshadow.SpaceRPG.interfaces.NodeElement;

public class QuadTree<T> implements ISpatialPartition<T> {
	public static int maxElementsPerNode = 2;
	public static int minWidth = 25;
	public Node<T> root;
	
	public QuadTree (float x, float y, float width, float height){
		root = new Node<T>(null,new BB(x,y,width,height));
	}	
	public QuadTree (BB box){
		root = new Node<T>(null, box);
	}

	public class Node<TT> {
		public ArrayList<TT> elements = new ArrayList<TT>();
		public BB box;
		public Node<TT> tl = null;
		public Node<TT> tr = null;
		public Node<TT> bl = null;
		public Node<TT> br = null;
		public Node<TT> parent = null;
		public int depth;
		public boolean leaf;
		
		public Node(Node<TT> parent, BB box){
			this.parent = parent;
			this.box = box;
			leaf = true;
			if (this.parent == null){
				this.depth = 1;
			}else{
				this.depth = this.parent.depth + 1;
			}
		}

		
		public Node(BB box) {
			this(null, box);
		}


		public void split(){
			leaf = false;
			float x = box.min.x;
			float y = box.min.y;
			float w = box.getWidth()/2f;
			float h = box.getHeight()/2f;
			this.tl = new Node<TT>(this,new BB(x,y,w,h));
			this.tr = new Node<TT>(this,new BB(x+w,y,w,h));
			this.bl = new Node<TT>(this,new BB(x,y+h,w,h));
			this.br = new Node<TT>(this,new BB(x+w,y+h,w,h));
		}
		
		//@SuppressWarnings("unchecked")
		public Node<TT> getParent(){
			return this.parent;
		}
		
		public void remove(TT c){
			if (!leaf){
				tl.remove(c);
				tr.remove(c);
				bl.remove(c);
				br.remove(c);
				unSplit();
			}else{
				elements.remove(c);
			}
		}
		
		public void unSplit(){
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
		}
		
		public ArrayList<TT> get(BB box){
			ArrayList<TT> list = new ArrayList<TT>();
			if (leaf){
				if (getBB().intersects(box))list.addAll(elements);
			}else{
				if (tl.intersects(box))list.addAll(tl.get(box));
				if (tr.intersects(box))list.addAll(tr.get(box));
				if (bl.intersects(box))list.addAll(bl.get(box));
				if (br.intersects(box))list.addAll(br.get(box));
			}
			return list;
		}
		
		public boolean intersects(BB box){
			return getBB().intersects(box);
		}
		
		public ArrayList<TT> getAll(){
			ArrayList<TT> list = new ArrayList<TT>();
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
		
		public void add(TT c){
			if (leaf){
				elements.add(c);
				if (elements.size() > QuadTree.maxElementsPerNode && getBB().getWidth() > QuadTree.minWidth){
					split();
					for (TT e: elements)pushDown(e);
				    elements.clear();
				}
			}else{
				pushDown(c);
			}
		}
		
		public void render(Graphics g){
			g.setColor(Color.green);
			g.draw(new Rectangle(box.min.x,box.min.y,box.min.x+box.getWidth(),box.min.y+box.getHeight())); //TODO remove this, or alter it to provide something more  useful.
			if (!leaf){
				tl.render(g);
				tr.render(g);
				bl.render(g);
				br.render(g);
			}
		}
		
		//@SuppressWarnings("unchecked")
		public void pushDown(TT c){
			int i = 0;
			if (tl.intersects(((NodeElement) c).getBB())){
				i++;
				tl.add(c);
			}
			if (tr.intersects(((NodeElement) c).getBB())){
				i++;
				tr.add(c);
			}
			if (bl.intersects(((NodeElement) c).getBB())){
				i++;
				bl.add(c);
			}
			if (br.intersects(((NodeElement) c).getBB())){
				i++;
				br.add(c);
			}
			if (i == 0){
				System.err.println("Element fell through cracks in tree.");
			}
		}
		public void pushUp(TT c){
			if (getParent() != null){
				getParent().add(c);
			}else{
				System.err.println("Object seems to have left the world space.");
			}
		}
		
		public BB getBB(){
			return this.box;
		}


		public ArrayList<T> get(Point p) {
			// TODO Auto-generated method stub
			return null;
		}
		

	}

	@Override
	public void remove(T obj) {
		root.remove(obj);
	}

	@Override
	public ArrayList<T> get(BB box) {
		return root.get(box);
	}

	@Override
	public ArrayList<T> get(Point p) {
		return root.get(p);
	}

	@Override
	public void add(T obj) {
		root.add(obj);
	}
	
	@Override
	public int count(){
		return root.count();
	}
	
	public void render(Graphics g){
		root.render(g);
	}
	
}



