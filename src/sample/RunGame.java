package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.scene.media.MediaView;


import java.io.IOException;


public class RunGame {

    @FXML private Button btn_play;
    @FXML private Button btn_stop;
    @FXML private Button btn_vol_up;
    @FXML private Button btn_vol_down;

    Media musicFile = new Media("Menu.wav");
    MediaPlayer mediaplayer = new MediaPlayer(musicFile);

    public void btn_playPressed(ActionEvent event) throws IOException
    {
        mediaplayer.play();
    }

    public void btn_stopPressed(ActionEvent event) throws IOException
    {
        mediaplayer.stop();
    }


    public static void display()
    {

    }

}

