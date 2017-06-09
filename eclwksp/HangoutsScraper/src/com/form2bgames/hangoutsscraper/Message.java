package com.form2bgames.hangoutsscraper;

public class Message {
	private Participant part;
	private String message,cName;
	private int groupID;
	public Participant getParticipant() {
		return part;
	}
	@Override
	public String toString() {
		String convName=cName.substring(0, (50<cName.length()?50:cName.length()));
		String userDispName=part.getName().substring(0, (30<part.getName().length()?30:part.getName().length()));
		return String.format("%-3d: %-50s: %-30s: %s",groupID,convName,userDispName,message);
	}
	public Message(Participant part, String message, String cName,int groupID) {
		super();
		this.part = part;
		this.message = message;
		this.cName = cName;
		this.groupID = groupID;
	}
	public String getMessage() {
		return message;
	}
	public String getcName() {
		return cName;
	}
	public int getGroupID() {
		return groupID;
	}
}
