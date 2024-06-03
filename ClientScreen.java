import java.io.*;
import java.net.Socket;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;
import java.awt.Font;
import java.net.URL;


public class ClientScreen extends JPanel implements ActionListener, MouseListener{
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private JTextField input;
    private JTextArea instructions;
    private String username;
    private JScrollPane scrollPane;
    private Board board;
    private int playerNum;
    private Color playerColor;
    private boolean startMenu = true;
    private boolean loseScreen = false;
    private boolean winScreen = false;
    private Color bgColor;
    private Random rand;
    private JButton buy, leave, forfeit, restart;
    private boolean move = true;
    private int dice1, dice2;
    private JLabel cardDirection;
    private Font diceFont = new Font("BlackNorthDemo", Font.PLAIN, 20);
    
    
    public ClientScreen() {

        bgColor = new Color(202, 232, 224);
        dice1 = 0;
        dice2 = 0;

        instructions = new JTextArea();
        instructions.setBounds(340, 360, 320, 320);
        //set the background of instructions to be transparent
        instructions.setOpaque(false);
        //add instructions
        instructions.setText("Welcome to Mountain View StreetMarket! \n\n" +
        "To start the game, click on one of the four colored squares. \n\n" +
        "To roll the dice, click anywhere on the screen. \n\n" +
        "To buy a property, click the buy button. \n\n" +
        "To leave a property, click the leave button. \n\n" +
        "To forfeit the game, click the forfeit button. \n\n");
        add(instructions);
        instructions.setVisible(false);
        
        buy = new JButton("Buy");
        buy.setBounds(0, 0, 100, 50);
        buy.addActionListener(this);
        add(buy);
        
        leave = new JButton("Leave");
        leave.setBounds(0, 50, 100, 50);
        leave.addActionListener(this);
        add(leave);

        forfeit = new JButton("Forfeit");
        forfeit.setBounds(800, 50, 100, 50);
        forfeit.addActionListener(this);
        add(forfeit);

        cardDirection = new JLabel("Card Direction");
        cardDirection.setBounds(0, 100, 100, 50);
        add(cardDirection);
        cardDirection.setVisible(false);

        //code restart button
        restart = new JButton("Restart");
        restart.setBounds(800, 0, 100, 50);
        add(restart);
        restart.setVisible(false);


        buy.setVisible(false);
        leave.setVisible(false);
        forfeit.setVisible(false);
        
		setLayout(null);
		setFocusable(true);
        addMouseListener(this);
    }
    public void connect(){
        String hostName = "192.168.1.15"; /*
        Anirudh's Computer: 192.168.5.114  for bens wifi, 10.210.114.146 for school wifi, 192.168.1.15 for anirudhs wifi
        */
		int portNumber = 1024;

        try {
			Socket serverSocket = new Socket(hostName, portNumber);
			outObj = new ObjectOutputStream(serverSocket.getOutputStream());
			inObj = new ObjectInputStream(serverSocket.getInputStream());
            try{
				while(true){
					Object o = inObj.readObject();
					if(o instanceof Board){
						board = (Board) o;
                        // System.out.println("Board received");
                        repaint();
					}
					//System.out.println(board);
				}
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
		}catch(IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

    public void paintComponent(Graphics g) {
        //go thorugh all players and check if they have forfeited
        try{winScreen = board.playerHasWon(playerNum);
        System.out.println("winScreen: " + winScreen);
        System.out.println("player Won: " + board.playerHasWon(playerNum));}
        catch(Exception e){}
        if(startMenu){
            //draw 4 squares with different colors
            g.setColor(Color.RED);
            g.fillRect(0, 0, 500, 500);
            g.setColor(Color.BLUE);
            g.fillRect(0, 500, 500, 500);
            g.setColor(Color.GREEN);
            g.fillRect(500, 0, 500, 500);
            g.setColor(Color.YELLOW);
            g.fillRect(500, 500, 500, 500);
        }
        else if(winScreen){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 1000);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 180));
            g.drawString("You Win!", 0, 550);
            //set all buttons to false
            buy.setVisible(false);
            leave.setVisible(false);
            forfeit.setVisible(false);
        }
        else if(loseScreen){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 1000);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 180));
            g.drawString("Game Over", 0, 550);
        }
        else{
            try{
                g.setColor(bgColor);
                g.fillRect(0, 0, 1000, 1000);
                drawBoard(g);
                // System.out.println("Board drawn");
                g.setColor(Color.BLACK);
                g.drawString(board.getPlayer(playerNum).getMoney() + "", 0, 250);
                g.drawString(board.getPlayerTurn() + "", 500, 250);
                //draw 2 dice
                g.setColor(Color.WHITE);
                g.fillRect(430, 50, 60, 60);
                g.fillRect(510, 50, 60, 60);
                g.setColor(Color.BLACK);
                g.drawRect(430, 50, 60, 60);
                g.drawRect(510, 50, 60, 60);
                g.setFont(diceFont);
                g.drawString(dice1 + "", 450, 80);
                g.drawString(dice2 + "", 530, 80);

                
            }catch(Exception e){
                System.out.println("board not initialized");
            }
        }

        
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buy){
            if(board.getPlayer(playerNum).buyStreet(board.getStreets().get(board.getPlayer(playerNum).getPosition()))){
            }
            leave.setVisible(false);
            buy.setVisible(false);
            board.incrementTurn();
            try{
                outObj.reset();
                outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            move = true;
            board.incrementTurn();
            try {
                URL url = this.getClass().getClassLoader().getResource("buy.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == leave){
            leave.setVisible(false);
            buy.setVisible(false);
            move = true;
        }
        if(e.getSource() == forfeit){
            loseScreen = true;
            board.incrementTurn();
            board.getPlayer(playerNum).setForfeited(true);
            try{
                outObj.reset();
                outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            forfeit.setVisible(false);
            instructions.setVisible(false);
            try {
                URL url = this.getClass().getClassLoader().getResource("forfeit.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        cardDirection.setVisible(false);
        // if(!startMenu) System.out.println("board.playerTurn() == playerNum: " + (board.getPlayerTurn() == playerNum) + " board.getPlayerTurn(): " + board.getPlayerTurn() + " playerNum: " + playerNum);
        if(startMenu){
            if(e.getX() <= 500 && e.getY() <= 500){
                playerNum = 1;
                playerColor = Color.RED;
                board.addPlayer(new Player(playerNum, playerColor));
            }
            else if(e.getX() <= 500 && e.getY() >= 500){
                playerNum = 2;
                playerColor = Color.BLUE;
                board.addPlayer(new Player(playerNum, playerColor));
            }
            else if(e.getX() >= 500 && e.getY() <= 500){
                playerNum = 3;
                playerColor = Color.GREEN;
                board.addPlayer(new Player(playerNum, playerColor));
            }
            else if(e.getX() >= 500 && e.getY() >= 500){
                playerNum = 4;
                playerColor = Color.YELLOW;
                board.addPlayer(new Player(playerNum, playerColor));
            }
            startMenu = false;
            forfeit.setVisible(true);
            instructions.setVisible(true);
            try{
                outObj.reset();
                outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            //repaint();
        }   
        else if(!buy.isVisible() && !leave.isVisible() && move && (board.getPlayerTurn() == playerNum)){
            rollDice();
            board.movePlayer(playerNum, dice1 + dice2);
            try{
                outObj.reset();
                outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            //if player lands on street and can buy it
            if((board.getStreets().get(board.getPlayer(playerNum).getPosition()).function <= 2) && (board.getPlayer(playerNum).canBuy(board.getStreets().get(board.getPlayer(playerNum).getPosition())))){
                // System.out.println("Buy or leave!");
                buy.setVisible(true);
                leave.setVisible(true);
                move = false;
            }
            //if player lands on street and needs to pay rent
            else if((board.getStreets().get(board.getPlayer(playerNum).getPosition()).function <= 2) && (board.getStreets().get(board.getPlayer(playerNum).getPosition()).needsRent(board.getPlayer(playerNum)))){
                System.out.println("Pay rent");
                board.getPlayer(playerNum).subtractMoney(board.getStreets().get(board.getPlayer(playerNum).getPosition()).getRent());
                board.getStreets().get(board.getPlayer(playerNum).getPosition()).getOwner().addMoney(board.getStreets().get(board.getPlayer(playerNum).getPosition()).getRent());
            }
            //if player lands on chance or community chest
            else if(board.getStreets().get(board.getPlayer(playerNum).getPosition()).function == 4){
                System.out.println("Chance");
                Card c = board.getChance().pop();
                // System.out.println(c);
                // System.out.println(c.getDirection());
                int action = c.action();
                if(action < 6){
                    board.getChance().add(c);
                }
                switch (action){
                    case 1:
                        board.getPlayer(playerNum).setPosition(c.getActionNum());
                        if(c.getActionNum() == 0){
                            board.getPlayer(playerNum).addMoney(200);
                        }
                        break;
                    case 2:
                        board.getPlayer(playerNum).move(c.getActionNum());
                        break;
                    case 3:
                        board.getPlayer(playerNum).addMoney(c.getActionNum());
                        break;
                    case 4:
                        board.getPlayer(playerNum).subtractMoney(c.getActionNum());
                        break;
                    case 5:
                        board.getPlayer(playerNum).sendToJail();
                        break;
                }
                cardDirection.setText(c.getDirection());
                cardDirection.setVisible(true);

            }
            else if(board.getStreets().get(board.getPlayer(playerNum).getPosition()).function == 5){
                System.out.println("Community Chest");
                Card c = board.getCommunityChest().pop();
                int action = c.action();
                if(action < 6){
                    board.getCommunityChest().add(c);
                }
                switch (action){
                    case 1:
                        board.getPlayer(playerNum).setPosition(c.getActionNum());
                        if(c.getActionNum() == 0){
                            board.getPlayer(playerNum).addMoney(200);
                        }
                        break;
                    case 2:
                        board.getPlayer(playerNum).move(c.getActionNum());
                        break;
                    case 3:
                        board.getPlayer(playerNum).addMoney(c.getActionNum());
                        break;
                    case 4:
                        board.getPlayer(playerNum).subtractMoney(c.getActionNum());
                        break;
                    case 5:
                        board.getPlayer(playerNum).sendToJail();
                        break;
                }
                cardDirection.setText(c.getDirection());
                cardDirection.setVisible(true);

            }
            else if(board.getStreets().get(board.getPlayer(playerNum).getPosition()).function == 9){
                board.getPlayer(playerNum).sendToJail();
            }
            if(move){
                board.incrementTurn();
            }
            //System.out.println("Turn: " + board.getTurn());
            try{
                outObj.reset();
                outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}

        }
        //System.out.println("board.getPlayerTurn() == playerNum: " + (board.getPlayerTurn() == playerNum) + " board.playerTurn(): " + board.getPlayerTurn() + " playerNum: " + playerNum);
        
    }

    public void rollDice(){
        rand = new Random();
        dice1 = rand.nextInt(6)+1;
        dice2 = rand.nextInt(6)+1;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public Dimension getPreferredSize() {
		return new Dimension(1000, 1000);
	}

    public void drawBoard(Graphics g){
        ArrayList <Street> streets = board.getStreets();
        g.setColor(bgColor);
        // System.out.println("Drawing board");
        g.fillRect(110, 110, 780, 780);
        g.setColor(Color.BLACK);
        g.drawRect(110, 110, 780, 780);
        int x = 770;
        int y = 770;
        for (int i = 0; i < 10; i++){
            streets.get(i).drawMe(g, x, y, 0);
            /*Player p = board.playerAtPos(i);
            if(p != null){
                System.out.println("Player at pos " + i);
                p.drawMe(x + 20, y + 50, g);
            }*/
            x -= 60;
        }
        x += 60;
        for(int i = 10; i < 20; i++){
            streets.get(i).drawMe(g, x, y, 90);
            // Player p = board.playerAtPos(i);
            // if(p != null){
            //     System.out.println("Player at pos " + i);
            //     p.drawMe(x - 50, y + 20, g);
            // }
            y -= 60;
        }
        y += 60;
        for(int i = 20; i < 30; i++){
            streets.get(i).drawMe(g, x, y, 180);
            x += 60;
        }
        x-=60;
        for(int i = 30; i < 40; i++){
            streets.get(i).drawMe(g, x, y, 270);
            y += 60;
        }
        x = 770;
        y = 770;
        
        for (int i = 0; i < 10; i++){
            Player p = board.playerAtPos(i);
            if(p != null){
                // System.out.println("Player at pos " + i);
                //System.out.println(board.playerAtPos(i).getMoney());
                p.drawMe(x + 20, y + 50, g);
                if(p.canBuy(streets.get(i)) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }
            }
            x -= 60;
        }
        x += 60;
        for(int i = 10; i < 20; i++){
    
            Player p = board.playerAtPos(i);
            if(p != null){
                // System.out.println("Player at pos " + i);
                //System.out.println(board.playerAtPos(i).getMoney());
                p.drawMe(x - 50, y + 20, g);
                if(p.canBuy(streets.get(i)) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }
            }
            y -= 60;
        }
        x-= 60;
        y += 60;
        for(int i = 20; i < 30; i++){
            Player p = board.playerAtPos(i);
            if(p != null){
                // System.out.println("Player at pos " + i);
                //System.out.println(board.playerAtPos(i).getMoney());
                p.drawMe(x + 20, y - 50, g);
                if(p.canBuy(streets.get(i)) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }
            }
            x += 60;
        }
        y-=60;
        for(int i = 30; i < 40; i++){
            Player p = board.playerAtPos(i);
            if(p != null){
                // System.out.println("Player at pos " + i);
                //System.out.println(board.playerAtPos(i).getMoney());
                p.drawMe(x + 50, y + 20, g);
                if(p.canBuy(streets.get(i)) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }
            }
            y += 60;
        }

        
    }
    public void resetBoard(){
        ArrayList<Street> streetList = new ArrayList<Street>();
		streetList.add(new Street("Go", Street.Function.GO.getInt()));
		streetList.add(new Street("Lubich", 2, 60, 40, 50, 50, 0, false, "Brown", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Brower Avenue", 4, 60, 40, 50, 50, 0, false, "Brown", Street.Function.STREET.getInt()));
		streetList.add(new Street("Income Tax", Street.Function.TAX.getInt()));
		streetList.add(new Street("CA-237", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Truman Ave", 6, 100, 50, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Bryant Avenue", 6, 100, 50, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Levin Avenue", 8, 120, 60, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Jail", Street.Function.JAIL.getInt()));
		streetList.add(new Street("Church St", 10, 140, 70, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("Electric Company", 12, 150, 75, 0, 0, 0, false, "Utility", Street.Function.UTILITY.getInt()));
		streetList.add(new Street("Cuesta Drive", 10, 140, 70, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("Covington Road", 12, 160, 80, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("US-101", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Middlefield Avenue", 14, 180, 90, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Whisman Road", 14, 180, 90, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("San Antonio Road", 16, 200, 100, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("Free Parking", Street.Function.FREE_PARKING.getInt()));
		streetList.add(new Street("Foothill Expwy", 18, 220, 110, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Central Expwy", 18, 220, 110, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("Moffet Boulevard", 20, 240, 120, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("CA-85", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Grant Road", 22, 260, 130, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Evelyn Ave", 22, 260, 130, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Water Works", 12, 150, 75, 0, 0, 0, false, "Utility", Street.Function.UTILITY.getInt()));
		streetList.add(new Street("Sleeper Avenue", 24, 280, 140, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Go To Jail", Street.Function.GO_TO_JAIL.getInt()));
		streetList.add(new Street("California Street", 26, 300, 150, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("Miramonte Avenue", 26, 300, 150, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Shoreline Boulevard", 28, 320, 160, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("I-280", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Castro St", 35, 350, 175, 200, 200, 0, false, "Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Luxury Tax", Street.Function.TAX.getInt()));
		streetList.add(new Street("El Camino Real", 50, 400, 200, 200, 200, 0, false, "Blue", Street.Function.STREET.getInt()));
		board = new Board(streetList);
	}

}