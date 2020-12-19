package World;
import java.io.IOException;
import java.util.*;

//public int getNewIndexAnimals() and grass lista w obserwerze po indexach{
//        return
//    }

public class MapObserver {
    private RectangularMap map;
    private int age = 0;
    private int numberOfDeadAnimals = 0;
    private float averageLifeOfDead = 0;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Grass> grassesList = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> bestGenomeList = new ArrayList<>();
    private ArrayList<Float> animalsPerAge = new ArrayList<>();
    private ArrayList<Float> grassesPerAge = new ArrayList<>();
    private ArrayList<Float>  averageEnergyPerDay= new ArrayList<>();
    private ArrayList<Float> averageOffspringsPerDay = new ArrayList<>();

    public MapObserver(RectangularMap map){
        this.map = map;
        for(Vector2d i : map.animals.keySet()) {
            for (int k = 0; k < map.animals.get(i).size(); k++) {
                animalList.add(map.animals.get(i).get(k));
            }
        }
        for(Vector2d i : map.grasses.keySet()){
            for(int k = 0; k < map.grasses.get(i).size(); k ++){
                grassesList.add(map.grasses.get(i).get(k));
            }
        }
        for(Animal i : animalList){
            bestGenomeList.add(i.getGenomeList());
        }
    }

    public void animalAppend(Animal animal){
        bestGenomeList.add(animal.getGenomeList());
        animalList.add(animal);
    }

    public void animalRemove(Animal animal){
        bestGenomeList.remove(animal.getGenomeList()); // moze nie dzialac
        numberOfDeadAnimals +=1;
        averageLifeOfDead = (averageLifeOfDead * (numberOfDeadAnimals-1) + animal.getAge())/numberOfDeadAnimals;
        animalList.remove(animal);
    }

    public void grassAppend(Grass grass){
        grassesList.add(grass);
    }

    public void grassRemove(Grass grass){
        grassesList.remove(grass);
    }

    public ArrayList<Integer> getBestGenome(){
        Collections.sort(bestGenomeList, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                int i = 0;
                while(i < o1.size() && o1.get(i).equals(o2.get(i))){
                    i += 1;
                }
                if(i == o1.size()){
                    return o1.get(i-1) - o2.get(i-1);
                }
                return o1.get(i) - o2.get(i);
            }
        });
        if(bestGenomeList.size() == 1){
            return bestGenomeList.get(0);
        }
        else{
            ArrayList<Integer> numberOfThisGenome = new ArrayList<>();
            ArrayList<ArrayList> allDifferentGenomes = new ArrayList<>();
            allDifferentGenomes.add(bestGenomeList.get(0));
            int k = 1;
            int sum = 1;
            ArrayList<Integer> ourGenome = bestGenomeList.get(0);
            while(k < bestGenomeList.size()){
                if(bestGenomeList.get(k).equals(ourGenome)){
                    sum += 1;
                    k += 1;
                }
                else{
                    numberOfThisGenome.add(sum);
                    sum = 1;
                    ourGenome = bestGenomeList.get(k);
                    allDifferentGenomes.add(ourGenome);
                    k += 1;
                }
            }
            numberOfThisGenome.add(sum);
            int m = numberOfThisGenome.get(0);
            int out = 0;
            for(int i =1;i< numberOfThisGenome.size();i+=1){
                if(m < numberOfThisGenome.get(i)){
                    out = i;
                    m = numberOfThisGenome.get(i);
                }
            }
            return allDifferentGenomes.get(out);
        }

    }

    public void nextAge(){
        age++;
        animalsPerAge.add((float) getNumberOfAnimals());
        grassesPerAge.add((float) getNumberOfGrasses());
        averageEnergyPerDay.add(averageEnergyOfAnimals());
        averageOffspringsPerDay.add(averageNumberOfChildren());

    }

    public int getAge() {
        return age;
    }

    public int getNumberOfAnimals(){
        return animalList.size();
    }

    public int getNumberOfGrasses(){
        return grassesList.size();
    }

    public float averageEnergyOfAnimals(){
        float allEnergy = 0;
        for(Animal i : animalList){
            allEnergy += i.energy;
        }
        allEnergy /= animalList.size();
        return allEnergy;
    }

    public float averageNumberOfChildren(){
        float allChildren = 0;
        for(Animal i : animalList){
            allChildren += i.offspringNumber; // zmiana na dzieciaki
        }
        allChildren /= animalList.size();
        return allChildren;
    }

    public int getNewIndexAnimals() {
        Collections.sort(animalList, new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                return o1.index - o2.index;
            }
        });
        int i = 0;
        for(Animal a : animalList){
            if(a.index == i){
                i +=1;
            }
            else{
                return i;
            }
        }
        return i+1;
    }

    public int getNewIndexGrasses() {
        Collections.sort(grassesList, new Comparator<Grass>() {
            @Override
            public int compare(Grass o1, Grass o2) {
                return o1.index - o2.index;
            }
        });
        int i = 0;
        for(Grass a : grassesList){
            if(a.index == i){
                i +=1;
            }
            else{
                return i;
            }
        }
        return i+1;
    }

    public void createReport(String path) throws IOException {
        String animalNumberAvg = String.valueOf(getAverageOfList(animalsPerAge));
        String grassNumberAvg = String.valueOf(getAverageOfList(grassesPerAge));
        String energyNumberAvg = String.valueOf(getAverageOfList(averageEnergyPerDay));
        String offspringsNumberAvg = String.valueOf(getAverageOfList(averageOffspringsPerDay));
        txtFileCreator report = new txtFileCreator(path);
        report.toTxt("Animal number per day average: " + animalNumberAvg + "\n" +
        "Grass number per day average: " + grassNumberAvg + "\n" +
        "Energy number per day average average: " + energyNumberAvg + "\n" +
        "Offsprings number per day average average: " + offspringsNumberAvg);
    }

    public static float getAverageOfList(ArrayList<Float> list){
        float sum = 0;
        for(int i =0; i < list.size();i++){
            sum += list.get(i);
        }
        return sum/list.size();
    }
}
