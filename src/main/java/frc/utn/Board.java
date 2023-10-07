package frc.utn;

public class Board {
    private char[][] board;

    public Board(int rows, int cols) {
        this.board = new char[rows][cols];
        for (int row = 0; row < this.board.length; row++){
            for (int col = 0; col < this.board[0].length; col++){
                this.board[row][col] = 'u';
            }
        }
    }

    public boolean checkWinCondition(int row, int col){
        char token = this.board[row][col];
        int[] directions = {1, 0,-1};
        for (int rowDir : directions){
            for (int colDir : directions){
                if(rowDir == 0 && colDir == 0) continue;
                int count = 0;
                int rIndex = row;
                int cIndex = col;
                // There's a chance that a token is placed in the middle of a winning combination.
                // This is an example pattern: 0 0 _ 0
                if(
                        rIndex-rowDir >= 0 && rIndex-rowDir < this.getBoardHeight() &&
                        cIndex-colDir >= 0 && cIndex-colDir < this.getBoardWidth() &&
                        this.board[rIndex-rowDir][cIndex-colDir] == token
                ) count++;
                // Check the winning condition in the direction of the current direction.
                while ( rIndex >= 0 && rIndex < this.getBoardHeight() &&
                        cIndex >= 0 && cIndex < this.getBoardWidth() &&
                        this.board[rIndex][cIndex] == token){
                    count++;
                    rIndex += rowDir;
                    cIndex += colDir;
                }
                if(count >= 4) return true;
            }
        }
        return false;
    }

    public int setToken(int col, char token) {
        for (int row = this.board.length - 1; row >= 0; row--) {
            if (this.board[row][col] == 'u') {
                this.board[row][col] = token;
                return row;
            }
        }
        return -1;
    }

    public int getBoardSize(){
        return this.getBoardHeight() * this.getBoardWidth();
    }
    public boolean setTokenAndCheckWin(int col, char token){
        int row = setToken(col, token);
        return checkWinCondition(row, col);
    }

    public String toString(){
        StringBuilder boardString = new StringBuilder();
        for (int row = 1; row <= this.getBoardHeight(); row++){
            boardString.append(row + " ");
        }
        boardString.append("\n");
        for (int row = 0; row < this.getBoardHeight(); row++){
            for (char c : this.board[row]) {
                boardString.append(c + " ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public char[][] getBoard() {
        return board;
    }

    public int getBoardWidth(){
        return this.board[0].length;
    }

    public int getBoardHeight(){
        return this.board.length;
    }

    public boolean isColumnFull(int col){
        return this.board[0][col] != 'u';
    }

    public String toStringF (){
        StringBuilder boardString = new StringBuilder();
        boardString.append("\u001B[36m");
        for (int rows = 1; rows <= this.getBoardWidth(); rows++){
            boardString.append((rows) + " ");
        }
        boardString.append("\n");
        for (int rows = 0; rows < this.board.length; rows++){
            for (int cols = 0; cols < this.getBoardWidth(); cols++) {
                char token = this.board[rows][cols];
                switch (token) {
                    case 'r' -> boardString.append("\u001B[31m");
                    case 'b' -> boardString.append("\u001B[34m");
                    case 'y' -> boardString.append("\u001B[33m");
                    case 'g' -> boardString.append("\u001B[32m");
                    default -> boardString.append("\u001B[37m");
                }
                boardString.append("O ");
            }
            boardString.append("\n\u001B[0m");
        }
        return boardString.toString();
    }
}
