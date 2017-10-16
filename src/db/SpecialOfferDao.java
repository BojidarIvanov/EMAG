package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import model.SpecialOfferPojo;


public class SpecialOfferDao {
	
	private static SpecialOfferDao instance;
	private SpecialOfferDao(){}
	
	public static synchronized SpecialOfferDao getInstance(){
		if(instance == null){
			instance = new SpecialOfferDao();
		}
		return instance;
	}
		
	public void addCategory(SpecialOfferPojo specialOffer) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO special_offers (promotional_code, new_price, start_date, expiry_date) VALUES (?,?,?,?)");
			ps.setString(1, specialOffer.getCode());
		    ps.setBigDecimal(2, specialOffer.getNewPrice());
		    ps.setString(3, specialOffer.getStartDate().toString());
		    ps.setString(4, specialOffer.getEndDate().toString());
		    ps.executeUpdate();
		    ps.close();
	}

	
	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		SpecialOfferDao sod = SpecialOfferDao.getInstance();
		SpecialOfferPojo sop = new SpecialOfferPojo("ChristmasEve", 5, new BigDecimal("10"), LocalDate.now(), LocalDate.now().plusDays(10));
		SpecialOfferPojo sop1 = new SpecialOfferPojo("NEWYear2018", 5, new BigDecimal("15"), LocalDate.now(), LocalDate.now().plusDays(10));
		SpecialOfferPojo sop2 = new SpecialOfferPojo("Easter2018", 5, new BigDecimal("20.255878"), LocalDate.now(), LocalDate.now().plusDays(10));

		sod.addCategory(sop);
		sod.addCategory(sop1);
		sod.addCategory(sop2);
		System.out.println("End");

	}
}
