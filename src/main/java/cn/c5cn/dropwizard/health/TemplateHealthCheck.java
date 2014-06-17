package cn.c5cn.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by ZYW on 2014/6/16.
 */
public class TemplateHealthCheck extends HealthCheck {
    private final String template;
    public TemplateHealthCheck(String template){
        this.template = template;
    }
    protected Result check() throws Exception{
        final String saying = String.format(template,"TEST");
        if(!saying.contains("TEST")){
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}
