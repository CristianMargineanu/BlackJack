import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BlackJackGui extends JFrame {

    ArrayList<Cards> deck;
    Random random = new Random();

    //Dearler

    Cards hiddenCard, cards;
    ArrayList<Cards> dealerHand, playerHand;
    int dealerSum, dealerAceCount, playerSum, playerAceCount, boardWidth = 800, boardHeight = 800, startHit = 1, firstA = 0;
    JFrame frame = new JFrame("BlackJack");
    ImageIcon img = new ImageIcon(this.getClass().getResource("cards/Backgr.jpg"));
    JPanel gamePanel = new JPanel();
    JLabel player1Card = CardsService.loadImage("cards/Card.jpg"),
            player2Card = CardsService.loadImage("cards/Card.jpg"),
            dealer1Card = CardsService.loadImage("cards/Card.jpg"),
            dealer2Card = CardsService.loadImage("cards/Card.jpg"),
            player3Card = CardsService.loadImage("cards/Card.jpg"),
            player4Card = CardsService.loadImage("cards/Card.jpg"),
            player5Card = CardsService.loadImage("cards/Card.jpg"),
            dealer3Card = CardsService.loadImage("cards/Card.jpg"),
            dealer4Card = CardsService.loadImage("cards/Card.jpg"),
            dealer5Card = CardsService.loadImage("cards/Card.jpg");

    JButton hitCard = new JButton("Hit");
    JButton stay = new JButton("Stay");

    JButton restartBtn = new JButton("Restart");

    JLabel lose = new JLabel(new ImageIcon(this.getClass().getResource("cards/GameOver.jpg")));
    JLabel win = new JLabel(new ImageIcon(this.getClass().getResource("cards/youWin.jpg")));
    JLabel equals = new JLabel(new ImageIcon(this.getClass().getResource("cards/equals.jpg")));

    public BlackJackGui() {
        startGame();

        gamePanel.add(hitCard);
        hitCard.setVisible(false);
        gamePanel.add(stay);
        stay.setVisible(false);
        gamePanel.add(restartBtn);
        restartBtn.setVisible(false);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(9, 82, 37));


        //Background
        JLabel background = new JLabel(img);
        background.setSize(10, 10);


        lose.setBounds(300, 30, 200, 150);
        lose.setVisible(false);
        frame.add(lose);

        win.setBounds(300, 30, 200, 150);
        win.setVisible(false);
        frame.add(win);

        equals.setBounds(100, 30, 650, 150);
        equals.setVisible(false);
        frame.add(equals);


        //Cards
        dealer1Card.setBounds(75, 225, 97, 149);
        gamePanel.add(dealer1Card);
        dealer2Card.setBounds(200, 225, 97, 149);
        gamePanel.add(dealer2Card);
        player1Card.setBounds(75, 400, 97, 149);
        gamePanel.add(player1Card);
        player2Card.setBounds(200, 400, 97, 149);
        gamePanel.add(player2Card);
        gamePanel.add(player3Card);
        gamePanel.add(player4Card);
        gamePanel.add(player5Card);
        gamePanel.add(dealer3Card);
        gamePanel.add(dealer4Card);
        gamePanel.add(dealer5Card);


        //Start Button
        JButton startButton = new JButton("Start");
        startButton.setBounds(325, 625, 100, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                startButton.setVisible(false);
                hitCard.setVisible(true);
                stay.setVisible(true);

                Thread shuffleThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CardsService.updateImage(dealer2Card, "cards/" + dealerHand.get(0) + ".jpg");
                        CardsService.updateImage(player1Card, "cards/" + playerHand.get(0) + ".jpg");
                        CardsService.updateImage(player2Card, "cards/" + playerHand.get(1) + ".jpg");
                        if (playerSum == 21 && dealerSum == 21) {
                            hitCard.setVisible(false);
                            stay.setVisible(false);
                            equals.setVisible(true);
                            restartGame();
                        }else if (playerSum == 21) {
                            hitCard.setVisible(false);
                            stay.setVisible(false);
                            win.setVisible(true);
                            restartGame();
                        }else if (dealerSum == 21) {
                            CardsService.updateImage(dealer1Card, "cards/" + hiddenCard + ".jpg");
                            hitCard.setVisible(false);
                            stay.setVisible(false);
                            lose.setVisible(true);
                            restartGame();
                        }
                        repaint();
                        revalidate();
                        duringGame();
                    }
                });
                shuffleThread.start();

            }
        });
        gamePanel.add(startButton);

        gamePanel.add(background);
        frame.add(gamePanel);

        frame.setSize(boardWidth, boardHeight);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startGame() {
        //deck
        buildDeck();
        shuffleDeck();

        //dealer
        dealerHand = new ArrayList<Cards>();
        dealerAceCount = 0;
        dealerSum = 0;

        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;
        if (hiddenCard.isAce())
            firstA = 1;

        cards = deck.remove(deck.size() - 1);
        dealerSum += cards.getValue();
        dealerAceCount += cards.isAce() ? 1 : 0;
        if (dealerSum > 21 && cards.isAce())
            dealerSum -= 10;
        dealerHand.add(cards);

//        System.out.println("Dealer");
//        System.out.println(hiddenCard);
//        System.out.println(dealerHand);
//        System.out.println(dealerSum);
//        System.out.println(dealerAceCount);

        //player
        playerHand = new ArrayList<Cards>();
        playerAceCount = 0;
        playerSum = 0;


        for (int i = 0; i < 2; i++) {

            cards = deck.remove(deck.size() - 1);
            playerSum += cards.getValue();
            playerAceCount += cards.isAce() ? 1 : 0;
            if (playerSum > 21 && cards.isAce())
                playerSum -= 10;
            playerHand.add(cards);

        }


//        System.out.println("Player");
//        System.out.println(playerHand);
//        System.out.println(playerSum);
//        System.out.println(playerAceCount);
    }

    private void duringGame() {

        hitCard.setBounds(225, 625, 100, 50);
        hitCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread hitThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cards = deck.remove(deck.size() - 1);
                        playerSum += cards.getValue();
                        playerAceCount += cards.isAce() ? 1 : 0;
                        if (playerSum > 21 && cards.isAce()) {
                            playerSum -= 10;
                        }
                        playerHand.add(cards);
                        startHit++;
                        switch (startHit) {
                            case 2: {
                                CardsService.updateImage(player3Card, "cards/" + playerHand.get(startHit) + ".jpg");
                                player3Card.setBounds(325, 400, 97, 149);
                                break;
                            }
                            case 3: {
                                CardsService.updateImage(player4Card, "cards/" + playerHand.get(startHit) + ".jpg");
                                player4Card.setBounds(450, 400, 97, 149);
                                break;
                            }
                            case 4: {
                                CardsService.updateImage(player5Card, "cards/" + playerHand.get(startHit) + ".jpg");
                                player5Card.setBounds(575, 400, 97, 149);
                                break;
                            }
                        }
                        repaint();
                        revalidate();
                        if (playerSum > 21) {
                            hitCard.setVisible(false);
                            stay.setVisible(false);
                            lose.setVisible(true);
                            restartGame();
                        } else if (playerSum == 21) {
                            hitCard.setVisible(false);
                            stay.setVisible(false);
                            dealerDeck();
                        }
                    }
                });
                hitThread.start();
            }
        });


        stay.setBounds(375, 625, 100, 50);
        stay.addActionListener(e -> {
            Thread hitDealerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    hitCard.setVisible(false);
                    stay.setVisible(false);
                    dealerDeck();
                }
            });
            hitDealerThread.start();

        });


    }

    private void restartGame() {
        restartBtn.setBounds(325, 625, 100, 50);
        restartBtn.setVisible(true);
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread restartThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        restartBtn.setVisible(false);
                        frame.dispose();
                        new BlackJackGui();
                    }
                });
                restartThread.start();
            }
        });
    }

    private void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Cards current = deck.get(i);
            Cards randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, current);

        }
