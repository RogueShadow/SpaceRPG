package rogueshadow.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Art;

public class DebriParticle extends AbstractParticle implements Particle {
	float size;
	public float scale;
	Color color = new Color(155,155,155,255);

	public DebriParticle(Vector2f position, Vector2f velocity, float life, float size) {
		super(position, velocity, life);
		this.size = size;
	}

	public void update(ParticleEngine engine, int delta){
		super.update(engine, delta);
	
	}
	
	public float life(){
		return (1 - (life/maxLife));
	}
	
	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(position.getX(), position.getY());
		//g.rotate(0, 0, rotation);
		g.scale(getSize()*life(), getSize()*life());

		//g.drawRect(-size/2, -size/2, size, size);
		Art.debri.drawCentered(0, 0);
		g.popTransform();
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
