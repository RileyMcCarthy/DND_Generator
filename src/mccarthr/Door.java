package mccarthr;

import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import java.util.Random;
import dnd.die.D20;

/**
*door object that contains properties.
* of door, and spaces that it connects.
**/
public class Door implements java.io.Serializable {

  /**
  * trap instance for door.
  **/
  private Trap trap;
  /**
  * trap instance for door.
  **/
  private transient Exit exit;

  /**
  * instance for door being trapped.
  **/
  private boolean trapped;
  /**
  * instance for door being open.
  **/
  private boolean open;
  /**
  * instance for door being archway.
  **/
  private boolean archway;
  /**
  * instance for door being locked.
  **/
  private boolean locked;
  /**
  * instance for doors direction.
  **/
  private String direction;
  /**
  * instance for doors location.
  **/
  private String location;
  /**
  * instance for doors index in space.
  **/
  private int index;
  /**
  * instance arrayList of spaces door connects.
  **/
  private ArrayList<Space> spaces;

  /**
  * die from 1-20.
  **/
  private transient D20 die20;

/**
* door constructor that inits and set defults.
**/
  public Door() {
    genMemory();
    setDefault();
    randomGenDoor();
  }

  private void genMemory() {
    exit = new Exit();
    die20 = new D20();
    spaces = new ArrayList<Space>();
  }

  private void setDefault() {
    trapped = false;
    open = false;
    archway = false;
    locked = false;
    location = exit.getLocation();
    direction = exit.getDirection();
  }

  /**
  * if no roll is given trap is randomly generated.
  * @param flag setting the door isTrapped.
  * @param roll roll to generate trap.
  **/
  public void setTrapped(final boolean flag, final int... roll) {
    if (flag) {
      setTrap(flag, roll);
      if (archway) {
        trapped = false;
      } else {
        trapped = flag;
      }
    } else {
      trap = null;
    }
  }

  /**
  * @param flag sets if door has trap.
  * @param roll optional sets roll.
  **/
  private void setTrap(final boolean flag, final int... roll) {
    trap = new Trap();
    if (roll.length == 0) {
      trap.chooseTrap(die20.roll());
    } else {
      trap.chooseTrap(roll[0]);
    }
  }

  /**
  *@param flag sets if door is open.
  **/
  public void setOpen(final boolean flag) {
    if (archway) {
      open = true;
    } else {
      open = flag;
    }
    if (flag) {
      locked = false;
    }
  }

  /**
  * @param flag sets the door as archway.
  **/
  public void setArchway(final boolean flag) {
    archway = flag;
    if (flag) {
      locked = false;
      setOpen(true);
      setTrapped(false);
    }
  }

  /**
  * @return if door isTrapped.
  **/
  public boolean isTrapped() {
    return trapped;
  }

/**
* @return if door is open.
**/
  public boolean isOpen() {
    return open;
  }

  /**
  * @return if door is archway.
  **/
  public boolean isArchway() {
    return archway;
  }

  /**
  *@return the trap description if trap exists, otherwise null.
  **/
  public String getTrapDescription() {
    if (trapped) {
      return trap.getDescription();
    }
    return null;
  }

  /**
  * @return the two spaces that are connected by the door.
  **/
  public ArrayList<Space> getSpaces() {
    return spaces;
  }


  /**
  * @param s space to add to door.
  **/
  public void setOneSpace(final Space s) {
    if (s != null) {
      spaces.add(s);
      s.setDoor(this);
    }
  }

  /**
  * @param spaceOne first space to add to door.
  * @param spaceTwo second space to add to door.
  **/
  public void setSpaces(final Space spaceOne, final Space spaceTwo) {
    setOneSpace(spaceOne);
    setOneSpace(spaceTwo);
  }

  /**
  *  @return description of door.
  **/
  public String getDescription() {
    String desc = "    The door is on "
    + location + " and " + direction + "\n";
    if (archway) {
      desc = desc.concat(
      "        - The door is an archway\n");
    } else {
      if (locked) {
        desc = desc.concat(
        "        - The door is locked\n");
      } else {
        desc = desc.concat(
        "        - The door is unlocked\n");
      }
      if (trapped) {
        desc = desc.concat(
        "        - The door is trapped\n");
      }
      if (open) {
        desc = desc.concat(
        "        - The door is open\n");
      } else {
        desc = desc.concat(
        "        - The door is closed\n");
      }
    }
    desc = desc.concat(initDoorDescription());
    return desc;
  }

  private String initDoorDescription() {
    int check = 0;
    String desc = "";
    desc = desc.concat("        -Door connects to spaces:\n");
    for (Space space : spaces) {
      //TODO make this description of space
      if (check != 0) {
        desc = desc.concat(space.getDescription());
      } else {
        check++;
      }
    }
    return desc;
  }

  /**
  * @param dir new direction of door.
  **/
  public void setDirection(final String dir) {
    direction = dir;
  }

  /**
  * @param loc new location of door.
  */
  public void setLocation(final String loc) {
    location = loc;
  }

  /**
  * @return index of door.
  **/
  public int getIndex() {
    return index;
  }

  /**
  *  randomly generate door from table in A2.
  **/
  private void randomGenDoor() {
    int rand;
    rand = randomNumber(1, 20);
    if (rand == 1) {
      setTrapped(true);
    }
    rand = randomNumber(1, 6);
    if (rand == 1) {
      locked = true;
    }
    rand = randomNumber(1, 6);
    if (rand == 1) {
      setOpen(true);
    }
    rand = randomNumber(1, 10);
    if (rand == 1) {
      setArchway(true);
    }
  }

  /**
  *  generate random number between min-max.
  * @param min lowest number to generate.
  * @param max highest number to generate.
  * @return the random number.
  **/
  private int randomNumber(final int min, final int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
  }

  /**
  *  @param id new index of door.
  **/
  public void setIndex(final int id) {
    index = id;
  }

}
