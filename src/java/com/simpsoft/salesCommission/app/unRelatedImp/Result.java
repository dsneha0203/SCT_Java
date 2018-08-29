package com.simpsoft.salesCommission.app.unRelatedImp;

public class Result {
	private String url;
	private String title;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		String revisedContent = content.replaceAll("\n", "");
		this.content = revisedContent;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "Result[url:" + url + ",title:" + title + "content:" + content
				+ "]";
	}
}
