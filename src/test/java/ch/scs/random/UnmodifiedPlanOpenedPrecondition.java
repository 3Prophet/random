package ch.scs.random;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import ch.scs.random.dom.User.Role;

public class UnmodifiedPlanOpenedPrecondition implements BeforeTestExecutionCallback {

    private static int index = 0;

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {

        SchaltplanEditorRobot robot = (SchaltplanEditorRobot) context.getRequiredTestClass()
                .getMethod("getRobot")
                .invoke(context.getRequiredTestInstance());

        robot.openUnmodifiedSchaltplanAs(getNextUserRole());
    }

    private static String getNextUserRole() {
        return Role.values()[index++].toString();
    }

}
