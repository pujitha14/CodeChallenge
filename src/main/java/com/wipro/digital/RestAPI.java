package com.wipro.digital;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(value = "rest")
public class RestAPI {

	@RequestMapping(value = "product", method = RequestMethod.GET)

	public String product(@RequestParam float number1, @RequestParam float number2) {

		JSONObject result = new JSONObject();
		float product = 0;
		try {
			product = number1 * number2;
			DecimalFormat df = new DecimalFormat("#.##");
			result.put("data", df.format(product));
			result.put("status", HttpStatus.OK.name());
			result.put("code", HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return result.toString();
	}

	@RequestMapping(value = "filecontent", method = RequestMethod.GET)

	public String fileContent(@RequestParam String filePath) {

		JSONObject result = new JSONObject();

		try {
			String data = "";
			data = new String(Files.readAllBytes(Paths.get(filePath)));
			result.put("data", data);
			result.put("status", HttpStatus.OK.name());
			result.put("code", HttpStatus.OK.value());
		} catch (Exception e) {

			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message", e.getClass());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(result);
		return result.toString();
	}

	@RequestMapping(value = "writetofile", method = RequestMethod.GET)

	public String WriteToFile(@RequestParam String fileContent) {

		JSONObject result = new JSONObject();

		try {
			Path path = Paths.get("WriteToFile.txt");
			byte[] strToBytes = fileContent.getBytes();
			Files.write(path, strToBytes);
			String read = Files.readAllLines(path).get(0);
			result.put("data", read);
			result.put("status", HttpStatus.OK.name());
			result.put("code", HttpStatus.OK.value());

		} catch (Exception e) {

			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message", e.getClass());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(result);
		return result.toString();
	}

	@RequestMapping(value = "nonrepeatingchar", method = RequestMethod.GET)
	public String NonRepeatingCharacter(@RequestParam String word) {
		Set<Character> repeating = new HashSet<>();
		List<Character> nonRepeating = new ArrayList<>();
		JSONObject result = new JSONObject();
		try {
			for (int i = 0; i < word.length(); i++) {
				char letter = word.charAt(i);
				if (repeating.contains(letter)) {
					continue;
				}
				if (nonRepeating.contains(letter)) {
					nonRepeating.remove((Character) letter);
					repeating.add(letter);
				} else {
					nonRepeating.add(letter);
				}
			}
			if(nonRepeating.size()==0){
				result.put("data", "No non-repeating character");
			}else{
				result.put("data", nonRepeating.get(0));
			}
			result.put("status", HttpStatus.OK.name());
			result.put("code", HttpStatus.OK.value());
		} catch (Exception e) {

			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message",e.getClass());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result.toString();
	}

	@RequestMapping(value = "webcrawler", method = RequestMethod.GET)

	public String WebCrawler(@RequestParam String url) {

		JSONObject result = new JSONObject();

		try {
			result=getPageLinks(url);
		} catch (Exception e) {

			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message", e.getClass());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(result);
		return result.toString();
	}

	public JSONObject getPageLinks(String URL) {
		JSONObject result = new JSONObject();
		HashSet<String> links = new HashSet<String>();
		URI uri = null;
		URI uri1 = null;
		try {
			uri = new URI(URL);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				result.put("status", HttpStatus.BAD_REQUEST.name());
				result.put("error_code", HttpStatus.BAD_REQUEST.value());
				result.put("message", e.getClass());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String domain = uri.getHost();
		if (!links.contains(URL)) {
			try {
				if (links.add(URL)) {
					//System.out.println(URL);
				}

				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");

				for (Element page : linksOnPage) {
					try {
						uri1 = new URI(page.attr("abs:href"));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (domain.equals(uri1.getHost()))
						getPageLinks(page.attr("abs:href"));
				}
				result.put("data", links);
				result.put("status", HttpStatus.OK.name());
				result.put("code", HttpStatus.OK.value());
			} catch (IOException e) {
				e.printStackTrace();
				try {
					result.put("status", HttpStatus.BAD_REQUEST.name());
					result.put("error_code", HttpStatus.BAD_REQUEST.value());
					result.put("message", e.getClass());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
				try {
					result.put("status", HttpStatus.BAD_REQUEST.name());
					result.put("error_code", HttpStatus.BAD_REQUEST.value());
					result.put("message", e.getClass());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
}
