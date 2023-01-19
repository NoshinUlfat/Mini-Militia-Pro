package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.AudioClip;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

//import static com.sun.javafx.scene.control.skin.Utils.getResource;


public class Main extends Application {

    // Obtracle Handling
    Map map=new Map();


    //Data input and output stream from server
    DataInputStream in;
    DataOutputStream out ;

    double total_time;

    boolean isReceiving = false;


    //initialize player

    int playerX=100,playerY=200;
    int playerX2=100,playerY2=800;

    int direction=0;
    int direction2=0;

    boolean isSheild=false;
    boolean isSheild2=false;

    double c_ammo=1.0;
    double c_ammo2=1.0;

    double player_change_health=1.0;
    double player2_change_health2=1.0;

    double shield_change1=1.0;
    double shield_change2=1.0;

    boolean isGun1=false,isGun2=false;
    boolean isGunSelected1=false,isGunSelected2=false;

    boolean isHealth1=false,isHealth2=false;

    final int[] initBulletPosX2 = new int[1];
    final int[] initBulletPosY2 = new int[1];
    final int[] initGrenadePosX2 = new int[1];
    final int[] initGrenadePosY2 = new int[1];


    public Image bg=new Image("file:bg.png");
    public Image bg2=new Image("file:bg2.png");
    public ImageView bgShow = new ImageView(bg);
    public ImageView bgShow2 = new ImageView(bg2);


    public Image playerL=new Image("file:5.png");
    public Image bullet=new Image("file:bullet_left.png");

    public Image fire=new Image("file:fire.png");


    public ImageView fireShow= new ImageView(fire);

    public ImageView playerLShow = new ImageView(playerL);
    public ImageView bulletShow = new ImageView(bullet);

    public Image playerL2=new Image("file:6.png");
    public Image bullet2=new Image("file:bullet_left.png");

    public ImageView playerLShow2 = new ImageView(playerL);
    public ImageView bulletShow2 = new ImageView(bullet);

    public Image fire2=new Image("file:fire.png");
    public ImageView fireShow2= new ImageView(fire);

    public Image shield=new Image("file:shield.png");
    public ImageView shield_Show1=new ImageView(shield);
    public ImageView shield_Show2=new ImageView(shield);
    public Image health=new Image("file:health.png");
    public ImageView healthShow1=new ImageView(health);
    public ImageView healthShow2=new ImageView(health);

    public Image gun=new Image("file:weapon_1.png");
    public ImageView gunShow1=new ImageView(gun);
    public ImageView gunShow2=new ImageView(gun);

    public Image grenade=new Image("file:grenade.png");
    public ImageView grenadeShow1=new ImageView(grenade);
    public ImageView grenadeShow2=new ImageView(grenade);
    public Image blast=new Image("file:blast_1.png");
    public ImageView blastShow1=new ImageView(blast);
    public ImageView blastShow2=new ImageView(blast);

    public Image win=new Image("file:win.jpg");
    public Image loss=new Image("file:loss.png");
    public ImageView winShow=new ImageView(win);
    public ImageView lossShow=new ImageView(loss);

    final ProgressBar player1_pBar=new ProgressBar(1);
    final ProgressBar player1_ammo_pBar=new ProgressBar(1);
    final ProgressBar player2_pBar=new ProgressBar(1);
    final ProgressBar player2_ammo_pBar=new ProgressBar(1);
    final ProgressBar shield1_pBar=new ProgressBar(1);
    final ProgressBar shield2_pBar=new ProgressBar(1);

