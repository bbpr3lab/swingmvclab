package swingmvclab;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/*
 * A hallgat�k adatait t�rol� oszt�ly.
 */
public class StudentData extends AbstractTableModel {

	/*
	 * Ez a tagv�ltoz� t�rolja a hallgat�i adatokat. NE M�DOS�TSD!
	 */
	List<Student> students = new ArrayList<Student>();

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return students.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Student student = students.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getName();
		case 1:
			return student.getNeptun();
		case 2:
			return student.hasSignature();
		default:
			return student.getGrade();
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case 0:
			return "nev";
		case 1:
			return "neptun";
		case 2:
			return "alairas";
		default:
			return "jegy";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
		case 1: return String.class;
		case 2: return Boolean.class;
		default: return Integer.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return columnIndex == 2 || columnIndex == 3;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Student student = students.get(rowIndex);
		if (columnIndex == 2)
			student.setSignature((Boolean) aValue);
		else if (columnIndex == 3)
			student.setGrade((Integer) aValue);
	}
	
	public void addStudent(String name, String neptun) {
		Student student = new Student(name, neptun, false, 0);
		students.add(student);
		fireTableRowsInserted(0, students.size() - 1);
	}

}
