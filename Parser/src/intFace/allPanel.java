package intFace;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import parserSwing.parserClasses.fxTrendParser;
import parserSwing.parserClasses.investflowPanteonParser;
import parserSwing.parserClasses.parser;

public class allPanel {
	JPanel mainPanel;
	final JTextField linkField = new JTextField("Link",20);
	final JTextField fileLinkField = new JTextField("testfile.xls",20);
	final JTextField pathToLinksFile = new JTextField("links.txt",20);
	JButton parseFromFile = new JButton("Parse 2");
	JButton goParse = new JButton("Parse");
	JButton exitButt = new JButton("Exit");
	parser Parser;
	int rowCount=1;
	
	
	public allPanel(){
		mainPanel = new JPanel();
		mainPanel.setBounds(50, 50, 300, 300);
		setLayout();
		 addListeners();
		 
	}
	void setLayout(){
		mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(linkField, c);
		
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(fileLinkField, c);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(goParse, c);

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 4;
		mainPanel.add(pathToLinksFile, c);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 5;
		mainPanel.add(parseFromFile, c);
		
		c.gridx = 1;
		c.gridy = 5;
		mainPanel.add(exitButt, c);

	}
	void addListeners(){
		goParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Parse");
			
				try {
					URL link=new URL(linkField.getText());
					File file = new File(fileLinkField.getText());
					
					System.out.println(link.getAuthority());
					if(link.getAuthority().equals("fx-trend.com")){
						Parser = new fxTrendParser(link);
					}
					if(link.getAuthority().equals("investflow.ru")){
						link=new URL("http://"+link.getAuthority()+link.getPath()+"?tab=profit");
						Parser = new investflowPanteonParser(link);
					}
					if(Parser!=null){
					Parser.saveToExcel(file, 2, rowCount++);
					}else{
						
					}
				} catch (MalformedURLException e1) {
				} catch (IOException e1) {
				} catch (RowsExceededException e1) {
				} catch (WriteException e1) {
				} catch (BiffException e1) {
				}
				
			
			}
		});
		parseFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new parseFromTxt(new File(pathToLinksFile.getText()),
							new File(fileLinkField.getText()));
				} catch (WriteException | BiffException| IOException e1) {
				}
				try {
					Desktop.getDesktop().edit(new File(fileLinkField.getText()));
					//Runtime.getRuntime().exec("links.txt");
				} catch (IOException e1) { System.out.println("shit!");
				}
			}
		});
		

		exitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	public JPanel getPanel(){
		return mainPanel;
	}
}
