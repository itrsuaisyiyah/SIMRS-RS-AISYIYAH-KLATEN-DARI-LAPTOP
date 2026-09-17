/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMTbakRalan extends javax.swing.JDialog {

    private final DefaultTableModel tabModePemeriksaanTbak;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps4, ps7;
    private ResultSet rs;
    private int i = 0;
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgCariPegawai pegawai = new DlgCariPegawai(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMTbakRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabModePemeriksaanTbak = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam",
            "Instruksi", "Background", "Assesment", "Recommendation", "NIP",
            "Dokter/Paramedis", "Profesi/Jabatan", "Status Verifikasi", "Kode Dokter", "Nama Dokter"}) {

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex == 0;  // hanya kolom pertama yang bisa diedit
            }

            Class[] types = new Class[]{
                Boolean.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPemeriksaanTbak.setModel(tabModePemeriksaanTbak);
        tbPemeriksaanTbak.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanTbak.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Optimasi pengaturan lebar kolom menggunakan switch-case
        for (int i = 0; i < 16; i++) {
            TableColumn column = tbPemeriksaanTbak.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(20);
                    break;
                case 1:
                    column.setPreferredWidth(105);
                    break;
                case 2:
                    column.setPreferredWidth(70);
                    break;
                case 3:
                    column.setPreferredWidth(180);
                    break;
                case 4:
                case 5:
                    column.setPreferredWidth(100);
                    break;
                case 6:
                    column.setPreferredWidth(190);
                    break;
                case 7:
                case 8:
                case 9:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    break;
                case 10:
                case 11:
                    column.setPreferredWidth(100);
                    break;
                case 12:
                case 13:
                    column.setPreferredWidth(120);
                    break;
                case 14:
                    column.setPreferredWidth(20);
                    break;
                case 15:
                    column.setPreferredWidth(120);
                    break;
            }
        }

        tbPemeriksaanTbak.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KdPeg4.setDocument(new batasInput((byte) 20).getKata(KdPeg4));
        TSituation1.setDocument(new batasInput((int) 1000).getKata(TSituation1));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanTbak();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanTbak();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanTbak();
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
                //    if(akses.getform().equals("DlgCariPerawatanRanap")){
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDok3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    TDokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
                KdDok3.requestFocus();
            }
            //    }

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

        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (pegawai.getTable().getSelectedRow() != -1) {
                    KdPeg4.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 0).toString());
                    TPegawai4.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 1).toString());
                    Jabatan2.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 3).toString());
                    KdPeg4.requestFocus();
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

        try {
            koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
        }

        ChkInput.setSelected(false);
        isForm();

        jam();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanADIME = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPemeriksaanTbak = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TCariPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        scrollPane11 = new widget.ScrollPane();
        TSituation1 = new widget.TextArea();
        jLabel94 = new widget.Label();
        jLabel98 = new widget.Label();
        KdPeg4 = new widget.TextBox();
        TPegawai4 = new widget.TextBox();
        BtnSeekPegawai2 = new widget.Button();
        Jabatan2 = new widget.TextBox();
        jLabel99 = new widget.Label();
        BtnVerifSbar1 = new widget.Button();
        jLabel87 = new widget.Label();
        jLabel100 = new widget.Label();
        BtnVerifSbar2 = new widget.Button();
        jLabel25 = new widget.Label();
        KdDok3 = new widget.TextBox();
        TDokter3 = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        scrollPane12 = new widget.ScrollPane();
        TBackground1 = new widget.TextArea();
        jLabel95 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        TAssesment1 = new widget.TextArea();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        TRecommendation1 = new widget.TextArea();
        TPegawai5 = new widget.TextBox();
        KdPeg5 = new widget.TextBox();
        jLabel85 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanADIME.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanADIME.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanADIME.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanADIME.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanADIME.setText("Formulir Catatan ADIME Gizi");
        MnCatatanADIME.setName("MnCatatanADIME"); // NOI18N
        MnCatatanADIME.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCatatanADIME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanADIMEActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanADIME);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data TBAK Ralan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbPemeriksaanTbak.setAutoCreateRowSorter(true);
        tbPemeriksaanTbak.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanTbak.setName("tbPemeriksaanTbak"); // NOI18N
        tbPemeriksaanTbak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanTbakMouseClicked(evt);
            }
        });
        tbPemeriksaanTbak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanTbakKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPemeriksaanTbak);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setToolTipText("");
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 306));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setEnabled(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TNoRwMouseClicked(evt);
            }
        });
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TCariPasien.setEditable(false);
        TCariPasien.setHighlighter(null);
        TCariPasien.setName("TCariPasien"); // NOI18N
        FormInput.add(TCariPasien);
        TCariPasien.setBounds(283, 10, 260, 23);

        jLabel18.setText("Tanggal :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(554, 10, 60, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(841, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        TSituation1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSituation1.setColumns(20);
        TSituation1.setRows(5);
        TSituation1.setName("TSituation1"); // NOI18N
        TSituation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSituation1KeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(TSituation1);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(30, 110, 420, 150);

        jLabel94.setText("Instruksi :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 80, 70, 23);

        jLabel98.setText("Dilakukan :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(10, 40, 70, 23);

        KdPeg4.setHighlighter(null);
        KdPeg4.setName("KdPeg4"); // NOI18N
        KdPeg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPeg4ActionPerformed(evt);
            }
        });
        KdPeg4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg4KeyPressed(evt);
            }
        });
        FormInput.add(KdPeg4);
        KdPeg4.setBounds(80, 40, 115, 23);

        TPegawai4.setEditable(false);
        TPegawai4.setHighlighter(null);
        TPegawai4.setName("TPegawai4"); // NOI18N
        TPegawai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPegawai4ActionPerformed(evt);
            }
        });
        FormInput.add(TPegawai4);
        TPegawai4.setBounds(200, 40, 212, 23);

        BtnSeekPegawai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai2.setMnemonic('4');
        BtnSeekPegawai2.setToolTipText("ALt+4");
        BtnSeekPegawai2.setName("BtnSeekPegawai2"); // NOI18N
        BtnSeekPegawai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekPegawai2);
        BtnSeekPegawai2.setBounds(410, 40, 28, 23);

        Jabatan2.setEditable(false);
        Jabatan2.setHighlighter(null);
        Jabatan2.setName("Jabatan2"); // NOI18N
        FormInput.add(Jabatan2);
        Jabatan2.setBounds(640, 40, 209, 23);

        jLabel99.setText("Profesi / Jabatan / Departemen :");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(450, 40, 190, 23);

        BtnVerifSbar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/folder.png"))); // NOI18N
        BtnVerifSbar1.setMnemonic('4');
        BtnVerifSbar1.setToolTipText("ALt+4");
        BtnVerifSbar1.setName("BtnVerifSbar1"); // NOI18N
        BtnVerifSbar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerifSbar1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnVerifSbar1);
        BtnVerifSbar1.setBounds(460, 110, 40, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Data Verifikasi TBAK");
        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel87.setName("jLabel87"); // NOI18N
        jLabel87.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel87MouseClicked(evt);
            }
        });
        FormInput.add(jLabel87);
        jLabel87.setBounds(500, 150, 200, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("Status Verifikasi TBAK");
        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel100.setName("jLabel100"); // NOI18N
        jLabel100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel100MouseClicked(evt);
            }
        });
        FormInput.add(jLabel100);
        jLabel100.setBounds(500, 110, 200, 23);

        BtnVerifSbar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/peminjaman.png"))); // NOI18N
        BtnVerifSbar2.setMnemonic('4');
        BtnVerifSbar2.setToolTipText("ALt+4");
        BtnVerifSbar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnVerifSbar2.setName("BtnVerifSbar2"); // NOI18N
        BtnVerifSbar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerifSbar2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnVerifSbar2);
        BtnVerifSbar2.setBounds(460, 150, 36, 23);

        jLabel25.setText("DPJP:");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(450, 230, 50, 23);

        KdDok3.setHighlighter(null);
        KdDok3.setName("KdDok3"); // NOI18N
        KdDok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok3KeyPressed(evt);
            }
        });
        FormInput.add(KdDok3);
        KdDok3.setBounds(500, 230, 100, 23);

        TDokter3.setEditable(false);
        TDokter3.setHighlighter(null);
        TDokter3.setName("TDokter3"); // NOI18N
        FormInput.add(TDokter3);
        TDokter3.setBounds(600, 230, 250, 23);

        BtnSeekDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter3.setMnemonic('4');
        BtnSeekDokter3.setToolTipText("ALt+4");
        BtnSeekDokter3.setName("BtnSeekDokter3"); // NOI18N
        BtnSeekDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekDokter3);
        BtnSeekDokter3.setBounds(860, 230, 28, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        TBackground1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TBackground1.setColumns(20);
        TBackground1.setRows(5);
        TBackground1.setName("TBackground1"); // NOI18N
        TBackground1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBackground1KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(TBackground1);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(100, 540, 360, 70);

        jLabel95.setText("Background :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(30, 540, 70, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        TAssesment1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAssesment1.setColumns(20);
        TAssesment1.setRows(5);
        TAssesment1.setName("TAssesment1"); // NOI18N
        TAssesment1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAssesment1KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(TAssesment1);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(580, 460, 360, 70);

        jLabel96.setText("Asesmen :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(480, 460, 90, 23);

        jLabel97.setText("Recommendation :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(480, 540, 90, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        TRecommendation1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRecommendation1.setColumns(20);
        TRecommendation1.setRows(5);
        TRecommendation1.setName("TRecommendation1"); // NOI18N
        TRecommendation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRecommendation1KeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(TRecommendation1);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(580, 540, 360, 70);

        TPegawai5.setEditable(false);
        TPegawai5.setHighlighter(null);
        TPegawai5.setName("TPegawai5"); // NOI18N
        FormInput.add(TPegawai5);
        TPegawai5.setBounds(350, 620, 212, 23);

        KdPeg5.setHighlighter(null);
        KdPeg5.setName("KdPeg5"); // NOI18N
        KdPeg5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg5KeyPressed(evt);
            }
        });
        FormInput.add(KdPeg5);
        KdPeg5.setBounds(230, 620, 115, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Dokter DPJP Utama");
        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(100, 620, 140, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TCariPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KdPeg4.getText().trim().equals("") || TPegawai4.getText().trim().equals("")) {
            Valid.textKosong(KdPeg4, "Pegawai");
        } else if (TSituation1.getText().trim().equals("")) {
            Valid.textKosong(TSituation1, "Instruksi");
        } else if (KdDok3.getText().trim().equals("")) {
            Valid.textKosong(KdDok3, "DPJP");
        } else {
            if (akses.getkode().equals("Admin Utama")) {
                simpan();
            } else {
                if (TanggalRegistrasi.getText().equals("")) {
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                }
                if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + "") + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem()) == true) {
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        /*    if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Instruksi,BtnBatal);
        } */
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbPemeriksaanTbak.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdPeg4.getText().equals(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 10).toString())) {
                    if (Sequel.cekTanggal48jam(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString(), Sequel.ambiltanggalsekarang()) == true) {
                        hapus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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

        if ((!TSituation1.getText().trim().equals("")) || (!TBackground1.getText().trim().equals("")) || (!TAssesment1.getText().trim().equals(""))
                || (!TRecommendation1.getText().trim().equals(""))) {
            if (tbPemeriksaanTbak.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.mengedit("pemeriksaan_ralan_tbak", "no_rawat='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5) + "'",
                            "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation1.getText() + "',background='" + TBackground1.getText() + "',"
                            + "assesment='" + TAssesment1.getText() + "',recommendation='" + TRecommendation1.getText() + "',"
                            + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                            + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                            + "nip='" + KdPeg4.getText() + "',kddokter='" + KdDok3.getText() + "'");
                    tampilPemeriksaanTbak();
                    BtnBatalActionPerformed(evt);
                } else {
                    if (akses.getkode().equals(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 10).toString())) {
                        Sequel.mengedit("pemeriksaan_ralan_tbak", "no_rawat='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1)
                                + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4)
                                + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5) + "'",
                                "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation1.getText() + "',background='" + TBackground1.getText() + "',"
                                + "assesment='" + TAssesment1.getText() + "',recommendation='" + TRecommendation1.getText() + "',"
                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',kddokter='" + KdDok3.getText() + "'");

                        tampilPemeriksaanTbak();
                        BtnBatalActionPerformed(evt);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                TCari.requestFocus();
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
        petugas.dispose();
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
        /*    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModePemeriksaanTbak.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabModePemeriksaanTbak.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(TCari.getText().equals("")){
                Valid.MyReportqry("rptDataCatatanADIMEGizi.jasper","report","::[ Data Catatan ADIME Gizi Pasien ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,catatan_adime_gizi.tanggal,catatan_adime_gizi.asesmen,catatan_adime_gizi.diagnosis,"+
                    "catatan_adime_gizi.intervensi,catatan_adime_gizi.monitoring,catatan_adime_gizi.evaluasi,catatan_adime_gizi.instruksi,"+
                    "catatan_adime_gizi.nip,petugas.nama "+
                    "from catatan_adime_gizi inner join reg_periksa on catatan_adime_gizi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_adime_gizi.nip=petugas.nip where "+
                    "catatan_adime_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by catatan_adime_gizi.tanggal ",param);
            }else{
                Valid.MyReportqry("rptDataCatatanADIMEGizi.jasper","report","::[ Data Catatan ADIME Gizi Pasien ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,catatan_adime_gizi.tanggal,catatan_adime_gizi.asesmen,catatan_adime_gizi.diagnosis,"+
                    "catatan_adime_gizi.intervensi,catatan_adime_gizi.monitoring,catatan_adime_gizi.evaluasi,catatan_adime_gizi.instruksi,"+
                    "catatan_adime_gizi.nip,petugas.nama "+
                    "from catatan_adime_gizi inner join reg_periksa on catatan_adime_gizi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on catatan_adime_gizi.nip=petugas.nip where "+
                    "catatan_adime_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "catatan_adime_gizi.asesmen like '%"+TCari.getText().trim()+"%' or catatan_adime_gizi.diagnosis like '%"+TCari.getText().trim()+"%' or catatan_adime_gizi.intervensi like '%"+TCari.getText().trim()+"%' or "+
                    "catatan_adime_gizi.monitoring like '%"+TCari.getText().trim()+"%' or catatan_adime_gizi.evaluasi like '%"+TCari.getText().trim()+"%' or catatan_adime_gizi.instruksi like '%"+TCari.getText().trim()+"%' or "+
                    "catatan_adime_gizi.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') order by catatan_adime_gizi.tanggal ",param);
            }  
        }
        this.setCursor(Cursor.getDefaultCursor()); */
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
        tampilPemeriksaanTbak();
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
        tampilPemeriksaanTbak();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampilPemeriksaanTbak();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TCariPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void MnCatatanADIMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanADIMEActionPerformed
        if (tbPemeriksaanTbak.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("diagnosa", Sequel.cariIsi("select kamar_inap.diagnosa_awal from kamar_inap where kamar_inap.diagnosa_awal<>'' and kamar_inap.no_rawat=? ", TNoRw.getText()));
            Valid.MyReportqry("rptFormulirCatatanADIMEGizi.jasper", "report", "::[ Formulir Catatan ADIME Gizi Pasien ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                    + "pasien.jk,catatan_adime_gizi.tanggal,catatan_adime_gizi.asesmen,catatan_adime_gizi.diagnosis,"
                    + "catatan_adime_gizi.intervensi,catatan_adime_gizi.monitoring,catatan_adime_gizi.evaluasi,catatan_adime_gizi.instruksi,"
                    + "catatan_adime_gizi.nip,petugas.nama "
                    + "from catatan_adime_gizi inner join reg_periksa on catatan_adime_gizi.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join petugas on catatan_adime_gizi.nip=petugas.nip where reg_periksa.no_rawat='" + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnCatatanADIMEActionPerformed

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TNoRwMouseClicked
        /*    Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        } */
    }//GEN-LAST:event_TNoRwMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        /*    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,DTPTgl,KdDok);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,DTPTgl,kdptg);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,DTPTgl,KdDok2);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,DTPTgl,KdPeg);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,DTPTgl,TTinggi_uteri);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,DTPTgl,TInspeksi);
            }
        } */
    }//GEN-LAST:event_TNoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        //  Valid.pindah(evt,TKdPrw,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, DTPTgl, cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        //    Valid.pindah(evt,cmbMnt,TKeluhan);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void TSituation1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSituation1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSituation1KeyPressed

    private void KdPeg4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPeg4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPeg4ActionPerformed

    private void KdPeg4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPeg4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPeg4KeyPressed

    private void TPegawai4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPegawai4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPegawai4ActionPerformed

    private void BtnSeekPegawai2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPegawai2ActionPerformed
        //  akses.setform("DlgRawatInap");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeekPegawai2ActionPerformed

    private void BtnVerifSbar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVerifSbar1ActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            VerifikasiTBAKRalan soap = new VerifikasiTBAKRalan(null, false);
            soap.setNoRawat(TNoRw.getText(), TNoRw.getText());
            soap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            soap.setLocationRelativeTo(internalFrame1);
            soap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnVerifSbar1ActionPerformed

    private void jLabel87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel87MouseClicked
        if (TCariPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ValidasiTBAK form = new ValidasiTBAK(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jLabel87MouseClicked

    private void jLabel100MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseClicked
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            VerifikasiTBAK soap = new VerifikasiTBAK(null, false);
            soap.setNoRawat(TNoRw.getText(), TNoRw.getText());
            soap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            soap.setLocationRelativeTo(internalFrame1);
            soap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel100MouseClicked

    private void BtnVerifSbar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVerifSbar2ActionPerformed
        if (TCariPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ValidasiTBAKRalan form = new ValidasiTBAKRalan(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnVerifSbar2ActionPerformed

    private void KdDok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDok3KeyPressed

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter3ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter3ActionPerformed

    private void TBackground1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBackground1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBackground1KeyPressed

    private void TAssesment1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAssesment1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAssesment1KeyPressed

    private void TRecommendation1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRecommendation1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRecommendation1KeyPressed

    private void KdPeg5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPeg5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPeg5KeyPressed

    private void tbPemeriksaanTbakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanTbakMouseClicked
        if (tabModePemeriksaanTbak.getRowCount() != 0) {
            try {
                getDataPemeriksaanTbak();
            } catch (java.lang.NullPointerException e) {
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemeriksaanTbakMouseClicked

    private void tbPemeriksaanTbakKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanTbakKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemeriksaanTbakKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTbakRalan dialog = new RMTbakRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeekDokter3;
    private widget.Button BtnSeekPegawai2;
    private widget.Button BtnSimpan;
    private widget.Button BtnVerifSbar1;
    private widget.Button BtnVerifSbar2;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Jabatan2;
    private widget.TextBox KdDok3;
    private widget.TextBox KdPeg4;
    private widget.TextBox KdPeg5;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCatatanADIME;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextArea TAssesment1;
    private widget.TextArea TBackground1;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TDokter3;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPegawai4;
    private widget.TextBox TPegawai5;
    private widget.TextArea TRecommendation1;
    private widget.TextArea TSituation1;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Umur;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel25;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel85;
    private widget.Label jLabel87;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.Table tbPemeriksaanTbak;
    // End of variables declaration//GEN-END:variables

    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TCariPasien.setText("");
        KdPeg4.setText("");
        Jabatan2.setText("");
        TPegawai4.setText("");
        TSituation1.setText("");
        KdDok3.setText("");
        TDokter3.setText("");
    }

    private void getData() {
        if (tbPemeriksaanTbak.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 0).toString());
            TNoRM.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1).toString());
            TCariPasien.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 2).toString());
            Umur.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 3).toString());
            JK.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4).toString());
            //           Valid.SetTgl(Tanggal,tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),5).toString());  
