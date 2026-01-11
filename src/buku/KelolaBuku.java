package buku;

import utils.ConsoleUtil;

public class KelolaBuku {
    private BukuService service = new BukuService();

    private void liatBuku() {
        service.tampilkanBuku();
    }

    private void cariBuku() {
        service.cariBuku();
    }

    private void tambahBuku() {
        service.tambahBuku();
    }

    private void updateBuku() {
        service.updateBuku();
    }

    private void hapusBuku() {
        service.hapusBuku();
    }

    private void sortingBuku() {
        service.menuSorting();
    }

    public void showMenu() {
        while(true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.menuKelolaBuku();

            int menu = ConsoleUtil.readInt("Pilih (1-7): ", 1, 7);

            switch(menu) {
                case 1 -> liatBuku();
                case 2 -> cariBuku();
                case 3 -> tambahBuku();
                case 4 -> updateBuku();
                case 5 -> hapusBuku();
                case 6 -> sortingBuku();
                case 7 -> {
                    ConsoleUtil.clearScreen();
                    return;
                }
            }
        }
    }
}
