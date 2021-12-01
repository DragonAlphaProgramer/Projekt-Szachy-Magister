/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

/**
 *
 * @author Patryk klasa przechowująca parametry ruchów
 */
public class Ruch1 implements Comparable<Ruch1> {

    Ruch1(String lista, int sposob, SI_MIN_MAX_Alfa_Beta1.figury bity) {
      szach = lista.charAt(lista.length() - 1) == '+';

        switch (bity) {
            case BPion:
            case CPion:
                korzystnosc_bicia = figura.Pion;
                break;
            case BSkoczek:
            case CSkoczek:
                korzystnosc_bicia = figura.Skoczek;
                break;
            case BGoniec:
            case CGoniec:
                korzystnosc_bicia = figura.Goniec;
                break;
            case BWieza:
            case CWieza:
                korzystnosc_bicia = figura.Wieza;
                break;
            case BHetman:
            case CHetman:
                korzystnosc_bicia = figura.Hetman;
                break;
            default:
                korzystnosc_bicia = figura.Pustka;
        }

        roszada = lista.substring(1, 4).equals("O-O");
        dlugaroszada = lista.substring(1, 6).equals("O-O-O");
        przelot = lista.charAt(6) == ('E');
        promocja = lista.charAt(6) == '='
                && (lista.charAt(0) == 'p' || lista.charAt(0) == 'P');
        if (lista.charAt(6) == '=') {
            switch (lista.charAt(7)) {
                case 'n':
                    promowana = figura.Skoczek;
                    wartosc_promocji = 1;
                    break;
                case 'N':
                    promowana = figura.Skoczek;
                    wartosc_promocji = 1;
                    break;
                case 'b':
                    promowana = figura.Goniec;
                    wartosc_promocji = 2;
                    break;
                case 'B':
                    promowana = figura.Goniec;
                    wartosc_promocji = 2;
                    break;
                case 'r':
                    promowana = figura.Wieza;
                    wartosc_promocji = 3;
                    break;
                case 'R':
                    promowana = figura.Wieza;
                    wartosc_promocji = 3;
                    break;
                case 'q':
                    promowana = figura.Hetman;
                    wartosc_promocji = 4;
                    break;
                case 'Q':
                    promowana = figura.Hetman;
                    wartosc_promocji = 4;
                    break;
                default:
                    promowana = null;
                    break;
            }
        }
        if (lista.substring(1, 4).equals("O-O")) {
            start1 = kolumna.KR;
            start2 = rzad.RR;
            koniec1 = kolumna.KR;
            koniec2 = rzad.RR;
        } else {
            switch (lista.charAt(1)) {
                case 'A':
                    start1 = kolumna.k1;
                    break;
                case 'B':
                    start1 = kolumna.k2;
                    break;
                case 'C':
                    start1 = kolumna.k3;
                    break;
                case 'D':
                    start1 = kolumna.k4;
                    break;
                case 'E':
                    start1 = kolumna.k5;
                    break;
                case 'F':
                    start1 = kolumna.k6;
                    break;
                case 'G':
                    start1 = kolumna.k7;
                    break;
                case 'H':
                    start1 = kolumna.k8;
                    break;
            }
            switch (lista.charAt(4)) {
                case 'A':
                    koniec1 = kolumna.k1;
                    break;
                case 'B':
                    koniec1 = kolumna.k2;
                    break;
                case 'C':
                    koniec1 = kolumna.k3;
                    break;
                case 'D':
                    koniec1 = kolumna.k4;
                    break;
                case 'E':
                    koniec1 = kolumna.k5;
                    break;
                case 'F':
                    koniec1 = kolumna.k6;
                    break;
                case 'G':
                    koniec1 = kolumna.k7;
                    break;
                case 'H':
                    koniec1 = kolumna.k8;
                    break;
            }
            switch (lista.charAt(2)) {
                case '1':
                    start2 = rzad.r1;
                    break;
                case '2':
                    start2 = rzad.r2;
                    break;
                case '3':
                    start2 = rzad.r3;
                    break;
                case '4':
                    start2 = rzad.r4;
                    break;
                case '5':
                    start2 = rzad.r5;
                    break;
                case '6':
                    start2 = rzad.r6;
                    break;
                case '7':
                    start2 = rzad.r7;
                    break;
                case '8':
                    start2 = rzad.r8;
                    break;
            }
            switch (lista.charAt(5)) {
                case '1':
                    koniec2 = rzad.r1;
                    break;
                case '2':
                    koniec2 = rzad.r2;
                    break;
                case '3':
                    koniec2 = rzad.r3;
                    break;
                case '4':
                    koniec2 = rzad.r4;
                    break;
                case '5':
                    koniec2 = rzad.r5;
                    break;
                case '6':
                    koniec2 = rzad.r6;
                    break;
                case '7':
                    koniec2 = rzad.r7;
                    break;
                case '8':
                    koniec2 = rzad.r8;
                    break;
            }
        }
        if (!lista.substring(1, 4).equals("O-O")) {
            switch (lista.charAt(0)) {
                case 'p':
                    czybialy = false;
                    kolejnosc = figura.Pion;
                    break;
                case 'P':
                    czybialy = true;
                    kolejnosc = figura.Pion;
                    break;
                case 'n':
                    czybialy = false;
                    kolejnosc = figura.Skoczek;
                    break;
                case 'N':
                    czybialy = true;
                    kolejnosc = figura.Skoczek;
                    break;
                case 'b':
                    czybialy = false;
                    kolejnosc = figura.Goniec;
                    break;
                case 'B':
                    czybialy = true;
                    kolejnosc = figura.Goniec;
                    break;
                case 'r':
                    czybialy = false;
                    kolejnosc = figura.Wieza;
                    break;
                case 'R':
                    czybialy = true;
                    kolejnosc = figura.Wieza;
                    break;
                case 'q':
                    czybialy = false;
                    kolejnosc = figura.Hetman;
                    break;
                case 'Q':
                    czybialy = true;
                    kolejnosc = figura.Hetman;
                    break;
                case 'k':
                    czybialy = false;
                    kolejnosc = figura.Krol;
                    break;
                case 'K':
                    czybialy = true;
                    kolejnosc = figura.Krol;
                    break;

            }
        } else {
            kolejnosc = figura.Krol;
            czybialy = lista.charAt(0) == 'K';
        }
        if (korzystnosc_bicia == figura.Pustka) {
            wspolczynnik_bicia = wartosc(figura.Krol) - wartosc(kolejnosc);
        } else {
            wspolczynnik_bicia = (wartosc(korzystnosc_bicia) - wartosc(kolejnosc) + wartosc(figura.Krol)) * 100;
        }

        switch (sposob) {
            case 1:
                this.sposob = sortowanie.pierwszy_szach;
                break;
            case 2:
                this.sposob = sortowanie.pierwsze_bicie;
                break;
            default:
                this.sposob = sortowanie.zaden;
        }}


