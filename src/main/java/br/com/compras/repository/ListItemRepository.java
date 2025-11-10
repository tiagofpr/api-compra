package br.com.compras.repository;

import br.com.compras.entity.ListItem;
import br.com.compras.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {
    List<ListItem> findByShoppingList(ShoppingList list);
}

