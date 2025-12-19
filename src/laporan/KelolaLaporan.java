package laporan;

import utils.ConsoleUtil;

public class KelolaLaporan {
    public void showMenu() {
        while(true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.menuLaporan();

            int pilih = ConsoleUtil.readInt("Pilih (1-3): ", 1, 3);

            switch(pilih) {
                case 1 -> LaporanService.laporanPerpustakaan();
                case 2 -> LaporanService.laporanTerlambat();
                case 3 -> {
                    ConsoleUtil.clearScreen();
                    return;
                }
            }
        }
    }
}
