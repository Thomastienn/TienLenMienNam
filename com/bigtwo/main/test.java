package com.bigtwo.main;

import java.io.File;

public class test {
    public static void main(String[] args) {
        // JFrame frame = new JFrame("My First GUI");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(300, 300);

        // JButton button = new JButton("Press");
        // button.setBackground(Color.RED);

        // frame.getContentPane().add(button); // Adds Button to content pane of frame
        // frame.setVisible(true);
        File file = new File("com/bigtwo/images/2D.png");

        System.out.println(file.getAbsolutePath());
    }
}
