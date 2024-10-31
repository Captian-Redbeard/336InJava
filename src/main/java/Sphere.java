import java.util.Optional;

// Sphere class in Java that implements the Hittable interface
public class Sphere implements Hittable {
    private final vec3 center;
    private final double radius;
    private Materials material;

    // Constructor for the Sphere
    public Sphere(vec3 center, double radius) {
        this.center = center;
        this.radius = Math.max(0, radius); // Use Math.max to ensure non-negative radius
       // this.material = material;
    }

    // Override the hit method from Hittable
    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        vec3 oc =  vec3.subtract(center, r.getOrigin()); // center - r.origin()
        double a = r.getDirection().lengthSquared(); // r.direction().length_squared()
        double halfB = vec3.dot(r.getDirection(),(oc)); // dot product of r.direction and oc
        double c = oc.lengthSquared() - (radius * radius); // oc.length_squared() - radius^2

        double discriminant = halfB * halfB - a * c;
        if (discriminant < 0) {
            return false;
        }

        double sqrtd = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        double root = (-halfB - sqrtd) / a;
        if (root <= rayT.min || rayT.max <= root) {
            root = (halfB + sqrtd) / a;
            if (root <= rayT.min || rayT.max <= root) {
                return false;
            }
        }

        rec.t = root;
        rec.point = r.at(rec.t);
        vec3 outward_normal = vec3.divide(vec3.subtract(rec.point, center), radius);
        rec.setFaceNormal(r, outward_normal);
       // rec.materials = Optional.of(material);
        return true;
    }

}
