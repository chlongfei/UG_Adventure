package adventure;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import java.awt.ComponentOrientation;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Component;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Container;
import java.io.File;

public class Gui extends JFrame {

    private Game theGame;
    private Container contentPane;
    private JTextArea gameOutputText;
    private JTextArea gameInventoryPrev;


    public static final int WIDTH = 620;
    public static final int HEIGHT = 600;

    private static final Font HEADFONT = new Font("Serif", NORMAL, 15);
    private static final Border BORDERDIMENTSION = BorderFactory.createEmptyBorder(5,20,5,10);
    private static final Dimension INVENTORYPANEDIMENSION = new Dimension(245,0);
    private static final Dimension OUTPUTPANEDIMENSION = new Dimension(290,0);
    private static final Insets OUTPUTTEXTAREAMARGIN = new Insets(5,5,5,5);
    private static final Border USERINPUTBORDER = BorderFactory.createEmptyBorder(0,20,10,10);
    private static final Font USERINPUTFONT  = new Font("Calibre", Font.BOLD, 15);
    private static final Dimension INPUTFIELDDIMENSION = new Dimension(480,25);
    private static final Dimension WELCOMEBUTTONSIZE = new Dimension(5,10);


    public Gui() {
        super();
        setup();
        gameOutputText = null;
        gameInventoryPrev = null;
    }

    /**
     * Constructor initializes default values
     * @param newGame
     */
    public Gui(Game newGame){
        this();
        this.theGame = newGame;
    }

    /**
     * Performs initialization of gui content pane
     */
    private void setup(){
        setSize(WIDTH,HEIGHT);
        setTitle("Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops program execution when window closes
        this.contentPane = getContentPane();
        this.contentPane.setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/resources/icon/adventure.jpg").getImage());
    }

//Game play gui

    /**
     * Adds components to the gui content pane
     * @param comp component to add
     * @param boarderLayout BorderLayout constant for location of component to be placed
     */
    private void addToContentPane(Component comp, String boarderLayout){
        this.contentPane.removeAll();
        this.contentPane.add(createMenuBar(),BorderLayout.PAGE_START);
        this.contentPane.add(comp,boarderLayout);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    /**
     * Creates screen for playtime interaction
     */
    private void createGamePlayScrn(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(createGameOutputPane(), BorderLayout.WEST);
        panel.add(createUserInputArea(),BorderLayout.PAGE_END);
        panel.add(createInventoryPreview(),BorderLayout.CENTER);
        addToContentPane(panel, BorderLayout.LINE_START);
        initRoomContent();
        updateViewers();
    }

    /**
     * Creates area where the inventory will be displayed
     * @return JPanel housing inventory display components
     */
    private JPanel createInventoryPreview(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BORDERDIMENTSION);
        JLabel outputLabel = new JLabel(theGame.getPlayerName() + "'s Inventory:");
        outputLabel.setFont(HEADFONT);
        panel.add(outputLabel, BorderLayout.PAGE_START);
        panel.add(createInventoryArea(), BorderLayout.CENTER);
        updateInventoryPreview();
        return panel;
    }

    /**
     * Creates scroll pane to hold text area for inventory display
     * @return JScrollPane which holds the text area displaying inventory
     */
    private JScrollPane createInventoryArea(){
        this.gameInventoryPrev = createGameOutputTextArea();
        JScrollPane inventoryScroll = new JScrollPane(this.gameInventoryPrev);
        inventoryScroll.setPreferredSize(INVENTORYPANEDIMENSION);
        return inventoryScroll;
    }

    /**
     * Creates area where text output from game processes will display to user
     * @return JPanel housing display elements
     */
    private JPanel createGameOutputPane(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BORDERDIMENTSION);
        JLabel outputLabel = new JLabel("Output:", JLabel.LEFT);
        outputLabel.setFont(HEADFONT);
        panel.add(outputLabel, BorderLayout.PAGE_START);
        panel.add(createGameOutputTextScrollArea(), BorderLayout.LINE_START);
        return panel;
    }

    /**
     * Creates the JScrollPane housing the text area which will display the
     * game output text
     * @return JScrollPane housing text area
     */
    private JScrollPane createGameOutputTextScrollArea(){
        this.gameOutputText = createGameOutputTextArea();
        JScrollPane outScroll = new JScrollPane(this.gameOutputText);
        outScroll.setPreferredSize(OUTPUTPANEDIMENSION);
        return outScroll;
    }

