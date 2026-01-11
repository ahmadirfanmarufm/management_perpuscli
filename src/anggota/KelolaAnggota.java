package anggota;

import java.util.Scanner;

import utils.ConsoleUtil;

public class KelolaAnggota {

    public void showMenu() {
        while(true) {
            ConsoleUtil.menuKelolaAnggota();

            int pilihan = ConsoleUtil.readInt("Pilih (1-7): ", 1, 7);

            switch (pilihan) {
                case 1: AnggotaService.lihatAnggota(); break;
                case 2: AnggotaService.cariAnggota(); break;
                case 3: AnggotaService.tambahAnggota(); break;
                case 4: AnggotaService.updateAnggota(); break;
                case 5: AnggotaService.hapusAnggota(); break;
                case 6: AnggotaService.menuSorting(); break;
                case 7: 
                    ConsoleUtil.clearScreen();
                    return;
            }

        }
    }

    static Scanner input = new Scanner(System.in);
}
