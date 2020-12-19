package World;

import java.util.*;

public class SimulationEngine implements IEngine {
    Random r = new Random();
    public RectangularMap map;
    public int w;
    public int h;
    public int age;
    public int startEnergy;
    private  final int lostEnergy;
    private final int plantEnergy;
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<Grass> grasses = new ArrayList<>();
    public ArrayList<Integer> animalsNumber = new ArrayList<>();
    public MapObserver observer;

    public SimulationEngine(int w, int h,int ratio, int startEnergy, int lostEnergy, int plantEnergy,int numberOfAnimal) {
        this.map = new RectangularMap(w, h,ratio);
        this.age = 0;
        this.startEnergy = startEnergy;
        this.lostEnergy = lostEnergy;
        this.plantEnergy = plantEnergy;
        for (int i = 0; i < numberOfAnimal; i += 1) {
            Animal newAnimal = initialAnimalCreator(startEnergy,i);
            map.addAnimal(newAnimal);
            animals.add(newAnimal);
        }
        Vector2d startPosition1 = map.jungle.jungleRandomPosition();
        Grass newGrass1 = new Grass(map, startPosition1,plantEnergy,0);
        grasses.add(newGrass1);
        map.addGrass(newGrass1);
        Vector2d startPosition2 = map.jungle.stepRandomPosition();
        Grass newGrass2 = new Grass(map, startPosition2, plantEnergy,1);
        grasses.add(newGrass2);
        map.addGrass(newGrass2);
        this.observer = new MapObserver(map);

    }

    @Override
    public void run() {
        animalsTurn();
        eatingGrasses();
        removeWorthlessGrasses();
        removeDeadAnimals();
        procreation2();
        addGrasses();
        nextAge();
    }

    public Animal initialAnimalCreator(int startEnergy,int index){
        ArrayList<Integer> basicGenomeList = new ArrayList<>();
        Vector2d startingPosition = map.randomFreePosition();
        for(int i = 0;i < 8; ++i){
            basicGenomeList.add(i);
        }
        for(int i = 8;i < 32; ++i){
            basicGenomeList.add(r.nextInt(8));
        }
        Collections.sort(basicGenomeList);
        Animal babyAnimal = new Animal(map,startingPosition,startEnergy,null,null,basicGenomeList, age,index);
        return babyAnimal;
    }

    public Animal animalCreator(Animal parent1,Animal parent2,int startEnergy){
        ArrayList<Integer> basicGenomeList = new ArrayList<>();
        Vector2d startingPosition = map.freePositionForNewborn(parent1.getPosition());
        for(int i = 0;i < 8; ++i){
            basicGenomeList.add(i);
        }
        for(int i = 8;i<20; ++ i){
            basicGenomeList.add(parent1.getGenomeList().get(r.nextInt(parent1.getGenomeList().size())));
        }
        for(int i = 20;i<32; ++ i){
            basicGenomeList.add(parent2.getGenomeList().get(r.nextInt(parent2.getGenomeList().size())));
        }
        parent1.childrenNumber += 1;
        parent2.childrenNumber += 1;
        Map<Integer,Animal> visited = new HashMap<>();
        visited = parent1.addOffspring(visited);
        visited.put(parent1.index,parent1);
        if(visited.get(parent2.index) == null){
            parent2.addOffspring(visited);
        }
        Collections.sort(basicGenomeList);
        Animal babyAnimal = new Animal(map,startingPosition,startEnergy,parent1,parent2,basicGenomeList,age, observer.getNewIndexAnimals());
        observer.animalAppend(babyAnimal);
        return  babyAnimal;
    }

    public void animalsTurn(){
        for (Animal i : animals) {
            map.removeAnimal(i);
            i.move();
            map.addAnimal(i);
        }
    }

