## Class Room

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Room() | initialize |id<br>start<br>name<br>shortDescription<br>longDescription<br>hasItems | (none) | (none) | 8 |
| Room(JSONObject, Adventure) | initialize with JSONObject and Adventure objects | currentAdventure<br>hasItems<br> | setId(int)<br>setName(String)<br>setStart(String)<br>setShort(String)<br>setLong(String)<br>setEnterence(JSONArray)<br>setLoot(JSONArray) | Adventure | 10 |
| void setRoomInformation(JSONObject) | sets the string room information | (none) | (none) | (none) | 9 |
| void setId(Int) | sets room id | id | (none) | (none) | 3 |
| void setName(String) | sets room name | name | (none) | (none) | 3 |
| void setStart(String) | sets room as starting point | start|(none) | Adventure | 8 |
| void setShortDescription(String) | sets room short descripion | shortDescription | (none) | (none) | 3 |
| void setLongDescription(String) | sets room long description | longDescription | (none) | (none) | 3 |
| void setEnterence(JSONArray) | initiate array of doors in the room | enterence | (none) | (none) | 9 |
| Map<String,Integer> getEnterances() | returns map of entrances in room | enternce | (none) | (none) | 3 |
| void setLoot(JSONArray) | initiates the array of items in the room | loot | findMeLoot() | Item | 10 |
| void addLoot(Item) | adds item to room inventory | loot | hasItems(boolean) | Item | 6 |
| void setCurrentAdventure(Adventure) | sets current instance of adventure to variable | currentAdventure | (none) | Adventure | 3 |
| Item findMeLoot(int) | returns the Item object based on item id | (none) | (none) | Item | 11 |
| boolean doYouHave(String) | check if room has specified loot by id | loot | (none) | (none) | 3 |
| void removeItem(Item) | removes item from room inventory | loot | (none) | Item | 6 |
| ArrayList<Item> listItems() | lists all the items currently in room | loot | (none) | Item | 6 |
| int getId() | returns the room id | id | (none) | (none) | 3 |
| String getName() | returns the room name | name | (none) | (none) | 3 |
| String getLongDescription() | returns the rooms long description | longDescription | (none) | (none) | 3 |
| String getshortDescription() | returns the rooms short description | shortDescription | (none) | (none) | 3 |
| Room getConnectedRoom(String) | returns the room connected to current room based user defined direction | currentAdventure<br>enterence | (none) | Adventure | 7 |
| Room getConnectedRoom(String, int) | returns the room connected to current room based user defined direction without setting current room| currentAdventure<br>enterence | (none) | Adventure | 7 |
| boolean hasLoot() | checks if room has any items | hasItems | (none) | (none) | 3 |
| Item getLoot(String) | returns the item from room based on item name | loot | (none) | Item | 3 |
| String toString() | returns the description of the room | (none) | getLongDescription() | (none) | 4 |