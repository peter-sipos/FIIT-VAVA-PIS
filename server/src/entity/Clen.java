package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entita reprezentujuca konkretneho clena
 */
@Entity
@Table(name = "clenovia")
public class Clen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "clen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UcastNaAkcii> ucastiNaAkciach;

    @Column(name = "email")
    private String email;

    @Column(name = "meno")
    private String meno;

    @Column(name = "priezvisko")
    private String priezvisko;

    @Column(name = "datum_narodenia")
    private LocalDate datumNarodenia;

    @Column(name = "miesto_narodenia")
    private String miestoNarodenia;

    @Column(name = "cislo_na_clena")
    private String cisloNaClena;

    @Column(name = "cislo_na_rodica")
    private String cisloNaRodica;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "cislo_domu")
    private int cisloDomu;

    @Column(name = "psc")
    private int psc;

    @Column(name = "obec")
    private String obec;

    @Column(name = "rola")
    private String rola;

    @Column(name = "oddiel")
    private String oddiel;

    @Column(name = "cislo_vlakovej_karticky")
    private String cisloVlakovejKarticky;

    @Override
    public String toString() {
        return "Clen{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", meno='" + meno + '\'' +
                ", priezvisko='" + priezvisko + '\'' +
                ", datumNarodenia=" + datumNarodenia +
                ", miestoNarodenia='" + miestoNarodenia + '\'' +
                ", cisloNaClena='" + cisloNaClena + '\'' +
                ", cisloNaRodica='" + cisloNaRodica + '\'' +
                ", ulica='" + ulica + '\'' +
                ", cisloDomu=" + cisloDomu +
                ", psc=" + psc +
                ", obec='" + obec + '\'' +
                ", rola='" + rola + '\'' +
                ", oddiel='" + oddiel + '\'' +
                ", cisloVlakovejKarticky='" + cisloVlakovejKarticky + '\'' +
                '}';
    }

    public Clen() {
        this.ucastiNaAkciach = new ArrayList<>();
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UcastNaAkcii> getUcastiNaAkciach() {
        return ucastiNaAkciach;
    }

    public void setUcastiNaAkciach(List<UcastNaAkcii> ucastiNaAkciach) {
        this.ucastiNaAkciach = ucastiNaAkciach;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public LocalDate getDatumNarodenia() {
        return datumNarodenia;
    }

    public void setDatumNarodenia(LocalDate datumNarodenia) {
        this.datumNarodenia = datumNarodenia;
    }

    public String getMiestoNarodenia() {
        return miestoNarodenia;
    }

    public void setMiestoNarodenia(String miestoNarodenia) {
        this.miestoNarodenia = miestoNarodenia;
    }

    public String getCisloNaClena() {
        return cisloNaClena;
    }

    public void setCisloNaClena(String cisloNaClena) {
        this.cisloNaClena = cisloNaClena;
    }

    public String getCisloNaRodica() {
        return cisloNaRodica;
    }

    public void setCisloNaRodica(String cisloNaRodica) {
        this.cisloNaRodica = cisloNaRodica;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getCisloDomu() {
        return cisloDomu;
    }

    public void setCisloDomu(int cisloDomu) {
        this.cisloDomu = cisloDomu;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public String getObec() {
        return obec;
    }

    public void setObec(String obec) {
        this.obec = obec;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public String getOddiel() {
        return oddiel;
    }

    public void setOddiel(String oddiel) {
        this.oddiel = oddiel;
    }

    public String getCisloVlakovejKarticky() {
        return cisloVlakovejKarticky;
    }

    public void setCisloVlakovejKarticky(String cisloVlakovejKarticky) {
        this.cisloVlakovejKarticky = cisloVlakovejKarticky;
    }
}
