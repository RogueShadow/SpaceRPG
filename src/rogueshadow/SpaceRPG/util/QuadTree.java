package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.ISpatialPartition;
import rogueshadow.SpaceRPG.interfaces.NodeElement;

public class QuadTree implements ISpatialPartition{
	public static int maxElementsPerNode = 2;
	public static int minWidth = 100;
	public Node root;
	private Node nodePool = new Node();
	private int nodePoolSize = 0;
	private int poolMaxSize = 100;
	
	public QuadTree (float x, float y, float width, float height){
		this(new BB(x,y,width,height));
	}	
	public QuadTree (BB box){
		root = new Node(null, box);
		getNodes(root);
	}
	
	public void getNodes(Node p){
		p.tl = makeNode().setParent(p);
		p.tr = makeNode().setParent(p);
		p.bl = makeNode().setParent(p);
		p.br = makeNode().setParent(p);
	
	}
	
	public void freeNode(Node n){
		if (nodePoolSize < poolMaxSize){
			n.tl = nodePool;
			nodePool = n;
			nodePoolSize++;		
		}else{
			n = null;
		}
	}
	
	public Node makeNode() {
		Node n;
		if (nodePoolSize == 0){
			n = new Node();
		}else{
			n = nodePool;
			nodePool = nodePool.tl;
			nodePoolSize--;
		}
		return n;
	}
	
	public Node releaseNodes(Node p){
		freeNode(p.tl);
		freeNode(p.tr);
		freeNode(p.bl);
		freeNode(p.br);
		return p;
	}

	public class Node {
		public HashSet<NodeElement> elements = new HashSet<NodeElement>();
		public BB box;
		public Node tl = null;
		public Node tr = null;
		public Node bl = null;
		public Node br = null;
		public Node parent = null;
		public boolean leaf = true;
		
		public Node(){
			super();
			this.parent = null;
			this.box = null;
			this.leaf = true;
		}
		
		public Node(Node parent, BB box){
			this.parent = parent;
			this.box = box;
			leaf = true;
		}
		
		public Node setBB(float x,float y,float w,float h){
			this.box = new BB(x,y,w,h);
			return this;
		}
		public Node setParent(Node n){
			this.parent = n;
			return this;
		}
		
		public void hasMoved(NodeElement obj){
			if (!leaf){
				m(obj,": referenced a leaf, that was not a leaf at all!" );
				if (intersects(obj)){
					m(obj,": is in range of the branch it was somehow associated with. Attempting to add it to a leaf");
					pushDown(obj);
				}
			}else{
				if (intersects(obj)){
					m(obj,": is still in range, ignoring hasMoved request");
					return;
				}else{
					m(obj,": is out of range, checking to see if it's in range of the parent branch.");
					if (getParent().intersects(obj)){
						m(obj,": intersects parent branch, trying to re-add it to a child leaf.");
						pushUp(obj);
					}else{
						m(obj,"is out of range of the parent, checking grandparent branch.");
						if (getParent().getParent() == null){
							remove(obj);
							root.add(obj);
							
						}
						if (getParent().getParent().intersects(obj)){
							m(obj,"is in range of the grandparent, trying to add it.");
							getParent().pushUp(obj);
						}else{
							m(obj,"giving up, re-adding to root branch, and removing fromt his branch/leaf");
							remove(obj);
							root.add(obj);
						}
					}
				}
			}
		}

		
		public Node(BB box) {
			this(null, box);
		}


		public void split(){
			if (!leaf)return;
			leaf = false;
			float x = box.min.x;
			float y = box.min.y;
			float w = box.getWidth()/2f;
			float h = box.getHeight()/2f;
			getNodes(this);
			tl.setParent(this).setBB(x, y, w, h).setLeaf(true);
			tr.setParent(this).setBB(x+w, y, w, h).setLeaf(true);
			bl.setParent(this).setBB(x, y+h, w, h).setLeaf(true);
			br.setParent(this).setBB(x+w, y+h, w, h).setLeaf(true);
		}
		
		private Node setLeaf(boolean b) {
			this.leaf = b;
			return this;
		}

		//@SuppressWarnings("unchecked")
		public Node getParent(){
			return this.parent;
		}
		
		public void remove(NodeElement c){
			if (leaf){
				elements.remove(c);
				getParent().unSplit();
			}else{
				tl.remove(c);
				tr.remove(c);
				bl.remove(c);
				br.remove(c); 
			}
		}
		
