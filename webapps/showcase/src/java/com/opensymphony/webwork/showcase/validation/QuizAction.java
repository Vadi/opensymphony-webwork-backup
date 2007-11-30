package com.opensymphony.webwork.showcase.validation;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author Patrick Lightbody (plightbo at gmail dot com)
 */

// START SNIPPET: quizAction 

public class QuizAction extends ActionSupport {
    int minAge = 13;
    int maxAge = 19;

    String name;
    int age;
    String answer;

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

// END SNIPPET: quizAction

