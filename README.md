# 📚 Sistem Manajemen Perpustakaan (Console Java)

Aplikasi **Sistem Manajemen Perpustakaan berbasis Java (Console)**  
Digunakan untuk mengelola **data buku, anggota, transaksi peminjaman & pengembalian**, serta **laporan statistik**.

Proyek ini dibuat sebagai **tugas kolaborasi tim** dan bertujuan untuk melatih:
- Pemrograman Java OOP
- Struktur data array
- Modularisasi program
- Validasi input pengguna
- Kerja tim (collaboration)

---

## 👥 Tim Pengembang
- **Ahmad Irfan Ma'ruf Maulana** — Main Menu, Transaksi, Laporan
- **Muhammad Faiq Muslih** — Modul Kelola Buku
- **Alifinzie Ruhul Ihsan** — Modul Kelola Anggota

---

## ✨ Fitur Utama

### 📘 Kelola Buku
- Tambah buku
- Lihat semua buku
- Cari buku (ISBN / Judul / Pengarang / Penerbit)
- Update data buku
- Hapus buku
- Sorting buu
- Manajemen stok eksemplar

### 👤 Kelola Anggota
- Tambah anggota
- Lihat semua anggota
- Cari anggota berdasarkan ID
- Update data anggota
- Hapus anggota
- Sorting Anggota

### 🔄 Transaksi
- Peminjaman buku
- Pengembalian buku
- Validasi stok buku
- Sorting
- Perhitungan denda otomatis (jika terlambat)
- Menampilkan daftar peminjaman aktif

### 📊 Laporan
- Laporan statistik perpustakaan
- Total buku & eksemplar per periode
- Total anggota per periode
- Total transaksi
- Laporan peminjaman terlambat + denda

### 🛠 Utility
- Validasi tanggal (`dd/mm/yyyy`)
- Pesan error yang jelas & user-friendly
- Tampilan tabel rapi di console
- Warna teks (error & sukses (~Soon))

---


## ⚙️ Cara Menjalankan Program

### 1️⃣ Persiapan
Pastikan sudah terinstall:
- **Java JDK 17+**
- Terminal / Command Prompt

Cek:
```bash
java -version
```
Disarankan menggunakan **Java JDK 17 atau lebih baru**

### 2️⃣ Compile Program
```bash
javac Main.java
```

### 3️⃣ Jalankan Program
```bash
java Main
```