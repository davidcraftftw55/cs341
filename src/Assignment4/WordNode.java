package Assignment4;

public class WordNode {

    private String word;
    public WordNode left;
    public WordNode right;

    public WordNode(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
