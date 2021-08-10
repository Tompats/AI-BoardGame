import java.util.ArrayList;


public class Move{











  //turn 0 -> pc, turn 1 -> human
  public Boolean isFinalCondition(ArrayList<ArrayList<Square>> possibleMoves){
    int counter = 0;
    for(int i=0;i<possibleMoves.size(); i++){
      if(possibleMoves.get(i).size()==0){
        counter++;
      }
    }
    if(counter==8){
      return true;
    }
    return false;
  }


  public ArrayList<ArrayList<Square>> getPossibleMoves(int m, int n, int distance,Square[][] squares_array,int[] position){
      ArrayList<ArrayList<Square>> possibleMoves = new ArrayList<ArrayList<Square>>();
      ArrayList<ArrayList<Square>> straightArray = straightMoves(m,n,distance,squares_array,position);
      ArrayList<ArrayList<Square>> diagonalArray = diagonalMoves(m,n,distance,squares_array,position);
      for(int i=0; i<straightArray.size();i++){
        possibleMoves.add(straightArray.get(i));
      }
      for(int i=0; i<diagonalArray.size();i++){
        possibleMoves.add(diagonalArray.get(i));
      }
      return possibleMoves;
  }



  public ArrayList<ArrayList<Square>> straightMoves(int m, int n, int distance, Square[][] squares_array, int[] position){
    ArrayList<ArrayList<Square>> possibleMoves = new ArrayList<ArrayList<Square>>();
    ArrayList<Square> array = new ArrayList<Square>();
    int row = position[0];
    int column = position[1];
    //array.clear();
    //all possible moves in the down
    for (int i = row + 1; i < row+distance; i++) {
      if(i>=m){
        //System.out.println(m);
        break;
      }
      else {
        Square square = squares_array[i][column];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //array.clear();
    //all possible moves in the up
    for (int i = row - 1; i > row-distance; i--) {
      if(i<0){
        break;
      }
      else{
        Square square = squares_array[i][column];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //array.clear();
    //all possible moves to the right
    for (int i = column + 1; i < column+distance; i++) {
      if(i>=n){
        break;
      }
      else{
        Square square = squares_array[row][i];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //array.clear();
    //all possible moves to the left
    for (int i = column - 1; i > column-distance; i--) {
      if(i<0){
        break;
      }
      else{
        Square square = squares_array[row][i];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //array.clear();
    return possibleMoves;
  }




  public ArrayList<ArrayList<Square>> diagonalMoves(int m, int n, int distance, Square[][] squares_array, int[] position){
    ArrayList<ArrayList<Square>> possibleMoves = new ArrayList<ArrayList<Square>>();
    ArrayList<Square> array = new ArrayList<Square>();
    int row = position[0];
    int column = position[1];
    //array.clear();
    //all possible moves in the down positive diagonal
    for (int j = column + 1, i = row + 1; j < column+distance && i < row+distance; j++, i++) {
      if(i>=m || j>=n){
        break;
      }
      else{
        Square square = squares_array[i][j];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //all possible moves in the down negative diagonal
    for (int j = column - 1, i = row + 1; j > column-distance && i < row+distance; j--, i++) {
      if(i>=m || j<0){
        break;
      }
      else{
        Square square = squares_array[i][j];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //all possible moves in the up negative diagonal
    for (int j = column - 1, i = row - 1; j > column-distance && i > row-distance; j--, i--) {
      if(i<0 || j<0){
        break;
      }
      else{
        Square square = squares_array[i][j];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    //all possible moves in the up positive diagonal
    for (int j = column + 1, i = row - 1; j < column+distance && i > row-distance; j++, i--) {
      if(i<0 || j>=n){
        break;
      }
      else{
        Square square = squares_array[i][j];
        if (square.getColor() == 1) {
            array.add(square);
        }
        else {
            break;
        }
      }
    }
    possibleMoves.add(array);
    array = new ArrayList<Square>();
    return possibleMoves;
  }

}
