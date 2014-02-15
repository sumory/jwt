package com.sumory.controller.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;

/**
 * @date 2013-3-28 下午7:52:05
 */
public class ControllerHelper {

    public static final Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

    /**
     * 发送json格式数据
     * 
     * @throws IOException
     */
    public static void sendJson(HttpServletResponse response, String data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write(data);
        writer.flush();
        writer.close();
    }

    /**
     * 只发送success标志位
     * 
     * @date 2013-5-19 上午9:58:48
     * @param response
     * @throws IOException
     */
    public static void sendSuccessJson(HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        result.put("success", true);
        sendJson(response, result.toString());
    }

    /**
     * 发送json格式数据
     * 
     * @throws IOException
     */
    public static void sendErrorJson(HttpServletResponse response, String msg) throws IOException {
        sendErrorJson(response, msg, null);
    }

    /**
     * 发送json格式数据
     * 
     * @throws IOException
     */
    public static void sendErrorJson(HttpServletResponse response, String msg, Exception e)
            throws IOException {
        JSONObject result = new JSONObject();
        if (e == null) {
            logger.error(msg);
        }
        else {
            logger.error(msg, e);
        }
        result.put("success", false);
        result.put("info", msg);
        ControllerHelper.sendJson(response, result.toString());
    }

    /**
     * 跳转错误页面
     * 
     * @date 2013-7-29 下午8:51:47
     * @param model
     * @param msg
     * @return
     */
    public static String goErrorPage(Model model, String msg) {
        model.addAttribute("errorMsg", msg);
        return "/error/error_show";
    }

    /**
     * 获得url前缀全路径
     * 
     * @date 2013-7-30 上午12:42:31
     * @param request
     * @return
     */
    public static String getAllPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }

    /**
     * 获得客户端ip
     * 
     * @date 2013-8-14 下午8:00:59
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            else {
                return ip;
            }
        }
        else {
            return request.getRemoteAddr();
        }
    }
}
