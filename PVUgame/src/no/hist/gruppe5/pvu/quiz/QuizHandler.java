package no.hist.gruppe5.pvu.quiz;

import no.hist.gruppe5.pvu.ScoreHandler;
import no.hist.gruppe5.pvu.Settings;

/**
 *
 * @author Rino
 */
public class QuizHandler {

    public static int[] mTotalCorrect;
    public static int quizzesCompleted;
    public static int completedMiniGames;
    
    public static final int PASS_GRADE = 3;
    
    public static final int LOCKED = 0;
    public static final int QUIZ_NEEDED = 1;
    public static final int QUIZ_PASSED = 2;
    
    

    public static void load() {
        mTotalCorrect = new int[5];
        completedMiniGames = 0;
        quizzesCompleted = 0;
        if(Settings.PLAYER_NAME.toLowerCase().equals("noquiz")){
            for(int i = 0; i < 5; i++){
                mTotalCorrect[i]=5;
            }
        }
    }

    public static boolean miniGameUnlocked(int miniGame) {
        return mTotalCorrect[miniGame] >= PASS_GRADE;
    }

    public static boolean miniGameQuizAvailable(int miniGame) {
        return (miniGame == 0) ? true : ScoreHandler.isMinigameCompleted(miniGame - 1);
    }
    
    public static void updateFinishedMiniGame(){
        completedMiniGames++;
    }

    public static void updateQuizScore(int score, int miniGame) {
        if (score > 0) {
            int previousScore = 0; 
            mTotalCorrect[miniGame] = score;
            for (int i = 0; i < mTotalCorrect.length; i++) {
                if (mTotalCorrect[i] == 0 && previousScore>=PASS_GRADE) {
                    quizzesCompleted = i;
                    break;
                }
                previousScore = mTotalCorrect[i];
            }
        }
    }
}
