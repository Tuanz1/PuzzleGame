package tuanz1.puzzle.utils;

public enum Level{
    NORMAL(3),
    HIGH(4),
    ULTRA(5);
    int level;
    Level(int l){
        this.level = l;
    }
    public static String getName(int i){
        switch (i){
            case 3:return "normal";
            case 4: return "high";
            case 5: return "ultra";
        }
        return "";
    }
    public int getInt() {
        return level;
    }
}