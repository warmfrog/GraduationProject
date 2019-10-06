# 毕业论文——移动端共享图书设计与开发——后端 Restful 服务设计实现

## 技术架构

Spring Boot + Spring Data JPA + JWT

## 项目分层

![][1]

## 资源

利用[豆瓣 API][2] 查询图书信息(已挂)。
 
利用 [Google API][3] 查询图书信息 (须修改hosts，国内书基本查不了)。

## 接口设计

### 用户相关接口/api/user/\* 设计

             **映射**             **HTTP方法**       **用途**
  ------------------------------ -------------- ------------------
                /                     GET          获取用户列表
                /                     POST           添加用户
        /check/{username}             GET        检查用户是否存在
              /{id}                   GET        获取用户相关信息
              /{id}                   PUT        更新用户相关信息
              /{id}                  DELETE       根据ID删除用户
       /{username}/release            POST         用户发布图书
       /{username}/balance            GET          查询用户余额
    /{username}/order/{ubookId}        POST           用户下单
    /{username}/cancel/{orderId}       POST         用户取消订单
       /{username}/myorder            GET          用户查看订单
    /{username}/pay/{orderId}         POST         用户支付订单
    /{username}/sign/{orderId}        POST         用户签收订单
    /{username}/delete/{orderId}      DELETE        用户删除订单
  
  ### 发布书相关接口/api/ubook/\* 设计
  
         **映射**        **HTTP方法**          **用途**
    ------------------- -------------- ------------------------
             /               POST              发布图书
     /{username}/count       GET           用户发布图书数量
           /{id}             GET          根据ID获取图书信息
       /{id}/before          GET        获取ID之前的发布的图书
        /{id}/after          GET        获取ID之后的发布的图书
           /top              GET            获取前 10 本书
           /{id}            DELETE       根据ID删除发布的图书
             /               PUT             更新发布图书  

### gbook相关接口/api/gbook/\* 设计

         **映射**         **HTTP方法**           **用途**
  ---------------------- -------------- --------------------------
      /isbn/{isbn13}          POST         根据ISBN获取图书信息
    /isbn/{isbn13}/image       GET        根据ISBN获取图书封面地址

[1]: src/main/resources/images/system_level_design.jpg
[2]: https://api.douban.com/v2/book/isbn/{isbn}
[3]: https://www.googleapis.com/books/v1/volumes?q=isbn:9781449374433

