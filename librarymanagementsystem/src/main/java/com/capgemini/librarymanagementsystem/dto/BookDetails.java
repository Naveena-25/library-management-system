package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;

public class BookDetails implements Serializable {

	private int bookId;
	private String bookName;
	private String author;
	private String publisherName;
	private boolean isBookAvailable;

	public BookDetails() {

	}

	public BookDetails(int bookId, String bookName, String author, String publisherName, boolean isBookAvailable) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.publisherName = publisherName;
		this.isBookAvailable = isBookAvailable;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public boolean isBookAvailable() {
		return isBookAvailable;
	}

	public void setBookAvailable(boolean isBookAvailable) {
		this.isBookAvailable = isBookAvailable;
	}

	@Override
	public String toString() {
		return "BookDetails [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", publisherName="
				+ publisherName + ", isBookAvailable=" + isBookAvailable + "]";
	}

}
