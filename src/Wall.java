
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

class Wall extends Rectangle
{
    double locationX;
    public boolean isUpperWall;
    public Group wallGroup = new Group();
    public Wall(boolean upperWall, double xLocation)
    {
        isUpperWall = upperWall;
        if(upperWall)
        {
            setHeight(300);
            setWidth(100);
            setX(xLocation);
            setY(-100);
        }
        if(!upperWall)
        {
            setHeight(600);
            setWidth(100);
            setX(xLocation);
            setY(400);
        }
    }
    
    
    public void updatePosition(int randomSeed, double lastWallX)//seed needs to be a value from 1-3
    {
        
        switch(randomSeed){
            case 1:     if(isUpperWall)
                        {
                            setY(-200);
                            setX(lastWallX);
                        }
                        else if(!isUpperWall)
                        {
                            setY(300);
                            setX(lastWallX);
                        }
                        break;
            
            case 2:     if(isUpperWall)
                        {
                            setY(-100);
                            setX(lastWallX);
                        }
                        else if(!isUpperWall)
                        {
                            setY(400);
                            setX(lastWallX);
                        }
                        break;
            
            case 3:     if(isUpperWall)
                        {
                            setY(0);
                            setX(lastWallX);
                        }
                        else if(!isUpperWall)
                        {
                            setY(500);
                            setX(lastWallX);
                        }
                        break;
            
        }
    }
    
    public void move()
    {
        locationX = getX() - 5;
        setX(locationX);
        
    }
    
}
