import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ParkingGUI extends JFrame {
    private ParkingManager manager;

    private JLabel lblTotalSlots;
    private JLabel lblAvailableSlots;
    private JLabel lblOccupiedSlots;

    private JLabel lblWingAStats;
    private JLabel lblWingBStats;
    private JLabel lblWingCStats;

    private Map<String, JButton> slotButtonMap;

    public ParkingGUI(ParkingManager manager) {
        this.manager = manager;
        this.slotButtonMap = new HashMap<>();

        setTitle("Parking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 800);
        setLocationRelativeTo(null);

        initUI();
        refreshDashboard();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(26, 37, 48));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("PARKING MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        JPanel dashboardStatsPanel = createDashboardStatsPanel();
        centerPanel.add(dashboardStatsPanel, BorderLayout.NORTH);

        JPanel visualGridPanel = createVisualGridPanel();
        JScrollPane scrollPane = new JScrollPane(visualGridPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                "PARKING SLOTS LAYOUT (Wing A, Wing B, Wing C)",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel actionPanel = createActionButtonsPanel();
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createDashboardStatsPanel() {
        JPanel container = new JPanel(new GridLayout(1, 4, 12, 0));
        container.setOpaque(false);

        lblTotalSlots = new JLabel("60", SwingConstants.CENTER);
        container.add(createStatCard("Total Slots", lblTotalSlots, new Color(52, 152, 219)));

        lblAvailableSlots = new JLabel("60", SwingConstants.CENTER);
        container.add(createStatCard("Available Slots", lblAvailableSlots, new Color(46, 204, 113)));

        lblOccupiedSlots = new JLabel("0", SwingConstants.CENTER);
        container.add(createStatCard("Occupied Slots", lblOccupiedSlots, new Color(231, 76, 60)));

        JPanel wingsCard = new JPanel(new GridLayout(3, 1));
        wingsCard.setBackground(Color.WHITE);
        wingsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        lblWingAStats = new JLabel("Wing A: Avail 20 | Occ 0");
        lblWingBStats = new JLabel("Wing B: Avail 20 | Occ 0");
        lblWingCStats = new JLabel("Wing C: Avail 20 | Occ 0");

        Font f = new Font("Segoe UI", Font.PLAIN, 12);
        lblWingAStats.setFont(f);
        lblWingBStats.setFont(f);
        lblWingCStats.setFont(f);

        wingsCard.add(lblWingAStats);
        wingsCard.add(lblWingBStats);
        wingsCard.add(lblWingCStats);

        container.add(wingsCard);

        return container;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(127, 140, 141));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createVisualGridPanel() {
        JPanel gridContainer = new JPanel(new GridLayout(1, 3, 15, 0));
        gridContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
        gridContainer.setBackground(Color.WHITE);

        String[] wingNames = {"Wing A", "Wing B", "Wing C"};

        for (String wing : wingNames) {
            JPanel wingPanel = new JPanel(new BorderLayout(5, 5));
            wingPanel.setOpaque(false);
            wingPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                    wing,
                    TitledBorder.CENTER, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
            ));

            JPanel slotsSubPanel = new JPanel(new GridLayout(2, 1, 0, 12));
            slotsSubPanel.setOpaque(false);

            JPanel twPanel = createVehicleTypeSlotSection(wing, "2-WHEELER (BIKE)", VehicleType.BIKE);
            JPanel fwPanel = createVehicleTypeSlotSection(wing, "4-WHEELER (CAR)", VehicleType.CAR);

            slotsSubPanel.add(twPanel);
            slotsSubPanel.add(fwPanel);

            wingPanel.add(slotsSubPanel, BorderLayout.CENTER);
            gridContainer.add(wingPanel);
        }

        return gridContainer;
    }

    private JPanel createVehicleTypeSlotSection(String wing, String title, VehicleType type) {
        JPanel sectionPanel = new JPanel(new BorderLayout(5, 5));
        sectionPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(new Color(52, 73, 94));
        lblTitle.setBorder(new EmptyBorder(2, 4, 4, 4));
        sectionPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel buttonsGrid = new JPanel(new GridLayout(5, 2, 8, 8));
        buttonsGrid.setOpaque(false);
        buttonsGrid.setPreferredSize(new Dimension(280, 240));

        for (ParkingSlot slot : manager.getParkingSlots()) {
            if (slot.getWing().equalsIgnoreCase(wing) && slot.getVehicleType() == type) {
                JButton slotBtn = new JButton();
                slotBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                slotBtn.setFocusPainted(false);
                slotBtn.setOpaque(true);
                slotBtn.setContentAreaFilled(true);
                slotBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                slotBtn.addActionListener(e -> handleSlotClick(slot));

                slotButtonMap.put(slot.getSlotNumber(), slotBtn);
                buttonsGrid.add(slotBtn);
            }
        }

        sectionPanel.add(buttonsGrid, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(new Color(236, 240, 241));

        JButton btnPark = createStyledButton("Park Vehicle", new Color(46, 204, 113));
        JButton btnExitVehicle = createStyledButton("Vehicle Exit", new Color(231, 76, 60));
        JButton btnViewSlots = createStyledButton("View Parking Slots", new Color(52, 152, 219));
        JButton btnViewAvailable = createStyledButton("View Available Slots", new Color(155, 89, 182));
        JButton btnExitApp = createStyledButton("Exit", new Color(127, 140, 141));

        btnPark.addActionListener(e -> openParkVehicleDialog());
        btnExitVehicle.addActionListener(e -> openVehicleExitDialog());
        btnViewSlots.addActionListener(e -> showAllSlotsDialog());
        btnViewAvailable.addActionListener(e -> showAvailableSlotsDialog());
        btnExitApp.addActionListener(e -> System.exit(0));

        actionPanel.add(btnPark);
        actionPanel.add(btnExitVehicle);
        actionPanel.add(btnViewSlots);
        actionPanel.add(btnViewAvailable);
        actionPanel.add(btnExitApp);

        return actionPanel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton("<html><center><span style='color:#FFFFFF;font-weight:bold;font-size:12px;'>" + text + "</span></center></html>");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void refreshDashboard() {
        int total = manager.getTotalSlots();
        int occupied = manager.getOccupiedSlotsCount();
        int available = manager.getAvailableSlotsCount();

        lblTotalSlots.setText(String.valueOf(total));
        lblAvailableSlots.setText(String.valueOf(available));
        lblOccupiedSlots.setText(String.valueOf(occupied));

        lblWingAStats.setText(String.format("Wing A: Avail %02d | Occ %02d",
                manager.getAvailableCountByWing("Wing A"), manager.getOccupiedCountByWing("Wing A")));
        lblWingBStats.setText(String.format("Wing B: Avail %02d | Occ %02d",
                manager.getAvailableCountByWing("Wing B"), manager.getOccupiedCountByWing("Wing B")));
        lblWingCStats.setText(String.format("Wing C: Avail %02d | Occ %02d",
                manager.getAvailableCountByWing("Wing C"), manager.getOccupiedCountByWing("Wing C")));

        for (ParkingSlot slot : manager.getParkingSlots()) {
            JButton btn = slotButtonMap.get(slot.getSlotNumber());
            if (btn != null) {
                if (slot.isOccupied() && slot.getVehicle() != null) {
                    Vehicle v = slot.getVehicle();
                    btn.setBackground(new Color(231, 76, 60));
                    btn.setForeground(Color.WHITE);
                    btn.setBorder(BorderFactory.createLineBorder(new Color(192, 57, 43), 2));

                    String htmlText = String.format(
                            "<html><center>"
                                    + "<span style='font-size:10px; color:#FFFFFF;'><b>%s</b></span><br/>"
                                    + "<span style='font-size:11px; color:#FFFF00;'><b>%s</b></span><br/>"
                                    + "<span style='font-size:8px; color:#FFD1DC;'>OCCUPIED</span>"
                                    + "</center></html>",
                            slot.getSlotNumber(),
                            v.getVehicleNumber()
                    );
                    btn.setText(htmlText);
                    btn.setToolTipText("Occupied by " + v.getVehicleNumber() + " (Owner: " + v.getOwnerName() + ")");
                } else {
                    btn.setBackground(new Color(46, 204, 113));
                    btn.setForeground(Color.WHITE);
                    btn.setBorder(BorderFactory.createLineBorder(new Color(39, 174, 96), 1));

                    String htmlText = String.format(
                            "<html><center>"
                                    + "<span style='font-size:10px; color:#FFFFFF;'><b>%s</b></span><br/>"
                                    + "<span style='font-size:9px; color:#E8F8F5;'>AVAILABLE</span>"
                                    + "</center></html>",
                            slot.getSlotNumber()
                    );
                    btn.setText(htmlText);
                    btn.setToolTipText("AVAILABLE (" + slot.getVehicleType() + ")");
                }
            }
        }

        revalidate();
        repaint();
    }

    private void handleSlotClick(ParkingSlot slot) {
        if (slot.isOccupied() && slot.getVehicle() != null) {
            Vehicle v = slot.getVehicle();
            String info = String.format("Slot: %s\nWing: %s\nStatus: OCCUPIED\n\nVehicle Number: %s\nOwner Name: %s\nVehicle Type: %s\nEntry Time: %s",
                    slot.getSlotNumber(), slot.getWing(), v.getVehicleNumber(), v.getOwnerName(), v.getVehicleType(), v.getEntryTime());
            JOptionPane.showMessageDialog(this, info, "Slot Details - " + slot.getSlotNumber(), JOptionPane.INFORMATION_MESSAGE);
        } else {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Slot " + slot.getSlotNumber() + " is AVAILABLE.\nDo you want to park a " + slot.getVehicleType() + " now?",
                    "Park Vehicle", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                openParkVehicleDialog(slot.getVehicleType());
            }
        }
    }

    private void openParkVehicleDialog() {
        openParkVehicleDialog(VehicleType.BIKE);
    }

    private void openParkVehicleDialog(VehicleType defaultType) {
        JDialog dialog = new JDialog(this, "Park Vehicle", true);
        dialog.setSize(420, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblVehNo = new JLabel("Vehicle Number:");
        lblVehNo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField txtVehNo = new JTextField(15);

        JLabel lblOwner = new JLabel("Owner Name:");
        lblOwner.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField txtOwner = new JTextField(15);

        JLabel lblType = new JLabel("Vehicle Type:");
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JComboBox<VehicleType> comboType = new JComboBox<>(VehicleType.values());
        comboType.setSelectedItem(defaultType);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblVehNo, gbc);
        gbc.gridx = 1;
        panel.add(txtVehNo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblOwner, gbc);
        gbc.gridx = 1;
        panel.add(txtOwner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblType, gbc);
        gbc.gridx = 1;
        panel.add(comboType, gbc);

        JButton btnSubmit = new JButton("<html><center><span style='color:#FFFFFF;font-weight:bold;font-size:13px;'>PARK VEHICLE</span></center></html>");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmit.setBackground(new Color(46, 204, 113));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setOpaque(true);
        btnSubmit.setContentAreaFilled(true);
        btnSubmit.setBorderPainted(true);
        btnSubmit.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(39, 174, 96), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSubmit, gbc);

        btnSubmit.addActionListener(e -> {
            String vehNo = txtVehNo.getText().trim();
            String owner = txtOwner.getText().trim();
            VehicleType type = (VehicleType) comboType.getSelectedItem();

            if (vehNo.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vehicle number cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (owner.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Owner name cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Vehicle vehicle = new Vehicle(vehNo, owner, type);
                ParkingSlot assignedSlot = manager.parkVehicle(vehicle);

                refreshDashboard();

                dialog.dispose();

                String msg = String.format("Vehicle Parked Successfully!\n\nVehicle Number: %s\nOwner: %s\nVehicle Type: %s\nWing: %s\nSlot: %s",
                        vehicle.getVehicleNumber(), vehicle.getOwnerName(), vehicle.getVehicleType(), assignedSlot.getWing(), assignedSlot.getSlotNumber());
                JOptionPane.showMessageDialog(this, msg, "Parking Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IllegalArgumentException | IllegalStateException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void openVehicleExitDialog() {
        JDialog dialog = new JDialog(this, "Vehicle Exit", true);
        dialog.setSize(440, 280);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblVehNo = new JLabel("Vehicle Number:");
        lblVehNo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField txtVehNo = new JTextField(15);

        JLabel lblDuration = new JLabel("Simulate Parked Time:");
        lblDuration.setFont(new Font("Segoe UI", Font.BOLD, 12));
        String[] durationOptions = {
                "Actual Time (Now)",
                "30 Minutes (1 Hr Billable)",
                "1 Hour",
                "1 Hour 10 Minutes (2 Hrs Billable)",
                "2 Hours 20 Minutes (3 Hrs Billable)",
                "5 Hours",
                "24 Hours"
        };
        JComboBox<String> comboDuration = new JComboBox<>(durationOptions);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblVehNo, gbc);
        gbc.gridx = 1;
        panel.add(txtVehNo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblDuration, gbc);
        gbc.gridx = 1;
        panel.add(comboDuration, gbc);

        JButton btnProcess = new JButton("<html><center><span style='color:#FFFFFF;font-weight:bold;font-size:13px;'>PROCESS VEHICLE EXIT</span></center></html>");
        btnProcess.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnProcess.setBackground(new Color(231, 76, 60));
        btnProcess.setForeground(Color.WHITE);
        btnProcess.setOpaque(true);
        btnProcess.setContentAreaFilled(true);
        btnProcess.setBorderPainted(true);
        btnProcess.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(192, 57, 43), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnProcess.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnProcess, gbc);

        btnProcess.addActionListener(e -> {
            String vehNo = txtVehNo.getText().trim();
            if (vehNo.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vehicle number cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ParkingSlot slot = manager.findSlotByVehicleNumber(vehNo);
            if (slot == null) {
                JOptionPane.showMessageDialog(dialog, "Vehicle number '" + vehNo.toUpperCase() + "' not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime entryTime = slot.getVehicle().getEntryTime();
            LocalDateTime simulatedExit = LocalDateTime.now();

            int selectedDurationIdx = comboDuration.getSelectedIndex();
            switch (selectedDurationIdx) {
                case 1:
                    simulatedExit = entryTime.plusMinutes(30);
                    break;
                case 2:
                    simulatedExit = entryTime.plusHours(1);
                    break;
                case 3:
                    simulatedExit = entryTime.plusHours(1).plusMinutes(10);
                    break;
                case 4:
                    simulatedExit = entryTime.plusHours(2).plusMinutes(20);
                    break;
                case 5:
                    simulatedExit = entryTime.plusHours(5);
                    break;
                case 6:
                    simulatedExit = entryTime.plusHours(24);
                    break;
                default:
                    simulatedExit = LocalDateTime.now();
                    break;
            }

            try {
                ParkingTicket ticket = manager.exitVehicle(vehNo, simulatedExit);

                refreshDashboard();

                dialog.dispose();

                showReceiptDialog(ticket);

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Exit Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showReceiptDialog(ParkingTicket ticket) {
        JDialog receiptDialog = new JDialog(this, "Parking Receipt", true);
        receiptDialog.setSize(480, 420);
        receiptDialog.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea(ticket.generateReceipt());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        textArea.setBackground(new Color(253, 254, 254));

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClose.addActionListener(e -> receiptDialog.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnClose);

        receiptDialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        receiptDialog.add(bottomPanel, BorderLayout.SOUTH);
        receiptDialog.setVisible(true);
    }

    private void showAllSlotsDialog() {
        JDialog dialog = new JDialog(this, "All Parking Slots Status", true);
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(this);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-8s | %-10s | %-10s | %-15s | %-15s\n",
                "SLOT", "WING", "TYPE", "STATUS", "VEHICLE NO", "OWNER NAME"));
        sb.append("-----------------------------------------------------------------------------------\n");

        for (ParkingSlot slot : manager.getParkingSlots()) {
            if (slot.isOccupied()) {
                Vehicle v = slot.getVehicle();
                sb.append(String.format("%-10s | %-8s | %-10s | %-10s | %-15s | %-15s\n",
                        slot.getSlotNumber(), slot.getWing(), slot.getVehicleType(), "OCCUPIED", v.getVehicleNumber(), v.getOwnerName()));
            } else {
                sb.append(String.format("%-10s | %-8s | %-10s | %-10s | %-15s | %-15s\n",
                        slot.getSlotNumber(), slot.getWing(), slot.getVehicleType(), "AVAILABLE", "-", "-"));
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showAvailableSlotsDialog() {
        JDialog dialog = new JDialog(this, "Available Parking Slots", true);
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(this);

        StringBuilder sb = new StringBuilder();
        sb.append("AVAILABLE PARKING SLOTS (TOTAL: ").append(manager.getAvailableSlotsCount()).append(")\n");
        sb.append("=======================================================\n\n");

        String[] wings = {"Wing A", "Wing B", "Wing C"};
        for (String wing : wings) {
            sb.append("--- ").append(wing).append(" ---\n");
            sb.append("2-WHEELER (BIKE): ");
            boolean first = true;
            for (ParkingSlot slot : manager.getParkingSlots()) {
                if (slot.getWing().equalsIgnoreCase(wing) && slot.getVehicleType() == VehicleType.BIKE && !slot.isOccupied()) {
                    if (!first) sb.append(", ");
                    sb.append(slot.getSlotNumber());
                    first = false;
                }
            }
            if (first) sb.append("None (FULL)");
            sb.append("\n");

            sb.append("4-WHEELER (CAR) : ");
            first = true;
            for (ParkingSlot slot : manager.getParkingSlots()) {
                if (slot.getWing().equalsIgnoreCase(wing) && slot.getVehicleType() == VehicleType.CAR && !slot.isOccupied()) {
                    if (!first) sb.append(", ");
                    sb.append(slot.getSlotNumber());
                    first = false;
                }
            }
            if (first) sb.append("None (FULL)");
            sb.append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
