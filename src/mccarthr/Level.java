package mccarthr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
*generates the random level.
**/



public class Level {
  /**
  doors and target chambers hashmap.
  **/
  private HashMap<Door, ArrayList<Chamber>> doorConnections;
  /**
  . arraylist of doors in level
  **/
  private ArrayList<Door> doors;
  /**
    arraylist of chambers in level.
  **/
  private ArrayList<Chamber> chambers;


  /**
  constructor initilizes the door array.
  **/
  public Level() {
    doorConnections = new HashMap<Door, ArrayList<Chamber>>();
    doors = new ArrayList<Door>();
    chambers = new ArrayList<Chamber>();
  }

  /**
  * algorithem to generate the level.
  **/
  public void generateLevel() {
    //generating 5 chambers
    initChambers();
    getDoors();
    //connect every door with a random chamber
    setDoorTarget();
    //connecting passage between door and target chamber
    //connect door space to passage,
    //and connect other end of passage to target door (random)
    connectPassages();
  }

  private void connectPassages() {
    int passageIndex = 0;
    for (Door door : doors) {
      for (Chamber targetChamber : doorConnections.get(door)) {
        Passage passage = generatePassage(passageIndex);
        passageIndex++;
        door.setOneSpace(passage);
        //get random door from chambers
        Door targetDoor = randomDoor(targetChamber.getDoors());
        targetDoor.setOneSpace(passage);
      }
    }
  }

  private void getDoors() {
    int doorCount = 0;
    //getting doors from all chambers
    for (Chamber chamber : chambers) {
      for (Door door : chamber.getDoors()) {
        door.setIndex(doorCount);
        doors.add(door);
        doorCount++;
      }
      doorCount = 0;
    }
  }

  private void initChambers() {
    for (int i = 0; i < 5; i++) {
      Chamber temp;
      temp = generateChamber(i);
      chambers.add(temp);
    }
  }

  private void setDoorTarget() {
    for (Door door : doors) {
      Chamber targetChamber;
      do {
        targetChamber = randomChamber(chambers);
      } while (door.getSpaces().get(0) == targetChamber);
      ArrayList<Chamber> temp = new ArrayList<Chamber>();
      temp.add(targetChamber);
      doorConnections.put(door, temp);
    }
  }

  private Chamber randomChamber(final ArrayList<Chamber> theChambers) {
    int low = 0;
    int high = theChambers.size();
    Random r = new Random();
    int result = r.nextInt(high - low) + low;
    return theChambers.get(result);
  }

  private Door randomDoor(final ArrayList<Door> theDoors) {
    int low = 0;
    int high = theDoors.size();
    Random r = new Random();
    int result = r.nextInt(high - low) + low;
    return theDoors.get(result);
  }

  /**
  * algorithem to generate the passage with passagesections.
  * @return new passage.
  * @param index index of new passage.
  **/
  private Passage generatePassage(final int index) {
    Passage passage = new Passage();
    passage.setIndex(index);
    passage.generatePassageSections();
    return passage;
  }

  /**
  * algorithem to generate chamber.
  * @param indexChamber index of new chamber.
  * @return new chamber.
  **/
  private Chamber generateChamber(final int indexChamber) {
    Chamber chamber = new Chamber();
    chamber.setIndex(indexChamber);
    return chamber;
  }

  /**
  * prints the level description in order.
  **/
  public void printDescription() {
    int i = 0;
    //going throught chambers 0-4
    for (Chamber current : chambers) {
      System.out.println(current.getDescription());
    }
  }

}
