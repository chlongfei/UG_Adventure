package adventure;

import java.util.Scanner;
import java.io.StringWriter;
import java.util.ArrayList;

public class Parser{

    private ArrayList<String> commandList;
    private Command returnCommand;
    private StringWriter allCommandList;

    /**
     * Constructor for parser
     */
    public Parser(){
        returnCommand = null;
        allCommandList = null;
        commandList = null;
    }

    /**
     * Analyse user command, ensuring it is valid
     * @param userCommand string line of user command
     * @return Command object for user command
     * @throws InvalidCommandException  invalid command
     */
    public Command parseUserCommand(String userCommand) throws InvalidCommandException{
        arrayCommands(userCommand);
        if (commandList.size() == 0){
            returnCommand = new Command();
        }else if (commandList.size() == 1){
            returnCommand = new Command(commandList.get(0));
        }else if (commandList.size() == 2){
            returnCommand = new Command(commandList.get(0), commandList.get(1));
        }        
        return returnCommand;
    }

    /**
     * Takes line of user command and assigns it into an array list
     * @param userCommand string ilne of user command
     */
    private void arrayCommands(String userCommand){
        Scanner cmdScan = new Scanner(userCommand);
        commandList = new ArrayList<String>();
        while (cmdScan.hasNext()){
            commandList.add(cmdScan.next().trim());
        }
        cmdScan.close();
    }

    /**
     * Outputs all possible commands player can use in this game
     * @return string denoting all commands player can use
     */
    public String allCommands(){
        allCommandList = new StringWriter();
        ArrayList<String> commands = null;
        try{ 
            commands = new Command().getCommands();
        }catch(Exception e){}
        //do nothing-exception will definitly be triggered, but illrelevant.
        for(String eachCommand:commands){
            allCommandList.write(eachCommand + ", ");
        }
        return allCommandList.toString().substring(0, allCommandList.toString().length()-1);
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Parser{"
               + "commandList=" + commandList
               + ", returnCommand=" + returnCommand
               + ", allCommandList=" + allCommandList
               + '}';
    }
}
