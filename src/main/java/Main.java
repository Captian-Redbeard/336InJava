import java.io.*;
import java.io.FileWriter;
import java.util.Random;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException {

        Thread thread = new Thread(() -> {
            // Task here
        });
        thread.start();
        try {
            thread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a HittableList for the scene
        HittableList world = new HittableList();
//        world.add(new Sphere(new vec3(0, 0, -1), 0.5)); // Add a sphere to the scene
//        world.add(new Sphere(new vec3(0, -100.5, -1), 100)); // Add a ground sphere

//        Materials MaterialGround = new Lambertian(new Color(.8,.8,0)); //Yellow
//        Materials MaterialCenter = new Lambertian(new Color(.1,.2,.5));
//        Materials MaterialLeft = new Lambertian(new Color(.8,.8,.8));
//        Materials MaterialRight = new Lambertian(new Color(.8,.6,.2));
//
//        world.add(new Sphere(new vec3(0.0, -100.5, -1.0), 100.0, MaterialGround));
//        world.add(new Sphere(new vec3(0.0, 0.0, -1.2), .5, MaterialCenter));
//        world.add(new Sphere(new vec3(-1.0, 0.0, -1.0), .5, MaterialLeft));
//        world.add(new Sphere(new vec3(1.0, 0.0, -1.0), .5, MaterialRight));

        world.add(new Sphere(new vec3(0,0, -1), .5));
        world.add(new Sphere(new vec3(0,-100.5, -1), 100));

        Camera cam = new Camera();
        cam.aspectRatio = 16.0 / 9.0;
        cam.imageWidth = 400;
        cam.samplesPerPixel = 100;
        cam.maxDepth = 2; //SHould be 50

        cam.render(world);

    }





//

}