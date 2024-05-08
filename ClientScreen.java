import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;


public class ClientScreen extends JPanel implements ActionListener{
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
    }

    public void connect() throws IOException{
        String hostName = "localhost"; 
		int portNumber = 1024;

        try {
			Socket serverSocket = new Socket(hostName, portNumber);

			outObj = new ObjectOutputStream(serverSocket.getOutputStream());
			inObj = new ObjectInputStream(serverSocket.getInputStream());

			Thread t = new Thread(new Receiver());
			t.start();
            try{
                Object o = inObj.readObject();
                if(o instanceof Board){
                    board = (Board) o;
                }
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
		}catch(IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

    private class Receiver implements Runnable {
        public void run() {
            while(true) {
                try {
                    Object o = inObj.readObject();
                    if (o instanceof String){
                    }
                    else{
                        System.out.println("Invalid object type");
                    }
                } catch(Exception e) {
                    System.err.println("Couldn't get I/O for the connection");
                    break;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try{
        board.drawBoard(g);
        }catch(Exception e){
            System.out.println("board not initialized");
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    public Dimension getPreferredSize() {
		return new Dimension(1000, 1000);
	}

}