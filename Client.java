import java.io.*;
import javax.swing.JFrame;

public class Client {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Samuel Kafka - 10171530 - Client Screen - Assignment 2 - CPSC 1181 - Fall 2021 - Prof. Chee - TR 2:30-3:45pm - Due 10/14/2021 - 10% Late Penalty Per Day Late - No Collaboration - No Resources - No Cheating - No Plagiarism - No Sharing - No Copying - No Looking - No Talking - No Texting - No Emailing - No Posting - No Posing - No Boasting - No Hosting - No Toasting - No Roasting - No Bo Peeping - No Peeping Tom - No Peeping Tammy - No Peeping Timmy - No Peeping Tommy - No Peeping Tim - No Peeping Tom - No Peeping Tam - No Peeping Ti - No Peeping T - No Peeping - No Peep - No Pee - No P - No - N");

		ClientScreen sc = new ClientScreen();
        frame.add(sc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

        sc.connect();
    }
}