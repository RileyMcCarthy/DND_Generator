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
  int startw;
  int starth;
  int maxw;
  int maxh;
  int doorl;
  int doorr;
  int dooro;

  public ChamberGrid(BorderPane border) {
    getTextures();
    initBackground();
    doorl=0;
    doorr=0;
    dooro=0;
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

  public void drawRectangle(int w,int h) {
    int maxw = w;
    int maxh = h;
    startw = 8/2 - w/2;
    starth = 8/2 - h/2;
    for (int i=0;i<w;i++) {
      for (int j=0;j<h;j++) {
        addFloorCell(i+startw, j+starth);
      }
    }
  }

  public void initBackground() {
    int row = 8;
    int col = 8;
    doorl=0;
    doorr=0;
    dooro=0;
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
  }

}
