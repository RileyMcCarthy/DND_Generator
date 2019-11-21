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

public class Interface extends Application {

  private Controller controller;
  //top level layout
  private BorderPane root;
  //second level layouts
  private VBox left;
  private VBox right;
  private ToolBar top;
  private TextArea bottom;
  //third level layouts
  private ListView<String> chamberView;
  private ListView<String> passageView;
  private ComboBox doorBox;

  @Override
    public void start(Stage stage) {
      controller = new Controller(this);

      root = setupLayout();

      stage.setTitle("Dungeons and Dragons Level Generator");
      Scene scene = new Scene(root, 800,600);
      stage.setScene(scene);
      stage.show();
    }

    private BorderPane setupLayout() {
      BorderPane border = new BorderPane();
      border.setTop(setupToolBar());
      border.setLeft(setupLeft());
      border.setRight(setupRight());
      border.setBottom(setupBottom());
      return border;
    }

    private VBox setupLeft() {
      left = new VBox();
      left.setPadding(new Insets(5));
      left.setAlignment(Pos.CENTER);
      chamberView = new ListView<String>();
      passageView = new ListView<String>();
      ArrayList<Integer> chamberIndexes = controller.getChamberIndexArray();
      ArrayList<Integer> passageIndexes = controller.getPassageIndexArray();
      for (Integer num : chamberIndexes) {
        chamberView.getItems().add("Chamber #"+num);
      }
      for (Integer num : passageIndexes) {
        passageView.getItems().add("Passage #"+num);
      }
      chamberView.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
        controller.chamberSelectChange(new_val);
      });

      passageView.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
        controller.passageSelectChange(new_val);
      });
      left.setPrefWidth(150);
      left.getChildren().add(new Label("Chambers"));
      left.getChildren().add(chamberView);
      left.getChildren().add(new Label("Passages"));
      left.getChildren().add(passageView);
      return left;
    }

    private VBox setupRight() {
      right = new VBox();
      doorBox = new ComboBox();
      doorBox.setValue("Doors");
      right.getChildren().add(doorBox);
      return right;
    }

    private TextArea setupBottom() {
      bottom = new TextArea();
      bottom.setEditable(false);
      return bottom;
    }

    private ToolBar setupToolBar() {
      top = new ToolBar();
      top.getItems().add(new Button("New"));
      top.getItems().add(new Button("Open"));
      top.getItems().add(new Button("Save"));
      return top;
    }

    public void updateDoors(ArrayList<Integer> indexes) {
      doorBox.getItems().removeAll(doorBox.getItems());
      for (Integer num : indexes) {
        doorBox.getItems().add("Door #"+num);
      }
      doorBox.getSelectionModel().selectFirst();
    }

    public void printDescription(String desc) {
      bottom.setText(desc);
    }

  public static void main(String[] args) {
    launch(args);
  }

}
