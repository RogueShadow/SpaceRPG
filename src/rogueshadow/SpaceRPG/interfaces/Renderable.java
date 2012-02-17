package rogueshadow.SpaceRPG.interfaces;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public interface Renderable {
	public void render(Graphics g);
	public float getCenterX();
	public float getCenterY();
	public float getSize();//TODO primarily using getSize() for isVisible calls right now.
	public Shape getShape();
}
