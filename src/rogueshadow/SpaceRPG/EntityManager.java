package rogueshadow.SpaceRPG;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class EntityManager {
	protected boolean paused = false;
	public final int ROCKS = 0;
	public final int BULLETS = 1;
	public final int POWERUPS = 2;
	public final int CHECKS = 3;
	
	protected int count[] = {0,0,0,0};
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
		
		
		for (int i = 0; i < count.length; i++)count[i] = 0;

		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			e.update(this, delta);
		}
		for (int i = 0; i < entities.size(); i++){
			
			Entity entity = (Entity) entities.get(i);
			
			if (entity instanceof Rock)count[ROCKS]++;
			if (entity instanceof Bullet)count[BULLETS]++;
			if (entity instanceof Powerup)count[POWERUPS]++;
			
			for (int j = i+1; j < entities.size(); j++){
				
				Entity other = (Entity) entities.get(j);
				
				CollisionInfo c = entity.collides(other);
				
				setClosestEntities(c);
				
				count[CHECKS]++;
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
		
		if (count[ROCKS] == 0){
		
			
		}
		
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
		if (other instanceof Powerup){
			if (count[POWERUPS] >= Powerup.MAX_POWERUPS)return;
		}
		other.setCam(getGame().cam);
		addList.add(other);
	}
	
	public void generateRocks(int round){
		for (Iterator<Entity> i = entities.iterator(); i.hasNext(); ){
			Entity e = i.next();
			if (e instanceof Rock){
				i.remove();
			}
		}
		ArrayList<Rock> rocks = new ArrayList<Rock>();
		Rock rock;
		float x, y, angle, speed;
		speed = 10f;
		int number = round * 150; // default 150
		int size = 10;
		while (number > 0){
			while (Math.pow(2, size) > number)size--;
			number -= Math.pow(2, size);
			
			x = (float)Math.random()*SpaceRPG.WORLD_WIDTH;
			y = (float)Math.random()*SpaceRPG.WORLD_HEIGHT;
			
			angle = (float)(Math.random()*360);
			rock = new Rock(new Vector2f(x,y), new Vector2f(angle).scale(speed), size);
			rock.setCam(getGame().cam);
			rocks.add(rock);
		}
		entities.addAll( rocks );
		
	}

	public GameContainer getContainer() {
		return container;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public SpaceRPG getGame() {
		return game;
	}

	public void setGame(SpaceRPG game) {
		this.game = game;
	}

	public int getKey(String name){
		return getGame().keyBinds.getKey(name);
	}
}
