package cs5004.marblesolitaire.model;

/**
 * An enum that represents a Marble in the Model.
 */
public enum Marble {
  marble('O'),
  empty('_'),
  invalid(' ');

  private final char value;

  Marble(char value) {
    this.value = value;
  }

  char getValue() {
    return this.value;
  }
}
