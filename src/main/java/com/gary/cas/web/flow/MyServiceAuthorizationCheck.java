package com.gary.cas.web.flow;

import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class MyServiceAuthorizationCheck extends AbstractAction {

    @NotNull
    private final ServicesManager servicesManager;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Initialize the component with an instance of the services manager.
     * @param servicesManager the service registry instance.
     */
    public MyServiceAuthorizationCheck(final ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    @Override
    protected Event doExecute(final RequestContext context) throws Exception {
        final Service service = WebUtils.getService(context);
        //No service == plain /login request. Return success indicating transition to the login form
        if (service == null) {
            return success();
        }
        
        if (this.servicesManager.getAllServices().size() == 0) {
            final String msg = String.format("No service definitions are found in the service manager. "
                    + "Service [%s] will not be automatically authorized to request authentication.", service.getId());
            logger.warn(msg);
            throw new UnauthorizedServiceException(UnauthorizedServiceException.CODE_EMPTY_SVC_MGMR);
        }
        final RegisteredService registeredService = this.servicesManager.findServiceBy(service);

        if (registeredService == null) {
            final String msg = String.format("ServiceManagement: Unauthorized Service Access. "
                    + "Service [%s] is not found in service registry.", service.getId());
            logger.warn(msg);
            throw new UnauthorizedServiceException(UnauthorizedServiceException.CODE_UNAUTHZ_SERVICE, msg);
        }
        if (!registeredService.isEnabled()) {
            final String msg = String.format("ServiceManagement: Unauthorized Service Access. "
                    + "Service %s] is not enabled in service registry.", service.getId());
            
            logger.warn(msg);
            throw new UnauthorizedServiceException(UnauthorizedServiceException.CODE_UNAUTHZ_SERVICE, msg);
        }

        return success();
    }
}
