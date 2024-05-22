import java.io.Serializable;

public class Board implements Serializable{
    ArrayList<Street> streets;
    ArrayList<Player> players;    
    public Board(ArrayList<Street> streets){
        this.streets = streets;
        players = new ArrayList<Player>();
    }

    public String toString(){
        String s = "";
        for(Street street : streets){
            s += street.toString() + ", ";
        }
        return s;
    }

    public Player playerAtPos(int pos){
        for(Player p : players){
            if(p.getPosition() == pos){
                System.out.println(players);
                System.out.println(p);
                return p;
            }
        }
        return null;
    }


    public ArrayList<Street> getStreets(){
        return streets;
    }

    public void addPlayer(Player p){
        players.add(p);
    }
    
    public void removePlayer(Player p){
        players.remove(p);
    }

    public void movePlayer(int playerNum, int numToMove){
        for(Player p : players){    
            if(p.getPlayerNum() == playerNum){
                p.move(numToMove);
                return;
            }
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

}
