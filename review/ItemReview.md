## Class Item

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Item() | initializes instance variables with default values | id<br>name<br>description<br>parentRoom | (none) | (none) | 6 |
| Item(JSONObject) | initializes the item with properties from JSON | (none) | this()<br>setId(id)<br>setName(String)<br>setDescription(String) | (none) | 6 |
| void setRoom(Room) | sets room the item belongs to | parentRoom | (none) | Room | 3 |
| void setId(int) | sets item id | id | (none) | (none) | 3 |
| void setName(String) | sets item name | name | (none) | (none) | 3 |
| void setDescription(String) | sets item description | description | (none) | (none) | 3 |
| int getId() | returns the item id | id | (none) | (none) | 3 |
| String getName() | returns the item name | name | (none) | (none) | 3 | 
| String getLongDescription() | returns the item long description | description |(none) | (none) | 3 |
| Room getContainingRoom() | returns the room that the item belongs to | parentRoom |(none) | Room | 3 |
| String toString() | returns a string of the variables of the item | id<br>name<br>description<br>parentRoom | (none) | (none) | 9 |