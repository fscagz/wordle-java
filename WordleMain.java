/*
    Wordle by Frank Scagluso
    May 20 2022
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WordleMain {
    public static char [][] line = new char [6][18];
	public static char [][] results = new char [6][18];
    public static String wrongGuess = "";
    public static Set<String> wrongGuesses = new TreeSet<String>();
    public static void main (String [] args) {
        setUpBoard();
        Set<String> wordDict = new HashSet<>(6359);
        
        try {
            Scanner dict = new Scanner(new File("dict.txt"));
            while(dict.hasNext()) {
                String word = dict.next();
                wordDict.add(word);
            }
            String guessWord = wordDict.getRandom();
           // System.out.println(guessWord);
            System.out.println("Welcome to Wordle.  You have " + Board.TURNS + " turns to guess the word!\n");
            System.out.println("Instructions: After each guess, one star under the word indicates the letter is in the actual word but in the wrong spot.  Two stars indicate that the letter is in the right place in the word.  Good luck!");
            System.out.println("Press q at any time to exit.");
            int turnCounter = 0;
            while (turnCounter != 6) {
                Scanner input = new Scanner(System.in);
                System.out.println("Enter a word: ");
                String guesso = input.next();
                if ("q".contains(guesso)) break;
               String guess = guesso.toLowerCase();
               if (isRightLength(guess)) {
                    System.out.println("Guess must be 5 letters long!");
                    continue;
             }
             if (isValidWord(wordDict, guess)) {
                System.out.println("Word not found in dictionary!");
                continue;
            }
            if (gameWon(guessWord, guess)) winSeq(turnCounter);
            wrongGuess += checkRez(turnCounter, guessWord, guess);
         // }
            System.out.println(stringRep(turnCounter));
            System.out.println("Letters not in word: " + wrongGuesses);
            turnCounter++;
        }
        System.out.println("You ran out of turns.  The word was: ");
        System.out.println(guessWord);
    }
        catch(FileNotFoundException ex) {
            System.out.println("File error");
        }    
    
    }
    public static boolean isRightLength(String s) {
        if (s.length() != 5) return true;
        return false;
        }
    public static boolean isValidWord(Set dict, String s) {
        if (dict.contains(s)) return false;
		return true; 
    }
    public static boolean gameWon(String tobeG, String guess) {
        if (tobeG.contains(guess)) return true;
		return false;
    }
    public static String checkRez(int turn, String guessWord, String guess) {
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
        return notInWord;
    }
    public static String stringRep(int turn) {
        String graphicalBoard = "";
		for (int i = 0; i < turn+1; i++) {
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
		return graphicalBoard;
    }
    public static void winSeq(int turns) {
        turns = turns+1;
        System.out.println("You won in " + turns + " turns!");
        System.exit(0);
    }
    public static void setUpBoard() {
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
}
