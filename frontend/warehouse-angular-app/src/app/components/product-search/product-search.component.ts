import { Component, OnInit } from '@angular/core';
import { Product, ProductInfo } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-search.component.html',
  styleUrl: './product-search.component.css'
})
export class ProductSearchComponent implements OnInit {
  products: Product[] = [];
  productsWithoutPrices: ProductInfo[] = [];
  code: string = "";
  brand: string = "";
  showSuccessAlert = false;
  showErrorAlert = false;
  alertMessage = '';

  sellItems: {
    id: number;
    price: string;
    quantity: number;
    createdAt: string;
    sellQuantity: number
  }[] = [];

  currentProduct: Product = {
    id: 0,
    code: '',
    name: '',
    brand: '',
    prices: []
  };

  constructor(private route: ActivatedRoute, private productService: ProductService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.code = params['code'];
      this.brand = params['brand'];
    })
    this.loadProducts();
  }

  loadProducts(): void {
    if (this.code && this.brand) {
      this.productService.search({ code: this.code, brand: this.brand }).subscribe((data) => {
        this.products = data;
      });
    } else {
      this.productService.getAllProducts().subscribe((data) => {
        this.products = data;
      });
    }
  }

  delete(id: number): void {
    this.productService.deleteProductById(id).subscribe(() => {
      this.products = this.products.filter((product) => product.id !== id);
      this.createSuccessAlert("Удалено!");
    });
  }

  isModalOpen = false;

  openModal(product: Product): void {
    this.currentProduct = product;
    this.sellItems = product.prices.map((price) => ({
      id: price.id,
      quantity: +price.quantity,
      sellQuantity: 0,
      price: price.value,
      createdAt: price.createdAt
    }));
    this.isModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  confirmSell(): void {
    const req = this.sellItems.map((item) => ({
      id: item.id,
      quantity: item.sellQuantity
    }));
    this.productService.deduct(req).subscribe(() => {
      console.log("Deduct succesfuly");
      this.createSuccessAlert("Усепшно списано");
      this.loadProducts();
      this.isModalOpen = false;
    });
  }

  updateQuantity(id: number, newQuantity: number) {
    const existingItem = this.sellItems.find(item => item.id === id);
    if (existingItem) {
      existingItem.sellQuantity = newQuantity;
    }
  }

  createSuccessAlert(message: string) {
    this.alertMessage = message;
    this.showSuccessAlert = true;
    setTimeout(() => this.showSuccessAlert = false, 9000);
  }
}
