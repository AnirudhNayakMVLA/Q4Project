import java.io.Serializable;
import java.awt.*;

public class Player implements Serializable{
   //write the class for a monopoly player
    private int playerNum;
    private int money;
    private int position;
    private boolean inJail;
    private int jailTurns;
    private int numRailroads;
    private int numUtilities;
    private ArrayList<Street> properties;
    private int numGetOutOfJailCards;
    private boolean isBankrupt;
    private int numDoubles;

    private Color color;

    public Player(int num, Color color){
        this.playerNum = num;
        this.color = color;
        money = 1500;
        position = 0;
        inJail = false;
        jailTurns = 0;
        numRailroads = 0;
        numUtilities = 0;
        properties = new ArrayList<Street>();
        numGetOutOfJailCards = 0;
        isBankrupt = false;
        numDoubles = 0;
        
    }

    public boolean buyStreet(Street s){
        if(money >= s.getPrice()){
            money -= s.getPrice();
            properties.add(s);
            s.setOwner(this);
            return true;
        }else{
            return false;
        }
    }

    public void sellStreet(Street s){
        if(properties.contains(s)){
            properties.remove(s);
            money += s.getPrice();
            s.setOwner(null);
        }
    }

    public void payPlayer(Player p, int amount){
        subtractMoney(amount);
        p.addMoney(amount);
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

    public void move(int m){
        position += m;
        if(position >= 40){
            position -= 40;
            addMoney(200);
        }
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

    public void setNumDoubles(int n){
        numDoubles = n;
    }


    public int getPlayerNum(){
        return playerNum;
    }

    public int getMoney(){
        return money;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public int getPosition(){
        return position;
    }

    public void drawMe(int x, int y, Graphics g){
        g.setColor(color);
        g.fillOval(x, y, 20, 20); 
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 20, 20);
    }

    public boolean isInJail(){
        return inJail;
    }

    public void sendToJail(){
        int position = 10;
        setInJail(true);
        setJailTurns(0);
    }
}