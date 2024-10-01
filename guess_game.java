import java.util.Scanner;
import java.util.Random;


class guess_game {
    //private int difficult;

    public guess_game() {
        
    }

    public void welcome_player() {
        System.out.println("Welcome to the Number Guessing Game!");
    }

    public void give_menu() {
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have 5 chances to guess the correct number.");
        System.out.println();
        System.out.println("Please select the difficulty level:");
        System.out.println("1. Easy (10 chances)");
        System.out.println("2. Medium (5 chances)");
        System.out.println("3. Hard (3 chances)");
        System.out.println();
    }
    public static void main(String[] args) {

        Scan scanner = new Scan();
        guess_game guess_game = new guess_game();
        guess_game.welcome_player();
        int keep_playing = 1;
        do {
            guess_game.give_menu();
            Guess guess = new Guess(scanner.map_difficult_number_of_tries(scanner.select_difficult()));
            guess.guessing_number();
            keep_playing = scanner.keep_playing();
        } while(keep_playing == 1);
        
    }
}


class Scan {
    Scanner myObj = new Scanner(System.in);

    public int keep_playing() {
        System.out.println("If you want to keep playing:");
        System.out.println("1. Yes");
        System.out.println("Otherwise. No");
        return myObj.nextInt();
    }

    /** 
     * requires: True
     * @return a number between 1 and 3, both incluided
    */
    public int select_difficult() {
        int number;
        do {
            System.out.print("Enter your choice: ");
            number = myObj.nextInt();
        } while(number <= 0 || number > 3);
        return number;
    }

    /**
     * 
     * @param difficult must be between 1 and 3, both incluided
     * @return the number of tries for the given difficult
     */
    public int map_difficult_number_of_tries(int difficult) {
        int number;
        if (difficult == 1) {
            number = 10;
        } else if (difficult == 2) {
            number = 5;
        } else  {
            number = 3;
        }
        return number;
    }

    /** 
     * requires: True
     * @return a number between 1 and 100, both incluided
    */
    public int ask_number() {
        int number;
        do {
            System.out.print("Enter your guess: ");
            number = myObj.nextInt();
        } while(number <= 0 || number > 100);
        return number;
    }

}

class Guess {
    private int tries;
    private int number_to_guess;
    private int guessings;

    public Guess(int tries) {
        Random rand = new Random();

        this.tries = tries;
        this.guessings = 1;
        this.number_to_guess = rand.nextInt(99) + 1;
    }

    public boolean guessing_number() {
        int user_input;
        Scan scanner = new Scan();
        System.out.println("The number of tries" + this.tries);
        while (tries > 0) {
            user_input = scanner.ask_number();
            if (user_input == this.number_to_guess) {
                System.out.println("Congratulations! You guessed the correct number in " + this.guessings + " attempts.");
                return true;
            } else if (user_input > this.number_to_guess) {
                System.out.println("Incorrect! The number is less than " + user_input);
            } else {
                System.out.println("Incorrect! The number is greater than " + user_input);
            }
            tries = tries - 1;
            guessings++;
            System.out.println();
        }
        System.out.println("The number was");
        System.out.println(this.number_to_guess);
        return false;
    }
}