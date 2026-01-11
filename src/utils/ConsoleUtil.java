package utils;

import java.util.Scanner;
import static utils.Constant.*;

public class ConsoleUtil {
    private static Scanner input = new Scanner(System.in);

    /* ---------- HELPER UI ---------- */
    public static void clearScreen() {
        try {
            if(System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception err) {
            System.out.println("Gagal clear screen!");
        }
    }

    public static void menuUtama() {
        out("============================================");
        out("        SISTEM MANAJEMEN PERPUSTAKAAN       ");
        out("============================================");
        out("1. Kelola Buku");
        out("2. Kelola Anggota");
        out("3. Peminjaman & Pengembalian");
        out("4. Laporan Statistik");
        out("5. Tentang Aplikasi");
        out("6. Keluar");
        out("============================================");
    }

    public static void menuKelolaAnggota() {
        out("============================================");
        out("                KELOLA ANGGOTA              ");
        out("============================================");
        out("1. Lihat Semua Anggota");
        out("2. Cari Anggota (ID)");
        out("3. Tambah Anggota");
        out("4. Update Anggota");
        out("5. Hapus Anggota");
        out("6. Urutkan Anggota");
        out("7. Kembali");
        out("============================================");
    }

    public static void menuKelolaBuku() {
        out("============================================");
        out("                 KELOLA BUKU                ");
        out("============================================");
        out("1. Lihat Semua Buku");
        out("2. Cari Buku");
        out("3. Tambah Buku");
        out("4. Update Buku");
        out("5. Hapus Buku");
        out("6. Sorting Buku");
        out("7. Kembali");
        out("============================================");
    }

    public static void menuTransaksi() {
        out("====================================");
        out("   PEMINJAMAN & PENGEMBALIAN BUKU   ");
        out("====================================");
        out("1. Pinjam Buku");
        out("2. Kembalikan Buku");
        out("3. Lihat Peminjaman Aktif");
        out("4. Kembali");
        out("====================================");
    }

    public static void menuLaporan() {
        out("=================================");
        out("        LAPORAN STATISTIK         ");
        out("=================================");
        out("1. Laporan Perpustakaan");
        out("2. Laporan Pengembalian Terlambat");
        out("3. Kembali");
        out("=================================");
    }

    public static void menuAbout() {
        out("============================================");
        out("               TENTANG APLIKASI             ");
        out("============================================");
        out("Nama Aplikasi : Sistem Manajemen Perpus");
        out("Versi         : 1.0");
        out("Dibuat oleh   :");
        out(" - Ahmad Irfan (Main Menu, Peminjaman, Laporan)");
        out(" - Faiq        (Kelola Buku)");
        out(" - Alief       (Kelola Anggota)");
        out("Deskripsi     :");
        out("Aplikasi ini digunakan untuk mengelola data buku,");
        out("data anggota, proses peminjaman, pengembalian,");
        out("serta menampilkan laporan statistik.");
        out("============================================");
    }
    /* -------------------------------- */

    /* ---------- INPUT UTIL ---------- */
    public static void resetBackInput() {
        out("\n0. Kembali ke menu sebelumnya ");

        while(true) {
            outn("Masukkan pilihan: ");
            String option = input.nextLine().trim();
            
            if(option.equals("0")) {
                clearScreen();
                return;
            }
            error("Input salah! Masukkan 0 untuk kembali.");
        }
    }

    public static int readInt(String label, int min, int max) {
        while (true) {
            outn(label);
            try {
                int x = Integer.parseInt(input.nextLine().trim());
                if(x < min || x > max) throw new NumberFormatException("");
                return x;
            } catch (NumberFormatException e) {
                if(min == max) {
                    error("Hanya bisa masukkan angka " + max);
                } else {
                    error("Masukkan angka " + min + "-" + max);
                }
            }
        }
    }

    public static String readStringOptional(String label, String placeholder) {
        out(label + " (" + placeholder + ", 0 = batal): ");
        String value = input.nextLine().trim();

        if (value.equals("0")) {
            clearScreen();
            return null;
        }
        return value;
    }

    public static boolean confirm(String pesan) {
        out(pesan + " (y/n): ");
        String res = input.nextLine().trim().toLowerCase();
        clearScreen();
        return res.equals("y");
    }

    
    public static int parseHari(String tgl) {
        String[] split = tgl.split("/");
        if(split.length != 3) {
            error("Format tanggal salah! Gunakan format dd/mm/yyyy");
            return -1;
        }

        int hari, bulan, tahun;

        try {
            hari = Integer.parseInt(split[0].trim());
        } catch(NumberFormatException e) {
            error("Hari harus berupa angka (1-31)");
            return -1;
        }

        if (hari < 1 || hari > 31) {
            error("Hari tidak valid! Masukkan angka 1 - 31");
            return -1;
        }

        try {
            bulan = Integer.parseInt(split[1].trim());
        } catch (NumberFormatException e) {
            error("Bulan harus berupa angka (1 - 12)");
            return -1;
        }

        if (bulan < 1 || bulan > 12) {
            error("Bulan tidak valid! Masukkan angka 1 - 12");
            return -1;
        }

        try {
            tahun = Integer.parseInt(split[2].trim());
        } catch (NumberFormatException e) {
            error("Tahun harus berupa angka (1900 - 2100)");
            return -1;
        }

        if (tahun < 1900 || tahun > 2100) {
            error("Tahun tidak valid! Masukkan tahun 1900 - 2100");
            return -1;
        }

        return tahun * 365 + bulan * 30 + hari;
    }

    public static String potong(String teks, int max) {
        if (teks == null) return "-";
        return teks.length() <= max ? teks : teks.substring(0, max - 3) + "...";
    }
    /* -------------------------------- */

    /* ---------- SHORTCUT OUTPUT ---------- */
    private static void out(Object s) { System.out.println(s); }
    private static void outn(Object s) { System.out.print(s); }
    private static void error(String s) { out(RED_COLOR + s + RESET); }
    // private static void success(String s) { out(GREEN_COLOR + s + RESET); }
    /* -------------------------------- */
}