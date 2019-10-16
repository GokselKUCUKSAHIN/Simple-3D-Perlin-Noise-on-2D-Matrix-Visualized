import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application
{

    ArrayList<Rectangle> rectangles = new ArrayList<>();
    boolean blackWhite = false;
    double pX = 0;
    double pY = 0;
    double pZ = 0;

    double scr = 500;
    double size = 5;
    double res = 0.08;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane root = new Pane();

        for (int i = 0; i < scr / size; i++)
        {
            for (int j = 0; j < scr / size; j++)
            {
                Rectangle rect = new Rectangle(j * size, i * size, size, size);
                rectangles.add(rect);
            }
        }

        Timeline tm = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            int index = 0;
            for (int i = 0; i < scr / size; i++)
            {
                pX = 0;
                for (int j = 0; j < scr / size; j++)
                {
                    if (blackWhite)
                    {
                        double noise = PerlinNoise.noise(pX, pY, pZ, 0, 1);
                        rectangles.get(index).setFill(Color.color(noise, noise, noise));
                    } else
                    {
                        double noise = PerlinNoise.noise(pX, pY, pZ, 0, 1250);
                        rectangles.get(index).setFill(Color.hsb(noise, 1, 1));
                    }
                    pX += res;
                    index++;
                }
                pY += res;
            }
            pY = 0;
            pZ += res;
        }));
        tm.setAutoReverse(false);
        tm.setRate(3);
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();

        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //Play
                    tm.play();
                    break;
                }
                case F2:
                {
                    //Pause
                    tm.pause();
                    break;
                }
                case F3:
                {
                    //Toggle Color/BW
                    this.blackWhite = !blackWhite;
                    break;
                }
                default:
                {
                    break;
                }
            }
        });


        root.getChildren().addAll(rectangles);
        primaryStage.setTitle("Perlin Noise");
        primaryStage.setScene(new Scene(root, 490, 490));
        primaryStage.setResizable(false);
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
