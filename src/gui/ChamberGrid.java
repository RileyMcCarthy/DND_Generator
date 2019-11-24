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

  public ChamberGrid(BorderPane border) {
    getTextures();
    initBackground();
  }

  public void drawSquare(int s) {
    int start = 8/2 - s/2;
    for (int i=0;i<s;i++) {
      for (int j=0;j<s;j++) {
        addFloorCell(i+start, j+start);
      }
    }
  }

  public void drawRectangle(int w,int h) {
    int startw = 8/2 - w/2;
      int starth = 8/2 - h/2;
    for (int i=0;i<w;i++) {
      for (int j=0;j<h;j++) {
        addFloorCell(i+startw, j+starth);
      }
    }
  }

  public void initBackground() {
    int row = 8;
    int col = 8;
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
  }

}
