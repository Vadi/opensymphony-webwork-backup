package com.opensymphony.webwork.showcase;

import com.opensymphony.xwork.ActionSupport;

import java.util.Random;

// START SNIPPET: example
public class Guess extends ActionSupport {
    int guess;

    public String execute() throws Exception {
        int answer = new Random().nextInt(100) + 1;
        int tries = 5;

        while (answer != guess && tries > 0) {
            pause(Action.SUCCESS);

            if (guess > answer) {
                addFieldError("guess", "Too high!");
            } else if (guess < answer) {
                addFieldError("guess", "Too low!");
            }

            tries--;
        }

        if (answer == guess) {
            addActionMessage("You got it!");
        } else {
            addActionMessage("You ran out of tries, the answer was " + answer);
        }

        return Action.SUCCESS;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
}
// END SNIPPET: example
