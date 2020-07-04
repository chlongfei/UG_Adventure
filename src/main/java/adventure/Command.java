package adventure;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {
    private String action;
    private String noun;
    private static final String[] CMDLIST = {"GO","LOOK","TAKE","INVENTORY","HELP"
                                                ,"EAT","WEAR","TOSS","READ","QUIT"};
    private static final String[] DIRLIST = {"N","E","S","W","UP","DOWN"};
    private static final ArrayList<String> VALIDCOMMANDS = new ArrayList<String>(Arrays.asList(CMDLIST));
    private static final ArrayList<String> VALIDDIRECTIONS = new ArrayList<String>(Arrays.asList(DIRLIST));

    /**
     * Create a command object with default values.  
     * both instance variables are set to null
     * @throws InvalidCommandException  invalid command
     */
    public Command() throws InvalidCommandException{
        this(null,null);
    }

    /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * @throws InvalidCommandException  invalid command
     */
    public Command(String command) throws InvalidCommandException{
        this(command, null);
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what      The second word of the command.
     * @throws InvalidCommandException  invalid command
     */
    public Command(String command, String what) throws InvalidCommandException{
        init(command, what);
        validateWhat(this.action);
    }

    /**
     * Initializes the class resources
     * @param command user entered command
     * @param what user specified second parameter
     */
    private void init(String command, String what){
        this.action = command;
        this.noun = what;
    }

    /**
     * Validates the second user command
     * @param command command user entered
     * @throws InvalidCommandException invalid command
     */
    private void validateWhat(String command) throws InvalidCommandException{
        validateCommand(command);
        validateGo(command);
        validateItemActionables(command);
    }

    /**
     * Validates user command is valid
     * @param command user command
     * @throws InvalidCommandException
     */
    private void validateCommand(String command) throws InvalidCommandException{
        if(!VALIDCOMMANDS.contains(command.toUpperCase())){
            throw new InvalidCommandException("I DON'T UNDERSTAND WHAT YOU SAID. TYPE 'HELP' TO SEE"
                                                + " LIST OF COMMANDS.");
        }
    }

    /**
     * Validates direction user wishes to go
     * @param command
     * @throws InvalidCommandException
     */
    private void validateGo(String command) throws InvalidCommandException{
        if(command.toUpperCase().equals("GO")){
            if (this.noun == null){
                throw new InvalidCommandException("IN WHICH DIRECTION DID YOU WANT TO GO? TRY \"go <direction>\"");
            }else if(!validateDirection(this.noun)){
                throw new InvalidCommandException("THAT IS NOT A VALID DIRECTION. "
                                                    + "YOU CAN TRY 'N','E','S','W','up','down'");
            }
        }
    }

    /**
     * Validates user item action command for right combination
     * @param command user command
     * @throws InvalidCommandException
     */
    private void validateItemActionables(String command) throws InvalidCommandException{
        validateTake(command);
        validateEat(command);
        validateWear(command);
        validateThrow(command);
        validateRead(command);
    }
    /**
     * Validates user has specified an item
     * @param command string indicating 'take' command
     * @throws InvalidCommandException no item specified
     */
    private void validateTake(String command) throws InvalidCommandException{
        if(command.toUpperCase().equals("TAKE")){
            if(this.noun == null) {
                throw new InvalidCommandException("PLEASE SPECIFY WHAT YOU WANT TO TAKE \"take <item>\"");
            }
        }
    }

    /**
     * Validates user has specified an item to eat
     * @param command string indicating 'eat' command
     * @throws InvalidCommandException no item specified
     */
    private void validateEat(String command) throws InvalidCommandException{
        if(command.toUpperCase().equals("EAT")) {
            if(this.noun == null) {
                throw new InvalidCommandException("PLEASE SPECIFY WHAT YOU WANT TO EAT \"eat <item>\"");
            }
        }
    }

    /**
     * Validates user has specified an item to wear
     * @param command string indicating 'wear' command
     * @throws InvalidCommandException no item specified
     */
    private void validateWear(String command) throws InvalidCommandException{
        if(command.toUpperCase().equals("WEAR")){
            if(this.noun == null){
                throw new InvalidCommandException("PLEASE SPECIFY WHAT YOU WANT TO WEAR \"wear< <item>\"");
            }
        }
    }

    /**
     * Validates user has specified item to throw
     * @param command string indicating 'throw' command
     * @throws InvalidCommandException no item specified
     */
    private void validateThrow(String command) throws InvalidCommandException {
        if(command.toUpperCase().equals("TOSS")) {
            if(this.noun == null) {
                throw new InvalidCommandException("PLEASE SPECIFY WHAT YOU WANT TO THROW \"toss <item>\"");
            }
        }
    }


    /**
     * Validates user has specified item to read
     * @param command strnig indicating 'read' command
     * @throws InvalidCommandException no item specified
     */
    private void validateRead(String command) throws InvalidCommandException{
        if(command.toUpperCase().equals("READ")) {
            if (this.noun == null) {
                throw new InvalidCommandException("PLEASE SPECIFY WHAT YOU WANT TO READ \"read <item>\"");
            }
        }
    }


    /**
     * Validates user defined direction is valid
     * @param direction direction user wishes to travel
     * @return true if direction is valid, false otherwise
     */
    private boolean validateDirection(String direction){
        if (VALIDDIRECTIONS.contains(direction.toUpperCase())){
            return true;
        }
        return false;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }

    /**
     * @return list of valid commands
     */
    public ArrayList<String> getCommands(){
        return VALIDCOMMANDS;
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Current commands on deck:\n"
                + "action = " + this.action
                + "noun = " + this.noun;
    }

}
