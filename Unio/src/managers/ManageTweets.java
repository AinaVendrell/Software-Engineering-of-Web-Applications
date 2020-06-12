package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Tweets;
import models.User;
import utils.DAO;


public class ManageTweets {
	
	private DAO db = null ;
	
	public ManageTweets() {
		try {
			db = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			super.finalize();
			db.disconnectBD();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/* Get a tweet given its PK */
	public Tweets getTweet(Integer tid) {
		String query = "SELECT U.avatar,T.tid,T.uid,T.postDateTime,T.content FROM tweets AS T, users AS U WHERE tid = ? AND T.uid = U.uid;";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Tweets tweet = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tid);
			rs = statement.executeQuery();
			if (rs.next()) {
				tweet = new Tweets();
				tweet.setTid(rs.getInt("tid"));
				tweet.setUid(rs.getString("uid"));
				tweet.setPostDateTime(rs.getTimestamp("postDateTime"));
				tweet.setContent(rs.getString("content"));
				tweet.setAvatar(rs.getString("avatar"));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tweet;
	}
	
	/* Add a tweet */
	public void addTweet(String uid, Timestamp postDateTime, String content ) {
		String query = "INSERT INTO tweets (uid,postdatetime,content) VALUES (?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,uid);
			statement.setTimestamp(2,postDateTime);
			statement.setString(3,content);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println("No new post");
			e.printStackTrace();
		}
	}
	
	/* Add a tweet comment */
	public void addComment(String uid, Timestamp postDateTime, String content, Integer pid ) {
		// Note that this is done using https://www.arquitecturajava.com/jdbc-prepared-statement-y-su-manejo/
		String query = "INSERT INTO tweets (uid,postdatetime,content,pid) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,uid);
			statement.setTimestamp(2,postDateTime);
			statement.setString(3,content);
			statement.setInt(4,pid);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Update a tweet */
	public void updateTweet(Integer tid, String uid, Timestamp postDateTime, String content) {
		// Note that this is done using https://www.arquitecturajava.com/jdbc-prepared-statement-y-su-manejo/
		String query = "UPDATE tweets SET uid = ? , postDateTime = ? , content = ?  WHERE tid = ? ;";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,uid);
			statement.setTimestamp(2,postDateTime);
			statement.setString(3,content);
			statement.executeUpdate();
			statement.setInt(4,tid);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Delete existing tweet */
	public void deleteTweet(Integer tid) {
		// Note that this is done using https://www.arquitecturajava.com/jdbc-prepared-statement-y-su-manejo/
		String query = "DELETE FROM tweets WHERE tid = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tid);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Delete tweets from user */
	public void deleteUserTweets(String uid) {
		// Note that this is done using https://www.arquitecturajava.com/jdbc-prepared-statement-y-su-manejo/
		String query = "DELETE FROM tweets WHERE uid = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,uid);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Get tweet comments */
	public Tweets getTweetComments(Integer tid) {
		String query = "SELECT U.avatar,T.tid,T.uid,T.postDateTime,T.content FROM tweets AS T, users AS U WHERE pid = ? AND T.uid = U.uid;";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Tweets tweet = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tid);
			rs = statement.executeQuery();
			if (rs.next()) {
				tweet = new Tweets();
				tweet.setTid(rs.getInt("tid"));
				tweet.setUid(rs.getString("uid"));
				tweet.setPostDateTime(rs.getTimestamp("postDateTime"));
				tweet.setContent(rs.getString("content"));
				tweet.setAvatar(rs.getString("avatar"));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tweet;
	}
	
	// Get tweets from a user
	public List<Tweets> getUserTweets(String uid) {
		 String query = "SELECT U.avatar, T.tid,T.uid,T.postdatetime,T.content FROM tweets AS T, users AS U WHERE T.uid = ? AND T.uid = U.uid";
		 PreparedStatement statement = null;
		 List<Tweets> l = new ArrayList<Tweets>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setString(1,uid);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 Tweets tweet = new Tweets();
       		     tweet.setTid(rs.getInt("tid"));
				 tweet.setUid(rs.getString("uid"));
				 tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
				 tweet.setContent(rs.getString("content"));
				 tweet.setAvatar(rs.getString("avatar"));
				 l.add(tweet);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	// Get tweets from a user given start and end
	public List<Tweets> getUserTweets(String uid,Integer start, Integer end) {
		 String query = "SELECT U.avatar, T.tid,T.uid,T.postdatetime,T.content FROM tweets AS T, users AS U WHERE T.uid = ? AND T.uid = U.uid ORDER BY T.postdatetime DESC LIMIT ?,? ";
		 PreparedStatement statement = null;
		 List<Tweets> l = new ArrayList<Tweets>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setString(1,uid);
			 statement.setInt(2,start);
			 statement.setInt(3,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 Tweets tweet = new Tweets();
       		     tweet.setTid(rs.getInt("tid"));
				 tweet.setUid(rs.getString("uid"));
				 tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
				 tweet.setContent(rs.getString("content"));
				 tweet.setAvatar(rs.getString("avatar"));
				 
				 l.add(tweet);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	// Get tweets from user follows
	public List<Tweets> getFollowsTweets(String uid) {
		 String query = "SELECT U.avatar, T.tid,T.uid,T.postdatetime,T.content FROM users AS U, tweets AS T JOIN followers ON followers.fid = T.uid WHERE followers.uid = ? AND  T.uid = U.uid;";
		 PreparedStatement statement = null;
		 List<Tweets> l = new ArrayList<Tweets>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setString(1,uid);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 Tweets tweet = new Tweets();
   			     tweet.setTid(rs.getInt("tid"));
				 tweet.setUid(rs.getString("uid"));
				 tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
				 tweet.setContent(rs.getString("content"));
				 tweet.setAvatar(rs.getString("avatar"));
				 l.add(tweet);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	// Get tweets from user follows start and end
	public List<Tweets> getFollowsTweets(String uid, Integer start, Integer end) {
		 String query = "SELECT U.avatar,T.tid,T.uid,T.postdatetime,T.content FROM users AS U, tweets AS T JOIN followers ON followers.fid = T.uid WHERE followers.uid = ? AND T.uid = U.uid ORDER BY tweets.postdatetime DESC LIMIT ?,? ;";
		 PreparedStatement statement = null;
		 List<Tweets> l = new ArrayList<Tweets>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setString(1,uid);
			 statement.setInt(2,start);
			 statement.setInt(3,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 Tweets tweet = new Tweets();
   			     tweet.setTid(rs.getInt("tid"));
				 tweet.setUid(rs.getString("uid"));
				 tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
				 tweet.setContent(rs.getString("content"));
				 tweet.setAvatar(rs.getString("avatar"));
				 l.add(tweet);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	// Get all the tweets
	public List<Tweets> getTweets() {
		 String query = "SELECT U.avatar,T.tid,T.uid,T.postdatetime,T.content FROM tweets AS T, users AS U WHERE T.uid = U.uid;";
		 PreparedStatement statement = null;
		 List<Tweets> l = new ArrayList<Tweets>();
		 try {
			 statement = db.prepareStatement(query);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 Tweets tweet = new Tweets();
     			 tweet.setTid(rs.getInt("tid"));
				 tweet.setUid(rs.getString("uid"));
				 tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
				 tweet.setContent(rs.getString("content"));
				 tweet.setAvatar(rs.getString("avatar"));
				 l.add(tweet);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}

}