package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {

    private Book book;
    private int quantity;

    public CartItem() {}

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotal(){
        if (book == null || book.getPrice() == null) return BigDecimal.ZERO;
        return book.getPrice().multiply(new BigDecimal(quantity));
    }
}