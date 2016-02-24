//         											 PLEASE READ
    /**	Commands follow the format: <TimeStamp> <Command> <Argument List><EOL>{separated by spaces} <-- when read from the console, the TimeStamp is prepended to the input line.
     *  This class assumes that this will always occur, otherwise it is an illegal format.
    	When the readCommand() matches a 'File' command, it will treat the rest of the input as an address to fetch the file from.
    	All commands inside a File must be formated exactly like the console format <TimeStamp> <Command> <Argument List> <EOL>, the only difference is that the File must have TimeStamp written into each line.
    	This class will not prepend TimeStamps to any commands, and the Main class should only do so for input read through the console.  
    	The 'File' command read from a file is treated as illegal.
    	All commands are matched by readCommand() and executed <--- I can't be more specific about this since we haven't written a main or decided how we're going to generate events and store information.
 		------------- Black Ranger
     */
package io;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommandParser {
	private String [] consoleLine;
	private Pattern timePattern;
	private Matcher match;
	
    public CommandParser(){
    	String regex = "\\d+:\\d{2,2}:\\d{2,2}\\.\\d{1,3}";	//<------------------ DO NOT MODIFY
		timePattern = Pattern.compile(regex);
    }
    
    /**	reads all Strings from console
     *  @param input, the String to be parsed, calls readCommand() if legal String(format and contents)
     */
    public void parse(String input){
        if(input.isEmpty()){
        	System.out.println("No command found");
            return;
        }
        consoleLine = input.split("\\s+");
        if(consoleLine.length <= 1){
        	System.out.println("Illegal command format");
        	return;
        }
        if(!checkTime(consoleLine[0])){
        	System.out.println("Illegal time format");
        	return;
        }
        readCommand(consoleLine);
    }
    
   
    private void fileCommandTest(String [] command){	
      for(String i : command){
    	  System.out.print(i + " ");
      }
      
    }
  
    
    
    /**	parses commands 
     *	@param command, an array of String splits by space: <TimeStamp> <Command> <EOL>
     *	matches command[1] to command cases, executes command if legal
     *	calls readFile() in case 'File'
     */
    private void readCommand(String [] command){
    	switch (command[1]){
    		case "FILE":
    			readFile(command[2]);
    			break;
    		case "FINISH":
    			fileCommandTest(command);
    			//finish 
    			break;
    		case "START":	
    			fileCommandTest(command);
    			//start 
    			break;
    		case "CANCEL":    		
    			fileCommandTest(command);
    			//cancel
    			break;
    		case "ON":   	
    			fileCommandTest(command);
    			//on 
    			break;
    		case "OFF":    		
    			fileCommandTest(command);
    			//off
    			break;
    		case "DNF":   	
    			fileCommandTest(command);
    			//DNF
    			break;
    		case "RESET":    			
    			fileCommandTest(command);
    			//reset 
    			break;
    		case "PRINT":
    			fileCommandTest(command);
    			//print
    			break;
    		case "TRIG":
    			if(!trigNumToggle(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//trig @param command[2]
    			break;
    		case "NUM" :
    			if(!trigNumToggle(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//num @param command[2]
    			break;
    		case "ENDRUN":
    			//endrun
    			fileCommandTest(command);
    			break;
    		case "NEWRUN":
    			fileCommandTest(command);
    			//newrun
    			break;
    		case "EXIT":
    			fileCommandTest(command);
    			//exit
    			break;
    		case "EVENT":
    			if(!EVENT(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//event @param command[2]
    			break;
    		case "TIME":
    			if(!TIME(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//TIME pass @param command[2]
    			break;
    		case "TOGGLE":
    			if(!trigNumToggle(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//toggle @param command[2]
    			break;
    		case "CONN":
    			if(!CONN(command)){
    				break;
    			}
    			fileCommandTest(command);
    			//conn @param command[2] and command[3]
   				break;
    		default:
    			System.out.println("Unrecognized command. Please try again");
    			break;
    	}
    }
    
    /**	reads a file from given address
     * 	@param input: address of file location
     * 	Reads commands from the file, one line at a time
     * 	calls readCommand() on lines with correctly formatted commands
     */
    private void readFile(String input){
        Scanner fileReader = null;
        String [] command;
        try {
            fileReader = new Scanner(Paths.get(input));
        } catch (IOException e) {
            System.out.println("File not found");
            return;
        }
        while(fileReader.hasNextLine()){
            String token = fileReader.nextLine();
            if(token.isEmpty()){
                continue;
            }
            command = token.split("\\s+");
            if(command.length <= 1){
            	System.out.println("Illegal command format in FILE");
            	continue;
            }
            if(!checkTime(command[0])){
            	System.out.println("Illegal time format");
            	continue;
            }
            if(command[1].equals("File")){System.out.println("Illegal command in FILE: File"); continue;}
            readCommand(command);
        }
        fileReader.close();
    }
    
    /**
     * @param time: time to check for correct format
     */
    private boolean checkTime(String time){
    	match = timePattern.matcher(time);
    	return match.find();
    }
    
    /**@param command [] : check format
     * 
     */
    private boolean trigNumToggle(String [] command){
    	if(!checkSize3(command)){
    		return false;
    	}
		if(!command[2].matches("\\d+")){
			System.out.println("Illegal command format: Number: NaN");
			return false;
		}
		return true;
    }
    
    /**@param command [] : check format
     * 
     */
    private boolean TIME(String [] command){
    	if(!checkSize3(command)){
    		return false;
    	}
		if(!checkTime(command[2])){
			System.out.println("Time format illegal");
			return false;
		}
		return true;
    }
    
    /**@param command [] : check format
     * 
     */
    private boolean CONN(String [] command){
    	if(!(command.length == 4)){
			System.out.println("Illegal command format: CONN");
			return false;
		}
		if(!(command[2].equals("GATE") || command[2].equals("EYE"))){
			System.out.println("Illegal CONN command: (must be GATE OR EYE)"); 
			return false;
		}
		if(!(command[3].matches("\\d+"))){
			System.out.println("Illegal command format: Number: NaN");
			return false;
		}
		return true;
    }
    
    /**@param command [] : check format
     * 
     */
    private boolean EVENT(String [] command){
    	if(!checkSize3(command)){
    		return false;
    	}
    	if(!(command[2].equals("IND"))){
    		System.out.println("Illegal command: EVENT : EVENT TYPE");
    		return false;
    	}
    	return true;
    }
    
    /**@param command [] : check format
     * 
     */
    private boolean checkSize3(String [] command){
    	if(!(command.length == 3)){
			System.out.println("Illegal command format: TIME");
			return false;
		}
    	return true;
    }
}