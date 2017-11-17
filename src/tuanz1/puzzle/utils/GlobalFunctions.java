package tuanz1.puzzle.utils;
public final class GlobalFunctions {
    public static void shuffle(StringBuffer origin, int d, char blank){
        int blankPos = origin.toString().indexOf(blank);
        char c = origin.charAt(blankPos + d);
        origin.replace(blankPos, blankPos + 1, c + "");
        origin.replace(blankPos + d, blankPos + 1 + d, blank + "");
    }
    public static String initStartStatus(int size){
        String startStatus = new String();
        for(int i = 0; i< size * size; i++){
            startStatus += ((char)('A' + i));
        }
        return startStatus;
    }
}
