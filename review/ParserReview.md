## Class Parser

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Parser () | initializes variables with default values | returnCommand | (none) | (none) | 3 |
|Command parseUserCommand(String) | analyzes the validity of the user commands - throws exception for invalid command| returnCommand<br>commandList | arrayCommands(String) | Command | 11 | 
|void arrayCommands(String) | turn the user command into an array for easier use | commandList | (none) | (none) | 8 |
|String allCommands() | creates a string of all possible commands for the game | allCommandList | (none) | Command | 11 |
|String toString() | returns overview of state of class | commandList<br>returnCommand<br>allCommandList | (none) | (none) | 8 |