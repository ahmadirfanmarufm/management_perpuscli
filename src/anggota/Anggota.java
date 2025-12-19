package anggota;

public class Anggota {
    int id;
    String nama;
    String noHp;
    String alamat;
    String tanggalDaftar;

    public Anggota(int id, String nama, String noHp, String alamat, String tanggalDaftar) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getNamaAnggota() {
        return nama;
    }
}
