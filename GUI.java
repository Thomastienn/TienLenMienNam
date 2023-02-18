import java.awt.*;
import javax.swing.*;

public class GUI {
    // Directory
    private final String currentWorkingDir = System.getProperty("user.dir").replace("\\", "/");
    private final String imgDir = currentWorkingDir + "/img";
    private JFrame frame;
    private JPanel mainPanel, firstLine, secondLine, thirdLine, prevCards, btns, playerCards;
    private JLabel player1, player2, player3;
    private JButton playBtn, skipBtn;
    private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int screenHeight = device.getDisplayMode().getHeight();
    private final int screenWidth = device.getDisplayMode().getWidth();
    

    public GUI(){
        final ImageIcon backIcon = new ImageIcon(imgDir + "/gray_back.png");

        this.frame = new JFrame("Big Two");
        this.mainPanel = new JPanel(new BorderLayout());
        this.firstLine = new JPanel();
        this.player1 = new JLabel(backIcon);
        this.secondLine = new JPanel(new BorderLayout());
        this.player2 = new JLabel(backIcon);
        this.player3 = new JLabel(backIcon);
        this.prevCards = new JPanel(new GridLayout(0, Math.max(BigTwo.previousPlayedCard.size(), 1)));
        this.thirdLine = new JPanel(new BorderLayout());
        this.btns = new JPanel();
        this.playerCards = new JPanel();
        this.playBtn = new JButton("Play");
        this.skipBtn = new JButton("Skip");
    }

    private String cardToDir(Card card){
        return System.getProperty("user.dir").replace("\\", "/") + "/img/" + card.toString() + ".png";
    }

    public void initGUI(){
        firstLine.add(player1);
        firstLine.setBackground(Color.RED);

        // Load previous card on the deck
        for(Card card: BigTwo.previousPlayedCard){
            ImageIcon cardImg = new ImageIcon(cardToDir(card));
            JLabel cardPrev = new JLabel(cardImg);
            prevCards.add(cardPrev);
        }
        prevCards.setBackground(Color.GREEN);

        secondLine.add(player2, BorderLayout.WEST);
        secondLine.add(prevCards, BorderLayout.CENTER);
        secondLine.add(player3, BorderLayout.EAST);
        secondLine.setBackground(Color.YELLOW);

        // Third line
        
        btns.add(playBtn);
        btns.add(skipBtn);
        
        // Load main player cards
        for(Player player: BigTwo.listPlayers){
            if(player.getId() == 0){
                for(int i = 0; i< player.getCardsAvailable().size(); i++){
                    Card card = player.getCardsAvailable().get(i);

                    ImageIcon cardImg = new ImageIcon(cardToDir(card));
                    JButton cardBtn = new JButton();

                    cardBtn.setIcon(cardImg);
                    cardBtn.setBorderPainted(false); 
                    cardBtn.setContentAreaFilled(false); 
                    cardBtn.setActionCommand(Integer.toString(i));
                    cardBtn.addActionListener(e -> {
                        System.out.println(cardBtn.getActionCommand());   
                    });

                    playerCards.add(cardBtn);
                }
            }
        }
        playerCards.setBackground(Color.BLUE);

        thirdLine.add(playerCards, BorderLayout.SOUTH);
        thirdLine.add(btns, BorderLayout.NORTH);

        // Add the the main
        mainPanel.add(firstLine, BorderLayout.NORTH);
        mainPanel.add(secondLine, BorderLayout.CENTER);
        mainPanel.add(thirdLine, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight-100);
        frame.setVisible(true);
    }
}
