import java.awt.*;

public class Street{
    String name, color;
    int rent, price, mortgage, houseCost, hotelCost, houses, function;
    public Street(String name, int rent, int price, int mortgage, int houseCost, int hotelCost, int houses, boolean hotel, String color, int function){
        this.name = name;
        this.rent = rent;
        this.price = price;
        this.mortgage = mortgage;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
        this.houses = houses;
        this.color = color;
        this.function = function;
    }

    public Street(String name, int function){
        this.name = name;
        this.function = function;
    }

        
    public static enum Function{
        STREET(0), RAILROAD(1), UTILITY(2), GO(3), JAIL(4), FREE_PARKING(5), GO_TO_JAIL(6), TAX(7), CHANCE(8), COMMUNITY_CHEST(9);

        private int i;
        
        private Function(int i){
            this.i = i;
        }

        public int getInt(){
            return i;
        }
    }

    public void drawMe(Graphics g, int x, int y, int rotation){
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(rotation), x, y);
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 30, 60);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 30, 60);
        g.setColor(Color.BLACK);
        g.drawString(name, x, y);
        g2d.rotate(Math.toRadians(-rotation), x, y);
        System.out.println("Drew street" + name);

    }
    

    public void drawHouse(Graphics g, int x, int y, int rotation){

    }

    public void drawHotel(Graphics g, int x, int y, int rotation){

    }

    public int getRent(){return rent;}
    public int getPrice(){return price;}
    public int getMortgage(){return mortgage;}
    public int getHouseCost(){return houseCost;}
    public int getHotelCost(){return hotelCost;}
    public int getHouses(){return houses;}
    public String getColor(){return color;}
    public String getName(){return name;}  
 
}
