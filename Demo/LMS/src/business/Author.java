package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String bio;
	public String getBio() {
		return bio;
	}
	
	Author(String f, String l, String t, String bio) {
		super(f, l, t);
		this.bio = bio;
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
