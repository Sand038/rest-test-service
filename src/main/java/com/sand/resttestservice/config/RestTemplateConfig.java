package com.sand.resttestservice.config;

import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
public class RestTemplateConfig {
  private static final int HTTP_MAX_IDLE = 100;
  private static final int HTTP_KEEP_ALIVE = 30;
  private static final int HTTP_CONNECTION_TIMEOUT = 10;
  private static final int READ_TIMEOUT = 20;
  private static final int WRITE_TIMEOUT = 60;
  private static final int RETRY_INIT_INTERVAL = 5000;
  private static final int RETRY_MAX_ATTEMPTS = 2;

  private static final Map<Class<? extends Throwable>, Boolean> RETRY_EXCEPTIONS =
      Stream.of(new SimpleEntry<>(InternalServerError.class, true))
          .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(RETRY_INIT_INTERVAL);
    retryTemplate.setBackOffPolicy(backOffPolicy);

    SimpleRetryPolicy retryPolicy =
        new SimpleRetryPolicy(RETRY_MAX_ATTEMPTS, RETRY_EXCEPTIONS, true, false);
    retryTemplate.setRetryPolicy(retryPolicy);
//    retryTemplate.registerListener(tokenRefreshRetrySupport());
    return retryTemplate;
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    ConnectionPool okHttpConnectionPool =
        new ConnectionPool(HTTP_MAX_IDLE, HTTP_KEEP_ALIVE, TimeUnit.SECONDS);

    builder
        .connectionPool(okHttpConnectionPool)
        .retryOnConnectionFailure(true)
        .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

    restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(builder.build()));
//    restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

    return restTemplate;
  }
}
