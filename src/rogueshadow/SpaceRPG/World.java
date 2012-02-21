package rogueshadow.SpaceRPG;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.Bullet;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.Collidable;
import rogueshadow.SpaceRPG.interfaces.NodeElement;
import rogueshadow.SpaceRPG.interfaces.Renderable;
import rogueshadow.SpaceRPG.interfaces.Updatable;
import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.QuadTree;


public class World {
	ArrayList<WorldObject> updatelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> visiblelist = new ArrayList<WorldObject>();
	QuadTree<WorldObject> staticTree = null;
	
	Camera camera = new Camera();

	public World() {
		super();
	}
	
	public void init(){
		staticTree = new QuadTree<WorldObject>(new BB(0,0,Engine.WORLD_WIDTH,Engine.WORLD_HEIGHT));

	}
	
	public void update(int delta){
		visiblelist.clear();
		visiblelist = staticTree.get(getCamera().getBB());

		for (WorldObject u: updatelist){
			((Updatable)u).update(delta);
			if (new BB(0,0,Engine.WORLD_WIDTH,Engine.WORLD_HEIGHT).intersects(u.getBB())){}else{remove(u);}//TODO do something about the Player if he crosses the line, or just forget it and remove the line
		}
		for (WorldObject r: visiblelist){
			if (r instanceof Updatable)((Updatable) r).update(delta);
		}
		
		collision();

		updateLists();
	}
	
	public void collision(){
		ArrayList<WorldObject> checks = new ArrayList<WorldObject>();		
		for (WorldObject c: updatelist){
			if (c instanceof Bullet){
				Collidable b = (Collidable) c;
				checks.addAll(staticTree.get(b.getBB()));
				for (WorldObject e: checks){
					if (e instanceof Collidable){
						if (b.getBB().intersects(e.getBB())){
							b.collided((Collidable)e);
							((Collidable)e).collided(b);
						}
					}
				}
				checks.clear();
			}
		}

	}
	
	
	public void render(Graphics g){
		for (NodeElement r: visiblelist){
			if (r instanceof Renderable)((Renderable) r).render(g);
		}
		for (WorldObject u: updatelist){
			if (u instanceof Renderable){
				if (getCamera().isVisible(u)){
					((Renderable) u).render(g);
				}
			}
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
			if (obj instanceof Ship || obj instanceof Bullet){
				updatelist.add(obj);
			}else{
				staticTree.add(obj);
			}
		}
		
		for (WorldObject obj: removelist){
			if (obj instanceof Ship || obj instanceof Bullet){
				updatelist.remove(obj);
			}else{
				staticTree.remove(obj);
			}
		}
		
		addlist.clear();
		removelist.clear();
	}

	public Camera getCamera() {
		return camera;
	}
	

}
