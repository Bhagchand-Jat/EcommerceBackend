/**
 * 
 */
package com.ecommerce.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exceptions.CartNotFoundException;
import com.ecommerce.exceptions.CustomException;
import com.ecommerce.exceptions.GlobalExceptionHandler;
import com.ecommerce.exceptions.PageNotFoundException;
import com.ecommerce.exceptions.UserAlreadyExistException;
import com.ecommerce.exceptions.UserNotFoundException;
import com.ecommerce.jwt.JwtTokenProvider;
import com.ecommerce.modals.Address;
import com.ecommerce.modals.Cart;
import com.ecommerce.modals.CartItem;
import com.ecommerce.modals.Product;
import com.ecommerce.modals.ProductImage;
import com.ecommerce.modals.ProductWithImage;
import com.ecommerce.modals.User;
import com.ecommerce.modals.UserImage;
import com.ecommerce.modals.ValidationGroups;
import com.ecommerce.modals.Wallet;
import com.ecommerce.repositories.AddressRepository;
import com.ecommerce.repositories.ProductImageRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.TokenRepository;
import com.ecommerce.repositories.UserImageRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.repositories.WalletRepository;
import com.ecommerce.response.ErrorResponse;
import com.ecommerce.response.Response;
import com.ecommerce.response.SuccessResponse;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OtpService;
import com.ecommerce.userdetails.CustomUserDetail;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 
 */
