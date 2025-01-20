import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  searchQuery: string = '';
  filteredResults: any[] = [];

  constructor(private router: Router, private productService: ProductService) { }

  onSearch() {
    if (this.searchQuery.trim().length > 0) {
      this.productService.getAllProductsWithoutPrices(this.searchQuery).subscribe((results) => {
        this.filteredResults = results;
      });
    } else {
      this.filteredResults = [];
    }
  }

  onSelect(result: any) {
    this.router.navigate(['/search'], {
      queryParams: {
        code: result.code,
        brand: result.brand
      }
    });
    this.filteredResults = [];
  }
}