    /**
     * Creates text area that displays text content generated from game
     * @return JTextArea that displayes game text
     */
    private JTextArea createGameOutputTextArea(){
        JTextArea outTextArea = new JTextArea();
        outTextArea.setFont(HEADFONT);
        outTextArea.setEditable(false);
        outTextArea.setLineWrap(true);
        outTextArea.setMargin(OUTPUTTEXTAREAMARGIN);
        return outTextArea;
    }

    /**
     * Creates user input area for user input to interact with game
     * @return JPane housing user input elements
     */
    private JPanel createUserInputArea(){
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(USERINPUTBORDER);
        JLabel inputLabel = new JLabel("Input Command (hit <enter> to submit): ");
        inputLabel.setFont(USERINPUTFONT);
        inputPanel.add(inputLabel, BorderLayout.PAGE_START);
        inputPanel.add(createUserInputField(), BorderLayout.PAGE_END);
        return inputPanel;
    }

    /**
     * Creates user input field for accepting user input
     * @return JTextField for accepting user input
     */
    private JTextField createUserInputField(){
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(INPUTFIELDDIMENSION);
        inputField.addActionListener(enterKeyListener -> {
            doSeekInput(inputField.getText().toUpperCase());
            inputField.setText("");
        });
        return inputField;
    }

    /**
     * Action of sending user input to game processes
     * @param input user input
     */
    private void doSeekInput(String input){
       try{
           this.theGame.seekInput(input);
           updateViewers();
       }catch(InvalidCommandException ice){
           showErrorDialog(ice.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
       }
       checkQuit();
    }

    /**
     * Monitors if user has indicated want to quit game
     */
    private void checkQuit(){
        if(theGame.isQuit()){
            promptSave();
        }
    }

    /**
     * Prompt user if they wish to save session
     */
    private void promptSave(){
        Object[] options = {"Yes", "No", "Cancel"};
        int n = JOptionPane.showOptionDialog(this.contentPane, "Would you like to save your progress?",
                                            "Save Progress?", JOptionPane.YES_NO_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE, null, options,options[0]);
        // 0 = yes, 1 = no, 2 = cancel
        if(n < 2) {
            if (n == 0) {
                doSaveGame();
            }
            System.exit(0);
        }
    }

//--

//Welcome Screen

    /**
     * Creates welcome screen
     */
    private void createWelcomeScrn(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel welcomeLbl = new JLabel("",JLabel.CENTER);
        welcomeLbl.setIcon(new ImageIcon("src/resources/icon/adventure_w_title.png"));
        panel.add(welcomeLbl, BorderLayout.PAGE_START);
        panel.add(createWelcomeBtns(), BorderLayout.CENTER);
        this.contentPane.add(panel, BorderLayout.CENTER);
    }

    /**
     * Creates user intractable buttons for choice of how to initialize the game
     * @return JPanel of user intractable buttons
     */
    private Component createWelcomeBtns(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.add(welcomeBtnLoadJSON());
        panel.add(welcomeBtnLoadSave());
        panel.add(welcomeStartNew());
        panel.add(welcomeBtnLoadInstructions());
        return panel;
    }

    /**
     * Creates button permitting loading of custom adventure JSON
     * @return JButton to be added to panel
     */
    private JButton welcomeBtnLoadJSON(){
        JButton loadJSONBtn = new JButton("Load Custom Adventure");
        loadJSONBtn.addActionListener(btnListen->doLoadCustomAdventure());
        loadJSONBtn.setSize(WELCOMEBUTTONSIZE);
        return loadJSONBtn;
    }

    /**
     * Creates button permitting loading of a saved game file
     * to resume progress
     * @return JButton to be added to panel
     */
    private JButton welcomeBtnLoadSave(){
        JButton loadJSONBtn = new JButton("Load Saved Game");
        loadJSONBtn.addActionListener(btnListen->doLoadSavedGame());
        loadJSONBtn.setSize(WELCOMEBUTTONSIZE);
        return loadJSONBtn;
    }

    /**
     * Creates button permitting of starting a new game from default
     * @return JButton to be added to panel
     */
    private JButton welcomeStartNew(){
        JButton loadJSONBtn = new JButton("Start Default Adventure");
        loadJSONBtn.addActionListener(btnListen->doStartNew());
        loadJSONBtn.setSize(WELCOMEBUTTONSIZE);
        return loadJSONBtn;
    }
    /**
     * Creates button permitting view instructions
     * @return JButton to be added to panel
     */
    private JButton welcomeBtnLoadInstructions(){
        JButton loadInstructions = new JButton("Instructions");
        loadInstructions.addActionListener(btnListener -> {
            JOptionPane.showMessageDialog(this.contentPane,theGame.getInstructions(),"Instructions",
                                        JOptionPane.INFORMATION_MESSAGE,null);
        });
        return loadInstructions;
    }
//--

//MENU BAR

    /**
     * Create menu bar
     * @return JMenuBar for adding to content pane
     */
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    /**
     * Create file menu button to be placed on menu bar
     * @return JMenu for adding to JMenuBar
     */
    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("Options");
        fileMenu.add(menuLoadJSONFile());
        fileMenu.add(menuLoadSaved());
        fileMenu.add(menuSaveGame());
        fileMenu.add(menuEditPlayerName());
        fileMenu.add(menueQuit());
        return fileMenu;
    }