    static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Play();
        /*FXMLLoader loader=new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent rootMenu=loader.load();
        // Controller controller=loader.getController();
        controller=(Controller)loader.getController();
        primaryStage.setTitle("Mini Militia PRO");
        primaryStage.setScene(new Scene(rootMenu, 1910, 990));
        primaryStage.show();*/

    }



    public void Play(){

        Socket socket = null;

        boolean serverStatus = false;


        //Game code is client , connect to server
        try {

            int port = 4444;
            String ip = "0.0.0.0";
            socket = new Socket(ip, port);
            serverStatus = true;


        } catch (IOException ex) {
            System.out.println("Server is off , Game can be RUN");
            serverStatus = false;
        }

        try {


            // Input and out put from server

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            isReceiving = true;

            //start a thread which will start listening for messages
            //from server and draw other player

            new ReceiveMessage(in).start();

            int countOfP = 0;
            DataInputStream in1 = new DataInputStream(socket.getInputStream());
            String count;
            try {

                count = in1.readUTF();
                countOfP = Integer.parseInt(count);
                System.out.println("Count ="+count);

            } catch (IOException e) {
                e.printStackTrace();
            }



            // send the name to the server!
            if (countOfP == 0) {
                 playerX=100;playerY=200;
                 playerX2=100;playerY2=800;
                out.writeUTF("player1");
                System.out.println("Only player 1 joined and ready ");
            } else {
                playerX=100;playerY=800;
                playerX2=100;playerY2=200;

                out.writeUTF("player2");
                System.out.println("Player 2 joined and Both player Ready ");
            }
        } catch (IOException ex) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ex1) {
                }
            }
        }


        AudioClip sound = new AudioClip("file:bgMusic.mp3");
        sound.setVolume(.5);
        sound.setCycleCount(AudioClip.INDEFINITE);
        sound.play();





        // stage creations

        Stage primaryStage=new Stage();

        Pane root=new Pane();
        Scene scene=new Scene(root,2000,1000);


        //initailizing player 1

       /* bgShow.setFitWidth(0);
        bgShow.setFitHeight(0);
        bgShow2.setFitWidth(0);
        bgShow2.setFitHeight(0);*/

        bgShow.setX(0);
        bgShow2.setX(-2000);

        playerLShow.setX(playerX);
        playerLShow.setY(playerY);
        fireShow.setFitWidth(200);
        fireShow.setFitHeight(150);
        fireShow.setX(-135);
        fireShow.setY(-150);
        bulletShow.setFitWidth(35);
        bulletShow.setFitHeight(35);
        bulletShow.setX(-135);
        bulletShow.setY(-133);
        grenadeShow1.setFitWidth(40);
        grenadeShow1.setFitHeight(40);
        grenadeShow1.setX(-100);

        final int[] initBulletPosX = new int[1];
        final int[] initBulletPosY = new int[1];

        final int[] initGrenadePosX = new int[1];
        final int[] initGrenadePosY = new int[1];


        //initailizing player 2

        playerLShow2.setX(playerX2);
        playerLShow2.setY(playerY2);
        fireShow2.setFitWidth(200);
        fireShow2.setFitHeight(150);
        fireShow2.setX(-135);
        fireShow2.setY(-150);
        bulletShow2.setFitWidth(35);
        bulletShow2.setFitHeight(35);
        bulletShow2.setX(-135);
        bulletShow2.setY(-133);
        grenadeShow2.setFitWidth(40);
        grenadeShow2.setFitHeight(40);
        grenadeShow2.setX(-100);

        shield_Show1.setFitWidth(75);
        shield_Show1.setFitHeight(75);
        shield_Show1.setX(-100);
        shield_Show2.setFitWidth(75);
        shield_Show2.setFitHeight(75);
        shield_Show2.setX(-100);

        healthShow1.setX(-200);
        healthShow2.setX(-200);

        blastShow1.setX(-2000);
        blastShow2.setX(-2000);

        winShow.setX(-3000);
        lossShow.setX(-3000);

        player1_pBar.setTranslateX(15);
        player1_pBar.setTranslateY(95);
        player1_pBar.setPrefWidth(200);
        player1_pBar.setPrefHeight(25);

        player1_ammo_pBar.setTranslateX(15);
        player1_ammo_pBar.setTranslateY(187);
        player1_ammo_pBar.setPrefWidth(200);
        player1_ammo_pBar.setPrefHeight(25);

        shield1_pBar.setTranslateX(15);
        shield1_pBar.setTranslateY(266);
        shield1_pBar.setPrefWidth(200);
        shield1_pBar.setPrefHeight(25);

        shield2_pBar.setTranslateX(1560);
        shield2_pBar.setTranslateY(266);
        shield2_pBar.setPrefWidth(200);
        shield2_pBar.setPrefHeight(25);

        player2_pBar.setTranslateX(1560);
        player2_pBar.setTranslateY(95);
        player2_pBar.setPrefWidth(200);
        player2_pBar.setPrefHeight(25);

        player2_ammo_pBar.setTranslateX(1560);
        player2_ammo_pBar.setTranslateY(187);
        player2_ammo_pBar.setPrefWidth(200);
        player2_ammo_pBar.setPrefHeight(25);

        gunShow1.setX(-300);
        gunShow2.setX(-300);

        System.out.println(controller.choice);

        if(controller.choice==1)
        {
            map.isChoice(controller.choice);
            bgShow.setX(0);
            bgShow.setY(0);
        }
        else if(controller.choice==2)
        {
            map.isChoice(controller.choice);
            bgShow2.setX(0);
            bgShow2.setY(0);
        }


        final long startNanoTime1 = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                 total_time = (now - startNanoTime1) / 1000000000.0;
                //   System.out.println(t);
               // bulletShow.setScaleX(-1);

                //System.out.println();
                if(player_change_health<0||player2_change_health2<0)
                {
                    System.out.println(total_time);
                    this.stop();
                }
            }
        }.start();



        // Own player movement and Shoot

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch ((event.getCode())){


                    case RIGHT:

                        try {
                            out.writeUTF("right");
                            System.out.println("right");
                        } catch (IOException ex) {
                        }


                        direction=1;
                        if(isSheild==true&&direction==1)
                            shield_Show1.setX(playerLShow.getX()+30+10);
                        ///N//when shield is true and right side view is true then position change with right side movement

                        else if(isSheild==true&&direction==2) shield_Show1.setX(playerLShow.getX()+123+10);

                        ///N//when shield is true and left side view is true then position change with right side movement

                        if(isHealth1==true&&(playerLShow.getX()+100)>1000&&(playerLShow.getX()+100)<1092&&(playerLShow.getY()+20)>800&&(playerLShow.getY()+20)<895)
                        {
                            isHealth1=false;
                            player1_pBar.setProgress(1);
                            healthShow1.setX(-300);
                        }
                        if(isHealth2==true&&(playerLShow.getX()+100)>400&&(playerLShow.getX()+100)<492&&(playerLShow.getY()+20)>400&&(playerLShow.getY()+20)<495)
                        {
                            isHealth2=false;
                            player1_pBar.setProgress(1);
                            healthShow2.setX(-300);
                        }
                        if((isGun1==true||isGun2==true)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                        {
                            isGunSelected1=true;
                            player1_ammo_pBar.setProgress(1);
                        }
                        if(isGunSelected1==true)
                        {
                            gunShow1.setX(playerLShow.getX()+35);
                            gunShow1.setY(playerLShow.getY());
                        }

                        if((playerLShow.getX()+100)>playerLShow2.getX()&&(playerLShow.getX()+100)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                        playerLShow.setX(playerLShow.getX()-15);

                        //fireShow.setX(-135);
                        playerLShow.setScaleX(-1);
                        if(map.isFire((playerLShow.getX()+22),(playerLShow.getY()+130))&&playerLShow.getY()<860)
                        {
                            fireShow.setX(-130);
                        }
                        if(isGunSelected1==true) gunShow1.setScaleX(-1);
                        //shield_Show1.setScaleX(-1);
                        playerLShow.setX(playerLShow.getX()+10);
                        fireShow.setX(playerLShow.getX()-50);
                        if(map.isObstacle(playerLShow.getX()+50,playerLShow.getY()+75))
                            playerLShow.setX(playerLShow.getX()-15);
                        System.out.println("Pressed == right");

                        break;

                    case LEFT:

                        try {
                            out.writeUTF("left");
                            System.out.println("left");
                        } catch (IOException ex) {
                        }

                        direction=2;
                        if(isSheild==true&&direction==1)
                            shield_Show1.setX(playerLShow.getX()+30-10);
                        else if(isSheild==true&&direction==2) shield_Show1.setX(playerLShow.getX()+65-10);
                        if(isHealth1==true&&(playerLShow.getX()+70)>1000&&(playerLShow.getX()+70)<1092&&(playerLShow.getY()+20)>800&&(playerLShow.getY()+20)<895)
                        {
                            isHealth1=false;
                            player1_pBar.setProgress(1);
                            healthShow1.setX(-300);
                        }
                        if(isHealth2==true&&(playerLShow.getX()+70)>400&&(playerLShow.getX()+70)<492&&(playerLShow.getY()+20)>400&&(playerLShow.getY()+20)<495)
                        {
                            isHealth2=false;
                            player1_pBar.setProgress(1);
                            healthShow2.setX(-300);
                        }
                        if((isGun1==true||isGun2==true)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                        {
                            isGunSelected1=true;
                            player1_ammo_pBar.setProgress(1);
                        }
                        if(isGunSelected1==true)
                        {
                            gunShow1.setX(playerLShow.getX()+30);
                            gunShow1.setY(playerLShow.getY());
                        }
                        if((playerLShow.getX()+70)>playerLShow2.getX()&&(playerLShow.getX()+70)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                            playerLShow.setX(playerLShow.getX()+15);
                       // fireShow.setX(-135);
                        if(map.isFire((playerLShow.getX()+130),(playerLShow.getY()+138))&&playerLShow.getY()<860)
                        {
                            fireShow.setX(-130);
                        }
                        playerLShow.setScaleX(1);
                        fireShow.setX(playerLShow.getX()+70);
                        if(isGunSelected1==true) gunShow1.setScaleX(1);
                        //shield_Show1.setScaleX(1);
                        playerLShow.setX(playerLShow.getX()-10);
                        if(map.isObstacle(playerLShow.getX()+50,playerLShow.getY()+75))
                            playerLShow.setX(playerLShow.getX()+15);

                        System.out.println("Pressed == left");

                        break;

                    case UP :
                        if(direction==1)
                        {
                            fireShow.setX(playerLShow.getX()-50);
                            if((isGun1==true||isGun2==true)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                            {
                                isGunSelected1=true;
                                player1_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected1==true)
                            {
                                gunShow1.setX(playerLShow.getX()+35);
                                gunShow1.setY(playerLShow.getY()-15);
                            }
                            if((playerLShow.getX()+100)>playerLShow2.getX()&&(playerLShow.getX()+100)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                                playerLShow.setY(playerLShow.getY()+15);

                            if(map.isFire((playerLShow.getX()+22),(playerLShow.getY()+130))&&playerLShow.getY()<860)
                            {
                                fireShow.setX(-122);

                            }
                        }
                        else if(direction==2)
                        {
                            fireShow.setX(playerLShow.getX()+70);
                            if((isGun1==true||isGun2==true)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                            {
                                isGunSelected1=true;
                                player1_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected1==true)
                            {
                                gunShow1.setX(playerLShow.getX()+35);
                                gunShow1.setY(playerLShow.getY()-15);
                            }
                            if((playerLShow.getX()+70)>playerLShow2.getX()&&(playerLShow.getX()+70)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                                playerLShow.setY(playerLShow.getY()+15);
                            if(map.isFire((playerLShow.getX()+130),(playerLShow.getY()+138))&&playerLShow.getY()<860)
                            {
                                fireShow.setX(-130);

                            }
                        }
                        if(isSheild==true&&direction==1)
                            shield_Show1.setY(playerLShow.getY()+30-10);
                        else if(isSheild==true&&direction==2) shield_Show1.setY(playerLShow.getY()+30-10);
                        if(isHealth1==true&&(playerLShow.getX()+70)>1000&&(playerLShow.getX()+70)<1092&&(playerLShow.getY()+20)>800&&(playerLShow.getY()+20)<895)
                        {
                            isHealth1=false;
                            player1_pBar.setProgress(1);
                            healthShow1.setX(-300);
                        }
                        if(isHealth2==true&&(playerLShow.getX()+70)>400&&(playerLShow.getX()+70)<492&&(playerLShow.getY()+20)>400&&(playerLShow.getY()+20)<495)
                        {
                            isHealth2=false;
                            player1_pBar.setProgress(1);
                            healthShow2.setX(-300);
                        }
                        fireShow.setY(playerLShow.getY()+42);
                        playerLShow.setY(playerLShow.getY()-10);
                        if(map.isObstacle(playerLShow.getX()+50,playerLShow.getY()+75))
                            playerLShow.setY(playerLShow.getY()+15);

                        System.out.println("Pressed == up");
                        try {
                            out.writeUTF("up");
                            System.out.println("up");
                        } catch (IOException ex) {
                        }

                        break;
                    case DOWN:
                        if(direction==1)
                        {
                            fireShow.setX(playerLShow.getX()-50);
                            if((isGun1==true||isGun2)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                            {
                                isGunSelected1=true;
                                player1_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected1==true)
                            {
                                gunShow1.setX(playerLShow.getX()+35);
                                gunShow1.setY(playerLShow.getY());
                            }
                            if((playerLShow.getX()+100)>playerLShow2.getX()&&(playerLShow.getX()+100)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                                playerLShow.setY(playerLShow.getY()-15);
                            if(map.isFire((playerLShow.getX()+22),(playerLShow.getY()+130))&&playerLShow.getY()<860)
                            {
                                fireShow.setX(-122);
                            }
                        }
                        else if(direction==2)
                        {
                            fireShow.setX(playerLShow.getX()+70);
                            if((isGun1==true||isGun2)&&(playerLShow.getX()+100)>800&&(playerLShow.getX()+100)<900&&(playerLShow.getY()+20)>530&&(playerLShow.getY()+20)<580)
                            {
                                isGunSelected1=true;
                                player1_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected1==true)
                            {
                                gunShow1.setX(playerLShow.getX()+35);
                                gunShow1.setY(playerLShow.getY());
                            }
                            if((playerLShow.getX()+70)>playerLShow2.getX()&&(playerLShow.getX()+70)<(playerLShow2.getX()+100)&&playerLShow.getY()>playerLShow2.getY()&&playerLShow.getY()<(playerLShow2.getY()+140))
                                playerLShow.setY(playerLShow.getY()-15);
                            if(map.isFire((playerLShow.getX()+130),(playerLShow.getY()+138))&&playerLShow.getY()<860)
                            {
                                fireShow.setX(-130);
                            }
                        }
                        if(isSheild==true&&direction==1)
                            shield_Show1.setY(playerLShow.getY()+30+10);
                        else if(isSheild==true&&direction==2) shield_Show1.setY(playerLShow.getY()+30+10);
                        if(isHealth1==true&&(playerLShow.getX()+70)>1000&&(playerLShow.getX()+70)<1092&&(playerLShow.getY()+20)>800&&(playerLShow.getY()+20)<895)
                        {
                            isHealth1=false;
                            player1_pBar.setProgress(1);
                            healthShow1.setX(-300);
                        }
                        if(isHealth2==true&&(playerLShow.getX()+70)>400&&(playerLShow.getX()+70)<492&&(playerLShow.getY()+20)>400&&(playerLShow.getY()+20)<495)
                        {
                            isHealth2=false;
                            player1_pBar.setProgress(1);
                            healthShow2.setX(-300);
                        }
                        fireShow.setY(playerLShow.getY()+60);
                        playerLShow.setY(playerLShow.getY()+10);
                        if(map.isObstacle(playerLShow.getX()+50,playerLShow.getY()+75))
                            playerLShow.setY(playerLShow.getY()-15);
                        System.out.println("Pressed == down");
                        try {
                            out.writeUTF("down");
                            System.out.println("down");
                        } catch (IOException ex) {
                        }
                        break;

                    case D:
                        try {
                            out.writeUTF("shoot");
                            System.out.println("shoot");
                        } catch (IOException ex) {
                        }



                        AudioClip sound = new AudioClip("file:Shoot_Bullet.mp3");
                        sound.setVolume(1);
                        sound.setCycleCount(1);
                        sound.play();


                        c_ammo=c_ammo-.01;
                        player1_ammo_pBar.setProgress(c_ammo);
                        if(c_ammo<.3)
                        {
                            scene.getStylesheets().add("Progress.css");
                        }

                        if(c_ammo<0)
                        {
                            bulletShow.setX(2100);
                        }

                        if(player2_change_health2>0.3&&player2_change_health2<.5)
                        {
                            isHealth1=true;
                            healthShow1.setX(1000);
                            healthShow1.setY(800);
                        }

                        if(c_ammo<.5&&c_ammo>.3)
                        {
                            isGun1=true;
                            gunShow1.setX(800);
                            gunShow1.setY(480);
                        }
                        if(isGunSelected1==true&&direction==1)
                        {
                            gunShow1.setX(playerLShow.getX()+35);
                            gunShow1.setY(playerLShow.getY());
                        }
                        else if(isGunSelected1==true&&direction==2)
                        {
                            gunShow1.setX(playerLShow.getX()+30);
                            gunShow1.setY(playerLShow.getY());
                        }


                        bulletShow.setY(playerLShow.getY() + 10);
                        initBulletPosY[0] = (int) bulletShow.getY();
                        // AnimationTimer animationTimer;
                        if (direction == 1) {
                            final long startNanoTime1 = System.nanoTime();
                            new AnimationTimer() {
                                @Override
                                public void handle(long now) {

                                    double t = (now - startNanoTime1) / 1000000000.0;
                                    //   System.out.println(t);
                                    bulletShow.setScaleX(-1);

                                    bulletShow.setX(playerLShow.getX() + 100);
                                    initBulletPosX[0] = (int) bulletShow.getX();
                                    bulletShow.setX(initBulletPosX[0] + t * 1350);
                                    bulletShow.setY(initBulletPosY[0]);
                                    if (bulletShow.getX() > initBulletPosX[0] + 600) {
                                        bulletShow.setX(2100);
                                        this.stop();
                                    }

                                    boolean wlFlag=false;

                                    if(c_ammo>0&&bulletShow.getX()>playerLShow2.getX()&&bulletShow.getY()>playerLShow2.getY()&&bulletShow.getY()<(playerLShow2.getY()+135))
                                    {
                                        bulletShow.setX(2100);
                                      if(isSheild2==true)
                                      {
                                          shield_change2=shield_change2-.002;
                                          shield2_pBar.setProgress(shield_change2);
                                          player2_change_health2=player2_change_health2-.0001;
                                          if(shield_change2<0) {
                                              shield_Show2.setX(-300);
                                              isSheild2=false;
                                          }
                                      }
                                      else if(isSheild2==false)
                                          player2_change_health2=player2_change_health2-.001;

                                      if(isGunSelected2==true)
                                          player2_change_health2=player2_change_health2-.0003;

                                        player2_pBar.setProgress(player2_change_health2);
                                        if(player2_change_health2<0) {
                                            winShow.setX(0);
                                            winShow.setY(0);

                                            wlFlag=true;
                                        }
                                    }

                                    if(wlFlag){
                                        try {
                                            out.writeUTF("loss");
                                            System.out.println("loss");
                                        } catch (IOException ex) {
                                        }
                                    }

                                    if(map.isObstacle(bulletShow.getX(),bulletShow.getY()))
                                    {
                                        this.stop();
                                        bulletShow.setX(2100);
                                    }
                                }
                            }.start();


                        }

                        else if (direction == 2) {
                            final long startNanoTime1 = System.nanoTime();
                            new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    double t = (now - startNanoTime1) / 1000000000.0;
                                    bulletShow.setScaleX(1);
                                    bulletShow.setX(playerLShow.getX());
                                    initBulletPosX[0] = (int) bulletShow.getX();
                                    bulletShow.setX(initBulletPosX[0] - t * 1350);
                                    bulletShow.setY(initBulletPosY[0]);

                                    boolean wlFlag=false;

                                    if(c_ammo>0&&bulletShow.getX()<(playerLShow2.getX()+70)&&bulletShow.getY()>playerLShow2.getY()&&bulletShow.getY()<playerLShow2.getY()+135)
                                    {

                                        bulletShow.setX(2100);
                                        if(isSheild2==true)
                                        {
                                            shield_change2=shield_change2-.002;
                                            shield2_pBar.setProgress(shield_change2);
                                            player2_change_health2=player2_change_health2-.0001;
                                            if(shield_change2<0) {
                                                shield_Show2.setX(-300);
                                                isSheild2=false;
                                            }
                                        }
                                        else if(isSheild2==false)
                                            player2_change_health2=player2_change_health2-.001;

                                        if(isGunSelected2==true)
                                            player2_change_health2=player2_change_health2-.0003;

                                        player2_pBar.setProgress(player2_change_health2);
                                        if(player2_change_health2<0) {
                                            winShow.setX(0);
                                            winShow.setY(0);

                                            wlFlag=true;
                                        }
                                    }

                                    if(wlFlag){
                                        try {
                                            out.writeUTF("loss");
                                            System.out.println("loss");
                                        } catch (IOException ex) {
                                        }
                                    }


                                    if (bulletShow.getX() < initBulletPosX[0] - 600) {
                                        bulletShow.setX(2100);
                                        this.stop();
                                    }
                                    if(map.isObstacle(bulletShow.getX(),bulletShow.getY())) {
                                        bulletShow.setX(2100);
                                        this.stop();
                                    }



                                }
                            }.start();
                        }

                        break;

                    case S:

                        System.out.println("Pressed == shield");
                        try {
                            out.writeUTF("shield");
                            System.out.println("shield");
                        } catch (IOException ex) {
                        }

                        //c_ammo=1;
                        isSheild=true;
                        if(shield_change1>0) {
                            if (direction == 1) {
                                shield_Show1.setX(playerLShow.getX() + 30);
                                shield_Show1.setY(playerLShow.getY() + 30);
                            } else {
                                shield_Show1.setX(playerLShow.getX() + 123);
                                shield_Show1.setY(playerLShow.getY() + 30);
                            }
                        }
                        break;

                    case N:
                        System.out.println("Pressed == shield_removed");
                        try {
                            out.writeUTF("shield_removed");
                            System.out.println("shield_removed");
                        } catch (IOException ex) {
                        }

                        isSheild=false;
                        shield_Show1.setX(-300);

                        break;
                    case R:

                        System.out.println("Pressed == reload");
                        try {
                            out.writeUTF("reload");
                            System.out.println("reload");
                        } catch (IOException ex) {
                        }

                        c_ammo=1;

                        break;
                    case G:
                        System.out.println("Pressed == grenade");
                        try {
                            out.writeUTF("grenade");
                            System.out.println("grenade");
                        } catch (IOException ex) {
                        }

                        if(player2_change_health2>0.3&&player2_change_health2<.5)
                        {
                            isHealth1=true;
                            healthShow1.setX(1000);
                            healthShow1.setY(800);
                        }

                        grenadeShow1.setX(playerLShow.getX() + 100);
                        grenadeShow1.setY(playerLShow.getY());
                        initGrenadePosX[0] = (int) grenadeShow1.getX();
                        initGrenadePosY[0] = (int) grenadeShow1.getY();

                        if(direction==1)
                        {
                            final long startNanoTime1 = System.nanoTime();
                            new AnimationTimer() {
                                @Override
                                public void handle(long now) {

                                    double t = (now - startNanoTime1) / 1000000000.0;
                                    //   System.out.println(t);
                                    //bulletShow.setScaleX(-1);

                                    grenadeShow1.setX(playerLShow.getX() + 100);
                                    initGrenadePosX[0] = (int) grenadeShow1.getX();
                                    initGrenadePosY[0] = (int) grenadeShow1.getY();
                                    grenadeShow1.setX(initGrenadePosX[0] + t * 300);
                                    grenadeShow1.setY(initGrenadePosY[0] + t * 100);
                                    if (grenadeShow1.getX() > initGrenadePosX[0] + 150) {
                                        grenadeShow1.setX(2100);
                                        this.stop();
                                    }

                                    if(grenadeShow1.getX()>playerLShow2.getX()&&grenadeShow1.getY()>playerLShow2.getY()&&grenadeShow1.getY()<(playerLShow2.getY()+135))
                                    {
                                        player2_change_health2=player2_change_health2-.003;
                                        player2_pBar.setProgress(player2_change_health2);
                                        grenadeShow1.setX(2100);
                                        blastShow1.setX(playerLShow2.getX()-100);
                                        blastShow1.setY(playerLShow2.getY()-150);

                                        if(player2_change_health2<0) {
                                            winShow.setX(0);
                                            winShow.setY(0);

                                            //wlFlag=true;
                                        }
                                    }
                                    if(grenadeShow1.getY()>1000)
                                        blastShow1.setX(2100);


                                    if(map.isObstacle(grenadeShow1.getX(),grenadeShow1.getY()))
                                    {
                                        this.stop();
                                        grenadeShow1.setX(2100);
                                    }

                                }
                            }.start();

                        }

                        else if(direction==2)
                        {
                            final long startNanoTime1 = System.nanoTime();
                            new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    double t = (now - startNanoTime1) / 1000000000.0;
                                    //bulletShow.setScaleX(1);
                                    grenadeShow1.setX(playerLShow.getX());

                                    initGrenadePosX[0] = (int) grenadeShow1.getX();
                                    initGrenadePosY[0] = (int) grenadeShow1.getY();
                                    grenadeShow1.setX(initGrenadePosX[0] - t * 300);
                                    grenadeShow1.setY(initGrenadePosY[0] + t * 100);
                                    if (grenadeShow1.getX() > initGrenadePosX[0] - 150) {
                                        grenadeShow1.setX(2100);
                                        this.stop();
                                    }

                                    if(grenadeShow1.getX()<(playerLShow2.getX()+70)&&grenadeShow1.getY()>playerLShow2.getY()&&grenadeShow1.getY()<playerLShow2.getY()+135)
                                    {
                                        player2_change_health2=player2_change_health2-.003;
                                        player2_pBar.setProgress(player2_change_health2);
                                        grenadeShow1.setX(2100);
                                        blastShow1.setX(playerLShow2.getX()-100);
                                        blastShow1.setY(playerLShow2.getY()-150);

                                        if(player2_change_health2<0) {
                                            winShow.setX(0);
                                            winShow.setY(0);

                                            //wlFlag=true;
                                        }

                                    }

                                    if(grenadeShow1.getY()>1000)
                                        blastShow1.setX(2100);

                                    if(map.isObstacle(grenadeShow1.getX(),grenadeShow1.getY())) {
                                        grenadeShow1.setX(2100);
                                        this.stop();
                                    }


                                    if(player2_change_health2<0) {
                                        winShow.setX(0);
                                        winShow.setY(0);

                                       // wlFlag=true;
                                    }

                                }
                            }.start();
                        }
                        break;

                }
            }
        });



        //bgShow.setViewport(new Rectangle2D(0,0,2000,1000));
        root.getChildren().addAll(bgShow,bgShow2,playerLShow,playerLShow2);
        root.getChildren().addAll(fireShow,fireShow2,bulletShow,bulletShow2);
        root.getChildren().addAll(player1_pBar,player1_ammo_pBar,player2_pBar,player2_ammo_pBar);
        root.getChildren().addAll(shield_Show1,shield_Show2,healthShow1,healthShow2);
        root.getChildren().addAll(shield1_pBar,shield2_pBar);
        root.getChildren().addAll(gunShow1,gunShow2,grenadeShow1,grenadeShow2,blastShow1,blastShow2);
        root.getChildren().addAll(winShow,lossShow);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }




    //

    public class ReceiveMessage extends Thread {

        final DataInputStream in;

        ReceiveMessage(DataInputStream in) {
            this.in = in;
        }

        public void run() {


            String message;


            while (isReceiving) {


                try {
                    message = in.readUTF();
                    String[] mess = message.split(" ");

                    System.out.println("Data from Server : " +message);

                    switch (mess[0]) {

                        case "loss":

                            lossShow.setX(0);
                            lossShow.setY(0);
                            break;

                        case "up":
                            if(direction2==1)
                            {
                                fireShow2.setX(playerLShow2.getX()-50);
                                if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                                {
                                    isGunSelected2=true;
                                    player2_ammo_pBar.setProgress(1);
                                }
                                if(isGunSelected2==true)
                                {
                                    gunShow2.setX(playerLShow2.getX()+35);
                                    gunShow2.setY(playerLShow2.getY()-15);
                                }
                                if((playerLShow2.getX()+100)>playerLShow.getX()&&(playerLShow2.getX()+100)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                    playerLShow2.setY(playerLShow2.getY()-15);
                                if(map.isFire((playerLShow2.getX()+22),(playerLShow2.getY()+130))&&playerLShow2.getY()<860)
                                {
                                    fireShow2.setX(-122);
                                }
                            }
                            else if(direction2==2)
                            {
                                fireShow2.setX(playerLShow2.getX()+70);
                                if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                                {
                                    isGunSelected2=true;
                                    player2_ammo_pBar.setProgress(1);
                                }
                                if(isGunSelected2==true)
                                {
                                    gunShow2.setX(playerLShow2.getX()+130);
                                    gunShow2.setY(playerLShow2.getY()-15);
                                }
                                if((playerLShow2.getX()+70)>playerLShow.getX()&&(playerLShow2.getX()+70)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                    playerLShow2.setY(playerLShow2.getY()-15);
                                if(map.isFire((playerLShow2.getX()+130),(playerLShow2.getY()+138))&&playerLShow2.getY()<860)
                                {
                                    fireShow2.setX(-130);
                                }
                            }
                            if(isSheild2==true&&direction2==1)
                                shield_Show2.setY(playerLShow2.getY()+30-10);
                            else if(isSheild2==true&&direction2==2) shield_Show2.setY(playerLShow2.getY()+30-10);
                            if(isHealth1==true&&(playerLShow2.getX()+70)>1000&&(playerLShow2.getX()+70)<1092&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth1=false;
                                player2_pBar.setProgress(1);
                                healthShow1.setX(-300);
                            }
                            if(isHealth2==true&&(playerLShow2.getX()+70)>400&&(playerLShow2.getX()+70)<492&&(playerLShow2.getY()+20)>400&&(playerLShow2.getY()+20)<495)
                            {
                                isHealth2=false;
                                player2_pBar.setProgress(1);
                                healthShow2.setX(-300);
                            }
                            fireShow2.setY(playerLShow2.getY()+42);
                            playerLShow2.setY(playerLShow2.getY()-10);
                            if(map.isObstacle(playerLShow2.getX()+50,playerLShow2.getY()+75))
                                playerLShow2.setY(playerLShow2.getY()+15);
                            break;
                        case "down":
                            if(direction2==1)
                            {
                                fireShow2.setX(playerLShow2.getX()-50);
                                if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                                {
                                    isGunSelected2=true;
                                    player2_ammo_pBar.setProgress(1);
                                }
                                if(isGunSelected2==true)
                                {
                                    gunShow2.setX(playerLShow2.getX()+35);
                                    gunShow2.setY(playerLShow2.getY());
                                }
                                if((playerLShow2.getX()+100)>playerLShow.getX()&&(playerLShow2.getX()+100)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                    playerLShow2.setY(playerLShow2.getY()-15);
                                if(map.isFire((playerLShow2.getX()+22),(playerLShow2.getY()+130))&&playerLShow2.getY()<860)
                                {
                                    fireShow2.setX(-122);
                                }
                            }
                            else if(direction2==2)
                            {
                                fireShow2.setX(playerLShow2.getX()+70);
                                if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                                {
                                    isGunSelected2=true;
                                    player2_ammo_pBar.setProgress(1);
                                }
                                if(isGunSelected2==true)
                                {
                                    gunShow2.setX(playerLShow2.getX()+130);
                                    gunShow2.setY(playerLShow2.getY());
                                }
                                if((playerLShow2.getX()+70)>playerLShow.getX()&&(playerLShow2.getX()+70)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                    playerLShow2.setY(playerLShow2.getY()-15);
                                if(map.isFire((playerLShow2.getX()+130),(playerLShow2.getY()+138))&&playerLShow2.getY()<860)
                                {
                                    fireShow2.setX(-130);
                                }
                            }
                            if(isSheild2==true&&direction2==1)
                                shield_Show2.setY(playerLShow2.getY()+30+10);
                            else if(isSheild2==true&&direction2==2) shield_Show2.setY(playerLShow2.getY()+30+10);
                            if(isHealth1==true&&(playerLShow2.getX()+70)>1000&&(playerLShow2.getX()+70)<1092&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth1=false;
                                player2_pBar.setProgress(1);
                                healthShow1.setX(-300);
                            }
                            if(isHealth2==true&&(playerLShow2.getX()+70)>1000&&(playerLShow2.getX()+70)<892&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth2=false;
                                player2_pBar.setProgress(1);
                                healthShow2.setX(-300);
                            }
                            fireShow2.setY(playerLShow2.getY()+60);
                            playerLShow2.setY(playerLShow2.getY()+10);
                            if(map.isObstacle(playerLShow2.getX()+50,playerLShow2.getY()+75))
                                playerLShow2.setY(playerLShow2.getY()-15);
                            break;
                        case "left":


                            direction2=2;
                            if(isSheild2==true&&direction2==1)
                                shield_Show2.setX(playerLShow2.getX()+30-10);
                            else if(isSheild2==true&&direction2==2) shield_Show2.setX(playerLShow2.getX()+65-10);
                            if(isHealth1==true&&(playerLShow2.getX()+70)>1000&&(playerLShow2.getX()+70)<1092&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth1=false;
                                player2_pBar.setProgress(1);
                                healthShow1.setX(-300);
                            }
                            if(isHealth2==true&&(playerLShow2.getX()+70)>1000&&(playerLShow2.getX()+70)<892&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth2=false;
                                player2_pBar.setProgress(1);
                                healthShow2.setX(-300);
                            }
                            if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                            {
                                isGunSelected2=true;
                                player2_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected2==true)
                            {
                                gunShow2.setX(playerLShow2.getX()+30);
                                gunShow2.setY(playerLShow2.getY());
                            }
                            if((playerLShow2.getX()+70)>playerLShow.getX()&&(playerLShow2.getX()+70)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                playerLShow2.setX(playerLShow2.getX()+15);
                          //  fireShow2.setX(-135);
                            if(map.isFire((playerLShow2.getX()+130),(playerLShow2.getY()+138))&&playerLShow2.getY()<860)
                            {
                                fireShow2.setX(-130);

                            }
                            playerLShow2.setScaleX(1);
                            fireShow2.setX(playerLShow2.getX()+70);
                            if(isGunSelected2==true) gunShow2.setScaleX(1);
                           // shield_Show2.setScaleX(1);
                            playerLShow2.setX(playerLShow2.getX()-10);
                            if(map.isObstacle(playerLShow2.getX()+50,playerLShow2.getY()+75))
                                playerLShow2.setX(playerLShow2.getX()+15);
                            break;
                        case "right":


                            direction2=1;
                            if(isSheild2==true&&direction2==1)
                                shield_Show2.setX(playerLShow2.getX()+30+10);
                            else if(isSheild2==true&&direction2==2) shield_Show2.setX(playerLShow2.getX()+123+10);
                            if(isHealth1==true&&(playerLShow2.getX()+100)>1000&&(playerLShow2.getX()+100)<1092&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth1=false;
                                player2_pBar.setProgress(1);
                                healthShow1.setX(-300);
                            }
                            if(isHealth2==true&&(playerLShow2.getX()+100)>1000&&(playerLShow2.getX()+100)<1092&&(playerLShow2.getY()+20)>800&&(playerLShow2.getY()+20)<895)
                            {
                                isHealth2=false;
                                player2_pBar.setProgress(1);
                                healthShow2.setX(-300);
                            }
                            if((isGun1==true||isGun2==true)&&(playerLShow2.getX()+100)>800&&(playerLShow2.getX()+100)<900&&(playerLShow2.getY()+20)>530&&(playerLShow2.getY()+20)<580)
                            {
                                isGunSelected2=true;
                                player2_ammo_pBar.setProgress(1);
                            }
                            if(isGunSelected2==true)
                            {
                                gunShow2.setX(playerLShow2.getX()+35);
                                gunShow2.setY(playerLShow2.getY());
                            }
                            if((playerLShow2.getX()+100)>playerLShow.getX()&&(playerLShow2.getX()+100)<(playerLShow.getX()+100)&&playerLShow2.getY()>playerLShow.getY()&&playerLShow2.getY()<(playerLShow.getY()+140))
                                playerLShow2.setX(playerLShow2.getX()-15);
                           // fireShow2.setX(-135);
                            if(map.isFire((playerLShow2.getX()+22),(playerLShow2.getY()+130))&&playerLShow2.getY()<860)
                            {
                                fireShow2.setX(-122);
                            }
                            playerLShow2.setScaleX(-1);
                            fireShow2.setX(playerLShow2.getX()-50);
                            if(isGunSelected2==true) gunShow2.setScaleX(-1);
                          //  shield_Show2.setScaleX(-1);
                            playerLShow2.setX(playerLShow2.getX()+10);
                            if(map.isObstacle(playerLShow2.getX()+50,playerLShow2.getY()+75))
                                playerLShow2.setX(playerLShow2.getX()-15);
                            break;


                        case "shoot":

                            AudioClip sound = new AudioClip("file:Shoot_Bullet.mp3");
                            sound.setVolume(1);
                            sound.setCycleCount(1);
                            sound.play();

                            c_ammo2=c_ammo2-.01;
                            player2_ammo_pBar.setProgress(c_ammo2);
                            //if(c_ammo<.3) scene.getStylesheets().add("Progress.css");

                            if(c_ammo2<0)
                            {
                                bulletShow2.setX(2100);
                            }
                            if(player_change_health>0.3&&player_change_health<.5)
                            {
                                isHealth2=true;
                                healthShow2.setX(800);
                                healthShow2.setY(800);
                            }

                            if(c_ammo2<.5&&c_ammo2>.3)
                            {
                                isGun2=true;
                                gunShow2.setX(800);
                                gunShow2.setY(480);
                            }
                            if(isGunSelected2==true&&direction2==1)
                            {
                                gunShow2.setX(playerLShow2.getX()+35);
                                gunShow2.setY(playerLShow2.getY());
                            }
                            else if(isGunSelected2==true&&direction2==2)
                            {
                                gunShow2.setX(playerLShow2.getX()+30);
                                gunShow2.setY(playerLShow2.getY());
                            }
                            bulletShow2.setY(playerLShow2.getY() + 10);
                            initBulletPosY2[0] = (int) bulletShow2.getY();
                            // AnimationTimer animationTimer;
                            if (direction2 == 1) {
                                final long startNanoTime1 = System.nanoTime();
                                new AnimationTimer() {
                                    @Override
                                    public void handle(long now) {

                                        double t = (now - startNanoTime1) / 1000000000.0;
                                        //  System.out.println(t);
                                        bulletShow2.setScaleX(-1);

                                        bulletShow2.setX(playerLShow2.getX() + 100);
                                        initBulletPosX2[0] = (int) bulletShow2.getX();
                                        bulletShow2.setX(initBulletPosX2[0] + t * 1350);
                                        bulletShow2.setY(initBulletPosY2[0]);
                                        if(c_ammo2>0&&bulletShow2.getX()>playerLShow.getX()&&bulletShow2.getY()>playerLShow.getY()&&bulletShow2.getY()<(playerLShow.getY()+135))
                                        {
                                            bulletShow2.setX(2100);

                                            if(isSheild==true)
                                            {
                                                shield_change1=shield_change1-.002;
                                                shield1_pBar.setProgress(shield_change1);
                                                player_change_health=player_change_health-.0001;
                                                if(shield_change1<0) {
                                                    shield_Show1.setX(-300);
                                                    isSheild=false;
                                                }
                                            }
                                            else if(isSheild==false)
                                                player_change_health=player_change_health-.001;

                                            if(isGunSelected1==true)
                                                player_change_health=player_change_health-.0003;

                                            player1_pBar.setProgress(player_change_health);
                                            if(player2_change_health2<0) {
                                                lossShow.setX(0);
                                                lossShow.setY(0);
                                            }
                                        }
                                        if (bulletShow2.getX() > initBulletPosX2[0] + 600) {
                                            bulletShow2.setX(2100);
                                            this.stop();
                                        }
                                        if(map.isObstacle(bulletShow2.getX(),bulletShow2.getY()))
                                        {
                                            bulletShow2.setX(2100);
                                            this.stop();
                                        }
                                    }
                                }.start();
                            }

                            else if (direction2 == 2) {
                                final long startNanoTime1 = System.nanoTime();
                                new AnimationTimer() {
                                    @Override
                                    public void handle(long now) {
                                        double t = (now - startNanoTime1) / 1000000000.0;
                                        bulletShow2.setScaleX(1);
                                        bulletShow2.setX(playerLShow2.getX());
                                        initBulletPosX2[0] = (int) bulletShow2.getX();
                                        bulletShow2.setX(initBulletPosX2[0] - t * 1350);
                                        bulletShow2.setY(initBulletPosY2[0]);
                                        if(c_ammo2>0&&bulletShow2.getX()<(playerLShow.getX()+70)&&bulletShow2.getY()>playerLShow.getY()&&bulletShow2.getY()<playerLShow.getY()+135)
                                        {
                                            bulletShow2.setX(2100);
                                            if(isSheild==true)
                                            {
                                                shield_change1=shield_change1-.002;
                                                shield1_pBar.setProgress(shield_change1);
                                                player_change_health=player_change_health-.0001;
                                                if(shield_change1<0) {
                                                    shield_Show1.setX(-300);
                                                    isSheild=false;
                                                }
                                            }
                                            else if(isSheild==false)
                                                player_change_health=player_change_health-.001;

                                            if(isGunSelected1==true)
                                                player_change_health=player_change_health-.0003;

                                            player1_pBar.setProgress(player_change_health);
                                            if(player2_change_health2<0) {
                                                lossShow.setX(0);
                                                lossShow.setY(0);
                                            }
                                        }
                                        if (bulletShow2.getX() < initBulletPosX2[0] - 600) {
                                            bulletShow2.setX(2100);
                                            this.stop();
                                        }
                                        if (map.isObstacle(bulletShow2.getX(), bulletShow2.getY()))
                                        {
                                            bulletShow2.setX(2100);
                                            this.stop();
                                        }
                                    }
                                }.start();
                            }
                            break;

                        case "shield":

                            //c_ammo2=1;
                            isSheild2=true;
                            if(shield_change2>0) {
                                if (direction2 == 1) {
                                    shield_Show2.setX(playerLShow2.getX() + 30);
                                    shield_Show2.setY(playerLShow2.getY() + 30);
                                } else {
                                    shield_Show2.setX(playerLShow2.getX() + 123);
                                    shield_Show2.setY(playerLShow2.getY() + 30);
                                }
                            }
                            break;

                        case "shield_removed":
                            isSheild2=false;
                            shield_Show2.setX(-300);
                            break;

                        case "reload":

                            c_ammo2=1;
                            break;

                        case "grenade":
                            grenadeShow2.setX(playerLShow2.getX() + 100);
                            grenadeShow2.setY(playerLShow2.getY());
                            initGrenadePosX2[0] = (int) grenadeShow2.getX();
                            initGrenadePosY2[0] = (int) grenadeShow2.getY();

                            if(player_change_health>0.3&&player_change_health<.5)
                            {
                                isHealth2=true;
                                healthShow2.setX(800);
                                healthShow2.setY(800);
                            }

                            if(direction==1)
                            {
                                final long startNanoTime1 = System.nanoTime();
                                new AnimationTimer() {
                                    @Override
                                    public void handle(long now) {

                                        double t = (now - startNanoTime1) / 1000000000.0;
                                        //   System.out.println(t);
                                        //bulletShow.setScaleX(-1);

                                        grenadeShow2.setX(playerLShow2.getX() + 100);
                                        initGrenadePosX2[0] = (int) grenadeShow2.getX();
                                        initGrenadePosY2[0] = (int) grenadeShow2.getY();
                                        grenadeShow2.setX(initGrenadePosX2[0] + t * 300);
                                        grenadeShow2.setY(initGrenadePosY2[0] + t * 100);
                                        if (grenadeShow2.getX() > initGrenadePosX2[0] + 150) {
                                            grenadeShow2.setX(2100);
                                            this.stop();
                                        }

                                        if(grenadeShow2.getX()>playerLShow.getX()&&grenadeShow2.getY()>playerLShow.getY()&&grenadeShow2.getY()<(playerLShow.getY()+135))
                                        {
                                            player_change_health=player_change_health-.003;
                                            player1_pBar.setProgress(player_change_health);
                                            grenadeShow2.setX(2100);
                                            blastShow2.setX(playerLShow.getX()-100);
                                            blastShow2.setY(playerLShow.getY()-150);

                                            if(player_change_health<0) {
                                                lossShow.setX(0);
                                                lossShow.setY(0);
                                            }
                                        }
                                        if(grenadeShow2.getY()>1000)
                                            blastShow2.setX(2100);


                                        if(map.isObstacle(grenadeShow2.getX(),grenadeShow2.getY()))
                                        {
                                            this.stop();
                                            grenadeShow2.setX(2100);
                                        }
                                    }
                                }.start();

                            }

                            else if(direction==2)
                            {
                                final long startNanoTime1 = System.nanoTime();
                                new AnimationTimer() {
                                    @Override
                                    public void handle(long now) {
                                        double t = (now - startNanoTime1) / 1000000000.0;
                                        //bulletShow.setScaleX(1);
                                        grenadeShow2.setX(playerLShow2.getX());

                                        initGrenadePosX2[0] = (int) grenadeShow2.getX();
                                        initGrenadePosY2[0] = (int) grenadeShow2.getY();
                                        grenadeShow2.setX(initGrenadePosX2[0] - t * 300);
                                        grenadeShow2.setY(initGrenadePosY2[0] + t * 100);
                                        if (grenadeShow2.getX() > initGrenadePosX2[0] - 150) {
                                            grenadeShow2.setX(2100);
                                            this.stop();
                                        }

                                        if(grenadeShow2.getX()<(playerLShow.getX()+70)&&grenadeShow2.getY()>playerLShow.getY()&&grenadeShow2.getY()<playerLShow.getY()+135)
                                        {
                                            player_change_health=player_change_health-.003;
                                            player1_pBar.setProgress(player_change_health);
                                            grenadeShow2.setX(2100);
                                            blastShow2.setX(playerLShow.getX()-100);
                                            blastShow2.setY(playerLShow.getY()-150);

                                            if(player_change_health<0) {
                                                lossShow.setX(0);
                                                lossShow.setY(0);
                                            }

                                        }

                                        if(grenadeShow2.getY()>1000)
                                            blastShow2.setX(2100);

                                        if(map.isObstacle(grenadeShow2.getX(),grenadeShow2.getY())) {
                                            grenadeShow2.setX(2100);
                                            this.stop();
                                        }



                                    }
                                }.start();
                            }
                            break;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
