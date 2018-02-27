package com.wj.server;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wj.common.Player;
import com.wj.common.PointGuard;

public class PlayerData
{
	public ArrayList<PointGuard> pointGuardList;
	
	public static final int SEASON_START_WEEK = 37;		// Regular Season usually starts when? in September-ish?
	public static final int WEEK_OVERRIDE = 3, YEAR_OVERRIDE = 2017;
	public static final int WEEK_NUMBER = WEEK_OVERRIDE == 0? Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - SEASON_START_WEEK : WEEK_OVERRIDE;
	public static final int CALENDAR_YEAR = YEAR_OVERRIDE == 0? Calendar.getInstance().get(Calendar.YEAR) : YEAR_OVERRIDE;
	
	public static final int THREAD_COUNT = Integer.valueOf(System.getProperty("project.loaddata.threadpool", "64"));
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	
	/* TODO:
	 * 	   1. Find URLs for mining PointGuard data (and other player types)
	 *     2. Improve performance by caching/saving website data to disk and loading from disk instead of from network call.
	 *        a. Implement structure for which files contain what data.
	 *        b. Could be worthwhile to connect to MSSQL/Oracle
	 *     3. Improve rankPlayers method. Specifically, improve (business) logic behind 'Player A is better than Player B'.
	 *     4. Improve logic behind injuryList to not have to re-loop through all the players and make sure that they're accounted for.
	 *     5. Evaluate abstracting out JSoup calls/implementation somehow to a data structure to clean up code.
	 *     	  a. Maybe this isn't needed. PlayerData.java is the only place JSoup is used, so encapsulation is good.
	 *     6. Clean up any excess code, and remove any hard-coded values
	 *     7. Research the SEASON_START_WEEK to see if this can be controlled better than a hard-coded value
	 *     8. Implement loadPG for the other positions (like SG, etc)
	 */
	public void loadData()
	{
		try
		{
			 // Sleep temporarily until functionality is implemented.
			// That way we can confirm splash screen functionality.
			Thread.sleep( 10 * 1000);
			
			loadInjuredPlayers();
			
			loadPG();

			rankPlayers();
			while(((ThreadPoolExecutor)threadPool).getActiveCount() > 0)
			{
				System.out.println("Sleeping... " + ((ThreadPoolExecutor)threadPool).getActiveCount());
				Thread.sleep(1 * 1000);
			}


		}
		catch(Throwable thr)
		{
			thr.printStackTrace();
		}
	}
	public static void main(String... args)
	{
		PlayerData main = new PlayerData();
		main.loadData();
	}

	class PlayerDataLoad extends Thread
	{
		private String type;
		private List<Element> playersFromWebsite;
		private ArrayList copyVector; 
		
		public PlayerDataLoad(String type, List<Element> playersFromWebsite, ArrayList copyVector)
		{
			this.type = type;
			this.playersFromWebsite = playersFromWebsite;
			this.copyVector = copyVector;
		}
		
