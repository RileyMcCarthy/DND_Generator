package gui;

import mccarthr.Level;
import mccarthr.Chamber;
import mccarthr.Passage;
import mccarthr.Door;
import java.util.ArrayList;

public class Controller {
  private Interface gui;
  private Level level;

  public Controller(Interface theGui) {
    gui = theGui;
    level = new Level();
    level.generateLevel();
    level.printDescription();
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

  public void chamberSelectChange(String value) {
    int index = Integer.parseInt(value.replaceAll("\\D+",""));
    gui.updateDoors(level.getDoorsFromChamber(index));
    gui.printDescription(level.getChamber(index).getDescription());
  }

  public void passageSelectChange(String value) {
    int index = Integer.parseInt(value.replaceAll("\\D+",""));
    gui.updateDoors(level.getDoorsFromPassage(index));
  }

}
