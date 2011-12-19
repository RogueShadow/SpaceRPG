package rogueshadow.SpaceRPG;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

//TODO When rotating the world is positions weird, needs fix (if using rotation will ever be needed)
//TODO When the world is smaller than the game window make it centered on the screen. (if that will ever happen also o.O)

public class Camera {
	public static final int WIDTH = SpaceRPG.WIDTH;
	public static final int HEIGHT = SpaceRPG.HEIGHT;
	public static int WORLD_WIDTH = SpaceRPG.WORLD_WIDTH;
	public static int WORLD_HEIGHT = SpaceRPG.WORLD_HEIGHT;
	public Vector2f following = new Vector2f(0,0);
	public float rotation = 0;
	public float scale = 1;
	
	public Camera(Vector2f following){
		super();
		this.following = following;
	}
	public Camera(){
		this(new Vector2f(0,0));
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
		if (x < 0){
			x = 0;
		}else
		if (x > ((WORLD_WIDTH - (WIDTH/scale)))){
			x = ((WORLD_WIDTH - (WIDTH/scale)));
		}
		return x;
	}
	public float getY() {
		float y = following.copy().getY() - ((HEIGHT/2f)/scale);
		if (y < 0){
			y = 0;
		}else
		if (y > ((WORLD_HEIGHT - (HEIGHT/scale)))){
			y = ((WORLD_HEIGHT - (HEIGHT/scale)));
			
		}
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
	
}
