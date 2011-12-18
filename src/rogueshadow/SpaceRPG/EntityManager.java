package rogueshadow.SpaceRPG;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class EntityManager {
	protected boolean paused = false;

	protected GameContainer container;
	protected SpaceRPG game;
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	protected ArrayList<Entity> removeList = new ArrayList<Entity>();
	protected ArrayList<Entity> addList = new ArrayList<Entity>();
	
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	public void togglePaused(){
		this.paused = !this.paused;
	}
	
	public String debug = "";
	
	public EntityManager(GameContainer container, SpaceRPG game){
		this.container = container;
		this.game = game;
	}
	
	public void update(int delta){
		if (isPaused())return;

		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			e.update(this, delta);
		}
		for (int i = 0; i < entities.size(); i++){
			
			Entity entity = (Entity) entities.get(i);
			
			for (int j = i+1; j < entities.size(); j++){
				
				Entity other = (Entity) entities.get(j);
				
				CollisionInfo c = entity.collides(other);
				
				setClosestEntities(c);
				
				if (c.isCollided()){
					entity.collided(this, other);
					other.collided(this, entity);
				}
				
			}
		}
		
		entities.removeAll(removeList);
		entities.addAll(addList);
		removeList.clear();
		addList.clear();
		
	}
	
	private void setClosestEntities(CollisionInfo c) {
		Rock rock;
		Bullet bullet;
		if (c.entity instanceof Rock && c.other instanceof Bullet){
			bullet = (Bullet)c.other;
			if (!(bullet.getHoming() > 0))return;
			rock = (Rock)c.entity;
		}else
		if(c.entity instanceof Bullet && c.other instanceof Rock){
			bullet = (Bullet)c.entity;
			if (!(bullet.getHoming() > 0))return;
			rock = (Rock)c.other;
		}else return;
		
		if (c.distance < bullet.closest_dist){
			bullet.closest = rock;
			bullet.closest_dist = c.distance;
		}
	}

	public void render(Graphics g){
		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			e.render(g);
		}
		
	}
	
	public void remove(Entity other){
		other.setDestroyed(true);
		removeList.add(other);
	}
	public void add(Entity other){
		other.setCam(getGame().cam);
		addList.add(other);
	}

	public GameContainer getContainer() {
		return container;
	}

	public SpaceRPG getGame() {
		return game;
	}
}
