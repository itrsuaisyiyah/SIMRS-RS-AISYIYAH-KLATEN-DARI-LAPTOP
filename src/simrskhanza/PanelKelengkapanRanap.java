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
import java.awt.Dimension;
public class PanelKelengkapanRanap {

    public interface ShortcutListener {
        void onAwalMedisKandungan();
        void onAwalMedisAnak();
        void onAwalBBL();
        void onAwalMedisNeonatus();
        void onAwalMedisBedah();
        void onAwalMedisOrthopedi();
        void onAwalMedisNeurologi();
        void onAwalMedisParu();
        void onAwalMedisPenyakitDalam();
        void onAwalMedisTHT();
        void onLaporanOP();
        void onResume();
        void onLab();
        void onRadiologi();
        void onResep();
        void onTransfer();
    }

    private final JPanel panel;
    private final ShortcutListener listener;
    private final Connection koneksi;
    private final JButton btnAwalMedisKandungan;
    private final JButton btnAwalMedisAnak;
    private final JButton btnAwalBBL;
    private final JButton btnAwalMedisNeonatus;
    private final JButton btnAwalMedisBedah;
    private final JButton btnAwalMedisOrthopedi;
    private final JButton btnAwalMedisNeurologi;
    private final JButton btnAwalMedisParu;
    private final JButton btnAwalMedisPenyakitDalam;
    private final JButton btnAwalMedisTHT;
    private final JButton btnLaporanOP;
    private final JButton btnResume;
    private final JButton btnLab;
    private final JButton btnRadiologi;
    private final JButton btnResep;
    private final JButton btnTransfer;

