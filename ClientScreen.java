import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;


public class ClientScreen extends JPanel implements ActionListener, MouseListener{
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private JTextField input;
    private JTextArea textArea;
    private String username;
    private JScrollPane scrollPane;
    private Board board;
    private int playerNum;
    private Color playerColor;
    private boolean startMenu = true;
    private Color bgColor;
    private Random rand;
    private JButton buy, leave;
    private boolean move = true;
    private int dice1, dice2;
    private JLabel cardDirection;
    
    
    public ClientScreen() {

        bgColor = new Color(202, 232, 224);
        dice1 = 0;
        dice2 = 0;
        
        buy = new JButton("Buy");
        buy.setBounds(0, 0, 100, 50);
        buy.addActionListener(this);
        add(buy);
        
        leave = new JButton("Leave");
        leave.setBounds(0, 50, 100, 50);
        leave.addActionListener(this);
        add(leave);

        cardDirection = new JLabel("Card Direction");
        cardDirection.setBounds(0, 100, 100, 50);
        add(cardDirection);
        cardDirection.setVisible(false);


        buy.setVisible(false);
        leave.setVisible(false);
        
		setLayout(null);
		setFocusable(true);
        addMouseListener(this);
    }
    public void connect(){
        String hostName = "10.210.116.96"; /*
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
                        System.out.println("Board received");
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
        else{
            try{
                g.setColor(bgColor);
                g.fillRect(0, 0, 1000, 1000);
                drawBoard(g);
                System.out.println("Board drawn");
                g.setColor(Color.BLACK);
                g.drawString(board.getPlayer(playerNum).getMoney() + "", 0, 250);
            }catch(Exception e){
                System.out.println("board not initialized");
            }
        }
        
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buy){
            if(board.getPlayer(playerNum).buyStreet(board.getStreets().get(board.getPlayer(playerNum).getPosition()))){
                System.out.println("Bought street");
            }
            leave.setVisible(false);
            buy.setVisible(false);
            try{outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            move = true;
        }
        if(e.getSource() == leave){
            System.out.println("fuck");
            leave.setVisible(false);
            buy.setVisible(false);
            move = true;
        }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        cardDirection.setVisible(false);
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
            try{outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            //repaint();
        }else if(buy.isVisible() == false && leave.isVisible() == false && move){
            rollDice();
            board.movePlayer(playerNum, dice1 + dice2);
            try{outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            if(board.getStreets().get(board.getPlayer(playerNum).getPosition()).function <= 2){
                System.out.println("Buy or leave!");
                buy.setVisible(true);
                leave.setVisible(true);
                move = false;
            }
            //if player lands on chance 
            if(board.getStreets().get(board.getPlayer(playerNum).getPosition()).function == 3){
                System.out.println("Chance");
                Card c = board.getChance().pop();
                int action = c.action();
                if(action < 6){
                    board.getChance().add(c);
                }
                switch (action){
                    case 1:
                        board.getPlayer(playerNum).setPosition(c.getActionNum());
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
                try{outObj.writeObject(board);}
                catch(IOException ex){ex.printStackTrace();}
                cardDirection.setText(c.getDirection());
                cardDirection.setVisible(true);

            }
            
            //repaint();
        }else{
            System.out.println("Buy or leave!");
        }
        
    }

    public void rollDice(){
        rand = new Random();
        dice1 = rand.nextInt(6)+1;
        dice2 = rand.nextInt(6)+1;
        System.out.println("Dice 1: " + dice1 + " Dice 2: " + dice2);
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
        System.out.println("Drawing board");
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
                if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() == null) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }else if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() != board.getPlayer(playerNum)) && !move){
                    p.payPlayer(streets.get(i).getOwner(), streets.get(i).getRent());
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
                if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() == null && !move)){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }else if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() != board.getPlayer(playerNum)) && !move){
                    p.payPlayer(streets.get(i).getOwner(), streets.get(i).getRent());
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
                if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() == null) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }else if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() != board.getPlayer(playerNum)) && !move){
                    p.payPlayer(streets.get(i).getOwner(), streets.get(i).getRent());
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
                if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() == null) && !move){
                   buy.setVisible(true);
                   leave.setVisible(true);
                }else if((streets.get(i).function <= 2 ) && (streets.get(i).getOwner() != board.getPlayer(playerNum)) && !move){
                    p.payPlayer(streets.get(i).getOwner(), streets.get(i).getRent());
                }
            }
            y += 60;
        }
        
    }

}