package com.blackdot.habits.Models;

public class Faq {
    String question, answer;

    public Faq(String question, String answer) {
        setQuestion(question);
        setAnswer(answer);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
