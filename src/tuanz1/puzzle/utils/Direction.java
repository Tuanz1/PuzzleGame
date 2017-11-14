package tuanz1.puzzle.utils;

public enum Direction {
    TOP("向上移动"),
    BOTTOM("向下移动"),
    LEFT("向左移动"),
    RIGHT("向右移动"),
    OHTER("无效的点击");
    private String s;
    Direction(String s){
        this.s = s;
    }
}
