package transaksi;

public class Transaksi {
    private int no;
    private int idAnggota;
    private String namaAnggota;
    private String isbnBuku;
    private String judulBuku;
    private String tglPinjam;
    private String tglKembali;
    private boolean sudahDikembalikan;

    public Transaksi(int no, int idAnggota, String namaAnggota, String isbnBuku, String judulBuku, String tglPinjam) {
        this.no = no;
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.isbnBuku = isbnBuku;
        this.judulBuku = judulBuku;
        this.tglPinjam = tglPinjam;
        this.tglKembali = "-";
        this.sudahDikembalikan = false;
    }

    public int getNo() { return no; }
    public int getIdAnggota() { return idAnggota; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getIsbnBuku() { return isbnBuku; }
    public String getJudulBuku() { return judulBuku; }
    public String getTglPinjam() { return tglPinjam; }
    public String getTglKembali() { return tglKembali; }
    public boolean isSudahDikembalikan() { return sudahDikembalikan; }

    public void kembalikan(String tglKembali) {
        this.tglKembali = tglKembali;
        this.sudahDikembalikan = true;
    }
}