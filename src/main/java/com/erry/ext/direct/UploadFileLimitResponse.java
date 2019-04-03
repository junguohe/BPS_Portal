package com.erry.ext.direct;

import ch.ralscha.extdirectspring.bean.BaseResponse;
import ch.ralscha.extdirectspring.bean.ExtDirectRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class UploadFileLimitResponse extends BaseResponse {


	private int tid;

	private String action;

	private String method;

	private Object result;

	private boolean streamResponse;

	public UploadFileLimitResponse() {
		action = "upLoadAction";
		method = "uploadMethod";
		tid = 99999;
		setType("rpc");
	}

	public UploadFileLimitResponse(ExtDirectRequest directRequest) {
		action = directRequest.getAction();
		method = directRequest.getMethod();
		tid = directRequest.getTid();
		setType(directRequest.getType());
	}

	public String getAction() {
		return action;
	}

	public String getMethod() {
		return method;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getTid() {
		return tid;
	}

	@JsonIgnore
	public boolean isStreamResponse() {
		return streamResponse;
	}

	public void setStreamResponse(boolean streamResponse) {
		this.streamResponse = streamResponse;
	}

	@Override
	public String toString() {
		return "ExtDirectResponse [tid=" + tid + ", action=" + action + ", method=" + method + ", result=" + result
				+ "]";
	}
	
}
