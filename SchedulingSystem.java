import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class YouTubeUniversitySystem extends JFrame {

    // --- GUI Colors & Styles ---
    static final Color YT_RED      = new Color(225, 30, 25);
    static final Color BG_WHITE    = Color.WHITE;
    static final Color CARD_GRAY   = new Color(235, 235, 235);
    static final Color REFRESH_GN  = new Color(110, 235, 131);
    static final Color TEXT_DARK   = new Color(30, 30, 30);
    static final Color BLUE_LINK   = new Color(0, 80, 200);

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // Dynamic Data Models
    DefaultTableModel preEnlistModel;
    DefaultTableModel scheduleModel;
    DefaultTableModel enrolledModel;
    DefaultTableModel finalScheduleModel; // Added for the screenshot layout

    // Data Repositories
    Map<String, List<Object[]>> subjectSections = new HashMap<>();

    public YouTubeUniversitySystem() {
        seedInitialData();
        setTitle("YouTube University – Enrollment System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        mainPanel.add(buildRoleSelection(), "ROLES");
        mainPanel.add(buildLoginScreen(),    "LOGIN");
        mainPanel.add(buildEnrollment(),     "ENROLLMENT");
        mainPanel.add(buildOfficialScheduleScreen(), "OFFICIAL_SCHEDULE");

        add(mainPanel);
        cardLayout.show(mainPanel, "ROLES");
        setVisible(true);
    }

    private void seedInitialData() {
        List<Object[]> nen1Schedules = new ArrayList<>();
        nen1Schedules.add(new Object[]{"SUB1", "SECTION A", "WF 09:00 AM - 12:00 PM", "Heavens Arena(Floor 200)", "Master Wing"});
        subjectSections.put("NEN1", nen1Schedules);

        List<Object[]> haki1Schedules = new ArrayList<>();
        haki1Schedules.add(new Object[]{"SUB2", "SECTION A", "TTh 01:00 PM - 04:00 PM", "Rusukaina Island", "Silvers Rayleigh"});
        subjectSections.put("HAKI1", haki1Schedules);

        List<Object[]> ba101Schedules = new ArrayList<>();
        ba101Schedules.add(new Object[]{"SUB3", "SECTION A", "Sat 07:00 PM - 10:00 PM", "Anti-Selos Headquarters(Room 404)", "Jak Roberto"});
        subjectSections.put("BA 101", ba101Schedules);
    }

    private JPanel buildRoleSelection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);
        String[] roles = {"STUDENT", "PROFESSOR", "ADMIN"};
        JPanel container = new JPanel(new GridLayout(1, 3, 20, 0));
        container.setBackground(BG_WHITE);

        for (String r : roles) {
            JButton b = new JButton(r);
            b.setPreferredSize(new Dimension(180, 180));
            b.setFont(new Font("Arial Black", Font.BOLD, 14));
            b.setBackground(CARD_GRAY);
            b.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
            container.add(b);
        }
        panel.add(container);
        return panel;
    }

    private JPanel buildLoginScreen() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_WHITE);

        JPanel topBar = new JPanel();
        topBar.setBackground(YT_RED);
        topBar.setPreferredSize(new Dimension(1200, 30));
        root.add(topBar, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_WHITE);

        JPanel logoSide = new JPanel();
        logoSide.setLayout(new BoxLayout(logoSide, BoxLayout.Y_AXIS));
        logoSide.setBackground(BG_WHITE);
        
        JLabel title1 = new JLabel("YOUTUBE");
        title1.setFont(new Font("Arial Black", Font.BOLD, 45));
        JLabel title2 = new JLabel("UNIVERSITY");
        title2.setFont(new Font("Arial Black", Font.BOLD, 45));
        title2.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, TEXT_DARK));
        
        JLabel tagline = new JLabel("A truly legendary university");
        tagline.setFont(new Font("Arial", Font.ITALIC, 16));
        tagline.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        logoSide.add(title1); logoSide.add(title2); logoSide.add(tagline);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(350, 400));
        card.setBackground(CARD_GRAY);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(35, 35, 35, 35)
        ));

        JLabel lHeader = new JLabel("LOG IN");
        lHeader.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 24));
        lHeader.setAlignmentX(CENTER_ALIGNMENT);

        JTextField idF = new JTextField();
        idF.setMaximumSize(new Dimension(300, 35));
        JPasswordField pwF = new JPasswordField();
        pwF.setMaximumSize(new Dimension(300, 35));
        
        JButton loginB = new JButton("SIGN IN");
        loginB.setAlignmentX(CENTER_ALIGNMENT);
        loginB.setPreferredSize(new Dimension(140, 35));
        loginB.addActionListener(e -> cardLayout.show(mainPanel, "ENROLLMENT"));

        card.add(lHeader); card.add(Box.createVerticalStrut(30));
        card.add(new JLabel("ID NUMBER")); card.add(idF);
        card.add(Box.createVerticalStrut(15));
        card.add(new JLabel("PASSWORD")); card.add(pwF);
        card.add(Box.createVerticalStrut(35));
        card.add(loginB);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(0, 40, 0, 40);
        center.add(logoSide, g); center.add(card, g);
        root.add(center, BorderLayout.CENTER);
        return root;
    }

    // ────────────────────────────────────────────────────────────────
    // SCREEN 3: ENROLLMENT PORTAL
    // ────────────────────────────────────────────────────────────────
    private JPanel buildEnrollment() {
        JPanel root = new JPanel(new BorderLayout());
        
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(YT_RED);
        JLabel l = new JLabel("▶  YOUTUBE UNIVERSITY");
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial Black", Font.BOLD, 42));
        header.add(l);
        root.add(header, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(BG_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel topInfo = new JPanel(new GridLayout(1, 2));
        topInfo.setBackground(BG_WHITE);
        JLabel leftTopText = new JLabel("<html><b>International enrollment for 2026-2027, first Semester</b> from <i>September 1, 2025 to September 17, 2025</i></html>");
        JLabel rightTopText = new JLabel("<html><b>Course:</b> Major in Aura Mastery & Advanced Chill Dynamics<br><b>Curriculum:</b> 2026-2027 Year: 1, Graduating: No, Finished: No</html>");
        leftTopText.setFont(new Font("Arial", Font.PLAIN, 11));
        rightTopText.setFont(new Font("Arial", Font.PLAIN, 11));
        topInfo.add(leftTopText); topInfo.add(rightTopText);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.weighty = 0.05;
        mainContent.add(topInfo, gbc);

        JPanel leftSide = new JPanel(new GridLayout(2, 1, 0, 10));
        leftSide.setBackground(BG_WHITE);

        String[] preCols = {"CODE", "DESCRIPTION", "REMARK"};
        Object[][] preData = {
            {"NEN1", "Water Divination & Aura Flow", "OK"},
            {"HAKI1", "Blindfolded Dodging & Internal Flow", "OK"},
            {"BA 101", "Advanced \"Zen\" & Social Media Detach", "OK"}
        };
        preEnlistModel = new DefaultTableModel(preData, preCols);
        JTable preTable = new JTable(preEnlistModel);
        preTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel p1 = createTableContainer("Pre-Enlisted Subjects:", preTable, 
            new ActionButton("Enrollment data", e -> {}),
            new ActionButton("Delete", e -> deleteRow(preTable, preEnlistModel))
        );

        String[] schedCols = {"CODE", "SECTION", "SCHEDULE"};
        scheduleModel = new DefaultTableModel(null, schedCols);
        JTable schedTable = new JTable(scheduleModel);
        
        JPanel p2 = createTableContainer("Schedule for:", schedTable,
            new ActionButton("Auto", Color.WHITE, e -> {}),
            new ActionButton("Select", Color.WHITE, e -> {}),
            new ActionButton("Add", REFRESH_GN, e -> enrollSelectedSubject(preTable, schedTable))
        );

        leftSide.add(p1); leftSide.add(p2);

        preTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int r = preTable.getSelectedRow();
                if (r != -1) {
                    String code = preTable.getValueAt(r, 0).toString();
                    loadSubjectSchedules(code);
                }
            }
        });

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0.42; gbc.weighty = 0.90;
        mainContent.add(leftSide, gbc);

        String[] enrCols = {"CODE", "SUBJECT", "DESCRIPTION", "UNIT", "TF", "LAB", "SCHEDULE"};
        enrolledModel = new DefaultTableModel(null, enrCols);
        JTable enrTable = new JTable(enrolledModel);
        
        JPanel rightPanel = createTableContainer("Enrolled Subjects:", enrTable,
            new ActionButton("Delete", new Color(230, 80, 80), e -> deleteRow(enrTable, enrolledModel)),
            new ActionButton("Delete All", new Color(230, 80, 80), e -> enrolledModel.setRowCount(0))
        );

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 0.58;
        mainContent.add(rightPanel, gbc);

        JPanel bottomAssessBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomAssessBar.setBackground(BG_WHITE);
        JButton btnAssess = new JButton("Assess");
        btnAssess.setPreferredSize(new Dimension(100, 30));
        btnAssess.addActionListener(e -> executeAssessment());
        bottomAssessBar.add(btnAssess);

        root.add(mainContent, BorderLayout.CENTER);
        root.add(bottomAssessBar, BorderLayout.SOUTH);
        return root;
    }

    // ────────────────────────────────────────────────────────────────
    // SCREEN 4: OFFICIAL SCHEDULE (Recreated from your image)
    // ────────────────────────────────────────────────────────────────
    private JPanel buildOfficialScheduleScreen() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_WHITE);

        // Header matching screenshot
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(YT_RED);
        JLabel l = new JLabel("▶  YOUTUBE UNIVERSITY");
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial Black", Font.BOLD, 42));
        header.add(l);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BG_WHITE);

        // Metadata panel
        JPanel metaPanel = new JPanel(null);
        metaPanel.setPreferredSize(new Dimension(1200, 110));
        metaPanel.setBackground(BG_WHITE);

        JLabel info = new JLabel("<html><b>Student ID:</b> 123456789 &nbsp;&nbsp; <b>Name:</b> JUAN DELA CRUZ<br><b>Period:</b> 26-1</html>");
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setBounds(20, 10, 450, 35);

        JButton dataBtn = new JButton("Enrollment data");
        dataBtn.setBounds(250, 45, 125, 25);
        JButton delBtn = new JButton("Delete");
        delBtn.setBounds(380, 45, 75, 25);

        JLabel enrolledStatus = new JLabel("Student is OFFICIALLY ENROLLED.");
        enrolledStatus.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
        enrolledStatus.setForeground(BLUE_LINK);
        enrolledStatus.setBounds(110, 80, 400, 25);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(REFRESH_GN);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBounds(1050, 55, 100, 32);
        refreshBtn.addActionListener(e -> cardLayout.show(mainPanel, "ENROLLMENT"));

        metaPanel.add(info);
        metaPanel.add(dataBtn);
        metaPanel.add(delBtn);
        metaPanel.add(enrolledStatus);
        metaPanel.add(refreshBtn);
        content.add(metaPanel, BorderLayout.NORTH);

        // Table initialization matching image columns exactly
        String[] cols = {"Class", "Subject Code", "Subject Description", "Unit", "Schedule", "Room", "Instructor", "Section"};
        finalScheduleModel = new DefaultTableModel(null, cols);
        JTable table = new JTable(finalScheduleModel);

        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(BG_WHITE);
        table.getTableHeader().setBackground(BG_WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Format column 2 ("Subject Description") as underlined blue text
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                lbl.setForeground(BLUE_LINK);
                lbl.setText(v != null ? "<html><u>" + v.toString() + "</u></html>" : "");
                return lbl;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG_WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        content.add(scroll, BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);
        return root;
    }

    // --- REUSABLE UTILITIES ---
    private JPanel createTableContainer(String title, JTable table, ActionButton... buttons) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_WHITE);
        JLabel titleLabel = new JLabel(" " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        topBar.add(titleLabel, BorderLayout.WEST);

        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 2));
        btnBox.setBackground(BG_WHITE);
        for (ActionButton b : buttons) {
            JButton customBtn = new JButton(b.text);
            customBtn.setFont(new Font("Arial", Font.PLAIN, 11));
            customBtn.setBackground(b.color != null ? b.color : CARD_GRAY);
            customBtn.setFocusPainted(false);
            customBtn.addActionListener(b.action);
            btnBox.add(customBtn);
        }
        topBar.add(btnBox, BorderLayout.EAST);

        table.setRowHeight(26);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowGrid(true);
        table.setGridColor(new Color(235, 235, 235));
        table.getTableHeader().setBackground(CARD_GRAY);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG_WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void loadSubjectSchedules(String code) {
        scheduleModel.setRowCount(0);
        List<Object[]> rows = subjectSections.get(code);
        if (rows != null) {
            for (Object[] r : rows) {
                scheduleModel.addRow(new Object[]{r[0], r[1], r[2]});
            }
        }
    }

    private void enrollSelectedSubject(JTable preTable, JTable schedTable) {
        int preIdx = preTable.getSelectedRow();
        int schedIdx = schedTable.getSelectedRow();

        if (preIdx == -1 || schedIdx == -1) {
            JOptionPane.showMessageDialog(this, "Please select both a Subject and its Section Schedule.");
            return;
        }

        String classCode = schedTable.getValueAt(schedIdx, 0).toString();
        String subCode = preTable.getValueAt(preIdx, 0).toString();
        String desc = preTable.getValueAt(preIdx, 1).toString();
        String schedule = schedTable.getValueAt(schedIdx, 2).toString();

        for (int i = 0; i < enrolledModel.getRowCount(); i++) {
            if (enrolledModel.getValueAt(i, 0).equals(classCode)) {
                JOptionPane.showMessageDialog(this, "Class code " + classCode + " is already enrolled!");
                return;
            }
        }

        enrolledModel.addRow(new Object[]{classCode, subCode, desc, "3.0", "3.0", "0.0", schedule});
    }

    private void deleteRow(JTable t, DefaultTableModel m) {
        int r = t.getSelectedRow();
        if (r != -1) {
            m.removeRow(r);
        } else {
            JOptionPane.showMessageDialog(this, "Highlight a row to remove it.");
        }
    }

    private void executeAssessment() {
        if (enrolledModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Select and Add subjects before conducting the Assessment.");
            return;
        }

        finalScheduleModel.setRowCount(0);
        for (int i = 0; i < enrolledModel.getRowCount(); i++) {
            String clsCode = enrolledModel.getValueAt(i, 0).toString();
            String subCode = enrolledModel.getValueAt(i, 1).toString();
            String desc = enrolledModel.getValueAt(i, 2).toString();
            String unit = enrolledModel.getValueAt(i, 3).toString();
            String schedule = enrolledModel.getValueAt(i, 6).toString();

            // Populate metadata derived from stored mappings
            String room = "Room 404";
            String instructor = "Dr. Wing";
            String section = "SECTION A";

            List<Object[]> rawData = subjectSections.get(subCode);
            if (rawData != null && !rawData.isEmpty()) {
                room = rawData.get(0)[3].toString();
                instructor = rawData.get(0)[4].toString();
                section = rawData.get(0)[1].toString();
            }

            finalScheduleModel.addRow(new Object[]{clsCode, subCode, desc, unit, schedule, room, instructor, section});
        }

        cardLayout.show(mainPanel, "OFFICIAL_SCHEDULE");
    }

    static class ActionButton {
        String text;
        Color color;
        ActionListener action;
        ActionButton(String text, ActionListener action) { this.text = text; this.action = action; this.color = null; }
        ActionButton(String text, Color color, ActionListener action) { this.text = text; this.color = color; this.action = action; }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(YouTubeUniversitySystem::new);
    }
}
