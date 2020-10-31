package pl.backlog.green;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static org.opencv.core.Core.addWeighted;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.*;

@CommandLine.Command(
        name = "watermark.sh",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Simple program to put watermark into image"
)
public class Watermak implements Callable<Integer> {

    @CommandLine.Option(names = {"-i", "--input"}, required = true, paramLabel = "PATH", description = "path to input image - to put image in")
    String imagePath;

    @CommandLine.Option(names = {"-o", "--output"}, required = true, paramLabel = "PATH", description = "path to output image")
    String outputImagePath;

    @CommandLine.Option(names = {"-w", "--watermark"}, required = true, paramLabel = "PATH", description = "path to watermark image")
    String watermarkPath;

    private Mat addWatermarkToImage(Mat image, Mat watermark) {
        Size inSize = image.size();
        Size outSize = new Size(inSize.width/2.5, inSize.height/2.5);

        Mat scaledImage = new Mat();
        resize(image, scaledImage, outSize);
        cvtColor(scaledImage, scaledImage, COLOR_BGR2BGRA);

        Mat transparentLayer = new Mat(outSize, CvType.CV_8UC4);
        Rect roi = new Rect(
                scaledImage.cols() - watermark.cols() - 10,
                scaledImage.rows() - watermark.rows() - 10,
                watermark.cols(),
                watermark.rows());
        watermark.copyTo(transparentLayer.submat(roi));

        Mat result = new Mat();
        addWeighted(scaledImage, 1, transparentLayer, 0.35, 0, result);

        return result;
    }

    @Override
    public Integer call() throws Exception {
        Mat image = Imgcodecs.imread(imagePath, IMREAD_COLOR);
        Mat watermark = imread(watermarkPath, IMREAD_UNCHANGED);
        imwrite(outputImagePath, addWatermarkToImage(image, watermark));
        return 0;
    }

    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();

        int exitCode = new CommandLine(new Watermak()).execute(args);
        System.exit(exitCode);
    }
}
