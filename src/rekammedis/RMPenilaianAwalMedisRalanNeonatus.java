package rekammedis;

import freehand.DlgMarkingImageAssMedisNeonatus;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;

/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRalanNeonatus extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private StringBuilder htmlContent;
    private String finger = "";
    private String TANGGALMUNDUR = "yes",urlImage;

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMPenilaianAwalMedisRalanNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "No. Rawat", "No RM", "Nama Pasien", "Tanggal Lahir", "Jenis Kelamin", "Tanggal", "Kode Dokter", "Nama Dokter", "Anamnesis",
            "Hubungan", "Keluhan Utama", "Riwayat Penyakit Sekarang (RPS)", "Riwayat Penyakit Dahulu (RPD)",
            "Riwayat Penyakit Keluarga (RPK)", "Riwayat Penggunaan Obat (RPO)", "Alergi", "Cara Lahir",
            "Lahir dengan Tindakan", "Umur Kehamilan", "Berat Badan Lahir", "Tempat Lahir",
            "Keterangan Tempat Lahir", "Ditolong Oleh", "Keterangan Penolong", "APGAR Score",
            "Imunisasi", "Riwayat Minum", "TD (mmHg)", "Nadi (x/menit)", "RR (x/menit)", "Suhu (°C)",
            "Berat Badan (Kg)", "Tinggi Badan (cm)", "LILA", "Lingkar Kepala (LK)", "Nyeri", "Lama Nyeri",
            "Skala Nyeri", "Keadaan", "Kesadaran", "Kepala", "Keterangan Kepala", "Mata",
            "Keterangan Mata", "Hidung", "Keterangan Hidung", "Gigi", "Keterangan Gigi",
            "Tenggorokan", "Keterangan Tenggorokan", "Telinga", "Keterangan Telinga", "Leher",
            "Keterangan Leher", "Thoraks", "Keterangan Thoraks", "Jantung", "Keterangan Jantung",
            "Paru", "Keterangan Paru", "Abdomen", "Keterangan Abdomen", "Genital",
            "Keterangan Genital", "Ekstremitas", "Keterangan Ekstremitas", "Kulit",
            "Keterangan Kulit", "Refleks", "Moro", "Roting", "Suching", "Kelainan",
            "Diagnosa", "Terapi", "Penunjang", "Operatif", "Non-Operatif", "Monitoring",
            "Konsultasi", "Lama", "Tujuan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] preferredWidths = {
            105, 70, 150, 65, 55, 80, 150, 115, 80, 100, 300,
            150, 150, 150, 150, 120, 90, 50, 80, 60, 75, 67,
            40, 40, 40, 40, 80, 80, 80, 80, 80, 80, 80, 80,
            80, 80, 300, 200, 170, 150, 300, 150, 150, 150,
            150, 150, 150, 150, 150, 150, 150, 150, 150,
            150, 150, 150, 150, 150, 150, 150, 150, 150,
            150, 150, 150, 150, 150, 150, 150, 150, 150,
            150, 150, 150, 150, 150, 150, 150, 150, 150
        };

        for (int i = 0; i < preferredWidths.length; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(preferredWidths[i]);
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int) 30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int) 2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int) 2000).getKata(RPS));
        RPK.setDocument(new batasInput((int) 2000).getKata(RPK));
        RPD.setDocument(new batasInput((int) 1000).getKata(RPD));
        RPO.setDocument(new batasInput((int) 1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int) 50).getKata(Alergi));
        Lila.setDocument(new batasInput((byte) 10).getKata(Lila));
        TD.setDocument(new batasInput((byte) 8).getKata(TD));
        Nadi.setDocument(new batasInput((byte) 5).getKata(Nadi));
        RR.setDocument(new batasInput((byte) 5).getKata(RR));
        Suhu.setDocument(new batasInput((byte) 5).getKata(Suhu));
        KulitKet.setDocument(new batasInput((byte) 5).getKata(KulitKet));
        BB.setDocument(new batasInput((byte) 5).getKata(BB));
        TB.setDocument(new batasInput((byte) 5).getKata(TB));
        KulitKet.setDocument(new batasInput((int) 5000).getKata(KulitKet));
        Kongenital.setDocument(new batasInput((int) 3000).getKata(Kongenital));
        Penunjang.setDocument(new batasInput((int) 3000).getKata(Penunjang));
        Diagnosis.setDocument(new batasInput((int) 500).getKata(Diagnosis));
        Tatalaksana.setDocument(new batasInput((int) 5000).getKata(Tatalaksana));
        Monitoring.setDocument(new batasInput((int) 1000).getKata(Monitoring));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetak = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jLabel28 = new widget.Label();
        Lila = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel41 = new widget.Label();
        KulitKet = new widget.TextBox();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gigi = new widget.ComboBox();
        jLabel45 = new widget.Label();
        Tenggorakan = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Genital = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Kulit = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        scrollPane8 = new widget.ScrollPane();
        Kongenital = new widget.TextArea();
        jLabel79 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jLabel103 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Monitoring = new widget.TextArea();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel42 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel14 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel43 = new widget.Label();
        CaraLahir = new widget.ComboBox();
        CaraLahirTindakan = new widget.TextBox();
        jLabel47 = new widget.Label();
        UmurKehamilan = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel27 = new widget.Label();
        BBLahir = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel53 = new widget.Label();
        LahirDi = new widget.ComboBox();
        DitolongKet = new widget.TextBox();
        jLabel54 = new widget.Label();
        Ditolong = new widget.ComboBox();
        LahirDiKet = new widget.TextBox();
        jLabel55 = new widget.Label();
        Apgar = new widget.TextBox();
        jLabel56 = new widget.Label();
        Imunisasi = new widget.TextBox();
        jLabel57 = new widget.Label();
        RiwayatMinum = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabel58 = new widget.Label();
        Lk = new widget.TextBox();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        LamaNyeri = new widget.ComboBox();
        Skala = new widget.TextBox();
        Nyeri = new widget.ComboBox();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        Hidung = new widget.ComboBox();
        Telinga = new widget.ComboBox();
        jLabel63 = new widget.Label();
        Leher = new widget.ComboBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Jantung = new widget.ComboBox();
        jLabel66 = new widget.Label();
        Paru = new widget.ComboBox();
        KepalaKet = new widget.TextBox();
        MataKet = new widget.TextBox();
        HidungKet = new widget.TextBox();
        GigiKet = new widget.TextBox();
        TenggorakanKet = new widget.TextBox();
        TelingaKet = new widget.TextBox();
        LeherKet = new widget.TextBox();
        ThoraksKet = new widget.TextBox();
        JantungKet = new widget.TextBox();
        ParuKet = new widget.TextBox();
        AbdomenKet = new widget.TextBox();
        GenitalKet = new widget.TextBox();
        EkstremitasKet = new widget.TextBox();
        jLabel67 = new widget.Label();
        Refleks = new widget.TextBox();
        jLabel68 = new widget.Label();
        Moro = new widget.TextBox();
        jLabel69 = new widget.Label();
        Roting = new widget.TextBox();
        jLabel70 = new widget.Label();
        Suching = new widget.TextBox();
        jLabel106 = new widget.Label();
        jLabel71 = new widget.Label();
        Operatif = new widget.TextBox();
        NonOperatif = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel107 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Konsultasi = new widget.TextArea();
        jLabel73 = new widget.Label();
        Lama = new widget.TextBox();
        jLabel108 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Tujuan = new widget.TextArea();
        BtnMarking = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetak.setBackground(new java.awt.Color(255, 255, 254));
        MnCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetak.setForeground(new java.awt.Color(50, 50, 50));
        MnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetak.setText("Cetak");
        MnCetak.setName("MnCetak"); // NOI18N
        MnCetak.setPreferredSize(new java.awt.Dimension(220, 26));
        MnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetak);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Jalan Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1550));
        FormInput.setLayout(null);

        TNoRw.setEnabled(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(200, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(270, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(360, 40, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(550, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(620, 390, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(650, 390, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(700, 390, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(750, 390, 45, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(720, 390, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(270, 390, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(220, 390, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(180, 390, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(500, 390, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(550, 390, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 390, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(30, 390, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(600, 390, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(110, 390, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(100, 420, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(430, 390, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(380, 390, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(330, 390, 40, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 190, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 190, 260, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(129, 90, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(130, 140, 310, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(-20, 140, 150, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(590, 140, 260, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(440, 140, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(184, 190, 255, 42);

        jLabel28.setText("LILA");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 420, 30, 23);

        Lila.setFocusTraversalPolicyProvider(true);
        Lila.setName("Lila"); // NOI18N
        Lila.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LilaKeyPressed(evt);
            }
        });
        FormInput.add(Lila);
        Lila.setBounds(30, 420, 60, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. PEMERIKSAAN FISIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 370, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(594, 90, 260, 43);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 370, 880, 1);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(230, 460, 70, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(100, 460, 118, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(-70, 490, 150, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(310, 460, 130, 23);

        jLabel41.setText("Keadaan Umum :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(-30, 460, 127, 23);

        KulitKet.setFocusTraversalPolicyProvider(true);
        KulitKet.setName("KulitKet"); // NOI18N
        KulitKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKetKeyPressed(evt);
            }
        });
        FormInput.add(KulitKet);
        KulitKet.setBounds(750, 670, 270, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(90, 490, 128, 23);

        jLabel44.setText("Gigi & Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(510, 520, 90, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(600, 520, 128, 23);

        jLabel45.setText("Tenggorokan:");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(-70, 550, 150, 23);

        Tenggorakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Tenggorakan.setName("Tenggorakan"); // NOI18N
        Tenggorakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TenggorakanKeyPressed(evt);
            }
        });
        FormInput.add(Tenggorakan);
        Tenggorakan.setBounds(90, 550, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(520, 580, 80, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(600, 580, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(-40, 640, 95, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(90, 640, 128, 23);

        jLabel50.setText("Genital & Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(520, 640, 80, 23);

        Genital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genital.setName("Genital"); // NOI18N
        Genital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKeyPressed(evt);
            }
        });
        FormInput.add(Genital);
        Genital.setBounds(600, 640, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(-40, 670, 95, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(90, 670, 128, 23);

        jLabel52.setText("Kulit :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(480, 670, 120, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(600, 670, 128, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 743, 1070, 0);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("II. RIWAYAT KELAHIRAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 250, 180, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/gambarbayi.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(30, 780, 520, 140);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Kongenital.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kongenital.setColumns(20);
        Kongenital.setRows(5);
        Kongenital.setName("Kongenital"); // NOI18N
        Kongenital.setPreferredSize(new java.awt.Dimension(182, 92));
        Kongenital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KongenitalKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Kongenital);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(570, 830, 550, 83);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Keterangan/ Kelainan Kongenital:");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(570, 810, 180, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 900, 880, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("III. STATUS LOKALIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 750, 180, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.setPreferredSize(new java.awt.Dimension(102, 52));
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Penunjang);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(60, 1210, 810, 63);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 920, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("3). Monitoring");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(20, 1320, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diagnosis);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(50, 940, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 990, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IV. DIAGNOSIS KERJA");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 920, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(20);
        Tatalaksana.setRows(15);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Tatalaksana);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(50, 1010, 810, 153);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("V. TERAPI");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 990, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Monitoring.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Monitoring.setColumns(20);
        Monitoring.setRows(5);
        Monitoring.setName("Monitoring"); // NOI18N
        Monitoring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MonitoringKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Monitoring);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(20, 1340, 430, 63);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(10, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2024 13:01:48" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(70, 40, 130, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1170, 880, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("VI. RENCANA PENATALAKSANAAN MEDIS");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 1170, 270, 23);

        jLabel42.setText("Mata :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(530, 490, 70, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(600, 490, 128, 23);

        jLabel14.setText("Riwayat Penggunaan Obat :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 190, 180, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("I. RIWAYAT KESEHATAN");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(10, 70, 180, 23);

        jLabel43.setText("Cara Lahir:");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(10, 270, 70, 23);

        CaraLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Tindakan" }));
        CaraLahir.setName("CaraLahir"); // NOI18N
        CaraLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraLahirKeyPressed(evt);
            }
        });
        FormInput.add(CaraLahir);
        CaraLahir.setBounds(80, 270, 128, 23);

        CaraLahirTindakan.setName("CaraLahirTindakan"); // NOI18N
        CaraLahirTindakan.setPreferredSize(new java.awt.Dimension(207, 23));
        CaraLahirTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraLahirTindakanKeyPressed(evt);
            }
        });
        FormInput.add(CaraLahirTindakan);
        CaraLahirTindakan.setBounds(210, 270, 230, 23);

        jLabel47.setText("minggu");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(610, 270, 50, 23);

        UmurKehamilan.setFocusTraversalPolicyProvider(true);
        UmurKehamilan.setName("UmurKehamilan"); // NOI18N
        UmurKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(UmurKehamilan);
        UmurKehamilan.setBounds(550, 270, 60, 23);

        jLabel48.setText("Umur Kehamilan:");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(440, 270, 100, 20);

        jLabel27.setText("BB Lahir:");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(670, 270, 70, 23);

        BBLahir.setFocusTraversalPolicyProvider(true);
        BBLahir.setName("BBLahir"); // NOI18N
        BBLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBLahirKeyPressed(evt);
            }
        });
        FormInput.add(BBLahir);
        BBLahir.setBounds(750, 270, 45, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Kg");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(800, 270, 30, 23);

        jLabel53.setText("Lahir di:");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 300, 70, 23);

        LahirDi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Klinik Bidan", "Klinik Dokter", "RS", "Lainnya" }));
        LahirDi.setName("LahirDi"); // NOI18N
        LahirDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LahirDiKeyPressed(evt);
            }
        });
        FormInput.add(LahirDi);
        LahirDi.setBounds(80, 300, 128, 23);

        DitolongKet.setName("DitolongKet"); // NOI18N
        DitolongKet.setPreferredSize(new java.awt.Dimension(207, 23));
        DitolongKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DitolongKetKeyPressed(evt);
            }
        });
        FormInput.add(DitolongKet);
        DitolongKet.setBounds(660, 300, 190, 23);

        jLabel54.setText("Ditolong:");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(450, 300, 70, 23);

        Ditolong.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bidan", "Dokter", "Lainnya" }));
        Ditolong.setName("Ditolong"); // NOI18N
        Ditolong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DitolongKeyPressed(evt);
            }
        });
        FormInput.add(Ditolong);
        Ditolong.setBounds(520, 300, 128, 23);

        LahirDiKet.setName("LahirDiKet"); // NOI18N
        LahirDiKet.setPreferredSize(new java.awt.Dimension(207, 23));
        LahirDiKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LahirDiKetKeyPressed(evt);
            }
        });
        FormInput.add(LahirDiKet);
        LahirDiKet.setBounds(210, 300, 230, 23);

        jLabel55.setText("APGAR Score:");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(-80, 330, 150, 23);

        Apgar.setFocusTraversalPolicyProvider(true);
        Apgar.setName("Apgar"); // NOI18N
        Apgar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApgarKeyPressed(evt);
            }
        });
        FormInput.add(Apgar);
        Apgar.setBounds(70, 330, 80, 23);

        jLabel56.setText("Imunisasi:");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(150, 330, 70, 23);

        Imunisasi.setFocusTraversalPolicyProvider(true);
        Imunisasi.setName("Imunisasi"); // NOI18N
        Imunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImunisasiKeyPressed(evt);
            }
        });
        FormInput.add(Imunisasi);
        Imunisasi.setBounds(220, 330, 100, 23);

        jLabel57.setText("Riwayat minum:");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(330, 330, 90, 23);

        RiwayatMinum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ASI", "Formula", "MP ASI" }));
        RiwayatMinum.setName("RiwayatMinum"); // NOI18N
        RiwayatMinum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatMinumKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatMinum);
        RiwayatMinum.setBounds(430, 330, 128, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText(" cm");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(800, 390, 30, 23);

        jLabel58.setText("LK:");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(130, 420, 30, 23);

        Lk.setFocusTraversalPolicyProvider(true);
        Lk.setName("Lk"); // NOI18N
        Lk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LkKeyPressed(evt);
            }
        });
        FormInput.add(Lk);
        Lk.setBounds(160, 420, 45, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText(" cm");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(210, 420, 30, 23);

        jLabel60.setText("Skala:");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(450, 420, 60, 23);

        LamaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Akut", "Kronis" }));
        LamaNyeri.setName("LamaNyeri"); // NOI18N
        LamaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(LamaNyeri);
        LamaNyeri.setBounds(380, 420, 70, 23);

        Skala.setName("Skala"); // NOI18N
        Skala.setPreferredSize(new java.awt.Dimension(207, 23));
        Skala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaKeyPressed(evt);
            }
        });
        FormInput.add(Skala);
        Skala.setBounds(520, 420, 60, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(300, 420, 70, 23);

        jLabel61.setText("Nyeri:");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(230, 420, 60, 23);

        jLabel62.setText("Hidung:");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(-70, 520, 150, 23);

        Hidung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Hidung.setName("Hidung"); // NOI18N
        Hidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidungKeyPressed(evt);
            }
        });
        FormInput.add(Hidung);
        Hidung.setBounds(90, 520, 128, 23);

        Telinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Telinga.setName("Telinga"); // NOI18N
        Telinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKeyPressed(evt);
            }
        });
        FormInput.add(Telinga);
        Telinga.setBounds(600, 550, 128, 23);

        jLabel63.setText("Telinga:");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(530, 550, 70, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(90, 580, 128, 23);

        jLabel64.setText("Leher:");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(-80, 580, 127, 23);

        jLabel65.setText("Jantung:");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(-80, 610, 127, 23);

        Jantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Jantung.setName("Jantung"); // NOI18N
        Jantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JantungKeyPressed(evt);
            }
        });
        FormInput.add(Jantung);
        Jantung.setBounds(90, 610, 128, 23);

        jLabel66.setText("Paru:");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(517, 610, 80, 23);

        Paru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Paru.setName("Paru"); // NOI18N
        Paru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKeyPressed(evt);
            }
        });
        FormInput.add(Paru);
        Paru.setBounds(600, 610, 128, 23);

        KepalaKet.setFocusTraversalPolicyProvider(true);
        KepalaKet.setName("KepalaKet"); // NOI18N
        KepalaKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKetKeyPressed(evt);
            }
        });
        FormInput.add(KepalaKet);
        KepalaKet.setBounds(240, 490, 270, 23);

        MataKet.setFocusTraversalPolicyProvider(true);
        MataKet.setName("MataKet"); // NOI18N
        MataKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKetKeyPressed(evt);
            }
        });
        FormInput.add(MataKet);
        MataKet.setBounds(750, 490, 270, 23);

        HidungKet.setFocusTraversalPolicyProvider(true);
        HidungKet.setName("HidungKet"); // NOI18N
        HidungKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidungKetKeyPressed(evt);
            }
        });
        FormInput.add(HidungKet);
        HidungKet.setBounds(240, 520, 270, 23);

        GigiKet.setFocusTraversalPolicyProvider(true);
        GigiKet.setName("GigiKet"); // NOI18N
        GigiKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKetKeyPressed(evt);
            }
        });
        FormInput.add(GigiKet);
        GigiKet.setBounds(750, 520, 270, 23);

        TenggorakanKet.setFocusTraversalPolicyProvider(true);
        TenggorakanKet.setName("TenggorakanKet"); // NOI18N
        TenggorakanKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TenggorakanKetKeyPressed(evt);
            }
        });
        FormInput.add(TenggorakanKet);
        TenggorakanKet.setBounds(240, 550, 270, 23);

        TelingaKet.setFocusTraversalPolicyProvider(true);
        TelingaKet.setName("TelingaKet"); // NOI18N
        TelingaKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKetKeyPressed(evt);
            }
        });
        FormInput.add(TelingaKet);
        TelingaKet.setBounds(750, 550, 270, 23);

        LeherKet.setFocusTraversalPolicyProvider(true);
        LeherKet.setName("LeherKet"); // NOI18N
        LeherKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKetKeyPressed(evt);
            }
        });
        FormInput.add(LeherKet);
        LeherKet.setBounds(240, 580, 270, 23);

        ThoraksKet.setFocusTraversalPolicyProvider(true);
        ThoraksKet.setName("ThoraksKet"); // NOI18N
        ThoraksKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKetKeyPressed(evt);
            }
        });
        FormInput.add(ThoraksKet);
        ThoraksKet.setBounds(750, 580, 270, 23);

        JantungKet.setFocusTraversalPolicyProvider(true);
        JantungKet.setName("JantungKet"); // NOI18N
        JantungKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JantungKetKeyPressed(evt);
            }
        });
        FormInput.add(JantungKet);
        JantungKet.setBounds(240, 610, 270, 23);

        ParuKet.setFocusTraversalPolicyProvider(true);
        ParuKet.setName("ParuKet"); // NOI18N
        ParuKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKetKeyPressed(evt);
            }
        });
        FormInput.add(ParuKet);
        ParuKet.setBounds(750, 610, 270, 23);

        AbdomenKet.setFocusTraversalPolicyProvider(true);
        AbdomenKet.setName("AbdomenKet"); // NOI18N
        AbdomenKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKetKeyPressed(evt);
            }
        });
        FormInput.add(AbdomenKet);
        AbdomenKet.setBounds(240, 640, 270, 23);

        GenitalKet.setFocusTraversalPolicyProvider(true);
        GenitalKet.setName("GenitalKet"); // NOI18N
        GenitalKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKetKeyPressed(evt);
            }
        });
        FormInput.add(GenitalKet);
        GenitalKet.setBounds(750, 640, 270, 23);

        EkstremitasKet.setFocusTraversalPolicyProvider(true);
        EkstremitasKet.setName("EkstremitasKet"); // NOI18N
        EkstremitasKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKetKeyPressed(evt);
            }
        });
        FormInput.add(EkstremitasKet);
        EkstremitasKet.setBounds(240, 670, 270, 23);

        jLabel67.setText("Reflek:");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 710, 70, 23);

        Refleks.setFocusTraversalPolicyProvider(true);
        Refleks.setName("Refleks"); // NOI18N
        Refleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefleksKeyPressed(evt);
            }
        });
        FormInput.add(Refleks);
        Refleks.setBounds(70, 710, 100, 23);

        jLabel68.setText("Moro:");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(190, 710, 70, 23);

        Moro.setFocusTraversalPolicyProvider(true);
        Moro.setName("Moro"); // NOI18N
        Moro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MoroKeyPressed(evt);
            }
        });
        FormInput.add(Moro);
        Moro.setBounds(260, 710, 100, 23);

        jLabel69.setText("Roting:");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(360, 710, 70, 23);

        Roting.setFocusTraversalPolicyProvider(true);
        Roting.setName("Roting"); // NOI18N
        Roting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RotingKeyPressed(evt);
            }
        });
        FormInput.add(Roting);
        Roting.setBounds(430, 710, 100, 23);

        jLabel70.setText("Suching:");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(540, 710, 70, 23);

        Suching.setFocusTraversalPolicyProvider(true);
        Suching.setName("Suching"); // NOI18N
        Suching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuchingKeyPressed(evt);
            }
        });
        FormInput.add(Suching);
        Suching.setBounds(610, 710, 100, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("1). PEMERIKSAAN PENUNJANG");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(30, 1190, 190, 23);

        jLabel71.setText("Tindakan Operatif:");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(-10, 1280, 110, 23);

        Operatif.setFocusTraversalPolicyProvider(true);
        Operatif.setName("Operatif"); // NOI18N
        Operatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperatifKeyPressed(evt);
            }
        });
        FormInput.add(Operatif);
        Operatif.setBounds(110, 1280, 340, 23);

        NonOperatif.setFocusTraversalPolicyProvider(true);
        NonOperatif.setName("NonOperatif"); // NOI18N
        NonOperatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NonOperatifKeyPressed(evt);
            }
        });
        FormInput.add(NonOperatif);
        NonOperatif.setBounds(570, 1280, 300, 23);

        jLabel72.setText("Tindakan Non Operatif:");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(450, 1280, 120, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("4) Konsultasi");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(470, 1320, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Konsultasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Konsultasi.setColumns(20);
        Konsultasi.setRows(5);
        Konsultasi.setName("Konsultasi"); // NOI18N
        Konsultasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonsultasiKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Konsultasi);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(470, 1340, 430, 63);

        jLabel73.setText("Lama Perawatan:");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(-20, 1410, 110, 23);

        Lama.setFocusTraversalPolicyProvider(true);
        Lama.setName("Lama"); // NOI18N
        Lama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKeyPressed(evt);
            }
        });
        FormInput.add(Lama);
        Lama.setBounds(100, 1410, 340, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Tujuan AkhirPengobatan dan Perawatan:");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(20, 1440, 190, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Tujuan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tujuan.setColumns(20);
        Tujuan.setRows(5);
        Tujuan.setName("Tujuan"); // NOI18N
        Tujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Tujuan);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(20, 1460, 430, 63);

        BtnMarking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnMarking.setMnemonic('G');
        BtnMarking.setText("MARKING LOKALIS");
        BtnMarking.setToolTipText("Alt+G");
        BtnMarking.setName("BtnMarking"); // NOI18N
        BtnMarking.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnMarking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMarkingActionPerformed(evt);
            }
        });
        BtnMarking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMarkingKeyPressed(evt);
            }
        });
        FormInput.add(BtnMarking);
        BtnMarking.setBounds(560, 780, 160, 30);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan Utama");
        } else if (RPS.getText().trim().equals("")) {
            Valid.textKosong(RPS, "Riwayat Penyakit Sekarang");
        } else if (RPK.getText().trim().equals("")) {
            Valid.textKosong(RPK, "Riwayat Penyakit Keluarga");
        } else if (RPD.getText().trim().equals("")) {
            Valid.textKosong(RPD, "Riwayat Penyakit Dahulu");
        } else if (RPO.getText().trim().equals("")) {
            Valid.textKosong(RPO, "Riwayat Pengunaan obat");
        } else {
            if (akses.getkode().equals("Admin Utama")) {
                simpan();
            } else {
                if (TanggalRegistrasi.getText().equals("")) {
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                }
                if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19)) == true) {
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, KulitKet, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString())) {
                    if (Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), Sequel.ambiltanggalsekarang()) == true) {
                        hapus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan Utama");
        } else if (RPS.getText().trim().equals("")) {
            Valid.textKosong(RPS, "Riwayat Penyakit Sekarang");
        } else if (RPK.getText().trim().equals("")) {
            Valid.textKosong(RPK, "Riwayat Penyakit Keluarga");
        } else if (RPD.getText().trim().equals("")) {
            Valid.textKosong(RPD, "Riwayat Penyakit Dahulu");
        } else if (RPO.getText().trim().equals("")) {
            Valid.textKosong(RPO, "Riwayat Pengunaan obat");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString())) {
                        if (Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), Sequel.ambiltanggalsekarang()) == true) {
                            if (TanggalRegistrasi.getText().equals("")) {
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                            }
                            if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19)) == true) {
                                ganti();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            try {
                htmlContent = new StringBuilder();
                htmlContent.append(
                        "<tr class='isi'>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.Rawat</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>No.RM</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pasien</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'><b>Tgl.Lahir</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55px'><b>J.K.</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kode Dokter</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Dokter</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='115px'><b>Tanggal</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Anamnesis</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Hubungan</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Keluhan Utama</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='120px'><b>Riwayat Alergi</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Keadaan Umum</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>GCS</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kesadaran</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>TD(mmHg)</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'><b>Nadi(x/menit)</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'><b>RR(x/menit)</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Suhu</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>SpO2</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>BB(Kg)</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>TB(cm)</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kepala</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Mata</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Gigi & Mulut</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>THT</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Thoraks</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Abdomen</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Genital & Anus</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Ekstremitas</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kulit</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Laboratorium</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Radiologi</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Penunjang Lainnya</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Tatalaksana</b></td>"
                        + "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Konsul/Rujuk</b></td>"
                        + "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                            "<tr class='isi'>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 0).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 1).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 2).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 3).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 4).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 5).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 6).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 7).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 8).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 9).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 10).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 11).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 12).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 13).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 14).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 15).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 16).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 17).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 18).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 19).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 20).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 21).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 22).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 23).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 24).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 25).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 26).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 27).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 28).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 29).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 30).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 31).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 32).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 33).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 34).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 35).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 36).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 37).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 38).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 39).toString() + "</td>"
                            + "<td valign='top'>" + tbObat.getValueAt(i, 40).toString() + "</td>"
                            + "</tr>");
                }
                LoadHTML.setText(
                        "<html>"
                        + "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
                        + htmlContent.toString()
                        + "</table>"
                        + "</html>"
                );

                File g = new File("file2.css");
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                        + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                        + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                        + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                        + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                        + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataPenilaianAwalMedisRanap.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>", "<head>"
                        + "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"
                        + "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        + "<tr class='isi2'>"
                        + "<td valign='top' align='center'>"
                        + "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>"
                        + akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                        + akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>"
                        + "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN BAYI/ANAK<br><br></font>"
                        + "</td>"
                        + "</tr>"
                        + "</table>")
                );
                bw.close();
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if ((evt.getClickCount() == 2) && (tbObat.getSelectedColumn() == 0)) {
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt, TB, TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt, Lila, BB);
    }//GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt, TD, RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt, RR, KulitKet);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt, BB, Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt, Nadi, Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt, RPO, Keadaan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt, TglAsuhan, Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt, Hubungan, RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt, RPK, RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt, RPS, RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt, RPD, Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void LilaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LilaKeyPressed
        Valid.pindah(evt, Kesadaran, TB);
    }//GEN-LAST:event_LilaKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt, KeluhanUtama, RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt, Alergi, Kesadaran);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt, Keadaan, Lila);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void KulitKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKetKeyPressed
        Valid.pindah(evt, Suhu, Kepala);
    }//GEN-LAST:event_KulitKetKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt, KulitKet, Mata);
    }//GEN-LAST:event_KepalaKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        Valid.pindah(evt, Mata, Tenggorakan);
    }//GEN-LAST:event_GigiKeyPressed

    private void TenggorakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TenggorakanKeyPressed
        Valid.pindah(evt, Gigi, Thoraks);
    }//GEN-LAST:event_TenggorakanKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt, Tenggorakan, Thoraks);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt, Thoraks, Genital);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void GenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKeyPressed
        Valid.pindah(evt, Abdomen, Ekstremitas);
    }//GEN-LAST:event_GenitalKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt, Genital, Kulit);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        Valid.pindah(evt, Ekstremitas, KulitKet);
    }//GEN-LAST:event_KulitKeyPressed

    private void KongenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KongenitalKeyPressed
        Valid.pindah2(evt, KulitKet, Penunjang);
    }//GEN-LAST:event_KongenitalKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt, Kongenital, Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt, Penunjang, Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        Valid.pindah2(evt, Diagnosis, Monitoring);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void MonitoringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MonitoringKeyPressed
        Valid.pindah2(evt, Tatalaksana, BtnSimpan);
    }//GEN-LAST:event_MonitoringKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt, Monitoring, Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt, Anamnesis, KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            try {                           
             param.put("lokalis","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/assesmentmedisneonatus/imagemarking/assMdsNeonatus"+TNoRw.getText().replaceAll("/","")+".png");    
            } catch (Exception e) {
            }
            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + "\nID " + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString() : finger) + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()));
