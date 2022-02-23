package sg.edu.nus.se.its.feedback;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.repair.RepairCandidate;

/**
 * Class for student feedback.
 */
public class StudentFeedback implements Feedback {

  @Override
  public String provideFeedback(RepairCandidate repairs, Program submittedProgram) {
    throw new NotImplementedException();
  }

}
