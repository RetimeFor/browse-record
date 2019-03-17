package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import dao.ItemsDAO;
import entity.Items;
import jdbc.util.DBHelper;

public class ItemsDaoImpl implements ItemsDAO {

	public ArrayList<Items> getAllItems() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Items> list = new ArrayList<Items>(); // 商品集合
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from picture_info_items;"; // SQL语句
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Items item = new Items();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCity(rs.getString("city"));
                item.setNumber(rs.getInt("number"));
                item.setPrice(rs.getInt("price"));
                item.setPicture(rs.getString("picture"));
                list.add(item);// 每次遍历时把一个商品加入集合
            }
            return list; // 返回集合。
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            //finally释放资源（从小到大释放资源rs,stmt。注意连接对象conn不要释放，因为它是单例模式，一旦释放了，后面的方法就没法使用了）
            // 释放数据集对象
            if (rs != null) {
                try {
                    rs.close();//这个地方会抛出异常
                    rs = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            // 释放语句对象
            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
    
 // 根据商品编号获得商品资料
    public Items getItemsById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from picture_info_items where id=?;"; // SQL语句
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Items item = new Items();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCity(rs.getString("city"));
                item.setNumber(rs.getInt("number"));
                item.setPrice(rs.getInt("price"));
                item.setPicture(rs.getString("picture"));
                return item;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // 释放数据集对象
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            // 释放语句对象
            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
    
    
  //获取最近浏览的前五条商品信息
    public ArrayList<Items> getViewList(String list)
    {
        
        ArrayList<Items> itemlist = new ArrayList<Items>();
        int iCount=5; //每次返回前五条记录
        if(list !=null && list.length() > 0)
        {
            String[] arr = list.split("-");
            
            //如果商品记录大于等于5条
            if(arr.length>=5)
            {
               for(int i=arr.length-1;i>=arr.length-iCount;i--)
               {
                  itemlist.add(getItemsById(Integer.parseInt(arr[i])));  
               }
            }
            else
            {
                for(int i =0 ;i < arr.length;i++)
                {
                    itemlist.add(getItemsById(Integer.parseInt(arr[i])));
                }
            }
            return itemlist;
        }
        else
        {
            return null;
        }
        
    }
}
