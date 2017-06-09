package com.form2bgames.hangoutsscraper.console;

import java.util.ArrayList;

import com.form2bgames.hangoutsscraper.Message;

public class ListCLHandler extends CLHandler {

	@Override
	public String getHandle() {
		// TODO Auto-generated method stub
		return "list";
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return "list";
	}

	@Override
	public ArrayList<Message> handle(ArrayList<Message> messages, String[] toHandle) {
		for(Message m:messages){
			System.out.println(m.toString());
		}
		return messages;
	}

}
