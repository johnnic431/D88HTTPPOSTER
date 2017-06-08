package com.form2bgames.hangoutsscraper;

public class Message {
	private Participant part;
	private String message;
	public Participant getParticipant() {
		return part;
	}
	@Override
	public String toString() {
		return String.format("%s: %s",part.getName(),message);
	}
	public Message(Participant part, String message) {
		super();
		this.part = part;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
