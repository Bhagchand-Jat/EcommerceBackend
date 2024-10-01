package com.ecommerce.service;

import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exceptions.CustomException;
import com.ecommerce.modals.Otp;
import com.ecommerce.modals.Token;
import com.ecommerce.modals.User;
import com.ecommerce.repositories.OtpRepository;
import com.ecommerce.repositories.TokenRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.response.ErrorResponse;
import com.ecommerce.response.Response;
import com.ecommerce.response.SuccessResponse;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class OtpService {

	private static final Logger logger = Logger.getLogger(OtpService.class.getName());

	private static final long OTP_EXPIRY_DURATION = 10 * 60 * 1000;

	private static final String RESET_URL = "http://localhost:8080/ecommerce/api/password/reset-password";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OtpRepository otpRepository;
	@Autowired
	private TokenRepository tokenRepository;

	public String generateOtp() {
		int length = 6;
		String characters = "0123456789";
		StringBuilder otp = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			otp.append(characters.charAt(random.nextInt(characters.length())));
		}
		return otp.toString();
	}

	@Transactional
	public Response sendOtpForUser(User user) {
		if (user.getOtpId() != null) {
			otpRepository.deleteById(user.getOtpId());
		}

		Long expiryTime = System.currentTimeMillis() + OTP_EXPIRY_DURATION;
		Otp otp = new Otp(generateOtp(), expiryTime);

		otpRepository.save(otp);

		userRepository.updateOtpIdByUserId(otp.getOtpId(), user.getUserId());
		try {
			emailService.sendEmail(user.getEmail(), otp.getOtp(), "otp send");
			return new SuccessResponse(otp);
		} catch (MessagingException e) {
			throw new CustomException(e.getLocalizedMessage());
		}

	}

	@Transactional
	public Response sendPasswordResetEmail(User user, String token) {

		Token tokenData = tokenRepository.save(new Token(token, user));
		String resetLink = RESET_URL + "/" + tokenData.getTokenId();
		try {
			emailService.sendEmail(user.getEmail(), resetLink, "reset Link");
			return new SuccessResponse("Reset Link Send Successfully " + resetLink);
		} catch (MessagingException e) {
			throw new CustomException(e.getLocalizedMessage());
		}

	}

	@Transactional
	public Response resendOtp(Long otpId, String email) {
		Optional<Otp> existingOtp = otpRepository.findById(otpId);

		if (existingOtp.isPresent()) {
			Otp otp = existingOtp.get();
			String otpGen = generateOtp();
			long expiryTime = System.currentTimeMillis() + OTP_EXPIRY_DURATION;
			otpRepository.updateOtpAndExpiryTimeByOtpId(otpGen, expiryTime, otp.getOtpId());
			try {
				emailService.sendEmail(email, otp.getOtp(), "otp send");
				otp.setOtp(otpGen);
				otp.setExpiryTime(expiryTime);
				return new SuccessResponse(otp);
			} catch (MessagingException e) {
				throw new CustomException(e.getLocalizedMessage());
			}
		} else {
			return new ErrorResponse(404, "Invalid User or User not Found");
		}
	}

	public boolean verifyOtp(Long otpId, String enteredOTP) {
		logger.info("verify otp init with --> " + otpId + " " + enteredOTP);

		Optional<Otp> optionalOtp = otpRepository.findByOtpIdAndOtp(otpId, enteredOTP);
		if (optionalOtp.isPresent()) {
			Otp otp = optionalOtp.get();
			logger.info("otp details---> " + otp);
			if (otp.getExpiryTime() > System.currentTimeMillis()) {
				return true;
			} else {
				return false;
			}

		}
		return false;

	}

	public void deleteByOtpId(Long otpId) {
		otpRepository.deleteById(otpId);
	}

}