//        System.out.println("After Shuffle");
//        System.out.println(deck);
    }

    private void buildDeck() {
        deck = new ArrayList<Cards>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"Rosu", "Romb", "Negru", "Trefla"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Cards cards = new Cards(values[j], types[i]);
                deck.add(cards);
            }
        }

//        System.out.println("Build Deck:");
//        System.out.println(deck);
    }

    private void dealerDeck() {
        CardsService.updateImage(dealer1Card, "cards/" + hiddenCard + ".jpg");
        startHit = 0;
        while (dealerSum < 17) {
            cards = deck.remove(deck.size() - 1);
            dealerSum += cards.getValue();
            if (dealerSum > 21 && dealerHand.get(0).isAce() && firstA != 0){
                dealerSum -= 10;
                firstA--;
            }

            if (dealerSum > 21 && cards.isAce()) {
                dealerSum -= 10;
            }
            dealerAceCount += cards.isAce() ? 1 : 0;
            dealerHand.add(cards);
            startHit++;
            switch (startHit) {
                case 1: {
                    CardsService.updateImage(dealer3Card, "cards/" + dealerHand.get(startHit) + ".jpg");
                    dealer3Card.setBounds(325, 225, 97, 149);
                    break;
                }
                case 2: {
                    CardsService.updateImage(dealer4Card, "cards/" + dealerHand.get(startHit) + ".jpg");
                    dealer4Card.setBounds(450, 225, 97, 149);
                    break;
                }
                case 3: {
                    CardsService.updateImage(dealer5Card, "cards/" + dealerHand.get(startHit) + ".jpg");
                    player5Card.setBounds(575, 225, 97, 149);
                    break;
                }
            }
        }
        repaint();
        revalidate();
        if (dealerSum > 21) {
            win.setVisible(true);
        } else if (dealerSum > playerSum) {
            lose.setVisible(true);
        } else if (dealerSum < playerSum) {
            win.setVisible(true);
        } else equals.setVisible(true);

        hitCard.setVisible(false);
        stay.setVisible(false);
        restartGame();
    }
}
