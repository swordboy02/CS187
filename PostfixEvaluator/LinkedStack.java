package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure to allow for
 * unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {
  private LLNode<T> head;
  private int stSize;
  public LinkedStack(){ head = null; stSize = 0;}

  /** {@inheritDoc} */
  @Override
  public T pop() throws StackUnderflowException {
    if(head == null) throw new StackUnderflowException();
    T temp = head.getData();
    head = head.getNext();
    stSize--;
    return temp;
  }

  /** {@inheritDoc} */
  @Override
  public T top() throws StackUnderflowException {
    if(head == null) throw new StackUnderflowException();
    return head.getData();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    return (head == null);
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return stSize;
  }

  /** {@inheritDoc} */
  @Override
  public void push(T elem) {
    LLNode<T> insertTop = new LLNode<T>(elem);
      insertTop.setNext(head);
      head = insertTop;
      stSize++;
  }
}
