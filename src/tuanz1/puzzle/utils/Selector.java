package tuanz1.puzzle.utils;

public enum Selector{
    ASTART("选择A*算法"),
    BFS("选择广度搜索"),
    IDA("选择迭代加深A*算法");
    String s;
    Selector(String s){
        this.s = s;
    }
}