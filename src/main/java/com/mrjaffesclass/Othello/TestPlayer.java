/*
 * A player for testing purposes
 * Copyright 2017 Roger Jaffe
 * All rights reserved
 */

 

import java.util.ArrayList;

/**
 * Test player
 */
public class TestPlayer extends Player {

  /**
   * Constructor
   * @param name Player's name
   * @param color Player color: one of Constants.BLACK or Constants.WHITE
   */
  public TestPlayer(int color) {
    super(color);
  }

  /**
   *
   * @param board
   * @return The player's next move
   */
  @Override
  public Position getNextMove(Board board) {
    ArrayList<Position> list = this.getLegalMoves(board);
    Position corner1 = new Position(0, 0);
    Position corner2 = new Position(0, 7);
    Position corner3 = new Position(7, 0);
    Position corner4 = new Position(7, 7);
    if (list.size() > 0) {
      if (list.contains(corner1)) {
          return corner1;
      }
      if (list.contains(corner2)) {
          return corner2;
      }
      if (list.contains(corner3)) {
          return corner3;
      }
      if (list.contains(corner4)) {
          return corner4;
      }
      int idx = (int) (Math.random() * list.size());
      if (list.get(idx) == new Position(0, 6) || list.get(idx) == new Position(1, 7) || list.get(idx) == new Position(1,6) || list.get(idx) == new Position(0, 1) || list.get(idx) == new Position(1, 0) || list.get(idx) == new Position(1, 1) || list.get(idx) == new Position(6, 0) || list.get(idx) == new Position(6, 1) || list.get(idx) == new Position(7, 1) || list.get(idx) == new Position(6, 7) || list.get(idx) == new Position(7, 6) || list.get(idx) == new Position(6, 6)) {
          idx = (int) (Math.random() * list.size());
      }
      return list.get(idx);
    } else {
      return null;
    }
  }
  
  /**
   * Is this a legal move?
   * @param player Player asking
   * @param positionToCheck Position of the move being checked
   * @return True if this space is a legal move
   */
  private boolean isLegalMove(Board board, Position positionToCheck) {
    for (String direction : Directions.getDirections()) {
      Position directionVector = Directions.getVector(direction);
      if (step(board, positionToCheck, directionVector, 0)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Traverses the board in the provided direction. Checks the status of
   * each space: 
   * a. If it's the opposing player then we'll move to the next
   *    space to see if there's a blank space
   * b. If it's the same player then this direction doesn't represent
   *    a legal move
   * c. If it's a blank AND if it's not the adjacent square then this
   *    direction is a legal move. Otherwise, it's not.
   * 
   * @param player  Player making the request
   * @param position Position being checked
   * @param direction Direction to move
   * @param count Number of steps we've made so far
   * @return True if we find a legal move
   */
  private boolean step(Board board, Position position, Position direction, int count) {
    Position newPosition = position.translate(direction);
    int color = this.getColor();
    if (newPosition.isOffBoard()) {
      return false;
    } else if (board.getSquare(newPosition).getStatus() == -color) {
      return this.step(board, newPosition, direction, count+1);
    } else if (board.getSquare(newPosition).getStatus() == color) {
      return count > 0;
    } else {
      return false;
    }
  }
  
  /**
   * Get the legal moves for this player on the board
   * @param board
   * @return True if this is a legal move for the player
   */
  public ArrayList<Position> getLegalMoves(Board board) {
    int color = this.getColor();
    ArrayList list = new ArrayList<>();
    for (int row = 0; row < Constants.SIZE; row++) {
      for (int col = 0; col < Constants.SIZE; col++) {
        if (board.getSquare(this, row, col).getStatus() == Constants.EMPTY) {
          Position testPosition = new Position(row, col);
          if (this.isLegalMove(board, testPosition)) {
            list.add(testPosition);
          }
        }        
      }
    }
    return list;
  }

}
