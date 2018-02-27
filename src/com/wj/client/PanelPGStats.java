package com.wj.client;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.wj.client.lib.PlayerDataPanel;
import com.wj.client.lib.PlayerPanel;
import com.wj.common.PointGuard;

public class PanelPGStats extends PlayerPanel
{
	protected ArrayList<PointGuard> PGList = null;
	protected Object[] columnNames 			= { "Week#", "team", "Stat1", "Stat2", "Stat3", "Stat4",
												"Stat5",	"Stat6", "Stat7", "Stat8", "Stat9", 
												"Stat10", "Stat11", "Stat12", "Stat13", };
	
	/*   TODO:
	 *   1. Implement a PanelXXStats for each player type.
	 *   2. Implement a line graph on dk points vs cost (per week)
	 */
	public PanelPGStats(ArrayList<PointGuard> PGList)
	{
		if(PGList == null || PGList.size() == 0)
		{
			ArrayList<PointGuard> defaultPGs = new ArrayList<PointGuard>();
			defaultPGs.add(new PointGuard("test player"));
			PGList = defaultPGs;
		}
		this.PGList = PGList;
		setupScreen();
	}
	
	public static void main(String[] args)
	{
		try
		{			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ArrayList<PointGuard> defaultPGs = new ArrayList<PointGuard>();
			defaultPGs.add(new PointGuard("test player"));
			SwingUtilities.invokeLater(new PanelPGStats(defaultPGs));	
		}
		catch(Throwable thr) {thr.printStackTrace();}
	}
	
	public void setupScreen()
	{
		setTitle("PG Stats");
		setupPGView();
	}

	public void setupPGView()
	{
		if(PGList != null)
		{
			for(int i = 0; i < PGList.size(); i++)
			{
				PGPanel panel = new PGPanel(PGList.get(i));
				playerTab.addTab(panel.getName(), panel);
				if(i == 0)
				{
					((DefaultTableModel)grid.getModel()).setDataVector(panel.getRowData(), columnNames);
				}
			}
			
			for(int i = 0; i < grid.getModel().getColumnCount(); i++)
			{
				if(i == 3 || i == 4 || i == 6 || i == grid.getModel().getColumnCount() -1)
					grid.getColumnModel().getColumn(i).setMinWidth(96);
			}			
		}
	}

	class PGPanel extends PlayerDataPanel
	{
		PointGuard PG = null;
		public PGPanel(PointGuard PG)
		{
			this.PG = PG;
			setName(PG.name);
		}
		
		
		@Override
		public Object[][] getRowData()
		{
			Object[][] list = new Object[20][15];
			
			return list;
		}		
	}
}
