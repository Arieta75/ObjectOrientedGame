import java.util.Scanner;
import java.util.Random;

public class Player{
   //what attributes does a Player have? -->name, current location, items currently held
   //what actions can a Player take? -->check room for objects, view inventory, examine object to learn identity, pick up object, drop object, move N/S/E/W
   
   //ATTRIBUTES OF THE PLAYER
   private String name;
   private Room currRoom = Room.bedroom; //initial value of currRoom
   private Item currItem; //initial value of currItem
   public Item[] inventory = new Item[4];
   //private int numMoves = 0;
   private int speed;
   private int numNaps = 0;
   private int totalNap = 0;
   private int bathroomBreaks = 0;
   private int snackBreaks = 0;
   //END ATTRIBUTES
   
   
   //EVERYTHING BETWEEN THESE COMMENTS IS JUST A GETTER/SETTER METHOD FOR THE PLAYER ATTRIBUTES
   public void setName(String name) {
      this.name = name;
   }
   public String getName() {
      return this.name;
   }
   public void setCurrentItem(Item clothing) {
      this.currItem = clothing;
   }
   public Item getCurrentItem() {
      return this.currItem;
   }
   public void setCurrentRoom(Room location) {
      this.currRoom = location;
   }
   public Room getCurrentRoom() {
      return this.currRoom;
   }
   public void setSpeed(int speed) {
      this.speed = speed;
   }
    //END GETTER AND SETTER METHOD SECTION
  
   
   //PLAYER ACTIONS
   public boolean pickUp() {
      //if the room doesn't have anything to pick up, display an error
      if(!this.currRoom.hasItem) { 
         Game.print("There's nothing to pick up in here! Use 'search' to figure out what objects might be in a room.\n", speed);
         return false; //end the method here
      }
      
      //if they're already holding an item, give them the option to drop it
      if(this.currItem != null) { 
         String answer = getInput("Sorry, you can only hold one thing at a time. You are currently holding the " + this.currItem.name + ".\n" + 
                                  "Would you like to drop your current item and pick up a new one? (yes/no)\n");
         
         //set up an error catching loop for input checking - only ends if a yes or no is given
         while(!answer.equals("yes") && !answer.equals("no")) {
               Game.print("Sorry, I didn't understand that. Please enter 'yes' or 'no' to drop your current item: \n", speed);
               answer = getInput();
         }
         
         //if they want to discard their current item, drop it and end the loop, then proceed with rest of method 
         if(answer.equals("yes")) { 
            drop(this.currItem);
            Game.print("You put down the " + this.currItem.name + " and back away slowly.\n", speed);
            this.currItem = null; 
         }
         //if they don't want to discard their current item, display message and end the method here
         else if (answer.equals("no")) {
            Game.print("You didn't pick up anything. Your current item is still: the " + this.currItem.name + ".\n", speed);
            return false;
         } 
      }
      
      //if the above code executes, 
      Item[] canPickUp = this.currRoom.itemHeld;
      
      Game.print("You can pick up one of the following items from the " + this.currRoom.name + ".\n", speed);
      Game.print("Please enter the number of the item that you would like to pick up:\n", speed);
      printRoomItems(canPickUp);
      
      int itemName = 0;
      
      boolean tryAgain = true;
      while(tryAgain) {
         try {
            itemName = Integer.parseInt(getInput());
            tryAgain = false;
         } catch (IllegalArgumentException e) {
            Game.print("I didn't quite get that. Enter the number of the item you'd like to pick up from the following list:\n",speed);
         }
      }
      
      boolean pickedUp = false;
      
      //cycle through all the items held in the room (includes null entries)
      for(int i = 0; i < canPickUp.length; i++) {
         //if the player input matches an item within the room
         if(canPickUp[i]!=null && itemName-1 == i) {
            this.currItem = canPickUp[i]; //update the player's current item to be the item they wanted (the item is 'picked up')
            
            Game.print("Yahoo! You just picked up the " + this.currItem.name + ". Remember, if you want to store the item in your " + 
                       "inventory, type in the command 'keep'.", speed);
            
            canPickUp[i] = null; //update the room's items held to be null at the entry that previously held an item
            
            //assumes that only one item was initially placed in the room
            this.currRoom.hasItem = false; 
      
            //cycles through the room's items and checks to make sure there was only one item
            for(int k = 0; k < canPickUp.length; k++) {
               //as soon as an instance of an item is found in the room, hasItem = true and won't be switched to false again
               if(canPickUp[k] != null) {
                  this.currRoom.hasItem = true;
               }
            }
            
            pickedUp = true; //update picked up to be true
            break; //stop the loop when the correct item is picked up
         }
      }
      
      //this should only ever execute if the player enters an invalid item # more than once
      if(!pickedUp) {
         Game.print("I wasn't able to process that. Are you sure the item you want to pick up is located in this room? " + 
                    "\n Try using 'search' to see the items available in the " + this.currRoom.name + ".\n", speed);
      }
      return true;
   }

