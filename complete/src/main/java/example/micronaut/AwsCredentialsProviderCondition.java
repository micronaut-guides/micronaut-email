package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.core.util.StringUtils;

public class AwsCredentialsProviderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        return envOrSystemProperty("AWS_ACCESS_KEY_ID", "aws.accesskeyid") &&
                envOrSystemProperty("AWS_SECRET_KEY", "aws.secretkey");
    }

    private boolean envOrSystemProperty(String env, String prop) {
        return StringUtils.isNotEmpty(System.getProperty(prop)) || StringUtils.isNotEmpty(System.getenv(env));
    }
}
