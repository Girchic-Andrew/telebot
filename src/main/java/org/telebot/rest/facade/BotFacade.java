package org.telebot.rest.facade;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.telebot.telegrambot.UpdateProcessor;
import org.telebot.telegrambot.WebhookInitialize;

@Path("/bot")
public class BotFacade {
  private static final Logger logger = Logger.getRootLogger();
  
  @POST
  @Path("/control")
  @Consumes({"application/json"})
  public Response onControlUpdateReceived(String jsonObject) {
    logger.info(jsonObject);
    UpdateProcessor processor = new UpdateProcessor(jsonObject);
    processor.process2();
    return Response.ok().build();
  }
  
  @POST
  @Path("/update")
  @Consumes({"application/json"})
  public Response onUpdateReceived(String jsonObject) {
    logger.info(jsonObject);
    UpdateProcessor processor = new UpdateProcessor(jsonObject);
    processor.process2();
    return Response.ok().build();
  }
  
  @GET
  @Path("/enablewebhook")
  @Produces({"text/plain"})
  public String enableWebhook() {
    WebhookInitialize initialize = new WebhookInitialize();
    return initialize.init();
  }
  
  @GET
  @Path("/update/{id}/{message}")
  @Consumes({"application/x-www-formurlencoded"})
  @Produces({"text/plain"})
  public String onUpdateReceivedGet(@PathParam("id") Integer id, @PathParam("message") String message) {
    logger.info(id.toString() + " " + message);
    return id.toString() + " " + message;
  }
}
