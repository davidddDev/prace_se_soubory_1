import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EvidenceZakazniku {
    private List<Zakaznik> zakaznici;

    public EvidenceZakazniku() {
        this.zakaznici = new ArrayList<>();
    }

    public void pridatZakaznika(Zakaznik zakaznik) {
        zakaznici.add(zakaznik);
    }

    public void odebratZakaznika(Zakaznik zakaznik) {
        zakaznici.remove(zakaznik);
    }

    private List<Zakaznik> getZakazniky() {
        return new ArrayList<>(zakaznici);
    }

    public void odebratPoslednihoZakaznika() {
        if (!zakaznici.isEmpty()) {
            zakaznici.remove(zakaznici.size() - 1);
        }
    }

    public void zapisZakaznikyDoSouboru(String nazevSouboru, String oddelovac) throws EvidenceException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)))) {
            for (Zakaznik zakaznik : zakaznici) {
                writer.println(zakaznik.getJmeno() + oddelovac
                        + zakaznik.getDatumNarozeni() + oddelovac
                        + zakaznik.getMesto() + oddelovac
                        + zakaznik.getPocetProdeju());
            }
        } catch (FileNotFoundException e) {
            throw new EvidenceException(" Soubor " + nazevSouboru + " nebyl nalezen: " + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new EvidenceException(" Chyba při zápisu do souboru: " + nazevSouboru + ": " + e.getLocalizedMessage());
        }
    }

    public void nactiZakaznikyZeSouboru(String nazevSouboru, String oddelovac) throws EvidenceException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(nazevSouboru)))) {
            while (scanner.hasNextLine()) {
                String radek = scanner.nextLine();
                pridatZakaznika(parseZakaznik(radek, oddelovac));
            }
        } catch (FileNotFoundException e) {
            throw new EvidenceException("Soubor " + nazevSouboru + " nebyl nalezen: " + e.getLocalizedMessage());
        }
    }

    private Zakaznik parseZakaznik(String radek, String oddelovac) throws EvidenceException {
        String[] polozky = radek.split(oddelovac);
        if (polozky.length != 4) {
            throw new EvidenceException("Chybný počet položek v řádku: " + radek + "!");
        }
        String jmeno = polozky[0].trim();
        LocalDate datumNarozeni = LocalDate.parse(polozky[1].trim());
        String mesto = polozky[2].trim();
        int pocetProdeju = Integer.parseInt(polozky[3].trim());
        return new Zakaznik(jmeno, datumNarozeni, mesto, pocetProdeju);
    }

    public List<Zakaznik> vyberZakaznikySPoctemProdejuVetsimNez(double limit) {
        List<Zakaznik> vybrani = new ArrayList<>();
        for (Zakaznik zakaznik : zakaznici) {
            if (zakaznik.getPocetProdeju() > limit) {
                vybrani.add(zakaznik);
            }
        }
        return vybrani;
    }

    public double spocitejPrumernyPocetProdejuZUH() {
        double soucet = 0.0;
        int pocet = 0;
        for (Zakaznik zakaznik : zakaznici) {
            if ("Uherské Hradiště".equals(zakaznik.getMesto())) {
                soucet += zakaznik.getPocetProdeju();
                pocet++;
            }
        }
        return pocet > 0 ? soucet / pocet : 0.0;
    }
}

