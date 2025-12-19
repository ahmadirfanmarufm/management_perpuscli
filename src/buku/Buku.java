package buku;

public class Buku {
    private int no;
    private String isbn;
    private String judul;
    private String pengarang;
    private String penerbit;
    private int tahun;
    private int eksemplar;
    private int eksemplarAwal;
    private String tanggalMasuk;

    public Buku(int no, String isbn, String judul, String pengarang,
                String penerbit, int tahun, int eksemplar, String tanggalMasuk) {
        this.no = no;
        this.isbn = isbn;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.eksemplar = eksemplar;
        this.eksemplarAwal = eksemplar;
        this.tanggalMasuk = tanggalMasuk;
    }

    public int getNo() {
        return no;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public int getTahun() {
        return tahun;
    }

    public int getEksemplar() {
        return eksemplar;
    }

    public int getEksemplarAwal() {
        return eksemplarAwal;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public void setEksemplar(int eksemplar) {
        this.eksemplar = eksemplar;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }
}
