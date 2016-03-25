//         											 PLEASE READ
/**	Commands follow the format: <TimeStamp> <Command> <Argument List><EOL>{separated by spaces} <-- when read from the console, the TimeStamp is prepended to the input line.
 *  This class assumes that this will always occur, otherwise it is an illegal format.
	When the readCommand() matches a 'File' command, it will treat the rest of the input as an address to fetch the file from.
	All commands inside a File must be formated exactly like the console format <TimeStamp> <Command> <Argument List> <EOL>, the only difference is that the File must have TimeStamp written into each line.
	This class will not prepend TimeStamps to any commands, and the Main class should only do so for input read through the console.
	All commands are matched and executed in the command class
 */
package io;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

import chronotimer.ChronoTimer;
import chronotimer.Time;

/** Parses strings and files into executable commands
 */
public class Parser {
	
	private ChronoTimer chronoTimer;
	
	/**
	 * Parses strings and files into executable commands
	 * @param chronoTimer chronotimer to parse the commands for
	 */
	public Parser(ChronoTimer chronoTimer) {
		this.chronoTimer = chronoTimer;
	}

	/**
	 * Attempts to parse the input into a command to be executed
	 * 
	 * @param input the string to be parsed
	 * @return true if successfully parsed, false otherwise.
	 * Doesn't guarantee the command was successfully executed
	 */
	public boolean parse(String input) {
		try {
			String[] buffer = input.split("\\s+");

			LocalTime timeStamp = Time.fromString(buffer[0]);
			String cmdName = buffer[1];
			String[] args = Arrays.copyOfRange(buffer, 2, buffer.length);

			if (cmdName.equals("FILE")) {
				parseFile(args[0]);
			} else {
				chronoTimer.executeCommand(timeStamp, cmdName, args);
			}
			return true;
		} catch (DateTimeParseException | IndexOutOfBoundsException e) {
			System.out.println("Error parsing command: " + input);
		}
		return false;
	}

	/** Reads a file from the specified path and attempts to parse each line into a command
	 * @param path the path of the file to read relative to the src\test\ directory
	 * @return true if file successfully read, false otherwise.
	 * Doesn't guarantee every line was successfully parsed, nor executed
	 */
	public boolean parseFile(String path) {
		try {
			Scanner fileReader = new Scanner(new File("src\\Test\\" + path));
			while (fileReader.hasNextLine()) {
				String nextLine = fileReader.nextLine();
				System.out.println(nextLine);
				parse(nextLine);
			}
			
			fileReader.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error parsing command file");
		}
		return false;
	}
}