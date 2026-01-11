package transaksi;

import anggota.Anggota;
import anggota.AnggotaService;
import buku.Buku;
import buku.BukuService;
import utils.ConsoleUtil;
import utils.Constant;

public class TransaksiService {

    private static final int MAX_DATA = 100;

    private static Transaksi[] data = new Transaksi[MAX_DATA];
    private static Transaksi[] originalData = new Transaksi[MAX_DATA];
    private static int jumlah = 0;
    private static int autoNo = 1;

    private static String lastSortKey = "";
    private static boolean ascending = true;
    private static boolean isOriginalSaved = false;

    private BukuService bukuService = new BukuService();

    /* ======================================================
       PEMINJAMAN BUKU
    ====================================================== */
    public void pinjamBuku() {
        ConsoleUtil.clearScreen();
        System.out.println("======= PEMINJAMAN BUKU =======");

        int idAnggota = ConsoleUtil.readInt("ID Anggota: ", 1, 9999);
        Anggota anggota = AnggotaService.getAnggotaById(idAnggota);

        if (anggota == null) {
            System.out.println("Anggota belum terdaftar.");
            ConsoleUtil.resetBackInput();
            return;
        }

        String isbn = ConsoleUtil.readStringOptional("ISBN Buku", "contoh: 978-602");
        if (isbn == null) return;

        Buku buku = bukuService.getBuku(isbn);
        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            ConsoleUtil.resetBackInput();
            return;
        }

        if (buku.getEksemplar() <= 0) {
            System.out.println("Stok buku habis!");
            ConsoleUtil.resetBackInput();
            return;
        }

        String tglPinjam = ConsoleUtil.readStringOptional(
                "Tanggal Pinjam (dd/mm/yyyy)",
                "contoh: 31/12/2025"
        );
        if (tglPinjam == null) return;

        if (jumlah >= MAX_DATA) {
            System.out.println("Data transaksi penuh!");
            ConsoleUtil.resetBackInput();
            return;
        }

        bukuService.kurangiEksemplar(buku);

        data[jumlah++] = new Transaksi(
                autoNo++,
                idAnggota,
                anggota.getNamaAnggota(),
                isbn,
                buku.getJudul(),
                tglPinjam
        );

