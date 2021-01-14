package logika;

import entity.Clen;
import entity.Email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// ZDROJE:
// https://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm
// https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail

/**
 * Trieda s logikou pre posielanie emailov.
 */
public class PosielanieEmailov {

    private static final String skauti = "skauti";
    private static final String skautky = "skautky";
    private static final String vlcata = "vĺčatá";
    private static final String vcielky = "včielky";

    private static final Logger emailLogger = KonfiguraciaLoggera.vratErrorLogger(PosielanieEmailov.class.getName(), Level.SEVERE);

    private DatabazaUtil databazaUtil = new DatabazaUtil();
    private Email email;

    public PosielanieEmailov(Email email){
        this.email = email;
    }

    private  List<Clen> ziskajAdresatov() {
        List<Clen> clenoviaNaPoslanie = new ArrayList<>();

        if (email.getSkautom() == 1){
            clenoviaNaPoslanie.addAll(databazaUtil.DBvratClenovPodlaOddielu(skauti));
        }

        if (email.getSkautkam() == 1){
            clenoviaNaPoslanie.addAll(databazaUtil.DBvratClenovPodlaOddielu(skautky));
        }

        if (email.getVlcatam() == 1){
            clenoviaNaPoslanie.addAll(databazaUtil.DBvratClenovPodlaOddielu(vlcata));
        }

        if (email.getVcielkam() == 1){
            clenoviaNaPoslanie.addAll(databazaUtil.DBvratClenovPodlaOddielu(vcielky));
        }

        if (clenoviaNaPoslanie.isEmpty()){
            throw new IllegalStateException();
        }

        return clenoviaNaPoslanie;
    }

//  POKUS O NACITANIE EMAILOVEJ KONFIGURACIE ZO SUBORU
//    private Properties nacitajEmailConfig(){
//        Properties properties = new Properties();
//
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream("../resources/email.properties");
//            properties.load(inputStream);
//        } catch(Exception e) {
//            emailLogger.log(Level.SEVERE, "Chyba pri otvarani email config suboru", e);
//        } finally {
//            if(inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    emailLogger.log(Level.SEVERE, "Chyba pri zatvarani email config suboru", e);
//                }
//            }
//        }
//        return properties;
//    }

    /**
     * Hlavna metoda zabezpecujuca samotne odoslanie mailov zvolenym adresatom
     * Adresati su vsetci clenovia prislusneho oddielu, ktoremu sa ma odoslat email
     * Emaily su generovane po jednom, pre kazdeho adresata zvlast, kvoli pridavaniu unikatneho QR kodu
     */
    public void posliMail() {
        List<Clen> adresati;
        try {
            adresati = ziskajAdresatov();
        } catch (IllegalStateException e) {
            emailLogger.log(Level.SEVERE, "Nebol vybraty ziaden oddiel, ktoremu sa ma poslat email", e);
            return;
        }

        String od = "user";
        String heslo = "password";

        // Properties emailProp = nacitajEmailConfig();
        Properties emailProp = new Properties();
        String host = "smtp.gmail.com";
        emailProp.put("mail.smtp.starttls.enable", "true");
        emailProp.put("mail.smtp.host", host);
        emailProp.put("mail.smtp.user", od);
        emailProp.put("mail.smtp.password", heslo);
        emailProp.put("mail.smtp.port", "587");
        emailProp.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(emailProp,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(od, heslo);
                    }
                });


        for (Clen clen : adresati) {
            try {
                Message sprava = new MimeMessage(session);
                sprava.setFrom(new InternetAddress(od));
                sprava.setSubject(email.getPredmet());
                sprava.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clen.getEmail()));

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(email.getSprava());
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                if (email.getQrCode() == 1) {

                    messageBodyPart = new MimeBodyPart();

                    QRCodeUtil qrCodeUtil = new QRCodeUtil();

                    String cestaKuQECodu = qrCodeUtil.vratQRCode(clen.getId(), clen.getMeno(), clen.getPriezvisko());

                    String nazovSuboru = "QRCode clena " + clen.getMeno() + " " + clen.getPriezvisko();
                    DataSource source = new FileDataSource(cestaKuQECodu);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(nazovSuboru);
                    multipart.addBodyPart(messageBodyPart);

                    sprava.setContent(multipart);

                }

                Transport.send(sprava);

            } catch (AddressException e) {
                emailLogger.log(Level.SEVERE, e.toString(), e);
            } catch (MessagingException e) {
                emailLogger.log(Level.SEVERE, e.toString(), e);
            }
        }

    }


}
