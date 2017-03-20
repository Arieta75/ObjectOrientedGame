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
   
   //constructor with overloading for directions
   public Room(String name, String description) {
      this.name = name;
      this.description = description;
      this.hasItem = false; //default = no item; only updated to true if assignItems updates it
   }
   
   //creation of rooms using the constructor
   public static Room kitchen = new Room("kitchen", "Pots and pans are piled up in the sink and the table is strewn with a messy pile of papers, but sunlight shines cheerfully through the windows " +
                                         "and the faint scent of baked goods lingers in the air. The fridge has lots of delicious leftovers, and makes you think about grabbing a quick 'snack'.\n");
      
   public static Room livingRoom = new Room("living room", "Ahhh, what you wouldn't give to sink into that comfy couch." +
                                            " She may be stained and creaky, but by god is she great for taking a 'nap'. In fact, the evidence of your last nap is still here - "+
                                               "there are blankets and pillows strewn about haphazardly, great hiding spots for an errant piece of clothing.\n");
   
   public static Room bathroom = new Room("bathroom","I hope you didn't come in here to 'use the toilet' - this place is definitely due for a cleaning on your next weekend off. The room is small, and the window is permanently stuck open a crack" +
                                      ", but it's got hot water and that's all that you really need. You often leave your clothes piled up on the counter before you shower -" +
                                          " could you have left your lucky socks or work shirt somewhere here?\n");
   
   public static Room bedroom = new Room("bedroom","There's a cozy bed that looks perfect to 'sleep' on, beside which a sturdy bedside table stands. A dresser stands against the far wall" +
                                      " and a positively overflowing laundry hamper just peeks out from within your dark closet. It's a small room, but a comfortable one.\n");
   
   public static Room closet = new Room("closet", "Well. This is cramped. You struggle to stop various sports gear from falling out and finally jam it into a vague equilibrium." +
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
      //rooms that don't exist are filled with the placeholder notARoom to avoid null pointer exceptions
      //the name of notARoom ("nothing") will print if the room doesn't exist
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
                 "|               |--   ------------------|\n" +
                 "|               |        |              |\n" +
                 "|               |        |              |\n" +
                 "|               |        |              |\n" +
                 "|-----    ------|  hall  |   kitchen    |\n" +
                 "|               |                       |\n" +
                 "|   bathroom    |        |              |\n" +
                 "|               |        |              |\n" +
                 "|---------------|--    --|--------------|\n" +
                 "|      |                 |               \n" +   
                 "|closet|     entrance    |               \n" +
                 "|      |      room                       \n" +
                 "|                        |               \n" +
                 "--------------------------               \n",5);
      
      
   }
}