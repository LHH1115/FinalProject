package com.example.demo.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.rentcar.dao.RentcarDAO;
import com.example.demo.rentcar.vo.RentcarVO;

import lombok.Setter;

@Controller
@Setter
public class RentcarController_manager {
	
	@Autowired
	RentcarDAO rentcarDAO;
	
	
	@GetMapping("/admin/rentcar/update")
	public ModelAndView update(int carNo) {
		ModelAndView mav = new ModelAndView("/admin/rentcarUpdate.html");
		
		return mav;
	}
	
	@PostMapping("/admin/rentcar/update")
	public ModelAndView update(RentcarVO vo, HttpServletRequest request) {
		System.out.println("vo:"+vo);
		ModelAndView mav = new ModelAndView("redirect:/rentcar/Detail?carNo="+vo.getCarNo());
		String fname=vo.getUploadFile().getOriginalFilename();
		String old_realPath = vo.getRealPath();
		System.out.println("www:"+old_realPath);

		if(fname!=null && !fname.equals("")) {
			vo.setPhotoPath(fname);
			int index=old_realPath.lastIndexOf("/")+1;
			String new_realPath = old_realPath.replace(vo.getRealPath().substring(index), fname);
			vo.setRealPath(new_realPath);
			System.out.println("updatedvo:"+vo);
		}
		
		int re=rentcarDAO.updateById(vo);
		
		if(re>0) {
			if(fname!=null && !fname.equals("")) {
				String path = request.getServletContext().getRealPath("photo");
				String old_filePath=path+old_realPath.substring(6);
				String new_filePath=path+vo.getRealPath().substring(6);
				
				File file = new File(old_filePath);
				file.delete();
				try {
					byte[] data=vo.getUploadFile().getBytes();
					System.out.println("new_filepath:"+new_filePath);
					FileOutputStream fos = new FileOutputStream(new_filePath);
					fos.write(data);
					fos.close();
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("error:"+e.getMessage());
				}
			}	
		}
		
		return mav;
	}
	
	@GetMapping("/admin/rentcar/delete")
	public ModelAndView delete(RentcarVO vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/rentcar/Detail?carNo="+vo.getCarNo());
		
		if(vo.getPhotoPath()!=null && !vo.getPhotoPath().equals("")) {
			String path = request.getServletContext().getRealPath("photo");
			String old_realPath = vo.getRealPath();
			String old_filePath=path+old_realPath.substring(6);
			File file = new File(old_filePath);
			file.delete();
		}
		rentcarDAO.deleteById(vo.getCarNo());
		
		return mav;
	}
	
}
