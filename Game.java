import java.util.Scanner;
import java.util.InputMismatchException;

public class Game {
   public static void main(String[] args) {
      //assign random locations to all the items present
      Item.assignLocations();
      //assign the appropriate items to the itemHeld array for each room
      Item.assignRoomItem();
      //set the values of north, east, south, and west of each room
      Room.setSurroundings();
      
      Scanner sc = new Scanner(System.in);
      print("Hello! It looks like you're a new player. Let's begin with a quick question: what's your name?\n",2);
      String playerName = sc.next();
      print("\n",2);
      
      String openMessage = "Welcome, " + playerName + ". Before we begin, select your text speed by typing in " + 
         "'1' for slow, '2' for medium (default), or '3' for fast into the console.";
      print(openMessage + "\n",2);
      
      int speed = sc.nextInt();
      while(speed != 1 && speed != 2 && speed != 3) {
         print("I'm sorry, I didn't understand that. Please type in '1', '2', or '3'.\n",2);
         speed = sc.nextInt();         
      }
      
      print("You can change the speed by entering the command 'change speed' at any time.\n",speed);
      
      openMessage = "\nType in 'help' at any point to get a list of commands (especially if this is your first time playing!) " +
         ", or 'quit' to stop the game.";
      print(openMessage + "\n",speed);
     
      //create the player in the game
      Player player1 = new Player();
      player1.setName(playerName);
      player1.setSpeed(speed);
      wait(1000);
      
      String input = "";
      String story = "\nOur story begins on a bright and early Monday morning. The sun is shining and birds are chirping cheerfully away outside." +
            "\nUnfortunately for you, it is already 8:14am and you are in severe danger of being late for the important meeting you have at 9:00am." +
            "\nEven worse, the clothes you'll need for the day - pants, shirt, socks, and tie - are scattered randomly about the house due to your disorganization!" +
           // "\nIt takes you 20min to drive to work on a good day, leaving you exactly 26min to get dressed and head outside." +
            "\nNavigate your way through the house and find the 4 pieces of clothing you'll need in order to go to work. Good luck!\n\n";
      
      //print(story, speed);
      
      String prompt = "To get started, why don't you try to take a 'look' around?\n";
      print(prompt,speed);
      
      while(!input.contains("quit")) {
         input = player1.getInput("\nWhat would you like to do next?\n");
            
         if(input.contains("help")) {
            print("The following commands are available actions for your player. Simply type in your command to the console: \n", speed);
            print("\t'help' - I think you've got this one down pat now. Good job.\n", speed);
            print("\t'quit' - End the game. :(\n", speed);
            print("\t'look' - Look around the current room you are in.\n", speed);
            print("\t'search room' - Search your current location for an item of clothing you might want.\n", speed);
            print("\t'view map' - See what rooms are connected to the one you currently stand in.\n", speed);
            print("\t'move + <direction>' - Move north, east, south, or west and enter a different room.\n", speed);
            print("\t'pick up + <item name>' - Store an item in your inventory, provided you have enough space.\n", speed);
            print("\t'drop + <item name>' - Drop an item in whatever the player's current room is.\n",speed);
            print("\t'open inventory' - Look at all the cool things you have.\n", speed);
            print("\t'examine + <item name>' - Learn more about an item in your inventory.\n", speed);
            print("\t'change speed' - Select a new text speed for reading.\n", speed);
         } else if (input.contains("quit")) {
            print("Goodbye, " + player1.getName() + ".", speed);

//EXAMINE COMMANDS
         } else if (input.contains("look")) {
            player1.lookAround(); //prints a description of the player's current room
         } else if (input.contains("search")) {
            Room currRoom = player1.getCurrentRoom();
            if(currRoom == Room.bedroom) {
               player1.searchBedroom();
            } else if(currRoom == Room.kitchen) {
               player1.searchKitchen();
            } else if(currRoom == Room.bathroom) {
               player1.searchBathroom();
            } else if(currRoom == Room.livingRoom) {
               player1.searchLivingRoom();
            } else if(currRoom == Room.hallway) {
               player1.searchHallway();
            } else if(currRoom == Room.entranceRoom) {
               player1.searchEntranceHall();
            } else if(currRoom == Room.closet) {
               player1.searchCloset();
            } else { //shouldn't ever be reached
               System.out.println("Error searching.");
            }

//MOVE COMMANDS
         } else if (input.equals("move")) { 
            Game.print("Please specify the direction (e.g. 'move north') that you'd like to move.\n",speed);
         } else if (input.contains("move") && input.contains("north")) {
            player1.moveNorth(); //updates the current room to the north room, if it exists
         } else if (input.contains("move") && input.contains("east")) {
            player1.moveEast(); //updates current room to east room, if it exists
         } else if (input.contains("move") && input.contains("west")) {
            player1.moveWest(); //updates current room to west room, if it exists
         } else if (input.contains("move") && input.contains("south")) {
            player1.moveSouth(); //updates current room to south room, if it exists

//INVENTORY COMMANDS
         } else if (input.contains("pick up")) {
            //this method should let a player enter the name of the item they would like to pick up
            //then set that item to the player's current item
            player1.pickUp();
         } else if (input.contains("keep")) {
            player1.setInventory(player1.getCurrentItem());
         } else if (input.contains("drop")) {
            player1.drop();
         } else if (input.contains("examine")) {
            player1.examine(); //prints out a description of an item **add the argument Item to this  
         } else if (input.contains("open") || input.contains("inventory")) {
            player1.viewInventory(); //prints out the array of items in the player's inventory        
         } else { //if the input doesn't match any of the possible commands, display error and then loop back up to get new input
            print("Sorry, I didn't understand that. ", speed);
         }
      
      }
      
      /*
      player1.setInventory(Item.socks1, 1);
      player1.viewInventory();
      player1.examine(player1.inventory[0]);
      
      
      player1.setInventory(Item.tie1, 1);
      player1.viewInventory();
      player1.examine();
      */ 
   }
   
   public static void print(String message, int speed) {
      for(int i = 0; i < message.length(); i++) {
         System.out.print(message.charAt(i));
         
         if(message.charAt(i) == '\n') { 
            //modify the speed for the printing of a new line with a short wait time
            wait(200);
         } else {
            //modify the speed for the printing of each character depending on the player's choice
            if(speed == 2) {
               wait(15);
            } 
            //modify the speed for 'slow'
            else if (speed == 1) {
               wait(50);
            }
            //don't modify the speed if 'fast' was selected
         }
      }
   }
   
   public static int changeSpeed(int speed) {
      Scanner sc = new Scanner(System.in);
      print("Would you like to change the speed of the text display? Enter '1' for slow, '2' for medium, " +
                         "and '3' for fast. Your current speed is: " + speed +"\n", speed);
      
      try {
         speed = sc.nextInt();
      } catch (InputMismatchException e) {
         Game.print("Sorry, you didn't enter a valid number just now. The speed will now be set to the default value of '2'.", 2);
         speed = 2;
      }
      
      //loops until the integer input is 1, 2, or 3
      while(speed != 1 && speed != 2 && speed != 3) {
         print("I'm sorry, that's not a valid selection. Please type in '1', '2', or '3'.\n",2);
         speed = sc.nextInt();
      }
      sc.close();
      
      return speed;
   }
   
   public static void wait(int modifier) {
      long start = System.currentTimeMillis();
      long ms = 0;
         
      while(System.currentTimeMillis() - start < modifier) {
         ms++;
      }
   }
}