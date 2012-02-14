package rogueshadow.SpaceRPG;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

//TODO When rotating the world is positions weird, needs fix (if using rotation will ever be needed)
//TODO When the world is smaller than the game window make it centered on the screen. (if that will ever happen also o.O)

public class Camera {
	public static int WIDTH;
	public static int HEIGHT;
	public Vector2f following = new Vector2f(0,0);
	public float rotation = 0;
	public float scale = 1;
	
	public Camera(){
		super();
		WIDTH = Engine.WIDTH;
		HEIGHT = Engine.HEIGHT;
		
	}
	public void translateIn(Graphics g){
		g.pushTransform();
		g.scale(scale, scale);
		g.translate(-getX(),-getY());
		g.rotate(following.getX(), following.getY(), rotation);
		
	}	
	public void translateOut(Graphics g){
		g.popTransform();
	}

	public float getX() {
		float x = following.copy().getX() - ((WIDTH/2f)/scale);
		return x;
	}
	public float getY() {
		float y = following.copy().getY() - ((HEIGHT/2f)/scale);
		return y;
	}
	public Vector2f getFollowing() {
		return following;
	}
	public void setFollowing(Vector2f following) {
		this.following = following;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public boolean isVisible(float centerX, float centerY, float size) {
		float x = centerX - getX();
		float y = centerY - getY();
		if (Math.abs(x) < WIDTH + size && Math.abs(y) < HEIGHT + size){
			return true;
		}
		return false;
	}
	
}
