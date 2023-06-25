/*
 * class for a Board object
 *
 * Frank Scagluso Feb 21 2022
 */

public class Board
{
	public static final int TURNS = 7;
	public static final int WORDSIZE = 5;
	public char [][] line = new char [TURNS][18];
	public  char [][] results = new char [TURNS][18];
	public int turnNum = 0;
	public Set<String> wrongGuesses = new TreeSet<String>();
//	private char [] [] board = new char [TURNS*2];

	/*
	 * initializes board by placing a dot in each cell
	 */

	public Board()
	{
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 18; j++) {
				line[i][j] = ' ';
				results[i][j] = ' ';
				if ((j == 0) || (j == 17)) {
					line[i][j] = '|';
					results[i][j] = '|';
				}
			}
		}
	}

	public boolean isRightLength(String s)
	{
		if (s.length() != WORDSIZE) return true;
		return false;
	}

	/*
	 * returns true if space is unfilled
	 */

	public boolean isValidWord(Set dict, String s)
	{
		if (dict.contains(s)) return false;
		return true; 
	}

	/*
	 * returns true if any row, column, or diagonal is filled with
	 * one player's symbol
	 */


	public boolean gameWon(String tobeG, String guess)
	{ 
		if (tobeG.contains(guess)) return true;
		return false;
	}

	public void displayResults(String guessWord, String guess, int turn) {
		String notInWord = "";
        for (int i = 0; i < 5; i++) {
			line[turn][2+(3*i)] = guess.charAt(i);
			String muffin = "";
			muffin = muffin + guess.charAt(i);
			if (guessWord.charAt(i) == guess.charAt(i)) {
				results[turn][2+(3*i)] = '*';
				results[turn][3+(3*i)] = '*';
			}
			else if (guessWord.contains(muffin)) {
				results[turn][2+(3*i)] = '*';
			}
            else {
                notInWord = "" + guess.charAt(i);
                wrongGuesses.add(notInWord);
            }
		}
		turnNum++;
	}
	public void winSeq() {
        turnNum = turnNum+1;
        System.out.println("You won in " + turnNum + " turns!");
        System.exit(0);
    }
	/*
	 * returns string representation of board
	 * 25814
	 * 01234567891123456
	 * | W  O  R  D  S |
	 * | .  *  ** ** . |
	 * | . . . |
	 */

	public String toString()
	{
		String graphicalBoard = "\n";
		for (int i = 0; i < turnNum; i++) {
			for (int j = 0; j < 18; j++) {
				graphicalBoard += line[i][j];
			}
            graphicalBoard += "\n";
            for (int j = 0; j < 18; j++) {
                graphicalBoard += results[i][j];
            }
			graphicalBoard += "\n";
		}
		//graphicalBoard += "\n";
		/*for (int i = 0; i < 18; i++) {
			graphicalBoard += results[turn][i];
		}*/
		graphicalBoard += "Wrong Guesses: ";
		graphicalBoard += wrongGuesses;
		graphicalBoard += "\n";
		return graphicalBoard;
	}
}
