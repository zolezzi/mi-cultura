package unq.edu.li.pdes.micultura.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringConfig {

	@Bean
	public Docket api() {
		TypeResolver typeResolver = new TypeResolver();
		return new Docket(DocumentationType.SWAGGER_2)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(List.class, LocalDateTime.class),
					typeResolver.resolve(List.class, Date.class), Ordered.HIGHEST_PRECEDENCE))
				.select()
				.apis(RequestHandlerSelectors.basePackage("unq.edu.li.pdes.micultura"))
				.paths(PathSelectors.any())
				.build();
	}
	
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	 return (web) -> web.ignoring().requestMatchers(
    			 new AntPathRequestMatcher("swagger-resources/**"),
    			 new AntPathRequestMatcher("resources/**"),
    			 new AntPathRequestMatcher("static/**"),
    			 new AntPathRequestMatcher("css/**"),
    			 new AntPathRequestMatcher("js/**"),
    			 new AntPathRequestMatcher("images/**"),
    			 new AntPathRequestMatcher("swagger-ui/**"),
    			 new AntPathRequestMatcher("v2/api-docs/**"));
    }
    
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, 
    		ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, 
    		EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
    	List<ExposableEndpoint<?>> allEndpoints = new ArrayList<ExposableEndpoint<?>>();
    	Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
    	allEndpoints.addAll(webEndpoints);
    	allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
    	allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
    	String basePath = webEndpointProperties.getBasePath();
    	EndpointMapping endpointMapping = new EndpointMapping(basePath);
    	boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
    	return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
    	return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
