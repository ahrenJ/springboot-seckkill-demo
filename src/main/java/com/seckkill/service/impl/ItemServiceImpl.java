package com.seckkill.service.impl;

import com.seckkill.dao.ItemMapper;
import com.seckkill.dao.ItemStockMapper;
import com.seckkill.domain.Item;
import com.seckkill.domain.ItemStock;
import com.seckkill.dto.ItemModel;
import com.seckkill.dto.PromoModel;
import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.service.ItemService;
import com.seckkill.validator.ValidationResult;
import com.seckkill.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "item")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemStockMapper stockMapper;
    @Autowired
    private PromoServiceImpl promoService;

    @Override
    @Transactional
    @CachePut(key = "#result.id")
    public ItemModel createItem(ItemModel itemModel) throws BusinessException{

        ValidationResult result=validator.validate(itemModel);
        if (result.isError()){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,result.getErrorMsg());
        }

        Item item=convertItemFromItemModel(itemModel);
        itemMapper.insertSelective(item);
        itemModel.setId(item.getId());

        ItemStock itemStock=convertItemStockFromItemModel(itemModel);
        stockMapper.insertSelective(itemStock);

        return getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> getItemList() {
        System.out.println("no cache.");
        List<Item> itemList=itemMapper.selectAllItem();
        List<ItemModel> itemModelList=itemList.stream().map(item -> {
            ItemStock itemStock=stockMapper.selectByItemId(item.getId());
            ItemModel itemModel=convertFromDataObject(item,itemStock);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    @Cacheable(key = "#id")
    public ItemModel getItemById(Integer id) {
        System.out.println("is not from Redis.");
        Item item=itemMapper.selectByPrimaryKey(id);
        if (item==null){
            return null;
        }
        //get item'stock
        ItemStock itemStock=stockMapper.selectByItemId(id);
        //Domain->Model
        ItemModel itemModel=convertFromDataObject(item,itemStock);

        //获取正在进行活动的商品信息
        PromoModel promoModel=promoService.getPromoByItemId(itemModel.getId());
        if (promoModel!=null&&promoModel.getStatus().intValue()!=3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean declineStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow=stockMapper.declineStock(itemId,amount);
        if (affectedRow>0){
            //Update stock success
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemMapper.increaseSales(itemId,amount);
    }

    private ItemModel convertFromDataObject(Item item,ItemStock itemStock){
        ItemModel itemModel=new ItemModel();

        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStock.getStock());

        return itemModel;
    }

    private Item convertItemFromItemModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        Item item=new Item();
        BeanUtils.copyProperties(itemModel,item);
        item.setPrice(itemModel.getPrice().doubleValue());

        return item;
    }

    private ItemStock convertItemStockFromItemModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        ItemStock itemStock=new ItemStock();
        itemStock.setItemId(itemModel.getId());
        itemStock.setStock(itemModel.getStock());
        return itemStock;
    }
}
