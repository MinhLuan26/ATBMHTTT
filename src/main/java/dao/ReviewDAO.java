
package dao;

import model.Review;
import java.util.*;

/** DAO đánh giá */
public interface ReviewDAO {
    int add(Review r);
    List<Review> getByBook(int bookId);
}
