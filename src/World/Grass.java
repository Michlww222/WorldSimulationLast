package World;

public class Grass {
    public RectangularMap map;
    public Vector2d position;
    public int energyIncrease;
    public int index;

    public Grass(RectangularMap map,Vector2d position,int energyIncrease,int index) {
        this.position = position;
        this.energyIncrease = energyIncrease;
        this.map = map;
        this.index = index;
    }

    public Vector2d getPosition(){ return position; }

}
