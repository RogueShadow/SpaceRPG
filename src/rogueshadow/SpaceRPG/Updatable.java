package rogueshadow.SpaceRPG;

public interface Updatable {
	public boolean isAlwaysUpdated();
	public void update(int delta);
	public boolean isActive();

}
