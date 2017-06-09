package com.form2bgames.hangoutsscraper.console;

import java.util.ArrayList;

import com.form2bgames.hangoutsscraper.Message;

public abstract class CLHandler {
	public abstract String getHandle();
	public abstract String help();
	public abstract ArrayList<Message> handle(ArrayList<Message> messages,String[] toHandle);
}
