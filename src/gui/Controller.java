package gui;

import mccarthr.Level;
import mccarthr.Chamber;
import mccarthr.Passage;
import mccarthr.Door;
import java.util.ArrayList;
import dnd.models.Trap;
import dnd.models.Monster;

public class Controller {
  private Interface gui;
  private Level level;

  public Controller(Interface theGui) {
    gui = theGui;
    level = new Level();
    level.generateLevel();
  }

  public ArrayList<Integer> getChamberIndexArray() {
    ArrayList<Integer> temp = new ArrayList<Integer>();
    ArrayList<Chamber> chambers = level.getChambers();
    for (Chamber chamber : chambers) {
      temp.add(chamber.getIndex());
    }
    return temp;
  }

  public ArrayList<Integer> getPassageIndexArray() {
    ArrayList<Integer> temp = new ArrayList<Integer>();
    ArrayList<Passage> passages = level.getPassages();
    for (Passage passage : passages) {
      temp.add(passage.getIndex());
    }
    return temp;
  }

  public void spaceSelectChange(String value) {
    int index = parseNumber(value);
    if (index == -1)
      return;
    if (value.contains("Passage")) {
      gui.updateDoors(level.getDoorsFromPassage(index));
      gui.printDescription(level.getPassage(index).getDescription());
    }else {
      gui.updateDoors(level.getDoorsFromChamber(index));
      gui.printDescription(level.getChamber(index).getDescription());
    }
  }

  public void createDoorPopup(String value) {
    int index = parseNumber(value);
    if (index == -1)
      return;
    gui.updateDoorPopup(level.getDoor(index).getDescription());
  }

  public void createEditPopup(String value) {
    int index = parseNumber(value);
    if (index == -1)
      return;
    if (value.contains("Passage")) {
      Passage passage = level.getPassage(index);
      ArrayList<Monster> monsters = passage.getMonsters();
      ArrayList<String> mDesc = new ArrayList<String>();
      for (Monster monster : monsters) {
        mDesc.add(monster.getDescription());
      }
      gui.updateEditPopup(mDesc);
    }else {
      Chamber chamber = level.getChamber(index);
      ArrayList<Monster> monsters = chamber.getMonsters();
      ArrayList<String> mDesc = new ArrayList<String>();
      for (Monster monster : monsters) {
        mDesc.add(monster.getDescription());
      }
      gui.updateEditPopup(mDesc);
    }
  }

  public void removeMonster(String space, String desc) {
    if (space == null) {
      return;
    }
    int index = parseNumber(space);
    if (index == -1)
      return;
    if (space.contains("Passage")) {
      Passage passage = level.getPassage(index);
      passage.removeMonster(desc);
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.removeMonster(desc);
    }
    createEditPopup(space);
  }

  public void addMonster(String space, int roll) {
    int index = parseNumber(space);
    if (index == -1)
      return;
    if (space.contains("Passage")) {
      Passage passage = level.getPassage(index);
      passage.addMonster(roll);
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.addMonster(roll);
    }
    createEditPopup(space);
  }

  private Integer parseNumber(String str) {
    String temp = str.replaceAll("\\D+","");
    if (temp.equals("")) {
      return -1;
    }
    int index = Integer.parseInt(temp);
    return index;
  }

}
