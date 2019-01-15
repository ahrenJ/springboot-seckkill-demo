package com.seckkill.controller;

import com.seckkill.dto.ItemModel;
import com.seckkill.dto.PromoModel;
import com.seckkill.error.BusinessException;
import com.seckkill.reponse.CommonReturnType;
import com.seckkill.service.ItemService;
import com.seckkill.vo.ItemVo;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = "*",allowedHeaders = "true")
public class ItemController /*extends BaseController*/{

    @Autowired
    private ItemService itemService;

    //Create item
    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes ="application/x-www-form-urlencoded" /*CONTENT_TYPE_FORMED*/)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam("title")String title,
                                       @RequestParam("description")String description,
                                       @RequestParam("price")BigDecimal price,
                                       @RequestParam("stock")Integer stock,
                                       @RequestParam("imgUrl")String imgUrl) throws BusinessException {
        //Packaging ItemModel Object
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);;
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn=itemService.createItem(itemModel);
        ItemVo itemVo=convertItemVoFromItemModel(itemModelForReturn);

        return CommonReturnType.create(itemVo);
    }

    //Get Item by ID
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItem(@RequestParam("id")Integer id){
        ItemModel itemModel=itemService.getItemById(id);

        ItemVo itemVo=convertItemVoFromItemModel(itemModel);

        return CommonReturnType.create(itemVo);
    }

    //获取商品列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItemList(){
        List<ItemModel> itemModelList=itemService.getItemList();

        List<ItemVo> itemVoList=itemModelList.stream().map(itemModel -> {
            ItemVo itemVo=convertItemVoFromItemModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVoList);
    }

    private ItemVo convertItemVoFromItemModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        ItemVo itemVo=new ItemVo();
        BeanUtils.copyProperties(itemModel,itemVo);
        if (itemModel.getPromoModel()!=null){
            PromoModel promoModel=itemModel.getPromoModel();
            itemVo.setPromoId(promoModel.getId());
            itemVo.setPromoStatus(promoModel.getStatus());
            itemVo.setStartTime(promoModel.getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(promoModel.getPromoItemPrice());;
        }else {
            itemVo.setPromoStatus(3);
        }
        return itemVo;
    }

    @RequestMapping("/gotocreate")
    public String createPage(){
        return "createitem";
    }

    @RequestMapping("/gotoitemlist")
    public String itemListPage(){
        return "itemlist";
    }

    @RequestMapping("/gotoitemdetail")
    public String itemDetailPage(){
        return "itemdetail";
    }
}
