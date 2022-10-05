package Assignment5;

public class LinkedNumNode {

    public double num;
    public LinkedNumNode next;

    public LinkedNumNode() {
        num = -1;
    }

    public LinkedNumNode(double num) {
        this.num = num;
    }

    public void addNode(double num) {
        if (next == null) {
            next = new LinkedNumNode(num);
        } else {
            next.addNode(num);
        }
    }

    public int size() {
        if (next == null) {
            return 1;
        } else {
            return 1 + next.size();
        }
    }
}
