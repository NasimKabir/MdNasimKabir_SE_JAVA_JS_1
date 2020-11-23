package com.spring.service.impl;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.assembler.ProductAssembler;
import com.spring.dto.ProductDto;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;
import com.spring.model.Product;
import com.spring.repository.ProductRepository;
import com.spring.repository.ProductTypeRepository;
import com.spring.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Value("${file.upload}")
	private String defaultFilePath;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProductAssembler productAssembler;
	@SuppressWarnings("rawtypes")
	@Autowired
	private PagedResourcesAssembler pagedResourcesAssembler;

	@Override
	public Response productInserted(ProductDto productDto, @RequestParam("file") MultipartFile file) {
		Product product = modelMapper.map(productDto, Product.class);
		double gainProfit = productDto.getSellingPrice() - productDto.getCostPrice();
		double profit = (gainProfit / productDto.getCostPrice()) * 100;
		product.setProfitPercentage(profit);
		product.setStatus(true);

		String categoryName = categoryRepository.findAll().listIterator().next().getName();

		try {
			FileManageServiceImpl.uploadFile(file, categoryName, productDto.getName(), defaultFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product = productRepository.save(product);
		if (product != null) {
			return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Product Creation Successfully",
					product.getName());
		}

		return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

	@Override
	public Response updateProduct(Long id, ProductDto productDto) {
		Product product = productRepository.findById(id).get();
		if (product != null) {
			modelMapper.map(productDto, Product.class);
			double gainProfit = productDto.getSellingPrice() - productDto.getCostPrice();
			double profit = (gainProfit / productDto.getCostPrice()) * 100;
			product.setProfitPercentage(profit);
			product = productRepository.save(product);
			if (product != null) {
				return ResponseBuilder.getSuccessResponse(HttpStatus.OK, " updated Successfully", null);
			}

			return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, " not found");
	}

	@Override
	public Response deleteProduct(Long id) {
		Product product = productRepository.findById(id).get();
		if (product != null) {

			product = productRepository.save(product);
			if (product != null) {
				return ResponseBuilder.getSuccessResponse(HttpStatus.OK, " deleted Successfully", null);
			}
			return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, " not found");
	}

	@Override
	public Response getProduct(Long id) {
		Product product = productRepository.findById(id).get();
		if (product != null) {
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			if (productDto != null) {
				return ResponseBuilder.getSuccessResponse(HttpStatus.OK, " retrieved Successfully", productDto);
			}
			return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, " not found");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PagedModel getAllProduct(int page, int size, String[] sort, String dir) {
		PageRequest pageRequest;
		Sort.Direction direction;
		if (sort == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			if (dir.equalsIgnoreCase("asc"))
				direction = Sort.Direction.ASC;
			else
				direction = Sort.Direction.DESC;
			pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));
		}
		Page<Product> products = productRepository.findAll(pageRequest);
		if (!CollectionUtils.isEmpty(products.getContent())) {
			return pagedResourcesAssembler.toModel(products, productAssembler);
		}
		return null;
	}

	
	@Override
	public Response min() {
		String minPrice = String.valueOf(productRepository.min());
		System.out.println("******************************" + minPrice);
		if (minPrice != null) {
			return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Minimum price is " + minPrice);
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, "Not found");
	}

	@Override
	public Response max() {
		String maxPrice = String.valueOf(productRepository.max());
		System.out.println("******************************" + maxPrice);
		if (maxPrice.length() > 0) {
			return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Maximum price is " + maxPrice);
		}
		return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, "Not found");
	}

}
