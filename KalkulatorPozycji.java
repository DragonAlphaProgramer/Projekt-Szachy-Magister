/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.Collection;
import szachy.Bezpieczenstwo_krol.analizator_odleglosci;

/**
 *
 * @author Patryk
 */
public class KalkulatorPozycji 
implements Kalkulator {

    private final int[] TEMP1 = {0,0};
    private final int RUCHY_BONUS = 5;
    private final int ROSZADA = 25;
    private final int SZACH = 45;
    private final int MAT = 1000000;
    private final int BICIA_BONUS = 1;
    public final static KalkulatorPozycji INSTANCE = new KalkulatorPozycji();
    public static final KalkulatorPozycji get(){
        return INSTANCE;
    }
    
    
    private final int[][] BONUS_CZARNY_PION = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {75, 75, 75, 75, 75, 75, 75, 75},
        {25, 25, 29, 29, 29, 29, 25, 25},
        {5, 5, 10, 55, 55, 10, 5, 5},
        {0, 0, 0, 20, 20, 0, 0, 0},
        {5, -5, -10, 0, 0, -10, -5, 5},
        {5, 10, 10, -20, -20, 10, 10, 5},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };

    private final int[][] BONUS_BIALY_PION = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {5, 10, 10, -20, -20, 10, 10, 5},
        {5, -5, -10, 0, 0, -10, -5, 5},
        {0, 0, 0, 20, 20, 0, 0, 0},
        {5, 5, 10, 55, 55, 10, 5, 5},
        {25, 25, 29, 29, 29, 29, 25, 25},
        {75, 75, 75, 75, 75, 75, 75, 75},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };

    private final int[][] BONUS_CZARNY_SKOCZEK = {
        {-50, -40, -30, -30, -30, -30, -40, -50},
        {-40, -20, 0, 5, 5, 0, -20, -40},
        {-30, 5, 10, 15, 15, 10, 5, -30},
        {-30, 0, 15, 20, 20, 15, 0, -30},
        {-30, 5, 15, 20, 20, 15, 5, -30},
        {-30, 0, 10, 15, 15, 10, 0, -30},
        {-40, -20, 0, 0, 0, 0, -20, -40},
        {-50, -40, -30, -30, -30, -30, -40, -50}
    };

    private final int[][] BONUS_BIALY_SKOCZEK = {
        {-50, -40, -30, -30, -30, -30, -40, -50},
        {-40, -20, 0, 0, 0, 0, -20, -40},
        {-30, 0, 10, 15, 15, 10, 0, -30},
        {-30, 5, 15, 20, 20, 15, 5, -30},
        {-30, 0, 15, 20, 20, 15, 0, -30},
        {-30, 5, 10, 15, 15, 10, 5, -30},
        {-40, -20, 0, 5, 5, 0, -20, -40},
        {-50, -40, -30, -30, -30, -30, -40, -50}};

    private final int[][] BONUS_CZARNY_GONIEC = {
        {-20, -10, -10, -10, -10, -10, -10, -20},
        {-10, 0, 0, 0, 0, 0, 0, -10},
        {-10, 0, 5, 10, 10, 5, 0, -10},
        {-10, 5, 5, 10, 10, 5, 5, -10},
        {-10, 0, 10, 10, 10, 10, 0, -10},
        {-10, 10, 10, 10, 10, 10, 10, -10},
        {-10, 5, 0, 0, 0, 0, 5, -10},
        {-20, -10, -10, -10, -10, -10, -10, -20}
    };

    private final int[][] BONUS_BIALY_GONIEC = {
        {-20, -10, -10, -10, -10, -10, -10, -20},
        {-10, 5, 0, 0, 0, 0, 5, -10},
        {-10, 10, 10, 10, 10, 10, 10, -10},
        {-10, 0, 10, 10, 10, 10, 0, -10},
        {-10, 5, 5, 10, 10, 5, 5, -10},
        {-10, 0, 5, 10, 10, 5, 0, -10},
        {-10, 0, 0, 0, 0, 0, 0, -10},
        {-20, -10, -10, -10, -10, -10, -10, -20}};

    private final int[][] BONUS_CZARNA_WIEZA = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {5, 20, 20, 20, 20, 20, 20, 5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {0, 0, 0, 5, 5, 0, 0, 0}
    };

    private final int[][] BONUS_BIALA_WIEZA = {
        {0, 0, 0, 5, 5, 0, 0, 0},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {-5, 0, 0, 0, 0, 0, 0, -5},
        {5, 20, 20, 20, 20, 20, 20, 5},
        {0, 0, 0, 0, 0, 0, 0, 0}};

    private final int[][] BONUS_CZARNY_HETMAN = {
        {-20, -10, -10, -5, -5, -10, -10, -20},
        {-10, 0, 0, 0, 0, 0, 0, -10},
        {-10, 0, 5, 5, 5, 5, 0, -10},
        {0, 0, 5, 5, 5, 5, 0, -5},
        {0, 0, 5, 5, 5, 5, 0, -5},
        {-10, 5, 5, 5, 5, 5, 0, -10},
        {-10, 0, 5, 0, 0, 0, 0, -10},
        {-20, -10, -10, -5, -5, -10, -10, -20}
    };

    private final int[][] BONUS_BIALY_HETMAN = {
        {-20, -10, -10, -5, -5, -10, -10, -20},
        {-10, 0, 5, 0, 0, 0, 0, -10},
        {-10, 5, 5, 5, 5, 5, 0, -10},
        {0, 0, 5, 5, 5, 5, 0, -5},
        {-5, 0, 5, 5, 5, 5, 0, -5},
        {-10, 0, 5, 5, 5, 5, 0, -10},
        {-10, 0, 0, 0, 0, 0, 0, -10},
        {-20, -10, -10, -5, -5, -10, -10, -20}
    };

    private final int[][] BONUS_CZARNY_KROL = {
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-20, -30, -30, -40, -40, -30, -30, -20},
        {-10, -20, -20, -20, -20, -20, -20, -10},
        {20, 20, 0, 0, 0, 0, 20, 20},
        {20, 30, 10, 0, 0, 10, 30, 20}
    };

    private final int[][] BONUS_BIALY_KROL = {
        {20, 30, 10, 0, 0, 10, 30, 20},
        {20, 20, 0, 0, 0, 0, 20, 20},
        {-10, -20, -20, -20, -20, -20, -20, -10},
        {-20, -30, -30, -40, -40, -30, -30, -20},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30}
    };

    public KalkulatorPozycji() {
        /*for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
        KalkulatorPozycji.ustawienie[x][y] = ustawienie[x][y];
            }}
        KalkulatorPozycji.tura_rywala = tura_rywala;
        KalkulatorPozycji.przelotcan = przelotcan;
        KalkulatorPozycji.bleft = bleft;
        KalkulatorPozycji.bright = bright;
        KalkulatorPozycji.wleft = wleft;
        KalkulatorPozycji.wright = wright;
        KalkulatorPozycji.roszadaB = roszadaB;
        KalkulatorPozycji.roszadaC = roszadaC;
        KalkulatorPozycji.wykonanaRochB = wykonanaRochB;
        KalkulatorPozycji.wykonanaRochC = wykonanaRochC;
        KalkulatorPozycji.glebia = glebia;
        KalkulatorPozycji.kol = kol;*/

    }

    @Override
    public int zliczacz(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, int kol) {
          /*  System.out.println("");
            System.out.println(("White Mobility : " + mobilnosc(ustawienie,true,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol)
                 + "\n") +
                "White kingThreats : " + szachmat(true,ustawienie,glebia,przelotcan,kol) + "\n" +
                "White attacks : " + ruchy_zbijajace(ustawienie,true,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol) + "\n" +
                "White castle : " + dokonano_roszady(true,wykonanaRochB,wykonanaRochC) + "\n" +
                "White pieceEval : " + wartosc_bierek(true,ustawienie) + "\n" +
                "White pawnStructure : " + pionkiS(true,ustawienie) + "\n" +
                "---------------------\n" +
                "Black Mobility : " + mobilnosc(ustawienie,false,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol) + "\n" +
                "Black kingThreats : " + szachmat(false,ustawienie,glebia,przelotcan,kol) + "\n" +
                "Black attacks : " + ruchy_zbijajace(ustawienie,false,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol) + "\n" +
                "Black castle : " + dokonano_roszady(false,wykonanaRochB,wykonanaRochC) + "\n" +
                "Black pieceEval : " + wartosc_bierek(false,ustawienie) + "\n" +
                "Black pawnStructure : " + pionkiS(true,ustawienie) + "\n\n");*/
        //System.out.println("kalkulator:"+(punktacja(true) - punktacja(false)));
        return punktacja(ustawienie,true,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,
                wykonanaRochB,wykonanaRochC,glebia,kol) - 
                punktacja(ustawienie,false,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,
                wykonanaRochB,wykonanaRochC,glebia,kol);
    }

    private int punktacja(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie, boolean strona, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, int kol) {
        return szachmat(strona,ustawienie,glebia,przelotcan,kol)
                + dokonano_roszady(strona,wykonanaRochB,wykonanaRochC)
                + mobilnosc(ustawienie,strona,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol)
                + pionkiS(strona,ustawienie)
                + wartosc_bierek(strona,ustawienie)
                + ruchy_zbijajace(ustawienie,strona,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol);
    }

    private int wartosc_bierek(boolean strona,SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie) {
        int wartosc = 0;
        int gonce=0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                //System.out.print(SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y]);
                if (strona == false) {
                    switch (ustawienie[x][y]) {
                        case CPion:
                            wartosc = wartosc + (100 + BONUS_CZARNY_PION[x][y]);
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNY_PION[x][y] + "]");
                            break;
                        case CSkoczek:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNY_SKOCZEK[x][y] + "]");
                            wartosc = wartosc + (300 + BONUS_CZARNY_SKOCZEK[x][y]);
                            break;
                        case CGoniec:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNY_GONIEC[x][y] + "]");
                            wartosc = wartosc + (330 + BONUS_CZARNY_GONIEC[x][y]);
                            gonce=gonce+1;
                            break;
                        case CWieza:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNA_WIEZA[x][y] + "]");
                            wartosc = wartosc + (500 + BONUS_CZARNA_WIEZA[x][y]);
                            break;
                        case CHetman:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNY_HETMAN[x][y] + "]");
                            wartosc = wartosc + (900 + BONUS_CZARNY_HETMAN[x][y]);
                            break;
                        case CKrol:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_CZARNY_KROL[x][y] + "]");
                            wartosc = wartosc + (10000 + BONUS_CZARNY_KROL[x][y]);
                            break;
                        default:
                              // //System.out.print("[" + 0 + "]");
                            break;
                    }
                } else {
                    switch (ustawienie[x][y]) {
                        case BPion:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALY_PION[x][y] + "]");
                            wartosc = wartosc + (100 + BONUS_BIALY_PION[x][y]);
                            break;
                        case BSkoczek:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALY_SKOCZEK[x][y] + "]");
                            wartosc = wartosc + (300 + BONUS_BIALY_SKOCZEK[x][y]);
                            break;
                        case BGoniec:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALY_GONIEC[x][y] + "]");
                            wartosc = wartosc + (330 + BONUS_BIALY_GONIEC[x][y]);
                            gonce=gonce+1;
                            break;
                        case BWieza:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALA_WIEZA[x][y] + "]");
                            wartosc = wartosc + (500 + BONUS_BIALA_WIEZA[x][y]);
                            break;
                        case BHetman:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALY_HETMAN[x][y] + "]");
                            wartosc = wartosc + (900 + BONUS_BIALY_HETMAN[x][y]);
                            break;
                        case BKrol:
                            //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[x][y] + BONUS_BIALY_KROL[x][y] + "]");
                            wartosc = wartosc + (10000 + BONUS_BIALY_KROL[x][y]);
                            break;
                        default:
                            //////System.out.println("[" + 0 + "]");
                            break;
                    }
                }
            }//System.out.println();
        }
        //System.out.println("-----------------");
        return wartosc+(gonce==2?25:0);
    }

    private int szachmat(boolean strona,SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie,int glebia,boolean przelotcan,int kol) {
        int[] krol = new int[2];
        boolean zagrozone;
        if (RuchZagrozenie_kontrola.szach(SI_MIN_MAX_Alfa_Beta.konwert(ustawienie), strona) == true) {
            zagrozone = true;
            char[][] backup = new char[8][8];
            char[][] backup1 = new char[8][8];
            char[][] backup2 = new char[8][8];
            char[][] backup3 = new char[8][8];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    backup[i][j] = SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[i][j];
                    backup[i][j] = SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[i][j];
                    backup2[i][j] = SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[i][j];
                    backup3[i][j] = SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)[i][j];
                    if ((ustawienie[i][j] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && strona == true)
                            || (ustawienie[i][j] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && strona == false)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }
            return SzachMatPatKontrola.uciekaj(backup1, strona, krol) == false
                    && SzachMatPatKontrola.zastaw(backup2, strona, Wspomagacz.znajdzklopot(backup2, strona), krol, przelotcan) == false
                    && SzachMatPatKontrola.znajdzodsiecz(backup3, strona, Wspomagacz.znajdzklopot(backup3, strona), kol, przelotcan) == false
                    ? (glebia != 0 ? -MAT * glebia : -MAT) : zagrozone == true ? -SZACH : 0;

        }
        ////System.out.printlnln("Wchodzi2");
        return 0;
    }

    private int dokonano_roszady(boolean strona,boolean wykonanaRochB,boolean wykonanaRochC) {
        if (strona == true) {
            if (wykonanaRochB == true) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        } else {
            if (wykonanaRochC == true) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        }
        ////System.out.printlnln("Wchodzi3");
        return 0;
    }

    private int pionkiS(boolean strona,SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie) {
////System.out.printlnln("Wchodzi5");
        return pozycja_PION.punktacja(strona, SI_MIN_MAX_Alfa_Beta.konwert(ustawienie));
    }

    private int wiezeS(boolean strona,SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie) {
        pozycja_WIEZA w = new pozycja_WIEZA();
        ////System.out.printlnln("Wchodzi6");
        return w.punktacja(strona, SI_MIN_MAX_Alfa_Beta.konwert(ustawienie)) - w.punktacja(!strona, SI_MIN_MAX_Alfa_Beta.konwert(ustawienie));
    }

    private int mobilnosc(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie, boolean strona, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, int kol) {
        ////System.out.printlnln("Wchodzi4");

        ////System.out.printlnln(stosunek(strona)*RUCHY_BONUS);
        return RUCHY_BONUS * stosunek(ustawienie,strona,przelotcan,bleft,bright,wleft,wright,roszadaB,roszadaC,kol);
    }

    private int stosunek(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie,boolean b,boolean przelotcan,
            boolean bleft,boolean bright,boolean wleft,boolean wright,boolean roszadaB,boolean roszadaC,int kol) {
        int swoje = (Generator.generuj_posuniecia(ustawienie, b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol, false, true).size());
        int wrogie = Generator.generuj_posuniecia(ustawienie, !b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol, false,true).size();
        
        return (int) ((swoje*10.0f) / wrogie);
        
    }

    private int save_king(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        analizator_odleglosci save = Bezpieczenstwo_krol.get().zliczpozycje(ustawienie, strona, przelotcan,
                RochB, RochC, wl, wr, bl, br, kol);
        return save.getWartosc();
    }

    private int ruchy_zbijajace(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie,boolean strona,boolean przelotcan,
            boolean bleft,boolean bright,boolean wleft,boolean wright,boolean roszadaB,boolean roszadaC,int kol) {
        ////System.out.printlnln("Wchodzi7");
        Collection<Ruch> lista = Generator.generuj_posuniecia((ustawienie), strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol, false, ' ', TEMP1, false);
        int licznik_ataku = 0;

        if (!lista.isEmpty()) {
            for (Ruch ruch : lista) {
                if (ruch.korzystnosc_bicia != Ruch.figura.Pustka) {
                    int wartosc_atakujacego = wartosc(ruch.kolejnosc);
                    int wartosc_atakowanego = wartosc(ruch.korzystnosc_bicia);
                    if (wartosc_atakujacego <= wartosc_atakowanego) {
                        licznik_ataku++;
                    }
                }
            }
        }

        //////System.out.printlnSystem.out.print(licznik_ataku*BICIA_BONUS);
        return (licznik_ataku * BICIA_BONUS);
    }

    private int wartosc(Ruch.figura bierka) {
        switch (bierka) {
            case Pion:
                return 100;
            case Skoczek:
                return 300;
            case Goniec:
                return 350;
            case Wieza:
                return 500;
            case Hetman:
                return 900;
            case Krol:
                return 10000;
        }
        return 0;
    }

  /*  private char pozyskaj_figure(Ruch.figura ruch) {
        switch (ruch) {
            case Pion:
                if (tura_rywala == true) {
                    return 'P';
                } else {
                    return 'p';
                }
            case Skoczek:
                if (tura_rywala == true) {
                    return 'N';
                } else {
                    return 'n';
                }
            case Goniec:
                if (tura_rywala == true) {
                    return 'B';
                } else {
                    return 'b';
                }
            case Wieza:
                if (tura_rywala == true) {
                    return 'R';
                } else {
                    return 'r';
                }
            case Hetman:
                if (tura_rywala == true) {
                    return 'Q';
                } else {
                    return 'q';
                }
            case Krol:
                if (tura_rywala == true) {
                    return 'K';
                } else {
                    return 'k';
                }
        }
        return ' ';
    }*/

}
