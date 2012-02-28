package rogueshadow.SpaceRPG;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.Grid;


public class World {
	ArrayList<WorldObject> updatelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> visiblelist = new ArrayList<WorldObject>();
	
	Grid<WorldObject> dataStructure = null;
	public long timer = 0;
	
	Camera camera = new Camera();

	public World() {
		super();
	}
	
	public void init(){
		dataStructure = new Grid<WorldObject>(Engine.WORLD_WIDTH,Engine.WORLD_HEIGHT);
	}
	
	public void update(int delta){

		visiblelist = dataStructure.get(Engine.getPlayer().getPoint());
		System.err.println(visiblelist.size());
		

		for (WorldObject u: updatelist){
			u.update(delta);
		}
		for (WorldObject r: visiblelist){
			if (!r.isAlwaysUpdated())r.update(delta);
		}

		
		updateLists();
	}
	

	public void render(Graphics g){
		for (WorldObject r: visiblelist){
			r.render(g);
		}
		for (WorldObject u: updatelist){
			if (getCamera().isVisible(u)){
				u.render(g);
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
			dataStructure.add(obj);
			if (obj.isAlwaysUpdated()){
				updatelist.add(obj);
			}
		}
		
		for (WorldObject obj: removelist){
			dataStructure.remove(obj);

			if (obj.isAlwaysUpdated()){
				updatelist.remove(obj);
			}
		}
		
		addlist.clear();
		removelist.clear();
	}

	public Camera getCamera() {
		return camera;
	}
	

}
