package DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Post;
// Data access object for the post entity
@Dao
 public interface PostDao {
 //get all posts
 @Query("SELECT * FROM post")
 List<Post> index();
 //get a post by id
 @Query("SELECT * FROM post WHERE roomID = :id")
 Post get(int id);
//insert, update and delete a post
 @Insert
 void insert(Post... posts);

 @Update
 void update(Post... posts);

 @Delete
 void delete(Post... posts);
 @Query("DELETE FROM post")
 void clear();
 }

