import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RollingDiceGui extends JFrame {

    JFrame frame = new JFrame("Roll The Dice");
    public RollingDiceGui() {
        addGuiComponent();

    }

    private void addGuiComponent() {

//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(null);
        Random rand = new Random();


        //Background
        ImageIcon img = new ImageIcon(this.getClass().getResource("resource/img.jpg"));

        JLabel background = new JLabel(img);
        background.setSize(50, 50);

        //Title
        JTextArea text = new JTextArea("Start rolling the dices");
        text.setBounds(625, 200, 225, 30);
        text.setEditable(false);
        text.setFont(new Font("Times New Roman", Font.BOLD, 24));
        text.setBackground(new Color(0, 0, 0, 50));
        text.setForeground(Color.cyan);
        background.add(text);

        //Dices
        JLabel diceOneImg = ImageService.loadImage("/resource/dice1.png");
        diceOneImg.setBounds(450, 300, 200, 200);
        background.add(diceOneImg);

        JLabel diceTwoImg = ImageService.loadImage("/resource/dice1.png");
        diceTwoImg.setBounds(850, 300, 200, 200);
        background.add(diceTwoImg);

        //Roll Button
        JButton rollButton = new JButton("Roll");
        rollButton.setBounds(650, 550, 200, 50);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollButton.setEnabled(false);

                long startTime = System.currentTimeMillis();
                Thread rollThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long endtime = System.currentTimeMillis();
                        try {
                            while ((endtime - startTime) / 1000F < 3) {
                                int diceOne = rand.nextInt(1, 7);
                                int diceTwo = rand.nextInt(1, 7);

                                ImageService.updateImage(diceOneImg, "resource/dice" + diceOne + ".png");
                                ImageService.updateImage(diceTwoImg, "resource/dice" + diceTwo + ".png");

                                repaint();
                                revalidate();

                                Thread.sleep(60);

                                endtime = System.currentTimeMillis();

                            }

                            rollButton.setEnabled(true);

                        } catch (Exception e) {
                            System.out.println("Threading Error: " + e);
                        }
                    }
                });
                rollThread.start();
            }
        });
        background.add(rollButton);



        frame.add(background);
        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
