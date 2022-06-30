package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

//    @Autowired
//    private UserMapper userMapper;

//    @PostMapping()
//    public boolean login(@RequestBody UserDTO userDTO){
//
//        return userService.login(userDTO);
//    }

    /**
     * 登录
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);

        return Result.success(dto);
    }

    /**
     * 注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    /**
     * 新增或更新
     * @param user
     * @return
     */
    @PostMapping()
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    /**
     * 用户管理 查询全部
     * @return
     */
    @GetMapping()
    public Result index(){
        return Result.success(userService.list());
    }

//    @GetMapping()
//    public Result findone(@PathVariable Integer id){
//        return Result.success(userService.getById(id));
//    }

    /**
     *查询个人信息
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public Result findoneperson(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/betch")
    public Result deleteBetch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }


    /**
     * 寻找下一页  分页
     * @param pageNum
     * @param pageSize
     * @param username
     * @param nickname
     * @param address
     * @return
     */
    //mybatis的方式
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username){
//        pageNum = (pageNum-1)*pageSize;
//        List<User> data = userMapper.selectPage(pageNum,pageSize,username);
//        Integer total = userMapper.selectTotal(username);
//
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }

    //mybatis-plus的方式
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String nickname, @RequestParam(defaultValue = "") String address){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if (!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if (!"".equals(address)){
            queryWrapper.like("address",address);
        }

        //获取当前用户信息，输出在控制台
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("当前用户==========="+currentUser);

        return Result.success(userService.page(page,queryWrapper));
    }

    /**
     * 导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        //查出所有数据
        List<User> list = userService.list();
        //通过工具类创建writer
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createtime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

//        一次性写出list到对象excel，实验默认样式，强制输出标题
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }


    /**
     * 导入
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    public Result impor(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //方式一：通过javabean的方式读取excel的对象，但表头必须英文和javabean的属性对应
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);


        //方式二：忽略表头的中文，直接读取表的内容
//        List <List<Object>> list = reader.read(1);
//        List<User> users = CollUtil.newArrayList();
//        for (List<Object> row:  list) {
//            User user = new User();
//            user.setUsername(row.get(0).toString());
//            user.setPassword(row.get(1).toString());
//            user.setNickname(row.get(2).toString());
//            user.setEmail(row.get(3).toString());
//            user.setAddress(row.get(4).toString());
//            user.setAvatarUrl(row.get(5).toString());
//            users.add(user);
//        }
//        userService.saveBatch(users);
        return Result.success(true);



    }
}