   //allows the player to place their current item in a free space within their inventory
   //else, can drop an inventory item and replace with the current item
   public void keep() {
      boolean storedItem = false;
      
      for(int i = 0; i < inventory.length; i++) {
         if(inventory[i] == null) { //if there is free space in slot i of the inventory
            inventory[i] = this.currItem; //store the current item in the inventory
            storedItem = true; //update the value of storedItem
            
            Game.print("You glance side to side, then hastily stuff the " + this.currItem.name + " into your pocket.\n", speed);
            Game.print("You have now stored the " + this.currItem.name + " in slot " + (i + 1) + " of your inventory.\n", speed);
            
            this.currItem = null; //set the new current item to null, since you're no longer holding anything
            break; //can stop putting the clothing into inventory after it's been stored once
         }
      }
      
      //no free space was ever found; never executes if an item got stored
      if(!storedItem) { 
         System.out.println("Sorry! Your inventory is full. Would you like to drop " +
                            "an object and replace it with your current one? (Enter 'yes' to drop an item, and 'no' to keep" +
                            " your current item.");
         
         String input = getInput();
         
         while(!input.equals("yes") && !input.equals("no")){
            input = getInput("Sorry, I didn't understand that response. Please try again: ");
         }
         
         //if they want to replace an item from their inventory, figure out which item they would like to drop
         if(input.equals("yes")) { 
            Game.print("Choose an item to discard by entering the inventory slot number: \n", speed);
            //cycle through each slot in the inventory
            for(int i = 0; i < inventory.length; i++) {
               //print out the slot number and the item name
               Game.print((i+1) + ". " + inventory[i].name + "\n", speed);
            }
            
            int slot = 0;
            try {
               //this will receive an answer to the previous question, i.e. which item they want to discard
               slot = Integer.parseInt(getInput());
            } catch (IllegalArgumentException e) {
               Game.print("Sorry, that wasn't a valid input. Please try again: \n", speed);
               slot = Integer.parseInt(getInput());
            }
            
            drop(inventory[slot-1]);
            Game.print("You carelessly discard the " + inventory[slot-1].name + " to the floor.", speed);
            
            //update the inventory entry to contain the player's current item
            inventory[slot-1] = this.currItem;
            
         //if they do not want to replace the item in their inventory, it will remain as their current item
         } else { 
            Game.print("It may not fit in your pockets, but I guess you can just hang onto that for now.\n" + 
                               "You are currently holding the " + this.currItem.name +".\n",speed);
         }     
      }  
   }
   
