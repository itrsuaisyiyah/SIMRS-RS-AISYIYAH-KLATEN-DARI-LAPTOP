package simrskhanza;

import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelKelengkapanIGD {

    public interface ShortcutListener {
        void onTriase();
        void onAsesmen();
        void onLab();
        void onRadiologi();
        void onResep();
        void onTransfer();

        public void onAsesmenKep();
    }

    private final JPanel panel;
    private final ShortcutListener listener;
    private final Connection koneksi;

    private final JButton btnTriase;
    private final JButton btnAsesmen;
    private final JButton btnAsesmenKep;
    private final JButton btnLab;
    private final JButton btnRadiologi;
    private final JButton btnResep;
    private final JButton btnTransfer;

    public PanelKelengkapanIGD(Connection koneksi, ShortcutListener listener) {
        this.koneksi = koneksi;
        this.listener = listener;

        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));

        btnTriase = new JButton("Triase");
        btnAsesmen = new JButton("Asesmen Dokter");
        btnAsesmenKep = new JButton("Asesmen Perawat");
        btnLab = new JButton("Lab");
        btnRadiologi = new JButton("Radiologi");
        btnResep = new JButton("Resep");
        btnTransfer = new JButton("Transfer Pasien");

        btnTriase.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onTriase();
            }
        });

        btnAsesmen.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onAsesmen();
            }
        });
        
         btnAsesmenKep.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onAsesmenKep();
            }
        });

        btnLab.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onLab();
            }
        });

        btnRadiologi.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onRadiologi();
            }
        });

        btnResep.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onResep();
            }
        });
        
        btnTransfer.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onTransfer();
            }
        });

        panel.add(btnTriase);
        panel.add(btnAsesmen);
        panel.add(btnAsesmenKep);
        panel.add(btnLab);
        panel.add(btnRadiologi);
        panel.add(btnResep);
        panel.add(btnTransfer);

        clear();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void setStatus(JButton btn, boolean ada) {
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);

        if (ada) {
            btn.setBackground(new Color(0, 180, 0));
            btn.setForeground(Color.WHITE);
            btn.setToolTipText("Sudah terisi");
        } else {
            btn.setBackground(new Color(220, 0, 0));
            btn.setForeground(Color.WHITE);
            btn.setToolTipText("Belum terisi");
        }
    }

    private boolean adaData(String sql, String noRawat) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Notif Kelengkapan IGD : " + e);
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }

            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
            }
        }
    }

    public void clear() {
        setStatus(btnTriase, false);
        setStatus(btnAsesmen, false);
         setStatus(btnAsesmenKep, false);
        setStatus(btnLab, false);
        setStatus(btnRadiologi, false);
        setStatus(btnResep, false);
         setStatus(btnTransfer, false);
    }

    public void loadFor(String noRawat) {

        setStatus(btnTriase,
                adaData(
                        "select no_rawat from data_triase_igd where no_rawat=? limit 1",
                        noRawat
                ));

        setStatus(btnAsesmen,
                adaData(
                        "select no_rawat from penilaian_medis_igd where no_rawat=? limit 1",
                        noRawat
                ));
        
         setStatus(btnAsesmenKep,
                adaData(
                        "select no_rawat from penilaian_awal_keperawatan_igd where no_rawat=? limit 1",
                        noRawat
                ));

        setStatus(btnLab,
                adaData(
                        "select no_rawat from permintaan_lab where no_rawat=? limit 1",
                        noRawat
                ));

        setStatus(btnRadiologi,
                adaData(
                        "select no_rawat from permintaan_radiologi where no_rawat=? limit 1",
                        noRawat
                ));

        setStatus(btnResep,
                adaData(
                        "select no_rawat from resep_obat where no_rawat=? limit 1",
                        noRawat
                ));
        
         setStatus(btnTransfer,
                adaData(
                        "select no_rawat from transfer_pasien_antar_ruang where no_rawat=? limit 1",
                        noRawat
                ));
    }

    public static List<String> getMissing(Connection koneksi, String noRawat) {

        List<String> missing = new ArrayList<>();

        try {
            if (!adaDataStatic(koneksi,
                    "select no_rawat from data_triase_igd where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Triase");
            }

            if (!adaDataStatic(koneksi,
                    "select no_rawat from penilaian_medis_igd where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Asesmen");
            }
            
            if (!adaDataStatic(koneksi,
                    "select no_rawat from penilaian_awal_keperawatan_igd where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("AsesmenKep");
            }

            if (!adaDataStatic(koneksi,
                    "select no_rawat from permintaan_lab where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Lab");
            }

            if (!adaDataStatic(koneksi,
                    "select no_rawat from permintaan_radiologi where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Radiologi");
            }

            if (!adaDataStatic(koneksi,
                    "select no_rawat from resep_obat where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Resep");
            }
            
            if (!adaDataStatic(koneksi,
                    "select no_rawat from transfer_pasien_antar_ruang where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Transfer");
            }

        } catch (Exception e) {
            System.out.println("Notif Kelengkapan IGD : " + e);
        }

        return missing;
    }

    private static boolean adaDataStatic(Connection koneksi, String sql, String noRawat) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }

            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
            }
        }
    }
}