/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Patryk Klasa odpowiedzialna za generowanie listy posunięć
 */
class Generator1 {

    /**
     * sprawdza, czy po ruchu są obecni królowie po obu stronach
     *
     * @param backup sprawdzana pozycja
     * @return jeśli królowie są obecni, zwróci true
     */
    private static boolean obecnosc(final SI_MIN_MAX_Alfa_Beta1.figury[][] backup) {
        boolean KB = false, KC = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (backup[i][j] == SI_MIN_MAX_Alfa_Beta1.figury.BKrol) {
                    KB = KB == false;
                }
                if (backup[i][j] == SI_MIN_MAX_Alfa_Beta1.figury.CKrol) {
                    KC = KC == false;
                }
            }
        }
        return KC == true && KB == true;
    }

    Generator1(char[][] ust) {

    }

    /**
     * Metoda generuje listę dozwolonych posunięć
     *
     * @param ust pozycja na planszy
     * @param tura_rywala strona, która wykonuje ruch
     * @param przelotcan dostęp do bicia w przelocie
     * @param blackleft dostęp czarnych do długiej roszady
     * @param blackright dostęp czarnych do krótkiej roszady
     * @param whiteleft dostęp białych do długiej roszady
     * @param whiteright dostęp białych do krótkiej roszady
     * @param kingrochB dostęp czarnych do roszady
     * @param kingrochC dostęp białych do roszady
     * @param sposob sposób sortowania
     * @param kolumna kolumna z dostępnym biciem w przelocie
     * @return bieżąca lista posunięć w danej pozycji
     */
    static Collection<Ruch1> generuj_posuniecia(SI_MIN_MAX_Alfa_Beta1.figury[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int sposob, byte kolumna) {
        List<Ruch1> lista_dopuszcalnych_Ruchów = new ArrayList<>();
        SI_MIN_MAX_Alfa_Beta1.figury[][] backup = ust.clone(); 
      
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean szach;
                boolean wynik;
                szach = false;
                SI_MIN_MAX_Alfa_Beta1.figury znak = backup[x][y];
                
                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case BPion:
                        if (tura_rywala != false) {
                            if (x == 4 && przelotcan == true && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.BPion;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                if (obecnosc(backup) == true) {
                                    wynik = true;
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x + 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.CPion;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.BPion;
                                if (wynik == true) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));
                                    if (szach == false) {
                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.CPion));
                                    } else {
                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.CPion));
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.CPion)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))) {
                                        SI_MIN_MAX_Alfa_Beta1.figury przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), sposob, przechowalnie));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), sposob, przechowalnie));
                                                }
                                            }
                                        } else {
                                            SI_MIN_MAX_Alfa_Beta1.figury[] symbole = {SI_MIN_MAX_Alfa_Beta1.figury.BHetman, SI_MIN_MAX_Alfa_Beta1.figury.BWieza, SI_MIN_MAX_Alfa_Beta1.figury.BGoniec, SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x + 1][y + b] = symbole[s];
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + 1][y + b] = przechowalnie;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), sposob, przechowalnie));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), sposob, przechowalnie));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol)) {
                                    SI_MIN_MAX_Alfa_Beta1.figury przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {
                                        backup[x + 1][y] = znak;
                                        backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (obecnosc(backup) == true) {
                                            wynik = true;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x + 1][y] = przechowalnie;
                                        if (wynik == true) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                            if (szach == true) {
                                                lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                            } else {
                                                lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                            }
                                        }
                                    } else {
                                        SI_MIN_MAX_Alfa_Beta1.figury[] symbole = {SI_MIN_MAX_Alfa_Beta1.figury.BHetman, SI_MIN_MAX_Alfa_Beta1.figury.BWieza, SI_MIN_MAX_Alfa_Beta1.figury.BGoniec, SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x + 1][y] = symbole[s];
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y] = przechowalnie;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol))
                                        && (backup[x + 1][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol)) && x == 1) {
                                    SI_MIN_MAX_Alfa_Beta1.figury przechowalnie = backup[x + 2][y];
                                    backup[x + 2][y] = znak;
                                    backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                    if (obecnosc(backup) == true) {
                                        wynik = true;
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x + 2][y] = przechowalnie;
                                    if (wynik == true) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CHetman:
                    case BHetman:

                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 == true || d2 == true || d3 == true || d4 == true
                                    || w1 == true || w2 == true || w3 == true || w4 == true) {
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1 == true) {

                                        if (d1 == true && backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2 == true) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 == true && backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3 == true) {

                                        if (d3 == true && backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4 == true) {

                                        if (d4 == true && backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x + param_ruch < 8 && w1 == true) {

                                        if (w1 == true && backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (x - param_ruch > -1 && w2 == true) {

                                        if (w2 == true && backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (y + param_ruch < 8 && w3 == true) {

                                        if (w3 == true && backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                    if (y - param_ruch > -1 && w4 == true) {

                                        if (w4 == true && backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BHetman && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CHetman && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {

                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BWieza:
                    case CWieza:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 == true || w2 == true || w3 == true || w4 == true) {
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    if (x + param_ruch < 8 && w1 == true) {

                                        if (w1 == true && backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    if (x - param_ruch > -1 && w2 == true) {

                                        if (w2 == true && backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    if (y + param_ruch < 8 && w3 == true) {

                                        if (w3 == true && backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    if (y - param_ruch > -1 && w4 == true) {

                                        if (w4 == true && backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BWieza && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CWieza && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {

                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BGoniec:
                    case CGoniec:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 == true || d2 == true || d3 == true || d4 == true) {
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1 == true) {

                                        if (d1 == true && backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2 == true) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 == true && backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3 == true) {

                                        if (d3 == true && backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala == true && znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4 == true) {

                                        if (d4 == true && backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case CPion:
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan == true && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.CPion;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                if (obecnosc(backup) == true) {
                                    wynik = true;
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x - 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta1.figury.BPion;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.CPion;
                                if (wynik == true) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));
                                    if (szach == false) {
                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.BPion));
                                    } else {
                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.BPion));
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.BPion)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman))) {
                                        SI_MIN_MAX_Alfa_Beta1.figury przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {
                                            backup[x - 1][y + b] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y + b] = przechowalnie;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), sposob, przechowalnie));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), sposob, przechowalnie));
                                                }
                                            }
                                        } else {
                                            SI_MIN_MAX_Alfa_Beta1.figury[] symbole = {SI_MIN_MAX_Alfa_Beta1.figury.CHetman, SI_MIN_MAX_Alfa_Beta1.figury.CWieza, SI_MIN_MAX_Alfa_Beta1.figury.CGoniec, SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x - 1][y + b] = symbole[s];
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x - 1][y + b] = przechowalnie;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                    if (szach == true) {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), sposob, przechowalnie));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), sposob, przechowalnie));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol)) {
                                    SI_MIN_MAX_Alfa_Beta1.figury przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (obecnosc(backup) == true) {
                                            wynik = true;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;
                                        if (wynik == true) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                            if (szach == true) {
                                                lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                            } else {
                                                lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                            }
                                        }
                                    } else {
                                        SI_MIN_MAX_Alfa_Beta1.figury[] symbole = {SI_MIN_MAX_Alfa_Beta1.figury.CHetman, SI_MIN_MAX_Alfa_Beta1.figury.CWieza, SI_MIN_MAX_Alfa_Beta1.figury.CGoniec, SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                            if (obecnosc(backup) == true) {
                                                wynik = true;
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;
                                            if (wynik == true) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                                if (szach == true) {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                } else {
                                                    lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol))
                                        && (backup[x - 1][y] == SI_MIN_MAX_Alfa_Beta1.figury.pustka || (backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CPion && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol)) && x == 6) {
                                    SI_MIN_MAX_Alfa_Beta1.figury przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                    if (obecnosc(backup) == true) {
                                        wynik = true;
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    if (wynik == true) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CSkoczek:
                    case BSkoczek:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek)) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek && 
                                                    ((backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BPion
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman)
                                                    || (backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CPion
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CGoniec
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CHetman
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol 
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek && 
                                                    ((backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CPion
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)
                                                    || (backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BPion 
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BGoniec 
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BWieza
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BHetman 
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.BKrol 
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta1.figury.CKrol)))) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;

                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przechowalnia == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        }
                                                    } else {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przechowalnia));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przechowalnia));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case BKrol:
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.pustka
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CPion || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CSkoczek || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CGoniec || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.CHetman)) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        }
                                                    } else {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przech));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przech));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (kingrochB == true) {
                                if (whiteright == true && (backup[0][5] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[0][6] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[0][4] == SI_MIN_MAX_Alfa_Beta1.figury.BKrol)
                                        && (backup[0][7] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza)) {
                                    boolean[] wyniki = new boolean[2];
                                    for (int r = 1; r < 3; r++) {
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][4 + r] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        wyniki[r - 1] = true;
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        backup[0][4 + r] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                    }
                                    if (wyniki[0] == true && wyniki[1] == true) {
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][7] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][5] = SI_MIN_MAX_Alfa_Beta1.figury.BWieza;
                                        backup[0][6] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        backup[0][7] = SI_MIN_MAX_Alfa_Beta1.figury.BWieza;
                                        backup[0][5] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][6] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("KO-O+   "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("KO-O    "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                                if (whiteleft == true && (backup[0][3] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[0][2] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[0][1] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[0][4] == SI_MIN_MAX_Alfa_Beta1.figury.BKrol)
                                        && (backup[0][0] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza)) {
                                    boolean[] wyniki = new boolean[2];
                                    for (int r = 1; r <= 2; r++) {
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][4 - r] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        wyniki[r - 1] = true;
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        backup[0][4 - r] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                    }
                                    if (wyniki[0] == true && wyniki[1] == true) {
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][0] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][3] = SI_MIN_MAX_Alfa_Beta1.figury.BWieza;
                                        backup[0][2] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        backup[0][4] = SI_MIN_MAX_Alfa_Beta1.figury.BKrol;
                                        backup[0][0] = SI_MIN_MAX_Alfa_Beta1.figury.BWieza;
                                        backup[0][3] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[0][2] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("KO-O-O+  "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("KO-O-O   "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CKrol:
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.pustka
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BPion || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BSkoczek || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BGoniec || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BWieza || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta1.figury.BHetman)) {
                                                SI_MIN_MAX_Alfa_Beta1.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                                if (obecnosc(backup) == true) {
                                                    wynik = true;
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                                if (wynik == true) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SI_MIN_MAX_Alfa_Beta1.figury.pustka) {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                                        }
                                                    } else {
                                                        if (szach == true) {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), sposob, przech));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchów.add(new Ruch1((pozyskaj_symbol(znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), sposob, przech));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (kingrochC == true) {
                                if (blackright == true && (backup[7][5] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[7][6] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[7][4] == SI_MIN_MAX_Alfa_Beta1.figury.CKrol)
                                        && (backup[7][7] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    boolean[] wyniki = new boolean[2];
                                    for (int r = 1; r < 3; r++) {
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][4 + r] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        wyniki[r - 1] = true;
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        backup[7][4 + r] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                    }
                                    if (wyniki[0] == true && wyniki[1] == true) {
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][7] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][5] = SI_MIN_MAX_Alfa_Beta1.figury.CWieza;
                                        backup[7][6] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        backup[7][7] = SI_MIN_MAX_Alfa_Beta1.figury.CWieza;
                                        backup[7][5] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][6] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("kO-O+     "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("kO-O      "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                                if (blackleft == true && (backup[7][3] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[7][2] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[7][1] == SI_MIN_MAX_Alfa_Beta1.figury.pustka)
                                        && (backup[7][4] == SI_MIN_MAX_Alfa_Beta1.figury.CKrol)
                                        && (backup[7][0] == SI_MIN_MAX_Alfa_Beta1.figury.CWieza)) {
                                    boolean[] wyniki = new boolean[2];
                                    for (int r = 1; r <= 2; r++) {
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][4 - r] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        wyniki[r - 1] = true;
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        backup[7][4 - r] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                    }
                                    if (wyniki[0] == true && wyniki[1] == true) {
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][0] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][3] = SI_MIN_MAX_Alfa_Beta1.figury.CWieza;
                                        backup[7][2] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        backup[7][4] = SI_MIN_MAX_Alfa_Beta1.figury.CKrol;
                                        backup[7][0] = SI_MIN_MAX_Alfa_Beta1.figury.CWieza;
                                        backup[7][3] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        backup[7][2] = SI_MIN_MAX_Alfa_Beta1.figury.pustka;
                                        if (szach == true) {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("kO-O-O+  "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        } else {
                                            lista_dopuszcalnych_Ruchów.add(new Ruch1(("kO-O-O   "), sposob, SI_MIN_MAX_Alfa_Beta1.figury.pustka));
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

            }//System.out.println("");

        }

     try{   
    Collections.sort(lista_dopuszcalnych_Ruchów);
     }catch(Exception e){
         System.out.println(e);
     }
        return lista_dopuszcalnych_Ruchów;
    }

    private static char[][] konwert(SI_MIN_MAX_Alfa_Beta1.figury[][] backup) {
        char[][] pozycja = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (backup[i][j]) {
                    case pustka:
                        pozycja[i][j] = ' ';
                        break;
                    case BPion:
                        pozycja[i][j] = 'P';
                        break;
                    case CPion:
                        pozycja[i][j] = 'p';
                        break;
                    case BSkoczek:
                        pozycja[i][j] = 'N';
                        break;
                    case CSkoczek:
                        pozycja[i][j] = 'n';
                        break;
                    case BGoniec:
                        pozycja[i][j] = 'B';
                        break;
                    case CGoniec:
                        pozycja[i][j] = 'b';
                        break;
                    case BWieza:
                        pozycja[i][j] = 'R';
                        break;
                    case CWieza:
                        pozycja[i][j] = 'r';
                        break;
                    case BHetman:
                        pozycja[i][j] = 'Q';
                        break;
                    case CHetman:
                        pozycja[i][j] = 'q';
                        break;
                    case BKrol:
                        pozycja[i][j] = 'K';
                        break;
                    case CKrol:
                        pozycja[i][j] = 'k';
                        break;
                }
            }
        }
        return pozycja;
    }

    private static char pozyskaj_symbol(SI_MIN_MAX_Alfa_Beta1.figury znak) {
        switch (znak) {
            case pustka:
                return ' ';
            case BPion:
                return 'P';
            case CPion:
                return 'p';
            case BSkoczek:
                return 'N';
            case CSkoczek:
                return 'n';
            case BGoniec:
                return 'B';
            case CGoniec:
                return 'b';
            case BWieza:
                return 'R';
            case CWieza:
                return 'r';
            case BHetman:
                return 'Q';
            case CHetman:
                return 'q';
            case BKrol:
                return 'K';
            case CKrol:
                return 'k';
        }
        return ' ';
    }

}
