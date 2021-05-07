package bcs345_semester_project;


import java.awt.MenuItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import java.lang.Object;
import javafx.application.Platform;
import javafx.event.EventHandler;



public class FXMLDocumentController implements Initializable {
    
    private String selectedShape="LINE";
    
    private Color selectedColor=Color.BLACK;
    //Scene scene = new Scene(root);
    double srtX = 0, srtY = 0;
    double endX = 0, endY = 0;
    
    private Label label;
    @FXML
    private ColorPicker mColorPicker;
    private Group c;
    @FXML
    private Canvas mCanvas;
    @FXML
    private Slider mSlider;
 
    @FXML
    private void setShape(ActionEvent event) {
        Button btn =(Button)event.getSource();
         
         switch(btn.getText()){
             case   "Line": selectedShape="LINE";       break;
             case   "Rect": selectedShape="RECT";       break;
             case   "Circle": selectedShape="CIRCLE";   break;
             case   "Pencil": selectedShape="PENCIL";   break;
             case   "Spray": selectedShape="SPRAY";     break;
         }
    }

    @FXML
    private void startShape(MouseEvent event) {
        srtX = event.getX();
        srtY = event.getY();
    }
    
    @FXML
    private void startDraw(MouseEvent event) {
        endX = event.getX();
        endY = event.getY();
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        gc.beginPath();
        gc.setStroke(selectedColor);
        System.out.println(selectedShape+" "+selectedColor);
        gc.setLineWidth(mSlider.getValue());
        switch(selectedShape){
          case "LINE":   gc.strokeLine(srtX, srtY, endX, endY);
            break;
          case "RECT":  
              if ((srtX < endX) && (srtY < endY)) {
                  gc.strokeRect(srtX, srtY, (endX-srtX), (endY-srtY));
                }
              else if ((srtX > endX) && (srtY < endY)) {
                  gc.strokeRect(endX, srtY, (srtX-endX), (endY-srtY));
                }
              else if ((srtX < endX) && (srtY > endY)) {
                  gc.strokeRect(srtX, endY, (endX-srtX), (srtY-endY));
                }
              else {
                  gc.strokeRect(endX, endY, (srtX-endX), (srtY-endY));
                }
            break;
          case "CIRCLE":
              if ((srtX < endX) && (srtY < endY)) {
                  gc.strokeOval(srtX, srtY, (endX-srtX), (endY-srtY));
                }
              else if ((srtX > endX) && (srtY < endY)) {
                  gc.strokeOval(endX, srtY, (srtX-endX), (endY-srtY));
                }
              else if ((srtX < endX) && (srtY > endY)) {
                  gc.strokeOval(srtX, endY, (endX-srtX), (srtY-endY));
                }
              else {
                  gc.strokeOval(endX, endY, (srtX-endX), (srtY-endY));
                }
            break;
        }
    }
    
    @FXML
    private void startPencil(MouseEvent event) {
        endX = event.getX();
        endY = event.getY();
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        gc.setStroke(selectedColor);
        System.out.println(selectedShape+" "+selectedColor);
        gc.setLineWidth(mSlider.getValue());
        switch(selectedShape){
          case "PENCIL": 
                gc.lineTo(endX, endY);
                gc.stroke();
            break;
          case "SPRAY":
                gc.beginPath();
                gc.lineTo(endX, endY);
                gc.stroke();
            break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void selectColor(ActionEvent event) {
     // selectedColor = mColorPicker.getValue();
 
            Button btn =(Button)event.getSource();   
         switch(btn.getId()){
             case   "blue": selectedColor=Color.BLUE;       break;
             case   "red": selectedColor=Color.RED;       break;
             case   "green": selectedColor=Color.GREEN;       break;
             case   "orange": selectedColor=Color.ORANGE;       break;
             case   "yellow": selectedColor=Color.YELLOW;       break;
             case   "purple": selectedColor=Color.PURPLE;       break;
             case   "black": selectedColor=Color.BLACK;       break;
             case   "white": selectedColor=Color.WHITE;       break;
             case   "brown": selectedColor=Color.BROWN;       break;
             case   "grey": selectedColor=Color.GREY;       break;    
             
         }
 mColorPicker.setOnAction(new EventHandler() {
     public void handle(Event t) {
         Color c = mColorPicker.getValue();
         selectedColor = c;
     }
 });
    }

    @FXML
    private void newPage(ActionEvent event) {
       GraphicsContext gc = mCanvas.getGraphicsContext2D();
       gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
    }

    @FXML
    private void openProgram(ActionEvent event) {
    }

    @FXML
    private void saveProgram(ActionEvent event) {
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        Platform.exit();
              System.exit(0);
    }
} //end of program