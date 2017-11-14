package tuanz1.puzzle.controller;

import tuanz1.puzzle.model.AStar;
import tuanz1.puzzle.model.IDAStar;
import tuanz1.puzzle.utils.Direction;
import tuanz1.puzzle.utils.Level;
import tuanz1.puzzle.utils.Selector;
import java.util.ArrayList;

public class Controller {
    public int totalSearchNum;
    private Selector selector;
    public void setSelector(Selector selector) {
        this.selector = selector;
        System.out.println("使用算法:" + this.selector);
    }

    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }
    public Controller(){
        this.level = Level.NORMAL;
        this.selector = Selector.ASTART;


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
        switch (selector) {
            case ASTART:
                AStar aStar = new AStar(this.level.getInt());
                result = aStar.search(start);
                totalSearchNum = aStar.getTotalSearch();
                break;
            case IDA:
                IDAStar idaStar = new IDAStar(this.level.getInt());
                result = idaStar.search(start);
                break;
        }
        Direction[] solution = translate(result);
        return solution;
    }
    // 需要重写
    public String randomStart(){
        int size = this.level.getInt();
        int n = 0;
        switch (level){
            case NORMAL: n = 100;
            break;
            case HIGH: n = 250;
            break;
            case ULTRA: n = 200;
            break;
        }
        StringBuffer origin = new StringBuffer();
        char endChar = (char)('A' + (size * size -1));
        for(int i = 0; i< size * size; i++){
            origin.append((char)('A' + i));
        }
        for(int i = 0; i < n; i++){
            int r = (int)(Math.random() *4)+1;
            int t;
            int index;
            char c;
            switch (r){
                case 1:
                    t = origin.toString().indexOf(endChar);
                    if((t -1) < 0 || t % size == 0)
                        break;
                    index = origin.toString().indexOf(endChar);
                    c = origin.charAt(index -1);
                    // 修改序列
                    origin.replace(index, index + 1, c + "");
                    origin.replace(index -1, index, endChar + "");
                    break;
                case 2:
                    t = origin.toString().indexOf(endChar);
                    if((t + 1) >= origin.length() || (t % size) == (size -1 ))
                        break;
                    index = origin.toString().indexOf(endChar);
                    c = origin.charAt(index + 1);
                    // 修改序列
                    origin.replace(index, index + 1, c + "");
                    origin.replace(index+1, index + 2, endChar + "");
                    break;
                case 3:
                    t = origin.toString().indexOf(endChar);
                    if((t - size) < 0 || t < size)
                        break;
                    index = origin.toString().indexOf(endChar);
                    c = origin.charAt(index -size);
                    origin.replace(index, index + 1, c + "");
                    origin.replace(index -size, index + 1 -size, endChar + "");
                    break;
                case 4:
                    t = origin.toString().indexOf(endChar);
                    if((t + size) >= origin.length() || t > size* (size -1))
                        break;
                    index = origin.toString().indexOf(endChar);
                    c = origin.charAt(index +size);
                    origin.replace(index, index + 1, c + "");
                    origin.replace(index +size, index + 1 + size, endChar + "");
                    break;
            }
        }
        System.out.println(origin);
        return origin.toString();
    }

}
