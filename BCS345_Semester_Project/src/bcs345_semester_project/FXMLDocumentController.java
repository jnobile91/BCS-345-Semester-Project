package bcs345_semester_project;

import java.awt.MenuItem;
import java.awt.Desktop;
import java.awt.image.RenderedImage;
import java.util.Scanner;
import java.io.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import java.lang.Object.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;




public class FXMLDocumentController implements Initializable {   
   @FXML
private Pane canvasRoot;
    // Establishes initial selected shape and color
    private String selectedShape = "LINE";
    private Color selectedColor = Color.BLACK;
    //Scene scene = new Scene(root);
    
    // Establishes variables needed to create shapes
    double srtX = 0, srtY = 0;
    double endX = 0, endY = 0;
    
    private Label label;
    @FXML
    private ColorPicker mColorPicker;
    private Group c;
    private Text text = new Text("");
    private TextField textInput = new TextField();
    @FXML
    private Canvas mCanvas;
    @FXML
    private Slider mSlider;
      double scaleSizeX =1.3;
    double scaleSizeY =1.3;
 
    @FXML
    private void setShape(ActionEvent event) {
        Button btn =(Button)event.getSource();
         
         switch(btn.getText()){
            case   "Line": 
               selectedShape = "LINE";       
               break;
            case   "Rect":
               selectedShape = "RECT";       
               break;
            case   "Circle": 
               selectedShape = "CIRCLE";   
               break;
            case   "Pencil": 
               selectedShape = "PENCIL";
               break;
            case   "Paint":
               selectedShape = "PAINT";
               break;
            case   "Spray":                 
               selectedShape = "SPRAY";     
               break;
            case   "Eraser":
               selectedShape = "ERASER";  
               break;
            case   "Text":                 
               selectedShape = "TEXT";     
               break;
            case   "Zoom":                 
               selectedShape = "ZOOM";     
               break;
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
            case "TEXT":
                // TextField input = new TextField();
                textInput.setLayoutX(srtX + 20);
                textInput.setLayoutY(srtY + 20);
                textInput.setOnKeyReleased(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        gc.setFill(selectedColor);
                        gc.fillText(textInput.getText(), srtX + 20, srtY + 20);
                        canvasRoot.getChildren().remove(textInput);
                    }                
                });
                
//                 textInput.setOnMouseExited(mouseEvent -> {
//                  
//                        
//                        gc.fillText(textInput.getText(), srtX + 20, srtY + 20);
//                        canvasRoot.getChildren().remove(textInput);
//                 });
                canvasRoot.getChildren().add(textInput);
                break;
                case "ZOOM":    
               
                   
                    mCanvas.setScaleX((scaleSizeX+=.3));
                    mCanvas.setScaleY((scaleSizeY+=.3));
                   
                    
                 mCanvas.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    mCanvas.setScaleX((scaleSizeX-=.6));
                    mCanvas.setScaleY((scaleSizeY-=.6));      
                  
                    }
                });
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
                gc.setLineWidth((mSlider.getValue())*0.5);
                gc.lineTo(endX, endY);
                gc.stroke();
            break;
          case "PAINT": 
                gc.setLineWidth((mSlider.getValue())*5);
                gc.lineTo(endX, endY);
                gc.stroke();
            break;
          case "SPRAY":
                gc.beginPath();
                gc.lineTo(endX, endY);
                gc.stroke();
            break;
          case "ERASER":
                gc.clearRect(endX, endY, mSlider.getValue(), mSlider.getValue());
            break;
        
            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void selectColor(ActionEvent event) {
   
 
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
       mCanvas.setScaleX(1.0);
         mCanvas.setScaleY(1.0);
    }


    @FXML
    private void saveProgram(ActionEvent event) {
        // Necessary for Open/Save file function
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(new File("C:\\temp"));
        try {
            File file = fileChooser.showSaveDialog(new Stage());
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                }
            else {
                System.out.println("File already exists.");
                }
            }
          catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            }
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private AnchorPane anchorid;
    
    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        Stage stage = (Stage) anchorid.getScene().getWindow();
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(773, 544);
                mCanvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                //Logger.getLogger(JavaFX_DrawOnCanvas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void openProgram(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        Stage stage = (Stage) anchorid.getScene().getWindow();
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile != null){
            try {
                InputStream io = new FileInputStream(selectedFile);
                Image img = new Image(io);
                mCanvas.getGraphicsContext2D().drawImage(img, 0, 0);
            } catch (IOException ex){
               
            }
        }
        
    }
   
    @FXML
    private void displayReadme(ActionEvent event) {
        String readme = "README.md";
        String line = null;

        try
        {
            // Creates new text area to display README file
            TextArea readmeText = new TextArea();
            readmeText.setPrefWidth(700);
            readmeText.setPrefHeight(700);
            
            // Initializes FileReader to read from README file
            FileReader fileReader = new FileReader(readme);

            // Initializes BufferedReader to read through README file line by line
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Appends text area to include each line from README file, separated
            // by new lines using "\n"
            while((line = bufferedReader.readLine()) != null)
            {
                readmeText.appendText(line + "\n");
                System.out.println(line);
            }

            // Disables reader from being able to edit the README text displayed
            readmeText.setEditable(false);
            
            // Establishes objects needed to display README in a new window
            Group readmeGroup = new Group(readmeText);
            Stage readmeStage = new Stage();
            readmeStage.setTitle("README");
            Scene readmeScene = new Scene(readmeGroup, 700, 700);
            readmeStage.setScene(readmeScene);
            readmeStage.show();
            
            bufferedReader.close();
            
        }
        catch(IOException e)
        {
            System.out.println("Error reading '" + readme + "' file.");
        }
    }
   
} //end of program