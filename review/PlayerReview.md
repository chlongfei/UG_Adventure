## Class Player

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Player() | initialize all instance vars to default values | name<br>inventory<br>currentRoom<br>gameSaveName | (none) | (none) | 6 |
| Player(String, Room) | initialize instance vars with parameters for player name and current room | name<br>currentRoom | Player()<br>setName(String) | (none) | 5 |
| void setName(String) | sets name of player | name | (none) | (none) | 3 |
| String getName() | returns player name | name | (none) | (none) | 3 |
| void addInventory(Item) | adds item to the player inventory | inventory | (none) | Item | 3 | 
| ArrayList\<Item> getInventory() | returns player inventory list | inventory | (none) | Item | 3 |
| Item getItem(String) | retrieves the item from player inventory | inventory | (none) | Item | 8 |
| void removeItem(String) | removes item from player inventory | inventory | (none) | (none) | 3 |
| boolean doYouHave(String) | answers queries for if player has an item inventory | inventory | (none) | Item | 8 |
| void setCurrentRoom(Room) | sets player current room | currentRoom | (none) | Room | 3 |
| Room getCurrentRoom() | returns players current room | currentRoom | (none) | Room | 3 |
| void setSaveGameName(String) | sets the name of the players game save | gameSaveName | (none) | (none) | 3 |
| String getSaveGameName() | returns the name of the game save | gameSaveName | (none) | (none) | 3 |
| String toString() | returns overview of current status of the class | name<br>inventory<br>currentRoom<br>gameSaveName| (none) | (none) | 9 |

