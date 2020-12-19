package World;

import java.util.*;

public class RectangularMap extends AbstractWorldMap {
    Random r = new Random();
    public int width;
    public int height;
    public Jungle jungle;
    public Map<Vector2d,ArrayList<Animal>> animals = new HashMap<>();
    public Map<Vector2d,ArrayList<Grass>> grasses = new HashMap<>();
    //creator
    public RectangularMap(int width,int height,int ratio) {
        this.width = width;
        this.height = height;
        this.jungle = new Jungle(this,ratio);
    }
    // check if animal can go to this position
    public boolean canMoveTo(Vector2d position) {
       return true;
    }

    public boolean isOccupied(Vector2d position) {
        if(isOccupiedByAnimal(position)){
            return true;
        }
        if(isOccupiedByGrass(position)){
            return true;
        }
        return false;
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        if(animals.get(position) == null || animals.get(position).size() == 0){
            return false;
        }
        return true;
    }

    public boolean isOccupiedByGrass(Vector2d position) {
        if(grasses.get(position) == null || grasses.get(position).size() == 0){
            return false;
        }
        return true;

    }
    // get object at position if there is any object
    public Object objectAt(Vector2d position) {
        return super.objectAt(position);
    }
    //get random free position on map
    public Vector2d randomFreePosition(){
        boolean i = false;
        Vector2d freePosition = new Vector2d(0,0);
        while(i == false){
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            freePosition = new Vector2d(x,y);
            if(!isOccupied(freePosition)){
                i = true;
            }
        }
        return freePosition;
    }
    //get Vector2d closes to middle of map
    public Vector2d getMiddle(){
        Vector2d vector = new Vector2d(width/2,height/2);
        return vector;
    }
    // get map Width
    public int getWidth(){
        return width;
    }
    //get map height
    public int getHeight(){
        return height;
    }
    //get position for newborn animal
    public Vector2d freePositionForNewborn(Vector2d parentsPosition){
        Vector2d position = parentsPosition;
        int x = parentsPosition.getX();
        int y = parentsPosition.getY();
        ArrayList<Integer> possiblePositions = new ArrayList<Integer>();
        for(int i=0;i<8;i++){
            possiblePositions.add(i);
        }
        while(possiblePositions.size() != 0){
            Enum direction = Move.MoveDirection.N;
            int a = r.nextInt(possiblePositions.size());
            direction = Move.turn((Move.MoveDirection) direction,possiblePositions.get(a));
            possiblePositions.remove(a);
             Vector2d vector = parentsPosition.add(Move.toUnitVector(direction));
            int x1 = vector.getX();
            int y2 = vector.getY();
            if(x1 >= getWidth()){
                x1 %= getWidth();
            } else if(x1 < 0){
                x1 = getWidth() - 1;
            }
            if(y2 >= getHeight()){
                y2 %= getHeight();
            } else if(y2 < 0){
                y2 = getHeight() - 1;
            }
            if(!isOccupied(vector)){
                return vector;
            }
        }
        Enum direction = Move.MoveDirection.N;
        int a = r.nextInt(8);
        direction = Move.turn((Move.MoveDirection) direction,a);
        Vector2d vector = parentsPosition.add(Move.toUnitVector(direction));
        int x1 = vector.getX();
        int y2 = vector.getY();
        if(x1 >= getWidth()){
            x1 %= getWidth();
        } else if(x1 < 0){
            x1 = getWidth() - 1;
        }
        if(y2 >= getHeight()){
            y2 %= getHeight();
        } else if(y2 < 0){
            y2 = getHeight() - 1;
        }
        vector = new Vector2d(x,y);
        return vector;
    }
    //remove Animal from map
    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
    }
    //remove Grass from map
    public void removeGrass(Grass grass){
        grasses.remove(grass.getPosition());
    }
    //add new Animal to map
    public void addAnimal(Animal animal){
        if(animals.get(animal.getPosition()) == null){
            ArrayList<Animal> list = new ArrayList<>();
            list.add(animal);
            animals.put(animal.getPosition(),list);
        }
        else{
            animals.get(animal.getPosition()).add(animal);
        }
    }
    //add new grass to map
    public void addGrass(Grass grass){
        if(grasses.get(grass.getPosition()) == null){
            ArrayList<Grass> list = new ArrayList<>();
            list.add(grass);
            grasses.put(grass.getPosition(),list);
        }
        else{
            grasses.get(grass.getPosition()).add(grass);
        }

    }
}
