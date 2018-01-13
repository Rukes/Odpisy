public class OdpisovaSkupina {

    private int id;
    private double sazbaPrvniRok;
    private double sazbaDalsiRoky;
    private double sazbaZvyseneCeny;
    private int koeficientPrvniRok;
    private int koeficientDalsiRoky;
    private int koeficientZvyseneCeny;
    private int dobaOdepisovani;

    OdpisovaSkupina(int id, int dobaOdepisovani, double sazbaPrvniRok, double sazbaDalsiRoky, double sazbaZvyseneCeny, int koeficientPrvniRok, int koeficientDalsiRoky, int koeficientZvyseneCeny){
        this.id = id;
        this.dobaOdepisovani = dobaOdepisovani;
        this.sazbaPrvniRok = sazbaPrvniRok;
        this.sazbaDalsiRoky = sazbaDalsiRoky;
        this.sazbaZvyseneCeny = sazbaZvyseneCeny;
        this.koeficientPrvniRok = koeficientPrvniRok;
        this.koeficientDalsiRoky = koeficientDalsiRoky;
        this.koeficientZvyseneCeny = koeficientZvyseneCeny;
    }

    public int getId() {
        return id;
    }

    public int getDobaOdepisovani() {
        return dobaOdepisovani;
    }

    public double getSazbaPrvniRok() {
        return sazbaPrvniRok;
    }

    public double getSazbaDalsiRoky() {
        return sazbaDalsiRoky;
    }

    public double getSazbaZvyseneCeny() {
        return sazbaZvyseneCeny;
    }

    public int getKoeficientPrvniRok() {
        return koeficientPrvniRok;
    }

    public int getKoeficientDalsiRoky() {
        return koeficientDalsiRoky;
    }

    public int getKoeficientZvyseneCeny() {
        return koeficientZvyseneCeny;
    }
}
