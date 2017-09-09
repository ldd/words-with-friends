package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author lflore10
 *
 */
public class PredefinedQueries extends Queries {

	/*
	 * Check functions
	 */
	public static boolean checkExists(String username){
		String[] st = {username};
		ArrayList<Object> obj = Queries.selectMultiple("username", "player", "username=?", st, 1);
		return (obj.size()>0);
	}

	public static boolean checkCredentials(String username, String password){
		String[] st = {username,password};
		ArrayList<Object> obj = Queries.selectMultiple("username,password", "player", "username=? and password=?", st, 1);
		return (obj.size()>0);
	}

   public static boolean isTurn(String username, String tableName, String game_id)
    {
	   String otherTable;
	   if(tableName.equals("Player1"))
		   otherTable = "Player2";
	   else
		   otherTable = "Player1";
	   
		String[] st = {username,game_id, username, game_id};
		ArrayList<Object> obj = Queries.selectUnionMultiple("is_turn", "is_turn", tableName, otherTable, "username=? AND game_id=?", "username=? AND game_id=?", st, 1);
		
		if(obj.size()<0)
			return false;
		else{
			System.out.println(((String) obj.get(0)));
			return ((String) obj.get(0)).equals("Y");
		}
    }   

	/*
	 * Get functions
	 */
	public static String[] getFriends(String username){
		String[] st = {username,username};
		ArrayList<Object> obj = Queries.selectUnionMultiple("username1", "username2", "friends_with","friends_with", "username2=?", "username1=?", st, 1);
		Object[] ob = obj.toArray();
		String[] returnString = new String[ob.length];

		for (int i=0;i<returnString.length;i++)
			returnString[i] = (String) ob[i];
			
		return returnString;
	}

	public static Object[] getActiveGameFriends(String username) {
		String[] st = {username,username};
		ArrayList<Object> obj = Queries.selectUnionMultiple("username", "username", "player1", "player2", 
															"game_id IN (SELECT game_id from player2 where username=?)",
															"game_id IN (SELECT game_id from player1 where username=?)",
															st, 1);
		if (obj==null || obj.equals(null)){
			String[] temp = {"No active Games!"};
			return temp;
		}
					
		Object[] ob = obj.toArray();
		
		String[] returnString = new String[ob.length];

		for (int i=0;i<returnString.length;i++)
			returnString[i] = (String) ob[i];

		return returnString;
	}

	public static Object[] getInactiveGameFriends(String username) {
		return getFriends(username);
	}

	
	public static int getActiveGame(String username, String s) {
		String[] st = {username, s, username, s};
		ArrayList<Object> obj = Queries.selectMultiple("g.game_id", "player1 g", 
				"game_id IN (SELECT gg.game_id from player2 gg WHERE (g.username=? and gg.username=?) or (gg.username=? and g.username=?))", st, 1);
		if (obj==null || obj.equals(null)){
			return -1;
		}
					
		Object[] ob = obj.toArray();
		String n = (String) ob[0]; 
		return Integer.parseInt(n);
	}

	public static String getBoard(int index){
		return (String) Queries.select("mult_arrangement", "board", "board_id IN (SELECT board_id FROM game WHERE game_id=?)", index);
	}

	public static String getBoardLetters(int index){
		return (String) Queries.select("letter_placement", "game", "game_id=?", index);
	}

	//TO DO
	public static String getPlayerLetters(String table, int index){
		return (String) Queries.select("current_letters", table, "game_id=?", index);
	}

	/*
	 * Update functions
	 */
	public static void submitWord(String newBoard, int id) {
		DatabaseConnect dc = new DatabaseConnect();
		Connection con = dc.getCon();
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("UPDATE game set letter_placement=? where game_id=?");
			pstmt.setString(1, newBoard);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();   // Perform the update 
			pstmt.close();                   
		} 
		
