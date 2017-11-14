package tuanz1.puzzle.model;

/**
 * 拼图状态计算助手，主要用来计算权重
 */
public class PuzzleStatusAssitant {
    private String endStatus;
    private int size;
    private String cur;
    public PuzzleStatusAssitant(int size, String endStatus) {
        this.size = size;
        this.endStatus = endStatus;
    }
    public int calPuzzleStatusWeight(String cur, int depth){
        this.cur = cur;
        int weight  = 0;
        for(int i = 0; i < endStatus.length(); i++){
            weight += MhtDistandce(i);
        }
        return weight +  depth;
    }

    /**
     * 计算曼哈顿距离
     * @param i　第i块拼图
     * @return　距离
     */
    public int calMhtDistance(String cur){
        this.cur = cur;
        int weight  = 0;
        for(int i = 0; i < endStatus.length(); i++){
            weight += MhtDistandce(i);
        }
        return weight;
    }
    private int MhtDistandce(int i){
        char t = endStatus.charAt(i);
        int index = cur.indexOf(t);
        return Math.abs(i % size - index % size) + Math.abs(i / size - index / size);
    }
}
