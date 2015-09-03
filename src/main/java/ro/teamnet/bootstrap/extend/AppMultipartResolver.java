package ro.teamnet.bootstrap.extend;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Un rezolver de multipart request
 */
public class AppMultipartResolver extends StandardServletMultipartResolver {

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        return new AppMultipartHttpServletRequest(request);
    }

}
