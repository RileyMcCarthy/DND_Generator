package mccarthr;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Trap;
import dnd.models.Stairs;
import dnd.die.D20;
import java.util.Random;
import java.util.ArrayList;
import dnd.exceptions.UnusualShapeException;

/**
* chamber class that can generate a random chamber.
**/
public class Chamber extends Space implements java.io.Serializable {

  /**
  * contents of the chamber.
  **/
  private ChamberContents myContents;
  /**
  * shape of the chamber.
  **/
  private ChamberShape myShape;
  /**
  * arraylist of doors in chamber.
  **/
  private ArrayList<Door> doors;
  /**
  * arrayList of monsters in chamber.
  **/
  private ArrayList<Monster> monsters;
  /**
  * arrayList of treasures in chamber.
  **/
  private ArrayList<Treasure> treasures;
  /**
  * arrayList of stairs in chamber.
  **/
  private ArrayList<Stairs> stairs;
  /**
  * arrayList of traps in chamber.
  **/
  private ArrayList<Trap> traps;
  /**
  * index of chamberin level.
  **/
  private int index;
  /**
  * die from 1-20.
  **/
  private transient D20 die20;

/**
* this is the chamber contructor that init chamber.
**/
public Chamber() {
  initMem();
  myContents.chooseContents(die20.roll());
  initContents();
  genShape();
}

private void initMem() {
  myContents = new ChamberContents();
  die20 = new D20();
  monsters = new ArrayList<Monster>();
  treasures = new ArrayList<Treasure>();
  stairs = new ArrayList<Stairs>();
  traps = new ArrayList<Trap>();
  doors = new ArrayList<Door>();
}

/**
* @param theShape new shape of chamber.
**/
public void setShape(final ChamberShape theShape) {
  myShape = theShape;
  initDoors(myShape.getNumExits());
}

/**
* @return shape of the chamber.
**/
public ChamberShape getChamberShape() {
  return myShape;
}

/**
* @return arraylist of doors in chamber.
**/
public ArrayList<Door> getDoors() {
  if (doors.size() == 0) {
    return null;
  }
  return doors;
}

/**
*@param theMonster monster to add to the chamber.
**/
public void addMonster(final int roll) {
  Monster temp = new Monster();
  temp.setType(roll);
  monsters.add(temp);
}

/**
* @return array of monsters in chamber.
**/
public ArrayList<Monster> getMonsters() {
  return monsters;
}

/**
* @return array of monsters in chamber.
**/
public ArrayList<Treasure> getTreasures() {
  return treasures;
}

/**
* @param theTreasure treasure to add to chamber.
**/
public void addTreasure(int roll) {
  Treasure temp = new Treasure();
  temp.chooseTreasure(roll);
  treasures.add(temp);
}

/**
* @return arrayList of treasure in chamber.
**/
public ArrayList<Treasure> getTreasureList() {
  //original
  return treasures;
}


/**
* abstract method from space.
* @return description of chamber.
**/
@Override
public String getDescription() {
  String desc = "Chamber #" + index + " Information:\n";
  desc = desc.concat("    Chamber shape: " + myShape.getShape() + "\n");
  desc = desc.concat(getDimDescription());
  //desc = desc.concat(getDoorsDescription());
  desc = desc.concat(getContentsDescription());
  desc = desc.concat(getStairsDescription());
  desc = desc.concat(getTrapDescription());
  desc = desc.concat(getTreasureDescription());

  return desc;
}

private String getDimDescription() {
  String tmp1;
  String tmp2;
  String desc = "";
  try {
    tmp1 = "    Chamber Length: " + myShape.getLength() + ".\n";
    tmp2 = "    Chamber Width: " + myShape.getWidth() + ".\n";
  } catch (UnusualShapeException e) {
    tmp1 = "    Chamber Length: Not Avaliable\n";
    tmp2 = "    Chamber Width: Not Avaliable\n";
  }
  desc = desc.concat(tmp1);
  desc = desc.concat(tmp2);
  desc = desc.concat("    Chamber area: " + myShape.getArea() + "\n");
  return desc;
}

private String getDoorsDescription() {
  String desc = "";
  desc = desc.concat("    Number of doors: " + (doors.size()) + "\n");
  for (int i = 0; i < doors.size(); i++) {
    desc = desc.concat("        -Door #" + (i + 1) + " : \n");
    //desc = desc.concat(doors.get(i).getDescription()); TODO use this for door description
  }
  return desc;
}

private String getContentsDescription() {
  String desc = "Chamber contents: "
  + myContents.getDescription() + "\n";
  if (monsters.size() !=0)
    desc = desc.concat("    Monsters: \n");
  for (Monster mon : monsters) {
    desc = desc.concat("        - Monster type: " + mon.getDescription() + "\n");
  }
  return desc;
}

private String getStairsDescription() {
  String desc = "";
  if (stairs.size() != 0) {
    desc = desc.concat("    Stairs:\n        - "
    + stairs.get(0).getDescription() + "\n");
  }
  return desc;
}

private String getTrapDescription() {
  String desc = "";
  if (traps.size() != 0) {
    desc = desc.concat("    Traps:\n        - "
      + traps.get(0).getDescription() + "\n");
  }
  return desc;
}

private String getTreasureDescription() {
  String desc = "";
  if (treasures.size() != 0)
    desc = desc.concat("    Treasure: \n");
  for (Treasure treasure : treasures) {
    desc = desc.concat("        - "+treasures.get(0).getDescription() + "\n");
  }
  return desc;
}

/**
* abstract method from Space.
* @param newDoor door to add to the chamber.
**/
@Override
public void setDoor(final Door newDoor) {
  doors.add(newDoor);
}

/**
* setter to set position/index of chamber.
* @param id new index of chamber.
**/
public void setIndex(final int id) {
  index = id;
}

/**
* @return index of chamber.
**/
public int getIndex() {
  return index;
}

private void initContents() {
  index = 0;
  initMonsters();
  initStairs();
  initTreasure();
  initTraps();
}

/**
* @param numDoors the exits to convert to doors.
**/
private void initDoors(final int numDoors) {
  doors = new ArrayList<Door>();
  for (int i = 1; i <= numDoors; i++) {
    Door temp = new Door();
    temp.setIndex(i);
    temp.setOneSpace(this);
  }
}

public void removeMonster(String desc) {
  for (Monster mons : monsters) {
    if (desc.equals(mons.getDescription())) {
      monsters.remove(mons);
      break;
    }
  }
}


public void removeTreasure(String desc) {
  for (Treasure mons : treasures) {
    if (desc.equals(mons.getDescription())) {
      treasures.remove(mons);
      break;
    }
  }
}

/**
* creates a random shape for chamber.
**/
private void genShape() {
  myShape = ChamberShape.selectChamberShape(die20.roll());
  myShape.setNumExits(die20.roll());
  initDoors(myShape.getNumExits());
}

/**
* @return # of exits from chamber shape.
**/
public int getExitCount() {
  return myShape.getNumExits();
}

/**
* check if description has monsters, adds monsters accordingly.
**/
private void initMonsters() {
  if (myContents.getDescription().toLowerCase().contains("monster")) {
    Monster temp = new Monster();
    temp.setType(randomHundredNumber());
    monsters.add(temp);
  }
}

/**
* checks if description has stairs, adds stairs accordingly.
**/
private void initStairs() {
  if (myContents.getDescription().toLowerCase().contains("stairs")) {
    Stairs temp = new Stairs();
    temp.setType(die20.roll());
    stairs.add(temp);
  }
}

/**
* checks if description has traps, and adds traps accordingly.
**/
private void initTraps() {
  if (myContents.getDescription().toLowerCase().contains("trap")) {
    Trap temp = new Trap();
    temp.chooseTrap(die20.roll());
    traps.add(temp);
  }
}

/**
*checks if description has treasure, and adds treasure accordingly.
**/
private void initTreasure() {
  if (myContents.getDescription().toLowerCase().contains("treasure")) {
    Treasure temp = new Treasure();
    temp.chooseTreasure(randomHundredNumber());
    treasures.add(temp);
  }
}

private int randomHundredNumber() {
  int low = 1;
  int high = 100;
  Random r = new Random();
  int result = r.nextInt(high - low) + low;
  return result;
}

}
