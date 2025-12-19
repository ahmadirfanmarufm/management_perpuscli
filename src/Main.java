import about.About;
import utils.ConsoleUtil;
import anggota.KelolaAnggota;
import buku.KelolaBuku;
import laporan.KelolaLaporan;
import transaksi.KelolaTransaksi;

public class Main {
    public static void menuUtama() {
        while(true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.menuUtama();
            
            int option = ConsoleUtil.readInt("Pilih (1-6): ", 1, 6);
    
            switch(option) {
                case 1 -> masukKelolaBuku();
                case 2 -> masukKelolaAnggota();
                case 3 -> masukPeminjamanPengembalian();
                case 4 -> masukLaporanStatistik();
                case 5 -> About.show();
                case 6 -> {
                    System.out.println("Terima kasih!");
                    System.exit(0);
                }
            }
        }
    }

    public static void masukKelolaBuku() {
        ConsoleUtil.clearScreen();
        KelolaBuku menuBuku = new KelolaBuku();
        menuBuku.showMenu();
    }

    public static void masukKelolaAnggota() {
        ConsoleUtil.clearScreen();
        KelolaAnggota menuAnggota = new KelolaAnggota();
        menuAnggota.showMenu();
    }

    public static void masukPeminjamanPengembalian() {
        ConsoleUtil.clearScreen();
        KelolaTransaksi menuTransaksi = new KelolaTransaksi();
        menuTransaksi.showMenu();
    }

    public static void masukLaporanStatistik() {
        ConsoleUtil.clearScreen();
        KelolaLaporan laporan = new KelolaLaporan();
        laporan.showMenu();
    }

    public static void main(String[] args) {
        menuUtama();
    }
}