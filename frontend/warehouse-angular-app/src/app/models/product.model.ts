export interface Price {
    id: number;
    value: string;
    quantity: string;
    createdAt: string;
}

export interface Product {
    id: number;
    code: string;
    name: string;
    brand: string;
    prices: Price[];
}

export interface ProductInfo {
    id: number;
    name: string;
    brand: string;
    code: string;
}
  
export interface ProductCreate {
    name: string;
    code: string;
    brand: string;
    price: number;
    quantity: number;
}
  