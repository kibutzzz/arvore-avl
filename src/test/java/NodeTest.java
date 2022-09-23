import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NodeTest {

  @Test
  void simpleTree() {
    final var root = new Node(5);
    assertEquals(5, root.getValue());
    assertEquals("5", root.toString());
  }

  @Test
  void shouldAddNonRepeatedValues() {
    final var root = new Node(7);
    root.insert(5);
    root.insert(8);
    root.insert(10);
    root.insert(2);
    root.insert(7);

    root.insert(2);

    assertEquals("7\n" +
        "╰R-8\n" +
        "\t╰R-10\n" +
        "╰L-5\n" +
        "\t╰L-2", root.toString());
  }

  @Test
  void treeOne() {
    final var root = new Node(7);
    root.insert(1);
    root.insert(2);
    root.insert(3);
    root.insert(4);
    root.insert(5);

    root.insert(6);
    root.insert(9);
    root.insert(10);
    root.insert(11);
    root.insert(12);

    assertEquals("7\n" +
        "╰R-9\n" +
        "\t╰R-10\n" +
        "\t\t╰R-11\n" +
        "\t\t\t╰R-12\n" +
        "╰L-1\n" +
        "\t╰R-2\n" +
        "\t\t╰R-3\n" +
        "\t\t\t╰R-4\n" +
        "\t\t\t\t╰R-5\n" +
        "\t\t\t\t\t╰R-6", root.toString());

  }

  @Test
  void testFull() {
    final var root = new Node(7);
    root.insert(5);
    root.insert(6);
    root.insert(4);
    root.insert(2);
    root.insert(3);
    root.insert(1);

    root.insert(11);
    root.insert(9);
    root.insert(8);
    root.insert(17);
    root.insert(15);
    root.insert(18);
    root.insert(16);

    System.out.println(root.toString());
    assertEquals("7\n" +
        "╰R-11\n" +
        "\t╰R-17\n" +
        "\t\t╰R-18\n" +
        "\t\t╰L-15\n" +
        "\t\t\t╰R-16\n" +
        "\t╰L-9\n" +
        "\t\t╰L-8\n" +
        "╰L-5\n" +
        "\t╰R-6\n" +
        "\t╰L-4\n" +
        "\t\t╰L-2\n" +
        "\t\t\t╰R-3\n" +
        "\t\t\t╰L-1", root.toString());
  }

  @Test
  void inOrder() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(1);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    assertEquals("1234567", root.inOrder().trim());
  }

  @Test
  void preOrder() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(1);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    assertEquals("4213657", root.preOrder().trim());
  }

  @Test
  void postOrder() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(1);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    System.out.println(root);
    assertEquals("1325764", root.postOrder().trim());
  }

  @Test
  void search() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(1);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    assertEquals(7, root.search(7).getValue());
  }

  @Test
  void notFound() {
    assertThrows(NoSuchElementException.class, () -> new Node(1).search(2));
  }

  @Test
  void deleteNotPresent() {
    final var root = new Node(3);

    assertThrows(NoSuchElementException.class, () -> root.delete(2));
  }

  @Test
  void deleteRoot() {
    final var root = new Node(1);
    assertThrows(IllegalStateException.class, () -> root.delete(1));
  }

  @Test
  void deleteLeafLeft() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(1);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    root.delete(1);


    assertEquals("4\n" +
        "╰R-6\n" +
        "\t╰R-7\n" +
        "\t╰L-5\n" +
        "╰L-2\n" +
        "\t╰R-3", root.toString());
  }


  @Test
  void deleteNodeWithOneChild() {
    final var root = new Node(4);
    root.insert(2);
    root.insert(6);
    root.insert(3);
    root.insert(5);
    root.insert(7);

    root.delete(2);

    assertEquals("4\n" +
            "╰R-6\n" +
            "\t╰R-7\n" +
            "\t╰L-5\n" +
            "╰L-3"
        , root.toString());

    root.delete(5);
    root.delete(6);

    assertEquals("4\n" +
            "╰R-7\n" +
            "╰L-3"
        , root.toString());
  }

}