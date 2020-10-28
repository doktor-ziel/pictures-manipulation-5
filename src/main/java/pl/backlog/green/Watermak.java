package pl.backlog.green;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import static org.opencv.core.Core.addWeighted;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.*;

public class Watermak {

    private static Mat addWatermarkToImage(Mat image, Mat watermark) {
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

    private static void performAction(String imagePath, String watermarkPath, String outputImagePath) {
        Mat image = Imgcodecs.imread(imagePath, IMREAD_COLOR);
        Mat watermark = imread(watermarkPath, IMREAD_UNCHANGED);
        imwrite(outputImagePath, addWatermarkToImage(image, watermark));
    }

    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();

//        Options options = new Options();
//
//        Option input = new Option("i", "input", true, "path to input image");
//        input.setRequired(true);
//        options.addOption(input);
//
//        Option output = new Option("o", "output", true, "path to outpur image");
//        output.setRequired(true);
//        options.addOption(output);
//
//        Option watermark = new Option("w", "watermark", true, "path to watermark image");
//        watermark.setRequired(true);
//        options.addOption(watermark);
//
//        Option help = new Option("h", "help", false, "prints this message");
//        options.addOption(help);
//
//
//        CommandLineParser parser = new DefaultParser();
//        HelpFormatter helpFormatter = new HelpFormatter();
//        CommandLine cmd = null;
//
//        try {
//            cmd = parser.parse(options, args);
//        } catch (ParseException e) {
//            System.out.println(e.getMessage());
//            helpFormatter.printHelp("watermark.sh [OPTIONS]", options);
//            System.exit(1);
//        }
//        performAction(cmd.getOptionValue("i"), cmd.getOptionValue("w"), cmd.getOptionValue("o"));
    }
}
