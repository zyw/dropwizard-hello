package cn.c5cn.dropwizard;

import cn.c5cn.dropwizard.health.TemplateHealthCheck;
import cn.c5cn.dropwizard.resources.ContactResource;
import cn.c5cn.dropwizard.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Created by ZYW on 2014/6/16.
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName(){
        return "config";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> helloWorldConfigurationBootstrap) {

    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {
        final HelloWorldResource resource = new HelloWorldResource(helloWorldConfiguration.getTemplate(),helloWorldConfiguration.getDefaultName());
        environment.jersey().register(resource);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment,helloWorldConfiguration.getDataSourceFactory(),"mysql");

        final ContactResource contactResource = new ContactResource(jdbi);
        environment.jersey().register(contactResource);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(helloWorldConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
