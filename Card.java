import java.io.Serializable;

public class Card implements Serializable{
    private boolean moveNumStreets;
    private boolean giveMoney;
    private boolean takeMoney;
    private boolean goToJail;
    private int amount;
    private int moveAmount; 
    private boolean getOutOfJail;
    private boolean moveToStreet;
    private int position;
    private String direction;

    public Card(String direction, cardType type, int num){
        this.moveToStreet = type.moveToStreet;
        this.giveMoney = type.giveMoney;
        this.takeMoney = type.takeMoney;
        this.goToJail = type.goToJail;
        this.amount = 0;
        this.moveAmount = 0;
        this.getOutOfJail = type.getOutOfJail;
        this.moveNumStreets = type.moveNumStreets;
        this.position = 0;
        this.direction = direction;

        if(type == cardType.MOVE_TO_STREET){
            position = num;
        }
        else if(type == cardType.MOVE_NUM_STREETS){
            moveAmount = num;
        }
        else if(type == cardType.GIVE_MONEY){
            amount = num;
        }
        else if(type == cardType.TAKE_MONEY){
            amount = num;
        }
        else if(type == cardType.GO_TO_JAIL){
            goToJail = true;
        }
        else if(type == cardType.GET_OUT_OF_JAIL){
            getOutOfJail = true;
            
        }
    }

    public static enum cardType{
        MOVE_TO_STREET(true, false, false,false, false, false),
        MOVE_NUM_STREETS(false, true, false, false, false, false),
        GIVE_MONEY(false, false, true, false, false, false),
        TAKE_MONEY(false, false, false, true, false, false),
        GO_TO_JAIL(false, false, false, false, true, false),
        GET_OUT_OF_JAIL(false, false, false, false, false, true);

        private final Boolean moveToStreet;
        private final Boolean giveMoney;
        private final Boolean takeMoney;
        private final Boolean goToJail;
        private final Boolean getOutOfJail;
        private final Boolean moveNumStreets;

        private cardType(Boolean moveToStreet, Boolean moveNumStreets, Boolean giveMoney, Boolean takeMoney, Boolean goToJail, Boolean getOutOfJail){
            this.moveToStreet = moveToStreet;
            this.giveMoney = giveMoney;
            this.takeMoney = takeMoney;
            this.goToJail = goToJail;
            this.getOutOfJail = getOutOfJail;
            this.moveNumStreets = moveNumStreets;
        }
    }

    public int action(){
        if(moveToStreet){
            return 1;
        }
        if(moveNumStreets){
            return 2;
        }
        if(giveMoney){
            return 3;
        }
        if(takeMoney){
            return 4;
        }
        if(goToJail){
            return 5;
        }
        if(getOutOfJail){
            return 6;
        }
        return 0;
    }

    public int getActionNum(){
        if(moveToStreet){
            return position;
        }
        if(moveNumStreets){
            return moveAmount;
        }
        if(giveMoney || takeMoney){
            return amount;
        }
        return 0;
    }

    public String getDirection(){
        return direction;
    }
  
}
