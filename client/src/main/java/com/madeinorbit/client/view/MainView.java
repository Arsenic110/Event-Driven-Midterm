package com.madeinorbit.client.view;

import com.madeinorbit.client.controller.ClientController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kuba Rodak
 */
public class MainView extends Application {
    private ClientController controller;
    private TextArea outputArea = new TextArea();
    private Button connectBtn = new Button("Connect!");
    private Button stopBtn = new Button ("Disconnect!");
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            controller = new ClientController();
            controller.setView(this);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            System.exit(1);
        }

        outputArea.setPrefSize(600, 300);
        outputArea.setEditable(false);

        connectBtn.setOnAction(e -> controller.connectAndHello());
        stopBtn.setOnAction(e -> controller.closeConnection());
        stopBtn.setDisable(true);

        VBox root = new VBox(10, connectBtn, stopBtn, outputArea);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 640, 480);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Timetable Client");
        primaryStage.setOnCloseRequest(e -> {
            try {
                controller.closeConnection();
                System.exit(0);
            } catch (Exception f) {
                System.exit(1);
            }
        });

        primaryStage.show();
        outputArea.appendText("Client ready. Press connect!\n");
    }

    public void onReady() {
        sayServer("Connected and ready.");
        connectBtn.setDisable(true);
        stopBtn.setDisable(false); //enable stop button
    }

    public void onError(String msg) {
        sayError(msg);
    }

    public void onDisconnected() {
        sayServer("Disconnecting.");
        connectBtn.setDisable(false); //enable connect button
        stopBtn.setDisable(true); //disable stop button
    }

    private String sayServer(String msg) {
        String s = "[SERVER] > " + msg + "\n";
        outputArea.appendText(s);
        return s;
    }

    private String sayClient(String msg) {
        String s = "[CLIENT] > " + msg + "\n";
        outputArea.appendText(s);
        return s;
    }

    private String sayError(String msg) {
        String s = "[ERROR] > " + msg + "\n";
        outputArea.appendText(s);
        return s;
    }
}
