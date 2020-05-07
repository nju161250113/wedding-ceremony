package com.wedding.usermanage.controller;

import com.wedding.model.ReturnMessage;
import com.wedding.usermanage.service.UserInfoService;
import com.wedding.usermanage.vo.BaseInfoVO;
import com.wedding.usermanage.vo.IntroductionVO;
import com.wedding.usermanage.vo.LoginVO;
import com.wedding.usermanage.vo.StandardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/userInfo")
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ResponseBody
    @RequestMapping(value = "/getStatusInfo",method = RequestMethod.GET)
    public ReturnMessage getStatusInfo(HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            return userInfoService.getUserStatus(loginVO.getUserid());
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/getBaseInfo",method = RequestMethod.GET)
    public ReturnMessage getBaseInfo(HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            return new ReturnMessage(true,userInfoService.getBaseInfo(loginVO.getUserid()));
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/changeBaseInfo",method = RequestMethod.POST)
    public ReturnMessage changeBaseInfo(@RequestBody BaseInfoVO baseInfoVO, HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            if(userInfoService.changeBaseInfo(loginVO.getUserid(),baseInfoVO).isResult()){
                loginVO.setUname_phone(baseInfoVO.getUsername());
                session.setAttribute("userinfo",loginVO);
            }
            return new ReturnMessage(true,"修改成功");
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/getStandard",method = RequestMethod.GET)
    public ReturnMessage getStandard(HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            return new ReturnMessage(true,userInfoService.getStandard(loginVO.getUserid()));
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/changeStandard",method = RequestMethod.POST)
    public ReturnMessage changeBaseInfo(@RequestBody StandardVO standardVO, HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            if(userInfoService.changeStandard(loginVO.getUserid(),standardVO).isResult()){
                return new ReturnMessage(true,"修改成功");
            }
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/getIntroduction",method = RequestMethod.GET)
    public ReturnMessage getIntroduction(HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            return new ReturnMessage(true,userInfoService.getIntroduction(loginVO.getUserid()));
        }
        return new ReturnMessage(false,"尚未登录");
    }

    @ResponseBody
    @RequestMapping(value = "/changeIntroduction",method = RequestMethod.POST)
    public ReturnMessage changeIntroduction(@RequestBody IntroductionVO introductionVO, HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(false);
        if(session!=null){
            LoginVO loginVO=(LoginVO) session.getAttribute("userinfo");
            if(userInfoService.changeIntroduction(loginVO.getUserid(),introductionVO).isResult()){
                return new ReturnMessage(true,"修改成功");
            }
        }
        return new ReturnMessage(false,"尚未登录");
    }
}
