package rogueshadow.SpaceRPG;

import org.newdawn.slick.Graphics;

public interface Renderable {
	public void render(Graphics g);
	public float getCenterX();
	public float getCenterY();
	public float getSize();//TODO primarily using getSize() for isVisible calls right now.
}
