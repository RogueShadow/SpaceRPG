package rogueshadow.SpaceRPG;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import rogueshadow.SpaceRPG.entities.Bullet;
import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.Collidable;
import rogueshadow.SpaceRPG.interfaces.Renderable;
import rogueshadow.SpaceRPG.interfaces.Updatable;
import rogueshadow.SpaceRPG.util.QuadTree;


public class World {
	ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
	ArrayList<Renderable> renderObjs = new ArrayList<Renderable>();
	ArrayList<Updatable> updateObjs = new ArrayList<Updatable>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	ArrayList<Collidable> bulletObjs = new ArrayList<Collidable>();
	QuadTree tree = null;
	
	Camera camera = new Camera();

	public World() {
		super();
	}
	
	public void init(){
		tree = new QuadTree(new Rectangle(0,0,Engine.WORLD_WIDTH,Engine.WORLD_HEIGHT));
	}
	
	public void update(int delta){
		ArrayList<Collidable> checks = new ArrayList<Collidable>();
		for (Updatable obj: updateObjs){
			if (obj.isActive()){
				obj.update(delta);
			}
		}
		for (Collidable c: bulletObjs){
			checks.addAll(tree.get(c.getRect()));
			for (Collidable e: checks){
				if (c.getRect().intersects(e.getRect())){
					c.collided(e);
					e.collided(c);
				}
			}
			checks.clear();
		}
		
		
		
		updateLists();
	}
	
	
	public void render(Graphics g){
		for (Renderable obj: renderObjs){
			if (getCamera().isVisible(obj))obj.render(g);
		}
	}

	public void add(WorldObject obj){
		obj.setWorld(this);
		addlist.add(obj);
	}
	
	public void remove(WorldObject obj){
		removelist.add(obj);
	}
	
	public void updateLists(){
		for (WorldObject obj: addlist){
			if (obj instanceof Updatable){
				updateObjs.add((Updatable) obj);
			}
			if (obj instanceof Renderable){
				renderObjs.add((Renderable) obj);
			}
			if (obj instanceof Bullet){
				bulletObjs.add((Collidable) obj);
			}
			if (obj instanceof Collidable && !(obj instanceof Bullet)){
				tree.add((Collidable) obj);
			}
		}
		objects.addAll(addlist);
		
		for (WorldObject obj: removelist){
			if (obj instanceof Updatable){
				updateObjs.remove((Updatable) obj);
			}
			if (obj instanceof Renderable){
				renderObjs.remove((Renderable)obj);
			}
			if (obj instanceof Bullet){
				bulletObjs.remove((Collidable) obj);
			}
			if (obj instanceof Collidable){
				tree.remove((Collidable) obj);
			}
		}
		objects.removeAll(removelist);
		
		addlist.clear();
		removelist.clear();
	}

	public Camera getCamera() {
		return camera;
	}
	

}
