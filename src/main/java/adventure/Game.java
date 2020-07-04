package adventure;

import java.io.StringWriter;
import java.util.Scanner;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* EXCEPTIONS */
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/* JSON Stuff */
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static java.lang.Character.toUpperCase;

//


public class Game{

    /* this is the class that runs the game.
    You may need some member variables */
    
    private String pathToFile;
    private Adventure currentAdventure; //ref to the current adventure object at play
    private boolean quit;
    private JSONObject jObj;
    private Scanner masterScanner;
    private Player thePlayer;
    private Parser parse;
    private int currentRoomId;

    private StringWriter outputString;

    /**
     * Initializes the default game file path and sets quit trigger to false
     */
    public Game(){
        pathToFile = "src/resources/default_adventure.json"; //default adventure location
        quit = false;
        parse = new Parser();
        outputString = new StringWriter();
        currentRoomId = 0;
    }

    /**
     * Sets current room in game instance
     * @param newRmId id of new current room
     */
    public void setCurrentRoomId(int newRmId){
        this.currentRoomId = newRmId;
    }

    /**
     * @return current room id
     */
    public int getCurrentRoomId(){
        return this.currentRoomId;
    }

    /**
     * Set a an instance of Scanner
     * @param scnr instance of Scanner for user input
     */
    public void setScanner(Scanner scnr){
        masterScanner = scnr;
    }

    /**
     * Prompts user for path game file (in type JSON) then changes the path to game file to userdefined.
     * @param scnr an instance of Scanner for user input
     */
    public void importAdventure(Scanner scnr){
        StringWriter prompt = new StringWriter();
        prompt.write("File Name:");
        System.out.printf("%s ", prompt);
        pathToFile = scnr.nextLine();

        /* verifies that the entered path is valid */
        File tmpFile = new File(pathToFile);
        if (!tmpFile.exists()){
            importAdventure(scnr);
        }
    }

    /**
     * @return string path to json file
     */
    public String getPath(){
        return pathToFile;
    }

    /**
     * Takes the String path to game file, reads in the JSON file, and parses file into JSONObject
     * @param filename String representation of the path to the game file user wishes to import
     * @return JSONObject created from the user provided JSON file.
     */
    public JSONObject loadAdventureJson(String filename){
        JSONObject adventureToLoad = null;
        try {
            Reader adventureFile = new FileReader(filename); //reads in the JSON file at path filename
            JSONParser parser = new JSONParser();
            adventureToLoad = (JSONObject)parser.parse(adventureFile); //parsing JSON
            adventureFile.close();
        }catch(FileNotFoundException e){ //signals the file at path provided does not exist.
            printFileNotFound();
        }catch(Exception e){} //no other exceptions should occur
        return adventureToLoad;
    }

    /**
     * File not found sequence
     */
    private void printFileNotFound(){
        System.out.println("ERROR: FILE_NOT_FOUND");
        importAdventure(masterScanner);
        loadAdventureJson(this.pathToFile);
    }

    /**
     * To load in adventure file
     * @param inputStream adventure file stream
     * @return JSONObject of adventure for play
     */
    public JSONObject loadAdventureJson(InputStream inputStream){
        JSONObject adventureToLoad = null;
        try {
            Reader adventureFile = (Reader) new InputStreamReader(inputStream); 
            //reads in the JSON file at path filename
            JSONParser parser = new JSONParser();
            adventureToLoad = (JSONObject)parser.parse(adventureFile); //parsing JSON
            adventureFile.close();
        }catch(FileNotFoundException e){ //signals the file at path provided does not exist.
            printFileNotFound();
        }catch(Exception e){} //no other exceptions should occur
        return adventureToLoad;
    }


    /**
     * Generates an adventure based on the JSON used
     * @param obj the JSONObject file to be used to generate the game.
     * @return Adventure object derived from the JSON file passed in.
     */
    public Adventure generateAdventure(JSONObject obj) {
        Adventure theAdventure = new Adventure();
        return theAdventure;
    }

