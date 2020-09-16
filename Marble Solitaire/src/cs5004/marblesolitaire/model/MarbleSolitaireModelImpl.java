package cs5004.marblesolitaire.model;

/**
 * A marble solitaire object that implements the model for the game through the interface.
 */
public class MarbleSolitaireModelImpl implements MarbleSolitaireModel {
  Marble[][] board;
  int armThickness;
  int size;
  int upperBound;
  int lowerBound;
  int emptySpaceRow;
  int emptySpaceCol;

  /**
   * An object that allows the marble solitaire Model to work through the interface. Creates with an
   * empty constructor.
   */
  public MarbleSolitaireModelImpl() {
    this.armThickness = 3;
    this.lowerBound = armThickness - 1;
    this.size = armThickness + lowerBound * 2;
    this.upperBound = (size - lowerBound) - 1;
    this.emptySpaceRow = Math.floorDiv(size, 2);
    this.emptySpaceCol = Math.floorDiv(size, 2);
    this.board = new Marble[size][size];
    createBoard();
  }

  /**
   * Creates object with row and column of the empty space.
   *
   * @param sRow an int that represents the row.
   * @param sCol an int that represents the column.
   * @throws IllegalArgumentException when an invalid position is entered.
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) throws IllegalArgumentException {
    this.armThickness = 3;
    this.lowerBound = armThickness - 1;
    this.size = armThickness + lowerBound * 2;
    this.upperBound = (size - lowerBound) - 1;

    if (sRow < 0 || sCol < 0 || sRow > size || sCol > size) {
      throw new IllegalArgumentException("Row or Column not on the board");
    }
    if (!checkValidPositions(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid empty cell position ( "
              + sRow + ", " + sCol + ")");
    }
    this.emptySpaceRow = sRow;
    this.emptySpaceCol = sCol;
    this.board = new Marble[size][size];
    createBoard();
  }

  /**
   * Creates the object with the arm thickness.
   *
   * @param armThickness an int that represents the arm thickness.
   * @throws IllegalArgumentException when the armThickness is invalid.
   */
  public MarbleSolitaireModelImpl(int armThickness) throws IllegalArgumentException {
    if (armThickness < 3 || armThickness % 2 < 1) {
      throw new IllegalArgumentException("Invalid number as Arm Thickness");
    }
    this.armThickness = armThickness;
    this.lowerBound = armThickness - 1;
    this.size = armThickness + lowerBound * 2;
    this.upperBound = (size - lowerBound) - 1;
    this.emptySpaceRow = Math.floorDiv(size, 2);
    this.emptySpaceCol = Math.floorDiv(size, 2);
    this.board = new Marble[size][size];
    createBoard();
  }

