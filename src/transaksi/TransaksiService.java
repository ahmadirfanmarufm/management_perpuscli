package transaksi;

import anggota.Anggota;
import anggota.AnggotaService;
import buku.Buku;
import buku.BukuService;
import utils.ConsoleUtil;
import utils.Constant;

public class TransaksiService {
    private static Transaksi[] data = new Transaksi[100];
    private static int jumlah = 0;
    private static int autoNo = 1;

    BukuService bukuService = new BukuService();
    
    public void pinjamBuku() {
        ConsoleUtil.clearScreen();
        System.out.println("======= PEMINJAMAN BUKU =======");

        int idAnggota = ConsoleUtil.readInt("ID Anggota: ", 1, 9999);
        Anggota a = AnggotaService.getAnggotaById(idAnggota);

        if(a == null) {
            System.out.println("Anggota belum terdaftar.");
            ConsoleUtil.resetBackInput();
            return;
        }

        String isbn = ConsoleUtil.readStringOptional("ISBN Buku", "contoh: 978-602");
        if (isbn == null) return;

        Buku b = bukuService.getBuku(isbn);

        if(b == null) {
            System.out.println("Buku tidak ditemukan");
            ConsoleUtil.resetBackInput();
            return;
        }

        if(b.getEksemplar() <= 0) {
            System.out.println("Stok buku habis!");
            ConsoleUtil.resetBackInput();
            return;
        }

        String tglPinjam = ConsoleUtil.readStringOptional(
            "Tanggal Pinjam (Contoh: 31/12/2025)",
            "dd/mm/yyyy"
        );

        if (tglPinjam == null) return;

        if (jumlah >= data.length) {
            System.out.println("Data transaksi penuh!");
            return;
        }

        bukuService.kurangiEksemplar(b);

        
        data[jumlah] = new Transaksi(autoNo++, idAnggota, a.getNamaAnggota(), isbn, tglPinjam);
        jumlah++;

        System.out.println("Peminjaman berhasil dicatat.");
        ConsoleUtil.resetBackInput();
    }
    
    public void kembalikanBuku() {
        ConsoleUtil.clearScreen();
        System.out.println("======= PENGEMBALIAN BUKU =======");

        int no = ConsoleUtil.readInt("No Transaksi: ", 1, autoNo);

        for (int i = 0; i < jumlah; i++) {
            Transaksi t = data[i];
            if (t.getNo() == no && !t.isSudahDikembalikan()) {

                Buku b = bukuService.getBuku(t.getIsbnBuku());
                if(b != null) bukuService.tambahEksemplar(b);

                String tglKembali = ConsoleUtil.readStringOptional(
                    "Tanggal Pengembalian (Contoh: 31/12/2025)",
                    "dd/mm/2025"
                );
                if (tglKembali == null) return;

                int hariPinjam = ConsoleUtil.parseHari(t.getTglPinjam());
                if(hariPinjam == -1) {
                    ConsoleUtil.resetBackInput();
                    return;
                }
                int hariKembali = ConsoleUtil.parseHari(tglKembali);
                if(hariKembali == -1) {
                    ConsoleUtil.resetBackInput();
                    return;
                }

                int selisih = hariKembali - hariPinjam;
                if(selisih > 7) {
                    int hariTerlambat = selisih - 7;
                    long denda = hariTerlambat * Constant.DENDA_PER_HARI;
                    System.out.println(Constant.RED_COLOR + "Terlambat! Denda Rp. " + denda + Constant.RESET);
                } else {
                    System.out.println(Constant.GREEN_COLOR + "Dikembalikan tepat waktu. Tidak ada denda " + Constant.RESET);
                }

                t.kembalikan(tglKembali);
                ConsoleUtil.resetBackInput();
                return;
            }
        }
        System.out.println("Transaksi tidak ditemukan / sudah dikembalikan.");
        ConsoleUtil.resetBackInput();
    }

    public void tampilkanAktif() {
        ConsoleUtil.clearScreen();
        System.out.println("========================= PEMINJAMAN AKTIF =========================");

        System.out.printf("| %-2s | %-10s | %-16s | %-13s | %-11s |\n", 
        "No", "ID Anggota", "Nama Anggota", "ISBN Buku", "Tgl Pinjam");
        System.out.println("---------------------------------------------------------------------");

        boolean ada = false;
        int totalAktif = 0;

        for (int i = 0; i < jumlah; i++) {
            Transaksi t = data[i];
            if (!t.isSudahDikembalikan()) {
                System.out.printf(
        "| %-2d | %-10d | %-16s | %-13s | %-11s |\n",
                t.getNo(),
                t.getIdAnggota(),
                ConsoleUtil.potong(t.getNamaAnggota(), 16),
                t.getIsbnBuku(),
                t.getTglPinjam()
            );
                ada = true;
                totalAktif++;
            }
        }

        System.out.println("---------------------------------------------------------------------");

        if (!ada) {
            System.out.println("Tidak ada peminjaman aktif.");
        } else {
            System.out.println("Total peminjaman aktif: " + totalAktif);
        }
        System.out.println("=====================================================================");
        ConsoleUtil.resetBackInput();
    }

    public static int getTotalTransaksiByPeriode(int hariLaporan) {
        int total = 0;

        for(int i = 0; i < jumlah; i++) {
            Transaksi t = data[i];

            int hariPinjam = ConsoleUtil.parseHari(t.getTglPinjam());
            if(hariPinjam == -1) continue;

            if(hariPinjam <= hariLaporan) {
                total++;
            }
        }

        return total;
    }

    public static int getJumlahTransaksi() {
        return jumlah;
    }

    public static int getJumlahAktif() {
        int count = 0;
        for(int i = 0; i < jumlah; i++) {
            if(!data[i].isSudahDikembalikan()) count++;
        }
        return count;
    }

    public static Transaksi[] getData() {
        return data;
    }
}