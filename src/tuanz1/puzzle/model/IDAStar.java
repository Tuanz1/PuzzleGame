package tuanz1.puzzle.model;

import java.util.ArrayList;

/**
 * 如何判断不会走回去，
 */
public class IDAStar {
    PuzzleStatusAssitant PSA;
    private int limit = 20;
    /**
     * 代表拼图最后一块的字符
     */
    private char blankChar;
    /**
     * 最大的步骤，即为初始状态下曼哈顿值
     */
    private int limitLength;
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
     *
     * @param cur
     */
    private void DFS(String cur, int length, int pre_d){
        // 已经有解
        if (flag)
            return;
        if (length > limitLength)
            return;
        if (cur.equals(endStatus)){
                flag = true;
                limitLength = length;
                return;
        }
        int blankPos = cur.indexOf(blankChar);
        if (blankPos > size - 1){
            move(cur, -size, pre_d, length);
        }
        // 空白拼图块向下移动
        if (blankPos < ((size - 1) * size)){
            move(cur, size, pre_d, length);
        }
        // 空白拼图块向左移动
        if (blankPos % size > 0){
            move(cur, -1, pre_d, length);
        }
        // 空白拼图块向右移动
        if (blankPos % size < (size-1)) {
            move(cur, 1, pre_d, length);
        }
    }
    private void move(String cur, int d, int pre_d, int length){
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
        if ( p + length <=  limitLength && !flag){
            solution[length] =  child;
            DFS(child, length + 1, d);
            if (flag)
                return;
        }

    }
    public ArrayList<String> search(String start){
        limitLength = PSA.calMhtDistance(start);
        solution = new String[200];
        System.out.println(limitLength);
        while (!flag && limitLength < 200){
            DFS(start, 0, 0);
            if (!flag)
                limitLength++;
        }
        if (flag){
            System.out.println("有解:" + limitLength);
        }else{
            System.out.println("无解");
        }
        ArrayList<String> result = new ArrayList<>();

        for (int i = limitLength -1; i >=0 ; i--){
            result.add(solution[i]);
        }
        result.add(start);

        return result;
    }

}
