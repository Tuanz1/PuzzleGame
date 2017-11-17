package tuanz1.puzzle.model;

import java.util.ArrayList;

/**
 * 如何判断不会走回去，
 */
public class IDAStar {
    private int count = 0;
    PuzzleStatusAssitant PSA;
    /**
     * 代表拼图最后一块的字符
     */
    private char blankChar;
    /**
     * 最大的步骤，即为初始状态下曼哈顿值
     */
    private int limitDepth;
    /**
     * 初始状态
     */
    private String startStatus;
    /**
     * 结束状态
     */
    private String endStatus = new String();
    private boolean flag = false;
    private int size;
    private String[] solution;
    public IDAStar(int size){
        this.size = size;
        for(int i = 0; i < size * size; i++){
            endStatus += (char)('A' + i);
        }
        blankChar = endStatus.charAt(size * size -1);
        PSA  = new PuzzleStatusAssitant(size, endStatus);
    }
    /**
     * 深度遍历
     * @param cur 当前搜索状态
     * @param depth 搜索深度
     * @param pre_d 前一次搜索的方向
     */

    private void DFS(String cur, int depth, int pre_d){
        count++;
        // 已经有解
        if (flag)
            return;
        if (cur.equals(endStatus)){
            flag = true;
            limitDepth = depth;
            return;
        }
        if (depth >= limitDepth)
            return;
        int blankPos = cur.indexOf(blankChar);
        if (blankPos > size - 1){
            move(cur, -size, pre_d, depth);
        }
        // 空白拼图块向下移动
        if (blankPos < ((size - 1) * size)){
            move(cur, size, pre_d, depth);
        }
        // 空白拼图块向左移动
        if (blankPos % size > 0){
            move(cur, -1, pre_d, depth);
        }
        // 空白拼图块向右移动
        if (blankPos % size < (size-1)) {
            move(cur, 1, pre_d, depth);
        }
    }
    private void move(String cur, int d, int pre_d, int depth){
        // 是否与上一次反向
        if (d + pre_d == 0)
            return;
        // 可以移动
        // 获得与空白拼图块交换的拼图块对应位置
        int blankPos = cur.indexOf(blankChar);
        StringBuffer parent = new StringBuffer(cur);
        char c = parent.charAt(blankPos + d);
        // 修改序列
        parent.replace(blankPos, blankPos + 1, c + "");
        parent.replace(blankPos + d, blankPos + d + 1, blankChar + "");
        String child = parent.toString();
        int p = PSA.calMhtDistance(child);
        // 是否需要剪枝
        if ( p + depth <=  limitDepth && !flag){
            solution[depth] =  child;
            DFS(child, depth + 1, d);
            if (flag)
                return;
        }

    }
    public ArrayList<String> search(String start){
        limitDepth = PSA.calMhtDistance(start);
        solution = new String[100];
        while (!flag && limitDepth < 100){
            DFS(start, 0, 0);
            if (!flag) {
                System.out.println("遍历深度" + limitDepth + "无解");
                limitDepth++;
            }
        }

        ArrayList<String> result = new ArrayList<>();

        for (int i = limitDepth -1; i >=0 ; i--){
            result.add(solution[i]);
        }
        result.add(start);

        return result;
    }

    /**
     * 打印结果
     */
    public void printResult(){
        if (flag){
            System.out.println("IDA* 算法:" );
            System.out.println("总共检索了" + count + "次");
            System.out.println("总共需要走" + limitDepth + "步");
        }else{
            System.out.println("无解");
        }
    }

}
