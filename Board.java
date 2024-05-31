import java.io.Serializable;

public class Board implements Serializable{
    ArrayList<Street> streets;
    ArrayList<Player> players;    
    Queue<Card> chance, communityChest;
    /*static */int playerTurn;
    
    public Board(ArrayList<Street> streets){
        playerTurn = 0;
        this.streets = streets;
        players = new ArrayList<Player>();
        chance = new Queue<Card>();
        communityChest = new Queue<Card>();
        setChance();
        setCommunityChest();
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
                System.out.println("Player at pos " + pos + ", " + streets.get(pos));
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
            if((p.getPlayerNum() == playerNum) && !p.isInJail()){
                p.move(numToMove);
                return;
            }else if(p.getPlayerNum() == playerNum && p.isInJail()){
                p.setInJail(false);
                p.subtractMoney(50);
            }
        }
    }
    
    public void setPlayerPos(int playerNum, int pos){
        for(Player p : players){
            if(p.getPlayerNum() == playerNum){
                System.out.println("Setting player " + playerNum + " to pos " + pos);
                p.setPosition(pos);
                System.out.println(playerAtPos(pos).toString());
                return;
            }
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getPlayer(int playerNum){
        for(Player p : players){
            if(p.getPlayerNum() == playerNum){
                return p;
            }
        }
        return null;

    }
    public void addChanceCard(Card c){
        chance.add(c);
    }
    public void addCommunityChestCard(Card c){
        communityChest.add(c);
    }
    public void setChance(){
        //add all of the chance cards to the queue
        chance.add(new Card("Advance to Go", Card.cardType.MOVE_TO_STREET, 0));
        chance.add(new Card("Advance to Illinois Ave", Card.cardType.MOVE_TO_STREET, 24));
        chance.add(new Card("Advance to St. Charles Place", Card.cardType.MOVE_TO_STREET, 11));
        //chance.add(new Card("Advance to nearest utility", 0));
        //chance.add(new Card("Advance to nearest railroad", 0));
        chance.add(new Card("Bank pays you dividend of $50", Card.cardType.GIVE_MONEY, 50));
        chance.add(new Card("Get out of jail free", Card.cardType.GET_OUT_OF_JAIL, 0));
        chance.add(new Card("Go back 3 spaces", Card.cardType.MOVE_NUM_STREETS, -3));
        chance.add(new Card("Go to jail", Card.cardType.GO_TO_JAIL, 0));
        //chance.add(new Card("Make general repairs on all your property", 0));
        chance.add(new Card("Pay poor tax of $15", Card.cardType.TAKE_MONEY, -15));
        chance.add(new Card("Take a trip to Reading Railroad", Card.cardType.MOVE_TO_STREET, 5));
        chance.add(new Card("Take a walk on the Boardwalk", Card.cardType.MOVE_TO_STREET, 39));
        chance.add(new Card("You have been elected chairman of the board", Card.cardType.TAKE_MONEY, (-50 * (players.size() - 1))));
        chance.add(new Card("Your building loan matures", Card.cardType.GIVE_MONEY, 150));

    }
    public void setCommunityChest(){
        //add all of the community chest cards to the queue
        communityChest.add(new Card("Advance to Go", Card.cardType.MOVE_TO_STREET, 0));
        communityChest.add(new Card("Bank error in your favor", Card.cardType.GIVE_MONEY, 200));
        communityChest.add(new Card("Doctor's fee", Card.cardType.TAKE_MONEY, -50));
        communityChest.add(new Card("From sale of stock you get $50", Card.cardType.GIVE_MONEY, 50));
        communityChest.add(new Card("Get out of jail free", Card.cardType.GET_OUT_OF_JAIL, 0));
        communityChest.add(new Card("Go to jail", Card.cardType.GO_TO_JAIL, 0));
        communityChest.add(new Card("Grand Opera Night", Card.cardType.TAKE_MONEY, -50));
        communityChest.add(new Card("Holiday fund matures", Card.cardType.GIVE_MONEY, 100));
        communityChest.add(new Card("Income tax refund", Card.cardType.GIVE_MONEY, 20));
        communityChest.add(new Card("It is your birthday", Card.cardType.GIVE_MONEY, 10));
        communityChest.add(new Card("Life insurance matures", Card.cardType.GIVE_MONEY, 100));
        communityChest.add(new Card("Pay hospital fees of $100", Card.cardType.TAKE_MONEY, -100));
        communityChest.add(new Card("Pay school fees of $150", Card.cardType.TAKE_MONEY, -150));
        communityChest.add(new Card("Receive $25 consultancy fee", Card.cardType.GIVE_MONEY, 25));
        //communityChest.add(new Card("You are assessed for street repairs", Card.cardType.TAKE_MONEY, -40));
        communityChest.add(new Card("You have won second prize in a beauty contest", Card.cardType.GIVE_MONEY, 10));
        communityChest.add(new Card("You inherit $100", Card.cardType.GIVE_MONEY, 100));
    }
    //get chance and community chest
    public Queue<Card> getChance(){
        return chance;
    }
    public Queue<Card> getCommunityChest(){
        return communityChest;
    }

    public int playerTurn(){
        System.out.println(players);
        System.out.println("player turn: " + playerTurn);
        return players.get(playerTurn).getPlayerNum();
    }
    
    public void incrementTurn(){   
        System.out.println("initial player turn: " + playerTurn); 
        playerTurn++;
        if(playerTurn >= players.size()){
            System.out.println("reset turn to 0");
            playerTurn = 0;
        }
        System.out.println("players size: " + players.size());
        System.out.println("incremented turn. new turn is " + playerTurn);
    }
    public int getTurn(){
        return playerTurn;
    }


}
