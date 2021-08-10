public class Square{

  private int color; //1 white 2 black 3 A 4 B
  private int[] position = new int[2];
  public Square(int[] pos){
    color = 1;
    position[0] = pos[0];
    position[1] = pos[1];
  }

  public int getColor(){
    return color;
  }

  public void setColor(int color){
      this.color=color;
  }

  public int[] getPosition(){
    return position;
  }
  public String toString(){
    String s = "Position: "+position[0]+","+position[1];
    return s;
  }
}
