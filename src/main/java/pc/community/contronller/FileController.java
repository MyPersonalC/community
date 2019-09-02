package pc.community.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pc.community.dto.FileDTO;
import pc.community.exception.CustomizeErrorCode;
import pc.community.exception.CustomizeException;
import pc.community.provider.TencentProvider;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private TencentProvider tencentProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        if (file==null){
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_NOT_FOUND);
        }
        try {
            String fileName = tencentProvider.upload(file);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(tencentProvider.download(fileName));
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(file.getContentType());
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/default.png");
        return fileDTO;
    }
}
