package sg.edu.nus.se.its.integration;

import com.google.ortools.Loader;
import sg.edu.nus.se.its.model.Input;
import sg.edu.nus.se.its.repair.LocalRepair;
import sg.edu.nus.se.its.repair.RepairCandidate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Simple test collection for the feedback.
 */
public class SampleTest {

  static final String unitTestFilePath = System.getProperty("user.dir") + "./src/test/resources/";

  @BeforeAll
  public static void loadOrTools() {
    Loader.loadNativeLibraries();
  }

  /**
   * Loads the inputs for a specific test case.
   *
   * @param name -- source file name
   * @param ignoreException -- boolean flag
   * @return List of inputs as String objects
   */
  private static List<Input> loadInputsByProgramName(String name, boolean ignoreException) {
    File inputFile = new File("../its-integration/src/test/resources/input/" + name + ".in");
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

  @Test
  public void sampleTest_1() {
    String referenceFileName = "c1.c";
    String submittedFileName = "i1.c";
    String inputFileName = "c1.c";

    System.out.println(">> testing: " + referenceFileName);

    File referenceSolution = new File("./src/test/resources/model/" + referenceFileName + ".json");
    File submittedFile = new File("./src/test/resources/model/" + submittedFileName + ".json");
    List<Input> inputs = loadInputsByProgramName(inputFileName, true);

    Pair<RepairCandidate, String> output =
        SampleWorkflow.repair(submittedFile, referenceSolution, inputs);
    assert output != null;
    List<LocalRepair> repairs = output.getValue0().getLocalRepairs();

    assertNotNull(repairs,
        "[" + referenceFileName + "] " + "The generated list of repair must not be null.");
    assertTrue(repairs.size() > 0,
        "[" + referenceFileName + "] " + "There must be at least one generated repair candidate.");

    String feedback = output.getValue1();

    assertNotNull(feedback, "[" + referenceFileName + "] The generated feedback must not be null.");
    assertTrue(feedback.length() > 0,
        "[" + referenceFileName + "] The generated feedback must not be empty.");
  }

  @Test
  public void sampleTest_2() {
    String referenceFileName = "c2.c";
    String submittedFileName = "i2.c";
    String inputFileName = "c2.c";

    System.out.println(">> testing: " + referenceFileName);

    File referenceSolution = new File("./src/test/resources/model/" + referenceFileName + ".json");
    File submittedFile = new File("./src/test/resources/model/" + submittedFileName + ".json");
    List<Input> inputs = loadInputsByProgramName(inputFileName, true);

    Pair<RepairCandidate, String> output =
        SampleWorkflow.repair(submittedFile, referenceSolution, inputs);
    assert output != null;
    List<LocalRepair> repairs = output.getValue0().getLocalRepairs();

    assertNotNull(repairs,
        "[" + referenceFileName + "] " + "The generated list of repair must not be null.");
    assertTrue(repairs.size() > 0,
        "[" + referenceFileName + "] " + "There must be at least one generated repair candidate.");

    String feedback = output.getValue1();

    assertNotNull(feedback, "[" + referenceFileName + "] The generated feedback must not be null.");
    assertTrue(feedback.length() > 0,
        "[" + referenceFileName + "] The generated feedback must not be empty.");
  }

  @Test
  public void sampleTest_3() {
    String referenceFileName = "c3.c";
    String submittedFileName = "i3.c";
    String inputFileName = "c3.c";

    System.out.println(">> testing: " + referenceFileName);

    File referenceSolution = new File("./src/test/resources/model/" + referenceFileName + ".json");
    File submittedFile = new File("./src/test/resources/model/" + submittedFileName + ".json");
    List<Input> inputs = loadInputsByProgramName(inputFileName, true);

    Pair<RepairCandidate, String> output =
        SampleWorkflow.repair(submittedFile, referenceSolution, inputs);
    assert output != null;
    List<LocalRepair> repairs = output.getValue0().getLocalRepairs();

    assertNotNull(repairs,
        "[" + referenceFileName + "] " + "The generated list of repair must not be null.");
    assertTrue(repairs.size() > 0,
        "[" + referenceFileName + "] " + "There must be at least one generated repair candidate.");

    String feedback = output.getValue1();

    assertNotNull(feedback, "[" + referenceFileName + "] The generated feedback must not be null.");
    assertTrue(feedback.length() > 0,
        "[" + referenceFileName + "] The generated feedback must not be empty.");
  }

  @Test
  public void sampleTest_4() {
    String referenceFileName = "c4.c";
    String submittedFileName = "i4.c";
    String inputFileName = "c4.c";

    System.out.println(">> testing: " + referenceFileName);

    File referenceSolution = new File("./src/test/resources/model/" + referenceFileName + ".json");
    File submittedFile = new File("./src/test/resources/model/" + submittedFileName + ".json");
    List<Input> inputs = loadInputsByProgramName(inputFileName, true);

    Pair<RepairCandidate, String> output =
        SampleWorkflow.repair(submittedFile, referenceSolution, inputs);
    assert output != null;
    List<LocalRepair> repairs = output.getValue0().getLocalRepairs();

    assertNotNull(repairs,
        "[" + referenceFileName + "] " + "The generated list of repair must not be null.");
    assertTrue(repairs.size() > 0,
        "[" + referenceFileName + "] " + "There must be at least one generated repair candidate.");

    String feedback = output.getValue1();

    assertNotNull(feedback, "[" + referenceFileName + "] The generated feedback must not be null.");
    assertTrue(feedback.length() > 0,
        "[" + referenceFileName + "] The generated feedback must not be empty.");
  }

  @Test
  public void sampleTest_5() {
    String referenceFileName = "c5.c";
    String submittedFileName = "i5.c";
    String inputFileName = "c5.c";

    System.out.println(">> testing: " + referenceFileName);

    File referenceSolution = new File("./src/test/resources/model/" + referenceFileName + ".json");
    File submittedFile = new File("./src/test/resources/model/" + submittedFileName + ".json");
    List<Input> inputs = loadInputsByProgramName(inputFileName, true);

    Pair<RepairCandidate, String> output =
        SampleWorkflow.repair(submittedFile, referenceSolution, inputs);
    assert output != null;
    List<LocalRepair> repairs = output.getValue0().getLocalRepairs();

    assertNotNull(repairs,
        "[" + referenceFileName + "] " + "The generated list of repair must not be null.");
    assertTrue(repairs.size() > 0,
        "[" + referenceFileName + "] " + "There must be at least one generated repair candidate.");

    String feedback = output.getValue1();

    assertNotNull(feedback, "[" + referenceFileName + "] The generated feedback must not be null.");
    assertTrue(feedback.length() > 0,
        "[" + referenceFileName + "] The generated feedback must not be empty.");
  }

}