  /**
   * Creates the object with the armThickness, row and column.
   *
   * @param armThickness an int that represents the armThickness.
   * @param sRow         an int that represents the row for the empty space.
   * @param sCol         an int that represents the column for the empty space.
   * @throws IllegalArgumentException when any of the inputs are invalid.
   */
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol)
          throws IllegalArgumentException {
    if (armThickness < 3 || armThickness % 2 < 1) {
      throw new IllegalArgumentException("Invalid number as Arm Thickness");
    }
    this.armThickness = armThickness;
    this.lowerBound = armThickness - 1;
    this.size = armThickness + lowerBound * 2;
    this.upperBound = (size - lowerBound) - 1;

    if (sRow < 0 || sCol < 0 || sRow > size - 1 || sCol > size - 1) {
      throw new IllegalArgumentException("Row or Column not on the board");
    }
    if (!checkValidPositions(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid empty cell position ( "
              + sRow + ", " + sCol + ")");
    }
    this.emptySpaceRow = sRow;
    this.emptySpaceCol = sCol;
    this.board = new Marble[size][size];
    createBoard();
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isGameOver()) {
      if (fromRow >= size || fromCol >= size || toRow >= size || toCol >= size
              || fromRow < 0 || fromCol < 0 || toRow < 0 || toCol < 0) {
        throw new IllegalArgumentException("From/To coordinates are outside the board");
      }
      if (!checkValidPositions(fromRow, fromCol) || !checkValidPositions(toRow, toCol)) {
        throw new IllegalArgumentException("Position specified is invalid - No marbles");
      }

      int rowDiff = Math.abs(fromRow - toRow);
      int colDiff = Math.abs(fromCol - toCol);

      if (board[fromRow][fromCol] != Marble.marble || board[toRow][toCol] != Marble.empty) {
        throw new IllegalArgumentException("Invalid location");
      }

      if ((rowDiff == 2 && colDiff == 0 && (moveUp(fromRow, fromCol) || moveDown(fromRow, fromCol)))
              || (rowDiff == 0 && colDiff == 2 && (moveRight(fromRow, fromCol)
              || moveLeft(fromRow, fromCol)))) {
        this.board[fromRow][fromCol] = Marble.empty;
        this.board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = Marble.empty;
        this.board[toRow][toCol] = Marble.marble;
      }
      else if (fromRow != toRow && fromCol != toCol) {
        throw new IllegalArgumentException("Your move is not legal");
      }
      else {
        throw new IllegalArgumentException("INVALID MOVE");
      }
    }
    else {
      throw new IllegalArgumentException("Game is already over");
    }
  }

  @Override
  public boolean isGameOver() {
    boolean currentStatus = true;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (moveUp(i, j) || moveDown(i, j) || moveRight(i, j) || moveLeft(i, j)) {
          currentStatus = false;
          break;
        }
      }
    }
    return currentStatus;
  }

  @Override
  public String getGameState() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i == size - 1 && j > lowerBound && board[i][j] == Marble.invalid) {
          str.deleteCharAt(str.length() - 1);
          break;
        }
        else if (j > lowerBound && board[i][j] == Marble.invalid) {
          str.deleteCharAt(str.length() - 1).append("\n");
          break;
        }
        else if (j == size - 1) {
          str.append(board[i][j].getValue()).append("\n");
        }
        else {
          str.append(board[i][j].getValue()).append(" ");
        }
      }
    }
    return str.toString();
  }

  @Override
  public int getScore() {
    int score = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (board[i][j] == Marble.marble) {
          score += 1;
        }
      }
    }
    return score;
  }

  private void createBoard() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i == this.emptySpaceRow && j == this.emptySpaceCol) {
          this.board[emptySpaceRow][emptySpaceCol] = Marble.empty;
        }
        else if (i < lowerBound && j < lowerBound || i < lowerBound && j > upperBound
                || i > upperBound && j < lowerBound || i > upperBound && j > upperBound) {
          this.board[i][j] = Marble.invalid;
        }
        else {
          this.board[i][j] = Marble.marble;
        }
      }
    }
  }

  private boolean checkValidPositions(int row, int col) {
    return (row >= lowerBound || col >= lowerBound) && (row >= lowerBound || col <= upperBound)
            && (row <= upperBound || col >= lowerBound) && (row <= upperBound || col <= upperBound);
  }

  private boolean moveRight(int row, int col) {
    if (col < size - 2) {
      return board[row][col] == Marble.marble && board[row][col + 1] == Marble.marble
              && board[row][col + 2] == Marble.empty;
    }
    else {
      return false;
    }
  }

  private boolean moveLeft(int row, int col) {
    if (col >= 2) {
      return board[row][col] == Marble.marble && board[row][col - 1] == Marble.marble
              && board[row][col - 2] == Marble.empty;
    }
    else {
      return false;
    }
  }

  private boolean moveUp(int row, int col) {
    if (row >= 2) {
      return board[row][col] == Marble.marble && board[row - 1][col] == Marble.marble
              && board[row - 2][col] == Marble.empty;
    }
    else {
      return false;
    }
  }

  private boolean moveDown(int row, int col) {
    if (row < size - 2) {
      return board[row][col] == Marble.marble && board[row + 1][col] == Marble.marble
              && board[row + 2][col] == Marble.empty;
    }
    else {
      return false;
    }
  }

}
