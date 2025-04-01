package com.example.imagepro.TEST;

import com.example.imagepro.R;
import java.util.ArrayList;
import java.util.List;

public class QuestionData {

    public static List<Question> getLevelOneQuestions() {
        List<Question> questions = new ArrayList<>();

        // Example questions for Level 1
        questions.add(new Question(
                "Which of the following is the sign for 'Hello'?",
                R.drawable.heloo,  // Image 1
                R.drawable.b,  // Image 2
                R.drawable.help,  // Image 3
                R.drawable.friend,  // Image 4
                R.drawable.heloo   // Correct Answer: Image 1
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Please'?",
                R.drawable.heloo,  // Image 1
                R.drawable.please,  // Image 2
                R.drawable.g,  // Image 3
                R.drawable.h,  // Image 4
                R.drawable.please   // Correct Answer: Image 2
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Yes'?",
                R.drawable.thanku,  // Image 1
                R.drawable.iloveyou,  // Image 2
                R.drawable.yes,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.yes   // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'I Love You'?",
                R.drawable.i,  // Image 1
                R.drawable.iloveyou,  // Image 2
                R.drawable.k,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.iloveyou  // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Friend'?",
                R.drawable.friend,  // Image 1
                R.drawable.no,  // Image 2
                R.drawable.k,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.friend   // Correct Answer: Image 3
        ));


        questions.add(new Question(
                "Which of the following is the sign for 'Sorry'?",
                R.drawable.i,  // Image 1
                R.drawable.heloo,  // Image 2
                R.drawable.sorry,  // Image 3
                R.drawable.s,  // Image 4
                R.drawable.sorry   // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Thank You'?",
                R.drawable.i,  // Image 1
                R.drawable.no,  // Image 2
                R.drawable.thanku,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.thanku  // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'No'?",
                R.drawable.no,  // Image 1
                R.drawable.j,  // Image 2
                R.drawable.yes,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.no   // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Goodbye'?",
                R.drawable.i,  // Image 1
                R.drawable.goodbye,  // Image 2
                R.drawable.yes,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.goodbye   // Correct Answer: Image 3
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Help'?",
                R.drawable.friend,  // Image 1
                R.drawable.help,  // Image 2
                R.drawable.no,  // Image 3
                R.drawable.l,  // Image 4
                R.drawable.help   // Correct Answer: Image 3
        ));

        // Add more questions similarly

        return questions;
    }

    public static List<Question> getLevelTwoQuestions() {
        List<Question> questions = new ArrayList<>();

        // Level 2 Questions
        questions.add(new Question(
                "Which of the following is the sign for 'Happy'?",
                R.drawable.bird,
                R.drawable.moon,
                R.drawable.cat,
                R.drawable.dog,
                R.drawable.happy
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Eat'?",
                R.drawable.wind,
                R.drawable.snow,
                R.drawable.river,
                R.drawable.eat,
                R.drawable.eat
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Drink'?",
                R.drawable.sun,
                R.drawable.fish,
                R.drawable.drink,
                R.drawable.bird,
                R.drawable.drink
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Work'?",
                R.drawable.snow,
                R.drawable.work,
                R.drawable.star,
                R.drawable.mountain,
                R.drawable.work
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Sleep'?",
                R.drawable.sleep,
                R.drawable.book,
                R.drawable.car,
                R.drawable.eat,
                R.drawable.sleep
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Family'?",
                R.drawable.family,
                R.drawable.stop,
                R.drawable.phone,
                R.drawable.house,
                R.drawable.family
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Book'?",
                R.drawable.stop,
                R.drawable.phone,
                R.drawable.book,
                R.drawable.tree,
                R.drawable.book
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'School'?",
                R.drawable.eat,
                R.drawable.sleep,
                R.drawable.y,
                R.drawable.school,
                R.drawable.school
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Love'?",
                R.drawable.eat,
                R.drawable.stop,
                R.drawable.love,
                R.drawable.family,
                R.drawable.love
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Stop'?",
                R.drawable.stop,
                R.drawable.eat,
                R.drawable.love,
                R.drawable.o,
                R.drawable.stop
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Car'?",
                R.drawable.family,
                R.drawable.a,
                R.drawable.drink,
                R.drawable.car,
                R.drawable.car
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Phone'?",
                R.drawable.car,
                R.drawable.eat,
                R.drawable.house,
                R.drawable.phone,
                R.drawable.phone
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'House'?",
                R.drawable.house,
                R.drawable.c,
                R.drawable.school,
                R.drawable.tree,
                R.drawable.house
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Water'?",
                R.drawable.water,
                R.drawable.sleep,
                R.drawable.tree,
                R.drawable.stop,
                R.drawable.water
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Tree'?",
                R.drawable.tree,
                R.drawable.yes,
                R.drawable.heloo,
                R.drawable.please,
                R.drawable.tree
        ));

        return questions;
    }

