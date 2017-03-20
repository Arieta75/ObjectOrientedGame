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
      
      String openMessage = "Welcome, " + playerName + ". Before we begin, you can change the speed of your text display to" +
                           " either speed it up or slow it down. ";
      print(openMessage,2);
      
      int speed = changeSpeed(2);
      /*while(speed != 1 && speed != 2 && speed != 3) {
         print("I'm sorry, I didn't understand that. Please type in '1', '2', or '3'.\n",2);
         speed = sc.nextInt();         
      }*/
      
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
      
      print(story, speed);
      
      String prompt = "To get started, why don't you try to take a 'look' around?\n";
      print(prompt,speed);
      
      while(!input.contains("quit")) {
         //if endGame() returns true, get input from the user and proceed as normal; else, if endGame() returns false
         //(which only happens once player has won) force the input to be quit and print the goodbye message and end loop
         if(player1.endGame()) {         
            input = player1.getInput("\nWhat would you like to do next?\n");
         } else {
            input = "quit";
         }
            
         if(input.contains("help")) {
            print("The following commands are available actions for your player. Simply type in your command to the console: \n", speed);
            print("\t'help' - I think you've got this one down pat now. Good job.\n", speed);
            print("\t'quit' - End the game. :(\n", speed);
            print("\t'change speed' - Select a new text speed for reading.\n", speed);
            
            print("\n\t'look' - Look around the current room you are in.\n", speed);
            print("\t'search room' - Search your current location for an item of clothing you might want.\n", speed);
            print("\t'view map' - See what rooms are connected to the one you currently stand in.\n", speed);
            print("\t'move + <direction>' - Move north, east, south, or west and enter a different room.\n", speed);
            
            print("\n\t'pick up + <item name>' - Pick up an item from your current location and keep it handy.\n", speed);
            print("\t'examine + <item name>' - Learn more about the item you're currently holding.\n", speed);
            print("\t'keep' - Store whatever you're holding within your inventory.\n", speed);
            print("\t'drop + <item name>' - Drop whatever you're holding in the room you're standing in.\n",speed);
            print("\t'open inventory' - Look at all the cool things you've decided to keep.\n", speed);
            
         } else if (input.contains("quit")) {
            //prints a goodbye message to the player, then stops the while loop asking for input
            print("Goodbye, " + player1.getName() + ".", speed);
         } else if (input.contains("speed")) {
            //changes the speed of the text display
            speed = changeSpeed(speed);
            player1.setSpeed(speed);
//MOVEMENT AND SEARCH COMMANDS
         } else if (input.contains("look")) {
            //prints a description of the player's current room
            player1.lookAround(); 
         } else if (input.contains("search")) {
            //checks whether the player's current room hasItem and returns the items present if appropriate
            player1.search();
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
         } else if (input.contains("map")) {
            //gives the rooms located north, east, south, and west
            //optionally prints an ASCII map of the house's floor plan
            print("Don't worry, we all forget how our houses are laid out from time to time. Your face screws up in concentration as you " +
                  "try to determine where the doors lead from your current position. After some deliberation, you reach " +
                  "the following conclusions about your surroundings: \n", speed);
            String[] surround = player1.getCurrentRoom().getSurroundings(player1.getCurrentRoom());
            for(int i = 0; i < surround.length; i++) {
               print(surround[i] + "  ",speed);
            }
            String answer = player1.getInput("\nWould you like to see a map of the entire house? (yes/no)");
            if(answer.equals("yes")) {
               Room.displayMap();
            } else if (!answer.equals("no") && !answer.equals("yes")) {
               print("Sorry, I didn't understand that. \n", speed);
            }
//INVENTORY COMMANDS
         } else if (input.contains("pick up")) {
            //this method should let a player enter the name of the item they would like to pick up
            //then set that item to the player's current item
            player1.pickUp();
         } else if (input.contains("examine")) {
            //prints out a description of whatever item the player currently holds 
            player1.examine(); 
         } else if (input.contains("keep")) {
            //this method should store the player's current item in their inventory
            player1.keep();
         } else if (input.contains("drop")) {
            //this will drop the player's current item if possible
            if(player1.getCurrentItem() != null) {
               Item curr = player1.getCurrentItem();
               player1.drop(curr);
               player1.setCurrentItem(null);
               Game.print("You put down the " + curr.name + " and back away slowly.\n", speed);
            } else {
               Game.print("You're not holding anything right now!\n", speed);
            }
         } else if (input.contains("inventory")) {
            //prints out the array of items in the player's inventory        
            player1.viewInventory(); 
         
//OTHER COMMANDS
         } else if (input.contains("sleep") || input.contains("nap")) {
            player1.sleep(); //take a quick nap
         } else if (input.contains("use the toilet")) {
            player1.useBathroom(); //use the loo
         } else if (input.contains("eat") || input.contains("snack")) {
            player1.snackBreak(); //grab a bite to eat
         } else { 
            //if the input doesn't match any of the possible commands, display error and then loop back up to get new input
            print("Sorry, I didn't understand that.\n", speed);
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
            wait(200); //wait 200ms every time a new line is printed
         } else {
            //note: if '3' is selected, just print each char in order without delay
            if(speed == 2) { //medium, default
               wait(15); //wait 15ms between characters
            } 
            else if (speed == 1) { //slow
               wait(30); //wait 30ms between characters
            }
            else if (speed != 3) { 
               //for personal use within code to print specific messages slowly
               wait(speed);
            }
         }
      }
   }
   
   public static int changeSpeed(int speed) {
      Scanner sc = new Scanner(System.in);
      print("Would you like to change the speed of the text display? Enter '1' for slow, '2' for medium, " +
            "and '3' for fast. Your current speed is: " + speed +"\n", speed);
      
      boolean validInput = false;
      
      while(!validInput) {
         try {
            speed = sc.nextInt();
            //first, try and catch an input mismatch
            //loops until the integer input is 1, 2, or 3
            while(speed != 1 && speed != 2 && speed != 3) {
               print("I'm sorry, that's not a valid selection. Please type in '1', '2', or '3'.\n",2);
               speed = sc.nextInt();
            }
            validInput = true;
         } catch (InputMismatchException e) {
            Game.print("Sorry, you didn't enter a valid number just now. The speed will now be set to the default value of '2'.", 2);
            speed = 2;
         }
      } 
      sc.close();
      return speed;
      
   }
   
   public static void wait(int modifier) {
      long start = System.currentTimeMillis();
      //long ms = 0;
      //continuously updates the current time and finds the time passed since method was called
      //once the time passed is greater than the modifier (in # ms) passed to it, while loop stops and method ends
      while(System.currentTimeMillis() - start < modifier) {
         //don't actually want to do anything except wait
      }
   }
}