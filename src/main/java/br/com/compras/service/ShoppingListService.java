package br.com.compras.service;

import br.com.compras.entity.AppUser;
import br.com.compras.entity.ShoppingList;
import br.com.compras.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    /**
     * Retorna todas as listas do usuário
     */
    public List<ShoppingList> findAllByUser(AppUser user){
        return shoppingListRepository.findByOwner(user);
    }

    /**
     * Busca uma lista específica por ID
     */
    public Optional<ShoppingList> findById(Long id){
        return shoppingListRepository.findById(id);
    }

    /**
     * Cria ou atualiza uma lista de compras
     */
    public ShoppingList save(ShoppingList list){
        if(list.getCreatedAt() == null){
            list.setCreatedAt(OffsetDateTime.now());
        }
        list.setUpdatedAt(OffsetDateTime.now());
        return shoppingListRepository.save(list);
    }

    /**
     * Exclui uma lista de compras pelo ID
     */
    public void delete(Long id){
        shoppingListRepository.deleteById(id);
    }

}
