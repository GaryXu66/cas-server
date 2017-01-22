package org.jasig.cas.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.webflow.execution.repository.BadlyFormattedFlowExecutionKeyException;
import org.springframework.webflow.execution.repository.FlowExecutionRepositoryException;

public final class FlowExecutionExceptionResolver
  implements HandlerExceptionResolver
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @NotNull
  private String modelKey = "exception.message";

  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
  {
    if ((!(exception instanceof FlowExecutionRepositoryException)) || 
      ((exception instanceof BadlyFormattedFlowExecutionKeyException))) {
      this.log.debug("Ignoring the received exception due to a type mismatch", exception);
      return null;
    }

    String urlToRedirectTo = request.getRequestURI() + (
      request.getQueryString() != null ? "?" + 
      request.getQueryString() : "");

    this.log.debug("Error getting flow information for URL [{}]", urlToRedirectTo, exception);
    Map model = new HashMap();
    model.put(this.modelKey, StringEscapeUtils.escapeHtml(exception.getMessage()));

    return new ModelAndView(new RedirectView(urlToRedirectTo), model);
  }

  public void setModelKey(String modelKey) {
    this.modelKey = modelKey;
  }
}