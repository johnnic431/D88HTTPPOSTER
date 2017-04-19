package sylvyrfysh.dupage88.firewallbuster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirewallBusterMain {

	private static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) {
		try{
			doStuff("http://www.gogle.com/");
		}catch(Exception e){}

	}

	private static void doStuff(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine+'\n');
		}
		in.close();
		
		if(responseCode==301){
			int loc=response.toString().indexOf("<A HREF=\"")+"<A HREF=\"".length();
			int end=response.toString().indexOf("\">here</A>.");
			
			String redir=response.toString().substring(loc, end);
			System.out.println(redir);
			doStuff(redir);
		}
	}

}
