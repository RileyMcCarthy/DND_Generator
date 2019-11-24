package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import java.io.*;

public class Interface extends Application {

  private Controller controller;

  private Stage myStage;
  //top level layout
  private BorderPane root;
  //second level layouts
  private VBox left;
  private VBox right;
  private ToolBar top;
  private TextArea bottom;
  private ChamberGrid centerLevel;
  //third level layouts
  private ListView<String> spaceView;
  private ComboBox doorBox;
  //Popups
  private Popup doorPane;
  private Popup editPane;

  @Override
    public void start(Stage stage) {
      controller = new Controller(this);
      myStage = stage;
      root = setupLayout();
      myStage.setTitle("Dungeons and Dragons Level Generator");
      Scene scene = new Scene(root, 800,600);
      myStage.setScene(scene);
      String temp = this.getClass().getResource("/res/myStyle.css").toExternalForm();
      scene.getStylesheets().add(temp);
      myStage.show();
    }

    private BorderPane setupLayout() {
      BorderPane border = new BorderPane();
      border.setTop(setupToolBar());
      border.setLeft(setupLeft());
      border.setRight(setupRight());
      border.setBottom(setupBottom());
      border.setCenter(setupCenter());
      return border;
    }

    private VBox setupLeft() {
      left = new VBox();
      left.getStyleClass().add("vbox");
      spaceView = new ListView<String>();
      ArrayList<Integer> chamberIndexes = controller.getChamberIndexArray();
      ArrayList<Integer> passageIndexes = controller.getPassageIndexArray();
      for (Integer num : chamberIndexes) {
        spaceView.getItems().add("Chamber #"+num);
      }
      for (Integer num : passageIndexes) {
        spaceView.getItems().add("Passage #"+num);
      }
      spaceView.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
        controller.spaceSelectChange(new_val);
      });
      Button edit = new Button("Edit");
      edit.setOnAction((ActionEvent event) -> {
            controller.createEditPopup(spaceView.getSelectionModel().getSelectedItem());
        });
      edit.getStyleClass().add("leftElements");
      left.getStyleClass().add("leftElements");
      left.getChildren().add(new Label("Spaces"));
      left.getChildren().add(spaceView);
      left.getChildren().add(edit);
      return left;
    }

    public void updateLeft() {
      spaceView.getItems().clear();
      ArrayList<Integer> chamberIndexes = controller.getChamberIndexArray();
      ArrayList<Integer> passageIndexes = controller.getPassageIndexArray();
      for (Integer num : chamberIndexes) {
        spaceView.getItems().add("Chamber #"+num);
      }
      for (Integer num : passageIndexes) {
        spaceView.getItems().add("Passage #"+num);
      }
    }

    private ChamberGrid setupCenter() {
      int width = 8;
      int height = 8;
      centerLevel = new ChamberGrid(root);
      centerLevel.drawRectangle(6,4);
      return centerLevel;
    }

    private VBox setupRight() {
      right = new VBox();
      right.getStyleClass().add("vbox");
      right.setPrefWidth(200);
      right.getStyleClass().add("leftElements");
      doorBox = new ComboBox();
      doorBox.setPrefWidth(200);
      doorBox.setValue("Doors");
      setupDoorPopup("Door description");
      Button showDesc = new Button("Show Door Description");
      Button hideDesc = new Button("Hide Door Description");
      showDesc.setOnAction((ActionEvent event) -> {
            controller.createDoorPopup((String)doorBox.getValue());
        });
      hideDesc.setOnAction((ActionEvent event) -> {
          doorPane.hide();
      });
      showDesc.setPrefWidth(300);
      hideDesc.setPrefWidth(300);
      right.getChildren().add(doorBox);
      right.getChildren().add(showDesc);
      right.getChildren().add(hideDesc);
      return right;
    }

    private TextArea setupBottom() {
      bottom = new TextArea();
      bottom.getStyleClass().add("textfield");
      bottom.setEditable(false);
      return bottom;
    }

    private ToolBar setupToolBar() {
      top = new ToolBar();
      Button open = new Button("Open");
      open.setOnAction((ActionEvent event) -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Save File");
        File saveFile = fileChooser.showOpenDialog(myStage);
        String path = saveFile.getPath();
        if (path != null)
          controller.openLevel(saveFile.getPath());
      });
      Button save = new Button("Save");
      save.setOnAction((ActionEvent event) -> {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Save Directory");
        File saveDir = chooser.showDialog(myStage);
        String path = saveDir.getPath();
        path = path.concat("/levelsave.sav");
        System.out.println(path);
        if (path != null)
          controller.saveLevel(path);
      });
      top.getItems().add(open);
      top.getItems().add(save);
      return top;
    }

    private void setupDoorPopup(String text) {
      if (doorPane == null) {
        doorPane = new Popup();
      }else {
        doorPane.getContent().removeAll(doorPane.getContent());
      }

      TextArea desc = new TextArea(text);
      doorPane.getContent().add(desc);
      desc.setStyle(" -fx-background-color: white;");
      desc.setMinWidth(300);
      desc.setMinHeight(200);
    }

    private void setupEditPopup(ArrayList<String> text) {
      if (editPane != null) {
        editPane.getContent().clear();
      }else {
        editPane = new Popup();
      }
      TextField monsterEntry = new TextField("75");
      HBox hbox = new HBox();
      VBox left = new VBox();
      ListView<String> right = new ListView<String>();
      int id = 1;
      for (String str : text) {
        right.getItems().add(str);
        id++;
      }
      Button add = new Button("Add Monster");
      add.setOnAction((ActionEvent event) -> {
          String temp = monsterEntry.getCharacters().toString();
          int roll = 30;
          try {
            roll = Integer.parseInt(temp);
          } catch (NumberFormatException e) {
            roll = 30;
          }
          controller.addMonster(spaceView.getSelectionModel().getSelectedItem(), roll);
      });
      Button remove = new Button("Remove Monster");
      remove.setOnAction((ActionEvent event) -> {
          controller.removeMonster(spaceView.getSelectionModel().getSelectedItem(), right.getSelectionModel().getSelectedItem());
      });
      Button close = new Button("Close Popup");
      close.setOnAction((ActionEvent event) -> {
          editPane.hide();
      });

      monsterEntry.setPrefColumnCount(1);
      left.getChildren().add(add);
      left.getChildren().add(monsterEntry);
      left.getChildren().add(remove);
      left.getChildren().add(close);
      hbox.setMinWidth(500);
      hbox.setMinHeight(300);
      hbox.getChildren().add(left);
      hbox.getChildren().add(right);
      editPane.getContent().add(hbox);
    }

    public void updateDoors(ArrayList<Integer> indexes) {
      doorBox.getItems().removeAll(doorBox.getItems());
      for (Integer num : indexes) {
        doorBox.getItems().add("Door #"+num);
      }
      doorBox.getSelectionModel().selectFirst();
    }

    public void updateDoorPopup(String text) {
      setupDoorPopup(text);
      doorPane.show(myStage);
    }

    public void updateEditPopup(ArrayList<String> text) {
      setupEditPopup(text);
      editPane.show(myStage);
    }

    public void printDescription(String desc) {
      bottom.setText(desc);
    }

  public static void main(String[] args) {
    launch(args);
  }

}
