package com.hackerrank.sample.controller;

import com.hackerrank.sample.clients.HackerrankAPIClient;
import com.hackerrank.sample.dto.FilteredProducts;
import com.hackerrank.sample.dto.SortedProducts;
import com.hackerrank.sample.models.hackerrank.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
public class SampleController {


	final String uri = "https://jsonmock.hackerrank.com/api/inventory?page=%s";
	RestTemplate restTemplate = new RestTemplate();
	String result = restTemplate.getForObject(uri, String.class);
	JSONObject root = new JSONObject(result);
	JSONArray data = root.getJSONArray("data");

	private final HackerrankAPIClient apiClient = new HackerrankAPIClient(this.restTemplate);

	@CrossOrigin
	@GetMapping("/filter/price/{initial_price}/{final_price}")
	private ResponseEntity< ArrayList<FilteredProducts> > filtered_books(
			@PathVariable("initial_price") int init_price ,
			@PathVariable("final_price") int final_price) {
		try {
			final ArrayList<FilteredProducts> books = this.apiClient.invokeAPI(uri)
					.filter(data -> data.getPrice() >= init_price && data.getPrice() <= final_price)
					.map(Data::getBarCode)
					.map(FilteredProducts::new)
					.collect(Collectors.toCollection(ArrayList<FilteredProducts>::new));

			if (books.isEmpty())
				return ResponseEntity.status(400).build();

			return new ResponseEntity<ArrayList<FilteredProducts>>(books, HttpStatus.OK);
		} catch(Exception E) {
			System.out.println("Error encountered : " + E.getMessage());
			return new ResponseEntity<ArrayList<FilteredProducts>>(HttpStatus.NOT_FOUND);
		}
	}


	@CrossOrigin
	@GetMapping("/sort/price")
	private ResponseEntity<SortedProducts[]> sorted_books() {
		try {
			final SortedProducts[] ans = this.apiClient.invokeAPI(uri)
					.sorted(Comparator.comparingInt(Data::getPrice))
					.map(Data::getBarCode)
					.map(SortedProducts::new)
					.toArray(SortedProducts[]::new);
			return new ResponseEntity<SortedProducts[]>(ans, HttpStatus.OK);
		} catch(Exception E) {
			System.out.println("Error encountered : "+E.getMessage());
			return new ResponseEntity<SortedProducts[]>(HttpStatus.NOT_FOUND);
		}
	}
}
