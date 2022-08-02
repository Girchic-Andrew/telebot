package org.telebot.rest.facade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.telebot.telegrambot.XTGProcessor;

@Path("/xtg")
public class XTGFacade {
  private static final Logger logger = Logger.getRootLogger();
  
  @POST
  @Path("/update")
  @Consumes({"application/json"})
  public Response onUpdateReceived(String jsonObject) {
    logger.info(jsonObject);
    XTGProcessor processor = new XTGProcessor();
    return Response.ok().build();
  }
}
