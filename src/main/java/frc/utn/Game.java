package frc.utn;
import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    // Create a list with lenght 2-
    private Player[] players;
    private Board board;
    private int move;
    public Game(int quantityOfPlayers, int boardWidth, int boardHeight) {
        this.players = new Player[quantityOfPlayers];
        this.board = new Board(boardWidth, boardHeight);
        this.move = 0;
    }

    public void start(){
        // Create players.
        players[0] = new HumanPlayer("Player1", 'b');
        //players[1] = new Bot();
        players[1] = new HumanPlayer("Player2", 'y');
        System.out.println("Welcome to Connect Four!");
        System.out.println(this.board.toStringF());
        while(true){
            for (Player player : players) {
                System.out.println("It's " + player.getName() + "'s turn.");
                int col = player.selectCol(this.board);
                boolean checkWin = this.board.setTokenAndCheckWin(col, player.getColor());
                System.out.println(this.board.toStringF());
                if(checkWin){
                    System.out.println("Player " + player.getName() + " won!");
                    return;
                }
                this.move++;
                if(this.move == this.board.getBoardSize()){
                    System.out.println("It's a tie!");
                    return;
                }

            }
        }
    }
}
