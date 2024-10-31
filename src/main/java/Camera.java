import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Random;

public class Camera {
    public double aspectRatio = 16.0 / 9.0;
    public int imageWidth = 400;
    public int imageHeight;
    private vec3 cameraCenter;
    private vec3 pixel00Loc;
    private vec3 pixelDeltaU;
    private vec3 pixelDeltaV;
    private final int MaxColorValue = 255;
    public int samplesPerPixel = 10; //Count of random samples for each pixel
    private double pixelSamplesScale;
    public int maxDepth = 10; //Max numb of ray bounces into scene

    public void render(Hittable world) throws IOException {
        initialize();
        FileWriter fw = MakeFile();

        //Render Image
//        Random rand = new Random();
//        double randDouble =  (double)(rand.nextInt(256)/100);
        for (int i = 0; i < imageHeight; i++) {
            System.out.println("Scans remaining: " + (imageWidth - i));
            for (int j = 0; j < imageWidth; j++) {
                Color pixelColor = new Color(0,0,0);
                for (int sample = 0; sample < samplesPerPixel; sample++) {
                    Ray r = getRay(j, i);
                    vec3 vecRayColor = vec3.add(pixelColor, rayColor(r, maxDepth, world));
                    pixelColor = new Color(vecRayColor.getX(), vecRayColor.getY(), vecRayColor.getZ());
                   // System.out.println("Pixel: "+(i*j));

                }
               // System.out.println(vec3.multiply(pixelSamplesScale , pixelColor));
                Color.write_color(fw, vec3.multiply(pixelSamplesScale , pixelColor));
               // System.out.println("Rendering Pixel Number: "+ (i+j)+" out of "+(imageHeight*imageWidth));
            }
            if((imageWidth - i) == 0) System.out.println("No more lines");
        }

    }

    private void initialize() {

        // Calculate the image height, and ensure that it's at least 1
        imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = Math.max(imageHeight, 1);

        pixelSamplesScale = 1.0/samplesPerPixel;
        cameraCenter = new vec3(0, 0, 0);
        // Camera setup
        double focalLength = 1.0;
        double viewportHeight = 2.0;
        double viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);

//        double viewportWidth = (viewportHeight * aspectRatio);
//        vec3 cameraCenter = new vec3(0, 0, 0);

        // Calculate the vectors across the horizontal and down the vertical viewport edges
        vec3 viewportU = new vec3(viewportWidth, 0, 0);
        vec3 viewportV = new vec3(0, -viewportHeight, 0);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel
        pixelDeltaU = viewportU.divide(imageWidth);
        pixelDeltaV = viewportV.divide(imageHeight);
        //Right hand world space
        // Calculate the location of the upper-left pixe
        vec3 viewportUpperLeft = cameraCenter.clone();
        viewportUpperLeft = vec3.subtract(viewportUpperLeft, new vec3(0, 0, focalLength));
        //System.out.println("Focal Length: "+viewportUpperLeft.getZ());
        viewportUpperLeft = vec3.subtract(viewportUpperLeft, viewportU.divide(2));
        //System.out.println("X value: "+viewportUpperLeft.getX());
        viewportUpperLeft = vec3.subtract(viewportUpperLeft, viewportV.divide(2));
        //System.out.println("Y value: "+viewportUpperLeft.getY());

