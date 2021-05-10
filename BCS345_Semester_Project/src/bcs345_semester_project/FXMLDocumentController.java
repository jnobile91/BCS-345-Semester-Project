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

/* 
*   @authors
*   Joseph Nobile
*   Juan Marrero
*   Michael Trant
*   Steven Lannon
*/
public class FXMLDocumentController implements Initializable {   
    @FXML
    private Pane canvasRoot;
    // Establishes initial selected shape and color
    private String selectedShape = "LINE";
    private Color selectedColor = Color.BLACK;
    
    // Establishes variables needed to for application
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
 
    // Function called when selecting a tool in the toolbar
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

    /* 
    *   Function called on initial mouse click. This is used to
    *   'reset' the starting point of all tools.
    */
    @FXML
    private void startShape(MouseEvent event) {
        srtX = event.getX();
        srtY = event.getY();
    }
    
    /*
    *   Functions called we releasing the mouse
    *   after selecting Line, Rectangle, Circle/Oval,
    *   Text, or Zoom tools.
    */
    @FXML
    private void startDraw(MouseEvent event) {
        // Retrieves the end point once the mouse click is released
        endX = event.getX();
        endY = event.getY();
        
        // Creates GraphicsContext needed to establish shapes, color, width, etc.
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        
        // beginPath method used to reset each shape
        gc.beginPath();
        
        /* 
        *   Sets the color of the shape to be created based upon which
        *   color is selected with the color selector tool
        */
        gc.setStroke(selectedColor);
        
        // Used to log the shape and color of each shape created
        System.out.println(selectedShape+" "+selectedColor);
        
        /* 
        *   Sets the width of the shape to be created based upon which
        *   width is selected with the width selector tool
        */
        gc.setLineWidth(mSlider.getValue());
        
        /*
        *   Uses a switch to create the chape that is selected
        *   from the toolbar
        */
        switch(selectedShape){
          case "LINE":   gc.strokeLine(srtX, srtY, endX, endY);
            break;
          case "RECT":  
              /*
              * Iterates through which direction the shape is being created
              * and allows the shape to be created from any start/end point
              */
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
              /*
              * Iterates through which direction the shape is being created
              * and allows the shape to be created from any start/end point
              */
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
                canvasRoot.getChildren().add(textInput);
                break;
            case "ZOOM":    
                // Zooms IN if left mouse button is clicked
                mCanvas.setScaleX((scaleSizeX+=.3));
                mCanvas.setScaleY((scaleSizeY+=.3));
                
                // Zooms OUT if right mouse button is clicked
                mCanvas.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    mCanvas.setScaleX((scaleSizeX-=.6));
                    mCanvas.setScaleY((scaleSizeY-=.6));
                    }
                });
                break;
        }
    }
    
    /* 
    *   Function called when dragging mouse for Pencil,
    *   Paintbrush, Spray, and Eraser tools
    */
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

    // Function called when selecting a color from the toolbar
    @FXML
    private void selectColor(ActionEvent event) {
            Button btn =(Button)event.getSource();   
            
         // Switch used for color presets
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
         
        // Establishes ColorPicker object
        mColorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = mColorPicker.getValue();
                selectedColor = c;
               }
            });
        }
  
    // Function called when selecting "New" from the menu
    @FXML
    private void newPage(ActionEvent event) {
       GraphicsContext gc = mCanvas.getGraphicsContext2D();
       gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
       mCanvas.setScaleX(1.0);
         mCanvas.setScaleY(1.0);
    }


    /* 
    *   ***** NOT USED *****
    *   First attempt at function called when selecting "Save" from the menu
    */
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

    // Function called when selecting "Close" from the menu
    @FXML
    private void closeProgram(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private AnchorPane anchorid;
    
    // Function called when selecting "Save" from the menu
    @FXML
    public void saveFile(ActionEvent event) {
        // Establishes FileChooser object used to open Windows Explorer
        FileChooser fileChooser = new FileChooser();

        // Specifies what types of files can be opened
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        Stage stage = (Stage) anchorid.getScene().getWindow();
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        // Uses iostream to save selected file, otherwise displays error message
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
    
    // Function called when selecting "Open" from the menu
    @FXML
    private void openProgram(ActionEvent event) {
        // Establishes FileChooser object used to open Windows Explorer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        
        // Specifies what types of files can be opened
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        Stage stage = (Stage) anchorid.getScene().getWindow();
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        // Uses iostream to load selected file, otherwise displays error message
        if (selectedFile != null){
            try {
                InputStream io = new FileInputStream(selectedFile);
                Image img = new Image(io);
                mCanvas.getGraphicsContext2D().drawImage(img, 0, 0);
            } catch (IOException ex){
               System.out.print("Error loading file.");
            }
        }
        
    }
   
    // Function called when selecting "Help" from the menu
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
   
}
// End of program