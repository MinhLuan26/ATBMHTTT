package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cart stored in session keeps mapping bookId -> CartItem.
 * Methods:
 *  - add(Book, qty): add quantity (if exists, increase)
 *  - update(bookId, qty): set quantity (<=0 removes)
 *  - remove(bookId): remove item
 *  - getItems(): return collection of CartItem
 *  - getTotal(): BigDecimal sum of item totals
 *  - clear(): empty cart
 */
public class Cart implements Serializable{
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    public Cart(){}

    public void add(Book b, int qty) {
        if (b == null || qty <= 0) return;
        CartItem existing = items.get(b.getId());
        if (existing == null) {
            items.put(b.getId(), new CartItem(b, qty));
        } else {
            existing.setQuantity(existing.getQuantity() + qty);
        }
    }

    public void update(int bookId, int qty) {
        if (qty <= 0) {
            items.remove(bookId);
        } else {
            CartItem ci = items.get(bookId);
            if (ci != null) ci.setQuantity(qty);
        }
    }

    public void remove(int bookId){ items.remove(bookId); }

    public Collection<CartItem> getItems(){ return items.values(); }

    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : items.values()){
            total = total.add(ci.getTotal());
        }
        return total;
    }

    public int getSize(){ return items.size(); }

    public void clear(){ items.clear(); }
}
