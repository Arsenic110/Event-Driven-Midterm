package com.madeinorbit.client.view;

import com.madeinorbit.client.controller.ClientController;
import com.madeinorbit.client.model.Action;
import com.madeinorbit.client.model.Row;
import com.madeinorbit.client.model.Lecture;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.Comparator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuba Rodak
 */
public class MainView extends Application {
    
    private ClientController controller;
    
    private ComboBox<Action> actionBox;
    private DatePicker datePicker;
    private ComboBox<String> timeBox;
    private TextField roomField, moduleField;
    private Button sendButton, stopButton, connectButton;
    private Label statusLabel;
    private TextArea logArea;
    private TableView<Row> table;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            controller = new ClientController(this);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            System.exit(1);
        }

        BorderPane root = new BorderPane();
        
        //make title
        root.setTop(makeTitle());
        //make form
        root.setLeft(makeForm());
        //make display table
        root.setRight(makeDisplayTable());
        //make log
        root.setBottom(makeLogArea());
        
        Scene scene = new Scene (root, 1280, 840);
        stage.setTitle("Lecture Scheduler - Client");
        stage.setScene(scene);
        
        stage.show();
    }
    
    private Node makeTitle() {
        Label title = new Label("Lecture Scheduler - Client");
        
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        HBox box = new HBox(title);
        box.setPadding(new Insets(12));
        box.setAlignment(Pos.CENTER_LEFT);
        
        return box;
    }
    
    private Node makeForm() {
        actionBox = new ComboBox<>(FXCollections.observableArrayList(Action.values()));
        actionBox.getSelectionModel().select(Action.ADD);

        datePicker = new DatePicker();

        timeBox = new ComboBox<>(FXCollections.observableArrayList(
                "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00",
                "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00"
        ));
        timeBox.getSelectionModel().selectFirst();

        roomField = new TextField();
        roomField.setPromptText("Room (e.g. AG01, B102)");

        moduleField = new TextField();
        moduleField.setPromptText("Module (e.g. CS4096)");

        connectButton = new Button("Connect");
        sendButton = new Button("Send");
        sendButton.setDisable(true);
        stopButton = new Button("Disconnect");
        sendButton.setDisable(true);
        statusLabel = new Label("Ready");

        connectButton.setOnAction(e -> controller.connectAndHello());
        sendButton.setOnAction(e -> {

            Action a = this.actionBox.getValue();
            LocalDate d = this.datePicker.getValue();
            String t = this.timeBox.getValue();
            String r = this.roomField.getText();
            String m = this.moduleField.getText();

            controller.onSend(a, d, t, r, m);
        });
        stopButton.setOnAction(e -> controller.onStop());

        //actionBox.valueProperty().addListener((obs, o, n) -> updateFormForAction(n));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        int r = 0;
        grid.addRow(r++, new Label("Action:"), actionBox);
        grid.addRow(r++, new Label("Date:"), datePicker);
        grid.addRow(r++, new Label("Time:"), timeBox);
        grid.addRow(r++, new Label("Room:"), roomField);
        grid.addRow(r++, new Label("Module:"), moduleField);

        VBox box = new VBox(10, grid, connectButton, sendButton, stopButton, statusLabel);
        box.setPadding(new Insets(12));
        box.setPrefWidth(320);
        
        return box;
    }

    private Node makeDisplayTable() {
        table = new TableView<>();

        TableColumn<Row, String> d = column("Date", r -> r.date);
        TableColumn<Row, String> t = column("Time", r -> r.time);
        TableColumn<Row, String> r = column("Room", row -> row.room);
        TableColumn<Row, String> m = column("Module", row -> row.module);

        table.getColumns().addAll(d, t, r, m);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox box = new VBox(8, new Label("Schedule"), table);
        box.setPadding(new Insets(12));
        
        return box;
    }
    
    private Node makeLogArea() {
        logArea = new TextArea();
        
        logArea.setEditable(false);
        logArea.setPrefRowCount(6);
        VBox box = new VBox(6, new Label("Command Log"), logArea);
        box.setPadding(new Insets(10));
        
        return box;
    }
    
    public void onReady() {
        say("Server", "Connection successful.");
        connectButton.setDisable(true);
        sendButton.setDisable(false);
        stopButton.setDisable(false); //enable send & stop buttons
    }

    public void onError(String msg) {
        say("Error", msg);
    }

    public void onDisconnected() {
        say("Server", "Connection Terminated.");
        connectButton.setDisable(false); //enable connect button
        sendButton.setDisable(true);
        stopButton.setDisable(true); //disable send & stop buttons
    }

    public String say(String tag, String msg) {
        String s = "[" + tag + "] > " + msg + "\n";
        logArea.appendText(s);
        return s;
    }

    public void refreshDisplay(List<Lecture> lectures) {
        List<Row> rows = new ArrayList<>();
        for (Lecture l : lectures) {
            l.toString();
            rows.add(new Row(l.getDate().toString(), l.getTime(), l.getRoom(), l.getModule()));
        }
        table.setItems(FXCollections.observableArrayList(rows));
    }

    private void updateFormForAction(Action a) {
        boolean add = a == Action.ADD;
        boolean remove = a == Action.REMOVE;

        datePicker.setDisable(!(add || remove));
        timeBox.setDisable(!(add || remove));
        roomField.setDisable(!add);
        moduleField.setDisable(!add);
    }
    
    private <T> TableColumn<Row, T> column(String name, Callback<Row, javafx.beans.value.ObservableValue<T>> f) {
        TableColumn<Row, T> c = new TableColumn<>(name);
        c.setCellValueFactory(d -> f.call(d.getValue()));
        return c;
    }

}
