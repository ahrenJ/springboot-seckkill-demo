package com.seckkill.service;

import com.seckkill.domain.Item;
import com.seckkill.dto.ItemModel;
import com.seckkill.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {
    //create item
    ItemModel createItem(ItemModel itemModel)throws BusinessException;
    //look for item list
    List<ItemModel> getItemList();
    //look for item detail
    ItemModel getItemById(Integer id);
    //decline stock
    boolean declineStock(Integer itemId,Integer amount)throws BusinessException;
    //increase sales
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}