    /**
     * Sets the current adventure.
     * @param curAdventure current adventure.
     */
    public void setCurrentAdventure(Adventure curAdventure){
        currentAdventure = curAdventure;
    }

    /**
     * @return the current adventure being played
     */
    public Adventure getCurrentAdventure(){
        return currentAdventure;
    }

    /**
     * Prompts user if they want a string of instructions for navigating the game
     * to be generated, and acts
     * @param scnr an instance of Scanner.
     * @return string of instructions for navigating the game.
     */
    public String generateInstructions(Scanner scnr){
        System.out.print("WOULD YOU LIKE INSTRUCTIONS? (yes/NO) \n> ");
        if (scnr.nextLine().toUpperCase().contains("YES")){
            return getInstructions() + "\n*   *   *   *   *   *   *   *   *    *   *   *";
        }
        return "*   *   *   *   *   *   *   *   *    *   *   *   *";
    }

    /**
     * @return string instructions of how to play game
     */
    String getInstructions(){
        return "\nCOMMANDS ARE USED TO NAVIGATE AND ACT IN THE GAME.\nTO GO, USE THE 'go <direction>' COMMAND TO GO N, "
                + "E, S, W, up, OR down.\nYOU CAN LOOK AROUND THE ROOM WITH 'look' COMMAND OR AT A SPECIFIC ITEM WITH"
                + " 'look <item>' COMMAND.\nIF YOU WANT TO TAKE IT WITH YOU, ENTER 'take <item>' AND ITEM WILL BE SAVED"
                + " IN YOUR INVENTORY.\nTYPE 'inventory' ANY TIME TO VIEW YOUR INVENTORY.\nYOU CAN WEAR CERTAIN ITEMS "
                + " WITH 'wear <item>',\nEAT CERTAIN EDIBLES WITH 'eat <item>',\nTOSS "
                + "AWAY ITEMS WITH 'toss <item>', AND\nREAD ITEMS WITH 'read <item>'."
                + "\nYOU MAY QUIT THE GAME AT ANYTIME USING THE 'quit' COMMAND.\nYOU'LL GET A CHANCE TO SAVE YOUR "
                + "PROGRESS SO YOU CAN RESUME WHEN YOU RETURN.";
    }

    /**
     * generates the description of the current room.
     */
    public void genRoomNameItem(){
        StringWriter prompt = new StringWriter();
        prompt.write("YOU ARE IN ");
        prompt.append(currentAdventure.getCurrentRoom().getName());
        if (currentAdventure.getCurrentRoom().hasLoot()){
            prompt.append("\t" + listCurrentRoomItems(currentAdventure.getCurrentRoom()));
        }else{
            prompt.append("\nTHIS AREA HAS NO ITEMS \n");
        }
        this.outputString.write(prompt.toString().toUpperCase());
    }

    /**
     * Takes user input and processes through parser to execute
     * @param userIn string of user provided commands
     */
    public void seekInput(String userIn) throws InvalidCommandException{
        Command commandHandler = null;

        Scanner scanAction = new Scanner(userIn);
        commandHandler = this.parse.parseUserCommand(scanAction.nextLine());
        runAction(commandHandler);
    }

    /**
     * Scans in userInput
     * @param userIn scanner for collecting user input
     */
    public void takeInput(Scanner userIn){
        System.out.print("> ");
        try{
            seekInput(userIn.nextLine().toUpperCase());
        }catch(InvalidCommandException ice){
            this.outputString.write(ice.getMessage() + "\n");
        }
    }

    /**
     * Executes the action user chooses to perform
     * @param commandHandler command user wishes to run
     */
    private void runAction(Command commandHandler) throws InvalidCommandException{
        String command = commandHandler.getActionWord().toUpperCase();
        String noun = commandHandler.getNoun();
        //System.out.println("runAction - " + commandHandler.getActionWord() + " - " + commandHandler.getNoun());
        cmdLook(command, noun);
        cmdGO(command, noun);
        cmdHelp(command, commandHandler);
        cmdInventory(command);
        runItemActionables(command,noun);
        setQuit(command);
    }

