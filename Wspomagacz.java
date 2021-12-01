/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

/**
 *
 * @author Patryk
 */
public class Wspomagacz {

    public static int[] znajdzmozliwosc(char[][] ustawienie, boolean czybiale, int[] poszukiwanie, 
            boolean przelotcan) {
        char[][] pomocnicza = new char[14][14];
        for (byte i = 0; i < 8; i++) {
            System.arraycopy(ustawienie[i], 0, pomocnicza[i + 3], 3, 8);
        }
        int pomoc;
        char figura;
        boolean akceptacja;
        int[] zaslona = new int[2];
        zaslona[0] = -1;
        zaslona[1] = -1;
        byte X = (byte) (poszukiwanie[0] + 3), Y = (byte) (poszukiwanie[1] + 3);
        if ((!czybiale && ((pomocnicza[X + 2][Y - 1] == 'n')
                || (pomocnicza[X + 2][Y + 1] == 'n')
                || (pomocnicza[X - 2][Y - 1] == 'n')
                || (pomocnicza[X - 2][Y + 1] == 'n')
                || (pomocnicza[X + 1][Y + 2] == 'n')
                || (pomocnicza[X + 1][Y - 2] == 'n')
                || (pomocnicza[X - 1][Y + 2] == 'n')
                || (pomocnicza[X - 1][Y - 2] == 'n')))
                || (czybiale && ((pomocnicza[X + 2][Y - 1] == 'N')
                || (pomocnicza[X + 2][Y + 1] == 'N')
                || (pomocnicza[X - 2][Y - 1] == 'N')
                || (pomocnicza[X - 2][Y + 1] == 'N')
                || (pomocnicza[X + 1][Y + 2] == 'N')
                || (pomocnicza[X + 1][Y - 2] == 'N')
                || (pomocnicza[X - 1][Y + 2] == 'N')
                || (pomocnicza[X - 1][Y - 2] == 'N')))) {
            //System.out.prbyteln("skoczek znaleziony");
            for (byte i = -2; i < 3; i++) {
                for (byte j = -2; j < 3; j++) {
                    if ((Math.abs(j) != Math.abs(i)) && (i != 0 && j != 0)) {
                        if ((!czybiale && pomocnicza[X + i][Y + j] == 'n') || (czybiale && pomocnicza[X + i][Y + j] == 'N')) {
                            zaslona[0] = (byte) (X + i - 3);
                            zaslona[1] = (byte) (Y + j - 3);
                            //System.out.prbyteln(ustawienie[zaslona[0]][zaslona[1]]);
                            if (czybiale) {
                                figura = 'N';
                            } else {
                                figura = 'n';
                            }
                            ustawienie[zaslona[0]][zaslona[1]] = ' ';
                            ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                            for (byte k = 0; k < 8; k++) {
                                for (byte l = 0; l < 8; l++) {
                                    //System.out.prbyte("'" + ustawienie[k][l] + "'");
                                }
                                //System.out.prbyteln();
                            }
                            akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                            ustawienie[zaslona[0]][zaslona[1]] = figura;
                            ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                            //System.out.prbyteln("skoczek: " + akceptacja);
                            //System.out.prbyteln(akceptacja);
                            if (akceptacja) {
                                return zaslona;
                            }
                        }
                    }
                }
            }
        }
        if ((czybiale && (pomocnicza[X - 1][Y] == 'P' || (pomocnicza[X - 2][Y] == 'P' && (X - 2) == 4)))
                || (!czybiale && (pomocnicza[X + 1][Y] == 'p' || (pomocnicza[X + 2][Y] == 'p' && (X + 2) == 9)))) {
            if (czybiale) {
                figura = 'P';
                if (pomocnicza[X - 1][Y] == 'P') {
                    zaslona[0] = (byte) (X - 1 - 3);
                    zaslona[1] = (byte) (Y - 3);
                } else {
                    zaslona[0] = (byte) (X - 2 - 3);
                    zaslona[1] = (byte) (Y - 3);
                }
            } else {
                figura = 'p';
                if (pomocnicza[X + 1][Y] == 'p') {
                    zaslona[0] = (byte) (X + 1 - 3);
                    zaslona[1] = (byte) (Y - 3);
                } else {
                    zaslona[0] = (byte) (X + 2 - 3);
                    zaslona[1] = (byte) (Y - 3);
                }
            }
            pomoc = zaslona[0];
            zaslona[0] = zaslona[1];
            zaslona[1] = (byte) pomoc;
            ustawienie[zaslona[1]][zaslona[0]] = ' ';
            ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
            akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
            ustawienie[zaslona[1]][zaslona[0]] = figura;
            ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
            //System.out.prbyteln(akceptacja);
            if (akceptacja) {
                return zaslona;
            }
        }
        byte alfa, beta, licz;
        alfa = (byte) (poszukiwanie[0] + 3);
        beta = (byte) (poszukiwanie[1] + 3);
        //System.out.prbyteln("sprawdzane: " + alfa + "," + beta);
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 13; i++) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 13) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((!czybiale && (pomocnicza[i][beta + licz] != 'q' && pomocnicza[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicza[i][beta + licz] != 'Q' && pomocnicza[i][beta + licz] != 'B'))) {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    } else {
                        if (czybiale) {
                            if (pomocnicza[i][beta + licz] == 'Q') {
                                figura = 'Q';
                            } else {
                                figura = 'B';
                            }
                        } else {
                            if (pomocnicza[i][beta + licz] == 'q') {
                                figura = 'q';
                            } else {
                                figura = 'b';
                            }
                        }
                        zaslona[1] = (byte) (beta + licz - 3);
                        zaslona[0] = (byte) (i - 3);

                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                        ustawienie[zaslona[0]][zaslona[1]] = ' ';
                        //System.out.prbyteln("1dfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                        //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                        //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                        //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                        akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                        ustawienie[zaslona[0]][zaslona[1]] = figura;
                        if (akceptacja) {
                            return zaslona;
                        } else {
                            zaslona[0] = -1;
                            zaslona[1] = -1;
                        }
                    }
                    break;
                }
            }
        }
        alfa = (byte) (poszukiwanie[0] + 3);
        beta = (byte) (poszukiwanie[1] + 3);
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 13; i++) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 2) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((!czybiale && (pomocnicza[i][beta + licz] != 'q' && pomocnicza[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicza[i][beta + licz] != 'Q' && pomocnicza[i][beta + licz] != 'B'))) {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    } else {
                        if (czybiale) {
                            if (pomocnicza[i][beta + licz] == 'Q') {
                                figura = 'Q';
                            } else {
                                figura = 'B';
                            }
                        } else {
                            if (pomocnicza[i][beta + licz] == 'q') {
                                figura = 'q';
                            } else {
                                figura = 'b';
                            }
                        }
                        zaslona[1] = (byte) (beta + licz - 3);
                        zaslona[0] = (byte) (i - 3);

                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                        ustawienie[zaslona[0]][zaslona[1]] = ' ';
                        //System.out.prbyteln("2dfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                        //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                        //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                        //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                        akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                        ustawienie[zaslona[0]][zaslona[1]] = figura;
                        if (akceptacja) {
                            return zaslona;
                        } else {
                            zaslona[0] = -1;
                            zaslona[1] = -1;
                        }
                    }
                    break;
                }
            }
        }
        alfa = (byte) (poszukiwanie[0] + 3);
        beta = (byte) (poszukiwanie[1] + 3);
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 3; i--) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 2) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if ((!czybiale && (pomocnicza[i][beta + licz] != 'q' && pomocnicza[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicza[i][beta + licz] != 'Q' && pomocnicza[i][beta + licz] != 'B'))) {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    } else {
                        if (czybiale) {
                            if (pomocnicza[i][beta + licz] == 'Q') {
                                figura = 'Q';
                            } else {
                                figura = 'B';
                            }
                        } else {
                            if (pomocnicza[i][beta + licz] == 'q') {
                                figura = 'q';
                            } else {
                                figura = 'b';
                            }
                        }
                        zaslona[1] = (byte) (beta + licz - 3);
                        zaslona[0] = (byte) (i - 3);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                        ustawienie[zaslona[0]][zaslona[1]] = ' ';
                        //System.out.prbyteln("3dfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                        //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                        //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                        //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                        akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                        ustawienie[zaslona[0]][zaslona[1]] = figura;
                        if (akceptacja) {

                            return zaslona;
                        } else {
                            zaslona[0] = -1;
                            zaslona[1] = -1;
                        }
                    }
                    break;
                }
            }
        }
        alfa = (byte) (poszukiwanie[0] + 3);
        beta = (byte) (poszukiwanie[1] + 3);
        licz = 0;

        for (byte i = (byte) (alfa - 1); i >= 3; i--) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 13) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if ((!czybiale && (pomocnicza[i][beta + licz] != 'q' && pomocnicza[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicza[i][beta + licz] != 'Q' && pomocnicza[i][beta + licz] != 'B'))) {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    } else {
                        if (czybiale) {
                            if (pomocnicza[i][beta + licz] == 'Q') {
                                figura = 'Q';
                            } else {
                                figura = 'B';
                            }
                        } else {
                            if (pomocnicza[i][beta + licz] == 'q') {
                                figura = 'q';
                            } else {
                                figura = 'b';
                            }
                        }
                        zaslona[1] = (byte) (beta + licz - 3);
                        zaslona[0] = (byte) (i - 3);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                        ustawienie[zaslona[0]][zaslona[1]] = ' ';
                        //System.out.prbyteln("4dfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                        //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                        //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                        //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                        akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                        ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                        ustawienie[zaslona[0]][zaslona[1]] = figura;
                        if (akceptacja) {

                            return zaslona;
                        } else {
                            zaslona[0] = -1;
                            zaslona[1] = -1;
                        }
                    }
                    break;
                }
            }
        }

        for (byte i = (byte) (alfa + 1); i <= 10; i++) {
            if (pomocnicza[i][beta] == ' ') {
                zaslona[0] = -1;
                zaslona[1] = -1;
            } else {
                if ((!czybiale && (pomocnicza[i][beta] != 'q' && pomocnicza[i][beta] != 'r'))
                        || (czybiale && (pomocnicza[i][beta] != 'Q' && pomocnicza[i][beta] != 'R'))) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if (czybiale) {
                        if (pomocnicza[i][beta] == 'Q') {
                            figura = 'Q';
                        } else {
                            figura = 'R';
                        }
                    } else {
                        if (pomocnicza[i][beta] == 'q') {
                            figura = 'q';
                        } else {
                            figura = 'r';
                        }
                    }
                    zaslona[1] = (byte) (Y - 3);
                    zaslona[0] = (byte) (i - 3);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                    ustawienie[zaslona[0]][zaslona[1]] = ' ';
                    //System.out.prbyteln("1lfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                    //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                    //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                    //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                    akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                    ustawienie[zaslona[0]][zaslona[1]] = figura;
                    if (akceptacja) {

                        return zaslona;
                    } else {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    }
                }
                break;
            }
        }

        for (byte i = (byte) (alfa - 1); i >= 3; i--) {
            if (pomocnicza[i][beta] == ' ') {
                zaslona[0] = -1;
                zaslona[1] = -1;
            } else {
                if ((!czybiale && (pomocnicza[i][beta] != 'q' && pomocnicza[i][beta] != 'r'))
                        || (czybiale && (pomocnicza[i][beta] != 'Q' && pomocnicza[i][beta] != 'R'))) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if (czybiale) {
                        if (pomocnicza[i][beta] == 'Q') {
                            figura = 'Q';
                        } else {
                            figura = 'R';
                        }
                    } else {
                        if (pomocnicza[i][beta] == 'q') {
                            figura = 'q';
                        } else {
                            figura = 'r';
                        }
                    }
                    zaslona[1] = (byte) (Y - 3);
                    zaslona[0] = (byte) (i - 3);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                    ustawienie[zaslona[0]][zaslona[1]] = ' ';
                    //System.out.prbyteln("2lfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                    //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                    //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                    //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                    akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                    ustawienie[zaslona[0]][zaslona[1]] = figura;
                    if (akceptacja) {

                        return zaslona;
                    } else {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    }
                }
                break;
            }
        }

        for (byte i = (byte) (Y + 1); i <= 10; i++) {
            if (pomocnicza[alfa][i] == ' ') {
                zaslona[0] = -1;
                zaslona[1] = -1;
            } else {
                if ((!czybiale && (pomocnicza[alfa][i] != 'q' && pomocnicza[alfa][i] != 'r'))
                        || (czybiale && (pomocnicza[alfa][i] != 'Q' && pomocnicza[alfa][i] != 'R'))) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if (czybiale) {
                        if (pomocnicza[alfa][i] == 'Q') {
                            figura = 'Q';
                        } else {
                            figura = 'R';
                        }
                    } else {
                        if (pomocnicza[alfa][i] == 'q') {
                            figura = 'q';
                        } else {
                            figura = 'r';
                        }
                    }
                    zaslona[1] = (byte) (i - 3);
                    zaslona[0] = (byte) (alfa - 3);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                    ustawienie[zaslona[0]][zaslona[1]] = ' ';
                    //System.out.prbyteln("3lfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                    //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                    //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                    //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));

                    akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                    ustawienie[zaslona[0]][zaslona[1]] = figura;
                    if (akceptacja) {

                        return zaslona;
                    } else {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    }
                }
                break;
            }
        }

        for (byte i = (byte) (beta - 1); i >= 3; i--) {
            if (pomocnicza[alfa][i] == ' ') {
                zaslona[0] = -1;
                zaslona[1] = -1;
            } else {
                if ((!czybiale && (pomocnicza[alfa][i] != 'q' && pomocnicza[alfa][i] != 'r'))
                        || (czybiale && (pomocnicza[alfa][i] != 'Q' && pomocnicza[alfa][i] != 'R'))) {
                    zaslona[0] = -1;
                    zaslona[1] = -1;
                } else {
                    if (czybiale) {
                        if (pomocnicza[alfa][i] == 'Q') {
                            figura = 'Q';
                        } else {
                            figura = 'R';
                        }
                    } else {
                        if (pomocnicza[alfa][i] == 'q') {
                            figura = 'q';
                        } else {
                            figura = 'r';
                        }
                    }
                    zaslona[1] = (byte) (i - 3);
                    zaslona[0] = (byte) (alfa - 3);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = figura;
                    ustawienie[zaslona[0]][zaslona[1]] = ' ';
                    //System.out.prbyteln("4lfunkcjaZASTAW:" + poza_odsieczy[zaslona[0]][zaslona[1]]);
                    //System.out.prbyteln("kordy odsiecz x:" + (zaslona[1]) + " y:" + (zaslona[0]));
                    //System.out.prbyteln("bij cel:" + poza_odsieczy[poszukiwanie[0]][poszukiwanie[1]]);
                    //System.out.prbyteln("kordy cel x:" + (poszukiwanie[1]) + " y:" + (poszukiwanie[0]));
                    akceptacja = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                    ustawienie[poszukiwanie[0]][poszukiwanie[1]] = ' ';
                    ustawienie[zaslona[0]][zaslona[1]] = figura;
                    if (akceptacja) {

                        return zaslona;
                    } else {
                        zaslona[0] = -1;
                        zaslona[1] = -1;
                    }
                }
                break;
            }
        }
        return zaslona;
    }

    public static int[] znajdzklopot(char[][] ust, boolean czybiale) {
        int[] klopocik = new int[2];
        byte krolX = 0, krolY = 0, alfa, beta;
        char[][] pomocnicza = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                pomocnicza[i + 4][j + 4] = ust[i][j];
                if ((czybiale && pomocnicza[i + 4][j + 4] == 'K') ||
                        (!czybiale && pomocnicza[i + 4][j + 4] == 'k')) {
                    krolX = (byte) (i + 4);
                    krolY = (byte) (j + 4);
                }
            }
        }
        //System.out.prbyteln(krolY + " " + krolX);
        if ((czybiale && ((pomocnicza[krolX + 2][krolY - 1] == 'n')
                || (pomocnicza[krolX + 2][krolY + 1] == 'n')
                || (pomocnicza[krolX - 2][krolY - 1] == 'n')
                || (pomocnicza[krolX - 2][krolY + 1] == 'n')
                || (pomocnicza[krolX + 1][krolY + 2] == 'n')
                || (pomocnicza[krolX + 1][krolY - 2] == 'n')
                || (pomocnicza[krolX - 1][krolY + 2] == 'n')
                || (pomocnicza[krolX - 1][krolY - 2] == 'n')))
                || (!czybiale && ((pomocnicza[krolX + 2][krolY - 1] == 'N')
                || (pomocnicza[krolX + 2][krolY + 1] == 'N')
                || (pomocnicza[krolX - 2][krolY - 1] == 'N')
                || (pomocnicza[krolX - 2][krolY + 1] == 'N')
                || (pomocnicza[krolX + 1][krolY + 2] == 'N')
                || (pomocnicza[krolX + 1][krolY - 2] == 'N')
                || (pomocnicza[krolX - 1][krolY + 2] == 'N')
                || (pomocnicza[krolX - 1][krolY - 2] == 'N')))) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                        if ((czybiale && pomocnicza[krolX + i][krolY + j] == 'n') || (!czybiale && pomocnicza[krolX + i][krolY + j] == 'N')) {
                            klopocik[1] = (byte) (krolX + i - 4);
                            klopocik[0] = (byte) (krolY + j - 4);
                            return klopocik;
                        }
                    }
                }
            }

        }
        if ((czybiale && ((pomocnicza[krolX + 1][krolY + 1] == 'p') || (pomocnicza[krolX + 1][krolY - 1] == 'p')))
                || (!czybiale && ((pomocnicza[krolX - 1][krolY + 1] == 'P') || (pomocnicza[krolX - 1][krolY - 1] == 'P')))) {
            if (czybiale) {
                if (pomocnicza[krolX + 1][krolY - 1] == 'p') {
                    klopocik[1] = (byte) (krolX + 1 - 4);
                    klopocik[0] = (byte) (krolY - 1 - 4);
                } else {
                    klopocik[1] = (byte) (krolX + 1 - 4);
                    klopocik[0] = (byte) (krolY + 1 - 4);
                }
            } else {
                if (pomocnicza[krolX - 1][krolY + 1] == 'P') {
                    klopocik[1] = (byte) (krolX - 1 - 4);
                    klopocik[0] = (byte) (krolY + 1 - 4);
                } else {
                    klopocik[1] = (byte) (krolX - 1 - 4);
                    klopocik[0] = (byte) (krolY - 1 - 4);
                }
            }
            return klopocik;
        }
        alfa = krolX;
        beta = krolY;
        byte licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicza[i][beta + licz] == 'q' || pomocnicza[i][beta + licz] == 'b'))
                            || (!czybiale && (pomocnicza[i][beta + licz] == 'Q' || pomocnicza[i][beta + licz] == 'B'))) {
                        klopocik[1] = (byte) (i - 4);
                        klopocik[0] = (byte) (beta + licz - 4);
                        return klopocik;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicza[i][beta + licz] == 'q' || pomocnicza[i][beta + licz] == 'b'))
                            || (!czybiale && (pomocnicza[i][beta + licz] == 'Q' || pomocnicza[i][beta + licz] == 'B'))) {
                        klopocik[1] = (byte) (i - 4);
                        klopocik[0] = (byte) (beta + licz - 4);
                        return klopocik;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicza[i][beta + licz] == 'q' || pomocnicza[i][beta + licz] == 'b'))
                            || (!czybiale && (pomocnicza[i][beta + licz] == 'Q' || pomocnicza[i][beta + licz] == 'B'))) {
                        klopocik[1] = (byte) (i - 4);
                        klopocik[0] = (byte) (beta + licz - 4);
                        return klopocik;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicza[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicza[i][beta + licz] == 'q' || pomocnicza[i][beta + licz] == 'b'))
                            || (!czybiale && (pomocnicza[i][beta + licz] == 'Q' || pomocnicza[i][beta + licz] == 'B'))) {
                        klopocik[1] = (byte) (i - 4);
                        klopocik[0] = (byte) (beta + licz - 4);
                        return klopocik;
                    }
                    break;
                }
            }
        }
        alfa = krolX;
        beta = krolY;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            if (pomocnicza[i][beta] == ' ') {
            } else {
                if ((czybiale && (pomocnicza[i][beta] == 'q' || pomocnicza[i][beta] == 'r'))
                        || (!czybiale && (pomocnicza[i][beta] == 'Q' || pomocnicza[i][beta] == 'R'))) {
                    klopocik[1] = (byte) (i - 4);
                    klopocik[0] = (byte) (beta - 4);
                    return klopocik;
                }
                break;
            }
        }
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            if (pomocnicza[i][beta] == ' ') {
            } else {
                if ((czybiale && (pomocnicza[i][beta] == 'q' || pomocnicza[i][beta] == 'r'))
                        || (!czybiale && (pomocnicza[i][beta] == 'Q' || pomocnicza[i][beta] == 'R'))) {
                    klopocik[1] = (byte) (i - 4);
                    klopocik[0] = (byte) (beta - 4);
                    return klopocik;
                }
                break;
            }
        }
        for (byte i = (byte) (beta + 1); i <= 11; i++) {
            if (pomocnicza[alfa][i] == ' ') {
            } else {
                if ((czybiale && (pomocnicza[alfa][i] == 'q' || pomocnicza[alfa][i] == 'r'))
                        || (!czybiale && (pomocnicza[alfa][i] == 'Q' || pomocnicza[alfa][i] == 'R'))) {
                    klopocik[1] = (byte) (alfa - 4);
                    klopocik[0] = (byte) (i - 4);
                    return klopocik;
                }
                break;
            }
        }
        for (byte i = (byte) (beta - 1); i >= 4; i--) {
            if (pomocnicza[alfa][i] == ' ') {
            } else {
                if ((czybiale && (pomocnicza[alfa][i] == 'q' || pomocnicza[alfa][i] == 'r'))
                        || (!czybiale && (pomocnicza[alfa][i] == 'Q' || pomocnicza[alfa][i] == 'R'))) {
                    klopocik[1] = (byte) (alfa - 4);
                    klopocik[0] = (byte) (i - 4);
                    return klopocik;
                }
                break;
            }
        }
        return klopocik;
    }
}
