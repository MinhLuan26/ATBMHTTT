package dao;

import model.Review;
import java.util.*;

public interface ReviewDAO {
    int add(Review r);
    List<Review> getByBook(int bookId);
}