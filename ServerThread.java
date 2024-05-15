import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{	
	private Socket clientSocket;
    private Manager manager;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    

	public ServerThread(Socket clientSocket, Manager manager){
		this.clientSocket = clientSocket;
        this.manager = manager;
        
	}
    
	public void run(){
		System.out.println(Thread.currentThread().getName() + ": connection opened.");
		try{
			outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			inObj = new ObjectInputStream(clientSocket.getInputStream());
            manager.broadcastMessage(manager.getBoard());
			while(true){
				Board o = (Board) inObj.readObject();
				manager.broadcastMessage(o);
			}
			
		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to " + clientSocket);
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.err.println("Class does not exist" + e);
		}
        
	}

    public void send(Board board){
        try{
			if( outObj != null ){
				outObj.reset();
				outObj.writeObject(board);
			}
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}