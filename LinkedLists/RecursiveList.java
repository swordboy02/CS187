package app;

import java.nio.channels.IllegalSelectorException;
import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {

  private int size;
  private Node<T> head = null;

  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) {
    if(elem == null) throw new NullPointerException();
    Node<T> node = new Node<T>(elem, null);
    if(head == null){
      head = node;
      size++;
    }else{
      node.setNext(head);
      head = node;
      size++;
    }
  }
  private Node<T> lastNode(Node<T> curr){
    if(curr.getNext() == null) return curr;
    else {
      curr = curr.getNext();
      return lastNode(curr);
    }
  }
  @Override
  public void insertLast(T elem) {
    if(elem == null) throw new NullPointerException();
    Node<T> node = new Node<T>(elem, null);
    if(head==null){ 
      head = node;
      size++;
    }
    else{
      Node<T> curr = lastNode(head);
      curr.setNext(node);
      size++;
    }
  }

  @Override
  public void insertAt(int index, T elem) {
    if(elem == null) throw new NullPointerException();
    if(index < 0 || index > this.size) throw new IndexOutOfBoundsException();

    if(head == null && index == 0){
      Node<T> tempNode = new Node<T>(elem, null);
      head = tempNode;
      size++;
    }
    else if(index == 0){
      Node<T> tempNode = new Node<T>(elem, null);
      tempNode.setNext(head);
      head = tempNode;
      size++;
    }
    else if(index == size){
      Node<T> tempNode = new Node<T>(elem, null);
      Node<T> curr = lastNode(head);
      curr.setNext(tempNode);
      size++;
    }
    else{
      Node<T> cur = recurHelp(index,head);
      Node<T> tempNode = new Node<T>(elem, cur);
      System.out.println(tempNode.getNext().getData()+"here");
      cur.setData(elem);
      cur.setNext(tempNode.getNext());
      //System.out.println(cur.getData());
      size++;
    }
  }
  private Node<T> recurHelp(int index, Node<T> node){
    if(index == 0){
      return node;
    }else{
      index--;
      node = node.getNext();
      return recurHelp(index, node);
    }
  }

  @Override
  public T removeFirst() {
    T removedItem = null;
    if(head == null) throw new IllegalStateException();
    removedItem = head.getData();
    head= head.getNext();
    size--;
    return removedItem;
  }

  @Override
  public T removeLast() {
    T removedItem = null;
    if(head == null) throw new IllegalStateException();
    Node<T> temp = lastNode(head);
    removedItem = (T)temp.getData();
    Node<T> temp1= head;
    temp1 = recurRemove(size-1, temp1);
    size--;
    return removedItem;
  }

  @Override
  public T removeAt(int i) {
    if(i < 0 || i >= this.size)throw new IndexOutOfBoundsException();
    T removedItem = null;
    if(head == null) return null;
    Node<T> temp = head;
    temp = recurRemove(i, temp);
    size--;
    removedItem = (T)temp.getData();
    return removedItem;
  }
  private Node<T> recurRemove(int index, Node<T> curr){
    if(index == 1 && curr.getNext() != null){
      if(curr.getNext().getNext() == null){
        Node<T> temp = curr.getNext();
        curr.setNext(null);
        return temp;
      }
      else {
        Node<T> temp = curr.getNext();
        curr.setNext(curr.getNext().getNext());
        return temp;
      }
    }
    else if(index == 0){
      if(curr.getNext() == null){
        Node<T> temp = head;
        head = null;
        return temp;
      }
      else if(curr.getNext().getNext() == null){
        Node<T> temp = curr.getNext();
        curr.setNext(null);
        return temp;
      }
      else {
        Node<T> temp = curr.getNext();
        curr.setData(curr.getNext().getData());
        curr.setNext(curr.getNext().getNext());
        return temp;
      }
    }
    else{
      index--;
      curr = curr.getNext();
      curr = recurRemove(index, curr);
    }
    return curr;
  }

  @Override
  public T getFirst() {
    T item = null;
    if(head == null) throw new IllegalStateException();;
    if(!isEmpty())
      item = head.getData();
    return item;
  }

  @Override
  public T getLast() {
    T item = null;
    if(head == null) throw new IllegalStateException();
    Node<T> temp = lastNode(head);
    if(temp!= null)
      item= temp.getData();
    return item;
  }

  @Override
  public T get(int i) {
    if(i < 0 || i >= this.size)throw new IndexOutOfBoundsException();
    T item = null;
    item = recurGet(i,head);
    return item;
  }
  private T recurGet(int index, Node<T> curr){
    int count = 0;
    if(index == count){
      return curr.getData();
    }else{
      return recurGet(index-1, curr.getNext());
    }
  }

  @Override
  public void remove(T elem) {
    if(elem == null)
      throw new NullPointerException();
    int tempSize = size;
    recurList(head, elem);
    if(size == tempSize){
      throw new ItemNotFoundException();
    }
  }
  private Node<T> recurList(Node<T> curr, T elem){
    if(curr == null)
      return null;
    if(curr.getNext() != null){
      if(curr.getNext().getNext() == null){
        if(curr.getNext().getData().equals(elem)){
          curr.setNext(null);
          size--;
          return curr;
        }
      }
    }
    if(curr.getData().equals(elem)){
      curr.setData(curr.next.getData());
      curr.setNext(curr.getNext().getNext());
      size--;
      return curr;
    }
    else{
      curr= curr.getNext();
      return recurList(curr, elem);
    }
  }

  @Override
  public int indexOf(T elem) {
    int index = -1;
    index = indexOfHelper(elem, head, 0);
    return index;
  }

  private int indexOfHelper(T elem, Node<T> curr, int index){
    if(curr == null)
      return -1;
    if(curr.getData().equals(elem))
      return index;
    else {
      curr = curr.next;
      index++;
      return indexOfHelper(elem, curr, index);
    }
  }


  @Override
  public boolean isEmpty() {
    boolean empty = false;
    if(head == null){
      empty = true;
    }
    return empty;
  }


  public Iterator<T> iterator(){
    Iterator<T> iter = null;
    iter = new LinkedNodeIterator<>(head);
    return iter;
  }
}
