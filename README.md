# ConnectFour

![Java Version](https://img.shields.io/badge/Java-17.0.9-red)
![IntelliJ Version](https://img.shields.io/badge/IntelliJ_IDEA-2023.3-blue)
![Maven Version](https://img.shields.io/badge/Apache_Maven-3.9.6-violet)

This is a simple proyect used for learning how to use matrices in java.
Besides, it was great to learn java basics.

## How to play

Each player places a token in his turn. The first to connect four in a row wins the game.
Its a tie when the board is full and no one has reached the goal.

You can run locally using the following commands:

```bash
  git clone https://github.com/moradelboca/connectfour

  cd connectfour/target

  java -jar ConnectFour-1.0-SNAPSHOT.jar
```

Java RE 17 needs to be installed in the computer first.

## The algorithm

Each time a player places a token, the matrix is traversed in all posible directions. One direction is chosen at a time, setted up by a double index.
When a direccion is setted, "count" will store the number of consecutive and equal tokens. If there are four consecutive and equal tokens, then the win condition is reached. Otherwise, another direction will be chosen until all directions have been revised.

```java
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
```

Notice that this algorithm will check in the every direction, *starting from* the point where the token is placed. 
If the token is in the middle of a winning pattern _(f.e.: 0 0 t 0, or 0 t 0 0)_ the algorithm won't notice the move as a winning move.
The solution is to check one square in the opossite direction of the one setted.

```java
    if(
        rIndex-rowDir >= 0 && rIndex-rowDir < this.getBoardHeight() &&
        cIndex-colDir >= 0 && cIndex-colDir < this.getBoardWidth() &&
        this.board[rIndex-rowDir][cIndex-colDir] == token
    ) count++;
```

Another issue to deal with is that as the horizontal direction is ```rowDir=(1 or -1)``` and ```colDir=0```, same as vertical direction is ```rowDir=0``` and ```colDir=(1 or -1)```, but direction ```rowDir=0``` and ```colDir=0``` needs to be avoided! That's what this line is doing:

```java
    if(rowDir == 0 && colDir == 0) continue;
```

## Bot

There's a "attempt" of bot coded. In order to use it, you will need a OpenAI API Key.
Also, you have to add the bot to the list of players in the game class. 

_Feel free to ask for a key ;)_
