package rogueshadow.combat2;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Camera {
	public static final int WIDTH = Combat2.WIDTH;
	public static final int HEIGHT = Combat2.HEIGHT;
	public static final int WORLD_WIDTH = Combat2.WORLD_WIDTH;
	public static final int WORLD_HEIGHT = Combat2.WORLD_HEIGHT;
	public Vector2f following;
	public float rotation = 0;
	public float scale = 1;
	
	public Camera(Vector2f following){
		this.following = following;
	}
	public Camera(){
		following = new Vector2f(0,0);
	}
	public void translateIn(Graphics g){
		g.pushTransform();
		g.translate(-getX(),-getY());
		g.rotate(0, 0, rotation);
		g.scale(scale, scale);
	}	
	public void translateOut(Graphics g){
		g.popTransform();
	}

	public float getX() {
		float x = following.copy().getX() - (WIDTH/2f);
		if (x < 0){
			x = 0;
		}else
		if (x > (WORLD_WIDTH - WIDTH)){
			x = (WORLD_WIDTH - WIDTH);
		}
		return x;
	}
	public float getY() {
		float y = following.copy().getY() - (HEIGHT/2f);
		if (y < 0){
			y = 0;
		}else
		if (y > (WORLD_HEIGHT - HEIGHT)){
			y = (WORLD_HEIGHT - HEIGHT);
			
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
