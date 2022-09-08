package evaluator.arith;

import language.BinaryOperator;
import language.UnaryOperator;
import language.Operand;
import language.Operator;
import language.arith.PlusOperator;
import language.arith.SubOperator;
import language.arith.NegateOperator;
import language.arith.MultOperator;
import language.arith.DivOperator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    stack = new LinkedStack<Operand<Integer>>();
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    int tempVar = 0;
    for (Token<Integer> token : parser) {
      Type type = token.getType();
      switch (type) {
        case OPERAND:
          stack.push(token.getOperand());
          if(expr.length() == 1){
            return stack.pop().getValue();
          }
          break;
        case OPERATOR:
          BinaryOperator<Integer> bin_op = null;
          UnaryOperator<Integer> uni_op = null;
          Operator<Integer> a = token.getOperator();
          if(a.toString().equals("+")) bin_op = new PlusOperator();
          else if(a.toString().equals("-")) bin_op = new SubOperator();
          else if(a.toString().equals("*")) bin_op = new MultOperator();
          else if(a.toString().equals("/")) bin_op = new DivOperator();
          else if(a.toString().equals("!")) uni_op = new NegateOperator();
          Operand<Integer> temp = null;
          if(bin_op != null){
            bin_op.setOperand(1, stack.pop());
            bin_op.setOperand(0, stack.pop());
            temp = bin_op.performOperation();
          }
          else if(uni_op != null){
            uni_op.setOperand(0, stack.pop());
            temp = uni_op.performOperation();
          }
          if(temp != null)
            stack.push(temp);
          tempVar++;
          break;
        default:
          throw new IllegalStateException("Parser returned an invalid Type: " + type);
      }
    }
    if(tempVar == 0) throw new IllegalPostfixExpressionException();
    return stack.pop().getValue();
  }
}
