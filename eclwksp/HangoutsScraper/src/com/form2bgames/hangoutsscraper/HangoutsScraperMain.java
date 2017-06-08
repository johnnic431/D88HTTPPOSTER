package com.form2bgames.hangoutsscraper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import com.afollestad.json.Ason;
import com.afollestad.json.AsonArray;
import com.form2bgames.hangoutsscraper.console.ConsoleHandler;

public class HangoutsScraperMain {

	public static void main(String[] args) {
		String file="";
		File fi=new File("res/Hangouts.json"),out=new File("res/out.json");
		long bytes=fi.length();
		System.out.printf("File is %d MB\n",bytes/(1024*1024));
		byte[] fBytes=new byte[(int) bytes];
		try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(fi))){
			for(int i=0;i<fBytes.length;i++){
				fBytes[i]=(byte) bis.read();
				if((i%1024)==0){
					System.out.printf("%d of %d KB read\n",i/1024,bytes/(1024));
				}
			}
			file=new String(fBytes);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Ason hangouts=new Ason(file);
		AsonArray<?> cs=hangouts.getJsonArray("conversation_state");
		
		Participant NO_USER=new Participant("No user","-1");
		
		ArrayList<ArrayList<Message>> cList=new ArrayList<ArrayList<Message>>();
		
		for(int i=0;i<cs.size();i++){
			ArrayList<Message> messageList=new ArrayList<Message>();
			Ason as=(Ason)cs.get(i);
			Ason cv=as.getJsonObject("conversation_state");
			Ason conv=cv.getJsonObject("conversation");
			try(FileWriter fw=new FileWriter(out,false)){
				fw.write(cv.toString(2));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AsonArray<?> participants=conv.getJsonArray("participant_data");
			ArrayList<Participant> ptc=new ArrayList<Participant>();
			for(int u=0;u<participants.size();u++){
				Ason pcp=((Ason) participants.get(u));
				String anme=pcp.getString("fallback_name");
				String id=pcp.getJsonObject("id").getString("gaia_id");
				Participant p=new Participant(anme,id);
				ptc.add(p);
				System.out.println(p.toString());
			}
			AsonArray<?> messages=cv.getJsonArray("event");
			for(int u=0;u<messages.size();u++){
				Ason msg=(Ason)messages.get(u);
				Participant sender=getParticipant(msg.getJsonObject("sender_id").getString("gaia_id"),ptc);
				String msgType=msg.getString("event_type");
				switch((msgType==null?"":msgType)){
				case "RENAME_CONVERSATION":
					messageList.add(new Message(NO_USER,String.format("Chat renamed to %s",msg.getJsonObject("conversation_rename").getString("new_name"))));
					break;
				case "REGULAR_CHAT_MESSAGE":
					Ason cntnt=msg.getJsonObject("chat_message").getJsonObject("message_content");
					String txt="";
					if(cntnt.has("segment")){
						txt=((Ason)cntnt.getJsonArray("segment").get(0)).get("text");
					}else{
						txt="[Pictures not yet supported]";
					}
					
					messageList.add(new Message(sender,txt));
					break;
				case "REMOVE_USER":
					Ason ruser=(Ason)msg.getJsonObject("membership_change").getJsonArray("participant_id").get(0);
					messageList.add(new Message(getParticipant(ruser.getString("gaia_id"),ptc),"[Removed]"));
					break;
				case "ADD_USER":
					Ason auser=(Ason)msg.getJsonObject("membership_change").getJsonArray("participant_id").get(0);
					messageList.add(new Message(getParticipant(auser.getString("gaia_id"),ptc),"[Added]"));
					break;
				case "HANGOUT_EVENT":
					messageList.add(new Message(NO_USER,"[Call of some sort was instantiated]"));
					break;
				case "":
					break;
				default:
					throw new RuntimeException("event_type "+msg.getString("event_type"));
				}
			}
			cList.add(messageList);
		}
		for(ArrayList<Message> m:cList){
			for(Message g:m){
				if(g.getMessage().contains("imgur.com"))
					System.out.println(g.toString());
			}
		}
		new ConsoleHandler().start();
	}

	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private static Participant getParticipant(String string,ArrayList<Participant> ptc) {
		for(Participant s:ptc){
			if(s.getId().equals(string)){
				return s;
			}
		}
		//Google API Service
		
		//https://www.googleapis.com/plus/v1/people/%s?key=AIzaSyC96jCZm6MgJRJmwIoUzoHLt0CEVqUSjZ0
		
		try{
			URL obj = new URL(String.format("https://www.googleapis.com/plus/v1/people/%s?key=AIzaSyC96jCZm6MgJRJmwIoUzoHLt0CEVqUSjZ0", string));
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + obj.toString());
			System.out.println("Response Code : " + responseCode);
			
			if(responseCode!=200){
				Participant o=new Participant("Unknown User",string);
				ptc.add(o);
				return o;
			}
				
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine+'\n');
			}
			in.close();
			
			Ason resp=new Ason(response.toString());
			System.out.println("Got user "+resp.getString("displayName"));
			
			Participant p=new Participant(resp.getString("displayName"),string);
			
			ptc.add(p);
			
			Thread.sleep(500);
			
			return p;
		}catch(Exception e){throw new RuntimeException(e);}
		
		
		
		
		//return new Participant("Unknown User, ID "+string,"0");
		
	}

	//
}
