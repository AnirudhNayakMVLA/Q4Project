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
    
    public ClientScreen() {
		setLayout(null);
		setFocusable(true);
        addMouseListener(this);
    }

    public void connect(){
        String hostName = "localhost"; 
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
        g.drawString("Welcome to the game", 10, 10);
        try{
            drawBoard(g);
            System.out.println("Board drawn");
        }catch(Exception e){
            System.out.println("board not initialized");
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + ", Y: " + e.getY());
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
        //g.setColor(bgColor);
        System.out.println("Drawing board");
        g.drawRect(110, 110, 780, 780);
        int x = 770;
        int y = 770;
        for (int i = 0; i < 10; i++){
            streets.get(i).drawMe(g, x, y, 0);
            if(i == 0){
                x -= 60;
            }
            else{
                x -= 60;
            }
        }
        x += 60;
        for(int i = 10; i < 20; i++){
            streets.get(i).drawMe(g, x, y, 90);
            if(i == 10){
                y -= 60;
            }
            else{
                y -= 60;
            }
        }
        y += 60;
        for(int i = 20; i < 30; i++){
            streets.get(i).drawMe(g, x, y, 180);
            if(i == 20){
                x += 60;
            }
            else{
                x += 60;
            }
        }
        x-=60;
        for(int i = 30; i < 40; i++){
            streets.get(i).drawMe(g, x, y, 270);
            if(i == 30){
                y += 60;
            }
            else{
                y += 60;
            }
        }
    }

}