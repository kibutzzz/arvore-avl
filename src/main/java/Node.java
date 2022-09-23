import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public class Node {

  private final int value;
  private Node left;
  private Node right;
  private Node parent;
  private int height = 0;

  public Node(int value) {
    this.value = value;
  }

  private Node(int value, int height, Node parent) {
    this.value = value;
    this.height = height + 1;
    this.parent = parent;
  }

  public int getValue() {
    return value;
  }

  public Node search(int value) {
    if (this.value == value) {
      return this;
    }

    try {
      if (this.left != null) {
        return left.search(value);
      }
      throw new NoSuchElementException();
    } catch (NoSuchElementException exception) {
      if (this.right != null) {
        return right.search(value);
      }
      throw new NoSuchElementException();
    }
  }

  public void insert(int value) {
    if (this.value == value) {
      return;
    }

    if (value > this.value) {
      if (this.right == null) {
        right = new Node(value, height, this);
      } else {
        right.insert(value);
      }
      return;
    }

    if (this.left == null) {
      left = new Node(value, height, this);
    } else {
      left.insert(value);
    }
  }


  @Override
  public String toString() {

    return value +
        nullableToString(right, "R") +
        nullableToString(left, "L");
  }

  private String nullableToString(Node node, String message) {
    if (node == null) return "";

    return "\n" + "\t".repeat(height) + "╰" + message + "-" + node.toString();
  }

  public String inOrder() {
    return valueToLeft(Node::inOrder) + this.value + valueToRight(Node::inOrder);
  }

  public String preOrder() {
    return this.value + valueToLeft(Node::preOrder) + valueToRight(Node::preOrder);
  }

  public String postOrder() {
    return valueToLeft(Node::postOrder) + valueToRight(Node::postOrder) + this.value;

  }


  private String valueToRight(Function<Node, String> order) {
    if (right != null) {
      String inOrder = order.apply(right);
      if (inOrder != null) {
        return inOrder;
      }
    }
    return "";
  }

  private String valueToLeft(Function<Node, String> order) {
    if (left != null) {
      String inOrder = order.apply(left);
      if (inOrder != null) {
        return inOrder;
      }
    }
    return "";
  }

  public void delete(int i) {
    final var node = search(i);

    if (node == this) {
      throw new IllegalStateException("Cannot delete itself");
    }

    final var parent = node.parent;
    if (parent.left == node) {
      if (doesOneElementExist(node)) {
        parent.left = Objects.requireNonNullElseGet(node.left, () -> node.right);
        return;
      }

      parent.left = null;
      return;
    }

    if (parent.right == node) {
      if (doesOneElementExist(node)) {
        parent.right = Objects.requireNonNullElseGet(node.left, () -> node.right);
        return;
      }

      parent.right = null;
    }
  }

  private boolean doesOneElementExist(Node node) {
    return node.left != null ^ node.right != null;
  }

}