//            Jam.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),5).toString().substring(11,13));
//            Menit.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),5).toString().substring(14,15));
//            Detik.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),5).toString().substring(17,19));
//            Asesmen.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),6).toString());
//            Diagnosis.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),7).toString());
//            Intervensi.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),8).toString());
//            Monitoring.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),9).toString());
//            Evaluasi.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),10).toString());
//            Instruksi.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),11).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TCariPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));
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
        ChkInput.setSelected(true);
        isForm();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            if (internalFrame1.getHeight() > 478) {
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH, 306));
                FormInput.setVisible(true);
                ChkInput.setVisible(true);
            } else {
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH, internalFrame1.getHeight() - 172));
                FormInput.setVisible(true);
                ChkInput.setVisible(true);
            }
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        /*    BtnSimpan.setEnabled(akses.getcatatan_adime_gizi());
        BtnHapus.setEnabled(akses.getcatatan_adime_gizi());
        BtnEdit.setEnabled(akses.getcatatan_adime_gizi());
        BtnPrint.setEnabled(akses.getcatatan_adime_gizi()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        } */
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        /*    if(Sequel.mengedittf("catatan_adime_gizi","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,asesmen=?,diagnosis=?,intervensi=?,monitoring=?,evaluasi=?,instruksi=?,nip=?",11,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Asesmen.getText(),Diagnosis.getText(),
            Intervensi.getText(),Monitoring.getText(),Evaluasi.getText(),Instruksi.getText(),KdPetugas.getText(),tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),5).toString(),
            tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(),0).toString()
        })==true){
            tbPemeriksaanTbak.setValueAt(TNoRw.getText(),tbPemeriksaanTbak.getSelectedRow(),0);
            tbPemeriksaanTbak.setValueAt(TNoRM.getText(),tbPemeriksaanTbak.getSelectedRow(),1);
            tbPemeriksaanTbak.setValueAt(TPasien.getText(),tbPemeriksaanTbak.getSelectedRow(),2);
            tbPemeriksaanTbak.setValueAt(Umur.getText(),tbPemeriksaanTbak.getSelectedRow(),3);
            tbPemeriksaanTbak.setValueAt(JK.getText(),tbPemeriksaanTbak.getSelectedRow(),4);
            tbPemeriksaanTbak.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbPemeriksaanTbak.getSelectedRow(),5);
            tbPemeriksaanTbak.setValueAt(Asesmen.getText(),tbPemeriksaanTbak.getSelectedRow(),6);
            tbPemeriksaanTbak.setValueAt(Diagnosis.getText(),tbPemeriksaanTbak.getSelectedRow(),7);
            tbPemeriksaanTbak.setValueAt(Intervensi.getText(),tbPemeriksaanTbak.getSelectedRow(),8);
            tbPemeriksaanTbak.setValueAt(Monitoring.getText(),tbPemeriksaanTbak.getSelectedRow(),9);
            tbPemeriksaanTbak.setValueAt(Evaluasi.getText(),tbPemeriksaanTbak.getSelectedRow(),10);
            tbPemeriksaanTbak.setValueAt(Instruksi.getText(),tbPemeriksaanTbak.getSelectedRow(),11);
            tbPemeriksaanTbak.setValueAt(KdPetugas.getText(),tbPemeriksaanTbak.getSelectedRow(),12);
            tbPemeriksaanTbak.setValueAt(NmPetugas.getText(),tbPemeriksaanTbak.getSelectedRow(),13);
            emptTeks();
        } */
    }

    private void hapus() {
        if (tabModePemeriksaanTbak.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TNoRw.requestFocus();
        } else {
            for (i = 0; i < tbPemeriksaanTbak.getRowCount(); i++) {

                if (tbPemeriksaanTbak.getValueAt(i, 0).toString().equals("true")) {
                    if (akses.getkode().equals("Admin Utama")) {
                        Sequel.queryu("delete from pemeriksaan_ralan_tbak where no_rawat='" + tbPemeriksaanTbak.getValueAt(i, 1).toString()
                                + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(i, 4).toString()
                                + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(i, 5).toString() + "' ");
                    } else {
                        if (akses.getkode().equals(tbPemeriksaanTbak.getValueAt(i, 10).toString())) {
                            Sequel.queryu("delete from pemeriksaan_ralan_tbak where no_rawat='" + tbPemeriksaanTbak.getValueAt(i, 1).toString()
                                    + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(i, 4).toString()
                                    + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(i, 5).toString() + "' ");
                        } else {
                            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                        }
                    }
                }
            }
            tampilPemeriksaanTbak();
        }
    }

    private void simpan() {
        if ((!TSituation1.getText().trim().equals("")) || (!TBackground1.getText().trim().equals("")) || (!TAssesment1.getText().trim().equals(""))
                || (!TRecommendation1.getText().trim().equals(""))) {
            if (KdPeg4.getText().trim().equals("") || TPegawai4.getText().trim().equals("")) {
                Valid.textKosong(KdPeg4, "Dokter/Paramedis masih kosong...!!");
            } else {
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.menyimpan("pemeriksaan_ralan_tbak", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        TSituation1.getText(), TBackground1.getText(), TAssesment1.getText(), TRecommendation1.getText(), KdPeg4.getText(), KdDok3.getText()
                    });
                    tampilPemeriksaanTbak();
                    //    BtnBatalActionPerformed(evt);
                } else {
                    if (akses.getkode().equals(KdPeg4.getText())) {
                        Sequel.menyimpan("pemeriksaan_ralan_tbak", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            TSituation1.getText(), TBackground1.getText(), TAssesment1.getText(), TRecommendation1.getText(), KdPeg4.getText(), KdDok3.getText()
                        });
                        tampilPemeriksaanTbak();
                        //    BtnBatalActionPerformed(evt);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                    }
                }
            }
        }
    }

    private void tampilPemeriksaanTbak() {
        Valid.tabelKosong(tabModePemeriksaanTbak);
        try {
            ps7 = koneksi.prepareStatement("select pemeriksaan_ralan_tbak.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                    + "pemeriksaan_ralan_tbak.tgl_perawatan,pemeriksaan_ralan_tbak.jam_rawat,pemeriksaan_ralan_tbak.situation,pemeriksaan_ralan_tbak.background, "
                    + "pemeriksaan_ralan_tbak.assesment,pemeriksaan_ralan_tbak.recommendation,pemeriksaan_ralan_tbak.nip,pegawai.nama,pegawai.jbtn,IF(vpt.no_rawat IS NOT NULL, 'Tervalidasi', 'Belum Divalidasi'),pemeriksaan_ralan_tbak.kddokter,dokter.nm_dokter "
                    + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join pemeriksaan_ralan_tbak on pemeriksaan_ralan_tbak.no_rawat=reg_periksa.no_rawat "
                    + "inner join pegawai on pemeriksaan_ralan_tbak.nip=pegawai.nik inner join dokter on pemeriksaan_ralan_tbak.kddokter=dokter.kd_dokter "
                    + "LEFT JOIN validasi_pemeriksaan_tbak vpt ON CONCAT(pemeriksaan_ralan_tbak.no_rawat, pemeriksaan_ralan_tbak.tgl_perawatan, pemeriksaan_ralan_tbak.jam_rawat) = CONCAT (vpt.no_rawat, vpt.tgl_perawatan, vpt.jam_rawat) where "
                    + "pemeriksaan_ralan_tbak.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                    + (TCari.getText().trim().equals("") ? "" : "and (pemeriksaan_ralan_tbak.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                    + "pemeriksaan_ralan_tbak.situation like ? or pemeriksaan_ralan_tbak.background like ? or pemeriksaan_ralan_tbak.assesment like ? or "
                    + "pemeriksaan_ralan_tbak.recommendation like ?)")
                    + "order by pemeriksaan_ralan_tbak.no_rawat,pemeriksaan_ralan_tbak.tgl_perawatan,pemeriksaan_ralan_tbak.jam_rawat desc");
            try {
                ps7.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps7.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps7.setString(3, "%" + TNoRM.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps7.setString(4, "%" + TCari.getText().trim() + "%");
                    ps7.setString(5, "%" + TCari.getText().trim() + "%");
                    ps7.setString(6, "%" + TCari.getText().trim() + "%");
                    ps7.setString(7, "%" + TCari.getText().trim() + "%");
                    ps7.setString(8, "%" + TCari.getText().trim() + "%");
                    ps7.setString(9, "%" + TCari.getText().trim() + "%");
                    ps7.setString(10, "%" + TCari.getText().trim() + "%");
                }

                rs = ps7.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaanTbak.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePemeriksaanTbak.getRowCount());
    }

    private void getDataPemeriksaanTbak() {
        if (tbPemeriksaanTbak.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 2).toString());
            TCariPasien.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 3).toString());
            TSituation1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 6).toString());
            TBackground1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 7).toString());
            TAssesment1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 8).toString());
            TRecommendation1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 9).toString());
            cmbJam.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(6, 8));
            KdDok3.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 14).toString());
            TDokter3.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 15).toString());
            Valid.SetTgl(DTPTgl, tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4).toString());
        }
    }

}
