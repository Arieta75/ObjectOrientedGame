public class Room {
   //traits of the room
   public String name;
   public String description;
   
   //can hold maximum 8 items if by some chance random generator allows that
   public Item[] itemHeld = new Item[8];
   
   public boolean hasItem;
   
   //attributes of the room, i.e. room.north refers to the Room above the instance 'room'
   public Room north;
   public Room south;
   public Room east;
   public Room west;
   
   //constructor 
   public Room(String name, String description) {
      this.name = name;
      this.description = description;
      this.hasItem = false; //default = no item; only updated to true if assignItems updates it
   }
   
   //creation of rooms using the constructor
   public static Room kitchen = new Room("kitchen", "There are pots and pans piled up in the sink, but otherwise it's clean and bright. " +
                                         "The cupboards are painted a cheerful light blue" +
                                        ", and the fridge is covered in various bits of paper and magnets.\n");
      
   public static Room livingRoom = new Room("living room", "Ahhh, what you wouldn't give to sink into that comfy couch." +
                                            " She may be stained and creaky, but by god is she great for a nap. Evidence of your last nap is still here - "+
                                               "there are blankets and pillows strewn about haphazardly.\n");
   
   public static Room bathroom = new Room("bathroom","This place is definitely due for a cleaning on your next weekend off. The sink is a bit rusty, and the window is permanently stuck open a crack" +
                                      ", but the shower provides hot water and that's all you really need. You often leave your clothes piled up on the counter when you shower -" +
                                          " could you have left your lucky socks or work shirt somewhere here?\n");
   
   public static Room bedroom = new Room("bedroom","There's a cozy bed in one corner, beside which a sturdy bedside table stands. A dresser stands against the far wall" +
                                      " and a positively overflowing laundry hamper just peeks out from within your dark closet. It's a small room, but a comfortable one.\n");
   
   public static Room closet = new Room("closet", "Well. This is cramped. You struggled to stop various sports gear from falling out and finally jam it into a vague equilibrium." +
                                        " Why did you come in here again?\n");
   
   public static Room entranceRoom = new Room("entrance room", "You can leave the comfort of your house from here and go to work. Why would you want to, though?\n");
   
   public static Room hallway = new Room("hallway", "There's not much to see here - an umbrella stand guards one corner of the room, while a coat rack looms in the other one. "+
                                            "You look at your reflection in the wall mirror and brush a few errant strands of hair back into place.\n");
   
   public static Room notARoom = new Room("nothing", "This should literally never be printed ever.");
   
   public static void setSurroundings() {
      for(int i = 0; i < Item.allRooms.length; i++) {
         
         if(Item.allRooms[i] == kitchen) {
            kitchen.west = hallway;
            kitchen.north = notARoom;
            kitchen.east = notARoom;
            kitchen.south = notARoom;
         }
         
         if(Item.allRooms[i] == livingRoom) {
            livingRoom.south = hallway;
            livingRoom.north = notARoom;
            livingRoom.east = notARoom;
            livingRoom.west = notARoom;
            
         }
         
         if(Item.allRooms[i] == hallway) {
            hallway.north = livingRoom;
            hallway.south = entranceRoom;
            hallway.west = bedroom;
            hallway.east = kitchen;
         }
         
         if(Item.allRooms[i] == bedroom) {
            bedroom.south = bathroom;
            bedroom.east = hallway;
            bedroom.north = notARoom;
            bedroom.west = notARoom;
         }
         
         if(Item.allRooms[i] == bathroom) {
            bathroom.north = bedroom;
            bathroom.west = notARoom;
            bathroom.east = notARoom;
            bathroom.south = notARoom;
         }
         
         if(Item.allRooms[i] == entranceRoom) {
            entranceRoom.west = closet;
            entranceRoom.north = hallway;
            entranceRoom.east = notARoom;
            entranceRoom.south = notARoom;
         }
         
         if(Item.allRooms[i] == closet) {
            closet.east = entranceRoom;
            closet.north = notARoom;
            closet.west = notARoom;
            closet.south = notARoom;
         }
      }
   }
   
   public String[] getSurroundings(Room currRoom) {
      //only one if block can execute in the above code
      //when that block executes, it will create .north/.west/etc. when appropriate but leave the others null
      String[] surround = {"N: " + this.north.name, "E: " + this.east.name, "W: " + this.west.name, "S: " + this.south.name};
      return surround;
   }
   
   
   public static void displayMap() {
      Game.print("-----------------------------------------\n" +
                 "|               |                       |\n" +
                 "|               |                       |\n" +
                 "|               |      living           |\n" +
                 "|               |        room           |\n" +
                 "|   bedroom     |                       |\n" +
                 "|               |--   --------    ------|\n" +
                 "|               |        |              |\n" +
                 "|               |        |              |\n" +
                 "|               |        |              |\n" +
                 "|-----    ------|  hall  |   kitchen    |\n" +
                 "|               |                       |\n" +
                 "|   bathroom    |        |              |\n" +
                 "|               |        |              |\n" +
                 "|---------------|--    --|--------------|\n" +
                 "|      |                 |               \n" +   
                 "|closet|    entrance     |               \n" +
                 "|      |     room        |               \n" +
                 "|      |                 |               \n" +
                 "--------------------------               \n",10);
      
      
   }
}