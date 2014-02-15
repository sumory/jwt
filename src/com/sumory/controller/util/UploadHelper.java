package com.sumory.controller.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 上传文件处理
 * 
 * @author sumory.wu
 * @date 2013-3-24 下午7:55:38
 */
@Component
public class UploadHelper {

    private static Logger logger = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * 
     * 保存multipart/form-data类型的表单中提交的文件， 并以key，value的格式返回从表单中解析出的参数
     * 
     * @param request
     * @param maxSize request最大长度
     * @param maxFileSize 文件大小限制
     * @param params 保存表单中解析出的参数
     * @return 从表单中解析出的文件
     * @throws Exception
     */
    private List<FileItem> handleFormUpload(HttpServletRequest request, int maxFileSize,
            Map<String, String> params) throws Exception {

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        fileItemFactory.setSizeThreshold(1024);
        upload.setFileSizeMax(maxFileSize);
        List<FileItem> FileItems = null;
        try {
            @SuppressWarnings("rawtypes")
            List fileItems = upload.parseRequest(request);
            FileItems = new ArrayList<FileItem>();
            for (int i = 0; i < fileItems.size(); i++) {
                FileItem item = (FileItem) fileItems.get(i);
                if (!item.isFormField()) {
                    FileItems.add(item);
                }
                else {
                    params.put(item.getFieldName(), item.getString("UTF-8"));
                }
            }
        }
        catch (SizeLimitExceededException e) {
            throw new Exception("文件大小超出限制", e);
        }
        catch (FileSizeLimitExceededException e) {
            throw new Exception("文件大小超出限制", e);
        }
        catch (Exception e) {
            throw new Exception("上传文件失败", e);
        }
        return FileItems;
    }

    /**
     * 处理单个文件上传，保存到文件系统中，首先验证文件合法性，验证非法时抛出相应异常，验证通过后保存并且返回文件名称和ID
     * 
     * @param request
     * @param response
     * @param maxSize
     * @param maxFileSize
     * @return 文件名称
     * @throws Exception
     */
    public String saveSingleUpload(String fileNamePrefix, HttpServletRequest request,
            int maxFileSize, long fileAvailableTime, Map<String, String> params, String folder) throws Exception {
        // String path = request.getSession().getServletContext().getRealPath("/resources/uploads");
        String path = request.getSession().getServletContext().getRealPath("/resources/uploads" + folder);
        List<FileItem> fileItems = null;
        try {
        	//如果目录不存在，则创建一个新的目录
        	File dir = new File(path);
            if (!dir.exists()) {
            	dir.mkdirs();
            }
        	
            fileItems = handleFormUpload(request, maxFileSize, params);
            if (fileItems.size() <= 0) {
                throw new Exception("空文件列表.");
            }
            // 单个上传
            FileItem item = fileItems.get(0);
            String fileName = item.getName();
            String suffix = "";
            // 某些版本浏览器上传文件时，http报文中的文件名称是全路径，会提示文件名不合法
            if (fileName.indexOf("\\") >= 0) {
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
            }
            fileName = fileName.trim();

            // 得到文件后缀
            if (fileName.indexOf(".") >= 0) {
                suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            if (item.getSize() <= 0) {
                throw new Exception("空文件.");
            }

            String saveFileName = fileNamePrefix + java.util.UUID.randomUUID().toString() + "."
                    + suffix;
            item.write(new File(path, saveFileName));// 执行上传....

            return saveFileName;
        }
        catch (Exception e) {
            logger.error("上传文件失败", e);
            throw new Exception("上传文件失败", e);
        }
        finally {
            if (fileItems != null) {
                for (FileItem item : fileItems) {
                    item.delete();
                }
            }
        }
    }

}