    /**
     * Executes actions related to items
     * @param command item related commands
     * @param noun item name
     */
    private void runItemActionables(String command, String noun) throws InvalidCommandException{
        cmdTake(command,noun);
        cmdEat(command,noun);
        cmdWear(command,noun);
        cmdThrow(command,noun);
        cmdRead(command,noun);
    }

    /**
     * Consumes item
     * @param command  user input
     * @param itemToEat name of item
     */
    private void cmdEat(String command,String itemToEat) throws InvalidCommandException{
        if(command.equals("EAT")){
            if(thePlayer.doYouHave(itemToEat)){
                Item thing = thePlayer.getItem(itemToEat);
                eating(thing);
            }else{
                throw new InvalidCommandException("YOU DON'T HAVE " + itemToEat.toUpperCase() + ".");
            }
        }
    }

    /**
     * Process of consuming
     * @param theItem item to be consumed
     */
    private void eating(Item theItem) throws InvalidCommandException{
        if(theItem instanceof Edible){
            thePlayer.removeItem(theItem.getName());
            Edible food = (Edible) theItem;
            this.outputString.write("\n" + food.eat().toUpperCase() + "\n");
        }else{
            throw new InvalidCommandException("YOU CAN'T EAT THE " + theItem.getName().toUpperCase() + ".");
        }
    }

    /**
     * Wears clothing
     * @param command user input
     * @param itemToWear item name
     */
    private void cmdWear(String command, String itemToWear) throws InvalidCommandException{
        if(command.equals("WEAR")){
            if(thePlayer.doYouHave(itemToWear)){
                Item thing = thePlayer.getItem(itemToWear);
                wearing(thing);
            }else{
                throw new InvalidCommandException("YOU DON'T HAVE " + itemToWear.toUpperCase() + ".");
            }
        }
    }

    /**
     * Process of wearing
     * @param theItem item to be worn
     */
    private void wearing(Item theItem) throws InvalidCommandException{
        if((theItem instanceof Wearable)){
            Wearable clothing = (Wearable) theItem;
            clothing.wear();
            this.outputString.write("\n" + clothing.wear().toUpperCase() + "\n");
        }else{
            throw new InvalidCommandException("YOU CAN'T WEAR " + theItem.getName().toUpperCase() + ".");
        }
    }

    /**
     * Tosses item
     * @param command user input
     * @param itemToThrow item to be tossed
     */
    private void cmdThrow(String command, String itemToThrow) throws InvalidCommandException{
        if(command.equals("TOSS")){
            if(thePlayer.doYouHave(itemToThrow)){
                Item thing = thePlayer.getItem(itemToThrow);
                throwing(thing);
            }else{
                throw new InvalidCommandException("YOU DON'T HAVE " + itemToThrow.toUpperCase() + ".");
            }
        }
    }

    /**
     * Tossing the item
     * @param theItem item to be tossed
     */
    private void throwing(Item theItem) throws InvalidCommandException{
        if(theItem instanceof Tossable){
            Tossable tossThing = (Tossable) theItem;
            thePlayer.removeItem(theItem.getName());
            currentAdventure.getCurrentRoom().addLoot(theItem);
            this.outputString.write("\n" + tossThing.toss().toUpperCase() + ".\n");
        }else{
            throw new InvalidCommandException(theItem.getName().toUpperCase() + " CANNOT BE TOSSED.");
        }
    }

    private void cmdRead(String cmd, String itemToRead) throws InvalidCommandException{
        if(cmd.equals("READ")){
            if(thePlayer.doYouHave(itemToRead)){
                Item thing = thePlayer.getItem(itemToRead);
                reading(thing);
            }else{
                throw new InvalidCommandException("YOU DON'T HAVE " + itemToRead.toUpperCase() + ".");
            }
        }
    }

