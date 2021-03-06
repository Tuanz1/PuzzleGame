package tuanz1.puzzle.model;

import tuanz1.puzzle.utils.GlobalFunctions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A*算法实现拼图还原
 * @author tuanz1
 */
public class AStar {


    /**
     * 总共检索的次数
     */
    private int searchCount;
    /**
     * 拼图的大小　默认4 * 4;
     */
    public int getSearchCount() {
        return searchCount;
    }
    private int size = 4;
    /**
     * 控制算法是否继续检索，false则表示检测到解决方案
     */
    private boolean loop=true;

    /**
     *　拼图的开始状态序列
     * 用A-Z序列表示（由于十五数码超过10，会导致一个拼图块占两位，所以使用大写字母替换)
     */

    private String startStatus;

    /**
     * 拼图的结束状态序列
     */
    private String endStatus = new String("");
    /**
     * 空白拼图块对应拼图序列中的字符
     */
    private char blankChar;
    /**
     * 用于帮助计算当前拼图状态的类
     */
    private PuzzleStatusAssitant PSA;
    /**
     * 关闭列表：储存已经检索过的拼图状态
     */
    private HashMap<String, String> closeList = new HashMap<String, String>();
    /**
     * 开放列表：存储需要检测的拼图状态
     */
    private PriorityQueue<PuzzleStatus> openList;

    public AStar(int size){
        this.size = size;
        endStatus = GlobalFunctions.initStartStatus(size);
        blankChar = endStatus.charAt(size * size -1);
        PSA = new PuzzleStatusAssitant(size, endStatus);
        openList = new PriorityQueue<>(Comparator.comparing(p->p.weight));

    }

    /**
     * 搜索当前状态下的复原路径
     * @param start 拼图状态序列
     * @return 拼图还原路径
     */
    public ArrayList<String> search(String start){
        this.startStatus = start;
        PuzzleStatus begin = new PuzzleStatus(startStatus, "", 0, 0);
        openList.offer(begin);
        while(loop){
            if (openList.peek() != null){
                PuzzleStatus head = openList.poll();
                expand(head);
            }
        }
        ArrayList<String> solution = new ArrayList<String>();
        String head = new String(endStatus);
        while (true){
            if (head.equals(""))
                break;
            solution.add(head);
            head = new String(closeList.get(head));
        }
        searchCount = closeList.size() + openList.size();
//        printResult();  //测试时候用的
        return solution;
    }
    /**
     * 由当前的拼图状态拓展出子状态
     * @param p　当前拼图状态
     */
    private void expand(PuzzleStatus p){
        closeList.put(p.cur, p.pre);
        int blankPos = p.cur.indexOf(blankChar);
        // 空白拼图块向上移动
        if (blankPos > size - 1){
            move(p, -size);
        }
        // 空白拼图块向下移动
        if (blankPos < ((size - 1) * size)){
            move(p, size);
        }
        // 空白拼图块向左移动
        if (blankPos % size > 0){
            move(p, -1);
        }
        // 空白拼图块向右移动
        if (blankPos % size < (size-1)) {
            move(p, 1);
        }
    }

    /**
     * 拼图空白格子移动，生成子拼图状态
     * @param p　父拼图状态
     * @param k　移动方向(由于拼图状态装换成字符串，所以移动方向用int表示）
     */
    private void move(PuzzleStatus p, int k){
        // 提取父状态的拼图序列
        StringBuffer parent = new StringBuffer(p.cur);
        GlobalFunctions.shuffle(parent, k, blankChar);
        String child = parent.toString();
        //　检测是否已经完成拼图
        if (child.equals(endStatus)){
            closeList.put(endStatus, p.cur);
            loop = false;
        }
        // 检测该子状态是否已经检测过
        if (closeList.containsKey(child))
            return;
        PuzzleStatus childStatus = new PuzzleStatus(child, p.cur, p.depth + 1, PSA.getWeight(child, p.depth + 1));
        openList.offer(childStatus);
    }

    /**
     * 测试函数，用来打印结果
     */
    public void printResult(){
        int count = 0;
        String head = new String(endStatus);
        System.out.println("A* 算法结果:");
        while (true){
            if (head.equals("")) {
                break;
//            System.out.println("第"+count+ "步");
            }
//            System.out.println(head);
            head = new String(closeList.get(head));
            count++;
        }
        System.out.println("总共需要走" + (count - 1) + "步");
        System.out.println("总共检索了" + (closeList.size() + openList.size()) + "种状态");
    }
}