		catch (SQLException e) {
			System.out.println("Error!");
		}
		dc.CloseConnection();
	}

   public static void updateTurn(int game_id, boolean your_turn, String table)
	    {
	   		System.out.println("you are "+table +" and your turn is" + your_turn);
	   		String s ="";
	   		if (table.equals("player1"))
	   			s = your_turn? "N": "Y";
	   		else
	   			s = your_turn? "Y": "N";
			DatabaseConnect dc = new DatabaseConnect();
			Connection con = dc.getCon();
			PreparedStatement pstmt;
			try {
				pstmt = con.prepareStatement("UPDATE player1 set is_turn=? WHERE game_id=?");
				System.out.println("UPDATE player1 set is_turn="+s + " WHERE game_id=?");
				pstmt.setString(1, s);
				pstmt.setInt(2, game_id);
				pstmt.executeUpdate();   // Perform the update 
				pstmt.close();                   
			} 
			
			catch (SQLException e) {
				System.out.println("Error!");
			}
			dc.CloseConnection();
	    }

   public static void register(String[] credentials) {
		DatabaseConnect dc = new DatabaseConnect();
		Connection con = dc.getCon();
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("INSERT INTO player VALUES(?,?,?,?,?,?,?)");
			pstmt.setString(1, credentials[0]);
			pstmt.setString(2, credentials[1]);
			pstmt.setString(3, credentials[2]);
			pstmt.setString(4, credentials[3]);
			pstmt.setString(5, credentials[4]);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.executeUpdate();   // Perform the update 
			pstmt.close();                   
		} 
		
		catch (SQLException e) {
			System.out.println("Error!");
		}
		dc.CloseConnection();
   }
	
	public static boolean addFriend(String username, String s) {
		DatabaseConnect dc = new DatabaseConnect();
		Connection con = dc.getCon();
		PreparedStatement pstmt;
		int result;
		try {
			pstmt = con.prepareStatement("INSERT INTO friends_with VALUES(?,?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, s);
			java.util.Date temp = new java.util.Date();
			Date date = new Date(temp.getTime());
			pstmt.setDate(3, date);
			result = pstmt.executeUpdate();   // Perform the update 
			pstmt.close();
		} 
		
		catch (SQLException e) {
			System.out.println("Error in INSERT function!");
			result = -1;
		}
		dc.CloseConnection();
		return (result==1);
	}
	
	public static String getHistory(String username) {
		DatabaseConnect ak = new DatabaseConnect();
		String arrangement = null;
		String call = "{call SUM_WON(?,?)}";

		try {
			CallableStatement pstmt = ak.getCon().prepareCall(call);
			pstmt.setString(1, username);
			pstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			pstmt.executeUpdate();   // Perform the update 
			int temp = pstmt.getInt(2);
			arrangement = Integer.toString(temp);
//			pstmt.close();
		} 

		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("The procedure");
			System.out.println("generated an error!");
			System.exit(-1);
		}
		
		ak.CloseConnection();
		System.out.println(arrangement);
		return arrangement;
	}

	public static int makeGame() {
		DatabaseConnect dc = new DatabaseConnect();
		Connection con = dc.getCon();
		PreparedStatement pstmt;
		int result;
		try {
			String insert = "SELECT game_id from FINAL TABLE (INSERT INTO game select max(game_id)+1, '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', 0, 0, 'english', 3, 1, '92240222633443731654422141'from game) WHERE 1=1";
			pstmt = con.prepareStatement(insert);
			ResultSet rs =  pstmt.executeQuery();   // Perform the update 
			rs.next();
			result = rs.getInt(1);
			pstmt.close();
		} 
		
		catch (SQLException e) {
			System.out.println("Error in INSERT function!");
			result = -1;
		}
		dc.CloseConnection();
		return (result);
	}

	public static boolean addPlayers(String table, String username, int game_id, String turn, String initial_letters) {
		DatabaseConnect dc = new DatabaseConnect();
		Connection con = dc.getCon();
		PreparedStatement pstmt;
		int result;
		try {
			pstmt = con.prepareStatement("INSERT INTO " + table +" (username, game_id, is_turn, points, current_letters) values (?,?,?,0,?)");
			pstmt.setString(1, username);
			pstmt.setInt(2, game_id);
			pstmt.setString(3, turn);
			pstmt.setString(4, initial_letters);
			result = pstmt.executeUpdate();   // Perform the update 
			pstmt.close();
		} 
		
		catch (SQLException e) {
			System.out.println("Error in INSERT function!");
			result = -1;
		}
		dc.CloseConnection();
		return (result==1);
	} 
	
	public static int createGame(String username, String opponent){
		//make new game
		int game_id = makeGame();
		//ad both players to player1 and player2 respectively
		addPlayers("player1", username, game_id, "Y","abcdefg");
		addPlayers("player2", opponent, game_id, "N","abcdefg");
		return game_id;
	}

	public static String getPlayerPosition(int game_id, String username) {
		Object[] st = {username, game_id};
		ArrayList<Object> obj = Queries.selectMultiple("username", "player1", "username=? AND game_id=?", st, 1);
		String result;
		result = (obj.size()>0)? "player1": "player2";
		return result;
	}
	
}
