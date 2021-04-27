package org.image.processing.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BenchMark {
    @State(Scope.Thread)
    public static class ClassValues {
        public static final Logger logger = Logger.getLogger(BenchMark.class.getName());
        static final int scaledWidth = 1024;
        static final int scaledHeight = 1000;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(0)
    public void rotateAndResizeImage(Blackhole bm) {
        try {
            // reads in the image
            BufferedImage image = ImageIO.read(new File("image.jpg"));
            // Creates output images
            BufferedImage outputImage = new BufferedImage(ClassValues.scaledWidth, ClassValues.scaledHeight, image.getType());

            //Scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(image, 0, 0, ClassValues.scaledWidth, ClassValues.scaledHeight, null);
            g2d.dispose();

            bm.consume(outputImage);
        } catch (Exception e) {
            ClassValues.logger.log(Level.INFO, " error occurred rotating the image-> " + e);
        }
    }

    public void main() throws Exception {
        Options opt = new OptionsBuilder()
                .include(BenchMark.class.getSimpleName())
                .build();
        new Runner(opt).run();

        //prints out the results to the console.
        ClassValues.logger.log(Level.INFO, "RESULTS_OF_BENCHMARK");
        ClassValues.logger.log(Level.INFO, "Image Rotation BenchMark has been run");
    }
}
