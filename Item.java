import java.util.Random;

public class Item {
   //what attributes can an item of clothing have?
   //-->a name/identifier
   
   public String name;
   public String description;
   public Room location;
   
   
   public Item(String type, String description) {
      this.name = type;
      this.description = description;
   }
   
   public void describe() {
      System.out.println(this.description);
   }
   
   //just a method to test the random generator within Game
   public static void getLocation(Item[] allItems) {
      for(int i = 0; i < allItems.length; i++) {
         System.out.println(allItems[i].location.name);
      }
   }
   
   //create instances of Item to represent items in game; set their descriptions
   //these belong to the CLASS, hence static; these CAN be accessed from outside the class, hence public
   public static Item socks1 = new Item("pair of cool socks", "It's got patterned stripes in the colours of the rainbow. Nice!\n");
   public static Item socks2 = new Item("pair of plain old socks", "They're long, black, and clean-ish. Would be appropriate for a wedding or a funeral.\n");
   
   public static Item tie1 = new Item("funky tie","It was a gift from an old coworker. It has a cool picture of a cat.\n");
   public static Item tie2 = new Item("lucky tie","Most people have lucky socks. You? You have lucky socks AND a lucky tie. " + 
                                      "This one features a nice shade of dark blue that's marred by a faded mustard stain. " + 
                                      "It's a perfect match for the navy blazer you spilled ketchup on yesterday.\n");
   
   public static Item pants1 = new Item("pair of cozy sweatpants", "You can't go wrong with a pair of comfy and cozy sweatpants like these.\n");
   public static Item pants2 = new Item("pair of dress pants", "These would probably go well with your black socks. The material is slightly wrinkled from poor storage.\n");
   
   public static Item shirt1 = new Item("formal button up shirt", "This is a nice white button up shirt. Your arms outgrew the sleeves in high school, but you haven't bothered to find a replacement yet.\n");      
   public static Item shirt2 = new Item("casual button up shirt", "The dress code says a button up shirt. This is a Hawaiian tee. It has buttons, and is arguably a shirt. Suitable?\n");
   
   //public static Item nothing = new Item("nothing", "You are not currently holding an item."); 
   
   public static Item[] allItems = {socks1,socks2,tie1,tie2,pants1,pants2,shirt1,shirt2};
   public static Room[] allRooms = {Room.kitchen, Room.livingRoom, Room.bathroom, Room.bedroom, Room.closet, Room.entranceRoom, Room.hallway};
   
   public static void assignLocations() {
      Random rand = new Random();
      int n = 0;
      //assign a random Room to the location of each item in the allItems array
      for(int i = 0; i < allItems.length; i++) {
         n = rand.nextInt(7); //7 rooms exist, therefore generate number where 0 <= n < 7
         allItems[i].location = allRooms[n];
         //System.out.println(allRooms[n].name);
      }
   }
   
   public static void assignRoomItem() {
      //this should overwrite the null entry with the correct item according to the randomly generated location
      for(int i = 0; i < allItems.length; i++) {
         int index = 0;
         //cycle through each room available
         for(int k = 0; k < allRooms.length; k++) {
            if(allItems[i].location == allRooms[k]) {
               //set hasItem to true if a match is made between an item's location and the room in allRooms array
               //add an entry into the itemHeld array, increasing the index of entry by one each time an addition is made
               allRooms[k].itemHeld[index] = allItems[i];
               //System.out.println(allRooms[k].itemHeld[index].name + " is held in " + allRooms[k].name);
               allRooms[k].hasItem = true;
               index++;

               //Game.print("Type: " + allItems[i].name + ".\tLocation: the " + allRooms[k].name + ".\n", 2);
            }
         }
      }
   }
}
   