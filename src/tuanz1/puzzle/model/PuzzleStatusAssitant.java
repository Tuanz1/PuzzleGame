package tuanz1.puzzle.model;

/**
 * 拼图状态计算助手，主要用来计算权重
 */
public class PuzzleStatusAssitant {
    /**
     * 拼图目标状态
     */
    private String targetStatus;
    private int size;
    private String cur;
    public PuzzleStatusAssitant(int size, String targetStatus) {
        this.size = size;
        this.targetStatus = targetStatus;
    }

    /**
     * 用于A*算法计算拼图状态的权重
     * @param cur 当前拼图状态序列
     * @param depth 搜索深度
     * @return 权重
     */
    public int calPuzzleStatusWeight(String cur, int depth){
        this.cur = cur;
        int weight  = 0;
        for(int i = 0; i < targetStatus.length(); i++){
            weight += MhtDistandce(i);
        }
        return weight * 5 + depth;
    }

    /**
     * 用于IDA*算法计算曼哈顿距离(不计算空白拼图块）
     * @param cur　拼图状态
     * @return　曼哈顿距离之和
     */
    public int calMhtDistance(String cur){
        this.cur = cur;
        int weight  = 0;
        for(int i = 0; i < targetStatus.length(); i++){
                weight += MhtDistandce(i);
        }
        return weight;
    }

    private int MhtDistandce(int i){
        char t = targetStatus.charAt(i);
        int index = cur.indexOf(t);
        return Math.abs(i % size - index % size) + Math.abs(i / size - index / size);
    }
}
