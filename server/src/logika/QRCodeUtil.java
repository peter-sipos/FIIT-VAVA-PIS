package logika;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

// ZDROJ:
// https://www.callicoder.com/generate-qr-code-in-java-using-zxing/
// https://www.callicoder.com/qr-code-reader-scanner-in-java-using-zxing/
// https://stackoverflow.com/questions/23979842/convert-base64-string-to-image/23979996


/**
 * Trieda obsluhujuca pracu s QR kodom
 */
public class QRCodeUtil {

//    POKUS O LOGGING PODLA PROPERTIES SUBORU
//    private static final LogManager logManager = LogManager.getLogManager();
//    private static final Logger qrLogger = Logger.getLogger("qrLogger");
//    static{
//
//        try {
//            String cesta = System.getProperty("jboss.server.config.dir") + "/logger.properties";
//            logManager.readConfiguration(new FileInputStream(cesta));
//        } catch (IOException exception) {
//            qrLogger.log(Level.SEVERE, "Error in loading configuration",exception);
//        }
//    }

    private static final Logger qrLogger = KonfiguraciaLoggera.vratErrorLogger(QRCodeUtil.class.getName(), Level.SEVERE);

    /**
     * Funkcia pouzita pri odosielani mailov. Vytvori QR kod, vlozi donho id clena, ulozi ho ako obrazok na prislusnom mieste a vrati cestu k nemu
     */
    public String vratQRCode(int idClena, String meno, String priezvisko) {

        String cestaKSuboru = null;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(Integer.toString(idClena), BarcodeFormat.QR_CODE, 400, 400);
            cestaKSuboru = "C:/Users/peter/Documents/Škola/VAVA/PIS/qrCodes/qrCode_" + meno + "_" + priezvisko + "_.png";
            Path p = Paths.get(cestaKSuboru).toAbsolutePath();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", p);

        } catch (WriterException e) {
            qrLogger.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            qrLogger.log(Level.SEVERE, e.toString(), e);
        }

        return cestaKSuboru;
    }

    /**
     * Sluzi na extrahovanie id clena z QR kodu
     * @param qrCodeString QR kod ako base64 string
     */
    public int rozlustiQRCode(String qrCodeString){

        String obsah = null;
        BufferedImage bufferedImage;

        try {
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(qrCodeString);

            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            obsah = result.getText();
        } catch (IOException e) {
            qrLogger.log(Level.SEVERE, "Neuspesne nacitanie QRCodu zo stringu", e);
        } catch (NotFoundException e) {
            qrLogger.log(Level.SEVERE, "Nepodarilo sa nacitat QR kod", e);
            System.out.println("Nepodarilo sa nacitat QR kod");
            return -1;
        }

        return Integer.parseInt(obsah);
    }
}
