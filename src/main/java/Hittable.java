import java.util.ArrayList;
import java.util.List;

public interface Hittable {
    boolean hit(Ray r, Interval rayT, HitRecord rec);
    }
