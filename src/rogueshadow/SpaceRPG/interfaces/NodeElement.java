package rogueshadow.SpaceRPG.interfaces;

import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.QuadTree.Node;

public interface NodeElement {
	public BB getBB();
	void setLeaf(Node leaf);
}
