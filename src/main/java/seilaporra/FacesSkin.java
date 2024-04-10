package seilaporra;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacesSkin extends JFrame implements ActionListener {
    private List<BufferedImage> images;
    private JPanel imagePanel;
    private JScrollPane scrollPane;
    private File initialDirectory = null;
    private int faceLimit = 7; // Limite de faces
    private int currentFaceCount = 0;
    private File lastSelectedDirectory; // Diretório da última imagem selecionada

    public FacesSkin() {
        setTitle("Choose the face");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1792, 356);
        setLocationRelativeTo(null);

        images = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton chooseImageButton = new JButton("Choose face");
        chooseImageButton.addActionListener(this);
        buttonPanel.add(chooseImageButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentFaceCount >= faceLimit) {
            JOptionPane.showMessageDialog(this, "Limite de faces atingido.");
            mergeImages(); // Salvar imagem final
            return;
        }

        JFileChooser fileChooser = new JFileChooser(initialDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            lastSelectedDirectory = selectedFile.getParentFile(); // Salva o diretório da última imagem selecionada
            if (initialDirectory == null) {
                initialDirectory = lastSelectedDirectory;
            }
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                images.add(image);
                addImageToPanel(image);
                currentFaceCount++; // Incrementa o contador de faces

                // Se for a última imagem, chama o método para salvar
                if (currentFaceCount == faceLimit) {
                    mergeImages();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading the face.");
            }
        }
    }

    private void addImageToPanel(BufferedImage image) {
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imagePanel.add(imageLabel);
        imagePanel.revalidate();
        imagePanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void mergeImages() {
        BufferedImage finalImage = new BufferedImage(1792, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();

        int x = 0;

        for (BufferedImage image : images) {
            g2d.drawImage(image, x, 0, null);
            x += image.getWidth();
        }

        g2d.dispose();

        try {
            File output = new File(lastSelectedDirectory, "faceskin.png"); // Salva a imagem final no diretório da última imagem selecionada
            ImageIO.write(finalImage, "png", output);
            JOptionPane.showMessageDialog(this, "Faces saved successfully: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving faces.");
        }

        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FacesSkin facesSkin = new FacesSkin();
            facesSkin.setVisible(true);
        });
    }
}