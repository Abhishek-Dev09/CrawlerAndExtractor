package com.springrest.springrest.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.springframework.stereotype.Service;

import com.springrest.springrest.models.PriceModel;
import com.springrest.springrest.models.ProductModel;

@Service
public class ProductServiceImpl implements ProductService {
	private final String amazonUrl = "https://www.amazon.in";
	private HashMap<String, ArrayList<ProductModel>> crawledItems = new HashMap<String, ArrayList<ProductModel>>();

	@Override
	public ProductModel getProductDetailsById(String id) throws Exception {
		//Return product detail of crawled  pages by sku/id.
		long startTime = System.nanoTime();
		String url = getUrl(id);
		if(!crawledItems.containsKey(url)) {
			throw new Exception("This Product is not crawled yet . First crawled it by GetHtml Api");
		}
		
		ArrayList<ProductModel> modelList = crawledItems.get(url);
		ProductModel model = modelList.get(modelList.size()-1);
		crawledItems.put(url,modelList);
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total time for execution of getProductDetailsById api:"+ totalTime + "ns");
	    return model;
	}
	
	public ProductModel CrawlById(String id) throws Exception {
		//Return model after crawling the page.
		String url = getUrl(id);
		if(crawledItems.containsKey(url)){
			Timestamp lasttime = crawledItems.get(url).get(0).getTime();
			long last = lasttime.getTime();
			long current = System.currentTimeMillis();
			long diff = current - last;
			long diffHours = diff / (60 * 60 * 1000);
			if(diffHours<1) {
				throw new Exception("This Product has been crawled within an hour");
			}
		}
		Document doc = getDocument(url);

		if (doc == null) {
			throw new Exception("Not able to fetch document");
		}

		ProductModel model = new ProductModel();
		model.title(extractProductName(doc));
		model.setPrice(extractProductPrice(doc));
		model.setDescription(extractProductDescription(doc));
		model.setTime(new Timestamp(System.currentTimeMillis()));
	    return model;
	}
    @Override
	public String getHtml(String id) throws Exception {
    	//Return Html after crawling the page.
    	long startTime = System.nanoTime();
		String url = getUrl(id);
		if(!crawledItems.containsKey(url)) {
			ProductModel model = CrawlById(id);
			ArrayList<ProductModel> modelList = new ArrayList<ProductModel>();
			modelList.add(model);
			crawledItems.put(url,modelList);
		}
		else if(crawledItems.containsKey(url)) {
			ArrayList<ProductModel> modelList = crawledItems.get(url);
			ProductModel model = CrawlById(id);
			modelList.add(model);
			crawledItems.put(url,modelList);
		}
		Document doc = getDocument(url);
		if (doc == null) {
			throw new Exception("Not able to fetch document");
		}
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total time for execution of getHtml api:" + totalTime + "ns");
		return doc.toString();

	}
	@Override
	public ArrayList<ProductModel> getList() throws Exception {
		//Return list of all crawled products.
		long startTime = System.nanoTime();
		ArrayList<ProductModel> List = new ArrayList<ProductModel>();
		for (Map.Entry mapElement : crawledItems.entrySet()) {
			ArrayList<ProductModel> modelList = (ArrayList<ProductModel>) mapElement.getValue();
			ProductModel model = modelList.get(modelList.size()-1);
            List.add(model);
        }
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total time for execution of getList api:"+ totalTime + "ns");
		return List;
		
	}
	@Override
	public ProductModel GetProductDetailsHistory(String id,String timeStamp) throws Exception {
		//Return details of a newest crawled products by sku before timestamp.
		long startTime = System.nanoTime();
		String url = getUrl(id);
		if(!crawledItems.containsKey(url)) {
			throw new Exception("This Product is not crawled yet . First crawled it by GetHtml Api");
		}
		Timestamp ts = Timestamp.valueOf(timeStamp);
		ArrayList<ProductModel> modelList = crawledItems.get(url);
		int i;
		for(i=0;i<modelList.size();i++) {
			ProductModel model = modelList.get(i);
			int diff = ts.compareTo(model.getTime());
			if(diff<0)
				break;
		}
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total time for execution of GetProductDetailsHistory api:" + totalTime + "ns");
		return i>0?modelList.get(i-1):null;
		
	}
	
	@Override
	public ArrayList<PriceModel> getPriceTrend(String id) throws Exception {
		//Return Price trend of crawled products by sku in sorted order by timestamp.
		long startTime = System.nanoTime();
		ArrayList<PriceModel> Pricelist = new ArrayList<PriceModel>();
		String url = getUrl(id);
		if(!crawledItems.containsKey(url)) {
			throw new Exception("This Product is not crawled yet . First crawled it by GetHtml Api");
		}
		ArrayList<ProductModel> modelList = crawledItems.get(url);
		for(int i=0;i<modelList.size();i++) {
			ProductModel model = modelList.get(i);
			PriceModel pricemodel = new PriceModel();
			pricemodel.setTimestamp(model.getTime());
			pricemodel.setPrice(model.getofferPrice());
			Pricelist.add(pricemodel);
		}
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total time for execution of GetPriceTrend api:" + totalTime + "ns");
		return Pricelist;
		
	}
	private Document getDocument(String url) throws IOException {
		//Return document for corresponding sku/id/url.
		Document doc = Jsoup.connect(url).userAgent("Chrome").timeout(5000).get();
		return doc;
	}

	private String getUrl(String id) {
		//Return a url for corresponding sku/id/url. 
		return amazonUrl + "/dp/" + id;
	}

	private String extractProductName(Document doc) {
		//Return title for corresponding sku/id/url. 
		return doc.title();
	}
	private String extractProductPrice(Document doc) {
		//Return price for corresponding sku/id/url.
		Elements price = doc.select("#priceblock_ourprice");
		return price.text();
	}
	private String extractProductDescription(Document doc) {
		//Return description for corresponding sku/id/url.
		Elements desc = doc.select("#productDescription > p");
		return desc.text();
	}
}
