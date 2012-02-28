package rogueshadow.SpaceRPG.interfaces;

import rogueshadow.SpaceRPG.util.BB;
import rogueshadow.SpaceRPG.util.QTree.Node;

public interface NodeElement {
	public BB getBB();
	void setLeaf(Node node);
	Node getLeaf();
}
