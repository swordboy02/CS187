package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t){
    if(t == null) throw new NullPointerException();
    return containsHelper(root, t);
  }

  protected boolean containsHelper(BSTNode<T> node, T key){
    if(node == null) return false;
    int temp = key.compareTo(node.getData());
    if(temp == 0){
			return true;
		}
		if(temp < 0 && node.getLeft() !=null){	
			return containsHelper(node.getLeft(), key);
		}
		if(temp > 0 && node.getRight() != null){
			return containsHelper(node.getRight(), key);
		}
		return false;
	}

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    if(t==null) throw new NullPointerException();
    return get(root, t);
  }
  protected T get(BSTNode<T> node, T key){
    T temp1 = null;
    int temp = ((Comparable)key).compareTo(node.getData());
    if(key.equals(node.getData())){
			temp1 = key;
		}
		if(temp < 0 && node.getLeft() !=null){
      return get(node.getLeft(), key);
		}
		else if(temp > 0 && node.getRight() != null){
			return get(node.getRight(), key);
		}
    return temp1;
	}


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    if(root==null) return null;
    BSTNode<T> temp = root;
    while(temp.getLeft() != null){
      temp = temp.getLeft();
    }
    return temp.getData();
  }


  @Override
  public T getMaximum() {
    if(root==null) return null;
    BSTNode<T> temp = root;
    while(temp.getRight() != null){
      temp = temp.getRight();
    }
    return temp.getData();
  }


  @Override
  public int height() {
    return height(root);
  }
  protected int height(BSTNode<T> node){
    if(node == null) return -1;
    return 1 + Math.max(height(node.getRight()),height(node.getLeft()));
  }


  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }
  protected void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }
  protected void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }


  @Override
  public boolean equals(BSTInterface<T> other) {
    if(other == null) throw new NullPointerException();
    return equals(other.getRoot(), root);
  }
  protected boolean equals(BSTNode<T> other, BSTNode<T> node){
    if(other == node) return true;
    if(node == null || other == null) return false;
    return (other.getData().compareTo(node.getData()) == 0) && (equals(other.getLeft(), node.getLeft())) && (equals(other.getRight(), node.getRight()));
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    if(other == null) throw new NullPointerException();
    else{
      Queue<T> queue1 = new LinkedList();
      Queue<T> queue2 = new LinkedList();
      this.inorderTraverse(queue1,this.getRoot());
      this.inorderTraverse(queue2,other.getRoot());
      while(queue1.size() > 0){
          T temp = queue1.poll();
          if(!queue2.contains(temp))
            return false;
          else{
            queue2.remove(temp);
          }
      }
      return queue1.equals(queue2);
    }
  }

  @Override
  public boolean isBalanced() {
    return isBalanced(root);
  }
  protected boolean isBalanced(BSTNode<T> node){
    if(node == null) return true;
    int leftHeight = height(node.getLeft());
    int rightHeight = height(node.getRight());
    if(Math.abs(leftHeight - rightHeight) <= 1) return true;
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance(){
    Iterator<T> inorderIter = this.inorderIterator();
    int size = this.size();
    T[] vals = (T[]) new Comparable[size];
    for(int i=0;i<size;i++){
      vals[i] = inorderIter.next();
    }
    this.root = null;
    balanceHelper(vals, 0, size-1);
  }
  protected void balanceHelper(T[] array, int low, int high){
    if(low == high){
      this.add(array[low]);
    }
    else if((low+1) == high){
      this.add(array[low]);
      this.add(array[high]);
    }else{
      int mid = (high+low)/2;
      this.add(array[mid]);
      balanceHelper(array, low, mid-1);
      balanceHelper(array, mid+1, high);
    }
  }

  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
