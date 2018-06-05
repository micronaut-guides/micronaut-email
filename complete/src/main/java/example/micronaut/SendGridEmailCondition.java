package example.micronaut;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;

class SendGridEmailCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context) {
        return (notBlankAndNotNull(System.getProperty("SENDGRID_APIKEY")) || notBlankAndNotNull(System.getenv("SENDGRID_APIKEY"))) &&
                (notBlankAndNotNull(System.getProperty("SENDGRID_FROM_EMAIL")) ||  notBlankAndNotNull(System.getenv("SENDGRID_FROM_EMAIL")));
    }

    private boolean notBlankAndNotNull(String str) {
        return str != null && !str.equals("");
    }
}
