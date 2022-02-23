package sg.edu.nus.se.its.integration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.javatuples.Pair;
import sg.edu.nus.se.its.alignment.AlignmentException;
import sg.edu.nus.se.its.alignment.CfgBasedStructuralAlignment;
import sg.edu.nus.se.its.alignment.StructuralMapping;
import sg.edu.nus.se.its.alignment.VariableAlignment;
import sg.edu.nus.se.its.alignment.VariableMapping;
import sg.edu.nus.se.its.alignment.VariableMappingByDefUseAnalysis;
import sg.edu.nus.se.its.errorlocalizer.BasicErrorLocalizer;
import sg.edu.nus.se.its.errorlocalizer.ErrorLocalisation;
import sg.edu.nus.se.its.errorlocalizer.ErrorLocation;
import sg.edu.nus.se.its.feedback.Feedback;
import sg.edu.nus.se.its.interpreter.Interpreter;
import sg.edu.nus.se.its.interpreter.Interpreter4C;
import sg.edu.nus.se.its.model.Input;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.model.Variable;
import sg.edu.nus.se.its.repair.IlpRepair;
import sg.edu.nus.se.its.repair.LocalRepair;
import sg.edu.nus.se.its.repair.RepairCandidate;
import sg.edu.nus.se.its.util.Constants;
import sg.edu.nus.se.its.util.TestUtils;

/**
 * Simple Integration Workflow.
 */
public class SampleWorkflow {

  public static void main (String[] args) {
    System.out.println("Hello world");
    System.out.println("Hello world");

  }

  /**
   * Concrete integration workflow.
   *
   * @param submittedFile - File
   * @param referenceSolutionFile - File
   * @param inputs - List of String value
   * @return List of Repair Candidates
   */
  public static Pair<RepairCandidate, String> repair(File submittedFile, File referenceSolutionFile,
      List<Input> inputs) {

    System.out.println(">> Submitted file: " + submittedFile.getAbsolutePath());
    System.out.println(">> Reference file: " + referenceSolutionFile.getAbsolutePath());
    System.out.println(">> Inputs: " + inputs);
    System.out.println();


    /* *************************************************** */
    /* PARSING */

    System.out.println(">> Parsing programs...");

    Program submittedProgram = TestUtils.loadProgramByFilePath(submittedFile.getAbsolutePath());
    Program referenceProgram =
        TestUtils.loadProgramByFilePath(referenceSolutionFile.getAbsolutePath());

    System.out.print(">> Submitted Solution:");
    System.out.println(submittedProgram);
    System.out.println();
    System.out.print(">> Reference Solution:");
    System.out.println(referenceProgram);
    System.out.println();


    /* *************************************************** */
    /* ALIGNMENT */

    System.out.println(">> Aligning programs...");

    CfgBasedStructuralAlignment na = new CfgBasedStructuralAlignment();
    StructuralMapping structuralAlignmentResult = null;
    try {
      structuralAlignmentResult =
          na.generateStructuralAlignment(referenceProgram, submittedProgram);

      System.out.println(">> Resulting mapping of blocks in function main: ");
      for (Entry<String, Map<Integer, Integer>> entry : structuralAlignmentResult.getAllMappings()
          .entrySet()) {
        System.out.println(entry.getKey() + ":" + entry.getValue());
      }
      System.out.println();
    } catch (AlignmentException e) {
      e.printStackTrace();
      return null;
    }

    VariableAlignment variableAlignment = new VariableMappingByDefUseAnalysis();
    VariableMapping variableAlignmentResult = null;
    try {
      variableAlignmentResult = variableAlignment.generateVariableAlignment(referenceProgram,
          submittedProgram, structuralAlignmentResult);

      System.out.print(">> All resulting variable allignments: ");
      System.out.println(variableAlignmentResult.getAllMappings());

    } catch (AlignmentException e) {
      e.printStackTrace();
      return null;
    }



    /* *************************************************** */
    /* ERROR LOCALIZER */

    System.out.println();
    System.out.println(">> Identifying error locations...");

    Interpreter interpreter = new Interpreter4C(50000, Constants.DEFAULT_ENTRY_FUNCTION_NAME);

    BasicErrorLocalizer errorLocalizer = new BasicErrorLocalizer();
    ErrorLocalisation errorLocations = errorLocalizer.localizeErrors(submittedProgram,
        referenceProgram, inputs, Constants.DEFAULT_ENTRY_FUNCTION_NAME, structuralAlignmentResult,
        variableAlignmentResult, interpreter);

    System.out.println(">> Identified error locations:");

    for (Map<Variable, Variable> mapping : variableAlignmentResult
        .getMappings(Constants.DEFAULT_ENTRY_FUNCTION_NAME)) {
      for (ErrorLocation location : errorLocations
          .getErrorLocations(Constants.DEFAULT_ENTRY_FUNCTION_NAME, mapping)) {
        System.out.println(location);
      }
    }

    /* *************************************************** */
    /* REPAIR */

    System.out.println();
    System.out.println(">> Generating repair candidates...");


    RepairCandidate repairCandidate = new RepairCandidate();
    try {
      // ILP repair does not require a structural mapping
      repairCandidate = new IlpRepair().repair(referenceProgram, submittedProgram, errorLocations,
          null, variableAlignmentResult, inputs, interpreter).get(0);

      // only one set of complete set of repairs to be output by current implementation of ILP
      // repair
      List<LocalRepair> repairs = repairCandidate.getLocalRepairs();

      System.out.print(">> number of resulting repair candidates: ");
      System.out.println(repairs.size());

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }



    /* *************************************************** */
    /* FEEDBACK */

    System.out.println();
    System.out.println(">> Generating simple feedback...");

    String extractedFeedback = LocalRepair.toString(repairCandidate.getLocalRepairs());

    System.out.println(extractedFeedback);

    return Pair.with(repairCandidate, extractedFeedback);
  }

}
