/*
Title: Flashcard
Filename: Flashcard.java
Author: Prajwal Pisal
Date: 12/01/21
Description: MVC-based app for Android to display random questions and display their answer in toast. User can mark the questions as "Mark Complete"
 so that same question will not get repeated. Once all questions are completed user acn rest the app by clicking on the "Reset Cards" button.
 */

package csumb.flashcard;

public class Flashcard {
    private int questionId;
    private String answerId;
    private boolean complete;

    public Flashcard(int questionId, String answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.complete = false;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