   //update the item's location to the current room;
   //update the current room's item held array to include the item if possible (don't drop if not)
   //update hasItem to be true once dropped
   public void drop(Item clothing) {
      Item[] roomItems = this.currRoom.itemHeld;
      boolean validDrop = false;
      
      for(int i = 0; i < 8; i++) {
         if(roomItems[i] == null) {
            roomItems[i] = clothing; //when free space is found in the room, store the item within the room's item array
            validDrop = true; //update valid drop to be true
            this.currRoom.hasItem = true; //there must now be an object here
            clothing.location = this.currRoom; //update the item's location to be the current room
            break; //stop the loop once a drop has been made
         }
      }
      
      if(!validDrop) {
         Game.print("Sorry, there wasn't enough space in this room to hold your item! Try dropping it somewhere else instead.\n", speed);
      }
   }
     
   //receive a description of the current item
   public void examine() {
      Item mystery = this.currItem;
      if(mystery != null) {
         Game.print("You take a look at the " + mystery.name + ". ",speed);
         Game.print(mystery.description, speed);
      } else {
         Game.print("You take a good hard look at your hands. You're not holding anything right now!\n" + 
                    "(Remember: you can only examine an item after you've decided to 'pick up' the object.)\n", speed);
      }
   }
   
   //prints out the slot number and name of item held, or 'empty' if the array entry is null
   public void viewInventory() {
      System.out.println("You are currently holding: ");
      for(int i = 0; i < 4; i++) {
         if(inventory[i] == null) {
            Game.print("\tSlot " + (i+1) + ": " + "empty\n",speed); //should avoid null pointer exception
         } else {         
            Game.print("\tSlot " + (i+1) + ": " + inventory[i].name + "\n", speed);
         }
      }
   }
   
   //receive a description of the current room
   public void lookAround(){
      Game.print("You glance around the " + this.getCurrentRoom().name + ".\n" + this.getCurrentRoom().description, 0);
   }
   
   //check if the current room contains any items and print them if yes
   public void search() {
      if(this.currRoom.hasItem) { //if the room contains an item, determine which items are held in the room
         Game.print("You narrow your eyes and scrutinize the room... Aha! Good intuition. There is indeed an object within this room.\n", speed);
         Game.print("After a frenzied search through the " + this.currRoom.name + ", you discover the following items:\n", speed);
         printRoomItems(this.currRoom.itemHeld);
         
      } else { //print a message if the room does not contain an item
         Game.print("A quick search reveals that there is nothing useful to be found in this room.",speed);
      }
   }
   
   //can't be static because of the 'speed' variable
   //assumes that there are items in the roomItems array being passed
   public void printRoomItems(Item[] roomItems) {
      int index = 0;
      for(int i = 0; i < roomItems.length; i++) {
         if(roomItems[i] != null) {
            Game.print("\t" + (index+1) + ". a " + roomItems[i].name + "\n",speed);
            index++;
         }
      }
   }
   
//MOVEMENT COMMANDS
   public void moveNorth() {
      Room room = this.currRoom;
      Game.print("You are currently standing in the " + room.name + ". ",speed);
      
      if(room.north.name.equals("nothing")) { //check if the room to the north is notARoom
         Game.print("You turn north and confidently stride in that direction. Ouch! Maybe you should choose a way that " +
                    "has a door instead of a wall. (Remember: use 'view map' to see the rooms you can enter from the " +
                    this.currRoom.name + ").\n",speed);
      } else {
         Game.print("You move north and enter the " + room.north.name + ".", speed);
         this.currRoom = room.north;
         Game.print(" You are now standing in the " + this.currRoom.name + ".\n", speed);
      }
   }
   
   public void moveEast() {
      Room room = this.currRoom;
      Game.print("You are currently standing in the " + room.name + ". ",speed);
      
      if(room.east.name.equals("nothing")) {
         Game.print("You turn east and confidently stride in that direction. Ouch! Maybe you should choose a way that " +
                    "has a door instead of a wall. (Remember: use 'view map' to see the rooms you can enter from the " +
                    this.currRoom.name + ").\n",speed);
      } else {
         Game.print("You move east and enter the " + room.east.name + ".", speed);
         this.currRoom = room.east;
         Game.print(" You are now standing in the " + this.currRoom.name + ".\n", speed);
      }
   }
   
