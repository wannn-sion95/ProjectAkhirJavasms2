package KodeProgram;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PensBook {
    private JFrame TampilanHalaman;
    private CardLayout cardLayout;
    private JPanel PanelUtama;
    private Map<String, String> users = new HashMap<>();
    private String currentUser = "";
    private List<Buku> Bukubuku = new ArrayList<>();
    private TampilanProgram KomponenUI;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new PensBook().createAndShowGUI());
    }

    private void createAndShowGUI() {
        DaftarBuku.initializeData(Bukubuku);
        KomponenUI = new TampilanProgram(this);

        TampilanHalaman = new JFrame("PENS BOOK");
        TampilanHalaman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TampilanHalaman.setSize(900, 700);
        TampilanHalaman.setMinimumSize(new Dimension(800, 600));
        TampilanHalaman.setLocationRelativeTo(null);
        TampilanHalaman.getContentPane().setBackground(TampilanProgram.BACKGROUND_COLOR);

        cardLayout = new CardLayout();
        PanelUtama = new JPanel(cardLayout);
        PanelUtama.setBackground(TampilanProgram.BACKGROUND_COLOR);

        // Membuat panel utamanya
        PanelUtama.add(KomponenUI.createLoginPanel(), "LOGIN");
        PanelUtama.add(KomponenUI.createRegisterPanel(), "REGISTER");

        TampilanHalaman.add(PanelUtama);
        TampilanHalaman.setVisible(true);
    }

    public void showDashboard() {
        JPanel dashboard = KomponenUI.createDashboardPanel();
        PanelUtama.add(dashboard, "Halaman Utama");
        cardLayout.show(PanelUtama, "Halaman Utama");
    }

    public void setCurrentUser(String user) {
        this.currentUser = user;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public List<Buku> getBukubuku() {
        return Bukubuku;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getPanelUtama() {
        return PanelUtama;
    }

    public JFrame getTampilanHalaman() {
        return TampilanHalaman;
    }
}