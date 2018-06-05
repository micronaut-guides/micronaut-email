package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;

public class AwsCredentialsProviderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        return (notBlankAndNotNull(System.getProperty("aws.accesskeyid")) || notBlankAndNotNull(System.getenv("AWS_ACCESS_KEY_ID"))) &&
                (notBlankAndNotNull(System.getProperty("aws.secretkey")) || notBlankAndNotNull(System.getenv("AWS_SECRET_KEY")));
    }

    private boolean notBlankAndNotNull(String str) {
        return str != null && !str.equals("");
    }
}
