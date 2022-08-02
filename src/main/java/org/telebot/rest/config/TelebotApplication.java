package org.telebot.rest.config;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.telebot.rest.facade.BotFacade;

@ApplicationPath("/")
public class TelebotApplication extends Application {
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<>();
    set.add(BotFacade.class);
    return set;
  }
}
