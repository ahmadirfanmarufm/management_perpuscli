package buku;

import utils.ConsoleUtil;

public class BukuService {
    private static Buku[] dataBuku = new Buku[50];
    private static int jumlah = 0;
    private static int autoNo = 1;

    public void tambahBuku() {
        ConsoleUtil.clearScreen();

        System.out.println("Tambah Buku (0 = batal)");

        String isbn = ConsoleUtil.readStringOptional("ISBN", "contoh: 978-602-03");
        if (isbn == null) return;

        String judul = ConsoleUtil.readStringOptional("Judul", "contoh: Algoritma Pemrograman");
        if (judul == null) return;

        String pengarang = ConsoleUtil.readStringOptional("Pengarang", "contoh: Fauzan");
        if (pengarang == null) return;

        String penerbit = ConsoleUtil.readStringOptional("Penerbit", "contoh: Erlangga");
        if (penerbit == null) return;

        int tahun = ConsoleUtil.readInt("Tahun Terbit: ", 1900, 2100);
        int eksemplar = ConsoleUtil.readInt("Jumlah Eksemplar: ", 1, 999);

        String tanggalMasuk = ConsoleUtil.readStringOptional("Tanggal Masuk", "contoh: 31/12/2025, format: dd/mm/yyyy");
        if (tanggalMasuk == null) return;

        if (!ConsoleUtil.confirm("Simpan data buku?")) return;

        if (jumlah >= dataBuku.length) {
            System.out.println("Data buku penuh!");
            return;
        }

        Buku buku = new Buku(autoNo++, isbn, judul, pengarang, penerbit, tahun, eksemplar, tanggalMasuk);
        dataBuku[jumlah] = buku;
        jumlah++;

        System.out.println("Buku berhasil ditambahkan.");
        
        ConsoleUtil.resetBackInput();
    }

