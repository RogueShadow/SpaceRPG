package rogueshadow.SpaceRPG.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import rogueshadow.SpaceRPG.Level;
import rogueshadow.SpaceRPG.Sounds;
import rogueshadow.SpaceRPG.SpaceRPG;


public class Ship extends AbstractEntity implements Entity {
	
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
	
	boolean engineActive = false;
	boolean leftThrusterActive = false;
	boolean rightThrusterActive = false;
	boolean primaryWeaponActive = false;
	boolean secondaryWeaponActive = false;
	boolean spaceBrake = false;
	
	float angle = 0;
	
	public Ship(Level level, Vector2f position) {
		super(level, position, new Vector2f(0,0));
		setSize(10);
	}
	
	public void ShootPrimaryWeapon(){
		Vector2f pos = getPosition().copy();
		pos.add(new Vector2f(getAngle()).scale(20));
		getLevel().add(new Bullet(getLevel(),pos,new Vector2f(this.getAngle()).scale(570).add(getVelocity())));
		SpaceRPG.snd.shot.play();
	}
	
	public void resetControls(){
		setEngineActive(false);
		setLeftThrusterActive(false);
		setRightThrusterActive(false);
		setSpaceBrake(false);
		setPrimaryWeaponActive(false);
		setSecondaryWeaponActive(false);
	}
	
	@Override
	public void update(int delta) {
		
		if (isEngineActive()){
			getVelocity().add(new Vector2f((getAngle())).scale(delta*0.05f*(getEngineStrength()+1)));
			Vector2f pos = getPosition().copy().add(new Vector2f(getAngle()).scale(-15));
			SpaceRPG.getEngine().addBox(pos.getX(), pos.getY());
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
	public void render(Graphics g) {
		g.pushTransform();
		g.translate(getX(), getY());
		g.rotate(0, 0, getAngle()+90);
		g.setColor(Color.magenta);
		g.drawLine(0, -20, -10, 10);
		g.drawLine(0, -20, 10, 10);
		g.drawLine(-10, 10, 10, 10);
		g.popTransform();
	}

	public boolean isEngineActive() {
		return engineActive;
	}


	public void setEngineActive(boolean engineActive) {
		this.engineActive = engineActive;
	}


	public boolean isLeftThrusterActive() {
		return leftThrusterActive;
	}


	public void setLeftThrusterActive(boolean leftThrusterActive) {
		this.leftThrusterActive = leftThrusterActive;
	}


	public boolean isRightThrusterActive() {
		return rightThrusterActive;
	}


	public void setRightThrusterActive(boolean rightThrusterActive) {
		this.rightThrusterActive = rightThrusterActive;
	}


	public boolean isPrimaryWeaponActive() {
		return primaryWeaponActive;
	}


	public void setPrimaryWeaponActive(boolean primaryWeaponActive) {
		this.primaryWeaponActive = primaryWeaponActive;
	}


	public boolean isSecondaryWeaponActive() {
		return secondaryWeaponActive;
	}


	public void setSecondaryWeaponActive(boolean secondaryWeaponActive) {
		this.secondaryWeaponActive = secondaryWeaponActive;
	}


	public boolean isSpaceBrake() {
		return spaceBrake;
	}


	public void setSpaceBrake(boolean spaceBrake) {
		this.spaceBrake = spaceBrake;
	}

	
	public int getMaxShield() {
		return maxShield;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public int getCurrentShield() {
		return currentShield;
	}

	public void setCurrentShield(int currentShield) {
		this.currentShield = currentShield;
	}

	public int getTypeShield() {
		return typeShield;
	}

	public void setTypeShield(int typeShield) {
		this.typeShield = typeShield;
	}

	public int getMaxArmor() {
		return maxArmor;
	}

	public void setMaxArmor(int maxArmor) {
		this.maxArmor = maxArmor;
	}

	public int getCurrentArmor() {
		return currentArmor;
	}

	public void setCurrentArmor(int currentArmor) {
		this.currentArmor = currentArmor;
	}

	public int getTypeArmor() {
		return typeArmor;
	}

	public void setTypeArmor(int typeArmor) {
		this.typeArmor = typeArmor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypePrimaryWeapon() {
		return typePrimaryWeapon;
	}

	public void setTypePrimaryWeapon(int typePrimaryWeapon) {
		this.typePrimaryWeapon = typePrimaryWeapon;
	}

	public int getTypeSecondaryWeapon() {
		return typeSecondaryWeapon;
	}

	public void setTypeSecondaryWeapon(int typeSecondaryWeapon) {
		this.typeSecondaryWeapon = typeSecondaryWeapon;
	}

	public int getStrengthPrimaryWeapon() {
		return strengthPrimaryWeapon;
	}

	public void setStrengthPrimaryWeapon(int strengthPrimaryWeapon) {
		this.strengthPrimaryWeapon = strengthPrimaryWeapon;
	}

	public int getStrengthSecondaryWeapon() {
		return strengthSecondaryWeapon;
	}

	public void setStrengthSecondaryWeapon(int strengthSecondaryWeapon) {
		this.strengthSecondaryWeapon = strengthSecondaryWeapon;
	}

	public int getShipType() {
		return shipType;
	}

	public void setShipType(int shipType) {
		this.shipType = shipType;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public int getEngineStrength() {
		return engineStrength;
	}


	public void setEngineStrength(int engineStrength) {
		this.engineStrength = engineStrength;
	}


	public int getThrusterStrength() {
		return thrusterStrength;
	}


	public void setThrusterStrength(int thrusterStrength) {
		this.thrusterStrength = thrusterStrength;
	}
	@Override
	public void collided(Entity other) {
		// TODO Auto-generated method stub
		
	}


}
