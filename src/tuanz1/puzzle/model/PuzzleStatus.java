package tuanz1.puzzle.model;

/**
 * 一个拼图状态对饮的数据结构
 */
public class PuzzleStatus {
    public PuzzleStatus(String cur, String pre, int depth, int weight){
        this.cur = cur;
        this.pre = pre;
        this.depth = depth;
        this.weight = weight;
    }

    /**
     * 当前拼图状态序列
     */
    public String cur;
    /**
     * 父拼图状态序列
     */
    public String pre;
    /**
     * 拼图深度
     */
    public int depth;
    /**
     * 拼图权重
     */
    public int weight;
}