    public void tampilkanBuku() {
        ConsoleUtil.clearScreen();
        
        if (jumlah == 0) {
            System.out.println("Data buku kosong!");
            return;
        }

        System.out.println("============================================= SEMUA BUKU =============================================");
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-13s | %-25s | %-15s | %-4s | %-9s | %-10s |\n",
                "No", "ISBN", "Judul", "Pengarang", "Thn", "Eksemplar", "Tgl Masuk");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            System.out.printf("| %-3d | %-13s | %-25s | %-15s | %-4d | %-9d | %-10s |\n",
                    b.getNo(),
                    b.getIsbn(),
                    ConsoleUtil.potong(b.getJudul(), 25),
                    ConsoleUtil.potong(b.getPengarang(), 15),
                    b.getTahun(),
                    b.getEksemplar(),
                    b.getTanggalMasuk());
        }

        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Total buku: " + jumlah);
        System.out.println("======================================================================================================");

        ConsoleUtil.resetBackInput();
    }

    public Buku getBuku(String keyword) {
        for(int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            if(b.getJudul().equalsIgnoreCase(keyword) || b.getPengarang().equalsIgnoreCase(keyword) || b.getIsbn().equalsIgnoreCase(keyword)) {
                return b;
            }
        }
        return null;
    }

    public Buku getBukuByJudulISBN(String keyword) {
        for(int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            if(b.getJudul().equalsIgnoreCase(keyword) || b.getIsbn().equalsIgnoreCase(keyword)) {
                return b;
            }
        }
        return null;
    }

    public static int getJumlahBukuByPeriode(int hariLaporan) {
        int total = 0;

        for (int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];

            int hariDaftar = ConsoleUtil.parseHari(b.getTanggalMasuk());
            if (hariDaftar == -1) continue;

            if (hariDaftar <= hariLaporan) {
                total++;
            }
        }

        return total;
    }

    private Buku[] cariBanyak(String keyword) {
        Buku[] hasil = new Buku[jumlah];
        int count = 0;

        for (int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            if (
                b.getIsbn().equalsIgnoreCase(keyword) ||
                b.getJudul().equalsIgnoreCase(keyword) ||
                b.getPengarang().equalsIgnoreCase(keyword) ||
                b.getPenerbit().equalsIgnoreCase(keyword)
            ) {
                hasil[count++] = b;
            }
        }

        Buku[] finalResult = new Buku[count];
        for (int i = 0; i < count; i++) {
            finalResult[i] = hasil[i];
        }

        return finalResult;
    }

    public void cariBuku() {
        ConsoleUtil.clearScreen();

        String key = ConsoleUtil.readStringOptional(
            "Cari Buku",
            "ISBN / Judul / Pengarang / Penerbit"
        );
        if (key == null) return;

        Buku[] hasil = cariBanyak(key);

        if(hasil.length == 0) {
            System.out.println("Buku tidak ditemukan.");
        } else {
            tampilkanHasilCari(hasil);
        }

        ConsoleUtil.resetBackInput();
    }

    public void updateBuku() {
        ConsoleUtil.clearScreen();

        Buku buku = null;
        String key;

        while(true) {
            key = ConsoleUtil.readStringOptional(
                "Update Buku",
                "Judul / Pengarang"
            );

            if(key == null) return;

            buku = getBuku(key);
            if(buku != null) {
                break;
            }

            ConsoleUtil.clearScreen();
            System.out.println("Buku tidak ditemukan. Silahkan coba lagi!");
        }

        ConsoleUtil.clearScreen();
        System.out.println("Masukkan data baru dan kosongkan jika tidak ingin mengubah");

        String isbn = ConsoleUtil.readStringOptional("ISBN", "tetap / baru");
        if (isbn == null) return;

        String judul = ConsoleUtil.readStringOptional("Judul", "tetap / baru");
        if (judul == null) return;

        String pengarang = ConsoleUtil.readStringOptional("Pengarang", "tetap / baru");
        if (pengarang == null) return;

        String penerbit = ConsoleUtil.readStringOptional("Penerbit", "tetap / baru");
        if (penerbit == null) return;

        String tahunStr = ConsoleUtil.readStringOptional("Tahun Terbit", "Kosongkan jika tetap");
        if (tahunStr == null) return;
        
        String eksemplar = ConsoleUtil.readStringOptional("Jumlah Eksemplar", "Kosong jika tetap");
        if (eksemplar == null) return;

        String tanggalMasuk = ConsoleUtil.readStringOptional("Tanggal Masuk", "Format: dd/mm/yyy, kosong jika tetap");
        if (tanggalMasuk == null) return;

        if (!ConsoleUtil.confirm("Update data buku?")) return;

        for (int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            if (b.getJudul().equalsIgnoreCase(key)
                    || b.getPengarang().equalsIgnoreCase(key)
                    || b.getIsbn().equalsIgnoreCase(key)) {

                if(!isbn.isEmpty()) b.setIsbn(isbn);
                if(!judul.isEmpty()) b.setJudul(judul);
                if(!pengarang.isEmpty()) b.setPengarang(pengarang);
                if(!penerbit.isEmpty()) b.setPenerbit(penerbit);
                if(!tahunStr.isEmpty()) {
                    b.setTahun(Integer.parseInt(tahunStr));
                }
                if(!eksemplar.isEmpty()) {
                    b.setEksemplar(Integer.parseInt(eksemplar));
                }
                if(!tanggalMasuk.isEmpty()) b.setTanggalMasuk(tanggalMasuk);

                System.out.println("Data buku berhasil diupdate.");
                return;
            }
        }
        System.out.println("Buku tidak ditemukan.");

        ConsoleUtil.resetBackInput();
    }

    public void hapusBuku() {
        ConsoleUtil.clearScreen();

        Buku buku = null;
        String key;

        while(true) {
            key = ConsoleUtil.readStringOptional(
                "Hapus Buku",
                "ISBN / Judul"
            );

            if(key == null) return;

            buku = getBukuByJudulISBN(key);
            if(buku != null) {
                break;
            }

            ConsoleUtil.clearScreen();
            System.out.println("Buku tidak ditemukan. Silahkan coba lagi!");
        }

        if (!ConsoleUtil.confirm("Yakin ingin menghapus buku ini?")) return;

        for (int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];
            if (b.getJudul().equalsIgnoreCase(key)
                    || b.getPengarang().equalsIgnoreCase(key)
                    || b.getIsbn().equalsIgnoreCase(key)) {

                for (int j = i; j < jumlah - 1; j++) {
                    dataBuku[j] = dataBuku[j + 1];
                    dataBuku[j].setNo(j + 1);
                }

                dataBuku[jumlah - 1] = null;
                jumlah--;
                autoNo = jumlah + 1;

                System.out.println("Buku berhasil dihapus.");
                return;
            }
        }
        System.out.println("Buku tidak ditemukan.");

        ConsoleUtil.resetBackInput();
    }

    public static int getJumlahBuku() {
        return jumlah;
    }

    public static int getTotalEksemplar() {
        int total = 0;
        for(int i = 0; i < jumlah; i++) {
            total += dataBuku[i].getEksemplar();
        }
        return total;
    }

    public static int getTotalEksemplarAwalByPeriode(int hariLaporan) {
        int total = 0;
        for(int i = 0; i < jumlah; i++) {
            Buku b = dataBuku[i];

            int hariMasuk = ConsoleUtil.parseHari(b.getTanggalMasuk());
            if(hariMasuk == -1) continue;

            if(hariMasuk <= hariLaporan) {
                total += b.getEksemplarAwal();
            }
        }

        return total;
    }

    public boolean kurangiEksemplar(Buku b) {
        if(b.getEksemplar() <= 0) return false;
        b.setEksemplar(b.getEksemplar() - 1);
        return true;
    }

    public void tambahEksemplar(Buku b) {
        b.setEksemplar(b.getEksemplar() + 1);
    }

    private void tampilkanHasilCari(Buku[] list) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-13s | %-25s | %-15s | %-4s | %-9s | %-10s |\n",
                "No", "ISBN", "Judul", "Pengarang", "Thn", "Eksemplar", "Tgl Masuk");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < list.length; i++) {
            Buku b = list[i];
            System.out.printf("| %-3d | %-13s | %-25s | %-15s | %-4d | %-9d | %-10s |\n",
                    b.getNo(),
                    b.getIsbn(),
                    ConsoleUtil.potong(b.getJudul(), 25),
                    ConsoleUtil.potong(b.getPengarang(), 15),
                    b.getTahun(),
                    b.getEksemplar(),
                    b.getTanggalMasuk());
        }

        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Total ditemukan: " + list.length + " buku");
    }
}
