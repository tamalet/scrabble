package scrabble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class PrefixTreeTest {

  @Test
  public void testGetWords() throws Exception {
    PrefixTree pt = new PrefixTree();

    pt.addWord("amar");
    pt.addWord("rama");
    pt.addWord("mara");
    pt.addWord("mareo");
    pt.addWord("amor");
    pt.addWord("mora");
    pt.addWord("omar");
    pt.addWord("ramo");
    pt.addWord("roma");
    pt.addWord("aroma");
    pt.addWord("amoral");
    pt.addWord("casa");
    pt.addWord("casamiento");
    pt.addWord("gato");
    pt.addWord("perro");

    assertContainsInAnyOrder(
        ImmutableList.of("amar", "amor", "aroma", "mara", "mareo", "mora", "omar", "rama", "ramo", "roma"),
        pt.getWords("arameo"));
    assertContainsInAnyOrder(
        ImmutableList.of("amor", "mora", "omar", "ramo", "roma"),
        pt.getWords("amor"));
    assertContainsInAnyOrder(
        ImmutableList.of("casa", "casamiento"),
        pt.getWords("casamiento"));
    assertContainsInAnyOrder(
        ImmutableList.of("gato"),
        pt.getWords("gatogato"));
    assertContainsInAnyOrder(ImmutableList.<String>of(), pt.getWords("pato"));
  }

  private <T> void assertContainsInAnyOrder(Collection<T> expected, Collection<T> actual) {
    assertEquals(expected.size(), actual.size());
    assertTrue("Extra actual element", expected.containsAll(actual));
    assertTrue("Extra expected element", actual.containsAll(expected));
  }

}