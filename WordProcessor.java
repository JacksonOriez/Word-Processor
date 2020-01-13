import java.util.LinkedList;
import java.util.List;

/**
 * A Simple Word Processing class
 *
 * <p>Purdue University -- CS25100 -- Fall 2019 -- Tries</p>
 *
 */
public class WordProcessor {

    private Node wordTrie;  //Root Node of the Trie

    private List<String> list;

    /**
     * A simple Node class representing each
     * individual node of the trie
     */
    public class Node {

        protected char c;
        protected Node left, equal, right;
        protected boolean isEnd = false;

        /**
         * Constructor for Node class
         * @param ca Character assigned to the node
         */
        public Node(char ca) {
            this.c = ca;
            left = null;
            right = null;
            equal = null;
        }
    }

    /**
     * Defualt constructor for the WordProcessor class
     */
    public WordProcessor() {

        wordTrie = null;
    }

    /**
     * Method to add a string to the trie
     * @param s String to be added
     */
    public void addWord(String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        Node root = wordTrie;
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            boolean inTrie = false;

            if (root == null) {
                root = new Node(current);
                wordTrie = root;
            }

            while (!inTrie) {
                if (root.c == current) {
                    inTrie = true;
                    if (i == s.length() - 1) {
                        break;
                    }
                    if (root.equal != null) {
                        root = root.equal;
                    } else {
                        root.equal = new Node(s.charAt(i + 1));
                        root = root.equal;
                    }
                } else if (root.c < current) {
                    if (root.right == null) {
                        root.right = new Node(current);
                        root = root.right;
                        if (i != s.length() - 1) {
                            root.equal = new Node(s.charAt(i + 1));
                            root = root.equal;
                        }
                        inTrie = true;
                    } else {
                        root = root.right;
                    }
                } else {
                    if (root.left == null) {
                        root.left = new Node(current);
                        root = root.left;
                        if (i != s.length() - 1) {
                            root.equal = new Node(s.charAt(i + 1));
                            root = root.equal;
                        }
                        inTrie = true;
                    } else {
                        root = root.left;
                    }
                }
            }
            if (i == s.length() - 1) {
                root.isEnd = true;
            }
        }
    }



    /**
     * Method to add an array of strings to the trie
     * @param s Array of strings to be added
     */
    public void addAllWords(String[] s) {
        for (int i = 0; i < s.length; i++) {
            addWord(s[i]);
        }
    }

    /**
     * Method to check of a string exists in the trie
     * @param s String to be checked
     * @return true if string exists
     */
    public boolean wordSearch(String s) {
        int counter = 0;
        Node root = wordTrie;

        if (wordTrie == null) {
            return false;
        }

        while (counter < s.length()) {
            if (s.charAt(counter) == wordTrie.c) {
                if (counter != s.length() - 1) {
                    wordTrie = wordTrie.equal;
                }
            } else if (s.charAt(counter) < wordTrie.c) {
                if (wordTrie.left == null) {
                    return false;
                }
                wordTrie = wordTrie.left;
                while (wordTrie != null) {
                    if (wordTrie.c == s.charAt(counter)) {
                        break;
                    } else if (s.charAt(counter) < wordTrie.c) {
                        wordTrie = wordTrie.left;
                    } else {
                        wordTrie = wordTrie.right;
                    }
                }
                if (wordTrie == null) {
                    return false;
                }
                if (counter != s.length() - 1) {
                    wordTrie = wordTrie.equal;
                }
            } else {
                if (wordTrie.right == null) {
                    return false;
                }
                wordTrie = wordTrie.right;
                while (wordTrie != null) {
                    if (wordTrie.c == s.charAt(counter)) {
                        break;
                    } else if (s.charAt(counter) < wordTrie.c) {
                        wordTrie = wordTrie.left;
                    } else {
                        wordTrie = wordTrie.right;
                    }
                }
                if (wordTrie == null) {
                    return false;
                }
                if (counter != s.length() - 1) {
                    wordTrie = wordTrie.equal;
                }
            }
            counter++;
        }

        if (wordTrie.isEnd) {
            wordTrie = root;
            return true;
        } else {
            wordTrie = root;
            return false;
        }
    }



    /**
     * Method to check if the trie if empty
     * (No stirngs added yet)
     * @return
     */
    public boolean isEmpty() {
        return (wordTrie == null);
    }

    /**
     * Method to empty the trie
     */
    public void clear() {
        wordTrie = null;
    }


    /**
     * Getter for wordTire
     * @return Node wordTrie
     */
    public Node getWordTrie() {
        return wordTrie;
    }


    /**
     * Method to search autocomplete options
     * @param s Prefix string being searched for
     * @return List of strings representing autocomplete options
     */
    public List<String> autoCompleteOptions(String s) {
        List<String> list = new LinkedList<>();

        if (wordSearch(s)) {
            return list;
        }

        int counter = 0;
        Node root = wordTrie;

        if (root == null) {
            return list;
        }

        while (counter < s.length()) {
            if (s.charAt(counter) == root.c) {
                root = root.equal;
            } else if (s.charAt(counter) < root.c) {
                if (root.left == null) {
                    return list;
                }
                root = root.left;
                while (root != null) {
                    if (root.c == s.charAt(counter)) {
                        break;
                    } else if (s.charAt(counter) < root.c) {
                        root = root.left;
                    } else {
                        root = root.right;
                    }
                }
                if (root == null) {
                    return list;
                }
                root = root.equal;
            } else {
                if (root.right == null) {
                    return list;
                }
                root = root.right;
                while (root != null) {
                    if (root.c == s.charAt(counter)) {
                        break;
                    } else if (s.charAt(counter) < root.c) {
                        root = root.left;
                    } else {
                        root = root.right;
                    }
                }
                if (root == null) {
                    return list;
                }
                root = root.equal;
            }
            counter++;
        }

        if (root == null) {
            return list;
        }

        trieTraversal(list, s, root);

        return list;

    }

    public void trieTraversal(List<String> list, String prefix, Node root) {
        if (root.isEnd) {
            String current = prefix + root.c;
            if (!list.contains(current)) {
                list.add(current);
            }
        }
        if (root.left != null) {
            trieTraversal(list, prefix, root.left);
        }
        if (root.right != null) {
            trieTraversal(list, prefix, root.right);
        }
        String current = prefix + root.c;
        if (root.equal != null) {
            trieTraversal(list, current, root.equal);
        }
    }
}
