package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.core.util.StringUtils;

public class AwsSesMailCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        return envOrSystemProperty("AWS_SOURCE_EMAIL", "aws.sourceemail") &&
                envOrSystemProperty("AWS_REGION", "aws.region");
    }

    private boolean envOrSystemProperty(String env, String prop) {
        return StringUtils.isNotEmpty(System.getProperty(prop)) || StringUtils.isNotEmpty(System.getenv(env));
    }
}