   public PanelKelengkapanRanap(Connection koneksi, ShortcutListener listener) {
    this.koneksi = koneksi;
    this.listener = listener;

    
    panel = new JPanel();
    panel.setLayout(new java.awt.GridLayout(1, 13, 1, 1));
    panel.setPreferredSize(new java.awt.Dimension(1200, 22));

    btnAwalMedisKandungan = new JButton("Obsgyn");
    btnAwalMedisAnak = new JButton("Anak");
    btnAwalBBL = new JButton("BBL");
    btnAwalMedisNeonatus = new JButton("Neonatus");
    btnAwalMedisBedah = new JButton("Bedah");
    btnAwalMedisOrthopedi = new JButton("Orthopedi");
    btnAwalMedisNeurologi = new JButton("Syaraf");
    btnAwalMedisParu = new JButton("Paru");
    btnAwalMedisPenyakitDalam = new JButton("Pnykt Dalam");
    btnAwalMedisTHT = new JButton("THT");
    btnLaporanOP = new JButton("Lap. Operasi");
    btnResume = new JButton("Resume");
    btnLab = new JButton("Lab");
    btnRadiologi = new JButton("Rad");
    btnResep = new JButton("Resep");
    btnTransfer = new JButton("TF Pasien");

    // Ukuran tombol
    Dimension ukuran = new Dimension(80, 18);

    btnAwalMedisKandungan.setPreferredSize(ukuran);
    btnAwalMedisAnak.setPreferredSize(ukuran);
    btnAwalBBL.setPreferredSize(ukuran);
    btnAwalMedisNeonatus.setPreferredSize(ukuran);
    btnAwalMedisBedah.setPreferredSize(ukuran);
    btnAwalMedisOrthopedi.setPreferredSize(ukuran);
    btnAwalMedisNeurologi.setPreferredSize(ukuran);
    btnAwalMedisParu.setPreferredSize(ukuran);
    btnAwalMedisPenyakitDalam.setPreferredSize(ukuran);
    btnAwalMedisTHT.setPreferredSize(ukuran);
    btnLaporanOP.setPreferredSize(ukuran);
    btnResume.setPreferredSize(ukuran);
    btnLab.setPreferredSize(ukuran);
    btnRadiologi.setPreferredSize(ukuran);
    btnResep.setPreferredSize(ukuran);
    btnTransfer.setPreferredSize(ukuran);

    // Action Listener
    btnAwalMedisKandungan.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisKandungan();
    });

    btnAwalMedisAnak.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisAnak();
    });

    btnAwalBBL.addActionListener(e -> {
        if (listener != null) listener.onAwalBBL();
    });

    btnAwalMedisNeonatus.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisNeonatus();
    });

    btnAwalMedisBedah.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisBedah();
    });
    
    btnAwalMedisOrthopedi.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisOrthopedi();
    });

    btnAwalMedisNeurologi.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisNeurologi();
    });

    btnAwalMedisParu.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisParu();
    });

    btnAwalMedisPenyakitDalam.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisPenyakitDalam();
    });
    
    btnAwalMedisTHT.addActionListener(e -> {
        if (listener != null) listener.onAwalMedisTHT();
    });

    btnLaporanOP.addActionListener(e -> {
        if (listener != null) listener.onLaporanOP();
    });

    btnResume.addActionListener(e -> {
        if (listener != null) listener.onResume();
    });

    btnLab.addActionListener(e -> {
        if (listener != null) listener.onLab();
    });

    btnRadiologi.addActionListener(e -> {
        if (listener != null) listener.onRadiologi();
    });

    btnResep.addActionListener(e -> {
        if (listener != null) listener.onResep();
    });

    btnTransfer.addActionListener(e -> {
        if (listener != null) listener.onTransfer();
    });

    panel.add(btnAwalMedisKandungan);
    panel.add(btnAwalMedisAnak);
    panel.add(btnAwalBBL);
    panel.add(btnAwalMedisNeonatus);
    panel.add(btnAwalMedisBedah);
    panel.add(btnAwalMedisOrthopedi);
    panel.add(btnAwalMedisNeurologi);
    panel.add(btnAwalMedisParu);
    panel.add(btnAwalMedisPenyakitDalam);
    panel.add(btnAwalMedisTHT);
    panel.add(btnLaporanOP);
    panel.add(btnResume);
    panel.add(btnLab);
    panel.add(btnRadiologi);
    panel.add(btnResep);
    panel.add(btnTransfer);

    // Pengisi kolom terakhir agar simetris
    panel.add(new javax.swing.JLabel(""));

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
        btn.setToolTipText("Sudah Terisi");
    } else {
        btn.setBackground(new Color(220, 0, 0));
        btn.setForeground(Color.WHITE);
        btn.setToolTipText("Belum Terisi");
    }

    btn.repaint();
    btn.revalidate();
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
            System.out.println("Notif Kelengkapan Ranap : " + e);
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
        setStatus(btnAwalMedisKandungan, false);
        setStatus(btnAwalMedisAnak, false);
        setStatus(btnAwalBBL, false);
        setStatus(btnAwalMedisNeonatus, false);
        setStatus(btnAwalMedisBedah, false);
        setStatus(btnAwalMedisOrthopedi, false);
        setStatus(btnAwalMedisNeurologi, false);
        setStatus(btnAwalMedisParu, false);
        setStatus(btnAwalMedisPenyakitDalam, false);
        setStatus(btnAwalMedisTHT, false);
        setStatus(btnLaporanOP, false);
        setStatus(btnResume, false);
        setStatus(btnLab, false);
        setStatus(btnRadiologi, false);
        setStatus(btnResep, false);
        setStatus(btnTransfer, false);
    }

    public void loadFor(String noRawat) {

    setStatus(btnAwalMedisKandungan,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_kandungan where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisAnak,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_anak where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalBBL,
            adaData(
                    "select no_rawat from penilaian_bayi_baru_lahir where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisNeonatus,
            adaData(
                    "select no_rawat from asesmen_medis_ranap_neonatus where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisBedah,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_bedah where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisOrthopedi,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_orthopedi where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisNeurologi,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_neurologi where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisParu,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_paru where no_rawat=? limit 1",
                    noRawat
            ));
    
    
    setStatus(btnAwalMedisPenyakitDalam,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_penyakit_dalam where no_rawat=? limit 1",
                    noRawat
            ));
    
    setStatus(btnAwalMedisTHT,
            adaData(
                    "select no_rawat from penilaian_medis_ranap_tht where no_rawat=? limit 1",
                    noRawat
            ));

    setStatus(btnLaporanOP,
            adaData(
                    "select no_rawat from laporan_operasi where no_rawat=? limit 1",
                    noRawat
            ));

    setStatus(btnResume,
            adaData(
                    "select no_rawat from resume_pasien_ranap where no_rawat=? limit 1",
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
                    "select no_rawat from detail_pemberian_obat where no_rawat=? limit 1",
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
                "select no_rawat from penilaian_medis_ranap_kandungan where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Kandungan");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_anak where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Anak");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_bayi_baru_lahir where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Bayi Baru Lahir");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_neonatus where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Neonatus");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_bedah where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Bedah");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_orthopedi where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Orthopedi");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_neurologi where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Saraf");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_paru where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Paru");
        }
        
        if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_penyakit_dalam where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis Penyakit Dalam");
        }
        
         if (!adaDataStatic(koneksi,
                "select no_rawat from penilaian_medis_ranap_tht where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Awal Medis THT");
        }

        if (!adaDataStatic(koneksi,
                "select no_rawat from laporan_operasi where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Laporan Operasi");
        }

        if (!adaDataStatic(koneksi,
                "select no_rawat from resume_pasien_ranap where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Resume");
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
                "select no_rawat from detail_pemberian_obat where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Resep");
        }

        if (!adaDataStatic(koneksi,
                "select no_rawat from transfer_pasien_antar_ruang where no_rawat=? limit 1",
                noRawat)) {
            missing.add("Transfer");
        }

    } catch (Exception e) {
        System.out.println("Notif Kelengkapan Ranap : " + e);
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