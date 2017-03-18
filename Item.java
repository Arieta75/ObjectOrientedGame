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
   public static Item socks1 = new Item("a pair of cool socks", "Coloured with stripes of the rainbow. Funky!\n");
   public static Item socks2 = new Item("a pair of bland socks", "Black and clean-ish (are black socks ever truly clean?). Appropriate for a funeral.\n");
   
   public static Item tie1 = new Item("a funky tie","Has a cool picture of a cat on it.\n");
   public static Item tie2 = new Item("a tie that screams 'trying too hard'","A nice solid shade of dark blue, marred by a small stain of faded mustard. Perfect match for the navy blazer you spilled ketchup on yesterday.\n");
   
   public static Item pants1 = new Item("a pair of cozy pants", "Can't go wrong with a pair of comfy and cozy sweatpants.\n");
   public static Item pants2 = new Item("a pair of formal pants", "Dark slacks, slightly wrinkled from hamper storage.\n");
   
   public static Item shirt1 = new Item("a button up shirt", "A nice white button up shirt. Your arms outgrew the sleeves in high school, but you still haven't found a replacement yet.\n");      
   public static Item shirt2 = new Item("a button up shirt", "Giving off summer vibes with a classic Hawaiian tee.\n");
   
   public static Item nothing = new Item("nothing", "You are not currently holding an item."); 
   
   public static Item[] allItems = {socks1,socks2,tie1,tie2,pants1,pants2,shirt1,shirt2};
   public static Room[] allRooms = {Room.kitchen, Room.livingRoom, Room.bathroom, Room.bedroom, Room.closet, Room.entranceRoom, Room.hallway};
   
   public static void assignLocations() {
      Random rand = new Random(123);
      //assign a random Room to the location of each item in the allItems array
      for(int i = 0; i < allItems.length; i++) {
         int n = rand.nextInt(7); //7 rooms exist, therefore generate number where 0 <= n < 7
         allItems[i].location = allRooms[n];
         System.out.println(allRooms[n].name);
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
               System.out.println(allRooms[k].itemHeld[index].name + " is held in " + allRooms[k].name);
               allRooms[k].hasItem = true;
               index++;

               //Game.print("Type: " + allItems[i].name + ".\tLocation: the " + allRooms[k].name + ".\n", 2);
            }
         }
      }
   }
}
   