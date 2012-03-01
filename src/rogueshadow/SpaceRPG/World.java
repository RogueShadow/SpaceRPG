package rogueshadow.SpaceRPG;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import rogueshadow.SpaceRPG.entities.Bullet;
import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.util.Grid;
import rogueshadow.utility.Point;


public class World {
	ArrayList<WorldObject> updatelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> addlist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> removelist = new ArrayList<WorldObject>();
	ArrayList<WorldObject> visiblelist = new ArrayList<WorldObject>();
	
	Grid dataStructure = null;
	public long timer = 0;
	
	Camera camera = new Camera();

	public World() {
		super();
	}
	
	public void init(){
		dataStructure = new Grid(Engine.WORLD_WIDTH,Engine.WORLD_HEIGHT);
	}
	
	public void update(int delta){

		visiblelist = dataStructure.get(getCamera().getBB());

		for (WorldObject u: updatelist){
			u.update(delta);
		}
		for (WorldObject r: visiblelist){
			if (!r.isAlwaysUpdated())r.update(delta);
		}

		collision();
		updateLists();
	}
	

	private void collision() {
		ArrayList<WorldObject> checks;
		for (WorldObject obj: updatelist){
			if (obj instanceof Bullet){
				checks = dataStructure.get(obj.getPoint());
				for (WorldObject o: checks){
					if (obj.getBB().intersects(o.getBB())){
						obj.collided(o);
						o.collided(obj);
					}
				}
				checks.clear();
			}
		}
	}
	

	public void render(Graphics g){
		for (WorldObject r: visiblelist){
			r.render(g);
		}
//		for (WorldObject u: updatelist){
//			if (getCamera().isVisible(u)){
//				u.render(g);
//			}
//		}
	}

	public void add(WorldObject obj){
		obj.setWorld(this);
		addlist.add(obj);
	}
	
	public void remove(WorldObject obj){
		obj.removed = true;
		removelist.add(obj);
	}
	
	public void updateLists(){
		for (WorldObject obj: addlist){
			if (!dataStructure.add(obj)){
				System.err.println(obj.toString() + " wasn't added to the grid somehow. Loc: " + obj.getPoint().toString());
			}else{
				if (obj.isAlwaysUpdated()){
					updatelist.add(obj);
				}
			}
		}
		
		for (WorldObject obj: removelist){
			if (!dataStructure.remove(obj)){
				System.err.println(obj.toString() + " wasn't removed from grid.");
			}

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

	public void hasMoved(Point oldPos, WorldObject obj) {
		if (obj.removed)return;
		int checkx = dataStructure.getCellx(oldPos.x);
		int checky = dataStructure.getCelly(oldPos.y);
		int checkx2 = dataStructure.getCellx(obj.getPoint().x);
		int checky2 = dataStructure.getCelly(obj.getPoint().y);
		if (checkx == checkx2 && checky == checky2){
			return;
		}else{
			dataStructure.move(checkx, checky, checkx2, checky2, obj);
		}
	}
	

}