    private void reading(Item theItem) throws InvalidCommandException{
        if(theItem instanceof Readable){
            Readable readingThing = (Readable) theItem;
            this.outputString.write("\n-   -   -   -   -   -   -   -   -\n");
            this.outputString.write(theItem.getName().toUpperCase() + " CONTENTS:\n" + readingThing.read() + "\n");
            this.outputString.write("-   -   -   -   -   -   -   -   -\n");
        }else{
            throw new InvalidCommandException(theItem.getName().toUpperCase() + " CANNOT BE READ.");
        }
    }

    /**
     * Responds to the 'look' command, analysis if user looking for room or item information
     * and responds respectively.
     * @param cmd user input
     * @param item second part of user input
     */
    public void cmdLook(String cmd, String item) throws InvalidCommandException{
        if (cmd.equals("LOOK")) {
            if (item == null) {
                this.outputString.write("\n" + currentAdventure.getCurrentRoomDescription().toUpperCase() + "\n");
            } else {
                cmdLookRoomDescription(item);
            }
        }
    }

    /**
     * Prints the room description from Look cmd
     * @param item second user input
     */
    private void cmdLookRoomDescription(String item) throws InvalidCommandException {
        try {
            Item thing = (Item)currentAdventure.getCurrentRoom().getLoot(item);
            this.outputString.write("\n" + thing.getLongDescription().toUpperCase() + "\n");
        } catch (NullPointerException e) {
            throw new InvalidCommandException("THAT ITEM DOESN'T SEEM TO EXIST IN THIS ROOM.");
        }
    }

    /**
     * Responds to the 'go' command, analyses direction user wants to travel, its validity, and responds.
     * @param cmd user input
     * @param direction second part of user input
     */
    public void cmdGO(String cmd,String direction) throws InvalidCommandException{
        if(cmd.equals("GO")) {
            if (direction == null) {
                throw new InvalidCommandException("PLEASE SPECIFY WHICH DIRECTION YOU WANT TO GO.\n"
                                                    +"FOR EXAMPLE: YOU CAN SAY 'go N' TO GO NORTH.\n");
            } else if (currentAdventure.getCurrentRoom().getConnectedRoom(direction) == null) {
                throw new InvalidCommandException("THERE'S NOTHING IN THAT DIRECTION.");
            }
        }
    }

    /**
     * Prints the all possible commands user can use.
     * @param cmd user input
     * @param commandHandler an instance of command object
     */
    private void cmdHelp(String cmd, Command commandHandler){
        if(cmd.equals("HELP")) {
            ArrayList<String> commands = commandHandler.getCommands();
            this.outputString.write("\nYOUR POSSIBLE COMMANDS:\n");
            for (String indivCmd : commands) {
                this.outputString.write(indivCmd + "    ");
            }
            this.outputString.write("\n");
        }
    }

    /**
     * Validates user take input - if item player wishes to seek does not exist, returns warning message.
     * @param cmd user input
     * @param goods name of the good user wishes to seek
     */
    private void cmdTake(String cmd,String goods) throws InvalidCommandException{
        if(cmd.equals("TAKE")) {
            if (goods != null) {
                findAndTake(goods);
            } else {
                this.outputString.write("NO ITEM SPECIFIED.");
            }
        }
    }

    /**
     * Locates the item player wishes to seek, places it in the players inventory and removes it from
     * the rooms loot inventory.
     * @param goods name of the good user wishes to seek
     */
    private void findAndTake(String goods) throws InvalidCommandException{
        Room currentRoom = currentAdventure.getCurrentRoom();
        if (currentRoom.doYouHave(goods)){
            thePlayer.addInventory(currentRoom.getLoot(goods));
            currentRoom.removeItem(currentRoom.getLoot(goods));
            System.out.println();
            this.outputString.write("ITEM IS NOW IN YOUR INVENTORY\n");
        }else{
            throw new InvalidCommandException("THAT ITEM DOESN'T SEEM TO BE IN THIS ROOM");
        }
    }


