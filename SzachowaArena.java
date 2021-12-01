/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.BufferedReader;
import javax.swing.border.LineBorder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Timer;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.util.TimerTask;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.DatagramSocket;
//import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
//import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.net.URLConnection;
//import java.net.UnknownHostException;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.nio.Buffer;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
//import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/*import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.support.igd.PortMappingListener;
import org.teleal.cling.support.model.PortMapping;*/
/**
 *
 * @author Patryk Główna klasa programu
 */
public class SzachowaArena extends javax.swing.JFrame {

    boolean wlasnykolor = false;
    Thread scanip = null;
    InetAddress[] wybory1;
    String[] choices;
    String[] choices2;
    boolean searching = false;
    Color rama = Color.GREEN, pomoc_ruch = Color.BLUE;
    ArrayList<String> hug_list = new ArrayList<>();
    public char[][][] szachownica_pokoj = new char[8][8][2];
    public Condition warunek;// gra na czas
    byte sztuczny_rywal; // do SI
    public Lock blokada; // gra na czas
    boolean SI_wyk = false; // do SI
    public Thread whitetime, blacktime; //gra na czas
    public static ServerSocket server; // gra online
    public boolean odwrot = false; // gra z SI i online
    zegar czasB; // gra na czas
    zegar czasC; // gra na czas
    int proby_laczenia = 0;
    public int plustime = 0, sekbaza; // gra na czas
    public boolean tura_rywala, SI_ON /*do SI*/, dokonano_RB = false, dokonano_RC = false;//baza
    boolean organizator, siec, oczekiwanie = false;// gra online
    public ArrayList<String> historia = new ArrayList<>();//baza
    public static ArrayList<String> listaip = new ArrayList<>();
    public static Socket socket;//gra online
    int[] poza_krolewska = new int[2]; // baza
    public int bonuss, sek, seksyg, czasgry; // gra na czas
    public byte kolor_zestaw, kroj_zestaw = 1, kolor_plansza = 1; // wyglad
    public int[] lokalS = new int[2]; // baza
    public int[] lokalK = new int[2];// baza
    public int[] klopoty = new int[2];// baza
    public int[] pole_baza = new int[2];// baza
    public boolean ruchB = true, koniecanimacji;// baza
    public String msgwe = "", msgwy = "", ruch = "";// gra online
    public boolean polestart, prze, gra, dokonanoEP, kon;// baza
    public boolean roch, wyk, przelot, przelotcan, zmien; // baza
    private Icon Cursor;//baza
    public char promo, pomoci1, symbol, pomoci2, znak_promocji = ' ';//baza
    public String pomocs, pomoce, koncowe, czarne, biale, start, stop;//baza
    public String pomoci, kap, nazwapola;//baza
    public Icon pomoc5;
    public byte zasada50, lekkieB, lekkieC, ciezkieB, ciezkieC, pionB, pionC;//baza
    char[][] kontrolka = new char[8][8];//baza
    char[][] pozycja_ustawki = new char[8][8];//baza
    char[][] ust = new char[8][8];//baza
    char[][] odwrotna = new char[8][8];//baza
    public char[][] kontrolamat = new char[8][8];//baza
    public boolean krolS, kingrochB, kingrochC, bleft, bright, wleft, wright, atak;//baza
    int krole_czarne, krole_biale;//baza
    public int kol;//baza
    int movenr, wybor, kolumna = 0, pole = 0;//baza
    public boolean wzor = false, ustawka = false;//baza
    public boolean szachB, szachC, promocja, dolicz, bicie, pierwsza_kolej;//baza
    boolean hodu = true, hitme = true, protectme = true, move = true;//baza
    boolean checka, checkp, pakc;//baza
    long laczny_czas = 0;//baza
    public Icon c1, c2, c3, c4;//baza
    public Icon b1, b2, b3, b4;//baza
    String KB = "BIAŁE", KC = "CZARNE", KL = "LOSOWY KOLOR";//gra online
    static DataInputStream in;//gra online
    static DataOutputStream out;//gra online
    lacze polaczenie_net;//gra online
    public String ruszaj, zatrzymaj;//gra online
    byte glebiaSI/* do SI */, tryb;//do gra na czas
    Timer zegarek;
    public boolean mysle;
    char[] symbole = new char[2];
    int liczba_usciskow = 0;
    Icon[] przytul_figure = new Icon[35];
    animacja2 anim;
    InetAddress[] wybory;
    String ostatni_start = "", ostatni_stop = "";
    int opcje_pomoc = 0;

    /**
     * Creates new form NewJFrame
     */
    public SzachowaArena() {
        bleft = true;
        bright = true;
        wleft = true;
        wright = true;
        initComponents();
        zegarek = new Timer();
        int szer = Toolkit.getDefaultToolkit().getScreenSize().width;
        int wys = Toolkit.getDefaultToolkit().getScreenSize().height;
        int szerRamki = this.getSize().width;
        int wysRamki = this.getSize().height;
        this.setLocation((szer - szerRamki) / 2, 0);
        kolor_zestaw = 2;
        czasgry = -1;
        ImageIcon ikona = new ImageIcon(this.getClass().getResource("icona.png"));
        this.setIconImage(ikona.getImage());
        czarneRuch.setVisible(false);
        bialeRuch.setVisible(false);
        jRadioButton11.setVisible(false);
        ustawWP.setVisible(false);
        ustawBP.setVisible(false);
        ustawWN.setVisible(false);
        ustawBN.setVisible(false);
        ustawWB.setVisible(false);
        ustawBB.setVisible(false);
        ustawWR.setVisible(false);
        ustawBR.setVisible(false);
        ustawWQ.setVisible(false);
        ustawBQ.setVisible(false);
        ustawWK.setVisible(false);
        ustawBK.setVisible(false);
        jButton72.setVisible(false);
        jButton81.setVisible(false);
        jTextArea2.setVisible(false);
        jTextField1.setVisible(false);
        jButton72.setVisible(false);
        b1 = new ImageIcon(this.getClass().getResource("Wqueen01.png"));
        b2 = new ImageIcon(this.getClass().getResource("Wrook001.png"));
        b3 = new ImageIcon(this.getClass().getResource("Wbishop1.png"));
        b4 = new ImageIcon(this.getClass().getResource("Wknight1.png"));
        c1 = new ImageIcon(this.getClass().getResource("Bqueen01.png"));
        c2 = new ImageIcon(this.getClass().getResource("Brook001.png"));
        c3 = new ImageIcon(this.getClass().getResource("Bbishop1.png"));
        c4 = new ImageIcon(this.getClass().getResource("Bknight1.png"));
        movenr = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (i) {
                    case 0:
                        switch (j) {
                            case 0:
                            case 7:
                                ust[i][j] = 'R';
                                odwrotna[i][j] = 'r';
                                break;
                            case 1:
                            case 6:
                                ust[i][j] = 'N';
                                odwrotna[i][j] = 'n';
                                break;
                            case 5:
                            case 2:
                                ust[i][j] = 'B';
                                odwrotna[i][j] = 'b';
                                break;
                            case 3:
                                ust[i][j] = 'Q';
                                odwrotna[i][j] = 'k';
                                break;
                            case 4:
                                ust[i][j] = 'K';
                                odwrotna[i][j] = 'q';
                                break;
                        }
                        break;
                    case 1:
                        ust[i][j] = 'P';
                        odwrotna[i][j] = 'p';
                        break;
                    case 7:
                        switch (j) {
                            case 0:
                            case 7:
                                ust[i][j] = 'r';
                                odwrotna[i][j] = 'R';
                                break;
                            case 1:
                            case 6:
                                ust[i][j] = 'n';
                                odwrotna[i][j] = 'N';
                                break;
                            case 5:
                            case 2:
                                ust[i][j] = 'b';
                                odwrotna[i][j] = 'B';
                                break;
                            case 3:
                                ust[i][j] = 'q';
                                odwrotna[i][j] = 'K';
                                break;
                            case 4:
                                ust[i][j] = 'k';
                                odwrotna[i][j] = 'Q';
                                break;
                        }
                        break;
                    case 6:
                        ust[i][j] = 'p';
                        odwrotna[i][j] = 'P';
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        ust[i][j] = ' ';
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                switch (i) {
                    case 0:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPQ.png"));
                                break;
                            case 5:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugPK.png"));
                                break;
                        }
                        break;
                    case 1:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNQ.png"));
                                break;
                            case 5:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugNK.png"));
                                break;
                        }
                        break;
                    case 2:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBQ.png"));
                                break;
                            case 5:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugBK.png"));
                                break;
                        }
                        break;
                    case 3:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRQ.png"));
                                break;
                            case 5:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugRK.png"));
                                break;
                        }
                        break;
                    case 4:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQQ.png"));
                                break;
                            case 5:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugQK.png"));
                                break;
                        }
                        break;
                    case 5:
                        switch (j) {
                            case 0:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugKP.png"));
                                break;
                            case 1:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugKN.png"));
                                break;
                            case 2:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugKB.png"));
                                break;
                            case 3:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugKR.png"));
                                break;
                            case 4:
                                przytul_figure[i * 6 + j] = new ImageIcon(this.getClass().getResource("hugKQ.png"));
                                break;
                            case 5:
                                break;
                        }
                        break;
                }
            }
        }
        kingrochB = true;
        kingrochC = true;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }

    /*public int getKol() {
        return kol;
    }/*

    /**
     * zmienia wygląd planszy i figur
     *
     * @param kolor_zestaw zmienia kolory figur
     * @param kroj_zestaw zmienia krój figur
     * @param kolor_plansza zmienia kolory planszy
     */
    public final void styl(int kolor_zestaw, int kroj_zestaw, int kolor_plansza) {
        String nazwabuttona;
        if (odwrot == true) {
            jLabel1.setText("H");
            jLabel2.setText("G");
            jLabel3.setText("F");
            jLabel4.setText("E");
            jLabel5.setText("D");
            jLabel6.setText("C");
            jLabel7.setText("B");
            jLabel8.setText("A");
            jLabel10.setText("8");
            jLabel14.setText("1");
            jLabel15.setText("2");
            jLabel16.setText("3");
            jLabel17.setText("4");
            jLabel18.setText("5");
            jLabel19.setText("6");
            jLabel11.setText("7");
        } else {
            jLabel1.setText("A");
            jLabel2.setText("B");
            jLabel3.setText("C");
            jLabel4.setText("D");
            jLabel5.setText("E");
            jLabel6.setText("F");
            jLabel7.setText("G");
            jLabel8.setText("H");
            jLabel10.setText("1");
            jLabel14.setText("8");
            jLabel15.setText("7");
            jLabel16.setText("6");
            jLabel17.setText("5");
            jLabel18.setText("4");
            jLabel19.setText("3");
            jLabel11.setText("2");
        }
        switch (kolor_zestaw) {
            case 1:
                switch (kroj_zestaw) {
                    case 1:
                        ustawWB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                        ustawWN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                        ustawWP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                        ustawWR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                        ustawWQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                        ustawWK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                        ustawBB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                        ustawBN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                        ustawBP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                        ustawBR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                        ustawBQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                        ustawBK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                        b1 = new ImageIcon(this.getClass().getResource("Wqueen01.png"));
                        b2 = new ImageIcon(this.getClass().getResource("Wrook001.png"));
                        b3 = new ImageIcon(this.getClass().getResource("Wbishop1.png"));
                        b4 = new ImageIcon(this.getClass().getResource("Wknight1.png"));
                        c1 = new ImageIcon(this.getClass().getResource("Bqueen01.png"));
                        c2 = new ImageIcon(this.getClass().getResource("Brook001.png"));
                        c3 = new ImageIcon(this.getClass().getResource("Bbishop1.png"));
                        c4 = new ImageIcon(this.getClass().getResource("Bknight1.png"));
                        break;
                    case 2:
                        ustawWB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wbishop4.png")));
                        ustawWN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wknight4.png")));
                        ustawWP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wpawn004.png")));
                        ustawWR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wrook004.png")));
                        ustawWQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wqueen04.png")));
                        ustawWK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wking004.png")));
                        ustawBB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bbishop4.png")));
                        ustawBN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bknight4.png")));
                        ustawBP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bpawn004.png")));
                        ustawBR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Brook004.png")));
                        ustawBQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bqueen04.png")));
                        ustawBK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bking004.png")));
                        b1 = new ImageIcon(this.getClass().getResource("Wqueen04.png"));
                        b2 = new ImageIcon(this.getClass().getResource("Wrook004.png"));
                        b3 = new ImageIcon(this.getClass().getResource("Wbishop4.png"));
                        b4 = new ImageIcon(this.getClass().getResource("Wknight4.png"));
                        c1 = new ImageIcon(this.getClass().getResource("Bqueen04.png"));
                        c2 = new ImageIcon(this.getClass().getResource("Brook004.png"));
                        c3 = new ImageIcon(this.getClass().getResource("Bbishop4.png"));
                        c4 = new ImageIcon(this.getClass().getResource("Bknight4.png"));
                        break;
                }
                break;
            case 2:
                switch (kroj_zestaw) {
                    case 1:
                        ustawWB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wbishop2.png")));
                        ustawWN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wknight2.png")));
                        ustawWP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wpawn002.png")));
                        ustawWR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wrook002.png")));
                        ustawWQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wqueen02.png")));
                        ustawWK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wking002.png")));
                        ustawBB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bbishop2.png")));
                        ustawBN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bknight2.png")));
                        ustawBP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bpawn002.png")));
                        ustawBR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Brook002.png")));
                        ustawBQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bqueen02.png")));
                        ustawBK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bking002.png")));
                        b1 = new ImageIcon(this.getClass().getResource("Wqueen02.png"));
                        b2 = new ImageIcon(this.getClass().getResource("Wrook002.png"));
                        b3 = new ImageIcon(this.getClass().getResource("Wbishop2.png"));
                        b4 = new ImageIcon(this.getClass().getResource("Wknight2.png"));
                        c1 = new ImageIcon(this.getClass().getResource("Bqueen02.png"));
                        c2 = new ImageIcon(this.getClass().getResource("Brook002.png"));
                        c3 = new ImageIcon(this.getClass().getResource("Bbishop2.png"));
                        c4 = new ImageIcon(this.getClass().getResource("Bknight2.png"));
                        break;
                    case 2:
                        ustawWB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wbishop3.png")));
                        ustawWN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wknight3.png")));
                        ustawWP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wpawn003.png")));
                        ustawWR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wrook003.png")));
                        ustawWQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wqueen03.png")));
                        ustawWK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Wking003.png")));
                        ustawBB.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bbishop3.png")));
                        ustawBN.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bknight3.png")));
                        ustawBP.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bpawn003.png")));
                        ustawBR.setSelectedIcon(new ImageIcon(this.getClass().getResource("Brook003.png")));
                        ustawBQ.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bqueen03.png")));
                        ustawBK.setSelectedIcon(new ImageIcon(this.getClass().getResource("Bking003.png")));
                        b1 = new ImageIcon(this.getClass().getResource("Wqueen03.png"));
                        b2 = new ImageIcon(this.getClass().getResource("Wrook003.png"));
                        b3 = new ImageIcon(this.getClass().getResource("Wbishop3.png"));
                        b4 = new ImageIcon(this.getClass().getResource("Wknight3.png"));
                        c1 = new ImageIcon(this.getClass().getResource("Bqueen03.png"));
                        c2 = new ImageIcon(this.getClass().getResource("Brook003.png"));
                        c3 = new ImageIcon(this.getClass().getResource("Bbishop3.png"));
                        c4 = new ImageIcon(this.getClass().getResource("Bknight3.png"));
                        break;
                }
                break;
        }

        kolumna = 0;
        for (char i = 'A'; i < 'I'; i++) {
            for (int j = 0; j < 8; j++) {
                nazwabuttona = i + String.valueOf(j + 1);
                JButton przycisk = dobierzprzycisk(nazwabuttona, false);
                if (wlasnykolor == false) {
                    if (kolor_plansza == 1) {
                        if ((pole % 2 == 0 && kolumna % 2 == 0) || (kolumna % 2 == 1 && pole % 2 == 1)) {
                            przycisk.setBackground(new Color(102, 102, 102));
                            przycisk.setForeground(new Color(102, 102, 102));
                        } else {
                            przycisk.setBackground(Color.WHITE);
                            przycisk.setForeground(Color.WHITE);
                        }
                    }
                    if (kolor_plansza == 2) {
                        if ((pole % 2 == 0 && kolumna % 2 == 0) || (kolumna % 2 == 1 && pole % 2 == 1)) {
                            przycisk.setBackground(new Color(90, 0, 0));
                            przycisk.setForeground(new Color(90, 0, 0));
                        } else {
                            przycisk.setBackground(new Color(116, 255, 255));
                            przycisk.setForeground(new Color(116, 255, 255));
                        }
                    }
                    if (kolor_plansza == 3) {
                        if ((pole % 2 == 0 && kolumna % 2 == 0) || (kolumna % 2 == 1 && pole % 2 == 1)) {
                            przycisk.setBackground(new Color(115, 69, 19));
                            przycisk.setForeground(new Color(115, 69, 19));
                        } else {
                            przycisk.setBackground(new Color(244, 164, 96));
                            przycisk.setForeground(new Color(244, 164, 96));
                        }
                    }
                }
                if (tryb != 3) {
                    if (odwrot == false) {
                        switch (kroj_zestaw) {
                            case 1:
                                switch (kolor_zestaw) {
                                    case 1:
                                        if (ust[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                    case 2:
                                        if (ust[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen02.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop2.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight2.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen02.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop2.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight2.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn002.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                }
                                break;
                            case 2:
                                switch (kolor_zestaw) {
                                    case 1:
                                        if (ust[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen04.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop4.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight4.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen04.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop4.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight4.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn004.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                    case 2:
                                        if (ust[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen03.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop3.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight3.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen03.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop3.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight3.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn003.png")));
                                        }
                                        if (ust[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                }
                                break;
                        }
                    } else {
                        switch (kroj_zestaw) {
                            case 1:
                                switch (kolor_zestaw) {
                                    case 1:
                                        if (odwrotna[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                    case 2:
                                        if (odwrotna[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen02.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop2.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight2.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen02.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop2.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight2.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn002.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                }
                                break;
                            case 2:
                                switch (kolor_zestaw) {
                                    case 1:
                                        if (odwrotna[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen04.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop4.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight4.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen04.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop4.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight4.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn004.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                    case 2:
                                        if (odwrotna[j][(int) i - 'A'] == 'K') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'Q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen03.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'R') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'B') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop3.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'N') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight3.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'P') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'k') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'q') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen03.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'r') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'b') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop3.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'n') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight3.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == 'p') {
                                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn003.png")));
                                        }
                                        if (odwrotna[j][(int) i - 'A'] == ' ') {
                                            przycisk.setIcon(null);
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                } else {
                    switch (kroj_zestaw) {
                        case 1:
                            switch (kolor_zestaw) {
                                case 1:
                                    switch (szachownica_pokoj[j][(int) i - 'A'][0]) {
                                        case 'P':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[0]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[1]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[2]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[3]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[4]);
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(przytul_figure[5]);
                                                    break;
                                            }
                                            break;
                                        case 'N':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[6]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[7]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[8]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[9]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[10]);
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(przytul_figure[11]);
                                                    break;
                                            }
                                            break;
                                        case 'B':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[12]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[13]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[14]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[15]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[16]);
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(przytul_figure[17]);
                                                    break;
                                            }
                                            break;
                                        case 'R':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[18]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[19]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[20]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[21]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[22]);
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(przytul_figure[23]);
                                                    break;
                                            }
                                            break;
                                        case 'Q':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[24]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[25]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[26]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[27]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[28]);
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(przytul_figure[29]);
                                                    break;
                                            }
                                            break;
                                        case 'K':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(przytul_figure[30]);
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(przytul_figure[31]);
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(przytul_figure[32]);
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(przytul_figure[33]);
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(przytul_figure[34]);
                                                    break;
                                            }
                                            break;
                                        case ' ':
                                            switch (szachownica_pokoj[j][(int) i - 'A'][1]) {
                                                case ' ':
                                                    przycisk.setIcon(null);
                                                    break;
                                                case 'p':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                                    break;
                                                case 'n':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                                    break;
                                                case 'b':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                                    break;
                                                case 'r':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                                    break;
                                                case 'q':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                                    break;
                                                case 'k':
                                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                    }

                }
                pole++;
            }
            pole = 0;
            kolumna = kolumna + 1;
        }
        kolumna = 0;
    }

    private void czysc_rame() {
        A8.setBorder(null);
        B8.setBorder(null);
        D8.setBorder(null);
        C8.setBorder(null);
        E8.setBorder(null);
        E7.setBorder(null);
        D7.setBorder(null);
        C7.setBorder(null);
        B7.setBorder(null);
        A7.setBorder(null);
        H8.setBorder(null);
        G8.setBorder(null);
        F8.setBorder(null);
        F7.setBorder(null);
        G7.setBorder(null);
        H7.setBorder(null);
        H6.setBorder(null);
        G6.setBorder(null);
        F6.setBorder(null);
        E6.setBorder(null);
        D6.setBorder(null);
        C6.setBorder(null);
        B6.setBorder(null);
        A6.setBorder(null);
        A5.setBorder(null);
        B5.setBorder(null);
        C5.setBorder(null);
        D5.setBorder(null);
        E5.setBorder(null);
        F5.setBorder(null);
        G5.setBorder(null);
        H5.setBorder(null);
        H4.setBorder(null);
        G4.setBorder(null);
        F4.setBorder(null);
        E4.setBorder(null);
        D4.setBorder(null);
        C4.setBorder(null);
        B4.setBorder(null);
        A4.setBorder(null);
        A3.setBorder(null);
        B3.setBorder(null);
        C3.setBorder(null);
        D3.setBorder(null);
        E3.setBorder(null);
        F3.setBorder(null);
        G3.setBorder(null);
        H3.setBorder(null);
        H2.setBorder(null);
        G2.setBorder(null);
        F2.setBorder(null);
        E2.setBorder(null);
        D2.setBorder(null);
        C2.setBorder(null);
        B2.setBorder(null);
        A2.setBorder(null);
        A1.setBorder(null);
        B1.setBorder(null);
        C1.setBorder(null);
        D1.setBorder(null);
        E1.setBorder(null);
        F1.setBorder(null);
        G1.setBorder(null);
        H1.setBorder(null);
    }

    private void koloruj_pola(boolean b, Color kolor) {
        if (b) {
            H1.setBackground(kolor);
            F1.setBackground(kolor);
            D1.setBackground(kolor);
            B1.setBackground(kolor);
            A2.setBackground(kolor);
            C2.setBackground(kolor);
            E2.setBackground(kolor);
            G2.setBackground(kolor);
            H3.setBackground(kolor);
            F3.setBackground(kolor);
            D3.setBackground(kolor);
            B3.setBackground(kolor);
            A4.setBackground(kolor);
            C4.setBackground(kolor);
            E4.setBackground(kolor);
            G4.setBackground(kolor);
            H5.setBackground(kolor);
            F5.setBackground(kolor);
            D5.setBackground(kolor);
            B5.setBackground(kolor);
            A6.setBackground(kolor);
            C6.setBackground(kolor);
            E6.setBackground(kolor);
            G6.setBackground(kolor);
            H7.setBackground(kolor);
            F7.setBackground(kolor);
            D7.setBackground(kolor);
            B7.setBackground(kolor);
            A8.setBackground(kolor);
            C8.setBackground(kolor);
            E8.setBackground(kolor);
            G8.setBackground(kolor);
        } else {
            A1.setBackground(kolor);
            C1.setBackground(kolor);
            E1.setBackground(kolor);
            G1.setBackground(kolor);
            B2.setBackground(kolor);
            D2.setBackground(kolor);
            F2.setBackground(kolor);
            H2.setBackground(kolor);
            A3.setBackground(kolor);
            C3.setBackground(kolor);
            E3.setBackground(kolor);
            G3.setBackground(kolor);
            B4.setBackground(kolor);
            D4.setBackground(kolor);
            F4.setBackground(kolor);
            H4.setBackground(kolor);
            A5.setBackground(kolor);
            C5.setBackground(kolor);
            E5.setBackground(kolor);
            G5.setBackground(kolor);
            B6.setBackground(kolor);
            D6.setBackground(kolor);
            F6.setBackground(kolor);
            H6.setBackground(kolor);
            A7.setBackground(kolor);
            C7.setBackground(kolor);
            E7.setBackground(kolor);
            G7.setBackground(kolor);
            B8.setBackground(kolor);
            D8.setBackground(kolor);
            F8.setBackground(kolor);
            H8.setBackground(kolor);
        }
        buttonGroup5.clearSelection();
    }

    private ArrayList<String> lista_pol(char symbol, byte[] start) {

        return null;
    }

    private void poprawrame() {
        A8.setBorder(A8.getBorder() != null ? new LineBorder(rama, 4) : null);
        B8.setBorder(B8.getBorder() != null ? new LineBorder(rama, 4) : null);
        D8.setBorder(D8.getBorder() != null ? new LineBorder(rama, 4) : null);
        C8.setBorder(C8.getBorder() != null ? new LineBorder(rama, 4) : null);
        E8.setBorder(E8.getBorder() != null ? new LineBorder(rama, 4) : null);
        E7.setBorder(E7.getBorder() != null ? new LineBorder(rama, 4) : null);
        D7.setBorder(D7.getBorder() != null ? new LineBorder(rama, 4) : null);
        C7.setBorder(C7.getBorder() != null ? new LineBorder(rama, 4) : null);
        B7.setBorder(B7.getBorder() != null ? new LineBorder(rama, 4) : null);
        A7.setBorder(A7.getBorder() != null ? new LineBorder(rama, 4) : null);
        H8.setBorder(H8.getBorder() != null ? new LineBorder(rama, 4) : null);
        G8.setBorder(G8.getBorder() != null ? new LineBorder(rama, 4) : null);
        F8.setBorder(F8.getBorder() != null ? new LineBorder(rama, 4) : null);
        F7.setBorder(F7.getBorder() != null ? new LineBorder(rama, 4) : null);
        G7.setBorder(G7.getBorder() != null ? new LineBorder(rama, 4) : null);
        H7.setBorder(H7.getBorder() != null ? new LineBorder(rama, 4) : null);
        A6.setBorder(A6.getBorder() != null ? new LineBorder(rama, 4) : null);
        B6.setBorder(B6.getBorder() != null ? new LineBorder(rama, 4) : null);
        D6.setBorder(D6.getBorder() != null ? new LineBorder(rama, 4) : null);
        C6.setBorder(C6.getBorder() != null ? new LineBorder(rama, 4) : null);
        E6.setBorder(E6.getBorder() != null ? new LineBorder(rama, 4) : null);
        E5.setBorder(E5.getBorder() != null ? new LineBorder(rama, 4) : null);
        D5.setBorder(D5.getBorder() != null ? new LineBorder(rama, 4) : null);
        C5.setBorder(C5.getBorder() != null ? new LineBorder(rama, 4) : null);
        B5.setBorder(B5.getBorder() != null ? new LineBorder(rama, 4) : null);
        A5.setBorder(A5.getBorder() != null ? new LineBorder(rama, 4) : null);
        H6.setBorder(H6.getBorder() != null ? new LineBorder(rama, 4) : null);
        G6.setBorder(G6.getBorder() != null ? new LineBorder(rama, 4) : null);
        F6.setBorder(F6.getBorder() != null ? new LineBorder(rama, 4) : null);
        F5.setBorder(F5.getBorder() != null ? new LineBorder(rama, 4) : null);
        G5.setBorder(G5.getBorder() != null ? new LineBorder(rama, 4) : null);
        H5.setBorder(H5.getBorder() != null ? new LineBorder(rama, 4) : null);
        A1.setBorder(A1.getBorder() != null ? new LineBorder(rama, 4) : null);
        B1.setBorder(B1.getBorder() != null ? new LineBorder(rama, 4) : null);
        D1.setBorder(D1.getBorder() != null ? new LineBorder(rama, 4) : null);
        C1.setBorder(C1.getBorder() != null ? new LineBorder(rama, 4) : null);
        E1.setBorder(E1.getBorder() != null ? new LineBorder(rama, 4) : null);
        E2.setBorder(E2.getBorder() != null ? new LineBorder(rama, 4) : null);
        D2.setBorder(D2.getBorder() != null ? new LineBorder(rama, 4) : null);
        C2.setBorder(C2.getBorder() != null ? new LineBorder(rama, 4) : null);
        B2.setBorder(B2.getBorder() != null ? new LineBorder(rama, 4) : null);
        A2.setBorder(A2.getBorder() != null ? new LineBorder(rama, 4) : null);
        H1.setBorder(H1.getBorder() != null ? new LineBorder(rama, 4) : null);
        G1.setBorder(G1.getBorder() != null ? new LineBorder(rama, 4) : null);
        F1.setBorder(F1.getBorder() != null ? new LineBorder(rama, 4) : null);
        F2.setBorder(F2.getBorder() != null ? new LineBorder(rama, 4) : null);
        G2.setBorder(G2.getBorder() != null ? new LineBorder(rama, 4) : null);
        H2.setBorder(H2.getBorder() != null ? new LineBorder(rama, 4) : null);
        A3.setBorder(A3.getBorder() != null ? new LineBorder(rama, 4) : null);
        B3.setBorder(B3.getBorder() != null ? new LineBorder(rama, 4) : null);
        D3.setBorder(D3.getBorder() != null ? new LineBorder(rama, 4) : null);
        C3.setBorder(C3.getBorder() != null ? new LineBorder(rama, 4) : null);
        E3.setBorder(E3.getBorder() != null ? new LineBorder(rama, 4) : null);
        E4.setBorder(E4.getBorder() != null ? new LineBorder(rama, 4) : null);
        D4.setBorder(D4.getBorder() != null ? new LineBorder(rama, 4) : null);
        C4.setBorder(C4.getBorder() != null ? new LineBorder(rama, 4) : null);
        B4.setBorder(B4.getBorder() != null ? new LineBorder(rama, 4) : null);
        A4.setBorder(A4.getBorder() != null ? new LineBorder(rama, 4) : null);
        H3.setBorder(H3.getBorder() != null ? new LineBorder(rama, 4) : null);
        G3.setBorder(G3.getBorder() != null ? new LineBorder(rama, 4) : null);
        F3.setBorder(F3.getBorder() != null ? new LineBorder(rama, 4) : null);
        F4.setBorder(F4.getBorder() != null ? new LineBorder(rama, 4) : null);
        G4.setBorder(G4.getBorder() != null ? new LineBorder(rama, 4) : null);
        H4.setBorder(H4.getBorder() != null ? new LineBorder(rama, 4) : null);
    }

    private void polacz(int bazamin) throws IOException {
        jTextArea2.setText(jTextArea2.getText().trim() + "znaleziono gracza \n");

        siec = true;
        obrotowy.setText("Obrót WYŁ");
        obrotowy.setVisible(false);
        polaczenie_net = new lacze();
        polaczenie_net.start();
        jButton67.setVisible(false);
        jButton65.setVisible(false);
        msgwy = String.valueOf(oczekiwanie);
        out.writeUTF("color:" + msgwy);
        if (oczekiwanie == true) {
            odwrot = true;
            jTextArea2.setText(jTextArea2.getText().trim() + "\n grasz jako: czarne \n");
            jButton66.setEnabled(false);
            jButton68.setEnabled(false);
        } else {
            odwrot = false;
            jTextArea2.setText(jTextArea2.getText().trim() + "\n grasz jako: białe  \n");
        }
        if (tryb == 1) {
            msgwy = String.valueOf(czasgry);
            out.writeUTF("TG" + msgwy);
            if (czasgry == 10) {
                msgwy = String.valueOf(bazamin);
                out.writeUTF("MIN" + msgwy);

                msgwy = String.valueOf(bonuss);
                out.writeUTF("BON" + msgwy);
            }
            whitetime = new Thread(czasB, "bieltime");
            blacktime = new Thread(czasC, "czerntime");
            whitetime.start();
            blacktime.start();
        }
        if (tryb == 2) {
            msgwy = String.valueOf(sek + "|" + seksyg + "|" + oczekiwanie);
            out.writeUTF("ALT" + msgwy);
            whitetime = new Thread(czasB, "bieltime");
            blacktime = new Thread(czasC, "czerntime");
            whitetime.start();
            blacktime.start();
        }
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        A8.setEnabled(true);
        B8.setEnabled(true);
        D8.setEnabled(true);
        C8.setEnabled(true);
        E8.setEnabled(true);
        E7.setEnabled(true);
        D7.setEnabled(true);
        C7.setEnabled(true);
        B7.setEnabled(true);
        A7.setEnabled(true);
        H8.setEnabled(true);
        G8.setEnabled(true);
        F8.setEnabled(true);
        F7.setEnabled(true);
        G7.setEnabled(true);
        H7.setEnabled(true);
        H6.setEnabled(true);
        G6.setEnabled(true);
        F6.setEnabled(true);
        E6.setEnabled(true);
        D6.setEnabled(true);
        C6.setEnabled(true);
        B6.setEnabled(true);
        A6.setEnabled(true);
        A5.setEnabled(true);
        B5.setEnabled(true);
        C5.setEnabled(true);
        D5.setEnabled(true);
        E5.setEnabled(true);
        F5.setEnabled(true);
        G5.setEnabled(true);
        H5.setEnabled(true);
        H4.setEnabled(true);
        G4.setEnabled(true);
        F4.setEnabled(true);
        E4.setEnabled(true);
        D4.setEnabled(true);
        C4.setEnabled(true);
        B4.setEnabled(true);
        A4.setEnabled(true);
        A3.setEnabled(true);
        B3.setEnabled(true);
        C3.setEnabled(true);
        D3.setEnabled(true);
        E3.setEnabled(true);
        F3.setEnabled(true);
        G3.setEnabled(true);
        H3.setEnabled(true);
        H2.setEnabled(true);
        G2.setEnabled(true);
        F2.setEnabled(true);
        E2.setEnabled(true);
        D2.setEnabled(true);
        C2.setEnabled(true);
        B2.setEnabled(true);
        A2.setEnabled(true);
        A1.setEnabled(true);
        B1.setEnabled(true);
        C1.setEnabled(true);
        D1.setEnabled(true);
        E1.setEnabled(true);
        F1.setEnabled(true);
        G1.setEnabled(true);
        H1.setEnabled(true);
        jButton66.setEnabled(true);
        jButton68.setEnabled(true);
        jButton71.setVisible(false);
        gra = true;
        zasada50 = 0;
        dolicz = false;
        ustawka = false;
        lekkieB = 4;
        lekkieC = 4;
        ciezkieB = 3;
        ciezkieC = 3;
        pionB = 8;
        pionC = 8;
    }

    /**
     * Odpowiada za działanie w tle programu SI
     *
     */
    public class Progres_postep extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground(){
            try{
            String oponet = "";
            char[][] backup = new char[8][8];
            jProgressBar1.setMinimum(0);
            SI_wyk = false;
            for (int x = 0; x < 8; x++) {
                System.arraycopy(ust[x], 0, backup[x], 0, 8);
            }
            int elem = 0, dlugosc = Generator.generuj_posuniecia(konwert(backup.clone()), ruchB, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, 1, kol, false, ' ', new int[2], false).size();
            System.out.println(dlugosc + "elem");
            jProgressBar1.setMaximum(dlugosc);
            jProgressBar1.setValue(0);
            int najlepszy = tura_rywala ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int najwieksza = Integer.MIN_VALUE;
            int najmniejsza = Integer.MAX_VALUE;
            int pula_pozycji = 0;
            int pula_sprawdzona = 0;
            String ostatni = "";
            long czas_start = System.currentTimeMillis();
            /*ExecutorService exec = Executors.newCachedThreadPool();
            ArrayList<Future<wyniki>> rezultaty = new ArrayList<>();
            wyniki[] w = new wyniki[9];
            
            Myslenie[] tm = new Myslenie[9];
             for (int i = 0; i < 9; i++) {
            tm[i] = new Myslenie(Generator.generuj_posuniecia(konwert(backup), ruchB, przelotcan,
                        bleft, bright, wleft, wright, kingrochB, kingrochC, 1, kol),
                        backup.clone(), ruchB, przelotcan,
                        bleft, bright, wleft, wright, kingrochB, kingrochC, dokonano_RB, dokonano_RC, kol, odwrot, i + 1, glebiaSI);
            
             }
            for (int i = 0; i < 9; i++) {
                synchronized (this) {
                    Future<wyniki> ruch = exec.submit(tm[i]);
                    rezultaty.add(ruch);
                }
            }
            int index = 0;
            for (Future<wyniki> wynik : rezultaty) {

                try {
                    w[index] = wynik.get();
                } catch (ExecutionException e) {
                    System.out.println("błąd pozyskania wartości");
                } finally {
                    exec.shutdown();
                }
                index++;
            }
            for (int i = 0; i < 9; i++) {
                pula_pozycji = pula_pozycji + w[i].pula_pozycji;
                pula_sprawdzona = pula_sprawdzona + w[i].pula_sprawdzona;
                if (ruchB && w[i].najlepszy > najlepszy && w[i].obecny) {
                    oponet = (w[i].oponet);
                    najlepszy = w[i].najlepszy;
                } else if (!ruchB && w[i].najlepszy < najlepszy && w[i].obecny) {
                    oponet = (w[i].oponet);
                    najlepszy = w[i].najlepszy;
                }
            }*/

            int licznik = 0;
            for (Ruch move : Generator.generuj_posuniecia(konwert(backup.clone()), ruchB, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, 1, kol, false, ' ', new int[2], false)) {
                if(Generator.generuj_posuniecia(konwert(backup.clone()), ruchB, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, 1, kol, false, ' ', new int[2], false).size()>1) {
            
                elem++;
                jProgressBar1.setValue(elem);
                setProgress((int) (elem * 1f / dlugosc * 100.0f));
                SI_MIN_MAX_Alfa_Beta ai = new SI_MIN_MAX_Alfa_Beta(backup.clone(), ruchB, przelotcan,
                        bleft, bright, wleft, wright, kingrochB, kingrochC, dokonano_RB, dokonano_RC, kol, odwrot, licznik, glebiaSI);
                jProgressBar1.setString("Rozpatrywane:" + (move.toString()) + "| bieżacy wybór:" + oponet);
                System.out.println((move.toString()) + " mysle");
                if (!move.roszada || !RuchZagrozenie_kontrola.szach(backup.clone(), ruchB)) {
                    System.out.println(najmniejsza + "#" + najwieksza + "   ");
                    int wynik = ai.wykonaj(glebiaSI, move, najwieksza, najmniejsza);
                    pula_pozycji = pula_pozycji + ai.all_position;
                    pula_sprawdzona = pula_sprawdzona + ai.pozycje;
                    System.out.println(ai.licznik);
                    licznik = ai.licznik;
                    System.out.println(najmniejsza + "|" + najwieksza + "   " + wynik);
                    if (!ai.isZakaz()) {
                        ostatni = (move.toString());
                        if (ruchB == true && wynik > najwieksza) {
                            najwieksza = wynik;
                            oponet = (move.toString());
                            najlepszy = wynik;
                        } else if (ruchB == false && wynik < najmniejsza) {
                            najmniejsza = wynik;
                            oponet = (move.toString());
                            najlepszy = wynik;
                        }
                        //System.out.println("wynik "+wynik);
                    } else {
                        System.out.println("cofka");
                    }
                    System.out.println((move.toString()) + " wynik(" + najlepszy + ")");

                } else {
                    System.out.println("error castling");
                }
                jProgressBar1.setString("Rozpatrywane:" + move.toString() + "| bieżacy wybór:" + oponet);
                if (ai.isPrzerwa()) {
                    System.out.println("przerwa");
                    break;
                }
                }else{
                    pula_pozycji=1;
                    pula_sprawdzona=1;
                    oponet=move.toString();
                    najlepszy=tura_rywala?Integer.MAX_VALUE:Integer.MIN_VALUE;
                }
            }
            long czas_stop = System.currentTimeMillis();
            long czas = (czas_stop - czas_start) / 1000;
            System.out.println("wszystkich kombinacji: " + pula_pozycji);
            System.out.println("zaanalizowano kombinacji: " + pula_sprawdzona);
            System.out.printf("procent analizowanych kombinacji: %.2f %n", (float) (pula_sprawdzona) / (float) (pula_pozycji) * 100f);
            if ("".equals(oponet)) {
                oponet = ostatni;
            }
            System.out.println("Ruch SI:" + oponet + "(" + najlepszy + ")");
            System.out.println("Czas SI:" + czas + "s");
            float procent = (Math.round(pula_sprawdzona * 10000.0f / pula_pozycji)) / 100.0f;
            System.out.println("Czas w min: " + czas / 60 + " min, " + czas % 60 + " s");
            jTextArea2.setVisible(true);
            laczny_czas = laczny_czas + czas;
            jTextArea2.append("Ruch SI:" + oponet + "(" + najlepszy + ") \n");
            jTextArea2.append("Czas SI:" + czas + "s" + " \n");
            jTextArea2.append("Czas w min: " + czas / 60 + " min, " + czas % 60 + " s" + " \n");
            jTextArea2.append("Czas łączny: " + laczny_czas + " \n");
            jTextArea2.append("wszystkich kombinacji: " + pula_pozycji + " \n");
            jTextArea2.append("zaanalizowano kombinacji: " + pula_sprawdzona + " \n");
            jTextArea2.append("procent analizowanych kombinacji:" + procent + " \n \n");
            jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
            SI_wyk = true;
            A1.setEnabled(true);
            A2.setEnabled(true);
            A3.setEnabled(true);
            A4.setEnabled(true);
            A5.setEnabled(true);
            A6.setEnabled(true);
            A7.setEnabled(true);
            A8.setEnabled(true);
            B1.setEnabled(true);
            B2.setEnabled(true);
            B3.setEnabled(true);
            B4.setEnabled(true);
            B5.setEnabled(true);
            B6.setEnabled(true);
            B7.setEnabled(true);
            B8.setEnabled(true);
            C1.setEnabled(true);
            C2.setEnabled(true);
            C3.setEnabled(true);
            C4.setEnabled(true);
            C5.setEnabled(true);
            C6.setEnabled(true);
            C7.setEnabled(true);
            C8.setEnabled(true);
            D1.setEnabled(true);
            D2.setEnabled(true);
            D3.setEnabled(true);
            D4.setEnabled(true);
            D5.setEnabled(true);
            D6.setEnabled(true);
            D7.setEnabled(true);
            D8.setEnabled(true);
            E1.setEnabled(true);
            E2.setEnabled(true);
            E3.setEnabled(true);
            E4.setEnabled(true);
            E5.setEnabled(true);
            E6.setEnabled(true);
            E7.setEnabled(true);
            E8.setEnabled(true);
            F1.setEnabled(true);
            F2.setEnabled(true);
            F3.setEnabled(true);
            F4.setEnabled(true);
            F5.setEnabled(true);
            F6.setEnabled(true);
            F7.setEnabled(true);
            F8.setEnabled(true);
            G1.setEnabled(true);
            G2.setEnabled(true);
            G3.setEnabled(true);
            G4.setEnabled(true);
            G5.setEnabled(true);
            G6.setEnabled(true);
            G7.setEnabled(true);
            G8.setEnabled(true);
            H1.setEnabled(true);
            H2.setEnabled(true);
            H3.setEnabled(true);
            H4.setEnabled(true);
            H5.setEnabled(true);
            H6.setEnabled(true);
            H7.setEnabled(true);
            H8.setEnabled(true);

            if (SI_wyk) {

                if (!"O-O".equals(oponet.substring(0, 3))) {
                    start = oponet.substring(1, 3);
                    stop = oponet.substring(4, 6);
                    if (oponet.charAt(6) == '=') {
                        znak_promocji = oponet.charAt(7);

                    }
                    aktywuj(odwrot, start);
                    aktywuj(odwrot, stop);
                } else {
                    if ("O-O".equals(oponet)) {
                        if (ruchB) {
                            aktywuj(odwrot, "E1");
                            aktywuj(odwrot, "G1");
                        } else {
                            aktywuj(odwrot, "E8");
                            aktywuj(odwrot, "G8");
                        }
                    }
                    if ("O-O-O".equals(oponet)) {
                        if (ruchB) {
                            aktywuj(odwrot, "E1");
                            aktywuj(odwrot, "C1");
                        } else {
                            aktywuj(odwrot, "E8");
                            aktywuj(odwrot, "C8");
                        }
                    }
                }

            }
            SI_wyk = false;

            
        }catch(Exception xg){
        xg.printStackTrace();
        
    }return null;
    }
    }
    private SI_MIN_MAX_Alfa_Beta.figury[][] konwert(char[][] ustawienie) {
        SI_MIN_MAX_Alfa_Beta.figury[][] pozycja = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
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
            }
        }
        return pozycja;
    }

    /* private MinMaxAB1.figury[][] konwert2(char[][] ustawienie) {
        MinMaxAB1.figury[][] pozycja = new MinMaxAB1.figury[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case ' ':
                        pozycja[i][j] = MinMaxAB1.figury.pustka;
                        break;
                    case 'P':
                        pozycja[i][j] = MinMaxAB1.figury.BPion;
                        break;
                    case 'p':
                        pozycja[i][j] = MinMaxAB1.figury.CPion;
                        break;
                    case 'N':
                        pozycja[i][j] = MinMaxAB1.figury.BSkoczek;
                        break;
                    case 'n':
                        pozycja[i][j] = MinMaxAB1.figury.CSkoczek;
                        break;
                    case 'B':
                        pozycja[i][j] = MinMaxAB1.figury.BGoniec;
                        break;
                    case 'b':
                        pozycja[i][j] = MinMaxAB1.figury.CGoniec;
                        break;
                    case 'R':
                        pozycja[i][j] = MinMaxAB1.figury.BWieza;
                        break;
                    case 'r':
                        pozycja[i][j] = MinMaxAB1.figury.CWieza;
                        break;
                    case 'Q':
                        pozycja[i][j] = MinMaxAB1.figury.BHetman;
                        break;
                    case 'q':
                        pozycja[i][j] = MinMaxAB1.figury.CHetman;
                        break;
                    case 'K':
                        pozycja[i][j] = MinMaxAB1.figury.BKrol;
                        break;
                    case 'k':
                        pozycja[i][j] = MinMaxAB1.figury.CKrol;
                        break;
                }
            }
        }
        return pozycja;
    }*/
    /**
     * Uruchamia działanie SI
     */
    private void SI_ma_ruch() {

        A1.setEnabled(false);
        A2.setEnabled(false);
        A3.setEnabled(false);
        A4.setEnabled(false);
        A5.setEnabled(false);
        A6.setEnabled(false);
        A7.setEnabled(false);
        A8.setEnabled(false);
        B1.setEnabled(false);
        B2.setEnabled(false);
        B3.setEnabled(false);
        B4.setEnabled(false);
        B5.setEnabled(false);
        B6.setEnabled(false);
        B7.setEnabled(false);
        B8.setEnabled(false);
        C1.setEnabled(false);
        C2.setEnabled(false);
        C3.setEnabled(false);
        C4.setEnabled(false);
        C5.setEnabled(false);
        C6.setEnabled(false);
        C7.setEnabled(false);
        C8.setEnabled(false);
        D1.setEnabled(false);
        D2.setEnabled(false);
        D3.setEnabled(false);
        D4.setEnabled(false);
        D5.setEnabled(false);
        D6.setEnabled(false);
        D7.setEnabled(false);
        D8.setEnabled(false);
        E1.setEnabled(false);
        E2.setEnabled(false);
        E3.setEnabled(false);
        E4.setEnabled(false);
        E5.setEnabled(false);
        E6.setEnabled(false);
        E7.setEnabled(false);
        E8.setEnabled(false);
        F1.setEnabled(false);
        F2.setEnabled(false);
        F3.setEnabled(false);
        F4.setEnabled(false);
        F5.setEnabled(false);
        F6.setEnabled(false);
        F7.setEnabled(false);
        F8.setEnabled(false);
        G1.setEnabled(false);
        G2.setEnabled(false);
        G3.setEnabled(false);
        G4.setEnabled(false);
        G5.setEnabled(false);
        G6.setEnabled(false);
        G7.setEnabled(false);
        G8.setEnabled(false);
        H1.setEnabled(false);
        H2.setEnabled(false);
        H3.setEnabled(false);
        H4.setEnabled(false);
        H5.setEnabled(false);
        H6.setEnabled(false);
        H7.setEnabled(false);
        H8.setEnabled(false);
        Progres_postep progresik = new Progres_postep();
        progresik.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            String name1 = evt.getPropertyName();
            if (name1.equals("progress")) {
                repaint();
            } else if (name1.equals("state")) {
                SwingWorker.StateValue state1 = (SwingWorker.StateValue) evt.getNewValue();
            }
        });
        progresik.execute();
        System.out.println("uruchomiony");

        // oponet = ai.wykonaj(glebiaSI);
    }

    static void dodaj_do_listy(String nazwa) {
        listaip.add(nazwa);
    }

    /**
     * usuwa wcześniejsze obramowania i potem ustawia obramowanie dla bieżącej
     * pary przycisków
     */
    public void ustawrame() {
        czysc_rame();
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        boolean zawrot = odwrot;
        if (!"".equals(ostatni_start) && !"".equals(ostatni_stop)) {
            JButton poczatek = dobierzprzycisk(ostatni_start, ((!SI_ON && !siec && zawrot) || ((tura_rywala && SI_ON) || (odwrot || siec))));
            JButton koniec = dobierzprzycisk(ostatni_stop, ((!SI_ON && !siec && zawrot) || ((tura_rywala && SI_ON) || (odwrot || siec))));
            poczatek.setBorder(new LineBorder(rama, 4));
            koniec.setBorder(new LineBorder(rama, 4));
        }
    }

    class zegar implements Runnable {

        long limit;
        JLabel labelek;
        Lock blokuj;
        Condition war;
        Timer licznik = new Timer();
        long kres;
        boolean stronnictwo;
        long m, s;
        int tryb;
        private boolean warn;

        private zegar(int czas, JLabel wybrany, Lock blokada, Condition warunek, boolean strona, int tryb) {
            this.limit = czas;
            labelek = wybrany;
            m = czas / 60000;
            this.war = warunek;
            this.blokuj = blokada;
            this.stronnictwo = strona;
            this.tryb = tryb;
            this.s = 0;

        }

        private zegar(int i, JLabel wybrany, Lock blokada, Condition warunek, boolean strona, int tryb, int seksyg) {
            this.limit = i / 1000;
            labelek = wybrany;
            m = seksyg;
            this.war = warunek;
            this.blokuj = blokada;
            this.stronnictwo = strona;
            this.tryb = tryb;
            this.s = i / 1000;
        }

        @Override
        public void run() {

            this.blokuj.lock();
            try {
                while (true) {
                    while (ruchB != stronnictwo) {
                        try {
                            this.war.await();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    if (tryb == 1) {
                        this.s = this.s - 1;
                        if (this.s == -1) {
                            this.s = 59;
                            this.m = this.m - 1;
                            labelek.setText(this.m + ":" + this.s);
                        } else {
                            if (s < 10) {
                                labelek.setText(this.m + ":0" + this.s);
                            } else {
                                labelek.setText(this.m + ":" + this.s);
                            }
                            if (this.s == 0 && this.m == 0) {
                                JOptionPane.showMessageDialog(rootPane, "Przekroczono czas na grę");
                                kapitulacja();
                            }
                        }
                    }
                    if (tryb == 2) {
                        this.s = this.s - 1;
                        labelek.setText(String.valueOf(this.s));
                        if (this.s == 0 && !this.warn) {
                            Toolkit.getDefaultToolkit().beep();
                            labelek.setBackground(Color.red);
                            labelek.setForeground(Color.red);
                            labelek.setText(String.valueOf(this.m));
                            this.warn = true;
                            this.s = m;
                        }
                        if (this.warn && this.s == 0) {
                            JOptionPane.showMessageDialog(rootPane, "Przekroczono czas");
                            kapitulacja();
                        }
                    }
                    try {
                        if (hodu || hitme || protectme) {
                            Thread.sleep(1000);
                        } else {
                            break;
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.war.signalAll();
                }
            } finally {
                labelek.setBackground(null);
                this.blokuj.unlock();
            }
        }
    }

    public class lacze extends Thread {

        public lacze() {
        }

        @Override
        public void run() {
            while (!msgwe.equals("exit")) {
                try {
                    msgwe = in.readUTF();
                    if ("Gramy dalej".equals(msgwe)) {
                        jButton66.setEnabled(true);
                        jButton68.setEnabled(true);
                        jButton69.setEnabled(false);
                        jButton70.setEnabled(false);
                    }
                    if ("remis?".equals(msgwe)) {
                        jButton68.setEnabled(false);
                        jButton69.setEnabled(true);
                        jButton70.setEnabled(true);
                    }
                    if ("Zgadzam sie na remis".equals(msgwe)) {
                        remis();
                    }
                    if ("poddaje sie. wygraleś".equals(msgwe)) {
                        if (organizator == true) {
                            try {
                                server.close();
                                socket.close();
                                in.close();
                                out.close();
                            } catch (IOException ex) {
                                Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                socket.close();
                                in.close();
                                out.close();
                            } catch (IOException ex) {
                                Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        kapitulacja();
                    }
                    if (msgwe.length() > 0) {
                        if (!"color:true".equals(msgwe) && !"color:false".equals(msgwe)) {

                            if (!(msgwe.substring(0, 2).equals("TG")) && !(msgwe.substring(0, 3).equals("BON")) && !(msgwe.substring(0, 3).equals("MIN"))
                                    && !(msgwe.substring(0, 3).equals("ALT"))) {
                                jTextArea2.setText(jTextArea2.getText().trim() + "\n" + msgwe);
                            }
                            if (msgwe.length() == 8) {
                                znak_promocji = msgwe.charAt(msgwe.length() - 1);
                            }
                            if (!"O-O".equals(msgwe) && !"O-O-O".equals(msgwe) && msgwe.length() >= 6) {
                                ruszaj = msgwe.substring(1, 3);
                                zatrzymaj = msgwe.substring(4, 6);
                                if (dobierzprzycisk(ruszaj, odwrot) != null && dobierzprzycisk(zatrzymaj, odwrot) != null) {
                                    wzor = true;
                                    aktywuj(odwrot, ruszaj);
                                    aktywuj(odwrot, zatrzymaj);
                                    oczekiwanie = oczekiwanie == false;
                                    if (oczekiwanie == true) {
                                        jButton66.setEnabled(false);
                                        jButton68.setEnabled(false);
                                    } else {
                                        jButton66.setEnabled(true);
                                        jButton68.setEnabled(true);
                                    }
                                    wzor = false;
                                }
                            }
                            if ("O-O".equals(msgwe)) {
                                wzor = true;
                                if (ruchB == true) {
                                    if (oczekiwanie == true) {
                                        if (odwrot == false) {
                                            Button_Clicked(E1);
                                            Button_Clicked(G1);
                                        } else {
                                            Button_Clicked(D8);
                                            Button_Clicked(B8);
                                        }
                                    }
                                } else {
                                    if (wzor == true) {
                                        if (oczekiwanie == true) {
                                            if (odwrot == false) {
                                                Button_Clicked(E8);
                                                Button_Clicked(G8);
                                            } else {
                                                Button_Clicked(D1);
                                                Button_Clicked(B1);
                                            }
                                        }
                                    }
                                }
                                oczekiwanie = oczekiwanie == false;
                                wzor = false;
                            }
                            if ("O-O-O".equals(msgwe)) {
                                wzor = true;
                                if (ruchB == true) {
                                    if (oczekiwanie == true) {
                                        if (odwrot == false) {
                                            Button_Clicked(E1);
                                            Button_Clicked(C1);
                                        } else {
                                            Button_Clicked(D8);
                                            Button_Clicked(F8);
                                        }
                                    }
                                } else {
                                    if (wzor == true) {
                                        if (oczekiwanie == true) {
                                            if (odwrot == false) {
                                                Button_Clicked(E8);
                                                Button_Clicked(C8);
                                            } else {
                                                Button_Clicked(D1);
                                                Button_Clicked(F1);
                                            }
                                        }
                                    }
                                }
                                oczekiwanie = oczekiwanie == false;
                                wzor = false;
                            }
                        } else {
                            if ("color:true".equals(msgwe)) {
                                oczekiwanie = false;
                                odwrot = false;
                                jTextArea2.setText(jTextArea2.getText().trim() + "\n" + "grasz jako białe");
                                jTextArea2.setText(jTextArea2.getText().trim() + "\n" + "czekasz" + oczekiwanie);
                                jButton66.setEnabled(true);
                            }
                            if ("color:false".equals(msgwe)) {
                                oczekiwanie = true;
                                jButton66.setEnabled(false);
                                odwrot = true;
                                JLabel pomoc1 = zegarbiale;
                                zegarbiale = zegarczarne;
                                zegarczarne = pomoc1;
                                styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                                jTextArea2.setText(jTextArea2.getText().trim() + "\n" + "grasz jako czarne");
                                jTextArea2.setText(jTextArea2.getText().trim() + "\n" + "czekasz" + oczekiwanie);
                            }
                        }
                        int bazamin;
                        if ((msgwe.substring(0, 2).equals("TG"))) {
                            tryb = 1;
                            blokada = new ReentrantLock();
                            warunek = blokada.newCondition();
                            String tmp = (msgwe.substring(2));
                            czasgry = Integer.parseInt(tmp);
                            if (tmp.equals("")) {
                                tmp = "0";
                            }
                            czasgry = -1;
                            switch (tmp) {
                                case "0":
                                    czasB = (new zegar(60 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(60 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("60:00");
                                    zegarczarne.setText("60:00");
                                    break;
                                case "1":
                                    czasB = (new zegar(30 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(30 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("30:00");
                                    zegarczarne.setText("30:00");

                                    break;
                                case "2":
                                    czasB = (new zegar(15 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(15 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("15:00");
                                    zegarczarne.setText("15:00");
                                    bonuss = 10;
                                    break;
                                case "3":
                                    czasB = (new zegar(10 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(10 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("10:00");
                                    zegarczarne.setText("10:00");
                                    break;
                                case "4":
                                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("5:00");
                                    zegarczarne.setText("5:00");
                                    bonuss = 5;
                                    break;
                                case "5":
                                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("5:00");
                                    zegarczarne.setText("5:00");
                                    break;
                                case "6":
                                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("3:00");
                                    zegarczarne.setText("3:00");
                                    bonuss = 2;
                                    break;
                                case "7":
                                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("3:00");
                                    zegarczarne.setText("3:00");
                                    break;
                                case "8":
                                    czasB = (new zegar(2 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(2 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("2:00");
                                    zegarczarne.setText("2:00");
                                    bonuss = 1;
                                    break;
                                case "9":
                                    czasB = (new zegar(1 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(1 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("1:00");
                                    zegarczarne.setText("1:00");
                                    break;
                                case "10":
                                    czasgry = 0;
                                    czasB = (new zegar(60 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(60 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText("60:00");
                                    zegarczarne.setText("60:00");
                                    break;
                            }

                            whitetime = new Thread(czasB, "bieltime");
                            blacktime = new Thread(czasC, "czerntime");
                            whitetime.start();
                            blacktime.start();
                        }
                        if ((msgwe.substring(0, 3).equals("ALT"))) {
                            tryb = 2;
                            blokada = new ReentrantLock();
                            warunek = blokada.newCondition();
                            String tmp = String.valueOf(msgwe.substring(3));
                            StringTokenizer tokeny = new StringTokenizer(tmp, "|");
                            sek = Integer.parseInt(tokeny.nextToken());
                            sekbaza = sek;
                            seksyg = Integer.parseInt(tokeny.nextToken());
                            oczekiwanie = Boolean.valueOf(tokeny.nextToken());
                            if (oczekiwanie == false) {
                                oczekiwanie = false;
                                jButton66.setEnabled(true);
                            } else {
                                oczekiwanie = true;
                                jButton66.setEnabled(false);
                            }
                            czasB = (new zegar(sek * 1000, zegarbiale, blokada, warunek, true, tryb, seksyg));
                            czasC = (new zegar(sek * 1000, zegarczarne, blokada, warunek, false, tryb, seksyg));
                            zegarbiale.setText(String.valueOf(sek));
                            zegarczarne.setText(String.valueOf(sek));
                            whitetime = new Thread(czasB, "bieltime");
                            blacktime = new Thread(czasC, "czerntime");
                            whitetime.start();
                            blacktime.start();
                        }
                        if ((msgwe.substring(0, 3).equals("MIN"))) {
                            tryb = 1;
                            String tmp = String.valueOf(msgwe.substring(3));
                            bazamin = Integer.parseInt(tmp);
                            czasB = (new zegar(bazamin * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                            czasC = (new zegar(bazamin * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                            zegarbiale.setText(bazamin + ":00");
                            zegarczarne.setText(bazamin + ":00");
                        }
                        if ((msgwe.substring(0, 3).equals("BON"))) {
                            tryb = 1;
                            String tmp = String.valueOf(msgwe.substring(3));
                            bonuss = Integer.parseInt(tmp);
                            whitetime = new Thread(czasB, "bieltime");
                            blacktime = new Thread(czasC, "czerntime");
                            whitetime.start();
                            blacktime.start();
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "przeciwnik się rozłączył. \n"
                            + "partia została przerwana, a wynik nie zostanie zapisany.\n"
                            + "Zostanie u ciebie utworzony plik z przebiegiem partii na wypadek,\n"
                            + "gdybyście chcieli odwzorować pozycję i od niej kontynuować");
                    try (PrintWriter plik1 = new PrintWriter(new FileWriter("spis partii przerwanych.txt", true))) {
                        jTextArea3.write(plik1);
                        plik1.println();
                        plik1.println();
                        plik1.println();
                        plik1.println();
                    } catch (IOException ex1) {
                        Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    if (siec == false) {
                        Object[] opcje_rewanzu = {"tak", "nie"};
                        int opcja_rewanzu = JOptionPane.showOptionDialog(null, "NASTĘPNA GRA?", "REAWNŻ",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rewanzu, null);
                        if (opcja_rewanzu == 0) {
                            try {
                                final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                                final File currentJar = new File(SzachowaArena.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                                final ArrayList<String> command = new ArrayList<>();
                                command.add(javaBin);
                                command.add("-jar");
                                command.add(currentJar.getPath());
                                final ProcessBuilder builder = new ProcessBuilder(command);
                                builder.start();
                            } catch (URISyntaxException | IOException ex1) {
                                Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                    System.exit(0);
                }
            }
        }
    }

    /**
     * zmusza do działania konkretne pzycisk
     *
     * @param odwrot1 Czy plansza jest odwrócona (białymi na górze)
     * @param ruszaj Nazwa pola
     */
    private void aktywuj(boolean odwrot1, String ruszaj) {
        switch (ruszaj) {
            case "A1":
                if (odwrot1 == false) {
                    Button_Clicked(A1);
                } else {
                    Button_Clicked(H8);
                }
                break;
            case "A2":
                if (odwrot1 == false) {
                    Button_Clicked(A2);
                } else {
                    Button_Clicked(H7);
                }
                break;
            case "A3":
                if (odwrot1 == false) {
                    Button_Clicked(A3);
                } else {
                    Button_Clicked(H6);
                }
                break;
            case "A4":
                if (odwrot1 == false) {
                    Button_Clicked(A4);
                } else {
                    Button_Clicked(H5);
                }
                break;
            case "A5":
                if (odwrot1 == false) {
                    Button_Clicked(A5);
                } else {
                    Button_Clicked(H4);
                }
                break;
            case "A6":
                if (odwrot1 == false) {
                    Button_Clicked(A6);
                } else {
                    Button_Clicked(H3);
                }
                break;
            case "A7":
                if (odwrot1 == false) {
                    Button_Clicked(A7);
                } else {
                    Button_Clicked(H2);
                }
                break;
            case "A8":
                if (odwrot1 == false) {
                    Button_Clicked(A8);
                } else {
                    Button_Clicked(H1);
                }
                break;
            case "B1":
                if (odwrot1 == false) {
                    Button_Clicked(B1);
                } else {
                    Button_Clicked(G8);
                }
                break;
            case "B2":
                if (odwrot1 == false) {
                    Button_Clicked(B2);
                } else {
                    Button_Clicked(G7);
                }
                break;
            case "B3":
                if (odwrot1 == false) {
                    Button_Clicked(B3);
                } else {
                    Button_Clicked(G6);
                }
                break;
            case "B4":
                if (odwrot1 == false) {
                    Button_Clicked(B4);
                } else {
                    Button_Clicked(G5);
                }
                break;
            case "B5":
                if (odwrot1 == false) {
                    Button_Clicked(B5);
                } else {
                    Button_Clicked(G4);
                }
                break;
            case "B6":
                if (odwrot1 == false) {
                    Button_Clicked(B6);
                } else {
                    Button_Clicked(G3);
                }
                break;
            case "B7":
                if (odwrot1 == false) {
                    Button_Clicked(B7);
                } else {
                    Button_Clicked(G2);
                }
                break;
            case "B8":
                if (odwrot1 == false) {
                    Button_Clicked(B8);
                } else {
                    Button_Clicked(G1);
                }
                break;
            case "C1":
                if (odwrot1 == false) {
                    Button_Clicked(C1);
                } else {
                    Button_Clicked(F8);
                }
                break;
            case "C2":
                if (odwrot1 == false) {
                    Button_Clicked(C2);
                } else {
                    Button_Clicked(F7);
                }
                break;
            case "C3":
                if (odwrot1 == false) {
                    Button_Clicked(C3);
                } else {
                    Button_Clicked(F6);
                }
                break;
            case "C4":
                if (odwrot1 == false) {
                    Button_Clicked(C4);
                } else {
                    Button_Clicked(F5);
                }
                break;
            case "C5":
                if (odwrot1 == false) {
                    Button_Clicked(C5);
                } else {
                    Button_Clicked(F4);
                }
                break;
            case "C6":
                if (odwrot1 == false) {
                    Button_Clicked(C6);
                } else {
                    Button_Clicked(F3);
                }
                break;
            case "C7":
                if (odwrot1 == false) {
                    Button_Clicked(C7);
                } else {
                    Button_Clicked(F2);
                }
                break;
            case "C8":
                if (odwrot1 == false) {
                    Button_Clicked(C8);
                } else {
                    Button_Clicked(F1);
                }
                break;
            case "D1":
                if (odwrot1 == false) {
                    Button_Clicked(D1);
                } else {
                    Button_Clicked(E8);
                }
                break;
            case "D2":
                if (odwrot1 == false) {
                    Button_Clicked(D2);
                } else {
                    Button_Clicked(E7);
                }
                break;
            case "D3":
                if (odwrot1 == false) {
                    Button_Clicked(D3);
                } else {
                    Button_Clicked(E6);
                }
                break;
            case "D4":
                if (odwrot1 == false) {
                    Button_Clicked(D4);
                } else {
                    Button_Clicked(E5);
                }
                break;
            case "D5":
                if (odwrot1 == false) {
                    Button_Clicked(D5);
                } else {
                    Button_Clicked(E4);
                }
                break;
            case "D6":
                if (odwrot1 == false) {
                    Button_Clicked(D6);
                } else {
                    Button_Clicked(E3);
                }
                break;
            case "D7":
                if (odwrot1 == false) {
                    Button_Clicked(D7);
                } else {
                    Button_Clicked(E2);
                }
                break;
            case "D8":
                if (odwrot1 == false) {
                    Button_Clicked(D8);
                } else {
                    Button_Clicked(E1);
                }
                break;
            case "E1":
                if (odwrot1 == false) {
                    Button_Clicked(E1);
                } else {
                    Button_Clicked(D8);
                }
                break;
            case "E2":
                if (odwrot1 == false) {
                    Button_Clicked(E2);
                } else {
                    Button_Clicked(D7);
                }
                break;
            case "E3":
                if (odwrot1 == false) {
                    Button_Clicked(E3);
                } else {
                    Button_Clicked(D6);
                }
                break;
            case "E4":
                if (odwrot1 == false) {
                    Button_Clicked(E4);
                } else {
                    Button_Clicked(D5);
                }
                break;
            case "E5":
                if (odwrot1 == false) {
                    Button_Clicked(E5);
                } else {
                    Button_Clicked(D4);
                }
                break;
            case "E6":
                if (odwrot1 == false) {
                    Button_Clicked(E6);
                } else {
                    Button_Clicked(D3);
                }
                break;
            case "E7":
                if (odwrot1 == false) {
                    Button_Clicked(E7);
                } else {
                    Button_Clicked(D2);
                }
                break;
            case "E8":
                if (odwrot1 == false) {
                    Button_Clicked(E8);
                } else {
                    Button_Clicked(D1);
                }
                break;
            case "F1":
                if (odwrot1 == false) {
                    Button_Clicked(F1);
                } else {
                    Button_Clicked(C8);
                }
                break;
            case "F2":
                if (odwrot1 == false) {
                    Button_Clicked(F2);
                } else {
                    Button_Clicked(C7);
                }
                break;
            case "F3":
                if (odwrot1 == false) {
                    Button_Clicked(F3);
                } else {
                    Button_Clicked(C6);
                }
                break;
            case "F4":
                if (odwrot1 == false) {
                    Button_Clicked(F4);
                } else {
                    Button_Clicked(C5);
                }
                break;
            case "F5":
                if (odwrot1 == false) {
                    Button_Clicked(F5);
                } else {
                    Button_Clicked(C4);
                }
                break;
            case "F6":
                if (odwrot1 == false) {
                    Button_Clicked(F6);
                } else {
                    Button_Clicked(C3);
                }
                break;
            case "F7":
                if (odwrot1 == false) {
                    Button_Clicked(F7);
                } else {
                    Button_Clicked(C2);
                }
                break;
            case "F8":
                if (odwrot1 == false) {
                    Button_Clicked(F8);
                } else {
                    Button_Clicked(C1);
                }
                break;
            case "G1":
                if (odwrot1 == false) {
                    Button_Clicked(G1);
                } else {
                    Button_Clicked(B8);
                }
                break;
            case "G2":
                if (odwrot1 == false) {
                    Button_Clicked(G2);
                } else {
                    Button_Clicked(B7);
                }
                break;
            case "G3":
                if (odwrot1 == false) {
                    Button_Clicked(G3);
                } else {
                    Button_Clicked(B6);
                }
                break;
            case "G4":
                if (odwrot1 == false) {
                    Button_Clicked(G4);
                } else {
                    Button_Clicked(B5);
                }
                break;
            case "G5":
                if (odwrot1 == false) {
                    Button_Clicked(G5);
                } else {
                    Button_Clicked(B4);
                }
                break;
            case "G6":
                if (odwrot1 == false) {
                    Button_Clicked(G6);
                } else {
                    Button_Clicked(B3);
                }
                break;
            case "G7":
                if (odwrot1 == false) {
                    Button_Clicked(G7);
                } else {
                    Button_Clicked(B2);
                }
                break;
            case "G8":
                if (odwrot1 == false) {
                    Button_Clicked(G8);
                } else {
                    Button_Clicked(B1);
                }
                break;
            case "H1":
                if (odwrot1 == false) {
                    Button_Clicked(H1);
                } else {
                    Button_Clicked(A8);
                }
                break;
            case "H2":
                if (odwrot1 == false) {
                    Button_Clicked(H2);
                } else {
                    Button_Clicked(A7);
                }
                break;
            case "H3":
                if (odwrot1 == false) {
                    Button_Clicked(H3);
                } else {
                    Button_Clicked(A6);
                }
                break;
            case "H4":
                if (odwrot1 == false) {
                    Button_Clicked(H4);
                } else {
                    Button_Clicked(A5);
                }
                break;
            case "H5":
                if (odwrot1 == false) {
                    Button_Clicked(H5);
                } else {
                    Button_Clicked(A4);
                }
                break;
            case "H6":
                if (odwrot1 == false) {
                    Button_Clicked(H6);
                } else {
                    Button_Clicked(A3);
                }
                break;
            case "H7":
                if (odwrot1 == false) {
                    Button_Clicked(H7);
                } else {
                    Button_Clicked(A2);
                }
                break;
            case "H8":
                if (odwrot1 == false) {
                    Button_Clicked(H8);
                } else {
                    Button_Clicked(A1);
                }
                break;
            default:
                break;
        }
    }

    public class animacja2 {

        String nazwaB;
        Timer czasowy;
        public int lina = 0;
        JButton przycisk;
        char znak;

        public animacja2(int czasy, boolean polestart, boolean zmien, JButton sender, String nazwa, char symbol) {
            czasowy = new Timer();
            nazwaB = nazwa;
            znak = symbol;
            przycisk = dobierzprzycisk(nazwaB, false);
            czasowy.schedule(new limit(polestart, zmien, znak), 0, 250 * czasy);
        }

        public class limit extends TimerTask {

            char tulis;

            public limit(boolean polestart, boolean zmien, char tulis) {
                this.tulis = tulis;
            }

            @Override
            public void run() {
                lina = lina + 1;
                if (lina % 2 == 0) {
                    przycisk.setIcon(Cursor);
                } else {
                    switch (tulis) {
                        case ' ':
                            przycisk.setIcon(null);
                            break;
                        case 'p':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                            break;
                        case 'P':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                            break;
                        case 'N':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                            break;
                        case 'n':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                            break;
                        case 'B':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                            break;
                        case 'b':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                            break;
                        case 'R':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                            break;
                        case 'r':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                            break;
                        case 'Q':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                            break;
                        case 'q':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                            break;
                        case 'K':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                            break;
                        case 'k':
                            przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                            break;
                    }
                    if (polestart == false) {
                        if (lokalS[0] == lokalK[0] && lokalS[1] == lokalK[1]) {
                            przycisk.setIcon(Cursor);
                        } else {
                            switch (tulis) {
                                case ' ':
                                    przycisk.setIcon(null);
                                    break;
                                case 'p':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                    break;
                                case 'P':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                    break;
                                case 'N':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                    break;
                                case 'n':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                    break;
                                case 'B':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                    break;
                                case 'b':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                    break;
                                case 'R':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                    break;
                                case 'r':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                    break;
                                case 'Q':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                    break;
                                case 'q':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                    break;
                                case 'K':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                    break;
                                case 'k':
                                    przycisk.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                    break;
                            }
                        }
                        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                        czasowy.cancel();
                    }
                }
            }
        }
    }

    /**
     * odpowiada za animowanie wybranej figury
     */
    public class animacja {

        String nazwaB;
        Timer czasowy;
        public int lina = 0;
        JButton przycisk;

        public animacja(int czasy, boolean polestart, boolean zmien, JButton sender, String nazwa) {
            czasowy = new Timer();
            nazwaB = nazwa;
            przycisk = dobierzprzycisk(nazwaB, false);
            czasowy.schedule(new limit(polestart, zmien), 0, 250L * czasy);
        }

        public class limit extends TimerTask {

            public limit(boolean polestart, boolean zmien) {
            }

            @Override
            public void run() {
                lina = lina + 1;
                if (lina % 2 == 0) {
                    przycisk.setIcon(Cursor);
                } else {
                    przycisk.setIcon(null);
                    if (!polestart) {
                        if (lokalS[0] == lokalK[0] && lokalS[1] == lokalK[1]) {
                            przycisk.setIcon(Cursor);
                        } else {
                            przycisk.setIcon(null);
                        }
                        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                        czasowy.cancel();
                    }
                }
            }
        }
    }

    /**
     * wywołuje procedurę poddania partii
     */
    void kapitulacja() {
        if (!siec) {
            if (tryb != 2) {
                if (ruchB) {
                    koncowe = "BIALE:0, CZARNE:1   \n";
                    biale = "0";
                    czarne = "1";
                    JOptionPane.showMessageDialog(rootPane, "KONIEC CZARNE WYGRAŁY", "KONIEC GRY",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    koncowe = "BIALE:1, CZARNE:0  \n";
                    biale = "1";
                    czarne = "0";
                    JOptionPane.showMessageDialog(rootPane, "KONIEC BIAŁE WYGRAŁY", "KONIEC GRY",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (ruchB) {
                    biale = "0";
                    if (movenr <= 25) {
                        czarne = "5";
                    }
                    if (movenr > 25 && movenr <= 40) {
                        czarne = "4";
                    }
                    if (movenr > 40 && movenr <= 55) {
                        czarne = "3";
                    }
                    if (movenr > 55) {
                        czarne = "2";
                    }
                    koncowe = "BIALE:0, CZARNE:" + czarne + "   \n";
                    JOptionPane.showMessageDialog(rootPane, "KONIEC CZARNE WYGRAŁY", "KONIEC GRY",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (movenr <= 25) {
                        biale = "5";
                    }
                    if (movenr > 25 && movenr <= 40) {
                        biale = "4";
                    }
                    if (movenr > 40 && movenr <= 55) {
                        biale = "3";
                    }
                    if (movenr > 55) {
                        biale = "2";
                    }
                    koncowe = "BIALE:" + biale + ", CZARNE:0  \n";
                    czarne = "0";
                    JOptionPane.showMessageDialog(rootPane, "KONIEC BIAŁE WYGRAŁY", "KONIEC GRY",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            int zwrot_numeru;
            if (!ustawka) {
                String baza = "kronika_szachowa";
                Connection polaczenie = lacze_z_baza.polacz(baza);
                assert polaczenie != null;
                lacze_z_baza.stworzTabele(polaczenie, baza);
                lacze_z_baza.dodajDane(baza, biale, czarne);
                zwrot_numeru = uzyskajid(baza);
                try (PrintWriter plik1 = new PrintWriter(new FileWriter("spis partii nr. " + (zwrot_numeru) + ".txt", true))) {
                    jTextArea3.write(plik1);
                    plik1.println();
                    if (SI_ON) {
                        plik1.println("Łączny czas SI: " + laczny_czas + " s");
                        plik1.println("Średni czas SI: " + (float) (laczny_czas / movenr) + " s");
                    }
                    plik1.println();
                    plik1.println();

                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                if (tryb != 2) {
                    try (PrintWriter plik = new PrintWriter(new FileWriter("wyniki.txt", true))) {
                        plik.println(koncowe);

                    } catch (IOException ex) {
                        Logger.getLogger(SzachowaArena.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try (PrintWriter plik = new PrintWriter(new FileWriter("wynik_szach_matynr. " + (zwrot_numeru) + ".txt", true))) {
                        plik.println(koncowe);

                    } catch (IOException ex) {
                        Logger.getLogger(SzachowaArena.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                String baza = "kronika_ustawek";
                Connection polaczenie = lacze_z_baza.polacz(baza);
                assert polaczenie != null;
                lacze_z_baza.stworzTabele(polaczenie, baza);
                lacze_z_baza.dodajDane(baza, biale, czarne);
                zwrot_numeru = uzyskajid(baza);
                try (PrintWriter plik1 = new PrintWriter(new FileWriter("partie ustawionenr. " + (zwrot_numeru) + ".txt", true))) {
                    if (pierwsza_kolej) {
                        plik1.println("Pierwszy ruch mają białe");
                    } else {
                        plik1.println("Pierwszy ruch mają czarne");
                    }
                    for (int i = 7; i > -1; i--) {
                        for (int j = 0; j < 8; j++) {
                            plik1.print("[" + pozycja_ustawki[i][j] + "]");
                        }
                        plik1.println();
                    }
                    jTextArea3.write(plik1);
                    plik1.println();
                    if (SI_ON) {
                        plik1.println("Łączny czas SI: " + laczny_czas + " s");
                        plik1.println("Średni czas SI: " + (float) (laczny_czas / movenr) + " s");
                    }
                    plik1.println();
                    plik1.println();
                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                try (PrintWriter plik = new PrintWriter(new FileWriter("wyniki ustawek.txt", true))) {
                    plik.println(koncowe);

                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            if (ruchB) {
                JOptionPane.showMessageDialog(rootPane, "czarne wygrywaja");
                kap = "czarne";
            } else {
                JOptionPane.showMessageDialog(rootPane, "biale wygrywaja");
                kap = "biale";
            }
            if (organizator) {
                try {
                    server.close();
                    socket.close();
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    socket.close();
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!siec) {
            Object[] opcje_rewanzu = {"tak", "nie"};
            int opcja_rewanzu = JOptionPane.showOptionDialog(null, "NASTĘPNA GRA?", "REAWNŻ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rewanzu, null);
            if (opcja_rewanzu == 0) {
                try {
                    final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                    final File currentJar = new File(SzachowaArena.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    final ArrayList<String> command = new ArrayList<>();
                    command.add(javaBin);
                    command.add("-jar");
                    command.add(currentJar.getPath());
                    final ProcessBuilder builder = new ProcessBuilder(command);
                    builder.start();
                } catch (URISyntaxException | IOException ex1) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        System.exit(0);
    }

    public int uzyskajid(String baza) {
        int wynik;
        int ic = 0;
        Statement stat;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            String szukajSQL = "SELECT ID FROM " + baza + " WHERE ID=(  SELECT MAX(ID) FROM " + baza + ");";
            try (ResultSet rezultacik = stat.executeQuery(szukajSQL)) {
                while (rezultacik.next()) {
                    ic = ((Number) rezultacik.getObject(1)).intValue();
                }
            }

            wynik = ic;
            System.out.println("id=" + wynik);
            if (wynik == 0) {
                return 1;
            }
        } catch (ClassNotFoundException | SQLException e) {
            return 1;
        }
        return wynik;
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Zwraca odpowiedni przycisk zależnie od nazwy pola
     *
     * @param nazwabuttona nazwa obsługiwanego pola
     * @param obrocona czy plansza obrocona
     * @return zwraca odpowiedni przycisk
     */
    public JButton dobierzprzycisk(String nazwabuttona, boolean obrocona) {
        switch (nazwabuttona) {
            case "A1":
                return obrocona == false ? A1 : H8;
            case "A2":
                return obrocona == false ? A2 : H7;
            case "A3":
                return obrocona == false ? A3 : H6;
            case "A4":
                return obrocona == false ? A4 : H5;
            case "A5":
                return obrocona == false ? A5 : H4;
            case "A6":
                return obrocona == false ? A6 : H3;
            case "A7":
                return obrocona == false ? A7 : H2;
            case "A8":
                return obrocona == false ? A8 : H1;
            case "B1":
                return obrocona == false ? B1 : G8;
            case "B2":
                return obrocona == false ? B2 : G7;
            case "B3":
                return obrocona == false ? B3 : G6;
            case "B4":
                return obrocona == false ? B4 : G5;
            case "B5":
                return obrocona == false ? B5 : G4;
            case "B6":
                return obrocona == false ? B6 : G3;
            case "B7":
                return obrocona == false ? B7 : G2;
            case "B8":
                return obrocona == false ? B8 : G1;
            case "C1":
                return obrocona == false ? C1 : F8;
            case "C2":
                return obrocona == false ? C2 : F7;
            case "C3":
                return obrocona == false ? C3 : F6;
            case "C4":
                return obrocona == false ? C4 : F5;
            case "C5":
                return obrocona == false ? C5 : F4;
            case "C6":
                return obrocona == false ? C6 : F3;
            case "C7":
                return obrocona == false ? C7 : F2;
            case "C8":
                return obrocona == false ? C8 : F1;
            case "D1":
                return obrocona == false ? D1 : E8;
            case "D2":
                return obrocona == false ? D2 : E7;
            case "D3":
                return obrocona == false ? D3 : E6;
            case "D4":
                return obrocona == false ? D4 : E5;
            case "D5":
                return obrocona == false ? D5 : E4;
            case "D6":
                return obrocona == false ? D6 : E3;
            case "D7":
                return obrocona == false ? D7 : E2;
            case "D8":
                return obrocona == false ? D8 : E1;
            case "E1":
                return obrocona == false ? E1 : D8;
            case "E2":
                return obrocona == false ? E2 : D7;
            case "E3":
                return obrocona == false ? E3 : D6;
            case "E4":
                return obrocona == false ? E4 : D5;
            case "E5":
                return obrocona == false ? E5 : D4;
            case "E6":
                return obrocona == false ? E6 : D3;
            case "E7":
                return obrocona == false ? E7 : D2;
            case "E8":
                return obrocona == false ? E8 : D1;
            case "F1":
                return obrocona == false ? F1 : C8;
            case "F2":
                return obrocona == false ? F2 : C7;
            case "F3":
                return obrocona == false ? F3 : C6;
            case "F4":
                return obrocona == false ? F4 : C5;
            case "F5":
                return obrocona == false ? F5 : C4;
            case "F6":
                return obrocona == false ? F6 : C3;
            case "F7":
                return obrocona == false ? F7 : C2;
            case "F8":
                return obrocona == false ? F8 : C1;
            case "G1":
                return obrocona == false ? G1 : B8;
            case "G2":
                return obrocona == false ? G2 : B7;
            case "G3":
                return obrocona == false ? G3 : B6;
            case "G4":
                return obrocona == false ? G4 : B5;
            case "G5":
                return obrocona == false ? G5 : B4;
            case "G6":
                return obrocona == false ? G6 : B3;
            case "G7":
                return obrocona == false ? G7 : B2;
            case "G8":
                return obrocona == false ? G8 : B1;
            case "H1":
                return obrocona == false ? H1 : A8;
            case "H2":
                return obrocona == false ? H2 : A7;
            case "H3":
                return obrocona == false ? H3 : A6;
            case "H4":
                return obrocona == false ? H4 : A5;
            case "H5":
                return obrocona == false ? H5 : A4;
            case "H6":
                return obrocona == false ? H6 : A3;
            case "H7":
                return obrocona == false ? H7 : A2;
            case "H8":
                return obrocona == false ? H8 : A1;
        }
        return null;
    }

    /**
     * Uruchamia provcedurę remisu
     */
    private void remis() {
        jButton68.setEnabled(true);
        jButton69.setEnabled(false);
        jButton70.setEnabled(false);
        if (tryb != 2) {
            koncowe = "BIALE:0.5, CZARNE:0.5   \n";
            biale = "0.5";
            czarne = "0.5";
        } else {
            koncowe = "BIALE:0, CZARNE:0   \n";
            biale = "0";
            czarne = "0";
        }
        JOptionPane.showMessageDialog(rootPane, "KONIEC REMIS", "KONIEC GRY",
                JOptionPane.INFORMATION_MESSAGE);

        if (tryb == 2) {
            try (PrintWriter plik = new PrintWriter(new FileWriter("wynik_szach_maty.txt", true))) {
                plik.println(koncowe);

            } catch (IOException ex) {
                Logger.getLogger(SzachowaArena.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!ustawka) {
            String baza = "kronika_szachowa";
            Connection polaczenie = lacze_z_baza.polacz(baza);
            assert polaczenie != null;
            lacze_z_baza.stworzTabele(polaczenie, baza);
            lacze_z_baza.dodajDane(baza, biale, czarne);
            int zwrot_numeru = uzyskajid(baza);
            try (PrintWriter plik1 = new PrintWriter(new FileWriter("spis partii nr. " + (zwrot_numeru) + ".txt", true))) {
                jTextArea3.write(plik1);
                plik1.println();
                if (SI_ON) {
                    plik1.println("Łączny czas SI: " + laczny_czas + " s");
                    plik1.println("Średni czas SI: " + (float) (laczny_czas / movenr) + " s");
                }
                plik1.println();
                plik1.println();

            } catch (IOException ex) {
                Logger.getLogger(SzachowaArena.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            if (tryb != 2) {
                try (PrintWriter plik = new PrintWriter(new FileWriter("wyniki.txt", true))) {
                    plik.println(koncowe);

                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try (PrintWriter plik = new PrintWriter(new FileWriter("wynik_szach_matynr. " + (zwrot_numeru) + ".txt", true))) {
                    plik.println(koncowe);

                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            String baza = "kronika_ustawek";
            Connection polaczenie = lacze_z_baza.polacz(baza);
            assert polaczenie != null;
            lacze_z_baza.stworzTabele(polaczenie, baza);
            lacze_z_baza.dodajDane(baza, biale, czarne);
            int zwrot_numeru = uzyskajid(baza);
            try (PrintWriter plik1 = new PrintWriter(new FileWriter("partie ustawionenr. " + (zwrot_numeru) + ".txt", true))) {
                if (pierwsza_kolej) {
                    plik1.println("Pierwszy ruch mają białe");
                } else {
                    plik1.println("Pierwszy ruch mają czarne");
                }
                for (int i = 7; i > -1; i--) {
                    for (int j = 0; j < 8; j++) {
                        plik1.print("[" + pozycja_ustawki[i][j] + "]");
                    }
                    plik1.println();
                }
                jTextArea3.write(plik1);
                plik1.println();
                if (SI_ON) {
                    plik1.println("Łączny czas SI: " + laczny_czas + " s");
                    plik1.println("Średni czas SI: " + (float) (laczny_czas / movenr) + " s");
                }
                plik1.println();
                plik1.println();
            } catch (IOException ex) {
                Logger.getLogger(SzachowaArena.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try (PrintWriter plik = new PrintWriter(new FileWriter("wyniki ustawek.txt", true))) {
                plik.println(koncowe);

            } catch (IOException ex) {
                Logger.getLogger(SzachowaArena.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (siec) {
            try {
                msgwy = "Zgadzam sie na remis";
                out.writeUTF(msgwy);
                jTextArea2.setText(jTextArea2.getText().trim() + "\n ja: " + msgwy + "\n");
                jTextField1.setText("");
            } catch (IOException ignored) {
            }
            if (organizator) {
                try {
                    server.close();
                    socket.close();
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    socket.close();
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!siec) {
            Object[] opcje_rewanzu = {"tak", "nie"};
            int opcja_rewanzu = JOptionPane.showOptionDialog(null, "NASTĘPNA GRA?", "REAWNŻ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rewanzu, null);
            if (opcja_rewanzu == 0) {
                try {
                    final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                    final File currentJar = new File(SzachowaArena.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    final ArrayList<String> command = new ArrayList<>();
                    command.add(javaBin);
                    command.add("-jar");
                    command.add(currentJar.getPath());
                    final ProcessBuilder builder = new ProcessBuilder(command);
                    builder.start();
                } catch (URISyntaxException | IOException ex1) {
                    Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        System.exit(0);
    }

    private void Rusz_przytul(Object source) {
        JButton BUTTON = (JButton) source;
        byte pomx = 0, pomy = 0;
        if (polestart == false) {

            BUTTON.setBorder(new LineBorder(rama, 4));
            switch ((BUTTON.getName().charAt(0))) {
                case 'A':
                    pomx = 1;
                    break;
                case 'B':
                    pomx = 2;
                    break;
                case 'C':
                    pomx = 3;
                    break;
                case 'D':
                    pomx = 4;
                    break;
                case 'E':
                    pomx = 5;
                    break;
                case 'F':
                    pomx = 6;
                    break;
                case 'G':
                    pomx = 7;
                    break;
                case 'H':
                    pomx = 8;
                    break;
            }
            switch ((BUTTON.getName().charAt(1))) {
                case '1':
                    pomy = 1;
                    break;
                case '2':
                    pomy = 2;
                    break;
                case '3':
                    pomy = 3;
                    break;
                case '4':
                    pomy = 4;
                    break;
                case '5':
                    pomy = 5;
                    break;
                case '6':
                    pomy = 6;
                    break;
                case '7':
                    pomy = 7;
                    break;
                case '8':
                    pomy = 8;
                    break;
            }
            lokalS[0] = (byte) pomx;
            lokalS[1] = (byte) pomy;
            Cursor = BUTTON.getIcon();
            symbole[0] = szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][0];
            symbole[1] = szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][1];
            symbol = ruchB == true ? symbole[0] : symbole[1];
            BUTTON.setIcon(null);
            jLabel12.setIcon(Cursor);
            jLabel12.setText(BUTTON.getName());
            if (szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] != ' ') {
                start = BUTTON.getName();
                polestart = true;
                if ((symbol == 'P' | symbol == 'p')) {
                    promo = ' ';
                }
                koniecanimacji = false;
            } else {
                if ((opcje_pomoc == 0 || opcje_pomoc == 2) && ((ruchB == true && symbole[0] == ' ' && symbole[1] != ' ') || (ruchB == true && symbole[1] == ' ' && symbole[0] != ' '))) {
                    JOptionPane.showMessageDialog(rootPane, ruchB == true ? "Aktualny ruch mają białe" : "Aktualny ruch mają czarne",
                            "Błąd wyboru", JOptionPane.WARNING_MESSAGE);
                    BUTTON.setBorder(null);
                }
            }
        } else {
            switch ((BUTTON.getName().charAt(0))) {
                case 'A':
                    pomx = 1;
                    break;
                case 'B':
                    pomx = 2;
                    break;
                case 'C':
                    pomx = 3;
                    break;
                case 'D':
                    pomx = 4;
                    break;
                case 'E':
                    pomx = 5;
                    break;
                case 'F':
                    pomx = 6;
                    break;
                case 'G':
                    pomx = 7;
                    break;
                case 'H':
                    pomx = 8;
                    break;
            }
            switch ((BUTTON.getName().charAt(1))) {
                case '1':
                    pomy = 1;
                    break;
                case '2':
                    pomy = 2;
                    break;
                case '3':
                    pomy = 3;
                    break;
                case '4':
                    pomy = 4;
                    break;
                case '5':
                    pomy = 5;
                    break;
                case '6':
                    pomy = 6;
                    break;
                case '7':
                    pomy = 7;
                    break;
                case '8':
                    pomy = 8;
                    break;
            }
            lokalK[0] = pomx;
            lokalK[1] = pomy;
            if (BUTTON.getIcon() == null) {
                if (lokalK[0] == lokalS[0] && lokalK[1] == lokalS[1]) {
                    BUTTON.setBorder(null);
                    if (liczba_usciskow == 0) {
                        zmien = false;
                        promo = ' ';
                        koniecanimacji = true;
                        BUTTON.setIcon(Cursor);
                        polestart = false;
                        wyk = false;
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][0] = symbole[0];
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][1] = symbole[1];
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = symbol;
                        for (int i = 0; i < 8; i++) {
                            for (int j = 7; j >= 0; j--) {
                                System.out.print("[" + (szachownica_pokoj[i][j][0] != ' ' ? szachownica_pokoj[i][j][0] : ' ') + "|" + (szachownica_pokoj[i][j][1] != ' ' ? szachownica_pokoj[i][j][1] : ' ') + "]");
                            }
                            System.out.println();
                        }
                    } else {
                        styl(1, 1, kolor_plansza);
                        char tmp = szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1];
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = symbol;
                        symbol = tmp;
                        switch (symbol) {
                            case 'K':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                break;
                            case 'Q':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                break;
                            case 'R':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                break;
                            case 'B':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                break;
                            case 'N':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                break;
                            case 'P':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                break;
                            case 'k':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                break;
                            case 'q':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                break;
                            case 'r':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                break;
                            case 'b':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                break;
                            case 'n':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                break;
                            case 'p':
                                jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                break;
                            default:
                                jLabel12.setIcon(null);
                                break;
                        }

                        liczba_usciskow = liczba_usciskow - 1;
                        start = hug_list.get(hug_list.size() - 1).substring(0, 2);
                        hug_list.remove(hug_list.size() - 1);
                        koniecanimacji = false;

                        switch ((BUTTON.getName().charAt(0))) {
                            case 'A':
                                pomx = 1;
                                break;
                            case 'B':
                                pomx = 2;
                                break;
                            case 'C':
                                pomx = 3;
                                break;
                            case 'D':
                                pomx = 4;
                                break;
                            case 'E':
                                pomx = 5;
                                break;
                            case 'F':
                                pomx = 6;
                                break;
                            case 'G':
                                pomx = 7;
                                break;
                            case 'H':
                                pomx = 8;
                                break;
                        }
                        switch ((BUTTON.getName().charAt(1))) {
                            case '1':
                                pomy = 1;
                                break;
                            case '2':
                                pomy = 2;
                                break;
                            case '3':
                                pomy = 3;
                                break;
                            case '4':
                                pomy = 4;
                                break;
                            case '5':
                                pomy = 5;
                                break;
                            case '6':
                                pomy = 6;
                                break;
                            case '7':
                                pomy = 7;
                                break;
                            case '8':
                                pomy = 8;
                                break;
                        }
                        lokalS[0] = (byte) pomx;
                        lokalS[1] = (byte) pomy;
                    }
                } else {
                    if ((symbol == 'K' || symbol == 'k')
                            && ((ruchB == true && kingrochB == true && (((lokalS[0] - lokalK[0]) == -2 && szachownica_pokoj[0][5][0] == ' ' && szachownica_pokoj[0][5][1] == ' ' && szachownica_pokoj[0][6][0] == ' ' && szachownica_pokoj[0][6][1] == ' ' && wright == true && szachownica_pokoj[0][7][1] == ' ')
                            || (lokalS[0] - lokalK[0] == 2 && szachownica_pokoj[0][3][0] == ' ' && szachownica_pokoj[0][2][0] == ' ' && szachownica_pokoj[0][1][0] == ' ' && szachownica_pokoj[0][3][1] == ' ' && szachownica_pokoj[0][2][1] == ' ' && szachownica_pokoj[0][1][1] == ' ' && wleft == true && szachownica_pokoj[0][0][1] == ' ')))
                            || (ruchB == false && kingrochC == true && (((lokalS[0] - lokalK[0]) == -2 && szachownica_pokoj[7][5][1] == ' ' && szachownica_pokoj[7][6][1] == ' ' && szachownica_pokoj[7][5][0] == ' ' && szachownica_pokoj[7][6][0] == ' ' && bright == true && szachownica_pokoj[7][7][0] == ' ')
                            || (lokalS[0] - lokalK[0] == 2 && szachownica_pokoj[7][3][0] == ' ' && szachownica_pokoj[7][2][0] == ' ' && szachownica_pokoj[7][1][0] == ' ' && szachownica_pokoj[7][3][1] == ' ' && szachownica_pokoj[7][2][1] == ' ' && szachownica_pokoj[7][1][1] == ' ' && bleft == true && szachownica_pokoj[7][0][0] == ' '))))) {
                        char[][][] kontrolna = szachownica_pokoj;
                        if (ruchB == true) {
                            if (lokalS[0] - lokalK[0] > 0) {

                                szachownica_pokoj[0][4][0] = ' ';
                                szachownica_pokoj[0][0][0] = ' ';
                                szachownica_pokoj[0][2][0] = 'K';
                                szachownica_pokoj[0][3][0] = 'R';

                            } else {

                                szachownica_pokoj[0][4][0] = ' ';
                                szachownica_pokoj[0][7][0] = ' ';
                                szachownica_pokoj[0][6][0] = 'K';
                                szachownica_pokoj[0][5][0] = 'R';

                            }
                            kingrochB = false;
                        } else {
                            if (lokalS[0] - lokalK[0] > 0) {

                                szachownica_pokoj[7][4][1] = ' ';
                                szachownica_pokoj[7][0][1] = ' ';
                                szachownica_pokoj[7][2][1] = 'k';
                                szachownica_pokoj[7][3][1] = 'r';

                            } else {

                                szachownica_pokoj[7][4][1] = ' ';
                                szachownica_pokoj[7][7][1] = ' ';
                                szachownica_pokoj[7][6][1] = 'k';
                                szachownica_pokoj[7][5][1] = 'r';

                            }
                            kingrochC = false;
                        }
                        stop = BUTTON.getName();
                        polestart = false;
                        koniecanimacji = true;
                        if (ruchB == false) {
                            movenr = movenr + 1;
                        }
                        czysc_rame();
                        ruchB = ruchB != true;
                        Cursor = null;

                    } else {
                        boolean dozwolony = Ruch_pokoj.ruch(lokalS, lokalK, symbol, szachownica_pokoj, ruchB, przelotcan, kol, ruchB == true ? symbole[1] : symbole[0]);
                        System.out.println("Accept " + dozwolony);
                        System.out.println(lokalK[0] + ":" + lokalK[1]);
                        if (dozwolony == true) {
                            if (symbole[ruchB == true ? 1 : 0] != ' ') {
                                liczba_usciskow = 0;
                                stop = BUTTON.getName();
                                koniecanimacji = true;
                                zmien = false;
                                BUTTON.setIcon(Cursor);
                                polestart = false;
                                wyk = false;
                            } else {
                                if (symbol == 'R' || symbol == 'r') {
                                    if (start.equals("A1")) {
                                        wleft = false;
                                    }
                                    if (start.equals("A8")) {
                                        bleft = false;
                                    }
                                    if (start.equals("H1")) {
                                        wright = false;
                                    }
                                    if (start.equals("H8")) {
                                        bright = false;
                                    }
                                    if (!bright && !bleft) {
                                        kingrochC = false;
                                    }
                                    if (!wright && !wleft) {
                                        kingrochB = false;
                                    }
                                }
                                if (symbol == 'K' || symbol == 'k') {
                                    if (start.equals("E1")) {
                                        kingrochB = false;
                                    }
                                    if (start.equals("E8")) {
                                        kingrochC = false;
                                    }
                                }
                                if ((symbol == 'P' && lokalS[0] == lokalK[0] && lokalS[1] - lokalK[1] == -2)
                                        || (symbol == 'p' && lokalS[0] == lokalK[0] && lokalS[1] - lokalK[1] == 2)) {
                                    przelotcan = true;
                                    kol = lokalS[0];
                                } else {
                                    if (przelotcan == true && (symbol == 'p' || symbol == 'P') && Math.abs(lokalS[1] - lokalK[1]) == 1
                                            && ((ruchB == true && lokalS[1] == 5) || (ruchB == false && lokalS[1] == 4))) {

                                    } else {
                                        kol = 0;
                                    }
                                }
                                if (symbol == 'P' || symbol == 'p') {
                                    if ((przelotcan == true && ((lokalK[0] == kol && symbol == 'P' && lokalS[1] - lokalK[1] == -1 && lokalS[0] != lokalK[0] && lokalS[1] == 5)
                                            || (lokalK[0] == kol && symbol == 'p' && lokalS[1] - lokalK[1] == 1 && lokalS[0] != lokalK[0] && lokalS[1] == 4)))) {

                                        kon = true;
                                        przelot = false;
                                        dokonanoEP = true;
                                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = symbol;
                                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 1 : 0] = symbol == 'P' ? 'p' : 'P';
                                        if ((ruchB == true && szachownica_pokoj[4][lokalK[0] - 1][0] == ' ')
                                                || (ruchB == false && szachownica_pokoj[3][lokalK[0] - 1][1] == ' ')) {
                                            liczba_usciskow = 0;
                                        } else {
                                            liczba_usciskow = liczba_usciskow + 1;
                                        }
                                        switch (lokalK[0]) {
                                            case 1:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][0][1] = ' ';
                                                    szachownica_pokoj[4][0][0] = ' ';
                                                    A5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][0][1] = ' ';
                                                    szachownica_pokoj[3][0][0] = ' ';
                                                    A4.setIcon(null);
                                                }
                                                break;
                                            case 2:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][1][1] = ' ';
                                                    szachownica_pokoj[4][1][0] = ' ';
                                                    B5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][1][1] = ' ';
                                                    szachownica_pokoj[3][1][0] = ' ';
                                                    B4.setIcon(null);
                                                }
                                                break;
                                            case 3:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][2][1] = ' ';
                                                    szachownica_pokoj[4][2][0] = ' ';
                                                    C5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][2][1] = ' ';
                                                    szachownica_pokoj[3][2][0] = ' ';
                                                    C4.setIcon(null);
                                                }
                                                break;
                                            case 4:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][3][1] = ' ';
                                                    szachownica_pokoj[4][3][0] = ' ';
                                                    D5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][3][1] = ' ';
                                                    szachownica_pokoj[3][3][0] = ' ';
                                                    D4.setIcon(null);
                                                }
                                                break;
                                            case 5:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][4][1] = ' ';
                                                    szachownica_pokoj[4][4][0] = ' ';
                                                    E5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][4][1] = ' ';
                                                    szachownica_pokoj[3][4][0] = ' ';
                                                    E4.setIcon(null);
                                                }
                                                break;
                                            case 6:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][5][1] = ' ';
                                                    szachownica_pokoj[4][5][0] = ' ';
                                                    F5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][5][1] = ' ';
                                                    szachownica_pokoj[3][5][0] = ' ';
                                                    F4.setIcon(null);
                                                }
                                                break;
                                            case 7:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][6][1] = ' ';
                                                    szachownica_pokoj[4][6][0] = ' ';
                                                    G5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][6][1] = ' ';
                                                    szachownica_pokoj[3][6][0] = ' ';
                                                    G4.setIcon(null);
                                                }
                                                break;
                                            case 8:
                                                if (ruchB == true) {
                                                    szachownica_pokoj[4][7][1] = ' ';
                                                    szachownica_pokoj[4][7][0] = ' ';
                                                    H5.setIcon(null);
                                                } else {
                                                    szachownica_pokoj[3][7][1] = ' ';
                                                    szachownica_pokoj[3][7][0] = ' ';
                                                    H4.setIcon(null);
                                                }
                                                break;
                                        }
                                        przelotcan = false;
                                        symbole[ruchB == true ? 1 : 0] = ruchB == true ? 'p' : 'P';
                                        wyk = true;
                                        prze = true;
                                        bicie = true;
                                        if (liczba_usciskow == 0) {
                                            stop = BUTTON.getName();
                                            polestart = false;
                                            koniecanimacji = true;
                                            if (ruchB == false) {
                                                movenr = movenr + 1;
                                            }
                                            Cursor = null;
                                            szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = ' ';
                                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = ruchB == true ? symbole[0] : symbole[1];
                                            styl(1, 1, kolor_plansza);

                                        } else {
                                            stop = BUTTON.getName();
                                            polestart = true;
                                            koniecanimacji = false;
                                            hug_list.add(symbol + "" + start + "*" + stop);
                                            szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = ' ';
                                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = ruchB == true ? symbole[0] : symbole[1];
                                            styl(1, 1, kolor_plansza);
                                        }
                                    }
                                } else {
                                    przelotcan = false;
                                }
                            }
                            stop = BUTTON.getName();
                            polestart = false;
                            koniecanimacji = true;
                            if (ruchB == false) {
                                movenr = movenr + 1;
                            }
                            czysc_rame();
                            Cursor = null;
                            szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = ' ';
                            szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 1 : 0] = ' ';
                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = ruchB == true ? symbole[0] : symbole[1];
                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 1 : 0] = symbole[ruchB == true ? 1 : 0] == ' ' ? ' ' : symbole[ruchB == true ? 1 : 0];
                            styl(1, 1, kolor_plansza);
                            if (liczba_usciskow == 0) {
                                ruchB = ruchB != true;
                            }
                        } else {
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                    /* for (int i = 7; i > -1; i--) {
                        for (int j = 0; j < 8; j++) {
                            System.out.print("[" + (szachownica_pokoj[i][j][0] != ' ' ? szachownica_pokoj[i][j][0] : ' ') + "|" + (szachownica_pokoj[i][j][1] != ' ' ? szachownica_pokoj[i][j][1] : ' ') + "]");
                        }
                        System.out.println();
                    }*/
                }
            } else {
                if (lokalK[0] == lokalS[0] && lokalK[1] == lokalS[1]) {
                    if (liczba_usciskow == 0) {
                        zmien = false;
                        promo = ' ';
                        koniecanimacji = true;
                        BUTTON.setIcon(Cursor);
                        polestart = false;
                        wyk = false;
                        BUTTON.setBorder(null);
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][0] = symbole[0];
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][1] = symbole[1];
                        szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = symbol;
                        /* for (int i = 0; i < 8; i++) {
                            for (int j = 7; j >= 0; j--) {
                                System.out.print("[" + (szachownica_pokoj[i][j][0] != ' ' ? szachownica_pokoj[i][j][0] : ' ') + "|" + (szachownica_pokoj[i][j][1] != ' ' ? szachownica_pokoj[i][j][1] : ' ') + "]");
                            }
                            System.out.println();
                        }*/
                    } else {

                        start = stop;
                        stop = "";
                        switch (hug_list.get(hug_list.size() - 1).charAt(0)) {
                            case 'p':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                break;
                            case 'n':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                break;
                            case 'b':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                break;
                            case 'r':
                                Cursor = (new ImageIcon(this.getClass().getResource("Brook001.png")));
                                break;
                            case 'q':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                break;
                            case 'k':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bking001.png")));
                                break;
                            case 'P':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                break;
                            case 'N':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                break;
                            case 'B':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                break;
                            case 'R':
                                Cursor = (new ImageIcon(this.getClass().getResource("Brook001.png")));
                                break;
                            case 'Q':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                break;
                            case 'K':
                                Cursor = (new ImageIcon(this.getClass().getResource("Bking001.png")));
                                break;
                        }
                        jLabel12.setIcon(Cursor);
                        dobierzprzycisk(start, false).setBorder(null);
                        char temp = szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1];
                        szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = symbol;
                        symbol = temp;
                        polestart = true;
                        start = hug_list.get(hug_list.size() - 1).substring(1, 3);
                        liczba_usciskow = liczba_usciskow - 1;
                        hug_list.remove(hug_list.size() - 1);
                        switch (start.charAt(0)) {
                            case 'A':
                                pomx = 1;
                                break;
                            case 'B':
                                pomx = 2;
                                break;
                            case 'C':
                                pomx = 3;
                                break;
                            case 'D':
                                pomx = 4;
                                break;
                            case 'E':
                                pomx = 5;
                                break;
                            case 'F':
                                pomx = 6;
                                break;
                            case 'G':
                                pomx = 7;
                                break;
                            case 'H':
                                pomx = 8;
                                break;
                        }
                        switch (start.charAt(1)) {
                            case '1':
                                pomy = 1;
                                break;
                            case '2':
                                pomy = 2;
                                break;
                            case '3':
                                pomy = 3;
                                break;
                            case '4':
                                pomy = 4;
                                break;
                            case '5':
                                pomy = 5;
                                break;
                            case '6':
                                pomy = 6;
                                break;
                            case '7':
                                pomy = 7;
                                break;
                            case '8':
                                pomy = 8;
                                break;
                        }
                        lokalS[0] = (byte) pomx;
                        lokalS[1] = (byte) pomy;

                    }
                } else {
                    boolean dozwolony = Ruch_pokoj.ruch(lokalS, lokalK, symbol, szachownica_pokoj, ruchB, przelotcan, kol, ruchB == true ? symbole[1] : symbole[0]);
                    System.out.println("Accept hug" + dozwolony + " " + symbol);
                    if (dozwolony == true) {
                        czysc_rame();
                        if (szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] == ' ') {
                            stop = BUTTON.getName();
                            hug_list.clear();
                            koniecanimacji = true;
                            zmien = false;
                            polestart = false;
                            wyk = false;
                            stop = BUTTON.getName();
                            polestart = false;
                            if (ruchB == false) {
                                movenr = movenr + 1;
                            }
                            if (liczba_usciskow == 0) {
                                szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = ' ';
                            }
                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = symbol;
                            czysc_rame();
                            styl(1, 1, kolor_plansza);
                            liczba_usciskow = 0;
                            Cursor = null;
                            ruchB = ruchB != true;
                            if (szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] == (ruchB == true ? 'K' : 'k')) {
                                JOptionPane.showMessageDialog(rootPane, "KRÓL ZOSTAŁ PRZYTULONY");
                                kapitulacja();
                            }

                        } else {

                            stop = BUTTON.getName();
                            koniecanimacji = true;
                            hug_list.add(symbol + "" + start + "*" + stop);
                            polestart = true;
                            if (liczba_usciskow < 1) {
                                szachownica_pokoj[lokalS[1] - 1][lokalS[0] - 1][ruchB == true ? 0 : 1] = ' ';
                            }

                            liczba_usciskow++;
                            char temp = symbol;
                            symbol = szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1];
                            szachownica_pokoj[lokalK[1] - 1][lokalK[0] - 1][ruchB == true ? 0 : 1] = temp;
                            styl(1, 1, kolor_plansza);
                            lokalS[0] = lokalK[0];
                            lokalS[1] = lokalK[1];
                            lokalK[0] = 0;
                            lokalK[1] = 0;
                            czysc_rame();
                            Cursor = BUTTON.getIcon();
                            switch (symbol) {
                                case 'K':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wking001.png")));
                                    break;
                                case 'Q':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wqueen01.png")));
                                    break;
                                case 'R':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wrook001.png")));
                                    break;
                                case 'B':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wbishop1.png")));
                                    break;
                                case 'N':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wknight1.png")));
                                    break;
                                case 'P':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Wpawn001.png")));
                                    break;
                                case 'k':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bking001.png")));
                                    break;
                                case 'q':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bqueen01.png")));
                                    break;
                                case 'r':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Brook001.png")));
                                    break;
                                case 'b':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bbishop1.png")));
                                    break;
                                case 'n':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bknight1.png")));
                                    break;
                                case 'p':
                                    jLabel12.setIcon(new ImageIcon(this.getClass().getResource("Bpawn001.png")));
                                    break;
                                default:
                                    jLabel12.setIcon(null);
                                    break;
                            }
                            jLabel12.setText("tulacy" + stop);
                            koniecanimacji = false;
                            System.out.println(symbol);
                        }
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
            for (int i = 7; i > -1; i--) {
                for (int j = 0; j < 8; j++) {
                    System.out.print("[" + (szachownica_pokoj[i][j][0] != ' ' ? szachownica_pokoj[i][j][0] : ' ') + "|" + (szachownica_pokoj[i][j][1] != ' ' ? szachownica_pokoj[i][j][1] : ' ') + "]");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
        styl(1, 1, kolor_plansza);
    }

    /**
     * Metoda ta obsługuje działanie przycisków planszy
     *
     * @param pole Przyjmuje za argument wciśnięty przycisk planszy
     */
    void Button_Clicked(Object pole) {
        JButton BUTTON = (JButton) pole;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        byte pomocx = 0, pomocy = 0;
        char pomoc2, pomoc3;
        if (gra == true) {
            if (siec == false || (siec == true && oczekiwanie == false) || wzor == true) {
                if (przelot == true) {
                } else {
                    prze = false;
                    if (roch == true) {
                    } else {
                        if (BUTTON.getIcon() != null || polestart == true) {
                            if (polestart == false) {
                                pomocs = BUTTON.getName();
                                pomoc2 = pomocs.charAt(0);
                                pomoc3 = pomocs.charAt(1);
                                if (odwrot == false) {
                                    switch (pomoc2) {
                                        case 'A':
                                            pomocx = 1;
                                            break;
                                        case 'B':
                                            pomocx = 2;
                                            break;
                                        case 'C':
                                            pomocx = 3;
                                            break;
                                        case 'D':
                                            pomocx = 4;
                                            break;
                                        case 'E':
                                            pomocx = 5;
                                            break;
                                        case 'F':
                                            pomocx = 6;
                                            break;
                                        case 'G':
                                            pomocx = 7;
                                            break;
                                        case 'H':
                                            pomocx = 8;
                                            break;
                                    }
                                    switch (pomoc3) {
                                        case '1':
                                            pomocy = 1;
                                            break;
                                        case '2':
                                            pomocy = 2;
                                            break;
                                        case '3':
                                            pomocy = 3;
                                            break;
                                        case '4':
                                            pomocy = 4;
                                            break;
                                        case '5':
                                            pomocy = 5;
                                            break;
                                        case '6':
                                            pomocy = 6;
                                            break;
                                        case '7':
                                            pomocy = 7;
                                            break;
                                        case '8':
                                            pomocy = 8;
                                            break;
                                    }
                                } else {
                                    switch (pomoc2) {
                                        case 'A':
                                            pomocx = 8;
                                            break;
                                        case 'B':
                                            pomocx = 7;
                                            break;
                                        case 'C':
                                            pomocx = 6;
                                            break;
                                        case 'D':
                                            pomocx = 5;
                                            break;
                                        case 'E':
                                            pomocx = 4;
                                            break;
                                        case 'F':
                                            pomocx = 3;
                                            break;
                                        case 'G':
                                            pomocx = 2;
                                            break;
                                        case 'H':
                                            pomocx = 1;
                                            break;
                                    }
                                    switch (pomoc3) {
                                        case '1':
                                            pomocy = 8;
                                            break;
                                        case '2':
                                            pomocy = 7;
                                            break;
                                        case '3':
                                            pomocy = 6;
                                            break;
                                        case '4':
                                            pomocy = 5;
                                            break;
                                        case '5':
                                            pomocy = 4;
                                            break;
                                        case '6':
                                            pomocy = 3;
                                            break;
                                        case '7':
                                            pomocy = 2;
                                            break;
                                        case '8':
                                            pomocy = 1;
                                            break;
                                    }
                                }
                                lokalS[0] = pomocx;
                                lokalS[1] = pomocy;

                                symbol = ust[pomocy - 1][pomocx - 1];
                                if (symbol == 'K' || symbol == 'Q' || symbol == 'R' || symbol == 'B' || symbol == 'N' || symbol == 'P') {
                                    pomoci1 = 'W';
                                } else {
                                    pomoci1 = 'B';
                                }
                                if ((pomoci1 == 'W' && ruchB == true) || (pomoci1 == 'B' && ruchB == false)) {
                                    zmien = false;
                                    nazwapola = BUTTON.getName();
                                    pomoc5 = BUTTON.getIcon();
                                    Cursor = BUTTON.getIcon();
                                    pomoc2 = pomocs.charAt(0);
                                    start = pomocs;
                                    BUTTON.setIcon(null);
                                    pomoc3 = pomocs.charAt(1);
                                    if (odwrot == false) {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 1;
                                                break;
                                            case 'B':
                                                pomocx = 2;
                                                break;
                                            case 'C':
                                                pomocx = 3;
                                                break;
                                            case 'D':
                                                pomocx = 4;
                                                break;
                                            case 'E':
                                                pomocx = 5;
                                                break;
                                            case 'F':
                                                pomocx = 6;
                                                break;
                                            case 'G':
                                                pomocx = 7;
                                                break;
                                            case 'H':
                                                pomocx = 8;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 1;
                                                break;
                                            case '2':
                                                pomocy = 2;
                                                break;
                                            case '3':
                                                pomocy = 3;
                                                break;
                                            case '4':
                                                pomocy = 4;
                                                break;
                                            case '5':
                                                pomocy = 5;
                                                break;
                                            case '6':
                                                pomocy = 6;
                                                break;
                                            case '7':
                                                pomocy = 7;
                                                break;
                                            case '8':
                                                pomocy = 8;
                                                break;
                                        }
                                    } else {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 8;
                                                break;
                                            case 'B':
                                                pomocx = 7;
                                                break;
                                            case 'C':
                                                pomocx = 6;
                                                break;
                                            case 'D':
                                                pomocx = 5;
                                                break;
                                            case 'E':
                                                pomocx = 4;
                                                break;
                                            case 'F':
                                                pomocx = 3;
                                                break;
                                            case 'G':
                                                pomocx = 2;
                                                break;
                                            case 'H':
                                                pomocx = 1;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 8;
                                                break;
                                            case '2':
                                                pomocy = 7;
                                                break;
                                            case '3':
                                                pomocy = 6;
                                                break;
                                            case '4':
                                                pomocy = 5;
                                                break;
                                            case '5':
                                                pomocy = 4;
                                                break;
                                            case '6':
                                                pomocy = 3;
                                                break;
                                            case '7':
                                                pomocy = 2;
                                                break;
                                            case '8':
                                                pomocy = 1;
                                                break;
                                        }
                                    }
                                    lokalS[0] = pomocx;
                                    lokalS[1] = pomocy;
                                    symbol = ust[pomocy - 1][pomocx - 1];
                                    if ((symbol == 'P' && lokalS[1] == 7) || (symbol == 'p' && lokalS[1] == 2)) {
                                        promo = ' ';
                                    }
                                    ust[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                    polestart = true;
                                    koniecanimacji = false;
                                    if (wzor == false) {
                                        animacja anim1 = new animacja(2, polestart, zmien, BUTTON, pomocs);
                                    }
                                    char[][] temp1 = new char[8][8];
                                    for (int i = 0; i < 8; i++) {
                                        for (int j = 0; j < 8; j++) {
                                            temp1[i][j] = ust[i][j];

                                        }
                                    }
                                    temp1[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                                    int[] temp2 = new int[2];
                                    temp2[0] = lokalS[1] - 1;
                                    temp2[1] = lokalS[0] - 1;
                                    if (opcje_pomoc == 1 || opcje_pomoc == 2) {
                                        for (Ruch move : Generator.generuj_posuniecia(konwert(temp1), ruchB, przelotcan,
                                                bleft, bright, wleft, wright, kingrochB, kingrochC, 1, kol, true, symbol, temp2, false)) {
                                            if (!move.toString().substring(0, 3).equals("O-O")) {
                                                JButton cel = dobierzprzycisk(move.toString().substring(4, 6), odwrot);
                                                cel.setBorder(new LineBorder(pomoc_ruch, 4));
                                            } else {
                                                if (ruchB) {
                                                    if (move.toString().equals("O-O")) {
                                                        dobierzprzycisk("G1", odwrot).setBorder(new LineBorder(pomoc_ruch, 4));
                                                    } else {
                                                        dobierzprzycisk("C1", odwrot).setBorder(new LineBorder(pomoc_ruch, 4));
                                                    }
                                                } else {
                                                    if (move.toString().equals("O-O")) {
                                                        dobierzprzycisk("G8", odwrot).setBorder(new LineBorder(pomoc_ruch, 4));
                                                    } else {
                                                        dobierzprzycisk("C8", odwrot).setBorder(new LineBorder(pomoc_ruch, 4));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (siec == false) {
                                        if (czasgry == -1 && siec == false && (opcje_pomoc == 0 || opcje_pomoc == 2)) {
                                            if (ruchB == true) {
                                                JOptionPane.showMessageDialog(rootPane, "BŁĄD! AKTUALNY RUCH MAJĄ BIAŁE", "bład koloru",
                                                        JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(rootPane, "BŁĄD! AKTUALNY RUCH MAJĄ CZARNE", "błąd koloru",
                                                        JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            Toolkit.getDefaultToolkit().beep();
                                        }
                                        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);

                                    }
                                }
                            } else {
                                if (BUTTON.getIcon() == null) {
                                    pomoce = BUTTON.getName();
                                    stop = pomoce;
                                    pomoc3 = pomoce.charAt(1);
                                    pomoc2 = pomoce.charAt(0);
                                    if (odwrot == false) {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 1;
                                                break;
                                            case 'B':
                                                pomocx = 2;
                                                break;
                                            case 'C':
                                                pomocx = 3;
                                                break;
                                            case 'D':
                                                pomocx = 4;
                                                break;
                                            case 'E':
                                                pomocx = 5;
                                                break;
                                            case 'F':
                                                pomocx = 6;
                                                break;
                                            case 'G':
                                                pomocx = 7;
                                                break;
                                            case 'H':
                                                pomocx = 8;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 1;
                                                break;
                                            case '2':
                                                pomocy = 2;
                                                break;
                                            case '3':
                                                pomocy = 3;
                                                break;
                                            case '4':
                                                pomocy = 4;
                                                break;
                                            case '5':
                                                pomocy = 5;
                                                break;
                                            case '6':
                                                pomocy = 6;
                                                break;
                                            case '7':
                                                pomocy = 7;
                                                break;
                                            case '8':
                                                pomocy = 8;
                                                break;
                                        }
                                    } else {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 8;
                                                break;
                                            case 'B':
                                                pomocx = 7;
                                                break;
                                            case 'C':
                                                pomocx = 6;
                                                break;
                                            case 'D':
                                                pomocx = 5;
                                                break;
                                            case 'E':
                                                pomocx = 4;
                                                break;
                                            case 'F':
                                                pomocx = 3;
                                                break;
                                            case 'G':
                                                pomocx = 2;
                                                break;
                                            case 'H':
                                                pomocx = 1;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 8;
                                                break;
                                            case '2':
                                                pomocy = 7;
                                                break;
                                            case '3':
                                                pomocy = 6;
                                                break;
                                            case '4':
                                                pomocy = 5;
                                                break;
                                            case '5':
                                                pomocy = 4;
                                                break;
                                            case '6':
                                                pomocy = 3;
                                                break;
                                            case '7':
                                                pomocy = 2;
                                                break;
                                            case '8':
                                                pomocy = 1;
                                                break;
                                        }
                                    }
                                    lokalK[0] = pomocx;
                                    lokalK[1] = pomocy;
                                    if ((lokalS[0] != lokalK[0]) || (lokalK[1] != lokalS[1])) {
                                        if (symbol == 'R' || symbol == 'r') {
                                            switch (symbol) {
                                                case 'R':
                                                    if (lokalS[0] == 8 && lokalS[1] == 1
                                                            && RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust, bicie, przelotcan, kol) == true) {
                                                        wright = false;
                                                        if (!wright && !wleft) {
                                                            kingrochB = false;
                                                        }
                                                    } else {
                                                        if (lokalS[0] == 1 && lokalS[1] == 1
                                                                && RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust, bicie, przelotcan, kol) == true) {
                                                            wleft = false;
                                                            if (!wright && !wleft) {
                                                                kingrochB = false;
                                                            }
                                                        }
                                                    }
                                                    break;
                                                case 'r':
                                                    if (lokalS[0] == 8 && lokalS[1] == 8
                                                            && RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust, bicie, przelotcan, kol) == true) {
                                                        bright = false;

                                                    } else {
                                                        if (lokalS[0] == 1 && lokalS[1] == 8
                                                                && RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust, bicie, przelotcan, kol) == true) {
                                                            bleft = false;

                                                        }
                                                    }
                                                    break;
                                            }
                                            if (!bright && !bleft) {
                                                kingrochC = false;
                                            }
                                            if (!wright && !wleft) {
                                                kingrochB = false;
                                            }
                                        }
                                        atak = krolS;
                                        if ((symbol == 'K' || symbol == 'k') && atak == false && ((ruchB == true && kingrochB == true && (((lokalS[0] - lokalK[0]) == -2 && ust[0][5] == ' ' && ust[0][6] == ' ' && wright == true) || (lokalS[0] - lokalK[0] == 2 && ust[0][3] == ' ' && ust[0][2] == ' ' && ust[0][1] == ' ' && wleft == true)))
                                                || (ruchB == false && kingrochC == true && (((lokalS[0] - lokalK[0]) == -2 && ust[7][5] == ' ' && ust[7][6] == ' ' && bright == true) || (lokalS[0] - lokalK[0] == 2 && ust[7][3] == ' ' && ust[7][2] == ' ' && ust[7][1] == ' ' && bleft == true))))) {
                                            if (ruchB == true) {
                                                symbol = 'K';
                                                if ((lokalS[0] - lokalK[0]) == -2) {
                                                    kontrolka[0][6] = 'K';
                                                    kontrolka[0][4] = ' ';
                                                    checka = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                    kontrolka[0][6] = ' ';
                                                    kontrolka[0][5] = 'K';
                                                    checkp = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                    kontrolka[0][5] = ' ';
                                                    kontrolka[0][4] = 'K';
                                                } else {
                                                    if ((lokalS[0] - lokalK[0]) == 2) {
                                                        kontrolka[0][2] = 'K';
                                                        kontrolka[0][4] = ' ';
                                                        checka = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        kontrolka[0][2] = ' ';
                                                        kontrolka[0][3] = 'K';
                                                        checkp = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        kontrolka[0][3] = ' ';
                                                        kontrolka[0][4] = 'K';
                                                    }
                                                }
                                            } else {
                                                symbol = 'k';
                                                if ((lokalS[0] - lokalK[0]) == -2) {
                                                    kontrolka[7][6] = 'k';
                                                    kontrolka[7][4] = ' ';
                                                    checka = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                    kontrolka[7][6] = ' ';
                                                    kontrolka[7][5] = 'k';
                                                    checkp = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                    kontrolka[7][5] = ' ';
                                                    kontrolka[7][4] = 'k';
                                                } else {
                                                    if ((lokalS[0] - lokalK[0]) == 2) {
                                                        kontrolka[7][2] = 'k';
                                                        kontrolka[7][4] = ' ';
                                                        checka = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        kontrolka[7][2] = ' ';
                                                        kontrolka[7][3] = 'k';
                                                        checkp = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        kontrolka[7][3] = ' ';
                                                        kontrolka[7][4] = 'k';
                                                    }
                                                }
                                            }
                                            if (checka == false && checkp == false) {
                                                wyk = true;
                                                zmien = true;
                                                ust[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                                if (ruchB == true) {
                                                    ust[lokalK[1] - 1][lokalK[0] - 1] = 'K';
                                                } else {
                                                    ust[lokalK[1] - 1][lokalK[0] - 1] = 'k';
                                                }
                                                roch = true;
                                                if (ruchB == true) {
                                                    kingrochB = false;
                                                } else {
                                                    kingrochC = false;
                                                }
                                                polestart = false;
                                                wyk = false;
                                                zmien = true;
                                                BUTTON.setIcon(Cursor);
                                                if (ruchB == true) {
                                                    kon = true;
                                                    ust[0][4] = ' ';
                                                    if (lokalS[0] - lokalK[0] == -2) {
                                                        ust[0][5] = 'R';
                                                        ust[0][7] = ' ';
                                                        if (odwrot == false) {
                                                            Cursor = H1.getIcon();
                                                            H1.setIcon(null);
                                                            F1.setIcon(Cursor);
                                                        } else {
                                                            Cursor = A8.getIcon();
                                                            A8.setIcon(null);
                                                            C8.setIcon(Cursor);
                                                        }
                                                        ust[0][6] = 'K';
                                                    } else {
                                                        ust[0][3] = 'R';
                                                        ust[0][0] = ' ';
                                                        if (odwrot == false) {
                                                            Cursor = A1.getIcon();
                                                            A1.setIcon(null);
                                                            D1.setIcon(Cursor);
                                                        } else {
                                                            Cursor = H8.getIcon();
                                                            H8.setIcon(null);
                                                            F8.setIcon(Cursor);
                                                        }
                                                        ust[0][2] = 'K';
                                                    }
                                                    kingrochB = false;
                                                    polestart = false;
                                                    roch = true;
                                                    dokonano_RB = true;
                                                    ruchB = ruchB != true;
                                                    kontrolamat = ust;
                                                    krolS = RuchZagrozenie_kontrola.szach(kontrolamat, ruchB);
                                                } else {
                                                    kon = true;
                                                    ust[7][4] = ' ';
                                                    if (lokalS[0] - lokalK[0] == -2) {
                                                        ust[7][5] = 'r';
                                                        ust[7][7] = ' ';
                                                        if (odwrot == false) {
                                                            Cursor = H8.getIcon();
                                                            H8.setIcon(null);
                                                            F8.setIcon(Cursor);
                                                        } else {
                                                            Cursor = A1.getIcon();
                                                            A1.setIcon(null);
                                                            C1.setIcon(Cursor);
                                                        }
                                                        ust[7][6] = 'k';
                                                    } else {
                                                        ust[7][3] = 'r';
                                                        ust[7][0] = ' ';
                                                        if (odwrot == false) {
                                                            Cursor = A8.getIcon();
                                                            A8.setIcon(null);
                                                            C8.setIcon(Cursor);
                                                        } else {
                                                            Cursor = H1.getIcon();
                                                            H1.setIcon(null);
                                                            F1.setIcon(Cursor);
                                                        }
                                                        ust[7][2] = 'k';
                                                    }
                                                    polestart = false;
                                                    kingrochC = false;
                                                    dokonano_RC = true;
                                                    ruchB = ruchB != true;
                                                    krolS = RuchZagrozenie_kontrola.szach(ust, ruchB);
                                                    kontrolamat = ust;
                                                }
                                                char[][] USTAWIENIE1 = ust;
                                                for (int i = 0; i < 8; i++) {
                                                    System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                }
                                                for (int i = 0; i < 8; i++) {
                                                    for (int j = 0; j < 8; j++) {
                                                        odwrotna[i][j] = ust[7 - i][7 - j];
                                                    }
                                                }
                                                for (int i = 0; i < 8; i++) {
                                                    for (int j = 0; j < 8; j++) {
                                                        System.out.print("{" + ust[i][j] + "}");
                                                    }
                                                    System.out.println();
                                                }
                                                for (int i = 0; i < 8; i++) {
                                                    System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                }
                                                if (krolS == true) {
                                                    char[][] backup = ust;
                                                    if (siec == false) {
                                                        JOptionPane.showMessageDialog(rootPane, "SZACH! KROL JEST ATAKOWANY", "Ostrzeżenie",
                                                                JOptionPane.WARNING_MESSAGE);
                                                    }
                                                    Toolkit.getDefaultToolkit().beep();
                                                    kontrolamat = ust;
                                                    for (int i = 0; i < 8; i++) {
                                                        for (int j = 0; j < 8; j++) {
                                                            if ((ruchB == true && kontrolamat[i][j] == 'K') || (ruchB == false && kontrolamat[i][j] == 'k')) {
                                                                poza_krolewska[0] = i;
                                                                poza_krolewska[1] = j;
                                                            }
                                                        }
                                                    }
                                                    klopoty = Wspomagacz.znajdzklopot(kontrolamat, ruchB);
                                                    System.out.println("klopoty X:" + klopoty[0] + "Y:" + klopoty[1]);
                                                    System.out.println(ust[klopoty[0]][klopoty[1]]);
                                                    backup = ust;
                                                    hodu = SzachMatPatKontrola.uciekaj(kontrolamat, ruchB, poza_krolewska);
                                                    if (hodu == false) {
                                                        backup = ust;
                                                        char[][] backupzapas = ust;
                                                        hitme = SzachMatPatKontrola.znajdzodsiecz(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), kol, przelotcan);
                                                        if (hitme == false) {
                                                            backup = ust;
                                                            protectme = SzachMatPatKontrola.zastaw(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), poza_krolewska, przelotcan);
                                                        }
                                                    }
                                                    // System.out.println("ust");
                                                    for (int i = 0; i < 8; i++) {
                                                        System.arraycopy(ust[i], 0, USTAWIENIE1[i], 0, 8); //  System.out.print("[" + ust[i][j] + "]");
                                                        //System.out.println();
                                                    }

                                                } else {
                                                    krolS = false;
                                                    move = false;
                                                    for (int i = 0; i < 8; i++) {
                                                        for (int j = 0; j < 8; j++) {
                                                            System.out.print("[" + ust[i][j] + "]");
                                                        }
                                                        System.out.println();
                                                    }
                                                    for (int i = 0; i < 8; i++) {
                                                        for (int j = 0; j < 8; j++) {
                                                            if (ust[i][j] != ' ') {
                                                                pole_baza[0] = j;
                                                                pole_baza[1] = i;
                                                                if (ust[i][j] == 'K' || ust[i][j] == 'k') {
                                                                    if ((ruchB == true && ust[i][j] == 'K') || (ruchB == false && ust[i][j] == 'k')) {
                                                                        pole_baza[0] = i;
                                                                        pole_baza[1] = j;
                                                                        hodu = SzachMatPatKontrola.uciekaj(ust, ruchB, pole_baza);
                                                                    }
                                                                } else {
                                                                    move = SzachMatPatKontrola.znajdz_ruch(ust, ruchB, ust[i][j], pole_baza, przelotcan);
                                                                    if (move == true) {
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (move == true) {
                                                            break;
                                                        }
                                                    }

                                                }
                                                roch = true;
                                            } else {
                                                if (czasgry == -1 && siec == false && (opcje_pomoc == 0 || opcje_pomoc == 2)) {
                                                    JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEDOZWOLONY.\n"
                                                            + "KRÓL PRZESZEDŁBY PRZEZ POLE ZAGROŻONE, \n"
                                                            + "LUB NA NIE BY WSZEDŁ", "Błąd roszady",
                                                            JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    Toolkit.getDefaultToolkit().beep();
                                                }
                                            }
                                        } else {
                                            if (lokalK[0] == lokalS[0] && lokalS[1] == lokalK[1]) {
                                                koniecanimacji = true;
                                                BUTTON.setIcon(Cursor);
                                                zmien = false;
                                                promo = ' ';
                                                polestart = false;
                                                wyk = false;
                                                ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                czysc_rame();
                                                ustawrame();
                                            } else {
                                                if (symbol == 'K' || symbol == 'k') {
                                                    if (((kingrochB == false || (((lokalS[0] - lokalK[0]) != -2) && (lokalS[0] - lokalK[0] != 2)))
                                                            || (kingrochC == false || (((lokalS[0] - lokalK[0]) != -2) && (lokalS[0] - lokalK[0] != 2))))) {
                                                        polestart = true;
                                                        wyk = true;
                                                        kontrolka = ust;
                                                        kontrolka[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                                        checka = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        kontrolka[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                                                        kon = RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, kontrolka, ruchB, przelotcan, kol);
                                                        if (kon == true && checka != true) {
                                                            zmien = true;
                                                            polestart = false;
                                                            krolS = false;
                                                            if (ruchB == true) {
                                                                kingrochB = false;
                                                            } else {
                                                                kingrochC = false;
                                                            }
                                                            BUTTON.setIcon(Cursor);
                                                            ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                            wyk = true;
                                                            pomoc5 = null;
                                                        } else {
                                                            wyk = false;
                                                            polestart = true;
                                                            if (czasgry == -1 && siec == false) {
                                                                if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                                    if (checka == false && kon == false) {
                                                                        JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                    }

                                                                    switch (symbol) {
                                                                        case 'K':
                                                                        case 'k':
                                                                            JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                        case 'Q':
                                                                        case 'q':
                                                                            JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                        case 'R':
                                                                        case 'r':
                                                                            JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                        case 'B':
                                                                        case 'b':
                                                                            JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                        case 'N':
                                                                        case 'n':
                                                                            JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                                    + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                        case 'P':
                                                                        case 'p':
                                                                            JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                                    + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                                    + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                                    + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                            break;
                                                                    }
                                                                }
                                                            } else {
                                                                Toolkit.getDefaultToolkit().beep();
                                                            }
                                                            styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                                                        }
                                                        if (wyk == true) {
                                                            char[][] USTAWIENIE1 = new char[8][8];
                                                            polestart = false;
                                                            ruchB = ruchB != true;
                                                            for (int i = 0; i < 8; i++) {
                                                                System.arraycopy(ust[i], 0, USTAWIENIE1[i], 0, 8);
                                                            }
                                                            krolS = RuchZagrozenie_kontrola.szach(ust, ruchB);
                                                            for (int i = 0; i < 8; i++) {
                                                                System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                            }
                                                            for (int i = 0; i < 8; i++) {
                                                                for (int j = 0; j < 8; j++) {
                                                                    odwrotna[i][j] = ust[7 - i][7 - j];
                                                                }
                                                            }
                                                            for (int i = 0; i < 8; i++) {
                                                                for (int j = 0; j < 8; j++) {
                                                                    System.out.print("{" + ust[i][j] + "}");
                                                                }
                                                                System.out.println();
                                                            }
                                                            for (int i = 0; i < 8; i++) {
                                                                System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                            }
                                                            if (krolS == true) {
                                                                char[][] backup = ust;
                                                                if (siec == false) {
                                                                    JOptionPane.showMessageDialog(rootPane, "SZACH! KROL JEST ATAKOWANY", "Ostrzeżenie",
                                                                            JOptionPane.WARNING_MESSAGE);
                                                                }
                                                                Toolkit.getDefaultToolkit().beep();
                                                                kontrolamat = ust;
                                                                for (int i = 0; i < 8; i++) {
                                                                    for (int j = 0; j < 8; j++) {
                                                                        USTAWIENIE1[i][j] = ust[i][j];
                                                                        if ((ruchB == true && kontrolamat[i][j] == 'K') || (ruchB == false && kontrolamat[i][j] == 'k')) {
                                                                            poza_krolewska[0] = i;
                                                                            poza_krolewska[1] = j;
                                                                        }
                                                                    }
                                                                }
                                                                klopoty = Wspomagacz.znajdzklopot(kontrolamat, ruchB);
                                                                System.out.println("klopoty X:" + klopoty[0] + "Y:" + klopoty[1]);
                                                                System.out.println(ust[klopoty[0]][klopoty[1]]);
                                                                backup = ust;
                                                                hodu = SzachMatPatKontrola.uciekaj(kontrolamat, ruchB, poza_krolewska);
                                                                if (hodu == false) {
                                                                    backup = ust;
                                                                    char[][] backupzapas = ust;
                                                                    hitme = SzachMatPatKontrola.znajdzodsiecz(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), kol, przelotcan);
                                                                    if (hitme == false) {
                                                                        backup = ust;
                                                                        protectme = SzachMatPatKontrola.zastaw(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), poza_krolewska, przelotcan);
                                                                    }
                                                                }
                                                                System.out.println("ust");
                                                                for (int i = 0; i < 8; i++) {
                                                                    System.arraycopy(USTAWIENIE1[i], 0, ust[i], 0, 8);
                                                                    System.out.println();
                                                                }

                                                            } else {
                                                                krolS = false;
                                                                move = false;
                                                                USTAWIENIE1 = ust;
                                                                for (int i = 0; i < 8; i++) {
                                                                    for (int j = 0; j < 8; j++) {
                                                                        if (ust[i][j] != ' ') {
                                                                            pole_baza[0] = j;
                                                                            pole_baza[1] = i;
                                                                            if (ust[i][j] == 'K' || ust[i][j] == 'k') {
                                                                                if ((ruchB == true && ust[i][j] == 'K') || (ruchB == false && ust[i][j] == 'k')) {
                                                                                    pole_baza[0] = i;
                                                                                    pole_baza[1] = j;
                                                                                    hodu = SzachMatPatKontrola.uciekaj(ust, ruchB, pole_baza);
                                                                                }
                                                                            } else {
                                                                                move = SzachMatPatKontrola.znajdz_ruch(ust, ruchB, ust[i][j], pole_baza, przelotcan);
                                                                                if (move == true) {
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    if (move == true) {
                                                                        break;
                                                                    }
                                                                }

                                                            }
                                                            for (int i = 0; i < 8; i++) {
                                                                System.arraycopy(USTAWIENIE1[i], 0, ust[i], 0, 8);
                                                            }
                                                        }
                                                    } else {
                                                        if (czasgry == -1 && siec == false && (opcje_pomoc == 0 || opcje_pomoc == 2)) {
                                                            JOptionPane.showMessageDialog(rootPane, "BŁĄD ROSZADY!", "Ostrzeżenie",
                                                                    JOptionPane.WARNING_MESSAGE);
                                                        } else {
                                                            Toolkit.getDefaultToolkit().beep();
                                                        }
                                                    }
                                                } else {
                                                    kon = RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust.clone(), ruchB, przelotcan, kol);

                                                    if (kon == true) {
                                                        kontrolka = ust.clone();
                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                                        kontrolka[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                        pakc = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                        zmien = true;
                                                    } else {
                                                        pakc = true;
                                                    }
                                                    if (pakc == false && kon == true) {
                                                        if ((symbol == 'P' && lokalS[0] == lokalK[0] && lokalS[1] - lokalK[1] == -2)
                                                                || (symbol == 'p' && lokalS[0] == lokalK[0] && lokalS[1] - lokalK[1] == 2)) {
                                                            przelotcan = true;
                                                            kol = lokalS[0];
                                                        } else {
                                                            if (przelotcan == true && (symbol == 'p' || symbol == 'P') && Math.abs(lokalS[1] - lokalK[1]) == 1
                                                                    && ((ruchB == true && lokalS[1] == 5) || (ruchB == false && lokalS[1] == 4))) {

                                                            } else {
                                                                kol = 0;
                                                            }
                                                        }
                                                        if (symbol == 'P' || symbol == 'p') {
                                                            if ((przelotcan == true && ((lokalK[0] == kol && symbol == 'P' && lokalS[1] - lokalK[1] == -1 && lokalS[0] != lokalK[0] && lokalS[1] == 5)
                                                                    || (lokalK[0] == kol && symbol == 'p' && lokalS[1] - lokalK[1] == 1 && lokalS[0] != lokalK[0] && lokalS[1] == 4)))) {
                                                                kontrolka = ust;
                                                                kontrolka[lokalS[1] - 1][kol - 1] = ' ';
                                                                kontrolka[lokalK[1] - 1][kol - 1] = symbol;
                                                                kontrolka[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                                                pakc = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                                kon = RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust, ruchB, przelotcan, kol);
                                                                if (pakc != true) {
                                                                    kon = true;
                                                                    przelot = false;
                                                                    dokonanoEP = true;
                                                                    BUTTON.setIcon(Cursor);
                                                                    ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                                    if (ruchB == false) {
                                                                        pionB = (byte) (pionB - 1);
                                                                    } else {
                                                                        pionC = (byte) (pionC - 1);
                                                                    }
                                                                    switch (lokalK[0]) {
                                                                        case 1:
                                                                            if (ruchB == true) {
                                                                                ust[4][0] = ' ';
                                                                                A5.setIcon(null);
                                                                            } else {
                                                                                ust[3][0] = ' ';
                                                                                A4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 2:
                                                                            if (ruchB == true) {
                                                                                ust[4][1] = ' ';
                                                                                B5.setIcon(null);
                                                                            } else {
                                                                                ust[3][1] = ' ';
                                                                                B4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 3:
                                                                            if (ruchB == true) {
                                                                                ust[4][2] = ' ';
                                                                                C5.setIcon(null);
                                                                            } else {
                                                                                ust[3][2] = ' ';
                                                                                C4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 4:
                                                                            if (ruchB == true) {
                                                                                ust[4][3] = ' ';
                                                                                D5.setIcon(null);
                                                                            } else {
                                                                                ust[3][3] = ' ';
                                                                                D4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 5:
                                                                            if (ruchB == true) {
                                                                                ust[4][4] = ' ';
                                                                                E5.setIcon(null);
                                                                            } else {
                                                                                ust[3][4] = ' ';
                                                                                E4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 6:
                                                                            if (ruchB == true) {
                                                                                ust[4][5] = ' ';
                                                                                F5.setIcon(null);
                                                                            } else {
                                                                                ust[3][5] = ' ';
                                                                                F4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 7:
                                                                            if (ruchB == true) {
                                                                                ust[4][6] = ' ';
                                                                                G5.setIcon(null);
                                                                            } else {
                                                                                ust[3][6] = ' ';
                                                                                G4.setIcon(null);
                                                                            }
                                                                            break;
                                                                        case 8:
                                                                            if (ruchB == true) {
                                                                                ust[4][7] = ' ';
                                                                                H5.setIcon(null);
                                                                            } else {
                                                                                ust[3][7] = ' ';
                                                                                H4.setIcon(null);
                                                                            }
                                                                            break;
                                                                    }
                                                                    wyk = true;
                                                                    prze = true;
                                                                    bicie = true;
                                                                    ruchB = ruchB == true;
                                                                } else {
                                                                    if (symbol == 'P') {
                                                                        kontrolka[lokalS[1] - 1][kol - 1] = 'p';
                                                                        kontrolka[lokalK[1] - 1][kol - 1] = ' ';
                                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = 'P';
                                                                    } else {
                                                                        kontrolka[lokalS[1] - 1][kol - 1] = 'P';
                                                                        kontrolka[lokalK[1] - 1][kol - 1] = ' ';
                                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = 'p';
                                                                    }
                                                                    polestart = true;
                                                                    wyk = false;
                                                                    przelot = false;
                                                                    if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                                        if (pakc == false && kon == false) {
                                                                            JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        }

                                                                        switch (symbol) {
                                                                            case 'K':
                                                                            case 'k':
                                                                                JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                            case 'Q':
                                                                            case 'q':
                                                                                JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                            case 'R':
                                                                            case 'r':
                                                                                JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                            case 'B':
                                                                            case 'b':
                                                                                JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                            case 'N':
                                                                            case 'n':
                                                                                JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                                        + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                            case 'P':
                                                                            case 'p':
                                                                                JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                                        + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                                        + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                                        + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                                break;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            przelotcan = false;
                                                        }
                                                        ust = kontrolka;
                                                        pakc = RuchZagrozenie_kontrola.szach(ust, ruchB);
                                                        if (pakc != true) {
                                                            polestart = false;
                                                            wyk = true;
                                                        } else {
                                                            polestart = true;
                                                            wyk = false;
                                                            if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                                if (pakc == false) {
                                                                    JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                } else {
                                                                    JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                }

                                                                switch (symbol) {
                                                                    case 'K':
                                                                    case 'k':
                                                                        JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'Q':
                                                                    case 'q':
                                                                        JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'R':
                                                                    case 'r':
                                                                        JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'B':
                                                                    case 'b':
                                                                        JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'N':
                                                                    case 'n':
                                                                        JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                                + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'P':
                                                                    case 'p':
                                                                        JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                                + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                                + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                                + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                }
                                                            }
                                                        }
                                                    } else {

                                                        if (czasgry == -1 && siec == false) {
                                                            kon = RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust.clone(), ruchB, przelotcan, kol);

                                                            if (kon == true) {
                                                                kontrolka = ust.clone();
                                                                kontrolka[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                                                kontrolka[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                                pakc = RuchZagrozenie_kontrola.szach(kontrolka, ruchB);
                                                                zmien = true;
                                                            } else {
                                                                pakc = true;
                                                            }
                                                            if ((!(pakc == true && kon == false)) && (opcje_pomoc == 0 || opcje_pomoc == 2)) {
                                                                JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                            } else {
                                                                JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                            }
                                                            if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                                switch (symbol) {
                                                                    case 'K':
                                                                    case 'k':
                                                                        JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'Q':
                                                                    case 'q':
                                                                        JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'R':
                                                                    case 'r':
                                                                        JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'B':
                                                                    case 'b':
                                                                        JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'N':
                                                                    case 'n':
                                                                        JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                                + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                    case 'P':
                                                                    case 'p':
                                                                        JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                                + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                                + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                                + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                                JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                        break;
                                                                }
                                                            }
                                                        } else {
                                                            Toolkit.getDefaultToolkit().beep();
                                                        }
                                                        kontrolka[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                                                        kontrolka[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                                                        polestart = true;
                                                        wyk = false;
                                                        przelot = false;
                                                        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                                                    }
                                                    if (wyk == true) {
                                                        if ((symbol == 'P' && lokalK[1] == 8) || (symbol == 'p' && lokalK[1] == 1)) {
                                                            promocja = true;
                                                            Object[] opcjeB = {b1, b2, b3, b4};
                                                            Object[] opcjeC = {c1, c2, c3, c4};
                                                            if (ruchB == true) {
                                                                pionB = (byte) (pionB - 1);
                                                                if (znak_promocji == ' ') {
                                                                    wybor = JOptionPane.showOptionDialog(null, "PROMOCJA WYBIERZ FIGURĘ", "PROMOCJA",
                                                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcjeB, null);
                                                                    switch (wybor) {
                                                                        case 0:
                                                                            symbol = 'Q';
                                                                            ciezkieB = (byte) (ciezkieB + 1);
                                                                            Cursor = b1;
                                                                            break;
                                                                        case 1:
                                                                            symbol = 'R';
                                                                            ciezkieB = (byte) (ciezkieB + 1);
                                                                            Cursor = b2;
                                                                            break;
                                                                        case 2:
                                                                            symbol = 'B';
                                                                            lekkieB = (byte) (lekkieB + 1);
                                                                            Cursor = b3;
                                                                            break;
                                                                        case 3:
                                                                            symbol = 'N';
                                                                            lekkieB = (byte) (lekkieB + 1);
                                                                            Cursor = b4;
                                                                            break;
                                                                        default:
                                                                            symbol = 'Q';
                                                                            ciezkieB = (byte) (ciezkieB + 1);
                                                                            Cursor = b1;
                                                                            break;
                                                                    }
                                                                } else {
                                                                    symbol = znak_promocji;
                                                                    switch (symbol) {
                                                                        case 'Q':
                                                                            ciezkieB = (byte) (ciezkieB + 1);
                                                                            break;
                                                                        case 'R':
                                                                            ciezkieB = (byte) (ciezkieB + 1);
                                                                            break;
                                                                        case 'B':
                                                                            lekkieB = (byte) (lekkieB + 1);
                                                                            break;
                                                                        case 'N':
                                                                            lekkieB = (byte) (lekkieB + 1);
                                                                            break;
                                                                    }
                                                                }
                                                                promo = symbol;
                                                            } else {
                                                                pionC = (byte) (pionC - 1);
                                                                if (znak_promocji == ' ') {
                                                                    wybor = JOptionPane.showOptionDialog(null, "PROMOCJA WYBIERZ FIGURĘ", "PROMOCJA",
                                                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcjeC, null);
                                                                    switch (wybor) {
                                                                        case 0:
                                                                            symbol = 'q';
                                                                            ciezkieC = (byte) (ciezkieC + 1);
                                                                            Cursor = c1;
                                                                            break;
                                                                        case 1:
                                                                            symbol = 'r';
                                                                            ciezkieC = (byte) (ciezkieC + 1);
                                                                            Cursor = c2;
                                                                            break;
                                                                        case 2:
                                                                            symbol = 'b';
                                                                            lekkieC = (byte) (lekkieC + 1);
                                                                            Cursor = c3;
                                                                            break;
                                                                        case 3:
                                                                            symbol = 'n';
                                                                            lekkieC = (byte) (lekkieC + 1);
                                                                            Cursor = c4;
                                                                            break;
                                                                        default:
                                                                            symbol = 'q';
                                                                            ciezkieC = (byte) (ciezkieC + 1);
                                                                            Cursor = c1;
                                                                            break;
                                                                    }
                                                                } else {
                                                                    symbol = znak_promocji;
                                                                    switch (symbol) {
                                                                        case 'q':
                                                                            ciezkieC = (byte) (ciezkieC + 1);
                                                                            break;
                                                                        case 'r':
                                                                            ciezkieC = (byte) (ciezkieC + 1);
                                                                            break;
                                                                        case 'b':
                                                                            lekkieC = (byte) (lekkieC + 1);
                                                                            break;
                                                                        case 'n':
                                                                            lekkieC = (byte) (lekkieC + 1);
                                                                            break;
                                                                    }
                                                                }
                                                                promo = symbol;
                                                            }
                                                        }
                                                        if (znak_promocji == ' ') {
                                                            BUTTON.setIcon(Cursor);
                                                        } else {
                                                            switch (znak_promocji) {
                                                                case 'Q':
                                                                    BUTTON.setIcon(b1);
                                                                    break;
                                                                case 'R':
                                                                    BUTTON.setIcon(b2);
                                                                    break;
                                                                case 'B':
                                                                    BUTTON.setIcon(b3);
                                                                    break;
                                                                case 'N':
                                                                    BUTTON.setIcon(b4);
                                                                    break;
                                                                case 'q':
                                                                    BUTTON.setIcon(c1);
                                                                    break;
                                                                case 'r':
                                                                    BUTTON.setIcon(c2);
                                                                    break;
                                                                case 'b':
                                                                    BUTTON.setIcon(c3);
                                                                    break;
                                                                case 'n':
                                                                    BUTTON.setIcon(c4);
                                                                    break;
                                                            }
                                                        }
                                                        ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                        pomoc5 = null;
                                                        char[][] USTAWIENIE1 = new char[8][8];
                                                        polestart = false;
                                                        ruchB = ruchB != true;
                                                        for (int i = 0; i < 8; i++) {
                                                            System.arraycopy(ust[i], 0, USTAWIENIE1[i], 0, 8);
                                                        }
                                                        krolS = RuchZagrozenie_kontrola.szach(ust, ruchB);
                                                        for (int i = 0; i < 8; i++) {
                                                            System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                        }
                                                        for (int i = 0; i < 8; i++) {
                                                            for (int j = 0; j < 8; j++) {
                                                                odwrotna[i][j] = ust[7 - i][7 - j];
                                                            }
                                                        }

                                                        for (int i = 0; i < 8; i++) {
                                                            System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                        }
                                                        if (krolS == true) {
                                                            char[][] backup = ust;
                                                            if (siec == false) {
                                                                JOptionPane.showMessageDialog(rootPane, "SZACH! KROL JEST ATAKOWANY", "Ostrzeżenie",
                                                                        JOptionPane.WARNING_MESSAGE);
                                                            }
                                                            Toolkit.getDefaultToolkit().beep();
                                                            kontrolamat = ust;
                                                            for (int i = 0; i < 8; i++) {
                                                                for (int j = 0; j < 8; j++) {
                                                                    USTAWIENIE1[i][j] = ust[i][j];
                                                                    if ((ruchB == true && kontrolamat[i][j] == 'K') || (ruchB == false && kontrolamat[i][j] == 'k')) {
                                                                        poza_krolewska[0] = i;
                                                                        poza_krolewska[1] = j;
                                                                    }
                                                                }
                                                            }
                                                            klopoty = Wspomagacz.znajdzklopot(kontrolamat, ruchB);
                                                            System.out.println("klopoty X:" + klopoty[0] + "Y:" + klopoty[1]);
                                                            System.out.println(ust[klopoty[0]][klopoty[1]]);
                                                            backup = ust;
                                                            hodu = SzachMatPatKontrola.uciekaj(kontrolamat, ruchB, poza_krolewska);
                                                            if (hodu == false) {
                                                                backup = ust;
                                                                char[][] backupzapas = new char[8][8];
                                                                for (int i = 0; i < 8; i++) {
                                                                    System.arraycopy(USTAWIENIE1[i], 0, backupzapas[i], 0, 8);
                                                                }
                                                                hitme = SzachMatPatKontrola.znajdzodsiecz(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), kol, przelotcan);
                                                                if (hitme == false) {
                                                                    for (int i = 0; i < 8; i++) {
                                                                        System.arraycopy(USTAWIENIE1[i], 0, backupzapas[i], 0, 8);
                                                                    }
                                                                    protectme = SzachMatPatKontrola.zastaw(backupzapas.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), poza_krolewska, przelotcan);
                                                                }
                                                            }
                                                            System.out.println("ust");
                                                            for (int i = 0; i < 8; i++) {
                                                                System.arraycopy(USTAWIENIE1[i], 0, ust[i], 0, 8);
                                                                System.out.println();
                                                            }

                                                        } else {
                                                            krolS = false;
                                                            move = false;

                                                            for (int i = 0; i < 8; i++) {
                                                                for (int j = 0; j < 8; j++) {
                                                                    if (ust[i][j] != ' ') {
                                                                        pole_baza[0] = j;
                                                                        pole_baza[1] = i;
                                                                        if (ust[i][j] == 'K' || ust[i][j] == 'k') {
                                                                            if ((ruchB == true && ust[i][j] == 'K') || (ruchB == false && ust[i][j] == 'k')) {
                                                                                pole_baza[0] = i;
                                                                                pole_baza[1] = j;
                                                                                hodu = SzachMatPatKontrola.uciekaj(ust, ruchB, pole_baza);
                                                                            }
                                                                        } else {
                                                                            move = SzachMatPatKontrola.znajdz_ruch(ust, ruchB, ust[i][j], pole_baza, przelotcan);
                                                                            if (move == true) {
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (move == true) {
                                                                    break;
                                                                }
                                                            }

                                                        }
                                                        bicie = prze == true;
                                                        prze = false;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        czysc_rame();
                                        ustawrame();
                                        zmien = false;
                                        promo = ' ';
                                        koniecanimacji = true;
                                        BUTTON.setIcon(Cursor);
                                        polestart = false;
                                        wyk = false;
                                        ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                    }
                                } else {
                                    char schodzi;
                                    pomoce = BUTTON.getName();
                                    pomoc2 = pomoce.charAt(0);
                                    pomoc3 = pomoce.charAt(1);
                                    stop = pomoce;
                                    if (odwrot == false) {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 1;
                                                break;
                                            case 'B':
                                                pomocx = 2;
                                                break;
                                            case 'C':
                                                pomocx = 3;
                                                break;
                                            case 'D':
                                                pomocx = 4;
                                                break;
                                            case 'E':
                                                pomocx = 5;
                                                break;
                                            case 'F':
                                                pomocx = 6;
                                                break;
                                            case 'G':
                                                pomocx = 7;
                                                break;
                                            case 'H':
                                                pomocx = 8;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 1;
                                                break;
                                            case '2':
                                                pomocy = 2;
                                                break;
                                            case '3':
                                                pomocy = 3;
                                                break;
                                            case '4':
                                                pomocy = 4;
                                                break;
                                            case '5':
                                                pomocy = 5;
                                                break;
                                            case '6':
                                                pomocy = 6;
                                                break;
                                            case '7':
                                                pomocy = 7;
                                                break;
                                            case '8':
                                                pomocy = 8;
                                                break;
                                        }
                                    } else {
                                        switch (pomoc2) {
                                            case 'A':
                                                pomocx = 8;
                                                break;
                                            case 'B':
                                                pomocx = 7;
                                                break;
                                            case 'C':
                                                pomocx = 6;
                                                break;
                                            case 'D':
                                                pomocx = 5;
                                                break;
                                            case 'E':
                                                pomocx = 4;
                                                break;
                                            case 'F':
                                                pomocx = 3;
                                                break;
                                            case 'G':
                                                pomocx = 2;
                                                break;
                                            case 'H':
                                                pomocx = 1;
                                                break;
                                        }
                                        switch (pomoc3) {
                                            case '1':
                                                pomocy = 8;
                                                break;
                                            case '2':
                                                pomocy = 7;
                                                break;
                                            case '3':
                                                pomocy = 6;
                                                break;
                                            case '4':
                                                pomocy = 5;
                                                break;
                                            case '5':
                                                pomocy = 4;
                                                break;
                                            case '6':
                                                pomocy = 3;
                                                break;
                                            case '7':
                                                pomocy = 2;
                                                break;
                                            case '8':
                                                pomocy = 1;
                                                break;
                                        }
                                    }
                                    lokalK[0] = pomocx;
                                    lokalK[1] = pomocy;
                                    schodzi = ust[lokalK[1] - 1][lokalK[0] - 1];
                                    if (schodzi == 'K' || schodzi == 'Q' || schodzi == 'R' || schodzi == 'B' || schodzi == 'N' || schodzi == 'P') {
                                        pomoci2 = 'W';
                                    } else {
                                        pomoci2 = 'B';
                                    }
                                    if (pomoci1 != pomoci2) {
                                        if (lokalK[0] == lokalS[0] && lokalS[1] == lokalK[1]) {
                                            koniecanimacji = true;
                                            zmien = false;
                                            promo = ' ';
                                            BUTTON.setIcon(Cursor);
                                            polestart = false;
                                            wyk = false;
                                            ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                            czysc_rame();
                                            ustawrame();
                                        } else {
                                            kon = RuchZagrozenie_kontrola.ruch(lokalS, lokalK, symbol, ust.clone(), ruchB, przelotcan, kol);
                                            char[][] tymczas = ust.clone();
                                            tymczas[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                                            char przech = tymczas[lokalK[1] - 1][lokalK[0] - 1];
                                            tymczas[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                            pakc = RuchZagrozenie_kontrola.szach(tymczas.clone(), ruchB);
                                            if (kon == true && pakc == false) {
                                                krolS = false;
                                                bicie = true;
                                                switch (schodzi) {
                                                    case 'P':
                                                        pionB = (byte) (pionB - 1);
                                                        break;
                                                    case 'p':
                                                        pionC = (byte) (pionC - 1);
                                                        break;
                                                    case 'N':
                                                        lekkieB = (byte) (lekkieB - 1);
                                                        break;
                                                    case 'B':
                                                        lekkieB = (byte) (lekkieB - 1);
                                                        break;
                                                    case 'n':
                                                        lekkieC = (byte) (lekkieC - 1);
                                                        break;
                                                    case 'b':
                                                        lekkieC = (byte) (lekkieC - 1);
                                                        break;
                                                    case 'R':
                                                        ciezkieB = (byte) (ciezkieB - 1);
                                                        break;
                                                    case 'Q':
                                                        ciezkieB = (byte) (ciezkieB - 1);
                                                        break;
                                                    case 'r':
                                                        ciezkieC = (byte) (ciezkieC - 1);
                                                        break;
                                                    case 'q':
                                                        ciezkieC = (byte) (ciezkieC - 1);
                                                        break;
                                                }
                                                if (symbol == 'K' || symbol == 'k') {
                                                    if (symbol == 'k') {
                                                        kingrochC = false;
                                                    } else {
                                                        kingrochB = false;
                                                    }
                                                }
                                                zmien = true;
                                                if (symbol == 'R' || symbol == 'r') {
                                                    switch (symbol) {
                                                        case 'R':
                                                            if (lokalS[1] == 8 && lokalS[0] == 1) {
                                                                wright = false;
                                                                if (!wright && !wleft) {
                                                                    kingrochB = false;
                                                                }
                                                            } else {
                                                                if (lokalS[1] == 1 && lokalS[0] == 1) {
                                                                    wleft = false;
                                                                    if (!wright && !wleft) {
                                                                        kingrochB = false;
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                        case 'r':
                                                            if (lokalS[1] == 8 && lokalS[0] == 8) {
                                                                bright = false;
                                                            } else {
                                                                if (lokalS[1] == 1 && lokalS[0] == 8) {
                                                                    bleft = false;
                                                                }
                                                            }
                                                            break;
                                                    }
                                                    if (!bright && !bleft) {
                                                        kingrochC = false;
                                                    }
                                                    if (!wright && !wleft) {
                                                        kingrochB = false;
                                                    }
                                                }
                                                if ((symbol == 'P' && lokalK[1] == 8) || (symbol == 'p' && lokalK[1] == 1)) {
                                                    promocja = true;
                                                    Object[] opcjeB = {b1, b2, b3, b4};
                                                    Object[] opcjeC = {c1, c2, c3, c4};
                                                    if (ruchB == true) {
                                                        pionB = (byte) (pionB - 1);
                                                        if (znak_promocji == ' ') {
                                                            wybor = JOptionPane.showOptionDialog(null, "PROMOCJA WYBIERZ FIGURĘ", "PROMOCJA",
                                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcjeB, null);
                                                            switch (wybor) {
                                                                case 0:
                                                                    symbol = 'Q';
                                                                    ciezkieB = (byte) (ciezkieB + 1);
                                                                    Cursor = b1;
                                                                    break;
                                                                case 1:
                                                                    symbol = 'R';
                                                                    ciezkieB = (byte) (ciezkieB + 1);
                                                                    Cursor = b2;
                                                                    break;
                                                                case 2:
                                                                    symbol = 'B';
                                                                    lekkieB = (byte) (lekkieB + 1);
                                                                    Cursor = b3;
                                                                    break;
                                                                case 3:
                                                                    symbol = 'N';
                                                                    lekkieB = (byte) (lekkieB + 1);
                                                                    Cursor = b4;
                                                                    break;
                                                                default:
                                                                    symbol = 'Q';
                                                                    ciezkieB = (byte) (ciezkieB + 1);
                                                                    Cursor = b1;
                                                                    break;
                                                            }
                                                        } else {
                                                            symbol = znak_promocji;
                                                            switch (symbol) {
                                                                case 'Q':
                                                                    ciezkieB = (byte) (ciezkieB + 1);
                                                                    break;
                                                                case 'R':
                                                                    ciezkieB = (byte) (ciezkieB + 1);
                                                                    break;
                                                                case 'B':
                                                                    lekkieB = (byte) (lekkieB + 1);
                                                                    break;
                                                                case 'N':
                                                                    lekkieB = (byte) (lekkieB + 1);
                                                                    break;
                                                            }
                                                        }
                                                        promo = symbol;
                                                    } else {
                                                        pionC = (byte) (pionC - 1);
                                                        if (znak_promocji == ' ') {
                                                            wybor = JOptionPane.showOptionDialog(null, "PROMOCJA WYBIERZ FIGURĘ", "PROMOCJA",
                                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcjeC, null);
                                                            switch (wybor) {
                                                                case 0:
                                                                    symbol = 'q';
                                                                    ciezkieC = (byte) (ciezkieC + 1);
                                                                    Cursor = c1;
                                                                    break;
                                                                case 1:
                                                                    symbol = 'r';
                                                                    ciezkieC = (byte) (ciezkieC + 1);
                                                                    Cursor = c2;
                                                                    break;
                                                                case 2:
                                                                    symbol = 'b';
                                                                    lekkieC = (byte) (lekkieC + 1);
                                                                    Cursor = c3;
                                                                    break;
                                                                case 3:
                                                                    symbol = 'n';
                                                                    lekkieC = (byte) (lekkieC + 1);
                                                                    Cursor = c4;
                                                                    break;
                                                                default:
                                                                    symbol = 'q';
                                                                    ciezkieC = (byte) (ciezkieC + 1);
                                                                    Cursor = c1;
                                                                    break;
                                                            }
                                                        } else {
                                                            symbol = znak_promocji;
                                                            switch (symbol) {
                                                                case 'q':
                                                                    ciezkieC = (byte) (ciezkieC + 1);
                                                                    break;
                                                                case 'r':
                                                                    ciezkieC = (byte) (ciezkieC + 1);
                                                                    break;
                                                                case 'b':
                                                                    lekkieC = (byte) (lekkieC + 1);
                                                                    break;
                                                                case 'n':
                                                                    lekkieC = (byte) (lekkieC + 1);
                                                                    break;
                                                            }
                                                        }
                                                        promo = symbol;
                                                    }
                                                }
                                                if (znak_promocji == ' ') {
                                                    BUTTON.setIcon(Cursor);
                                                } else {
                                                    switch (znak_promocji) {
                                                        case 'Q':
                                                            BUTTON.setIcon(b1);
                                                            break;
                                                        case 'R':
                                                            BUTTON.setIcon(b2);
                                                            break;
                                                        case 'B':
                                                            BUTTON.setIcon(b3);
                                                            break;
                                                        case 'N':
                                                            BUTTON.setIcon(b4);
                                                            break;
                                                        case 'q':
                                                            BUTTON.setIcon(c1);
                                                            break;
                                                        case 'r':
                                                            BUTTON.setIcon(c2);
                                                            break;
                                                        case 'b':
                                                            BUTTON.setIcon(c3);
                                                            break;
                                                        case 'n':
                                                            BUTTON.setIcon(c4);
                                                            break;
                                                    }
                                                }
                                                ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                                pomoc5 = null;
                                                polestart = false;
                                                wyk = true;
                                            } else {
                                                for (int i = 0; i < 8; i++) {
                                                    for (int j = 0; j < 8; j++) {
                                                        ust[i][j] = odwrotna[7 - i][7 - j];
                                                    }
                                                }
                                                wyk = false;
                                                polestart = true;
                                                if (czasgry == -1 && siec == false) {
                                                    if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                        if (pakc == true) {
                                                            JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                        } else {
                                                            JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                        }

                                                        switch (symbol) {
                                                            case 'K':
                                                            case 'k':
                                                                JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                            case 'Q':
                                                            case 'q':
                                                                JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                            case 'R':
                                                            case 'r':
                                                                JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                            case 'B':
                                                            case 'b':
                                                                JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                            case 'N':
                                                            case 'n':
                                                                JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                        + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                            case 'P':
                                                            case 'p':
                                                                JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                        + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                        + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                        + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                        JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                                break;
                                                        }
                                                    }
                                                } else {
                                                    Toolkit.getDefaultToolkit().beep();
                                                }
                                                //styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                                            }
                                            if (wyk == true) {
                                                wyk = false;
                                                char[][] USTAWIENIE1 = new char[8][8];
                                                ruchB = ruchB != true;
                                                if ((lokalS[0] != lokalK[0]) || (lokalK[1] != lokalS[1])) {
                                                    krolS = RuchZagrozenie_kontrola.szach(ust, ruchB);
                                                    for (int i = 0; i < 8; i++) {
                                                        System.arraycopy(ust[i], 0, USTAWIENIE1[i], 0, 8);
                                                    }
                                                    for (int i = 0; i < 8; i++) {
                                                        for (int j = 0; j < 8; j++) {
                                                            odwrotna[i][j] = ust[7 - i][7 - j];
                                                        }
                                                    }

                                                    for (int i = 0; i < 8; i++) {
                                                        for (int j = 0; j < 8; j++) {
                                                            System.out.print("{" + ust[i][j] + "}");
                                                        }
                                                        System.out.println();
                                                    }
                                                    for (int i = 0; i < 8; i++) {
                                                        System.arraycopy(ust[i], 0, kontrolka[i], 0, 8);
                                                    }
                                                    if (krolS == true) {
                                                        char[][] backup;
                                                        if (siec == false) {
                                                            JOptionPane.showMessageDialog(rootPane, "SZACH! KROL JEST ATAKOWANY", "Ostrzeżenie",
                                                                    JOptionPane.WARNING_MESSAGE);
                                                        }
                                                        Toolkit.getDefaultToolkit().beep();
                                                        kontrolamat = ust;
                                                        for (int i = 0; i < 8; i++) {
                                                            for (int j = 0; j < 8; j++) {
                                                                USTAWIENIE1[i][j] = ust[i][j];
                                                                if ((ruchB == true && kontrolamat[i][j] == 'K') || (ruchB == false && kontrolamat[i][j] == 'k')) {
                                                                    poza_krolewska[0] = i;
                                                                    poza_krolewska[1] = j;
                                                                }
                                                            }
                                                        }
                                                        klopoty = Wspomagacz.znajdzklopot(kontrolamat, ruchB);
                                                        System.out.println("klopoty X:" + klopoty[0] + "Y:" + klopoty[1]);
                                                        System.out.println(ust[klopoty[0]][klopoty[1]]);
                                                        hodu = SzachMatPatKontrola.uciekaj(kontrolamat, ruchB, poza_krolewska);
                                                        if (hodu == false) {
                                                            backup = ust;
                                                            klopoty = Wspomagacz.znajdzklopot(kontrolamat, ruchB);
                                                            hitme = SzachMatPatKontrola.znajdzodsiecz(backup.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), kol, przelotcan);
                                                            if (hitme == false) {
                                                                backup = ust;
                                                                protectme = SzachMatPatKontrola.zastaw(backup.clone(), ruchB, Wspomagacz.znajdzklopot(kontrolamat, ruchB), poza_krolewska, przelotcan);
                                                            }
                                                        }
                                                        System.out.println("ust");
                                                        for (int i = 0; i < 8; i++) {
                                                            System.arraycopy(USTAWIENIE1[i], 0, ust[i], 0, 8);
                                                            System.out.println();
                                                        }
                                                        System.out.println("U" + hodu + " Z" + protectme + " B" + hitme);

                                                    } else {
                                                        krolS = false;
                                                        move = false;
                                                        for (int i = 0; i < 8; i++) {
                                                            for (int j = 0; j < 8; j++) {
                                                                System.out.print("[" + ust[i][j] + "]");
                                                            }
                                                            System.out.println();
                                                        }
                                                        for (int i = 0; i < 8; i++) {
                                                            for (int j = 0; j < 8; j++) {
                                                                if (ust[i][j] != ' ') {
                                                                    pole_baza[0] = j;
                                                                    pole_baza[1] = i;
                                                                    if (ust[i][j] == 'K' || ust[i][j] == 'k') {
                                                                        if ((ruchB == true && ust[i][j] == 'K') || (ruchB == false && ust[i][j] == 'k')) {
                                                                            pole_baza[0] = i;
                                                                            pole_baza[1] = j;
                                                                            hodu = SzachMatPatKontrola.uciekaj(ust, ruchB, pole_baza);
                                                                        }
                                                                    } else {
                                                                        move = SzachMatPatKontrola.znajdz_ruch(ust, ruchB, ust[i][j], pole_baza, przelotcan);
                                                                        if (move == true) {
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (move == true) {
                                                                break;
                                                            }
                                                        }

                                                    }
                                                } else {
                                                    koniecanimacji = true;
                                                    BUTTON.setIcon(Cursor);
                                                }
                                            }
                                        }
                                    } else {
                                        if (lokalS[0] == lokalK[0] && lokalS[1] == lokalK[1]) {
                                            czysc_rame();
                                            ustawrame();
                                            koniecanimacji = true;
                                            zmien = false;
                                            promo = ' ';
                                            BUTTON.setIcon(Cursor);
                                            polestart = false;
                                            wyk = false;
                                            ust[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                                        } else {
                                            polestart = true;
                                            if (czasgry == -1 && siec == false) {
                                                if (opcje_pomoc == 0 || opcje_pomoc == 2) {
                                                    JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH NIEZGODNY Z ZASADAMI", "Ostrzeżenie",
                                                            JOptionPane.INFORMATION_MESSAGE);

                                                    // JOptionPane.showMessageDialog(rootPane, "BŁĄD! RUCH WYSTAWIA KRÓLA NA ZAGROŻENIE, \n LUB POZOSTAWIA KRÓLA W ZAGROŻENIU", "Ostrzeżenie",
                                                    //       JOptionPane.INFORMATION_MESSAGE);
                                                    switch (symbol) {
                                                        case 'K':
                                                        case 'k':
                                                            JOptionPane.showMessageDialog(rootPane, "Król może na dowolne nie zagrożone biciem sąsiadujące z każdej strony pole", "szachowe prawidła król",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                        case 'Q':
                                                        case 'q':
                                                            JOptionPane.showMessageDialog(rootPane, "Hetman może po każdej przekątnej i każdej prostej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła hetman",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                        case 'R':
                                                        case 'r':
                                                            JOptionPane.showMessageDialog(rootPane, "Wieża może poruszać się tylko po prostej o ile chce chyba że coś stoi na drodze", "szachowe prawidła wieża",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                        case 'B':
                                                        case 'b':
                                                            JOptionPane.showMessageDialog(rootPane, "Goniec może poruszać się tylko po przekątnej o ile chce, chyba że coś stoi na drodze", "szachowe prawidła goniec",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                        case 'N':
                                                        case 'n':
                                                            JOptionPane.showMessageDialog(rootPane, "Skoczek może jako jedyny przeskakiwać figury. Tylko docelowe pole nie może być zajęte przez sprzymierzoną figurę.\n"
                                                                    + " Konie skaczą o 2 pola po prostej i 1 w bok .Przypomina to literę ’L’", "szachowe prawidła skoczek",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                        case 'P':
                                                        case 'p':
                                                            JOptionPane.showMessageDialog(rootPane, "Piony mogą się poruszać się tylko po prostej na przód o 1 pole i nigdy nie idą inaczej. Bije tylko 1 pole po przekątnej do przodu. \n"
                                                                    + "Jeśli wciąż stoi na swojej linii (białe 2 linia czarne 7 linia) to ma prawo ruszyć się o 2 pola na przód.)", "szachowe prawidła: pionek",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            JOptionPane.showMessageDialog(rootPane, "Jeśli pion przeciwny ruszył o 2 pola i stanie obok twego piona, twój pion może go bić w przelocie.\n"
                                                                    + " Czyli rusza się za piona co ruszył o 2 pola na przód i bije go.\n"
                                                                    + " Można go wykonać tylko bezpośrednio po tym ruchu i tylko pion piona tak może. Jeśli nie bijesz w przelocie, nie będziesz mógł go bić w przelocie później", "szachowe prawidła, bicie w przelocie",
                                                                    JOptionPane.INFORMATION_MESSAGE, Cursor);
                                                            break;
                                                    }
                                                }
                                            } else {
                                                Toolkit.getDefaultToolkit().beep();
                                            }
                                        }
                                    }
                                }
                                if (polestart == false) {
                                    if (((lokalS[0] != lokalK[0]) || (lokalK[1] != lokalS[1])) && kon == true) {
                                        if (SI_ON == false && siec == false && obrotowy.getText().equals("Obrót WŁ") == true) {
                                            odwrot = !odwrot;
                                        }
                                        ustawrame();

                                        if (tryb == 2) {
                                            if (odwrot == false) {
                                                if (ruchB == false) {
                                                    czasB.s = sekbaza;
                                                    czasB.m = seksyg;
                                                    czasB.warn = false;
                                                    zegarbiale.setBackground(Color.BLACK);
                                                    zegarbiale.setForeground(Color.BLACK);
                                                    zegarbiale.setText(String.valueOf(czasB.s));
                                                } else {
                                                    czasC.s = sekbaza;
                                                    czasC.m = seksyg;
                                                    czasC.warn = false;
                                                    zegarczarne.setBackground(Color.BLACK);
                                                    zegarczarne.setForeground(Color.BLACK);
                                                    zegarczarne.setText(String.valueOf(czasC.s));
                                                }
                                            } else {
                                                if (ruchB == false) {
                                                    czasC.s = sekbaza;
                                                    czasC.m = seksyg;
                                                    czasC.warn = false;
                                                    zegarczarne.setBackground(Color.BLACK);
                                                    zegarczarne.setForeground(Color.BLACK);
                                                    zegarczarne.setText(String.valueOf(czasC.s));
                                                } else {
                                                    czasB.s = sekbaza;
                                                    czasB.m = seksyg;
                                                    czasB.warn = false;
                                                    zegarbiale.setBackground(Color.BLACK);
                                                    zegarbiale.setForeground(Color.BLACK);
                                                    zegarbiale.setText(String.valueOf(czasB.s));
                                                }
                                            }
                                        }
                                        if (tryb == 1) {
                                            switch (czasgry) {
                                                case 2:
                                                case 4:
                                                case 6:
                                                case 8:
                                                case 10:
                                                    if (ruchB == false) {
                                                        czasB.s = czasB.s + bonuss;
                                                        if (czasB.s >= 60) {
                                                            czasB.s = czasB.s - 60;
                                                            czasB.m = czasB.m + 1;
                                                        }
                                                        if (czasB.s < 10) {
                                                            zegarbiale.setText(czasB.m + ":0" + czasB.s);
                                                        } else {
                                                            zegarbiale.setText(czasB.m + ":" + czasB.s);
                                                        }
                                                    } else {
                                                        czasC.s = czasC.s + bonuss;
                                                        if (czasC.s >= 60) {
                                                            czasC.s = czasC.s - 60;
                                                            czasC.m = czasC.m + 1;
                                                        }

                                                        if (czasB.s < 10) {
                                                            zegarczarne.setText(czasB.m + ":0" + czasB.s);
                                                        } else {
                                                            zegarczarne.setText(czasB.m + ":" + czasB.s);
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                        boolean zawrot = obrotowy.getText().equals("Obrót WŁ") == true && siec == false;
                                        System.out.println(zawrot);
                                        if ((((zawrot == true) && (odwrot == false))) || (odwrot == true && (SI_ON == true || siec == true))) {
                                            System.out.println(start);
                                            switch (start.charAt(0)) {
                                                case 'A':
                                                    start = start.replace('A', 'H');
                                                    break;
                                                case 'B':
                                                    start = start.replace('B', 'G');
                                                    break;
                                                case 'C':
                                                    start = start.replace('C', 'F');
                                                    break;
                                                case 'D':
                                                    start = start.replace('D', 'E');
                                                    break;
                                                case 'E':
                                                    start = start.replace('E', 'D');
                                                    break;
                                                case 'F':
                                                    start = start.replace('F', 'C');
                                                    break;
                                                case 'G':
                                                    start = start.replace('G', 'B');
                                                    break;
                                                case 'H':
                                                    start = start.replace('H', 'A');
                                                    break;
                                            }
                                            switch (start.charAt(1)) {
                                                case '1':
                                                    start = start.replace('1', '8');
                                                    break;
                                                case '2':
                                                    start = start.replace('2', '7');
                                                    break;
                                                case '3':
                                                    start = start.replace('3', '6');
                                                    break;
                                                case '4':
                                                    start = start.replace('4', '5');
                                                    break;
                                                case '5':
                                                    start = start.replace('5', '4');
                                                    break;
                                                case '6':
                                                    start = start.replace('6', '3');
                                                    break;
                                                case '7':
                                                    start = start.replace('7', '2');
                                                    break;
                                                case '8':
                                                    start = start.replace('8', '1');
                                                    break;
                                            }
                                            switch (stop.charAt(0)) {
                                                case 'A':
                                                    stop = stop.replace('A', 'H');
                                                    break;
                                                case 'B':
                                                    stop = stop.replace('B', 'G');
                                                    break;
                                                case 'C':
                                                    stop = stop.replace('C', 'F');
                                                    break;
                                                case 'D':
                                                    stop = stop.replace('D', 'E');
                                                    break;
                                                case 'E':
                                                    stop = stop.replace('E', 'D');
                                                    break;
                                                case 'F':
                                                    stop = stop.replace('F', 'C');
                                                    break;
                                                case 'G':
                                                    stop = stop.replace('G', 'B');
                                                    break;
                                                case 'H':
                                                    stop = stop.replace('H', 'A');
                                                    break;
                                            }
                                            switch (stop.charAt(1)) {
                                                case '1':
                                                    stop = stop.replace('1', '8');
                                                    break;
                                                case '2':
                                                    stop = stop.replace('2', '7');
                                                    break;
                                                case '3':
                                                    stop = stop.replace('3', '6');
                                                    break;
                                                case '4':
                                                    stop = stop.replace('4', '5');
                                                    break;
                                                case '5':
                                                    stop = stop.replace('5', '4');
                                                    break;
                                                case '6':
                                                    stop = stop.replace('6', '3');
                                                    break;
                                                case '7':
                                                    stop = stop.replace('7', '2');
                                                    break;
                                                case '8':
                                                    stop = stop.replace('8', '1');
                                                    break;
                                            }
                                        }
                                        ostatni_start = start;
                                        ostatni_stop = stop;
                                        ustawrame();
                                        if (((symbol == 'K' || (symbol == 'k')) && (lokalS[0] - lokalK[0] == -2 || lokalS[0] - lokalK[0] == 2))) {
                                            if (ruchB == false) {
                                                if (lokalS[0] - lokalK[0] == -2) {
                                                    jTextArea3.append(movenr + "  O-O");
                                                    ruch = "O-O";
                                                    if (krolS == true) {
                                                        if (hodu == false && hitme == false && protectme == false) {
                                                            jTextArea3.append("#");
                                                        } else {
                                                            jTextArea3.append("+");
                                                        }

                                                    }
                                                } else {
                                                    if (lokalS[0] - lokalK[0] == 2) {
                                                        jTextArea3.append(movenr + "  O-O-O");
                                                        ruch = "O-O-O";
                                                        if (krolS == true) {
                                                            if (hodu == false && hitme == false && protectme == false) {
                                                                jTextArea3.append("#");
                                                            } else {
                                                                jTextArea3.append("+");
                                                            }
                                                            ostatni_start = "E1";
                                                            ostatni_stop = "C1";
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (lokalS[0] - lokalK[0] == -2) {
                                                    jTextArea3.append("     O-O");
                                                    ruch = "O-O";
                                                    if (krolS == true) {
                                                        if (hodu == false && hitme == false && protectme == false) {
                                                            jTextArea3.append("#");
                                                        } else {
                                                            jTextArea3.append("+");
                                                        }
                                                        ostatni_start = "E8";
                                                        ostatni_stop = "G8";
                                                    }
                                                } else {
                                                    if (lokalS[0] - lokalK[0] == 2) {
                                                        jTextArea3.append("     O-O-O");
                                                        ruch = "O-O-O";
                                                        if (krolS == true) {
                                                            if (hodu == false && hitme == false && protectme == false) {
                                                                jTextArea3.append("#");
                                                            } else {
                                                                jTextArea3.append("+");
                                                            }
                                                            ostatni_start = "E8";
                                                            ostatni_stop = "C8";
                                                        }
                                                    }
                                                }
                                                jTextArea3.append("\n");
                                                movenr = movenr + 1;
                                            }
                                        } else {
                                            if (prze == false) {
                                                if (ruchB == false) {
                                                    if ((stop.charAt(1) == '8') && (promo != ' ') && (promocja == true)) {
                                                        if (bicie == false) {
                                                            jTextArea3.append(movenr + ".  P" + start + "-" + stop + "=" + promo);
                                                            ruch = "P" + start + "-" + stop + "=" + promo;
                                                        } else {
                                                            jTextArea3.append(movenr + ".  P" + start + "x" + stop + "=" + promo);
                                                            ruch = "P" + start + "x" + stop + "=" + promo;
                                                        }
                                                    } else {
                                                        if (bicie == false) {
                                                            jTextArea3.append(movenr + ".  " + symbol + start + "-" + stop);
                                                            ruch = symbol + start + "-" + stop;
                                                        } else {
                                                            if (dokonanoEP == true) {
                                                                jTextArea3.append(movenr + ".  " + symbol + start + "x" + stop + "EP");
                                                            } else {
                                                                jTextArea3.append(movenr + ".  " + symbol + start + "x" + stop);
                                                            }
                                                            ruch = symbol + start + "x" + stop;
                                                        }
                                                    }
                                                    if (krolS == true) {
                                                        if (hodu == false && hitme == false && protectme == false) {
                                                            jTextArea3.append("#");
                                                        } else {
                                                            jTextArea3.append("+");
                                                        }
                                                    }
                                                } else {
                                                    if ((stop.charAt(1) == '1') && (promo != ' ') && (promocja == true)) {
                                                        if (bicie == false) {
                                                            jTextArea3.append("    p" + start + "-" + stop + "=" + promo);
                                                            ruch = "P" + start + "-" + stop + "=" + promo;
                                                        } else {
                                                            jTextArea3.append("    p" + start + "x" + stop + "=" + promo);
                                                            ruch = "P" + start + "x" + stop + "=" + promo;
                                                        }

                                                    } else {
                                                        if (bicie == false) {
                                                            jTextArea3.append("    " + symbol + start + "-" + stop);
                                                            ruch = symbol + start + "-" + stop;
                                                        } else {
                                                            if (dokonanoEP == true) {
                                                                jTextArea3.append("  " + symbol + start + "x" + stop + "EP");
                                                            } else {
                                                                jTextArea3.append("  " + symbol + start + "x" + stop);
                                                            }
                                                            ruch = symbol + start + "x" + stop;

                                                        }
                                                    }
                                                    if (krolS == true) {
                                                        if (hodu == false && hitme == false && protectme == false) {
                                                            jTextArea3.append("#");

                                                        } else {
                                                            jTextArea3.append("+");
                                                        }

                                                    }
                                                    jTextArea3.append("\n");
                                                    movenr = movenr + 1;
                                                }
                                            }
                                            if (symbol == 'K') {
                                                kingrochB = false;
                                                dokonano_RB = true;
                                            }
                                            if (symbol == 'k') {
                                                kingrochC = false;
                                                dokonano_RC = true;
                                            }
                                            if (odwrot == false) {
                                                switch (start) {
                                                    case "A1":
                                                        wleft = false;
                                                        if (!wright && !wleft) {
                                                            kingrochB = false;
                                                        }
                                                        break;
                                                    case "A8":
                                                        wleft = false;
                                                        if (!wright && !wleft) {
                                                            kingrochB = false;
                                                        }
                                                        break;
                                                    case "H1":
                                                        bleft = false;
                                                        if (!bright && !bleft) {
                                                            kingrochC = false;
                                                        }
                                                        break;
                                                    case "H8":
                                                        bright = false;
                                                        if (!bright && !bleft) {
                                                            kingrochC = false;
                                                        }
                                                        break;
                                                }
                                            } else {
                                                switch (start) {
                                                    case "A1":
                                                        bright = false;
                                                        if (!bright && !bleft) {
                                                            kingrochC = false;
                                                        }
                                                        break;
                                                    case "A8":
                                                        bleft = false;
                                                        if (!bright && !bleft) {
                                                            kingrochC = false;
                                                        }
                                                        break;
                                                    case "H1":
                                                        wright = false;
                                                        if (!wright && !wleft) {
                                                            kingrochB = false;
                                                        }
                                                        break;
                                                    case "H8":
                                                        wleft = false;
                                                        if (!wright && !wleft) {
                                                            kingrochB = false;
                                                        }
                                                        break;
                                                }
                                            }
                                        }
                                        if (siec == true) {
                                            try {
                                                msgwy = ruch;
                                                out.writeUTF(msgwy);
                                            } catch (IOException ex) {
                                                Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if (bicie == true || (promocja == true || (symbol == 'P' || symbol == 'p'))) {
                                            dolicz = false;
                                            zasada50 = 0;
                                            bicie = false;
                                        } else {
                                            if (dolicz == true) {
                                                zasada50 = (byte) (zasada50 + 1);
                                                dolicz = false;
                                            } else {
                                                dolicz = true;
                                            }
                                        }
                                        if (lokalK[0] != lokalS[0] || lokalK[1] != lokalS[1]) {
                                            String tmp3;
                                            int powtorki = 0;
                                            tmp3 = "";
                                            for (int x = 0; x < 8; x++) {
                                                for (int y = 0; y < 8; y++) {
                                                    tmp3 = tmp3.concat(String.valueOf(ust[x][y]));
                                                }
                                            }
                                            tmp3 = tmp3.concat(String.valueOf(" " + ruchB + " " + przelotcan + " " + kingrochB + " " + kingrochC));
                                            historia.add(tmp3);
                                            Collections.sort(historia);
                                            if (historia.size() > 2) {
                                                for (int i = 1; i < historia.size(); i++) {
                                                    if (historia.get(i).equals(historia.get(i - 1))) {
                                                        powtorki = powtorki + 1;
                                                        if (powtorki == 2) {
                                                            if (czasgry != -1) {
                                                                whitetime.interrupt();
                                                                blacktime.interrupt();
                                                            }
                                                            JOptionPane.showMessageDialog(rootPane, "3-krotne powtórzenie pozycji. \nREMIS!", "Zasada", JOptionPane.WARNING_MESSAGE);
                                                            remis();
                                                        }
                                                    } else {
                                                        powtorki = 0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                promo = ' ';
                                znak_promocji = ' ';
                                promocja = false;
                                roch = false;
                                dokonanoEP = false;
                            }
                        }
                    }
                }
                if (hodu == false && hitme == false && protectme == false) {
                    if (czasgry != -1) {
                        whitetime.interrupt();
                        blacktime.interrupt();
                    }
                    JOptionPane.showMessageDialog(rootPane, "SZACH MAT!", "Ostrzeżenie",
                            JOptionPane.WARNING_MESSAGE);
                    kapitulacja();
                }
                if (hodu == false && move == false) {
                    if (czasgry != -1) {
                        whitetime.interrupt();
                        blacktime.interrupt();
                    }
                    JOptionPane.showMessageDialog(rootPane, "PAT! REMIS", "Remis",
                            JOptionPane.WARNING_MESSAGE);

                    remis();
                }
                if ((pionB < 1 && pionC < 1 && lekkieB < 2 && lekkieC < 2 && ciezkieB < 1 && ciezkieC < 1)) {
                    if (czasgry != -1) {
                        whitetime.interrupt();
                        blacktime.interrupt();
                    }
                    JOptionPane.showMessageDialog(rootPane, "Remis na wskutek niewystarczajacego materiału do mata.", "Remis", JOptionPane.WARNING_MESSAGE);
                    remis();
                }
                if (zasada50 == 50 && krolS == false && (hodu == true || protectme == true || hitme == true)) {
                    if (czasgry != -1) {
                        whitetime.interrupt();
                        blacktime.interrupt();
                    }
                    JOptionPane.showMessageDialog(rootPane, "zasada 50 ruchów.(50 ruchów po obu stronach bez bicia lub ruchu pionem). \nREMIS!", "Zasada", JOptionPane.WARNING_MESSAGE);
                    remis();
                }
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        odwrotna[i][j] = ust[7 - i][7 - j];
                    }
                }
                if (SI_wyk == false && SI_ON == true && tura_rywala == ruchB && (move == true || hodu == true || hitme == true || protectme == true)) {
                    char[][] backup1 = new char[8][8];
                    for (int i = 0; i < 8; i++) {
                        System.arraycopy(ust[i], 0, backup1[i], 0, 8);
                    }
                    SI_ma_ruch();
                }
            }
            pomoc_ruch = ruchB ? Color.blue : Color.red;
            styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        } else {
            BUTTON.setIcon(Cursor);
            pomocs = BUTTON.getName();
            pomoc2 = pomocs.charAt(0);
            switch (pomoc2) {
                case 'A':
                    pomocx = 0;
                    break;
                case 'B':
                    pomocx = 1;
                    break;
                case 'C':
                    pomocx = 2;
                    break;
                case 'D':
                    pomocx = 3;
                    break;
                case 'E':
                    pomocx = 4;
                    break;
                case 'F':
                    pomocx = 5;
                    break;
                case 'G':
                    pomocx = 6;
                    break;
                case 'H':
                    pomocx = 7;
                    break;
            }
            pomoc3 = pomocs.charAt(1);
            switch (pomoc3) {
                case '1':
                    pomocy = 0;
                    break;
                case '2':
                    pomocy = 1;
                    break;
                case '3':
                    pomocy = 2;
                    break;
                case '4':
                    pomocy = 3;
                    break;
                case '5':
                    pomocy = 4;
                    break;
                case '6':
                    pomocy = 5;
                    break;
                case '7':
                    pomocy = 6;
                    break;
                case '8':
                    pomocy = 7;
                    break;
            }
            switch (ust[pomocy][pomocx]) {
                case 'P':
                    pionB--;
                    break;
                case 'p':
                    pionC--;
                    break;
                case 'N':
                    lekkieB--;
                    break;
                case 'n':
                    lekkieC--;
                    break;
                case 'B':
                    lekkieB--;
                    break;
                case 'b':
                    lekkieC--;
                    break;
                case 'R':
                    ciezkieB--;
                    break;
                case 'r':
                    ciezkieC--;
                    break;
                case 'Q':
                    ciezkieB--;
                    break;
                case 'q':
                    ciezkieC--;
                    break;
            }
            if (ust[pomocy][pomocx] == 'K') {
                krole_biale--;
            }
            if (ust[pomocy][pomocx] == 'k') {
                krole_czarne--;
            }
            ust[pomocy][pomocx] = symbol;
            // System.out.println("["+ust[pomocy][pomocx]+"]");
            //System.out.println(pomocy+"|"+pomocx);
            if (ust[pomocy][pomocx] == 'K') {
                krole_biale++;
            }
            if (ust[pomocy][pomocx] == 'k') {
                krole_czarne++;
            }
            switch (symbol) {
                case 'P':
                    if (pomocy != 7) {
                        pionB++;
                    } else {
                        ust[pomocy][pomocx] = ' ';
                        BUTTON.setIcon(null);
                    }
                    break;
                case 'p':
                    if (pomocy != 0) {
                        pionC++;
                    } else {
                        ust[pomocy][pomocx] = ' ';
                        BUTTON.setIcon(null);
                    }
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
            styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        }

        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);

    }

    static String naStringa(Ruch move) {
        String nazwa_ruchu = "";
        if (move.roszada == true) {
            if (move.dlugaroszada == true) {
                return move.szach == true ? "O-O-O+" : "O-O-O ";
            } else {
                return move.szach == true ? "O-O+  " : "O-O   ";
            }
        } else {
            switch (move.kolejnosc) {
                case Goniec:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("B");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("b");
                    }
                    break;
                case Hetman:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("Q");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("q");
                    }
                    break;
                case Krol:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("K");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("k");
                    }
                    break;
                case Pion:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("P");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("p");
                    }
                    break;
                case Skoczek:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("N");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("n");
                    }
                    break;
                case Wieza:
                    if (move.czybialy == true) {
                        nazwa_ruchu = nazwa_ruchu.concat("R");
                    } else {
                        nazwa_ruchu = nazwa_ruchu.concat("r");
                    }
                    break;
            }
            switch (move.start1) {
                case k1:
                    nazwa_ruchu = nazwa_ruchu.concat("A");
                    break;
                case k2:
                    nazwa_ruchu = nazwa_ruchu.concat("B");
                    break;
                case k3:
                    nazwa_ruchu = nazwa_ruchu.concat("C");
                    break;
                case k4:
                    nazwa_ruchu = nazwa_ruchu.concat("D");
                    break;
                case k5:
                    nazwa_ruchu = nazwa_ruchu.concat("E");
                    break;
                case k6:
                    nazwa_ruchu = nazwa_ruchu.concat("F");
                    break;
                case k7:
                    nazwa_ruchu = nazwa_ruchu.concat("G");
                    break;
                case k8:
                    nazwa_ruchu = nazwa_ruchu.concat("H");
                    break;
            }
            switch (move.start2) {
                case r1:
                    nazwa_ruchu = nazwa_ruchu.concat("1");
                    break;
                case r2:
                    nazwa_ruchu = nazwa_ruchu.concat("2");
                    break;
                case r3:
                    nazwa_ruchu = nazwa_ruchu.concat("3");
                    break;
                case r4:
                    nazwa_ruchu = nazwa_ruchu.concat("4");
                    break;
                case r5:
                    nazwa_ruchu = nazwa_ruchu.concat("5");
                    break;
                case r6:
                    nazwa_ruchu = nazwa_ruchu.concat("6");
                    break;
                case r7:
                    nazwa_ruchu = nazwa_ruchu.concat("7");
                    break;
                case r8:
                    nazwa_ruchu = nazwa_ruchu.concat("8");
                    break;
            }

            if (move.korzystnosc_bicia != Ruch.figura.Pustka) {
                nazwa_ruchu = nazwa_ruchu.concat("x");
            } else {
                nazwa_ruchu = nazwa_ruchu.concat("-");
            }
            switch (move.koniec1) {
                case k1:
                    nazwa_ruchu = nazwa_ruchu.concat("A");
                    break;
                case k2:
                    nazwa_ruchu = nazwa_ruchu.concat("B");
                    break;
                case k3:
                    nazwa_ruchu = nazwa_ruchu.concat("C");
                    break;
                case k4:
                    nazwa_ruchu = nazwa_ruchu.concat("D");
                    break;
                case k5:
                    nazwa_ruchu = nazwa_ruchu.concat("E");
                    break;
                case k6:
                    nazwa_ruchu = nazwa_ruchu.concat("F");
                    break;
                case k7:
                    nazwa_ruchu = nazwa_ruchu.concat("G");
                    break;
                case k8:
                    nazwa_ruchu = nazwa_ruchu.concat("H");
                    break;
            }
            switch (move.koniec2) {
                case r1:
                    nazwa_ruchu = nazwa_ruchu.concat("1");
                    break;
                case r2:
                    nazwa_ruchu = nazwa_ruchu.concat("2");
                    break;
                case r3:
                    nazwa_ruchu = nazwa_ruchu.concat("3");
                    break;
                case r4:
                    nazwa_ruchu = nazwa_ruchu.concat("4");
                    break;
                case r5:
                    nazwa_ruchu = nazwa_ruchu.concat("5");
                    break;
                case r6:
                    nazwa_ruchu = nazwa_ruchu.concat("6");
                    break;
                case r7:
                    nazwa_ruchu = nazwa_ruchu.concat("7");
                    break;
                case r8:
                    nazwa_ruchu = nazwa_ruchu.concat("8");
                    break;
            }
            if (move.przelot == true) {
                nazwa_ruchu = nazwa_ruchu.concat("EP");
            } else if (move.promocja == true) {
                switch (move.promowana) {
                    case Skoczek:
                        if (move.czybialy == true) {
                            nazwa_ruchu = nazwa_ruchu.concat("=N");
                        } else {
                            nazwa_ruchu = nazwa_ruchu.concat("=n");
                        }
                        break;
                    case Goniec:
                        if (move.czybialy == true) {
                            nazwa_ruchu = nazwa_ruchu.concat("=B");
                        } else {
                            nazwa_ruchu = nazwa_ruchu.concat("=b");
                        }
                        break;
                    case Wieza:
                        if (move.czybialy == true) {
                            nazwa_ruchu = nazwa_ruchu.concat("=R");
                        } else {
                            nazwa_ruchu = nazwa_ruchu.concat("=r");
                        }
                        break;
                    case Hetman:
                        if (move.czybialy == true) {
                            nazwa_ruchu = nazwa_ruchu.concat("=Q");
                        } else {
                            nazwa_ruchu = nazwa_ruchu.concat("=q");
                        }
                        break;
                }
            } else {
                nazwa_ruchu = nazwa_ruchu.concat("--");
            }
            if (move.szach == true) {
                nazwa_ruchu = nazwa_ruchu.concat("+");
            } else {
                nazwa_ruchu = nazwa_ruchu.concat(" ");
            }

        }
        return nazwa_ruchu;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        kroj = new javax.swing.JMenu();
        alfastyl = new javax.swing.JRadioButtonMenuItem();
        klasykstyl = new javax.swing.JRadioButtonMenuItem();
        kolor = new javax.swing.JMenu();
        whiteandblackfigury = new javax.swing.JRadioButtonMenuItem();
        blueandredfigury = new javax.swing.JRadioButtonMenuItem();
        plansza = new javax.swing.JMenu();
        whiteandblackboard = new javax.swing.JRadioButtonMenuItem();
        blueandredboard = new javax.swing.JRadioButtonMenuItem();
        brownboard1 = new javax.swing.JRadioButtonMenuItem();
        Wlasne_kolor_jasne = new javax.swing.JMenuItem();
        Wlasne_kolor_ciemne = new javax.swing.JMenuItem();
        Ramowka = new javax.swing.JMenuItem();
        podpowiedz = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        A8 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        D8 = new javax.swing.JButton();
        C8 = new javax.swing.JButton();
        E8 = new javax.swing.JButton();
        E7 = new javax.swing.JButton();
        D7 = new javax.swing.JButton();
        C7 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        A7 = new javax.swing.JButton();
        H8 = new javax.swing.JButton();
        G8 = new javax.swing.JButton();
        F8 = new javax.swing.JButton();
        F7 = new javax.swing.JButton();
        G7 = new javax.swing.JButton();
        H7 = new javax.swing.JButton();
        H6 = new javax.swing.JButton();
        G6 = new javax.swing.JButton();
        F6 = new javax.swing.JButton();
        E6 = new javax.swing.JButton();
        D6 = new javax.swing.JButton();
        C6 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        A6 = new javax.swing.JButton();
        A5 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        C5 = new javax.swing.JButton();
        D5 = new javax.swing.JButton();
        E5 = new javax.swing.JButton();
        F5 = new javax.swing.JButton();
        G5 = new javax.swing.JButton();
        H5 = new javax.swing.JButton();
        H4 = new javax.swing.JButton();
        G4 = new javax.swing.JButton();
        F4 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        D4 = new javax.swing.JButton();
        C4 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        A4 = new javax.swing.JButton();
        A3 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        C3 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        F3 = new javax.swing.JButton();
        G3 = new javax.swing.JButton();
        H3 = new javax.swing.JButton();
        H2 = new javax.swing.JButton();
        G2 = new javax.swing.JButton();
        F2 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        C2 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        A2 = new javax.swing.JButton();
        A1 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        C1 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        E1 = new javax.swing.JButton();
        F1 = new javax.swing.JButton();
        G1 = new javax.swing.JButton();
        H1 = new javax.swing.JButton();
        jButton67 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        czarneRuch = new javax.swing.JRadioButton();
        bialeRuch = new javax.swing.JRadioButton();
        ustawWP = new javax.swing.JRadioButton();
        ustawBP = new javax.swing.JRadioButton();
        ustawWN = new javax.swing.JRadioButton();
        ustawBN = new javax.swing.JRadioButton();
        ustawWB = new javax.swing.JRadioButton();
        ustawBB = new javax.swing.JRadioButton();
        ustawWR = new javax.swing.JRadioButton();
        ustawBR = new javax.swing.JRadioButton();
        ustawWQ = new javax.swing.JRadioButton();
        ustawBQ = new javax.swing.JRadioButton();
        jButton72 = new javax.swing.JButton();
        ustawWK = new javax.swing.JRadioButton();
        ustawBK = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton81 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton82 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        zegarbiale = new javax.swing.JLabel();
        zegarczarne = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        obrotowy = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        resetgame = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        Przycisk_help = new javax.swing.JButton();
        partia_wznowiona = new javax.swing.JButton();
        partia_odlozona = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        plansza1 = new javax.swing.JMenu();
        whiteandblackboard1 = new javax.swing.JRadioButtonMenuItem();
        blueandredboard1 = new javax.swing.JRadioButtonMenuItem();
        brownboard = new javax.swing.JRadioButtonMenuItem();
        Wlasne_kolor_jasne1 = new javax.swing.JMenuItem();
        Wlasne_kolor_ciemne1 = new javax.swing.JMenuItem();
        kolor1 = new javax.swing.JMenu();
        whiteandblackfigury1 = new javax.swing.JRadioButtonMenuItem();
        blueandredfigury1 = new javax.swing.JRadioButtonMenuItem();
        kroj1 = new javax.swing.JMenu();
        alfastyl1 = new javax.swing.JRadioButtonMenuItem();
        klasykstyl1 = new javax.swing.JRadioButtonMenuItem();
        Ramowka1 = new javax.swing.JMenuItem();
        podpowiedz1 = new javax.swing.JMenuItem();

        kroj.setText("zmien styl figur");

        buttonGroup1.add(alfastyl);
        alfastyl.setText("Alfa");
        alfastyl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alfastylActionPerformed(evt);
            }
        });
        kroj.add(alfastyl);

        buttonGroup1.add(klasykstyl);
        klasykstyl.setText("Klasyk");
        klasykstyl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                klasykstylActionPerformed(evt);
            }
        });
        kroj.add(klasykstyl);

        jPopupMenu1.add(kroj);

        kolor.setText("zmień kolor figur");

        buttonGroup4.add(whiteandblackfigury);
        whiteandblackfigury.setText("Biało czarni");
        whiteandblackfigury.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteandblackfiguryActionPerformed(evt);
            }
        });
        kolor.add(whiteandblackfigury);

        buttonGroup4.add(blueandredfigury);
        blueandredfigury.setText("niebiescy czerwoni");
        blueandredfigury.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueandredfiguryActionPerformed(evt);
            }
        });
        kolor.add(blueandredfigury);

        jPopupMenu1.add(kolor);

        plansza.setText("zmie styl szachownicy");

        buttonGroup5.add(whiteandblackboard);
        whiteandblackboard.setText("biało czarny");
        whiteandblackboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteandblackboardActionPerformed(evt);
            }
        });
        plansza.add(whiteandblackboard);

        buttonGroup5.add(blueandredboard);
        blueandredboard.setText("niebiesko czerwony");
        blueandredboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueandredboardActionPerformed(evt);
            }
        });
        plansza.add(blueandredboard);

        buttonGroup5.add(brownboard1);
        brownboard1.setText("jasny/ciemny brąz");
        brownboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brownboard1ActionPerformed(evt);
            }
        });
        plansza.add(brownboard1);

        Wlasne_kolor_jasne.setText("Własny kolor jasnych pól");
        Wlasne_kolor_jasne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wlasne_kolor_jasneActionPerformed(evt);
            }
        });
        plansza.add(Wlasne_kolor_jasne);

        Wlasne_kolor_ciemne.setText("Własny kolor ciemnych pól");
        Wlasne_kolor_ciemne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wlasne_kolor_ciemneActionPerformed(evt);
            }
        });
        plansza.add(Wlasne_kolor_ciemne);

        jPopupMenu1.add(plansza);

        Ramowka.setText("Zmiana obramowania pól");
        Ramowka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RamowkaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Ramowka);

        podpowiedz.setText("Zmiana koloru podpowiedzi");
        podpowiedz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                podpowiedzActionPerformed(evt);
            }
        });
        jPopupMenu1.add(podpowiedz);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Szachy Magister");
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        A8.setBackground(new java.awt.Color(255, 255, 255));
        A8.setForeground(new java.awt.Color(255, 255, 255));
        A8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Brook001.png"))); // NOI18N
        A8.setBorder(null);
        A8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A8.setEnabled(false);
        A8.setMaximumSize(new java.awt.Dimension(75, 75));
        A8.setMinimumSize(new java.awt.Dimension(75, 75));
        A8.setName("A8"); // NOI18N
        A8.setPreferredSize(new java.awt.Dimension(75, 75));
        A8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A8ActionPerformed(evt);
            }
        });

        B8.setBackground(new java.awt.Color(102, 102, 102));
        B8.setForeground(new java.awt.Color(102, 102, 102));
        B8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bknight1.png"))); // NOI18N
        B8.setBorder(null);
        B8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B8.setEnabled(false);
        B8.setMaximumSize(new java.awt.Dimension(75, 75));
        B8.setMinimumSize(new java.awt.Dimension(75, 75));
        B8.setName("B8"); // NOI18N
        B8.setPreferredSize(new java.awt.Dimension(75, 75));
        B8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B8ActionPerformed(evt);
            }
        });

        D8.setBackground(new java.awt.Color(102, 102, 102));
        D8.setForeground(new java.awt.Color(102, 102, 102));
        D8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bqueen01.png"))); // NOI18N
        D8.setBorder(null);
        D8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D8.setEnabled(false);
        D8.setMaximumSize(new java.awt.Dimension(75, 75));
        D8.setMinimumSize(new java.awt.Dimension(75, 75));
        D8.setName("D8"); // NOI18N
        D8.setPreferredSize(new java.awt.Dimension(75, 75));
        D8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D8ActionPerformed(evt);
            }
        });

        C8.setBackground(new java.awt.Color(255, 255, 255));
        C8.setForeground(new java.awt.Color(255, 255, 255));
        C8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bbishop1.png"))); // NOI18N
        C8.setBorder(null);
        C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C8.setEnabled(false);
        C8.setMaximumSize(new java.awt.Dimension(75, 75));
        C8.setMinimumSize(new java.awt.Dimension(75, 75));
        C8.setName("C8"); // NOI18N
        C8.setPreferredSize(new java.awt.Dimension(75, 75));
        C8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C8ActionPerformed(evt);
            }
        });

        E8.setBackground(new java.awt.Color(255, 255, 255));
        E8.setForeground(new java.awt.Color(255, 255, 255));
        E8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bking001.png"))); // NOI18N
        E8.setBorder(null);
        E8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E8.setEnabled(false);
        E8.setMaximumSize(new java.awt.Dimension(75, 75));
        E8.setMinimumSize(new java.awt.Dimension(75, 75));
        E8.setName("E8"); // NOI18N
        E8.setPreferredSize(new java.awt.Dimension(75, 75));
        E8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E8ActionPerformed(evt);
            }
        });

        E7.setBackground(new java.awt.Color(102, 102, 102));
        E7.setForeground(new java.awt.Color(102, 102, 102));
        E7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        E7.setBorder(null);
        E7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E7.setEnabled(false);
        E7.setMaximumSize(new java.awt.Dimension(75, 75));
        E7.setMinimumSize(new java.awt.Dimension(75, 75));
        E7.setName("E7"); // NOI18N
        E7.setPreferredSize(new java.awt.Dimension(75, 75));
        E7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E7ActionPerformed(evt);
            }
        });

        D7.setBackground(new java.awt.Color(255, 255, 255));
        D7.setForeground(new java.awt.Color(255, 255, 255));
        D7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        D7.setBorder(null);
        D7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D7.setEnabled(false);
        D7.setMaximumSize(new java.awt.Dimension(75, 75));
        D7.setMinimumSize(new java.awt.Dimension(75, 75));
        D7.setName("D7"); // NOI18N
        D7.setPreferredSize(new java.awt.Dimension(75, 75));
        D7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D7ActionPerformed(evt);
            }
        });

        C7.setBackground(new java.awt.Color(102, 102, 102));
        C7.setForeground(new java.awt.Color(102, 102, 102));
        C7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        C7.setBorder(null);
        C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C7.setEnabled(false);
        C7.setMaximumSize(new java.awt.Dimension(75, 75));
        C7.setMinimumSize(new java.awt.Dimension(75, 75));
        C7.setName("C7"); // NOI18N
        C7.setPreferredSize(new java.awt.Dimension(75, 75));
        C7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C7ActionPerformed(evt);
            }
        });

        B7.setBackground(new java.awt.Color(255, 255, 255));
        B7.setForeground(new java.awt.Color(255, 255, 255));
        B7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        B7.setBorder(null);
        B7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B7.setEnabled(false);
        B7.setMaximumSize(new java.awt.Dimension(75, 75));
        B7.setMinimumSize(new java.awt.Dimension(75, 75));
        B7.setName("B7"); // NOI18N
        B7.setPreferredSize(new java.awt.Dimension(75, 75));
        B7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B7ActionPerformed(evt);
            }
        });

        A7.setBackground(new java.awt.Color(102, 102, 102));
        A7.setForeground(new java.awt.Color(102, 102, 102));
        A7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        A7.setBorder(null);
        A7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A7.setEnabled(false);
        A7.setMaximumSize(new java.awt.Dimension(75, 75));
        A7.setMinimumSize(new java.awt.Dimension(75, 75));
        A7.setName("A7"); // NOI18N
        A7.setPreferredSize(new java.awt.Dimension(75, 75));
        A7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A7ActionPerformed(evt);
            }
        });

        H8.setBackground(new java.awt.Color(102, 102, 102));
        H8.setForeground(new java.awt.Color(102, 102, 102));
        H8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Brook001.png"))); // NOI18N
        H8.setBorder(null);
        H8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H8.setEnabled(false);
        H8.setMaximumSize(new java.awt.Dimension(75, 75));
        H8.setMinimumSize(new java.awt.Dimension(75, 75));
        H8.setName("H8"); // NOI18N
        H8.setPreferredSize(new java.awt.Dimension(75, 75));
        H8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H8ActionPerformed(evt);
            }
        });

        G8.setBackground(new java.awt.Color(255, 255, 255));
        G8.setForeground(new java.awt.Color(255, 255, 255));
        G8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bknight1.png"))); // NOI18N
        G8.setBorder(null);
        G8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G8.setEnabled(false);
        G8.setMaximumSize(new java.awt.Dimension(75, 75));
        G8.setMinimumSize(new java.awt.Dimension(75, 75));
        G8.setName("G8"); // NOI18N
        G8.setPreferredSize(new java.awt.Dimension(75, 75));
        G8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G8ActionPerformed(evt);
            }
        });

        F8.setBackground(new java.awt.Color(102, 102, 102));
        F8.setForeground(new java.awt.Color(102, 102, 102));
        F8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bbishop1.png"))); // NOI18N
        F8.setBorder(null);
        F8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F8.setEnabled(false);
        F8.setMaximumSize(new java.awt.Dimension(75, 75));
        F8.setMinimumSize(new java.awt.Dimension(75, 75));
        F8.setName("F8"); // NOI18N
        F8.setPreferredSize(new java.awt.Dimension(75, 75));
        F8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F8ActionPerformed(evt);
            }
        });

        F7.setBackground(new java.awt.Color(255, 255, 255));
        F7.setForeground(new java.awt.Color(255, 255, 255));
        F7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        F7.setBorder(null);
        F7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F7.setEnabled(false);
        F7.setMaximumSize(new java.awt.Dimension(75, 75));
        F7.setMinimumSize(new java.awt.Dimension(75, 75));
        F7.setName("F7"); // NOI18N
        F7.setPreferredSize(new java.awt.Dimension(75, 75));
        F7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F7ActionPerformed(evt);
            }
        });

        G7.setBackground(new java.awt.Color(102, 102, 102));
        G7.setForeground(new java.awt.Color(102, 102, 102));
        G7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        G7.setBorder(null);
        G7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G7.setEnabled(false);
        G7.setMaximumSize(new java.awt.Dimension(75, 75));
        G7.setMinimumSize(new java.awt.Dimension(75, 75));
        G7.setName("G7"); // NOI18N
        G7.setPreferredSize(new java.awt.Dimension(75, 75));
        G7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G7ActionPerformed(evt);
            }
        });

        H7.setBackground(new java.awt.Color(255, 255, 255));
        H7.setForeground(new java.awt.Color(255, 255, 255));
        H7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        H7.setBorder(null);
        H7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H7.setEnabled(false);
        H7.setMaximumSize(new java.awt.Dimension(75, 75));
        H7.setMinimumSize(new java.awt.Dimension(75, 75));
        H7.setName("H7"); // NOI18N
        H7.setPreferredSize(new java.awt.Dimension(75, 75));
        H7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H7ActionPerformed(evt);
            }
        });

        H6.setBackground(new java.awt.Color(102, 102, 102));
        H6.setForeground(new java.awt.Color(102, 102, 102));
        H6.setBorder(null);
        H6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H6.setEnabled(false);
        H6.setMaximumSize(new java.awt.Dimension(75, 75));
        H6.setMinimumSize(new java.awt.Dimension(75, 75));
        H6.setName("H6"); // NOI18N
        H6.setPreferredSize(new java.awt.Dimension(75, 75));
        H6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H6ActionPerformed(evt);
            }
        });

        G6.setBackground(new java.awt.Color(255, 255, 255));
        G6.setForeground(new java.awt.Color(255, 255, 255));
        G6.setBorder(null);
        G6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G6.setEnabled(false);
        G6.setMaximumSize(new java.awt.Dimension(75, 75));
        G6.setMinimumSize(new java.awt.Dimension(75, 75));
        G6.setName("G6"); // NOI18N
        G6.setPreferredSize(new java.awt.Dimension(75, 75));
        G6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G6ActionPerformed(evt);
            }
        });

        F6.setBackground(new java.awt.Color(102, 102, 102));
        F6.setForeground(new java.awt.Color(102, 102, 102));
        F6.setBorder(null);
        F6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F6.setEnabled(false);
        F6.setMaximumSize(new java.awt.Dimension(75, 75));
        F6.setMinimumSize(new java.awt.Dimension(75, 75));
        F6.setName("F6"); // NOI18N
        F6.setPreferredSize(new java.awt.Dimension(75, 75));
        F6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F6ActionPerformed(evt);
            }
        });

        E6.setBackground(new java.awt.Color(255, 255, 255));
        E6.setForeground(new java.awt.Color(255, 255, 255));
        E6.setBorder(null);
        E6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E6.setEnabled(false);
        E6.setMaximumSize(new java.awt.Dimension(75, 75));
        E6.setMinimumSize(new java.awt.Dimension(75, 75));
        E6.setName("E6"); // NOI18N
        E6.setPreferredSize(new java.awt.Dimension(75, 75));
        E6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E6ActionPerformed(evt);
            }
        });

        D6.setBackground(new java.awt.Color(102, 102, 102));
        D6.setForeground(new java.awt.Color(102, 102, 102));
        D6.setBorder(null);
        D6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D6.setEnabled(false);
        D6.setMaximumSize(new java.awt.Dimension(75, 75));
        D6.setMinimumSize(new java.awt.Dimension(75, 75));
        D6.setName("D6"); // NOI18N
        D6.setPreferredSize(new java.awt.Dimension(75, 75));
        D6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D6ActionPerformed(evt);
            }
        });

        C6.setBackground(new java.awt.Color(255, 255, 255));
        C6.setForeground(new java.awt.Color(255, 255, 255));
        C6.setBorder(null);
        C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C6.setEnabled(false);
        C6.setMaximumSize(new java.awt.Dimension(75, 75));
        C6.setMinimumSize(new java.awt.Dimension(75, 75));
        C6.setName("C6"); // NOI18N
        C6.setPreferredSize(new java.awt.Dimension(75, 75));
        C6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C6ActionPerformed(evt);
            }
        });

        B6.setBackground(new java.awt.Color(102, 102, 102));
        B6.setForeground(new java.awt.Color(102, 102, 102));
        B6.setBorder(null);
        B6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B6.setEnabled(false);
        B6.setMaximumSize(new java.awt.Dimension(75, 75));
        B6.setMinimumSize(new java.awt.Dimension(75, 75));
        B6.setName("B6"); // NOI18N
        B6.setPreferredSize(new java.awt.Dimension(75, 75));
        B6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B6ActionPerformed(evt);
            }
        });

        A6.setBackground(new java.awt.Color(255, 255, 255));
        A6.setForeground(new java.awt.Color(255, 255, 255));
        A6.setBorder(null);
        A6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A6.setEnabled(false);
        A6.setMaximumSize(new java.awt.Dimension(75, 75));
        A6.setMinimumSize(new java.awt.Dimension(75, 75));
        A6.setName("A6"); // NOI18N
        A6.setPreferredSize(new java.awt.Dimension(75, 75));
        A6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A6ActionPerformed(evt);
            }
        });

        A5.setBackground(new java.awt.Color(102, 102, 102));
        A5.setForeground(new java.awt.Color(102, 102, 102));
        A5.setBorder(null);
        A5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A5.setEnabled(false);
        A5.setMaximumSize(new java.awt.Dimension(75, 75));
        A5.setMinimumSize(new java.awt.Dimension(75, 75));
        A5.setName("A5"); // NOI18N
        A5.setPreferredSize(new java.awt.Dimension(75, 75));
        A5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A5ActionPerformed(evt);
            }
        });

        B5.setBackground(new java.awt.Color(255, 255, 255));
        B5.setForeground(new java.awt.Color(255, 255, 255));
        B5.setBorder(null);
        B5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B5.setEnabled(false);
        B5.setMaximumSize(new java.awt.Dimension(75, 75));
        B5.setMinimumSize(new java.awt.Dimension(75, 75));
        B5.setName("B5"); // NOI18N
        B5.setPreferredSize(new java.awt.Dimension(75, 75));
        B5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B5ActionPerformed(evt);
            }
        });

        C5.setBackground(new java.awt.Color(102, 102, 102));
        C5.setForeground(new java.awt.Color(102, 102, 102));
        C5.setBorder(null);
        C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C5.setEnabled(false);
        C5.setMaximumSize(new java.awt.Dimension(75, 75));
        C5.setMinimumSize(new java.awt.Dimension(75, 75));
        C5.setName("C5"); // NOI18N
        C5.setPreferredSize(new java.awt.Dimension(75, 75));
        C5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C5ActionPerformed(evt);
            }
        });

        D5.setBackground(new java.awt.Color(255, 255, 255));
        D5.setForeground(new java.awt.Color(255, 255, 255));
        D5.setBorder(null);
        D5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D5.setEnabled(false);
        D5.setMaximumSize(new java.awt.Dimension(75, 75));
        D5.setMinimumSize(new java.awt.Dimension(75, 75));
        D5.setName("D5"); // NOI18N
        D5.setPreferredSize(new java.awt.Dimension(75, 75));
        D5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D5ActionPerformed(evt);
            }
        });

        E5.setBackground(new java.awt.Color(102, 102, 102));
        E5.setForeground(new java.awt.Color(102, 102, 102));
        E5.setBorder(null);
        E5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E5.setEnabled(false);
        E5.setMaximumSize(new java.awt.Dimension(75, 75));
        E5.setMinimumSize(new java.awt.Dimension(75, 75));
        E5.setName("E5"); // NOI18N
        E5.setPreferredSize(new java.awt.Dimension(75, 75));
        E5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E5ActionPerformed(evt);
            }
        });

        F5.setBackground(new java.awt.Color(255, 255, 255));
        F5.setForeground(new java.awt.Color(255, 255, 255));
        F5.setBorder(null);
        F5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F5.setEnabled(false);
        F5.setMaximumSize(new java.awt.Dimension(75, 75));
        F5.setMinimumSize(new java.awt.Dimension(75, 75));
        F5.setName("F5"); // NOI18N
        F5.setPreferredSize(new java.awt.Dimension(75, 75));
        F5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F5ActionPerformed(evt);
            }
        });

        G5.setBackground(new java.awt.Color(102, 102, 102));
        G5.setForeground(new java.awt.Color(102, 102, 102));
        G5.setBorder(null);
        G5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G5.setEnabled(false);
        G5.setMaximumSize(new java.awt.Dimension(75, 75));
        G5.setMinimumSize(new java.awt.Dimension(75, 75));
        G5.setName("G5"); // NOI18N
        G5.setPreferredSize(new java.awt.Dimension(75, 75));
        G5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G5ActionPerformed(evt);
            }
        });

        H5.setBackground(new java.awt.Color(255, 255, 255));
        H5.setForeground(new java.awt.Color(255, 255, 255));
        H5.setBorder(null);
        H5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H5.setEnabled(false);
        H5.setMaximumSize(new java.awt.Dimension(75, 75));
        H5.setMinimumSize(new java.awt.Dimension(75, 75));
        H5.setName("H5"); // NOI18N
        H5.setPreferredSize(new java.awt.Dimension(75, 75));
        H5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H5ActionPerformed(evt);
            }
        });

        H4.setBackground(new java.awt.Color(102, 102, 102));
        H4.setForeground(new java.awt.Color(102, 102, 102));
        H4.setBorder(null);
        H4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H4.setEnabled(false);
        H4.setMaximumSize(new java.awt.Dimension(75, 75));
        H4.setMinimumSize(new java.awt.Dimension(75, 75));
        H4.setName("H4"); // NOI18N
        H4.setPreferredSize(new java.awt.Dimension(75, 75));
        H4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H4ActionPerformed(evt);
            }
        });

        G4.setBackground(new java.awt.Color(255, 255, 255));
        G4.setForeground(new java.awt.Color(255, 255, 255));
        G4.setBorder(null);
        G4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G4.setEnabled(false);
        G4.setMaximumSize(new java.awt.Dimension(75, 75));
        G4.setMinimumSize(new java.awt.Dimension(75, 75));
        G4.setName("G4"); // NOI18N
        G4.setPreferredSize(new java.awt.Dimension(75, 75));
        G4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G4ActionPerformed(evt);
            }
        });

        F4.setBackground(new java.awt.Color(102, 102, 102));
        F4.setForeground(new java.awt.Color(102, 102, 102));
        F4.setBorder(null);
        F4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F4.setEnabled(false);
        F4.setMaximumSize(new java.awt.Dimension(75, 75));
        F4.setMinimumSize(new java.awt.Dimension(75, 75));
        F4.setName("F4"); // NOI18N
        F4.setPreferredSize(new java.awt.Dimension(75, 75));
        F4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F4ActionPerformed(evt);
            }
        });

        E4.setBackground(new java.awt.Color(255, 255, 255));
        E4.setForeground(new java.awt.Color(255, 255, 255));
        E4.setBorder(null);
        E4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E4.setEnabled(false);
        E4.setMaximumSize(new java.awt.Dimension(75, 75));
        E4.setMinimumSize(new java.awt.Dimension(75, 75));
        E4.setName("E4"); // NOI18N
        E4.setPreferredSize(new java.awt.Dimension(75, 75));
        E4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E4ActionPerformed(evt);
            }
        });

        D4.setBackground(new java.awt.Color(102, 102, 102));
        D4.setForeground(new java.awt.Color(102, 102, 102));
        D4.setBorder(null);
        D4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D4.setEnabled(false);
        D4.setMaximumSize(new java.awt.Dimension(75, 75));
        D4.setMinimumSize(new java.awt.Dimension(75, 75));
        D4.setName("D4"); // NOI18N
        D4.setPreferredSize(new java.awt.Dimension(75, 75));
        D4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D4ActionPerformed(evt);
            }
        });

        C4.setBackground(new java.awt.Color(255, 255, 255));
        C4.setForeground(new java.awt.Color(255, 255, 255));
        C4.setBorder(null);
        C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C4.setEnabled(false);
        C4.setMaximumSize(new java.awt.Dimension(75, 75));
        C4.setMinimumSize(new java.awt.Dimension(75, 75));
        C4.setName("C4"); // NOI18N
        C4.setPreferredSize(new java.awt.Dimension(75, 75));
        C4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C4ActionPerformed(evt);
            }
        });

        B4.setBackground(new java.awt.Color(102, 102, 102));
        B4.setForeground(new java.awt.Color(102, 102, 102));
        B4.setBorder(null);
        B4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B4.setEnabled(false);
        B4.setMaximumSize(new java.awt.Dimension(75, 75));
        B4.setMinimumSize(new java.awt.Dimension(75, 75));
        B4.setName("B4"); // NOI18N
        B4.setPreferredSize(new java.awt.Dimension(75, 75));
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });

        A4.setBackground(new java.awt.Color(255, 255, 255));
        A4.setForeground(new java.awt.Color(255, 255, 255));
        A4.setBorder(null);
        A4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A4.setEnabled(false);
        A4.setMaximumSize(new java.awt.Dimension(75, 75));
        A4.setMinimumSize(new java.awt.Dimension(75, 75));
        A4.setName("A4"); // NOI18N
        A4.setPreferredSize(new java.awt.Dimension(75, 75));
        A4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A4ActionPerformed(evt);
            }
        });

        A3.setBackground(new java.awt.Color(102, 102, 102));
        A3.setForeground(new java.awt.Color(102, 102, 102));
        A3.setBorder(null);
        A3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A3.setEnabled(false);
        A3.setMaximumSize(new java.awt.Dimension(75, 75));
        A3.setMinimumSize(new java.awt.Dimension(75, 75));
        A3.setName("A3"); // NOI18N
        A3.setPreferredSize(new java.awt.Dimension(75, 75));
        A3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A3ActionPerformed(evt);
            }
        });

        B3.setBackground(new java.awt.Color(255, 255, 255));
        B3.setForeground(new java.awt.Color(255, 255, 255));
        B3.setBorder(null);
        B3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B3.setEnabled(false);
        B3.setMaximumSize(new java.awt.Dimension(75, 75));
        B3.setMinimumSize(new java.awt.Dimension(75, 75));
        B3.setName("B3"); // NOI18N
        B3.setPreferredSize(new java.awt.Dimension(75, 75));
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });

        C3.setBackground(new java.awt.Color(102, 102, 102));
        C3.setForeground(new java.awt.Color(102, 102, 102));
        C3.setBorder(null);
        C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C3.setEnabled(false);
        C3.setMaximumSize(new java.awt.Dimension(75, 75));
        C3.setMinimumSize(new java.awt.Dimension(75, 75));
        C3.setName("C3"); // NOI18N
        C3.setPreferredSize(new java.awt.Dimension(75, 75));
        C3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C3ActionPerformed(evt);
            }
        });

        D3.setBackground(new java.awt.Color(255, 255, 255));
        D3.setForeground(new java.awt.Color(255, 255, 255));
        D3.setBorder(null);
        D3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D3.setEnabled(false);
        D3.setMaximumSize(new java.awt.Dimension(75, 75));
        D3.setMinimumSize(new java.awt.Dimension(75, 75));
        D3.setName("D3"); // NOI18N
        D3.setPreferredSize(new java.awt.Dimension(75, 75));
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });

        E3.setBackground(new java.awt.Color(102, 102, 102));
        E3.setForeground(new java.awt.Color(102, 102, 102));
        E3.setBorder(null);
        E3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E3.setEnabled(false);
        E3.setMaximumSize(new java.awt.Dimension(75, 75));
        E3.setMinimumSize(new java.awt.Dimension(75, 75));
        E3.setName("E3"); // NOI18N
        E3.setPreferredSize(new java.awt.Dimension(75, 75));
        E3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E3ActionPerformed(evt);
            }
        });

        F3.setBackground(new java.awt.Color(255, 255, 255));
        F3.setForeground(new java.awt.Color(255, 255, 255));
        F3.setBorder(null);
        F3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F3.setEnabled(false);
        F3.setMaximumSize(new java.awt.Dimension(75, 75));
        F3.setMinimumSize(new java.awt.Dimension(75, 75));
        F3.setName("F3"); // NOI18N
        F3.setPreferredSize(new java.awt.Dimension(75, 75));
        F3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F3ActionPerformed(evt);
            }
        });

        G3.setBackground(new java.awt.Color(102, 102, 102));
        G3.setForeground(new java.awt.Color(102, 102, 102));
        G3.setBorder(null);
        G3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G3.setEnabled(false);
        G3.setMaximumSize(new java.awt.Dimension(75, 75));
        G3.setMinimumSize(new java.awt.Dimension(75, 75));
        G3.setName("G3"); // NOI18N
        G3.setPreferredSize(new java.awt.Dimension(75, 75));
        G3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G3ActionPerformed(evt);
            }
        });

        H3.setBackground(new java.awt.Color(255, 255, 255));
        H3.setForeground(new java.awt.Color(255, 255, 255));
        H3.setBorder(null);
        H3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H3.setEnabled(false);
        H3.setMaximumSize(new java.awt.Dimension(75, 75));
        H3.setMinimumSize(new java.awt.Dimension(75, 75));
        H3.setName("H3"); // NOI18N
        H3.setPreferredSize(new java.awt.Dimension(75, 75));
        H3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H3ActionPerformed(evt);
            }
        });

        H2.setBackground(new java.awt.Color(102, 102, 102));
        H2.setForeground(new java.awt.Color(102, 102, 102));
        H2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        H2.setBorder(null);
        H2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H2.setEnabled(false);
        H2.setMaximumSize(new java.awt.Dimension(75, 75));
        H2.setMinimumSize(new java.awt.Dimension(75, 75));
        H2.setName("H2"); // NOI18N
        H2.setPreferredSize(new java.awt.Dimension(75, 75));
        H2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H2ActionPerformed(evt);
            }
        });

        G2.setBackground(new java.awt.Color(255, 255, 255));
        G2.setForeground(new java.awt.Color(255, 255, 255));
        G2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        G2.setBorder(null);
        G2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G2.setEnabled(false);
        G2.setMaximumSize(new java.awt.Dimension(75, 75));
        G2.setMinimumSize(new java.awt.Dimension(75, 75));
        G2.setName("G2"); // NOI18N
        G2.setPreferredSize(new java.awt.Dimension(75, 75));
        G2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G2ActionPerformed(evt);
            }
        });

        F2.setBackground(new java.awt.Color(102, 102, 102));
        F2.setForeground(new java.awt.Color(102, 102, 102));
        F2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        F2.setBorder(null);
        F2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F2.setEnabled(false);
        F2.setMaximumSize(new java.awt.Dimension(75, 75));
        F2.setMinimumSize(new java.awt.Dimension(75, 75));
        F2.setName("F2"); // NOI18N
        F2.setPreferredSize(new java.awt.Dimension(75, 75));
        F2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F2ActionPerformed(evt);
            }
        });

        E2.setBackground(new java.awt.Color(255, 255, 255));
        E2.setForeground(new java.awt.Color(255, 255, 255));
        E2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        E2.setBorder(null);
        E2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E2.setEnabled(false);
        E2.setMaximumSize(new java.awt.Dimension(75, 75));
        E2.setMinimumSize(new java.awt.Dimension(75, 75));
        E2.setName("E2"); // NOI18N
        E2.setPreferredSize(new java.awt.Dimension(75, 75));
        E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E2ActionPerformed(evt);
            }
        });

        D2.setBackground(new java.awt.Color(102, 102, 102));
        D2.setForeground(new java.awt.Color(102, 102, 102));
        D2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        D2.setBorder(null);
        D2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D2.setEnabled(false);
        D2.setMaximumSize(new java.awt.Dimension(75, 75));
        D2.setMinimumSize(new java.awt.Dimension(75, 75));
        D2.setName("D2"); // NOI18N
        D2.setPreferredSize(new java.awt.Dimension(75, 75));
        D2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D2ActionPerformed(evt);
            }
        });

        C2.setBackground(new java.awt.Color(255, 255, 255));
        C2.setForeground(new java.awt.Color(255, 255, 255));
        C2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        C2.setBorder(null);
        C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C2.setEnabled(false);
        C2.setMaximumSize(new java.awt.Dimension(75, 75));
        C2.setMinimumSize(new java.awt.Dimension(75, 75));
        C2.setName("C2"); // NOI18N
        C2.setPreferredSize(new java.awt.Dimension(75, 75));
        C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C2ActionPerformed(evt);
            }
        });

        B2.setBackground(new java.awt.Color(102, 102, 102));
        B2.setForeground(new java.awt.Color(102, 102, 102));
        B2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        B2.setBorder(null);
        B2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B2.setEnabled(false);
        B2.setMaximumSize(new java.awt.Dimension(75, 75));
        B2.setMinimumSize(new java.awt.Dimension(75, 75));
        B2.setName("B2"); // NOI18N
        B2.setPreferredSize(new java.awt.Dimension(75, 75));
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });

        A2.setBackground(new java.awt.Color(255, 255, 255));
        A2.setForeground(new java.awt.Color(255, 255, 255));
        A2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        A2.setBorder(null);
        A2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A2.setEnabled(false);
        A2.setMaximumSize(new java.awt.Dimension(75, 75));
        A2.setMinimumSize(new java.awt.Dimension(75, 75));
        A2.setName("A2"); // NOI18N
        A2.setPreferredSize(new java.awt.Dimension(75, 75));
        A2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A2ActionPerformed(evt);
            }
        });

        A1.setBackground(new java.awt.Color(102, 102, 102));
        A1.setForeground(new java.awt.Color(102, 102, 102));
        A1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wrook001.png"))); // NOI18N
        A1.setBorder(null);
        A1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        A1.setEnabled(false);
        A1.setMaximumSize(new java.awt.Dimension(75, 75));
        A1.setMinimumSize(new java.awt.Dimension(75, 75));
        A1.setName("A1"); // NOI18N
        A1.setPreferredSize(new java.awt.Dimension(75, 75));
        A1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A1ActionPerformed(evt);
            }
        });

        B1.setBackground(new java.awt.Color(255, 255, 255));
        B1.setForeground(new java.awt.Color(255, 255, 255));
        B1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wknight1.png"))); // NOI18N
        B1.setBorder(null);
        B1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B1.setEnabled(false);
        B1.setMaximumSize(new java.awt.Dimension(75, 75));
        B1.setMinimumSize(new java.awt.Dimension(75, 75));
        B1.setName("B1"); // NOI18N
        B1.setPreferredSize(new java.awt.Dimension(75, 75));
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });

        C1.setBackground(new java.awt.Color(102, 102, 102));
        C1.setForeground(new java.awt.Color(102, 102, 102));
        C1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wbishop1.png"))); // NOI18N
        C1.setBorder(null);
        C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        C1.setEnabled(false);
        C1.setMaximumSize(new java.awt.Dimension(75, 75));
        C1.setMinimumSize(new java.awt.Dimension(75, 75));
        C1.setName("C1"); // NOI18N
        C1.setPreferredSize(new java.awt.Dimension(75, 75));
        C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ActionPerformed(evt);
            }
        });

        D1.setBackground(new java.awt.Color(255, 255, 255));
        D1.setForeground(new java.awt.Color(255, 255, 255));
        D1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wqueen01.png"))); // NOI18N
        D1.setBorder(null);
        D1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        D1.setEnabled(false);
        D1.setMaximumSize(new java.awt.Dimension(75, 75));
        D1.setMinimumSize(new java.awt.Dimension(75, 75));
        D1.setName("D1"); // NOI18N
        D1.setPreferredSize(new java.awt.Dimension(75, 75));
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });

        E1.setBackground(new java.awt.Color(102, 102, 102));
        E1.setForeground(new java.awt.Color(102, 102, 102));
        E1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wking001.png"))); // NOI18N
        E1.setBorder(null);
        E1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        E1.setEnabled(false);
        E1.setMaximumSize(new java.awt.Dimension(75, 75));
        E1.setMinimumSize(new java.awt.Dimension(75, 75));
        E1.setName("E1"); // NOI18N
        E1.setPreferredSize(new java.awt.Dimension(75, 75));
        E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E1ActionPerformed(evt);
            }
        });

        F1.setBackground(new java.awt.Color(255, 255, 255));
        F1.setForeground(new java.awt.Color(255, 255, 255));
        F1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wbishop1.png"))); // NOI18N
        F1.setBorder(null);
        F1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        F1.setEnabled(false);
        F1.setMaximumSize(new java.awt.Dimension(75, 75));
        F1.setMinimumSize(new java.awt.Dimension(75, 75));
        F1.setName("F1"); // NOI18N
        F1.setPreferredSize(new java.awt.Dimension(75, 75));
        F1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F1ActionPerformed(evt);
            }
        });

        G1.setBackground(new java.awt.Color(102, 102, 102));
        G1.setForeground(new java.awt.Color(102, 102, 102));
        G1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wknight1.png"))); // NOI18N
        G1.setBorder(null);
        G1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        G1.setEnabled(false);
        G1.setMaximumSize(new java.awt.Dimension(75, 75));
        G1.setMinimumSize(new java.awt.Dimension(75, 75));
        G1.setName("G1"); // NOI18N
        G1.setPreferredSize(new java.awt.Dimension(75, 75));
        G1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G1ActionPerformed(evt);
            }
        });

        H1.setBackground(new java.awt.Color(255, 255, 255));
        H1.setForeground(new java.awt.Color(255, 255, 255));
        H1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wrook001.png"))); // NOI18N
        H1.setBorder(null);
        H1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        H1.setEnabled(false);
        H1.setMaximumSize(new java.awt.Dimension(75, 75));
        H1.setMinimumSize(new java.awt.Dimension(75, 75));
        H1.setName("H1"); // NOI18N
        H1.setPreferredSize(new java.awt.Dimension(75, 75));
        H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H1ActionPerformed(evt);
            }
        });

        jButton67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton67.setText("stacjonarnie");
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });

        jButton65.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton65.setText("online stworz gre");
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        jButton66.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton66.setText("poddaj się");
        jButton66.setEnabled(false);
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });

        jButton68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton68.setText("proponuj \nremis");
        jButton68.setEnabled(false);
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });

        jButton69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton69.setText("przyjmij  remis");
        jButton69.setEnabled(false);
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });

        jButton70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton70.setText("odrzuc remis");
        jButton70.setEnabled(false);
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });

        jButton71.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton71.setText("Ustaw  Pozycję");
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });

        buttonGroup3.add(czarneRuch);
        czarneRuch.setLabel("ruch dla czarnych");
        czarneRuch.setName(""); // NOI18N
        czarneRuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                czarneRuchActionPerformed(evt);
            }
        });

        buttonGroup3.add(bialeRuch);
        bialeRuch.setLabel("ruch dla białych");
        bialeRuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bialeRuchActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWP);
        ustawWP.setText("Biały pion");
        ustawWP.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wpawn001.png"))); // NOI18N
        ustawWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWPActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBP);
        ustawBP.setText("czarny pion");
        ustawBP.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bpawn001.png"))); // NOI18N
        ustawBP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBPActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWN);
        ustawWN.setText("biały skoczek");
        ustawWN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wknight1.png"))); // NOI18N
        ustawWN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWNActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBN);
        ustawBN.setText("czarny skoczek");
        ustawBN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bknight1.png"))); // NOI18N
        ustawBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBNActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWB);
        ustawWB.setText("biały goniec");
        ustawWB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wbishop1.png"))); // NOI18N
        ustawWB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWBActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBB);
        ustawBB.setText("czarny goniec");
        ustawBB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bbishop1.png"))); // NOI18N
        ustawBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBBActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWR);
        ustawWR.setText("biała wieża");
        ustawWR.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wrook001.png"))); // NOI18N
        ustawWR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWRActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBR);
        ustawBR.setText("czarna wieża");
        ustawBR.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Brook001.png"))); // NOI18N
        ustawBR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBRActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWQ);
        ustawWQ.setText("Biały hetman");
        ustawWQ.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wqueen01.png"))); // NOI18N
        ustawWQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWQActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBQ);
        ustawBQ.setText("czarny hetman");
        ustawBQ.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bqueen01.png"))); // NOI18N
        ustawBQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBQActionPerformed(evt);
            }
        });

        jButton72.setText("gotów");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawWK);
        ustawWK.setText("Biały król");
        ustawWK.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Wking001.png"))); // NOI18N
        ustawWK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawWKActionPerformed(evt);
            }
        });

        buttonGroup4.add(ustawBK);
        ustawBK.setText("czarny król");
        ustawBK.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/Bking001.png"))); // NOI18N
        ustawBK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ustawBKActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton11);
        jRadioButton11.setText("kasuj figurę");
        jRadioButton11.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/szachy/fragi.png"))); // NOI18N
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextArea2);

        jButton81.setText("wyslij wiadomosc");
        jButton81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton81ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton82.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton82.setText("online dolacz do gry");
        jButton82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton82ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("A");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("B");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("C");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("D");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("E");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("F");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("G");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("H");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("8");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("7");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("6");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("5");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("4");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("3");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("1");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("2");

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        zegarbiale.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        zegarbiale.setText("czas białych");

        zegarczarne.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        zegarczarne.setText("czas czarnych");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("pokaz moje ip");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jProgressBar1.setToolTipText("");
        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jProgressBar1.setName(""); // NOI18N
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jButton1.setText("Zlicz Pozycję");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Przebieg partii");

        obrotowy.setText("Obrót WŁ");
        obrotowy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obrotowyActionPerformed(evt);
            }
        });

        jButton2.setText("SI_włącz");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        resetgame.setText("Nowa gra(Reset)");
        resetgame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetgameActionPerformed(evt);
            }
        });

        Przycisk_help.setText("Pokaż zasady poruszania figur");
        Przycisk_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Przycisk_helpActionPerformed(evt);
            }
        });

        partia_wznowiona.setText("Wczytaj partię odłożoną");
        partia_wznowiona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partia_wznowionaActionPerformed(evt);
            }
        });

        partia_odlozona.setText("Odłóż partię");
        partia_odlozona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partia_odlozonaActionPerformed(evt);
            }
        });

        jMenu1.setText("zmień styl");

        plansza1.setText("zmie styl szachownicy");

        buttonGroup5.add(whiteandblackboard1);
        whiteandblackboard1.setText("biało czarny");
        whiteandblackboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteandblackboard1ActionPerformed(evt);
            }
        });
        plansza1.add(whiteandblackboard1);

        buttonGroup5.add(blueandredboard1);
        blueandredboard1.setText("niebiesko czerwony");
        blueandredboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueandredboard1ActionPerformed(evt);
            }
        });
        plansza1.add(blueandredboard1);

        buttonGroup5.add(brownboard);
        brownboard.setText("jasny/ciemny brąz");
        brownboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brownboardActionPerformed(evt);
            }
        });
        plansza1.add(brownboard);

        Wlasne_kolor_jasne1.setText("Własny kolor jasnych pól");
        Wlasne_kolor_jasne1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wlasne_kolor_jasne1ActionPerformed(evt);
            }
        });
        plansza1.add(Wlasne_kolor_jasne1);

        Wlasne_kolor_ciemne1.setText("Własny kolor ciemnych pól");
        Wlasne_kolor_ciemne1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wlasne_kolor_ciemne1ActionPerformed(evt);
            }
        });
        plansza1.add(Wlasne_kolor_ciemne1);

        jMenu1.add(plansza1);

        kolor1.setText("zmień kolor figur");

        buttonGroup4.add(whiteandblackfigury1);
        whiteandblackfigury1.setText("Biało czarni");
        whiteandblackfigury1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteandblackfigury1ActionPerformed(evt);
            }
        });
        kolor1.add(whiteandblackfigury1);

        buttonGroup4.add(blueandredfigury1);
        blueandredfigury1.setText("niebiescy czerwoni");
        blueandredfigury1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueandredfigury1ActionPerformed(evt);
            }
        });
        kolor1.add(blueandredfigury1);

        jMenu1.add(kolor1);

        kroj1.setText("zmien styl figur");

        buttonGroup1.add(alfastyl1);
        alfastyl1.setText("Alfa");
        alfastyl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alfastyl1ActionPerformed(evt);
            }
        });
        kroj1.add(alfastyl1);

        buttonGroup1.add(klasykstyl1);
        klasykstyl1.setText("Klasyk");
        klasykstyl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                klasykstyl1ActionPerformed(evt);
            }
        });
        kroj1.add(klasykstyl1);

        jMenu1.add(kroj1);

        Ramowka1.setText("Zmiana obramowania pól");
        Ramowka1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ramowka1ActionPerformed(evt);
            }
        });
        jMenu1.add(Ramowka1);

        podpowiedz1.setText("Zmiana koloru podpowiedzi");
        podpowiedz1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                podpowiedz1ActionPerformed(evt);
            }
        });
        jMenu1.add(podpowiedz1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(D8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(E8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(F8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(G8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(H8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(D7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(G7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(H7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1)
                                .addGap(69, 69, 69)
                                .addComponent(jLabel2)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel3)
                                .addGap(72, 72, 72)
                                .addComponent(jLabel4)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel5)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(G1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(G2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel6)
                                .addGap(72, 72, 72)
                                .addComponent(jLabel7)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel8))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton72, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(bialeRuch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(czarneRuch)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ustawWK, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawWQ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawWR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawWN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawWP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawWB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ustawBQ, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawBK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ustawBR, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawBB, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ustawBN, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(ustawBP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(27, 27, 27)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jRadioButton11))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zegarczarne)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(zegarbiale)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton65, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton82, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(obrotowy, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resetgame, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton66, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                            .addComponent(partia_odlozona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton68)
                                            .addComponent(jButton69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(partia_wznowiona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton81))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Przycisk_help, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(89, 89, 89))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(H8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel14)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(D7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel16)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel17)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel19))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(31, 31, 31)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(G1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(zegarczarne)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ustawWP, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ustawBP, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ustawWN, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ustawBN, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ustawWB, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ustawBB, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ustawWR, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ustawBR, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ustawWQ, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ustawBQ, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ustawWK, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ustawBK, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bialeRuch)
                            .addComponent(czarneRuch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton82, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(zegarbiale))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton81, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(Przycisk_help))
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13)
                            .addComponent(obrotowy))
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetgame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton67)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(partia_wznowiona)
                            .addComponent(partia_odlozona))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton70)
                            .addComponent(jButton69, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton66, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton68))))
                .addContainerGap())
        );

        czarneRuch.getAccessibleContext().setAccessibleName("RadioButton9");
        bialeRuch.getAccessibleContext().setAccessibleName("RadioButton10");
        ustawWP.getAccessibleContext().setAccessibleName("jRadioButton16");
        ustawBP.getAccessibleContext().setAccessibleName("jRadioButton17");
        ustawWN.getAccessibleContext().setAccessibleName("jRadioButton18");
        ustawBN.getAccessibleContext().setAccessibleName("jRadioButton19");
        ustawWB.getAccessibleContext().setAccessibleName("jRadioButton20");
        ustawBB.getAccessibleContext().setAccessibleName("jRadioButton21");
        ustawWR.getAccessibleContext().setAccessibleName("jRadioButton22");
        ustawBR.getAccessibleContext().setAccessibleName("jRadioButton23");
        ustawWQ.getAccessibleContext().setAccessibleName("jRadioButton24");
        ustawBQ.getAccessibleContext().setAccessibleName("jRadioButton25");
        ustawWK.getAccessibleContext().setAccessibleName("jRadioButton26");
        ustawBK.getAccessibleContext().setAccessibleName("jRadioButton27");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void D7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D7ActionPerformed

    private void A8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A8ActionPerformed

    private void B8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B8ActionPerformed

    private void C8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C8ActionPerformed

    private void D8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D8ActionPerformed

    private void E8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E8ActionPerformed

    private void F8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F8ActionPerformed

    private void G8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G8ActionPerformed

    private void H8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H8ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H8ActionPerformed

    private void A7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A7ActionPerformed

    private void B7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B7ActionPerformed

    private void C7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C7ActionPerformed

    private void E7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E7ActionPerformed

    private void F7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F7ActionPerformed

    private void G7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G7ActionPerformed

    private void H7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H7ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H7ActionPerformed

    private void A6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A6ActionPerformed

    private void B6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B6ActionPerformed

    private void C6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C6ActionPerformed

    private void D6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D6ActionPerformed

    private void E6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E6ActionPerformed

    private void F6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F6ActionPerformed

    private void G6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G6ActionPerformed

    private void H6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H6ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H6ActionPerformed

    private void H5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H5ActionPerformed

    private void G5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G5ActionPerformed

    private void F5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F5ActionPerformed

    private void E5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E5ActionPerformed

    private void D5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D5ActionPerformed

    private void C5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C5ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B5ActionPerformed

    private void A5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A5ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A5ActionPerformed

    private void A4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A4ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B4ActionPerformed

    private void C4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C4ActionPerformed

    private void D4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D4ActionPerformed

    private void E4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E4ActionPerformed

    private void F4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F4ActionPerformed

    private void G4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G4ActionPerformed

    private void H4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H4ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H4ActionPerformed

    private void H3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H3ActionPerformed

    private void G3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G3ActionPerformed

    private void F3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F3ActionPerformed

    private void E3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E3ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D3ActionPerformed

    private void C3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C3ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B3ActionPerformed

    private void A3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A3ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A3ActionPerformed

    private void A2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A2ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B2ActionPerformed

    private void C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C2ActionPerformed

    private void D2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D2ActionPerformed

    private void E2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E2ActionPerformed

    private void F2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F2ActionPerformed

    private void G2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G2ActionPerformed

    private void H2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H2ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H2ActionPerformed

    private void H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_H1ActionPerformed

    private void G1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_G1ActionPerformed

    private void F1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_F1ActionPerformed

    private void E1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_E1ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_D1ActionPerformed

    private void C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_C1ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_B1ActionPerformed

    private void A1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A1ActionPerformed
        if (tryb != 3) {
            Button_Clicked(evt.getSource());
        } else {
            Rusz_przytul(evt.getSource());
        }
    }//GEN-LAST:event_A1ActionPerformed

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
        Object[] opcje_czasu = {"60", "30", "15+10", "10", "5+5", "5", "3+2", "3", "2+1", "1", "inny system"};
        Object[] opcje_trybu = {"klasyczny bez limitu", "klasyczny na czas", "SzachMaty", "Paco Sako"};
        Object[] opcje_rywala = {"Graj z innym graczem", "Graj z SI jako białe", "Graj z SI jako czarne"};
        sztuczny_rywal = (byte) JOptionPane.showOptionDialog(rootPane, "Gra z SI?", "opcje SI", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rywala, null);
        JSlider suwak_trudnosci = new JSlider(1, 14, 1);
        Hashtable Opisy = new Hashtable();
        suwak_trudnosci.setOrientation(JSlider.VERTICAL);
        suwak_trudnosci.setMinorTickSpacing(1);
        Opisy.put(1, new JLabel("Nowicjusz"));
        Opisy.put(2, new JLabel("Przeciętny"));
        Opisy.put(4, new JLabel("Doświadczony"));
        Opisy.put(6, new JLabel("Weteran"));
        Opisy.put(8, new JLabel("Mistrz"));
        Opisy.put(10, new JLabel("Kandydat na Arcymistrza"));
        Opisy.put(12, new JLabel("Arcymistrz"));
        Opisy.put(14, new JLabel("Wielki Arcymistrz"));
        suwak_trudnosci.setLabelTable(Opisy);
        suwak_trudnosci.setPaintTicks(true);
        suwak_trudnosci.setPaintLabels(true);
        czasgry = -1;
        switch (sztuczny_rywal) {
            case 1:
                tura_rywala = false;
                tryb = 0;
                SI_ON = true;
                odwrot = false;
                obrotowy.setText("Obrót WYŁ");
                JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                glebiaSI = (byte) ((byte) ((byte) suwak_trudnosci.getValue()));
                break;
            case 2:
                tura_rywala = true;
                tryb = 0;
                SI_ON = true;
                odwrot = true;
                obrotowy.setText("Obrót WYŁ");
                JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                glebiaSI = (byte) ((byte) ((byte) suwak_trudnosci.getValue()));
                break;
            case 0:
                obrotowy.setText("Obrót WŁ");
                SI_ON = false;
                break;
            default:
                SI_ON = false;
                obrotowy.setText("Obrót WŁ");
                break;
        }
        System.out.println(glebiaSI);
        if (odwrot == true) {
            JLabel pomoc1 = zegarbiale;
            zegarbiale = zegarczarne;
            zegarczarne = pomoc1;
        }
        if (SI_ON == false) {
            tryb = (byte) JOptionPane.showOptionDialog(rootPane, "wybierz opcje gry", "opcje gry", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_trybu, null);
        }
        if (tryb == 1) {
            czasgry = JOptionPane.showOptionDialog(rootPane, "wybierz opcje czasowe", "opcje czasu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_czasu, null);
        }
        if (tryb == 2) {
            boolean prawidlowosc;
            do {
                try {
                    sek = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj na ruch w sekundach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                    prawidlowosc = sek > 0;
                    if (prawidlowosc == true) {
                        seksyg = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj dlugosc sygnału(mniejszy niż czas na ruch)", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                        prawidlowosc = sek > seksyg && seksyg > 0;
                        if (prawidlowosc == true) {
                            sekbaza = sek;
                            blokada = new ReentrantLock();
                            warunek = blokada.newCondition();
                            czasB = (new zegar(sek * 1000, zegarbiale, blokada, warunek, true, tryb, seksyg));
                            czasC = (new zegar(sek * 1000, zegarczarne, blokada, warunek, false, tryb, seksyg));
                            zegarbiale.setText(String.valueOf(sek));
                            zegarczarne.setText(String.valueOf(sek));
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku");
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku");
                    }
                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą większą od 0 \n bez wartosci po przecinku");
                    prawidlowosc = false;
                }
            } while (prawidlowosc != true);
        }
        if (tryb == 3) {
            obrotowy.setText("Obrót WYŁ");
            jMenu1.setEnabled(false);
            jPopupMenu1.setEnabled(false);
            kroj_zestaw = 1;
            kolor_zestaw = 1;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    switch (ust[i][j]) {
                        case 'K':
                        case 'Q':
                        case 'R':
                        case 'B':
                        case 'N':
                        case 'P':
                            szachownica_pokoj[i][j][0] = ust[i][j];
                            szachownica_pokoj[i][j][1] = ' ';
                            break;
                        case 'k':
                        case 'q':
                        case 'r':
                        case 'b':
                        case 'n':
                        case 'p':
                            szachownica_pokoj[i][j][1] = ust[i][j];
                            szachownica_pokoj[i][j][0] = ' ';
                            break;
                        default:
                            szachownica_pokoj[i][j][0] = ' ';
                            szachownica_pokoj[i][j][1] = ' ';
                            break;
                    }
                }
            }
        }
        blokada = new ReentrantLock();
        warunek = blokada.newCondition();
        if (tryb == 1) {
            switch (czasgry) {
                case 0:
                    czasB = (new zegar(60 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(60 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("60:00");
                    zegarczarne.setText("60:00");
                    break;
                case 1:
                    czasB = (new zegar(30 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(30 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("30:00");
                    zegarczarne.setText("30:00");

                    break;
                case 2:
                    czasB = (new zegar(15 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(15 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    bonuss = 10;
                    zegarbiale.setText("15:00");
                    zegarczarne.setText("15:00");
                    break;
                case 3:
                    czasB = (new zegar(10 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(10 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("10:00");
                    zegarczarne.setText("10:00");
                    break;
                case 4:
                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("5:00");
                    zegarczarne.setText("5:00");
                    bonuss = 5;
                    break;
                case 5:
                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("5:00");
                    zegarczarne.setText("5:00");
                    break;
                case 6:
                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("3:00");
                    zegarczarne.setText("3:00");
                    bonuss = 2;
                    break;
                case 7:
                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("3:00");
                    zegarczarne.setText("3:00");
                    break;
                case 8:
                    czasB = (new zegar(2 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(2 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("2:00");
                    zegarczarne.setText("2:00");
                    bonuss = 1;
                    break;
                case 9:
                    czasB = (new zegar(1 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(1 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("1:00");
                    zegarczarne.setText("1:00");
                    break;
                case 10:
                    boolean prawidlowosc;
                    int bazamin;
                    do {
                        try {
                            bazamin = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj czas na grę w minutach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                            prawidlowosc = bazamin > 0;
                            if (prawidlowosc == true) {
                                bonuss = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj czas dodawany do czasy  sekundach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                                if (bonuss >= 0) {
                                    czasB = (new zegar(bazamin * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(bazamin * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText(bazamin + ":00");
                                    zegarczarne.setText(bazamin + ":00");
                                } else {
                                    prawidlowosc = false;
                                    JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą większą od 0 \n bez wartosci po przecinku", "błąd parametrów", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą większą od 0 \n bez wartosci po przecinku", "błąd parametrów", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (HeadlessException | NumberFormatException e) {
                            JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą większą od 0 \n bez wartosci po przecinku", "błąd parametrów", JOptionPane.ERROR_MESSAGE);
                            prawidlowosc = false;
                        }
                    } while (prawidlowosc != true);
                    break;
                default:
                    czasB = (new zegar(60 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(60 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("60:00");
                    zegarczarne.setText("60:00");
                    czasgry = 0;
                    break;
            }
        }
        if (tryb != 0) {
            whitetime = new Thread(czasB, "bieltime");
            blacktime = new Thread(czasC, "czerntime");
            whitetime.start();
            blacktime.start();
        }

        jButton67.setVisible(false);
        jButton65.setVisible(false);
        jButton82.setVisible(false);
        ustawka = false;
        A8.setEnabled(true);
        B8.setEnabled(true);
        D8.setEnabled(true);
        C8.setEnabled(true);
        E8.setEnabled(true);
        E7.setEnabled(true);
        D7.setEnabled(true);
        C7.setEnabled(true);
        B7.setEnabled(true);
        A7.setEnabled(true);
        H8.setEnabled(true);
        G8.setEnabled(true);
        F8.setEnabled(true);
        F7.setEnabled(true);
        G7.setEnabled(true);
        H7.setEnabled(true);
        H6.setEnabled(true);
        G6.setEnabled(true);
        F6.setEnabled(true);
        E6.setEnabled(true);
        D6.setEnabled(true);
        C6.setEnabled(true);
        B6.setEnabled(true);
        A6.setEnabled(true);
        A5.setEnabled(true);
        B5.setEnabled(true);
        C5.setEnabled(true);
        D5.setEnabled(true);
        E5.setEnabled(true);
        F5.setEnabled(true);
        G5.setEnabled(true);
        H5.setEnabled(true);
        H4.setEnabled(true);
        G4.setEnabled(true);
        F4.setEnabled(true);
        E4.setEnabled(true);
        D4.setEnabled(true);
        C4.setEnabled(true);
        B4.setEnabled(true);
        A4.setEnabled(true);
        A3.setEnabled(true);
        B3.setEnabled(true);
        C3.setEnabled(true);
        D3.setEnabled(true);
        E3.setEnabled(true);
        F3.setEnabled(true);
        G3.setEnabled(true);
        H3.setEnabled(true);
        H2.setEnabled(true);
        G2.setEnabled(true);
        F2.setEnabled(true);
        E2.setEnabled(true);
        D2.setEnabled(true);
        C2.setEnabled(true);
        B2.setEnabled(true);
        A2.setEnabled(true);
        A1.setEnabled(true);
        B1.setEnabled(true);
        C1.setEnabled(true);
        D1.setEnabled(true);
        E1.setEnabled(true);
        F1.setEnabled(true);
        G1.setEnabled(true);
        H1.setEnabled(true);
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        jButton66.setEnabled(true);
        jButton68.setEnabled(true);
        jButton71.setVisible(false);
        gra = true;
        zasada50 = 0;
        dolicz = false;
        lekkieB = 4;
        lekkieC = 4;
        ciezkieB = 3;
        ciezkieC = 3;
        pionB = 8;
        pionC = 8;
        if (SI_ON == true && tura_rywala == true) {
            char[][] backup1 = new char[8][8];
            for (int i = 0; i < 8; i++) {
                System.arraycopy(ust[i], 0, backup1[i], 0, 8);
            }
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    System.out.print("{" + backup1[i][j] + "}");
                }
                System.out.println();
            }
            SI_ma_ruch();
        }
        obrotowy.setVisible(!(SI_ON == true || siec == true));
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        if (siec) {
            try {
                msgwy = "poddaje sie. wygraleś";
                out.writeUTF(msgwy);
                jTextArea2.setText(jTextArea2.getText().trim() + "\n ja: " + msgwy + "\n");
                jTextField1.setText("");
            } catch (IOException ignored) {
            }
        }
        kapitulacja();
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        jButton68.setEnabled(false);
        jButton69.setEnabled(true);
        jButton70.setEnabled(true);
        if (siec) {
            jButton69.setEnabled(false);
            jButton70.setEnabled(false);
            try {
                msgwy = "remis?";
                out.writeUTF(msgwy);
                jTextArea2.setText(jTextArea2.getText().trim() + "\n ja: " + msgwy + "\n");
                jTextField1.setText("");
            } catch (IOException ignored) {
            }
        }
    }//GEN-LAST:event_jButton68ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        jButton68.setEnabled(true);
        jButton69.setEnabled(false);
        jButton70.setEnabled(false);
        if (siec) {
            try {
                msgwy = "Gramy dalej";
                out.writeUTF(msgwy);
                jTextArea2.setText(jTextArea2.getText().trim() + "\n ja: " + msgwy + "\n");
                jTextField1.setText("");
            } catch (IOException ignored) {
            }
        }
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
        remis();
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        Object[] opcjeK = {KB, KC, KL};
        URL whatismyip = null;
        String ip2 = "";
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException ex) {
            Logger.getLogger(SzachowaArena.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader in2 = null;
        try {
            in2 = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
        } catch (IOException ex) {
            Logger.getLogger(SzachowaArena.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        String lokalneIP = "";
        try {
            ip2 = in2.readLine();
            lokalneIP = InetAddress.getLocalHost().getHostAddress();
        } catch (IOException ex) {
            Logger.getLogger(SzachowaArena.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        jLabel9.setText("twoje ip globalne:" + ip2 + "  ip lokalne:" + lokalneIP);
        wybor = JOptionPane.showOptionDialog(null, "WYBIERZ KOLOR", "WYBÓR",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcjeK, null);
        Random los = new Random();
        switch (wybor) {
            case 0:
                oczekiwanie = false;
                break;
            case 1:
                oczekiwanie = true;
                odwrot = true;
                break;
            case 2:
                oczekiwanie = los.nextBoolean();
                break;
            default:
                oczekiwanie = los.nextBoolean();
                break;
        }
        if (odwrot == true) {
            JLabel pom1 = zegarbiale;
            zegarbiale = zegarczarne;
            zegarczarne = pom1;
        }
        Object[] opcje_czasu = {"60", "30", "15+10", "10", "5+5", "5", "3+2", "3", "2+1", "1", "inny system"};
        Object[] opcje_trybu = {"klasyczny bez limitu", "klasyczny na czas", "SzachMaty"};
        tryb = (byte) JOptionPane.showOptionDialog(rootPane, "wybierz opcje gry", "opcje gry", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_trybu, null);
        zegarbiale.setText("--:--");
        zegarczarne.setText("--:--");
        if (tryb == 1) {
            czasgry = JOptionPane.showOptionDialog(rootPane, "wybierz opcje czasowe", "opcje czasu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_czasu, null);
        }
        if (tryb == 2) {
            boolean prawidlowosc;
            do {
                try {
                    sek = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj na ruch w sekundach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                    prawidlowosc = sek > 0;
                    if (prawidlowosc == true) {
                        seksyg = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj dlugosc sygnału(mniejszy niż czas na ruch)", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                        prawidlowosc = sek > seksyg && seksyg > 0;
                        if (prawidlowosc == true) {
                            sekbaza = sek;
                            blokada = new ReentrantLock();
                            warunek = blokada.newCondition();
                            czasB = (new zegar(sek * 1000, zegarbiale, blokada, warunek, true, tryb, seksyg));
                            czasC = (new zegar(sek * 1000, zegarczarne, blokada, warunek, false, tryb, seksyg));
                            zegarbiale.setText(String.valueOf(sek));
                            zegarczarne.setText(String.valueOf(sek));
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku", "błąd parametru", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku", "błąd parametru", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku", "błąd parametru", JOptionPane.ERROR_MESSAGE);
                    prawidlowosc = false;
                }
            } while (prawidlowosc != true);
        }

        blokada = new ReentrantLock();
        warunek = blokada.newCondition();
        int bazamin = 0;
        if (tryb == 1) {
            switch (czasgry) {
                case 0:
                    czasB = (new zegar(60 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(60 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("60:00");
                    zegarczarne.setText("60:00");
                    break;
                case 1:
                    czasB = (new zegar(30 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(30 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("30:00");
                    zegarczarne.setText("30:00");

                    break;
                case 2:
                    czasB = (new zegar(15 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(15 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    bonuss = 10;
                    zegarbiale.setText("15:00");
                    zegarczarne.setText("15:00");
                    break;
                case 3:
                    czasB = (new zegar(10 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(10 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("10:00");
                    zegarczarne.setText("10:00");
                    break;
                case 4:
                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("5:00");
                    zegarczarne.setText("5:00");
                    bonuss = 5;
                    break;
                case 5:
                    czasB = (new zegar(5 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(5 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("5:00");
                    zegarczarne.setText("5:00");
                    break;
                case 6:
                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("3:00");
                    zegarczarne.setText("3:00");
                    bonuss = 2;
                    break;
                case 7:
                    czasB = (new zegar(3 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(3 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("3:00");
                    zegarczarne.setText("3:00");
                    break;
                case 8:
                    czasB = (new zegar(2 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(2 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("2:00");
                    zegarczarne.setText("2:00");
                    bonuss = 1;
                    break;
                case 9:
                    czasB = (new zegar(1 * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                    czasC = (new zegar(1 * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                    zegarbiale.setText("1:00");
                    zegarczarne.setText("1:00");
                    break;
                case 10:
                    boolean prawidlowosc;
                    do {
                        try {
                            bazamin = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj czas na grę w minutach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                            prawidlowosc = bazamin > 0;
                            if (prawidlowosc == true) {
                                bonuss = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "podaj czas dodawany do czasy  sekundach", "parametry gry", JOptionPane.INFORMATION_MESSAGE));
                                if (bonuss >= 0) {
                                    czasB = (new zegar(bazamin * 60 * 1000, zegarbiale, blokada, warunek, true, tryb));
                                    czasC = (new zegar(bazamin * 60 * 1000, zegarczarne, blokada, warunek, false, tryb));
                                    zegarbiale.setText(bazamin + ":00");
                                    zegarczarne.setText(bazamin + ":00");
                                } else {
                                    prawidlowosc = false;
                                    JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą większą od 0 \n bez wartosci po przecinku", "błąd parametrów", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku", "bląd parametru", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (HeadlessException | NumberFormatException e) {
                            JOptionPane.showMessageDialog(rootPane, "musisz podać liczbę całkowitą \n bez wartosci po przecinku", "bląd parametru", JOptionPane.ERROR_MESSAGE);
                            prawidlowosc = false;
                        }
                    } while (prawidlowosc != true);
                    break;
                default:
                    zegarbiale.setText("--:--");
                    zegarczarne.setText("--:--");
                    czasgry = 0;
                    break;
            }
        }
        jTextArea2.setVisible(true);
        jTextField1.setVisible(true);
        jButton81.setVisible(true);
        jButton82.setVisible(false);
        jButton65.setVisible(false);
        jButton67.setVisible(false);
        jButton71.setVisible(false);
        siec = true;
        organizator = true;
        try {
            String ipAddress = null;
            int port = 6070;
            JOptionPane.showMessageDialog(rootPane, "czekaj aż ktoś się z tobą polączy na porcie " + port + "(do 5 minut) \n"
                    + "jeśli twój partner nie zna twego IP, podaj mu i liczbę 6070 port połączenia\n"
                    + "w przypadku braku połączenia z innym graczem w ciągu minuty\n będziesz mógł znowu wysłać zapytanie o grę lub zagrać stacjonarnie", "informacja", JOptionPane.INFORMATION_MESSAGE);
            server = new ServerSocket(port);
            server.setSoTimeout(300000);

            socket = server.accept();
            socket.getRemoteSocketAddress();
            DataInputStream in3 = new DataInputStream(socket.getInputStream());
            DataOutputStream out3 = new DataOutputStream(socket.getOutputStream());
            if (!in3.readUTF().equals("TEST")) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                polacz(bazamin);
            } else {
                in3.close();
                out3.close();
                server.close();
                socket.close();
                server = new ServerSocket(port);
                server.setSoTimeout(300000);
                socket = server.accept();
                socket.getRemoteSocketAddress();
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                polacz(bazamin);
            }
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(rootPane, "nie udało się połączyć, upłynął czas oczekiwania", "informacja", JOptionPane.ERROR_MESSAGE);
            zegarbiale.setText("czas białych");
            zegarczarne.setText("czas czarnych");
            jTextArea2.setVisible(false);
            jTextField1.setVisible(false);
            jButton81.setVisible(false);
            jButton82.setVisible(true);
            jButton65.setVisible(true);
            jButton67.setVisible(true);
            jButton71.setVisible(true);
            siec = false;
            organizator = false;
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_jButton65ActionPerformed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
        for (int i = 0; i < 8; i++) {
            System.arraycopy(ust[i], 0, pozycja_ustawki[i], 0, 8);
        }
        if ((pionB > 0 || pionC > 0 || lekkieB > 1 || lekkieC > 1 || ciezkieB > 0 || ciezkieC > 0)) {
            if (krole_biale == 1 && krole_czarne == 1 && (czarneRuch.isSelected() || bialeRuch.isSelected())) {
                szachB = RuchZagrozenie_kontrola.szach(ust.clone(), true);
                szachC = RuchZagrozenie_kontrola.szach(ust.clone(), false);
                if (szachB && szachC) {
                    JOptionPane.showMessageDialog(rootPane, "NIE MOŻE BYĆ SZACH PO OBU STRONACH", "ZŁA POZYCJA",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if ((czarneRuch.isSelected() && szachB) || (bialeRuch.isSelected() && szachC)) {
                        JOptionPane.showMessageDialog(rootPane, "NIE MOŻNA WYKONYWAĆ RUCHU GDY DRUGI KRÓL JEST W SZACHU", "ZŁA POZYCJA",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        char[][] USTAWIENIE1;
                        USTAWIENIE1 = ust.clone();
                        kontrolamat = ust.clone();
                        for (byte i = 0; i < 8; i++) {
                            for (byte j = 0; j < 8; j++) {
                                if ((ruchB && kontrolamat[i][j] == 'K') || (!ruchB && kontrolamat[i][j] == 'k')) {
                                    poza_krolewska[0] = i;
                                    poza_krolewska[1] = j;
                                }
                            }
                        }
                        klopoty = Wspomagacz.znajdzklopot(kontrolamat.clone(), ruchB);
                        hodu = SzachMatPatKontrola.uciekaj(USTAWIENIE1.clone(), ruchB, poza_krolewska);
                        if (!hodu) {
                            char[][] backupzapas = USTAWIENIE1.clone();
                            hitme = SzachMatPatKontrola.znajdzodsiecz(backupzapas.clone(), ruchB, klopoty, kol, przelotcan);
                            if (!hitme) {
                                protectme = SzachMatPatKontrola.zastaw(USTAWIENIE1.clone(), ruchB, klopoty, poza_krolewska, przelotcan);
                            }
                        }
                        for (byte i = 0; i < 8; i++) {
                            for (byte j = 0; j < 8; j++) {
                                odwrotna[i][j] = ust[7 - i][7 - j];
                                if (ust[i][j] != ' ') {
                                    pole_baza[0] = j;
                                    pole_baza[1] = i;
                                    move = SzachMatPatKontrola.znajdz_ruch(USTAWIENIE1, ruchB, ust[i][j], pole_baza, przelotcan);
                                    if (move) {
                                        break;
                                    }
                                }
                            }
                            if (move) {
                                break;
                            }
                        }
                        if (!move && !hodu && !hitme && !protectme) {
                            JOptionPane.showMessageDialog(rootPane, "NIE MOŻNA USTAWIĆ POZYCJI KOŃCOWEJ", "ZŁA POZYCJA",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            gra = true;
                            if (ust[0][4] == 'K') {
                                if (ust[0][0] == 'R' || ust[0][7] == 'R') {
                                    kingrochB = true;
                                    wleft = ust[0][0] == 'R';
                                    wright = ust[0][7] == 'R';
                                } else {
                                    kingrochB = false;
                                    wleft = false;
                                    wright = false;
                                }
                            } else {
                                kingrochB = false;
                                wleft = false;
                                wright = false;
                            }
                            if (ust[7][4] == 'k') {
                                if (ust[7][0] == 'r' || ust[7][7] == 'r') {
                                    kingrochC = true;
                                    bleft = ust[7][0] == 'r';
                                    bright = ust[7][7] == 'r';
                                } else {
                                    kingrochC = false;
                                    bleft = false;
                                    bright = false;
                                }
                            } else {
                                kingrochC = false;
                                bleft = false;
                                bright = false;
                            }
                            jRadioButton11.setVisible(false);
                            jButton72.setVisible(false);
                            czarneRuch.setVisible(false);
                            bialeRuch.setVisible(false);
                            ustawWP.setVisible(false);
                            ustawBP.setVisible(false);
                            ustawWN.setVisible(false);
                            ustawBN.setVisible(false);
                            ustawWB.setVisible(false);
                            ustawBB.setVisible(false);
                            ustawWR.setVisible(false);
                            ustawBR.setVisible(false);
                            ustawWQ.setVisible(false);
                            ustawBQ.setVisible(false);
                            ustawWK.setVisible(false);
                            ustawBK.setVisible(false);
                            jButton67.setVisible(false);
                            jButton65.setVisible(false);
                            A8.setEnabled(true);
                            B8.setEnabled(true);
                            D8.setEnabled(true);
                            C8.setEnabled(true);
                            E8.setEnabled(true);
                            E7.setEnabled(true);
                            D7.setEnabled(true);
                            C7.setEnabled(true);
                            B7.setEnabled(true);
                            A7.setEnabled(true);
                            H8.setEnabled(true);
                            G8.setEnabled(true);
                            F8.setEnabled(true);
                            F7.setEnabled(true);
                            G7.setEnabled(true);
                            H7.setEnabled(true);
                            H6.setEnabled(true);
                            G6.setEnabled(true);
                            F6.setEnabled(true);
                            E6.setEnabled(true);
                            D6.setEnabled(true);
                            C6.setEnabled(true);
                            B6.setEnabled(true);
                            A6.setEnabled(true);
                            A5.setEnabled(true);
                            B5.setEnabled(true);
                            C5.setEnabled(true);
                            D5.setEnabled(true);
                            E5.setEnabled(true);
                            F5.setEnabled(true);
                            G5.setEnabled(true);
                            H5.setEnabled(true);
                            H4.setEnabled(true);
                            G4.setEnabled(true);
                            F4.setEnabled(true);
                            E4.setEnabled(true);
                            D4.setEnabled(true);
                            C4.setEnabled(true);
                            B4.setEnabled(true);
                            A4.setEnabled(true);
                            A3.setEnabled(true);
                            B3.setEnabled(true);
                            C3.setEnabled(true);
                            D3.setEnabled(true);
                            E3.setEnabled(true);
                            F3.setEnabled(true);
                            G3.setEnabled(true);
                            H3.setEnabled(true);
                            H2.setEnabled(true);
                            G2.setEnabled(true);
                            F2.setEnabled(true);
                            E2.setEnabled(true);
                            D2.setEnabled(true);
                            C2.setEnabled(true);
                            B2.setEnabled(true);
                            A2.setEnabled(true);
                            A1.setEnabled(true);
                            B1.setEnabled(true);
                            C1.setEnabled(true);
                            D1.setEnabled(true);
                            E1.setEnabled(true);
                            F1.setEnabled(true);
                            G1.setEnabled(true);
                            H1.setEnabled(true);
                            jButton66.setEnabled(true);
                            jButton68.setEnabled(true);
                            pierwsza_kolej = ruchB;
                            zegarbiale.setText("--:--");
                            zegarczarne.setText("--:--");
                            Object[] opcje_rywala = {"Graj z innym graczem", "Graj z SI jako białe", "Graj z SI jako czarne"};
                            sztuczny_rywal = (byte) JOptionPane.showOptionDialog(rootPane, "Gra z SI?", "opcje SI", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rywala, null);
                            JSlider suwak_trudnosci = new JSlider(1, 14, 1);
                            Hashtable Opisy = new Hashtable();
                            suwak_trudnosci.setOrientation(JSlider.VERTICAL);
                            suwak_trudnosci.setMinorTickSpacing(1);
                            Opisy.put(1, new JLabel("Nowicjusz"));
                            Opisy.put(2, new JLabel("Przeciętny"));
                            Opisy.put(4, new JLabel("Doświadczony"));
                            Opisy.put(6, new JLabel("Weteran"));
                            Opisy.put(8, new JLabel("Mistrz"));
                            Opisy.put(10, new JLabel("Kandydat na Arcymistrza"));
                            Opisy.put(12, new JLabel("Arcymistrz"));
                            Opisy.put(14, new JLabel("Wielki Arcymistrz"));
                            suwak_trudnosci.setLabelTable(Opisy);
                            suwak_trudnosci.setPaintTicks(true);
                            suwak_trudnosci.setPaintLabels(true);
                            switch (sztuczny_rywal) {
                                case 1:
                                    tura_rywala = false;
                                    obrotowy.setText("Obrót WYŁ");
                                    tryb = 0;
                                    SI_ON = true;
                                    odwrot = false;
                                    JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                                    glebiaSI = (byte) (byte) suwak_trudnosci.getValue();
                                    break;
                                case 2:
                                    tura_rywala = true;
                                    obrotowy.setText("Obrót WYŁ");
                                    tryb = 0;
                                    SI_ON = true;
                                    odwrot = true;
                                    JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                                    glebiaSI = (byte) (byte) suwak_trudnosci.getValue();
                                    break;
                                case 0:
                                    SI_ON = false;
                                    break;
                                default:
                                    SI_ON = false;
                                    break;
                            }
                            obrotowy.setVisible(!(SI_ON || siec));
                            System.out.println(glebiaSI);
                            if (SI_ON && ((tura_rywala && bialeRuch.isSelected()) || (!tura_rywala && czarneRuch.isSelected()))) {
                                char[][] backup1 = ust.clone();
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (odwrot) {
                                            odwrotna[i][j] = ust[7 - i][7 - j];
                                        }
                                        // backup1[i][j] = ust[i][j];
                                        System.out.print("[" + backup1[i][j] + "]");
                                    }
                                    System.out.println();
                                }
                                styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
                                SI_ma_ruch();
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "MUSI BYĆ PO OBU STRONACH 1 KRÓL. TRZEBA TEŻ USTALIĆ KTO ZACZYNA", "ZŁA POZYCJA",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "MATERIAŁ PO OBU STRONACH JEST NIE WYSTARCZAJĄCY DO MATA. REMIS", "ZŁA POZYCJA",
                    JOptionPane.WARNING_MESSAGE);
        }
        for (int i = 0; i < 8; i++) {
            System.arraycopy(pozycja_ustawki[i], 0, ust[i], 0, 8);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + ust[i][j] + "]");
            }
            System.out.println();
        }

        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_jButton72ActionPerformed

    private void ustawWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWPActionPerformed
        symbol = 'P';
        Cursor = ustawWP.getSelectedIcon();
    }//GEN-LAST:event_ustawWPActionPerformed

    private void ustawWNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWNActionPerformed
        symbol = 'N';
        Cursor = ustawWN.getSelectedIcon();
    }//GEN-LAST:event_ustawWNActionPerformed

    private void ustawWBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWBActionPerformed
        symbol = 'B';
        Cursor = ustawWB.getSelectedIcon();
    }//GEN-LAST:event_ustawWBActionPerformed

    private void ustawWRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWRActionPerformed
        symbol = 'R';
        Cursor = ustawWR.getSelectedIcon();
    }//GEN-LAST:event_ustawWRActionPerformed

    private void ustawWQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWQActionPerformed
        symbol = 'Q';
        Cursor = ustawWQ.getSelectedIcon();
    }//GEN-LAST:event_ustawWQActionPerformed

    private void ustawWKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawWKActionPerformed
        symbol = 'K';
        Cursor = ustawWK.getSelectedIcon();
    }//GEN-LAST:event_ustawWKActionPerformed

    private void ustawBPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBPActionPerformed
        symbol = 'p';
        Cursor = ustawBP.getSelectedIcon();
    }//GEN-LAST:event_ustawBPActionPerformed

    private void ustawBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBNActionPerformed
        symbol = 'n';
        Cursor = ustawBN.getSelectedIcon();
    }//GEN-LAST:event_ustawBNActionPerformed

    private void ustawBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBBActionPerformed
        symbol = 'b';
        Cursor = ustawBB.getSelectedIcon();
    }//GEN-LAST:event_ustawBBActionPerformed

    private void ustawBRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBRActionPerformed
        symbol = 'r';
        Cursor = ustawBR.getSelectedIcon();
    }//GEN-LAST:event_ustawBRActionPerformed

    private void ustawBQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBQActionPerformed
        symbol = 'q';
        Cursor = ustawBQ.getSelectedIcon();
    }//GEN-LAST:event_ustawBQActionPerformed

    private void ustawBKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ustawBKActionPerformed
        symbol = 'k';
        Cursor = ustawBK.getSelectedIcon();
    }//GEN-LAST:event_ustawBKActionPerformed

    private void bialeRuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bialeRuchActionPerformed
        ruchB = true;
    }//GEN-LAST:event_bialeRuchActionPerformed

    private void czarneRuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_czarneRuchActionPerformed
        ruchB = false;
    }//GEN-LAST:event_czarneRuchActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
        jButton67.setVisible(false);
        jButton65.setVisible(false);
        jButton82.setVisible(false);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ust[i][j] = ' ';
                odwrotna[i][j] = ' ';
            }
        }
        krole_biale = 0;
        krole_czarne = 0;
        ustawka = true;
        obrotowy.setVisible(!(SI_ON || siec));
        A8.setEnabled(true);
        B8.setEnabled(true);
        D8.setEnabled(true);
        C8.setEnabled(true);
        E8.setEnabled(true);
        E7.setEnabled(true);
        D7.setEnabled(true);
        C7.setEnabled(true);
        B7.setEnabled(true);
        A7.setEnabled(true);
        H8.setEnabled(true);
        G8.setEnabled(true);
        F8.setEnabled(true);
        F7.setEnabled(true);
        G7.setEnabled(true);
        H7.setEnabled(true);
        H6.setEnabled(true);
        G6.setEnabled(true);
        F6.setEnabled(true);
        E6.setEnabled(true);
        D6.setEnabled(true);
        C6.setEnabled(true);
        B6.setEnabled(true);
        A6.setEnabled(true);
        A5.setEnabled(true);
        B5.setEnabled(true);
        C5.setEnabled(true);
        D5.setEnabled(true);
        E5.setEnabled(true);
        F5.setEnabled(true);
        G5.setEnabled(true);
        H5.setEnabled(true);
        H4.setEnabled(true);
        G4.setEnabled(true);
        F4.setEnabled(true);
        E4.setEnabled(true);
        D4.setEnabled(true);
        C4.setEnabled(true);
        B4.setEnabled(true);
        A4.setEnabled(true);
        A3.setEnabled(true);
        B3.setEnabled(true);
        C3.setEnabled(true);
        D3.setEnabled(true);
        E3.setEnabled(true);
        F3.setEnabled(true);
        G3.setEnabled(true);
        H3.setEnabled(true);
        H2.setEnabled(true);
        G2.setEnabled(true);
        F2.setEnabled(true);
        E2.setEnabled(true);
        D2.setEnabled(true);
        C2.setEnabled(true);
        B2.setEnabled(true);
        A2.setEnabled(true);
        A1.setEnabled(true);
        B1.setEnabled(true);
        C1.setEnabled(true);
        D1.setEnabled(true);
        E1.setEnabled(true);
        F1.setEnabled(true);
        G1.setEnabled(true);
        H1.setEnabled(true);
        A8.setIcon(null);
        B8.setIcon(null);
        D8.setIcon(null);
        C8.setIcon(null);
        E8.setIcon(null);
        E7.setIcon(null);
        D7.setIcon(null);
        C7.setIcon(null);
        B7.setIcon(null);
        A7.setIcon(null);
        H8.setIcon(null);
        G8.setIcon(null);
        F8.setIcon(null);
        F7.setIcon(null);
        G7.setIcon(null);
        H7.setIcon(null);
        H6.setIcon(null);
        G6.setIcon(null);
        F6.setIcon(null);
        E6.setIcon(null);
        D6.setIcon(null);
        C6.setIcon(null);
        B6.setIcon(null);
        A6.setIcon(null);
        A5.setIcon(null);
        B5.setIcon(null);
        C5.setIcon(null);
        D5.setIcon(null);
        E5.setIcon(null);
        F5.setIcon(null);
        G5.setIcon(null);
        H5.setIcon(null);
        H4.setIcon(null);
        G4.setIcon(null);
        F4.setIcon(null);
        E4.setIcon(null);
        D4.setIcon(null);
        C4.setIcon(null);
        B4.setIcon(null);
        A4.setIcon(null);
        A3.setIcon(null);
        B3.setIcon(null);
        C3.setIcon(null);
        D3.setIcon(null);
        E3.setIcon(null);
        F3.setIcon(null);
        G3.setIcon(null);
        H3.setIcon(null);
        H2.setIcon(null);
        G2.setIcon(null);
        F2.setIcon(null);
        E2.setIcon(null);
        D2.setIcon(null);
        C2.setIcon(null);
        B2.setIcon(null);
        A2.setIcon(null);
        A1.setIcon(null);
        B1.setIcon(null);
        C1.setIcon(null);
        D1.setIcon(null);
        E1.setIcon(null);
        F1.setIcon(null);
        G1.setIcon(null);
        H1.setIcon(null);
        jButton67.setVisible(false);
        jButton71.setVisible(false);
        jButton72.setVisible(true);
        czarneRuch.setVisible(true);
        bialeRuch.setVisible(true);
        jRadioButton11.setVisible(true);
        ustawWP.setVisible(true);
        ustawBP.setVisible(true);
        ustawWN.setVisible(true);
        ustawBN.setVisible(true);
        ustawWB.setVisible(true);
        ustawBB.setVisible(true);
        ustawWR.setVisible(true);
        ustawBR.setVisible(true);
        ustawWQ.setVisible(true);
        ustawBQ.setVisible(true);
        ustawWK.setVisible(true);
        ustawBK.setVisible(true);
        zasada50 = 0;
        dolicz = false;
        lekkieB = 0;
        lekkieC = 0;
        ciezkieB = 0;
        ciezkieC = 0;
        pionB = 0;
        pionC = 0;
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jRadioButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton11ActionPerformed
        Cursor = null;
        symbol = ' ';
    }//GEN-LAST:event_jRadioButton11ActionPerformed

    private void jButton81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton81ActionPerformed
        try {
            msgwy = jTextField1.getText().trim();
            out.writeUTF(msgwy);
            jTextArea2.setText(jTextArea2.getText().trim() + "\n ja: " + msgwy + "\n");
            jTextField1.setText("");
        } catch (IOException ignored) {
        }
    }//GEN-LAST:event_jButton81ActionPerformed

    private void jButton82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton82ActionPerformed
        int portk = 6070;
        ArrayList<InetAddress> listaipt = new ArrayList();
        ArrayList<String> listaip2 = new ArrayList();
        jTextArea2.setVisible(true);
        jTextField1.setVisible(true);
        jButton81.setVisible(true);
        jButton82.setVisible(false);
        jButton67.setVisible(false);
        jButton65.setVisible(false);
        jButton67.setVisible(false);
        jButton71.setVisible(false);
        siec = true;
        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();

                listaipt.add(i);

            }
        }
        String temp = listaipt.get(2).toString().substring(1);
        listaipt.clear();
        System.out.println(temp);
        int[] adresik = new int[4];
        StringTokenizer tokeny = new StringTokenizer(temp, ".", false);
        adresik[0] = Integer.parseInt(tokeny.nextToken());
        adresik[1] = Integer.parseInt(tokeny.nextToken());
        adresik[2] = Integer.parseInt(tokeny.nextToken());
        try {

            scanip = new Thread() {  //creazione del thread responsabile per la scansione

                public void run() {

                    ExecutorService exec = Executors.newCachedThreadPool();  //ExecutorService, mi permette di gestire e interrompere i sotto-thread che vengono creati

                    int progres = 2;
                    for (int i = 2; i <= 254; i++) {
                        try {
                            Thread.sleep(230);
                        } catch (InterruptedException ex) {
                            ;
                        }
                        exec.execute(new Pingowanie((String.valueOf(adresik[0]) + "." + String.valueOf(adresik[1]) + "."
                                + String.valueOf(adresik[2]) + "."),
                                (String.valueOf(adresik[0]) + "." + String.valueOf(adresik[1]) + "."
                                + String.valueOf(adresik[2]) + ".") + i)
                        );
                        progres++;
                    }
                    exec.shutdown();
                    if (progres == 255) {
                        scanip.interrupt();
                    }

                }
            };
            scanip.start();
            while (!scanip.isInterrupted()) {
            }
            choices = new String[listaip.size() + 2];
            int numerek = 0;
            for (String ip : listaip) {
                choices[numerek] = ip;
                numerek = numerek + 1;
            }
            choices[numerek] = "127.0.0.1";
            choices[numerek + 1] = "Gość z obcej sieci";
            String serverName2 = (String) JOptionPane.showInputDialog(
                    this, // optional reference to frame/window or null
                    "Wybierz gracza...", // prompt displayed over list
                    "Wybór gracza", // title
                    JOptionPane.QUESTION_MESSAGE, // message style
                    null, // Use default icon for message style
                    choices, // array of choices
                    null);
            String serverName = null;
            for (int i = 0; i < choices.length - 1; i++) {
                if (serverName2.equals(choices[i])) {
                    serverName = choices[i];
                }
            }
            if (serverName == null) {
                String serverName3 = JOptionPane.showInputDialog(rootPane, "wybierz ip przeciwnika", "nawiąż połączenie", JOptionPane.INFORMATION_MESSAGE);
                socket = new Socket();
                socket.connect(new InetSocketAddress(serverName3, portk), 1000);
            } else {
                socket = new Socket();
                socket.connect(new InetSocketAddress(serverName, portk), 1000);
            }
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("");
            jTextArea2.setText(jTextArea2.getText().trim() + "polaczono z innym graczem" + "\n");
            zegarbiale.setText("--:--");
            zegarczarne.setText("--:--");
            polaczenie_net = new lacze();
            polaczenie_net.start();
            jButton67.setVisible(false);
            jButton65.setVisible(false);
            A8.setEnabled(true);
            B8.setEnabled(true);
            D8.setEnabled(true);
            C8.setEnabled(true);
            E8.setEnabled(true);
            E7.setEnabled(true);
            D7.setEnabled(true);
            C7.setEnabled(true);
            B7.setEnabled(true);
            A7.setEnabled(true);
            H8.setEnabled(true);
            G8.setEnabled(true);
            F8.setEnabled(true);
            F7.setEnabled(true);
            G7.setEnabled(true);
            H7.setEnabled(true);
            H6.setEnabled(true);
            G6.setEnabled(true);
            F6.setEnabled(true);
            E6.setEnabled(true);
            D6.setEnabled(true);
            C6.setEnabled(true);
            B6.setEnabled(true);
            A6.setEnabled(true);
            A5.setEnabled(true);
            B5.setEnabled(true);
            C5.setEnabled(true);
            D5.setEnabled(true);
            E5.setEnabled(true);
            F5.setEnabled(true);
            G5.setEnabled(true);
            H5.setEnabled(true);
            H4.setEnabled(true);
            G4.setEnabled(true);
            F4.setEnabled(true);
            E4.setEnabled(true);
            D4.setEnabled(true);
            C4.setEnabled(true);
            B4.setEnabled(true);
            A4.setEnabled(true);
            A3.setEnabled(true);
            B3.setEnabled(true);
            C3.setEnabled(true);
            D3.setEnabled(true);
            E3.setEnabled(true);
            F3.setEnabled(true);
            G3.setEnabled(true);
            H3.setEnabled(true);
            H2.setEnabled(true);
            G2.setEnabled(true);
            F2.setEnabled(true);
            E2.setEnabled(true);
            D2.setEnabled(true);
            C2.setEnabled(true);
            B2.setEnabled(true);
            A2.setEnabled(true);
            A1.setEnabled(true);
            B1.setEnabled(true);
            C1.setEnabled(true);
            D1.setEnabled(true);
            E1.setEnabled(true);
            F1.setEnabled(true);
            G1.setEnabled(true);
            H1.setEnabled(true);
            jButton71.setVisible(false);
            gra = true;
            zasada50 = 0;
            dolicz = false;
            lekkieB = 4;
            lekkieC = 4;
            ciezkieB = 3;
            ciezkieC = 3;
            pionB = 8;
            pionC = 8;
            ustawka = false;
            obrotowy.setVisible(false);
        } catch (IOException ex) {
            zegarbiale.setText("czas białych");
            zegarczarne.setText("czas czarnych");
            jTextArea2.setVisible(false);
            jTextField1.setVisible(false);
            jButton81.setVisible(false);
            jButton82.setVisible(true);
            jButton67.setVisible(true);
            jButton65.setVisible(true);
            jButton67.setVisible(true);
            jButton71.setVisible(true);
            siec = false;
            JOptionPane.showMessageDialog(rootPane, "nie udało się połączyć", "błąd połączenia", JOptionPane.ERROR_MESSAGE);
        }/* catch (NullPointerException e) {
            zegarbiale.setText("czas białych");
            zegarczarne.setText("czas czarnych");
            jTextArea2.setVisible(false);
            jTextField1.setVisible(false);
            jButton81.setVisible(false);
            jButton82.setVisible(true);
            jButton67.setVisible(true);
            jButton65.setVisible(true);
            jButton67.setVisible(true);
            jButton71.setVisible(true);
            siec = false;
            System.out.println(e);
        }*/
    }//GEN-LAST:event_jButton82ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void alfastylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alfastylActionPerformed
        kroj_zestaw = 1;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_alfastylActionPerformed

    private void klasykstylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_klasykstylActionPerformed
        kroj_zestaw = 2;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_klasykstylActionPerformed

    private void whiteandblackfiguryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteandblackfiguryActionPerformed
        kolor_zestaw = 2;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_whiteandblackfiguryActionPerformed

    private void blueandredfiguryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueandredfiguryActionPerformed
        kolor_zestaw = 1;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_blueandredfiguryActionPerformed

    private void whiteandblackboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteandblackboardActionPerformed
        kolor_plansza = 1;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_whiteandblackboardActionPerformed

    private void blueandredboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueandredboardActionPerformed
        kolor_plansza = 2;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_blueandredboardActionPerformed

    private void whiteandblackboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteandblackboard1ActionPerformed
        kolor_plansza = 1;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_whiteandblackboard1ActionPerformed

    private void blueandredboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueandredboard1ActionPerformed
        kolor_plansza = 2;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_blueandredboard1ActionPerformed

    private void whiteandblackfigury1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteandblackfigury1ActionPerformed
        kolor_zestaw = 2;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_whiteandblackfigury1ActionPerformed

    private void blueandredfigury1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueandredfigury1ActionPerformed
        kolor_zestaw = 1;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_blueandredfigury1ActionPerformed

    private void alfastyl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alfastyl1ActionPerformed
        kroj_zestaw = 1;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_alfastyl1ActionPerformed

    private void klasykstyl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_klasykstyl1ActionPerformed
        kroj_zestaw = 2;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_klasykstyl1ActionPerformed

    private void brownboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brownboardActionPerformed
        kolor_plansza = 3;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_brownboardActionPerformed

    private void brownboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brownboard1ActionPerformed
        kolor_plansza = 3;
        wlasnykolor = false;
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
    }//GEN-LAST:event_brownboard1ActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

    }//GEN-LAST:event_jLabel9MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        JOptionPane.showMessageDialog(rootPane, ("wynik pozycji:" + new KalkulatorPozycji().
                zliczacz(konwert(ust),tura_rywala,przelotcan,
                    bleft,bright,wleft,wright,kingrochB,kingrochC,dokonano_RB,dokonano_RC,0,kolumna)));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void obrotowyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obrotowyActionPerformed
        if (obrotowy.getText().equals("Obrót WŁ")) {
            obrotowy.setText("Obrót WYŁ");
            odwrot = false;
            styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
            try {
                ustawrame();
            } catch (NullPointerException ignored) {

            }
        } else {
            obrotowy.setText("Obrót WŁ");
        }
    }//GEN-LAST:event_obrotowyActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jButton2.getText().equals("SI_włącz")) {
            Object[] opcje_rywala = {"Anuluj", "Graj z SI jako białe", "Graj z SI jako czarne"};
            sztuczny_rywal = (byte) JOptionPane.showOptionDialog(rootPane, "Gra z SI?", "opcje SI", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcje_rywala, null);
            JSlider suwak_trudnosci = new JSlider(1, 14, 1);
            Hashtable Opisy = new Hashtable();
            suwak_trudnosci.setOrientation(JSlider.VERTICAL);
            suwak_trudnosci.setMinorTickSpacing(1);
            Opisy.put(1, new JLabel("Nowicjusz"));
            Opisy.put(2, new JLabel("Przeciętny"));
            Opisy.put(4, new JLabel("Doświadczony"));
            Opisy.put(6, new JLabel("Weteran"));
            Opisy.put(8, new JLabel("Mistrz"));
            Opisy.put(10, new JLabel("Kandydat na Arcymistrza"));
            Opisy.put(12, new JLabel("Arcymistrz"));
            Opisy.put(14, new JLabel("Wielki Arcymistrz"));
            suwak_trudnosci.setLabelTable(Opisy);
            suwak_trudnosci.setPaintTicks(true);
            suwak_trudnosci.setPaintLabels(true);
            czasgry = -1;
            switch (sztuczny_rywal) {
                case 1:
                    tura_rywala = false;
                    tryb = 0;
                    SI_ON = true;
                    odwrot = false;
                    obrotowy.setText("Obrót WYŁ");
                    JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                    glebiaSI = (byte) (byte) suwak_trudnosci.getValue();
                    break;
                case 2:
                    tura_rywala = true;
                    tryb = 0;
                    SI_ON = true;
                    odwrot = true;
                    obrotowy.setText("Obrót WYŁ");
                    JOptionPane.showMessageDialog(null, suwak_trudnosci, "wybierz stopień trudności", JOptionPane.INFORMATION_MESSAGE);
                    glebiaSI = (byte) (byte) suwak_trudnosci.getValue();
                    break;
                case 0:
                    obrotowy.setText("Obrót WŁ");
                    SI_ON = false;
                    break;
                default:
                    SI_ON = false;
                    obrotowy.setText("Obrót WŁ");
                    break;
            }
            if (SI_ON && tura_rywala == ruchB) {
                char[][] backup1 = new char[8][8];
                for (int i = 0; i < 8; i++) {
                    System.arraycopy(ust[i], 0, backup1[i], 0, 8);
                }
                for (byte i = 0; i < 8; i++) {
                    for (byte j = 0; j < 8; j++) {
                        System.out.print("{" + backup1[i][j] + "}");
                    }
                    System.out.println();
                }
                SI_ma_ruch();
            }
            obrotowy.setVisible(!(SI_ON || siec));
        } else {
            SI_ON = false;
            obrotowy.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void resetgameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetgameActionPerformed
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(SzachowaArena.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());
            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (URISyntaxException | IOException ex1) {
            Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex1);
        }
        System.exit(0);
    }//GEN-LAST:event_resetgameActionPerformed

    private void Wlasne_kolor_jasneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wlasne_kolor_jasneActionPerformed
        Color kolor3 = JColorChooser.showDialog(rootPane, "kolor jasnych pól", Color.WHITE);
        if (kolor3 != null) {
            wlasnykolor = true;
            koloruj_pola(true, kolor3);
        }
    }//GEN-LAST:event_Wlasne_kolor_jasneActionPerformed

    private void Wlasne_kolor_ciemneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wlasne_kolor_ciemneActionPerformed
        Color kolor3 = JColorChooser.showDialog(rootPane, "kolor ciemych pól", Color.black);
        if (kolor3 != null) {
            wlasnykolor = true;
            koloruj_pola(false, kolor3);
        }
    }//GEN-LAST:event_Wlasne_kolor_ciemneActionPerformed

    private void Wlasne_kolor_jasne1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wlasne_kolor_jasne1ActionPerformed
        Color kolor3 = JColorChooser.showDialog(rootPane, "kolor jasnych pól", Color.white);
        if (kolor3 != null) {
            wlasnykolor = true;
            koloruj_pola(true, kolor3);
        }
    }//GEN-LAST:event_Wlasne_kolor_jasne1ActionPerformed

    private void Wlasne_kolor_ciemne1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wlasne_kolor_ciemne1ActionPerformed
        Color kolor3 = JColorChooser.showDialog(rootPane, "kolor ciemnych pól", Color.black);
        if (kolor3 != null) {
            wlasnykolor = true;
            koloruj_pola(false, kolor3);
        }
    }//GEN-LAST:event_Wlasne_kolor_ciemne1ActionPerformed

    private void Ramowka1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ramowka1ActionPerformed
        rama = JColorChooser.showDialog(rootPane, "kolor obramowania", Color.GREEN);
        if (rama == null) {
            rama = Color.GREEN;
        }
        poprawrame();
    }//GEN-LAST:event_Ramowka1ActionPerformed

    private void RamowkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RamowkaActionPerformed
        rama = JColorChooser.showDialog(rootPane, "kolor obramowania", Color.GREEN);
        if (rama == null) {
            rama = Color.GREEN;
        }
        poprawrame();
    }//GEN-LAST:event_RamowkaActionPerformed

    private void Przycisk_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Przycisk_helpActionPerformed
        switch (opcje_pomoc) {
            case 0:
                opcje_pomoc++;
                Przycisk_help.setText("Pokaż podpowiedzi");
                break;
            case 1:
                opcje_pomoc++;
                Przycisk_help.setText("Pokaż zasady i podpowiedzi");
                break;
            case 2:
                opcje_pomoc++;
                Przycisk_help.setText("Nie pokazuj nic");
                break;
            case 3:
                opcje_pomoc = 0;
                Przycisk_help.setText("Pokaż zasady");
                break;
        }
    }//GEN-LAST:event_Przycisk_helpActionPerformed

    private void podpowiedzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_podpowiedzActionPerformed
        pomoc_ruch = JColorChooser.showDialog(rootPane, "kolor podpowiedzi", Color.BLUE);
        if (pomoc_ruch == null) {
            pomoc_ruch = Color.BLUE;
        }
    }//GEN-LAST:event_podpowiedzActionPerformed

    private void podpowiedz1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_podpowiedz1ActionPerformed
        pomoc_ruch = JColorChooser.showDialog(rootPane, "kolor podpowiedzi", Color.BLUE);
        if (pomoc_ruch == null) {
            pomoc_ruch = Color.BLUE;
        }
    }//GEN-LAST:event_podpowiedz1ActionPerformed

    private void partia_odlozonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partia_odlozonaActionPerformed
       JFileChooser wyborP = new JFileChooser();
        wyborP.setDialogType(JFileChooser.SAVE_DIALOG);
        wyborP.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
        if(wyborP.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file;
            
                
                
                   file = wyborP.getSelectedFile();
               if (!file.getName().endsWith(".txt")) {
    file = new File(file.toString() + ".txt"); 
               }
            
try ( PrintWriter fileWriter = new PrintWriter(file.getPath())) {
    fileWriter.flush();
    for(int i=0;i<8;i++){
        for(int j=0;j<8;j++){
        fileWriter.write(ust[i][j]);
        }fileWriter.write("\n");}
    fileWriter.print(ruchB+"|"+przelotcan+"|"+kingrochB+"|"+kingrochC+"|"+dokonano_RB+"|"+dokonano_RC
    +"|"+dolicz+"|");
    fileWriter.println();
    fileWriter.print(zasada50+"+"+kol+"+"+movenr);  
    fileWriter.println();
    historia.forEach((s) -> {
        fileWriter.println(s);
                }); 
    fileWriter.println("----");
    fileWriter.println(jTextArea3.getText());
fileWriter.close();

}catch(IOException e){
    
}
}
        
    }//GEN-LAST:event_partia_odlozonaActionPerformed

    private void partia_wznowionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partia_wznowionaActionPerformed
        FileDialog plikw = new FileDialog(this, "Wybierz plik", FileDialog.LOAD);
        plikw.setVisible(true);
        String katalog = plikw.getDirectory();
        String nazwa = plikw.getFile();
        if(plikw.getFile()!=null){
            try {
                jTextArea3.setText("");
                String workingDirectory = System.getProperty(katalog) + File.separator;
                System.out.println(katalog+nazwa);
                File plik = new File(katalog, nazwa);
                
                Scanner inP;
                inP = new Scanner(plik);
                for(int i=0;i<8;i++){
                    
                    String zdanie = inP.nextLine();
                    for(int j=0;j<8;j++){
                        ust[i][j]=zdanie.charAt(j);
                        switch(zdanie.charAt(j)){
                            case 'P': pionB++; break;
                            case 'p': pionC++; break;
                            case 'N':
                            case 'B': lekkieB++; break;
                            case 'n':
                            case 'b': lekkieC++; break;
                            case 'Q':
                            case 'R': ciezkieB++; break;
                            case 'r':
                            case 'q': ciezkieC++; break;
                            
                        }
                    }
                }
                String zdanie = inP.nextLine();
                StringTokenizer st = new StringTokenizer(zdanie,"|");
                ruchB=Boolean.valueOf(st.nextToken());
                przelotcan=Boolean.valueOf(st.nextToken());
                kingrochB=Boolean.valueOf(st.nextToken());
                kingrochC=Boolean.valueOf(st.nextToken());
                dokonano_RB=Boolean.valueOf(st.nextToken());
                dokonano_RC=Boolean.valueOf(st.nextToken());
                dolicz=Boolean.valueOf(st.nextToken());
                zdanie = inP.nextLine();
                st = new StringTokenizer(zdanie);
                zasada50=Byte.parseByte(st.nextToken("+"));
                kol=Byte.parseByte(st.nextToken("+"));
                movenr = Integer.parseInt(st.nextToken("+"));
                zdanie=inP.nextLine();
                while(!"----".equals(zdanie)){
                    zdanie=inP.nextLine();
                    historia.add(zdanie);
                }
                zdanie=inP.nextLine();
                while(inP.hasNextLine()||!zdanie.equals("")){
                    System.out.println(zdanie);
                    jTextArea3.append(zdanie+"\n");
                     if(inP.hasNextLine()){zdanie=inP.nextLine();}
                     else{
                         break;
                     }
                }
                JOptionPane.showMessageDialog(rootPane, "Ruch mają "+(ruchB ? "białe" : "czarne")+"." );
                jButton67.setVisible(false);
        jButton65.setVisible(false);
        jButton82.setVisible(false);
        ustawka = false;
        A8.setEnabled(true);
        B8.setEnabled(true);
        D8.setEnabled(true);
        C8.setEnabled(true);
        E8.setEnabled(true);
        E7.setEnabled(true);
        D7.setEnabled(true);
        C7.setEnabled(true);
        B7.setEnabled(true);
        A7.setEnabled(true);
        H8.setEnabled(true);
        G8.setEnabled(true);
        F8.setEnabled(true);
        F7.setEnabled(true);
        G7.setEnabled(true);
        H7.setEnabled(true);
        H6.setEnabled(true);
        G6.setEnabled(true);
        F6.setEnabled(true);
        E6.setEnabled(true);
        D6.setEnabled(true);
        C6.setEnabled(true);
        B6.setEnabled(true);
        A6.setEnabled(true);
        A5.setEnabled(true);
        B5.setEnabled(true);
        C5.setEnabled(true);
        D5.setEnabled(true);
        E5.setEnabled(true);
        F5.setEnabled(true);
        G5.setEnabled(true);
        H5.setEnabled(true);
        H4.setEnabled(true);
        G4.setEnabled(true);
        F4.setEnabled(true);
        E4.setEnabled(true);
        D4.setEnabled(true);
        C4.setEnabled(true);
        B4.setEnabled(true);
        A4.setEnabled(true);
        A3.setEnabled(true);
        B3.setEnabled(true);
        C3.setEnabled(true);
        D3.setEnabled(true);
        E3.setEnabled(true);
        F3.setEnabled(true);
        G3.setEnabled(true);
        H3.setEnabled(true);
        H2.setEnabled(true);
        G2.setEnabled(true);
        F2.setEnabled(true);
        E2.setEnabled(true);
        D2.setEnabled(true);
        C2.setEnabled(true);
        B2.setEnabled(true);
        A2.setEnabled(true);
        A1.setEnabled(true);
        B1.setEnabled(true);
        C1.setEnabled(true);
        D1.setEnabled(true);
        E1.setEnabled(true);
        F1.setEnabled(true);
        G1.setEnabled(true);
        H1.setEnabled(true);
        styl(kolor_zestaw, kroj_zestaw, kolor_plansza);
        jButton66.setEnabled(true);
        jButton68.setEnabled(true);
        jButton71.setVisible(false);
        
        gra = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SzachowaArena.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        
        }
    }//GEN-LAST:event_partia_wznowionaActionPerformed
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SzachowaArena.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SzachowaArena().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton A1;
    private javax.swing.JButton A2;
    private javax.swing.JButton A3;
    private javax.swing.JButton A4;
    private javax.swing.JButton A5;
    private javax.swing.JButton A6;
    private javax.swing.JButton A7;
    private javax.swing.JButton A8;
    private javax.swing.JButton B1;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JButton B5;
    private javax.swing.JButton B6;
    private javax.swing.JButton B7;
    private javax.swing.JButton B8;
    private javax.swing.JButton C1;
    private javax.swing.JButton C2;
    private javax.swing.JButton C3;
    private javax.swing.JButton C4;
    private javax.swing.JButton C5;
    private javax.swing.JButton C6;
    private javax.swing.JButton C7;
    private javax.swing.JButton C8;
    private javax.swing.JButton D1;
    private javax.swing.JButton D2;
    private javax.swing.JButton D3;
    private javax.swing.JButton D4;
    private javax.swing.JButton D5;
    private javax.swing.JButton D6;
    private javax.swing.JButton D7;
    private javax.swing.JButton D8;
    private javax.swing.JButton E1;
    private javax.swing.JButton E2;
    private javax.swing.JButton E3;
    private javax.swing.JButton E4;
    private javax.swing.JButton E5;
    private javax.swing.JButton E6;
    private javax.swing.JButton E7;
    private javax.swing.JButton E8;
    private javax.swing.JButton F1;
    private javax.swing.JButton F2;
    private javax.swing.JButton F3;
    private javax.swing.JButton F4;
    private javax.swing.JButton F5;
    private javax.swing.JButton F6;
    private javax.swing.JButton F7;
    private javax.swing.JButton F8;
    private javax.swing.JButton G1;
    private javax.swing.JButton G2;
    private javax.swing.JButton G3;
    private javax.swing.JButton G4;
    private javax.swing.JButton G5;
    private javax.swing.JButton G6;
    private javax.swing.JButton G7;
    private javax.swing.JButton G8;
    private javax.swing.JButton H1;
    private javax.swing.JButton H2;
    private javax.swing.JButton H3;
    private javax.swing.JButton H4;
    private javax.swing.JButton H5;
    private javax.swing.JButton H6;
    private javax.swing.JButton H7;
    private javax.swing.JButton H8;
    private javax.swing.JButton Przycisk_help;
    private javax.swing.JMenuItem Ramowka;
    private javax.swing.JMenuItem Ramowka1;
    private javax.swing.JMenuItem Wlasne_kolor_ciemne;
    private javax.swing.JMenuItem Wlasne_kolor_ciemne1;
    private javax.swing.JMenuItem Wlasne_kolor_jasne;
    private javax.swing.JMenuItem Wlasne_kolor_jasne1;
    private javax.swing.JRadioButtonMenuItem alfastyl;
    private javax.swing.JRadioButtonMenuItem alfastyl1;
    private javax.swing.JRadioButton bialeRuch;
    private javax.swing.JRadioButtonMenuItem blueandredboard;
    private javax.swing.JRadioButtonMenuItem blueandredboard1;
    private javax.swing.JRadioButtonMenuItem blueandredfigury;
    private javax.swing.JRadioButtonMenuItem blueandredfigury1;
    private javax.swing.JRadioButtonMenuItem brownboard;
    private javax.swing.JRadioButtonMenuItem brownboard1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JRadioButton czarneRuch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButtonMenuItem klasykstyl;
    private javax.swing.JRadioButtonMenuItem klasykstyl1;
    private javax.swing.JMenu kolor;
    private javax.swing.JMenu kolor1;
    private javax.swing.JMenu kroj;
    private javax.swing.JMenu kroj1;
    private javax.swing.JButton obrotowy;
    private javax.swing.JButton partia_odlozona;
    private javax.swing.JButton partia_wznowiona;
    private javax.swing.JMenu plansza;
    private javax.swing.JMenu plansza1;
    private javax.swing.JMenuItem podpowiedz;
    private javax.swing.JMenuItem podpowiedz1;
    private javax.swing.JButton resetgame;
    private javax.swing.JRadioButton ustawBB;
    private javax.swing.JRadioButton ustawBK;
    private javax.swing.JRadioButton ustawBN;
    private javax.swing.JRadioButton ustawBP;
    private javax.swing.JRadioButton ustawBQ;
    private javax.swing.JRadioButton ustawBR;
    private javax.swing.JRadioButton ustawWB;
    private javax.swing.JRadioButton ustawWK;
    private javax.swing.JRadioButton ustawWN;
    private javax.swing.JRadioButton ustawWP;
    private javax.swing.JRadioButton ustawWQ;
    private javax.swing.JRadioButton ustawWR;
    private javax.swing.JRadioButtonMenuItem whiteandblackboard;
    private javax.swing.JRadioButtonMenuItem whiteandblackboard1;
    private javax.swing.JRadioButtonMenuItem whiteandblackfigury;
    private javax.swing.JRadioButtonMenuItem whiteandblackfigury1;
    private javax.swing.JLabel zegarbiale;
    private javax.swing.JLabel zegarczarne;
    // End of variables declaration//GEN-END:variables
}
