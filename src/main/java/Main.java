import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static HashMap<Integer, OdpisovaSkupina> odpisoveSkupiny;

    public static void main(String[] args){
        initOdpisovychSkupin();
        String predmet;
        long vstupniCena = 0L;
        int odpisovaSkupinaId = 0;
        OdpisovaSkupina odpisovaSkupina;
        Scanner in = new Scanner(System.in);

        Utils.log("Zadejte prosím předmět odpisu:");
        predmet = in.nextLine().trim();

        Utils.log("Zadejte prosím vstupní cenu:");
        do{
            String message = in.nextLine().trim();
            try {
                vstupniCena = Long.parseLong(message);
                if(vstupniCena <= 0L){
                    vstupniCena = 0L;
                    Utils.log("Chyba! Zadejte prosím kladné (přirozené) číslo.");
                }
            }catch (NumberFormatException ex){
                Utils.log("Chyba! Zadejte prosím správný číselný formát.");
            }
        }while (vstupniCena == 0L);

        Utils.log("Zadejte prosím odpisovou skupinu (hodnota 1 až 6):");
        do{
            String message = in.nextLine().trim();
            try {
                odpisovaSkupinaId = Integer.parseInt(message);
                if(!(odpisovaSkupinaId > 0 && odpisovaSkupinaId <= 6)){
                    odpisovaSkupinaId = 0;
                    Utils.log("Chyba! Zadejte prosím pouze číselné hodnoty 1 až 6.");
                }
            }catch (NumberFormatException ex){
                Utils.log("Chyba! Zadejte prosím správný číselný formát.");
            }
        }while (odpisovaSkupinaId == 0);
        odpisovaSkupina = getOdpisovaSkupinaById(odpisovaSkupinaId);
        if(odpisovaSkupina == null){
            Utils.log("Činnost programu byla pozastavena, vyskytla se chyba při načítání odpisové skupiny!");
            return;
        }

        Utils.log("Vše bylo správně zadáno, nyní probíhá výpočet...");
        Utils.log("");
        vypocetDanoveRovnomerne(vstupniCena, odpisovaSkupina);
        Utils.log("");
        vypocetDanoveZrychlene(vstupniCena, odpisovaSkupina);
        Utils.log("");
        Utils.log("--== Shrnutí ==--");
        Utils.log("Odpisovaný předmět: "+predmet);
        Utils.log("Odpisová skupina: "+odpisovaSkupinaId);
        Utils.log("Doba odepisování: "+Utils.getDobaOdepisovaniFormated(odpisovaSkupina.getDobaOdepisovani()));
        Utils.log("Vstupní cena: "+Utils.decimation(vstupniCena, " ")+",-");
        Utils.log("--==--==--==--==--");
    }

    private static void initOdpisovychSkupin(){
        odpisoveSkupiny = new HashMap<>();
        odpisoveSkupiny.put(1, new OdpisovaSkupina(1, 3, 20, 40, 33.3, 3, 4, 3));
        odpisoveSkupiny.put(2, new OdpisovaSkupina(2, 5, 11, 22.25, 20, 5, 6, 5));
        odpisoveSkupiny.put(3, new OdpisovaSkupina(3, 10, 5.5, 10.5, 10, 10, 11, 10));
        odpisoveSkupiny.put(4, new OdpisovaSkupina(4, 20, 2.15, 5.15, 5, 20, 21, 20));
        odpisoveSkupiny.put(5, new OdpisovaSkupina(5, 30, 1.4, 3.4, 3.4, 30, 31, 30));
        odpisoveSkupiny.put(6, new OdpisovaSkupina(6, 50, 1.02, 2.02, 2, 50, 51, 50));
    }

    private static void vypocetDanoveRovnomerne(long vstupniCena, OdpisovaSkupina odpisovaSkupina){
        long odpis;
        long opravky = 0L;
        long zustatkovaCena = vstupniCena;
        int dobaOdepisovani = odpisovaSkupina.getDobaOdepisovani();
        double sazbaPrvniRok = odpisovaSkupina.getSazbaPrvniRok();
        double sazbaDalsiRoky = odpisovaSkupina.getSazbaDalsiRoky();
        double sazba;

        Utils.log("------- Daňové (rovnoměrné) odpisování -------");
        for(int rok = 1; rok <= dobaOdepisovani; rok++){
            sazba = rok == 1 ? sazbaPrvniRok : sazbaDalsiRoky;
            odpis = (long) Math.ceil((((double) vstupniCena) * sazba) / 100);
            opravky += odpis;
            zustatkovaCena -= odpis;
            Utils.log(rok+". rok (sazba: "+sazba+"): odpis = "+Utils.decimation(odpis, " ")+" | oprávky = "+Utils.decimation(opravky, " ")+" | zůstatková cena = "+Utils.decimation(zustatkovaCena, " "));
        }
        Utils.log("----------------------------------------------");
    }

    private static void vypocetDanoveZrychlene(long vstupniCena, OdpisovaSkupina odpisovaSkupina){
        long odpis;
        long opravky = 0L;
        long zustatkovaCena = vstupniCena;
        int dobaOdepisovani = odpisovaSkupina.getDobaOdepisovani();
        int koeficientPrvniRok = odpisovaSkupina.getKoeficientPrvniRok();
        int koeficientDalsiRoky = odpisovaSkupina.getKoeficientDalsiRoky();
        int koeficient;

        Utils.log("------- Daňové (zrychlené) odpisování -------");
        for(int rok = 1; rok <= dobaOdepisovani; rok++){
            koeficient = rok == 1 ? koeficientPrvniRok : koeficientDalsiRoky;
            if(rok == 1){
                odpis = (long) Math.ceil(((double) vstupniCena) / ((double) koeficientPrvniRok));
                opravky += odpis;
                zustatkovaCena -= odpis;
            }else{
                odpis = (2 * zustatkovaCena) / (koeficientDalsiRoky - (rok - 1));
                opravky += odpis;
                zustatkovaCena -= odpis;
            }
            Utils.log(rok+". rok (koeficient: "+koeficient+"): odpis = "+Utils.decimation(odpis, " ")+" | oprávky = "+Utils.decimation(opravky, " ")+" | zůstatková cena = "+Utils.decimation(zustatkovaCena, " "));
        }
        Utils.log("----------------------------------------------");
    }

    private static OdpisovaSkupina getOdpisovaSkupinaById(int id){
        return odpisoveSkupiny.getOrDefault(id, null);
    }
}
