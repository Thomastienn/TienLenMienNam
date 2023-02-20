import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //String players = JOptionPane.showInputDialog("How many players do you want? (2-4)", "4");

        String players = "2";

        try {
            BigTwo game = new BigTwo(Integer.parseInt(players));
            game.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
