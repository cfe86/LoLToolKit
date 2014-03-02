package view.tablemodel;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.structure.Champion;
import model.structure.ChampionSpells;
import model.util.Graphics;

public class ChampionTableModel implements TableModel {

	/**
	 * the champion
	 */
	private Champion champ;
	/**
	 * the skillpoints
	 */
	private boolean[][] skilled;
	/**
	 * selected image
	 */
	private ImageIcon selected;
	/**
	 * unselected image
	 */
	private ImageIcon unselected;

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public ChampionTableModel() throws IOException {
		this.skilled = new boolean[4][18];
		this.champ = new Champion();
		ChampionSpells s = new ChampionSpells();
		s.setPassive("-");
		s.setSpell1("-");
		s.setSpell2("-");
		s.setSpell3("-");
		s.setSpell4("-");
		this.champ.setSpells(s);

		this.selected = new ImageIcon(Graphics.readImageFromJar("images/point.png"));
		this.unselected = new ImageIcon(Graphics.readImageFromJar("images/white.png"));
	}

	/**
	 * Constructor
	 * 
	 * @param champ
	 *            the champion
	 * @param skilled
	 *            the skills
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public ChampionTableModel(Champion champ, boolean[][] skilled) throws IOException {
		this.champ = champ;
		this.skilled = skilled;
		this.selected = new ImageIcon(Graphics.readImageFromJar("images/point.png"));
		this.unselected = new ImageIcon(Graphics.readImageFromJar("images/white.png"));
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

		return ImageIcon.class;
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

		return skilled[rowIndex][columnIndex - 1] ? selected : unselected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#removeTableModelListener(javax.swing.event
	 * .TableModelListener)
	 */
	@Override
	public void removeTableModelListener(TableModelListener l) {
		// unused
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// unused
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#addTableModelListener(javax.swing.event.
	 * TableModelListener)
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		// unused
	}
}