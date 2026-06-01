
package model;

public class Review {
    private int id, userId, bookId, rating;
    private String comment;

    /** Trả về số sao */
    public int getRating(){ return rating; }
    public void setRating(int rating) {
    	this.rating = rating;
    }
    // getters/setters...
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getUserId() {
    	return userId;
    }
    
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    
    public int getBookId() {
    	return bookId;
    }
    
    public void setBookId(int bookId) {
    	this.bookId = bookId;
    }
    
    public String getComment() {
    	return comment;
    }
    
    public void setComment(String comment) {
    	this.comment = comment;
    }
}
