package sample;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import sample.RunGame;


public class Controller  implements Initializable{

    @FXML  private Button roomButton;
    @FXML  private Button settingButton;
    @FXML  private Button exitButton;
    @FXML  private Button extraButton;
    @FXML  private Button soundButton;

    public static int choice=0;

    @FXML public ChoiceBox<String> comboBoxBase;

    Main pezi=new Main();

    ObservableList<String> listBase= FXCollections.observableArrayList("base1","base2","base3");

    //comboBoxBase.setItems(listBase);

    @FXML private ImageView baseIv=new ImageView();

    public void image1Pressed(ActionEvent event) throws Exception
    {
        choice=1;
        System.out.println("jjjjj");
    }
    public void image2Pressed(ActionEvent event) throws Exception
    {
        choice=2;
    }

    public void baseComboShow(ActionEvent event) throws Exception{

        if(comboBoxBase.getValue().equals("base1"))
        {
            Image img= new Image("/Images/Base/base1.jpg");
            baseIv.setImage(img);
        }
        else   if(comboBoxBase.getValue().equals("base2"))
        {
            Image img= new Image("/Images/Base/base2.jpg");
            baseIv.setImage(img);
        }
        else   if(comboBoxBase.getValue().equals("base3"))
        {
            Image img= new Image("/Images/Base/base3.jpg");
            baseIv.setImage(img);
        }


        System.out.println(comboBoxBase.getValue());

    }



    public void playPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("RoomMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

        sceneNew.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {

                System.out.println("Escape key was pressed");
            }
        });
    }

    public void hsMenuPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("HighScore.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

        sceneNew.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {

                System.out.println("Escape key was pressed");
            }
        });
    }

    public void creditMenuPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("CreditMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

        sceneNew.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.out.println("Escape key was pressed");
            }
        });
    }

    public void extraPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("ExtraMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();


    }

    public void settingPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("settingMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();
    }

    public void firstMapPressed(ActionEvent event) throws IOException
    {
       /* Parent windowNew= FXMLLoader.load(getClass().getResource("FirstMap.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();*/

        boolean answer= AlertBox.display("Sure","Are you SURE more than 2 player Connected ?");
        if(answer)
        {
            Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();
            windowOld.close();
            pezi.Play();
        }


    }

    public void exitPressed(ActionEvent event) throws IOException
    {
        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();
        windowOld.close();
    }


    public void backToMain(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

    }
    public void helpPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("HelpMenu1.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

    }

    public void backToExtra(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("ExtraMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();

    }



    public void soundPressed(ActionEvent event) throws IOException
    {
        Parent windowNew= FXMLLoader.load(getClass().getResource("soundMenu.fxml"));
        Scene sceneNew = new Scene(windowNew);

        //This line gets the Stage information
        Stage windowOld = (Stage)((Node)event.getSource()).getScene().getWindow();

        windowOld.setScene(sceneNew);
        windowOld.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //comboBoxBase.setItems(listBase);
    }
}
