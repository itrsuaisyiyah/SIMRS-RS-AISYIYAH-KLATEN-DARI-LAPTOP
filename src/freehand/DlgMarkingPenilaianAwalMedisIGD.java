/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media).
 */

package freehand;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Dialog untuk coret-coret (marking) gambar III. STATUS LOKALIS
 * pada form Penilaian Awal Medis IGD.
 *
 * Disalin & disesuaikan dari freehand.DlgMarkingImageAssMedisIGD yang
 * sudah berjalan di modul Asesmen Medis IGD, supaya cara simpan/upload
 * ke web server (htdocs) dan struktur datanya konsisten dengan modul lain.
 *
 * @author perpustakaan
 */
public class DlgMarkingPenilaianAwalMedisIGD extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private String urlImage = "";
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int index = 0;
    private Point[] arr = new Point[100000];
    private BufferedImage img;
    private final SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");

    // Nama tabel & folder upload khusus form ini, dipisah dari
    // modul Asesmen Medis IGD supaya datanya tidak tercampur.
    private static final String TABEL_MARKING = "penilaian_medis_igd_image_marking";
    private static final String FOLDER_UPLOAD = "penilaianawalmedisigd/imagemarking";
    private static final String PREFIX_FILE = "PenilaianAwalIGD";
    // Gambar induk (default) untuk III. STATUS LOKALIS, harus sudah
    // diupload ke web server pada: htdocs/<HYBRIDWEB>/imagefreehand/masterimage/semua.png
    private static final String MASTER_IMAGE = "semua.png";

    /**
     * Creates new form DlgMarkingPenilaianAwalMedisIGD
     *
     * @param parent
     * @param modal
     */
    public DlgMarkingPenilaianAwalMedisIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setResizable(false);
        this.setLocation(0, 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRawat = new widget.TextBox();
        panelGlass9 = new widget.panelisi();
        PanelWall = new usu.widget.glass.PanelGlass();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnHapus1 = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Marking Status Lokalis - Penilaian Awal Medis IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 60));
        FormInput.setLayout(null);

        jLabel3.setText("No. Rawat");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 65, 23);

        TNoRawat.setEditable(false);
        TNoRawat.setHighlighter(null);
        TNoRawat.setName("TNoRawat"); // NOI18N
        FormInput.add(TNoRawat);
        TNoRawat.setBounds(70, 10, 470, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        panelGlass9.setBorder(null);
        panelGlass9.setAlignmentX(0.0F);
        panelGlass9.setAlignmentY(0.0F);
        panelGlass9.setMinimumSize(new java.awt.Dimension(0, 0));
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(878, 556));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWallMouseDragged(evt);
            }
        });
        PanelWall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWallMouseReleased(evt);
            }
        });
        PanelWall.setLayout(null);
        panelGlass9.add(PanelWall);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));
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
        panelGlass8.add(BtnSimpan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus Marking");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnHapus1.setMnemonic('G');
        BtnHapus1.setText("Gambar Baru");
        BtnHapus1.setToolTipText("Alt+G");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnHapus1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar.setMnemonic('T');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+T");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(DlgMarkingPenilaianAwalMedisIGD.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage Image = r.createScreenCapture(panelGlass9.bounds());
        String fileName = PREFIX_FILE + TNoRawat.getText().replaceAll("/", "") + ".png";
        try {
            new File("tmpImageFreehand").mkdirs();
            ImageIO.write(Image, "png", new File("tmpImageFreehand/" + fileName));
        } catch (IOException ex) {
            Logger.getLogger(DlgMarkingPenilaianAwalMedisIGD.class.getName()).log(Level.SEVERE, null, ex);
        }
        uploadImage(fileName, FOLDER_UPLOAD);

        String urlRelatif = FOLDER_UPLOAD + "/" + fileName;
        if (Sequel.cariInteger("select count(no_rawat) as jumlah from " + TABEL_MARKING + " where no_rawat='" + TNoRawat.getText() + "'") > 0) {
            Sequel.mengedittf(TABEL_MARKING, "no_rawat=?", "tanggal=?,jam=?,url_image=?", 4, new String[]{
                tanggalNow.format(new Date()), jamNow.format(new Date()), urlRelatif, TNoRawat.getText()
            });
        } else {
            Sequel.menyimpantf(TABEL_MARKING, "?,?,?,?", "No.Rawat", 4, new String[]{
                TNoRawat.getText(), tanggalNow.format(new Date()), jamNow.format(new Date()), urlRelatif
            });
        }
        dispose();
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void formWindowActivated(java.awt.event.WindowEvent evt) {
    }

    private void PanelWallMouseDragged(java.awt.event.MouseEvent evt) {
        arr[index] = new Point(evt.getXOnScreen(), evt.getYOnScreen());
        index++;
        Graphics g = getGraphics();
        g.setColor(Color.red);
        for (int i = 0; i < index - 1; i++) {
            g.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }

    private void PanelWallMouseReleased(java.awt.event.MouseEvent evt) {
        arr = new Point[100000];
        index = 0;
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        // Menghapus coretan yang baru digambar (belum disimpan) dengan
        // memuat ulang gambar yang sedang aktif (master atau hasil marking
        // terakhir yang tersimpan), lalu me-repaint panel.
        if (urlImage == null || urlImage.trim().isEmpty()) {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/masterimage/" + MASTER_IMAGE);
        } else {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/" + urlImage.trim());
        }
        repaint();
    }

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Kembali ke gambar induk/kosong (tanpa marking sama sekali)
        imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/masterimage/" + MASTER_IMAGE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgMarkingPenilaianAwalMedisIGD dialog = new DlgMarkingPenilaianAwalMedisIGD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.TextBox TNoRawat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    // End of variables declaration//GEN-END:variables

    /**
     * Dipanggil dari RMPenilaianAwalMedisIGD sebelum dialog ditampilkan.
     * Memuat gambar marking terakhir untuk no_rawat tsb kalau sudah ada,
     * atau gambar induk (semua.png) kalau belum pernah di-marking.
     *
     * @param norw no_rawat pasien yang sedang dibuka
     */
    public void setNoRw(String norw) {
        TNoRawat.setText(norw);
        urlImage = Sequel.cariIsi("select url_image from " + TABEL_MARKING + " where no_rawat='" + norw + "' ");
        if (urlImage == null || urlImage.trim().isEmpty()) {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/masterimage/" + MASTER_IMAGE);
        } else {
            imageAssesment("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/" + urlImage.trim());
        }
    }

    void uploadImage(String FileName, String docpath) {
        try {
            File file = new File("tmpImageFreehand/" + FileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
            deleteFile();
        } catch (Exception e) {
            System.out.println("Upload error" + e);
        }
    }

    void deleteFile() {
        File file = new File("tmpImageFreehand");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    void imageAssesment(String url) {
        try {
            BufferedImage image = ImageIO.read(new URL(url.trim()));
            PanelWall.setBackgroundImage(new javax.swing.ImageIcon(image));
        } catch (IOException ex) {
            // gagal ambil gambar dari web server (mis. file belum diupload / server tidak bisa diakses)
            Logger.getLogger(DlgMarkingPenilaianAwalMedisIGD.class.getName()).log(Level.WARNING, "Gagal memuat gambar lokalis dari {0}: {1}", new Object[]{url, ex.getMessage()});
        }
    }
}