    public static List<Question> getLevelThreeQuestions() {
        List<Question> questions = new ArrayList<>();

        // Level 3 Questions
        questions.add(new Question(
                "Which of the following is the sign for 'Dog'?",
                R.drawable.dog,
                R.drawable.r,
                R.drawable.s,
                R.drawable.t,
                R.drawable.dog
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Cat'?",
                R.drawable.cat,
                R.drawable.u,
                R.drawable.v,
                R.drawable.w,
                R.drawable.cat
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Bird'?",
                R.drawable.bird,
                R.drawable.friend,
                R.drawable.y,
                R.drawable.no,
                R.drawable.bird
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Fish'?",
                R.drawable.thanku,
                R.drawable.yes,
                R.drawable.fish,
                R.drawable.please,
                R.drawable.fish
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Tree'?",
                R.drawable.please,
                R.drawable.goodbye,
                R.drawable.tree,
                R.drawable.yes,
                R.drawable.tree
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Sun'?",
                R.drawable.a,
                R.drawable.moon,
                R.drawable.wind,
                R.drawable.sun,
                R.drawable.sun
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Moon'?",
                R.drawable.fish,
                R.drawable.b,
                R.drawable.moon,
                R.drawable.d,
                R.drawable.moon
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Star'?",
                R.drawable.rain,
                R.drawable.eat,
                R.drawable.star,
                R.drawable.dog,
                R.drawable.star
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Rain'?",
                R.drawable.rain,
                R.drawable.wind,
                R.drawable.dog,
                R.drawable.sorry,
                R.drawable.rain
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Snow'?",
                R.drawable.snow,
                R.drawable.car,
                R.drawable.book,
                R.drawable.tree,
                R.drawable.snow
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'House'?",
                R.drawable.house,
                R.drawable.c,
                R.drawable.school,
                R.drawable.tree,
                R.drawable.house
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Wind'?",
                R.drawable.school,
                R.drawable.tree,
                R.drawable.sorry,
                R.drawable.wind,
                R.drawable.wind
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'River'?",
                R.drawable.river,
                R.drawable.house,
                R.drawable.school,
                R.drawable.school,
                R.drawable.river
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Mountain'?",
                R.drawable.mountain,
                R.drawable.love,
                R.drawable.road,
                R.drawable.phone,
                R.drawable.mountain
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Bridge'?",
                R.drawable.book,
                R.drawable.x,
                R.drawable.bridge,
                R.drawable.work,
                R.drawable.bridge
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Road'?",
                R.drawable.water,
                R.drawable.road,
                R.drawable.love,
                R.drawable.phone,
                R.drawable.road
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Sky'?",
                R.drawable.no,
                R.drawable.friend,
                R.drawable.family,
                R.drawable.sky,
                R.drawable.sky
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'City'?",
                R.drawable.city,
                R.drawable.friend,
                R.drawable.please,
                R.drawable.no,
                R.drawable.city
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'Tree'?",
                R.drawable.goodbye,
                R.drawable.moon,
                R.drawable.tree,
                R.drawable.family,
                R.drawable.tree
        ));

        questions.add(new Question(
                "Which of the following is the sign for 'House'?",
                R.drawable.a,
                R.drawable.iloveyou,
                R.drawable.no,
                R.drawable.house,
                R.drawable.house
        ));

        return questions;
    }
}
