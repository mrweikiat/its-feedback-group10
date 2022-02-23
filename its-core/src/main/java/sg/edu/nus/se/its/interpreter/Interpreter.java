package sg.edu.nus.se.its.interpreter;

import java.util.List;
import org.javatuples.Pair;
import sg.edu.nus.se.its.model.Constant;
import sg.edu.nus.se.its.model.Expression;
import sg.edu.nus.se.its.model.Function;
import sg.edu.nus.se.its.model.Input;
import sg.edu.nus.se.its.model.Memory;
import sg.edu.nus.se.its.model.Operation;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.model.Variable;

/**
 * Interfaces for the interpretation of the program execution.
 */
public interface Interpreter {

  /**
   * Executes a program and produces an execution trace.
   *
   * @param program -- Program object
   * @return execution trace
   */
  public Trace executeProgram(Program program);

  /**
   * Executes a program with the given input and produces an execution trace.
   *
   * @param program -- Program object
   * @param input -- program's input
   * @return execution trace
   */
  public Trace executeProgram(Program program, Input input);

  /**
   * Executes the provided program element with respect to the given memory.
   *
   * @param executable -- executable Object
   * @param memory -- Memory object
   * @return result of execution
   */
  public Object execute(Executable executable, Memory memory);

  /**
   * Executes a function and produces a Trace object.
   *
   * @param function -- Function object to execute
   * @param memory -- Memory object
   * @return result of execution
   */
  public Object executeFunction(Function function, Memory memory);

  /**
   * Executes a constant, i.e., determines its value and the correct data type.
   *
   * @param constant -- Constant object
   * @param memory -- Memory object
   * @return value of Constant object
   */
  public Object executeConstant(Constant constant, Memory memory);

  /**
   * Executes the given operation with regard to the memory instance.
   *
   * @param operation -- Operation object
   * @param memory -- Memory object
   * @return the result of the execution
   */
  public Object executeOperation(Operation operation, Memory memory);

  /**
   * Executes the variable, i.e., retrieves the variable for the current memory instance.
   *
   * @param variable - Variable object
   * @param memory - Memory object
   * @return value of variable in memory
   */
  public Object executeVariable(Variable variable, Memory memory);

  /**
   * Executes the given block with regard to the provided parameters.
   *
   * @param function -- the function to execute
   * @param memory -- memory for the execution, can be null
   * @param loc -- the location of the function to execute
   * @return TraceEntry object as result of the interpreted execution
   */
  public TraceEntry executeBlock(Function function, Memory memory, int loc);

  /**
   * Executes the given block with regard to the provided parameters.
   *
   * @param function -- the function to execute
   * @param block -- the block to execute
   * @param memory -- memory for the execution, can be null
   * @return TraceEntry object as result of the interpreted execution
   */
  public TraceEntry executeBlock(Function function, List<Pair<String, Expression>> block,
      Memory memory);

  /**
   * Sets an internal timeout for the interpretation/execution of the program.
   *
   * @param timeout -- timeout in seconds
   */
  public void setTimeout(int timeout);

}
