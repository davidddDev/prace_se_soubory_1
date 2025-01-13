
public class Main {

    private static final String nazevSouboruKonecneho = "vybraniZakaznici.txt";
    private static final String nazevSouboru = "zakaznici.txt";
    private static final String oddelovac = ":";

    public static void main(String[] args) {
        EvidenceZakazniku evidenceZakazniku = new EvidenceZakazniku();

        try {
            evidenceZakazniku.nactiZakaznikyZeSouboru(nazevSouboru, oddelovac);
            System.out.println("Zákazníci byli úspěšně načteni ze souboru.");
        } catch (EvidenceException e) {
            System.err.println("Chyba při čtení ze souboru: " + e.getLocalizedMessage());
        }

        System.out.println("Zákazníci s počtem prodejů větším než 25:");
        for (Zakaznik zakaznik : evidenceZakazniku.vyberZakaznikySPoctemProdejuVetsimNez(25)) {
            System.out.println(zakaznik.getJmeno() + ", Prodeje: " + zakaznik.getPocetProdeju());
        }

        double prumer = evidenceZakazniku.spocitejPrumernyPocetProdejuZUH();
        System.out.println("Průměrný počet prodejů v Uherském Hradišti: " + prumer);

        EvidenceZakazniku vybranaEvidence = new EvidenceZakazniku();
        for (Zakaznik zakaznik : evidenceZakazniku.vyberZakaznikySPoctemProdejuVetsimNez(25)) {
            vybranaEvidence.pridatZakaznika(zakaznik);
        }
    }
}
