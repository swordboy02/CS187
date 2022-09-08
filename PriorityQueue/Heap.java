package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
    this.isMaxHeap = isMaxHeap;
    this.comparator = comparator;
    numElements = 0;
    this.heap = (T[]) new Object[INIT_SIZE];
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in
   * the array and then this method is called on that index.
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index){
    bubbleUpHelper(index, this.heap);
  }
  private void bubbleUpHelper(int index, T[] array) {
    T temp = null;
    while (index > 0) {
      int a = (index - 1) / 2;
      if (this.compare(this.heap[index], this.heap[a]) < 0) return;
      else {
        temp = this.heap[index];
        this.heap[index] = this.heap[a];
        this.heap[a] = temp;
        index = a;
      }
    }
  }
  public void expandCapacity(){
    T[] temp = (T[]) new Object[heap.length*2];
    for(int i = 0; i< heap.length; i++){
      temp[i] = heap[i];
    }
    heap = temp;
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   *
   * @param index
   */
  public void bubbleDown(int index){
    bubbleDownHelper(index, this.heap);
  }
  private void bubbleDownHelper(int index, T[] array){
    T tempx = null;
    int child = 2*index+1;
    T value = array[index];
    while(child < this.size()){
      T max = value;
      int maxInd = -1;
      for(int i =0; i<2 && i+child < this.size(); i++){
        if(this.compare(this.heap[i+child], max) > 0){
          max = this.heap[i+child];
          maxInd = i+child;
        }
      }
      if(max == value){
        return;
      }
      else{
        tempx = this.heap[index];
        this.heap[index] = this.heap[maxInd];
        this.heap[maxInd] = tempx;
        index = maxInd;
        child = 2*index+1;
      }
    }
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
    if(this.size() ==0) isEmpty = true;
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size(){
    int size = -100;
    size = numElements;
    return size;
  }

  /**
   * Compare method to implement max/min heap behavior.  It calls the comparae method from the
   * comparator object and multiply its output by 1 and -1 if max and min heap respectively.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, negative int otherwise
   */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
    T data = null;
    if(isEmpty()) throw new QueueUnderflowException();
    data = this.heap[0];
    return data;
  }

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException{
    T data = null;
    if(isEmpty()) throw new QueueUnderflowException();
    data = this.heap[0];
    this.heap[0] = this.heap[this.size()-1];
    numElements--;
    bubbleDown(0);
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueue(T newElement) {
    this.heap[this.size()] = newElement;
    bubbleUp(this.size());
    numElements++;
    if(numElements == heap.length) expandCapacity();
  }


}