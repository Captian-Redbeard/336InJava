public class vec3 implements Cloneable {

        private final double[] e = new double[3];

        public vec3() {
            this.e[0] = 0;
            this.e[1] = 0;
            this.e[2] = 0;
        }

        public vec3(double e0, double e1, double e2) {
            this.e[0] = e0;
            this.e[1] = e1;
            this.e[2] = e2;
        }

        public double getX() {return e[0];}
        public double getY() {return e[1];}
        public double getZ() {return e[2];}

        public vec3 negate() {
            return new vec3(-e[0], -e[1], -e[2]);
        }

        public double get(int i){
            return e[i];
        }

        public void set(int i, double value){
            e[i] = value;
        }

        public vec3 add(vec3 v){
            vec3 newVec = new vec3(this.e[0] + v.e[0], this.e[1] + v.e[1], this.e[2] + v.e[2]);
            return newVec;
        }

        public vec3 multiply(double t){
            return new vec3(getX()*t, getY()*t, getZ()*t);
        }

        public vec3 divide(double t){
            return multiply(1/t);
        }

        public double length(vec3 v){
            return Math.sqrt(v.lengthSquared());
        }

        public double lengthSquared(){
            return getX()*getX() + getY()*getY() + getZ()*getZ();
        }

        public boolean nearZero(){
            double s = 1e-8;
            return (e[0] < s && e[1] < s && e[2] < s);
        }

        //Utility Functions
        public static vec3 add(vec3 v1, vec3 v2){
            return new vec3(v1.getX()+v2.getX(), v1.getY()+v2.getY(), v1.getZ()+ v2.getZ());
        }

        public static vec3 subtract(vec3 v1, vec3 v2){
            return new vec3(v1.e[0]-v2.e[0], v1.e[1]-v2.e[1], v1.e[2]-v2.e[2]);
        }
        public static vec3 multiplyVec(vec3 v1, vec3 v2){
            return new vec3(v1.e[0]*v2.e[0], v1.e[1]*v2.e[1], v1.e[2]*v2.e[2]);
        }

        public static vec3 multiply(double t, vec3 v) {
            return new vec3(t * v.e[0], t * v.e[1], t * v.e[2]);
        }
        public static vec3 divide(vec3 v1, double t){
            return multiply(1.0 / t, v1);
        }

        public static double dot(vec3 v1, vec3 v2){
            return  v1.e[0]*v2.e[0] + v1.e[1]*v2.e[1] + v1.e[2]*v2.e[2];
        }

        public static vec3 cross(vec3 v1, vec3 v2){
            return new vec3(
                    v1.e[1] * v2.e[2] - v1.e[2] * v2.e[1],
                    v1.e[2] * v2.e[0] - v1.e[0] * v2.e[2],
                    v1.e[0] * v2.e[1] - v1.e[1] * v2.e[0]
            );
        }

        public static vec3 random(){
            return new vec3(Utils.randomDouble(), Utils.randomDouble(), Utils.randomDouble());
        }

        public static vec3 random(double min, double max){
            return new vec3(Utils.randomDouble(min,max), Utils.randomDouble(min,max), Utils.randomDouble(min,max));
        }

        public static vec3 unitVector(vec3 v){
            return divide(v, v.length(v));
        }

        public static vec3 randomUnitVector(){
            while (true) { //beware of infinite loops
                vec3 p = random(-1, 1);
                double lensq = p.lengthSquared();
                if (1e-160 < lensq && lensq <= 1) return unitVector(p);
            }
         }

         public static vec3 randomOnHemisphere(vec3 normal){
           vec3 onUnitSphere = randomUnitVector();
           if(dot(normal,onUnitSphere) > 0) return onUnitSphere;
           else return new vec3(-onUnitSphere.e[0],-onUnitSphere.e[1],-onUnitSphere.e[2]);
         }

         public static vec3 reflect(vec3 v, vec3 n){
            return vec3.subtract(v, vec3.multiply(2*vec3.dot(v,n),n));
         }


        @Override
        public String toString() {
            String  s = (int)(255.999 * e[0]) + " " + (int)(255.999 * e[1]) + " " + (int)(255.999 * e[2])+"\n";

            return s;
        }

        @Override
        public vec3 clone(){
            vec3 NewVec = new vec3();
            NewVec.e[0] = this.e[0];
            NewVec.e[1] = this.e[1];
            NewVec.e[2] = this.e[2];
            return NewVec;
        }


}