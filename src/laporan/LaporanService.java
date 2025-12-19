package laporan;

import anggota.AnggotaService;
import buku.BukuService;
import transaksi.Transaksi;
import transaksi.TransaksiService;
import utils.ConsoleUtil;
import utils.Constant;

public class LaporanService {
    public static void laporanPerpustakaan() {
        ConsoleUtil.clearScreen();

        String hariIniStr = ConsoleUtil.readStringOptional(
            "Masukkan tanggal hari ini",
            "Contoh: 01/12/2025"
        );
        if (hariIniStr == null) return;

        String tanggalLaporan = ConsoleUtil.readStringOptional(
            "Masukkan tanggal laporan",
            "Contoh: 31/12/2025"
        );
        if (tanggalLaporan == null) return;

        int hariIni = ConsoleUtil.parseHari(hariIniStr);
        if(hariIni == -1) {
            ConsoleUtil.resetBackInput();
            return;
        }
        int hariLaporan = ConsoleUtil.parseHari(tanggalLaporan);
        if(hariLaporan == -1) {
            ConsoleUtil.resetBackInput();
            return;
        }

        int peminjamanAktif = 0;
        int terlambat = 0;
        int totalDenda = 0;
        int totalEksemplarAwal = BukuService.getTotalEksemplarAwalByPeriode(hariLaporan);
        int eksemplarDipinjam = 0; 

        Transaksi[] data = TransaksiService.getData();

        for (int i = 0; i < TransaksiService.getJumlahTransaksi(); i++) {
            Transaksi t = data[i];

            int hariPinjam = ConsoleUtil.parseHari(t.getTglPinjam());
            if (hariPinjam == -1) return;

            if (hariPinjam > hariLaporan) continue;

            if (!t.isSudahDikembalikan()) {
                eksemplarDipinjam++;
                peminjamanAktif++;
                continue;
            }

            int hariKembali = ConsoleUtil.parseHari(t.getTglKembali());
            if (hariKembali == -1) return;

            if (hariKembali > hariLaporan) {
                eksemplarDipinjam++;
                peminjamanAktif++;
                continue;
            }

            int lamaPinjam = hariKembali - hariPinjam;

            if (lamaPinjam > 7 && hariKembali >= hariIni && hariKembali <= hariLaporan) {

                terlambat++;
                int hariTerlambat = lamaPinjam - 7;
                totalDenda += hariTerlambat * Constant.DENDA_PER_HARI;
            }
        }

        int stokPerTanggal = totalEksemplarAwal - eksemplarDipinjam;
        int totalBukuPeriode = BukuService.getJumlahBukuByPeriode(hariLaporan);
        int totalAnggotaPeriode = AnggotaService.getTotalAnggotaByPeriode(hariLaporan);
        int totalTransaksi = TransaksiService.getTotalTransaksiByPeriode(hariLaporan);

        System.out.println("\n========= LAPORAN PERPUSTAKAAN =========");
        System.out.println("Periode laporan : " + hariIniStr + " s/d " + tanggalLaporan);

        System.out.println("\nBuku");
        System.out.println("- Total buku         : " + totalBukuPeriode + " buku");
        System.out.println("- Total eksemplar    : " + stokPerTanggal + " eksemplar");

        System.out.println("\nAnggota");
        System.out.println("- Total anggota      : " + totalAnggotaPeriode + " anggota");

        System.out.println("\nTransaksi");
        System.out.println("- Total transaksi    : " + totalTransaksi + " transaksi");
        System.out.println("- Peminjaman aktif   : " + peminjamanAktif + " aktif");
        System.out.println("- Terlambat          : " + terlambat + " anggota");

        System.out.println("\nDenda");
        System.out.println("- Total denda        : Rp " + totalDenda);

        System.out.println("======================================");

        ConsoleUtil.resetBackInput();
    }

    public static void laporanTerlambat() {
        ConsoleUtil.clearScreen();

        String hariIniStr = ConsoleUtil.readStringOptional(
            "Masukkan tanggal hari ini",
            "Contoh: 31/12/2025"
        );
        if (hariIniStr == null) return;

        int hariIni = ConsoleUtil.parseHari(hariIniStr);
        if(hariIni == -1) {
            ConsoleUtil.resetBackInput();
            return;
        }

        String tanggalLaporan = ConsoleUtil.readStringOptional(
            "Masukkan tanggal laporan",
            "Contoh: 31/12/2025"
        );
        if (tanggalLaporan == null) return;

        int hariLaporan = ConsoleUtil.parseHari(tanggalLaporan);
        if(hariLaporan == -1) {
            ConsoleUtil.resetBackInput();
            return;
        }

        System.out.println("====== LAPORAN PEMINJAMAN TERLAMBAT ======");
        System.out.println("Periode : " + hariIniStr + " s/d " + tanggalLaporan);

        boolean ada = false;
        Transaksi[] data = TransaksiService.getData();

        for (int i = 0; i < TransaksiService.getJumlahTransaksi(); i++) {
            Transaksi t = data[i];

            if (!t.isSudahDikembalikan()) continue;

            int hariPinjam = ConsoleUtil.parseHari(t.getTglPinjam());
            if(hariPinjam == -1) {
                ConsoleUtil.resetBackInput();
                return;
            }
            int hariKembali = ConsoleUtil.parseHari(t.getTglKembali());
            if(hariKembali == -1) {
                ConsoleUtil.resetBackInput();
                return;
            }

            if (hariKembali < hariIni || hariKembali > hariLaporan) continue;

            int lamaPinjam = hariKembali - hariPinjam;

            if (lamaPinjam > 7) {
                int hariTerlambat = lamaPinjam - 7;
                long denda = hariTerlambat * Constant.DENDA_PER_HARI;

                System.out.println("--------------------------------");
                System.out.println("No Transaksi : " + t.getNo());
                System.out.println("ID Anggota   : " + t.getIdAnggota());
                System.out.println("Nama         : " + t.getNamaAnggota());
                System.out.println("ISBN Buku    : " + t.getIsbnBuku());
                System.out.println("Tgl Pinjam   : " + t.getTglPinjam());
                System.out.println("Tgl Kembali  : " + t.getTglKembali());
                System.out.println("Terlambat    : " + hariTerlambat + " hari");
                System.out.println("Denda        : Rp " + denda);
                ada = true;
            }
        }

        if (!ada) {
            System.out.println("Tidak ada peminjaman terlambat.");
        }

        ConsoleUtil.resetBackInput();
    }

}
