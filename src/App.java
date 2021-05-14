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
        Image.setDir("images/filters");

        head.saveAs("head.jpg");
        head.toNegative().saveAs("head_negative.jpg");

        anatole.saveAs("anatole.jpg");
        anatole.toNegative().saveAs("anatole_negative.jpg");

        head.toGrayScale().saveAs("head_grayscale.jpg");
        head.toGrayScale().toNegative().saveAs("head_grayscale_negative.jpg");

        anatole.toGrayScale().saveAs("anatole_grayscale.jpg");
        anatole.toGrayScale().toNegative().saveAs("anatole_grayscale_negative.jpg");

        head.toBW().saveAs("head_bw.jpg");
        head.toBW().toNegative().saveAs("head_bw_negative.jpg");

        anatole.toBW().saveAs("anatole_bw.jpg");
        anatole.toBW().toNegative().saveAs("anatole_bw_negative.jpg");
    }

    public static void convolutions() throws IOException{
        Image.setDir("images/convolutions");

        head.applyConvolution(Image.sharpen).saveAs("head_sharpened1.jpg");
        head.applyConvolution(Image.sharpen, 10).saveAs("head_sharpened10.jpg");

        anatole.applyConvolution(Image.sharpen).saveAs("anatole_sharpened1.jpg");
        anatole.applyConvolution(Image.sharpen, 10).saveAs("anatole_sharpened10.jpg");

        head.applyConvolution(Image.blurGaussian, 10).saveAs("head_blurred10.jpg");
        head.applyConvolution(Image.blurGaussian, 100).saveAs("head_blurred10.jpg");

        anatole.applyConvolution(Image.blurGaussian, 10).saveAs("anatole_blurred10.jpg");
        anatole.applyConvolution(Image.blurGaussian, 100).saveAs("anatole_blurred100.jpg");

        head.applyConvolution(Image.edges).saveAs("head_edges.jpg");
        head.applyConvolution(Image.edges, 100).saveAs("head_edges100.jpg");

        anatole.applyConvolution(Image.edges).saveAs("anatole_edges.jpg");
        anatole.applyConvolution(Image.edges, 100).saveAs("anatole_edges100.jpg");

        head.applyConvolution(Image.stamped, 1).saveAs("head_stamped1.jpg");
        head.applyConvolution(Image.stamped, 10).saveAs("head_stamped10.jpg");

        anatole.applyConvolution(Image.stamped, 1).saveAs("anatole_stamped1.jpg");
        anatole.applyConvolution(Image.stamped, 10).saveAs("anatole_stamped10.jpg");
    }

    public static void transformations() throws IOException{
        Image.setDir("images/transformations");

        head.applyTransformation(Image.dilatation(0.25, 0.5)).saveAs("head_dilated1.jpg");
        head.applyTransformation(Image.dilatation(0.75, 0.33)).saveAs("head_dilated2.jpg");

        anatole.applyTransformation(Image.dilatation(0.25, 0.5)).saveAs("anatole_dilated1.jpg");
        anatole.applyTransformation(Image.dilatation(0.75, 0.33)).saveAs("anatole_dilated2.jpg");

        head.applyTransformation(Image.verticalSym).saveAs("head_vertSym.jpg");
        head.applyTransformation(Image.horizontalSym).saveAs("head_horizSym.jpg");

        anatole.applyTransformation(Image.verticalSym).saveAs("anatole_vertSym.jpg");
        anatole.applyTransformation(Image.horizontalSym).saveAs("anatole_horizSym.jpg");

        head.applyTransformation(Image.rotation(Math.PI / 3)).saveAs("head_rotated.jpg");
        head.applyTransformation(Image.incline1).saveAs("head_incline.jpg");

        anatole.applyTransformation(Image.rotation(Math.PI / 3)).saveAs("anatole_rotated.jpg");
        anatole.applyTransformation(Image.incline1).saveAs("anatole_incline.jpg");
    }

    public static void experiments() throws IOException{
        Image.setDir("images/experiments");

        Image grid = Image.newImage(1000, 1000);
        grid.insert(anatole.applyConvolution(Image.sharpen, 10).applyTransformation(Image.rotation(- Math.PI / 3)).resize(500, 500), 0, 0);
        grid.insert(anatole.applyConvolution(Image.blurGaussian, 200).applyTransformation(Image.rotation(Math.PI / 3)).resize(500, 500), 0, 500);
        grid.insert(anatole.applyConvolution(Image.stamped, 2).applyTransformation(Image.rotation(- Math.PI / 3 * 2)).resize(500, 500), 500, 0);
        grid.insert(anatole.applyConvolution(Image.edges2, 4).applyTransformation(Image.rotation(Math.PI / 3 * 2)).resize(500, 500), 500, 500);
        grid.insert(anatole.resize(250, 250), 500 - 125, 500 - 125);
        grid.saveAs("anatole_moment.jpg");
    }
}