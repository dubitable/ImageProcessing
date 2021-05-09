public class App {
    public static void main(String[] args) throws Exception {
        String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
        String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

        Image head = Image.open(img1);
        Image anatole = Image.open(img2);
    }
}