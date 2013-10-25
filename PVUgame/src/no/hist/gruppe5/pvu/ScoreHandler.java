package no.hist.gruppe5.pvu;

/**
 *
 * @author linnk
 */
public class ScoreHandler {

    public static final int VISION = 0;
    public static final int REQ = 1;
    public static final int SEQ = 2;
    public static final int UMLBLOCKS = 3;
    public static final int CODE = 4;
    
    public static final int LOCKED = 0;
    public static final int QUIZ_NEEDED = 1;
    public static final int QUIZ_PASSED = 2; 
    
    private static float[] totalScore;
    private static boolean[] quizzesCompleted;
    private static boolean completedAllLevels;
    private static float total;

    /**
     *
     */
    public static void load() {
        totalScore = new float[5];
        completedAllLevels = false;
        total = 0;
        quizzesCompleted = new boolean[5];
    }

    /**
     * Checks if a minigame is completed.
     *
     * @param minigame
     * @return
     */
    public static boolean isMinigameCompleted(int minigame) {
        try {
            if (totalScore[minigame] > 0f) {
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static int getQuizzesCompleted() {
        for(int i = 0; i < quizzesCompleted.length; i++){
            if(!quizzesCompleted[i]){
                return i; 
            }
        }
        return quizzesCompleted.length+1;
    }

    /**
     * Returns the score of a certain minigame
     *
     * @param minigame Constant, e.g ScoreHandler.VISION
     * @return The percentage of the minigame, 0-1f
     */
    public static float getMiniGameGrade(int minigame) {
        try {
            return totalScore[minigame];
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public static int numberOfGamesCompleted() {
        for (int i = 0; i < totalScore.length; i++) {
            if (totalScore[i] == 0f) {
                return i;
            }
        }
        return totalScore.length;
    }

    /**
     * Checks if all minigames are completed.
     *
     * @return true/false
     */
    private static boolean checkScore() {
        for (int i = 0; i < totalScore.length; i++) {
            if (totalScore[i] == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void updateQuizzesCompleted(int number){
        quizzesCompleted[number]=true; 
    }

    /**
     * Updates totalscore based on the result of current minigame.
     *
     * @param miniGame
     * @param percent
     * @return if the total score was modified
     */
    public static boolean updateScore(int miniGame, float percent) {
        if(percent > 1 || percent < 0) return false;
        if (miniGame < totalScore.length) {
            totalScore[miniGame] = percent;
            if (checkScore()) {
                completedAllLevels = true;
            }
            return true;
        }
        return false;
    }

    public static float getTotalScore() {
        float total = 0;
        for(float f : totalScore) {
            total += f;
        }
        return total;
    }
    
    public static void setNoQuiz(){
        for(int i = 0; i < quizzesCompleted.length; i++){
            quizzesCompleted[i]=true; 
        }
    }

    /**
     *
     * @return a grade based on the total score.
     */
    public static Character getGrade() {
        if(numberOfGamesCompleted() == 0) return 'F'; // No need to calculate further

        float average = getTotalScore() / (float) numberOfGamesCompleted();
        PVU.log(ScoreHandler.class, "Total score is " + getTotalScore() + ", average is " + average + ". Games completed is " + numberOfGamesCompleted());

        if (average > 0.9) {
            return 'A';
        } else if (average > 0.8) {
            return 'B';
        } else if (average > 0.7) {
            return 'C';
        } else if (average > 0.6) {
            return 'D';
        } else if (average > 0.4) {
            return 'E';
        } else {
            return 'F';
        }
    }
}
