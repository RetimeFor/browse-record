package dao;

import java.util.ArrayList;

import entity.Items;

//商品的业务逻辑类
public interface ItemsDAO {
    // 获得所有的商品信息
    public ArrayList<Items> getAllItems();

    
 // 根据商品编号获得商品资料
    public Items getItemsById(int id);
    
  //获取最近浏览的前五条商品信息
    public ArrayList<Items> getViewList(String list);

}