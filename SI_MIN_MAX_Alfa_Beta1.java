package szachy;

import java.util.ArrayList;
import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patryk
 */
public class SI_MIN_MAX_Alfa_Beta1 extends Observable {

    ArrayList<Ruch> historia = new ArrayList<>();
    int pozycje = 0;
    int all_position = 0;
    int licznik, maxglebia;
    String riposta = "";
    SI_MIN_MAX_Alfa_Beta.figury[][] pozycja = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
    SI_MIN_MAX_Alfa_Beta.figury[][] pozycja_wyjsciowa = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
    boolean tura_rywala, przelotcan, bleft, bright, wleft, wright,
            kingrochB, kingrochC, didRochB, didRochC;
    int kolumna;
    boolean przerwa;
    boolean zakaz;
    int najlepszy;
    private final boolean wyjsciowa_tura;
    private final Kalkulator wynikowa;

    synchronized private boolean kontrola_pat(char[][] ustawienie, boolean strona, boolean przelotcan) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[] poza_krolewska = new int[2];
                poza_krolewska[0] = j;
                poza_krolewska[1] = i;
                if (ustawienie[i][j] == 'K' || ustawienie[i][j] == 'k') {

                    //System.out.println("wchodzi król "+(ustawienie[i][j]=='K'?"biały":"czarny"));
                    if ((strona == true && ustawienie[i][j] == 'K')
                            || (strona == false && ustawienie[i][j] == 'k')) {
                        //   System.out.println("wchodzi "+ustawienie[i][j]+" "+strona);
                        poza_krolewska[0] = i;
                        poza_krolewska[1] = j;
                        //  System.out.println(ustawienie[poza_krolewska[0]][poza_krolewska[1]]);
                        if (SzachMatPatKontrola.uciekaj(ustawienie, strona, poza_krolewska) == true) {

                            return false;
                        }
                    }

                } else {

                    if (SzachMatPatKontrola.znajdz_ruch(ustawienie, strona, ustawienie[i][j], poza_krolewska, przelotcan) == true) {

                        return false;
                    }

                }
            }
        }
        return true;
    }

    public enum figury {
        BKrol, BHetman, BWieza, BGoniec, BSkoczek, BPion,
        CKrol, CHetman, CWieza, CGoniec, CSkoczek, CPion, pustka;
    }

    public synchronized static char[][] konwert(SI_MIN_MAX_Alfa_Beta.figury[][] pozycja) {
        char[][] wynik = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (pozycja[i][j]) {
                    case BKrol:
                        wynik[i][j] = 'K';
                        break;
                    case BHetman:
                        wynik[i][j] = 'Q';
                        break;
                    case BWieza:
                        wynik[i][j] = 'R';
                        break;
                    case BGoniec:
                        wynik[i][j] = 'B';
                        break;
                    case BSkoczek:
                        wynik[i][j] = 'N';
                        break;
                    case BPion:
                        wynik[i][j] = 'P';
                        break;
                    case CKrol:
                        wynik[i][j] = 'k';
                        break;
                    case CHetman:
                        wynik[i][j] = 'q';
                        break;
                    case CWieza:
                        wynik[i][j] = 'r';
                        break;
                    case CGoniec:
                        wynik[i][j] = 'b';
                        break;
                    case CSkoczek:
                        wynik[i][j] = 'n';
                        break;
                    case CPion:
                        wynik[i][j] = 'p';
                        break;
                    case pustka:
                        wynik[i][j] = ' ';
                        break;
                }
            }
        }
        return wynik;
    }

    public SI_MIN_MAX_Alfa_Beta1(char[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean kingrochB, boolean kingrochC, boolean dokonano_RB, boolean dokonano_RC,
            int kol, boolean odwrot, int licznik, int glebina) {
        this.licznik = licznik;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case ' ':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                        break;
                    case 'P':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BPion;
                        break;
                    case 'p':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CPion;
                        break;
                    case 'N':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BSkoczek;
                        break;
                    case 'n':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CSkoczek;
                        break;
                    case 'B':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BGoniec;
                        break;
                    case 'b':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CGoniec;
                        break;
                    case 'R':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                        break;
                    case 'r':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                        break;
                    case 'Q':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BHetman;
                        break;
                    case 'q':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CHetman;
                        break;
                    case 'K':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                        break;
                    case 'k':
                        pozycja[i][j] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                        break;
                }
                pozycja_wyjsciowa[i][j] = pozycja[i][j];
            }
        }
        this.tura_rywala = tura_rywala;
        this.wyjsciowa_tura = tura_rywala;
        this.przelotcan = przelotcan;
        this.bleft = bleft;
        this.bright = bright;
        this.wleft = wleft;
        this.wright = wright;
        this.didRochB = dokonano_RB;
        this.didRochC = dokonano_RC;
        this.kolumna = kol;
        this.kingrochB = kingrochB;
        this.kingrochC = kingrochC;
        this.maxglebia = glebina;
        wynikowa = KalkulatorPozycji.get();
    }

    synchronized public int wykonaj(int glebia, Ruch move, int najwieksza, int najmniejsza) {
        int biezaca_ogolna;
        all_position = all_position + 1;
        byte Nkolumna;
        if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
            Nkolumna = (byte) (pozyskajkordrzad(move.start1));
            this.przelotcan = true;
        } else {
            Nkolumna = 0;
            this.przelotcan = false;
        }
        historia.add(move);
        try {

            if (RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala) == false
                    && obecnosc(this.pozycja) == true) {
                aktualizacja_parametrow(move);
                this.tura_rywala = this.tura_rywala != true;
                biezaca_ogolna = (wyjsciowa_tura == true)
                        ? minimum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after)
                        : maximum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after);
                this.tura_rywala = this.tura_rywala == false;
                if (wyjsciowa_tura == true) {
                    setPrzerwa(kontrola_mat(konwert(move.chessboard_after), false, Nkolumna, przelotcan));
                } else {
                    setPrzerwa(kontrola_mat(konwert(move.chessboard_after), true, Nkolumna, przelotcan));
                }

                przywroc_parametry(move);
                setZakaz(false);
                System.out.println("biezaca ogolna: " + biezaca_ogolna);
                System.out.println("licznik After " + licznik);
                return biezaca_ogolna;
            } else {
                setZakaz(true);
                System.out.println("err2");
                przywroc_parametry(move);
                return wyjsciowa_tura == true ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
        } catch (Exception e) {
            
            
           
            System.out.println(e);
            e.printStackTrace();
            return wyjsciowa_tura == true ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

    }

    synchronized public boolean isZakaz() {
        return zakaz;
    }

    synchronized public void setZakaz(boolean zakaz) {
        this.zakaz = zakaz;
    }

    synchronized private int maximum(final int glebia, byte kolumna, int biggest, int samllest, SI_MIN_MAX_Alfa_Beta.figury[][] chessboard) {
        int[] temp1 = {0, 0};
        if (glebia == 0 || koniec(konwert(chessboard), this.tura_rywala, this.przelotcan, kolumna) == true
                || Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                        this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false).isEmpty()) {
            pozycje = pozycje + 1;
            return this.wynikowa.zliczacz(chessboard, tura_rywala, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, didRochB, didRochC, glebia, kolumna);
        }
        int temp = biggest;
        all_position = all_position + Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false).size();
        for (Ruch move : Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false)) {
            if (RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala) == false
                    && obecnosc(move.chessboard_after) == true) {
                 historia.add(move);
                byte Nkolumna;
                if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                    Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                    this.przelotcan = true;
                } else {
                    Nkolumna = 0;
                    this.przelotcan = false;
                }
                aktualizacja_parametrow(move);
                this.tura_rywala = this.tura_rywala != true;
                temp = Math.max(temp, minimum(((glebia - 1)), Nkolumna,
                        temp, samllest, move.chessboard_after));
                przywroc_parametry(move);
                this.tura_rywala = this.tura_rywala == false;
                historia.remove(historia.size() - 1);
                if (samllest <= temp) {
                   break;
                }
            }
        }
        return temp;
    }

    synchronized private int minimum(final int glebia, byte kolumna, int biggest, int smallest, SI_MIN_MAX_Alfa_Beta.figury[][] chessboard) {
        int[] temp1 = new int[2];
        if (glebia == 0 || koniec(konwert(chessboard),
                this.tura_rywala, this.przelotcan, kolumna) == true
                || Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                        this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false).isEmpty()) {
            pozycje = pozycje + 1;
            return this.wynikowa.zliczacz(chessboard, tura_rywala, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, didRochB, didRochC, glebia, kolumna);
        }
        int temp = smallest;
        all_position = all_position + Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false).size();
         for (Ruch move : Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false)) {
            if (RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala) == false
                    && obecnosc(move.chessboard_after) == true) {
                byte Nkolumna;
                historia.add(move);
                 if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                    Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                    this.przelotcan = true;
                } else {
                    Nkolumna = 0;
                    this.przelotcan = false;
                }
                aktualizacja_parametrow(move);
                this.tura_rywala = this.tura_rywala != true;
                temp = Math.min(temp, maximum(((glebia - 1)),
                        Nkolumna, biggest, temp, move.chessboard_after));
                przywroc_parametry(move);
                this.tura_rywala = this.tura_rywala == false;
                historia.remove(historia.size() - 1);
                if (temp <= biggest) {
                    break;
                }
            }
        }
        return temp;
    }

    synchronized private boolean obecnosc(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie13) {
        byte KB = 0, KC = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (ustawienie13[x][y] == SI_MIN_MAX_Alfa_Beta.figury.BKrol) {
                    KB++;
                }
                if (ustawienie13[x][y] == SI_MIN_MAX_Alfa_Beta.figury.CKrol) {
                    KC++;
                }
            }
        }
        return KB == 1 && KC == 1;
    }

    synchronized private boolean koniec(char[][] ustawienie, boolean strona, boolean przelotcan, int kol) {
        int pionB = 0, pionC = 0, lekkieB = 0, lekkieC = 0, ciezkieB = 0, ciezkieC = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case 'P':
                        pionB++;
                        break;
                    case 'p':
                        pionC++;
                        break;
                    case 'N':
                        lekkieB++;
                        break;
                    case 'n':
                        lekkieC++;
                        break;
                    case 'B':
                        lekkieB++;
                        break;
                    case 'b':
                        lekkieC++;
                        break;
                    case 'R':
                        ciezkieB++;
                        break;
                    case 'r':
                        ciezkieC++;
                        break;
                    case 'Q':
                        ciezkieB++;
                        break;
                    case 'q':
                        ciezkieC++;
                        break;
                }

            }
        }
        if (pionB < 1 && pionC < 1 && lekkieB < 2 && lekkieC < 2 && ciezkieB < 1 && ciezkieC < 1) {
            return true;
        } else {

            if (RuchZagrozenie_kontrola.szach(ustawienie, strona) == true) {
                return kontrola_mat(ustawienie, strona, (byte) kol, przelotcan);
            } else {

                return kontrola_pat(ustawienie, strona, przelotcan);
            }
        }
    }

    synchronized private boolean kontrola_mat(char[][] ustawienie, boolean strona, byte kol, boolean przelotcan) {
        int[] krol = new int[2];
        if (RuchZagrozenie_kontrola.szach((ustawienie), strona) == true) {
            char[][] backup = new char[8][8];
            char[][] backup1 = new char[8][8];
            char[][] backup2 = new char[8][8];
            char[][] backup3 = new char[8][8];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    backup[i][j] = ustawienie[i][j];
                    backup1[i][j] = ustawienie[i][j];
                    backup2[i][j] = ustawienie[i][j];
                    backup3[i][j] = ustawienie[i][j];
                    if ((ustawienie[i][j] == 'K' && strona == true)
                            || (ustawienie[i][j] == 'k' && strona == false)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }

            return SzachMatPatKontrola.uciekaj(backup1, strona, krol) == false
                    && SzachMatPatKontrola.zastaw(backup2, strona, Wspomagacz.znajdzklopot(backup, strona), krol, przelotcan) == false
                    && SzachMatPatKontrola.znajdzodsiecz(backup3, strona, Wspomagacz.znajdzklopot(backup, strona), kol, przelotcan) == false;

        } else {
            return false;
        }

    }

    synchronized private void aktualizacja_parametrow(Ruch move) {
        if (move.roszada == true && move.dlugaroszada == false) {
            if (this.tura_rywala == true) {
                wright = false;
            } else {
                bright = false;
            }
        } else if (move.roszada == true && move.dlugaroszada == true) {
            if (this.tura_rywala == true) {
                wleft = false;
            } else {
                wleft = false;
            }
        }
        if (move.roszada == true) {
            if (this.tura_rywala == true) {
                this.didRochB = true;
                kingrochB = false;
            } else {
                this.didRochC = true;
                kingrochB = false;
            }
        } else {
            switch (move.kolejnosc) {
                case Wieza:
                    if (tura_rywala == false) {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                            bleft = false;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                            bright = false;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                            wleft = false;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                            wright = false;
                        }
                    }
                    break;
                case Krol:
                    if (tura_rywala == true) {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                            kingrochB = false;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                            kingrochC = false;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    synchronized private void przywroc_parametry(Ruch move) {
        if (move.roszada == true && move.dlugaroszada == false) {
            if (this.tura_rywala == true) {
                wright = true;
            } else {
                bright = true;
            }
        } else if (move.roszada == true && move.dlugaroszada == true) {
            if (this.tura_rywala == true) {
                wleft = true;
            } else {
                wleft = true;
            }
        }
        if (move.roszada == true) {
            if (this.tura_rywala == true) {
                this.didRochB = false;
                kingrochB = true;
            } else {
                this.didRochC = false;
                kingrochB = true;
            }
        } else {
            switch (move.kolejnosc) {
                case Wieza:
                    if (tura_rywala == false) {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                            bleft = true;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                            bright = true;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                            wleft = true;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                            wright = true;
                        }
                    }
                    break;
                case Krol:
                    if (tura_rywala == true) {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                            kingrochB = true;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                            kingrochC = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    synchronized private void wykonajruch(Ruch move) {
        /*  for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("["+konwert(this.pozycja)[i][j]+"]");   
            }System.out.println("");
            }*/
        if (move.roszada == true) {

            if (move.roszada == true && move.dlugaroszada == false) {
                if (move.czybialy == true) {
                    this.pozycja[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[0][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[0][5] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    this.pozycja[0][6] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    this.pozycja[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[7][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[7][5] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    this.pozycja[7][6] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            } else if (move.roszada == true && move.dlugaroszada == true) {
                if (move.czybialy == true) {
                    this.pozycja[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[0][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[0][3] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    this.pozycja[0][2] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    this.pozycja[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[7][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    this.pozycja[7][3] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    this.pozycja[7][2] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            }

        } else {

            if (move.kolejnosc == Ruch.figura.Pion) {
                if (move.przelot == true) {
                    this.pozycja[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                }
            }
            this.pozycja[pozyskajkordkolumna(move.start2) - 1][pozyskajkordrzad(move.start1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
            if (move.przelot == true) {
                this.pozycja[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

            } else if (move.promocja == true) {
                this.pozycja[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.promowana, move.czybialy);
            } else {
                this.pozycja[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.kolejnosc, move.czybialy);
            }
        }
        /*  for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("<"+konwert(this.pozycja)[i][j]+">");   
            }System.out.println("");
            }*/
    }

    synchronized private SI_MIN_MAX_Alfa_Beta.figury[][] wykonajruch1(Ruch move) {
        SI_MIN_MAX_Alfa_Beta.figury[][] poza = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy((this.pozycja)[i], 0, poza[i], 0, 8); //  System.out.print("[" + konwert(this.pozycja)[i][j] + "]");
            //  System.out.println("");
        }
        if (move.roszada == true) {

            if (move.roszada == true && move.dlugaroszada == false) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][5] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][6] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][5] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][6] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            } else if (move.roszada == true && move.dlugaroszada == true) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][3] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][2] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][3] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][2] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            }

        } else {

            if (move.kolejnosc == Ruch.figura.Pion) {
                if (move.przelot == true) {
                    poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                }
            }
            poza[pozyskajkordkolumna(move.start2) - 1][pozyskajkordrzad(move.start1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
            if (move.przelot == true) {
                poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

            } else if (move.promocja == true) {
                poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.promowana, move.czybialy);
            } else {
                poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.kolejnosc, move.czybialy);
            }
        }
        /*  for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("<"+konwert(poza)[i][j]+">");   
            }System.out.println("");
            }*/
        return poza;
    }

    synchronized private SI_MIN_MAX_Alfa_Beta.figury[][] wykonajruch1(Ruch move, SI_MIN_MAX_Alfa_Beta.figury[][] poza) {

        if (move.roszada == true) {

            if (move.roszada == true && move.dlugaroszada == false) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][5] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][6] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][5] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][6] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            } else if (move.roszada == true && move.dlugaroszada == true) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][3] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][2] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][3] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][2] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            }

        } else {

            if (move.kolejnosc == Ruch.figura.Pion) {
                if (move.przelot == true) {
                    poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                }
            }
            poza[pozyskajkordkolumna(move.start2) - 1][pozyskajkordrzad(move.start1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
            if (move.przelot == true) {
                poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

            } else if (move.promocja == true) {
                poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.promowana, move.czybialy);
            } else {
                poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1] = pozyskaj_figure(move.kolejnosc, move.czybialy);
            }
        }
        /*  for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("<"+konwert(poza)[i][j]+">");   
            }System.out.println("");
            }*/
        return poza;
    }

    synchronized private SI_MIN_MAX_Alfa_Beta.figury[][] cofnij_ruch(Ruch move, SI_MIN_MAX_Alfa_Beta.figury[][] poza) {

        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("|"+konwert(this.pozycja)[i][j]+"|");   
            }System.out.println("");
            } System.out.println(move.toString());*/
        if (move.roszada == true) {

            if (move.roszada == true && move.dlugaroszada == false) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                    poza[0][7] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][5] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][6] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                    poza[7][7] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][5] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][6] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                }
            } else if (move.roszada == true && move.dlugaroszada == true) {
                if (move.czybialy == true) {
                    poza[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                    poza[0][0] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    poza[0][3] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[0][2] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                } else {
                    poza[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                    poza[7][0] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    poza[7][3] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    poza[7][2] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                }
            }

        } else {

            if (move.kolejnosc == Ruch.figura.Pion) {
                if (move.przelot == true) {
                    poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1]
                            = move.czybialy == false ? SI_MIN_MAX_Alfa_Beta.figury.BPion : SI_MIN_MAX_Alfa_Beta.figury.CPion;
                }
            }
            poza[pozyskajkordkolumna(move.start2) - 1][pozyskajkordrzad(move.start1) - 1] = pozyskaj_figure(move.kolejnosc, move.czybialy);
            if (move.przelot == true) {
                poza[move.czybialy == false ? 3 : 4][pozyskajkordrzad(move.koniec1) - 1]
                        = move.czybialy == false ? SI_MIN_MAX_Alfa_Beta.figury.BPion : SI_MIN_MAX_Alfa_Beta.figury.CPion;

            } else if (move.promocja == true) {
                poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1]
                        = pozyskaj_figure(move.korzystnosc_bicia, !move.czybialy);
            }
            poza[pozyskajkordkolumna(move.koniec2) - 1][pozyskajkordrzad(move.koniec1) - 1]
                    = pozyskaj_figure(move.korzystnosc_bicia, !move.czybialy);

        }
        /* for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("("+konwert(poza)[i][j]+")");   
            }System.out.println("");
            }*/
        //  System.out.println(pozyskaj_figure(move.kolejnosc, move.czybialy)+"("+pozyskaj_figure(move.korzystnosc_bicia, !move.czybialy)+")");
        return poza;
    }

    synchronized private int pozyskajkordkolumna(Ruch.rzad kord) {
        switch (kord) {
            case r1:
                return 1;
            case r2:
                return 2;
            case r3:
                return 3;
            case r4:
                return 4;
            case r5:
                return 5;
            case r6:
                return 6;
            case r7:
                return 7;
            case r8:
                return 8;
        }
        return 0;
    }

    synchronized private int pozyskajkordrzad(Ruch.kolumna kord) {
        switch (kord) {
            case k1:
                return 1;
            case k2:
                return 2;
            case k3:
                return 3;
            case k4:
                return 4;
            case k5:
                return 5;
            case k6:
                return 6;
            case k7:
                return 7;
            case k8:
                return 8;
        }
        return 0;

    }

    synchronized private SI_MIN_MAX_Alfa_Beta.figury pozyskaj_figure(Ruch.figura ruch, boolean czybialy) {
        switch (ruch) {
            case Pion:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BPion;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CPion;
                }
            case Skoczek:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BSkoczek;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CSkoczek;
                }
            case Goniec:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BGoniec;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CGoniec;
                }
            case Wieza:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                }
            case Hetman:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BHetman;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CHetman;
                }
            case Krol:
                if (czybialy == true) {
                    return SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                } else {
                    return SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                }
            default:
                return SI_MIN_MAX_Alfa_Beta.figury.pustka;
        }
    }

    synchronized public void setPrzerwa(boolean przerwa) {
        this.przerwa = przerwa;
    }

    synchronized public int getPozycje() {
        return pozycje;
    }

    synchronized public int getAll_position() {
        return all_position;
    }

    synchronized public boolean isPrzerwa() {
        return przerwa;
    }

    synchronized private int zrekalkuluj_glebie(int glebia, boolean[] bicia) {
        if (glebia == 1 && this.licznik < 25000) {
            int aktywne = 0;
            if (RuchZagrozenie_kontrola.szach(konwert(this.pozycja), tura_rywala)) {
                aktywne = aktywne + 1;
            }

            for (int i = historia.size() - 2; i < historia.size(); i++) {
                if (historia.get(i).atak == true) {
                    aktywne = aktywne + 1;
                }
            }

            if (aktywne >= 2) {
                this.licznik = this.licznik + 1;
//                System.out.println("rekalk");
                return 1;
            }
        }

        return glebia - 1;
    }
}
