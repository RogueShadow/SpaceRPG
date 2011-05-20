package rogueshadow.particles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public interface Particle {
	public void update(ParticleEngine engine, int delta);
	public void render(Graphics g);
	public void setGravity(Vector2f gravity);
}
