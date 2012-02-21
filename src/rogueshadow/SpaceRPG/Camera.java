package rogueshadow.SpaceRPG;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.entities.WorldObject;
import rogueshadow.SpaceRPG.util.BB;

//TODO When rotating the world is positions weird, needs fix (if using rotation will ever be needed)
//TODO When the world is smaller than the game window make it centered on the screen. (if that will ever happen also o.O)

public class Camera {
	public static int WIDTH;
	public static int HEIGHT;
	public Vector2f following = new Vector2f(0,0);
	
	public Camera(){
		super();
		WIDTH = Engine.WIDTH;
		HEIGHT = Engine.HEIGHT;
		
	}
	public void translateIn(Graphics g){
		g.pushTransform();
		g.translate(-getX(),-getY());
		
	}	
	public void translateOut(Graphics g){
		g.popTransform();
	}

	public float getX() {
		float x = following.copy().getX() - (WIDTH/2f);
		return x;
	}
	public float getY() {
		float y = following.copy().getY() - (HEIGHT/2f);
		return y;
	}
	public Vector2f getFollowing() {
		return following;
	}
	public void setFollowing(Vector2f following) {
		this.following = following;
	}
	public boolean isVisible(WorldObject obj) {
		return getBB().intersects(obj.getBB());
	}
	public BB getBB() {
		return new BB(getX(), getY(), WIDTH, HEIGHT);
	}
	
}