@Validated
@RequestMapping("/ecommerce/api/user")
@RestController
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private GlobalExceptionHandler exceptionHandler;

	@Autowired
	private CartService cartService;
	@Autowired
	private UserImageRepository userImageRepository;

	@Transactional(noRollbackFor = MethodArgumentNotValidException.class)
	@PostMapping("/signup")
	public ResponseEntity<Response> signUp(@Validated(ValidationGroups.Create.class) @RequestBody User user) {

		user.setUserId(System.currentTimeMillis());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedOn(LocalDateTime.now());
		Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
		if (userByEmail.isPresent()) {
			if (userByEmail.get().isVerified()) {
				return exceptionHandler.userAlreadyExist(new UserAlreadyExistException("User Already Exist"));
			} else {
				// otpService.deleteByUserId(userByEmail.get().getUserId());
				userRepository.deleteById(userByEmail.get().getUserId());
			}

		}
		user.setRoles("USER");
		userRepository.save(user);

		if (userRepository.existsById(user.getUserId())) {

			return ResponseEntity.ok(otpService.sendOtpForUser(user));

		} else {
			logger.info("User is : ---> " + user.toString());
			return exceptionHandler.customException(new CustomException("User Not found"), HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(HttpServletRequest request) {

		logger.info("login success");
		String token = (String) request.getAttribute("token");

		Map<String, String> result = new HashMap<>();

		result.put("token", token);
		return ResponseEntity.ok(new SuccessResponse(result));

	}

	@Transactional
	@PostMapping("/otp-authenticate")
	public ResponseEntity<Response> otpAuthentication(HttpServletRequest request) {
		logger.info("otp authenticated successfully");
		User user = (User) request.getAttribute("user");
		int results = userRepository.updateIsVerifiedByUserId(true, user.getUserId());
		if (results == 0) {
			logger.info("Update unsuccessful");
		}
		Cart cart = cartService.createCart(user);
		logger.info("cart is ---> " + cart);
		String token = (String) request.getAttribute("token");
		Map<String, String> result = new HashMap<>();

		result.put("token", token);
		return ResponseEntity.ok(new SuccessResponse(result));
	}

//	@Transactional
//	@PostMapping("/otp/send")
//	public ResponseEntity<Response> sendOtp(HttpServletRequest request) {
//		User user = (User) request.getAttribute("user");
//
//		return ResponseEntity.ok(otpService.sendOtpForUser(user));
//	}

	@Transactional
	@PostMapping("/otp/resend")
	public ResponseEntity<Response> resendOtp(HttpServletRequest request, @RequestParam("otpId") Long otpId) {

		Optional<User> optionalUser = userRepository.findByOtpId(otpId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return ResponseEntity.ok(otpService.resendOtp(otpId, user.getEmail()));
		}

		return ResponseEntity.ok(new ErrorResponse(400, "Invalid otpId"));
	}

	@Transactional
	@PostMapping("/forgot-password")
	public ResponseEntity<Response> forgotPassword(
			@Valid @Email(message = "Enter valid Email") @RequestParam String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User Not found");
		}
		User user = optionalUser.get();
		String token = jwtTokenProvider.generateRefreshToken(new CustomUserDetail(user.getEmail(), user.getPassword(),
				user.getUserId(), Set.copyOf(Arrays.asList(user.getRoles().split(",")))));
		return ResponseEntity.ok(otpService.sendPasswordResetEmail(user, token));
	}

	@Transactional(noRollbackFor = MethodArgumentNotValidException.class)
	@PostMapping("/addAddress")
	public ResponseEntity<Response> addAddrress(@Validated(ValidationGroups.Create.class) @RequestBody Address address,
			HttpServletRequest request) {

		User user = (User) request.getAttribute("user");
		logger.info("user --> " + user);
		logger.info("address --> " + address);
		address.setAddressId(System.currentTimeMillis());
		address.setUser(user);
		address = addressRepository.save(address);
		logger.info("address  is --> " + address);
		return ResponseEntity.ok(new SuccessResponse("Address Added Successfully"));

	}

	@GetMapping("/products")
	public ResponseEntity<Response> getAllProducts(HttpServletRequest request,
			@RequestParam(value = "productType", defaultValue = "") String productType,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequest pageable = PageRequest.of(page, size);
		Page<Product> productPage = productType.isBlank() ? productRepository.findAll(pageable)
				: productRepository.findAllByProductType(productType, pageable);
		if (page >= productPage.getTotalPages() && productPage.getTotalPages() > 0) {
			throw new PageNotFoundException("Page " + page + " does not exist.");
		}
		List<ProductWithImage> productsWithImages = productPage.stream().map(product -> {
			List<ProductImage> images = productImageRepository.getAllByProduct(product);
			images.forEach(image -> image.setProduct(null));
			return new ProductWithImage(product, images);

		}).toList();

		return ResponseEntity.ok(new SuccessResponse(productsWithImages));
	}

	@Transactional
	@PostMapping("/create_wallet")
	public ResponseEntity<Response> createWallet(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		logger.info("user is ---> " + user);
		Optional<Wallet> optionalWallet = walletRepository.getWalletByUser(user);
		if (optionalWallet.isEmpty()) {
			Wallet wallet = new Wallet();
			// wallet.setWalletId(System.currentTimeMillis());
			wallet.setUser(user);
			walletRepository.save(wallet);
			return ResponseEntity.ok(new SuccessResponse("Wallet Created Successfully"));
		} else {
			return ResponseEntity.ok(new SuccessResponse("Wallet Already Exist"));
		}
	}

	@Transactional(rollbackFor = { MethodArgumentNotValidException.class, CustomException.class })
	@PostMapping("/cart/add_item")
	public ResponseEntity<Response> addItemToCart(
			@Validated(ValidationGroups.Create.class) @RequestBody CartItem cartItem, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		Optional<Cart> cart = cartService.getCartByUser(user);

		if (cart.isEmpty()) {
			return exceptionHandler
					.cartNotFound(new CartNotFoundException("Cart does not initialized for the current user"));
		}

		logger.info("Cart data is --->" + cart.get());
		logger.info("Cart Item is --->" + cartItem);
		Optional<Product> optionalProduct = productRepository.findById(cartItem.getProduct().getProductId());
		if (optionalProduct.isPresent()) {
			cartItem.setItemId(System.currentTimeMillis());
			cartService.addItemToCart(cart.get(), cartItem);
			return ResponseEntity.ok(new SuccessResponse("Item Added to  Cart Successfully"));
		}
		return exceptionHandler.customException(new CustomException("Product with this id is no longer exist."),
				HttpStatus.NOT_FOUND);
	}

	@Transactional
	@DeleteMapping("/cart/delet_cart_item/{item_id}")
	public ResponseEntity<Response> deleteCartItem(@PathVariable("item_id") Long itemId) {

		cartService.deleteCartItem(itemId);
		return ResponseEntity.ok(new SuccessResponse("Item Deleted Successfully"));
	}

	@GetMapping("/cart")
	public ResponseEntity<Response> getCart(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		Optional<Cart> cart = cartService.getCartByUser(user);
		Map<Object, Object> result = cart.get().getCartItems().stream()
				.collect(Collectors.toMap(cartItem -> cartItem.getItemId(), // Replace with the actual key extraction
						cartItem -> {

							Map<String, Object> map = new HashMap<>();
							map.put("cartItem", cartItem);
							Optional<ProductImage> primaryImage = productImageRepository
									.getByProductAndPrimary(cartItem.getProduct(), true);
							if (primaryImage.isPresent()) {
								map.put("primaryImage", primaryImage.get());
							}
							return map;
						}, (existingMap, newMap) -> {

							return existingMap;
						}));

		if (cart.isEmpty()) {
			return exceptionHandler
					.cartNotFound(new CartNotFoundException("Cart does not initialized for the current user"));
		}
		return ResponseEntity.ok(new SuccessResponse(result));
	}

	@Transactional(noRollbackFor = { MethodArgumentNotValidException.class, CustomException.class,
			ConstraintViolationException.class })
	@PostMapping("/reset-password")
	public ResponseEntity<Response> resetPassword(
			@Valid @NotEmpty(message = "Password cannot be null or Empty") @Size(min = 8, message = "Minimum Password length should be 8") @RequestBody String newPassword,
			HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		logger.info("new Password " + newPassword);
		if (passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new ConstraintViolationException("Cannot use Last used password", new HashSet<>());
		} else {
			newPassword = passwordEncoder.encode(newPassword);
			userRepository.updatePasswordByUserId(newPassword, user.getUserId());
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				logger.info("Header Name: " + headerName + ", Value: " + request.getHeader(headerName));
			}
			logger.info("hearder " + request.getHeader("tokenId"));

			tokenRepository.deleteById(Long.valueOf(request.getHeader("tokenId")));
			return ResponseEntity.ok(new SuccessResponse("Password Reset successfully"));

		}

	}

	@GetMapping("/get_user_image")
	public ResponseEntity<Response> getUserImage(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		Optional<UserImage> userImage = userImageRepository.getByUser(user);
		if (userImage.isEmpty()) {
			return ResponseEntity.ok(new ErrorResponse(202, "Image Not Found"));
		}
		return ResponseEntity.ok(new SuccessResponse(userImage.get()));
	}

	@Transactional(noRollbackFor = { MethodArgumentNotValidException.class, CustomException.class })
	@PutMapping("/updateUser")
	public ResponseEntity<Response> updateUser(@Valid @ModelAttribute User user,
			@RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
			HttpServletRequest request) {
		User userData = (User) request.getAttribute("user");
		user.setUserId(userData.getUserId());
		logger.info("user" + user);
		try {
			if (profileImage != null) {
				UserImage userImg = new UserImage(convertMultipartFileToBytes(profileImage));
				userImg.setUser(user);
				userImageRepository.save(userImg);
			}
			if (!user.getFullName().isBlank() && !userData.getFullName().equals(user.getFullName())) {

				userRepository.updateFullNameByUserId(user.getFullName(), user.getUserId());

			}

		} catch (Exception ex) {
			return ResponseEntity.ok(new ErrorResponse(500, ex.getMessage()));
		}

		return ResponseEntity.ok(new SuccessResponse("User updated successfully"));
	}

	private byte[] convertMultipartFileToBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert file to bytes", e);
		}
	}

}
