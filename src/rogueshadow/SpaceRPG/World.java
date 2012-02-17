package rogueshadow.SpaceRPG;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.Bullet;
import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.interfaces.Collidable;
import rogueshadow.SpaceRPG.interfaces.Renderable;
import rogueshadow.SpaceRPG.interfaces.Updatable;


public class World {
	ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
	ArrayList<Renderable> renderObjs = new ArrayList<Renderable>();
	ArrayList<Updatable> updateObjs = new ArrayList<Updatable>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	ArrayList<Collidable> bulletObjs = new ArrayList<Collidable>();
	
	Camera camera = new Camera();

	public World() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void update(int delta){
		for (Updatable obj: updateObjs){
			if (obj.isActive()){
				obj.update(delta);
			}
		}

		for (Collidable b: bulletObjs){
			for (WorldObject c: objects){
				if (!(c instanceof Collidable))continue;
				if (b == c)continue;
				if (c.getPosition().distanceSquared(((WorldObject)b).getPosition()) > 1000)continue;
				if (b.intersects((Collidable)c)){
					b.collided((Collidable)c);
					((Collidable)c).collided(b);
				}
				
			}
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
		}
		objects.removeAll(removelist);
		
		addlist.clear();
		removelist.clear();
	}

	public Camera getCamera() {
		return camera;
	}
	

}
