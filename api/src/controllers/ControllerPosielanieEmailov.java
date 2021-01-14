package controllers;

import entity.Email;
import logika.PosielanieEmailov;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.StringReader;

/**
 * API pre posielanie mailov
 * Pre spristupnenie funkcie bolo pouzite REST rozhranie a anotanie POST
 */
@Path("/emailAPI")
public class ControllerPosielanieEmailov {

    @POST
    @Path("/posliEmail")
    public void posliEmail(JsonObject jsonEmail){

        Email email = jsonToEmail(jsonEmail.getString("body"));
        PosielanieEmailov posielanieEmailov = new PosielanieEmailov(email);

        posielanieEmailov.posliMail();

    }

    /**
     * Utility funkcia ktora premeni Json, ktory sa prijme z frontendu, na entitu emailu
     */
    private Email jsonToEmail(String json){
        Email email = new Email();

        JsonParser parser = Json.createParser(new StringReader(json));
        JsonParser.Event event = parser.next();

        event = parser.next();
        event = parser.next();
        email.setSkautom(parser.getInt());

        event = parser.next();
        event = parser.next();
        email.setSkautkam(parser.getInt());

        event = parser.next();
        event = parser.next();
        email.setVlcatam(parser.getInt());

        event = parser.next();
        event = parser.next();
        email.setVcielkam(parser.getInt());

        event = parser.next();
        event = parser.next();
        email.setPredmet(parser.getString());

        event = parser.next();
        event = parser.next();
        email.setSprava(parser.getString());

        event = parser.next();
        event = parser.next();
        email.setIdAkcie(parser.getInt());

        event = parser.next();
        event = parser.next();
        email.setQrCode(parser.getInt());

        return email;
    }

}
