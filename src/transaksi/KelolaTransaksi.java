package transaksi;

import utils.ConsoleUtil;

public class KelolaTransaksi {
    private TransaksiService service = new TransaksiService();

    public void showMenu() {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.menuTransaksi();

            int pilih = ConsoleUtil.readInt("Pilih (1-4): ", 1, 4);

            switch (pilih) {
                case 1 -> service.pinjamBuku();
                case 2 -> service.kembalikanBuku();
                case 3 -> service.tampilkanAktif();
                case 4 -> {
                    ConsoleUtil.clearScreen();
                    return;
                }
            }
        }
    }
}