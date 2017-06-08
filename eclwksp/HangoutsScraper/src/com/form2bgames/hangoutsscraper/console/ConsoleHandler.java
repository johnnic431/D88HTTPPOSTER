package com.form2bgames.hangoutsscraper.console;

import java.io.Console;

public class ConsoleHandler extends Thread{

	private Console cons;

	@Override
	public void run() {
		if ((cons = System.console()) == null) {
			throw new RuntimeException("A console must be present");
		}
		while(true){
			String parse=cons.readLine();
			
		}
	}

}
