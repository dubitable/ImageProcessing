public class App {
    public static HackImage head, anatole;
    public static void main(String[] args) throws Exception {
        String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
        String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

        head = new HackImage(Image.open(img1));
        anatole = new HackImage(Image.open(img2));

        HackImageTester.test4();
    }

}