		public void run()
		{
//			try
//			{
//				URL salaryURL     = null;
//				String categoryId = null;
//				Class playerClass = null;
//				
//				if("PG".equals(type))
//				{
//					salaryURL     = new URL("");
//					categoryId    = "0"; 
//					playerClass   = PointGuard.class;
//				}
//				
//				for(int i = 0; i < playersFromWebsite.size(); i++)	
//				{
//					Player singlePlayer            = (Player)playerClass.newInstance();
//					Element singlePlayerElement    = playersFromWebsite.get(i);
//					singlePlayer.setInitialData(singlePlayerElement.text().split(" "));
//										
//					//load weekly totals
//					for(int j = 1; j <= WEEK_NUMBER; j++)	// j Start at 1 because it's week 1
//					{
//						try
//						{
//							Document doc2 = Jsoup.parse(new URL(""), 10000);
//							Elements weeklyPlayerData = null;
//							int recordsInResultSet = 50;
//							while(true)
//							{
//								try
//								{
//									weeklyPlayerData = doc2.getElementsContainingOwnText(singlePlayer.name).parents().get(1).getElementsByAttributeValue("class", "playertableStat");					
//									if(weeklyPlayerData == null || weeklyPlayerData.size() <= 0) {
//										throw new Exception("Could not find data for player " + singlePlayer.getFullNameReversed() );
//									}
//									break;
//								}
//								catch(Throwable thr )
//								{																
////									if(thr instanceof java.net.ConnectException)
////										j--;	// retry
//								}
//							}
//
//							singlePlayer.addWeeklyTotals(weeklyPlayerData);	
//						}
//						catch(Throwable thr) { thr.printStackTrace(); singlePlayer.addWeeklyTotals(null); continue; }		
//					}
//					Document doc3 = Jsoup.parse(salaryURL, 10000);
//	
//					String fullName  = singlePlayer.getFullNameReversed();
//					String fullList  = doc3.text();
//					fullList = fullList.substring(fullList.indexOf(fullName) +fullName.length());
//					singlePlayer.setDKValue(fullList.split(";")[4]);
//					singlePlayer.calculateAverageDKPoints();
//					
//					copyVector.add(singlePlayer);
//				}
//			}
//			catch(Throwable thr)
//			{
//				thr.printStackTrace();
//			}
		}
	}
	
	public void loadPG()
	{
//		List<Element> playerList = getPlayerData("PG");	
//		pointGuardList = new ArrayList<PointGuard>(playerList.size());
//		List<List<Element>> subSets = ListUtils.partition(playerList, 3);
//		for(int i = 0; i < subSets.size(); i++)
//		{
//			PlayerDataLoad playerDataLoader = new PlayerDataLoad("PG", (List<Element>)subSets.get(i), pointGuardList);
//			threadPool.submit(playerDataLoader);				
//		}
	}

	public List<Element> getPlayerData(String type)
	{
		List<Element> playerList = null;
//		try
//		{
//			URL playerListURL = null;
//			if("PG".equals(type))
//			{
//				playerListURL = new URL("");
//			}
//			
//			Document doc = Jsoup.parse(playerListURL, 10000);
//			Elements websiteData = doc.getElementsByTag("tr");
//			if(websiteData.size() <= 0)
//				throw new Exception("No player data found from : " + playerListURL);
//			
//			playerList = websiteData.subList(1, websiteData.size());	//Starts at 1 to skip header
//		}
//		catch(Throwable thr) { thr.printStackTrace(); }
		
		return playerList;
	}
	
	public void rankPlayers()
	{
		Collections.sort(pointGuardList, new Comparator<PointGuard>() {
			public int compare(PointGuard o1, PointGuard o2)
			{
				return o2.compareTo(o1);
			}			
		});
	}
	
	public void loadInjuredPlayers()
	{
//		try
//		{
//			Document doc = Jsoup.parse(new URL(""), 10000);
//			Elements playersFromWebsite = doc.getElementsByClass("player-expanded");
//			Pattern p = Pattern.compile("[{][p][l][a][y][e][r].*[}]");
//			
//			for(Element element : playersFromWebsite)
//			{
//				Matcher m = p.matcher(element.html());
//				while( m.find() )
//				{						
//					String groupValue = m.group();
//					
//					String position = groupValue.substring((groupValue.indexOf("position: \"")+"position: \"".length()), groupValue.indexOf("position: \"")+"position: \"".length() + 2);
//					
//					String value = groupValue.substring(groupValue.indexOf("\""), groupValue.indexOf(",") -1);
//					value = value.replace("\"", "");
//					value = value.trim();
//					
//					injuryMap.put(value, groupValue);
//				}
//			}
//			if(injuryMap.isEmpty())
//			{
//				JOptionPane.showMessageDialog(null, "No players were found on injury list. Please confirm by visiting: http://...");
//			}			
//		}
//		catch(Throwable thr) { throw new RuntimeException(thr.getMessage()); }
	}
}
