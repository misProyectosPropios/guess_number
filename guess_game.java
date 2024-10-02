import java.util.Scanner;
import java.time.Instant;
import java.util.Random;
import java.time.Duration;
import java.time.LocalTime;

class guess_game {
    private int[] high_score = new int[3];
 
    public guess_game() {
        this.high_score[0] = -1;
        this.high_score[1] = -1;
        this.high_score[2] = -1;
    }

    public int get_high_score(int difficult) {
        if (difficult >= 0 && difficult <= 2) {
            return this.high_score[difficult];
        } 
        return -1;
    }

    public void set_high_score(int difficult, int new_possible_high_score) {
        if (difficult >= 0 && difficult <= 2) {
            if (this.high_score[difficult] == -1) {
                this.high_score[difficult] = new_possible_high_score;
            } else if (this.high_score[difficult] > new_possible_high_score) {
                this.high_score[difficult] = new_possible_high_score;
            }
            
        } 
    }

    private boolean some_has_high_score() {
        boolean res = false;
        for(int i = 0; i < 3; i++) {
            if (this.high_score[i] != -1) {
                res = true;
            }
        }
        return res;
    }

    public void show_high_score() {
        if (some_has_high_score()) {
            System.out.println("The high_scores are: ");
            for(int i = 0; i < 3; i++) {
                if (this.high_score[i] != -1) {
                    System.out.println(i + ". " + this.high_score[i]);
                }
                
            }
            System.out.println();
        }
    }

    public void welcome_player() {
        System.out.println("Welcome to the Number Guessing Game!");
    }

    public void give_menu() {
        System.out.println("I'm thinking of a number between 1 and 100.");
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
            guess_game.show_high_score();
            guess_game.give_menu();
            int difficult = scanner.select_difficult();
            Guess guess = new Guess(scanner.map_difficult_number_of_tries(difficult));
            int number_of_guess = guess.guessing_number();
            if (number_of_guess != -1) {
                guess_game.set_high_score(difficult, number_of_guess);
            }
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
        System.out.print("Enter your choice: ");
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
    private LocalTime time = null;

    public Guess(int tries) {
        Random rand = new Random();

        this.tries = tries;
        this.guessings = 1;
        this.number_to_guess = rand.nextInt(99) + 1;
    }

    public void setActualTime() {
        this.time = LocalTime.now();
    }

    public void timePassed() {
        if (this.time != null) {
            System.out.print("The time that took to find the number was ");
            Duration duration = Duration.between(this.time, LocalTime.now());
            long seconds = duration.getSeconds(); 
            System.out.println(seconds + " seconds.");

            this.time = null;
        }
    }

    public int guessing_number() {
        int user_input;
        Scan scanner = new Scan();
        this.setActualTime();
        while (tries > 0) {
            user_input = scanner.ask_number();
            if (user_input == this.number_to_guess) {
                System.out.println("Congratulations! You guessed the correct number in " + this.guessings + " attempts.");
                this.timePassed();
                return this.guessings;
            } else if (user_input > this.number_to_guess) {
                System.out.println("Incorrect! The number is less than " + user_input);
            } else {
                System.out.println("Incorrect! The number is greater than " + user_input);
            }
            tries = tries - 1;
            guessings++;
            System.out.println();
        }
        System.out.println("The number was" + this.number_to_guess);
        System.out.println();
        return -1;
    }
}