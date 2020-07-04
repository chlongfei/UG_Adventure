## Class Adventure

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Adventure() | initialize instance variable with default values | currentRoomId | (none) | (none) | 3 |
| Adventure(JSONObject) | initializes the adveture with parameters from JSON | (none) | Adventure()<br>setItems()<br>setRooms() | (none) | 6 |
| String validateJSON() | validates the JSON file for discrepencies | roomMaster<br>errorItem | setValidJSON(boolean)<br>validateJSONRooms(ArrayList<Room>) | (none) | 12 |
| void reportError(boolean) | collects error report from other methods | errorItem | (none) | (none) | 3 |
| boolean validateJSONRooms(ArrayList<Room>) | validates room entries by JSON file | (none) | (none) | (none) |10 |
| boolean compareRoomConnection(Room, Map<String,Integer>, ArrayList<String>) | compares the connectivity of the rooms | roomMaster | checkConnection(Room, String, Room) | Room | 12 |
| boolean checkConnection(Room, String, Room) | checks connectivity between two given rooms | (none) | (none) | Room | 11 |
| String oppositeDirection(String) | produces the opposite direction | (none) | sendOppositeUpDown(String) | (none) | 12 |
| String oppositeDirectionUpDown(String) | produces the opposite direction | (none) | (none) | (none) | 7 |
| void setValidJSON(boolean) | sets if JSON file is valid | validJSON | (none) | (none)  | 3 |
| void setRoomMaster(JSONObject) | converts JSONArray of rooms in adventure into arraylist | roomMaster | (none) | Room | 9 |
| void setItemMaster(JSONObject) | converts JSONArray of items in adventure into arraylist | itemMaster | (none) | Item | 7 |
| void createItem(JSONObject) | creates item reflecting its characteristics using their respective classes | (none) | createFood(theItems,typeIdentifier)<br>createSmFood(theItems,typeIdentifier)<br>createClothing(theItems,typeIdentifier)<br>createBrandedClothing(theItems,typeIdentifier)<br>createWeapon(theItems,typeIdentifier)<br>createSpell(theItems,typeIdentifier) | (none) | 9 |
| void creatFood(JSONObject, String) | creates an edible item | itemMaster | (none) | Food | 5 |
| void creatSmFood(JSONObject, String) | creates an edible and tossable item | itemMaster | (none) | SmallFood | 5 |
| void createClothing(JSONObject, String) | creates an wearable item | itemMaster | (none) | Clothing | 5 |
| void creatBrandedClothing(JSONObject, String) | creates an edible and readable item | itemMaster | (none) | BrandedClothing | 5 |
| void createWeapon(JSONObject, String) | creates an tossable item | itemMaster | (none) | Weapon | 5 |
| void createSpell(JSONObject, String) | creates an readable item | itemMaster | (none) | Spell | 5 |
| String checkItemType(JSONObject) | checks for item type based on JSON conditions | (none) | checkItemEdible(Item)<br>checkItemTossable(Item)<br>checkItemReadable(Item)<br>checkItemWearable(Item) | (none) | 10 |
| String checkITemEdible(JSONObject) | checks if item is set to be edible in JSON | (none) | (none) | (none) |  8 |
| String checkItemTossable(JSONObject) | checks if item is set to be tossable in JSON | (none) | (none) | (none) |  8 |
| String checkItemReadable(JSONObject) | checks if item is set to be readable in JSON | (none) | (none) | (none) |  8 |
| String checkItemWearable(JSONObject) | checks if item is set to be wearable in JSON | (none) | (none) | (none) |  8 |
| void setCurrentRoomId(int) | sets current room id | currentRoomId | (none) | (none) | 3 |
| Room getRoom(int) | returns the room based on room id | roomMaster | (none) | Room) | 3 |
| void setItems() | initializes the list of items in the adventure based on JSON | itemMaster | (none) | Item | 8 |
| ArrayList\<Room> listAllRooms() | returns a list of rooms in the adventure | roomMaster | (none) | Room | 5 |
| ArrayList\<Item> listAllItems() | returns a list of items in the adveture | itemMaster | (none) | Item | 3 |
| String getCurrentRoomDescription() | returns the long description of the current room | currentRoomId<br>roomMaster | (none) | Room | 5 |
| Room getCurrentRoom() | returns the room that player is currently in | currentRoomId | getRoom(int) | (none) | 3 |