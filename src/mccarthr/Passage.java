package mccarthr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
*passage objec that holds passagesections.
**/
public class Passage extends Space {

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
  toAdd.setIndex(thePassage.size());
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

/**
* @return the description of the passage.
*/
@Override
public String getDescription() {
  String desc = "            - Passage #" + index + " with passage sections:\n";
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

/**
* generates the PassageSections for the passage.
**/
public void generatePassageSections() {
  int sectionIndex = 0;
  PassageSection temp = new PassageSection(randomDescriptionGen());
  addPassageSection(temp);
  sectionIndex++;
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
* @return if while loop should exit.
**/
private boolean generationExit() {
  if (getLatestPassage() == null) {
    return true;
  }
  return getLatestPassage().isEnd();
}

/**
* @return random description of PassageSection from table in A2.
*/
private String randomDescriptionGen() {
  Random r = new Random();
  int low = 0;
  int high = 2;
  int roll = r.nextInt(high - low) + low;
  ArrayList<String> table = new ArrayList<String>();
  table = generatePassageTable();
  return table.get(roll);
  }

  private ArrayList<String> generatePassageTable() {
    ArrayList<String> passageTable = new ArrayList<String>();
    passageTable.add("passage goes straight for 10 ft");
    passageTable.add("passage turns to left and continues for 10 ft");
    passageTable.add("passage turns to right and continues for 10 ft");
    return passageTable;
  }
}
