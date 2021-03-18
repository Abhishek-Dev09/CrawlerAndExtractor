# CrawlerAndExtractor

A service which takes an input amazon url or a product SKU (unique identifier for every product) and crawl the amazon page and extract several attributes.

sku : B07XMLWH7J

New url formed by just sku : https://www.amazon.in/dp/B07XMLWH7J/

## API 

1. GetHtml 

This api takes a sku ,crawls it and stores the html and returns it. 

Get - http://localhost:8080/GetHtml/sku


2. GetProductDetails 

This api takes an input  a sku. Return a json of below attributes. 
A. page title
B. offerPrice
C. Product description
D. Timestamp

Get - http://localhost:8080/GetProductDetails/sku


3. GetProductDetailsHistory

This api will take a date or a timestamp as well, this will return the info for the newest page crawled before the given timestamp.

timestamp format - YYYY-MM-DD HH:MM:SS

Get - http://localhost:8080/GetProductDetailsHistory/sku/timestamp

Example - http://localhost:8080/GetProductDetailsHistory/B07XMLWH7J/2021-03-18 14:00:00
4. List All crawled Products and details till now.

Return all crawled Products with latest page.

Get - http://localhost:8080/ListAll

5. PriceTrend

Return price trend of product by taking sku which shows all prices collected till now sorted by timestamp. 

Get - http://localhost:8080/PriceTrend/sku

### Packages/Frameworks
1. Jsoup - A Java library for working with real-world HTML. It provides a very convenient API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM methods and CSS selectors.
2. springframework - application framework and inversion of control container for the Java platform. 

## Output
*See screen shot directory.All outputs has been recoded by Postman.*