    /**
     * Creates load JSON file menu button
     * @return JMenuItem to add to JMenu
     */
    private JMenuItem menuLoadJSONFile(){
        JMenuItem menuItem = new JMenuItem("Load JSON");
        menuItem.addActionListener(listen->doLoadCustomAdventure());
        return menuItem;
    }

    /**
     * Creates load saved game menu button
     * @return JMenuItem to add to JMenu
     */
    private JMenuItem menuLoadSaved(){
        JMenuItem menuItem = new JMenuItem("Load Saved");
        menuItem.addActionListener(listen->doLoadSavedGame());
        return menuItem;
    }

    /**
     * Creates save game menu button
     * @return JMenuItem to add to JMenu
     */
    private JMenuItem menuSaveGame(){
        JMenuItem menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(listen->doSaveGame());
        return menuItem;
    }

    /**
     * Creates edit player name file menu button
     * @return JMenuItem to add to JMenu
     */
    private JMenuItem menuEditPlayerName(){
        JMenuItem menuItem = new JMenuItem("Edit Player Name");
        menuItem.addActionListener(listen->doEditPlayerName());
        return menuItem;
    }

    /**
     * Creates quit menu button
     * @return JMenuItem to add to JMenu
     */
    private JMenuItem menueQuit(){
        JMenuItem menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(listen->doSeekInput("QUIT"));
        return menuItem;
    }
//--

//Button Actions & Helpers

