package frc.utn;
import java.util.Scanner;

public class HumanPlayer implements Player{
    private String name;
    private char color;
    public HumanPlayer(String name, char color) {
        this.name = name;
        setColor(color);
    }

    @Override
    public int selectCol(Board board) {
        Scanner scanner = new Scanner(System.in);
        int col;
        System.out.print("Choose a column: ");
        col = scanner.nextInt()-1;
        while(col < 0 || col >= board.getBoardWidth() || board.isColumnFull(col)){
            System.out.println("You've entered a wrong number! ");
            System.out.print("Try again: ");
            col = scanner.nextInt()-1;
        }
        return col;
    }

    public String getName() { return this.name; }

    public char getColor() { return this.color; }

    public void setColor(char color) {
        if(color == 'r' || color == 'y' || color == 'b' || color == 'g') {
            this.color = color;
        }
    }
}
