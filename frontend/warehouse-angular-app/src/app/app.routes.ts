import { Routes } from '@angular/router';
import { ProductCreateComponent } from './components/product-create/product-create.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'products', component: ProductSearchComponent},
    {path: 'create', component: ProductCreateComponent},
    {path: 'search', component: ProductSearchComponent}
];
