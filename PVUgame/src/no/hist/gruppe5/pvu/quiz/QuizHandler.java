package no.hist.gruppe5.pvu.quiz;

import no.hist.gruppe5.pvu.ScoreHandler;

/**
 *
 * @author Rino
 */
public class QuizHandler {

    public static int[] totalCorrect;
    public static int quizzesCompleted;
    public static final int PASS_GRADE = 3;
    
    public static final int LOCKED = 0;
    public static final int QUIZ_NEEDED = 1;
    public static final int QUIZ_PASSED = 2;
    
    

    public static void load() {
        totalCorrect = new int[5];
        quizzesCompleted = 0;
    }

    public static boolean miniGameUnlocked(int miniGame) {
        return totalCorrect[miniGame] >= PASS_GRADE;
    }

    public static boolean miniGameQuizAvailable(int miniGame) {
        return (miniGame == 0) ? true : ScoreHandler.isMinigameCompleted(miniGame - 1);
    }

    public static void updateQuizScore(int score, int miniGame) {
        if (score > 0) {
            totalCorrect[miniGame] = score;
            for (int i = 0; i < totalCorrect.length; i++) {
                if (totalCorrect[i] == 0) {
                    quizzesCompleted = i;
                }
            }
        }
    }
}
