import java.util.ArrayList;
import java.util.List;

public class HittableList implements Hittable {
    private final List<Hittable> objects;


    public HittableList() {
        objects = new ArrayList<>();
    }

    public void clear() {
        objects.clear();
    }

    public void add(Hittable object) {
        objects.add(object);
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        HitRecord temp_rec = new HitRecord();
        boolean hitAnything = false;
        double closestSoFar = rayT.max;

        for (Hittable object : objects) {
            if (object.hit(r,new Interval(rayT.min, closestSoFar), temp_rec)) {
                hitAnything = true;
                closestSoFar = temp_rec.t;
                rec.clone(temp_rec); // Assuming a method to copy hit record data
            }
        }
        return hitAnything;
    }
}