    /**
     * Prints the player inventory
     * @param cmd user input
     */
     void cmdInventory(String cmd){
        if(cmd.equals("INVENTORY")) {
            this.outputString.write("YOUR INVENTORY:\n");
            makeInventoryList();
        }
    }

    /**
     * Creates user inventory list
     */
    public void makeInventoryList() {
        ArrayList<Item> playerInventory = thePlayer.getInventory();
        if (playerInventory.isEmpty()) {
            this.outputString.write("IS EMPTY.\n");
        } else {
            for (Item itemElem : playerInventory) {
                System.out.print("\t");
                this.outputString.write(itemElem.getName().toUpperCase() + isWearing(itemElem) + "\n");
            }
        }
    }

    /**
     * Prints wearing tag if player is wearing the piece of clothing
     * @param clothing clothing to check
     * @return "(WEARING)" tag
     */
    private String isWearing(Item clothing){
        if(clothing instanceof  Wearable){
            Wearable wear = (Wearable) clothing;
            if(wear.isWear()){
                return " ( WEARING )";
            }
        }
        return "";
    }

    /**
     * Sets quit value to true - ends program
     * @param cmd user input
     */
    private void setQuit(String cmd) {
        if (cmd.equals("QUIT")) {
            this.quit = true;
        }
    }

    /**
     * @return valud indicating if user wants to quit game
     */
    public boolean isQuit(){
        return quit;
    }

    /**
     * Creates a string list of all items in specified room.
     * @param theRoom room of which list should reflect
     * @return string list of items in specified room
     */
    public String listCurrentRoomItems(Room theRoom){
        StringWriter list = new StringWriter();
        list.write("\nTHIS ROOM HAS:\n");
        for (Item items:theRoom.listItems()){
            String itemName = items.getName();
            list.append(itemName.toUpperCase() + "\n");
        }
        return list.toString();
    }

