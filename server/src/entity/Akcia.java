package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "akcie")
public class Akcia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "akcia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UcastNaAkcii> ucastiNaAkcii;

    @Column(name = "nazov")
    private String nazov;

    @Column(name = "popis")
    private String popis;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_zodpovednej_osoby")
    private Clen zodpovednaOsoba;

    @Column(name = "datum_akcie")
    private LocalDate datumAkcie;

    @Column(name = "miesto_konania")
    private String miestoKonania;

    @Column(name = "oddiel")
    private String oddiel;


    public Akcia() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UcastNaAkcii> getUcastiNaAkcii() {
        return ucastiNaAkcii;
    }

    public void setUcastiNaAkcii(List<UcastNaAkcii> ucastiNaAkcii) {
        this.ucastiNaAkcii = ucastiNaAkcii;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Clen getZodpovednaOsoba() {
        return zodpovednaOsoba;
    }

    public void setZodpovednaOsoba(Clen zodpovednaOsoba) {
        this.zodpovednaOsoba = zodpovednaOsoba;
    }

    public LocalDate getDatumAkcie() {
        return datumAkcie;
    }

    public void setDatumAkcie(LocalDate datumAkcie) {
        this.datumAkcie = datumAkcie;
    }

    public String getMiestoKonania() {
        return miestoKonania;
    }

    public void setMiestoKonania(String miestoKonania) {
        this.miestoKonania = miestoKonania;
    }

    public String getOddiel() {
        return oddiel;
    }

    public void setOddiel(String oddiel) {
        this.oddiel = oddiel;
    }

    @Override
    public String toString() {
        return "Akcia{" +
                "id=" + id +
                ", nazov='" + nazov + '\'' +
                ", popis='" + popis + '\'' +
                ", zodpovednaOsoba=" + zodpovednaOsoba.getMeno() + " " + zodpovednaOsoba.getPriezvisko() +
                ", datumAkcie=" + datumAkcie +
                ", miestoKonania='" + miestoKonania + '\'' +
                '}';
    }
}

