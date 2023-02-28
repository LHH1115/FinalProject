package com.example.demo.rentcar.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.rentcar.db.DBManager;
import com.example.demo.rentcar.vo.RentcarVO;


@Repository
public class RentcarDAO {
	
//	public int getNextNo() {
//		return DBManager.getNextNo();
//	}
	
	public RentcarVO findByCarno(int no){
		return DBManager.findByCarno(no);
	}
	
	public List<String> findRentByCarno(int carno){
		return DBManager.findRentByCarno(carno);
	}
	
	public RentcarVO findRentInfoByStorename(String name){
		return DBManager.findRentInfoByStorename(name);
	}
	
	public List<Integer> findPopularCar(){
		return DBManager.findPopularCar();
	}
	
	public List<Integer> findSameStarCar(int no){
		return DBManager.findSameStarCar(no);
	}
	
	public int updateById(RentcarVO vo) {
		return DBManager.updateById(vo);
	}
	
	public int deleteById(int no) {
		return DBManager.deleteById(no);
	}
	
	public List<RentcarVO> searchC(HashMap<String, Object> map){
		return DBManager.searchC(map);
	}
	
	public int countC(String category) {
		return DBManager.countC(category);
	}
	
	public List<RentcarVO> searchName(HashMap<String, Object> map) {
		return DBManager.searchName(map);
	}
	
	public int countName(String modelname) {
		return DBManager.countName(modelname);
	}
	
	public List<RentcarVO> searchCatAndName(HashMap<String, Object> map){
		return DBManager.searchCatAndName(map);
	}
	
	public int countCatAndName(HashMap<String, Object> map) {
		return DBManager.countCatAndName(map);
	}
	
}


