    /**
     * Parses the user arguments from command line
     * and executes respective methods to reflect them.
     * @param cmdArgs string array of arguments from command line
     */
    private void acceptCmdArgs(String[] cmdArgs) throws ArrayIndexOutOfBoundsException{
        if (cmdArgs.length > 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        gameCmd(cmdArgs[0], cmdArgs[1]);
    }

    /**
     * Processes command line arguments
     * @param flag command line flag
     * @param filePath filepath provided
     */
    private void gameCmd(String flag, String filePath){
        if (flag.equals("-l")){ //load game save (the Adventure object)
            openGame(filePath); //load last game save
        }else if (flag.equals("-a")){ //load adventure file (the JSONObject)
            loadAdventureFile(filePath); //load in JSON adventure
            createNewAdventure(); //create new adventure session
        }
    }

    /**
     * Loads in saved game file and creates Adventure object 
     * with the user provided game save. If user defined file
     * does not exist, new file will be created.
     * @param filePath string path to the game save file
     */
    private void loadGameSave(String filePath){
        File tmpFile = new File(filePath);
        if (!tmpFile.exists()){
            this.currentAdventure = importSave(readInFile(tmpFile));
        }else{
            this.currentAdventure = generateAdventure(loadAdventureJson(readInFile(new File(pathToFile))));
        }
    }

    /**
     * Loads in a user defined adventure file and creates a 
     * JSONObject based on it. If user defined file does not exist
     * default game will be loaded.
     * @param filePath string path to user defined adventure json
     */
     void loadAdventureFile(String filePath){
        File tmpFile = new File(filePath);
        if (tmpFile.exists()){
            this.jObj = loadAdventureJson((InputStream)readInFile(tmpFile));
        }else{
            loadDefaultAdventure();
        }
    }

    /**
     * Loads in a user defined adventure file and creates a
     * JSONObject based on it. If user defined file does not exist
     * default game will be loaded.
     * @param file json file
     */
    void loadAdventureFile(File file){
        if (file.exists()){
            this.jObj = loadAdventureJson((InputStream)readInFile(file));
        }else{
            loadDefaultAdventure();
        }
    }

    /**
     * Creates a new JSONObject based on the default adventure file as
     * specified in pathToFile variable.
     */
     void loadDefaultAdventure(){
        File tmpFile = new File(this.pathToFile);
        this.jObj = loadAdventureJson(readInFile(tmpFile));
    }

    /**
     * Creates a new adventure obeject
     */
    void createNewAdventure(){
        this.currentAdventure = new Adventure(this.jObj);
    }


    /**
     * Reads in any user defined files, creating them into an InputStream
     * @param file File defined by user
     * @return a FileInputStream object created from user defined file
     */
    private InputStream readInFile(File file){
        try {
            FileInputStream fileInput = new FileInputStream(file);
            return fileInput;
        }catch(Exception e){
            //catches any other exeption that may be thrown
            System.out.println("ERROR:" + e);
        }
        return null;
    }

    /**
     * Uses FileInputStream from @see #readInFile(File) to create an 
     * Adventure object for game.
     * @param fileStream FileInputStream object created by the readInFile(File) method 
     * @return Adventure object for the current game
     */
    private Adventure importSave(InputStream fileStream){
        try(ObjectInputStream objIn = new ObjectInputStream(fileStream)){
            return (Adventure)objIn.readObject();
        }catch(IOException e){
            System.out.println("ERR:IO_EXCEPTION_HAS_OCCURED\nIS_FROM_SAME_GAME?");
        }catch(ClassNotFoundException e){
            System.out.println("ERR:CLASS_NOT_FOUND");
        }
        return null;
    }

    /**
     * Creates a new user profile if one does not exist.
     * @param scnr scanner object 
     */
    private void playerProfile(Scanner scnr){
        if (isNewPlayer()){
            System.out.println("WELCOME NEW PLAYER - LET'S CREATE YOU A PROFILE:");
            System.out.print("NAME?\n> ");
            createNewPlayer(scnr.nextLine());
            System.out.println("\nWELCOME " + this.thePlayer.getName().toUpperCase());      
        }else {
            System.out.println("HELLO AGAIN " + this.thePlayer.getName().toUpperCase());
        }

    }

    /**
     * Creates new player profile
     * @param name player name
     */
    public void  createNewPlayer(String name){
        this.thePlayer = new Player(name, this.currentAdventure.getCurrentRoom());
    }

    /**
     *  @return indicates if player profile is empty
     */
    public boolean isNewPlayer(){
        return this.thePlayer == null;
    }

    /**
     * Sets player name
     * @param newName player name
     */
    public void setPlayerName(String newName){
        this.thePlayer.setName(newName);
    }

    /**
     * @return player name in setence case format (i.e. John Doe)
     */
    public String getPlayerName(){
        char[] name = this.thePlayer.getName().toCharArray();
        for(int i = 0; i < name.length; i++){
            if(i == 0){
                name[i] = toUpperCase(name[i]);
            }else if (name[i-1] == ' '){
                name[i] = toUpperCase(name[i]);
            }
        }
        return new String(name);
    }

    /**
     * Set the players current room
     */
    public void setCurrentRoom(){
        currentRoomId = currentAdventure.getCurrentRoom().getId();
        thePlayer.setCurrentRoom(currentAdventure.getCurrentRoom());
    }

    /**
     * Asks player if they would like to save progress
     * @param scnr scanner object for user input
     */
    public void promptSave(Scanner scnr){
        System.out.println("\n*   *   *   *   *   *   *   *   *    *   *    "
                            + "*   *\n" + "WOULD YOU LIKE TO SAVE? (yes/NO)");
        System.out.print("> ");
        if (scnr.nextLine().toUpperCase().equals("YES")){
            System.out.println("WHAT WOULD YOU LIKE TO CALL YOUR SAVE (.adventure)?");
            String fileName = scnr.nextLine();
            thePlayer.setSaveGameName(fileName);
            saveGame(fileName);
        }
    }

    /**
     * Saves the players current game state
     * @param fileName name of the save file
     */
    void saveGame(String fileName){
        fileName += ".adventure";
        try {
                ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(fileName));
                objectStream.writeObject(currentAdventure);
                objectStream.writeObject(thePlayer);
                objectStream.close();
        }catch (Exception e){
            System.out.println("ERROR:" + e);
        }
    }

