package controllers;

import entity.Akcia;
import entity.Clen;
import entity.UcastNaAkcii;
import logika.DatabazaUtil;
import logika.QRCodeUtil;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * API pre pracu s databazou
 * Pre spristupnenie funkcii bolo pouzite REST rozhranie a anotanie POST a GET
 */
@Path("/dbAPI")
public class ControllerDatabazaUtil {
    private DatabazaUtil databazaUtil = new DatabazaUtil();

    @GET
    @Path("/getAkcie")
    public JsonArray vratAkcie() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<Akcia> akcie = databazaUtil.DBvratVsetkyAkcie();

        for (Akcia akcia:akcie){
            builder.add(akciaToJson(akcia));
        }

        return builder.build();
    }

    /**
     * Utility funkcia ktora premeni akciu na Json, ktory sa posle na frontend
     */
    private JsonObject akciaToJson(Akcia akcia) {
        return Json.createObjectBuilder().
                add("id", akcia.getId()).
                add("nazov", akcia.getNazov()).
                add("popis", akcia.getPopis()).
                add("zodpovedna_osoba", akcia.getZodpovednaOsoba().getMeno()).
                add("miesto_konania", akcia.getMiestoKonania()).
                add("oddiel", akcia.getOddiel()).
                add("datum", akcia.getDatumAkcie().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).
                build();
    }

    @POST
    @Path("/getUcast")
    public JsonArray vratUcastniciAkcie(JsonObject jsonAkcia) {
        Akcia akcia = jsonToAkcia(jsonAkcia.getString("body"));
        JsonArrayBuilder builder = Json.createArrayBuilder();

        List<Clen> clenovia = databazaUtil.DBvratPrihlasenychNaAkciu(akcia);

        for (Clen clen:clenovia){
            builder.add(clenToJson(clen));
        }

        return builder.build();
    }

    private JsonObject clenToJson(Clen clen) {

        return Json.createObjectBuilder().
                add("meno", clen.getMeno()).
                add("priezvisko", clen.getPriezvisko()).
                add("cislo", clen.getCisloNaClena()).
                add("email", clen.getEmail()). //
                add("rola", clen.getRola()).
                add("cislo_na_rodica", clen.getCisloNaRodica()).
                add("datum_narodenia", clen.getDatumNarodenia().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).
                add("miesto_narodenia", clen.getMiestoNarodenia()).
                add("obec", clen.getObec()).
                add("psc", clen.getPsc()).
                add("ulica", clen.getUlica()).
                add("cislo_domu", clen.getCisloDomu()).
                add("oddiel", clen.getOddiel()).
                build();
    }

    /**
     * Utility funkcia ktora premeni Json na akciu, ktory sa prijme z frontendu
     */
    private Akcia jsonToAkcia(String json){
        Akcia akcia = new Akcia();

        JsonParser parser = Json.createParser(new StringReader(json));
        JsonParser.Event event = parser.next();

        event = parser.next();
        event = parser.next();
        akcia.setId(parser.getInt());

        event = parser.next();
        event = parser.next();
        akcia.setNazov(parser.getString());

        event = parser.next();
        event = parser.next();
        akcia.setPopis(parser.getString());

        event = parser.next();
        event = parser.next();
        akcia.setZodpovednaOsoba(new Clen());
        akcia.getZodpovednaOsoba().setMeno(parser.getString());

        event = parser.next();
        event = parser.next();
        akcia.setMiestoKonania(parser.getString());

        event = parser.next();
        event = parser.next();
        akcia.setOddiel(parser.getString());

        event = parser.next();
        event = parser.next();
        String datum = parser.getString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        akcia.setDatumAkcie(LocalDate.parse(datum, formatter));

        return akcia;
    }

    @POST
    @Path("/vratIdClena")
    public int vratClenaPodlaMena(JsonObject jsonClen){
        Clen clen = new Clen();
        clen.setMeno(jsonClen.getString("meno"));
        clen.setPriezvisko(jsonClen.getString("priezvisko"));
        return databazaUtil.DBvratIdClenaPodlaMena(clen);
    }

    @POST
    @Path("/pridajDochadzku")
    public void pridajDochadku(JsonObject jsonUcastNaAkcii){

        UcastNaAkcii ucastNaAkcii = new UcastNaAkcii();
        Clen clen = databazaUtil.DBvratClenaPodlaId(jsonUcastNaAkcii.getInt("idClena"));
        ucastNaAkcii.setClen(clen);
        Akcia akcia = databazaUtil.DBvratAkciuPodlaId(jsonUcastNaAkcii.getInt("idAkcie"));
        ucastNaAkcii.setAkcia(akcia);
        ucastNaAkcii.setIsic(jsonUcastNaAkcii.getBoolean("isic"));
        ucastNaAkcii.setKartaNaVlaky(jsonUcastNaAkcii.getBoolean("vlaky"));
        ucastNaAkcii.setKartaPoistenca(jsonUcastNaAkcii.getBoolean("poistenca"));
        ucastNaAkcii.setPoplatok(jsonUcastNaAkcii.getInt("poplatok"));
        ucastNaAkcii.setPrihlaska(jsonUcastNaAkcii.getBoolean("prihlaska"));

        databazaUtil.DBpridajUcastNaAkciu(ucastNaAkcii);
    }

    @POST
    @Path("/pridajAkciu")
    public boolean pridajAkciu(JsonObject jsonAkcia){
        Akcia akcia = new Akcia();

        akcia.setOddiel(jsonAkcia.getString("oddiel"));
        akcia.setMiestoKonania(jsonAkcia.getString("miestoKonania"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        akcia.setDatumAkcie(LocalDate.parse(jsonAkcia.getString("datum"), formatter));

        Clen clen = new Clen();
        clen.setMeno(jsonAkcia.getString("zodpovednaOsobaMeno"));
        clen.setPriezvisko(jsonAkcia.getString("zodpovednaOsobaPriezvisko"));
        Clen hladany = databazaUtil.DBvratClenaPodlaMena(clen);
        akcia.setZodpovednaOsoba(hladany);

        akcia.setPopis(jsonAkcia.getString("popis"));
        akcia.setNazov(jsonAkcia.getString("nazov"));

        databazaUtil.DBpridajAkciu(akcia);

        return true;
    }

    @POST
    @Path("/qrToIdClena")
    public int vratClenaPodlaQRCodu(JsonObject jsonQRCode){
        String qrCodeString = jsonQRCode.getString("image");
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        int idClena = qrCodeUtil.rozlustiQRCode(qrCodeString);

        return idClena;
    }

}

