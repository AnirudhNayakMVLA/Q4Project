import java.io.Serializable;

public class Player implements Serializable{
   //write the class for a monopoly player
    private int playerNum;
    private int money;
    private int position;
    private boolean inJail;
    private int jailTurns;
    private int numRailroads;
    private int numUtilities;
    private Street [] properties;
    private int numGetOutOfJailCards;
    private boolean isBankrupt;
    private int numTurns;
    private int numDoubles;
    private int numJailTurns;
    private String color;

    public Player(int num){
        this.playerNum = num;
        money = 1500;
        position = 0;
        inJail = false;
        jailTurns = 0;
        numRailroads = 0;
        numUtilities = 0;
        properties = new Street[28];
        numGetOutOfJailCards = 0;
        isBankrupt = false;
        numTurns = 0;
        numDoubles = 0;
        numJailTurns = 0;
        color = "";
    }

    public void addProperty(Street s){
        for(int i = 0; i < properties.length; i++){
            if(properties[i] == null){
                properties[i] = s;
                break;
            }
        }
    }

    public void removeProperty(Street s){
        for(int i = 0; i < properties.length; i++){
            if(properties[i] == s){
                properties[i] = null;
                break;
            }
        }
    }

    public void addMoney(int m){
        money += m;
    }

    public void subtractMoney(int m){
        money -= m;
    }

    public void setPosition(int p){
        position = p;
    }

    public void setInJail(boolean b){
        inJail = b;
    }

    public void setJailTurns(int j){
        jailTurns = j;
    }

    public void setNumRailroads(int n){
        numRailroads = n;
    }

    public void setNumUtilities(int n){
        numUtilities = n;
    }

    public void setNumGetOutOfJailCards(int n){
        numGetOutOfJailCards = n;
    }

    public void setIsBankrupt(boolean b){
        isBankrupt = b;
    }

    public void setNumTurns(int n){
        numTurns = n;
    }

    public void setNumDoubles(int n){
        numDoubles = n;
    }

    public void setNumJailTurns(int n){
        numJailTurns = n;
    }

    public int getPlayerNum(){
        return playerNum;
    }

    public int getMoney(){
        return money;
    }
    public void setColor(String color){
        this.color = color;
    }

    public int getPosition(){
        return position;
    }

    
}