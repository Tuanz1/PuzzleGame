package tuanz1.puzzle.utils;

public enum Algorithm {
    ASTART("A*算法"),
    IDAStar("迭代加深A*算法");
    String s;
    Algorithm(String s) {
        this.s = s;
    }
}