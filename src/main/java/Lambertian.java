public class Lambertian extends Materials{
    private Color albedo;

    //My mod to make this work in java
//    private Color attenuation;
//    private Ray scattered;


    public Lambertian(Color albedo){
        this.albedo = albedo.clone();
    }

    @Override
    public ScatterReturn scatter(Ray rayIn, HitRecord hitRecord, Color attenuation, Ray scattered) {
        vec3 scatterDirection = vec3.add(hitRecord.normal, vec3.randomUnitVector());
        if(scatterDirection.nearZero()) scatterDirection = hitRecord.normal;

        ScatterReturn scatterReturn = new ScatterReturn();
        scatterReturn.ray = new Ray(hitRecord.point, scatterDirection);
        scatterReturn.scatter = true;
        scatterReturn.color = albedo.clone();
        return scatterReturn;
    }

}
