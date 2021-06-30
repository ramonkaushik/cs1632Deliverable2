import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	// TODO: Add more member variables and methods as needed.
	private boolean gameStatus; //true if won
	Player player;
	private AbstractList<Room> rooms; //map of rooms 
	private int roomCounter; //we'll use this as a live array counter - also the index of our arraylist of rooms
	private Room currentRoom = null;

	CoffeeMakerQuestImpl() 
	{
		// TODO 
		gameStatus = false; //game is not won
		player = null;
		rooms = new ArrayList<Room>();
		roomCounter = 0;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		//The game ends when the player has all three items and presses D
		return gameStatus;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		// DONE
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		//Edge cases: if the room passed in is null or there are already existing rooms
		if(rooms.isEmpty() == false || room == null)
		{
			return false;
		}
		//at this point, we know we have good input
		rooms.add(room);
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		// TODO
		//checking boundary cases 
		if(room == null || northDoor == null || southDoor == null || rooms.isEmpty() == true) return false;
		for(Room r : rooms)
		{
			//Check that a room like this does no already exist with string comparison
			if(r.getFurnishing().equals(room.getFurnishing()) || r.getAdjective().equals(room.getAdjective()))
			{
				return false; 
			}
		}
		//at this point, the value is unique so lets add it 
		// subtract 1 because .size() gives us logical size and our counter starts at 1
		rooms.get(rooms.size() - 1).setNorthDoor(northDoor); 
		room.setSouthDoor(southDoor);
		rooms.add(room);
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		// TODO
		if(currentRoom == null)
		{
			return null;
		}
		if(rooms.isEmpty() == false)
		{
			// room counter keeps the value of the room you are in as you traverse the doors 
			//System.out.println(roomCounter);
			return rooms.get(roomCounter);
		}
		//needs to return room so I just need to return this 
		return null;
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		// TODO
		int i = rooms.indexOf(room);
		if(rooms.isEmpty() == false)
		{
			roomCounter = i;
			currentRoom = room;
			//room successfully added 
			return true;
		}
		//otherwise
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() 
	{
		// Done
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) 
	{
		// TODO
		//case insensitive
		cmd = cmd.toLowerCase();
		//n: north command 
		if(cmd.equals("n"))
		{
			if(roomCounter == 6)
			{
				return "A door in that direction does not exist.\n";
			}
			//at this point, we can know we can move to the next room 
			roomCounter++;
			return "";
		}
		//s: south command 
		if(cmd.equals("s"))
		{
			if(roomCounter == 0) //cant go south
			{
				return "A door in that direction does not exist.\n";
			}

			roomCounter--;
			return "";
		}
		//i: show inventory 
		if(cmd.equals("i"))
		{
			return player.getInventoryString();
		}
		//d: drink, win or lose
		if(cmd.equals("d"))
		{
			String winningString = "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n";
			//gameStatus = true;
			if(drinkMessage().equals(winningString))
			{
				gameStatus = true;
				return drinkMessage();
			}
			return drinkMessage();
		}
		//h: help command
		if(cmd.equals("h"))
		{
			String message = "";
			message += "N - Go north";
			message += "\nS - Go south";
			message += "\nL - Look and collect any items in the room";
			message += "\nI - Show inventory of items collected";
			message += "\nD - Drink coffee made from items in inventory\n";
			return message;
		}
		//l: look command. either find an item or don't 
		if(cmd.equals("l"))
		{
			switch (getCurrentRoom().getItem())
			{
				case CREAM:
					player.addItem(getCurrentRoom().getItem());
					return "There might be something here...\nYou found some creamy cream!\n";	
				
				case COFFEE:
					player.addItem(getCurrentRoom().getItem());
					return "There might be something here...\nYou found some caffeinated coffee!\n";

				case SUGAR:
					player.addItem(getCurrentRoom().getItem());
					return "There might be something here...\nYou found some sweet sugar!\n";

			}
			return "You don't see anything out of the ordinary.\n";
	}
	return "What?";
}
	public String drinkMessage(){
		String winningStatement = "\nYou drink the beverage and are ready to study!\nYou win!\n";
		String noSugar = "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
		String noCream = "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
		String noCoffee = "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
		String justCoffee = "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
		String justSugar = "\nYou eat the sugar, but without caffeine,  you cannot study.\nYou lose!\n";
		String justCream = "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
		String losingStatement = "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";

		if(player.checkCoffee() && player.checkCream() && player.checkSugar()) return winningStatement; 
		else if(player.checkCoffee() && player.checkCream()) return noSugar;
		else if(player.checkSugar() && player.checkCream()) return noCoffee;
		else if(player.checkSugar() && player.checkCoffee()) return noCream;
		else if(player.checkCoffee()) return justCoffee;
		else if(player.checkSugar()) return justSugar;
		else if(player.checkCream()) return justCream; 
		else return losingStatement;
	}	
}

