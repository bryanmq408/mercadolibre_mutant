package com.meli.mutants.mutant.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {

    @Value("${dynamodb.host}")
    private String host;

    @Value("${dynamodb.region}")
    private String region;

    @Value("${dynamodb.aws_access_key_id}")
    private String accessKey;

    @Value("${dynamodb.aws_secret_access_key}")
    private String secretKey;

    @Bean
    public AmazonDynamoDB buildAmazonDynamoClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(host, region))
                .withCredentials(
                    new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public DynamoDBMapper getMapper(AmazonDynamoDB client){
        return new DynamoDBMapper(client);
    }
}
