package rogueshadow.SpaceRPG.interfaces;

public interface Updatable {
	public boolean isAlwaysUpdated();
	public void update(int delta);
	public boolean isActive();

}
