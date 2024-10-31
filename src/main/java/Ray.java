public class Ray {

    // Member variables for the origin and direction
    private  vec3 orig;
    private  vec3 dir;

    public Ray() {
        this.orig = new vec3();
        this.dir = new vec3();
    }

    // Constructor with parameters
    public Ray(vec3 origin, vec3 direction) {
        this.orig = origin.clone();
        this.dir = direction.clone();
    }

    // Getter for origin
    public vec3 getOrigin() {
        return orig;
    }

    // Getter for origin
    public void setOrigin(vec3 origin) {
        this.orig = origin;
    }

    // Getter for direction
    public vec3 getDirection() {
        return dir;
    }

    // Setter for direction
    public void setDirection(vec3 direction) {
        this.dir = direction;
    }

    // The 'at' method that calculates a point along the ray
    public vec3 at(double t) {
        return orig.add(dir.multiply(t)); // Assuming Vec3 supports 'add' and 'multiply' methods
    }

    public static double dot(vec3 v1, vec3 v2){
        return  vec3.dot(v1,v2);
    }

}
