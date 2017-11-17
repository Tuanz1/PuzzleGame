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
    /**
     * 拼图当前状态
     */
    private String curStatus;
    public PuzzleStatusAssitant(int size, String targetStatus) {
        this.size = size;
        this.targetStatus = targetStatus;
    }

    /**
     * 用于A*算法计算拼图状态的权重
     * @param curStatus 当前拼图状态序列
     * @param depth 搜索深度
     * @return 权重
     */
    public int getWeight(String curStatus, int depth){
        return 5 * sumOfMhtDistance(curStatus) + depth;
    }

    /**
     * 获得IDA*算法计算拼图权重
     * @param curStatus 当前拼图状态序列
     * @return 权重
     */
    public int getWeight(String curStatus){
        this.curStatus = curStatus;
        return sumOfMhtDistance(curStatus);
    }

    /**
     * 曼哈顿距离之和
     * @param curStatus　拼图状态
     * @return　曼哈顿距离之和
     */
    private int sumOfMhtDistance(String curStatus){
        this.curStatus = curStatus;
        int weight  = 0;
        for(int i = 0; i < targetStatus.length(); i++){
                weight += mhtDistandce(i);
        }
        return weight;
    }

    /**
     * 计算曼哈顿距离
     * @param i
     * @return 曼哈顿距离值
     */
    private int mhtDistandce(int i){
        char t = targetStatus.charAt(i);
        int index = curStatus.indexOf(t);
        return Math.abs(i % size - index % size) + Math.abs(i / size - index / size);
    }
}
