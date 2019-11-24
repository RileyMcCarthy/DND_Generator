package mccarthr;

import dnd.models.Monster;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.Percentile;

/**
*Represents a 10 ft section of passageway.
**/
public class PassageSection implements java.io.Serializable {

  /**
  **monster object in section.
  **/
  private ArrayList<Monster> monsters;
  private ArrayList<Trap> traps;
  /**
  **door object in section.
  **/
  private Door door;
  /**
   *description of section.
  **/
  private String description;
  /**
  *direction of section.
  **/
  private String direction;
  /**
  *String holding the passage description.
  **/
  private String passageDescription;
  /**
  *boolean indicating that section is last section.
  **/
  private boolean end;
  /**
  *index of section in passage.
  **/
  private int index;
  private transient Percentile die;

/**
* sets up the 10 foot section with default settings.
**/
public PassageSection() {
  this("passage goes straight for 10 ft");
}

/**
* sets up a specific passage based on the values sent in from.
* @param theDescription description of passagesection.
**/
public PassageSection(final String theDescription) {
  description = theDescription;
  direction = "\0";
  end = false;
  monsters = new ArrayList<Monster>();
  traps = new ArrayList<Trap>();
  die = new Percentile();
  initFromDescription();
}

/**
* @return the door that is in the.
**/
public Door getDoor() {
  return door;
}

/**
* @return the monster that is in the passage section, if there is one.
**/
public ArrayList<Monster> getMonsters() {
  return monsters;
}

/**
* @return the section description.
**/
public String getDescription() {
  String desc = "";
  desc = desc.concat("    - Passage section "
  + getIndex() + ". " + description + " \n");
  for (Monster monster : monsters) {
    desc = desc.concat("        -Monster: "+monster.getDescription()+"\n");
  }
/*  if (getDoor() != null) {
    //  desc = desc.concat("            " + getDoor().getDescription());
    desc = desc.concat("                - Door in passage goes to spaces:\n");
    desc = desc.concat("                    -Chamber #"
    + ((Chamber) (getDoor().getSpaces().get(0))).getIndex() + "\n");
  }*/
  return desc;
}


/**
* @param theMonster adds monster to section.
**/
public void addMonster(final Monster theMonster) {
  monsters.add(theMonster);
}

/**
* @param theTrap adds trap to section.
**/
public void addTrap(final Trap theTrap) {
  traps.add(theTrap);
}

/**
* @param theMonster removes monster.
**/
public void removeMonster(final Monster theMonster) {
  monsters.remove(theMonster);
}

/**
* @param theTrap removes trap.
**/
public void removeTrap(final Trap theTrap) {
  traps.remove(theTrap);
}

/**
* @param theDoor new door to add to section.
**/
public void setDoor(final Door theDoor) {
  door = theDoor;
}

/**
* @param id new index of passage.
**/
public void setIndex(final int id) {
  index = id;
}

/**
* @return index of section.
**/
public int getIndex() {
  return index;
}

/**
* creates passage items from description.
**/
private void initFromDescription() {
  initMonsters();
  initDeadEnd();
  initPassageDirection();
}

private void initMonsters() {
  if (description.toLowerCase().contains("monster")) {
    Monster temp = new Monster();
    temp.setType(die.roll());
    monsters.add(temp);
  }
}

private void initDeadEnd() {
  if (description.toLowerCase().contains("end")) {
    setEnd(true);
  }
}

private void setEnd(final boolean flag) {
  end = true;
  direction = "straight";
}

private void initDoors() {
  Exit tmp = new Exit();
  if (description.toLowerCase().contains("door")) {
    door = new Door();
    door.setDirection(tmp.getDirection());
    if (description.toLowerCase()
        .contains("(door) to right")) {
      door.setLocation("right wall");
    } else if (description.toLowerCase()
          .contains("(door) to left")) {
      door.setLocation("right wall");
    } else if (description.toLowerCase().contains("end")) {
      door.setLocation("opposite wall");
    }
  /*  if (description.toLowerCase().contains("archway")) {
      //door.setArchway(true);
    }*/
  }
}

private void initPassageDirection() {
  if (description.toLowerCase().contains("turns to left")) {
    direction = "left";
  }

  if (description.toLowerCase().contains("turns to right")) {
    direction = "right";
  }

  if (description.toLowerCase().contains("straight")) {
    direction = "straight";
  }
}

/**
* @return if section is an end.
**/
public boolean isEnd() {
  return end;
}

}
