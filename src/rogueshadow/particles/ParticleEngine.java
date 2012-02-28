package rogueshadow.particles;

import java.util.ArrayList;

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
	public ArrayList<Particle> getParticles(){
		return particles;
	}
	
}
