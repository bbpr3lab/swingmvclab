package swingmvclab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/*
 * A megjelen�tend� ablakunk oszt�lya.
 */
public class StudentFrame extends JFrame {

	class StudentTableCellRenderer implements TableCellRenderer {

		private TableCellRenderer renderer;

		public StudentTableCellRenderer(TableCellRenderer renderer) {
			// TODO Auto-generated constructor stub
			this.renderer = renderer;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			// Kikeressük az éppen megjelenítendő hallgatót a külső,
			// StudentFrame osztály data tagváltozójából,
			// megállapítjuk, hogy bukásra áll-e vagy sem,
			// és ez alapján átállítjuk a komponens háttérszínét:
			// component.setBackground(...)
			
			int modelIndex = table.getRowSorter().convertRowIndexToModel(row);
			Student student = data.students.get(modelIndex);
			if (student.hasSignature() && student.getGrade() > 1)
				component.setBackground(Color.GREEN);
			else
				component.setBackground(Color.RED);
			return component;
		}

	}

	/*
	 * Ebben az objektumban vannak a hallgat�i adatok. A program indul�s ut�n
	 * bet�lti az adatokat f�jlb�l, bez�r�skor pedig kimenti oda.
	 * 
	 * NE M�DOS�TSD!
	 */
	private StudentData data;
	private JTextField nameField;
	private JTextField neptunField;

	/*
	 * Itt hozzuk l�tre �s adjuk hozz� az ablakunkhoz a k�l�nb�z� komponenseket
	 * (t�bl�zat, beviteli mez�, gomb).
	 */
	private void initComponents() {
		this.setLayout(new BorderLayout());

		// ...
		JTable table = new JTable(data);
		table.setAutoCreateRowSorter(true);
		
		table.setDefaultRenderer(String.class,
				new StudentTableCellRenderer(table.getDefaultRenderer(String.class)));
		table.setDefaultRenderer(Boolean.class,
				new StudentTableCellRenderer(table.getDefaultRenderer(Boolean.class)));
		table.setDefaultRenderer(Integer.class,
				new StudentTableCellRenderer(table.getDefaultRenderer(Integer.class)));
		
		 table.setFillsViewportHeight(true);
		JScrollPane sp = new JScrollPane(table);
		getContentPane().add(sp, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		JLabel nameLabel = new JLabel("nev");
		nameField = new JTextField(20);
		JLabel neptunLabel = new JLabel("neptun");
		neptunField = new JTextField(6);
		JButton button = new JButton("felvesz");
		southPanel.add(nameLabel);
		southPanel.add(nameField);
		southPanel.add(neptunLabel);
		southPanel.add(neptunField);
		southPanel.add(button);
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		button.addActionListener(e -> data.addStudent(nameField.getText(), neptunField.getText()));

	}

	/*
	 * Az ablak konstruktora.
	 * 
	 * NE M�DOS�TSD!
	 */
	@SuppressWarnings("unchecked")
	public StudentFrame() {
		super("Hallgat�i nyilv�ntart�s");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Indul�skor bet�ltj�k az adatokat
		try {
			data = new StudentData();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
			data.students = (List<Student>) ois.readObject();
			ois.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Bez�r�skor mentj�k az adatokat
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"));
					oos.writeObject(data.students);
					oos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// Fel�p�tj�k az ablakot
		setMinimumSize(new Dimension(500, 200));
		initComponents();
	}

	/*
	 * A program bel�p�si pontja.
	 * 
	 * NE M�DOS�TSD!
	 */
	public static void main(String[] args) {
		// Megjelen�tj�k az ablakot
		StudentFrame sf = new StudentFrame();
		sf.setVisible(true);
	}
}
