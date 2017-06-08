package com.form2bgames.hangoutsscraper;

public class Participant {
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public Participant(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	@Override
	public String toString() {
		return "Participant [name=" + name + ", id=" + id + "]";
	}
}
