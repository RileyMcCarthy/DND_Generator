package mccarthr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
*generates the random level.
**/



public class Level implements java.io.Serializable{
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
  private ArrayList<Passage> passages;


  /**
  constructor initilizes the door array.
  **/
  public Level() {
    doorConnections = new HashMap<Door, ArrayList<Chamber>>();
    doors = new ArrayList<Door>();
    chambers = new ArrayList<Chamber>();
    passages = new ArrayList<Passage>();
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
    int passageIndex = 1;
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
    int doorCount = 1;
    //getting doors from all chambers
    for (Chamber chamber : chambers) {
      for (Door door : chamber.getDoors()) {
        door.setIndex(doorCount);
        doors.add(door);
        doorCount++;
      }
    }
  }

  private void initChambers() {
    for (int i = 1; i < 6; i++) {
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
    passages.add(passage);
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

  public ArrayList<Chamber> getChambers() {
    return chambers;
  }

  public ArrayList<Passage> getPassages() {
    return passages;
  }

  public Chamber getChamber(int theIndex) {
    for (Chamber chamber : chambers) {
      if (chamber.getIndex() == theIndex) {
        return chamber;
      }
    }
    return null;
  }

  public Passage getPassage(int theIndex) {
    for (Passage passage : passages) {
      if (passage.getIndex() == theIndex) {
        return passage;
      }
    }
    return null;
  }

  public Door getDoor(int doorIndex) {
    for (Door door : doors) {
      if (doorIndex == door.getIndex()) {
        return door;
      }
    }
    return null;
  }

  public ArrayList<Integer> getDoorsFromChamber(int index) {
    ArrayList<Integer> doorIndexes = new ArrayList<Integer>();
    for (Chamber chamber : chambers) {
      if (index == chamber.getIndex()) {
        ArrayList<Door> doors = chamber.getDoors();
        for (Door door : doors) {
          doorIndexes.add(door.getIndex());
        }
        return doorIndexes;
      }
    }
    return doorIndexes;
  }

  public ArrayList<Integer> getDoorsFromPassage(int index) {
    ArrayList<Integer> doorIndexes = new ArrayList<Integer>();
    for (Passage passage : passages) {
      if (index == passage.getIndex()) {
        ArrayList<Door> doors = passage.getDoors();
        for (Door door : doors) {
          doorIndexes.add(door.getIndex());
        }
        return doorIndexes;
      }
    }
    return doorIndexes;
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
