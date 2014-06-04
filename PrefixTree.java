package scrabble;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PrefixTree {

  private static class Node {
    private Character letter;
    private Map<Character, Node> children;
    private boolean isWord;

    private Node(Character letter) {
      this.letter = letter;
      this.children = new HashMap<>();
    }

    public Node addChild(Character c) {
      checkNotNull(c);
      // Do not replace existing children since they may have been marked as words
      if (children.containsKey(c)) {
        return children.get(c);
      } else {
        Node node = new Node(c);
        children.put(c, node);
        return node;
      }
    }

    public void setAsWord() {
      isWord = true;
    }

    public List<String> getWords(String letters) {
      List<String> words = Lists.newArrayList();
      Set<Character> uniqueLetters = Sets.newHashSet(Lists.charactersOf(letters));
      for (Character c : uniqueLetters) {
        Node child = children.get(c);
        if (child != null) {
          words.addAll(child.getWords(removeLetter(letters, c)));
        }
      }
      if (isWord) {
        words.add("");
      }
      return (letter == null) ? words : Lists.transform(words, new Function<String, String>() {
        @Override
        public String apply(String word) {
          return letter + word;
        }
      });
    }

    private String removeLetter(String letters, Character letter) {
      int index = letters.indexOf(letter);
      return letters.substring(0, index) + letters.substring(index + 1);
    }

    @Override
    public String toString() {
      return letter + "";
    }
  }

  private final Node root;

  public PrefixTree() {
    root = new Node(null);
  }

  public void addWord(String word) {
    word = word.toLowerCase().trim();
    Node last = root;
    for (Character c : Lists.charactersOf(word)) {
      last = last.addChild(c);
    }
    last.setAsWord();
  }

  /**
   * Returns all the words in the dictionary that can be formed with the given letters.
   */
  public List<String> getWords(String letters) {
    return root.getWords(letters);
  }

}
