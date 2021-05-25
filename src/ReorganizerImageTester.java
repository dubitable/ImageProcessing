import java.io.IOException;

public class ReorganizerImageTester {
    public static void test() throws IOException{
        String img1 = "/Users/pierrequereuil/Desktop/anatole.png";
        String img2 = "/Users/pierrequereuil/Desktop/head.jpeg";

        ReorganizerImage anatole = new ReorganizerImage(Image.open(img1));
        ReorganizerImage head = new ReorganizerImage(Image.open(img2));

        Image.setDir("images");
        anatole.reoganize(anatole).saveAs("yeah.jpg");
    }   
}
