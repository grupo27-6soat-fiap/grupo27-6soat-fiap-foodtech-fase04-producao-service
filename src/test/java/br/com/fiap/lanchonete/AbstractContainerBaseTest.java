package br.com.fiap.lanchonete;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;

public abstract class AbstractContainerBaseTest {

    static final LocalStackContainer MY_LOCALSTACK_CONTAINTER ;
    static final MongoDBContainer MY_MONGODBCONTAINER ;

    static {
        MY_LOCALSTACK_CONTAINTER = new LocalStackContainer();
        MY_LOCALSTACK_CONTAINTER.withServices(LocalStackContainer.Service.SQS);
        MY_LOCALSTACK_CONTAINTER.start();

        MY_MONGODBCONTAINER = new MongoDBContainer();
        MY_MONGODBCONTAINER.start();
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        String mongoUri = String.format("mongodb://%s:%d/test",
                MY_MONGODBCONTAINER.getHost(),
                MY_MONGODBCONTAINER.getMappedPort(27017));
        registry.add("spring.data.mongodb.uri", () -> mongoUri);

        String sqsEndpoint = MY_LOCALSTACK_CONTAINTER.getEndpointOverride(LocalStackContainer.Service.SQS).toString();
        registry.add("spring.cloud.aws.sqs.endpoint", () -> sqsEndpoint);
        registry.add("spring.cloud.aws.region.static", () -> MY_LOCALSTACK_CONTAINTER.getRegion());
    }
}
