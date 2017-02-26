package model;

public class Mother extends Parent {

	private String maidenName;
	private Children children;

	public Mother() {
		super();
	}

	public Mother(String maidenName) {
		super();
		this.maidenName = maidenName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public Children getChildren() {
		return children;
	}

	public void setChildren(Children children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return super.toString() + " maidenName= " + maidenName + " childrenCount=" + getChildren().getCount();
	}
}
