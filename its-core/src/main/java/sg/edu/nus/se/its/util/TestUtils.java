package sg.edu.nus.se.its.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.javatuples.Pair;
import sg.edu.nus.se.its.alignment.StructuralMapping;
import sg.edu.nus.se.its.alignment.VariableMapping;
import sg.edu.nus.se.its.model.Expression;
import sg.edu.nus.se.its.model.Function;
import sg.edu.nus.se.its.model.Input;
import sg.edu.nus.se.its.model.Operation;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.model.Variable;

/**
 * File loading utility method to load inputs and intermediate representations for testing purposes.
 */
public class TestUtils {

  /**
   * Stores given program in the JSON format.
   *
   * @param program - Program
   * @param filePath - String
   * @return success
   */
  public static boolean storeProgramAsJsonFile(Program program, String filePath) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    String value = gson.toJson(program);

    try {
      FileWriter myWriter = new FileWriter(filePath);
      myWriter.write(value);
      myWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Loads the Program model from the JSON format into the Program object.
   *
   * @param filePath - String
   * @return Program object
   */
  public static Program loadProgramByFilePath(String filePath) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    Gson gson = builder.create();
    File modelFile = new File(filePath);
    try {
      return gson.fromJson(new FileReader(modelFile), Program.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads the Program model from the JSON format into the Program object.
   *
   * @param name - String
   * @return Program object
   */
  public static Program loadProgramByName(String name) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    Gson gson = builder.create();

    File modelFile = new File("../its-core/src/test/resources/model/" + name + ".json");

    try {
      return gson.fromJson(new FileReader(modelFile), Program.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static File loadFileResourceByName(String name) {
    return new File("../its-core/src/test/resources/source/" + name);
  }

  public static List<String> loadStringInputsByProgramName(String name) {
    return loadStringInputsByProgramName(name, false);
  }

  /**
   * Loads the inputs for a specific program.
   *
   * @param name program name
   * @param ignoreException whether to ignore the exception
   * @return a List of inputs in form of String
   */
  public static List<String> loadStringInputsByProgramName(String name, boolean ignoreException) {
    File inputFile = new File("../its-core/src/test/resources/input/" + name + ".in");
    try {
      Scanner reader = new Scanner(inputFile);
      List<String> result = new ArrayList<>();
      while (reader.hasNext()) {
        result.add(reader.next());
      }
      reader.close();
      return result;
    } catch (FileNotFoundException e) {
      if (ignoreException) {
        return Collections.emptyList();
      } else {
        e.printStackTrace();
        return null;
      }

    }
  }

  /**
   * Loads the inputs for a specific test case.
   *
   * @param name -- source file name
   * @return List of inputs as String objects
   */
  public static List<Input> loadInputsByProgramName(String name) {
    return loadInputsByProgramName(name, false);
  }

  /**
   * Loads the inputs for a specific test case.
   *
   * @param name -- source file name
   * @param ignoreException -- boolean flag
   * @return List of inputs as String objects
   */
  public static List<Input> loadInputsByProgramName(String name, boolean ignoreException) {
    File inputFile = new File("../its-core/src/test/resources/input/" + name + ".in");
    try {
      Scanner reader = new Scanner(inputFile);
      List<String> result = new ArrayList<>();
      while (reader.hasNext()) {
        result.add(reader.next());
      }
      reader.close();
      String[] ioInputs = result.toArray(new String[result.size()]);
      return Arrays.asList(new Input(ioInputs, null));
    } catch (FileNotFoundException e) {
      if (ignoreException) {
        return Arrays.asList(new Input(null, null));
      } else {
        e.printStackTrace();
        return null;
      }

    }
  }

  /**
   * Loads test inputs for Python interpreter.
   *
   * @param name - task name
   * @return list of inputs
   */
  public static List<Input> loadInputsForInterpreterByPythonTaskName(String name) {
    // Map<Integer, String>>
    File inputFiles = new File("../its-core/src/test/resources/python/input/" + name + "/");
    FileFilter fileFilter = new WildcardFileFilter("simplified_input_*.txt");
    File[] files = inputFiles.listFiles(fileFilter);
    Arrays.sort(files);
    List<Input> listOfInputs = new ArrayList<>();
    try {
      for (File file : files) {
        Scanner reader = new Scanner(file);
        List<String> tmpList = new ArrayList<>();
        while (reader.hasNextLine()) {
          tmpList.add(reader.nextLine());
        }
        String[] args = tmpList.toArray(new String[tmpList.size()]);
        Input i = new Input(null, args);
        listOfInputs.add(i);
        reader.close();
      }
      return listOfInputs;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads File objects for Python test task.
   *
   * @param name - task name
   * @return array of File objects
   */
  public static File[] loadProgramsByPythonTaskName(String name) {
    File inputFiles = new File("../its-core/src/test/resources/python/source/" + name + "/");
    FileFilter fileFilter = new WildcardFileFilter("correct_*_*.py");
    return inputFiles.listFiles(fileFilter);
  }

  /**
   * Load structural mapping from file.
   *
   * @param filePath the file path
   * @return the structural mapping
   */
  public static StructuralMapping loadStructuralMappingFromFile(String filePath) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    File modelFile = new File(filePath);
    try {
      return gson.fromJson(new FileReader(modelFile), StructuralMapping.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Load variable mapping from file.
   *
   * @param filePath the file path
   * @return the variable mapping
   */
  public static VariableMapping loadVariableMappingFromFile(String filePath) {
    GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    Gson gson = builder.create();
    File modelFile = new File(filePath);
    try {
      return gson.fromJson(new FileReader(modelFile), VariableMapping.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Store structural mapping as json file.
   *
   * @param mapping the mapping
   * @param filePath the file path
   * @return success
   */
  public static boolean storeStructuralMappingAsJsonFile(StructuralMapping mapping,
      String filePath) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    String value = gson.toJson(mapping);

    try {
      FileWriter myWriter = new FileWriter(filePath);
      myWriter.write(value);
      myWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Store variable mapping as json file.
   *
   * @param mapping the mapping
   * @param filePath the file path
   * @return success
   */
  public static boolean storeVariableMappingAsJsonFile(VariableMapping mapping, String filePath) {
    // Enable complex map-key serialization as VariableMapping contains Map<Variable, Variable>
    GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    String value = gson.toJson(mapping);

    try {
      FileWriter myWriter = new FileWriter(filePath);
      myWriter.write(value);
      myWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Stub method to generate variable mapping.
   *
   * @param index Denotes the test cases index for repair test cases.
   */
  public static VariableMapping hardcodeVariableMapping(int index) {
    sg.edu.nus.se.its.alignment.VariableMapping hardcodedMappings =
        new sg.edu.nus.se.its.alignment.VariableMapping();

    if (index == 1) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping1.put(getVarFromName("-"), getVarFromName("*"));
      mapping1.put(getVarFromName("a"), getVarFromName("x"));
      mapping1.put(getVarFromName("b"), getVarFromName("y"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

      Map<Variable, Variable> mapping2 = new HashMap<>();
      mapping2.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping2.put(getVarFromName("-"), getVarFromName("*"));
      mapping2.put(getVarFromName("a"), getVarFromName("y"));
      mapping2.put(getVarFromName("b"), getVarFromName("x"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping2);

    } else if (index == 2) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping1.put(getVarFromName("-"), getVarFromName("*"));
      mapping1.put(getVarFromName("a"), getVarFromName("a"));
      mapping1.put(getVarFromName("b"), getVarFromName("b"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

      Map<Variable, Variable> mapping2 = new HashMap<>();
      mapping2.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping2.put(getVarFromName("-"), getVarFromName("*"));
      mapping2.put(getVarFromName("a"), getVarFromName("b"));
      mapping2.put(getVarFromName("b"), getVarFromName("a"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping2);

    } else if (index == 3) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping1.put(getVarFromName("-"), getVarFromName("*"));
      mapping1.put(getVarFromName("a"), getVarFromName("x"));
      mapping1.put(getVarFromName("b"), getVarFromName("y"));
      mapping1.put(getVarFromName("c"), getVarFromName("z"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

      Map<Variable, Variable> mapping2 = new HashMap<>();
      mapping2.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping2.put(getVarFromName("-"), getVarFromName("*"));
      mapping2.put(getVarFromName("a"), getVarFromName("y"));
      mapping2.put(getVarFromName("b"), getVarFromName("x"));
      mapping1.put(getVarFromName("c"), getVarFromName("z"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping2);

    } else if (index == 4) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      mapping1.put(getVarFromName("-"), getVarFromName("*"));
      mapping1.put(getVarFromName("a1"), getVarFromName("a1"));
      mapping1.put(getVarFromName("b1"), getVarFromName("b1"));
      mapping1.put(getVarFromName("a2"), getVarFromName("a2"));
      mapping1.put(getVarFromName("b2"), getVarFromName("b2"));
      mapping1.put(getVarFromName("X"), getVarFromName("X"));
      mapping1.put(getVarFromName("Y"), getVarFromName("Y"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 5) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName("a"), getVarFromName("a"));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 6) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("a"), getVarFromName("a"));
      mapping1.put(getVarFromName("b"), getVarFromName("b"));
      mapping1.put(getVarFromName("i"), getVarFromName("i"));
      mapping1.put(getVarFromName(Constants.VAR_COND), getVarFromName(Constants.VAR_COND));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 7) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("i"), getVarFromName("i"));
      mapping1.put(getVarFromName("j"), getVarFromName("j"));
      mapping1.put(getVarFromName("k"), getVarFromName("k"));
      mapping1.put(getVarFromName("N"), getVarFromName("N"));
      mapping1.put(getVarFromName("count"), getVarFromName("count"));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 8) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("a"), getVarFromName("a"));
      mapping1.put(getVarFromName("b"), getVarFromName("b"));
      mapping1.put(getVarFromName("c"), getVarFromName("c"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 9) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("i"), getVarFromName("i"));
      mapping1.put(getVarFromName("number"), getVarFromName("number"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      hardcodedMappings.add("check_prime", mapping1);

    } else if (index == 10) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("i"), getVarFromName("i"));
      mapping1.put(getVarFromName("n"), getVarFromName("n"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      mapping1.put(getVarFromName("T"), getVarFromName("T"));
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 11) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("a"), getVarFromName("a"));
      mapping1.put(getVarFromName("b"), getVarFromName("b"));
      mapping1.put(getVarFromName("i"), getVarFromName("i"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      mapping1.put(getVarFromName("rem"), getVarFromName("rem"));
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    } else if (index == 12) {

      Map<Variable, Variable> mapping1 = new HashMap<>();
      mapping1.put(getVarFromName("C"), getVarFromName("C"));
      mapping1.put(getVarFromName(Constants.VAR_OUT), getVarFromName(Constants.VAR_OUT));
      mapping1.put(getVarFromName(Constants.VAR_IN), getVarFromName(Constants.VAR_IN));
      mapping1.put(getVarFromName(Constants.VAR_RET), getVarFromName(Constants.VAR_RET));
      hardcodedMappings.add(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping1);

    }
    return hardcodedMappings;
  }

  public static Variable getVarFromName(String variableName) {
    return new Variable(variableName);
  }

  /**
   * Checks whether the variables are included in the given mapping.
   *
   * @param mappings - map of variables
   * @param variables - one or many variable names
   * @return boolean
   */
  public static boolean containsVariableMappings(Map<Variable, Variable> mappings,
      String... variables) {
    assert variables.length % 2 == 0;
    for (int i = 0; i < variables.length; i += 2) {
      boolean containsCurrMapping =
          containsVariableMapping(mappings, variables[i], variables[i + 1]);
      if (!containsCurrMapping) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether the two variables are included in the given mapping.
   *
   * @param mappings - map of variables
   * @param varA - variable name
   * @param varB - variable name
   * @return boolean
   */
  public static boolean containsVariableMapping(Map<Variable, Variable> mappings, String varA,
      String varB) {
    for (Map.Entry<Variable, Variable> mapping : mappings.entrySet()) {
      if (mapping.getKey().getUnprimedName().equals(varA)
          && mapping.getValue().getUnprimedName().equals(varB)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper method used if reference function and expected function have different location mapping.
   *
   * @param expectedFunction expected function
   * @param actualFunction actual function from implemented c-parser
   * @param locationMappings From expectedFunction --> generated function
   */
  private static void locationEquivalenceCheck(Function expectedFunction, Function actualFunction,
      HashMap<Integer, Integer> locationMappings) {


    // checking number of locations
    assertEquals(expectedFunction.getLocations().size(), actualFunction.getLocations().size());


    // check every location expression
    for (Integer expectedLocation : locationMappings.keySet()) {
      int actualLocation = locationMappings.get(expectedLocation);
      // check location description
      assertEquals(expectedFunction.getLocationDesc(expectedLocation),
          actualFunction.getLocationDesc(actualLocation));

      // checking expressions for each location
      List<Pair<String, Expression>> expectedExprList = expectedFunction.getExprs(expectedLocation);
      List<Pair<String, Expression>> actualExprList = actualFunction.getExprs(actualLocation);
      HashMap<String, Expression> expectedExprMap = getExprMap(expectedExprList);
      HashMap<String, Expression> actualExprMap = getExprMap(actualExprList);
      assertEquals(expectedExprMap, actualExprMap);
    }

    // check loc trans, bfs through the 'correct' locations and check it against the 'incorrect'
    // locations
    assertEquals(locationMappings.get(expectedFunction.getInitloc()), actualFunction.getInitloc());
    HashSet<Integer> visitedExpected = new HashSet<>();
    Stack<Integer> toVisitExpected = new Stack<>();

    toVisitExpected.add(expectedFunction.getInitloc());
    while (toVisitExpected.size() > 0) {
      int locationExpected = toVisitExpected.pop();

      // getting mapped location to actual program
      int locationActual = locationMappings.get(locationExpected);
      if (visitedExpected.contains(locationExpected)) {
        continue;
      }
      visitedExpected.add(locationExpected);

      Integer trueLocation = expectedFunction.getTrans(locationExpected, true);
      assertEquals(locationMappings.get(trueLocation),
          actualFunction.getTrans(locationActual, true));
      if (trueLocation != null) {
        toVisitExpected.add(trueLocation);
      }
      Integer falseLocation = expectedFunction.getTrans(locationExpected, false);
      assertEquals(locationMappings.get(falseLocation),
          actualFunction.getTrans(locationActual, false));
      if (falseLocation != null) {
        toVisitExpected.add(falseLocation);
      }
    }
  }

  /**
   * Converts a list of pairs (String, Expression) to a map.
   *
   * @param exprList - list of pairs
   * @return mapping
   */
  private static HashMap<String, Expression> getExprMap(List<Pair<String, Expression>> exprList) {
    HashMap<String, Expression> exprMap = new HashMap<>();
    for (Pair<String, Expression> exprPair : exprList) {
      Expression expr = exprPair.getValue1();
      expr = maxTwoArguments(expr);
      exprMap.put(exprPair.getValue0(), expr);
    }
    return exprMap;
  }

  /**
   * Helper method to post process operations to only have a maximum of two arguments for certain
   * operations. This is just for testing purposes, to ensure other semantically equivalent output
   * from the parser is still valid.
   *
   * @return Operation with only a max of two arguments
   */
  private static Expression maxTwoArguments(Expression expression) {
    if (!(expression instanceof Operation)) {
      return expression;
    }
    Operation operation = (Operation) expression;
    if (!isOperator(operation.getName())) {
      // still recursively child operations
      List<Expression> args = operation.getArgs();
      List<Expression> newArgs = new ArrayList<>();

      for (Expression arg : args) {
        arg = maxTwoArguments(arg);
        newArgs.add(arg);
      }
      return new Operation(operation.getName(), newArgs, operation.getLineNumber());
    }
    int numOfArguments = operation.getArgs().size();
    List<Expression> arguments = new ArrayList<>();
    if (numOfArguments < 3) {
      return operation;
    }

    arguments.add(operation.getArgs().get(0));
    arguments.add(operation.getArgs().get(1)); // append first two arguments
    int lineNumber = operation.getLineNumber();
    String operationName = operation.getName();
    Operation currentOperation = new Operation(operationName, arguments, lineNumber);

    for (int i = 2; i < numOfArguments; i++) {
      Expression argument = operation.getArgs().get(i);
      currentOperation =
          new Operation(operationName, Arrays.asList(currentOperation, argument), lineNumber);
    }
    return currentOperation;
  }

  /**
   * Helper method to test if operation name is a unary or binary operator.
   *
   * @param s operation name
   * @return true if operator is an binary/unary operator
   */
  private static boolean isOperator(String s) {
    if (s == null || s.trim().isEmpty()) {
      return false;
    }
    Pattern p = Pattern.compile("[^A-Za-z0-9]");
    Matcher m = p.matcher(s);
    return m.find();
  }


  /**
   * Helper method to for an abstract equivalence check of program models. It checks whether both
   * programs contain the same functions with regard to their names.
   *
   * @param expectedProgram - Program
   * @param actualProgram - Program
   */
  public static void programEquivalenceCheck(Program expectedProgram, Program actualProgram) {
    assertNotNull(expectedProgram);
    assertNotNull(actualProgram);
    Map<String, Function> expectedFuncList = expectedProgram.getFncs();
    Map<String, Function> actualFuncList = actualProgram.getFncs();
    assertEquals(expectedFuncList.keySet(), actualFuncList.keySet());
    assert actualFuncList.keySet().size() > 0;
    for (String name : expectedFuncList.keySet()) {
      HashMap<Integer, Integer> locationMapping =
          blockMappingGenerator(expectedProgram, actualProgram, name);
      locationEquivalenceCheck(expectedProgram.getFunctionForName(name),
          actualProgram.getFunctionForName(name), locationMapping);
    }
  }

  /**
   * Performs trivial block alignment by comparing the descriptions between the two programs.
   *
   * @param expectedProgram reference program
   * @param actualProgram actual program output by C Parser
   * @param functionName name of function to be fixed
   * @return Mapping of reference program location ~ submitted program location
   */
  private static HashMap<Integer, Integer> blockMappingGenerator(Program expectedProgram,
      Program actualProgram, String functionName) {
    Function expectedFunction = expectedProgram.getFunctionForName(functionName);
    Function actualFunction = actualProgram.getFunctionForName(functionName);
    HashMap<Integer, Integer> mapping = new HashMap<>();
    Set<Integer> expectedFunctionLocations = new HashSet<>(expectedFunction.getLocations());
    Set<Integer> actualFunctionLocations = new HashSet<>(actualFunction.getLocations());
    assertEquals(expectedFunctionLocations.size(), actualFunctionLocations.size());

    for (Integer expectedLocation : expectedFunctionLocations) {
      for (Iterator<Integer> actualLocationIterator =
          actualFunctionLocations.iterator(); actualLocationIterator.hasNext();) {
        Integer actualLocation = actualLocationIterator.next();
        if (expectedFunction.getLocationDesc(expectedLocation)
            .equals(actualFunction.getLocationDesc(actualLocation))) {
          mapping.put(expectedLocation, actualLocation);
          actualFunctionLocations.remove(actualLocation);
          break;
        }
      }
    }
    assertEquals(0, actualFunctionLocations.size());
    return mapping;
  }

}