    enum figura {
        Pion, Skoczek, Goniec, Wieza, Hetman, Krol, Pustka;
    };

    enum rzad {
        r1, r2, r3, r4, r5, r6, r7, r8, RR;
    };

    enum kolumna {
        k1, k2, k3, k4, k5, k6, k7, k8, KR;
    };

    enum sortowanie {
        pierwszy_szach, pierwsze_bicie, zaden
    };
    boolean szach, roszada, czybialy, przelot, dlugaroszada, promocja;
    kolumna start1, koniec1;
    rzad start2, koniec2;
    figura kolejnosc, promowana = null;
    final sortowanie sposob;
    int wspolczynnik_bicia = 0;
    int wartosc_promocji = 0;
    figura korzystnosc_bicia;

    private int wartosc(figura f) {
        switch (f) {
            case Goniec:
                return 330;
            case Hetman:
                return 900;
            case Krol:
                return 10000;
            case Pion:
                return 100;
            case Pustka:
                return 0;
            case Skoczek:
                return 300;
            case Wieza:
                return 500;
            default:
                return 0;
        }

    }

   

    @Override
    public int compareTo(Ruch1 obiekt) {
        switch (this.sposob) {
            case pierwszy_szach:
                if (szach == (obiekt.szach)) {
                    if (roszada == (obiekt.roszada)) {
                       if (kolejnosc == obiekt.kolejnosc) {
                            if (wspolczynnik_bicia == (obiekt.wspolczynnik_bicia)) {
                              if (start2.compareTo(obiekt.start2) == 0) {
                                    if (start1.compareTo(obiekt.start1) == 0) {
                                         if (koniec2.compareTo(obiekt.koniec2) == 0) {
                                            return (koniec1.compareTo(obiekt.koniec1)) * (czybialy == true ? 1 : 1);
                                        } else {
                                            return koniec2.compareTo(obiekt.koniec2) * (czybialy == true ? 1 : -1);
                                        }
                                    } else {
                                        return start1.compareTo(obiekt.start1) * (czybialy == true ? 1 : 1);
                                    }
                                } else {
                                    return start2.compareTo(obiekt.start2) * (czybialy == true ? -1 : 1);
                                }
                        } else {
                             return (wspolczynnik_bicia < obiekt.wspolczynnik_bicia) ? 1 : -1;
                        }
                    } else {
                            switch (kolejnosc) {
                                case Pion:
                                    switch (obiekt.kolejnosc) {
                                        case Skoczek:
                                            return -1;
                                        case Goniec:
                                            return -1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Skoczek:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return -1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Goniec:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Wieza:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Hetman:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Wieza:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Krol:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Wieza:
                                            return 1;
                                        case Hetman:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                    }
                                    break;
                            }
                       
                    }

                    } else {
                        if (roszada == true && obiekt.roszada == false) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                } else {
                    if (szach == true && obiekt.szach == false) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

            case pierwsze_bicie:
                if (roszada == (obiekt.roszada)) {
                        if (kolejnosc == obiekt.kolejnosc) {
                            if (wspolczynnik_bicia == (obiekt.wspolczynnik_bicia)) {
                              if (start2.compareTo(obiekt.start2) == 0) {
                                    if (start1.compareTo(obiekt.start1) == 0) {
                                         if (koniec2.compareTo(obiekt.koniec2) == 0) {
                                            return (koniec1.compareTo(obiekt.koniec1)) * (czybialy == true ? 1 : 1);
                                        } else {
                                            return koniec2.compareTo(obiekt.koniec2) * (czybialy == true ? 1 : -1);
                                        }
                                    } else {
                                        return start1.compareTo(obiekt.start1) * (czybialy == true ? 1 : 1);
                                    }
                                } else {
                                    return start2.compareTo(obiekt.start2) * (czybialy == true ? -1 : 1);
                                }
                        } else {
                             return (wspolczynnik_bicia < obiekt.wspolczynnik_bicia) ? 1 : -1;
                        }
                    } else {
                            switch (kolejnosc) {
                                case Pion:
                                    switch (obiekt.kolejnosc) {
                                        case Skoczek:
                                            return -1;
                                        case Goniec:
                                            return -1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Skoczek:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return -1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Goniec:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Wieza:
                                            return -1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Wieza:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Hetman:
                                            return -1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Hetman:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Wieza:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                        case Krol:
                                            return -1;
                                    }
                                    break;
                                case Krol:
                                    switch (obiekt.kolejnosc) {
                                        case Pion:
                                            return 1;
                                        case Goniec:
                                            return 1;
                                        case Wieza:
                                            return 1;
                                        case Hetman:
                                            return 1;
                                        case Skoczek:
                                            return 1;
                                    }
                                    break;
                            }
                    }

                } else {
                    if (roszada == true && obiekt.roszada == false) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
        }
        return 0;
    }

    @Override
    public String toString() {
        String wynik = "";
        if (roszada == true) {
            wynik = dlugaroszada == true ? "O-O-O" : "O-O";
            return szach ? wynik + "+" : wynik;
        } else {
            switch (kolejnosc) {
                case Pion:
                    wynik = wynik.concat(czybialy ? "P" : "p");
                    break;
                case Skoczek:
                    wynik = wynik.concat(czybialy ? "N" : "n");
                    break;
                case Goniec:
                    wynik = wynik.concat(czybialy ? "B" : "b");
                    break;
                case Wieza:
                    wynik = wynik.concat(czybialy ? "R" : "r");
                    break;
                case Hetman:
                    wynik = wynik.concat(czybialy ? "Q" : "q");
                    break;
                case Krol:
                    wynik = wynik.concat(czybialy ? "K" : "k");
                    break;
            }
            switch (start1) {
                case k1:
                    wynik = wynik.concat("A");
                    break;
                case k2:
                    wynik = wynik.concat("B");
                    break;
                case k3:
                    wynik = wynik.concat("C");
                    break;
                case k4:
                    wynik = wynik.concat("D");
                    break;
                case k5:
                    wynik = wynik.concat("E");
                    break;
                case k6:
                    wynik = wynik.concat("F");
                    break;
                case k7:
                    wynik = wynik.concat("G");
                    break;
                case k8:
                    wynik = wynik.concat("H");
                    break;
            }
            switch (start2) {
                case r1:
                    wynik = wynik.concat("1");
                    break;
                case r2:
                    wynik = wynik.concat("2");
                    break;
                case r3:
                    wynik = wynik.concat("3");
                    break;
                case r4:
                    wynik = wynik.concat("4");
                    break;
                case r5:
                    wynik = wynik.concat("5");
                    break;
                case r6:
                    wynik = wynik.concat("6");
                    break;
                case r7:
                    wynik = wynik.concat("7");
                    break;
                case r8:
                    wynik = wynik.concat("8");
                    break;
            }
            wynik = wynik.concat(korzystnosc_bicia == figura.Pustka ? "-" : "x");
            switch (koniec1) {
                case k1:
                    wynik = wynik.concat("A");
                    break;
                case k2:
                    wynik = wynik.concat("B");
                    break;
                case k3:
                    wynik = wynik.concat("C");
                    break;
                case k4:
                    wynik = wynik.concat("D");
                    break;
                case k5:
                    wynik = wynik.concat("E");
                    break;
                case k6:
                    wynik = wynik.concat("F");
                    break;
                case k7:
                    wynik = wynik.concat("G");
                    break;
                case k8:
                    wynik = wynik.concat("H");
                    break;
            }
            switch (koniec2) {
                case r1:
                    wynik = wynik.concat("1");
                    break;
                case r2:
                    wynik = wynik.concat("2");
                    break;
                case r3:
                    wynik = wynik.concat("3");
                    break;
                case r4:
                    wynik = wynik.concat("4");
                    break;
                case r5:
                    wynik = wynik.concat("5");
                    break;
                case r6:
                    wynik = wynik.concat("6");
                    break;
                case r7:
                    wynik = wynik.concat("7");
                    break;
                case r8:
                    wynik = wynik.concat("8");
                    break;
            }
            if (przelot) {
                wynik = wynik.concat("EP");
            } else if (promocja) {
                wynik = wynik.concat("=");
                switch (promowana) {
                    case Skoczek:
                        wynik = wynik.concat(czybialy ? "N" : "n");
                        break;
                    case Goniec:
                        wynik = wynik.concat(czybialy ? "B" : "b");
                        break;
                    case Wieza:
                        wynik = wynik.concat(czybialy ? "R" : "r");
                        break;
                    case Hetman:
                        wynik = wynik.concat(czybialy ? "Q" : "q");
                        break;
                }
            } else {
                wynik = wynik.concat("--");
            }
            return szach ? wynik + "+" : wynik;
        }

    }
}
