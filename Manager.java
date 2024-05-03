import java.util.ArrayList;

public class Manager {
    private ArrayList<ServerThread> threads;

    public Manager() {
        threads = new ArrayList<ServerThread>();
    }
 
    public void add(ServerThread thread) {
        threads.add(thread);
    }
 
    public void broadcastMessage(String message) {
        for (ServerThread thread : threads) {
            thread.send(message);
        }
    }

    

}