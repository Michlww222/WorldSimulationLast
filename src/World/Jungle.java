package World;

import java.util.Random;

public class Jungle {
    Random r = new Random();
    private final  int width;
    private final  int height;
    public RectangularMap map;

    public Vector2d lowerPosition; // jungle bottom left corner
    public Vector2d higherPosition; // jungle top right corner

    //creator
    public Jungle(RectangularMap map,int ratio){
        this.map = map;
        this.width = map.width / ratio;
        this.height = map.height / ratio;
        Vector2d averageVector = new Vector2d(this.width/2,this.height/2);
        lowerPosition = map.getMiddle().subtract(averageVector);
        higherPosition = map.getMiddle().add(averageVector);
    }
    //check if this position is in jungle
    public boolean isInJungle(Vector2d position){
        if(position.follows(lowerPosition) && position.precedes(higherPosition)){
            return true;
        }
        return false;
    }
    //check if jungle is full
    public boolean isFullJungle(){
        for(int i = lowerPosition.getX();i < higherPosition.getX()+1; ++i){
            for(int j = lowerPosition.getY();j < higherPosition.getY()+1; ++j){
                if(!map.isOccupied(new Vector2d(i,j))){
                    return false;
                }
            }
        }
        return true;
    }
    //get random free position from jungle
    public Vector2d jungleRandomPosition(){
        if (isFullJungle()){
            return null;
        }
        int x = r.nextInt(width+1);
        int y = r.nextInt(height+1);
        Vector2d vector = new Vector2d(x+lowerPosition.getX(),y+ lowerPosition.getY());
        if(!map.isOccupied(vector)){
            return vector;
        }
        while(map.isOccupied(vector)){
            x = r.nextInt(width+1);
            y = r.nextInt(height+1);
            vector = new Vector2d(x+lowerPosition.getX(),y+ lowerPosition.getY());
        }
        return vector;

    }
    //get random position outside jungle
    public Vector2d stepRandomPosition(){
        if (isFullStep()){
            return null;
        }
        int x = r.nextInt(map.width+1);
        int y = r.nextInt(map.height+1);
        Vector2d vector = new Vector2d(x,y);
        while(map.isOccupied(vector) || isInJungle(vector)){
            x = r.nextInt(map.width);
            y = r.nextInt(map.height);
            vector = new Vector2d(x,y);
        }
        return vector;

    }
    //check if there is any free position outside the jungle
    public Boolean isFullStep(){
        for(int i = 0; i < map.getWidth(); ++i){
            for(int j = 0;j < map.getHeight(); ++j){
                Vector2d vector = new Vector2d(i,j);
                if(!map.isOccupied(vector) && !isInJungle(vector) ){
                    return false;
                }
            }
        }
        return true;
    }
}
