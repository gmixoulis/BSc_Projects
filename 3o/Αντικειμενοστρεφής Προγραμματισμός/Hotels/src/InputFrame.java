import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputFrame extends JFrame {
	
	private JPanel panel;
	private JPanel diamoniPanel;
	private JPanel allInclusivePanel;
	private JPanel buttonPanel;
	
	private JLabel diamoniLabel;
	private JLabel hotelLabel;
	private JLabel allInclusiveLabel;
	
	private JList listView;
	private DefaultListModel model;
	
	private JTextField daysField;
	private JTextField mealsField;
	
	private JButton storeButton;
	private JButton calculateCostButton;
	
	private JTextField costField;
	private ArrayList<Hotel> hotels;

	
	public InputFrame() {
		
		
		panel = new JPanel();
		diamoniPanel = new JPanel();
		allInclusivePanel = new JPanel();
		buttonPanel = new JPanel();	
		
		listView = new JList();
		model = new DefaultListModel();
		
		
		
			FileInputStream fInputStream;
			try {
				fInputStream = new FileInputStream("hotels.txt");
				ObjectInputStream inputstream = new ObjectInputStream(fInputStream);
				hotels = (ArrayList<Hotel>)inputstream.readObject();
				inputstream.close();
				fInputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		model.addElement("Dummy Hotel Name 1");
		model.addElement("Dummy Hotel Name 2");
		model.addElement("Dummy Hotel Name 3");
		model.addElement("Dummy Hotel Name 4");
		
		listView.setModel(model);
		model.clear();
		

		Collections.sort(hotels);
		
		for(Hotel h : hotels) {
			model.addElement(h.getName());
		}
		
		diamoniLabel = new JLabel("Stoixeia Diamonis");
		hotelLabel = new JLabel("Hotel");
		allInclusiveLabel = new JLabel("AllInclusive");
		
		daysField = new JTextField("Hmeres Diamonis");
		mealsField = new JTextField("Plithos Geumatwn (1,2,3)");
		costField = new JTextField("Synoliko Kostos");
		
		storeButton = new JButton("Apothikeusi Kratisis");
		calculateCostButton = new JButton("Ypologismos Kostous");
		
		diamoniPanel.setLayout(new BoxLayout(diamoniPanel, BoxLayout.Y_AXIS));
		diamoniLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		diamoniPanel.add(diamoniLabel);
		hotelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		diamoniPanel.add(hotelLabel);
		diamoniPanel.add(listView);
		diamoniPanel.add(daysField);
		diamoniPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		allInclusivePanel.setLayout(new GridLayout(2,0));
		allInclusivePanel.add(allInclusiveLabel);
		allInclusivePanel.add(mealsField);
		allInclusivePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonPanel.setLayout(new GridLayout(2,0));
		buttonPanel.add(storeButton);
		buttonPanel.add(calculateCostButton);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel.add(diamoniPanel);
		panel.add(allInclusivePanel);
		panel.add(buttonPanel);
		panel.add(costField);
		
		this.setContentPane(panel);
		
		ButtonListener listener = new ButtonListener();
		storeButton.addActionListener(listener);
		calculateCostButton.addActionListener(listener);
		
		this.setVisible(true);
		this.setSize(200, 320);
		this.setLocation(200, 0);
		this.setTitle("Input");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String selectedHotelname = (String)listView.getSelectedValue();
			Hotel selectedHotel = null;
			for(Hotel h : hotels){
				if(h.getName().equals(selectedHotelname)) {
					selectedHotel = h;
				}
					
			}
			
			
			
			if(e.getSource().equals(storeButton)) {
				
				int days = Integer.parseInt(daysField.getText());
				
				if(mealsField.getText().equals("") || mealsField.getText().equals("Plithos Geumatwn (1,2,3)")) {
					SimpleReservation sr = new SimpleReservation(days);
					selectedHotel.addReservation(sr);
				}
				else  {
					
					int meals = Integer.parseInt(mealsField.getText());
					AllInclusive ai = new AllInclusive(days, meals);
					selectedHotel.addReservation(ai);
				}
					
				
			}
			else if(e.getSource().equals(calculateCostButton)) {
				double totalCost = selectedHotel.calculateTotalCost();
				String totalCostText = Double.toString(totalCost);
				costField.setText(totalCostText);
				
			}
			
			
			
		}
	}

}
