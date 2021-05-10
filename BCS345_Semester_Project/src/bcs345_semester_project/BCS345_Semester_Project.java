package bcs345_semester_project;


import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* 
*   @authors
*   Joseph Nobile
*   Juan Marrero
*   Michael Trant
*   Steven Lannon
*/
public class BCS345_Semester_Project extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*
        * The next few lines establish a window to hold and display our pain application.
        * All of the functionality is held in the FXMLDocumentControler.java file.
        */
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Paint Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}