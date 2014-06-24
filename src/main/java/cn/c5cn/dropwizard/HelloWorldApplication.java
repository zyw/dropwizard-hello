package cn.c5cn.dropwizard;

import cn.c5cn.dropwizard.health.TemplateHealthCheck;
import cn.c5cn.dropwizard.resources.ClientResource;
import cn.c5cn.dropwizard.resources.ContactResource;
import cn.c5cn.dropwizard.resources.HelloWorldResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
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
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {

        environment.jersey().register(new BasicAuthProvider<Boolean>(new PhonebookAuthenticator(),"Web Service Realm"));

        final HelloWorldResource resource = new HelloWorldResource(helloWorldConfiguration.getTemplate(),helloWorldConfiguration.getDefaultName());
        environment.jersey().register(resource);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment,helloWorldConfiguration.getDataSourceFactory(),"mysql");

        final ContactResource contactResource = new ContactResource(jdbi);
        environment.jersey().register(contactResource);

        final Client client = new JerseyClientBuilder(environment).build("REST Client");

        environment.jersey().register(new ClientResource(client));

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(helloWorldConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
