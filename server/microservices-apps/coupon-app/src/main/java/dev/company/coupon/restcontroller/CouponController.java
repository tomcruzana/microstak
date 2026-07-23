package dev.company.coupon.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.company.coupon.dto.CouponDto;
import dev.company.coupon.service.CouponService;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

	@Autowired
	CouponService couponService;

	@GetMapping("/validate")
	public ResponseEntity<CouponDto> validateCouponCode(@RequestParam String code) throws Exception {
		CouponDto couponDto = couponService.getCoupon(code);
		return new ResponseEntity<>(couponDto, HttpStatus.OK);
	}

}
