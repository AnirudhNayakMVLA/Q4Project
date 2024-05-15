public class Manager {
    private ArrayList<ServerThread> threads;
    Board board;

    public Manager() {
        threads = new ArrayList<ServerThread>();
        resetBoard();
    }
 
    public void add(ServerThread thread) {
        threads.add(thread);
		//broadcastMessage(board);
    }
 
    public void broadcastMessage(Board board) {
        this.board = board;
        for (ServerThread thread : threads) {
            thread.send(board);
        }
    }

    public Board getBoard(){
        return board;
    }

    
	public void resetBoard(){
		ArrayList<Street> streetList = new ArrayList<Street>();
		streetList.add(new Street("Go", Street.Function.GO.getInt()));
		streetList.add(new Street("2555 Katrina Way", 2, 60, 40, 50, 50, 0, false, "Brown", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Brower Avenue", 4, 60, 40, 50, 50, 0, false, "Brown", Street.Function.STREET.getInt()));
		streetList.add(new Street("Income Tax", Street.Function.TAX.getInt()));
		streetList.add(new Street("CA-237", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Truman Ave", 6, 100, 50, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Bryant Avenue", 6, 100, 50, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Levin Avenue", 8, 120, 60, 50, 50, 0, false, "Light Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Jail", Street.Function.JAIL.getInt()));
		streetList.add(new Street("Church St", 10, 140, 70, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("Electric Company", 12, 150, 75, 0, 0, 0, false, "Utility", Street.Function.UTILITY.getInt()));
		streetList.add(new Street("Cuesta Drive", 10, 140, 70, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("Covington Road", 12, 160, 80, 100, 100, 0, false, "Pink", Street.Function.STREET.getInt()));
		streetList.add(new Street("US-101", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Middlefield Avenue", 14, 180, 90, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Whisman Road", 14, 180, 90, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("San Antonio Road", 16, 200, 100, 100, 100, 0, false, "Orange", Street.Function.STREET.getInt()));
		streetList.add(new Street("Free Parking", Street.Function.FREE_PARKING.getInt()));
		streetList.add(new Street("Foothill Expwy", 18, 220, 110, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Central Expwy", 18, 220, 110, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("Moffet Boulevard", 20, 240, 120, 150, 150, 0, false, "Red", Street.Function.STREET.getInt()));
		streetList.add(new Street("CA-85", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Grant Road", 22, 260, 130, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Evelyn Ave", 22, 260, 130, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Water Works", 12, 150, 75, 0, 0, 0, false, "Utility", Street.Function.UTILITY.getInt()));
		streetList.add(new Street("Sleeper Avenue", 24, 280, 140, 150, 150, 0, false, "Yellow", Street.Function.STREET.getInt()));
		streetList.add(new Street("Go To Jail", Street.Function.GO_TO_JAIL.getInt()));
		streetList.add(new Street("California Street", 26, 300, 150, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("Miramonte Avenue", 26, 300, 150, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("Community Chest", Street.Function.COMMUNITY_CHEST.getInt()));
		streetList.add(new Street("Shoreline Boulevard", 28, 320, 160, 200, 200, 0, false, "Green", Street.Function.STREET.getInt()));
		streetList.add(new Street("I-280", 25, 200, 100, 100, 100, 0, false, "Railroad", Street.Function.RAILROAD.getInt()));
		streetList.add(new Street("Chance", Street.Function.CHANCE.getInt()));
		streetList.add(new Street("Castro St", 35, 350, 175, 200, 200, 0, false, "Dark Blue", Street.Function.STREET.getInt()));
		streetList.add(new Street("Luxury Tax", Street.Function.TAX.getInt()));
		streetList.add(new Street("El Camino Real", 50, 400, 200, 200, 200, 0, false, "Dark Blue", Street.Function.STREET.getInt()));
		board = new Board(streetList);
	}

}