package com.siasun.controller;

import com.siasun.dao.mysql.UserDao_Mysql;
import com.siasun.entities.Address;
import com.siasun.entities.User;
import com.siasun.multidatasource.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.siasun.multidatasource.DataSourceContextHolder.DATASOURCE_POSTGRE;

/**
 * Created by hliu on 16/9/16.
 */

@Controller
//@SessionAttributes(value = {"user"},types = {String.class})
@RequestMapping("/mapping")
public class HelloWorld {

    @Autowired
    private UserDao_Mysql userDaoMysql;

    private static final String SUCCESS = "success";

    //test dynamic datasource
    @RequestMapping("/helloworld")
    public String hello() {

        System.out.println("helloworld");
        List<User> list = userDaoMysql.findAllUsers();

        System.out.println(list);

        DataSourceContextHolder.setDataSourceType(DATASOURCE_POSTGRE);

        List<User> list2 = userDaoMysql.findAllUsers();
        System.out.println(list2);

        return "success";
    }

    @RequestMapping(value ="/testMethod",method = RequestMethod.POST)
    public String testMethod() {
        System.out.print("testMethod");
        return "success";
    }

    /**
     * @PathVariable 可以映射 URL 中的占位符到目标方法的参数中
     *
     */
    @RequestMapping(value = "/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") Integer id) {
        System.out.print("testPathVariable"+id);
        return "success";
    }

    //rest风格的请求
    @RequestMapping(value = "/testRestGet/{id}",method = RequestMethod.GET)
    public String testRestGet(@PathVariable Integer id) {
        System.out.print("testRestGet");
        return SUCCESS;
    }

    @RequestMapping(value = "/testRestPost",method = RequestMethod.POST)
    public String testRestPost() {
        System.out.print("testRestPost");
        return SUCCESS;
    }

    @RequestMapping(value = "/testRestPut/{id}",method = RequestMethod.PUT)
//    @ResponseBody()//使用@ResponseBody注解不映射到success.jsp页面,返回success字符串
    public String testRestPut(@PathVariable Integer id) {
        System.out.print("testRestPut"+id);
        return SUCCESS;
    }

    @RequestMapping(value = "/testRestDelete/{id}",method = RequestMethod.DELETE)
    @ResponseBody()//使用@ResponseBody注解不映射到success.jsp页面,返回success字符串

    public String testRestDelete(@PathVariable Integer id) {
        System.out.print("testRestDelete"+id);
        return SUCCESS;
    }

    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "age",required = false,defaultValue = "0") int age) {

        return SUCCESS;
    }

    //testPojo
    @RequestMapping(value = "testPojo")
    public String testPojo(User user) {

        System.out.println("testPojo:"+user.toString());
        return SUCCESS;
    }

    //testModelAndView
    //springMVC 会把ModelAndView 中的model放到 request 域中
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {

        String viewName = SUCCESS;
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("time",new Date());

        return modelAndView;
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map) {

        map.put("names", Arrays.asList("Tom","Jerry","Mike"));
        return SUCCESS;
    }

    @RequestMapping("/testSessionAttributes")
    public String testSessionAttributes(Map<String,Object> map) {
        User user = new User(1,"liuhao","123456",25,"liuhao257@163.com",new Address("shenyang","Liaoning"));
        map.put("user",user);
        map.put("school","辽宁科技大学");
        return SUCCESS;
    }

    //!!!ModelAttributes
    /**
     * 执行流程
     * 1.执行 @ModelAttribute 注解修饰的方法,从数据库中取出对象,把它放到map中,键为user
     * 2.spring MVC 从 map中取出对象user,把表单的请求参数,赋给user的对应属性
     * 3.pring MVC 把上述对象传入目标方法的参数中
     *
     * 注意:在ModelAttributes修饰的方法中,放入map中的键必须和目标方法的参数类名一致
     *
     *
     * SpringMVC 确定目标方法 POJO 类型入参的过程
     * 1.确定一个key
     *  1).若目标方法的 POJO 类型的参数没有使用 @ModelAttribute 修饰,则key为POJO类名第一个字母的小写.
     *  2).若使用了 @ModelAttribute 修饰,则key为 @ModelAttribute注解的value属性值.
     *
     * 2.在 implicitModel 中查找 key 对应的对象,若存在,则作为入参传入
     *  1).若在 @ModelAttribute 标记的方法中在Map中保存过,且key和1确定的key一致,则会获取到
     *
     * 3.若 implicitModel 中不存在 key 对应的对象,则检查当前的Handler 是否使用@SessionAttributes
     *   注解修饰,若使用了该注解,且@SessionAttributes 注解的value属性包含了key,则会从HttpSession
     *   中来获取 key 所对应的 value 值,若存在则直接传入到目标方法的入参中,若不存在则会抛出异常
     * 4.若Handler 没有标识 @SessionAttibutes 注解或 @SessionAttributes 注解的value值中不包含
     *   key,则会通过反射来创建 POJO 类型的对象参数,传入为目标方法的参数.
     * 5.SpringMVC 会把 key 和 value 保存到implicitModel中,进而会保存到request中.
     *
     *
     * 源码分析流程
     * 1.调用 @ModelAttribute 注解修饰的方法,实际上吧 @ModelAttribute 方法中 Map 中的
     * 数据放在了 implicitModel 中.
     *
     * 2.解析请求处理器的目标参数,实际上该目标参数都来自于 WebDataBinder 对象的target属性
     * 1).创建WebDataBinder 对象:
     *   -确定objectName 属性:若传入的 attrName 属性值为"",则objectName 为类名第一个字母小写,
     *   注意: attrName,若目标方法的POJO属性使用了 @ModelAttribute来修饰,则attrName值为 @ModelAttribute
     *   的value属性值
     *
     *   -确定target 属性:
     *   在implicitModel 中查找attrName 对应的属性值,若存在,ok
     *   若不存在:则验证当前handler 是否使用了@SessionAttributes 进行修饰,若使用了,则会尝试从
     *   Session中获取attrName 所对应的属性,若session 中没有对应的属性值,则抛出异常.
     *   若Handler没有使用@SessionAttributes 进行修饰,或@SessionAttributes 中没有使用value值指定
     *   的key 和 attrName相匹配,则通过反射创建了 POJO对象
     * 2).SpringMVC 把表单的请求参数赋给了 WebDataBinder的target 对应的属性.
     * 3).SpringMVC 会把WebDataBinder 的attrName 和 target给到implicitModel,进而传到request域对象中
     * 4).把WebDataBinder 的 target 作为参数传给目标方法的入参.
     *
     */
    @RequestMapping("/testModelAttributes")
    public String testModelAttributes(User user) {

        System.out.print(user);
        return SUCCESS;
    }

    //有@ModelAttribute 标记的方法,会在每个目标方法执行前被 springMVC 调用
    @ModelAttribute
    public void getUser(@RequestParam(value = "id",required = false) Integer id,
                        Map<String,Object> map) {
        if (id != null) {
            //模拟从数据库中查找的操作
            System.out.println("fetch a user from database");
            User user = new User(1,"tom","123456",25,"liuhao257@163.com",null);
            map.put("user",user);
        }
    }
}
