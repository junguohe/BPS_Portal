package com.erry.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import ch.ralscha.extdirectspring.bean.BaseResponse;
import ch.ralscha.extdirectspring.controller.ConfigurationService;
import ch.ralscha.extdirectspring.controller.RouterController;
import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;

import com.erry.ext.direct.UploadFileLimitResponse;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyMappingException extends SimpleMappingExceptionResolver {
	
	private ConfigurationService findConfigurationService() {
		return (ConfigurationService) SpringContextHolder.getBean(ConfigurationService.class);
	}
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {
		
		if((ex instanceof MaxUploadSizeExceededException || ex instanceof SizeLimitExceededException)){
			UploadFileLimitResponse directResponse = new UploadFileLimitResponse();
			
			
			handleException(directResponse, ex);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			directResponse.setResult(result);
			try {
				writeJsonResponse(response, directResponse, true, ExtDirectSpringUtil.isMultipart(request));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
		
		return super.doResolveException(request, response, handler, ex);
	}
	
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
			ex.printStackTrace();
	    //this.logger.error(buildLogMessage(ex, request), ex);
	}
	
	private void handleException(BaseResponse response, Exception e) {
		Throwable cause;
		if (e.getCause() != null) {
			cause = e.getCause();
		} else {
			cause = e;
		}
		
		response.setType("exception");
		response.setMessage(findConfigurationService().getConfiguration().getMessage(cause));

		if (findConfigurationService().getConfiguration().isSendStacktrace()) {
			response.setWhere(ExtDirectSpringUtil.getStackTrace(cause));
		} else {
			response.setWhere(null);
		}
	}
	
	@SuppressWarnings("resource")
	public void writeJsonResponse(HttpServletResponse response, Object responseObject, boolean streamResponse,
			boolean isMultipart) throws IOException, JsonGenerationException, JsonMappingException {

		if (isMultipart) {
			response.setContentType(RouterController.TEXT_HTML.toString());
			response.setCharacterEncoding(RouterController.TEXT_HTML.getCharSet().name());

			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			bos.write("<html><body><textarea>".getBytes(ExtDirectSpringUtil.UTF8_CHARSET));

			String responseJson = findConfigurationService().getJsonHandler().getMapper().writeValueAsString(responseObject);

			responseJson = responseJson.replace("&quot;", "\\&quot;");
			bos.write(responseJson.getBytes(ExtDirectSpringUtil.UTF8_CHARSET));
			bos.write("</textarea></body></html>".getBytes(ExtDirectSpringUtil.UTF8_CHARSET));

			response.setContentLength(bos.size());
			FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
		} else {

			response.setContentType(RouterController.APPLICATION_JSON.toString());
			response.setCharacterEncoding(RouterController.APPLICATION_JSON.getCharSet().name());

			ObjectMapper objectMapper = findConfigurationService().getJsonHandler().getMapper();

			ServletOutputStream outputStream = response.getOutputStream();

			if (!streamResponse) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
				JsonGenerator jsonGenerator = objectMapper.getFactory().createJsonGenerator(bos, JsonEncoding.UTF8);
				objectMapper.writeValue(jsonGenerator, responseObject);
				response.setContentLength(bos.size());
				outputStream.write(bos.toByteArray());
				jsonGenerator.close();
			} else {
				JsonGenerator jsonGenerator = objectMapper.getFactory().createJsonGenerator(outputStream,
						JsonEncoding.UTF8);
				objectMapper.writeValue(jsonGenerator, responseObject);
				jsonGenerator.close();
			}

			outputStream.flush();
		}
	}

	
}
