/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAbout.java
 *
 * Created on 23 Jun 10, 19:03:08
 */

package integration_orthanc;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import custom.*;
import fungsi.WarnaTable;
import laporan.*;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import simrskhanza.DlgPilihanCetakDokumen;

/**
 *
 * @author perpustakaan
 */
public class DicomViewerHybridSplitRadiologiClient extends javax.swing.JDialog {
    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    private int i;
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JLabel lblStatus = new JLabel();
    private final DefaultTableModel tabMode;
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();
    private final Properties prop = new Properties(); 
    private final validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private String halaman="",norawat="",auth,authEncrypt,requestJson;
    private PreparedStatement ps;
    private ResultSet rs;
    private final validasi validasi=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private final DlgPilihanCetakDokumen pilihan=new DlgPilihanCetakDokumen(null,false);
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root,subroot,subroot2,subresponse,subresponse2;
    private JsonNode nameNode;
    private JsonNode response,responsename;
    private ApiIntegrationOrthanc apiDicom=new ApiIntegrationOrthanc();
    public DicomViewerHybridSplitRadiologiClient(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initComponents2();
        auth=koneksiDB.USERORTHANC()+":"+koneksiDB.PASSORTHANC();
        byte[] encodedBytes = Base64.encodeBase64(auth.getBytes());
        authEncrypt= new String(encodedBytes);
        tabMode=new DefaultTableModel(null,new Object[]{
            "P","UUID Pasien","ID Pasein","Nama Pasien","ID Studies","ID Series","No Rawat"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
                     , java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbListRadiologi.setModel(tabMode);
        tbListRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbListRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbListRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(300);
            }else if(i==4){
                column.setPreferredWidth(300);
            }else if(i==5){
                column.setPreferredWidth(300);
            }else if(i==6){
                column.setPreferredWidth(160);
            }
        }
        tbListRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
    }
    public void tampilDicomServer(String TglAwal, String TglAkhir, String NoRm,String NoRawat){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.tabelKosong(tabMode);
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
                requestEntity = new HttpEntity(headers);
                requestJson = "{"+
                                 "\"Level\": \"Study\","+
                                 "\"Expand\": true,"+
                                 "\"Query\": {"+
                                 "\"StudyDate\": \""+TglAwal+"-"+TglAkhir+"\","+
                                "\"PatientID\": \""+NoRm+"\","+
                                 "}}";
                            requestEntity = new HttpEntity(requestJson,headers);
                            root = mapper.readTree(apiDicom.getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody());
                            i=1;
                            for(JsonNode list:root){
                                 for(JsonNode sublist:list.path("Series")){
                                      tabMode.addRow(new Object[]{
                                      false,list.path("ParentPatient").asText(),list.path("PatientMainDicomTags").path("PatientID").asText(),list.path("PatientMainDicomTags").path("PatientName").asText(), list.path("ID").asText(),sublist.asText(),NoRawat
                                   });   
                                 }
                                          
                            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        try {

                        loadURL(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),5).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),0).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),3).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),4).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),5).toString());

                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }
         this.setCursor(Cursor.getDefaultCursor());
        
    }
    public void setPasien(String NoRawat,String TglPeriksa,String JamPeriksa){
                HasilPeriksa.setText("");
                noRawat.setText("");
                tglPeriksa.setText("");
                jamPeriksa.setText("");
                noRawat.setVisible(false);
                tglPeriksa.setVisible(false);
                jamPeriksa.setVisible(false);
                HasilPeriksa.setText(Sequel.cariIsi("select hasil from hasil_radiologi where hasil_radiologi.no_rawat='"+NoRawat+"' and hasil_radiologi.tgl_periksa='"+TglPeriksa+"' and hasil_radiologi.jam='"+JamPeriksa+"'"));
                noRawat.setText(NoRawat);
                tglPeriksa.setText(TglPeriksa);
                jamPeriksa.setText(JamPeriksa);
    }
    private void initComponents2() {           
        txtURL.addActionListener((ActionEvent e) -> {
            loadURL(txtURL.getText(),"","","","");
        });
  
        progressBar.setPreferredSize(new Dimension(550, 508));
        progressBar.setStringPainted(true);
        
        panel.add(jfxPanel, BorderLayout.CENTER);
        
        internalFrame1.setLayout(new BorderLayout());
        internalFrame1.add(panel);        
    }
    
     private void createScene() {        
        Platform.runLater(new Runnable() {

            public void run() {
                WebView view = new WebView();
                
                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);
                
                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });
                
                engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        DicomViewerHybridSplitRadiologiClient.this.setTitle(newValue);
                    });
                });
                
                
                engine.setOnStatusChanged((final WebEvent<String> event) -> {
                    SwingUtilities.invokeLater(() -> {
                        lblStatus.setText(event.getData());
                    });
                });
                
                
                engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(newValue.intValue());
                    });                                                   
                });
                
                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    panel,
                                    (value != null) ?
                                            engine.getLocation() + "\n" + value.getMessage() :
                                            engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                
                engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        txtURL.setText(newValue);
                    });
                });
                
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            try {
                                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                                if(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").contains("gbrpemeriksaan/pages")){
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    Valid.panggilUrl(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/gbrpemeriksaan/pages/upload/","gbrpemeriksaan/").replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+"/"+prop.getProperty("HYBRIDWEB")+"/gbrpemeriksaan/pages/upload/","gbrpemeriksaan/"));
                                    engine.executeScript("history.back()");
                                    setCursor(Cursor.getDefaultCursor());
                                }else if(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").contains("Keluar")){
                                    dispose();
                                }else if(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").contains("GABUNG")){
                                    norawat=Sequel.cariIsi("select no_rawat from temppanggilnorawat");
                                    ps=koneksi.prepareStatement("SELECT berkas_digital_perawatan.lokasi_file "+
                                                  "from berkas_digital_perawatan inner join master_berkas_digital "+
                                                  "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                                  "where berkas_digital_perawatan.no_rawat=? ORDER BY master_berkas_digital.nama ASC ");
                                    try {
                                        PDFMergerUtility ut = new PDFMergerUtility();
                                        URL url;
                                        ps.setString(1,norawat);
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            url = new URL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/berkasrawat/"+rs.getString("lokasi_file"));
                                            InputStream is = url.openStream();
                                            ut.addSource(is);
                                        }
                                        ut.setDestinationFileName("merge.pdf");
                                        ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                                        JOptionPane.showMessageDialog(null,"Proses gabung file selesai..!");
                                        Properties systemProp = System.getProperties();
                                        String currentDir = systemProp.getProperty("user.dir");
                                        File dir = new File(currentDir);
                                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                        Valid.panggilUrl2(dir+"/merge.pdf");
                                        setCursor(Cursor.getDefaultCursor());
                                    } catch (SQLException e) {
                                        System.out.println("Notif : "+e);
                                    } catch (IOException e) {
                                        System.out.println("Notif : "+e);
                                        JOptionPane.showMessageDialog(null,"Gagal menggabungkan file, cek kembali file apakah sudah dalam bentuk PDF.\nAtau cek kembali hak akses file di server dokumen..!!");
                                    } finally{
                                        if(rs!=null){
                                            rs.close();
                                        }
                                        if(ps!=null){
                                            ps.close();
                                        }
                                    }                                    
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : "+ex);
                            }
                        } 
                    }
                });
                
                jfxPanel.setScene(new Scene(view));
            }
        });
    }
 
    public void loadURL(String url,String NoRawat,String TglPeriksa,String JamPeriksa,String UUIDSeries) {  
        try {
            createScene();
        } catch (Exception e) {
        }
        
        Platform.runLater(() -> {
            try {
                
                engine.getCreatePopupHandler(); //setOnAlert(null);
                engine.setJavaScriptEnabled(true);
                engine.setUserAgent("foo\nAuthorization: Basic "+authEncrypt);
                engine.load(url);
                
            }catch (Exception exception) {
                engine.load(url);
            }
        });        
    }    
    
    public void CloseScane(){
        Platform.setImplicitExit(false);
    }
    
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGlass8 = new widget.panelisi();
        BtnKeluar1 = new widget.Button();
        noRawat = new widget.TextBox();
        tglPeriksa = new widget.TextBox();
        jamPeriksa = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        internalFrame1 = new widget.InternalFrame();
        internalFrame2 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        HasilPeriksa = new widget.TextArea();
        Scroll4 = new widget.ScrollPane();
        tbListRadiologi = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("::[ About Program ]::");
        setUndecorated(true);
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar1);

        noRawat.setEditable(false);
        noRawat.setToolTipText("Alt+C");
        noRawat.setName("noRawat"); // NOI18N
        noRawat.setPreferredSize(new java.awt.Dimension(160, 23));
        noRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noRawatActionPerformed(evt);
            }
        });
        noRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noRawatKeyPressed(evt);
            }
        });
        panelGlass8.add(noRawat);

        tglPeriksa.setEditable(false);
        tglPeriksa.setToolTipText("Alt+C");
        tglPeriksa.setName("tglPeriksa"); // NOI18N
        tglPeriksa.setPreferredSize(new java.awt.Dimension(160, 23));
        tglPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglPeriksaActionPerformed(evt);
            }
        });
        tglPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglPeriksaKeyPressed(evt);
            }
        });
        panelGlass8.add(tglPeriksa);

        jamPeriksa.setEditable(false);
        jamPeriksa.setToolTipText("Alt+C");
        jamPeriksa.setName("jamPeriksa"); // NOI18N
        jamPeriksa.setPreferredSize(new java.awt.Dimension(160, 23));
        jamPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jamPeriksaActionPerformed(evt);
            }
        });
        jamPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jamPeriksaKeyPressed(evt);
            }
        });
        panelGlass8.add(jamPeriksa);

        getContentPane().add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "Gambar Xray", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(500, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(internalFrame1);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(500, 500));
        internalFrame2.setLayout(new java.awt.BorderLayout());

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Hasil Pemeriksaan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        HasilPeriksa.setColumns(2);
        HasilPeriksa.setRows(5);
        HasilPeriksa.setName("HasilPeriksa"); // NOI18N
        Scroll3.setViewportView(HasilPeriksa);

        internalFrame2.add(Scroll3, java.awt.BorderLayout.CENTER);

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " List Foto Pemeriksaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(460, 200));

        tbListRadiologi.setName("tbListRadiologi"); // NOI18N
        tbListRadiologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListRadiologiMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbListRadiologi);

        internalFrame2.add(Scroll4, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(internalFrame2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Platform.setImplicitExit(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if(this.isActive()==false){
            Platform.setImplicitExit(false);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{}
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void noRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noRawatActionPerformed

    private void noRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noRawatKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            btnCariPeriksaActionPerformed(null);
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
//            btnCariPeriksa.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            BtnTambahPeriksa.requestFocus();
//        }
    }//GEN-LAST:event_noRawatKeyPressed

    private void tglPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglPeriksaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tglPeriksaActionPerformed

    private void tglPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglPeriksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tglPeriksaKeyPressed

    private void jamPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jamPeriksaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamPeriksaActionPerformed

    private void jamPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jamPeriksaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamPeriksaKeyPressed

    private void tbListRadiologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListRadiologiMouseClicked
        loadURL(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),5).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),0).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),3).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),4).toString(),tbListRadiologi.getValueAt(tbListRadiologi.getSelectedRow(),5).toString());
    }//GEN-LAST:event_tbListRadiologiMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DicomViewerHybridSplitRadiologiClient dialog = new DicomViewerHybridSplitRadiologiClient(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar1;
    private widget.TextArea HasilPeriksa;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox jamPeriksa;
    private widget.TextBox noRawat;
    private widget.panelisi panelGlass8;
    private widget.Table tbListRadiologi;
    private widget.TextBox tglPeriksa;
    // End of variables declaration//GEN-END:variables

    public void setJudul(String Judul,String Pages){
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), Judul, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70,70,70))); 
        this.halaman=Pages;
    }
}
