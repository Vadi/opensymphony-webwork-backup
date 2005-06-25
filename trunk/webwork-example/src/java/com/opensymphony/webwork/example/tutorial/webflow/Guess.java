package com.opensymphony.webwork.example.tutorial.webflow;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

/**
 * User: plightbo
 * Date: Jun 25, 2005
 * Time: 10:12:25 AM
 */
public class Guess extends ActionSupport implements SessionAware, ModelDriven {
    private Map session;
    private int guess;

    public void setSession(Map session) {
        this.session = session;
    }

    public String doDefault() throws Exception {
        session.put("guess", new GuessState());

        return INPUT;
    }

    public String execute() throws Exception {
        GuessState state = (GuessState) getModel();
        if (state.guess(guess)) {
            return SUCCESS;
        } else if (state.isGameOver()) {
            return ERROR;
        } else {
            return INPUT;
        }
    }

    public Object getModel() {
        return session.get("guess");
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    class GuessState implements Serializable {
        private int random;
        private int guesses;

        public GuessState() {
            guesses = 5;
            random = new Random().nextInt(100) + 1;
        }

        public int getRandom() {
            return random;
        }

        public int getGuesses() {
            return guesses;
        }

        public boolean isGameOver() {
            return (guesses <= 0);
        }

        public boolean guess(int guess) {
            if (random == guess) {
                return true;
            } else {
                guesses--;

                return false;
            }
        }
    }
}
