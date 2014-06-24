package cn.c5cn.dropwizard;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by ZYW on 2014/6/24.
 */
public class PhonebookAuthenticator implements Authenticator<BasicCredentials,Boolean> {
    @Override
    public Optional<Boolean> authenticate(BasicCredentials c) throws AuthenticationException {
        if(c.getUsername().equals("zhangsan") && c.getPassword().equals("000000")){
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
