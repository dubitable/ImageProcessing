import java.io.IOException;

public class App {
    static Image head, anatole;
    public static void main(String[] args) throws Exception {
        String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
        String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

        head = Image.open(img1);
        anatole = Image.open(img2);
    }

    public static void filters() throws IOException{
        String dir = "images/filters";

        head.saveAs("head.jpg", dir);
        head.toNegative().saveAs("head_negative.jpg", dir);

        anatole.saveAs("anatole.jpg", dir);
        anatole.toNegative().saveAs("anatole_negative.jpg", dir);

        head.toGrayScale().saveAs("head_grayscale.jpg", dir);
        head.toGrayScale().toNegative().saveAs("head_grayscale_negative.jpg", dir);

        anatole.toGrayScale().saveAs("anatole_grayscale.jpg", dir);
        anatole.toGrayScale().toNegative().saveAs("anatole_grayscale_negative.jpg", dir);

        head.toBW().saveAs("head_bw.jpg", dir);
        head.toBW().toNegative().saveAs("head_bw_negative.jpg", dir);

        anatole.toBW().saveAs("anatole_bw.jpg", dir);
        anatole.toBW().toNegative().saveAs("anatole_bw_negative.jpg", dir);
    }

    public static void convolutions() throws IOException{
        String dir = "images/convolutions";

        head.applyConvolution(Image.sharpen).saveAs("head_sharpened1.jpg", dir);
        head.applyConvolution(Image.sharpen, 10).saveAs("head_sharpened10.jpg", dir);

        anatole.applyConvolution(Image.sharpen).saveAs("anatole_sharpened1.jpg", dir);
        anatole.applyConvolution(Image.sharpen, 10).saveAs("anatole_sharpened10.jpg", dir);

        head.applyConvolution(Image.blurGaussian, 10).saveAs("head_blurred10.jpg", dir);
        head.applyConvolution(Image.blurGaussian, 100).saveAs("head_blurred10.jpg", dir);

        anatole.applyConvolution(Image.blurGaussian, 10).saveAs("anatole_blurred10.jpg", dir);
        anatole.applyConvolution(Image.blurGaussian, 100).saveAs("anatole_blurred100.jpg", dir);

        head.applyConvolution(Image.edges).saveAs("head_edges.jpg", dir);
        head.applyConvolution(Image.edges, 100).saveAs("head_edges100.jpg", dir);

        anatole.applyConvolution(Image.edges).saveAs("anatole_edges.jpg", dir);
        anatole.applyConvolution(Image.edges, 100).saveAs("anatole_edges100.jpg", dir);

        head.applyConvolution(Image.stamped, 1).saveAs("head_stamped1.jpg", dir);
        head.applyConvolution(Image.stamped, 10).saveAs("head_stamped10.jpg", dir);

        anatole.applyConvolution(Image.stamped, 1).saveAs("anatole_stamped1.jpg", dir);
        anatole.applyConvolution(Image.stamped, 10).saveAs("anatole_stamped10.jpg", dir);
    }

    public static void transformations() throws IOException{
        String dir = "images/transformations";

        head.applyTransformation(Image.dilatation(0.25, 0.5)).saveAs("head_dilated1.jpg", dir);
        head.applyTransformation(Image.dilatation(0.75, 0.33)).saveAs("head_dilated2.jpg", dir);

        anatole.applyTransformation(Image.dilatation(0.25, 0.5)).saveAs("anatole_dilated1.jpg", dir);
        anatole.applyTransformation(Image.dilatation(0.75, 0.33)).saveAs("anatole_dilated2.jpg", dir);

        head.applyTransformation(Image.verticalSym).saveAs("head_vertSym.jpg", dir);
        head.applyTransformation(Image.horizontalSym).saveAs("head_horizSym.jpg", dir);

        anatole.applyTransformation(Image.verticalSym).saveAs("anatole_vertSym.jpg", dir);
        anatole.applyTransformation(Image.horizontalSym).saveAs("anatole_horizSym.jpg", dir);

        head.applyTransformation(Image.rotation(Math.PI / 3)).saveAs("head_rotated.jpg", dir);
        head.applyTransformation(Image.incline1).saveAs("head_incline.jpg", dir);

        anatole.applyTransformation(Image.rotation(Math.PI / 3)).saveAs("anatole_rotated.jpg", dir);
        head.applyTransformation(Image.incline1).saveAs("anatole_incline.jpg", dir);
    }

    public static void experiments() throws IOException{
        String dir = "images/experiments";

        Image grid = Image.newImage(1000, 1000);
        grid.insert(anatole.applyConvolution(Image.sharpen, 10).applyTransformation(Image.rotation(- Math.PI / 3)).resize(500, 500), 0, 0);
        grid.insert(anatole.applyConvolution(Image.blurGaussian, 200).applyTransformation(Image.rotation(Math.PI / 3)).resize(500, 500), 0, 500);
        grid.insert(anatole.applyConvolution(Image.stamped, 2).applyTransformation(Image.rotation(- Math.PI / 3 * 2)).resize(500, 500), 500, 0);
        grid.insert(anatole.applyConvolution(Image.edges2, 4).applyTransformation(Image.rotation(Math.PI / 3 * 2)).resize(500, 500), 500, 500);
        grid.insert(anatole.resize(250, 250), 500 - 125, 500 - 125);
        grid.saveAs("anatole_moment.jpg", dir);
    }
}