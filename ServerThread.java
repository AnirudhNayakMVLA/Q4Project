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
        try{
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
            send(manager.getBoard());
        }catch(SocketException e){
            e.printStackTrace();
            System.out.println("socket exception???/");
        }
        catch(IOException e){
            System.err.println("Couldn't get I/O for the connection to " + clientSocket);
            e.printStackTrace();
        }
	}

	public void run(){
		System.out.println(Thread.currentThread().getName() + ": connection opened.");
		while(true){
            try{
                Object o = inObj.readObject();
                sendMessage((Board)o);
                //Clears and close the output stream.
            } catch (IOException ex){
                System.out.println("Error listening for a connection");
                System.out.println(ex.getMessage());
            }catch(ClassNotFoundException e){
                System.err.println("Class does not exist" + e);
            }
        }
	}
    
    public void sendMessage(Board board){
        manager.broadcastMessage(board);
    }

    public void send(Board board){
        try{
            outObj.writeObject(board);

        }catch(Exception e){

        }
    }
}