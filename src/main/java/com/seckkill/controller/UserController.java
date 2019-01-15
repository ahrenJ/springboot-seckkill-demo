package com.seckkill.controller;

import com.seckkill.dto.UserModel;
import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.reponse.CommonReturnType;
import com.seckkill.service.UserService;
import com.seckkill.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest httpServletRequest;

    //注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam("telphone")String telphone,
                                     @RequestParam("otpCode")String otpCode,
                                     @RequestParam("name")String name,
                                     @RequestParam("gender")String gender,
                                     @RequestParam("age")String age,
                                     @RequestParam("password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和OTP
        String inSessionOtpCode= (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        UserModel userModel=new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setAge(new Integer(age));
        userModel.setGender(new Byte(gender));
        userModel.setRegisterMode("ByPhone");
        userModel.setEncrptPassword(this.encodeByMD5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String encodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定算法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder encoder=new BASE64Encoder();
        //加密
        String encryptedPassword=encoder.encode(md5.digest(password.getBytes("utf-8")));
        return encryptedPassword;
    }

    //用户登录接口
    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam("telphone")String telphone,
                                  @RequestParam("password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //参数检验
        if (StringUtils.isEmpty(telphone)|| StringUtils.isEmpty(password)){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户密码校验
        UserModel userModel=userService.validateLogin(telphone,encodeByMD5(password));
        //将登录凭证和用户信息存储在Session
        this.httpServletRequest.getSession().setAttribute("LOGIN_SUCCESS",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(null);
    }

    //获取获取OTP(动态密码)短信接口
    @RequestMapping(value = "/getotp",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);

        //使用HttpSession的方式将OTP验证码与对应用户的手机号关联
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信通道发送给用户，省略
        System.out.println("telphone="+telphone+"&otpCode="+otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
        //调用service层获取对应id的用户模型对象
        UserModel userModel= userService.getUserById(id);
        //若获取的对于用户信息不存在
        if (userModel==null){
            throw new BusinessException(EnumBussinessError.USER_NOT_EXIST);
        }

        //将用户模型对象转换为UI展示所需的视图对象
        UserVo userVo=convertFromModel(userModel);
        return CommonReturnType.create(userVo);
    }

    private UserVo convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }

    @RequestMapping(value="/gotogetotp")
    public String otpPage(){
        return "getotp";
    }

    @RequestMapping(value = "/gotoregister")
    public String registerPage(){
        return "register";
    }

    @RequestMapping(value = "/gotologin")
    public String loginPage(){
        return "login";
    }
}
