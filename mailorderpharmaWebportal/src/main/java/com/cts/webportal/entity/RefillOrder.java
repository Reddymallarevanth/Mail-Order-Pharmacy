package com.cts.webportal.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefillOrder {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
	private Date refilledDate;
	
	private Boolean payStatus;
	
	private long sub_id;
	
	private int quantity;
	
	private String memberId;


}