        pixel00Loc = viewportUpperLeft
                .add(pixelDeltaU.multiply(0.5))
                .add(pixelDeltaV.multiply(0.5));
    }

    public static Color rayColor(Ray r, int depth,  Hittable world) {
        if(depth <= 0) {
            return new Color(0,0,0);
        }

        HitRecord record = new HitRecord();

//        if (world.hit(r, new Interval(0.001, Double.POSITIVE_INFINITY), record)) {
//            Ray scattered = new Ray();
//            Color attenuation = new Color();//This is a color reduction as it passes through a medium
//           // Boolean isMatRec = record.materials.get().scatter(r, record, attenuation, scattered);
//         //   scattered = record.materials.get().getScattered();
//            ScatterReturn scatterReturn = record.materials.get().scatter(r, record, attenuation, scattered);
//            if (scatterReturn.scatter){
//              // System.out.println("scatterReturn "+scatterReturn.color);
//            //   vec3 tVec =  vec3.multiply(.1, Color.multiply(.75, rayColor(scattered, depth -1, world))); // Shade based on normal
//                   vec3 rayColor = rayColor(scatterReturn.ray, depth - 1, world);
//                 // System.out.println("Scattered color: " + rayColor);
//                   vec3 tVec = vec3.multiplyVec(scatterReturn.color, rayColor);
//                   Color newColor =  new Color(tVec.getX(), tVec.getY(), tVec.getZ());
//                  // System.out.println("newColor" + newColor);
//                   return newColor;
//            }
//          //  System.out.println("we r");
//            return new Color(0,0,0);//Otherwise the color is not scattered, it is absorbed.
//        }
        if (world.hit(r, new Interval(0.001, Double.POSITIVE_INFINITY), record)) {
            vec3 direction = vec3.add(record.normal, vec3.randomUnitVector());
            Ray ray = new Ray(record.point, direction);

            vec3 tVec = vec3.multiply(.1, rayColor(ray, depth - 1, world)); // Shade based on normal
            // tVec = tVec.multiplyVec(tVec, new vec3(0.5, 0.7, 1.0)); //Let's make it blue
            // tVec = tVec.multiplyVec(tVec, new vec3(0.0, 1, 0.0)); //Let's make it blue and green
            //  tVec = tVec.multiplyVec(tVec, new vec3(.2, 0.0, 0.0)); //Let's make it blue and green
            tVec = vec3.multiplyVec(tVec, new vec3(0.0, 0.0, 1.0)); //Let's make it blue and green
            return new Color(tVec.getX(), tVec.getY(), tVec.getZ());
        }



        // Background color
        vec3 unitDirection = vec3.unitVector(r.getDirection());
        double t = 0.5 * (unitDirection.getY() + 1.0);

        vec3 transitionVec = vec3.add(vec3.multiply(1.0 - t, new vec3(1.0, 1.0, 1.0)), Color.multiply(t, new Color(0.5, 0.7, 1.0)));
       // System.out.println("Background "+transitionVec);
        return new Color(transitionVec.getX(), transitionVec.getY(), transitionVec.getZ());

    }

    private void writeColor(Color pixelColor) {
        // Output the pixel color
        System.out.printf("%d %d %d%n",
                (int) (255.999 * pixelColor.r),
                (int) (255.999 * pixelColor.g),
                (int) (255.999 * pixelColor.b));
    }

    private FileWriter MakeFile() throws IOException {
        //Make File
        File imagePPM = null;
        FileWriter fw = null;
        String fileName = "imageTak1.ppm" + LocalDate.now();
        try {
            imagePPM = new File(fileName);
            if (imagePPM.createNewFile()) {
                System.out.println("File created: " + imagePPM.getName());
            } else {
                System.out.println("File already exists. We are overwriting it");
            }
            fw = new FileWriter(imagePPM);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        fw.write("P3\n");
        fw.write(imageWidth + " " + imageHeight + "\n");
        fw.write(MaxColorValue + "\n");
        return fw;
    }

    public Ray getRay(int i, int j) {
        // Construct a camera ray originating from the origin and directed at randomly sampled
        // point around the pixel location i, j.

        vec3 offset = sampleSquare();
        vec3 line1 = vec3.multiply((i+offset.getX()), pixelDeltaU);
        vec3 line2 = vec3.multiply((j+offset.getY()), pixelDeltaV);

        vec3 pixelSample = vec3.add(pixel00Loc,vec3.add(line1,line2));

        vec3 rayOrigin = cameraCenter;
        vec3 rayDirection = vec3.subtract(pixelSample , rayOrigin);

        return new Ray(rayOrigin, rayDirection);
    }

    public vec3 sampleSquare(){
        // Returns the vector to a random point in the [-.5,-.5]-[+.5,+.5] unit square.
        return new vec3(Utils.randomDouble() - 0.5, Utils.randomDouble() - 0.5, 0);
    }
}