//if(pasien.jk='L','Laki-Laki','Perempuan') as jk
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanNeonatus.jasper", "report", "::[ Laporan Penilaian Awal Medis Ralan Neonatus ]::",
                    "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk, pasien.tgl_lahir, dokter.nm_dokter,"
                    + "penilaian_medis_ralan_neonatus.tanggal, penilaian_medis_ralan_neonatus.kd_dokter, penilaian_medis_ralan_neonatus.anamnesis,"
                    + "penilaian_medis_ralan_neonatus.hubungan, penilaian_medis_ralan_neonatus.keluhan_utama, penilaian_medis_ralan_neonatus.rps,"
                    + "penilaian_medis_ralan_neonatus.rpd, penilaian_medis_ralan_neonatus.rpk, penilaian_medis_ralan_neonatus.rpo,"
                    + "penilaian_medis_ralan_neonatus.alergi, penilaian_medis_ralan_neonatus.caralahir, penilaian_medis_ralan_neonatus.lahirtindakan,"
                    + "penilaian_medis_ralan_neonatus.umurkehamilan, penilaian_medis_ralan_neonatus.bblahir, penilaian_medis_ralan_neonatus.tempatlahir,"
                    + "penilaian_medis_ralan_neonatus.tempatlahir_ket, penilaian_medis_ralan_neonatus.ditolong, penilaian_medis_ralan_neonatus.ditolong_ket,"
                    + "penilaian_medis_ralan_neonatus.apgar, penilaian_medis_ralan_neonatus.imunisasi, penilaian_medis_ralan_neonatus.riwayatminum,"
                    + "penilaian_medis_ralan_neonatus.td, penilaian_medis_ralan_neonatus.nadi, penilaian_medis_ralan_neonatus.rr,"
                    + "penilaian_medis_ralan_neonatus.suhu, penilaian_medis_ralan_neonatus.bb, penilaian_medis_ralan_neonatus.tb,"
                    + "penilaian_medis_ralan_neonatus.lila, penilaian_medis_ralan_neonatus.lk, penilaian_medis_ralan_neonatus.nyeri,"
                    + "penilaian_medis_ralan_neonatus.lamanyeri, penilaian_medis_ralan_neonatus.skala, penilaian_medis_ralan_neonatus.keadaan,"
                    + "penilaian_medis_ralan_neonatus.kesadaran, penilaian_medis_ralan_neonatus.kepala, penilaian_medis_ralan_neonatus.kepala_ket,"
                    + "penilaian_medis_ralan_neonatus.mata, penilaian_medis_ralan_neonatus.mata_ket, penilaian_medis_ralan_neonatus.hidung,"
                    + "penilaian_medis_ralan_neonatus.hidung_ket, penilaian_medis_ralan_neonatus.gigi, penilaian_medis_ralan_neonatus.gigi_ket,"
                    + "penilaian_medis_ralan_neonatus.tenggorokan, penilaian_medis_ralan_neonatus.tenggorokan_ket, penilaian_medis_ralan_neonatus.telinga,"
                    + "penilaian_medis_ralan_neonatus.telinga_ket, penilaian_medis_ralan_neonatus.leher, penilaian_medis_ralan_neonatus.leher_ket,"
                    + "penilaian_medis_ralan_neonatus.thoraks, penilaian_medis_ralan_neonatus.thoraks_ket, penilaian_medis_ralan_neonatus.jantung,"
                    + "penilaian_medis_ralan_neonatus.jantung_ket, penilaian_medis_ralan_neonatus.paru, penilaian_medis_ralan_neonatus.paru_ket,"
                    + "penilaian_medis_ralan_neonatus.abdomen, penilaian_medis_ralan_neonatus.abdomen_ket, penilaian_medis_ralan_neonatus.genital,"
                    + "penilaian_medis_ralan_neonatus.genital_ket, penilaian_medis_ralan_neonatus.ekstremitas, penilaian_medis_ralan_neonatus.ekstremitas_ket,"
                    + "penilaian_medis_ralan_neonatus.kulit, penilaian_medis_ralan_neonatus.kulit_ket, penilaian_medis_ralan_neonatus.refleks,"
                    + "penilaian_medis_ralan_neonatus.moro, penilaian_medis_ralan_neonatus.roting, penilaian_medis_ralan_neonatus.suching,"
                    + "penilaian_medis_ralan_neonatus.kelainan, penilaian_medis_ralan_neonatus.diagnosa, penilaian_medis_ralan_neonatus.terapi,"
                    + "penilaian_medis_ralan_neonatus.penunjang, penilaian_medis_ralan_neonatus.operatif, penilaian_medis_ralan_neonatus.nonoperatif,"
                    + "penilaian_medis_ralan_neonatus.monitoring, penilaian_medis_ralan_neonatus.konsultasi, penilaian_medis_ralan_neonatus.lama,"
                    + "penilaian_medis_ralan_neonatus.tujuan FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter INNER JOIN penilaian_medis_ralan_neonatus ON dokter.kd_dokter = penilaian_medis_ralan_neonatus.kd_dokter "
                    + "AND reg_periksa.no_rawat = penilaian_medis_ralan_neonatus.no_rawat where penilaian_medis_ralan_neonatus.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnCetakActionPerformed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt, Kepala, Gigi);
    }//GEN-LAST:event_MataKeyPressed

    private void CaraLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraLahirKeyPressed

    private void CaraLahirTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraLahirTindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CaraLahirTindakanKeyPressed

    private void UmurKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKehamilanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmurKehamilanKeyPressed

    private void BBLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BBLahirKeyPressed

    private void LahirDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LahirDiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LahirDiKeyPressed

    private void DitolongKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DitolongKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DitolongKetKeyPressed

    private void DitolongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DitolongKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DitolongKeyPressed

    private void LahirDiKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LahirDiKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LahirDiKetKeyPressed

    private void ApgarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApgarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApgarKeyPressed

    private void ImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImunisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImunisasiKeyPressed

    private void RiwayatMinumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatMinumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatMinumKeyPressed

    private void LkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LkKeyPressed

    private void LamaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LamaNyeriKeyPressed

    private void SkalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriKeyPressed

    private void HidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidungKeyPressed

    private void TelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelingaKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeherKeyPressed

    private void JantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JantungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JantungKeyPressed

    private void ParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ParuKeyPressed

    private void KepalaKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaKetKeyPressed

    private void MataKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MataKetKeyPressed

    private void HidungKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidungKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidungKetKeyPressed

    private void GigiKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GigiKetKeyPressed

    private void TenggorakanKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TenggorakanKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenggorakanKetKeyPressed

    private void TelingaKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelingaKetKeyPressed

    private void LeherKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeherKetKeyPressed

    private void ThoraksKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThoraksKetKeyPressed

    private void JantungKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JantungKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JantungKetKeyPressed

    private void ParuKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ParuKetKeyPressed

    private void AbdomenKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbdomenKetKeyPressed

    private void GenitalKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenitalKetKeyPressed

    private void EkstremitasKetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKetKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkstremitasKetKeyPressed

    private void RefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefleksKeyPressed

    private void MoroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MoroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MoroKeyPressed

    private void RotingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RotingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RotingKeyPressed

    private void SuchingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuchingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuchingKeyPressed

    private void OperatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperatifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OperatifKeyPressed

    private void NonOperatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NonOperatifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NonOperatifKeyPressed

    private void KonsultasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonsultasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KonsultasiKeyPressed

    private void LamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LamaKeyPressed

    private void TujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TujuanKeyPressed

    private void BtnMarkingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMarkingActionPerformed
        DlgMarkingImageAssMedisNeonatus form=new DlgMarkingImageAssMedisNeonatus(null,false);
        form.setNoRw(TNoRw.getText());
        form.setVisible(true);
        form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                urlImage=Sequel.cariIsi("select url_image from asesmen_medis_neonatus_image_marking where no_rawat='"+TNoRw.getText()+"' ");
                imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage+"");
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }//GEN-LAST:event_BtnMarkingActionPerformed

    private void BtnMarkingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMarkingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnMarkingKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanNeonatus dialog = new RMPenilaianAwalMedisRalanNeonatus(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ComboBox Abdomen;
    private widget.TextBox AbdomenKet;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox Apgar;
    private widget.TextBox BB;
    private widget.TextBox BBLahir;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMarking;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CaraLahir;
    private widget.TextBox CaraLahirTindakan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private widget.ComboBox Ditolong;
    private widget.TextBox DitolongKet;
    private widget.ComboBox Ekstremitas;
    private widget.TextBox EkstremitasKet;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Genital;
    private widget.TextBox GenitalKet;
    private widget.ComboBox Gigi;
    private widget.TextBox GigiKet;
    private widget.ComboBox Hidung;
    private widget.TextBox HidungKet;
    private widget.TextBox Hubungan;
    private widget.TextBox Imunisasi;
    private widget.ComboBox Jantung;
    private widget.TextBox JantungKet;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.TextBox KepalaKet;
    private widget.ComboBox Kesadaran;
    private widget.TextArea Kongenital;
    private widget.TextArea Konsultasi;
    private widget.ComboBox Kulit;
    private widget.TextBox KulitKet;
    private widget.Label LCount;
    private widget.ComboBox LahirDi;
    private widget.TextBox LahirDiKet;
    private widget.TextBox Lama;
    private widget.ComboBox LamaNyeri;
    private widget.ComboBox Leher;
    private widget.TextBox LeherKet;
    private widget.TextBox Lila;
    private widget.TextBox Lk;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Mata;
    private widget.TextBox MataKet;
    private javax.swing.JMenuItem MnCetak;
    private widget.TextArea Monitoring;
    private widget.TextBox Moro;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NonOperatif;
    private widget.ComboBox Nyeri;
    private widget.TextBox Operatif;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Paru;
    private widget.TextBox ParuKet;
    private widget.TextArea Penunjang;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextBox Refleks;
    private widget.ComboBox RiwayatMinum;
    private widget.TextBox Roting;
    private widget.ScrollPane Scroll;
    private widget.TextBox Skala;
    private widget.TextBox Suching;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Tatalaksana;
    private widget.ComboBox Telinga;
    private widget.TextBox TelingaKet;
    private widget.ComboBox Tenggorakan;
    private widget.TextBox TenggorakanKet;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.TextBox ThoraksKet;
    private widget.TextArea Tujuan;
    private widget.TextBox UmurKehamilan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter,"
                        + "penilaian_medis_ralan_neonatus.tanggal, penilaian_medis_ralan_neonatus.kd_dokter, penilaian_medis_ralan_neonatus.anamnesis,"
                        + "penilaian_medis_ralan_neonatus.hubungan, penilaian_medis_ralan_neonatus.keluhan_utama, penilaian_medis_ralan_neonatus.rps,"
                        + "penilaian_medis_ralan_neonatus.rpd, penilaian_medis_ralan_neonatus.rpk, penilaian_medis_ralan_neonatus.rpo,"
                        + "penilaian_medis_ralan_neonatus.alergi, penilaian_medis_ralan_neonatus.caralahir, penilaian_medis_ralan_neonatus.lahirtindakan,"
                        + "penilaian_medis_ralan_neonatus.umurkehamilan, penilaian_medis_ralan_neonatus.bblahir, penilaian_medis_ralan_neonatus.tempatlahir,"
                        + "penilaian_medis_ralan_neonatus.tempatlahir_ket, penilaian_medis_ralan_neonatus.ditolong, penilaian_medis_ralan_neonatus.ditolong_ket,"
                        + "penilaian_medis_ralan_neonatus.apgar, penilaian_medis_ralan_neonatus.imunisasi, penilaian_medis_ralan_neonatus.riwayatminum,"
                        + "penilaian_medis_ralan_neonatus.td, penilaian_medis_ralan_neonatus.nadi, penilaian_medis_ralan_neonatus.rr,"
                        + "penilaian_medis_ralan_neonatus.suhu, penilaian_medis_ralan_neonatus.bb, penilaian_medis_ralan_neonatus.tb,"
                        + "penilaian_medis_ralan_neonatus.lila, penilaian_medis_ralan_neonatus.lk, penilaian_medis_ralan_neonatus.nyeri,"
                        + "penilaian_medis_ralan_neonatus.lamanyeri, penilaian_medis_ralan_neonatus.skala, penilaian_medis_ralan_neonatus.keadaan,"
                        + "penilaian_medis_ralan_neonatus.kesadaran, penilaian_medis_ralan_neonatus.kepala, penilaian_medis_ralan_neonatus.kepala_ket,"
                        + "penilaian_medis_ralan_neonatus.mata, penilaian_medis_ralan_neonatus.mata_ket, penilaian_medis_ralan_neonatus.hidung,"
                        + "penilaian_medis_ralan_neonatus.hidung_ket, penilaian_medis_ralan_neonatus.gigi, penilaian_medis_ralan_neonatus.gigi_ket,"
                        + "penilaian_medis_ralan_neonatus.tenggorokan, penilaian_medis_ralan_neonatus.tenggorokan_ket, penilaian_medis_ralan_neonatus.telinga,"
                        + "penilaian_medis_ralan_neonatus.telinga_ket, penilaian_medis_ralan_neonatus.leher, penilaian_medis_ralan_neonatus.leher_ket,"
                        + "penilaian_medis_ralan_neonatus.thoraks, penilaian_medis_ralan_neonatus.thoraks_ket, penilaian_medis_ralan_neonatus.jantung,"
                        + "penilaian_medis_ralan_neonatus.jantung_ket, penilaian_medis_ralan_neonatus.paru, penilaian_medis_ralan_neonatus.paru_ket,"
                        + "penilaian_medis_ralan_neonatus.abdomen, penilaian_medis_ralan_neonatus.abdomen_ket, penilaian_medis_ralan_neonatus.genital,"
                        + "penilaian_medis_ralan_neonatus.genital_ket, penilaian_medis_ralan_neonatus.ekstremitas, penilaian_medis_ralan_neonatus.ekstremitas_ket,"
                        + "penilaian_medis_ralan_neonatus.kulit, penilaian_medis_ralan_neonatus.kulit_ket, penilaian_medis_ralan_neonatus.refleks,"
                        + "penilaian_medis_ralan_neonatus.moro, penilaian_medis_ralan_neonatus.roting, penilaian_medis_ralan_neonatus.suching,"
                        + "penilaian_medis_ralan_neonatus.kelainan, penilaian_medis_ralan_neonatus.diagnosa, penilaian_medis_ralan_neonatus.terapi,"
                        + "penilaian_medis_ralan_neonatus.penunjang, penilaian_medis_ralan_neonatus.operatif, penilaian_medis_ralan_neonatus.nonoperatif,"
                        + "penilaian_medis_ralan_neonatus.monitoring, penilaian_medis_ralan_neonatus.konsultasi, penilaian_medis_ralan_neonatus.lama,"
                        + "penilaian_medis_ralan_neonatus.tujuan FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter INNER JOIN penilaian_medis_ralan_neonatus ON dokter.kd_dokter = penilaian_medis_ralan_neonatus.kd_dokter "
                        + "AND reg_periksa.no_rawat = penilaian_medis_ralan_neonatus.no_rawat where "
                        + "penilaian_medis_ralan_neonatus.tanggal between ? and ? order by penilaian_medis_ralan_neonatus.tanggal");
            } else {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter,"
                        + "penilaian_medis_ralan_neonatus.tanggal, penilaian_medis_ralan_neonatus.kd_dokter, penilaian_medis_ralan_neonatus.anamnesis,"
                        + "penilaian_medis_ralan_neonatus.hubungan, penilaian_medis_ralan_neonatus.keluhan_utama, penilaian_medis_ralan_neonatus.rps,"
                        + "penilaian_medis_ralan_neonatus.rpd, penilaian_medis_ralan_neonatus.rpk, penilaian_medis_ralan_neonatus.rpo,"
                        + "penilaian_medis_ralan_neonatus.alergi, penilaian_medis_ralan_neonatus.caralahir, penilaian_medis_ralan_neonatus.lahirtindakan,"
                        + "penilaian_medis_ralan_neonatus.umurkehamilan, penilaian_medis_ralan_neonatus.bblahir, penilaian_medis_ralan_neonatus.tempatlahir,"
                        + "penilaian_medis_ralan_neonatus.tempatlahir_ket, penilaian_medis_ralan_neonatus.ditolong, penilaian_medis_ralan_neonatus.ditolong_ket,"
                        + "penilaian_medis_ralan_neonatus.apgar, penilaian_medis_ralan_neonatus.imunisasi, penilaian_medis_ralan_neonatus.riwayatminum,"
                        + "penilaian_medis_ralan_neonatus.td, penilaian_medis_ralan_neonatus.nadi, penilaian_medis_ralan_neonatus.rr,"
                        + "penilaian_medis_ralan_neonatus.suhu, penilaian_medis_ralan_neonatus.bb, penilaian_medis_ralan_neonatus.tb,"
                        + "penilaian_medis_ralan_neonatus.lila, penilaian_medis_ralan_neonatus.lk, penilaian_medis_ralan_neonatus.nyeri,"
                        + "penilaian_medis_ralan_neonatus.lamanyeri, penilaian_medis_ralan_neonatus.skala, penilaian_medis_ralan_neonatus.keadaan,"
                        + "penilaian_medis_ralan_neonatus.kesadaran, penilaian_medis_ralan_neonatus.kepala, penilaian_medis_ralan_neonatus.kepala_ket,"
                        + "penilaian_medis_ralan_neonatus.mata, penilaian_medis_ralan_neonatus.mata_ket, penilaian_medis_ralan_neonatus.hidung,"
                        + "penilaian_medis_ralan_neonatus.hidung_ket, penilaian_medis_ralan_neonatus.gigi, penilaian_medis_ralan_neonatus.gigi_ket,"
                        + "penilaian_medis_ralan_neonatus.tenggorokan, penilaian_medis_ralan_neonatus.tenggorokan_ket, penilaian_medis_ralan_neonatus.telinga,"
                        + "penilaian_medis_ralan_neonatus.telinga_ket, penilaian_medis_ralan_neonatus.leher, penilaian_medis_ralan_neonatus.leher_ket,"
                        + "penilaian_medis_ralan_neonatus.thoraks, penilaian_medis_ralan_neonatus.thoraks_ket, penilaian_medis_ralan_neonatus.jantung,"
                        + "penilaian_medis_ralan_neonatus.jantung_ket, penilaian_medis_ralan_neonatus.paru, penilaian_medis_ralan_neonatus.paru_ket,"
                        + "penilaian_medis_ralan_neonatus.abdomen, penilaian_medis_ralan_neonatus.abdomen_ket, penilaian_medis_ralan_neonatus.genital,"
                        + "penilaian_medis_ralan_neonatus.genital_ket, penilaian_medis_ralan_neonatus.ekstremitas, penilaian_medis_ralan_neonatus.ekstremitas_ket,"
                        + "penilaian_medis_ralan_neonatus.kulit, penilaian_medis_ralan_neonatus.kulit_ket, penilaian_medis_ralan_neonatus.refleks,"
                        + "penilaian_medis_ralan_neonatus.moro, penilaian_medis_ralan_neonatus.roting, penilaian_medis_ralan_neonatus.suching,"
                        + "penilaian_medis_ralan_neonatus.kelainan, penilaian_medis_ralan_neonatus.diagnosa, penilaian_medis_ralan_neonatus.terapi,"
                        + "penilaian_medis_ralan_neonatus.penunjang, penilaian_medis_ralan_neonatus.operatif, penilaian_medis_ralan_neonatus.nonoperatif,"
                        + "penilaian_medis_ralan_neonatus.monitoring, penilaian_medis_ralan_neonatus.konsultasi, penilaian_medis_ralan_neonatus.lama,"
                        + "penilaian_medis_ralan_neonatus.tujuan FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter INNER JOIN penilaian_medis_ralan_neonatus ON dokter.kd_dokter = penilaian_medis_ralan_neonatus.kd_dokter "
                        + "AND reg_periksa.no_rawat = penilaian_medis_ralan_neonatus.no_rawat where "
                        + "penilaian_medis_ralan_neonatus.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                        + "penilaian_medis_ralan_neonatus.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_neonatus.tanggal");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("jk"),
                        rs.getString("tgl_lahir"),
                        rs.getString("nm_dokter"),
                        rs.getString("tanggal"),
                        rs.getString("anamnesis"),
                        rs.getString("hubungan"),
                        rs.getString("keluhan_utama"),
                        rs.getString("rps"),
                        rs.getString("rpd"),
                        rs.getString("rpk"),
                        rs.getString("rpo"),
                        rs.getString("alergi"),
                        rs.getString("caralahir"),
                        rs.getString("lahirtindakan"),
                        rs.getString("umurkehamilan"),
                        rs.getString("bblahir"),
                        rs.getString("tempatlahir"),
                        rs.getString("tempatlahir_ket"),
                        rs.getString("ditolong"),
                        rs.getString("ditolong_ket"),
                        rs.getString("apgar"),
                        rs.getString("imunisasi"),
                        rs.getString("riwayatminum"),
                        rs.getString("td"),
                        rs.getString("nadi"),
                        rs.getString("rr"),
                        rs.getString("suhu"),
                        rs.getString("bb"),
                        rs.getString("tb"),
                        rs.getString("lila"),
                        rs.getString("lk"),
                        rs.getString("nyeri"),
                        rs.getString("lamanyeri"),
                        rs.getString("skala"),
                        rs.getString("keadaan"),
                        rs.getString("kesadaran"),
                        rs.getString("kepala"),
                        rs.getString("kepala_ket"),
                        rs.getString("mata"),
                        rs.getString("mata_ket"),
                        rs.getString("hidung"),
                        rs.getString("hidung_ket"),
                        rs.getString("gigi"),
                        rs.getString("gigi_ket"),
                        rs.getString("tenggorokan"),
                        rs.getString("tenggorokan_ket"),
                        rs.getString("telinga"),
                        rs.getString("telinga_ket"),
                        rs.getString("leher"),
                        rs.getString("leher_ket"),
                        rs.getString("thoraks"),
                        rs.getString("thoraks_ket"),
                        rs.getString("jantung"),
                        rs.getString("jantung_ket"),
                        rs.getString("paru"),
                        rs.getString("paru_ket"),
                        rs.getString("abdomen"),
                        rs.getString("abdomen_ket"),
                        rs.getString("genital"),
                        rs.getString("genital_ket"),
                        rs.getString("ekstremitas"),
                        rs.getString("ekstremitas_ket"),
                        rs.getString("kulit"),
                        rs.getString("kulit_ket"),
                        rs.getString("refleks"),
                        rs.getString("moro"),
                        rs.getString("roting"),
                        rs.getString("suching"),
                        rs.getString("kelainan"),
                        rs.getString("diagnosa"),
                        rs.getString("terapi"),
                        rs.getString("penunjang"),
                        rs.getString("operatif"),
                        rs.getString("nonoperatif"),
                        rs.getString("monitoring"),
                        rs.getString("konsultasi"),
                        rs.getString("lama"),
                        rs.getString("tujuan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPK.setText("");
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        Keadaan.setSelectedIndex(0);
        Lila.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        BB.setText("");
        TB.setText("");
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        Tenggorakan.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Genital.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Kulit.setSelectedIndex(0);
        KulitKet.setText("");
        Kongenital.setText("");
        Penunjang.setText("");
        Penunjang.setText("");
        Diagnosis.setText("");
        Tatalaksana.setText("");
        Monitoring.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Lila.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            KulitKet.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            Tenggorakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            Genital.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            KulitKet.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
            Kongenital.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString());
            Tatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString());
            Monitoring.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 40).toString());
            Valid.SetTgl2(TglAsuhan, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"
                    + "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "
                    + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        if (akses.getjml2() >= 1) {
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if (NmDokter.getText().equals("")) {
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan Dokter...!!");
            }
        }

        if (TANGGALMUNDUR.equals("no")) {
            if (!akses.getkode().equals("Admin Utama")) {
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from penilaian_medis_ralan_neonatus where no_rawat=?", 1, new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if (Sequel.mengedittf("penilaian_medis_ralan_neonatus", "no_rawat=?", "no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,alergi=?,keadaan=?,gcs=?,kesadaran=?,td=?,nadi=?,rr=?,suhu=?,"
                + "spo=?,bb=?,tb=?,kepala=?,mata=?,gigi=?,tht=?,thoraks=?,abdomen=?,genital=?,ekstremitas=?,kulit=?,ket_fisik=?,ket_lokalis=?,penunjang=?,diagnosis=?,tata=?,konsul=?", 37, new String[]{
                    TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), KdDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
                    KeluhanUtama.getText(), RPS.getText(), RPK.getText(), RPD.getText(), RPO.getText(), Alergi.getText(), Keadaan.getSelectedItem().toString(), Lila.getText(), Kesadaran.getSelectedItem().toString(), TD.getText(),
                    Nadi.getText(), RR.getText(), Suhu.getText(), KulitKet.getText(), BB.getText(), TB.getText(), Kepala.getSelectedItem().toString(), Mata.getSelectedItem().toString(), Gigi.getSelectedItem().toString(), Tenggorakan.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(), Abdomen.getSelectedItem().toString(), Genital.getSelectedItem().toString(), Ekstremitas.getSelectedItem().toString(), Kulit.getSelectedItem().toString(), KulitKet.getText(),
                    Kongenital.getText(), Penunjang.getText(), Diagnosis.getText(), Tatalaksana.getText(), Monitoring.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                }) == true) {
            tbObat.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 0);
            tbObat.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 1);
            tbObat.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 2);
            tbObat.setValueAt(TglLahir.getText(), tbObat.getSelectedRow(), 3);
            tbObat.setValueAt(Jk.getText(), tbObat.getSelectedRow(), 4);
            tbObat.setValueAt(KdDokter.getText(), tbObat.getSelectedRow(), 5);
            tbObat.setValueAt(NmDokter.getText(), tbObat.getSelectedRow(), 6);
            tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), tbObat.getSelectedRow(), 7);
            tbObat.setValueAt(Anamnesis.getSelectedItem().toString(), tbObat.getSelectedRow(), 8);
            tbObat.setValueAt(Hubungan.getText(), tbObat.getSelectedRow(), 9);
            tbObat.setValueAt(KeluhanUtama.getText(), tbObat.getSelectedRow(), 10);
            tbObat.setValueAt(RPS.getText(), tbObat.getSelectedRow(), 11);
            tbObat.setValueAt(RPD.getText(), tbObat.getSelectedRow(), 12);
            tbObat.setValueAt(RPK.getText(), tbObat.getSelectedRow(), 13);
            tbObat.setValueAt(RPO.getText(), tbObat.getSelectedRow(), 14);
            tbObat.setValueAt(Alergi.getText(), tbObat.getSelectedRow(), 15);
            tbObat.setValueAt(Keadaan.getSelectedItem().toString(), tbObat.getSelectedRow(), 16);
            tbObat.setValueAt(Lila.getText(), tbObat.getSelectedRow(), 17);
            tbObat.setValueAt(Kesadaran.getSelectedItem().toString(), tbObat.getSelectedRow(), 18);
            tbObat.setValueAt(TD.getText(), tbObat.getSelectedRow(), 19);
            tbObat.setValueAt(Nadi.getText(), tbObat.getSelectedRow(), 20);
            tbObat.setValueAt(RR.getText(), tbObat.getSelectedRow(), 21);
            tbObat.setValueAt(Suhu.getText(), tbObat.getSelectedRow(), 22);
            tbObat.setValueAt(KulitKet.getText(), tbObat.getSelectedRow(), 23);
            tbObat.setValueAt(BB.getText(), tbObat.getSelectedRow(), 24);
            tbObat.setValueAt(TB.getText(), tbObat.getSelectedRow(), 25);
            tbObat.setValueAt(Kepala.getSelectedItem().toString(), tbObat.getSelectedRow(), 26);
            tbObat.setValueAt(Mata.getSelectedItem().toString(), tbObat.getSelectedRow(), 27);
            tbObat.setValueAt(Gigi.getSelectedItem().toString(), tbObat.getSelectedRow(), 28);
            tbObat.setValueAt(Tenggorakan.getSelectedItem().toString(), tbObat.getSelectedRow(), 29);
            tbObat.setValueAt(Thoraks.getSelectedItem().toString(), tbObat.getSelectedRow(), 30);
            tbObat.setValueAt(Abdomen.getSelectedItem().toString(), tbObat.getSelectedRow(), 31);
            tbObat.setValueAt(Genital.getSelectedItem().toString(), tbObat.getSelectedRow(), 32);
            tbObat.setValueAt(Ekstremitas.getSelectedItem().toString(), tbObat.getSelectedRow(), 33);
            tbObat.setValueAt(Kulit.getSelectedItem().toString(), tbObat.getSelectedRow(), 34);
            tbObat.setValueAt(KulitKet.getText(), tbObat.getSelectedRow(), 35);
            tbObat.setValueAt(Kongenital.getText(), tbObat.getSelectedRow(), 36);
            tbObat.setValueAt(Penunjang.getText(), tbObat.getSelectedRow(), 37);
            tbObat.setValueAt(Diagnosis.getText(), tbObat.getSelectedRow(), 38);
            tbObat.setValueAt(Tatalaksana.getText(), tbObat.getSelectedRow(), 39);
            tbObat.setValueAt(Monitoring.getText(), tbObat.getSelectedRow(), 40);
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if (Sequel.menyimpantf("penilaian_medis_ralan_neonatus", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 77, new String[]{
            TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), KdDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
            KeluhanUtama.getText(), RPS.getText(), RPD.getText(), RPK.getText(), RPO.getText(), Alergi.getText(), CaraLahir.getSelectedItem().toString(), CaraLahirTindakan.getText(), UmurKehamilan.getText(), BBLahir.getText(),
            LahirDi.getSelectedItem().toString(), LahirDiKet.getText(), Ditolong.getSelectedItem().toString(), DitolongKet.getText(), Apgar.getText(), Imunisasi.getText(), RiwayatMinum.getSelectedItem().toString(),
            TD.getText(), Nadi.getText(), RR.getText(), Suhu.getText(), BB.getText(), TB.getText(), Lila.getText(), Lk.getText(), Nyeri.getSelectedItem().toString(), LamaNyeri.getSelectedItem().toString(),
            Skala.getText(), Keadaan.getSelectedItem().toString(), Kesadaran.getSelectedItem().toString(), Kepala.getSelectedItem().toString(), KepalaKet.getText(), Mata.getSelectedItem().toString(), MataKet.getText(), Hidung.getSelectedItem().toString(), HidungKet.getText(),
            Gigi.getSelectedItem().toString(), GigiKet.getText(), Tenggorakan.getSelectedItem().toString(), TenggorakanKet.getText(), Telinga.getSelectedItem().toString(), TelingaKet.getText(),
            Leher.getSelectedItem().toString(), LeherKet.getText(), Thoraks.getSelectedItem().toString(), ThoraksKet.getText(), Jantung.getSelectedItem().toString(), JantungKet.getText(),
            Paru.getSelectedItem().toString(), ParuKet.getText(), Abdomen.getSelectedItem().toString(), AbdomenKet.getText(), Genital.getSelectedItem().toString(), GenitalKet.getText(),
            Ekstremitas.getSelectedItem().toString(), EkstremitasKet.getText(), Kulit.getSelectedItem().toString(), KulitKet.getText(), Refleks.getText(), Moro.getText(), Roting.getText(), Suching.getText(), Kongenital.getText(), Diagnosis.getText(), Tatalaksana.getText(), Penunjang.getText(), Operatif.getText(), NonOperatif.getText(), Monitoring.getText(), Konsultasi.getText(), Lama.getText(),
            Tujuan.getText()

        }) == true) {
            tabMode.addRow(new String[]{
                TNoRw.getText(), TNoRM.getText(), TPasien.getText(), TglLahir.getText(), Jk.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), KdDokter.getText(), NmDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
                KeluhanUtama.getText(), RPS.getText(), RPD.getText(), RPK.getText(), RPO.getText(), Alergi.getText(), CaraLahir.getSelectedItem().toString(), CaraLahirTindakan.getText(), UmurKehamilan.getText(), BBLahir.getText(),
                LahirDi.getSelectedItem().toString(), LahirDiKet.getText(), Ditolong.getSelectedItem().toString(), DitolongKet.getText(), Apgar.getText(), Imunisasi.getText(), RiwayatMinum.getSelectedItem().toString(),
                TD.getText(), Nadi.getText(), RR.getText(), Suhu.getText(), BB.getText(), TB.getText(), Lila.getText(), Lk.getText(), Nyeri.getSelectedItem().toString(), LamaNyeri.getSelectedItem().toString(),
                Skala.getText(), Keadaan.getSelectedItem().toString(), Kesadaran.getSelectedItem().toString(), Kepala.getSelectedItem().toString(), KepalaKet.getText(), Mata.getSelectedItem().toString(), MataKet.getText(), Hidung.getSelectedItem().toString(), HidungKet.getText(),
                Gigi.getSelectedItem().toString(), GigiKet.getText(), Tenggorakan.getSelectedItem().toString(), TenggorakanKet.getText(), Telinga.getSelectedItem().toString(), TelingaKet.getText(),
                Leher.getSelectedItem().toString(), LeherKet.getText(), Thoraks.getSelectedItem().toString(), ThoraksKet.getText(), Jantung.getSelectedItem().toString(), JantungKet.getText(),
                Paru.getSelectedItem().toString(), ParuKet.getText(), Abdomen.getSelectedItem().toString(), AbdomenKet.getText(), Genital.getSelectedItem().toString(), GenitalKet.getText(),
                Ekstremitas.getSelectedItem().toString(), EkstremitasKet.getText(), Kulit.getSelectedItem().toString(), KulitKet.getText(), Refleks.getText(), Moro.getText(), Roting.getText(), Suching.getText(), Kongenital.getText(), Diagnosis.getText(), Tatalaksana.getText(), Penunjang.getText(), Operatif.getText(), NonOperatif.getText(), Monitoring.getText(), Konsultasi.getText(), Lama.getText(),
                Tujuan.getText()
            });
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        }       
        
    }
    void imageAssesment(String url){
        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            PanelWall.setBackgroundImage(new javax.swing.ImageIcon(img));
        }
        catch(IOException ex) {

        }
    }
}