   public void moveWest() {
      Room room = this.currRoom;
      Game.print("You are currently standing in the " + room.name + ". ",speed);
      
      if(room.west.name.equals("nothing")) {
         Game.print("You turn west and confidently stride in that direction. Ouch! Maybe you should choose a way that " +
                    "has a door instead of a wall.(Remember: use 'view map' to see the rooms you can enter from the " +
                    this.currRoom.name + ").\n",speed);
      } else {
         Game.print("You move west and enter the " + room.west.name + ".", speed);
         this.currRoom = room.west;
         Game.print(" You are now standing in the " + this.currRoom.name + ".\n", speed);
      }
   }
   
   public void moveSouth() {
      Room room = this.currRoom;
      Game.print("You are currently standing in the " + room.name + ". ",speed);
      
      if(room.south.name.equals("nothing")) {
         Game.print("You turn south and confidently stride in that direction. Ouch! Maybe you should choose a way that " +
                    "has a door instead of a wall. (Remember: use 'view map' to see the rooms you can enter from the " +
                    this.currRoom.name + ").\n",speed);
      } else {
         Game.print("You move south and enter the " + room.south.name + ".", speed);
         this.currRoom = room.south;
         Game.print(" You are now standing in the " + this.currRoom.name + ".\n", speed);
      }
   }    

//OTHER COMMANDS 
   public void sleep() {
      if(this.currRoom == Room.bedroom || this.currRoom == Room.livingRoom) {
         if(numNaps <= 3) { //make sure the player isn't sleeping too much
            Game.print("You're already late for work as it is, but a quick power nap shouldn't do any harm...\n",speed);
            for(int i = 0; i < 3; i++) {
               Game.print("...\n",200);
               Game.wait(1000);
            }
            
            Random rand = new Random();
            int napLength = rand.nextInt(60-10+1) + 5; //get a random nap length between 6 minutes and 60 minutes
            Game.print("Oh no! Your small snooze turned into a " + napLength + " minute nap!\n" + 
                       "Your boss is reeeeally not going to be happy with you - better hurry up on getting dressed!\n", speed);
            totalNap += napLength;
            numNaps++;
         } else { //after 3 naps, don't let the player sleep any more
            Game.print("You have now spent exactly " + totalNap + " minutes sleeping in - I think you've slept enough. " + 
                       "When did you even go to bed last night?\n" + 
                       "Don't worry, your boss will understand when you tell him how exhausted you've been. Probably.\n", speed);
            
         }
      } else { //if they're not in the correct room
         Game.print("What, are you planning on sleeping on the floor? Get to work!", speed);
      }
   }
   
   public void useBathroom() {
      if(this.currRoom != Room.bathroom) {
         Game.print("You are currently standing in the " + this.currRoom.name + ".\n", speed);
         Game.print("Just... no.\n", speed);
      } else {
         if(bathroomBreaks > 2) {
            Game.print("I don't mean to pry, but are you feeling okay? You've already used the washroom " + bathroomBreaks + " times since waking up!\n", speed);
            Game.print("I hope you're not just wasting time to put off going to work... \n", speed);
         }
         
         for(int i = 0; i < 3; i++) {
            Game.print("...\n",200);
            Game.wait(1000);
         }
         
         Game.print("You take care of business and get back to the hunt.\n", speed);
         bathroomBreaks++;
      }
   }
   
