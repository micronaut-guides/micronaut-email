package example.micronaut;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;

@Singleton // <1>
@Requires(condition = AwsCredentialsProviderCondition.class) // <2>
class AwsCredentialsProviderService implements AWSCredentialsProvider {

    String accessKey;
    String secretKey;

    AwsCredentialsProviderService(@Value("${AWS_ACCESS_KEY_ID}") String accessKey, // <3>
                                  @Value("${AWS_SECRET_KEY}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Override
    public void refresh() {

    }
}