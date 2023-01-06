/*
Title: Flashcard
Filename: MainActivity.java
Author: Prajwal Pisal
Date: 12/01/21
Description: MVC-based app for Android to display random questions and display their answer in toast. User can mark the questions as "Mark Complete"
 so that same question will not get repeated. Once all questions are completed user acn rest the app by clicking on the "Reset Cards" button.
 */

package csumb.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnShowAnswer;
    private Button btnMarkComplete;
    private Button btnRandomCard;
    private Button btnResetCards;

    private TextView QuestionTV;

//    private List<Flashcard> QuestionsList = Arrays.asList(
//            new Flashcard(R.string.question_1, "Australia"),
//            new Flashcard(R.string.question_2, "Slytherin"),
//            new Flashcard(R.string.question_compiler, "Compiler")
//            );
    private ArrayList<Flashcard> QuestionsList = new ArrayList<>();
    private ArrayList<Integer> numbers = new ArrayList<>(); // ArrayList to store number of flashcards


    private int currentQuestionNumber; //Index of current flashcard
    int remainingFlashCard;
    private Flashcard currentQuestion;
    private Flashcard display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        QuestionsList.add(new Flashcard(R.string.question_1, "Australia"));
        QuestionsList.add(new Flashcard(R.string.question_2, "Slytherin"));
        QuestionsList.add(new Flashcard(R.string.question_compiler, "Compiler"));
        QuestionsList.add(new Flashcard(R.string.question_rdbmbs, "Relational database management system"));
        QuestionsList.add(new Flashcard(R.string.question_os, "Operating System"));
        QuestionsList.add(new Flashcard(R.string.question_https, "Hypertext Transfer Protocol Secure"));

        remainingFlashCard = QuestionsList.size(); // Count of remaining flashcards

        for(int i=0;i<QuestionsList.size();i++){
            numbers.add(i);
        }

        // Assigning id's to buttons
        btnShowAnswer = findViewById(R.id.btnShowAnswer);
        btnMarkComplete = findViewById(R.id.btnMarkComplete);
        btnRandomCard = findViewById(R.id.btnRandomCard);
        btnResetCards = findViewById(R.id.btnResetCards);

        // Assigning id to textview of the question
        QuestionTV = findViewById(R.id.Question_text);

        display = pickRandomFlashCard();
        String temp = getResources().getString(display.getQuestionId());
        QuestionTV.setText(temp);


        // Random Flash Card Button
        btnRandomCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display = pickRandomFlashCard();
                if(display!=null) {
                    String temp = getResources().getString(display.getQuestionId());
                    QuestionTV.setText(temp);
                }
                else{
                    QuestionTV.setText("You Win");
                }
            }
        });

        // Show Answer Button
        btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(display);
            }
        });

//        Button markComplete = binding.btnMarkComplete;
//        markComplete.setOnClickListener(v -> markCorrect());

        // Mark Complete Button
        btnMarkComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remainingFlashCard==1){ // true value 1
                    markCorrect();
                    QuestionTV.setText("Cards Finished! You're Ready!");
                }
                else{
                    display = markCorrect();
                    if(display!=null) {
                        String temp = getResources().getString(display.getQuestionId());
                        QuestionTV.setText(temp);
                    }

                }
            }
        });

        // Reset Cards Button
        btnResetCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display = resetCards();
                if(display!=null) {
                    String temp = getResources().getString(display.getQuestionId());
                    QuestionTV.setText(temp);
                }
            }
        });
    }

    Flashcard pickRandomFlashCard(){
        Random rand = new Random();
        if(remainingFlashCard>0){
            int random = rand.nextInt(numbers.size());
            int randomElement = numbers.get(random);
            numbers.removeAll(Arrays.asList(randomElement));
            Flashcard ques = QuestionsList.get(randomElement);
            if(!ques.isComplete()){
                Flashcard toBeReturned = ques;
                QuestionsList.get(random).setComplete(true);
                remainingFlashCard--;
                return toBeReturned;
            }
            else{
                markCorrect();
            }

        }
        return null;
    }

    void showAnswer(Flashcard flash){
        Toast toast = Toast.makeText(MainActivity.this,flash.getAnswerId(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }

    Flashcard markCorrect() {
        if (remainingFlashCard == 0) { //true value 1
            btnShowAnswer.setEnabled(false);
            btnRandomCard.setEnabled(false);
            btnMarkComplete.setEnabled(false);
        } else {
            display.setComplete(true);
            remainingFlashCard--;
            Random rand = new Random();
            if (remainingFlashCard > 0) { //true value 1
                int random = rand.nextInt(numbers.size());
                int randomElement = numbers.get(random);
                numbers.removeAll(Arrays.asList(randomElement));
                Flashcard ques = QuestionsList.get(randomElement);
                if (!ques.isComplete()) {
                    Flashcard toBeReturned = ques;
                    QuestionsList.get(random).setComplete(true);
                    remainingFlashCard--;
                    return toBeReturned;
                }
            }
        }
        return null;
    }

    Flashcard resetCards(){
        for(Flashcard f: QuestionsList){
            f.setComplete(false);
        }
        btnShowAnswer.setEnabled(true);
        btnRandomCard.setEnabled(true);
        btnMarkComplete.setEnabled(true);

        remainingFlashCard = QuestionsList.size();

        numbers.clear();
        for(int i=0;i<QuestionsList.size();i++){
            numbers.add(i);
        }

        Random rand = new Random();
        if (remainingFlashCard > 1) {
            int random = rand.nextInt(numbers.size());
            int randomElement = numbers.get(random);
            numbers.removeAll(Arrays.asList(randomElement));
            Flashcard ques = QuestionsList.get(randomElement);
            if (!ques.isComplete()) {
                Flashcard toBeReturned = ques;
                QuestionsList.get(random).setComplete(true);
                remainingFlashCard--;
                return toBeReturned;
            }
        }
        return null;
    }
}