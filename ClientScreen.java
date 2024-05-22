import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.MouseListener;


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

    
    public ClientScreen() {
        bgColor = new Color(202, 232, 224);
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
                    System.out.println(o);
					if(o instanceof Board){
						board = (Board) o;
                        System.out.println("Board received");
                        repaint();
					}
					System.out.println(board);
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
            }catch(Exception e){
                System.out.println("board not initialized");
            }
        }
        
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + ", Y: " + e.getY());
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
            repaint();
        }else{
            board.movePlayer(playerNum, 1);
            try{outObj.writeObject(board);}
            catch(IOException ex){ex.printStackTrace();}
            repaint();
        }
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
            Player p = board.playerAtPos(i);
            if(p != null){
                System.out.println("Player at pos " + i);
                p.drawMe(x + 20, y + 50, g);
                System.out.println("Player drawn at x: " + (x + 20) + ", y: " + (y + 50));
            }
            x -= 60;
        }
        x += 60;
        for(int i = 10; i < 20; i++){
            streets.get(i).drawMe(g, x, y, 90);
            Player p = board.playerAtPos(i);
            if(p != null){
                System.out.println("Player at pos " + i);
                p.drawMe(x - 50, y + 20, g);
                System.out.println("Player drawn at x: " + (x -50) + ", y: " + (y + 20));
            }
            y -= 60;
        }
        y += 60;
        for(int i = 20; i < 30; i++){
            streets.get(i).drawMe(g, x, y, 180);
            Player p = board.playerAtPos(i);
            if(p != null){
                System.out.println("Player at pos " + i);
                p.drawMe(x + 20, y - 10, g);
                System.out.println("Player drawn at x: " + (x + 20) + ", y: " + (y - 50));
            }
            x += 60;
        }
        x-=60;
        for(int i = 30; i < 40; i++){
            streets.get(i).drawMe(g, x, y, 270);
            Player p = board.playerAtPos(i);
            if(p != null){
                System.out.println("Player at pos " + i);
                p.drawMe(x + 50, y + 20, g);
                System.out.println("Player drawn at x: " + (x + 50) + ", y: " + (y + 20));
            }
            y += 60;
        }
        
    }

}