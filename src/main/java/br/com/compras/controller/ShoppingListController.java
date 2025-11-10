package br.com.compras.controller;

import br.com.compras.entity.AppUser;
import br.com.compras.entity.ListItem;
import br.com.compras.entity.ShoppingList;
import br.com.compras.repository.AppUserRepository;
import br.com.compras.repository.ShoppingListRepository;
import br.com.compras.service.ShoppingListService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/list")
@CrossOrigin(origins = "*") //permite acesso do flutter
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Lista todas as listas de compras de um usuário
    */

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingList>> getAllListByUser(@PathVariable Long userId){
        Optional<AppUser> userOpt = appUserRepository.findById(userId);
        if(userOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<ShoppingList> lists = shoppingListService.findAllByUser(userOpt.get());
        return ResponseEntity.ok(lists);
    }

    /**
     * Busca uma lista específica pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getListById(@PathVariable Long id) {
        return shoppingListService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova lista de compras
     */
    @PostMapping
    public ResponseEntity<ShoppingList> createList(@RequestBody ShoppingList list) {
        if (list.getOwner() == null || list.getOwner().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<AppUser> userOpt = appUserRepository.findById(list.getOwner().getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        list.setOwner(userOpt.get());
        ShoppingList created = shoppingListService.save(list);
        return ResponseEntity.ok(created);
    }

    /**
     * Atualiza uma lista existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateList(@PathVariable Long id, @RequestBody ShoppingList list) {
        Optional<ShoppingList> existingOpt = shoppingListService.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ShoppingList existing = existingOpt.get();
        existing.setTitle(list.getTitle());
        existing.setDescription(list.getDescription());
        existing.setScheduledDate(list.getScheduledDate());
        existing.setStatus(list.getStatus());
        existing.setUpdatedAt(list.getUpdatedAt());

        ShoppingList updated = shoppingListService.save(existing);
        return ResponseEntity.ok(updated);
    }

    /**
     * Exclui uma lista de compras
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        Optional<ShoppingList> existingOpt = shoppingListService.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        shoppingListService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
