package gui;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.stage.Modality;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

public class EditInterface {

  Stage editStage;
  String space;
  HBox hbox;

  ListView<String> monsterView;
  ComboBox<String> monsterBox;

  ListView<String> treasureView;
  ComboBox<String> treasureBox;

  Controller controller;

  public EditInterface(String value,Controller theController) {
    System.out.println("new edit window");
    controller = theController;
    space = value;
    editStage = new Stage();
    editStage.setTitle("Edit Space");
    editStage.initModality(Modality.APPLICATION_MODAL);

    hbox = new HBox();

    Scene scene = new Scene(hbox, 800,300);

    setupMonsterOptions();
    displayMonsters();

    setupTreasureOptions();
    displayTreasures();

    editStage.setScene(scene);
    showWindow();
  }

  private void setupTreasureOptions() {
    Button add = new Button("Add Treasure");
    add.setOnAction((ActionEvent event) -> {
        String temp = treasureBox.getValue();
        boolean check = confirm("Adding: "+temp);
        if (check)
        controller.addTreasure(space, temp);
    });

    treasureBox = new ComboBox<String>();
    treasureBox.setValue("Treasures");
    for (String str : controller.getTreasureTable()) {
      treasureBox.getItems().add(str);
    }

    Button remove = new Button("Remove Treasure");
    remove.setOnAction((ActionEvent event) -> {
      boolean check = confirm("Removing: "+treasureView.getSelectionModel().getSelectedItem());
      if (check)
        controller.removeTreasure(space, treasureView.getSelectionModel().getSelectedItem());
    });

    VBox treasureOptions = new VBox();
    treasureOptions.getChildren().add(add);
    treasureOptions.getChildren().add(treasureBox);
    treasureOptions.getChildren().add(remove);
    hbox.getChildren().add(treasureOptions);
  }

  private void displayTreasures() {
    ArrayList<String> treasures = controller.getTreasureDescriptions(space);
    if (treasureView == null) {
    treasureView = new ListView<String>();
    hbox.getChildren().add(treasureView);
    }else {
      treasureView.getItems().clear();
    }

    for (String str : treasures) {
      treasureView.getItems().add(str);
    }
  }

  private void setupMonsterOptions() {
    Button add = new Button("Add Monster");
    add.setOnAction((ActionEvent event) -> {
        String temp = monsterBox.getValue();
        boolean check = confirm("Adding: "+temp);
        if (check)
        controller.addMonster(space, temp);
    });
    monsterBox = new ComboBox<String>();
    monsterBox.setValue("Monsters");
    for (String str : controller.getMonsterTable()) {
      monsterBox.getItems().add(str);
    }

    Button remove = new Button("Remove Monster");
    remove.setOnAction((ActionEvent event) -> {
      boolean check = confirm("Removing: "+monsterView.getSelectionModel().getSelectedItem());
      if (check)
        controller.removeMonster(space, monsterView.getSelectionModel().getSelectedItem());
    });

    VBox monsterOptions = new VBox();
    monsterOptions.getChildren().add(add);
    monsterOptions.getChildren().add(monsterBox);
    monsterOptions.getChildren().add(remove);
    hbox.getChildren().add(monsterOptions);
  }

  private void displayMonsters() {
    ArrayList<String> monsters = controller.getMonsterDescriptions(space);
    if (monsterView == null) {
    monsterView = new ListView<String>();
    hbox.getChildren().add(monsterView);
    }else {
      monsterView.getItems().clear();
    }
    int id = 1;
    for (String str : monsters) {
      monsterView.getItems().add(str);
      id++;
    }
  }

  private void clearStuff() {
    monsterView.getItems().clear();
  }

  private boolean confirm(String desc) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Are you sure?");
    alert.setContentText(desc);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
      return true;
    } else {
      return false;
    }
  }

  public void update(String value) {
    space = value;
    clearStuff();
    displayMonsters();
    displayTreasures();
  }


  public void showWindow() {
    editStage.show();
  }

}