		public void unSplit(){
			if (leaf)return;
			int sum = 0;
			sum += tl.count() + tr.count() + bl.count() + br.count();
			if (sum < QuadTree.maxElementsPerNode){
				elements.addAll(tl.getAll());
				elements.addAll(tr.getAll());
				elements.addAll(bl.getAll());
				elements.addAll(br.getAll());
				releaseNodes(this);
				for (NodeElement e: elements)e.setLeaf(this);
				leaf = true;
			}
		}
		
		public ArrayList<NodeElement> get(BB box){
			ArrayList<NodeElement> list = new ArrayList<NodeElement>();
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
		
		public boolean intersects(NodeElement e){
			return getBB().intersects(e.getBB());
		}
		public boolean intersects(BB box){
			return getBB().intersects(box);
		}
		
		public ArrayList<NodeElement> getAll(){
			ArrayList<NodeElement> list = new ArrayList<NodeElement>();
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
		
		public void add(NodeElement c){
			if (leaf){
				if ((elements.size()+1) > QuadTree.maxElementsPerNode && (getBB().getWidth() > QuadTree.minWidth)){
					m(c,": can't be held in leaf, too big, splitting this leaf into branch.");
					split();
					Iterator<NodeElement> i = elements.iterator();
					while (i.hasNext()){
						NodeElement e = i.next();
						pushDown(e);
						i.remove();
					}
					pushDown(c);
				}else{
					elements.add(c);
					c.setLeaf(this);
					m(c, "has been added to a leaf node, for real!");
				}
			}else{
				pushDown(c);
			}
		}
		
		public void render(Graphics g){
			if (leaf){
				g.setColor(Color.green);
				float x = getBB().min.x;
				float y = getBB().min.y;
				float w = Math.abs(getBB().max.x - getBB().min.x);
				float h = Math.abs(getBB().max.y - getBB().min.y);
				g.draw(new Rectangle(x,y,w,h)); //TODO remove this, or alter it to provide something more  useful.
				g.drawString("S: " + elements.size(), x+1, y+1);
			}else
			if (!leaf){
				tl.render(g);
				tr.render(g);
				bl.render(g);
				br.render(g);
			}
		}
		
		public void m(NodeElement e, String s){
			//System.err.println(this.toString() + "::" + e.toString() + ": " + s);
		}
		public void pushDown(NodeElement c){
			int i = 0;
			if (tl.intersects(c)){
				i++;
				tl.add(c);
				m(c,": is being added to top left " +  (tl.leaf ? "leaf":"branch"));
			}
			if (tr.intersects(c)){
				i++;
				tr.add(c);
				m(c,": is being added to top right " +  (tr.leaf ? "leaf":"branch"));
			}
			if (bl.intersects(c)){
				i++;
				bl.add(c);
				m(c,": is being added to bottom left " +  (bl.leaf ? "leaf":"branch"));
			}
			if (br.intersects(c)){
				i++;
				br.add(c);
				m(c,": is being added to bottom right " +  (br.leaf ? "leaf":"branch"));
			}
			if (i == 0){
				m(c,": seems to have been lost... attempting to report it as moved...");
				//hasMoved(c);
			}
		}
		public void pushUp(NodeElement c){
			if (getParent() != null){
				remove(c);
				getParent().add(c);
			}else{
				m(c,": seems to have left the world space.");
			}
		}
		
		public BB getBB(){
			return this.box;
		}


		public ArrayList<NodeElement> get(Point p) {
			// TODO Auto-generated method stub
			return null;
		}

		public void unSplitAll() {
	
			if (!leaf){
				if (tl != null)tl.unSplitAll();
				if (tr != null)tr.unSplitAll();
				if (bl != null)bl.unSplitAll();
				if (br != null)br.unSplitAll();
			}else{
				if (getParent() != null){
					verifyElements();
				}
			}
		
		}
		public void verifyElements(){
			Iterator<NodeElement> i = elements.iterator();
			NodeElement e;
			while (i.hasNext()){
				e = i.next();
				if (!intersects(e)){
					i.remove();
				}
			}
			getParent().unSplit();
		}

		

	}
	
	public void unSplitAll(){
		root.unSplitAll();
	}

	@Override
	public void remove(NodeElement obj) {
		root.remove(obj);
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<? extends WorldObject> get(BB box) {
		return (Collection<? extends WorldObject>) ((ArrayList<T>)root.get(box));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ArrayList<T> get(Point p) {
		return ((ArrayList<T>)root.get(p));
	}

	@Override
	public void add(NodeElement obj) {
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



