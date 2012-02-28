package rogueshadow.SpaceRPG.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.NodeElement;

public class QuadTree implements ISpatialPartition {
	public class Node {
		public HashSet<NodeElement> elements = new HashSet<NodeElement>();
		public Node tl = null;
		public Node tr = null;
		public Node bl = null;
		public Node br = null;
		public boolean leaf = true;
		public Node parent = null;
		public BB box;
		
		public Node(){
			this(null,null);
		}
		
		public Node(BB box) {
			this(null, box);
		}
		
		public Node(Node parent, BB box){
			super();
			this.parent = parent;
			this.box = box;
			leaf = true;
		}
		public void add(NodeElement c){ // return true if actually added
			if (leaf){
				if ((elements.size()+1) > QuadTree.maxElementsPerNode && (getBB().getWidth() > QuadTree.minWidth)){
					split(c);
					pushDown(c);
				}else{
					c.setLeaf(this);
					elements.add(c);
					
				}
			}else{
				pushDown(c);
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

		
		public ArrayList<NodeElement> get(BB box){
			ArrayList<NodeElement> list = new ArrayList<NodeElement>();
			if (leaf){
				list.addAll(elements);
				return list;
			}else{
				if (tl.intersects(box))list.addAll(tl.get(box));
				if (tr.intersects(box))list.addAll(tr.get(box));
				if (bl.intersects(box))list.addAll(bl.get(box));
				if (br.intersects(box))list.addAll(br.get(box));
			}
			return list;
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
		
		public BB getBB(){
			return this.box;
		}

		//@SuppressWarnings("unchecked")
		public Node getParent(){
			return this.parent;
		}
		
		public void hasMoved(NodeElement obj){
			if (!leaf){
				if (intersects(obj)){
					pushDown(obj);
				}
			}else{
				Node n = this;
				while (!n.intersects(obj)){
					if (n.getParent() != null){
						n = n.getParent();
					}else{
						break;
					}
				}

				if (n.getParent() == null){
					if (!n.intersects(obj)){
						System.err.println("Object out of world.");
						return;
					}
				}
				
				n.remove(obj);
				n.add(obj);
			}
		}
		
		public boolean intersects(BB box){
			return getBB().intersects(box);
		}
		
		public boolean intersects(NodeElement e){
			if (getBB() == null){
				System.err.println("Node has a null box");
				System.err.println("Is node a leaf? yes?: " + leaf);
				if (getParent() == null){
					System.err.println("Node Parent is null.");
				}else{
					if (getParent().getBB() == null){
						System.err.println("Parent has a null box..");
					}
				}
				
			}
			if (e.getBB() == null){
				System.err.println("Node Element has the null box");
			}
			return getBB().intersects(e.getBB());
		}
		
		public void m(NodeElement e, String s){
			System.err.println(this.toString() + "::" + e.toString() + ": " + s);
			
		}
		public void pushDown(NodeElement c){ // return true for added, false for having to split instead.
			int i = 0;
			if (tl.intersects(c)){
				i++;
				tl.add(c);
			}
			if (tr.intersects(c)){
				i++;
				tr.add(c);
			}
			if (bl.intersects(c)){
				i++;
				bl.add(c);
			}
			if (br.intersects(c)){
				i++;
				br.add(c);
			}
			if (i == 0){
				System.err.println("Tried to push object into branch it  didn't fit in. Curious...");
				
				if (root.intersects(c))root.add(c); else System.out.println("Didn't seem to be in the world Space");
			}
		}
		

		public void remove(NodeElement c){
			if (leaf){
				elements.remove(c);
				if (getParent() != null)getParent().unSplit();
			}else{
				tl.remove(c);
				tr.remove(c);
				bl.remove(c);
				br.remove(c); 
			}
		}
		
		public void render(Graphics g){
			if (leaf){
				if (box == null){
					System.err.println("Tried to render a Node without a box? Not right ..");
					return;
				}
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
		

		public Node setBB(float x,float y,float w,float h){
			this.box = new BB(x,y,w,h);
			if (this.box == null)System.err.println("Box created a null box.");
			
			return this;
		}
   
		public Node setParent(Node n){
			this.parent = n;
			return this;
		}
		public void split(NodeElement obj){
			if (!leaf){
				rogueshadow.SpaceRPG.Log.error(this.getClass().getName(), "Tried to split a branch again.");
				return;
			}
			
			leaf = false;
			float x = box.min.x;
			float y = box.min.y;
			float w = box.getWidth()/2f;
			float h = box.getHeight()/2f;
			if (box == null)System.err.println("The box is null, not good!");
			if ( w == 0 || h == 0){
				System.err.println("Something is wrong with the new box.");
			}
			getNodes(this);
			tl.setBB(x, y, w, h);
			tr.setBB(x+w, y, w, h);
			bl.setBB(x, y+h, w, h);
			br.setBB(x+w, y+h, w, h);
			
			Iterator<NodeElement> i = elements.iterator();
			while (i.hasNext()){
				NodeElement e = i.next();
				pushDown(e);
				i.remove();
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
				for (NodeElement e: elements)e.setLeaf(this);
				tl.elements.clear();
				tr.elements.clear();
				bl.elements.clear();
				br.elements.clear();
				releaseNodes(this);	
				leaf = true;
			}
		}

		public void searchRemove(NodeElement e) {
			Node n = this;
			while (n.intersects(e)){
				if (n.getParent() != null)n = n.getParent();
				if (n.getBB() == null)break;
			}
			n.remove(e);            
		}		
	}
	
	public static int maxElementsPerNode = 2;
	public static int minWidth = 100;
	private Node nodePool = new Node();
	private int nodePoolSize = 0;
	private int poolMaxSize = 100;
	
	public Node root;	
	public QuadTree (BB box){
		root = new Node(null, box);
		getNodes(root);
	}
	
	public QuadTree (float x, float y, float width, float height){
		this(new BB(x,y,width,height));
	}
	
	@Override
	public void add(NodeElement obj) {
		root.add(obj);
	}
	
	@Override
	public int count(){
		return root.count();
	}
	
	public void freeNode(Node n){
		if (nodePoolSize < poolMaxSize){
			n.tl = null; n.tr = null; n.br = null; n.bl = null;
			n.parent = null;
			n.leaf = true;
			if (!n.elements.isEmpty()){
				System.err.println("Why isn't the node being freed empty?");
				for (NodeElement e: n.elements)e.setLeaf(null);
			}
			n.elements.clear();
			n.box = null;
			n.tl = nodePool;
			nodePool = n;
			nodePoolSize++;		
		}else{
			n = null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<? extends NodeElement> get(BB box) {
		return (Collection<? extends WorldObject>) ((ArrayList<T>)root.get(box));
	}
	
	@Override
	public <T> ArrayList<T> get(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	public void getNodes(Node p){
		p.tl = makeNode().setParent(p);
		p.tr = makeNode().setParent(p);
		p.bl = makeNode().setParent(p);
		p.br = makeNode().setParent(p);
	
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
	
	@Override
	public void remove(NodeElement obj) {
		root.remove(obj);
	}
	
	public void render(Graphics g){
		root.render(g);
	}

}



