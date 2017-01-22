package org.jasig.cas.web.flow;

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

public final class ServiceAuthorizationCheck extends AbstractAction
{

  @NotNull
  private final ServicesManager servicesManager;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  public ServiceAuthorizationCheck(ServicesManager servicesManager) {
    this.servicesManager = servicesManager;
  }

  protected Event doExecute(RequestContext context) throws Exception
  {
    Service service = WebUtils.getService(context);

    if (service == null) {
      return success();
    }
    RegisteredService registeredService = this.servicesManager.findServiceBy(service);

    if (registeredService == null) {
      this.logger.warn("Unauthorized Service Access for Service: [ {} ] - service is not defined in the service registry.", service.getId());
      throw new UnauthorizedServiceException();
    }
    if (!registeredService.isEnabled()) {
      this.logger.warn("Unauthorized Service Access for Service: [ {} ] - service is not enabled in the service registry.", service.getId());
      throw new UnauthorizedServiceException();
    }

    return success();
  }
}