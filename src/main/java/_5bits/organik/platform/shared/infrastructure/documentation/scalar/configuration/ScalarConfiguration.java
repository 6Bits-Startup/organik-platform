package _5bits.organik.platform.shared.infrastructure.documentation.scalar.configuration;

import com.scalar.maven.webmvc.ScalarWebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Registers the Scalar API reference that renders the OpenAPI document of the platform.
 * <p>
 * The Scalar Web MVC integration is imported explicitly so the API reference is available at
 * {@code /scalar} on Spring Boot 4, reading the document published by SpringDoc at {@code /v3/api-docs}.
 * </p>
 */
@Configuration
@Import(ScalarWebMvcAutoConfiguration.class)
public class ScalarConfiguration {
}
