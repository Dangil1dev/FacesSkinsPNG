package seilaporra;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MergeImages extends JFrame implements ActionListener {
    private BufferedImage[] images;
    private int imageCount = 0;
    private File initialDirectory = null;

    public MergeImages() {
        setTitle("Selecione as Imagens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton chooseImageButton = new JButton("Escolher Imagem");
        chooseImageButton.addActionListener(this);
        panel.add(chooseImageButton, BorderLayout.CENTER);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(initialDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (initialDirectory == null) {
                initialDirectory = selectedFile.getParentFile();
            }
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                if (images == null) {
                    images = new BufferedImage[7];
                }
                images[imageCount++] = image;
                if (imageCount == 7) {
                    mergeImages();
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione a próxima imagem.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem.");
            }
        }
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
            File output = new File(initialDirectory, "faceskin.png");
            ImageIO.write(finalImage, "png", output);
            JOptionPane.showMessageDialog(this, "Imagem final criada com sucesso: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar a imagem final.");
        }

        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MergeImages mergeImages = new MergeImages();
            mergeImages.setVisible(true);
        });
    }
}