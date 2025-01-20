import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductInfo, ProductCreate, Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8081/api/products';

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  search(req: { code: string, brand: string }): Observable<Product[]> {
    return this.http.post<Product[]>(this.apiUrl + "/search", req);
  }

  getAllProductsWithoutPrices(query: string): Observable<ProductInfo[]> {
    return this.http.get<ProductInfo[]>(`${this.apiUrl}/search?query=${query}`);
  }

  createProduct(productRequest: ProductCreate[]): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, productRequest);
  }

  deleteProductById(id: number): Observable<void> {
    return this.http.delete<void>(this.apiUrl + "/" + id);
  }

  deduct(products: { id: number; quantity: number }[]): Observable<void> {
    return this.http.post<void>(this.apiUrl + "/deduct", products);
  }
}
