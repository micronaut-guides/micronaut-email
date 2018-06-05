package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;

class AwsSesMailCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        return (notBlankAndNotNull(System.getProperty("AWS_SOURCE_EMAIL")) || notBlankAndNotNull(System.getenv("AWS_SOURCE_EMAIL"))) ||
                (notBlankAndNotNull(System.getProperty("AWS_SOURCE_EMAIL")) &&  notBlankAndNotNull(System.getenv("AWS_SOURCE_EMAIL")));
    }

    private boolean notBlankAndNotNull(String str) {
        return str != null && !str.equals("");
    }
}