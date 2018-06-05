package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;

class AwsCredentialsProviderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        return (notBlankAndNotNull(System.getProperty("AWS_ACCESS_KEY_ID")) || notBlankAndNotNull(System.getenv("AWS_ACCESS_KEY_ID"))) &&
                (notBlankAndNotNull(System.getProperty("AWS_SECRET_KEY")) || notBlankAndNotNull(System.getenv("AWS_SECRET_KEY")));
    }

    private boolean notBlankAndNotNull(String str) {
        return str != null && !str.equals("");
    }
}