        System.out.println("Peminjaman berhasil dicatat.");
        ConsoleUtil.resetBackInput();
    }

    /* ======================================================
       PENGEMBALIAN BUKU
    ====================================================== */
    public void kembalikanBuku() {
        ConsoleUtil.clearScreen();
        System.out.println("======= PENGEMBALIAN BUKU =======");

        int no = ConsoleUtil.readInt("No Transaksi: ", 1, autoNo);

        for (int i = 0; i < jumlah; i++) {
            Transaksi t = data[i];

            if (t.getNo() == no && !t.isSudahDikembalikan()) {

                Buku buku = bukuService.getBuku(t.getIsbnBuku());
                if (buku != null) bukuService.tambahEksemplar(buku);

                String tglKembali = ConsoleUtil.readStringOptional(
                        "Tanggal Pengembalian (dd/mm/yyyy)",
                        "contoh: 31/12/2025"
                );
                if (tglKembali == null) return;

                int hariPinjam = parseHariAman(t.getTglPinjam());
                int hariKembali = parseHariAman(tglKembali);
                if (hariPinjam == -1 || hariKembali == -1) return;

                int selisih = hariKembali - hariPinjam;

                if (selisih > 7) {
                    int terlambat = selisih - 7;
                    long denda = terlambat * Constant.DENDA_PER_HARI;
                    System.out.println(
                            Constant.RED_COLOR + "Terlambat! Denda Rp. " + denda + Constant.RESET
                    );
                } else {
                    System.out.println(
                            Constant.GREEN_COLOR + "Dikembalikan tepat waktu." + Constant.RESET
                    );
                }

                t.kembalikan(tglKembali);
                ConsoleUtil.resetBackInput();
                return;
            }
        }

        System.out.println("Transaksi tidak ditemukan / sudah dikembalikan.");
        ConsoleUtil.resetBackInput();
    }

    /* ======================================================
       TAMPILKAN PEMINJAMAN AKTIF
    ====================================================== */
    public void tampilkanAktif() {
        ConsoleUtil.clearScreen();
        System.out.println("====================================== PEMINJAMAN AKTIF ========================================");

        System.out.printf(
                "| %-2s | %-10s | %-16s | %-13s | %-25s | %-11s |\n",
                "No", "ID Anggota", "Nama Anggota", "ISBN Buku", "Judul Buku", "Tgl Pinjam"
        );
        System.out.println("------------------------------------------------------------------------------------------------");

        int total = 0;

        for (int i = 0; i < jumlah; i++) {
            Transaksi t = data[i];
            if (!t.isSudahDikembalikan()) {
                System.out.printf(
                        "| %-2d | %-10d | %-16s | %-13s | %-25s | %-11s |\n",
                        t.getNo(),
                        t.getIdAnggota(),
                        ConsoleUtil.potong(t.getNamaAnggota(), 16),
                        t.getIsbnBuku(),
                        ConsoleUtil.potong(t.getJudulBuku(), 25),
                        t.getTglPinjam()
                );
                total++;
            }
        }

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Total peminjaman aktif: " + total);
        System.out.println("================================================================================================");
        System.out.println("1. Sorting");
        System.out.println("2. Kembali");

        int option = ConsoleUtil.readInt("Masukkan pilihan: ", 1, 2);
        if (option == 1) {
            sortingPeminjamanAktif();
            tampilkanAktif();
        }
    }

    /* ======================================================
       SORTING
    ====================================================== */
    public void sortingPeminjamanAktif() {
        String key = ConsoleUtil.readStringOptional(
                "Sorting (No / ID Anggota / Nama Anggota / ISBN Buku / Judul Buku / Tanggal Pinjam / Reset)",
                "Contoh: Nama Anggota"
        );
        if (key == null) return;

        if (key.equalsIgnoreCase("reset")) {
            resetSorting();
            System.out.println("Sorting berhasil di-reset!");
            return;
        }

        saveOriginalData();

        if (key.equalsIgnoreCase(lastSortKey)) {
            ascending = !ascending;
        } else {
            ascending = true;
            lastSortKey = key;
        }

        bubbleSortAktif(key);

        System.out.println(
                "Sorting " + (ascending ? "Ascending" : "Descending") +
                        " berdasarkan " + lastSortKey
        );
    }

    private void bubbleSortAktif(String key) {
        for (int i = 0; i < jumlah - 1; i++) {
            for (int j = 0; j < jumlah - 1 - i; j++) {

                Transaksi a = data[j];
                Transaksi b = data[j + 1];

                if (a.isSudahDikembalikan() || b.isSudahDikembalikan()) continue;

                int cmp = compareTransaksi(a, b, key);

                if ((ascending && cmp > 0) || (!ascending && cmp < 0)) {
                    Transaksi temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    private int compareTransaksi(Transaksi a, Transaksi b, String key) {
        switch (key.toLowerCase()) {
            case "no":
                return Integer.compare(a.getNo(), b.getNo());
            case "id":
            case "id anggota":
                return Integer.compare(a.getIdAnggota(), b.getIdAnggota());
            case "nama":
            case "nama anggota":
                return a.getNamaAnggota().compareToIgnoreCase(b.getNamaAnggota());
            case "isbn":
            case "isbn buku":
                return a.getIsbnBuku().compareToIgnoreCase(b.getIsbnBuku());
            case "judul":
            case "judul buku":
                return a.getJudulBuku().compareToIgnoreCase(b.getJudulBuku());
            case "tanggal":
            case "tanggal pinjam":
                return Integer.compare(
                        parseHariAman(a.getTglPinjam()),
                        parseHariAman(b.getTglPinjam())
                );
            default:
                return 0;
        }
    }

    /* ======================================================
       UTIL SORTING
    ====================================================== */
    private void saveOriginalData() {
        if (isOriginalSaved) return;
        for (int i = 0; i < jumlah; i++) {
            originalData[i] = data[i];
        }
        isOriginalSaved = true;
    }

    private void resetSorting() {
        if (!isOriginalSaved) return;
        for (int i = 0; i < jumlah; i++) {
            data[i] = originalData[i];
        }
        lastSortKey = "";
        ascending = true;
    }

    private int parseHariAman(String tanggal) {
        int hari = ConsoleUtil.parseHari(tanggal);
        if (hari == -1) {
            System.out.println("Format tanggal salah!");
        }
        return hari;
    }

    /* ======================================================
       GETTER STATISTIK
    ====================================================== */
    public static int getJumlahTransaksi() {
        return jumlah;
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

    public static int getJumlahAktif() {
        int count = 0;
        for (int i = 0; i < jumlah; i++) {
            if (!data[i].isSudahDikembalikan()) count++;
        }
        return count;
    }

    public static Transaksi[] getData() {
        return data;
    }
}
