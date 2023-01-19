package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    static boolean answer ;

    public static boolean display(String title, String messege) {


        Stage window =new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);


        Label label = new Label();
        label.setText(messege);

        label.setStyle(" -fx-text-fill: #010101;\n" +
                "    -fx-font-weight: bold;");


        Button yesButton = new Button("YES");

        yesButton.setOnAction(e->{
            answer=true;
            window.close();
        });
        Button noButton = new Button("NO");
        noButton.setOnAction(e->{
            answer=false ;
            window.close();
        });

        yesButton.setStyle(" -fx-background-color: linear-gradient(#6E0C0C, #E5401D, #6E0C0C);\n" +
                "    -fx-text-fill: #FFFF;\n" +
                "    -fx-background-radius: 4;");

        noButton.setStyle(" -fx-background-color: linear-gradient(#6E0C0C, #E5401D, #6E0C0C);\n" +
                "    -fx-text-fill: #FFFF;\n" +
                "    -fx-background-radius: 4;");

        VBox layout = new VBox(10);

        layout.setStyle("-fx-background-color: linear-gradient(#FF900E,#FFF80E,#FF900E);");

        layout.getChildren().addAll(label,yesButton,noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        //scene.getStylesheets().add("sample/Volcano.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }


}

