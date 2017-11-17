package tuanz1.puzzle.controller;

import tuanz1.puzzle.model.AStar;
import tuanz1.puzzle.model.IDAStar;
import tuanz1.puzzle.utils.Direction;
import tuanz1.puzzle.utils.GlobalFunctions;
import tuanz1.puzzle.utils.Level;
import tuanz1.puzzle.utils.Algorithm;
import java.util.ArrayList;

/**
 * 拼图游戏的控制器
 */
public class Controller {
    private char blankChar;
    private int searchCount;
    public int getSearchCount() {
        return searchCount;
    }

    private Algorithm algorithm;
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        System.out.println("使用算法:" + this.algorithm);
    }

    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }
    public Controller(){
        this.level = Level.NORMAL;
        this.algorithm = Algorithm.ASTART;
    }

    public Level getLevel() {
        return level;
    }

    /**
     * 将状态序列翻译成方向
     * @param result　输入状态序列集合
     * @return　翻译后的方向数组(从最后开始读取)
     */
    private Direction[] translate(ArrayList<String> result){
        int level  = this.level.getInt();
        char end = (char)('A' + level * level - 1);
        int length = result.size() - 1;
        Direction[] solution = new Direction[length];
        for(int i = 0; i < length; i++){
            String cur = result.get(i);
            String pre = result.get(i + 1);
            int n1 = cur.indexOf(end);
            int n2 = pre.indexOf(end);
            int d = n1 - n2;
            if (d == -1)
                 solution[i] = Direction.LEFT;
            else if (d == 1)
                solution[i] = Direction.RIGHT;
            else if (d < -1)
                solution[i] = Direction.TOP;
            else if (d > 1)
                solution[i] = Direction.BOTTOM;
        }
        return solution;
    }

    public Direction[] getSolution(String start) {
        ArrayList<String> result = new ArrayList<>();
        switch (algorithm) {
            case ASTART:
                AStar aStar = new AStar(this.level.getInt());
                result = aStar.search(start);
                searchCount = aStar.getSearchCount();
                break;
            case IDAStar:
                IDAStar idaStar = new IDAStar(this.level.getInt());
                result = idaStar.search(start);
                searchCount = idaStar.getSearchCount();
                break;
        }
        Direction[] solution = translate(result);
        return solution;
    }


    /**
     * 获得一个随机的拼图状态
     * @return 随机开始拼图序列
     */
    public String randomStart(){
        int size = this.level.getInt();
        int n = 0; //随机次数
        // 调整随机难度修改下面的数字
        switch (level){
            case NORMAL: n = 100;
            break;
            case HIGH: n = 200;
            break;
            case ULTRA: n = 300;
            break;
        }

        StringBuffer origin = new StringBuffer(GlobalFunctions.initStartStatus(size));
        blankChar = (char)('A' + (size * size -1));

        for(int i = 0; i < n; i++){
            // 随机数，用来随机选择一个方向(1:左；2:右；3:上；4:下)
            int r = (int)(Math.random() *4)+1;
            // 暂存空白方块位置，用于判断是否可以移动
            int t;
            int index;
            char c;
            t = origin.toString().indexOf(blankChar);
            switch (r){
                case 1:
                    if((t -1) < 0 || t % size == 0)
                        break;
                    GlobalFunctions.shuffle(origin, -1, blankChar);
                    break;
                case 2:
                    if((t + 1) >= origin.length() || (t % size) == (size -1 ))
                        break;
                    GlobalFunctions.shuffle(origin, 1, blankChar);
                    break;
                case 3:
                    if((t - size) < 0 || t < size)
                        break;
                    GlobalFunctions.shuffle(origin, -size, blankChar);
                    break;
                case 4:
                    if((t + size) >= origin.length() || t > size* (size -1))
                        break;
                    GlobalFunctions.shuffle(origin, size, blankChar);
                    break;
                default:
                    break;
            }
        }
        return origin.toString();
    }

}
