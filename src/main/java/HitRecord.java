import java.util.Optional;

public class HitRecord {

        vec3 point;
        vec3 normal;
        public Optional<Materials> materials;

        double t;
        boolean frontFace;

        public void setFaceNormal(Ray r, vec3 outwardNormal) {
            // Sets the hit record normal vector.
            // Assumes that outwardNormal has unit length.

            frontFace = vec3.dot(r.getDirection(),outwardNormal) < 0;
            normal = frontFace ? outwardNormal : outwardNormal.negate(); // Use negate() for -outwardNormal
        }

        public void clone(HitRecord tempRec) {
            this.point = tempRec.point;
            this.normal = tempRec.normal;
            this.t = tempRec.t;
            this.frontFace = tempRec.frontFace;
            this.materials = tempRec.materials;

        }
}

