package com.opensymphony.webwork.sandbox;

import com.opensymphony.xwork.ActionSupport;

import java.util.Random;

public class Guess extends ActionSupport {
    // START SNIPPET: example
    int guess;

    public String execute() throws Exception {
        int answer = new Random().nextInt(100) + 1;
        int tries = 5;

        while (answer != guess && tries > 0) {
            pause(SUCCESS);

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

        return SUCCESS;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
    // END SNIPPET: guess
}
