package rogueshadow.SpaceRPG;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;


public class World {
	ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
	ArrayList<Renderable> renderObjs = new ArrayList<Renderable>();
	ArrayList<Updatable> updateObjs = new ArrayList<Updatable>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	
	Camera camera = new Camera();

	public World() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void update(int delta){
		for (Updatable obj: updateObjs){
			if (obj.isActive())obj.update(delta);
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
				renderObjs.add((Renderable)obj);
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
		}
		objects.removeAll(removelist);
		
		addlist.clear();
		removelist.clear();
	}

	public Camera getCamera() {
		return camera;
	}
	

}
