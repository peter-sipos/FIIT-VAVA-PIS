package entity;

import javax.persistence.*;

/**
 * Entita predstavujuca ucast konkretneho clena na konretnej ackii
 */
@Entity
@Table(name = "ucasti_na_akciach")
public class UcastNaAkcii {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_clena")
    private Clen clen;

    @ManyToOne
    @JoinColumn(name = "id_akcie")
    private Akcia akcia;

    @Column(name = "karta_poistenca")
    private boolean kartaPoistenca;

    @Column(name = "karta_na_vlaky")
    private boolean kartaNaVlaky;

    @Column(name = "isic")
    private boolean isic;

    @Column(name = "prihlaska")
    private boolean prihlaska;

    @Column(name = "poplatok")
    private int poplatok;

    public UcastNaAkcii() {
        this.akcia = null;
        this.clen = null;
        this.isic = false;
        this.kartaNaVlaky = false;
        this.kartaPoistenca = false;
        this.prihlaska = false;
        this.poplatok = 0;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clen getClen() {
        return clen;
    }

    public void setClen(Clen clen) {
        this.clen = clen;
    }

    public Akcia getAkcia() {
        return akcia;
    }

    public void setAkcia(Akcia akcia) {
        this.akcia = akcia;
    }

    public boolean isKartaPoistenca() {
        return kartaPoistenca;
    }

    public void setKartaPoistenca(boolean kartaPoistenca) {
        this.kartaPoistenca = kartaPoistenca;
    }

    public boolean isKartaNaVlaky() {
        return kartaNaVlaky;
    }

    public void setKartaNaVlaky(boolean kartaNaVlaky) {
        this.kartaNaVlaky = kartaNaVlaky;
    }

    public boolean isIsic() {
        return isic;
    }

    public void setIsic(boolean isic) {
        this.isic = isic;
    }

    public boolean isPrihlaska() {
        return prihlaska;
    }

    public void setPrihlaska(boolean prihlaska) {
        this.prihlaska = prihlaska;
    }

    public int getPoplatok() {
        return poplatok;
    }

    public void setPoplatok(int poplatok) {
        this.poplatok = poplatok;
    }

    @Override
    public String toString() {
        return "UcastNaAkcii{" +
                "id=" + id +
                ", kartaPoistenca=" + kartaPoistenca +
                ", kartaNaVlaky=" + kartaNaVlaky +
                ", isic=" + isic +
                ", prihlaska=" + prihlaska +
                ", poplatok=" + poplatok +
                '}';
    }
}
