import java.util.Scanner;

public class Player{
   //what attributes does a Player have? -->name, current location, items currently held
   //what actions can a Player take? -->check room for objects, view inventory, examine object to learn identity, pick up object, drop object, move N/S/E/W
   
   //ATTRIBUTES OF THE PLAYER
   private String name;
   private Room currRoom = Room.bedroom; //initial value of currRoom
   private Item currItem = Item.nothing; //initial value of currItem
   public Item[] inventory = new Item[4];
   private int numMoves = 0;
   private int speed;
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
   

   //allows the player to pick up an item and place it in their specified inventory slot
   //will check if the slot is empty; if full, will determine whether the item should be replaced or not
   //note: clothing stores the player's current item
   public void setInventory(Item clothing) {
      for(int i = 0; i < inventory.length; i++) {
         if(inventory[i] != null) {
            inventory[i] = clothing;
            Game.print("You have now stored " + clothing.name + " in slot " + (i + 1) + " of your inventory.\n", speed);
            break; //can stop putting the clothing into inventory after it's been stored once
         
         //if no free space is ever found
         } else { 
            System.out.println("Sorry! Your inventory is full. Would you like to drop " +
                               "an object and replace it with your current one? (Enter 'yes' to drop, and 'no' to keep" +
                               " your current item.");
            
            String input = getInput();
            
            while(!input.equals("yes") && !input.equals("no")){
               input = getInput("Sorry, I don't understand that command. Please try again: ");
            }
            
            //if they want to replace an item from their inventory, figure out which item they would like to drop
            //note: can't call drop() here because that would drop the current item, instead of an item in the inventory
            if(input.equals("yes")) { 
               Game.print("Choose an item to discard by entering the slot's number: \n", speed);
               for(int k = 0; i < inventory.length; k++) {
                  Game.print((i+k) + ". " + inventory[k].name + "\n", speed);
               }
               
               int slot = 0;
               try {
                  slot = Integer.parseInt(getInput());
               } catch (IllegalArgumentException e) {
                  Game.print("Sorry, that wasn't a valid input. Please try again: \n", speed);
                  slot = Integer.parseInt(getInput());
               }
               
               //slot-1 will give the index in the array
               Game.print("You carelessly discard the " + inventory[slot-1].name + " to the floor.", speed);
               //update the inventory entry to be the new item of clothing
               inventory[slot-1] = clothing;
               
               //update the location of the Item to be the player's current Room
               clothing.location = currRoom;
               
            } else { //if they do not want to replace the item in their inventory
               System.out.println("You put the " + clothing.name + " down and back away slowly.");
            }     
         }      
      }
   }
   
   public void drop() {
      Item clothing = this.currItem;
      //updates the new location of the item to the current room
      clothing.location = this.currRoom;
      
      //updates the itemHeld array of the current room to include the item
      for(int i = 0; i < 8; i++) {
         //in the first null entry of the item held array
         if(this.currRoom.itemHeld[i] == null) {
            //add the clothing to the empty entry
            this.currRoom.itemHeld[i] = clothing;
            //stop looping once the item has been added
            break;
         } else {
            Game.print("Error! There wasn't any space available in this room to hold this item.\n", speed);
            //if an item is in the inventory or the player's hand, it has a null location
            clothing.location = null;
         }
         
      }
         
      //updates the current item to be nothing
      this.currItem = null;
   }
   
   public void pickUp() {
      //if they're already holding an item
      if(this.currItem != null) {
         String answer = getInput("Sorry, you can only hold one thing at a time. Would you like to drop your current item and pick up a new one? (yes/no)\n");
         if(answer.equals("yes")) {
            drop();
         } else if (answer.equals("no")) {
            Game.print("You didn't pick up anything. Your current item is still: " + this.currItem.name + ".\n", speed);
         } else {
            Game.print("Sorry, I didn't understand that.\n", speed);
         }
      }
      //if they're not currently holding an item
      else {
         
         Game.print("Which item would you like to pick up from the " + this.currRoom.name + "?\n", speed);
         String itemName = getInput();
         boolean pickedUp = false;
         
         //look inside the current room and determine what items can be picked up
         Item[] canPickUp = this.currRoom.itemHeld;
         for(int i = 0; i < canPickUp.length; i++) {
            //if the player input matches an item within the room
            if(itemName.equals(canPickUp[i].name)) {
               //update the player's current item to be the item they wanted
               this.currItem = canPickUp[i];
               pickedUp = true;
               //stop looping when you find the right item
               break;
            }
         }
         
         if(!pickedUp) {
            Game.print("I wasn't able to process that. Are you sure the item you want to pick up is located in this room? " + 
                       "\n Try using 'search' to see the items available in the " + this.currRoom.name + ".\n", speed);
         }
      }
   }
   
