package model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Queries {

	//execute stmt on n_cols id values 
	public static ArrayList<Object> nParameter(String stmt, int n_col, Object[] id){
		System.out.println(stmt+"\n");
		DatabaseConnect ak = new DatabaseConnect();
		PreparedStatement pstmt;
		ArrayList<Object> arrangement = new ArrayList<Object>();

		try {
			pstmt = ak.getCon().prepareStatement(stmt);
			for (int i=0;i< id.length;i++)
				pstmt.setObject(i+1, id[i]);
			
			ResultSet rs =  pstmt.executeQuery();   // Perform the update 

			while (rs.next()){//for(int i=0;i<n_cols;i++){
				arrangement.add(rs.getString(n_col));
			}

			pstmt.close();
		} 

		
		catch (SQLException e) {
			return null;
		}
		
		ak.CloseConnection();
		return arrangement;
		
	}
	
	public static Object oneParameter(String stmt, Object id){
		DatabaseConnect ak = new DatabaseConnect();
		PreparedStatement pstmt;
		Object arrangement = null;

		try {
			pstmt = ak.getCon().prepareStatement(stmt);
			pstmt.setObject(1, id);
			ResultSet rs =  pstmt.executeQuery();   // Perform the update 
			rs.next();
			arrangement = rs.getString(1);
			pstmt.close();
		} 

		
		catch (SQLException e) {
			System.out.println(stmt);
			System.out.println("generated an error!");
			System.exit(-1);
		}
		
		ak.CloseConnection();
		return arrangement;
	}
	
	public static Object select(String column, String table_name, String cond, Object id){
		return oneParameter("SELECT "+ column + " from " + table_name + " WHERE " + cond, id);
	}

	public static ArrayList<Object> selectUnionMultiple(String column1, String column2, String table_name, String cond1, String cond2, Object[] id, int count){
		return nParameter("SELECT "+ column1 + " from " + table_name + " WHERE " + cond1 + " UNION " +
							"SELECT " + column2 + " from " + table_name + " WHERE " + cond2, 
							count, id);
	}

	public static ArrayList<Object> selectUnionMultiple(String column1, String column2, String table_name, String other_table, String cond1, String cond2, Object[] id, int count){
		return nParameter("SELECT "+ column1 + " from " + table_name + " WHERE " + cond1 + " UNION " +
							"SELECT " + column2 + " from " + other_table + " WHERE " + cond2, 
							count, id);
	}

	public static ArrayList<Object> selectMultiple(String column, String table_name, String cond, Object[] id, int count){
		return nParameter("SELECT "+ column + " from " + table_name + " WHERE " + cond, count, id);
	}
	    
}
