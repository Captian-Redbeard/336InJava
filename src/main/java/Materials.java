public abstract class Materials {
    //My mod to make this work in java

    public ScatterReturn scatter(Ray rayIn, HitRecord hitRecord, Color attenuation, Ray scattered) {
        return new ScatterReturn();
    }


}
