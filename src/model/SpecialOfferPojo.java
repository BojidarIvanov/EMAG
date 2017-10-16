package model;

import java.math.BigDecimal;
import java.time.LocalDate;



public class SpecialOfferPojo {

	private String promoCode;
	private int productID;
	private BigDecimal newPrice;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
	private LocalDate startDate;
	private LocalDate expiryDate;

	public SpecialOfferPojo(String promoCode, int productID, BigDecimal newPrice, LocalDate date, LocalDate date2) {
		super();
		this.promoCode = promoCode;
		this.productID = productID;
		this.newPrice = newPrice;
		this.startDate = date;
		this.expiryDate = date2;
	}

	private BigDecimal rounded(BigDecimal aNumber) {
		return aNumber.setScale(DECIMALS, ROUNDING_MODE);
	}

	public String getCode() {
		return promoCode;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return expiryDate;
	}
	
	
	
	

}