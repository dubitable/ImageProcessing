public class App {
    public static void main(String[] args) throws Exception {
        String anatole = "/Users/pierrequereuil/Desktop/anatole.png";
        String head = "/Users/pierrequereuil/Desktop/head.jpeg";

        Image img1 = Image.open(anatole);
        img1.resize(100, 1000).saveAs("resized.pbm");;
    }
}
