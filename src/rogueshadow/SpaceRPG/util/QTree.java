package rogueshadow.SpaceRPG.util;

import java.util.Collection;
import java.util.HashSet;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.NodeElement;

public class QTree {
	
	public Node root = null;
	
	public QTree(BB bb) {
		root = new Node(null, bb);
	}

	public int minimumNodeWidth = 100;
	public int maxElementsPerNode = 2;
	
	public class Node {
		public Node parent;
		public Node tl, tr, bl, br;
		
		public boolean leaf;
		public BB area;
		
		public HashSet<NodeElement> elements;
		
		public Node(Node parent, BB area){
			super();
			this.parent = parent;
			this.area = area;
			this.elements = new HashSet<NodeElement>();
			this.leaf = true;
		}
		
		public Node(){
			this(null, null);
		}
		
		public Node add(NodeElement e){ // returns list of nodes that added the element
			Node n = null;
			
			if (leaf){
				if (elements.size() + 1 > maxElementsPerNode && !(area.getWidth() < (minimumNodeWidth+minimumNodeWidth))){
					split();
					return add(e);
				}else{
					elements.add(e);
					n = this;
				}
			}else{
				n = pushDown(e);
			}
			
			
			return n;
		}
		
		public Node getContainingParent(NodeElement e){
			if (getParent() != null){
				Node n = getParent();
				while (!n.contains(e)){
					if (n.getParent() != null)n = n.getParent();
				}
				return n;
			}
			return this;
		}
		
		public Node hasMoved(NodeElement e){ 
			if (leaf){
				if (!contains(e)){
					remove(e);
					return getContainingParent(e).add(e);
				}else{
					return this;
				}
			}else{
				if (contains(e)){
					remove(e);
					return pushDown(e);
				}else{
					remove(e);
					return getContainingParent(e).add(e);
				}
			}
		}
		
		public Node getParent(){
			return parent;
		}
		
		public void render(Graphics g){
			if (leaf){
				area.render(g);
			}else{
				tl.render(g);
				tr.render(g);
				bl.render(g);
				br.render(g);
			}
		}
		
		public void remove(NodeElement e){ // modifies leaves in NodeElement
			if (leaf){
				elements.remove(e);
			}else{
				tl.remove(e);
				tr.remove(e);
				bl.remove(e);
				br.remove(e);
				unSplit();
			}
		}
		
		public void unSplit(){
			if (count() <= maxElementsPerNode){
				elements.addAll(getAllElements());
				for (NodeElement e: elements)e.setLeaf(this);
				tl = null;
				tr = null;
				bl = null;
				br = null;
				leaf = true;
			}
		}
		
		private HashSet<Node> getNodes() {
			HashSet<Node> list = new HashSet<Node>();
			list.add(this);
			list.add(tl);
			list.add(tr);
			list.add(bl);
			list.add(br);
			return list;
		}
		
		public HashSet<NodeElement> getAllElements(){
			HashSet<NodeElement> list = new HashSet<NodeElement>();
			for (Node n: getNodes())list.addAll(n.elements);
			return list;
		}

		private HashSet<NodeElement> getAll() {
			HashSet<NodeElement> list = new HashSet<NodeElement>();
			list.addAll(tl.getAll());
			list.addAll(tr.getAll());
			list.addAll(bl.getAll());
			list.addAll(br.getAll());
			return list;
		}

		public int count(){
			return (leaf ? elements.size():(tl.count() + tr.count() + bl.count() + br.count()));
		}

		private boolean contains(NodeElement e) {
			return area.intersects(e.getBB());
		}

		private Node pushDown(NodeElement e) {
			Node n = null;
			if (tl == null)return (getContainingParent(e).add(e));
			if (tl.contains(e))n = tl.add(e);
			if (tr.contains(e))n = tr.add(e);
			if (bl.contains(e))n = bl.add(e);
			if (br.contains(e))n = br.add(e);
			if (n == null)n = getParent();
			return n;
		}

		private void split() { // modifies leaves from the NodeElements in elements
			leaf = false;
			float x = area.min.x;
			float y = area.min.y;
			float w = area.getWidth()/2f;
			float h = area.getHeight()/2f;
			tl = new Node(this, new BB(x,y,w,h));
			tr = new Node(this, new BB(x+w,y,w,h));
			bl = new Node(this, new BB(x,y+h,w,h));
			br = new Node(this, new BB(x+w,y+h,w,h));
			
			for (NodeElement e: elements){
				e.setLeaf(pushDown(e));
			}
			elements.clear();
		}

		public HashSet<NodeElement> get(BB bb) {
			if (leaf){
				if (contains(bb)){
					return elements;
				}else{
					return new HashSet<NodeElement>();
				}
			}else{
				HashSet<NodeElement> list = new  HashSet<NodeElement>();
				list.addAll(tl.get(bb));
				list.addAll(tr.get(bb));
				list.addAll(bl.get(bb));
				list.addAll(br.get(bb));
				return list;
			}
		}

		private boolean contains(BB bb) {
			return area.intersects(bb);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends WorldObject> get(BB bb) {
		return (Collection<? extends WorldObject>) root.get(bb);
	}

	public void add(NodeElement obj) {
		obj.setLeaf(root.add(obj));
	}
	
	public int count(){
		return root.count();
	}
}


