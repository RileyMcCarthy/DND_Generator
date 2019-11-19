package mccarthr;

import dnd.models.Monster;
import dnd.models.Exit;

/**
*Represents a 10 ft section of passageway.
**/
public class PassageSection {

  /**
  **monster object in section.
  **/
  private Monster monster;
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
private Monster getMonster() {
return monster;
}

/**
* @return the section description.
**/
public String getDescription() {
  String desc = "";
  desc = desc.concat("                 - Passage section "
  + (getIndex() + 1) + ". " + description + " \n");
  if (getDoor() != null) {
    //  desc = desc.concat("            " + getDoor().getDescription());
    desc = desc.concat("                - Door in passage goes to spaces:\n");
    desc = desc.concat("                    -Chamber #"
    + ((Chamber) (getDoor().getSpaces().get(0))).getIndex() + "\n");
  }
  return desc;
}


/**
* @param theMonster adds monster to section.
**/
private void addMonster(final Monster theMonster) {
  monster = theMonster;
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
  initDoors();
  initDeadEnd();
  initPassageDirection();
}

private void initMonsters() {
  if (description.toLowerCase().contains("monster")) {
    monster = new Monster();
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
