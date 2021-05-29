import java.io.IOException;

public class ReorganizerImageTester {
    public static void test() throws IOException{
        String img1 = "/Users/pierrequereuil/Desktop/anatole.png";
        String img2 = "/Users/pierrequereuil/Desktop/head.jpeg";

        ReorganizerImage anatole = new ReorganizerImage(Image.open(img1));
        ReorganizerImage head = new ReorganizerImage(Image.open(img2));

        Image.setDir("images/reorganizing");

        head.reoganize(anatole).saveAsPBM("head_reorganized_anatole.jpg");
        anatole.reoganize(head).saveAsPBM("anatole_reorganized_head.jpg");

        anatole.reoganize(anatole).saveAsPBM("anatole_reorganized_anatole.jpg");
        head.reoganize(head).saveAsPBM("head_reorganized_head.jpg");
    }   
}
