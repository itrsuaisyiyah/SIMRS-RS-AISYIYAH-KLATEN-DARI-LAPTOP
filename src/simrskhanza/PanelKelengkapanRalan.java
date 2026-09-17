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

/**
 * Panel indikator kelengkapan data pasien Rawat Jalan (mirip PanelKelengkapanIGD),
 * dipakai di DlgKasirRalan. Menampilkan tombol berwarna hijau (sudah terisi) atau
 * merah (belum terisi) untuk tiap jenis data, dan berfungsi sebagai shortcut ke
 * form terkait kalau tombolnya diklik.
 */
public class PanelKelengkapanRalan {

    public interface ShortcutListener {
        void onPemeriksaan();
        void onResep();
        void onLab();
        void onRadiologi();
    }

    private final JPanel panel;
    private final ShortcutListener listener;
    private final Connection koneksi;

    private final JButton btnPemeriksaan;
    private final JButton btnResep;
    private final JButton btnLab;
    private final JButton btnRadiologi;

    public PanelKelengkapanRalan(Connection koneksi, ShortcutListener listener) {
        this.koneksi = koneksi;
        this.listener = listener;

        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));

        btnPemeriksaan = new JButton("Pemeriksaan Dokter");
        btnResep = new JButton("Resep");
        btnLab = new JButton("Lab");
        btnRadiologi = new JButton("Radiologi");

        btnPemeriksaan.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onPemeriksaan();
            }
        });

        btnResep.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.onResep();
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

        panel.add(btnPemeriksaan);
        panel.add(btnResep);
        panel.add(btnLab);
        panel.add(btnRadiologi);

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
            System.out.println("Notif Kelengkapan Ralan : " + e);
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
        setStatus(btnPemeriksaan, false);
        setStatus(btnResep, false);
        setStatus(btnLab, false);
        setStatus(btnRadiologi, false);
    }

    public void loadFor(String noRawat) {

        setStatus(btnPemeriksaan,
                adaData(
                        "select no_rawat from rawat_jl_dr where no_rawat=? limit 1",
                        noRawat
                ));

        setStatus(btnResep,
                adaData(
                        "select no_rawat from resep_obat where no_rawat=? limit 1",
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
    }

    public static List<String> getMissing(Connection koneksi, String noRawat) {

        List<String> missing = new ArrayList<>();

        try {
            if (!adaDataStatic(koneksi,
                    "select no_rawat from rawat_jl_dr where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Pemeriksaan Dokter");
            }

            if (!adaDataStatic(koneksi,
                    "select no_rawat from resep_obat where no_rawat=? limit 1",
                    noRawat)) {
                missing.add("Resep");
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

        } catch (Exception e) {
            System.out.println("Notif Kelengkapan Ralan : " + e);
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