   public void snackBreak() {
      if(this.currRoom != Room.kitchen) {
         Game.print("You are currently standing in the " + this.currRoom.name + ".\n", speed);
         Game.print("Where do you think you're going to find food here??\n", speed);
      } else {
         if(snackBreaks > 2 && bathroomBreaks > 1){
            Game.print("I think I understand why you use the bathroom so much now. Slow down with the snacks!\n",speed);
         }
         Random rand = new Random();
         int randNum = rand.nextInt(4); //should give 0, 1, 2, or 3
         if(randNum%2 ==  0) {
            Game.print("You grab something from the fridge and quickly chow down. Mmm... that hit the spot!\n", speed);
         } else {
            Game.print("You take a quick swig of water from your water bottle and sigh in satisfaction. You're feeling super refreshed!\n", speed);
         }
         snackBreaks++;
      }
   }
     
   public boolean endGame() {
      boolean inventoryFull = true; //assumes the inventory is full
      
      for(int i = 0; i < this.inventory.length; i++) {
         if(this.inventory[i] == null) {
            inventoryFull = false; //but, if a null entry is found, irreversibly (w/in loop) changes the boolean to false
         }
      }
      
      if(this.currRoom == Room.entranceRoom && inventoryFull) {
         String answer = getInput("\nWoah there! Looks like you've got four pieces of clothing with you. " + 
                                  "Are you ready to get dressed and leave for work? (yes/no)\n");
         
         while(!answer.equals("yes") && !answer.equals("no")) {
            answer = getInput("Hmm, can you try that again? Enter 'yes' if you're ready or 'no' to keep going.\n");
         }
         
         if(answer.equals("yes")) {
            Game.print("Let's take a look at what you've come up with...\n", speed);
            
            boolean hasEverything = false; //assume they don't have one of everything
            
            //determine how many of the items in the inventory are of each type
            int pantsCount = 0;
            int shirtCount = 0;
            int socksCount = 0;
            int tieCount = 0;
            
            for(int i = 0; i < inventory.length; i++) {
               if(inventory[i].name.contains("pants")) {
                  pantsCount++;
               } else if (inventory[i].name.contains("shirt")) {
                  shirtCount++;
               } else if (inventory[i].name.contains("tie")) {
                  tieCount++;
               } else if (inventory[i].name.contains("socks")) {
                  socksCount++;
               }
            }
            
            //use the counts to ensure each item appears exactly once
            if(pantsCount == 1 && shirtCount == 1 && tieCount == 1 && socksCount == 1) {
               hasEverything = true;
            }
            
            for(int i = 0; i < 4; i++) { 
               Game.print("Processing", 2);
               Game.print("...\n",200);
               Game.wait(1000);
            }
            
            //if the player has one of each item
            if(hasEverything) {
               Game.print("Congratulations! It looks like you managed to find one of every needed item within the house. " + 
                          "It's time to get dressed and get to work. Once your boss sees you sporting ", speed);
               
               for(int i = 0; i < 3; i++) {
                  Game.print("your " + inventory[i].name + ", ", speed);
               }
               
               Game.print("and your " + inventory[3].name + ", there's no way they can be mad anymore. Have a good day, " +
                          this.name + ", thanks for playing!!", speed);
               
               return false;
               
            //if the player does not have one of each item
            } else {
               Game.print("Uh oh!", speed);
               viewInventory();
               Game.print("\nIt looks like you're missing something pretty important - you need to have (1) of each item " +
                          "in order to get dressed. Go make sure you have a shirt, a pair of pants, a pair of socks, and a tie!\n", speed);
               return true;
            }
               
         //if the player is not ready to get dressed
         } else {
            Game.print("No time to waste! Get back to the search for the perfect work outfit!", speed);
            return true;
         }
      }
      
      //if the player isn't in the entrance room with a full inventory
      return true;
   }
   
//PLAYER INPUT METHODS 
   //a method that gets the next line input from the user
   public String getInput(String question) {
      Scanner sc = new Scanner(System.in);
      Game.print(question, speed);
      String input = sc.nextLine();
      input = input.toLowerCase();
      sc.close();      
      return input;
   }
   
   public String getInput() {
      Scanner sc = new Scanner(System.in);
      String input = sc.nextLine();
      input = input.toLowerCase();
      sc.close();      
      return input;
   }
   
}
