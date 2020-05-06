package com.capgemini.librarymanagementhibernate.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "library_books")
public class BookDetails implements Serializable{
	@Id
	@Column
	private int bookId;
	@Column
	private String bookName;
	@Column
	private String author;
	@Column
	private String publisher;
	@Column
	private boolean isAvailable;
}
