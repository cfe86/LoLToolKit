package view.tablemodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.structure.Champion;
import model.structure.ChampionSpells;

public class ChampionEditorTableModel implements TableModel {

	/**
	 * the champion
	 */
	private Champion champ;
	/**
	 * the skillpoints
	 */
	private boolean[][] skilled;

	/**
	 * Constructor
	 */
	public ChampionEditorTableModel() {
		this.skilled = new boolean[4][18];
		this.champ = new Champion();
		ChampionSpells s = new ChampionSpells();
		s.setPassive("-");
		s.setSpell1("-");
		s.setSpell2("-");
		s.setSpell3("-");
		s.setSpell4("-");
		this.champ.setSpells(s);
	}

	/**
	 * get current skills
	 * 
	 * @return the skills
	 */
	public boolean[][] getSkills() {
		return this.skilled;
	}

	/**
	 * Constructor
	 * 
	 * @param champ
	 *            given champion
	 * @param skilled
	 *            given skills
	 */
	public ChampionEditorTableModel(Champion champ, boolean[][] skilled) {
		this.champ = champ;
		this.skilled = skilled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return String.class;

		return Boolean.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 19;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0)
			return "Player Level";
		return Integer.toString(columnIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			if (rowIndex == 0)
				return this.champ.getSpells().getSpell1();
			else if (rowIndex == 1)
				return this.champ.getSpells().getSpell2();
			else if (rowIndex == 2)
				return this.champ.getSpells().getSpell3();
			else if (rowIndex == 3)
				return this.champ.getSpells().getSpell4();
		}

		return skilled[rowIndex][columnIndex - 1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		skilled[rowIndex][columnIndex - 1] = (boolean) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#removeTableModelListener(javax.swing.event
	 * .TableModelListener)
	 */
	@Override
	public void removeTableModelListener(TableModelListener arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#addTableModelListener(javax.swing.event.
	 * TableModelListener)
	 */
	@Override
	public void addTableModelListener(TableModelListener arg0) {
	}
}