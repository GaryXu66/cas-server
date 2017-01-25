//package org.jasig.cas.web.flow;
//
//import javax.validation.constraints.NotNull;
//import org.jasig.cas.authentication.principal.Service;
//import org.jasig.cas.services.ServicesManager;
//import org.jasig.cas.services.UnauthorizedServiceException;
//import org.jasig.cas.web.support.WebUtils;
//import org.springframework.webflow.action.AbstractAction;
//import org.springframework.webflow.execution.Event;
//import org.springframework.webflow.execution.RequestContext;
//
//public class GatewayServicesManagementCheck extends AbstractAction
//{
//
//  @NotNull
//  private final ServicesManager servicesManager;
//
//  public GatewayServicesManagementCheck(ServicesManager servicesManager)
//  {
//    this.servicesManager = servicesManager;
//  }
//
//  protected Event doExecute(RequestContext context) throws Exception
//  {
//    Service service = WebUtils.getService(context);
//
//    boolean match = this.servicesManager.matchesExistingService(service);
//
//    if (match) {
//      return success();
//    }
//
//    throw new UnauthorizedServiceException(String.format("Service [%s] is not authorized to use CAS.", new Object[] { service.getId() }));
//  }
//}