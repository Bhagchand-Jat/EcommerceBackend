/**
 * 
 */
package com.ecommerce.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exceptions.CustomException;
import com.ecommerce.exceptions.GlobalExceptionHandler;
import com.ecommerce.exceptions.PageNotFoundException;
import com.ecommerce.exceptions.UserAlreadyExistException;
import com.ecommerce.modals.Product;
import com.ecommerce.modals.ProductImage;
import com.ecommerce.modals.ProductWithImage;
import com.ecommerce.modals.User;
import com.ecommerce.modals.ValidationGroups;
import com.ecommerce.repositories.ProductImageRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.response.ErrorResponse;
import com.ecommerce.response.Response;
import com.ecommerce.response.SuccessResponse;
import com.ecommerce.service.OtpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * 
 */
@Validated
@RequestMapping("/ecommerce/api/seller")
@RestController
public class SellerController {
	private static final Logger logger = Logger.getLogger(SellerController.class.getName());
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductImageRepository productImageRepository;
	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository sellerRepository;

	@Autowired
	private GlobalExceptionHandler exceptionHandler;

	@Transactional(noRollbackFor = MethodArgumentNotValidException.class)
	@PostMapping("/signup")
	public ResponseEntity<Response> signUp(@Validated(ValidationGroups.Create.class) @RequestBody User seller) {

		seller.setUserId(System.currentTimeMillis());
		seller.setPassword(passwordEncoder.encode(seller.getPassword()));
		seller.setCreatedOn(LocalDateTime.now());
		Optional<User> sellerByEmail = sellerRepository.findByEmail(seller.getEmail());
		if (sellerByEmail.isPresent()) {
			if (sellerByEmail.get().isVerified()) {
				return exceptionHandler.userAlreadyExist(new UserAlreadyExistException("User Already Exist"));
			} else {
				// otpService.deleteByUserId(userByEmail.get().getUserId());
				sellerRepository.deleteById(sellerByEmail.get().getUserId());
			}

		}
		seller.setRoles("SELLER");
		sellerRepository.save(seller);

		if (sellerRepository.existsById(seller.getUserId())) {

			return ResponseEntity.ok(otpService.sendOtpForUser(seller));

		} else {
			logger.info("User is : ---> " + seller.toString());
			return exceptionHandler.customException(new CustomException("User Not found"), HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(HttpServletRequest request) {

		logger.info("seller login success");
		String token = (String) request.getAttribute("token");

		Map<String, String> result = new HashMap<>();

		result.put("token", token);
		return ResponseEntity.ok(new SuccessResponse(result));

	}

	@Transactional
	@PostMapping("/otp-authenticate")
	public ResponseEntity<Response> otpAuthentication(HttpServletRequest request) {
		logger.info("otp authenticated successfully");
		User seller = (User) request.getAttribute("user");
		int results = sellerRepository.updateIsVerifiedByUserId(true, seller.getUserId());
		if (results == 0) {
			logger.info("Update unsuccessful");
		}
		String token = (String) request.getAttribute("token");
		Map<String, String> result = new HashMap<>();

		result.put("token", token);
		return ResponseEntity.ok(new SuccessResponse(result));
	}

	@Transactional
	@PostMapping("/otp/send")
	public ResponseEntity<Response> sendOtp(HttpServletRequest request) {
		User seller = (User) request.getAttribute("user");

		return ResponseEntity.ok(otpService.sendOtpForUser(seller));
	}

	@Transactional
	@PostMapping("/otp/resend")
	public ResponseEntity<Response> resendOtp(HttpServletRequest request, @RequestParam Long otpId) {
		User user = (User) request.getAttribute("user");

		return ResponseEntity.ok(otpService.resendOtp(otpId, user.getEmail()));
	}

	@GetMapping("/products")
	public ResponseEntity<Response> getAllProducts(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		User seller = (User) request.getAttribute("user");
		PageRequest pageable = PageRequest.of(page, size);
		Page<Product> productPage = productRepository.getAllBySeller(seller, pageable);
		if (page >= productPage.getTotalPages() && productPage.getTotalPages() > 0) {
			throw new PageNotFoundException("Page " + page + " does not exist.");
		}
		;
		List<ProductWithImage> productsWithImages = productPage.stream().map(product -> {
			List<ProductImage> images = productImageRepository.getAllByProduct(product);
			images.forEach(image -> image.setProduct(null));
			return new ProductWithImage(product, images);

		}).toList();

		return ResponseEntity.ok(new SuccessResponse(productsWithImages));
	}

	@GetMapping("/primaryPoductImage/{productId}")
	public ResponseEntity<Response> getPrimaryProductImage(HttpServletRequest request,
			@PathVariable("productId") Long productId) {
		Product product = new Product();
		product.setProductId(productId);
		Optional<ProductImage> productImage = productImageRepository.getByProductAndPrimary(product, true);
		if (productImage.isEmpty()) {
			return ResponseEntity.ok(new ErrorResponse(400, "Primary Image Not found"));
		}
		return ResponseEntity.ok(new SuccessResponse(productImage.get()));
	}

	@Transactional(noRollbackFor = { MethodArgumentNotValidException.class, DataIntegrityViolationException.class })
	@PostMapping("/addProduct")
	public ResponseEntity<Response> addProduct(HttpServletRequest request, @Valid @ModelAttribute Product product,
			@RequestPart(name = "productImages", required = false) MultipartFile[] images) {
		product.setProductId(System.currentTimeMillis());
		User seller = (User) request.getAttribute("user");
		product.setSeller(seller);

		logger.info("Seller " + seller);
		logger.info("Product " + product);

		Set<ProductImage> imgFiles = new HashSet<>();

		product.setProductId(System.currentTimeMillis());
		logger.info("product " + product);
		// product.setImages(imgFiles);
		productRepository.save(product);
		for (MultipartFile file : images) {
			try {

				imgFiles.add(new ProductImage(convertMultipartFileToBytes(file), product, false));
			} catch (Exception ex) {
				return ResponseEntity.ok(new ErrorResponse(500, ex.getMessage()));
			}

		}

		if (!imgFiles.isEmpty()) {
			productImageRepository.saveAll(imgFiles);
		}

		return ResponseEntity.ok(new SuccessResponse("Product Added successfully"));
	}

	@Transactional(noRollbackFor = MissingPathVariableException.class)
	@PostMapping("/addImageToProduct/{productId}")
	public ResponseEntity<Response> addImageToProduct(@PathVariable("productId") Long productId,
			@RequestPart("primaryImage") MultipartFile primaryImage,
			@RequestParam(value = "primary", defaultValue = "false") boolean primary) {

		try {
			Product product = new Product();
			product.setProductId(productId);
			ProductImage productImage = new ProductImage(convertMultipartFileToBytes(primaryImage), product, primary);
			productImageRepository.save(productImage);

			return ResponseEntity.ok(new SuccessResponse("Primary Image Added successfully"));
		} catch (Exception ex) {
			return ResponseEntity.ok(new ErrorResponse(500, ex.getMessage()));
		}

	}

	private byte[] convertMultipartFileToBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert file to bytes", e);
		}
	}

}
