package br.com.compras.repository;

import br.com.compras.entity.AppUser;
import br.com.compras.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findByOwner(AppUser owner);
}
