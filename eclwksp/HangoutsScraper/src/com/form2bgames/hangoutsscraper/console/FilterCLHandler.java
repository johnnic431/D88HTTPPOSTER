package com.form2bgames.hangoutsscraper.console;

import java.util.ArrayList;

import com.form2bgames.hangoutsscraper.Message;

public class FilterCLHandler extends CLHandler{

	@Override
	public String getHandle() {
		// TODO Auto-generated method stub
		return "filter";
	}

	@Override
	public String help() {
		return "filter <user|conversation_name|chat_message|conversation_id|reset> [filterwords]";
	}

	@Override
	public ArrayList<Message> handle(ArrayList<Message> messages, String[] toHandle) {
		/*ArrayList<String> filters=new ArrayList<String>();
		boolean inString=false;
		String adding="";
		for(int u=2;u<=toHandle.length;u++){
			
		}*/
		
		if(toHandle[1].equals("reset"))
			return ConsoleHandler.messageMain;
		if(toHandle.length<=2){
			System.out.println(help());
			return messages;
		}
		ArrayList<Message> filtered=new ArrayList<Message>();
		if(toHandle[1].equals("user")){
			for(Message g:messages){
				for(int u=2;u<=toHandle.length-1;u++){
					if(g.getParticipant().getName().toLowerCase().contains(toHandle[u].toLowerCase()))
						filtered.add(g);
				}
				
			}
		}else if(toHandle[1].equals("conversation_name")){
			for(Message g:messages){
				for(int u=2;u<=toHandle.length-1;u++){
					if(g.getcName().toLowerCase().contains(toHandle[u].toLowerCase()))
						filtered.add(g);
				}
				
			}
		}else if(toHandle[1].equals("chat_message")){
			for(Message g:messages){
				for(int u=2;u<=toHandle.length-1;u++){
					if(g.getMessage().toLowerCase().contains(toHandle[u].toLowerCase()))
						filtered.add(g);
				}
				
			}
		}else if(toHandle[1].equals("conversation_id")){
			for(Message g:messages){
				for(int u=2;u<=toHandle.length-1;u++){
					int parse=0;
					try{
						parse=Integer.parseInt(toHandle[u]);
					}catch(Exception e){
						System.out.printf("%s is not a valid integer\n",toHandle[u]);
						return messages;
					}
					if(g.getGroupID()==parse)
						filtered.add(g);
				}
				
			}
		}
		
		return filtered;
	}

}
