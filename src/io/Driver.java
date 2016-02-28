package io;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import chronotimer.ChronoTimer;
public class Driver {
	public static void main(String[] args){

		ChronoTimer chronoTimer = new ChronoTimer();
		Scanner input = new Scanner(System.in);
		Parser parser = new Parser();
		SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss.s");
		
		// Use the parser to read commands from the console
		while(true){
			
			// Retrieves input command and prepends time label to command passed to parser
			// For testing from file use command: FILE followed by address---> location of testCommands.txt
			String time = ft.format(new Date().getTime());
			ArrayList<Command> commands = parser.parse(time + " " + input.nextLine());
			
			if (commands != null) {
				for (Command command : commands)
					command.execute(chronoTimer);
			}
		}																			
	}
}
