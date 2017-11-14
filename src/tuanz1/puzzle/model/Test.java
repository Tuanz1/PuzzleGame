package tuanz1.puzzle.model;

import tuanz1.puzzle.controller.Controller;
import tuanz1.puzzle.utils.Level;

public class Test {
     public static void main(String[] args) {
         // 生成一个4*4 的二维数组，然后将数组打乱，最后生成一个处事拼图序列，然后使用A*算法还原
         Controller test = new Controller();
         test.setLevel(Level.HIGH);
         String origin = test.randomStart();
         System.out.println("origin:" + origin);
         IDAStar i = new IDAStar(4);
         i.search("BCJHMPFGNAELIOKD");
         AStar a = new AStar(4);
         a.search("BCJHMPFGNAELIOKD");
     }
}
