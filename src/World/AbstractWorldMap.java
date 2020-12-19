package World;

import java.util.HashMap;
import java.util.Map;


abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {
    public Map<Integer, Animal> animals = new HashMap<>();
    protected Map<Integer, Grass> grasses = new HashMap<>();

    /*public String toString(Vector2d start, Vector2d end) {
        MapVisualizer drawing = new MapVisualizer(this);
        return drawing.draw(start, end);
    }
*/
    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.put(animal.getPosition().hashCode(), animal);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition().toString() + " is already occupied");
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (animals.get(position.hashCode()) != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (animals.get(position.hashCode()) != null ) {
            return true;
        } else if (grasses.get(position.hashCode()) != null){
            return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (animals.get(position.hashCode()) != null) {
            return animals.get(position.hashCode());
        }
        if (grasses.get(position.hashCode()) != null) {
            return grasses.get(position.hashCode());
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animals.put(newPosition.hashCode(), animals.get(oldPosition.hashCode()));
        animals.remove(oldPosition.hashCode());
    }
}

