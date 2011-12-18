package rogueshadow.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class StarDust extends AbstractParticle implements Particle {
	float size;
	Color color = new Color(155,155,155,255);

	public StarDust(Vector2f position) {
		super(position, new Vector2f(0,0), 1000);
		this.size = 1;
		setImmortal(true);
	}

	public void update(ParticleEngine engine, int delta){
		super.update(engine, delta);
		setLife(1);
	}
	
	@Override
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(position.getX(), position.getY());
		g.rotate(0, 0, rotation);
		color.a = 1-(life/maxLife);
		g.setColor(color);
		g.drawRect(-size/2, -size/2, size, size);
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
