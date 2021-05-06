import jdk.jshell.execution.Util;

public class App {
    public static void main(String[] args) throws Exception {
        String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
        String img2 = "/Users/pierrequereuil/Desktop/anatole.png";
        String img3 = "/Users/pierrequereuil/Desktop/impostor.png";

        Image head = Image.open(img1);
        Image anatole = Image.open(img2);
        
        anatole.applyConvolution(Utilities.flou, 100).saveAs("whatamidoingwithmylife.jpg");
    }
}
