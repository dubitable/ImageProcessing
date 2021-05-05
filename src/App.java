public class App {
    public static void main(String[] args) throws Exception {
        String anatole = "/Users/pierrequereuil/Desktop/anatole.png";
        String head = "/Users/pierrequereuil/Desktop/head.jpeg";

        Image img1 = Image.open(anatole);
        img1.toNegative().toGrayScale().toNegative().saveAs("anatole_negative_grayscale_negative.pbm");
    }
}
