package by.epamtc.stanislavmelnikov.controller.filters;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.controller.factory.CommandProvider;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileExistsException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultipartDataFilter implements Filter {
    private File file;
    private static final CommandProvider commandProvider = new CommandProvider();
    private static final String maxFileKey = "max.file.size";
    private static final String maxMemKey = "max.mem.size";
    private static final String filePathKey = "img.path";
    private static final String tempRepKey = "temp.rep";
    private static final String extensionRegex = ".*(\\..*)";
    private static final String nameRegex = "[\\.:]";
    private static final String commandKey = "command";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!ServletFileUpload.isMultipartContent((request))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Map<String, String> params = new HashMap<>();
        String imgPath = PropertyLoader.getProperty(filePathKey);
        ServletFileUpload upload = initUpload();
        List fileItems;
        try {
            fileItems = upload.parseRequest(request);
            uploadData(fileItems, imgPath, params);
        } catch (FileExistsException e) {
            throw new ServletException("uploaded file already exists");
        } catch (FileNotFoundException e) {
            throw new ServletException("file not found exception", e);
        } catch (FileUploadException e) {
            throw new ServletException("fail uploading images", e);
        } catch (Exception e) {
            throw new ServletException("cannot upload files", e);
        }
        request.setAttribute("params", params);
        String command = params.get(commandKey);
        Command com = commandProvider.getCommand(command);
        com.execute(request, response);
    }

    private ServletFileUpload initUpload() throws IOException {
        long maxFileSize = Long.parseLong(PropertyLoader.getProperty(maxFileKey));
        int maxMemSize = Integer.parseInt(PropertyLoader.getProperty(maxMemKey));
        String tempRep = PropertyLoader.getProperty(tempRepKey);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File(tempRep));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("UTF-8");
        return upload;
    }


    public void uploadData(List fileItems, String imgPath, Map<String, String> params) throws Exception {
        Iterator iterator = fileItems.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            FileItem fileItem = (FileItem) iterator.next();
            if (!fileItem.isFormField()) {
                index++;
                String fileName = fileItem.getName();
                if (fileName.equals("")) continue;
                String newFileName = generateName();
                String extension = getExtension(fileName);
                file = new File(imgPath + newFileName + extension);
                fileItem.write(file);
                String path = file.getName();
                String key = "image" + index;
                params.put(key, path);
            } else {
                params.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
            }
        }
    }


    public String generateName() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String name = localDateTime.toString().replaceAll(nameRegex, "-");
        return name;
    }

    public String getExtension(String fileName) {
        String empty = "";
        Pattern pattern = Pattern.compile(extensionRegex);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return empty;
    }

    @Override
    public void destroy() {

    }
}
