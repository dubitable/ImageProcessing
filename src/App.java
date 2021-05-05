public class App {
    public static void main(String[] args) throws Exception {
        String anatole = "/Users/pierrequereuil/Desktop/anatole.png";
        String head = "/Users/pierrequereuil/Desktop/head.jpeg";

        Image img1 = Image.open(anatole);
        Image img2 = Image.open(head);
        Image img3 = img2.insert(img1);
        img3.saveAs("head_anatole.pbm");
    }
}
