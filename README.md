## 介绍
JDoc是基于JAVA开发的，针对主流web框架做的接口文档生成利器，只需极简配置就可以实现文档的生成，且对项目零入侵，主要目的是解决协作开发中接口文档及时更新

## 使用说明

### 使用依赖

```
<dependency>
    <groupId>com.jdocapi</groupId>
    <artifactId>jdoc</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 配置说明

```
#指定java文件路径
java.source=src/main/java
#指定需要生成文档的包路径
package.name=com.wht.hello
#指定采用的框架类型,springmvc,service两种框架,springvmc:针对的是Controller。service针对的是Dubbo
parser.name=springmvc
#指定文档输出路径
out.path=src/main/api.html
#指定模版生成器，目前仅支持html
parser.formater=html
#指定请求类型默认为form
request.type=json
#生成文档时是否对接口进行调用测试，默认false
use.analysis
```


### 注释标签
1. @title，菜单栏
2. @param，基本类型请求参数
3. @paramBean，复杂类型请求参数
4. @respBody，返回示例
5. @respParam，基本类型返回
6. @respParamBean，复杂类型返回
7. @respSubParamBean，范型复杂类型返回
8. @see，组合复杂类型使用
9. @return，返回参数描述

### 注释方式
类、方法、属性的注释都以段落注释的方式

```
/**
*
*
*/
```

### 类注释
>类都注释是为了说明该类的使用范围,或者属于哪一个模块
```
/**
 * 用户模块
 * 
 * @author haoting.wang
 * @Date 2017年3月27日
 */
@Controller
@RequestMapping("/user")
public class UserController {

}

Jdoc 对此类的 用户模块 会进行解析，解析成一个菜单，其方法为子菜单
```

### 方法注释
> 方法注释需要说明该方法的用途，注意点

1. 如果方法的请求参数为基本数据类型(包括Date)时，使用@param
2. 如果方法的请求参数为复杂数据类型，使用@paramBean、@see
3. 如果方法的返回对象为基本类型，使用@respParam
4. 如果方法的返回对象为复杂对象,使用@respParamBean、@see
5. 如果方法的返回对象为复杂对象并且含有范型，使用@respParamBean、@respSubParamBean、@see

### 属性注释
> 属性注释需要说明该属性的含义
```
public class User {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String  username;

    /**
     * 密码
     */
    private String  password;

    /**
     * 班主任
     * 
     * @see com.wht.hello.bean.Teacher
     */
    private Teacher teacher;
    
}

```

#### Demo1
```
    /**
     * 用于添加用户功能
     *
     * @title 新增用户
     * @param username|用户名|String|必填
     * @param password|密码|String|必填
     * @respBody {"code":"100000","data":"","message":"新增成功"}
     */
    @RequestMapping("/add")
    @ResponseBody
    public MessageResp<String> add(String username,String password){
        MessageResp<String> resp = new MessageResp<String>();
        resp.setData("");
        resp.setCode("100000");
        resp.setMessage("新增成功");
        return resp;
    }
```
![image.png-95.3kB](http://static.zybuluo.com/1234567890/x4cvq511r7vx71niikhxz4cb/image.png)

#### Demo2
```
/**
* 通过用户id查询用户功能
* 
* @title 查询ID查用户
* @respParam username 用户名 String 必填
* @respParam password 密码 String 必填
* @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
*/
@RequestMapping("/getUserById")
public MessageResp<User> getUserById() {
    MessageResp<User> resp = new MessageResp<User>();
    User user = new User();
    user.setUsername("13811111111");
    user.setPassword("123456");
    resp.setData(user);
    resp.setCode("100000");
    resp.setMessage("获取成功");
    return resp;
}
```
![image.png-107.4kB](http://static.zybuluo.com/1234567890/wjesu3s815d8hirvz3z67gli/image.png)

#### Demo3
```
/**
* 通过用户id查询用户功能. 测试respSubParamBean
*
* @title 查询ID查用户
* @param userId 用户id
* @respParamBean MessageResp
* @respSubParamBean User
* @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
* @see com.wht.hello.bean.User
* @see com.wht.hello.common.MessageResp
*/
@RequestMapping("/getUserById")
public MessageResp<User> getUser(String userId) {
    MessageResp<User> resp = new MessageResp<User>();
    resp.setData(new User());
    resp.setCode("100000");
    resp.setMessage("获取成功");
    return resp;
}
```
![image.png-128.7kB](http://static.zybuluo.com/1234567890/6u3ojss41oi7fu6ryshxj6rk/image.png)
