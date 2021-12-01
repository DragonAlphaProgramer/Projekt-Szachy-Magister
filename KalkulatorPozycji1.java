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
public class KalkulatorPozycji1 {

    private static final int RUCHY_BONUS = 5;
    private static final int ROSZADA = 25;
    private static final int SZACH = 45;
    private static final int MAT = 1000000;
    private static final int BICIA_BONUS = 1;
    static SI_MIN_MAX_Alfa_Beta1.figury[][] ustawienie;
    static boolean tura_rywala, przelotcan, bleft, bright, wleft, wright,
            roszadaB, roszadaC, wykonanaRochB, wykonanaRochC;
    static int glebia;
    static byte kol;
    private static final int[] BONUS_BIALY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        75, 75, 75, 75, 75, 75, 75, 75,
        25, 25, 29, 29, 29, 29, 25, 25,
        5, 5, 10, 55, 55, 10, 5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, -5, -10, 0, 0, -10, -5, 5,
        5, 10, 10, -20, -20, 10, 10, 5,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final int[] BONUS_CZARNY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 10, 10, -20, -20, 10, 10, 5,
        5, -5, -10, 0, 0, -10, -5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, 5, 10, 55, 55, 10, 5, 5,
        25, 25, 29, 29, 29, 29, 25, 25,
        75, 75, 75, 75, 75, 75, 75, 75,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final int[] BONUS_BIALY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50
    };

    private static final int[] BONUS_CZARNY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50,};

    private static final int[] BONUS_BIALY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -20, -10, -10, -10, -10, -10, -10, -20
    };

    private static final int[] BONUS_CZARNY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -10, -10, -10, -10, -20,};

    private static final int[] BONUS_BIALA_WIEZA = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 20, 20, 20, 20, 20, 20, 5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        0, 0, 0, 5, 5, 0, 0, 0
    };

    private static final int[] BONUS_CZARNA_WIEZA = {
        0, 0, 0, 5, 5, 0, 0, 0,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        5, 20, 20, 20, 20, 20, 20, 5,
        0, 0, 0, 0, 0, 0, 0, 0,};

    private static final int[] BONUS_BIALY_HETAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -5, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 5, 5, 5, 5, 5, 0, -10,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private static final int[] BONUS_CZARNY_HETMAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -10, 5, 5, 5, 5, 5, 0, -10,
        0, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private static final int[] BONUS_BIALY_KROL = {
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        20, 20, 0, 0, 0, 0, 20, 20,
        20, 30, 10, 0, 0, 10, 30, 20
    };

    private static final int[] BONUS_CZARNY_KROL = {
        20, 30, 10, 0, 0, 10, 30, 20,
        20, 20, 0, 0, 0, 0, 20, 20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30
    };

    public KalkulatorPozycji1(SI_MIN_MAX_Alfa_Beta1.figury[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, byte kol) {
        this.ustawienie = ustawienie;
        this.tura_rywala = tura_rywala;
        this.przelotcan = przelotcan;
        this.bleft = bleft;
        this.bright = bright;
        this.wleft = wleft;
        this.wright = wright;
        this.roszadaB = roszadaB;
        this.roszadaC = roszadaC;
        this.wykonanaRochB = wykonanaRochB;
        this.wykonanaRochC = wykonanaRochC;
        this.glebia = glebia;
        this.kol = kol;

    }

    public int zliczacz() {
        /* //System.out.println(("White Mobility : " + mobilnosc(true) + "\n") +
                "White kingThreats : " + szachmat(true) + "\n" +
                "White attacks : " + ruchy_zbijajace(true) + "\n" +
                "White castle : " + dokonano_roszady(true) + "\n" +
                "White pieceEval : " + wartosc_bierek(true) + "\n" +
                "White pawnStructure : " + pionkiS(true) + "\n" +
                "---------------------\n" +
                "Black Mobility : " + mobilnosc(false) + "\n" +
                "Black kingThreats : " + szachmat(false) + "\n" +
                "Black attacks : " + ruchy_zbijajace(false) + "\n" +
                "Black castle : " + dokonano_roszady(false) + "\n" +
                "Black pieceEval : " + wartosc_bierek(false) + "\n" +
                "Black pawnStructure : " + pionkiS(true) + "\n\n" +
                "Final Score = " + (punktacja(true) - punktacja(false))+"\n \n \n");*/
        return punktacja(true) - punktacja(false);
    }

    private static int punktacja(boolean strona) {
        return szachmat(strona)
                + dokonano_roszady(strona)
                + mobilnosc(strona)
                + pionkiS(strona)
                + wartosc_bierek(strona)
                + ruchy_zbijajace(strona);
    }

    private static int wartosc_bierek(boolean strona) {
        int wartosc = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (strona == false) {
                    switch (ustawienie[x][y]) {
                        case CPion:
                            wartosc = wartosc + (100 + BONUS_CZARNY_PION[Math.abs(8*(7-x)+y)]);
                            //System.out.print("[" + BONUS_CZARNY_PION[Math.abs(8*(7-x)+y)] + "]");
                            break;
                        case CSkoczek:
                            //System.out.print("[" + BONUS_CZARNY_SKOCZEK[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (300 + BONUS_CZARNY_SKOCZEK[Math.abs(8*(7-x)+y)]);
                            break;
                        case CGoniec:
                            //System.out.print("[" + BONUS_CZARNY_GONIEC[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (330 + BONUS_CZARNY_GONIEC[Math.abs(8*(7-x)+y)]);
                            break;
                        case CWieza:
                            //System.out.print("[" + BONUS_CZARNA_WIEZA[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (500 + BONUS_CZARNA_WIEZA[Math.abs(8*(7-x)+y)]);
                            break;
                        case CHetman:
                            //System.out.print("[" + BONUS_CZARNY_HETMAN[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (900 + BONUS_CZARNY_HETMAN[Math.abs(8*(7-x)+y)]);
                            break;
                        case CKrol:
                            //System.out.print("[" + BONUS_CZARNY_KROL[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (10000 + BONUS_CZARNY_KROL[Math.abs(8*(7-x)+y)]);
                            break;
                        default:
                            //System.out.print("[" + 0 + "]");
                            break;
                    }
                } else {
                    switch (ustawienie[x][y]) {
                        case BPion:
                            //System.out.print("[" + BONUS_BIALY_PION[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (100 + BONUS_BIALY_PION[Math.abs(8*(7-x)+y)]);
                            break;
                        case BSkoczek:
                            //System.out.print("[" + BONUS_BIALY_SKOCZEK[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (300 + BONUS_BIALY_SKOCZEK[Math.abs(8*(7-x)+y)]);
                            break;
                        case BGoniec:
                            //System.out.print("[" + BONUS_BIALY_GONIEC[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (330 + BONUS_BIALY_GONIEC[Math.abs(8*(7-x)+y)]);
                            break;
                        case BWieza:
                            //System.out.print("[" + BONUS_BIALA_WIEZA[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (500 + BONUS_BIALA_WIEZA[Math.abs(8*(7-x)+y)]);
                            break;
                        case BHetman:
                            //System.out.print("[" + BONUS_BIALY_HETAN[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (900 + BONUS_BIALY_HETAN[Math.abs(8*(7-x)+y)]);
                            break;
                        case BKrol:
                            //System.out.print("[" + BONUS_BIALY_KROL[Math.abs(8*(7-x)+y)] + "]");
                            wartosc = wartosc + (10000 + BONUS_BIALY_KROL[Math.abs(8*(7-x)+y)]);
                            break;
                        default:
                            //System.out.print("[" + 0 + "]");
                            break;
                    }
                }
            }//System.out.println();
        }
        //System.out.println("-----------------");
        return wartosc;
    }

    private static int szachmat(boolean strona) {
        int[] krol = new int[2];
        boolean zagrozone;
        try {
            boolean wyszlo = RuchZagrozenie_kontrola.szach(SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie), strona);
        } catch (Exception x) {
            //System.out.println("szach error");
        }
        if (RuchZagrozenie_kontrola.szach(SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie), strona) == true) {
            zagrozone = true;
            char[][] backup = SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie);
            char[][] backup1 = SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie);
            char[][] backup2 = SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie);
            char[][] backup3 = SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie);
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {

                    if ((ustawienie[i][j] == SI_MIN_MAX_Alfa_Beta1.figury.BKrol && strona == true)
                            || (ustawienie[i][j] == SI_MIN_MAX_Alfa_Beta1.figury.CKrol && strona == false)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }
            return SzachMatPatKontrola.uciekaj(backup1, strona, krol) == false
                    && SzachMatPatKontrola.zastaw(backup2, strona, Wspomagacz.znajdzklopot(backup, strona), krol, przelotcan) == false
                    && SzachMatPatKontrola.znajdzodsiecz(backup3, strona, Wspomagacz.znajdzklopot(backup, strona), kol, przelotcan) == false
                    ? (KalkulatorPozycji1.glebia != 0 ? -MAT * KalkulatorPozycji1.glebia : -MAT) : zagrozone == true ? -SZACH : 0;

        }
        ////System.out.println("Wchodzi2");
        return 0;
    }

    private static int dokonano_roszady(boolean strona) {
        if (strona == true) {
            if (wykonanaRochB == true) {
                //////System.out.print(ROSZADA+"+");
                return ROSZADA;
            }
        } else {
            if (wykonanaRochC == true) {
                //////System.out.print(ROSZADA+"+");
                return ROSZADA;
            }
        }
        ////System.out.println("Wchodzi3");
        return 0;
    }

    private static int pionkiS(boolean strona) {
////System.out.println("Wchodzi5");
        return pozycja_PION.punktacja(strona, SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie));
    }

    private int wiezeS(boolean strona) {
        pozycja_WIEZA w = new pozycja_WIEZA();
        ////System.out.println("Wchodzi6");
        return w.punktacja(strona, SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie)) - w.punktacja(!strona, SI_MIN_MAX_Alfa_Beta1.konwert(ustawienie));
    }

    private static int mobilnosc(boolean strona) {
        ////System.out.println("Wchodzi4");

        ////System.out.println(stosunek(strona)*RUCHY_BONUS);
        return RUCHY_BONUS * stosunek(strona);
    }

    private static int stosunek(boolean b) {
        float swoje = (Generator1.generuj_posuniecia(ustawienie, b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol).size() * 10.0f);
        float wrogie = Generator1.generuj_posuniecia(ustawienie, !b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol).size();
        return (int) (swoje / wrogie);
    }

    private int save_king(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        analizator_odleglosci save = Bezpieczenstwo_krol.get().zliczpozycje(ustawienie, strona, przelotcan,
                RochB, RochC, wl, wr, bl, br, kol);
        return save.getWartosc();
    }

    private static int ruchy_zbijajace(boolean strona) {
        ////System.out.println("Wchodzi7");
        Collection<Ruch1> lista = Generator1.generuj_posuniecia((ustawienie), strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, 0, kol);
        int licznik_ataku = 0;

        if (!lista.isEmpty()) {
            for (Ruch1 ruch : lista) {
                if (ruch.korzystnosc_bicia != Ruch1.figura.Pustka) {
                    int wartosc_atakujacego = wartosc(ruch.kolejnosc);
                    int wartosc_atakowanego = wartosc(ruch.korzystnosc_bicia);
                    if (wartosc_atakujacego <= wartosc_atakowanego) {
                        licznik_ataku++;
                    }
                }
            }
        }

        //////System.out.print(licznik_ataku*BICIA_BONUS);
        return (licznik_ataku * BICIA_BONUS);
    }

    private static int wartosc(Ruch1.figura bierka) {
        switch (bierka) {
            case Pion:
                return 100;
            case Skoczek:
                return 300;
            case Goniec:
                return 330;
            case Wieza:
                return 500;
            case Hetman:
                return 900;
            case Krol:
                return 10000;
        }
        return 0;
    }

    private char pozyskaj_figure(Ruch.figura ruch) {
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
    }

}
