package com.gary.cas.sso.handler;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;

import com.gary.cas.service.biz.UserBizService;

public class MyAcceptUsersAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    /** The list of users we will accept. */
   /* @NotNull
    private Map<String, String> users;*/
    
    @NotNull 
    private UserBizService userBizService;

    /** {@inheritDoc} */
    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String username = credential.getUsername();
        try {
        	boolean loginResult = userBizService.loginValid(credential.getUsername(), credential.getPassword());
        	if(!loginResult){
        		throw new AccountNotFoundException("用户不存在或者用户名与密码不匹配");
        	}
		} catch (Exception e) {
			throw new FailedLoginException("登录验证时出现异常:"+e.getMessage());
		}
        
        /*final String cachedPassword = this.users.get(username);

        if (cachedPassword == null) {
           logger.debug("{} was not found in the map.", username);
           throw new AccountNotFoundException(username + " not found in backing map.");
        }

        final String encodedPassword = this.getPasswordEncoder().encode(credential.getPassword());
        if (!cachedPassword.equals(encodedPassword)) {
            throw new FailedLoginException();
        }*/
        return createHandlerResult(credential, new SimplePrincipal(username), null);
    }

    /**
     * @param users The users to set.
     */
    /*public final void setUsers(final Map<String, String> users) {
        this.users = Collections.unmodifiableMap(users);
    }*/
    
    public void setUserBizService(UserBizService userBizService) {
		this.userBizService = userBizService;
	}
}
