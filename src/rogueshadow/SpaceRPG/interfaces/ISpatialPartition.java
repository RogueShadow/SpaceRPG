package rogueshadow.SpaceRPG.interfaces;

import java.util.ArrayList;
import java.util.Collection;

import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.Point;

public interface ISpatialPartition {
	public void remove(NodeElement obj);
	public <T> ArrayList<T> get(Point p);
	public void add(NodeElement obj);
	public int count();
	public <T> Collection<? extends NodeElement> get(BB box);
}
