package entity;

/**
 * Entita reprezentujuca emailovu spravu spolu s adresatmi
 */
public class Email {

    private int skautom;
    private int skautkam;
    private int vlcatam;
    private int vcielkam;
    private String predmet;
    private String sprava;
    private int idAkcie;
    private int qrCode;

    public int getSkautom() {
        return skautom;
    }

    public void setSkautom(int skautom) {
        this.skautom = skautom;
    }

    public int getSkautkam() {
        return skautkam;
    }

    public void setSkautkam(int skautkam) {
        this.skautkam = skautkam;
    }

    public int getVlcatam() {
        return vlcatam;
    }

    public void setVlcatam(int vlcatam) {
        this.vlcatam = vlcatam;
    }

    public int getVcielkam() {
        return vcielkam;
    }

    public void setVcielkam(int vcielkam) {
        this.vcielkam = vcielkam;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getSprava() {
        return sprava;
    }

    public void setSprava(String sprava) {
        this.sprava = sprava;
    }

    public int getIdAkcie() {
        return idAkcie;
    }

    public void setIdAkcie(int idAkcie) {
        this.idAkcie = idAkcie;
    }

    public int getQrCode() {
        return qrCode;
    }

    public void setQrCode(int qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "Email{" +
                "skautom=" + skautom +
                ", skautkam=" + skautkam +
                ", vlcatam=" + vlcatam +
                ", vcielkam=" + vcielkam +
                ", predmet='" + predmet + '\'' +
                ", sprava='" + sprava + '\'' +
                ", idAkcie=" + idAkcie +
                ", qrCode=" + qrCode +
                '}';
    }
}
