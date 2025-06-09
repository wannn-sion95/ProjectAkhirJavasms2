package KodeProgram;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TampilanProgram {
    public static final Color PRIMARY_COLOR = new Color(44, 95, 182);
    public static final Color SECONDARY_COLOR = new Color(202, 208, 227);
    public static final Color BACKGROUND_COLOR = new Color(244, 246, 250);
    public static final Color SURFACE_COLOR = new Color(251, 251, 251, 255);
    public static final Color TEXT_COLOR = new Color(66, 66, 66);
    public static final Color ACCENT_COLOR = new Color(44, 95, 182);
    public static final Color ERROR_COLOR = new Color(181, 29, 29);
    public static final Color SUCCESS_COLOR = new Color(46, 125, 50);

    private PensBook app;

    public TampilanProgram(PensBook app) {
        this.app = app;
    }

    public JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridBagLayout());

        JPanel card = createModernCard(450, 550);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("PENS BOOK");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        card.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Masuk ke akun Anda untuk melanjutkan");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(156, 163, 175));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        card.add(subtitleLabel, gbc);

        JTextField usernameField = createModernTextField("Username");
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 30, 20, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(usernameField, gbc);

        JPasswordField passwordField = createModernPasswordField("Password");
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 30, 30, 30);
        card.add(passwordField, gbc);

        JButton loginButton = createModernButton("Masuk", PRIMARY_COLOR);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 20, 30);
        card.add(loginButton, gbc);

        JButton registerLink = createLinkButton("Belum punya akun? Daftar di sini");
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 30, 20, 30);
        card.add(registerLink, gbc);

        ActionListener loginAction = _ -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                NotifPembuatanAkun("Mohon isi semua data", ERROR_COLOR);
                return;
            }

            if (app.getUsers().containsKey(username) && app.getUsers().get(username).equals(password)) {
                app.setCurrentUser(username);
                app.showDashboard();
            } else {
                NotifPembuatanAkun("Username atau password salah", ERROR_COLOR);
            }
        };

        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        registerLink.addActionListener(_ -> app.getCardLayout().show(app.getPanelUtama(), "REGISTER"));

        panel.add(card);
        return panel;
    }

    public JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridBagLayout());

        JPanel card = createModernCard(450, 650);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Buat Akun Baru");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(20, 0, 20, 0);
        card.add(titleLabel, gbc);

        JTextField usernameField = createModernTextField("Username");
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 30, 15, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(usernameField, gbc);

        JPasswordField passwordField = createModernPasswordField("Password");
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 30, 15, 30);
        card.add(passwordField, gbc);

        JPasswordField confirmPasswordField = createModernPasswordField("Confirm Password");
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 25, 30);
        card.add(confirmPasswordField, gbc);

        JButton registerButton = createModernButton("Daftar akun", ACCENT_COLOR);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 30, 15, 30);
        card.add(registerButton, gbc);

        JButton loginLink = createLinkButton("Udah punya akun? Masuk di sini");
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 30, 20, 30);
        card.add(loginLink, gbc);

        registerButton.addActionListener(_ -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                NotifPembuatanAkun("Mohon isi semua data", ERROR_COLOR);
                return;
            }

            if (!password.equals(confirmPassword)) {
                NotifPembuatanAkun("Passwords do not match", ERROR_COLOR);
                return;
            }

            if (app.getUsers().containsKey(username)) {
                NotifPembuatanAkun("Username sudah ada yang punya, pake yang lain", ERROR_COLOR);
                return;
            }

            app.getUsers().put(username, password);
            NotifPembuatanAkun("Akun berhasil dibuat!", SUCCESS_COLOR);
            app.getCardLayout().show(app.getPanelUtama(), "LOGIN");
        });

        loginLink.addActionListener(_ -> app.getCardLayout().show(app.getPanelUtama(), "LOGIN"));

        panel.add(card);
        return panel;
    }

    public JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JPanel header = createHeader();
        panel.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND_COLOR);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(BACKGROUND_COLOR);

        JLabel welcomeLabel = new JLabel("Selamat datang, " + app.getCurrentUser());
        welcomeLabel.setFont(new Font("Sego UI", Font.BOLD, 24));
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomePanel.add(welcomeLabel);

        content.add(welcomePanel, BorderLayout.NORTH);

        JPanel booksGrid = createBooksGrid();
        JScrollPane scrollPane = new JScrollPane(booksGrid);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        styleScrollBar(scrollPane);

        content.add(scrollPane, BorderLayout.CENTER);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SURFACE_COLOR);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(SURFACE_COLOR);

        JLabel titleLabel = new JLabel("PENS BOOK");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);
        leftPanel.add(titleLabel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(SURFACE_COLOR);

        JLabel userLabel = new JLabel(app.getCurrentUser());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(TEXT_COLOR);

        JButton logoutButton = createModernButton("Logout", ERROR_COLOR);
        logoutButton.setPreferredSize(new Dimension(80, 35));
        logoutButton.addActionListener(_ -> {
            app.setCurrentUser("");
            app.getCardLayout().show(app.getPanelUtama(), "LOGIN");
        });

        rightPanel.add(userLabel);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(logoutButton);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createBooksGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(BACKGROUND_COLOR);
        grid.setBorder(new EmptyBorder(20, 0, 20, 0));

        for (Buku book : app.getBukubuku()) {
            JPanel bookCard = createBookCard(book);
            grid.add(bookCard);
        }

        return grid;
    }

    private JPanel createBookCard(Buku book) {
        JPanel card = createModernCard(350, 300);
        card.setLayout(new BorderLayout());
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(SURFACE_COLOR);
        infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(book.GambarBuku));
            Image img = icon.getImage().getScaledInstance(110, 130, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("Gagal load gambar: " + book.GambarBuku);
        }

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(book.judul);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLabel = new JLabel("Penulis: " + book.penulis);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        authorLabel.setForeground(new Color(156, 163, 175));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genreLabel = new JLabel(book.genre);
        genreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        genreLabel.setForeground(PRIMARY_COLOR);
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        infoPanel.add(iconLabel);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(genreLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(SURFACE_COLOR);

        JButton readButton = createModernButton("Baca buku", PRIMARY_COLOR);
        readButton.setPreferredSize(new Dimension(120, 35));
        readButton.addActionListener(_ -> openBookReader(book));

        buttonPanel.add(readButton);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(null);
                card.repaint();
            }
        });

        return card;
    }

    private void openBookReader(Buku book) {
        JFrame readerFrame = new JFrame("Membaca: " + book.judul);
        readerFrame.setSize(1000, 700);
        readerFrame.setLocationRelativeTo(app.getTampilanHalaman());
        readerFrame.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel readerPanel = new JPanel(new BorderLayout());
        readerPanel.setBackground(BACKGROUND_COLOR);

        JPanel readerHeader = new JPanel(new BorderLayout());
        readerHeader.setBackground(SURFACE_COLOR);
        readerHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel bookTitleLabel = new JLabel(book.judul + " - " + book.penulis);
        bookTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bookTitleLabel.setForeground(TEXT_COLOR);

        JButton closeButton = createModernButton("Tutup", ERROR_COLOR);
        closeButton.addActionListener(_ -> readerFrame.dispose());

        readerHeader.add(bookTitleLabel, BorderLayout.WEST);
        readerHeader.add(closeButton, BorderLayout.EAST);

        JTextArea contentArea = new JTextArea(book.content);
        contentArea.setFont(new Font("Georgia", Font.PLAIN, 16));
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setBackground(BACKGROUND_COLOR);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBorder(new EmptyBorder(30, 40, 30, 40));

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBorder(null);
        contentScroll.setBackground(BACKGROUND_COLOR);
        contentScroll.getViewport().setBackground(BACKGROUND_COLOR);
        styleScrollBar(contentScroll);

        readerPanel.add(readerHeader, BorderLayout.NORTH);
        readerPanel.add(contentScroll, BorderLayout.CENTER);

        readerFrame.add(readerPanel);
        readerFrame.setVisible(true);
    }

    public JPanel createModernCard(int width, int height) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();
            }
        };
        card.setPreferredSize(new Dimension(width, height));
        card.setBackground(SURFACE_COLOR);
        card.setOpaque(false);
        return card;
    }

    public JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                super.paintComponent(g);
                g2.dispose();
            }
        };

        field.setPreferredSize(new Dimension(300, 45));
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(10, 15, 10, 15));
        field.setOpaque(false);
        field.setText(placeholder);
        field.setForeground(new Color(156, 163, 175));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(156, 163, 175));
                }
            }
        });

        return field;
    }

    public JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                super.paintComponent(g);
                g2.dispose();
            }
        };

        field.setPreferredSize(new Dimension(300, 45));
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(10, 15, 10, 15));
        field.setOpaque(false);
        field.setEchoChar('•');

        return field;
    }

    public JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                g2.drawString(getText(),
                        (getWidth() - textWidth) / 2,
                        (getHeight() + textHeight) / 2 - 2);

                g2.dispose();
            }
        };

        button.setPreferredSize(new Dimension(300, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(PRIMARY_COLOR);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(PRIMARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void styleScrollBar(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = SECONDARY_COLOR;
                this.trackColor = BACKGROUND_COLOR;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });
    }

    private void NotifPembuatanAkun(String message, Color color) {
        JDialog dialog = new JDialog(app.getTampilanHalaman(), true);
        dialog.setUndecorated(true);
        dialog.setSize(270, 80);
        dialog.setLocationRelativeTo(app.getTampilanHalaman());

        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setHorizontalAlignment(JLabel.CENTER);

        panel.add(label);
        dialog.add(panel);

        Timer timer = new Timer(1000, _ -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }
}