import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { ProductCreate } from '../../models/product.model';

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent {

  productForm: FormGroup;
  suggestions: { [index: number]: any[] } = {};

  showSuccessAlert = false;
  showErrorAlert = false;

  constructor(private fb: FormBuilder, private productService: ProductService) {
    this.productForm = this.fb.group({
      products: this.fb.array([this.createProduct()]) 
    });
  }

  get products(): FormArray {
    return this.productForm.get('products') as FormArray;
  }

  private createProduct(): FormGroup {
    return this.fb.group({
      code: ['', Validators.required],
      brand: ['', Validators.required],
      name: ['', Validators.required],
      price: [1.00, [Validators.required, Validators.min(0.01)]],
      quantity: [1, [Validators.required, Validators.min(1)]]
    });
  }

  addProduct() {
    this.products.push(this.createProduct());
  }

  removeProduct(index: number) {
    this.products.removeAt(index);
  }

  submitForm() {
    if (this.productForm.valid) {
      const req: ProductCreate[] = this.products.value.map((product: any) => ({
        code: product.code,
        brand: product.brand,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
      }));


      this.productService.createProduct(req).subscribe({
        next: (response) => {
          console.log('Products successfully created:', response);
          this.productForm.reset();
          this.products.clear();
          this.products.push(this.createProduct());

          this.showSuccessAlert = true;
          setTimeout(() => this.showSuccessAlert = false, 9000);

        },
        error: (err) => {
          console.error('Error creating products:', err);

          this.showErrorAlert = true;
          setTimeout(() => this.showErrorAlert = false, 9000);
        },
      });
    } else {
      console.error('Form is invalid');
      this.productForm.markAllAsTouched();

      this.showErrorAlert = true;
      setTimeout(() => this.showErrorAlert = false, 9000);
    }
  }

  onCodeInput(event: Event, index: number) {
    const inputElement = event.target as HTMLInputElement;
    const value = inputElement.value;

    if (value.length < 3) {
      this.suggestions[index] = [];
      return;
    }

    this.productService.getAllProductsWithoutPrices(value).subscribe((results) => {
      this.suggestions[index] = results;
    });
  }

  selectProduct(product: any, index: number) {
    const productGroup = this.products.at(index);
    productGroup.patchValue({
      code: product.code,
      brand: product.brand,
      name: product.name,
      price: 1,
      quantity: 1
    });

    this.suggestions[index] = [];
  }

}
