package ro.teamnet.bootstrap.extend;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;


public class AppFilterHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_PARAMETER = "filters";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    private String getFilterParameter() {
        return DEFAULT_PARAMETER;
    }

    public Filters resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String[] filterStrings = webRequest.getParameterValues(getFilterParameter());

        if (filterStrings == null)
            return null;

        Filters filters = new Filters();
        ObjectMapper mapper = new ObjectMapper();
        for (String filterString : filterStrings) {
            Filter filter = null;
            try {
                filter = mapper.readValue(filterString, Filter.class);
                if (filter.getType() == null) {
                    if (filter.getValue() != null) {
                        filter.setType(Filter.FilterType.EQUAL);
                    } else if (filter.getValues() != null) {
                        filter.setType(Filter.FilterType.IN);
                    }
                }
            } catch (IOException e) {
                return null;
            }
            filters.addFilter(filter);
        }
        return filters;
    }


}
