package unq.edu.li.pdes.micultura.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class MapperProviderFactory {
	
	@Bean()
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public MapperFactory produceMapper() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		return mapperFactory;
	}
}
