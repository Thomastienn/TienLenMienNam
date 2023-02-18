import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // BigTwo game = new BigTwo(2);
        // game.run();

        String currentWorkingDir = System.getProperty("user.dir").replace("\\", "/");
        String imgDir = currentWorkingDir + "/img";

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        JFrame frame = new JFrame("Big Two");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(width, height);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.RED);

        // First line
        JPanel firstLinePanel = new JPanel();
        firstLinePanel.setBackground(Color.RED);
        ImageIcon icon = new ImageIcon(imgDir + "/gray_back.png");
        JLabel label = new JLabel(icon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstLinePanel.add(label);
        mainPanel.add(firstLinePanel);

        // Second line
        JPanel secondLinePanel = new JPanel(new BorderLayout());
        secondLinePanel.setBackground(Color.RED);

        JLabel label1 = new JLabel(new ImageIcon(imgDir + "/gray_back.png"));
        secondLinePanel.add(label1, BorderLayout.WEST);

        JPanel secondLineCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondLineCenterPanel.setBackground(Color.RED);
        for(int i = 0; i < 4; i++){
            secondLineCenterPanel.add(new JLabel(new ImageIcon(imgDir + "/gray_back.png")));
        }
        secondLinePanel.add(secondLineCenterPanel, BorderLayout.CENTER);

        JLabel label2 = new JLabel(new ImageIcon(imgDir + "/gray_back.png"));
        secondLinePanel.add(label2, BorderLayout.EAST);

        mainPanel.add(secondLinePanel);

        // Third line
        JPanel thirdLinePanel = new JPanel();
        thirdLinePanel.setBackground(Color.RED);
        
        JPanel thirdLineCenterPanel = new JPanel();

        JButton playButton = new JButton("Play");
        JButton skipButton = new JButton("Skip");
        thirdLineCenterPanel.add(playButton);
        thirdLineCenterPanel.add(skipButton);
        thirdLineCenterPanel.setOpaque(false);

        thirdLinePanel.add(thirdLineCenterPanel);

        mainPanel.add(thirdLinePanel);

        // Fourth line
        JPanel fourthLinePanel = new JPanel();
        fourthLinePanel.setBackground(Color.RED);

        for(int i = 0; i < 13; i++){
            fourthLinePanel.add(new JLabel(new ImageIcon(imgDir + "/gray_back.png")));
        }

        mainPanel.add(fourthLinePanel);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }
}
