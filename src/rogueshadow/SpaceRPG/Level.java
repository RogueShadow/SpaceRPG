package rogueshadow.SpaceRPG;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.Bullet;
import rogueshadow.SpaceRPG.entities.Entity;
import rogueshadow.SpaceRPG.entities.Planet;
import rogueshadow.SpaceRPG.entities.PlayerShip;
import rogueshadow.SpaceRPG.entities.Rock;
import rogueshadow.SpaceRPG.entities.Ship;
import rogueshadow.SpaceRPG.entities.Star;

public class Level {
	protected boolean paused = false;
	protected int worldWidth;
	protected int worldHeight;
	protected int maximumUpdateDistance = 2000;
	protected Camera cam = new Camera();
	protected PlayerShip playerShip;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	protected ArrayList<Entity> removeList = new ArrayList<Entity>();
	protected ArrayList<Entity> addList = new ArrayList<Entity>();
	
	public String debug = "";
	
	public void setPlayer(PlayerShip ship){
		playerShip = ship;
	}
	
	public boolean isVisible(Entity entity){	
		return getCamera().isVisible(entity.getCenterX(), entity.getCenterY(), entity.getSize());
	}
	
	public Level(){
		super();
	}
	
	public void update(int delta){
		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			if (e.isActive())e.update(delta);
			if (e instanceof Bullet){
				checkBulletCollisions((Bullet) e);
			}
		}
		
		entities.removeAll(removeList);
		entities.addAll(addList);
		removeList.clear();
		addList.clear();
		
	}
	
	public void checkBulletCollisions(Bullet b){
		int distance = 500;
		float checkedDist = 0;
		for (Entity e: entities){
			if (!((e instanceof Bullet) || (e instanceof PlayerShip))){
				checkedDist = e.getPosition().distance(b.getPosition());
				if (checkedDist < distance){
					float radius = e.getSize()/2;
					radius += b.getSize()/2;
					if (checkedDist < radius){
						e.collided(b);
						b.collided(e);
						break;
					}
				}
			}
		}
	}

	public void render(Graphics g){
		for (int i = 0; i < entities.size(); i++ ){
			Entity e = (Entity) entities.get(i);
			if (isVisible(e)){
				e.setActive(true);
				e.render(g);
			}else{
				if (!e.isPersistent()){
					if (e.getPosition().distanceSquared(this.getPlayer().getPosition()) > maximumUpdateDistance){
						e.setActive(false);
					}else{
						e.setActive(true);
					}
				}
			}
		}
		
	}
	
	public void remove(Entity other){
		other.setDestroyed(true);
		removeList.add(other);
	}
	public void add(Entity other){
		addList.add(other);
	}

	public Camera getCamera() {
		return cam;
	}

	public PlayerShip getPlayer() {
		return playerShip;
	}
	
}
