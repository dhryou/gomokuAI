# GomokuAI (Java)

## About the Game
*Gomoku* is a popular abstract strategy board game played on a 15x15 or 19x19 grid board, similar to "five in a row" or "connect five." Players take turns placing black (starts first) and white stones. The winner is the first player to form an unbroken row of five stones in any direction—horizontally, vertically, or diagonally.

Depending on where you play the game, there are different names. For example, *omok* in Korean, *go* in chinese, or *gomoku* in Japanese. There are also different rules that each variation abides by. In my case, I created a game that follows the rules of _**renju gomoku**_.

## Renju Gomoku
*Renju gomoku* is one of the most popular variations of the game, played on a 15x15 board, with specific rules about three and three, four and four, and overline (six consecutive) combinations that apply to only black stones, as this player has a starting advantage.

One of the reasons that *renju gomoku* is widely used is due to the fact that without these rules, there is a strategy for black to win 99.9% of the time.

## Playing my Game
There are four modes you can play in my adaption of the game.

1. Player vs. player
1. Player (black) vs. AI
1. Player (white) vs. AI
1. AI vs. AI

* Play the game with a friend! The game will keep track of the board and make sure no *illegal* moves are taken, with a surprise at the end for the winner.
* Play the game as black stone against my AI*.
* Play the game as white stone against my AI*.
* Watch the game be played against two AI bots.

*Note: The AI I've developed is assessed as "hard," as the program will look from all different directions of the four (or more, sitaution-dependent) adjacent blocks. Most AI programs of *gomoku* are randomized and will place a stone anywhere, which will allow you to win in five moves. *boring*...

## Other Functions In-game
### Hints
The game will offer you infinite (*yes, there are technically only 225 possible positions on the board*) hints throughout the game. The hint block will be an outline of a stone with transparent color.

### Save
Save the game that you just played, in whichever mode you played or even whether you completed the game or not, and the code will be saved in a legible form for anyone familiar with the game to reproduce on an actual *gomoku* board. Save the game after placing a couple moves and find out!

*Date, winner, number of moves are all saved at the end
*Title of text file saved is in the following sample format: "190101_1346_Gomoku" showing date, time, game.

### Open
You are able to open any saved game and reproduce the game, as the stones appear on the screen (every 1.5s) in the order they were placed in the saved game. When the program is done reproducing the game, you can even **continue** any saved game! 

## Structure of the code
This project is divided into different class files, each with different functions, with the main() method in Main_Gomoku. Below are the different files in this project, in descending order of what is called by each preceding class.

* Main_Gomoku
* Menu_Gomoku
* Board_Gomoku
* Renju_Gomoku
* AI_Gomoku
* PointGrade

### Main_Gomoku
This class houses the main method, instantiating a **Menu_Gomoku** class, and loads the correct *Board_Gomoku* based on the game mode selected from **Menu_Gomoku**, containing the ActionListener.

### Menu_Gomoku
This class displays the menu UI as shown when you run the game and to input which game mode you selected to deliver to **Main_Gomoku** to load the version of **Board_Gomoku** you are about to play.

### Board_Gomoku
This class creates the yellow 15x15 board you will play the game on. There are various MenuBar options on the top to explore different functions outside just the game. The GameCanvas within this class will place the stones, after verifying with a *judge*, or an instantiation of the **Renju_Gomoku** class.

### Renju_Gomoku
This class does the entire menial labor of scanning the board for different winning strategies based on what has been placed. The program assesses **56 different types of stone arrangement combinations** divided into **six different priorities** to place the next stone (if you are AI or asked for a hint).

Priority 1: Game ends within one move (e.g. four stones of the same color have been placed and there are no obstacles in the way, and thus the game will absolutely end.)

Priority 2: Game ends within two moves (e.g. three stones of the same color have been placed with no obstacles on either left or right, and thus the game will absolutely end, unless somebody blocks.)

... *et ceterae*

### AI_Gomoku
This class is summoned by **Board_Gomoku** if you chose to play the second, third, or fourth game mode with an AI. This class prints out the different *priorities* of stone placements in the terminal, so you can see the AI's decision-making.

### PointGrade
This class computes the 56 different stone combinations using prime numbers to assess which combination is the winning strategy, leading the AI to place a stone at the best possible option.

## Updates
The game runs smoothly with the exception of a certain features that have been taken out in the middle for fluidity and bug fixes. At this moment, the game will place stones at random locations, but there is a simple fix in my previous versions to replace the current Random() placement.