    /**
     * Loads existing game save 
     * @param filePath filepath to the game save file
     */
     void openGame(String filePath){
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))){
            currentAdventure = (Adventure) inputStream.readObject();
            thePlayer = (Player) inputStream.readObject();
        }catch(FileNotFoundException fnf){
            System.out.println("ERROR:FILE_NOT_FOUND");
        }catch(Exception e){
            System.out.println("ERROR:" + e.getMessage());
        }
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Game{"
               + "pathToFile='" + pathToFile + '\''
               + ", currentAdventure=" + currentAdventure
               + ", quit=" + quit
               + ", jObj=" + jObj
               + ", masterScanner=" + masterScanner
               + ", thePlayer=" + thePlayer
               + ", parse=" + parse
               + '}';
    }

    /**
     * Returns game prompts to user, and clears buffer for next prompt
     * @return string prompts for game
     */
    public String getOutputString(){
        String out = outputString.toString();
        outputString.getBuffer().setLength(0);
        return out;
    }

    /**
     * Processes command line arguments
     * @param args command line arguments
     */
    private void acceptCommandArge(String[] args) {
        try {
            acceptCmdArgs(args); //accept command line argumemts
        } catch (ArrayIndexOutOfBoundsException e) {
            //if no arguments present, load defaults
            loadDefaultAdventure();
            createNewAdventure();

        }
    }

    /**
     * Performs pre execution import verification
     * @return boolean indicating if fault is present
     */
    private boolean validateImports(){
        boolean fault = false;
        String jsonValidation = getCurrentAdventure().validateJSON();
        if(jsonValidation.length() > 1) {
            fault = true;
            System.err.println("\n\n" + jsonValidation);
        }
        return fault;
    }

    /**
     * Updates current room information during gameplay
     */
    private void initAdventure(){
        if (getCurrentRoomId() != currentAdventure.getCurrentRoom().getId()) {
            setCurrentRoom();
            genRoomNameItem();
        }
        System.out.print("\n" + getOutputString());
    }

    /**
     * Outputs welcome prompt to user start of game
     * @param scnr scanner for user input
     */
    private void welcomingPrompts(Scanner scnr){
        System.out.println("\n\n\n*   *   *   WELCOME TO YOUR ADVENTURE   *   *   *");
        playerProfile(scnr);
        System.out.println(generateInstructions(scnr));
    }

    /**
     * Main gameplay loop
     * @param scnr scanner for user input
     */
    private void gamePlay(Scanner scnr){
        while (!quit) {
            initAdventure();
            takeInput(scnr);
        }
    }

    /**
     * Prompts to be displayed to user upon ending game
     * @param scnr scanner for user input
     */
    private void exitPrompts(Scanner scnr){
        promptSave(scnr);
        System.out.println("*   *   *   THANKS FOR PLAYING - GOOD BYE   *  *   *");
    }

    public static void main(String[] args){
        Game theGame = new Game();
        Scanner scnr = new Scanner(System.in);
        theGame.setScanner(scnr);
        theGame.acceptCommandArge(args);
        if(!theGame.validateImports()) {
            theGame.welcomingPrompts(scnr);
            theGame.gamePlay(scnr);
            theGame.exitPrompts(scnr);
            scnr.close();//closing scanner
        }
    }
}