   //receive a description of the current item
   public void examine() {
      Item mystery = this.currItem;
      if(!mystery.name.equals("nothing")) {
         Game.print("You take a look at the " + mystery.name + ". ",speed);
         Game.print(mystery.description, speed);
      } else {
         Game.print("You take a good hard look at your hands. You're not holding anything right now!\n", speed);
      }
   }
   
   //prints out the slot number and name of item held, or 'empty' if the array entry is null
   public void viewInventory() {
      System.out.println("You are currently holding: ");
      for(int i = 0; i < 4; i++) {
         if(inventory[i] == null) {
            Game.print("\tSlot " + (i+1) + ": " + "empty",speed);
         } else {         
            Game.print("\tSlot " + (i+1) + ": " + inventory[i].name, speed);
         }
      }
   }
   
   
//receive a description of the current room
   public void lookAround(){
      Game.print("You glance around the " + this.getCurrentRoom().name + ".\n" + this.getCurrentRoom().description, 0);
   }
   
   public boolean search() {
      if(!this.currRoom.hasItem) { //print a message if the room does not contain an item
         Game.print("There is nothing useful to be found in this room.",speed);
         return false;
      } else {
         Game.print("You narrow your eyes and scrutinize the room... Aha! Good intuition. There is indeed an object within this room. The only question is - is it the right one?\n", speed);
         return true;
         //Game.print("Select where you'd like to search from the following list. Type in 'end search' when you want to move on.\n", speed);   
      }   
   }
   
   public void searchBedroom() {
      if(search()) {
         Item[] roomItems = Room.bedroom.itemHeld;
         Game.print("After a frenzied search through your bedroom, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i]!=null) { //avoid run time null pointer exception error
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }
   }
   
   public void searchKitchen() {
      if(search()) {
         
         Item[] roomItems = Room.kitchen.itemHeld;
         Game.print("After a frenzied search through your kitchen, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i]!=null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }
   }
   
   public void searchHallway() {
      if(search()){
         Item[] roomItems = Room.hallway.itemHeld;
         Game.print("After a frenzied search through your hallway, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i] != null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }         
   }
   
   public void searchBathroom() {
      if(search()) {
         Item[] roomItems = Room.bathroom.itemHeld;
         Game.print("After a frenzied search through your bathroom, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i] != null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }      
   }
   
   public void searchLivingRoom() {
      if(search()) {
         Item[] roomItems = Room.livingRoom.itemHeld;
         Game.print("After a frenzied search through your living room, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i] != null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }
   }
   
   public void searchEntranceHall() {
      if(search()) {
         Item[] roomItems = Room.entranceRoom.itemHeld;
         Game.print("After a frenzied search through your entrance room, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i] != null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      }
   }
   
   public void searchCloset() {
      if(search()) {
         Item[] roomItems = Room.closet.itemHeld;
         Game.print("After a frenzied search through your closet, you discover the following items:\n", speed);
         
         for(int i = 0; i < roomItems.length; i++) {
            if(roomItems[i] != null) {
               Game.print("\t" + (i+1) + ". " + roomItems[i].name + "\n",speed);
            }
         } 
      } 
   }
   
//choose a direction to move, and enter that location
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
         Game.print(" You are now standing in the " + this.currRoom.name + ".", speed);
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
         Game.print(" You are now standing in the " + this.currRoom.name + ".", speed);
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
         Game.print(" You are now standing in the " + this.currRoom.name + ".", speed);
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
         Game.print(" You are now standing in the " + this.currRoom.name + ".", speed);
      }
   }    
   
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
