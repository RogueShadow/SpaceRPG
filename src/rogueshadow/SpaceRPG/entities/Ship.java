package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Engine;
import rogueshadow.SpaceRPG.Sounds;
import rogueshadow.SpaceRPG.interfaces.Renderable;


public class Ship extends MovableObject implements Renderable {
	
	float size;

	int maxShield = 0;

	int currentShield = 0;

	int typeShield = 0;

	int maxArmor = 0;

	int currentArmor = 0;
	int typeArmor = 0;
	
	String name = "";
	int typePrimaryWeapon = 0;
	int typeSecondaryWeapon = 0;
	int strengthPrimaryWeapon = 0;
	int strengthSecondaryWeapon = 0;
	int shipType = 0;
	int engineStrength = 0;
	int thrusterStrength = 0;
	int topSpeedLvl = 0;
	boolean engineActive = false;
	boolean leftThrusterActive = false;
	boolean rightThrusterActive = false;
	boolean primaryWeaponActive = false;
	boolean secondaryWeaponActive = false;
	boolean spaceBrake = false;
	
	float angle = 0;

	public Ship(float x, float y) {
		super(x,y);
		generateShape();
		size = 10f;
	}

	public float getAngle() {
		return angle;
	}
	public int getCurrentArmor() {
		return currentArmor;
	}
	public int getCurrentShield() {
		return currentShield;
	}
	public int getEngineStrength() {
		return engineStrength;
	}
	
	public int getMaxArmor() {
		return maxArmor;
	}
	
	public int getMaxShield() {
		return maxShield;
	}
	
	public String getName() {
		return name;
	}
	
	public int getShipType() {
		return shipType;
	}

	public float getSize() {
		return size;
	}

	public int getStrengthPrimaryWeapon() {
		return strengthPrimaryWeapon;
	}


	public int getStrengthSecondaryWeapon() {
		return strengthSecondaryWeapon;
	}


	public int getThrusterStrength() {
		return thrusterStrength;
	}


	public int getTopSpeedLvl() {
		return topSpeedLvl;
	}


	public int getTypeArmor() {
		return typeArmor;
	}


	public int getTypePrimaryWeapon() {
		return typePrimaryWeapon;
	}


	public int getTypeSecondaryWeapon() {
		return typeSecondaryWeapon;
	}


	public int getTypeShield() {
		return typeShield;
	}


	public boolean isEngineActive() {
		return engineActive;
	}

	
	public boolean isLeftThrusterActive() {
		return leftThrusterActive;
	}

	public boolean isPrimaryWeaponActive() {
		return primaryWeaponActive;
	}

	public boolean isRightThrusterActive() {
		return rightThrusterActive;
	}

	public boolean isSecondaryWeaponActive() {
		return secondaryWeaponActive;
	}

	public boolean isSpaceBrake() {
		return spaceBrake;
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.translate(getX(), getY());
		g.rotate(0, 0, getAngle()+90);
		g.setColor(new Color(0x808080));
		g.draw(getShape());
		g.popTransform();
	}

	public void resetControls(){
		setEngineActive(false);
		setLeftThrusterActive(false);
		setRightThrusterActive(false);
		setSpaceBrake(false);
		setPrimaryWeaponActive(false);
		setSecondaryWeaponActive(false);
	}

	private void generateShape() {
		Polygon p = new Polygon();
		p.addPoint(0, -20);
		p.addPoint(10,10);
		p.addPoint(-10, 10);
		p.setClosed(true);
		p.setCenterX(0);
		p.setCenterY(0);
		setShape(p);
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setCurrentArmor(int currentArmor) {
		this.currentArmor = currentArmor;
	}

	public void setCurrentShield(int currentShield) {
		this.currentShield = currentShield;
	}

	public void setEngineActive(boolean engineActive) {
		this.engineActive = engineActive;
	}

	public void setEngineStrength(int engineStrength) {
		this.engineStrength = engineStrength;
	}

	public void setLeftThrusterActive(boolean leftThrusterActive) {
		this.leftThrusterActive = leftThrusterActive;
	}

	public void setMaxArmor(int maxArmor) {
		this.maxArmor = maxArmor;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrimaryWeaponActive(boolean primaryWeaponActive) {
		this.primaryWeaponActive = primaryWeaponActive;
	}

	public void setRightThrusterActive(boolean rightThrusterActive) {
		this.rightThrusterActive = rightThrusterActive;
	}

	public void setSecondaryWeaponActive(boolean secondaryWeaponActive) {
		this.secondaryWeaponActive = secondaryWeaponActive;
	}

	public void setShipType(int shipType) {
		this.shipType = shipType;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void setSpaceBrake(boolean spaceBrake) {
		this.spaceBrake = spaceBrake;
	}

	public void setStrengthPrimaryWeapon(int strengthPrimaryWeapon) {
		this.strengthPrimaryWeapon = strengthPrimaryWeapon;
	}

	public void setStrengthSecondaryWeapon(int strengthSecondaryWeapon) {
		this.strengthSecondaryWeapon = strengthSecondaryWeapon;
	}

	public void setThrusterStrength(int thrusterStrength) {
		this.thrusterStrength = thrusterStrength;
	}

	public void setTopSpeedLvl(int topSpeedLvl) {
		this.topSpeedLvl = topSpeedLvl;
	}


	public void setTypeArmor(int typeArmor) {
		this.typeArmor = typeArmor;
	}


	public void setTypePrimaryWeapon(int typePrimaryWeapon) {
		this.typePrimaryWeapon = typePrimaryWeapon;
	}


	public void setTypeSecondaryWeapon(int typeSecondaryWeapon) {
		this.typeSecondaryWeapon = typeSecondaryWeapon;
	}
	public void setTypeShield(int typeShield) {
		this.typeShield = typeShield;
	}

	public void ShootPrimaryWeapon(){
		Vector2f pos = getPosition().copy();
		pos.add(new Vector2f(getAngle()).scale(25));
		getWorld().add(new Bullet(pos.x, pos.y,new Vector2f(this.getAngle()).scale(570).add(getVelocity()),this));
		Sounds.shot.play();
	}

	@Override
	public void update(int delta) {
		
		if (isEngineActive()){

			getVelocity().add(new Vector2f((getAngle())).scale(delta*0.05f*(getEngineStrength()+1)));
			
			if (getVelocity().lengthSquared() > (getTopSpeedLvl()*10000)+10000)getVelocity().scale(0.95f);
			
			Vector2f pos = getPosition().copy().add(new Vector2f(getAngle()).scale(-15));
			Engine.getEngine().addBox(pos.getX(), pos.getY());
		}
		if (isLeftThrusterActive()){
			setAngle(getAngle() - (delta*0.05f*(1 + getThrusterStrength())));
		}
		if (isRightThrusterActive()){
			setAngle(getAngle() + (delta*0.05f*(1 + getThrusterStrength())));
		}
		if (isSpaceBrake()){
			getVelocity().scale(0.95f);
		}
		
		super.update(delta);
		
	}

	@Override
	public float getCenterX() {
		return getX()+getSize()/2f;
	}

	@Override
	public float getCenterY() {
		return getY()+getSize()/2f;
	}


}
