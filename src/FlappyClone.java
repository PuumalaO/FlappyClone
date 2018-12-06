
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.text.*;


public class FlappyClone extends Application {

    
    double playerX = 400;
    double playerY = 400;
    double fallSpeed = 6;
    double lastWallX = 450;
    boolean collisionDetected = false;
    boolean gameRunning = false;
    boolean gameFailed = false;
    boolean spaceReleased = false;
    int randomSeed = 1;
    
    public void reloadGame(ArrayList<Wall> walls, Circle player, Text text)
    {
        double lastWallX = 650;
        for(Wall wall : walls)
        {
            if(wall.isUpperWall)
            {
                wall.setX(lastWallX);
            }
            
            if(!wall.isUpperWall)
            {
                wall.setX(lastWallX);
                lastWallX += 350;
            }
        }
        
        player.setCenterX(300);
        player.setCenterY(340);
        playerY = 340;
        playerX = 300;
        
        
        text.setText("Hold SPACE to fly upwards");
        text.setVisible(true);
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        Group root = new Group();
        Random rand = new Random();
        ArrayList<Wall> walls = new ArrayList();
        
        Text text = new Text();
        text.setFont(new Font(40));
        text.setWrappingWidth(400);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("Hold SPACE to fly upwards");
        text.setX(100);
        text.setY(100);
        
        Scene scene = new Scene(root,600,600);
        
        Circle player = new Circle(300,340,48);
        Wall up = new Wall(true, 650);
        walls.add(up);
        
        Wall down = new Wall(false, 650);
        walls.add(down);
        
        Wall up2 = new Wall(true, 950);
        walls.add(up2);
        
        Wall down2 = new Wall(false, 950);
        walls.add(down2);
        
        Wall up3 = new Wall(true, 1250);
        walls.add(up3);
        
        Wall down3 = new Wall(false, 1250);
        walls.add(down3);
        
        root.getChildren().addAll(player, down, up, down2, up2, down3, up3, text);
        root.getChildren().add(up.wallGroup);
        
        player.setFill(Color.GREEN);
        
        
        
        AnimationTimer animator = new AnimationTimer(){

            
       @Override
       public void handle(long arg0) 
       {
           randomSeed = rand.nextInt(3)+1;
           playerY += fallSpeed;
           
           player.setCenterY(playerY);
           collisionDetected = false;
           for(Wall wall : walls)
           {
               wall.setFill(Color.BROWN);
                //If wall piece is out of screen update its position to the other side of screen
                if(wall.getX() == -100 && gameRunning)
                {
                    if(wall.isUpperWall == true)
                    {
                        wall.updatePosition(randomSeed, lastWallX + 350);
                    }
                    
                    if(wall.isUpperWall == false)
                    {
                        wall.updatePosition(randomSeed, lastWallX);
                    }
                }
                
                lastWallX = wall.getX();
                
                wall.move();
                
                if (player.getBoundsInParent().intersects(wall.getBoundsInParent()))
                {
                    collisionDetected = true;
                }
                
                if (collisionDetected)
                {
                    this.stop();
                    gameFailed = true;
                }
                
                if(gameFailed)
                {
                    text.setText("Game failed\n press SPACE to try again");
                    text.setX(100);
                    text.setY(100);
                    text.setVisible(true);
                }
           }
       }
      };             

        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                
                if (event.getCode()==KeyCode.SPACE && !gameFailed) //Start the game
                { 
                    gameRunning = true;
                    text.setVisible(false);
                    animator.start();
                } 
                
                if (event.getCode()==KeyCode.SPACE && gameRunning)  //Move ball upwards when space is pressed and game is running
                { 
                    fallSpeed = -5;
                }
                
                if(gameFailed && gameRunning && spaceReleased && event.getCode()==KeyCode.SPACE)    //If player failed the game and pressed space after releasing the key first, reload the game
                {
                    reloadGame(walls, player, text);
                    gameFailed =! gameFailed;
                } 
                
                spaceReleased = false;  //Save that space is being pressed
                }  
        });
        
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                spaceReleased = true;   //Save that space was released
                
                if (event.getCode()==KeyCode.SPACE) //Move the ball downwards
                { 
                    fallSpeed = 6;
                }   
                }  
        });
              primaryStage.setScene(scene);
              primaryStage.show();
    }
        
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
