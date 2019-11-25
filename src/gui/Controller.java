package gui;

import mccarthr.Level;
import mccarthr.Chamber;
import mccarthr.Passage;
import mccarthr.Door;
import java.util.ArrayList;
import dnd.models.Trap;
import dnd.models.Monster;
import java.io.*;
import dnd.models.Treasure;
import javafx.collections.ObservableList;


public class Controller {
  private Interface gui;
  private Level level;
  private ArrayList<String> monsterTable;
  private ArrayList<String> treasureTable;

  public Controller(Interface theGui) {
    gui = theGui;
    level = new Level();
    level.generateLevel();
    initMonsterTable();
    initTreasureTable();
  }

  private void initMonsterTable() {
    monsterTable = level.getAllMonsters();
  }

  public ArrayList<String> getMonsterTable() {
    return monsterTable;
  }

  private void initTreasureTable() {
    treasureTable = level.getAllTreasures();
  }

  public ArrayList<String> getTreasureTable() {
    return treasureTable;
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

  public ArrayList<String> doorDescription(ObservableList<String> door) {
    ArrayList<String> descriptions = new ArrayList<String>();
    for(String str : door) {
      int index = parseNumber(str);
      if (index == -1)
        break;
      descriptions.add(level.getDoor(index).getDescription());
    }
    return descriptions;
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

  public ArrayList<String> getMonsterDescriptions(String value) {
    int index = parseNumber(value);
    if (index == -1)
      return null;
    if (value.contains("Passage")) {
      Passage passage = level.getPassage(index);
      ArrayList<Monster> monsters = passage.getMonsters();
      ArrayList<String> mDesc = new ArrayList<String>();
      for (Monster monster : monsters) {
        mDesc.add(monster.getDescription());
      }
      return mDesc;
    }else {
      Chamber chamber = level.getChamber(index);
      ArrayList<Monster> monsters = chamber.getMonsters();
      ArrayList<String> mDesc = new ArrayList<String>();
      for (Monster monster : monsters) {
        mDesc.add(monster.getDescription());
      }
      return mDesc;
    }
  }

  public ArrayList<String> getTreasureDescriptions(String value) {
    int index = parseNumber(value);
    if (index == -1)
      return null;
    if (value.contains("Passage")) {
      Passage passage = level.getPassage(index);
      ArrayList<Treasure> treasures = passage.getTreasures();
      ArrayList<String> tDesc = new ArrayList<String>();
      for (Treasure treasure : treasures) {
        tDesc.add(treasure.getDescription());
      }
      return tDesc;
    }else {
      Chamber chamber = level.getChamber(index);
      ArrayList<Treasure> treasures = chamber.getTreasures();
      ArrayList<String> tDesc = new ArrayList<String>();
      for (Treasure treasure : treasures) {
        tDesc.add(treasure.getDescription());
      }
      return tDesc;
    }
  }

  public void removeTreasure(String space, String desc) {
    if (space == null) {
      return;
    }
    int index = parseNumber(space);
    if (index == -1)
      return;
    if (space.contains("Passage")) {
      Passage passage = level.getPassage(index);
      passage.removeTreasure(desc);
      gui.printDescription(passage.getDescription());
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.removeTreasure(desc);
      gui.printDescription(chamber.getDescription());
    }
    gui.setupEditPopup();
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
      gui.printDescription(passage.getDescription());
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.removeMonster(desc);
      gui.printDescription(chamber.getDescription());
    }
    gui.setupEditPopup();
  }

  public void addMonster(String space, String desc) {
    int index = parseNumber(space);
    if (index == -1)
      return;
    int roll = level.getMonsterRoll(desc);
    if (space.contains("Passage")) {
      Passage passage = level.getPassage(index);
      passage.addMonster(roll);
      gui.printDescription(passage.getDescription());
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.addMonster(roll);
      gui.printDescription(chamber.getDescription());
    }
    gui.setupEditPopup();
  }

  public void addTreasure(String space, String desc) {
    int index = parseNumber(space);
    if (index == -1)
      return;
    int roll = level.getTreasureRoll(desc);
    if (space.contains("Passage")) {
      Passage passage = level.getPassage(index);
      passage.addTreasure(roll);
      gui.printDescription(passage.getDescription());
    }else {
      Chamber chamber = level.getChamber(index);
      chamber.addTreasure(roll);
      gui.printDescription(chamber.getDescription());
    }
    gui.setupEditPopup();
  }

  public void saveLevel(String path) {
    try {
      FileOutputStream fileout = new FileOutputStream(path);
      ObjectOutputStream out = new ObjectOutputStream(fileout);
      out.writeObject(level);
      out.close();
      fileout.close();
      System.out.println("SAVED!");
    } catch(IOException i) {
      i.printStackTrace();
    }
  }

  public void openLevel(String path) {
    boolean nope;
    try {
      FileInputStream filein = new FileInputStream(path);
      ObjectInputStream in = new ObjectInputStream(filein);
      level = (Level) in.readObject();
      in.close();
      filein.close();
      nope = false;
    }catch(IOException i) {
      i.printStackTrace();
      nope = true;
    }catch(ClassNotFoundException c) {
      c.printStackTrace();
      nope = true;
    }
    if (!nope)
      updateLevel();
  }

  private void updateLevel() {
    gui.updateLeft();
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
