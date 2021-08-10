import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game_Init {


  public void createBoard(int m,int n,Square[][] squares_array){
      String d = "-";
      for(int i=0; i<n; i++){
        d+="----";
      }
      for (int row = 0; row < m; row++)
      {
          System.out.println("");
          System.out.println(d);
          for (int column = 0; column < n; column++)
          {
            if (squares_array[row][column].getColor() == 1) {
              System.out.print("| " + " " + " ");
            }
            else if (squares_array[row][column].getColor() == 3) {
              System.out.print("| " + "A" + " ");
            }
            else if (squares_array[row][column].getColor() == 4) {
              System.out.print("| " + "B" + " ");
            }
            else{
              System.out.print("| " + "*" + " ");
            }
          }
          System.out.print("|");
      }
      System.out.println("");
      System.out.println(d);
  }

  public int[] readPosition(){
    int[] array = new int[2];
    Scanner sc = new Scanner(System.in);
    String inputString = sc.nextLine();
    String[] res = inputString.split(",");
    for(int i = 0; i<2; i++){
        array[i] = Integer.parseInt(res[i]);
    }
    return array;
  }


  public void randomizeBlackSquares(int m, int n, Square[][] squares_array, int percent){
    int size = m*n;
    float per = (percent*size)/100;
    Random rand = new Random();
    for(int i=0; i<per; i++){
      int rand_m = rand.nextInt(m);
      int rand_n = rand.nextInt(n);
      if(squares_array[rand_m][rand_n].getColor() != 3 && squares_array[rand_m][rand_n].getColor() != 4){
        squares_array[rand_m][rand_n].setColor(2); //set black color
      }
    }
  }





  public void PlayerMove(int m, int n, int distance,Square[][] squares_array,int[] a_pos){
    Move object = new Move();
    ArrayList<ArrayList<Square>> moves = object.getPossibleMoves(m, n, distance, squares_array, a_pos);
    System.out.println("Your possible moves are: ");
    for(int i=0; i<moves.size(); i++){
      System.out.print("Move "+i+": ");
      for(int j=0; j<moves.get(i).size(); j++){
        if (j== moves.get(i).size()-1) {
          System.out.print(moves.get(i).get(j).toString());
        }
        else{
          System.out.print(moves.get(i).get(j).toString()+" -> ");
        }
      }
      System.out.println();
    }
    Scanner input = new Scanner(System.in);
    System.out.println("Enter Your Choice: (Ex. 2)");
    int ch = input.nextInt();
    while(ch<0 || ch>moves.size()-1){
      System.out.println("Enter Your Choice: (Ex. 2)");
      ch = input.nextInt();
    }
    play(moves.get(ch), squares_array, a_pos, 3);
  }




  public Square[][] play(ArrayList<Square> move,Square[][] squares_array,int[] pl_pos, int player){
    squares_array[pl_pos[0]][pl_pos[1]].setColor(2);
    for(int i=0; i<move.size(); i++){
      if (i==move.size()-1) {
        int[] pos = move.get(i).getPosition();
        squares_array[pos[0]][pos[1]].setColor(player);
      }
      else{
        int[] pos = move.get(i).getPosition();
        squares_array[pos[0]][pos[1]].setColor(2);
      }
    }
    return squares_array;
  }



  public int[] getPos(Square[][] squares_array,int turn){
    int[] po = new int[2];
    for(int i=0; i<squares_array.length; i++){
      for(int j=0; j<squares_array[i].length; j++){
        if(squares_array[i][j].getColor()==turn){
          po[0] = i;
          po[1] = j;
        }
      }
    }

    return po;
  }




  //turn = 3 for min, turn = 4 for max
  public int ExecuteMinMax(int m, int n, int depth, int number_of_moves, int turn, int distance, Square[][] squares_array_clone, int[] pos, ArrayList<Square> final_move,int counter){
    Move object_move = new Move();
    ArrayList<Square> array = new ArrayList<Square>();
    ArrayList<ArrayList<Square>> moves = new ArrayList<ArrayList<Square>>();
    moves = object_move.getPossibleMoves(m, n, distance, squares_array_clone, pos);
    Boolean cond = object_move.isFinalCondition(moves);
    //this.createBoard(m, n, squares_array_clone);
    if(depth <= 0 || cond){
        //System.out.println("HI");
        return finalState(turn);
    }


    else{
      //MAX PLAYER
      if(turn==4){
        int best_move = -1;
        for(int i=0; i<moves.size(); i++){
          if(moves.get(i).size() > 0 ){
            number_of_moves++;
            squares_array_clone = play(moves.get(i),squares_array_clone,pos,4);
            int lats_element = moves.get(i).size()-1;
            int[] new_pos = moves.get(i).get(lats_element).getPosition();
            int temp = ExecuteMinMax(m,n,depth-1,number_of_moves,3,distance,squares_array_clone,new_pos,final_move,counter);
            if(temp-number_of_moves > best_move){
              array = moves.get(i);
            }
            best_move=Math.max(temp-number_of_moves, best_move);
  				  //start from the beginning and try another move
  				  number_of_moves=0;
          }
        }
        final_move.clear();
        if(counter==0){
          for(int j=0; j<array.size();j++){
            final_move.add(array.get(j));
            counter++;
          }
        }
        return best_move;
      }
      //MIN PLAYER
      else{
        int best_move = 9;
        for(int i=0; i<moves.size(); i++){
          if(moves.get(i).size() > 0 ){
            number_of_moves++;
            squares_array_clone = play(moves.get(i),squares_array_clone,pos,3);
            int lats_element = moves.get(i).size()-1;
            int[] new_pos = moves.get(i).get(lats_element).getPosition();
            int temp = ExecuteMinMax(m,n,depth-1,number_of_moves,4,distance,squares_array_clone,new_pos,final_move,counter);
            best_move=Math.min(temp-number_of_moves, best_move);
  				  //start from the beginning and try another move
  				  number_of_moves=0;
          }
        }
        return best_move;
      }
    }
  }





  public int finalState(int turn){
    int r = 0;
    if(turn==3){
      r = Integer.MAX_VALUE;
    }
    else{
      r = Integer.MIN_VALUE;
    }
    return r;
  }


    public static void main(String args[]){
      Scanner input = new Scanner(System.in);  // Create a Scanner object
      System.out.println("Enter Number of Rows (M): ");
      int m = input.nextInt();
      System.out.println("Enter Number of Columns (N): ");
      int n = input.nextInt();
      Game_Init initializer = new Game_Init();



      Square[][] squares_array = new Square[m][n];
      for(int i=0; i<m; i++){
        for(int j=0; j<n; j++){
          int[] pos = {i,j};
          Square square = new Square(pos);
          squares_array[i][j] = square;
        }
      }
      System.out.println("Give Position of A separated by commmas: ");
      int[] a_pos = initializer.readPosition();
      System.out.println("Give Position of B separated by commmas: ");
      int[] b_pos = initializer.readPosition();
      squares_array[a_pos[0]][a_pos[1]].setColor(3);
      squares_array[b_pos[0]][b_pos[1]].setColor(4);
      System.out.println("Give percentage of black squares: ");
      int percent = input.nextInt();
      initializer.randomizeBlackSquares(m,n,squares_array,percent);
      initializer.createBoard(m,n,squares_array);
      System.out.println("Enter the max distance that pawns can move: ");
      int distance = input.nextInt();
      distance++;
      Boolean flag1 = false;
      Boolean flag2 = false;
      while(true){
        Move object_move = new Move();

        if(object_move.isFinalCondition(object_move.getPossibleMoves(m, n, distance, squares_array, a_pos))){
          flag1 = true;
          break;
        }
        initializer.PlayerMove(m,n,distance,squares_array,a_pos);
        initializer.createBoard(m,n,squares_array);
        a_pos = initializer.getPos(squares_array, 3);
        Square[][] squares_array_clone = new Square[m][n];
        for(int i=0; i<m; i++){
          for(int j=0; j<n; j++){
            int[] pos = {i,j};
            Square square = new Square(pos);
            square.setColor(squares_array[i][j].getColor());
            squares_array_clone[i][j] = square;
          }
        }

        if(object_move.isFinalCondition(object_move.getPossibleMoves(m, n, distance, squares_array, b_pos))){
          flag2 = true;
          break;
        }
        ArrayList<Square> pc_move = new ArrayList<Square>();
        initializer.ExecuteMinMax(m,n,4,0,4,distance,squares_array_clone,b_pos,pc_move,0);
        initializer.play(pc_move,squares_array,b_pos,4);
        initializer.createBoard(m, n, squares_array);
        b_pos = initializer.getPos(squares_array, 4);
      }


      if(flag1){
        System.out.println("PC WON");
      }
      if(flag2){
        System.out.println("USER WON");
      }
    }

}
