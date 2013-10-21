package no.hist.gruppe5.pvu.quiz;

import no.hist.gruppe5.pvu.ScoreHandler;

/**
 *
 * @author Rino
 */
public class QuizHandler {

    public static int[] totalCorrect;
    public static int quizesCompleted;
    public static final int PASS_GRADE = 3;

    public static void load() {
        totalCorrect = new int[5];
        quizesCompleted = 0;
    }

    public static boolean miniGameUnlocked(int miniGame) {
        return totalCorrect[miniGame] >= PASS_GRADE;
    }

    public static boolean miniGameQuizAvailable(int miniGame) {
        return (miniGame==0) ? true : ScoreHandler.isMinigameCompleted(miniGame-1); 
    }
}
