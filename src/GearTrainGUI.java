import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GearTrainGUI extends JFrame {
    private JTextField pegInput;
    private JTextArea resultArea;
    private GearPanel gearPanel;
    private JButton visualizeButton;
    private JButton animateButton;
    private Timer animationTimer;
    private boolean isAnimating = false;
    
    public GearTrainGUI() {
        setTitle("Gear Train Problem Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Top panel with input
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Peg Positions (comma-separated):"));
        pegInput = new JTextField("4, 30, 50", 20);
        inputPanel.add(pegInput);
        
        visualizeButton = new JButton("Visualize");
        visualizeButton.addActionListener(this::visualize);
        inputPanel.add(visualizeButton);
        
        animateButton = new JButton("Animate");
        animateButton.addActionListener(this::toggleAnimation);
        inputPanel.add(animateButton);
        
        topPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Example buttons panel
        JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        examplePanel.add(new JLabel("Examples:"));
        
        addExampleButton(examplePanel, "Valid 3", "4, 30, 50");
        addExampleButton(examplePanel, "Invalid", "4, 17, 50");
        addExampleButton(examplePanel, "2 Pegs", "10, 50");
        addExampleButton(examplePanel, "4 Pegs", "10, 30, 40, 60");
        addExampleButton(examplePanel, "Powers of 2", "4, 8, 16, 32, 64");
        
        topPanel.add(examplePanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with visualization
        gearPanel = new GearPanel();
        gearPanel.setPreferredSize(new Dimension(800, 400));
        gearPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(gearPanel, BorderLayout.CENTER);
        
        // Bottom panel with results
        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        // Initial visualization
        visualize(null);
    }
    
    private void addExampleButton(JPanel panel, String label, String pegs) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            pegInput.setText(pegs);
            visualize(null);
        });
        panel.add(button);
    }
    
    private void visualize(ActionEvent e) {
        String input = pegInput.getText();
        String[] parts = input.split(",");
        List<Integer> pegList = new ArrayList<>();
        
        try {
            for (String part : parts) {
                pegList.add(Integer.parseInt(part.trim()));
            }
            
            if (pegList.size() < 2) {
                showError("Please enter at least 2 peg positions");
                return;
            }
            
            int[] pegs = pegList.stream().sorted().mapToInt(i -> i).toArray();
            int[] result = Solution.solution(pegs);
            
            gearPanel.setPegsAndSolution(pegs, result);
            displayResults(pegs, result);
            
        } catch (NumberFormatException ex) {
            showError("Invalid input format. Please enter comma-separated integers.");
        }
    }
    
    private void toggleAnimation(ActionEvent e) {
        if (isAnimating) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }
    
    private void startAnimation() {
        if (gearPanel.gears.isEmpty()) {
            showError("Please visualize a solution first");
            return;
        }
        
        isAnimating = true;
        animateButton.setText("Stop");
        
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    gearPanel.updateAnimation();
                    gearPanel.repaint();
                });
            }
        }, 0, 50); // 20 FPS
    }
    
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.cancel();
            animationTimer = null;
        }
        isAnimating = false;
        animateButton.setText("Animate");
    }
    
    private void displayResults(int[] pegs, int[] result) {
        StringBuilder sb = new StringBuilder();
        
        if (result[0] == -1) {
            sb.append("No valid solution exists for these peg positions.\n");
            sb.append("Pegs: ").append(arrayToString(pegs)).append("\n");
        } else {
            double firstRadius = (double) result[0] / result[1];
            sb.append("Solution Found!\n");
            sb.append("First gear radius: ");
            if (result[1] == 1) {
                sb.append(result[0]);
            } else {
                sb.append(result[0]).append("/").append(result[1]);
            }
            sb.append(" = ").append(String.format("%.3f", firstRadius)).append("\n\n");
            
            // Calculate all radii
            List<Double> radii = calculateAllRadii(pegs, firstRadius);
            sb.append("All gear radii:\n");
            for (int i = 0; i < radii.size(); i++) {
                sb.append(String.format("Gear %d: Position=%d, Radius=%.3f", 
                    i + 1, pegs[i], radii.get(i)));
                if (i == 0) sb.append(" (First - 2x speed)");
                if (i == radii.size() - 1) sb.append(" (Last - 1x speed)");
                sb.append("\n");
            }
        }
        
        resultArea.setText(sb.toString());
    }
    
    private List<Double> calculateAllRadii(int[] pegs, double firstRadius) {
        List<Double> radii = new ArrayList<>();
        radii.add(firstRadius);
        
        for (int i = 0; i < pegs.length - 1; i++) {
            double distance = pegs[i + 1] - pegs[i];
            double nextRadius = distance - radii.get(i);
            radii.add(nextRadius);
        }
        
        return radii;
    }
    
    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    private void showError(String message) {
        resultArea.setText("Error: " + message);
        gearPanel.clearGears();
    }
    
    // Inner class for gear visualization panel
    class GearPanel extends JPanel {
        private List<Gear> gears = new ArrayList<>();
        private double animationAngle = 0;
        
        public void setPegsAndSolution(int[] pegs, int[] result) {
            gears.clear();
            
            if (result[0] == -1) {
                repaint();
                return;
            }
            
            double firstRadius = (double) result[0] / result[1];
            List<Double> radii = calculateAllRadii(pegs, firstRadius);
            
            int minPeg = pegs[0];
            int maxPeg = pegs[pegs.length - 1];
            int range = maxPeg - minPeg;
            
            for (int i = 0; i < pegs.length; i++) {
                double x = 50 + (pegs[i] - minPeg) * (getWidth() - 100.0) / range;
                double y = getHeight() / 2.0;
                double radius = radii.get(i) * 300.0 / range; // Scale radius
                
                Color color = Color.GRAY;
                if (i == 0) color = new Color(39, 174, 96); // Green for first
                if (i == pegs.length - 1) color = new Color(52, 152, 219); // Blue for last
                
                gears.add(new Gear(x, y, radius, color, i));
            }
            
            repaint();
        }
        
        public void clearGears() {
            gears.clear();
            repaint();
        }
        
        public void updateAnimation() {
            animationAngle += 2; // degrees per frame
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            // Draw gears
            for (int i = 0; i < gears.size(); i++) {
                Gear gear = gears.get(i);
                
                // Calculate rotation based on gear position
                double rotation = animationAngle;
                if (i == 0) {
                    rotation *= 2; // First gear rotates 2x speed
                } else if (i == gears.size() - 1) {
                    rotation *= 1; // Last gear rotates 1x speed
                } else {
                    // Middle gears alternate direction
                    rotation *= (i % 2 == 0 ? 2 : -2) * gears.get(gears.size() - 1).radius / gear.radius;
                }
                
                drawGear(g2, gear, rotation);
            }
        }
        
        private void drawGear(Graphics2D g2, Gear gear, double rotation) {
            // Draw gear circle
            g2.setColor(gear.color);
            g2.setStroke(new BasicStroke(3));
            Ellipse2D circle = new Ellipse2D.Double(
                gear.x - gear.radius, 
                gear.y - gear.radius, 
                gear.radius * 2, 
                gear.radius * 2
            );
            g2.draw(circle);
            
            // Draw teeth
            int numTeeth = (int)(gear.radius / 5);
            double angleStep = 360.0 / numTeeth;
            
            for (int i = 0; i < numTeeth; i++) {
                double angle = Math.toRadians(i * angleStep + rotation);
                double toothHeight = Math.min(8, gear.radius * 0.15);
                
                double x1 = gear.x + gear.radius * Math.cos(angle);
                double y1 = gear.y + gear.radius * Math.sin(angle);
                double x2 = gear.x + (gear.radius + toothHeight) * Math.cos(angle);
                double y2 = gear.y + (gear.radius + toothHeight) * Math.sin(angle);
                
                g2.draw(new Line2D.Double(x1, y1, x2, y2));
            }
            
            // Draw center peg
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval((int)(gear.x - 3), (int)(gear.y - 3), 6, 6);
            
            // Draw gear number
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            String label = "G" + (gear.index + 1);
            g2.drawString(label, (int)(gear.x - 10), (int)(gear.y - gear.radius - 10));
        }
    }
    
    // Gear data class
    class Gear {
        double x, y, radius;
        Color color;
        int index;
        
        Gear(double x, double y, double radius, Color color, int index) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
            this.index = index;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GearTrainGUI gui = new GearTrainGUI();
            gui.setVisible(true);
        });
    }
}