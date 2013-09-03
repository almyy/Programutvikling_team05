package no.hist.gruppe5.pvu.mainroom;

/**
 *
 * @author linnk
 */
public class ScoreHandler {

    private static int[] totalScore;
    public static boolean completedAllLevels;
    public static int total;
    
    /**
     * 
     */
    public static void load() {
        totalScore = new int[5];
        completedAllLevels = false;
        total = 0;
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

    /**
     * Updates totalscore based on the result of current minigame.
     *
     * @param minigame
     * @param score
     * @return if the total score was modified
     */
    public static boolean updateScore(int minigame, int score) {
        if (minigame < totalScore.length) {
            totalScore[minigame] = score;
            total+=score;
            if (checkScore()) {
                completedAllLevels = true;
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @return a grade based on the total score.
     */
    public static Character getGrade() {
        if(total>90){
            return 'A';
        }else if(total>80){
            return 'B';
        }else if(total>70){
            return 'C';
        }else if(total>60){
            return 'D';
        }else{
            return 'E';
        }
    }
}
