package anggota;

import java.util.Scanner;

import utils.ConsoleUtil;

public class AnggotaService {

    static Anggota[] members = new Anggota[100];
    static int jumlahMember = 0;
    static int idGenerator = 1;

    static Scanner input = new Scanner(System.in);

    /* ------------ HEADER TABLE ----------- */
    static void printHeader(String title) {
        System.out.println("=================================== " + title +" ===================================");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf(
            "%-4s | %-15s | %-13s | %-30s | %-12s%n",
            "ID", "Nama", "No HP", "Alamat", "Tgl Daftar"
        );
        System.out.println("-------------------------------------------------------------------------------------");
    }

    /* ----------- ROW TABLE ----------- */
    
    static void printRow(Anggota a, int total) {
        System.out.printf(
            "%-4d | %-15s | %-13s | %-30s | %-12s%n",
            a.id, a.nama, a.noHp, a.alamat, a.tanggalDaftar
        );
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Total anggota: " + total);
        System.out.println("=====================================================================================");
    }

    /* ------------ One Table ----------- */
    static void printOneTable(Anggota a) {
        System.out.println("--------------------------------------------");
        System.out.println("No         : " + a.id);
        System.out.println("Nama       : " + a.nama);
        System.out.println("No.Hp      : " + a.noHp);
        System.out.println("Alamat     : " + a.alamat);
        System.out.println("Daftar     : " + a.tanggalDaftar);
        System.out.println("--------------------------------------------");
    }

    /* ------------ Public Access Jumlah member ----------- */
    public static int getJumlahAnggota() {
        return jumlahMember;
    }

    /* ---------- TAMBAH ANGGOTA ---------- */
    static void tambahAnggota() {
        ConsoleUtil.clearScreen();
        
        if (jumlahMember >= members.length) {
            System.out.println("Data anggota penuh!");
            return;
        }

        String nama = ConsoleUtil.readStringOptional(
            "Nama",
            "contoh: Ahmad Irfan"
        );
        if (nama == null) return;

        String noHp = ConsoleUtil.readStringOptional(
            "No HP",
            "contoh: 08123456789"
        );
        if (noHp == null) return;

        String alamat = ConsoleUtil.readStringOptional(
            "Alamat",
            "contoh: Asrama Telkom University"
        );
        if (alamat == null) return;

        String tanggalDaftar = ConsoleUtil.readStringOptional(
            "Tanggal Daftar",
            "contoh: 31/12/2025"
        );
        if (tanggalDaftar == null) return;

        members[jumlahMember] = new Anggota(idGenerator++, nama, noHp, alamat, tanggalDaftar);
        jumlahMember++;

        System.out.println("Anggota berhasil ditambahkan!");
        ConsoleUtil.resetBackInput();
    }

    /* ---------- LIHAT SEMUA ANGGOTA ---------- */
    static void lihatAnggota() {
        ConsoleUtil.clearScreen();
        if (jumlahMember == 0) {
            System.out.println("Belum ada anggota.");
            return;
        }

        ConsoleUtil.clearScreen();

        int total = 0;

        printHeader("SEMUA ANGGOTA");
        for (int i = 0; i < jumlahMember; i++) {
            total++;
            printRow(members[i], total);
        }
        ConsoleUtil.resetBackInput();
    }

    /* ---------- GET ANGGOTA BY ID ---------- */
    public static Anggota getAnggotaById(int id) {
        for (int i = 0; i < jumlahMember; i++) {
            if (members[i].id == id) {
                return members[i];
            }
        }
        return null;
    }

    /* ---------- GET TOTAL ANGGOTA BY PERIODE ---------- */
    public static int getTotalAnggotaByPeriode(int hariLaporan) {
        int total = 0;

        for(int i = 0; i < jumlahMember; i++) {
            Anggota a = members[i];

            int hariDaftar = ConsoleUtil.parseHari(a.tanggalDaftar);
            if(hariDaftar == -1) continue;

            if( hariDaftar <= hariLaporan) {
                total++;
            }
        }

        return total;
    }

    /* ------------ CARI ANGGOTA ------------- */
    static void cariAnggota() {
        ConsoleUtil.clearScreen();
        int id = ConsoleUtil.readInt("Masukkan ID: ", 1, 9999);

        Anggota a = getAnggotaById(id);

        if(a == null) {
            System.out.println("Anggota tidak ditemukan.");
        } else {
            ConsoleUtil.clearScreen();
            printOneTable(a);
            ConsoleUtil.resetBackInput();
        }
    }

    /* ---------- UPDATE ANGGOTA ---------- */
    static void updateAnggota() {
        ConsoleUtil.clearScreen();
        System.out.print("Masukkan ID (0 = batal): ");
        int id = Integer.parseInt(input.nextLine());

        if (id == 0) {
            ConsoleUtil.clearScreen();
            return;
        }

        for (int i = 0; i < jumlahMember; i++) {
            if (members[i].id == id) {
                System.out.println("Kosongkan jika tidak ingin mengubah");

                System.out.print("Nama baru (" + members[i].nama + "): ");
                String nama = input.nextLine().trim();
                if (!nama.isEmpty()) members[i].nama = nama;

                System.out.print("No HP baru (" + members[i].noHp + "): ");
                String noHp = input.nextLine().trim();
                if (!noHp.isEmpty()) members[i].noHp = noHp;

                System.out.print("Alamat baru (" + members[i].alamat + "): ");
                String alamat = input.nextLine().trim();
                if (!alamat.isEmpty()) members[i].alamat = alamat;

                System.out.println("Data berhasil di-update.");

                ConsoleUtil.resetBackInput();
                return;
            }
        }
        System.out.println("Anggota tidak ditemukan.");
    }

    /* ---------- HAPUS ANGGOTA ---------- */
    static void hapusAnggota() {
        ConsoleUtil.clearScreen();
        System.out.print("Masukkan ID (0 = batal): ");
        int id = Integer.parseInt(input.nextLine());

        if (id == 0) {
            ConsoleUtil.clearScreen();
            return;
        }

        for (int i = 0; i < jumlahMember; i++) {
            if (members[i].id == id) {
                boolean yakin = ConsoleUtil.confirm(
                    "Yakin ingin menghapus anggota ini?"
                );

                if (!yakin) return;

                for (int j = i; j < jumlahMember - 1; j++) {
                    members[j] = members[j + 1];
                }

                members[jumlahMember - 1] = null;
                jumlahMember--;

                System.out.println("Data berhasil dihapus.");
                return;
            }
        }
        System.out.println("Anggota tidak ditemukan.");
    }
}
