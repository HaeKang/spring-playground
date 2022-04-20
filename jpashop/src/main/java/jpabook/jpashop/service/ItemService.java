package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // 변경 감지 기능 ~> merge보다 이런식으로 하는것을 추천
    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity ){
        Item findItem = itemRepository.findOne(itemId);
        
        // findItem.change(name, price, stockQuantity) 이런 change 메서드를 따로 만드는게 좋음 setter 쓰는것 보다 ~> 추적 어려워짐
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        return findItem;

    }

    public List<Item> findItem(){
        return itemRepository.findAll();
    }
    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

}
