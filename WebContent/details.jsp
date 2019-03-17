<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ page import="entity.Items"%>
<%@ page import="service.ItemsDaoImpl"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'details.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <style type="text/css">
       div{
          float:left;
          margin-left: 30px;
          margin-right:30px;
          margin-top: 5px;
          margin-bottom: 5px;
       }
       div dd{
          margin:0px;
          font-size:10pt;
       }
       div dd.dd_name
       {
          color:blue;
       }
       div dd.dd_city
       {
          color:#000;
       }
    </style>
  </head>
  
  <body>
    <h1>商品详情</h1>
    <hr>
    <center>
      <table width="750" height="60" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <!-- 商品详情 -->
          <% 
             ItemsDaoImpl itemDao = new ItemsDaoImpl();
             Items item = itemDao.getItemsById(Integer.parseInt(request.getParameter("id")));
             if(item!=null)
             {
          %>
          <td width="70%" valign="top">
             <table>
               <tr>
                 <td rowspan="4"><img src="images/<%=item.getPicture()%>" width="200" height="160"/></td>
               </tr>
               <tr>
                 <td><B><%=item.getName() %></B></td> 
               </tr>
               <tr>
                 <td>产地：<%=item.getCity()%></td>
               </tr>
               <tr>
                 <td>价格：<%=item.getPrice() %>￥</td>
               </tr> 
             </table>
          </td>
          <% 
            }
          %>
          <% 
              String list ="";
              //从客户端获得Cookies集合
              Cookie[] cookies = request.getCookies();
              //遍历这个Cookies集合
              if(cookies != null && cookies.length>0)
              {
                  for(Cookie c:cookies)
                  {
                      if(c.getName().equals("ListViewCookie"))
                      {
	                     list = c.getValue();
                      }
                  }
              }
              
              String str = request.getParameter("id");
              String[] arr1 = list.split("-");
              if(arr1 !=null && arr1.length > 0)
              {
            	  boolean isExist = false;//浏览记录是否存在
            	  int index = 0;//之前该商品的在数组中的位置
    			  for(int i = 0 ; i < arr1.length ; i++){
            		  if(arr1[i].equals(str) ){
            			  isExist = true;
            			  index = i;
            		      break;
            		  }
            	  }
    			  if(isExist){//如果浏览记录存在，先将之前的该商品浏览记录删除，再在第一条添加该商品浏览记录
    			  		String[] arr2 = new String[arr1.length];
    			  		StringBuffer sb = new StringBuffer();
    			  		arr2[0] = arr1[index];
    			  		for(int j = 1 ; j < arr1.length;j++){
    			  			if(j<=index){
    			  				arr2[j] = arr1[j-1];
    			  			}else{
    			  				arr2[j] = arr1[j];
    			  			}
    			  		}
    			  		for(int i = 0; i <arr1.length ; i++){//这个循环的目的是将list的顺序和数组一样，否则list的顺序一成不变
    			  			sb = sb.append(arr2[i] + "-");
    			  			list = sb.toString();
    			  		}
	    			  		arr1 = arr2;
    			  }else{//如果浏览记录不存在
    				  list += str + "-";
    			  }
                  if(arr1.length >= 1000) //如果浏览记录超过1000条，清零
                  {
                      list = "";
                  }
              }
             // Cookie cookie = new Cookie("ListViewCookie",list.replaceAll("," , ""));//发现报错An invalid character [44] was present in the Cookie value Cookie(原因是cookie中含有非法字符，如逗号,但是可以使用-)
              Cookie cookie = new Cookie("ListViewCookie",list);
              response.addCookie(cookie);
          
          %>
          
          <!-- 浏览过的商品 -->
          <td width="30%" bgcolor="#EEE" align="center">
             <br>
             <b>您浏览过的商品</b><br>
             <!-- 循环开始 -->
             <% 
                ArrayList<Items> itemlist = itemDao.getViewList(list);
                if(itemlist !=null && itemlist.size()>0)
                {
                   
                   for(Items i:itemlist)
                   {
                         
		      %>
             <div>
	             <dl>
	               <dt>
	                 <a href="details.jsp?id=<%=i.getId()%>"><img src="images/<%=i.getPicture()%>" width="120" height="90" border="1"/></a>
	               </dt>
	               <dd class="dd_name"><%=i.getName()%></dd> 
	               <dd class="dd_city">产地:<%=i.getCity() %>&nbsp;&nbsp;价格:<%=i.getPrice() %> ￥ </dd> 
	             </dl>
             </div>
		     <% 
                   }
                }
             %>
             <!-- 循环结束 -->
          </td>
        </tr>
      </table>
    </center>
  </body>
</html>