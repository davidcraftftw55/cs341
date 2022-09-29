package Assignment4;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private WordNode root;

    public boolean addWord(String word) {
        assert (word != null) : "null value given to addWord()";
        assert (!word.isBlank()) : "Whitespace entered in addWord()";

        boolean wordAdded = false;
        WordNode node = root;
        if (root == null) {
            root = new WordNode(word);
            wordAdded = true;
        }

        while (!wordAdded) {
            if (word.compareTo(node.getWord()) < 0) {
                if (node.left == null) {
                    node.left = new WordNode(word);
                    wordAdded = true;
                }
                node = node.left;
            } else if (word.compareTo(node.getWord()) > 0) {
                if (node.right == null) {
                    node.right = new WordNode(word);
                    wordAdded = true;
                }
                node = node.right;
            } else {
                // word is already in tree
                break;
            }
        }

        assert spellCheck(word) : "word unsuccessfully added to tree:" + word;
        assert checkBST() : "word addition invalidated Dictionary: " + word;

        return wordAdded;
    }

    public boolean removeWord(String word) {
        assert (word != null) : "null value given to removeWord()";
        assert (!word.isBlank()) : "Whitespace entered in removeWord()";

        // find node to be removed
        WordNode parent = null;
        boolean leftChild = false; // true if toRemove is left-child of parent, false if right-child
        WordNode toRemove = root;
        while (toRemove != null && !toRemove.getWord().contentEquals(word)) {
            if (word.compareTo(toRemove.getWord()) < 0) {
                parent = toRemove;
                leftChild = true;
                toRemove = toRemove.left;
            } else {
                parent = toRemove;
                leftChild = false;
                toRemove = toRemove.right;
            }
        }
        if (toRemove == null) {
            // word is not in tree
            return false;
        }

        // use a helper method to remove the node
        removeNode(toRemove, parent, leftChild);

        assert !spellCheck(word) : "word unsuccessfully removed from tree: " + word;
        assert checkBST() : "word removal invalidated Dictionary: " + word;

        return true;
    }

    private void removeNode(WordNode toRemove, WordNode parent, boolean leftChild) {
        if (toRemove.left == null && toRemove.right == null) {
            // node to be removed has no children, simply remove it
            if (toRemove == root) {
                // toRemove is root; since root has no children, just reset the tree
                root = null;
            } else {
                // otherwise, remove it from its parent
                if (leftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        } else if (toRemove.right == null) {
            // node to be removed has one child (left), have child take its place
            if (toRemove == root) {
                // toRemove is root; set root to its child
                root = root.left;
            } else {
                // otherwise set its parent's child
                if (leftChild) {
                    parent.left = toRemove.left;
                } else {
                    parent.right = toRemove.left;
                }
            }
        } else if (toRemove.left == null) {
            // node to be removed has one child (right), have child take its place
            if (toRemove == root) {
                // toRemove is root; set root to its child
                root = root.right;
            } else {
                // otherwise set its parent's child
                if (leftChild) {
                    parent.left = toRemove.right;
                } else {
                    parent.right = toRemove.right;
                }
            }
        } else {
            // node has two children; we deal with this by swapping the toRemove with the right-most child on toRemove's
            // left subtree, then remove that child

            // first find the right-most left child of toRemove
            WordNode swapNodeParent = toRemove;
            boolean swapNodeLeftChild = true;
            WordNode swapNode = toRemove.left;
            while(swapNode.right != null) {
                swapNodeParent = swapNode;
                swapNodeLeftChild = false;
                swapNode = swapNode.right;
            }

            // then swap the two nodes
            String temp = toRemove.getWord();
            toRemove.setWord(swapNode.getWord());
            swapNode.setWord(temp);

            // finally, we remove the swapNode (now holding the value we want to remove)
            // we'll recursively call this method to reuse the above code
            // (in this recursive call, it is impossible to enter this block)
            removeNode(swapNode, swapNodeParent, swapNodeLeftChild);
        }
    }

    public boolean spellCheck(String word) {
        return wordSearch(word, root);
    }

    @Override
    public String toString() {
        if (root != null) {
            List<String> bst = new ArrayList<>();
            toList(bst, root);

            StringBuilder output = new StringBuilder(bst.get(0));
            for (int i = 1; i < bst.size(); i++) {
                output.append(" ").append(bst.get(i));
            }
            return output.toString();
        } else {
            return "";
        }
    }

    private boolean wordSearch(String word, WordNode node) {
        if (node == null) {
            return false;
        } else if (word.contentEquals(node.getWord())) {
            return true;
        } else if (word.compareTo(node.getWord()) < 0) {
            return wordSearch(word, node.left);
        } else {
            return wordSearch(word, node.right);
        }
    }

    private boolean checkBST() {
        List<String> bstList = new ArrayList<>();
        toList(bstList, root);

        // check for duplicates and that order is incrementing
        List<String> encountered = new ArrayList<>();
        String prevWord = bstList.get(0);
        for (int i = 1; i < bstList.size(); i++) {
            if (encountered.contains(bstList.get(i)) || prevWord.compareTo(bstList.get(i)) > 0) {
                return false;
            }
            encountered.add(bstList.get(i));
            prevWord = bstList.get(i);
        }

        return true;
    }

    private void toList(List<String> list, WordNode root) {
        if (root != null) {
            toList(list, root.left);
            list.add(root.getWord());
            toList(list, root.right);
        }
    }
}
