public class App {
    public static void main(String[] args) throws Exception {
        String anatole = "/Users/pierrequereuil/Desktop/anatole.png";
        Image img1 = Image.openRGB(anatole);
        img1.saveAs("anatole.pbm");
    }
}
