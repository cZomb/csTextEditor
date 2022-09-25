/*
 * Main text editor class for initializing and running 
 * the custom text editor Swing application
 * 
 * @Author: Chris Skura
 * JavaSE-17
 */

package csTextEdit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.*;

//main class to handle the text editor
public class TextEditor implements ActionListener {
	// main window
	private JFrame mainWin;

	// main text area
	private JTextArea text;

	// top menu bar
	private JMenuBar menu;

	// fonts that handle text resizing
	private Font font1;
	private Font font2;
	private Font font3;

	// main menu categories
	private JMenu File;
	private JMenu Font;

	// font size sub menu
	private JMenu fontSize;

	// file menu items
	private JMenuItem New;
	private JMenuItem Open;
	private JMenuItem Save;
	private JMenuItem Close;

	// scroll bars
	private JScrollPane scroll;

	// font menu items
	private JRadioButton font12;
	private JRadioButton font22;
	private JRadioButton font32;

	// button group to swap radio buttons
	private ButtonGroup fontSizeBg;

	// return value to handle errors while loading files
	private int returnValue = 0;

	// main constructor
	TextEditor() {
		// create radio button menu groups
		fontSizeBg = new ButtonGroup();

		// create main window
		mainWin = new JFrame("csTextEditor");
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin.setSize(800, 600);

		// initialize fonts
		font1 = new Font("Arial", 0, 12);
		font2 = new Font("Arial", 0, 22);
		font3 = new Font("Arial", 0, 32);

		// initialize text area
		text = new JTextArea();

		// add text area to main window
		mainWin.add(text);

		// set default font of text area
		text.setFont(font1);

		// initialize scroll bars
		scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// apply scroll bars to main window
		mainWin.add(scroll);
		// initialize
		mainWin.setVisible(true);
		mainWin.setLayout(new BorderLayout());

		// resize to insure scrollbars are fully visible
		mainWin.setSize(798, 630);
		mainWin.setResizable(false);

		// initialize menu/menuitems
		menu = new JMenuBar();
		File = new JMenu("File");
		New = new JMenuItem("New");
		Open = new JMenuItem("Open");
		Save = new JMenuItem("Save");
		Close = new JMenuItem("Close");

		Font = new JMenu("Font");
		fontSize = new JMenu("Font Size");
		font12 = new JRadioButton("12", true);
		font22 = new JRadioButton("22");
		font32 = new JRadioButton("32");

		fontSizeBg.add(font12);
		fontSizeBg.add(font22);
		fontSizeBg.add(font32);

		// add menu items to menu
		menu.add(File);
		File.add(New);
		File.addSeparator();
		File.add(Open);
		File.add(Save);
		File.addSeparator();
		File.add(Close);

		menu.add(Font);
		Font.add(fontSize);
		fontSize.add(font12);
		fontSize.add(font22);
		fontSize.add(font32);

		// add actionlisteners to menu items
		New.addActionListener(this);
		Open.addActionListener(this);
		Save.addActionListener(this);
		Close.addActionListener(this);

		font12.addActionListener(this);
		font22.addActionListener(this);
		font32.addActionListener(this);

		// assign menu to main window
		mainWin.setJMenuBar(menu);
	}

	// check for menu item interaction
	@Override
	public void actionPerformed(ActionEvent e) {
		// handles text ingest
		String input = "";

		// initialize file I/o
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose destination.");

		String ae = e.getActionCommand();// ae = ActionEvent

		// test Open Menu Option
		if (ae == "Open") {
			this.returnValue = jfc.showOpenDialog(null);

			File f = new File(jfc.getSelectedFile().getAbsolutePath());
			try {
				FileReader read = new FileReader(f);
				Scanner scan = new Scanner(read);

				while (scan.hasNextLine()) {
					String line = scan.nextLine() + "\n";
					input = input + line;
				}
				text.setText(input);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			// sets main window title to reflect path of opened file
			mainWin.setTitle(jfc.getSelectedFile().getAbsolutePath());
		}
		// test Save Menu Option
		if (ae == "Save") {
			this.returnValue = jfc.showSaveDialog(null);
			try {
				File f = new File(jfc.getSelectedFile().getAbsolutePath());
				FileWriter out = new FileWriter(f);
				out.write(text.getText());
				out.close();
			} catch (FileNotFoundException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "File not found.");
			} catch (IOException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "Error.");
			}
		}
		// create new text file
		if (ae == "New") {
			// clear text area
			text.setText("");

			// set main window title
			mainWin.setTitle("New Text File");
		}
		// close application
		if (ae == "Close") {
			System.exit(0);
		}
		// select radio button for different text sizes
		if (font12.isSelected()) {
			text.setFont(font1);
		} else if (font22.isSelected()) {
			text.setFont(font2);
		} else if (font32.isSelected()) {
			text.setFont(font3);
		}
	}
}
