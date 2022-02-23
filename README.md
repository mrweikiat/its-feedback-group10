# its-feedback-template

## Overview
In this project, you have to propose *innovative* feedback mechanisms, which guide the students to understand his/her mistakes comprehensively. Given a repair candidate, this project should automatically generate meaningful, customized feedback for each submission at different levels, including but not limited to (1) analyze the repair candidate and evaluate the difficulty level to fix the incorrect submission; (2) explain the root cause of the incorrect submission and guide the student to relevant material. In general, the automated feedback require three steps:
* Analyze what information are provided in the repair candidate.
* Propose reasonable feedback mechanisms by using the information in the repair candidates.
* Implement an automated feedback generation system that applies the proposed feedback mechanism.

### Note that this project contains parts of the overall system (e.g., the jar files in the lib folder) that must not be shared with any other groups! Strictly do not share any code or artifacts.

## Entry Points
* This projects has a local dependencies on several components from the baseline implementation. Please use the provided [install.sh](./install.sh) to install the provided jar files.
You can check the [sg.edu.nus.its.integration.SampleWorkflow](./its-integration/src/main/java/sg/edu/nus/se/its/integration/SampleWorkflow.java) and [sg.edu.nus.its.integration.SampleTest](./its-integration/src/test/java/sg/edu/nus/se/its/integration/SampleTest.java) to see how one can use the components. Note that the tests only illustration purpose, however, they also can act as basic tests if you want to. For example, you can use the generated artifacts (e.g., error locations and repair candidates) to generate feedback with your implementation. You would still need to define the specific expected output for your component.

* Since this project is in its nature vaguley defined, we also cannot provide many entry points for now. Below you find an empty class for the feedback generation. However, the concrete interface and implementation will depend on your plans. With **Assignment 4b** you will submit your ideas and we will discuss with you their feasbility within our system.

* [sg.edu.nus.its.feedback.StudentFeedback](./its-feedback/src/main/java/sg/edu/nus/se/its/feedback/StudentFeedback.java)
```
/**
 * Class for student feedback.
 */
public class StudentFeedback implements Feedback {

  @Override
  public String provideFeedback(RepairCandidate repairs, Program submittedProgram) {
    throw new NotImplementedException();
  }

}
```

* [sg.edu.nus.its.feedback.BasicTest](./its-feedback/src/test/java/sg/edu/nus/se/its/feedback/BasicTest.java):
We usually provide the projects with some basic test cases. However, for your project the evaluation is not as straightforward. Therefore, the provided [BasicTest](./its-feedback/src/test/java/sg/edu/nus/se/its/feedback/BasicTest.java) class is still _empty_ and will need to be filled by yourself.

* You can use `mvn clean compile test` to build and test your implementation.

## Restrictions
* You are not allowed to change any code in the [sg.edu.nus.its.its-core](./its-core).
* You are not allowed to change the file/class name or move [sg.edu.nus.its.feedback.StudentFeedback](./its-feedback/src/main/java/sg/edu/nus/se/its/feedback/StudentFeedback.java).
* If you would require any other dependencies or libraries, you first need to seek approval by the tutors.
* You are not allowed to change any file within [.github](./.github).
