package mccarthr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import dnd.models.Trap;
import dnd.models.Monster;

/**
*passage objec that holds passagesections.
**/
public class Passage extends Space implements java.io.Serializable{

/**
*list of passage sections.
**/
private ArrayList<PassageSection> thePassage;
/**

/**
*list of doors.
**/
private ArrayList<Door> doors;
/**
*hashmap using doors a key and PassageSection as value.
*/
private HashMap<Door, PassageSection> doorMap;
/**
*length of the passage.
**/
private int length;
/**
*index of the passage in level.
**/
private int index;

/**
* constructor that initilizes object.
**/
public Passage() {
  thePassage = new ArrayList<PassageSection>();
  doorMap = new HashMap<Door, PassageSection>();
  doors = new ArrayList<Door>();
  length = 0;
}
/**
* @return all of the doors in the entire passage.
*/
public ArrayList<Door> getDoors() {
  return doors;
}

/**
*@param toAdd adds the passage section to the passageway.
**/
public void addPassageSection(final PassageSection toAdd) {
  toAdd.setIndex(thePassage.size()+1);
  thePassage.add(toAdd);
  if (toAdd.getDoor() != null) {
    toAdd.getDoor().setOneSpace(this);
  }
  length += 10;
}

/**
* @param newDoor add a door connection to the current Passage Section.
**/
@Override
public void setDoor(final Door newDoor) {
  if (getLatestPassage() != null) {
    doorMap.put(newDoor, getLatestPassage());
    getLatestPassage().setDoor(newDoor);
    doors.add(newDoor);
  }
}

public ArrayList<Monster> getMonsters(){
  ArrayList<Monster> temp = new ArrayList<Monster>();
  for(PassageSection section : thePassage) {
    ArrayList<Monster> secMonsters = new ArrayList<Monster>();
    secMonsters = section.getMonsters();
    for (Monster monster : secMonsters) {
      temp.add(monster);
    }
  }
  return temp;
}

/**
* @return the description of the passage.
*/
@Override
public String getDescription() {
  String desc = "Passage #" + index + " with passage sections:\n";
  for (PassageSection section : thePassage) {
    desc = desc.concat(section.getDescription());
  }
//  desc = desc.concat(getDoorsDescription());
return desc;
}

private String getDoorsDescription() {
  String desc = "";
  if (doors.size() != 0) {
    desc = desc.concat("Door to chamber #"
    + index + " is in passage section #"
    + (doorMap.get(getDoors().get(getDoors().size() - 1)).getIndex()
    + 1) + "\n");
  }
  return desc;
}

/**
* @return the last created  PassageSection.
*/
public PassageSection getLatestPassage() {
  if (thePassage.size() == 0) {
    return null;
  }
  return thePassage.get(thePassage.size() - 1);
}

public void removeMonster(String desc) {
  for (PassageSection sec : thePassage) {
    ArrayList<Monster> temp = sec.getMonsters();
    for (Monster mon : temp) {
      if (desc.equals(mon.getDescription())) {
        sec.removeMonster(mon);
        break;
      }
    }
  }
}

public void addMonster(int roll) {
  Monster temp = new Monster();
  temp.setType(roll);
  getLatestPassage().addMonster(temp);
}

/**
* generates the PassageSections for the passage.
**/
public void generatePassageSections() {
  PassageSection temp = new PassageSection(randomDescriptionGen());
  addPassageSection(temp);
  temp = new PassageSection("passage ends in Door to a Chamber");
  addPassageSection(temp);
}

/**
* @return index of passage.
**/
public int getIndex() {
  return index;
}

/**
* @param theIndex new index of passage.
**/
public void setIndex(final int theIndex) {
  index = theIndex;
}

/**
* @return random description of PassageSection from table in A2.
*/
private String randomDescriptionGen() {
  Random r = new Random();
  int low = 0;
  int high = 4;
  int roll = r.nextInt(high - low) + low;
  ArrayList<String> table;
  table = generatePassageTable();
  return table.get(roll);
  }

  private ArrayList<String> generatePassageTable() {
    ArrayList<String> passageTable = new ArrayList<String>();
    passageTable.add("passage goes straight for 10 ft");
    passageTable.add("passage turns to left and continues for 10 ft");
    passageTable.add("passage turns to right and continues for 10 ft");
    passageTable.add("Monster!,passage goes straight for 10 ft");
    return passageTable;
  }
}
