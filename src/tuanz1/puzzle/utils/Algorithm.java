package tuanz1.puzzle.utils;

public enum Algorithm {
    ASTART("选择A*算法"),
    IDAStar("选择迭代加深A*算法");
    String s;

    Algorithm(String s) {
        this.s = s;
    }
}