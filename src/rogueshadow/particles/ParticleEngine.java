package rogueshadow.particles;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ParticleEngine {
	Vector2f gravity = new Vector2f(0,0);
	ArrayList<Particle> addList = new ArrayList<Particle>();
	ArrayList<Particle> removeList = new ArrayList<Particle>();
	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public ParticleEngine(){
		super();
	}
	
	public void update(int delta){
		for (Particle p: particles)p.update(this, delta);
		particles.addAll(addList);
		particles.removeAll(removeList);
		addList.clear();
		removeList.clear();
	}
	public void render(Graphics g){
		for (Particle p: particles)p.render(g);
	}
	
	public void add(Particle p){
		p.setGravity(gravity);
		addList.add(p);
	}
	public Vector2f getGravity() {
		return gravity;
	}

	public void setGravity(Vector2f gravity) {
		this.gravity = gravity;
	}

	public void remove(Particle p){
		removeList.add(p);
	}
	
	public void addBox(float x, float y){
		BoxParticle b = new BoxParticle(new Vector2f(x,y),new Vector2f(Math.random()*360).scale(16), 1000, 4);
		b.setRotationSpeed(5f);
		add(b);
	}
	public void explosion(float x, float y){
		for (int i = 0; i < 6; i ++){
			BoxParticle b = new BoxParticle(new Vector2f(x,y),new Vector2f(Math.random()*360).scale((float)Math.random()*66), 1000, (float)Math.random()*14);
			b.setRotationSpeed((float)-4 + (float)Math.random()*8);
			b.setColor(new Color(0,255,255));
			add(b);
		}
	}
	
}
