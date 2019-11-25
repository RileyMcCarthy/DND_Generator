package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

public class ChamberGrid extends GridPane {

  Image floorimage;
  Image backimage;
  Image doorimage;
  Image treasureimage;
  Image monsterimage;
  int startw;
  int starth;
  int maxw;
  int maxh;
  int doorl;
  int doorr;
  int dooro;
  int treasurec;
  int monsterc;
  int passagec;

  public ChamberGrid(BorderPane border) {
    getTextures();
    initBackground();
    doorl=0;
    doorr=0;
    dooro=0;
    treasurec=0;
    passagec=0;
    monsterc=0;
  }

  public void doorLeft() {
    Label cell = new Label();
    ImageView door = new ImageView(doorimage);
    door.setFitWidth(50);
    door.setFitHeight(50);
    door.setRotate(door.getRotate() - 90);
    cell.setGraphic(door);
    int newstart = starth+(maxh/2)+doorl;
    add(cell,startw,newstart);
    doorl++;
  }

  public void doorRight() {
    Label cell = new Label();
    ImageView door = new ImageView(doorimage);
    door.setFitWidth(50);
    door.setFitHeight(50);
    door.setRotate(door.getRotate() + 90);
    cell.setGraphic(door);
    int newstart = starth+(maxh/2)+doorr;
    add(cell,startw+maxw-1,newstart);
    doorr++;
  }

  public void doorOpposite() {
    Label cell = new Label();
    ImageView door = new ImageView(doorimage);
    door.setFitWidth(50);
    door.setFitHeight(50);
    cell.setGraphic(door);
    int newstart = startw+(maxw/2)+dooro;
    add(cell,newstart,starth);
    dooro++;
  }

  public void doorEntrance() {
    Label cell = new Label();
    ImageView door = new ImageView(doorimage);
    door.setFitWidth(50);
    door.setFitHeight(50);
    door.setRotate(door.getRotate() + 180);
    cell.setGraphic(door);
    add(cell,startw+maxw/2,starth+maxh-1);
    dooro++;
  }

  public void addTreasure() {
    Label cell = new Label();
    ImageView tresure = new ImageView(treasureimage);
    tresure.setFitWidth(50);
    tresure.setFitHeight(50);
    cell.setGraphic(tresure);
    int newstart = startw+(maxw/2)+dooro;
    if (treasurec ==0) {
      add(cell,startw+1,starth+1);
    }else if (treasurec ==1) {
      add(cell,startw+2,starth+1);
    }else if (treasurec == 2) {
      add(cell,startw+2,starth+2);
    }else {
      add(cell,startw+treasurec,starth+treasurec);
    }
    treasurec++;
  }
  public void addMonster() {
    Label cell = new Label();
    ImageView monster = new ImageView(monsterimage);
    monster.setFitWidth(50);
    monster.setFitHeight(50);
    cell.setGraphic(monster);
    int newstart = startw+(maxw/2)+dooro;
    if (treasurec ==0) {
      add(cell,startw+1,starth+1);
    }else if (treasurec ==1) {
      add(cell,startw+2,starth+1);
    }else if (treasurec == 2) {
      add(cell,startw+2,starth+2);
    }else {
      add(cell,startw+treasurec,starth+treasurec);
    }
    treasurec++;
  }

  public void drawRectangle(int w,int h) {
    maxw = w;
    maxh = h;
    startw = 8/2 - w/2;
    starth = 8/2 - h/2;
    for (int i=0;i<w;i++) {
      for (int j=0;j<h;j++) {
        addFloorCell(i+startw, j+starth);
      }
    }
  }

  public void drawRectanglePassage(String str) {
    int w =0;
    int h=0;
    int dw=0;
    int dh=0;
    ImageView door = new ImageView(doorimage);
    if (str.equals("right")) {
      starth=1;
      startw=0;
      w=3;
      h=5;
      dw=1;
      dh=1;
    }else if (str.equals("left")) {
      starth=3;
      startw=0;
      w=3;
      h=5;
      dw=1;
      dh=7;
      door.setRotate(door.getRotate() + 180);
    }else if (str.equals("end")) {
      w=5;
      h=3;
      starth=8/2 - h/2;
      startw=3;
      dw=7;
      dh=starth+1;
      door.setRotate(door.getRotate() + 90);
    }else {
      w=5;
      h=3;
      starth = 8/2 - h/2;
      startw=0;
      dw=0;
      dh=starth+1;
      door.setRotate(door.getRotate() - 90);
    }
    maxw = w;
    maxh = h;
    for (int i=0;i<w;i++) {
      for (int j=0;j<h;j++) {
        addFloorCell(i+startw, j+starth);
      }
    }
    Label cell = new Label();
    door.setFitWidth(50);
    door.setFitHeight(50);
    cell.setGraphic(door);
    add(cell,dw,dh);
    passagec++;
  }

  public void treasurePassage() {
    Label cell = new Label();
    ImageView tresure = new ImageView(treasureimage);
    tresure.setFitWidth(50);
    tresure.setFitHeight(50);
    cell.setGraphic(tresure);
    add(cell,6-treasurec*2,8/2 - 3/2+1);
    treasurec++;
  }

  public void monsterPassage() {
    Label cell = new Label();
    ImageView monster = new ImageView(monsterimage);
    monster.setFitWidth(50);
    monster.setFitHeight(50);
    cell.setGraphic(monster);
    add(cell,6-monsterc*2-1,8/2 - 3/2+1);
    monsterc++;
  }

  public void initBackground() {
    int row = 8;
    int col = 8;
    doorl=0;
    doorr=0;
    dooro=0;
    treasurec=0;
    passagec=0;
    monsterc=0;
    this.getChildren().clear();
    for (int i=0;i<row;i++) {
      for (int j=0;j<col;j++) {
        Label cell = new Label();
        ImageView background = new ImageView(backimage);
        background.setFitWidth(50);
        background.setFitHeight(50);
        cell.setGraphic(background);
        add(cell,i,j);
      }
    }
  }

  private void addFloorCell(int x, int y) {
    Label cell = new Label();
    ImageView floor = new ImageView(floorimage);
    floor.setFitWidth(50);
    floor.setFitHeight(50);
    cell.setGraphic(floor);
    add(cell,x,y);
  }

  private void getTextures() {
    backimage = new Image(getClass().getResourceAsStream("/res/voidtexture.jpg"));
    floorimage = new Image(getClass().getResourceAsStream("/res/Rectangular Tiles E.jpg"));
    doorimage = new Image(getClass().getResourceAsStream("/res/door.jpg"));
    treasureimage = new Image(getClass().getResourceAsStream("/res/treasure.jpg"));
    monsterimage = new Image(getClass().getResourceAsStream("/res/monster.jpg"));
  }

}