    /**
     * Load in custom adventure JSON
     */
    private void doLoadCustomAdventure(){
        if (loadJSON()) {
            if(verifyJSON()) {
                try {
                    createPlayer();
                    createGamePlayScrn();
                } catch (NullPointerException npe) {
                    showErrorDialog("Name Cannot Be Blank.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Takes user chosen file and creates adventure using it
     * @return boolean value indicating a successful creation
     */
    private boolean loadJSON(){
        File file = fileChooserOpen("JSON (.json)", "json");
        try{
            theGame.loadAdventureFile(file);
            theGame.createNewAdventure();
        }catch(NullPointerException npe){
            showErrorDialog("No File Selected", "File Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Loads in user defined saved game file
     */
    private void doLoadSavedGame() {
            File file = fileChooserOpen("ADVENTURE (.adventure)", "adventure");
            try {
                theGame.openGame(file.getPath());
                if(verifyJSON()){
                    createGamePlayScrn();
                }
            } catch (NullPointerException npe) {
        }
    }

    /**
     * Saves game session to file
     */
    private void doSaveGame(){
        try {
            String pathToSave = fileChooserSave();
            theGame.saveGame(pathToSave);
        }catch(NullPointerException npe){
        }
    }

    /**
     * Using the JFileChooser, user selects directory in which to save
     * the game session to file.
     * @return filepath to which user wishes to save current game session
     */
    private String fileChooserSave(){
        JFileChooser chooseFileBox = new JFileChooser();
        FileNameExtensionFilter fnFilter = new FileNameExtensionFilter("Adventure (.adventure)", "adventure");
        chooseFileBox.setAcceptAllFileFilterUsed(false);
        chooseFileBox.setFileFilter(fnFilter);
        chooseFileBox.showSaveDialog(this.contentPane);
        String filePath = chooseFileBox.getSelectedFile().getPath();
        return filePath;
    }

    /**
     * Using JFileChooser, user selects file to import
     * @param fileTypeDesc string description of file type
     * @param fileType file extension
     * @return the reterived file
     */
    private File fileChooserOpen(String fileTypeDesc, String fileType){
        JFileChooser chooseFileBox = new JFileChooser();
        FileNameExtensionFilter fnFilter = new FileNameExtensionFilter(fileTypeDesc, fileType);
        chooseFileBox.setAcceptAllFileFilterUsed(false);
        chooseFileBox.setFileFilter(fnFilter);
        chooseFileBox.showOpenDialog(this.contentPane);
        return chooseFileBox.getSelectedFile();
    }

    /**
     * Starts a new game using default adventure file
     */
    private void doStartNew(){
        try{
            theGame.loadDefaultAdventure();
            theGame.createNewAdventure();
            if(verifyJSON()) {
                createPlayer();
                createGamePlayScrn();
            }
        }catch (NullPointerException npe){
            showErrorDialog("Name Cannot Be Blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows user a message dialog
     * @param message the message
     * @param title dialog title
     * @param type type of message based on JOptionPane constants
     */
    private void showErrorDialog(String message, String title, int type){
        JOptionPane.showMessageDialog(this.contentPane, message,title, type);
    }

    /**
     * Creates player profile
     * @throws NullPointerException no name was provided
     */
    private void createPlayer() throws NullPointerException{
        String name = setNewPlayerName();
        if (name.isBlank()) {
            throw new NullPointerException();
        }
        theGame.createNewPlayer(name);
    }

    /**
     * Sets player name
     * @return player name
     */
    private String setNewPlayerName(){
        JOptionPane popup = new JOptionPane();
        popup.setName("New Player");
        String name = (String) JOptionPane.showInputDialog("Hello new adventurer, "
                    + "nice to meet you, what should I call you?");
        if(name.isEmpty()){
            return null;
        }
        return name;
    }

    /**
     * Edits player name
     */
    private void doEditPlayerName(){
        JOptionPane popup = new JOptionPane();
        popup.setName("Edit Player Name");
        String name = (String)JOptionPane.showInputDialog("What do you want me to call you?");
        this.theGame.setPlayerName(name);
        createGamePlayScrn();
    }

    /**
     * Updates output windows for game output and inventory
     */
    private void updateViewers(){
        this.updateOutputText();
        this.updateInventoryPreview();
    }

    /**
     * Generate room description content for output
     */
    private void initRoomContent(){
        theGame.setCurrentRoom();
        theGame.genRoomNameItem();
    }

    /**
     * Updates game output window text
     */
    private void updateOutputText(){
        if (theGame.getCurrentRoomId() != theGame.getCurrentAdventure().getCurrentRoom().getId()){
            initRoomContent();
        }

        this.gameOutputText.setText(theGame.getOutputString());
    }

    /**
     * Updates inventory window with player inventory
     */
    private void updateInventoryPreview(){
        theGame.makeInventoryList();
        this.gameInventoryPrev.setText(theGame.getOutputString());
    }

    /**
     * Verify JSON file
     * @return boolean indicating if an error existst in the JSON file that may affect program
     */
    private boolean verifyJSON(){
        String jsonValidation = theGame.getCurrentAdventure().validateJSON();
        System.err.println(jsonValidation);
        return detectViolation(jsonValidation);
    }

    /**
     * Detects if an error in JSON file exists
     * @param jsonValidation error message passed from error catching methods
     * @return boolean indicating if an error is present
     */
    private boolean detectViolation(String jsonValidation){
        Object[] options = {"Try Again", "Close Program"};
        if(jsonValidation.length() > 1){
            int n = JOptionPane.showOptionDialog(this.contentPane, jsonValidation, "JSON ERROR",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,null,options,options[1]);
            if(n == 1){
                System.exit(0);
            }
            return false;
        }
        return true;
    }
//--


    public static void main(String[] args){
        Game theGame = new Game();
        Gui theGui = new Gui(theGame);
        theGui.createWelcomeScrn();
        theGui.setVisible(true);
    }

}