    public void addGrasses(){
        Vector2d jungleGrass = map.jungle.jungleRandomPosition();
        if(jungleGrass != null){

            Grass grass1 = new Grass(map,jungleGrass,getPlantEnergy(), observer.getNewIndexGrasses());
            observer.grassAppend(grass1);
            map.addGrass(grass1);
            grasses.add(grass1);
        }
        Vector2d stepGrass = map.jungle.stepRandomPosition();
        if(stepGrass != null){
            Grass grass2 = new Grass(map,stepGrass,getPlantEnergy(), observer.getNewIndexGrasses());
            observer.grassAppend(grass2);
            map.addGrass(grass2);
            grasses.add(grass2);
        }
    }

    public void removeDeadAnimals(){
        int i = 0;
        while(i < animals.size()){
            animals.get(i).energy -= lostEnergy;
            if(animals.get(i).energy <= 0){
                map.removeAnimal(animals.get(i));
                observer.animalRemove(animals.get(i));
                animals.remove(i);
            }
            else{
                i++;
            }
        }
    }

    public void removeWorthlessGrasses() {
        int i = 0;
        while (i < grasses.size()) {
            if (grasses.get(i).energyIncrease <= 0) {
                map.removeGrass(grasses.get(i));
                observer.grassRemove(grasses.get(i));
                grasses.remove(i);
            } else {
                i++;
            }
        }
    }

    public void nextAge(){
        animalsNumber.add(animals.size());
        age += 1;
        observer.nextAge();
    }

    public int getPlantEnergy(){
        return plantEnergy;
    }

    public void eatingGrasses(){
        for(Vector2d i : map.animals.keySet()){
            if(map.animals.get(i).size()!= 0){
                if(map.isOccupiedByGrass(map.animals.get(i).get(0).getPosition())){
                    if(map.animals.get(i).size() == 1){
                        map.animals.get(i).get(0).energy += map.grasses.get(map.animals.get(i).get(0).getPosition()).get(0).energyIncrease;
                        map.grasses.get(map.animals.get(i).get(0).getPosition()).get(0).energyIncrease = 0;
                    }
                    else{
                        ArrayList<Animal> hOS = new ArrayList<>();
                        Collections.sort(map.animals.get(i), new Comparator<Animal>() {
                            @Override
                            public int compare(Animal o1, Animal o2) {
                                return o1.energy - o2.energy;
                            }
                        });
                        hOS.add(map.animals.get(i).get(0));
                        int j = 1;
                        while( j < map.animals.get(i).size() && map.animals.get(i).get(j).energy == map.animals.get(i).get(0).energy){
                            hOS.add(map.animals.get(i).get(j));
                            j++;
                        }
                        for (Animal k : hOS) {
                            k.energy += (map.grasses.get(map.animals.get(i).get(0).getPosition()).get(0).energyIncrease / hOS.size());
                        }
                        map.grasses.get(map.animals.get(i).get(0).getPosition()).get(0).energyIncrease = 0;
                        hOS.clear();
                    }
                }
            }


        }
    }

    public void procreation2(){
        List<Animal> newbornList = new ArrayList<>();
        for(Vector2d i : map.animals.keySet()){
            if(map.animals.get(i).size() > 1){
                if(map.animals.get(i).get(0).energy >= startEnergy && map.animals.get(i).get(1).energy >= startEnergy){
                    int newbornEnergy = map.animals.get(i).get(0).energy/4 + map.animals.get(i).get(1).energy/4;
                    map.animals.get(i).get(0).energy *= 3;
                    map.animals.get(i).get(0).energy /= 4;
                    map.animals.get(i).get(1).energy *= 3;
                    map.animals.get(i).get(1).energy /= 4;
                    Animal newborn = animalCreator(map.animals.get(i).get(0),map.animals.get(i).get(1),newbornEnergy);
                    newbornList.add(newborn);
                }
            }
        }
        for(Animal i : newbornList){
            map.addAnimal(i);
            animals.add(i);
        }
    }
}
