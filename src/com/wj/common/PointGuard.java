package com.wj.common;

import org.jsoup.select.Elements;

public class PointGuard extends Player implements Comparable<PointGuard>
{

	public PointGuard() {}
	public PointGuard(String name) { this.name = name; }
	
	/*   TODO:
	 *   1. Finish weekly totals from ffotoday.com
	 *   2. Improve compareTo functionality to better judge QB A is better than QB B.
	 *   3. Implement calculateAverageDKPoints
	 *   	a. I don't know that it'll be needed. It would probably be better to have
	 *   		a line graph for DK Points vs Cost (per week).
	 */
	
	
	//TODO: 
	@Override
	public void setInitialData (String[] elementData)
	{
		name = elementData[1] + " " + elementData[2];
		team = elementData[3];
	}
	
	public String toString()
	{
		String toString = "Rank\tName\t\tTeam\n"
			+"\t"+String.format("%8.12s", name)+"\t"+team+"\n";
		
		return toString;
	}
	
	@Override
	public void addWeeklyTotals(Elements weeklyData)
	{
		
	}

	@Override
	public void setDKValue(String value)
	{

	}


	public int compareTo(PointGuard o)
	{
		int returnValue = -1;
		
		return returnValue;
	}
}