package tuanz1.puzzle.model;

import tuanz1.puzzle.controller.Controller;
import tuanz1.puzzle.utils.Level;

public class Test {
     public static void main(String[] args) {
         // 生成一个4*4 的二维数组，然后将数组打乱，最后生成一个处事拼图
         // 序列，然后使用A*算法还原
         Controller test = new Controller();
         test.setLevel(Level.ULTRA);
         String start = test.randomStart();
//         System.out.println(start);
         System.out.println("初始状态序列: " + start);
         long t3 = System.currentTimeMillis();
         AStar a = new AStar(5);
         a.search(start);
         long t4 = System.currentTimeMillis();
         a.printResult();
         System.out.println("总共耗时：" + (double)(t4 - t3 )/1000 + "s");
         long t0 = System.currentTimeMillis();
         IDAStar i = new IDAStar(5);
         i.search(start);

         long t1 = System.currentTimeMillis();

         i.printResult();
         System.out.println("总共耗时：" + (double)(t1 - t0)/1000 + "s");



     }
}
