package org.jasig.cas.util;

import java.util.Collection;
import java.util.Map;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public final class AutowiringSchedulerFactoryBean extends SchedulerFactoryBean
  implements ApplicationContextAware, InitializingBean
{
  private final Logger log = LoggerFactory.getLogger(getClass());
  private ApplicationContext applicationContext;

  public void afterPropertiesSet()
    throws Exception
  {
    Map triggers = this.applicationContext.getBeansOfType(Trigger.class);
    super.setTriggers((Trigger[])triggers.values().toArray(new Trigger[triggers.size()]));

    if (this.log.isDebugEnabled()) {
      this.log.debug("Autowired the following triggers defined in application context: " + triggers.keySet().toString());
    }

    super.afterPropertiesSet();
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    super.setApplicationContext(applicationContext);
    this.applicationContext = applicationContext;
  }
}