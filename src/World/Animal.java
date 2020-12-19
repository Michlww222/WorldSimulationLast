package World;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Animal  {
    Random r = new Random();
    private Vector2d initialPosition; // starting position
    public int energy; // starting energy of animal
    private final int age; // age in which animal started its life
    public int offspringNumber = 0; // number of offsprings of this animal
    private ArrayList<Integer> genomeList = new ArrayList<>(); // genome list
    private Move.MoveDirection orientation = Move.MoveDirection.N; // starting orientation of animal
    public int index; // unique index of animal
    public ArrayList<Animal> parents = new ArrayList<>(); // parents of this animal
    public RectangularMap map; // map of this animal
    public int childrenNumber = 0;

    public Animal(RectangularMap map, Vector2d initialPosition, int energy, Animal animal1, Animal animal2, ArrayList<Integer> genomeList, int age,int index){
        int a = r.nextInt(8);
        this.parents.add(animal1);
        this.parents.add(animal2);
        this.index = index;
        this.initialPosition = initialPosition;
        this.map = map;
        this.energy = energy;
        this.orientation = Move.turn(orientation,a);
        this.genomeList = genomeList;
        this.age = age;

    }

    // get animal position
    public Vector2d getPosition(){
        return initialPosition;
    }
    public Integer getEnergy() {
        return energy;
    }
    public Integer getAge() {
        return age;
    }
    public Integer getOffspringNumber() {
        return offspringNumber;
    }
    public Integer getChildrenNumber() {
        return childrenNumber;
    }
    public ArrayList<Integer> getGenomeList() {
        return genomeList;
    }

    // remove this
    public String toString(){ return String.valueOf(energy); }
    // move animal to random position next to it
    public void move() {
        Move.MoveDirection direction = orientation;
        int a = r.nextInt(genomeList.size());
        direction = Move.turn(direction, genomeList.get(a));
        Vector2d  possiblePosition = initialPosition.add(Move.toUnitVector(direction));
        int x = possiblePosition.getX();
        int y = possiblePosition.getY();
        if(x >= map.getWidth()){
            x %= map.getWidth();
        } else if(x < 0){
            x = map.getWidth() - 1;
        }
        if(y >= map.getHeight()){
            y %= map.getHeight();
        } else if(y < 0){
            y = map.getHeight() - 1;
        }
        orientation = direction;
        initialPosition = new Vector2d(x,y);
    }
    //turn animal to the right direction
    // add 1 to number of offspring of this animal and all his ancestors
    public Map<Integer,Animal> addOffspring(Map<Integer,Animal> visited){
        if(parents.get(0) != null && visited.get(parents.get(0).index) == null) {
            visited.put(parents.get(0).index,parents.get(0));
            visited = parents.get(0).addOffspring(visited);
        }
        if(parents.get(1) != null && visited.get(parents.get(1).index) == null){
            visited.put(parents.get(1).index,parents.get(1));
            visited = parents.get(1).addOffspring(visited);
        }
        offspringNumber += 1;
        return visited;
    }


}
