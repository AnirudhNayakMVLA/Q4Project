import java.awt.*;


public class Board {
    ArrayList<Street> streets;
    Color bgColor = new Color(205, 230, 208);
    
    public Board(ArrayList<Street> streets){
        this.streets = streets;
    }

    public void drawBoard(Graphics g){
        g.setColor(bgColor);
        g.drawRect(170, 170, 660, 660);
        int x = 770;
        int y = 770;
        for (int i = 0; i < 1; i++){
            streets.get(i).drawMe(g, x, y, 0);
            if(i == 0){
                x -= 60;
            }
            else{
                x -= 30;
            }
        }
        for(int i = 10; i < 20; i++){
            streets.get(i).drawMe(g, x, y, 90);
            if(i == 10){
                y -= 60;
            }
            else{
                y -= 30;
            }
        }
        for(int i = 20; i < 30; i++){
            streets.get(i).drawMe(g, x, y, 180);
            if(i == 20){
                x += 60;
            }
            else{
                x += 30;
            }
        }
        for(int i = 30; i < 40; i++){
            streets.get(i).drawMe(g, x, y, 270);
            if(i == 30){
                y += 60;
            }
            else{
                y += 30;
            }
        }
    }
    
}
