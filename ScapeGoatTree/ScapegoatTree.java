package structures;

public class ScapegoatTree<T extends Comparable<T>> extends structures.BinarySearchTree<T> {
  private int upperBound;
  int n = super.size();
  @Override
  public void add(T t) {
    upperBound++;
    super.add(t);
    if(super.height() > (Math.log(upperBound)/Math.log(3/2))){
    }
  }
  @Override
  public boolean remove(T element) {
    if(super.remove(element)){
      if(2*n < upperBound){
        balance();
        n--;
        upperBound = n;
      }
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }*/
  }
}
