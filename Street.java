import java.awt.*;
import java.io.Serializable;

public class Street implements Serializable{
    Font font = new Font("KabelBdNormal", Font.PLAIN, 12);
    String name, color;
    int rent, price, mortgage, houseCost, hotelCost, houses, function, xSize, ySize;
    Color drawColor;
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
        mapColors();
        if(function < 6){
            xSize = 60;
        }
        else{
            xSize = 120;
        }
        ySize = 120;
    }

    private String [] getNameAsList(){
        String [] names = name.split(" ");
        return names;
    }

    public Street(String name, int function){
        this.name = name;
        this.function = function;
        mapColors();
        if(function < 6){
            xSize = 60;
        }
        else{
            xSize = 120;
        }
        ySize = 120;
    }

    public String toString(){
        return name;
    }

        
    public static enum Function{
        STREET(0), RAILROAD(1), UTILITY(2), TAX(3), CHANCE(4), COMMUNITY_CHEST(5), GO(6), JAIL(7), FREE_PARKING(8), GO_TO_JAIL(9);

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
        g.setColor(drawColor);
        g.fillRect(x, y, xSize, ySize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, xSize, ySize);
        int yAdd = 0;
        for(String i : getNameAsList()){
            FontMetrics metrics = g.getFontMetrics(font);
            // Determine the X coordinate for the text
            int xAdd = (xSize - metrics.stringWidth(i)) / 2;
            x += xAdd;
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen);
            g.setFont(font);
            g.drawString(i, x, y+20 + yAdd);
            yAdd += 20;
            x -= xAdd;
        }
        
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
    public int getXSize(){return xSize;}
    public int getYSize(){return ySize;}

    private void mapColors(){
        //make it so that it makes the drawColor be the color of the street
        if(function > 1){
            drawColor = new Color(150, 150, 150);
            return;
        }else if(color.equals("Brown")){
            drawColor = new Color(139, 69, 19);
            return;
        }
        else if(color.equals("Light Blue")){
            drawColor = new Color(173, 216, 230);
            return;
        }
        else if(color.equals("Pink")){
            drawColor = new Color(255, 192, 203);
            return;
        }
        else if(color.equals("Orange")){
            drawColor = new Color(255, 165, 0);
            return;
        }
        else if(color.equals("Red")){
            drawColor = new Color(255, 0, 0);
            return;
        }
        else if(color.equals("Yellow")){
            drawColor = new Color(255, 255, 0);
            return;
        }
        else if(color.equals("Green")){
            drawColor = new Color(0, 128, 0);
            return;
        }
        else if(color.equals("Blue")){
            drawColor = new Color(0, 0, 255);
            return;
        }
    }
 
}
